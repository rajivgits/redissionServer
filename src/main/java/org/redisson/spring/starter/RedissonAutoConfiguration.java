/**
 * Copyright (c) 2013-2020 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.redisson.spring.starter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * @author Nikita Koksharov
 * @author Nikos Kakavas (https://github.com/nikakis)
 *
 */
@Configuration
@ConditionalOnClass({Redisson.class, RedisOperations.class})
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties({RedissonProperties.class, RedisProperties.class})
public class RedissonAutoConfiguration {

  // @Autowired(required = false)
  // private List<RedissonAutoConfigurationCustomizer> redissonAutoConfigurationCustomizers;

  @Autowired
  private RedissonProperties redissonProperties;

  @Autowired
  private RedisProperties redisProperties;

  @Autowired
  private ApplicationContext ctx;

  @Bean
  @Primary
  @Qualifier
  public ReactiveRedisTemplate<String, OrderEntity> reactiveRedisTemplate(
      ReactiveRedisConnectionFactory factory) {

    StringRedisSerializer keySerializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer<OrderEntity> valueSerializer =
        new Jackson2JsonRedisSerializer<>(OrderEntity.class);
    RedisSerializationContext.RedisSerializationContextBuilder<String, OrderEntity> builder =
        RedisSerializationContext.newSerializationContext(keySerializer);
    RedisSerializationContext<String, OrderEntity> context = builder.value(valueSerializer).build();

    return new ReactiveRedisTemplate<>(factory, context);
  }

  @Bean
  @Primary
  @Qualifier
  public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
    return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
  }


  @Bean(destroyMethod = "shutdown")
  @ConditionalOnMissingBean(RedissonClient.class)
  public RedissonClient redisson() throws IOException {
    Config config = null;
    Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
    Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
    Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
    int timeout;
    if (null == timeoutValue) {
      timeout = 10000;
    } else if (!(timeoutValue instanceof Integer)) {
      Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
      timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
    } else {
      timeout = (Integer) timeoutValue;
    }

    if (redissonProperties.getConfig() != null) {
      try {
        InputStream is = getConfigStream();
        config = Config.fromJSON(is);
      } catch (IOException e) {
        // trying next format
        try {
          InputStream is = getConfigStream();
          config = Config.fromYAML(is);
        } catch (IOException e1) {
          throw new IllegalArgumentException("Can't parse config", e1);
        }
      }
    } else if (redisProperties.getSentinel() != null) {
      Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, "getNodes");
      Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

      String[] nodes;
      if (nodesValue instanceof String) {
        nodes = convert(Arrays.asList(((String) nodesValue).split(",")));
      } else {
        nodes = convert((List<String>) nodesValue);
      }

      config = new Config();
      config.useSentinelServers().setMasterName(redisProperties.getSentinel().getMaster())
          .addSentinelAddress(nodes).setDatabase(redisProperties.getDatabase())
          .setConnectTimeout(timeout).setPassword(redisProperties.getPassword());
    } else if (clusterMethod != null
        && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
      Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
      Method nodesMethod = ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
      List<String> nodesObject = (List) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);

      String[] nodes = convert(nodesObject);

      config = new Config();
      config.useClusterServers().addNodeAddress(nodes).setConnectTimeout(timeout)
          .setPassword(redisProperties.getPassword());
    } else {
      config = new Config();
      String prefix = "redis://";
      Method method = ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
      if (method != null && (Boolean) ReflectionUtils.invokeMethod(method, redisProperties)) {
        prefix = "rediss://";
      }

      config.useSingleServer()
          .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
          .setConnectTimeout(timeout).setDatabase(redisProperties.getDatabase())
          .setPassword(redisProperties.getPassword());
    }
    /*
     * if (redissonAutoConfigurationCustomizers != null) { for (RedissonAutoConfigurationCustomizer
     * customizer : redissonAutoConfigurationCustomizers) { customizer.customize(config); } }
     */
    return Redisson.create(config);
  }

  private String[] convert(List<String> nodesObject) {
    List<String> nodes = new ArrayList<String>(nodesObject.size());
    for (String node : nodesObject) {
      if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
        nodes.add("redis://" + node);
      } else {
        nodes.add(node);
      }
    }
    return nodes.toArray(new String[nodes.size()]);
  }

  private InputStream getConfigStream() throws IOException {
    Resource resource = ctx.getResource(redissonProperties.getConfig());
    InputStream is = resource.getInputStream();
    return is;
  }


}
