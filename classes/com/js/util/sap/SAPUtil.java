package com.js.util.sap;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SAPUtil {
  public SAPResult execute(String functionName, String resultType, List<String> queryTableName, Map<String, String> parameters) {
    return execute("", functionName, resultType, queryTableName, parameters);
  }
  
  public SAPResult execute(String poolName, String functionName, String resultType, List<String> queryTableName, Map<String, String> parameters) {
    SAPResult sapResult = new SAPResult();
    Map<String, JCoTable> queryTable = new HashMap<String, JCoTable>();
    JCoFunction function = null;
    JCoDestination destination = SAPConn.connect(poolName);
    try {
      function = destination.getRepository().getFunction(functionName);
      if (parameters != null) {
        Iterator<Map.Entry> it = parameters.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry entry = it.next();
          String key = entry.getKey().toString();
          String value = parameters.get(key);
          function.getImportParameterList().setValue(key, value);
        } 
      } 
      function.execute(destination);
      if (resultType.indexOf("result_log") >= 0)
        sapResult.setResultLog(function.getExportParameterList().getString("RESULT_LOG")); 
      if (resultType.indexOf("xml") >= 0)
        sapResult.setResultXml(function.getExportParameterList().toXML()); 
      if (resultType.indexOf("table") >= 0) {
        for (int i = 0; i < queryTableName.size(); i++)
          queryTable.put(queryTableName.get(i), function.getTableParameterList().getTable(queryTableName.get(i))); 
        sapResult.setResultTable(queryTable);
      } 
    } catch (JCoException e) {
      e.printStackTrace();
    } 
    return sapResult;
  }
}
