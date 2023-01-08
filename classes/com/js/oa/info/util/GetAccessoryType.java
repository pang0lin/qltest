package com.js.oa.info.util;

import com.js.oa.info.infomanager.service.InformationBD;

public class GetAccessoryType {
  public String getAccessoryType(String informationId) {
    return (new InformationBD()).getAccessoryType(informationId);
  }
}
