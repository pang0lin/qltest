package com.js.oa.info.infomanager.bean;

import com.js.oa.info.infomanager.po.InformationAccessoryPO;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.util.hibernate.HibernateBase;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class InformationAccessoryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getAccessory(String informationId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.accessoryId,aaa.accessoryName,aaa.accessorySaveName,aaa.accessoryType,aaa.accessoryIsImage from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa join aaa.information bbb where bbb.informationId = " + informationId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public void updateAccessory(String informationId, InformationAccessoryPO informationAccessoryPO) throws Exception {
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(InformationPO.class, new Long(informationId));
      informationAccessoryPO.setInformation(informationPO);
      this.session.save(informationAccessoryPO);
      this.transaction.commit();
    } catch (Exception e) {
      this.transaction.rollback();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public String getAccessoryFile(String accessoryId) throws Exception {
    String saveName = "";
    begin();
    try {
      Query query = this.session.createQuery("select aaa.accessorySaveName from com.js.oa.info.infomanager.po.InformationAccessoryPO aaa where aaa.accessoryId = " + accessoryId);
      List<String> list = query.list();
      saveName = list.get(0);
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return saveName;
  }
  
  public List getHistAccessory(String historyId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(" select aaa.accessoryId,aaa.accessoryName,aaa.accessorySaveName, aaa.accessoryType,aaa.accessoryIsImage from  com.js.oa.info.infomanager.po.InforHistoryAccessoryPO aaa  join aaa.informationHistory bbb where bbb.historyId = " + 

          
          historyId);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getOneInfoPic(String informationId) throws Exception {
    begin();
    String saveName = "";
    try {
      Query query = this.session.createQuery(" select aaa.accessorySaveName from  com.js.oa.info.infomanager.po.InformationAccessoryPO aaa  join aaa.information bbb where bbb.informationId = " + 
          
          informationId + " and aaa.accessoryIsImage=1");
      List<E> list = query.list();
      if (list != null && list.size() > 0)
        saveName = list.get(0).toString(); 
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return saveName;
  }
  
  public void updateAccessory(String informationId, String[] infoPicName, String[] infoPicSaveName, String[] infoAppendName, String[] infoAppendSaveName) throws Exception {
    begin();
    try {
      InformationPO informationPO = (InformationPO)this.session.load(InformationPO.class, new Long(informationId));
      Set accessory = informationPO.getInformationAccessory();
      informationPO.setInformationAccessory(null);
      Iterator<InformationAccessoryPO> iter = accessory.iterator();
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      if (infoPicName != null)
        for (int i = 0; i < infoPicName.length; i++) {
          InformationAccessoryPO informationAccessoryPO = new InformationAccessoryPO();
          informationAccessoryPO.setAccessoryName(infoPicName[i]);
          informationAccessoryPO.setAccessorySaveName(infoPicSaveName[i]);
          informationAccessoryPO.setAccessoryIsImage(1);
          if (infoPicSaveName[i].indexOf(".") > 0) {
            informationAccessoryPO.setAccessoryType(infoPicSaveName[i].substring(infoPicSaveName[i].indexOf(".") + 1, infoPicSaveName[i].length()));
          } else {
            informationAccessoryPO.setAccessoryType("");
          } 
          informationAccessoryPO.setInformation(informationPO);
          this.session.save(informationAccessoryPO);
        }  
      if (infoAppendName != null)
        for (int i = 0; i < infoAppendName.length; i++) {
          InformationAccessoryPO informationAccessoryPO = new InformationAccessoryPO();
          informationAccessoryPO.setAccessoryName(infoAppendName[i]);
          informationAccessoryPO.setAccessorySaveName(infoAppendSaveName[i]);
          informationAccessoryPO.setAccessoryIsImage(0);
          if (infoAppendSaveName[i].indexOf(".") > 0) {
            informationAccessoryPO.setAccessoryType(infoAppendSaveName[i].substring(infoAppendSaveName[i].indexOf(".") + 1, infoAppendSaveName[i].length()));
          } else {
            informationAccessoryPO.setAccessoryType("");
          } 
          informationAccessoryPO.setInformation(informationPO);
          this.session.save(informationAccessoryPO);
        }  
      this.transaction.commit();
    } catch (Exception e) {
      this.transaction.rollback();
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
}
