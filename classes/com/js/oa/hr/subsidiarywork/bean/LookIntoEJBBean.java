package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO;
import com.js.oa.hr.subsidiarywork.po.NetSurveyPO;
import com.js.util.hibernate.HibernateBase;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class LookIntoEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids) throws Exception {
    begin();
    try {
      if (ids != null && !ids.equals(""))
        this.session.delete(
            " from com.js.oa.hr.subsidiarywork.po.NetSurveyPO po where po.id in (" + 
            ids.substring(0, ids.length() - 1) + ")"); 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAll(String wherePara) throws Exception {
    begin();
    try {
      this.session.delete(
          "from com.js.oa.hr.subsidiarywork.po.NetSurveyPO po where " + wherePara);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void add(Object[] obj, String userId, String orgId) throws Exception {
    begin();
    NetSurveyPO po = new NetSurveyPO();
    try {
      po.setSurveyBeginTime((Date)obj[0]);
      po.setSurveyEndTime((Date)obj[1]);
      po.setSurveyContent(obj[2].toString());
      po.setCreatedEmp(Long.parseLong(userId));
      po.setCreatedOrg(Long.parseLong(orgId));
      po.setSurveyType(Byte.parseByte(obj[3].toString()));
      po.setSurveyRange(obj[4].toString());
      po.setSurveyStatus(Byte.parseByte(obj[5].toString()));
      po.setSurveyRangeName(obj[6].toString());
      po.setDomainId(obj[8].toString());
      this.session.save(po);
      po.setSurveyItems(getSurveyItems((String[])obj[7], obj[8].toString()));
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private Set getSurveyItems(String[] items, String domainId) throws Exception {
    Set<NetSurveyItemPO> set = new HashSet();
    try {
      NetSurveyItemPO itemPo = null;
      for (int i = 0; i < items.length; i++) {
        if (items[i] != null && !items[i].equals("")) {
          itemPo = new NetSurveyItemPO();
          itemPo.setItemContent(items[i]);
          itemPo.setDomainId(domainId);
          this.session.save(itemPo);
          set.add(itemPo);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return set;
  }
  
  public Map load(String editId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      NetSurveyPO po = (NetSurveyPO)this.session.load(NetSurveyPO.class, new Long(editId));
      result.put("netSurveyPO", po);
      List list = this.session.createQuery("select po from com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO po join po.survey poo where poo.id=" + po.getId() + " order by po.id").list();
      result.put("items", getItems(list));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  private String[][] getItems(List list) throws Exception {
    int size = list.size();
    String[][] items = new String[size][2];
    try {
      int i = 0;
      Iterator<NetSurveyItemPO> it = list.iterator();
      while (it.hasNext()) {
        NetSurveyItemPO po = it.next();
        items[i][0] = po.getItemContent();
        items[i][1] = String.valueOf(po.getId());
        i++;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return items;
  }
  
  public void update(String surveyBeginTime, String surveyEndTime, String surveyContent, String surveyRange, String surveyRangeName, String surveyStatus, String surveyType, String[] updateItems, String[] updateItemsIds, String[] newItems, String delItems, String editId) throws Exception {
    begin();
    try {
      NetSurveyPO po = (NetSurveyPO)this.session.load(NetSurveyPO.class, new Long(editId));
      po.setSurveyBeginTime(new Date(surveyBeginTime));
      po.setSurveyEndTime(new Date(surveyEndTime));
      po.setSurveyContent(surveyContent);
      po.setSurveyRange(surveyRange);
      po.setSurveyRangeName(surveyRangeName);
      po.setSurveyStatus(Byte.parseByte(surveyStatus));
      po.setSurveyType(Byte.parseByte(surveyType));
      updateItems(po, updateItems, updateItemsIds, newItems, delItems, po.getDomainId());
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private void updateItems(NetSurveyPO po, String[] updateItems, String[] updateItemsIds, String[] newItems, String delItems, String domainId) throws Exception {
    try {
      if (delItems != null && !"".equals(delItems) && delItems.indexOf(",") != -1)
        this.session.delete(
            " from com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO po where po.id in (" + 
            delItems.substring(0, delItems.length() - 1) + ")"); 
      if (updateItemsIds != null && !"".equals(updateItemsIds))
        for (int i = 0; i < updateItemsIds.length; i++) {
          if (updateItems[i] != null && !"".equals(updateItems[i])) {
            NetSurveyItemPO upItemPo = (NetSurveyItemPO)this.session.load(NetSurveyItemPO.class, new Long(updateItemsIds[i]));
            upItemPo.setItemContent(updateItems[i]);
          } else {
            this.session.delete(" from com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO po where po.id=" + updateItemsIds[i]);
          } 
        }  
      if (newItems != null)
        for (int i = 0; i < newItems.length; i++) {
          if (!"".equals(newItems[i])) {
            NetSurveyItemPO newItemPo = new NetSurveyItemPO();
            newItemPo.setSurvey(po);
            newItemPo.setItemContent(newItems[i]);
            newItemPo.setDomainId(domainId);
            this.session.save(newItemPo);
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String voteAdd(String surveyId, String itemIds, String curUserId, String domainId) throws Exception {
    String retString = "false";
    begin();
    try {
      List list = this.session.createQuery("select po.id from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO po where po.surveyId=" + surveyId + " and po.employeeId=" + curUserId).list();
    } catch (Exception e) {
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
  
  public List voteList(String surveyId, String itemIds) throws Exception {
    List<Object> retList = new ArrayList();
    if (surveyId == null || "".equals(surveyId))
      return retList; 
    if (itemIds == null || "".equals(itemIds))
      return retList; 
    begin();
    try {
      List<Object[]> listSurvey = this.session.createQuery("select po.surveyContent , po.surveyType from com.js.oa.hr.subsidiarywork.po.NetSurveyPO po where po.id=" + surveyId).list();
      if (listSurvey.size() > 0) {
        Object[] obj = listSurvey.get(0);
        retList.add(obj[0]);
        retList.add(obj[1]);
      } else {
        return retList;
      } 
      float countAll = 1.0F;
      List<String> listCountAll = this.session.createQuery("select count(po.id) from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO po where po.surveyId=" + surveyId).list();
      if (listCountAll.size() > 0)
        countAll = Integer.parseInt(listCountAll.get(0)); 
      if (countAll == 0.0F) {
        retList.add("0");
        List listZero = this.session.createQuery("select po.itemContent from com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO po where po.survey.id=" + surveyId + " order by po.id").list();
        for (int j = 0; j < listZero.size(); j++) {
          retList.add(listZero.get(j));
          retList.add("0");
          retList.add("0");
        } 
        retList.add("0");
        return retList;
      } 
      retList.add((new StringBuilder(String.valueOf((new Float(countAll)).intValue()))).toString());
      String[] items = itemIds.split(",");
      for (int i = 0; i < items.length && items[i] != null && !"".equals(items[i]); i++) {
        List listItem = this.session.createQuery("select po.itemContent from com.js.oa.hr.subsidiarywork.po.NetSurveyItemPO po where po.id=" + items[i]).list();
        if (listItem.size() > 0) {
          retList.add(listItem.get(0));
        } else {
          retList.add("调查项不详");
        } 
        float countItem = 0.0F;
        List<String> listCount = this.session.createQuery("select count(po.id) from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO po where po.surveyId=" + surveyId + " and po.item.id=" + items[i]).list();
        if (listCount.size() > 0)
          countItem = Integer.parseInt(listCount.get(0)); 
        retList.add((new StringBuilder(String.valueOf(round(100.0F * countItem / countAll, 2)))).toString());
        retList.add((new StringBuilder(String.valueOf((new Float(countItem)).intValue()))).toString());
      } 
      List listEmp = this.session.createQuery("select count(distinct po.employeeId) from com.js.oa.hr.subsidiarywork.po.NetSurveyVotePO po where po.surveyId=" + surveyId).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    this.session.close();
    return retList;
  }
  
  private float round(float v, int scale) {
    if (scale < 0)
      return 0.0F; 
    BigDecimal b = new BigDecimal(Float.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, 4).floatValue();
  }
}
