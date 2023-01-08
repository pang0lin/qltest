package com.js.oa.personalwork.netemp.bean;

import com.js.oa.personalwork.netemp.po.NetEmpPO;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.hibernate.HibernateBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class NetEmpEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void update(NetEmpPO netEmpPO) throws Exception {
    List<Long> list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po.id from NetEmpPO po where po.userId = " + netEmpPO.getUserId()).list();
      if (list.isEmpty()) {
        this.session.save(netEmpPO);
      } else {
        Long obj = list.get(0);
        netEmpPO.setId(obj.longValue());
        this.session.update(netEmpPO);
      } 
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
  
  public NetEmpPO load(long userId) throws Exception {
    NetEmpPO netEmpPO = new NetEmpPO();
    List<NetEmpPO> list = new ArrayList();
    begin();
    try {
      list = this.session.createQuery("select po from com.js.oa.personalwork.netemp.po.NetEmpPO po where po.userId = " + userId).list();
      if (!list.isEmpty()) {
        netEmpPO = list.get(0);
      } else {
        netEmpPO = null;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return netEmpPO;
  }
  
  public Map getAllRelationEmp(String userId) {
    Map<Object, Object> relationEmp = new HashMap<Object, Object>();
    try {
      begin();
      NetEmpPO netEmpPO = new NetEmpPO();
      List<NetEmpPO> list = this.session.createQuery("select po from com.js.oa.personalwork.netemp.po.NetEmpPO po where po.userId = " + userId).list();
      if (!list.isEmpty()) {
        netEmpPO = list.get(0);
        String superiorsId = netEmpPO.getSuperiorsId();
        String superiorsName = netEmpPO.getSuperiorsName();
        String underlingId = netEmpPO.getUnderlingId();
        String underlingName = netEmpPO.getUnderlingName();
        String netEmpId = netEmpPO.getNetEmpId();
        String netEmpName = netEmpPO.getNetEmpName();
        if (superiorsId != null && !"".equals(superiorsId) && !superiorsId.equals("null")) {
          superiorsName = superiorsName.substring(0, superiorsName.length());
          superiorsId = superiorsId.substring(1, superiorsId.length());
          String[] superiorsNames = superiorsName.split(",");
          String[] superiorsIds = superiorsId.split("\\$\\$");
          if (superiorsIds.length > 0) {
            String[][] obj = new String[superiorsIds.length][2];
            for (int i = 0; i < superiorsIds.length; i++) {
              obj[i][0] = superiorsIds[i];
              obj[i][1] = superiorsNames[i];
            } 
            relationEmp.put("supEmp", obj);
          } 
        } 
        if (underlingId != null && !"".equals(underlingId) && !underlingId.equals("null")) {
          underlingName = underlingName.substring(0, underlingName.length());
          underlingId = underlingId.substring(1, underlingId.length() - 1);
          String[] underlingNames = underlingName.split(",");
          String[] underlingIds = underlingId.split("\\$\\$");
          if (underlingIds.length > 0) {
            String[][] obj = new String[underlingIds.length][2];
            for (int i = 0; i < underlingIds.length; i++) {
              obj[i][0] = underlingIds[i];
              obj[i][1] = underlingNames[i];
            } 
            relationEmp.put("underEmp", obj);
          } 
        } 
        if (netEmpId != null && !"".equals(netEmpId) && !netEmpId.equals("null")) {
          netEmpName = netEmpName.substring(0, netEmpName.length());
          netEmpId = netEmpId.substring(1, netEmpId.length() - 1);
          String[] netEmpNames = netEmpName.split(",");
          String[] netEmpIds = netEmpId.split("\\$\\$");
          if (netEmpIds.length > 0) {
            String[][] obj = new String[netEmpIds.length][2];
            for (int i = 0; i < netEmpIds.length; i++) {
              obj[i][0] = netEmpIds[i];
              obj[i][1] = netEmpNames[i];
            } 
            relationEmp.put("netEmp", obj);
          } 
        } 
      } else {
        UserBD userBD = new UserBD();
        String superiorsId = netEmpPO.getSuperiorsId();
        String superiorsName = netEmpPO.getSuperiorsName();
        String underlingId = netEmpPO.getUnderlingId();
        String underlingName = netEmpPO.getUnderlingName();
        List emplist = new ArrayList();
        try {
          emplist = userBD.selectMyUnderling(userId);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (!list.isEmpty())
          for (int i = 0; i < list.size(); i++) {
            Object[] obj = (Object[])list.get(i);
            underlingName = String.valueOf(underlingName) + obj[1] + ",";
            underlingId = String.valueOf(underlingId) + "$" + obj[0] + "$";
          }  
        EmployeeVO employeeVO = new EmployeeVO();
        try {
          employeeVO = userBD.getEmployeeVO(Long.valueOf(userId));
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (employeeVO != null) {
          superiorsId = employeeVO.getEmpLeaderId();
          superiorsName = employeeVO.getEmpLeaderName();
        } 
        if (superiorsId != null && !"".equals(superiorsId) && !superiorsId.equals("null")) {
          if (superiorsName.indexOf(",") > 0)
            superiorsName = superiorsName.substring(0, superiorsName.length() - 1); 
          superiorsId = superiorsId.substring(1, superiorsId.length() - 1);
          String[] superiorsNames = superiorsName.split(",");
          String[] superiorsIds = superiorsId.split("\\$\\$");
          if (superiorsIds.length > 0) {
            String[][] obj = new String[superiorsIds.length][2];
            for (int i = 0; i < superiorsIds.length; i++) {
              obj[i][0] = superiorsIds[i];
              obj[i][1] = superiorsNames[i];
            } 
            relationEmp.put("supEmp", obj);
          } 
        } 
        if ("".equals(underlingId) || "null".equals(underlingId) || underlingId == null) {
          List<Object[]> underlist = new ArrayList();
          try {
            underlist = userBD.selectMyUnderling(userId);
          } catch (Exception e) {
            e.printStackTrace();
          } 
          if (!underlist.isEmpty()) {
            String[][] underEmp = new String[underlist.size()][2];
            for (int i = 0; i < underlist.size(); i++) {
              Object[] obj = underlist.get(i);
              underEmp[i][0] = (String)obj[0];
              underEmp[i][1] = (String)obj[1];
            } 
            relationEmp.put("underEmp", underEmp);
          } 
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return relationEmp;
  }
}
