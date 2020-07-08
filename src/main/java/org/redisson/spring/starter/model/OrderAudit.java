package org.redisson.spring.starter.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class OrderAudit {

  // Order.EDITOR_ID
  private Long lastEditorId;

  // Order.TIMEPLACED
  private Timestamp timePlaced;

  // Order.LASTUPTimestamp
  private Timestamp lastUpTimestampd;

  private Timestamp orderCreatedOn;

  private String createdBy;

  private String lastAccessedBy;

  private String lastUpTimestampdBy;

  private Timestamp cancelledOn;

  private Timestamp submittedOn;

  // XOrders.APPROVEDTIME
  private Timestamp approvedTime;

  // XOrders.LOCKEDTIME
  private Timestamp lockedTime;

  private Long lockedBy;

}
