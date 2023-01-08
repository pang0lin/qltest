package com.js.oa.security.seamoon.service;

import com.js.oa.security.seamoon.bean.SeaMoonEJBBean;
import com.js.oa.security.seamoon.po.SecSeaMoon;
import java.util.List;
import seamoonotp.seamoonapi;

public class SeaMoonService {
  public String checkPasswordBySnDynKey(String sninfo, String sc_key) {
    seamoonapi sc = new seamoonapi();
    SecSeaMoon po = loadSeaMoon(sninfo);
    if (po == null)
      return "2"; 
    String snKey = po.getSnKey();
    String str = sc.checkpassword(snKey, sc_key);
    if (str.length() > 3) {
      po.setSnKey(str);
      updateSeaMoon(po);
      return "1";
    } 
    if (str.equals("-1"))
      return "3"; 
    if (str.equals("0"))
      return "4"; 
    return "0";
  }
  
  public String checkPasswordByUserAccountDynKey(String userAccount, String sc_key) {
    String sql = "SELECT s.sn,s.snKey FROM com.js.oa.security.seamoon.po.SecSeaMoon s,com.js.system.vo.usermanager.EmployeeVO o  WHERE s.userId=o.empId AND o.userAccounts='" + 
      userAccount + "' and o.userIsDeleted=0";
    String re = "0";
    try {
      List list = (new SeaMoonEJBBean()).getListByYourSQL(sql);
      if (list == null)
        return "2"; 
      String sn = ((Object[])list.get(0))[0].toString();
      SecSeaMoon po = loadSeaMoon(sn);
      String snKey = po.getSnKey();
      seamoonapi sc = new seamoonapi();
      String str = sc.checkpassword(snKey, sc_key);
      if (str.length() > 3) {
        po.setSnKey(str);
        updateSeaMoon(po);
        re = "1";
      } else if (str.equals("-1")) {
        re = "3";
      } else if (str.equals("0")) {
        re = "4";
      } else {
        re = "0";
      } 
    } catch (Exception e) {
      re = "0";
      e.printStackTrace();
    } 
    return re;
  }
  
  public String seaMoonSyn(String sn, String scKey) {
    String str = "";
    seamoonapi sc = new seamoonapi();
    SecSeaMoon po = loadSeaMoon(sn);
    if (po == null)
      return "2"; 
    str = sc.checkpassword(po.getSnKey(), scKey);
    if (str.length() > 3) {
      po.setSnKey(str);
      updateSeaMoon(po);
      return "1";
    } 
    if (str.equals("-1"))
      return "3"; 
    if (str.equals("0"))
      return "4"; 
    return "0";
  }
  
  public String addSeaMoon(SecSeaMoon po) {
    String id = null;
    try {
      id = (new SeaMoonEJBBean()).saveSeaMoon(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return id;
  }
  
  public String updateSeaMoonByUserManage(SecSeaMoon po) {
    String sql = "select po.sn,po.userId from com.js.oa.security.seamoon.po.SecSeaMoon po where po.sn='" + po.getSn() + "'";
    SeaMoonEJBBean seaMoonEJBBean = new SeaMoonEJBBean();
    String re = "1";
    try {
      List list = seaMoonEJBBean.getListByYourSQL(sql);
      if (list != null && list.size() > 0) {
        SecSeaMoon oldPo = loadSeaMoon(po.getSn());
        oldPo.setUserId(po.getUserId());
        oldPo.setUserName(po.getUserName());
        updateSeaMoon(oldPo);
      } else {
        return "2";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = "0";
    } 
    return re;
  }
  
  public boolean updateSeaMoon(SecSeaMoon po) {
    boolean re = false;
    try {
      re = (new SeaMoonEJBBean()).updateSeaMoon(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return re;
  }
  
  public SecSeaMoon loadSeaMoon(String sn) {
    SecSeaMoon seaMoon = null;
    try {
      seaMoon = (new SeaMoonEJBBean()).loadSeaMoon(sn);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return seaMoon;
  }
  
  public boolean deleteSeaMoon(String ids) throws Exception {
    boolean re = true;
    try {
      (new SeaMoonEJBBean()).deleteSeaMoon(ids);
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } 
    return re;
  }
  
  public boolean deleteSeaMoonByUserId(String userIds) {
    boolean re = true;
    try {
      (new SeaMoonEJBBean()).deleteSeaMoonByUserId(userIds);
    } catch (Exception e) {
      e.printStackTrace();
      re = false;
    } 
    return re;
  }
  
  public String getSeeMoonSNByUserId(String userId) {
    String sn = "";
    try {
      sn = (new SeaMoonEJBBean()).getSeeMoonSNByUserId(userId);
    } catch (Exception e) {
      e.printStackTrace();
      sn = "";
    } 
    return sn;
  }
  
  public String checkUserAndSN(String userId, String sn) {
    String re = "-1";
    try {
      re = (new SeaMoonEJBBean()).checkUserAndSN(userId, sn);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return re;
  }
  
  public String checkUserAndUkey(String userId, String sn) {
    String re = "-1";
    try {
      re = (new SeaMoonEJBBean()).checkUserAndSN(userId, sn);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return re;
  }
}
