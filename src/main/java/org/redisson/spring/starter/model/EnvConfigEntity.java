package org.redisson.spring.starter.model;

import lombok.Data;

@Data
public class EnvConfigEntity {

  // @Id
  private String id;

  // Autogenerated
  private Long configId;

  // XSTORECONF.LANGUAGE_ID
  private Long languageId;

  // XSTORECONF.NAME
  // @NotNull
  private String name;

  // XSTORECONF.VALUE
  private String value;

  // XSTORECONF.ENCRYPT
  private boolean encrypt;

}
