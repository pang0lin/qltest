package com.js.oa.jsflow.bean;

import com.js.ldap.supervise.CreateProcessForSupervise;

public class FlowEndFollowUp {
  public void flowEnd(String recordId) {
    CreateProcessForSupervise.updateSuperviseDualData(recordId);
  }
}
