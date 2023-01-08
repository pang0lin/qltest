package com.js.oa.bbs.bean;

import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.po.ForumPO;
import com.js.oa.bbs.po.PersonalStatPO;
import com.js.oa.search.client.SearchService;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class ForumEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Vector list(String classId, String startDate, String endDate, Integer offset, String queryText, String queryItem, String queryClass, String queryMan, String queryForumType, String wherePara, String replyDesc, String kitDesc, String btimeDesc, String sortFirst, String domainId) throws Exception {
    Vector<String> vec = new Vector();
    int pageSize = 15;
    int currentPage = offset.intValue() / pageSize + 1;
    begin();
    try {
      Date date = null;
      String classOwnerName = "";
      String classOwnerId = "";
      String classDate = "";
      String classRange = "";
      boolean isTrue = true;
      if (classId != null && !"none".equals(classId)) {
        List<ForumClassPO> list1 = this.session.createQuery(
            "select po from com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
            classId).list();
        if (list1.size() > 0) {
          ForumClassPO classPo = list1.get(0);
          classOwnerName = classPo.getClassOwnerName();
          classOwnerId = (new StringBuilder(String.valueOf(classPo.getClassOwnerId()))).toString();
          List tmpList = this.session.createQuery(
              "select po.orgNameString from com.js.system.vo.organizationmanager.OrganizationVO  po join po.employees poo  where poo.empId=" + 
              classOwnerId).list();
          if (tmpList.size() > 0)
            classOwnerName = (new StringBuilder()).append(tmpList.get(0)).append(".").append(classOwnerName).toString(); 
          Locale.setDefault(Locale.CHINA);
          date = classPo.getClassDate();
          classRange = classPo.getClassUserName();
          String period = "";
          SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
          period = sdf.format(new Date());
          if (1 != classPo.getFullDay()) {
            String startPeriods = String.valueOf(classPo.getStartPeriod()) + ",";
            String endPeriods = String.valueOf(classPo.getEndPeriod()) + ",";
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
        } 
      } else {
        isTrue = false;
      } 
      Object object1 = "1";
      Object object2 = "";
      if (classId != null && !"none".equals(classId)) {
        List<Object[]> ll = this.session.createQuery("select po.classHasJunior,po.classOwnerIds from com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
            classId).list();
        if (ll.size() > 0) {
          Object[] obj = ll.get(0);
          object1 = obj[0];
          object2 = obj[1];
        } 
      } 
      vec.add("");
      vec.add((new StringBuilder(String.valueOf(pageSize))).toString());
      vec.add(null);
      vec.add(classOwnerName);
      vec.add(date);
      vec.add(object1);
      vec.add(object2);
      Vector navigationVec = new Vector();
      vec.add(getNavigatorVector(navigationVec, classId, wherePara, true));
      vec.add(classRange);
      vec.add(Boolean.valueOf(isTrue));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      vec = new Vector<String>();
      vec.add("0");
      vec.add("15");
      vec.add((String)new ArrayList());
      vec.add(" ");
      vec.add(new Date());
      vec.add("1");
      vec.add("");
      vec.add((String)new Vector());
      vec.add("");
      vec.add(Boolean.valueOf(false));
      throw e;
    } finally {}
    this.session.close();
    this.transaction = null;
    return vec;
  }
  
  private Vector getNavigatorVector(Vector<Vector<String>> navigationVec, String classId, String wherePara, boolean blnCount) throws Exception {
    try {
      if ("none".equals(classId) || classId == null) {
        Vector<String> itemVec = new Vector();
        itemVec.add("none");
        itemVec.add("所有帖子");
        String count = "0";
        List<String> list = this.session.createQuery("select count(po.id) from com.js.oa.bbs.po.ForumPO po join po.forumClass where " + wherePara).list();
        if (list.size() > 0)
          count = list.get(0); 
        itemVec.add(count);
        navigationVec.add(itemVec);
      } else {
        List<Object[]> list = this.session.createQuery("select po.classParent ,po.id, po.className from com.js.oa.bbs.po.ForumClassPO po where po.id = " + classId).list();
        if (list.size() > 0) {
          Object[] obj = list.get(0);
          Vector<Object> itemVec = new Vector();
          itemVec.add(obj[1]);
          itemVec.add(obj[2]);
          int count = 0;
          if (blnCount) {
            count = getCountByClassId(classId, wherePara);
            itemVec.add((new StringBuilder(String.valueOf(count))).toString());
          } 
          navigationVec.add(itemVec);
          if (!"0".equals(obj[0]))
            navigationVec = getNavigatorVector(navigationVec, obj[0].toString(), wherePara, false); 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return navigationVec;
  }
  
  private int getCountByClassId(String classId, String wherePara) throws Exception {
    int count = 0;
    try {
      List list = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classSort like '%$" + 
          classId + "$%' ").list();
      StringBuffer ids = new StringBuffer(classId);
      for (int i = 0; i < list.size(); i++)
        ids.append(",").append(list.get(i)); 
      List<String> list1 = this.session.createQuery("select count(po.id) from com.js.oa.bbs.po.ForumPO po join po.forumClass where  po.forumClass.classHasJunior=2 and  po.forumClass.id in (" + ids + ") and (" + wherePara + ")").list();
      if (list1.size() > 0 && !"0".equals(list1.get(0)))
        count = Integer.parseInt(list1.get(0)); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return count;
  }
  
  public String classIdQuery(String classId) throws Exception {
    begin();
    StringBuffer ids = new StringBuffer(classId);
    try {
      List list = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumClassPO po where po.classSort like '%$" + 
          classId + "$%' ").list();
      for (int i = 0; i < list.size(); i++)
        ids.append(",").append(list.get(i)); 
    } catch (Exception e) {
      e.printStackTrace();
      ids = new StringBuffer(classId);
      throw e;
    } finally {
      this.session.close();
    } 
    return " po.forumClass.id in (" + ids + ") ";
  }
  
  public String see(Long curUserId) throws Exception {
    String retString = "";
    begin();
    try {
      List<String> list = this.session.createQuery("select po.empGnome from com.js.system.vo.usermanager.EmployeeVO po where po.empId=" + 
          curUserId.longValue()).list();
      if (list.size() > 0)
        retString = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String add(ForumPO po, String classId, String forumId, Long curUserId, String addSign, String forumSign) throws Exception {
    String retString = "true";
    begin();
    try {
      if (classId != null)
        classId.indexOf("^"); 
      ForumClassPO classPo = (ForumClassPO)this.session.load(ForumClassPO.class, 
          new Long(classId));
      po.setForumClass(classPo);
      String owns = classPo.getClassOwnerIds();
      String own = (new Long(po.getForumAuthorId())).toString();
      if (owns.indexOf("*" + own + "*") != -1) {
        po.setExaminNum(3);
      } else if (classPo.getCheckExamin() != null && classPo.getCheckExamin().equals("1")) {
        po.setExaminNum(1);
      } else {
        po.setExaminNum(3);
      } 
      if ("none".equals(forumId)) {
        classPo.setClassHasJunior(Byte.parseByte("2"));
        this.session.update(classPo);
      } else {
        ForumPO mainPo = (ForumPO)this.session.load(ForumPO.class, 
            new Long(forumId));
        mainPo.setForumRevertNum(mainPo.getForumRevertNum() + 1);
        mainPo.setForumKits(mainPo.getForumKits() + 1);
        po.setForumTopicId(Long.parseLong(forumId));
      } 
      Long nowId = (Long)this.session.save(po);
      retString = nowId.toString();
      List<PersonalStatPO> listStat = this.session.createQuery("select po from com.js.oa.bbs.po.PersonalStatPO po where po.empId=" + curUserId.longValue()).list();
      if (listStat.size() <= 0) {
        PersonalStatPO stat = new PersonalStatPO();
        stat.setEmpId(curUserId.longValue());
        stat.setForumNum(1);
        stat.setDomainId(po.getDomainId());
        this.session.save(stat);
      } else {
        PersonalStatPO stat = listStat.get(0);
        stat.setForumNum(1 + stat.getForumNum());
        this.session.update(stat);
      } 
      this.session.flush();
    } catch (Exception ex) {
      retString = "false";
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
    } 
    return retString;
  }
  
  public Vector followList(String forumId, Integer offset, String sign) throws Exception {
    int pageSize = 20;
    Vector<String> vec = new Vector();
    List<ForumPO> list = new ArrayList();
    begin();
    try {
      String topicTitle = "";
      list = this.session.createQuery(
          "select po from com.js.oa.bbs.po.ForumPO po where po.id=" + 
          forumId).list();
      int oldkits = 0;
      if (list.size() > 0) {
        ForumPO po = list.get(0);
        topicTitle = po.getForumTitle();
        vec.add(topicTitle);
        vec.add(null);
        vec.add("");
        vec.add((new StringBuilder(String.valueOf(pageSize))).toString());
        oldkits = po.getForumKits();
        vec.add("");
        vec.add("0");
        vec.add(Long.valueOf(po.getForumAuthorId()));
      } 
      if ("0".equals(sign)) {
        Connection conn = null;
        try {
          int kits = oldkits + 1;
          conn = (new DataSourceBase()).getDataSource().getConnection();
          Statement stmt = conn.createStatement();
          stmt.executeUpdate("update oa_forum set forumkits='" + kits + 
              "' where forum_id=" + forumId);
          stmt.close();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          conn.close();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return vec;
  }
  
  public Long getAuthorIdByForumId(String id) throws Exception {
    Connection conn = null;
    Long author = new Long(0L);
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select forumauthorid from oa_forum where forum_id=" + id + " and forumtopicid=0");
      if (rs.next())
        author = Long.valueOf(rs.getLong(1)); 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      conn.close();
    } 
    return author;
  }
  
  public List loadContent(List<Object[]> list) throws Exception {
    begin();
    ArrayList<Object[]> retList = new ArrayList();
    try {
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        Object object = obj[6];
        String content = "";
        Query query = this.session.createQuery(" select aaa.forumContent from com.js.oa.bbs.po.ForumPO aaa where aaa.id=" + 
            object);
        obj[5] = query.iterate().next();
        retList.add(obj);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retList;
  }
  
  public String getForumTitle(String forumId) throws Exception {
    String retString = "";
    begin();
    try {
      ForumPO po = (ForumPO)this.session.load(ForumPO.class, new Long(forumId));
      retString = po.getForumTitle();
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retString;
  }
  
  public String delForum(String forumId, String classId) throws Exception {
    String retString = "true";
    begin();
    Connection conn = null;
    Statement stmt = null;
    try {
      List li = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumPO po where po.forumTopicId=0 and po.id=" + 
          forumId).list();
      if (li.size() <= 0) {
        List<String> lii = this.session.createQuery(
            "select po.forumTopicId from com.js.oa.bbs.po.ForumPO po where po.id=" + 
            forumId).list();
        if (lii.size() > 0) {
          ForumPO fpo = (ForumPO)this.session.load(ForumPO.class, new Long(lii.get(0)));
          int kits = fpo.getForumKits() + 1;
          int revertnum = fpo.getForumRevertNum() - 1;
          conn = (new DataSourceBase()).getDataSource().getConnection();
          stmt = conn.createStatement();
          stmt.executeUpdate("update oa_forum set forumkits='" + kits + "', forumrevertnum= " + revertnum + " where forum_id=" + fpo.getId());
          stmt.close();
        } 
      } 
      List<String> listAccessory = this.session.createQuery("select po.forumAttachSave from  com.js.oa.bbs.po.ForumPO po where po.id=" + 
          forumId + " or po.forumTopicId=" + forumId).list();
      for (int i = 0; i < listAccessory.size(); i++)
        retString = String.valueOf(retString) + "|" + listAccessory.get(i); 
      Query querydelete = this.session.createQuery("select po.id from  com.js.oa.bbs.po.ForumPO po  where po.id=" + 
          forumId + " or po.forumTopicId=" + forumId);
      List<Long> listdelet = querydelete.list();
      this.session.delete(
          " from com.js.oa.bbs.po.ForumPO po where po.id=" + 
          forumId + " or po.forumTopicId=" + forumId);
      SearchService.getInstance();
      String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
      SearchService.getInstance();
      String isearchSwitch = SearchService.getiSearchSwitch();
      if (listdelet != null && listdelet.size() != 0)
        for (int j = 0; j < listdelet.size(); j++) {
          Long id = listdelet.get(j);
          if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id.toString()) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
            SearchService.getInstance();
            SearchService.deleteIndex(id.toString(), "oa_forum");
          } 
        }  
      List list = this.session.createQuery("select po.id from  com.js.oa.bbs.po.ForumPO po join po.forumClass where po.forumClass.id=" + 
          classId).list();
      if (list.size() <= 0) {
        ForumClassPO po = (ForumClassPO)this.session.load(ForumClassPO.class, 
            new Long(classId));
        po.setClassHasJunior(Byte.parseByte("0"));
        this.session.update(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String goExamin(String forumId) throws Exception {
    String retString = "true";
    begin();
    try {
      List<ForumPO> list = this.session.createQuery(
          "select po from  com.js.oa.bbs.po.ForumPO po where po.id=" + 
          forumId).list();
      if (list.size() > 0) {
        ForumPO po = list.get(0);
        po.setExaminNum(2);
        po.setForumModifyTime(new Date());
        this.session.update(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String soulForum(String forumId, String status) throws Exception {
    String retString = "true";
    begin();
    try {
      List<ForumPO> list = this.session.createQuery(
          "select po from  com.js.oa.bbs.po.ForumPO po where po.id=" + 
          forumId).list();
      if (list.size() > 0) {
        ForumPO po = list.get(0);
        po.setForumIsSoul(Byte.parseByte(status));
        this.session.update(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String setAuth(String type, String isnot, String forumId) throws Exception {
    String retString = "true";
    begin();
    int ntIsNot = Integer.parseInt(isnot);
    try {
      List<ForumPO> list = this.session.createQuery("select po from  com.js.oa.bbs.po.ForumPO po where po.id=" + forumId).list();
      if (list.size() > 0) {
        ForumPO po = list.get(0);
        if ("print".equals(type)) {
          po.setForumNotPrint(ntIsNot);
        } else if ("upd".equals(type)) {
          po.setForumNotUpd(ntIsNot);
        } else {
          po.setForumNotFlow(ntIsNot);
        } 
        this.session.update(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public String setTop(String setTopType, String forumId, String classId) throws Exception {
    String retString = "true";
    begin();
    try {
      List<ForumPO> list = this.session.createQuery("select po from  com.js.oa.bbs.po.ForumPO po where po.id=" + forumId).list();
      if (list.size() > 0) {
        ForumPO po = list.get(0);
        if ("1".equals(setTopType)) {
          Query query = this.session.createQuery(
              "select max(po.forumTopOrder) from  com.js.oa.bbs.po.ForumPO po where po.forumClass.id=" + classId);
          int maxforumTopOrder = Integer.parseInt(query.iterate().next().toString());
          maxforumTopOrder++;
          po.setForumTopOrder(maxforumTopOrder);
        } else {
          po.setForumTopOrder(0L);
        } 
        this.session.update(po);
      } 
      this.session.flush();
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return retString;
  }
  
  public List getForumClassPODetails(String classId) throws Exception {
    List<ForumClassPO> retString = new ArrayList();
    begin();
    try {
      List<ForumClassPO> list = this.session.createQuery(
          "select po from  com.js.oa.bbs.po.ForumClassPO po where po.id=" + 
          classId).list();
      if (list.size() > 0) {
        ForumClassPO po = list.get(0);
        retString.add(po);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return retString;
  }
  
  public List getForumPODetails(String forumId) throws Exception {
    List<ForumPO> retString = new ArrayList();
    begin();
    try {
      List<ForumPO> list = this.session.createQuery("select po from  com.js.oa.bbs.po.ForumPO po where po.id=" + forumId).list();
      if (list.size() > 0) {
        ForumPO po = list.get(0);
        retString.add(po);
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
  
  public String move(String ids, String classId, String oriClassId) throws Exception {
    String retString = "true";
    begin();
    try {
      if (ids == null)
        return "false"; 
      ForumClassPO cpo = (ForumClassPO)this.session.load(ForumClassPO.class, 
          new Long(classId));
      if (!"".equals(ids)) {
        ForumPO po = (ForumPO)this.session.load(ForumPO.class, 
            new Long(ids));
        List<ForumPO> followList = this.session.createQuery(
            "select po from com.js.oa.bbs.po.ForumPO po where po.forumTopicId=" + 
            ids).list();
        for (int k = 0; k < followList.size(); k++) {
          ForumPO followPo = followList.get(k);
          followPo.setForumClass(cpo);
          followPo.setForumModifyTime(new Date());
          this.session.update(followPo);
        } 
        po.setForumModifyTime(new Date());
        po.setForumClass(cpo);
        if (cpo.getCheckExamin() != null) {
          po.setExaminNum(Integer.parseInt(cpo.getCheckExamin()));
        } else {
          po.setExaminNum(2);
        } 
        this.session.update(po);
        cpo.setClassHasJunior(Byte.parseByte("2"));
      } 
      this.session.update(cpo);
      List list = this.session.createQuery("select po.id from com.js.oa.bbs.po.ForumPO po join po.forumClass where po.forumClass.id=" + 
          oriClassId).list();
    } catch (Exception e) {
      retString = "false";
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    this.session.close();
    this.transaction = null;
    return retString;
  }
  
  public List userlist(String empId) throws Exception {
    List<String> list = new ArrayList();
    begin();
    try {
      EmployeeVO po = (EmployeeVO)this.session.load(EmployeeVO.class, new Long(empId));
      list.add(po.getEmpName());
      list.add(po.getEmpNumber());
      List<String> listOrg = new ArrayList();
      Set set = po.getOrganizations();
      if (set.size() > 0) {
        Iterator<OrganizationVO> it = set.iterator();
        while (it.hasNext()) {
          OrganizationVO orgPo = it.next();
          listOrg.add(orgPo.getOrgNameString());
        } 
      } 
      list.add(listOrg);
      list.add(po.getEmpEnglishName());
      list.add((new StringBuilder(String.valueOf(po.getEmpSex()))).toString());
      list.add(po.getEmpNativePlace());
      list.add(po.getEmpNation());
      list.add(po.getEmpInterest());
      list.add(po.getEmpDescribe());
      list.add(po.getEmpGnome());
      list.add(po.getEmpLivingPhoto());
    } catch (Exception e) {
      e.printStackTrace();
      list = new ArrayList<String>();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return list;
  }
  
  public Vector noteBook(String forumId) throws Exception {
    Vector<Object> vec = new Vector();
    begin();
    try {
      List<Object[]> list = this.session.createQuery("select poo.id,po.forumTitle,po.forumTopicId from com.js.oa.bbs.po.ForumPO po join po.forumClass poo where po.id=" + forumId).list();
      if (list.size() > 0) {
        Object[] obj = list.get(0);
        vec.add(obj[0]);
        vec.add(obj[1]);
        if ("0".equals(obj[2])) {
          vec.add(forumId);
        } else {
          vec.add(obj[2]);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      vec = new Vector();
      throw e;
    } finally {
      this.session.close();
      this.transaction = null;
    } 
    return vec;
  }
  
  public Map getSingle(String forumId, String userId) throws Exception {
    begin();
    HashMap<Object, Object> forumMap = new HashMap<Object, Object>();
    try {
      Query query = this.session.createQuery("select a.forumTitle,a.forumSign,a.forumType,a.anonymous,a.forumAttachName,a.forumAttachSave,a.forumContent from com.js.oa.bbs.po.ForumPO a where a.id=" + 
          
          forumId + " and a.forumAuthorId=" + userId);
      Iterator<Object[]> iter = query.iterate();
      if (iter.hasNext()) {
        Object[] obj = iter.next();
        forumMap.put("forumTitle", obj[0]);
        forumMap.put("forumSign", obj[1]);
        forumMap.put("forumType", obj[2]);
        forumMap.put("anonymous", obj[3]);
        String forumAttachName = (obj[4] == null) ? "" : obj[4].toString();
        String forumAttachSave = (obj[5] == null) ? "" : obj[5].toString();
        String[] forumAttachNameArr = new String[0];
        String[] forumAttachSaveArr = new String[0];
        if (forumAttachName.indexOf("|") >= 0) {
          forumAttachName = forumAttachName.replace('|', ',');
          forumAttachNameArr = forumAttachName.split(",");
          forumAttachSave = forumAttachSave.replace('|', ',');
          forumAttachSaveArr = forumAttachSave.split(",");
        } else if (!"".equals(forumAttachName)) {
          forumAttachNameArr = new String[1];
          forumAttachSaveArr = new String[1];
          forumAttachNameArr[0] = forumAttachName;
          forumAttachSaveArr[0] = forumAttachSave;
        } 
        forumMap.put("forumAttachName", forumAttachNameArr);
        forumMap.put("forumAttachSave", forumAttachSaveArr);
        forumMap.put("content", obj[6]);
      } 
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return forumMap;
  }
  
  public Integer update(String[] forumPara) throws Exception {
    begin();
    int result = 0;
    try {
      ForumPO forumPO = (ForumPO)this.session.load(ForumPO.class, new Long(forumPara[0]));
      forumPO.setForumTitle(forumPara[1]);
      ForumClassPO classPo = forumPO.getForumClass();
      String owns = classPo.getClassOwnerIds();
      String own = (new Long(forumPO.getForumAuthorId())).toString();
      if (owns.indexOf("*" + own + "*") != -1) {
        forumPO.setExaminNum(3);
      } else if (classPo.getCheckExamin() != null && classPo.getCheckExamin().equals("1")) {
        forumPO.setExaminNum(1);
      } else {
        forumPO.setExaminNum(3);
      } 
      forumPO.setAnonymous(Byte.parseByte(forumPara[4]));
      forumPO.setForumSign(forumPara[5]);
      forumPO.setForumAttachName(forumPara[6]);
      forumPO.setForumAttachSave(forumPara[7]);
      forumPO.setForumContent(forumPara[3]);
      forumPO.setForumModifyTime(new Date());
      this.session.flush();
    } catch (Exception e) {
      System.out.println("---------------------------------------------");
      e.printStackTrace();
      System.out.println("---------------------------------------------");
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return new Integer(result);
  }
  
  public List searchByClassId(long fornmId) throws Exception {
    begin();
    List list = null;
    try {
      String sqlString = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm where fornm.forumClass.id =" + fornmId + "  and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumType desc,fornm.forumTopOrder desc,fornm.forumIsSoul desc,fornm.newretime desc limit 5";
        list = this.session.createQuery(sqlString).list();
      } else if (databaseType.indexOf("oracle") >= 0) {
        sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm where fornm.forumClass.id =" + fornmId + "  and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumType desc,fornm.forumTopOrder desc,fornm.forumIsSoul desc,fornm.newretime desc";
        Query oracleQuery = this.session.createQuery(sqlString);
        oracleQuery.setMaxResults(5);
        list = oracleQuery.list();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public ForumPO searchById(long fornmId) throws Exception {
    begin();
    ForumPO forumPO = null;
    try {
      forumPO = (ForumPO)this.session.get(ForumPO.class, Long.valueOf(fornmId));
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return forumPO;
  }
  
  public List searchAttentionByUserid(long userId, String isPortal) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if ("Y".equals(isPortal)) {
        String sqlString = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          sqlString = "select fornm from com.js.system.vo.messages.Remind remind,com.js.oa.bbs.po.ForumPO fornm  where remind.data_id=fornm.id and remind.emp_id =" + userId + " and remind.remind_type like 'Forum' and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumModifyTime desc limit 6";
          list = this.session.createQuery(sqlString).list();
        } else if (databaseType.indexOf("oracle") >= 0) {
          sqlString = "select fornm from com.js.system.vo.messages.Remind remind,com.js.oa.bbs.po.ForumPO fornm  where remind.data_id=fornm.id and remind.emp_id =" + userId + " and remind.remind_type like 'Forum' and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumModifyTime desc";
          Query oracleQuery = this.session.createQuery(sqlString);
          oracleQuery.setMaxResults(6);
          list = oracleQuery.list();
        } 
      } else {
        list = this.session.createQuery("select fornm from com.js.system.vo.messages.Remind remind,com.js.oa.bbs.po.ForumPO fornm  where remind.data_id=fornm.id and remind.emp_id =" + userId + " and remind.remind_type like 'Forum' and fornm.forumTopicId=0  and fornm.examinNum <>1 order by fornm.forumModifyTime desc ").list();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List searchSoulByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if (!"".equals(forumClass)) {
        forumClass = forumClass.substring(0, forumClass.length() - 1);
        if ("Y".equals(isPortal)) {
          String sqlString = "";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0  and fornm.forumIsSoul='1'  and fornm.examinNum <>1 order by fornm.forumModifyTime desc limit 6";
            list = this.session.createQuery(sqlString).list();
          } else if (databaseType.indexOf("oracle") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0  and fornm.forumIsSoul='1'  and fornm.examinNum <>1 order by fornm.forumModifyTime desc";
            Query oracleQuery = this.session.createQuery(sqlString);
            oracleQuery.setMaxResults(6);
            list = oracleQuery.list();
          } 
        } else {
          list = this.session.createQuery("select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0  and fornm.forumIsSoul='1'  and fornm.examinNum <>1 order by fornm.forumModifyTime desc ").list();
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List searchTopByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if (!"".equals(forumClass)) {
        forumClass = forumClass.substring(0, forumClass.length() - 1);
        if ("Y".equals(isPortal)) {
          String sqlString = "";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0  and fornm.forumTopOrder<>'0'  and fornm.examinNum <>1 order by fornm.forumTopOrder desc limit 6";
            list = this.session.createQuery(sqlString).list();
          } else if (databaseType.indexOf("oracle") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0  and fornm.forumTopOrder<>'0'  and fornm.examinNum <>1 order by fornm.forumTopOrder desc";
            Query oracleQuery = this.session.createQuery(sqlString);
            oracleQuery.setMaxResults(6);
            list = oracleQuery.list();
          } 
        } else {
          list = this.session.createQuery("select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0  and fornm.forumIsSoul<>'0'  and fornm.examinNum <>1 order by fornm.forumTopOrder desc ").list();
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List searchHotByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if (!"".equals(forumClass)) {
        forumClass = forumClass.substring(0, forumClass.length() - 1);
        if ("Y".equals(isPortal)) {
          String sqlString = "";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0   and fornm.examinNum <>1 order by fornm.forumKits desc limit 6";
            list = this.session.createQuery(sqlString).list();
          } else if (databaseType.indexOf("oracle") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0   and fornm.examinNum <>1 order by fornm.forumKits desc";
            Query oracleQuery = this.session.createQuery(sqlString);
            oracleQuery.setMaxResults(6);
            list = oracleQuery.list();
          } 
        } else {
          list = this.session.createQuery("select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0    and fornm.examinNum <>1 order by fornm.forumKits desc ").list();
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List searchNewUpdateByUserid(String isPortal, String forumClass) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if (!"".equals(forumClass)) {
        forumClass = forumClass.substring(0, forumClass.length() - 1);
        if ("Y".equals(isPortal)) {
          String sqlString = "";
          String databaseType = SystemCommon.getDatabaseType();
          if (databaseType.indexOf("mysql") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0   and fornm.examinNum <>1 order by fornm.forumModifyTime desc limit 6";
            list = this.session.createQuery(sqlString).list();
          } else if (databaseType.indexOf("oracle") >= 0) {
            sqlString = "select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0   and fornm.examinNum <>1 order by fornm.forumModifyTime desc";
            Query oracleQuery = this.session.createQuery(sqlString);
            oracleQuery.setMaxResults(6);
            list = oracleQuery.list();
          } 
        } else {
          list = this.session.createQuery("select fornm from com.js.oa.bbs.po.ForumPO fornm , fornm.forumClass forumClass where  forumClass.id in(" + forumClass + ") and fornm.forumTopicId=0    and fornm.examinNum <>1 order by fornm.forumModifyTime desc ").list();
        } 
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getForumClass(String forumClassIds, String domainId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      if (forumClassIds.length() > 0) {
        forumClassIds = forumClassIds.substring(0, forumClassIds.length() - 1);
        list = this.session.createQuery("select po.id,po.className from  com.js.oa.bbs.po.ForumClassPO po where po.id in(" + forumClassIds + ")").list();
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public boolean isSubOrg(String subOrgId, String parentOrgId) throws Exception {
    boolean returnValue = Boolean.FALSE.booleanValue();
    if ("".equals(subOrgId) || subOrgId == null)
      return returnValue; 
    begin();
    OrganizationVO vo = new OrganizationVO();
    Long id = new Long(subOrgId);
    try {
      this.session.load(vo, id);
      String orgIdString = vo.getOrgIdString();
      orgIdString = orgIdString.substring(0, orgIdString.length() - 1);
      String[] orgIds = orgIdString.split("\\$");
      for (int i = 0; i < orgIds.length; i++) {
        String orgIdPattern = orgIds[i];
        if (!orgIdPattern.startsWith("_"))
          if (orgIdPattern.equals(parentOrgId)) {
            returnValue = Boolean.TRUE.booleanValue();
            break;
          }  
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return returnValue;
  }
  
  public String getCustomScope(String currentUserId, String rightCode, String domainId) throws Exception {
    String flag = "0";
    if ("08*03*02".equals(rightCode) || "08*03*01".equals(rightCode))
      flag = "1"; 
    return getCustomScope(currentUserId, rightCode, domainId, flag);
  }
  
  public String getCustomScope(String currentUserId, String rightCode, String domainId, String flag) throws Exception {
    String returnValue = "";
    Statement stat = null;
    ResultSet rs = null;
    DbOpt opt = new DbOpt();
    try {
      stat = opt.getStatement();
      String sql = "select scope.RIGHTSCOPESCOPE,scope.RIGHTSCOPEUSER from org_rightscope scope,org_right org_right where scope.right_id=org_right.right_id and scope.emp_id='" + 

        
        currentUserId + "' " + 
        "and scope.domain_id='" + domainId + "' " + 
        "and org_right.rightcode='" + rightCode + "'";
      rs = stat.executeQuery(sql);
      String orgString = "";
      String scopeUserString = "";
      if (rs.next()) {
        orgString = rs.getString("RIGHTSCOPESCOPE");
        if ("1".equals(flag))
          orgString = StaticParam.orgIdsByOrgId(orgString); 
        scopeUserString = rs.getString("RIGHTSCOPEUSER");
      } 
      returnValue = String.valueOf(orgString) + "-" + scopeUserString;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      stat.close();
      opt.close();
    } 
    return returnValue;
  }
  
  public String[] getCreateIdsOrgIdsByForumId(String forumClassId, String domainId) throws Exception {
    String[] returnValue = new String[2];
    Statement stat = null;
    ResultSet rs = null;
    DbOpt opt = new DbOpt();
    try {
      stat = opt.getStatement();
      String sql = "select CREATEDEMP,CREATEDORG from oa_forumclass where class_id='" + forumClassId + "' and domain_id='" + domainId + "'";
      rs = stat.executeQuery(sql);
      if (rs.next()) {
        returnValue[0] = rs.getString("CREATEDEMP");
        returnValue[1] = rs.getString("CREATEDORG");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      rs.close();
      stat.close();
      opt.close();
    } 
    return returnValue;
  }
  
  public void updateModifytime(Long id) {
    String sql = "";
    Statement stat = null;
    DbOpt opt = new DbOpt();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      stat = opt.getStatement();
      if (databaseType.indexOf("mysql") >= 0)
        sql = "update oa_forum set newretime=now() where forum_id=" + id + " or forumtopicid=" + id; 
      if (databaseType.indexOf("oracle") >= 0)
        sql = "update oa_forum set newretime=sysdate where forum_id=" + id + " or forumtopicid=" + id; 
      stat.executeUpdate(sql);
      stat.close();
      opt.close();
    } catch (Exception x) {
      try {
        stat.close();
        opt.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      x.printStackTrace();
    } 
  }
}
