package org.redisson.spring.starter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Sequence {
  // @Id
  private String id;
  private Long sequence;
}
