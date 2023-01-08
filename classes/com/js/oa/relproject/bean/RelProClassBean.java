package com.js.oa.relproject.bean;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.relproject.po.RelProClassPO;
import com.js.util.hibernate.HibernateBase;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

public class RelProClassBean extends HibernateBase {
  public List<Object> searchAll(Long id) {
    List<Object[]> list = null;
    try {
      begin();
      list = this.session.createQuery("select po.id,po.name,po.level,po.classSort from com.js.oa.relproject.po.RelProClassPO po  where po.id <>" + id + " order by po.classSort ").list();
      Object[] objects = (Object[])null;
      String classOrt = "";
      String classCurrentId = id.toString();
      for (int i = list.size() - 1; i >= 0; i--) {
        objects = list.get(i);
        classOrt = String.valueOf(objects[3]);
        classOrt = classOrt.substring(0, classOrt.length() - 1);
        String[] classOrts = classOrt.split("\\$");
        for (int j = 0; j < classOrts.length; j++) {
          String classFindId = classOrts[j];
          if (!classFindId.startsWith("_"))
            if (classFindId.equals(classCurrentId)) {
              list.remove(i);
              break;
            }  
        } 
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
    return (List)list;
  }
  
  private String countSortCode(String parentId, String currentSortCode, String insertSite) throws Exception {
    int sortCode = 500000;
    List<E> list = null;
    Query query = null;
    if ("-1".equals(currentSortCode)) {
      sortCode = 500000;
    } else if ("0".equals(insertSite)) {
      query = this.session.createQuery("SELECT MAX(po.classSortCode) FROM com.js.oa.relproject.po.RelProClassPO po WHERE po.parentId=" + 
          parentId + 
          " AND po.classSortCode<" + 
          currentSortCode);
      list = query.list();
      if (list == null || (
        list.size() == 1 && (
        "0".equals(list.get(0)) || list.get(0) == null))) {
        sortCode = Integer.parseInt(currentSortCode) - 10000;
      } else {
        sortCode = (Integer.valueOf(list.get(0).toString()).intValue() + Integer.valueOf(currentSortCode).intValue()) / 2;
      } 
    } else {
      query = this.session.createQuery("SELECT MIN(po.classSortCode) FROM com.js.oa.relproject.po.RelProClassPO po WHERE po.parentId=" + 
          parentId + 
          " AND po.classSortCode>" + 
          currentSortCode);
      list = query.list();
      if (list == null || (list.size() == 1 && list.get(0) == null)) {
        sortCode = Integer.parseInt(currentSortCode) + 10000;
      } else {
        sortCode = (((Integer)list.get(0)).intValue() + 
          Integer.parseInt(currentSortCode)) / 2;
      } 
    } 
    return sortCode;
  }
  
  public void save(RelProClassPO relProClassPO, String parentId, String sortCode, String insertSite) throws Exception {
    try {
      begin();
      if ("-1".equals(parentId)) {
        String sortCode1 = countSortCode(parentId, sortCode, insertSite);
        relProClassPO.setClassSortCode(Integer.valueOf(sortCode1));
        relProClassPO.setLevel((short)1);
        relProClassPO.setParentId(-1L);
        Long id = (Long)this.session.save(relProClassPO);
        String classSort = "_" + sortCode1 + "$" + id.toString() + "$";
        relProClassPO.setClassSort(classSort);
        this.session.flush();
      } else {
        RelProClassPO parentClass = (RelProClassPO)this.session.get(RelProClassPO.class, Long.valueOf(parentId));
        String sortCode1 = countSortCode(parentId, sortCode, insertSite);
        relProClassPO.setLevel((short)(parentClass.getLevel() + 1));
        relProClassPO.setClassSortCode(Integer.valueOf(sortCode1));
        relProClassPO.setParentId(parentClass.getId());
        Long id = (Long)this.session.save(relProClassPO);
        String classSort = String.valueOf(parentClass.getClassSort()) + "_" + sortCode1 + "$" + id.toString() + "$";
        relProClassPO.setClassSort(classSort);
        this.session.flush();
      } 
      saveForum(relProClassPO);
      saveInfoChannel(relProClassPO);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
  }
  
  public void saveForum(RelProClassPO relProClassPO) {
    try {
      System.out.println("saveForum....start");
      ForumClassPO classPO = new ForumClassPO();
      classPO.setClassName(relProClassPO.getName());
      classPO.setClassLevel(relProClassPO.getLevel());
      classPO.setClassHasJunior((byte)1);
      classPO.setClassOwnerName(relProClassPO.getCreateEmpName());
      classPO.setClassOwnerId(0L);
      classPO.setClassOwnerIds("*" + relProClassPO.getCreateEmp() + "*");
      classPO.setClassSortCode(relProClassPO.getClassSortCode().intValue());
      classPO.setClassDate(new Date());
      classPO.setCreatedOrg(relProClassPO.getCreateOrg());
      classPO.setCreatedEmp(relProClassPO.getCreateEmp());
      classPO.setCreatedEmpName(relProClassPO.getCreateEmpName());
      classPO.setDomainId(Long.valueOf(0L));
      classPO.setFullDay(1);
      classPO.setBanPrint("0");
      classPO.setRelProjectId(Long.valueOf(0L));
      classPO.setProClassId(Long.valueOf(relProClassPO.getId()));
      Long id = (Long)this.session.save(classPO);
      if (String.valueOf(-1).equals(String.valueOf(relProClassPO.getParentId()))) {
        classPO.setClassParent(0L);
        classPO.setClassParentName(relProClassPO.getName());
        classPO.setClassSort("_" + relProClassPO.getClassSortCode() + "$" + id + "$");
      } else {
        List<Object[]> list = this.session.createQuery("select po.id,po.className,po.classSort from com.js.oa.bbs.po.ForumClassPO po  where po.proClassId =" + relProClassPO.getParentId()).list();
        Object[] obj = list.get(0);
        classPO.setClassParent(Long.valueOf(obj[0].toString()).longValue());
        classPO.setClassParentName(obj[1].toString());
        classPO.setClassSort(String.valueOf(obj[2].toString()) + "_" + relProClassPO.getClassSortCode() + "$" + id + "$");
      } 
      this.session.flush();
      System.out.println("saveForum....end");
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public void saveInfoChannel(RelProClassPO relProClassPO) {
    try {
      InformationChannelPO channelPO = new InformationChannelPO();
      channelPO.setChannelType(2);
      channelPO.setChannelLevel(relProClassPO.getLevel());
      channelPO.setChannelName(relProClassPO.getName());
      channelPO.setChannelSort(relProClassPO.getClassSortCode().intValue());
      channelPO.setChannelNeedCheckup(0);
      channelPO.setChannelShowType(0);
      channelPO.setChannelIssuer("");
      channelPO.setChannelIssuerName("");
      channelPO.setCreatedOrg(Long.valueOf(relProClassPO.getCreateOrg()));
      channelPO.setCreatedEmp(Long.valueOf(relProClassPO.getCreateEmp()));
      channelPO.setCreatedEmpName(relProClassPO.getCreateEmpName());
      channelPO.setOnDesktop(0);
      channelPO.setIsRollOnDesktop(0);
      channelPO.setChannelPosition(0);
      channelPO.setPositionUpDown(1);
      channelPO.setOnDepaDesk(0);
      channelPO.setUserDefine("0");
      channelPO.setInfoNum(Integer.valueOf(10));
      channelPO.setDesktopType(Integer.valueOf(0));
      channelPO.setIncludeChild(1);
      channelPO.setDomainId(Long.valueOf("0"));
      channelPO.setChannelManagerName(String.valueOf(relProClassPO.getCreateEmpName()) + ",");
      channelPO.setChannelManager("$" + relProClassPO.getCreateEmp() + "$");
      channelPO.setChannelManagerOrg("");
      channelPO.setChannelManagerGroup("");
      channelPO.setAfficheChannelStatus("0");
      channelPO.setIsAllowReview("0");
      channelPO.setRelProjectId(Long.valueOf(0L));
      channelPO.setProClassId(Long.valueOf(relProClassPO.getId()));
      Long id = (Long)this.session.save(channelPO);
      if (String.valueOf(-1).equals(String.valueOf(relProClassPO.getParentId()))) {
        channelPO.setChannelParentId(Long.valueOf(0L));
        channelPO.setChannelIdString(relProClassPO.getClassSortCode() + "$" + id + "$" + "_");
      } else {
        List<Object[]> list = this.session.createQuery("select po.channelId,po.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO  po  where po.proClassId =" + relProClassPO.getParentId()).list();
        Object[] obj = list.get(0);
        channelPO.setChannelParentId(Long.valueOf(obj[0].toString()));
        channelPO.setChannelIdString(String.valueOf(obj[1].toString()) + relProClassPO.getClassSortCode() + "$" + id + "$" + "_");
      } 
      this.session.flush();
      System.out.println("saveInfoChannel....end");
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public void update(RelProClassPO relProClassPO, String parentId, String sortCode, String insertSite) throws Exception {
    String currentId = String.valueOf(relProClassPO.getId());
    String oldSortString = relProClassPO.getClassSort();
    try {
      begin();
      if ("-1".equals(parentId)) {
        String sortCode1 = countSortCode(parentId, sortCode, insertSite);
        relProClassPO.setLevel((short)1);
        relProClassPO.setParentId(-1L);
        relProClassPO.setClassSortCode(Integer.valueOf(sortCode1));
        String classSort = "_" + sortCode1 + "$" + relProClassPO.getId() + "$";
        String newSortString = classSort;
        relProClassPO.setClassSort(classSort);
        updateSubClassSort(this.session, currentId, oldSortString, newSortString);
        this.session.update(relProClassPO);
        this.session.flush();
      } else {
        RelProClassPO parentClass = (RelProClassPO)this.session.get(RelProClassPO.class, Long.valueOf(parentId));
        String sortCode1 = countSortCode(parentId, sortCode, insertSite);
        relProClassPO.setClassSortCode(Integer.valueOf(sortCode1));
        relProClassPO.setLevel((short)(parentClass.getLevel() + 1));
        relProClassPO.setParentId(parentClass.getId());
        String classSort = String.valueOf(parentClass.getClassSort()) + "_" + sortCode1 + "$" + relProClassPO.getId() + "$";
        String newSortString = classSort;
        relProClassPO.setClassSort(classSort);
        updateSubClassSort(this.session, currentId, oldSortString, newSortString);
        this.session.update(relProClassPO);
        this.session.flush();
      } 
      updateForum(relProClassPO);
      updateInfoChannel(relProClassPO);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
  }
  
  public void updateForum(RelProClassPO relProClassPO) {
    try {
      ForumClassPO classPO = new ForumClassPO();
      List<ForumClassPO> list1 = this.session.createQuery("select po from com.js.oa.bbs.po.ForumClassPO po  where po.proClassId =" + relProClassPO.getId()).list();
      classPO = list1.get(0);
      classPO.setClassName(relProClassPO.getName());
      classPO.setClassLevel(relProClassPO.getLevel());
      classPO.setClassSortCode(relProClassPO.getClassSortCode().intValue());
      if (String.valueOf(-1).equals(String.valueOf(relProClassPO.getParentId()))) {
        classPO.setClassParent(0L);
        classPO.setClassParentName(relProClassPO.getName());
        classPO.setClassSort("_" + relProClassPO.getClassSortCode() + "$" + classPO.getId() + "$");
      } else {
        List<Object[]> list = this.session.createQuery("select po.id,po.className,po.classSort from com.js.oa.bbs.po.ForumClassPO po  where po.proClassId =" + relProClassPO.getParentId()).list();
        Object[] obj = list.get(0);
        classPO.setClassParent(Long.valueOf(obj[0].toString()).longValue());
        classPO.setClassParentName(obj[1].toString());
        classPO.setClassSort(String.valueOf(obj[2].toString()) + "_" + relProClassPO.getClassSortCode() + "$" + classPO.getId() + "$");
      } 
      this.session.update(classPO);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public void updateInfoChannel(RelProClassPO relProClassPO) {
    try {
      InformationChannelPO channelPO = null;
      List<InformationChannelPO> list1 = this.session.createQuery("select po from com.js.oa.info.channelmanager.po.InformationChannelPO  po  where po.proClassId =" + relProClassPO.getId()).list();
      if (list1 != null && list1.size() > 0) {
        channelPO = list1.get(0);
        channelPO.setChannelLevel(relProClassPO.getLevel());
        channelPO.setChannelName(relProClassPO.getName());
        channelPO.setChannelSort(relProClassPO.getClassSortCode().intValue());
        if (String.valueOf(-1).equals(String.valueOf(relProClassPO.getParentId()))) {
          channelPO.setChannelParentId(Long.valueOf(0L));
          channelPO.setChannelIdString(relProClassPO.getClassSortCode() + "$" + channelPO.getChannelId() + "$" + "_");
        } else {
          List<Object[]> list = this.session.createQuery("select po.channelId,po.channelIdString from com.js.oa.info.channelmanager.po.InformationChannelPO  po  where po.proClassId =" + relProClassPO.getParentId()).list();
          Object[] obj = list.get(0);
          channelPO.setChannelParentId(Long.valueOf(obj[0].toString()));
          channelPO.setChannelIdString(String.valueOf(obj[1].toString()) + relProClassPO.getClassSortCode() + "$" + channelPO.getChannelId() + "$" + "_");
        } 
        this.session.update(channelPO);
        this.session.flush();
      } else {
        saveInfoChannel(relProClassPO);
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
  }
  
  public RelProClassPO loadById(Long proClassId) {
    RelProClassPO relProClassPO = null;
    try {
      begin();
      relProClassPO = (RelProClassPO)this.session.get(RelProClassPO.class, proClassId);
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
    return relProClassPO;
  }
  
  public void del(Long proClassId) {
    try {
      begin();
      Transaction transaction = this.session.beginTransaction();
      RelProClassPO proClassPO = (RelProClassPO)this.session.get(RelProClassPO.class, proClassId);
      Query queryProClass = this.session.createQuery("from com.js.oa.relproject.po.RelProClassPO po where po.classSort like '%$" + proClassId + "$%'");
      Iterator<RelProClassPO> iterator = queryProClass.iterate();
      while (iterator.hasNext()) {
        RelProClassPO relProClassPO = iterator.next();
        this.session.delete("from com.js.oa.relproject.po.RelProjectPO po where po.classId=" + relProClassPO.getId());
        this.session.delete("from com.js.oa.relproject.po.ProNotePO po where po.projectId in (" + relProClassPO.getId() + ")");
        this.session.delete("from com.js.oa.bbs.po.ForumClassPO po where po.relProjectId in (" + relProClassPO.getId() + ")");
        this.session.delete("from com.js.oa.relproject.po.RelProActorPO po where po.id in (" + relProClassPO.getId() + ")");
        this.session.delete("from com.js.oa.relproject.po.RelProItemPO po where po.projectId in (" + relProClassPO.getId() + ")");
        this.session.delete("from com.js.oa.scheme.workreport.po.WorkReportPO po where po.relprojectId in (" + relProClassPO.getId() + ")");
      } 
      this.session.delete("from com.js.oa.relproject.po.RelProClassPO po where po.classSort like '%$" + proClassId + "$%'");
      this.session.delete(proClassPO);
      Query query = this.session.createQuery("from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.proClassId=" + proClassId);
      List<InformationChannelPO> list = query.list();
      if (list.size() > 0) {
        InformationChannelPO infoChannel = list.get(0);
        Long channelId = infoChannel.getChannelId();
        this.session.delete("from com.js.oa.info.infomanager.po.InformationPO po where po.informationChannel.channelIdString like '%$" + channelId + "$%'");
        this.session.delete("from com.js.oa.info.channelmanager.po.InformationChannelPO po where po.channelIdString like '%$" + channelId + "$%'");
        this.session.delete(infoChannel);
      } 
      transaction.commit();
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
  }
  
  public List<Object> searchByParentId(Long parentId, Long id) {
    List<Object> list = null;
    try {
      begin();
      list = this.session.createQuery("select po.name,po.classSortCode from com.js.oa.relproject.po.RelProClassPO po where po.parentId=" + parentId + " and po.id <>" + id + " order by po.classSort").list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
    return list;
  }
  
  public Object[] searchByParentId(Long parentId, Integer sortCode, Long id) {
    Object[] c = new Object[2];
    Query query = null;
    List<E> list = null;
    try {
      begin();
      query = this.session.createQuery("SELECT po.classSortCode FROM com.js.oa.relproject.po.RelProClassPO po WHERE po.parentId=" + 
          parentId + 
          " AND po.id <>" + 
          id + " order by po.classSortCode");
      list = query.list();
      if (list.isEmpty()) {
        c[0] = "0";
        c[1] = "0";
      } else {
        if (Integer.valueOf(list.get(0).toString()).intValue() > sortCode.intValue()) {
          c[0] = "1";
          c[1] = list.get(0).toString();
        } 
        if (Integer.valueOf(list.get(list.size() - 1).toString()).intValue() < sortCode.intValue()) {
          c[0] = "2";
          c[1] = list.get(list.size() - 1).toString();
        } 
        if (Integer.valueOf(list.get(0).toString()).intValue() < sortCode.intValue() && Integer.valueOf(list.get(list.size() - 1).toString()).intValue() > sortCode.intValue())
          for (int i = 0; i < list.size(); i++) {
            if (Integer.valueOf(list.get(i).toString()).intValue() > sortCode.intValue()) {
              c[0] = "3";
              c[1] = list.get(i).toString();
              break;
            } 
          }  
      } 
    } catch (HibernateException e) {
      e.printStackTrace();
    } finally {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
    } 
    return c;
  }
  
  public void updateSubClassSort(Session session, String parentId, String oldParentSort, String newParentSort) {
    if (oldParentSort.equals(newParentSort))
      return; 
    try {
      begin();
      Query query = session.createQuery("from com.js.oa.relproject.po.RelProClassPO po where po.parentId=" + parentId);
      List<RelProClassPO> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        RelProClassPO po = list.get(i);
        String classSort = po.getClassSort();
        classSort = classSort.replace(oldParentSort, newParentSort);
        po.setClassSort(classSort);
        session.update(po);
        session.beginTransaction().commit();
        session.flush();
      } 
    } catch (HibernateException e) {
      if (session != null)
        try {
          session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public String getSelfAndSubProClassIds(String proClassId) {
    String classesIdString = "";
    try {
      begin();
      Query query = this.session.createQuery("from com.js.oa.relproject.po.RelProClassPO po where po.classSort like '%$" + proClassId + "$%'");
      List<RelProClassPO> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        RelProClassPO po = list.get(i);
        classesIdString = String.valueOf(classesIdString) + String.valueOf(po.getId()) + ",";
      } 
      if (classesIdString.length() > 0)
        classesIdString = classesIdString.substring(0, classesIdString.length() - 1); 
      this.session.close();
    } catch (Exception e) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return classesIdString;
  }
}
