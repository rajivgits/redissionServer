package org.redisson.spring.starter.controller;

import org.redisson.spring.starter.model.OrderEntity;
import org.redisson.spring.starter.service.RedissionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/redisdata")
public class RedissionController {

  private final Logger log = LoggerFactory.getLogger(getClass());
  @Autowired
  public RedissionServiceImpl redissionService;

  @RequestMapping("/check")
  public Mono<String> check() {
    return Mono.just("hello");
  }

  @PostMapping("/persist")
  public Mono<Boolean> add(@RequestBody OrderEntity orderEntity) {
    return redissionService.add(orderEntity.getOrderId().toString(), orderEntity);
  }

  @GetMapping("/order/{orderId}")
  public OrderEntity getById(@PathVariable final String orderId) {
    return redissionService.getById(orderId);
  }

  @PostMapping("/order/{orderId}/update")
  public Mono<Boolean> update(@PathVariable final String orderId, @RequestBody OrderEntity entity) {
    return redissionService.updateOrder(orderId, entity);

  }

  @DeleteMapping("/delete/{orderId}")
  public Boolean delete(@PathVariable final String orderId) {
    return redissionService.delete(orderId);
  }

  @GetMapping("/getAll")
  public Flux<OrderEntity> all() {
    return redissionService.getall();
  }

}
