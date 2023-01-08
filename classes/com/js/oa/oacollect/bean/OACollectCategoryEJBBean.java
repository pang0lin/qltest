package com.js.oa.oacollect.bean;

import com.js.oa.message.bean.MsManageEJBBean;
import com.js.oa.oacollect.po.OaCollectCategory;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

public class OACollectCategoryEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Long saveOACollectCategory(OaCollectCategory po, HttpServletRequest request) throws Exception {
    String insertSite = request.getParameter("insertSite");
    return saveOACollectCategory(po, insertSite);
  }
  
  public Long saveOACollectCategory(OaCollectCategory po, String insertSite) throws Exception {
    Long id = null;
    begin();
    try {
      if (-1L == po.getParentId().longValue()) {
        String sortCode1 = countSortCode(po.getParentId().toString(), po.getCategorySortCode().toString(), insertSite);
        po.setCategorySortCode(Integer.valueOf(sortCode1));
        po.setCategoryLevel(Integer.valueOf(1));
        po.setParentId(new Long(-1L));
        id = (Long)this.session.save(po);
        String classSort = "_" + sortCode1 + "$" + id.toString() + "$";
        po.setCategorySort(classSort);
        this.session.flush();
      } else {
        OaCollectCategory poParent = (OaCollectCategory)this.session.get(OaCollectCategory.class, po.getParentId());
        String sortCode1 = countSortCode(po.getParentId().toString(), po.getCategorySortCode().toString(), insertSite);
        po.setCategoryLevel(Integer.valueOf(poParent.getCategoryLevel().intValue() + 1));
        po.setCategorySortCode(Integer.valueOf(sortCode1));
        po.setParentId(poParent.getCategoryId());
        id = (Long)this.session.save(po);
        String classSort = String.valueOf(poParent.getCategorySort()) + "_" + sortCode1 + "$" + id.toString() + "$";
        po.setCategorySort(classSort);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return id;
  }
  
  public OaCollectCategory loadOaCollectCategory(Long id) throws Exception {
    OaCollectCategory po = null;
    begin();
    try {
      po = (OaCollectCategory)this.session.get(OaCollectCategory.class, id);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return po;
  }
  
  public boolean updateOACollectCategory(OaCollectCategory po, HttpServletRequest request) throws HibernateException {
    boolean re = true;
    String insertSite = request.getParameter("insertSite");
    String currentId = String.valueOf(po.getCategoryId());
    String oldSortString = po.getCategorySort();
    begin();
    try {
      if (po.getParentId().longValue() == -1L) {
        String sortCode1 = countSortCode(po.getParentId().toString(), po.getCategorySortCode().toString(), insertSite);
        po.setCategoryLevel(Integer.valueOf(1));
        po.setParentId(new Long(-1L));
        po.setCategorySortCode(Integer.valueOf(sortCode1));
        String classSort = "_" + sortCode1 + "$" + po.getCategoryId() + "$";
        String newSortString = classSort;
        po.setCategorySort(classSort);
        updateSubClassSort(this.session, currentId, oldSortString, newSortString);
        this.session.update(po);
        this.session.flush();
      } else {
        OaCollectCategory parentPo = (OaCollectCategory)this.session.get(OaCollectCategory.class, Long.valueOf(po.getParentId().longValue()));
        String sortCode1 = countSortCode(po.getParentId().toString(), po.getCategorySortCode().toString(), insertSite);
        po.setCategorySortCode(Integer.valueOf(sortCode1));
        po.setCategoryLevel(Integer.valueOf(parentPo.getCategoryLevel().intValue() + 1));
        po.setParentId(parentPo.getCategoryId());
        String classSort = String.valueOf(parentPo.getCategorySort()) + "_" + sortCode1 + "$" + po.getCategoryId() + "$";
        String newSortString = classSort;
        po.setCategorySort(classSort);
        updateSubClassSort(this.session, currentId, oldSortString, newSortString);
        this.session.update(po);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return re;
  }
  
  public boolean updateByYourYuanShengSql(String sql) throws Exception {
    boolean result = true;
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status_db = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status_db);
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
      result = false;
    } 
    return result;
  }
  
  public boolean deleteOACollectCategory(String ids) throws Exception {
    begin();
    boolean re = true;
    try {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      String[] id = ids.split(",");
      String whereString = "where 1<>1";
      for (int i = 0; i < id.length; i++)
        whereString = String.valueOf(whereString) + " or po.categorySort like '%$" + ids + "$%'"; 
      this.session.delete("from com.js.oa.oacollect.po.OaCollectCategory po " + whereString);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } finally {
      this.session.close();
    } 
    return re;
  }
  
  private String countSortCode(String parentId, String currentSortCode, String insertSite) throws Exception {
    int sortCode = 500000;
    List<E> list = null;
    Query query = null;
    if ("-1".equals(currentSortCode)) {
      sortCode = 500000;
    } else if ("0".equals(insertSite)) {
      query = this.session.createQuery("SELECT MAX(po.categorySortCode) FROM com.js.oa.oacollect.po.OaCollectCategory po WHERE po.parentId=" + 
          parentId + 
          " AND po.categorySortCode<" + 
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
      query = this.session.createQuery("SELECT MIN(po.categorySortCode) FROM com.js.oa.oacollect.po.OaCollectCategory po WHERE po.parentId=" + 
          parentId + 
          " AND po.categorySortCode>" + 
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
  
  public List<Object> searchAll(Long id, String empId) {
    return searchAll(id, empId, "");
  }
  
  public List<Object> searchAll(Long id, String empId, String flag) {
    List<Object[]> list = null;
    String con = "";
    if ("".equals(flag)) {
      String group = StaticParam.getGroupIdByEmpId(empId);
      String orgId = StaticParam.getOrgIdByEmpId(empId);
      orgId = StaticParam.getParentOrgIdsByOrgId(orgId);
      String sidelineorg = StaticParam.getSidelineorg(empId);
      String[] sidelineorgs = sidelineorg.split(",");
      String orgIdsString = "";
      for (int i = 0; i < sidelineorgs.length; i++) {
        if (!"".equals(sidelineorgs[i]))
          orgIdsString = String.valueOf(orgIdsString) + StaticParam.getParentOrgIdsByOrgId(sidelineorgs[i]); 
      } 
      String[] org = (String.valueOf(orgId) + orgIdsString).split(",");
      for (int j = 0; j < org.length; j++) {
        if (!"-1".equals(org[j]))
          con = String.valueOf(con) + "or po.readerId like '%*" + org[j] + "*%' "; 
      } 
      con = String.valueOf(con) + "or po.readerId like '%*-1*%' ";
      String[] groups = group.split(",");
      for (int k = 0; k < groups.length; k++)
        con = String.valueOf(con) + "or po.readerId like '%@" + groups[k] + "@%' "; 
      con = String.valueOf(con) + "or po.readerId like '%$" + empId + "$%' ";
    } 
    try {
      begin();
      String hql = "select po.categoryId,po.categoryName,po.categoryLevel,po.categorySort,po.parentId from com.js.oa.oacollect.po.OaCollectCategory po  where po.categoryId <>" + 
        id + " and (";
      if ("".equals(flag)) {
        hql = String.valueOf(hql) + " po.readerId=null or po.readerId='' ";
      } else {
        hql = String.valueOf(hql) + " 1=1 ";
      } 
      hql = String.valueOf(hql) + con + ") order by po.categorySort";
      list = this.session.createQuery(hql).list();
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
    } catch (Exception e) {
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
  
  public List getChildrenPO(String id) {
    List list = null;
    try {
      begin();
      String hql = "select po.categoryId from com.js.oa.oacollect.po.OaCollectCategory po where po.categorySort like '%$" + id + "$%' order by po.categorySort";
      list = this.session.createQuery(hql).list();
    } catch (Exception e) {
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
  
  public List getListByYourSQL(String sql) throws Exception {
    List listInfo = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      MsManageEJBBean bean = new MsManageEJBBean();
      listInfo = bean.getListByYourSQL(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return listInfo;
  }
  
  public Object[] searchByParentId(Long parentId, Integer sortCode, Long id) {
    Object[] c = { "0", "0" };
    Query query = null;
    List<E> list = null;
    try {
      begin();
      query = this.session.createQuery("SELECT po.categorySortCode FROM com.js.oa.oacollect.po.OaCollectCategory po WHERE po.parentId=" + 
          parentId + 
          " AND po.categoryId <>" + 
          id + " order by po.categorySortCode");
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
      Query query = session.createQuery("from com.js.oa.oacollect.po.OaCollectCategory po where po.categorySort like '%$" + parentId + "$%'");
      List<OaCollectCategory> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        OaCollectCategory po = list.get(i);
        String classSort = po.getCategorySort();
        classSort = classSort.replace(oldParentSort, newParentSort);
        po.setCategorySort(classSort);
        po.setCategoryLevel(Integer.valueOf((classSort.split("\\$")).length / 2));
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
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
  
  public String[] treeShow(List<Object[]> list, String userId) {
    StringBuffer schoolFile = new StringBuffer("");
    StringBuffer selfFile = new StringBuffer("");
    ManagerService managerBD = new ManagerService();
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = list.get(i);
      if (managerBD.hasRight(userId, "09*01*01")) {
        String mainNode = treeNode(obj[0].toString(), userId, "0");
        if (mainNode.length() >= 0) {
          schoolFile.append("tree.nodes[\"A" + obj[4] + "_" + "A" + obj[0] + "\"] = \"text:" + obj[1] + "; data:id=320;\";\n");
          schoolFile.append(mainNode);
        } 
      } 
      String subNode = treeNode(obj[0].toString(), userId, "1");
      if (subNode.length() >= 0) {
        selfFile.append("tree.nodes[\"B" + obj[4] + "_" + "B" + obj[0] + "\"] = \"text:" + obj[1] + "; data:id=320;\";\n");
        selfFile.append(subNode);
      } 
    } 
    String[] returnValue = { schoolFile.toString(), selfFile.toString() };
    return returnValue;
  }
  
  public String treeNode(String categoryId, String userId, String flag) {
    StringBuffer returnStr = new StringBuffer("");
    try {
      String sql = "select po.collectId,po.collectTitle,po.categoryId from com.js.oa.oacollect.po.OaCollect po,com.js.oa.oacollect.po.OaCollectEmp emp  where po.collectId=emp.collectId and po.categoryId=" + 
        categoryId + " and po.collectZl=1 and ( emp.empId=" + userId + " or po.createEmp= " + userId + ")";
      List<Object[]> tempList = getListByYourSQL(sql);
      if (tempList != null && tempList.size() > 0)
        for (int w = 0; w < tempList.size(); w++) {
          Object[] ob = tempList.get(w);
          String node = "A" + categoryId + "_AF" + ob[0].toString();
          String href = "/jsoa/OACollectFileAction.do?action=fileList&displayFlag=suoluetu&collectId=" + ob[0];
          if (flag.equals("1")) {
            node = "B" + categoryId + "_BF" + ob[0].toString();
            href = String.valueOf(href) + "&fromFlag=gerenwendang";
          } 
          returnStr.append("tree.nodes[\"" + node + "\"] = \"icon:design;text:" + ob[1] + ";method:OpenDiv('" + href + "');\"\n");
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnStr.toString();
  }
  
  public Long saveCategoryCopy(OaCollectCategory po, Map<String, String> idMap) {
    Long id = Long.valueOf(0L);
    try {
      begin();
      Long long_ = po.getCategoryId();
      po.setCategoryId(null);
      po.setCreatedDate(new Date());
      po.setParentId(
          Long.valueOf((po.getParentId().longValue() == -1L) ? -1L : ((idMap.get(po.getParentId()) == null) ? -1L : Long.valueOf(idMap.get(po.getParentId())).longValue())));
      id = (Long)this.session.save(po);
      String sortCode = po.getCategorySort();
      String[] sort = sortCode.split("\\$");
      for (int i = 1; i < sort.length; i += 2) {
        if (idMap.get(sort[i]) != null)
          sortCode = sortCode.replace("$" + sort[i] + "$", "$" + (String)idMap.get(sort[i]) + "$"); 
      } 
      sortCode = sortCode.replace("$" + long_ + "$", "$" + id + "$");
      po.setCategorySort(sortCode);
      this.session.saveOrUpdate(po);
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    return id;
  }
}
