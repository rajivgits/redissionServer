package org.redisson.spring.starter.model;

import java.sql.Date;
import lombok.Data;

@Data
public class OrderComment {

  // ORCOMMENT.LASTUPDATE
  private Date date;

  // ORCOMMENT.COMMENTS
  private String notes;

  // ORCOMMENT.SERVICEREP_ID
  private String user;

}
