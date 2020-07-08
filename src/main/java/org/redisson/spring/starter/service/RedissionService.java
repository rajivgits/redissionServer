package org.redisson.spring.starter.service;

import org.redisson.spring.starter.model.OrderEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RedissionService {
  public Mono<Boolean> add(String orderId, OrderEntity orderEntity);

  public OrderEntity getById(String id);

  public Mono<Boolean> updateOrder(String orderId, OrderEntity entity);

  public Boolean delete(String orderId);

  public Flux<OrderEntity> getall();
}
