package org.redisson.spring.starter.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ChangeMobileNumber {

  private List<PortIn> portIn = new ArrayList<>();

  private PreferredAreaCode preferredAreaCode;

}
