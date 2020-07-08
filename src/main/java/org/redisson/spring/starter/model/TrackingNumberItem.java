package org.redisson.spring.starter.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class TrackingNumberItem {

  // XPackageTracker.IMEI
  private String imei;

  // XPackageTracker.SIM
  private String simNumber;

  // XPackageTracker.MSISDN
  private String msisdn;

  // XPackageTracker.SAP_NUM
  private Long sapId;

  // XPackageTracker.
  private Timestamp shipDate;

  // XPackageTracker.HANDSET
  // to be made as an Enum.
  private String handset;

  // XPackageTracker.TRACKING_NUMBER
  private String trackingNumber;

  // XPackageTracker.
  private String trackingCarrier;

  // XPackageTracker.MFNAME
  private String manufactureName;

}
