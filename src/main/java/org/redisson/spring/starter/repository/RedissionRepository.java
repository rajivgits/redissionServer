package org.redisson.spring.starter.repository;

import javax.annotation.PostConstruct;
import org.redisson.spring.starter.model.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RedissionRepository {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  public ReactiveRedisTemplate<String, OrderEntity> operations;
  // public ReactiveRedisOperations<String, String> operations;

  public ReactiveValueOperations<String, OrderEntity> reactiveValueOps;

  @PostConstruct
  public void initService() {
    reactiveValueOps = operations.opsForValue();
  }


  public Mono<Boolean> add(String orderId, OrderEntity orderEntity) {
    return reactiveValueOps.set(orderId, orderEntity);
  }

  public Mono<OrderEntity> getById(String orderId) {
    Mono<OrderEntity> object = reactiveValueOps.get(orderId).single();
    return object;

  }

  public Mono<Boolean> update(String orderId, OrderEntity orderEntity) {
    return reactiveValueOps.get(orderId)
        .flatMap(entity -> reactiveValueOps.set(orderId, orderEntity));
  }


  public Mono<Boolean> delete(String orderId) {
    return reactiveValueOps.delete(orderId);
  }


  public Flux<OrderEntity> getall() {
    return operations.keys("*").flatMap(reactiveValueOps::get);
  }



}
