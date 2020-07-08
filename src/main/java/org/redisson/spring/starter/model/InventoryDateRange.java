package org.redisson.spring.starter.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class InventoryDateRange {

  private Timestamp esdFrom;

  private Timestamp esdTo;

  private Long feedQuantity;
}
