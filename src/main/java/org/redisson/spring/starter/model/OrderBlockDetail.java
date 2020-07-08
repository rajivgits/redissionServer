package org.redisson.spring.starter.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class OrderBlockDetail {

  // ORDERBLK.ORDERBLK_ID
  // Auto-Generated Id
  private Long orderBlockId;

  // ORDERBLK.BLKCOMMENT
  private String blockComment;

  // ORDERBLK.RESOLVED
  private boolean resolved;

  // ORDERBLK.TIMEBLOCKED
  private Timestamp timeBlocked;

  private BlockReasonEntity blockReason;
}
