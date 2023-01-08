package com.js.oa.personalwork.netaddress.bean;

import com.js.oa.personalwork.netaddress.po.AddressClassPO;
import com.js.oa.personalwork.netaddress.po.AddressPO;
import com.js.oa.personalwork.netaddress.po.AddressShowPO;
import com.js.oa.personalwork.netaddress.po.AddressUpdatePO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.BASE64;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class AddressEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  BASE64 bASE64 = new BASE64();
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void delBatch(String ids, String userId) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals("")) {
        ids = ids.substring(0, ids.length() - 1);
        String sql = "select po from com.js.oa.personalwork.netaddress.po.AddressPO po where po.id in (" + 
          ids + ")" + 
          " and po.createdEmpId<>" + userId + " and po.isShare=1";
        List<AddressPO> list = this.session.createQuery(sql).list();
        String shareIds = "-1";
        String netAddressId = "";
        String delUser = "";
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        if (list != null)
          for (int i = 0; i < list.size(); i++) {
            AddressPO po = list.get(i);
            netAddressId = String.valueOf(po.getId());
            shareIds = String.valueOf(shareIds) + "," + po.getId();
            delUser = po.getShareDelUserId();
            if (delUser == null)
              delUser = ""; 
            delUser = String.valueOf(delUser) + "*" + userId + "*";
            stmt.executeUpdate(
                "update oa_netaddress set sharedeluserid='" + 
                delUser + "' where netaddress_id=" + 
                netAddressId);
          }  
        sql = "delete from  oa_netaddress where netaddress_id in (" + ids + ") and netaddress_id not in(" + shareIds + ") and createdempid=" + userId;
        stmt.executeUpdate(sql);
        sql = "delete from  oa_netaddressshow where netaddress_id in (" + ids + ") and emp_id=" + userId;
        stmt.executeUpdate(sql);
        sql = "delete from OA_NETADDRESSUPDATE where NETADDRESS_ID in (" + ids + ") and EMP_ID=" + userId;
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delShare(String ids, String userId) throws Exception {
    try {
      begin();
      if (ids != null && !ids.equals("")) {
        AddressPO po = (AddressPO)this.session.load(AddressPO.class, new Long(ids));
        po.setShareDelUserId(String.valueOf(po.getShareDelUserId()) + "*" + userId + "*");
        this.session.update(po);
        this.session.delete("from com.js.oa.personalwork.netaddress.po.AddressShowPO po where po.netAddressId  = " + ids);
        this.session.flush();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public void delAll(String userId) throws Exception {
    try {
      begin();
      this.session.delete(
          "from com.js.oa.personalwork.netaddress.po.AddressPO po where po.createdEmpId = " + 
          userId);
      this.session.delete("from com.js.oa.personalwork.netaddress.po.AddressShowPO po where po.empId =" + 
          userId);
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
  
  public void add(AddressPO po, String userId, String userName, String myAddressClass, Byte isShare, String ids) throws Exception {
    begin();
    try {
      po.setAddressClass((AddressClassPO)this.session.load(AddressClassPO.class, 
            new Long(myAddressClass)));
      po.setCreatedEmpId(Long.parseLong(userId));
      po.setCreatedEmpName(userName);
      String username = "";
      String userpass = "";
      if (po.getUsername() != null && !"".equals(po.getUsername()))
        username = this.bASE64.BASE64Encoder(po.getUsername()); 
      if (po.getPassword() != null && !"".equals(po.getPassword()))
        userpass = this.bASE64.BASE64Encoder(po.getPassword()); 
      po.setUsername(username);
      po.setPassword(userpass);
      if (isShare.byteValue() == 1) {
        po.setShareToEmp(StringSplit.splitWith(ids, "$", "*@"));
        po.setShareToGroup(StringSplit.splitWith(ids, "@", "$*"));
        po.setShareToOrg(StringSplit.splitWith(ids, "*", "@$"));
      } 
      Long id = (Long)this.session.save(po);
      this.session.flush();
      if (po.getSsologin() == 0)
        setAccount(String.valueOf(id), userId, po.getUsername(), po.getPassword(), ""); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  public AddressPO load(String eidtId) throws Exception {
    AddressPO po = null;
    begin();
    try {
      po = (AddressPO)this.session.load(AddressPO.class, new Long(eidtId));
    } catch (Exception e) {
      this.transaction.rollback();
      throw e;
    } finally {}
    this.session.close();
    this.session = null;
    return po;
  }
  
  public void update(Byte isShare, String synopsis, String saveImg, Integer operate, Integer sso, String formaction, String formusername, String formuserpassword, Integer ssologin, String username, String password, String netAddressName, String netAddressUrl, String editId, String ids, String shareToName, String myAddress, String formelseparam, String oldpth, String userName, String userId, String domainId, String fieldValues, String isImmoField, int ordernumber) throws Exception {
    begin();
    try {
      AddressPO po = new AddressPO();
      String hql = "select po.id from com.js.oa.personalwork.netaddress.po.AddressUpdatePO po where po.empId=" + userId + " and po.netAddressId=" + editId;
      List isFind = this.session.createQuery(hql.toString()).list();
      po = (AddressPO)this.session.load(AddressPO.class, new Long(editId));
      if (!"".equals(shareToName) && shareToName != null)
        isShare = Byte.valueOf((byte)1); 
      po.setIsShare(isShare.byteValue());
      po.setNetAddressName(netAddressName);
      po.setNetAddressUrl(netAddressUrl);
      po.setSynopsis(synopsis);
      po.setSaveImg(saveImg);
      po.setOperate(operate.intValue());
      po.setSso(sso.intValue());
      po.setFormaction(formaction);
      po.setFormusername(formusername);
      po.setFormuserpassword(formuserpassword);
      po.setSsologin(ssologin.intValue());
      po.setFormelseparamType(isImmoField);
      po.setFormelseparamValue(fieldValues);
      po.setOrdernumber(ordernumber);
      if (username != null && !"".equals(username)) {
        po.setUsername(this.bASE64.BASE64Encoder(username));
      } else {
        po.setUsername("");
      } 
      if (password != null && !"".equals(password)) {
        po.setPassword(this.bASE64.BASE64Encoder(password));
      } else {
        po.setPassword("");
      } 
      if (formelseparam != null && !"".equals(formelseparam)) {
        po.setFormelseparam(formelseparam);
      } else {
        po.setFormelseparam("");
      } 
      po.setAddressClass((AddressClassPO)this.session.load(AddressClassPO.class, new Long(myAddress)));
      if (isShare.byteValue() == 1) {
        po.setShareToEmp(StringSplit.splitWith(ids, "$", "*@"));
        po.setShareToGroup(StringSplit.splitWith(ids, "@", "$*"));
        po.setShareToOrg(StringSplit.splitWith(ids, "*", "@$"));
        po.setShareToName(shareToName);
        po.setShareDelUserId("");
      } else {
        po.setShareToEmp("");
        po.setShareToGroup("");
        po.setShareToOrg("");
        po.setShareToName("");
      } 
      if (isFind != null) {
        AddressUpdatePO addressUpdatePO = new AddressUpdatePO();
        addressUpdatePO.setEmpId((new Long(userId)).longValue());
        addressUpdatePO.setNetAddressId((new Long(editId)).longValue());
        addressUpdatePO.setNetAddressURL(netAddressUrl);
        addressUpdatePO.setNetAddressName(netAddressName);
        addressUpdatePO.setDomainId(domainId);
        if (isFind.size() <= 0) {
          this.session.save(addressUpdatePO);
        } else {
          updateNetAddressURL(addressUpdatePO);
        } 
      } 
      String netAddressUrls = getMainNetAddressURL(editId, userId);
      if (netAddressUrls != null && !"".equals(netAddressUrls)) {
        po.setNetAddressUrl(netAddressUrls);
      } else {
        netAddressUrl = getNetAddressURL(editId);
        po.setNetAddressUrl(netAddressUrl);
      } 
      String empId = getCreatedEmpid(editId);
      if (!empId.equals(userId)) {
        netAddressUrl = getNetAddressURL(editId);
        po.setNetAddressUrl(netAddressUrl);
      } 
      this.session.update(po);
      List<AddressShowPO> list = this.session.createQuery("select po from com.js.oa.personalwork.netaddress.po.AddressShowPO po where po.netAddressId =" + 
          editId).list();
      if (list.size() > 0) {
        AddressShowPO spo = list.get(0);
        spo.setNetAddress(po.getNetAddressName());
        spo.setNetAddressURL(po.getNetAddressUrl());
      } 
      this.session.flush();
      if (po.getSsologin() == 0) {
        setAccount(String.valueOf(editId), String.valueOf(userId), username, password, "");
      } else {
        delAccount(String.valueOf(editId), String.valueOf(userId));
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
  }
  
  private List listAddressClasses(String userId) throws Exception {
    List list = null;
    begin();
    try {
      list = this.session.createQuery("select po.id,po.className from com.js.oa.personalwork.netaddress.po.AddressClassPO po ").list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    this.session.close();
    this.session = null;
    this.transaction = null;
    return list;
  }
  
  public List see(String userId) throws Exception {
    List list = new ArrayList();
    try {
      list = listAddressClasses(userId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    return list;
  }
  
  public String showDesktop(String userId, String isShow, String netAddressId) throws Exception {
    begin();
    String message = "";
    try {
      if ("0".equals(isShow)) {
        this.session.delete(" from com.js.oa.personalwork.netaddress.po.AddressShowPO po where po.empId =" + 
            userId + " and po.netAddressId = " + netAddressId);
        AddressPO po = (AddressPO)this.session.load(AddressPO.class, new Long(netAddressId));
        String deskShowUser = po.getDeskShowUser();
        if (po.getDeskShowUser() != null && deskShowUser.indexOf("*" + userId + "*") != -1) {
          po.setDeskShowUser(deskShowUser.replaceAll("\\*" + userId + "\\*", ""));
          this.session.update(po);
        } 
        message = "delete";
      } else {
        List list = this.session.createQuery("select po.id ,po.empId from com.js.oa.personalwork.netaddress.po.AddressShowPO po where po.empId =" + userId).list();
        if (list.size() >= 10) {
          message = "big";
        } else {
          AddressPO po = (AddressPO)this.session.load(AddressPO.class, new Long(netAddressId));
          AddressShowPO spo = new AddressShowPO();
          spo.setEmpId(Long.parseLong(userId));
          spo.setNetAddress(po.getNetAddressName());
          spo.setNetAddressId(Long.parseLong(netAddressId));
          spo.setNetAddressURL(po.getNetAddressUrl());
          spo.setDomainId(po.getDomainId());
          this.session.save(spo);
          if (po.getDeskShowUser() != null) {
            po.setDeskShowUser(String.valueOf(po.getDeskShowUser()) + "*" + userId + "*");
          } else {
            po.setDeskShowUser("*" + userId + "*");
          } 
          this.session.update(po);
          message = "add";
        } 
      } 
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      message = "false";
      throw new Exception(e.getMessage());
    } finally {}
    this.session.close();
    this.session = null;
    this.transaction = null;
    return message;
  }
  
  public List getBox(String userId) throws Exception {
    begin();
    List list = null;
    String hql = "from com.js.oa.personalwork.netaddress.po.AddressPO po where po.addressClass=0 and po.createdEmpId='" + userId + "'";
    try {
      list = this.session.createQuery(hql).list();
      this.session.close();
    } catch (Exception e) {
      this.session.close();
    } 
    return list;
  }
  
  public List getBox(String userId, String curOrgId, String orgIdString) {
    List list = null;
    String idstring = orgIdString.substring(0, orgIdString.indexOf("$" + curOrgId + "$"));
    idstring = idstring.substring(1, idstring.length() - 1);
    try {
      StringBuffer hql = new StringBuffer("from AddressPO po where po.addressClass=1 and (po.shareDelUserId not like '%*" + userId + "*%' or po.isShare=0  ");
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        hql.append("or po.shareDelUserId='' )");
      } else if (databaseType.indexOf("oracle") >= 0) {
        hql.append("or po.shareDelUserId is null )");
      } 
      begin();
      List<Long> groupList = this.session.createQuery("select g.groupId from GroupVO g where g.groupUserString like '%$" + userId + "$%' ").list();
      if (groupList != null && groupList.size() > 0) {
        hql.append(" and (");
        int i;
        for (i = 0; i < groupList.size(); i++) {
          Long groupId = groupList.get(i);
          hql.append(" po.shareToGroup like '%@" + groupId + "@%' or ");
        } 
        hql.append(" po.shareToOrg like '%*" + curOrgId + "*%' ");
        for (i = 0; i < (idstring.split("\\$")).length; i++)
          hql.append(" or po.shareToOrg like '%*" + idstring.split("\\$")[i] + "*%' "); 
        hql.append(" or po.shareToEmp like '%$" + userId + "$%' or po.createdEmpId=" + userId);
        hql.append(")");
      } else {
        hql.append(" and (po.shareToOrg like '%*" + curOrgId + "*%' ");
        for (int i = 0; i < (idstring.split("\\$")).length; i++)
          hql.append(" or po.shareToOrg like '%*" + idstring.split("\\$")[i] + "*%' "); 
        hql.append(" or po.shareToEmp like '%$" + userId + "$%' or po.createdEmpId=" + userId);
        hql.append(")");
      } 
      list = this.session.createQuery(hql.toString()).list();
      this.session.close();
      this.session = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getBox1(String userId, String curOrgId, String orgIdString, String channelId) {
    List list = null;
    String idstring = orgIdString.substring(0, orgIdString.indexOf("$" + curOrgId + "$"));
    if (idstring.length() > 1)
      idstring = idstring.substring(1, idstring.length() - 1); 
    try {
      StringBuffer hql = new StringBuffer("select po.id,po.netAddressName,po.saveImg,po.netAddressUrl,po.sso,po.formaction,po.formusername,po.formuserpassword,po.ssologin,po.username,po.password,po.synopsis,po.formelseparam  from AddressPO po where po.addressClass.id=" + channelId + " and (po.shareDelUserId not like '%*" + userId + "*%' or po.isShare=0  ");
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        hql.append("or po.shareDelUserId='' or po.shareDelUserId is null)");
      } else if (databaseType.indexOf("oracle") >= 0) {
        hql.append("or po.shareDelUserId is null )");
      } 
      begin();
      List<Long> groupList = this.session.createQuery("select g.groupId from GroupVO g where g.groupUserString like '%$" + userId + "$%' ").list();
      if (groupList != null && groupList.size() > 0) {
        hql.append(" and (");
        for (int i = 0; i < groupList.size(); i++) {
          Long groupId = groupList.get(i);
          hql.append(" po.shareToGroup like '%@" + groupId + "@%' or ");
        } 
        hql.append(" po.shareToOrg like '%*" + curOrgId + "*%' ");
        String[] idArr = idstring.split("\\$");
        for (int j = 0; j < idArr.length; j++) {
          if (!"".equals(idArr[j]))
            hql.append(" or po.shareToOrg like '%*" + idArr[j] + "*%' "); 
        } 
        hql.append(" or po.shareToEmp like '%$" + userId + "$%' or po.createdEmpId=" + userId);
        hql.append(")");
      } else {
        hql.append(" and (po.shareToOrg like '%*" + curOrgId + "*%' ");
        String[] idArr = idstring.split("\\$");
        for (int i = 0; i < idArr.length; i++) {
          if (!"".equals(idArr[i]))
            hql.append(" or po.shareToOrg like '%*" + idArr[i] + "*%' "); 
        } 
        hql.append(" or po.shareToEmp like '%$" + userId + "$%' or po.createdEmpId=" + userId);
        hql.append(")");
      } 
      hql.append(" order by po.ordernumber,po.id desc");
      list = this.session.createQuery(hql.toString()).list();
      this.session.close();
      this.session = null;
    } catch (Exception e) {
      e.printStackTrace();
      try {
        this.session.close();
      } catch (Exception err) {
        err.printStackTrace();
      } 
    } 
    return list;
  }
  
  public String setAccount(String id, String userId, String userName, String userPass, String elseparamvalue) throws Exception {
    Connection conn = null;
    BASE64 base = new BASE64();
    try {
      if (userName != null && !"".equals(userName)) {
        userName = base.BASE64Encoder(userName);
      } else {
        userName = "";
      } 
      if (userPass != null && !"".equals(userPass)) {
        userPass = base.BASE64Encoder(userPass);
      } else {
        userPass = "";
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      StringBuffer buffer = new StringBuffer(" where emp_id=");
      buffer.append(userId).append(" and address_id=").append(id);
      String where = buffer.toString();
      ResultSet rs = stmt.executeQuery("select id from oa_netaddresskey " + where);
      if (rs.next()) {
        rs.close();
        stmt.executeUpdate("update oa_netaddresskey set elseparamvalue='" + elseparamvalue + "',username='" + userName + "',userpass='" + userPass + "'" + where);
      } else {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          stmt.executeUpdate("insert into oa_netaddresskey(emp_id,address_id,username,userpass,elseparamvalue) values (" + userId + "," + id + ",'" + userName + "','" + userPass + "','" + elseparamvalue + "')");
        } else if (databaseType.indexOf("oracle") >= 0) {
          stmt.executeUpdate("insert into oa_netaddresskey(id,emp_id,address_id,username,userpass,elseparamvalue) values (HIBERNATE_SEQUENCE.nextval," + userId + "," + id + ",'" + userName + "','" + userPass + "','" + elseparamvalue + "')");
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    return null;
  }
  
  public String[] getAccount(String id, String userId) throws Exception {
    Connection conn = null;
    BASE64 base = new BASE64();
    String[] res = { "", "", "" };
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      StringBuffer buffer = new StringBuffer(" where emp_id=");
      buffer.append(userId).append(" and address_id=").append(id);
      String where = buffer.toString();
      ResultSet rs = stmt.executeQuery("select username,userpass,elseparamvalue from oa_netaddresskey " + where);
      if (rs.next()) {
        if (rs.getString(1) != null && !"".equals(rs.getString(1))) {
          res[0] = base.BASE64Decoder(rs.getString(1));
        } else {
          res[0] = "";
        } 
        if (rs.getString(2) != null && !"".equals(rs.getString(2))) {
          res[1] = base.BASE64Decoder(rs.getString(2));
        } else {
          res[1] = "";
        } 
        if (rs.getString(3) != null && !"".equals(rs.getString(3))) {
          res[2] = base.BASE64Decoder(rs.getString(3));
        } else {
          res[2] = "";
        } 
      } 
      ResultSet rs1 = stmt.executeQuery("select formelseparamtype,formelseparamvalue from OA_NETADDRESS where netaddress_id=" + id);
      if (rs1.next()) {
        String types = rs1.getString(1);
        String values = rs1.getString(2);
        if (types != null && !"".equals(types)) {
          String result = "";
          String[] res1 = res[2].split("#&#");
          String[] types1 = types.split("#&#");
          String[] values1 = values.split("#&#");
          int j = 0;
          for (int k = 0; k < types1.length; k++) {
            if ("true".equals(types1[k])) {
              result = String.valueOf(result) + "#&#" + values1[k];
            } else if (res[2] != null && !"".equals(res[2]) && res1.length > j) {
              result = String.valueOf(result) + "#&#" + res1[j++];
            } else {
              result = String.valueOf(result) + "#&#";
            } 
          } 
          if (result.startsWith("#&#"))
            result = result.substring(3); 
          res[2] = result;
        } 
      } 
      rs1.close();
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    return res;
  }
  
  public String delAccount(String id, String userId) throws Exception {
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("delete from oa_netaddresskey where address_id=" + id + " and emp_id=" + userId);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    return null;
  }
  
  public String getFormelseParam(String id) throws Exception {
    Connection conn = null;
    String formelseparam = "";
    String paraName = "";
    String paraType = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select formelseparam ,formelseparamtype from oa_netaddress where netaddress_id= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        paraName = rs.getString(1);
        paraType = rs.getString(2);
      } 
      String[] strs1 = paraName.split("#&#");
      String[] strs2 = paraType.split("#&#");
      if (paraName != null && !"".equals(paraName))
        for (int i = 0; i < strs1.length; i++) {
          if (!"true".equalsIgnoreCase(strs2[i]))
            formelseparam = String.valueOf(formelseparam) + "#&#" + strs1[i]; 
        }  
      if (formelseparam.startsWith("#&#"))
        formelseparam = formelseparam.substring(3); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(formelseparam) || formelseparam == null)
      formelseparam = ""; 
    return formelseparam;
  }
  
  public String getFormelseAllParam(String id) throws Exception {
    Connection conn = null;
    String formelseparam = "";
    String paraName = "";
    String paraType = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select formelseparam  from oa_netaddress where netaddress_id= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        formelseparam = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(formelseparam) || formelseparam == null)
      formelseparam = ""; 
    return formelseparam;
  }
  
  public String getElseParam(String id, String userId) throws Exception {
    Connection conn = null;
    String elseparam = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select elseparamvalue from oa_netaddresskey where emp_id=" + userId + " and address_id= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        elseparam = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(elseparam) || elseparam == null)
      elseparam = ""; 
    return elseparam;
  }
  
  public Map<String, String> getParam(String id, String userId) throws Exception {
    Connection conn = null;
    String elseparam = "";
    String usernameString = "";
    String passwordString = "";
    Map<String, String> map = new HashMap<String, String>();
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select username,userpass,elseparamvalue from oa_netaddresskey where emp_id=" + userId + " and address_id= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        usernameString = rs.getString("username");
        passwordString = rs.getString("userpass");
        elseparam = rs.getString("elseparamvalue");
      } 
      map.put("usernameString", usernameString);
      map.put("passwordString", passwordString);
      map.put("elseparam", elseparam);
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    return map;
  }
  
  public String getNetAddressURL(String id, String userId) throws Exception {
    Connection conn = null;
    String netaddresString = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select NETADDRESSURL from OA_NETADDRESSUPDATE where EMP_ID=" + userId + " and  NETADDRESS_ID= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        netaddresString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(netaddresString) || netaddresString == null)
      netaddresString = ""; 
    return netaddresString;
  }
  
  public String getMainNetAddressURL(String id, String userId) throws Exception {
    Connection conn = null;
    String netaddresString = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select NETADDRESSURL from OA_NETADDRESSUPDATE where emp_id=" + userId + " and   NETADDRESS_ID= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        netaddresString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(netaddresString) || netaddresString == null)
      netaddresString = ""; 
    return netaddresString;
  }
  
  public String getNetAddressURL(String id) throws Exception {
    Connection conn = null;
    String netaddresString = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select NETADDRESSURL from OA_NETADDRESS where   NETADDRESS_ID= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        netaddresString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(netaddresString) || netaddresString == null)
      netaddresString = ""; 
    return netaddresString;
  }
  
  public String getCreatedEmpid(String id) throws Exception {
    Connection conn = null;
    String CreatedEmpid = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select CREATEDEMPID from OA_NETADDRESS where   NETADDRESS_ID= " + id;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        CreatedEmpid = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
    if ("".equals(CreatedEmpid) || CreatedEmpid == null)
      CreatedEmpid = ""; 
    return CreatedEmpid;
  }
  
  public void updateNetAddressURL(AddressUpdatePO po) throws Exception {
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "update OA_NETADDRESSUPDATE set netAddressURL='" + po.getNetAddressURL() + "', netAddressName='" + po.getNetAddressName() + "'  where EMP_ID=" + po.getEmpId() + " and  NETADDRESS_ID= " + po.getNetAddressId();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception err) {
        err.printStackTrace();
      } 
      e.printStackTrace();
      throw e;
    } 
  }
}
