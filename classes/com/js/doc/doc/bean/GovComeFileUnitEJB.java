package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovComeFileUnitPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface GovComeFileUnitEJB extends EJBObject {
  String add(GovComeFileUnitPO paramGovComeFileUnitPO) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  GovComeFileUnitPO load(String paramString) throws Exception, RemoteException;
  
  String update(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getComeFileUnit(String paramString) throws Exception, RemoteException;
}
