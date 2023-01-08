package com.js.oa.bbs.bean;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.system.manager.service.ManagerService;
import com.js.util.hibernate.HibernateBase;
import com.js.util.page.simple.Page;
import com.js.util.util.StringSplit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class ForumClassEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List listMenu(String wherePara, String curUserId, String domainId) throws Exception {
    List<Object[]> list = new ArrayList();
    try {
      wherePara = String.valueOf(wherePara) + getClassIdString(curUserId, "po.id", wherePara);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    begin();
    List<Object[]> _list = new ArrayList();
    String period = "";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    period = sdf.format(new Date());
    boolean isTrue = true;
    try {
      String hql = " select po.id,po.className,po.classLevel,po.classOwnerName,po.classUserName, po.classHasJunior,po.classParent,po.createdEmp,po.createdOrg,po.className, po.checkExamin,po.fullDay,po.startPeriod,po.endPeriod,po.createdEmp,po.classOwnerId from com.js.oa.bbs.po.ForumClassPO po where (" + 


        
        wherePara + 
        " or ( ( po.classUserId is null or po.classUserId='') and (po.classUserOrg is null or po.classUserOrg ='' )" + 
        " and  ( po.classUserGroup is null or po.classUserGroup ='') ) ) and po.relProjectId=0 and po.proClassId=0 " + 
        "and po.domainId=" + domainId + "   order by po.classSort ";
      _list = this.session.createQuery(hql).list();
      for (int i = 0; i < _list.size(); i++) {
        Object[] obj = _list.get(i);
        if (!"1".equals(obj[11].toString())) {
          String startPeriods = String.valueOf(obj[12].toString()) + ",";
          String endPeriods = String.valueOf(obj[13].toString()) + ",";
          String[] startPeriodss = startPeriods.split(",");
          String[] endPeriodss = endPeriods.split(",");
          for (int ii = 0; ii < startPeriodss.length; ii++) {
            if (period.compareTo(startPeriodss[ii]) >= 0 && 
              period.compareTo(endPeriodss[ii]) <= 0) {
              isTrue = true;
              break;
            } 
            isTrue = false;
          } 
        } 
        if (isTrue)
          list.add(obj); 
        isTrue = true;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  private String getClassIdString(List tmpList, String poId) throws Exception {
    StringBuffer tmp = new StringBuffer(" or " + poId + " in ( ");
    StringBuffer where = new StringBuffer("");
    StringBuffer sql = new StringBuffer("");
    for (int i = 0; i < tmpList.size(); i++)
      sql.append(" po.classSort like '%$").append(tmpList.get(i)).append("$%' or "); 
    sql.append(" 1<>1 ");
    try {
      List tmpList1 = this.session.createQuery(
          "select po.classSort from com.js.oa.bbs.po.ForumClassPO po where " + 
          sql).list();
      for (int j = 0; j < tmpList1.size(); j++)
        where.append(tmpList1.get(j)); 
      String[] str = ("$" + 
        
        StringSplit.splitOrgIdString(where.toString(), "$", "_") + "$")
        .split("\\$\\$");
      where = new StringBuffer("");
      for (int k = 0; k < str.length; k++) {
        if (str[k] != null && !"".equals(str[k]))
          where.append(str[k])
            .append(","); 
      } 
      if (!"".equals(where.toString()) && 
        where.toString().indexOf(",") != -1) {
        tmp.append(where.toString().substring(0, 
              where.toString().lastIndexOf(","))).append(")");
      } else {
        tmp = new StringBuffer("");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      tmp = new StringBuffer("");
      throw e;
    } 
    return tmp.toString();
  }
  
  public String getClassIdString(String curUserId, String poId, String wherePara) throws Exception {
    String tmp = "";
    begin();
    try {
      List tmpList = this.session.createQuery("select po.id  from com.js.oa.bbs.po.ForumClassPO po where  po.classOwnerId=" + curUserId + " or " + wherePara).list();
      tmp = getClassIdString(tmpList, poId);
    } catch (Exception e) {
      e.printStackTrace();
      tmp = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return tmp;
  }
  
  public String getClassIdString(String curUserId, String poId) throws Exception {
    return getClassIdString(curUserId, poId, "1<>1");
  }
  
  private String getSubClassIdString(String wherePara) throws Exception {
    String tmp = "";
    begin();
    try {
      List tmpList = this.session.createQuery("select po.id  from com.js.oa.bbs.po.ForumClassPO po where " + wherePara).list();
      tmp = getSubClassIdString(tmpList, "po.id");
    } catch (Exception e) {
      e.printStackTrace();
      tmp = "";
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return tmp;
  }
  
  private String getSubClassIdString(List tmpList, String poId) throws Exception {
    StringBuffer tmp = new StringBuffer(" or " + poId + " in ( ");
    StringBuffer where = new StringBuffer("");
    StringBuffer sql = new StringBuffer("");
    for (int i = 0; i < tmpList.size(); i++)
      sql.append(" po.classSort like '%$").append(tmpList.get(i)).append("$%' or "); 
    sql.append(" 1<>1 ");
    try {
      List tmpList1 = this.session.createQuery(
          "select po.id from com.js.oa.bbs.po.ForumClassPO po where " + 
          sql).list();
      for (int j = 0; j < tmpList1.size(); j++)
        where.append(tmpList1.get(j)).append(","); 
      if (!"".equals(where.toString()) && 
        where.toString().indexOf(",") != -1) {
        tmp.append(where.toString().substring(0, 
              where.toString().lastIndexOf(","))).append(")");
      } else {
        tmp = new StringBuffer("");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      tmp = new StringBuffer("");
      throw e;
    } 
    return tmp.toString();
  }
  
  public Vector list(Integer offset, String queryText, String wherePara) throws Exception {
    Vector<String> vec = new Vector();
    List list = new ArrayList();
    int pageSize = 15;
    int currentPage = offset.intValue() / pageSize + 1;
    StringBuffer where = new StringBuffer("where 1 = 1 ");
    if (queryText == null || "".equals(queryText)) {
      where.append(" and 1 = 1 ");
    } else {
      where.append(" and po.className like '%").append(queryText).append("%'");
    } 
    where.append(" and (1<>1").append(getSubClassIdString(wherePara)).append(")");
    where.append(" order by po.classSort ");
    try {
      Page page = new Page(
          " po.id,po.className,po.classLevel,po.classOwnerName,po.classUserName,po.classHasJunior,po.createdEmpName,po.classOwnerIds,po.proClassId", 
          " com.js.oa.bbs.po.ForumClassPO po ", 
          where.toString());
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      vec.add(recordCount);
      vec.add((new StringBuilder(String.valueOf(pageSize))).toString());
      vec.add(list);
    } catch (Exception e) {
      e.printStackTrace();
      vec = new Vector<String>();
      vec.add("0");
      vec.add("15");
      vec.add((String)new ArrayList());
      throw e;
    } 
    return vec;
  }
  
  public String getForumClass(Integer offset, String queryText, String wherePara) throws Exception {
    List<String> list = new ArrayList();
    String retStr = ",";
    int pageSize = 999999;
    int currentPage = offset.intValue() / pageSize + 1;
    StringBuffer where = new StringBuffer("where 1 = 1 ");
    if (queryText == null || "".equals(queryText)) {
      where.append(" and 1 = 1 ");
    } else {
      where.append(" and po.className like '%").append(queryText).append("%'");
    } 
    where.append(" and ").append(wherePara).append("");
    where.append(" order by po.classSort ");
    try {
      Page page = new Page(
          " po.id", 
          " com.js.oa.bbs.po.ForumClassPO po ", 
          where.toString());
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      if (list != null)
        for (int i = 0; i < list.size(); i++)
          retStr = String.valueOf(retStr) + list.get(i) + ",";  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return retStr;
  }
  
  public List see(String rightWhere, String domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.id , po.className , po.classLevel , po.classSort, po.classSortCode,po.classParent ,po.classHasJunior from com.js.oa.bbs.po.ForumClassPO po where 1<>1 " + 
          getSubClassIdString(rightWhere) + 
          " and po.domainId=" + domainId + " and po.relProjectId=0 and po.proClassId=0 order by po.classSort ").list();
    } catch (Exception e) {
      e.printStackTrace();
      list = new ArrayList();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public String del(String id) throws Exception {
    begin();
    String retString = "";
    try {
      String fatherId = "none";
      List<String> list = this.session.createQuery("select po.classParent from com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
          id).list();
      String ids = getSubIds(id);
      List<String> listAccessory = this.session.createQuery("select po.forumAttachSave  from com.js.oa.bbs.po.ForumPO po join po.forumClass poo where poo.id in( " + ids + ")").list();
      for (int i = 0; i < listAccessory.size(); i++)
        retString = String.valueOf(retString) + "|" + listAccessory.get(i); 
      this.session.delete(" from com.js.oa.bbs.po.ForumClassPO po where po.id in( " + ids + ")");
      if (list.size() > 0)
        fatherId = list.get(0); 
      if (!"none".equals(fatherId)) {
        List list1 = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classParent=" + 
            fatherId).list();
        if (list1.size() <= 0) {
          List<ForumClassPO> list2 = this.session.createQuery(
              "select po from com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
              fatherId).list();
          if (list2.size() > 0) {
            ForumClassPO po = list2.get(0);
            po.setClassHasJunior(Byte.parseByte("0"));
            this.session.update(po);
          } 
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  private String getSubIds(String id) throws Exception {
    StringBuffer ids = new StringBuffer(id);
    try {
      List<String> list = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classParent=" + id).list();
      for (int i = 0; i < list.size(); i++) {
        String curId = list.get(i);
        ids.append(",").append(getSubIds(curId));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      ids = new StringBuffer(id);
      throw e;
    } 
    return ids.toString();
  }
  
  public ForumClassPO load(String editId) throws Exception {
    ForumClassPO po = null;
    begin();
    try {
      po = (ForumClassPO)this.session.load(ForumClassPO.class, new Long(editId));
    } catch (Exception e) {
      e.printStackTrace();
      po = new ForumClassPO();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return po;
  }
  
  public String update(String editType, String editId, String userOrgGroup, Long classOwnId, String classOwnerName, String classUserName, Short classLevel, String className, String classRemark, String parentId, String currentSortCode, String insertSite, String parentSort, String checkExamin, String startPeriod, String endPeriod, Integer fullDay) throws Exception {
    return update("", "", "", editType, editId, userOrgGroup, 
        classOwnId, classOwnerName, classUserName, 
        classLevel, className, classRemark, parentId, 
        currentSortCode, insertSite, parentSort, checkExamin, 
        startPeriod, endPeriod, fullDay, "");
  }
  
  public String update(String classEmail, String BanPrint, String ownerIdss, String editType, String editId, String userOrgGroup, Long classOwnId, String classOwnerName, String classUserName, Short classLevel, String className, String classRemark, String parentId, String currentSortCode, String insertSite, String parentSort, String checkExamin, String startPeriod, String endPeriod, Integer fullDay, String estopAnonymity) throws Exception {
    String retString = "true";
    begin();
    try {
      Long classId = new Long(editId);
      ForumClassPO forumClassPO = (ForumClassPO)this.session.load(ForumClassPO.class, classId);
      List tmpList = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classLevel=" + classLevel + " and po.classParent=" + parentId + " and po.className='" + className + "' and po.id<>" + editId + " and po.domainId=" + forumClassPO.getDomainId()).list();
      if (tmpList.size() > 0) {
        retString = className;
      } else {
        long oldParent = forumClassPO.getClassParent();
        if ("done".equals(editType)) {
          String sortCode = countSortCode(parentId, currentSortCode, 
              insertSite);
          String classSort = String.valueOf(parentSort) + "_" + sortCode + "$" + 
            classId.toString() + 
            "$";
          forumClassPO.setClassSort(classSort);
          forumClassPO.setClassSortCode(Integer.parseInt(sortCode));
          forumClassPO.setClassParent(Long.parseLong(parentId));
          forumClassPO.setClassLevel(classLevel.shortValue());
          if (parentId != null && !"".equals(parentId)) {
            List<ForumClassPO> list = new ArrayList();
            list = this.session.createQuery(
                "select po from com.js.oa.bbs.po.ForumClassPO po where po.id =" + 
                parentId).list();
            if (list.size() > 0) {
              ForumClassPO parentPo = list.get(0);
              parentPo.setClassHasJunior(Byte.parseByte("1"));
              this.session.update(parentPo);
            } 
          } 
          List list1 = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classParent=" + 
              oldParent).list();
          if (list1.size() <= 0) {
            List<ForumClassPO> list2 = this.session.createQuery(
                "select po from com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
                oldParent).list();
            if (list2.size() > 0) {
              ForumClassPO po = list2.get(0);
              po.setClassHasJunior(Byte.parseByte("0"));
              this.session.update(po);
            } 
          } 
          List<Object[]> tmp = this.session.createQuery("select po.classOwnerIds,po.classParentName from com.js.oa.bbs.po.ForumClassPO po where po.id=" + parentId).list();
          Object object1 = "";
          Object object2 = "";
          if (tmp.size() > 0) {
            Object[] obj = tmp.get(0);
            object1 = obj[0];
            object2 = obj[1];
          } 
          object1 = String.valueOf(object1) + "*" + classOwnId + "*";
          if (!"".equals(object2)) {
            object2 = String.valueOf(object2) + "--" + className;
          } else {
            object2 = className;
          } 
          forumClassPO.setClassOwnerIds((String)object1);
          forumClassPO.setClassParentName((String)object2);
          forumClassPO.setClassOwnerIds(ownerIdss);
          forumClassPO.setBanPrint(BanPrint);
          forumClassPO.setClassEmail(classEmail);
          forumClassPO.setEstopAnonymity(estopAnonymity);
          updateSubTree(classId.longValue(), 
              classLevel.intValue() + 1, 
              sortCode, classSort, 
              (String)object1, (String)object2);
        } 
        forumClassPO.setClassUserId(StringSplit.splitWith(userOrgGroup, 
              "$", 
              "*@"));
        forumClassPO.setClassUserOrg(StringSplit.splitWith(userOrgGroup, 
              "*", "@$"));
        forumClassPO.setClassUserGroup(StringSplit.splitWith(
              userOrgGroup, 
              "@", "$*"));
        forumClassPO.setClassUserName(classUserName);
        forumClassPO.setClassOwnerId(classOwnId.longValue());
        forumClassPO.setClassOwnerName(classOwnerName);
        forumClassPO.setClassName(className);
        forumClassPO.setClassRemark(classRemark);
        forumClassPO.setCheckExamin(checkExamin);
        forumClassPO.setStartPeriod(startPeriod);
        forumClassPO.setEndPeriod(endPeriod);
        forumClassPO.setFullDay(fullDay.intValue());
        this.session.update(forumClassPO);
      } 
      this.session.flush();
    } catch (Exception ex) {
      retString = "false";
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String updatechild(String classEmail, String BanPrint, String ownerIdss, String editType, String editId, String userOrgGroup, Long classOwnId, String classOwnerName, String classUserName, Short classLevel, String className, String classRemark, String parentId, String currentSortCode, String insertSite, String parentSort, String checkExamin, String startPeriod, String endPeriod, Integer fullDay, String estopAnonymity, String isproject) throws Exception {
    String retString = "true";
    begin();
    try {
      Long classId = new Long(editId);
      ForumClassPO forumClassPO = (ForumClassPO)this.session.load(ForumClassPO.class, classId);
      List tmpList = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classLevel=" + classLevel + " and po.classParent=" + parentId + " and po.className='" + className + "' and po.id<>" + editId + " and po.domainId=" + forumClassPO.getDomainId()).list();
      if (tmpList.size() > 0) {
        retString = className;
      } else {
        if ("done".equals(editType)) {
          String sortCode = countSortCode(parentId, currentSortCode, 
              insertSite);
          forumClassPO.setClassParent(Long.parseLong(parentId));
          forumClassPO.setClassLevel(classLevel.shortValue());
          List<Object[]> tmp = this.session.createQuery("select po.classOwnerIds,po.classParentName from com.js.oa.bbs.po.ForumClassPO po where po.id=" + parentId).list();
          Object object = "";
          if (tmp.size() > 0) {
            Object[] obj = tmp.get(0);
            object = obj[0];
          } 
          object = String.valueOf(object) + "*" + classOwnId + "*";
          forumClassPO.setClassOwnerIds((String)object);
          forumClassPO.setClassOwnerIds(ownerIdss);
          forumClassPO.setBanPrint(BanPrint);
          forumClassPO.setClassEmail(classEmail);
          forumClassPO.setEstopAnonymity(estopAnonymity);
        } 
        Query query = null;
        int sort = 0;
        if (currentSortCode.equals("-1")) {
          sort = 500000;
        } else if (insertSite.equals("0")) {
          query = this.session.createQuery(
              " select max(aaa.classSortCode) from  com.js.oa.bbs.po.ForumClassPO aaa where   aaa.classSortCode < " + 

              
              currentSortCode);
          Object maxChannelSort = query.list().get(0);
          if (maxChannelSort == null) {
            sort = Integer.parseInt(currentSortCode) - 10000;
          } else {
            sort = (Integer.parseInt(currentSortCode) + 
              Integer.parseInt(maxChannelSort.toString())) / 
              2;
          } 
        } else {
          query = this.session.createQuery(
              " select min(aaa.classSortCode) from  com.js.oa.bbs.po.ForumClassPO aaa  where aaa.classSortCode > " + 
              
              currentSortCode);
          List<Object> list = new ArrayList();
          list = query.list();
          String minChannelSort = "0";
          if (list != null && list.size() > 0 && list.get(0) != null)
            minChannelSort = list.get(0).toString(); 
          if (!minChannelSort.equals("0")) {
            sort = (Integer.parseInt(currentSortCode) + 
              Integer.parseInt(minChannelSort)) / 2;
          } else {
            sort = Integer.parseInt(currentSortCode) + 10000;
          } 
        } 
        System.out.println(sort);
        if (!"Y".equals(isproject)) {
          forumClassPO.setClassSortCode(sort);
          forumClassPO.setClassSort("_" + sort + "$" + editId + "$");
        } 
        forumClassPO.setClassUserId(StringSplit.splitWith(userOrgGroup, 
              "$", 
              "*@"));
        forumClassPO.setClassUserOrg(StringSplit.splitWith(userOrgGroup, 
              "*", "@$"));
        forumClassPO.setClassUserGroup(StringSplit.splitWith(
              userOrgGroup, 
              "@", "$*"));
        forumClassPO.setClassUserName(classUserName);
        forumClassPO.setClassOwnerId(classOwnId.longValue());
        forumClassPO.setClassOwnerName(classOwnerName);
        forumClassPO.setClassName(className);
        forumClassPO.setClassRemark(classRemark);
        forumClassPO.setCheckExamin(checkExamin);
        forumClassPO.setStartPeriod(startPeriod);
        forumClassPO.setEndPeriod(endPeriod);
        forumClassPO.setFullDay(fullDay.intValue());
        this.session.update(forumClassPO);
      } 
      this.session.flush();
    } catch (Exception ex) {
      retString = "false";
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  private void updateSubTree(long editId, int classLevel, String currentSortCode, String parentSort, String ownerIds, String classParentName) throws Exception {
    try {
      List<ForumClassPO> list = this.session.createQuery("select po from com.js.oa.bbs.po.ForumClassPO po where po.classParent=" + 
          editId + " order by po.classSort  ")
        .list();
      for (int i = 0; i < list.size(); i++) {
        ForumClassPO po = list.get(i);
        long id = po.getId();
        String sortCode = countSortCode((new StringBuilder(String.valueOf(editId))).toString(), (
            new StringBuilder(String.valueOf(currentSortCode))).toString(), "0");
        String classSort = String.valueOf(parentSort) + "_" + sortCode + "$" + id + "$";
        po.setClassSort(classSort);
        po.setClassSortCode(Integer.parseInt(sortCode));
        po.setClassLevel(Short.parseShort((new StringBuilder(String.valueOf(classLevel))).toString()));
        po.setClassOwnerIds(String.valueOf(ownerIds) + "*" + po.getClassOwnerId() + "*");
        po.setClassParentName(String.valueOf(classParentName) + "--" + po.getClassName());
        this.session.update(po);
        updateSubTree(id, classLevel + 1, (new StringBuilder(String.valueOf(po.getClassSortCode()))).toString(), 
            classSort, String.valueOf(ownerIds) + "*" + po.getClassOwnerId() + "*", String.valueOf(classParentName) + "--" + po.getClassName());
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  private String countSortCode(String parentId, String currentSortCode, String insertSite) throws Exception {
    int sortCode = 500000;
    List<Integer> list = null;
    Query query = null;
    if ("-1".equals(currentSortCode)) {
      sortCode = 500000;
    } else if ("0".equals(insertSite)) {
      query = this.session.createQuery("SELECT MAX(forumClass.classSortCode) FROM com.js.oa.bbs.po.ForumClassPO forumClass WHERE forumClass.classParent=" + 
          parentId + 
          " AND forumClass.classSortCode<" + 
          currentSortCode);
      list = query.list();
      if (list == null || (
        list.size() == 1 && (
        "0".equals(list.get(0)) || list.get(0) == null))) {
        sortCode = Integer.parseInt(currentSortCode) - 10000;
      } else {
        sortCode = (((Integer)list.get(0)).intValue() + 
          Integer.parseInt(currentSortCode)) / 2;
      } 
    } else {
      query = this.session.createQuery("SELECT MIN(forumClass.classSortCode) FROM com.js.oa.bbs.po.ForumClassPO forumClass WHERE forumClass.classParent=" + 
          parentId + 
          " AND forumClass.classSortCode>" + 
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
  
  public String add(ForumClassPO forumClassPO, String parentId, String currentSortCode, String insertSite, String parentSort) throws Exception {
    String retString = "true";
    begin();
    try {
      List tmpList = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classLevel=" + forumClassPO.getClassLevel() + " and po.classParent=" + forumClassPO.getClassParent() + " and po.className='" + forumClassPO.getClassName().trim() + "' and po.domainId=" + forumClassPO.getDomainId()).list();
      if (tmpList.size() > 0) {
        retString = forumClassPO.getClassName();
      } else {
        Long classId = (Long)this.session.save(forumClassPO);
        String sortCode = countSortCode(parentId, currentSortCode, 
            insertSite);
        String classSort = String.valueOf(parentSort) + "_" + sortCode + "$" + 
          classId.toString() + 
          "$";
        forumClassPO.setClassSort(classSort);
        forumClassPO.setClassSortCode(Integer.parseInt(sortCode));
        forumClassPO.setClassDate(new Date());
        forumClassPO.setClassHasJunior(Byte.parseByte("0"));
        forumClassPO.setProClassId(Long.valueOf(0L));
        if (parentId != null && !"".equals(parentId)) {
          List<ForumClassPO> list = new ArrayList();
          list = this.session.createQuery(
              "select po from com.js.oa.bbs.po.ForumClassPO po where po.id =" + 
              parentId).list();
          if (list.size() > 0) {
            ForumClassPO parentPo = list.get(0);
            parentPo.setClassHasJunior(Byte.parseByte("1"));
          } 
        } 
        List<Object[]> tmp = this.session.createQuery("select po.classOwnerIds,po.classParentName from com.js.oa.bbs.po.ForumClassPO po where po.id=" + parentId).list();
        if (tmp.size() > 0) {
          Object[] obj = tmp.get(0);
          forumClassPO.setClassOwnerIds(obj[0] + forumClassPO.getClassOwnerIds());
          forumClassPO.setClassParentName(obj[1] + "--" + forumClassPO.getClassParentName());
        } 
      } 
      this.session.flush();
    } catch (Exception ex) {
      retString = "false";
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public Boolean isClassOwner(Long userId, Long domainId) throws Exception {
    Boolean result = new Boolean(false);
    begin();
    try {
      List list = this.session.createQuery("select po from com.js.oa.bbs.po.ForumClassPO po where (po.classOwnerId=" + 
          userId + " or po.classOwnerIds like '%*" + userId + "*%') " + 
          "and po.domainId=" + domainId)
        .list();
      if (list != null && list.size() > 0)
        result = new Boolean(true); 
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return result;
  }
  
  public String selectSimpleName(String simpleNames, String domainId) throws HibernateException {
    String simpleNameStr = "";
    try {
      begin();
      Query simpleNameQuery = 
        this.session.createQuery(
          "select employee.userSimpleName,employee.empName,organization.orgSimpleName  from com.js.system.vo.usermanager.EmployeeVO employee join employee.organizations organization  where employee.empId in(" + 
          
          simpleNames + ") and employee.domainId=" + domainId + " order by employee.empId");
      Iterator<Object[]> iterator = simpleNameQuery.iterate();
      Object[] obj = (Object[])null;
      while (iterator.hasNext()) {
        obj = iterator.next();
        simpleNameStr = 
          String.valueOf(simpleNameStr) + 
          obj[0].toString() + 
          "<" + 
          obj[1].toString() + 
          "/" + 
          obj[2].toString() + 
          ">,";
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println(
          " error in ForumClassEJBBean selectSimpleNameEJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return simpleNameStr;
  }
  
  public Object[] searchClassHasJunior(long fornmId) throws Exception {
    begin();
    List<Object[]> list = null;
    Object[] obj = new Object[2];
    try {
      list = this.session.createQuery("select po.classHasJunior,po.classOwnerIds from com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
          fornmId).list();
      if (!list.isEmpty())
        obj = list.get(0); 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return obj;
  }
  
  public String canIssureNewTitle(String userId, String orgId, String orgIdString, String classId) throws Exception {
    String num = "0";
    begin();
    try {
      List list = null;
      ManagerService mservice = new ManagerService();
      String where = " po.id=" + classId + " and (";
      where = String.valueOf(where) + "(po.classOwnerIds like '%*" + userId + "*%')or(po.classUserName is null)or(";
      where = String.valueOf(where) + mservice.getScopeFinalWhere(userId, orgIdString, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
      where = String.valueOf(where) + "))";
      num = this.session.createQuery("select count(po.id) from com.js.oa.bbs.po.ForumClassPO po where " + where).iterate().next().toString();
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
    } 
    return num;
  }
}
