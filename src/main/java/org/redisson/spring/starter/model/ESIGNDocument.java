package org.redisson.spring.starter.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class ESIGNDocument {

  // XOrdersAttrs.ESIGN_DOC_ID
  private String documentId;

  // XOrdersAttrs.ESIG_MODIFIED_ON
  private Timestamp modifiedOn;

  // XOrdersAttrs.ESIGN_DOC_URL
  private String documentURL;

  // XOrdersAttrs.ESIGN_STATUS
  private boolean status;

  // XOrdersAttrs.ESIG_BY
  private String eSignatureBy;

  // XOrdersAttrs.docusign_emailAddr
  private String emailId;
}
