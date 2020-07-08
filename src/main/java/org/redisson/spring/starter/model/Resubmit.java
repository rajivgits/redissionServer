package org.redisson.spring.starter.model;

import lombok.Data;

@Data
public class Resubmit {

  // XOrdersAttrs.RESUBMIT_ORDER_ID_FLAG
  private boolean flag;

  // XOrdersAttrs.resubmitOrderId
  private Long resubmitOrderId;

  // XOrdersAttrs.resubmitParentOrderId
  private Long parentOrderId;
}
