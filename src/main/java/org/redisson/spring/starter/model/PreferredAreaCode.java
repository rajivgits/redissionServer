package org.redisson.spring.starter.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PreferredAreaCode {

  private List<AreaCode> areaCodes = new ArrayList<>();
}
