package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovComeFileUnitPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface GovComeFileUnitEJBLocal extends EJBLocalObject {
  String add(GovComeFileUnitPO paramGovComeFileUnitPO) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  GovComeFileUnitPO load(String paramString) throws Exception;
  
  String update(String paramString1, String paramString2) throws Exception;
  
  List getComeFileUnit(String paramString) throws Exception;
}
