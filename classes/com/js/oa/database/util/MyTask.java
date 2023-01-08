package com.js.oa.database.util;

public class MyTask implements CustomTask {
  public void execute() throws Exception {
    String type = XmlUtil.selectDatabase();
    if ("oracle".equals(type))
      BackupAndRecoverForOracle.exp(); 
    if ("mysql".equals(type))
      BackupAndRecover.backup(); 
  }
}
