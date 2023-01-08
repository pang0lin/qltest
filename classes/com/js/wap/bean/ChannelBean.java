package com.js.wap.bean;

import com.js.oa.info.infomanager.po.InformationPO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class ChannelBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Map getChannelList(String userId, String orgId, String orgIdString, String domainId, int beginIndex, int limited) {
    Map<Object, Object> queryMap = new HashMap<Object, Object>();
    int recordCount = 0;
    List channelList = null;
    List groupList = new ArrayList();
    try {
      begin();
      StringBuffer sql = new StringBuffer("select po from com.js.oa.info.infomanager.po.InformationPO po left join po.informationChannel icpo where icpo.channelType<>1");
      StringBuffer buffer = new StringBuffer();
      orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
      String[] orgIdArray = orgIdString.split("\\$\\$");
      groupList = this.session.createQuery("select po.groupId from com.js.system.vo.groupmanager.GroupVO po join po.employees emp where emp.empId=" + userId).list();
      sql.append(" and (");
      int i;
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          sql.append(" icpo.channelReaderOrg like '%*").append(
              orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        sql.append(" icpo.channelReaderGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      sql.append(" icpo.channelReader like '%$").append(userId).append("$%' ");
      sql.append(" or ((icpo.channelReader is null or icpo.channelReader='') and (icpo.channelReaderGroup is null or icpo.channelReaderGroup='') and (icpo.channelReaderOrg is null or icpo.channelReaderOrg='')))");
      sql.append(" and ((");
      for (i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          sql.append(" po.informationReaderOrg like '%*").append(orgIdArray[i]).append("*%' or "); 
      } 
      for (i = 0; i < groupList.size(); i++)
        sql.append(" po.informationReaderGroup like '%@").append(groupList.get(i)).append("@%' or "); 
      sql.append(" po.informationReader like '%$").append(userId).append("$%' ");
      sql.append(") or ((po.informationReader is null or po.informationReader='') and (po.informationReaderOrg is null or po.informationReaderOrg='') and (po.informationReaderGroup is null or po.informationReaderGroup='')))");
      sql.append(" and po.domainId=" + domainId);
      sql.append(" order by po.informationIssueTime desc");
      Query query = this.session.createQuery(sql.toString());
      query.setFirstResult(beginIndex);
      query.setMaxResults(limited);
      channelList = query.list();
      Query query_count = this.session.createQuery(sql.toString().replace("select po from", "select po.informationId from"));
      recordCount = query_count.list().size();
      queryMap.put("QUERY_LIST", channelList);
      queryMap.put("RECORD_COUNT", Integer.valueOf(recordCount));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        this.session.close();
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
    } 
    return queryMap;
  }
  
  public InformationPO getInfoPOByID(String infoID) throws Exception {
    InformationPO po = new InformationPO();
    begin();
    po = (InformationPO)this.session.get(InformationPO.class, Long.valueOf(Long.parseLong(infoID)));
    this.session.close();
    return po;
  }
}
