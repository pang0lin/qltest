package com.js.oa.userdb.service;

import com.js.oa.userdb.bean.CustomDBEJBBean;
import com.js.oa.userdb.bean.CustomDBEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class CustomDatabaseBD {
  private static Logger logger = Logger.getLogger(CustomDatabaseBD.class
      .getName());
  
  public String[][] getModelInfo(String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getModelInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getModelInfo information :" + e.getMessage());
    } 
    return list;
  }
  
  public int addModel(String code, String name, String remark, String user, String domainid) {
    int success = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(code, String.class);
      pg.put(name, String.class);
      pg.put(remark, String.class);
      pg.put(user, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("addModel", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to addModel information :" + e.getMessage());
    } 
    return success;
  }
  
  public int batchDelete(String[] modelID) {
    int success = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(modelID, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("batchDelete", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDelete information :" + e.getMessage());
    } 
    return success;
  }
  
  public int batchDeleteModel(String[] modelID) {
    int success = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(modelID, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("batchDeleteModel", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDeleteModel information :" + e.getMessage());
    } 
    return success;
  }
  
  public int delSingleModel(String modelid) {
    int success = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(modelid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("delSingleModel", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delSingleModel information :" + e.getMessage());
    } 
    return success;
  }
  
  public void updateModel(String modelid, String modelname, String modelcode, String modeldis) {
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(modelid, String.class);
      pg.put(modelname, String.class);
      pg.put(modelcode, String.class);
      pg.put(modeldis, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("updateModel", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateModel information :" + e.getMessage());
    } 
  }
  
  public String[][] getTableInfo(String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getTableInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getTableInfo information :" + e.getMessage());
    } 
    return list;
  }
  
  public String[][] getTableInfo(String model, String tabledesname, String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(model, String.class);
      pg.put(tabledesname, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getTableInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getTableInfo(String tableid) information :" + e.getMessage());
    } 
    return list;
  }
  
  public String[][] getModelIDName(String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getModelIDName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getTableInfo information :" + e.getMessage());
    } 
    return list;
  }
  
  public int addTable(String tablecode, String tabledesname, String tablename, String tablemodel, String tablefilepath, String tableowner, String domainid, String empId, String orgId) {
    int success = 1;
    try {
      ParameterGenerator pg = new ParameterGenerator(9);
      pg.put(tablecode, String.class);
      pg.put(tabledesname, String.class);
      pg.put(tablename, String.class);
      pg.put(tablemodel, String.class);
      pg.put(tablefilepath, String.class);
      pg.put(tableowner, String.class);
      pg.put(domainid, String.class);
      pg.put(empId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("addTable", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to addTable information :" + e.getMessage());
      success = 0;
    } 
    return success;
  }
  
  public String getTableName(String domainId) {
    String name = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      name = (String)ejbProxy.invoke("getTableName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getTableName information :" + e.getMessage());
    } 
    return name;
  }
  
  public void batchDeleteTable(String[] tablename) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tablename, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("batchDeleteTable", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDeleteTable information :" + e.getMessage());
    } 
  }
  
  public void delSignleTable(String table_name) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(table_name, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("delSignleTable", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delSignleTable information :" + e.getMessage());
    } 
  }
  
  public int updateTable(String table_name, String table_code, String table_desname, String table_model, String table_filepath, String domainId) {
    int success = 1;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(table_name, String.class);
      pg.put(table_code, String.class);
      pg.put(table_desname, String.class);
      pg.put(table_model, String.class);
      pg.put(table_filepath, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("updateTable", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      logger.error("error to updateTable information :" + e.getMessage());
    } 
    return success;
  }
  
  public int updateSimpleTable(String tableId, String tableName, String domainId) {
    int success = 1;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(tableName, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("updateSimpleTable", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      logger.error("error to updateSimpleTable information :" + e.getMessage());
    } 
    return success;
  }
  
  public String[][] getFieldInfo(String tableid, String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableid, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getFieldInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getFieldInfo(String tableid) information :" + e.getMessage());
    } 
    return list;
  }
  
  public void batchDeleteField(String tablename, String[] fieldname) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tablename, String.class);
      pg.put(fieldname, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("batchDeleteField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDeleteField information :" + e.getMessage());
    } 
  }
  
  public void delSignleField(String tablename, String fieldname) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tablename, String.class);
      pg.put(fieldname, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("delSignleField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delSignleField information :" + e.getMessage());
    } 
  }
  
  public int updateField(String fieldname, String fieldnull, String fielddefault, String fielddes, String fieldindex, String fieldupdata, String fielddesname, String fieldcode, String fieldlen, String tableid, String fieldonly, String queryField, String totField) {
    int success = 1;
    try {
      ParameterGenerator pg = new ParameterGenerator(13);
      pg.put(fieldname, String.class);
      pg.put(fieldnull, String.class);
      pg.put(fielddefault, String.class);
      pg.put(fielddes, String.class);
      pg.put(fieldindex, String.class);
      pg.put(fieldupdata, String.class);
      pg.put(fielddesname, String.class);
      pg.put(fieldcode, String.class);
      pg.put(fieldlen, String.class);
      pg.put(tableid, String.class);
      pg.put(fieldonly, String.class);
      pg.put(queryField, String.class);
      pg.put(totField, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("updateField", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      logger.error("error to updateField information :" + e.getMessage());
    } 
    return success;
  }
  
  public int updateSimpleField(String fieldId, String tableId, String tableSysName, String fieldType, String fieldname, String fieldnull, String fielddefault, String fieldDiplayName, String fieldlen, String queryField, String totField, String fieldBoxSearch, String fieldChangeMethod, String fieldFetchSql, String fieldToForm, String fieldScript, String fieldFilterList, String fieldFilterSearch, String fieldInterfaceName, String fieldInterfaceMethodName, String fieldInterfaceMethodPara) {
    return updateSimpleField(fieldId, tableId, tableSysName, fieldType, fieldname, fieldnull, 
        fielddefault, fieldDiplayName, fieldlen, queryField, totField, fieldBoxSearch, 
        fieldChangeMethod, fieldFetchSql, fieldToForm, fieldScript, fieldFilterList, fieldFilterSearch, 
        "0", "0", "0", "", "", fieldInterfaceName, fieldInterfaceMethodName, fieldInterfaceMethodPara, "", "");
  }
  
  public int updateSimpleField(String fieldId, String tableId, String tableSysName, String fieldType, String fieldname, String fieldnull, String fielddefault, String fieldDiplayName, String fieldlen, String queryField, String totField, String fieldBoxSearch, String fieldChangeMethod, String fieldFetchSql, String fieldToForm, String fieldScript, String fieldFilterList, String fieldFilterSearch, String inputSearchType, String startRange, String handleRange, String startText, String handleText, String fieldInterfaceName, String fieldInterfaceMethodName, String fieldInterfaceMethodPara, String fieldInterfacetype, String defaultShowTimeValue) {
    int success = 1;
    try {
      ParameterGenerator pg = new ParameterGenerator(28);
      pg.put(fieldId, String.class);
      pg.put(tableId, String.class);
      pg.put(tableSysName, String.class);
      pg.put(fieldType, String.class);
      pg.put(fieldname, String.class);
      pg.put(fieldnull, String.class);
      pg.put(fielddefault, String.class);
      pg.put(fieldDiplayName, String.class);
      pg.put(fieldlen, String.class);
      pg.put(queryField, String.class);
      pg.put(totField, String.class);
      pg.put(fieldBoxSearch, String.class);
      pg.put(fieldChangeMethod, String.class);
      pg.put(fieldFetchSql, String.class);
      pg.put(fieldToForm, String.class);
      pg.put(fieldScript, String.class);
      pg.put(fieldFilterList, String.class);
      pg.put(fieldFilterSearch, String.class);
      pg.put(inputSearchType, String.class);
      pg.put(startRange, String.class);
      pg.put(handleRange, String.class);
      pg.put(startText, String.class);
      pg.put(handleText, String.class);
      pg.put(fieldInterfaceName, String.class);
      pg.put(fieldInterfaceMethodName, String.class);
      pg.put(fieldInterfaceMethodPara, String.class);
      pg.put(fieldInterfacetype, String.class);
      pg.put(defaultShowTimeValue, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("updateSimpleField", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      logger.error("error to updateSimpleField information :" + e.getMessage());
    } 
    return success;
  }
  
  public int addField(String fieldtypeid, String tableid, String tablename, String modelid, String fielddesname, String fieldcode, String fieldname, String fieldtype, String fieldlen, String fieldnull, String fielddefault, String fielddes, String fieldonly, String fieldindex, String fieldupdata, String fieldowner, String queryField, String totField, String domainid, String fieldShowTimeValue) {
    int success = 1;
    try {
      ParameterGenerator pg = new ParameterGenerator(20);
      pg.put(fieldtypeid, String.class);
      pg.put(tableid, String.class);
      pg.put(tablename, String.class);
      pg.put(modelid, String.class);
      pg.put(fielddesname, String.class);
      pg.put(fieldcode, String.class);
      pg.put(fieldname, String.class);
      pg.put(fieldtype, String.class);
      pg.put(fieldlen, String.class);
      pg.put(fieldnull, String.class);
      pg.put(fielddefault, String.class);
      pg.put(fielddes, String.class);
      pg.put(fieldonly, String.class);
      pg.put(fieldindex, String.class);
      pg.put(fieldupdata, String.class);
      pg.put(fieldowner, String.class);
      pg.put(queryField, String.class);
      pg.put(totField, String.class);
      pg.put(domainid, String.class);
      pg.put(fieldShowTimeValue, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      Object obj = ejbProxy.invoke("addField", pg.getParameters());
      success = Integer.parseInt(obj.toString());
    } catch (Exception e) {
      success = 0;
      e.printStackTrace();
      logger.error("error to addField information :" + e.getMessage());
    } 
    return success;
  }
  
  public String getFieldName(String domainId) {
    String name = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      name = (String)ejbProxy.invoke("getFieldName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getFieldName information :" + e.getMessage());
    } 
    return name;
  }
  
  public String[][] getType() {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(0);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getType", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getType information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getModelid(String tableid) {
    String modelid = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      modelid = (String)ejbProxy.invoke("getModelid", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getModelid information :" + e.getMessage());
    } finally {}
    return modelid;
  }
  
  public void updateFieldHide(String[] fieldname, String tableId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(fieldname, String[].class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("updateFieldHide", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateFieldHide information :" + e.getMessage());
    } 
  }
  
  public String[][] getAssociateInfo(String tableid, String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableid, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getAssociateInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAssociateInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void batchDelAssociate(String[] associateid) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(associateid, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("batchDelAssociate", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchDelAssociate information :" + e.getMessage());
    } 
  }
  
  public void delSignleAssociate(String associateid) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(associateid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("delSignleAssociate", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delSignleAssociate information :" + e.getMessage());
    } 
  }
  
  public String[][] getMainFieldInfo(String tableid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getMainFieldInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMainFieldInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getPryTableInfo(String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getPryTableInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getPryTableInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getPryFieldInfo(String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getPryFieldInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getPryFieldInfo information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public int addAssociate(String name, String limittable, String limitfield, String limitprytable, String limitpryfield, String limitowner, String domainid) {
    int success = -1;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(name, String.class);
      pg.put(limittable, String.class);
      pg.put(limitfield, String.class);
      pg.put(limitprytable, String.class);
      pg.put(limitpryfield, String.class);
      pg.put(limitowner, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = Integer.parseInt(ejbProxy.invoke("addAssociate", pg.getParameters()).toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to addAssociate information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public int updateAssociate(String limitid, String limitprytable, String limitpryfield, String name) {
    int success = -1;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(limitid, String.class);
      pg.put(limitprytable, String.class);
      pg.put(limitpryfield, String.class);
      pg.put(name, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = Integer.parseInt(ejbProxy.invoke("updateAssociate", pg.getParameters()).toString());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateAssociate information :" + e.getMessage());
    } 
    return success;
  }
  
  public String[][] getShow(String tableid, String domainid) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableid, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getShow", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getShow information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void setShow(String fieldlist, String fieldwidth, String fieldshow, String fieldvalue, String fieldname, String fieldseq, String tableId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(fieldlist, String.class);
      pg.put(fieldwidth, String.class);
      pg.put(fieldshow, String.class);
      pg.put(fieldvalue, String.class);
      pg.put(fieldname, String.class);
      pg.put(fieldseq, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("setShow", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to setShow information :" + e.getMessage());
    } 
  }
  
  public String[][] getCountField(String tableid, String fieldname) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableid, String.class);
      pg.put(fieldname, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getCountField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getCountField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getQueryField(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getQueryField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getQueryField(String tableId, String field) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(field, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getQueryField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Boolean addQueryField(String fieldId, String queryMode, String tableId) {
    Boolean success = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(fieldId, String.class);
      pg.put(queryMode, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = (Boolean)ejbProxy.invoke("addQueryField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to addQueryField information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public Boolean editQueryField(String fieldId, String queryMode) {
    Boolean success = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(fieldId, String.class);
      pg.put(queryMode, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = (Boolean)ejbProxy.invoke("editQueryField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to editQueryField information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public Boolean delQueryField(String fieldId) {
    Boolean success = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delQueryField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delQueryField information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public Boolean delQueryField(String[] fieldId) {
    Boolean success = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delQueryField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to delQueryField information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public String getQueryFieldHTML(String fieldId) {
    String html = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      html = ejbProxy.invoke("getQueryFieldHTML", pg.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryFieldHTML information :" + e.getMessage());
    } finally {}
    return html;
  }
  
  public String[][] getListField(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getListField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getListField(String tableId, String field) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(field, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getListField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getAllField(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getAllField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getFieldFang(String tableId, String empId, String processId) {
    String[][] list = (String[][])null;
    try {
      list = (new CustomDBEJBBean()).getFieldFang(tableId, empId, processId);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getSubFieldFang(String tableId, String empId, String processId) {
    String[][] list = (String[][])null;
    try {
      list = (new CustomDBEJBBean()).getSubFieldFang(tableId, empId, processId);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getSubListField(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getSubListField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getSubListField(String tableId, String field) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(field, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getSubListField", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getSubTableId(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getSubTableId", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getListField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String[][] getSingleTable(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getSingleTable", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getSingleTable information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Boolean batchUpdateShow(String[] fieldname, String tableId) {
    Boolean success = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(fieldname, String[].class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = (Boolean)ejbProxy.invoke("batchUpdateShow", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to batchUpdateShow information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public String getSystemMark() {
    String type = "0";
    String name = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(0);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      name = (String)ejbProxy.invoke("getSystemMark", pg.getParameters());
      if (name != null && !name.equals("")) {
        int d = name.indexOf("_");
        if (d != -1) {
          type = "1";
          name = name.substring(0, d);
        } 
      } 
      if (type.equals("0"))
        name = "jst"; 
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getSystemMark information :" + e.getMessage());
    } finally {}
    return name;
  }
  
  public String getMarkCode(String domainId) {
    String name = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      name = (String)ejbProxy.invoke("getMarkCode", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMarkCode information :" + e.getMessage());
    } finally {}
    return name;
  }
  
  public String getFiledCode(String domainId) {
    String name = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      name = (String)ejbProxy.invoke("getFiledCode", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getMarkCode information :" + e.getMessage());
    } finally {}
    return name;
  }
  
  public String checkSame(String fielddesname, String fieldname, String tableid) {
    String name = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(fielddesname, String.class);
      pg.put(fieldname, String.class);
      pg.put(tableid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      name = (String)ejbProxy.invoke("checkSame", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to checkSame information :" + e.getMessage());
    } finally {}
    return name;
  }
  
  public List getAssociateTable(String tableId, String domainId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (List)ejbProxy.invoke("getAssociateTable", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAssociateTable information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Boolean addAssociateTable(String fieldId, String queryMode, String tableId, String domainId) {
    Boolean success = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(fieldId, String.class);
      pg.put(queryMode, String.class);
      pg.put(tableId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", 
          "CustomDBEJBLocal", CustomDBEJBHome.class);
      success = (Boolean)ejbProxy.invoke("addAssociateTable", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to addAssociateTable information :" + e.getMessage());
    } finally {}
    return success;
  }
  
  public String getTableTotFieldValue(String tableId, String condition) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(condition, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getTableTotFieldValue", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getTableTotFieldValue information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getTableTotField(String tableId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getTableTotField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getTotField(String tableId) {
    String[][] list = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (String[][])ejbProxy.invoke("getTotField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getTotFieldInfo(String tableId) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      list = (List)ejbProxy.invoke("getTotFieldInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Integer saveTotField(String tableId, String fieldName) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(fieldName, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("saveTotField", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String pasteTable(String tables, String domainId, String userId, String orgId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tables, String.class);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      ejbProxy.invoke("pasteTable", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getModuleName(String[] moduleId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getModuelName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getTableName(String[] id) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getTableName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getTableDesName(String talbeName) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(talbeName, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getTableDesName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getAllFieldInfo(String tableId, String domainId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String[][])ejbProxy.invoke("getAllFieldInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getSimpleTableInfo(String tableId, String domainId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String[][])ejbProxy.invoke("getSimpleTableInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getQueryField information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getShouldDelId(String tableId, String[] field) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(field, String[].class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getShouldDelId", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getShouldDelId information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String checkTableName(String tableId, String tableName, String domainId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(tableName, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("checkTableName", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to checkTableName information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String checkBaseName(String tableId, String tableName, String domainId) {
    CustomDBEJBBean cejb = new CustomDBEJBBean();
    return cejb.checkBaseName(tableId, tableName, domainId);
  }
  
  public String getDefaultModelId(String domainid) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String)ejbProxy.invoke("getDefaultModelId", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to checkTableName information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getBoxSearchField(String tableId, String domainId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String[][])ejbProxy.invoke("getBoxSearchField", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to checkTableName information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getOnchangeMethodInfo(String tableId, String domainId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (List)ejbProxy.invoke("getOnchangeMethodInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to checkTableName information :" + e.getMessage());
    } 
    return result;
  }
  
  public String[] getFieldExtProperty(String fieldId) {
    String[] result = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String[])ejbProxy.invoke("getFieldExtProperty", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getFieldExtProperty information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getFieldSimpleInfo(String tableId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomDBEJB", "CustomDBEJBLocal", CustomDBEJBHome.class);
      result = (String[][])ejbProxy.invoke("getFieldSimpleInfo", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getFieldExtProperty information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public void updateTableEmp(String tableId, String empId) {
    try {
      (new CustomDBEJBBean()).updateTableEmp(tableId, empId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getTableEmp(String tableId) {
    try {
      return (new CustomDBEJBBean()).getTableEmp(tableId);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public void setTotalValue(String[] displayName, String tableId, String[] totalValue, String[] fieldIsTotal) {
    (new CustomDBEJBBean()).setTotalValue(displayName, tableId, totalValue, fieldIsTotal);
  }
  
  public String getTotalValue(String fieldId) {
    return (new CustomDBEJBBean()).getTotalValue(fieldId);
  }
  
  public String getTemplate(String defaultString, String fileType) {
    return (new CustomDBEJBBean()).getTemplate(defaultString, fileType);
  }
  
  public void setName(String flag, String tableId) {
    (new CustomDBEJBBean()).setName(flag, tableId);
  }
  
  public String setFieldName(String fieldId, String tableId, String tableName, String domainId) {
    return (new CustomDBEJBBean()).setFieldName(fieldId, tableId, tableName, domainId);
  }
}
