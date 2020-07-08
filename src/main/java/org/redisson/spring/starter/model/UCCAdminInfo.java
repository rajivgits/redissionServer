package org.redisson.spring.starter.model;

import lombok.Data;

@Data
public class UCCAdminInfo {

  private String emailAddress;

  private String firstName;

  private String lastName;

  private String loginType;

  private Long tenantId;

  private String userType;

}
