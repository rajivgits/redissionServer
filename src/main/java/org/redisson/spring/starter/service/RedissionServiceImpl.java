package org.redisson.spring.starter.service;

import org.redisson.spring.starter.model.OrderEntity;
import org.redisson.spring.starter.repository.RedissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RedissionServiceImpl implements RedissionService {

  private final Logger log = LoggerFactory.getLogger(getClass());
  @Autowired
  public RedissionRepository repos;


  @Override
  public Mono<Boolean> add(String orderId, OrderEntity orderEntity) {
    return repos.add(orderId, orderEntity);
  }

  @Override
  public OrderEntity getById(String orderId) {
    OrderEntity object = repos.getById(orderId).block();
    return object;
  }

  @Override
  public Mono<Boolean> updateOrder(String orderId, OrderEntity orderEntity) {
    return repos.update(orderId, orderEntity);
  }

  @Override
  public Boolean delete(String orderId) {
    return repos.delete(orderId).block();
  }

  public Flux<OrderEntity> getall() {

    return repos.getall();
  }

}
