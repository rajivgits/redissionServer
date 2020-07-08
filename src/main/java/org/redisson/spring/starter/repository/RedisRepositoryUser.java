package org.redisson.spring.starter.repository;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.model.User;
import org.springframework.data.redis.core.RedisTemplate;


// @Repository
public class RedisRepositoryUser {

  // @Autowired
  private RedissonClient redisson;

  // @Autowired
  private RedisTemplate<String, String> template;

  public User getUser(String userId) {
    RMap<String, User> map = redisson.getMap("myMap");
    boolean contains = map.containsKey(userId);
    if (contains) {
      User user = map.get(userId);
      return user;
    }
    return null;
  }

}
