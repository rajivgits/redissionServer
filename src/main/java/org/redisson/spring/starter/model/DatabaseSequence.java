package org.redisson.spring.starter.model;

import lombok.Data;

@Data
public class DatabaseSequence {

  // @Id
  private String id;

  private Long sequence;
}
