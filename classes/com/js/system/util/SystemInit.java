package com.js.system.util;

import com.js.net.NetReader;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.Encrypt;
import com.js.util.util.MD5;
import com.js.util.util.TextFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SystemInit {
  public boolean initData() {
    Connection conn = null;
    try {
      SystemCommon.init();
      NetReader nr = new NetReader();
      String[] curMac = nr.getmac().split(",");
      String[] sysInfo = getSysInfo();
      String usernum = sysInfo[1];
      String onlineUser = sysInfo[2];
      String enddate = sysInfo[3];
      String mobileusernum = sysInfo[4];
      String module = sysInfo[5];
      if ("-1".equals(sysInfo[0])) {
        System.out.println("您的软件未经授权认证，请与供应商联系！");
        System.exit(0);
      } else {
        boolean licOK = false;
        for (int j = 0; j < curMac.length; j++) {
          if (!"".equals(curMac[j]) && curMac[j].length() > 10 && 
            sysInfo[0].indexOf(curMac[j]) >= 0) {
            licOK = true;
            break;
          } 
        } 
        if (!licOK) {
          usernum = "1";
          onlineUser = "1";
          enddate = "1970-01-01";
          TextFile tf = new TextFile();
          String curPath = System.getProperty("user.dir");
          File licFile = new File(String.valueOf(curPath) + "/ithink.lic");
          File newFile = new File(String.valueOf(curPath) + "/ithink.license");
          tf.writeFile(newFile, String.valueOf(curMac[0]) + tf.readFile(licFile));
        } 
      } 
      if ("-1".equals(usernum)) {
        usernum = onlineUser;
        SystemCommon.setLicType("1");
      } else {
        SystemCommon.setLicType("0");
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String dataType = SystemCommon.getDatabaseType();
      SystemCommon.setModules(module);
      if (module.indexOf(",extendmenu,") >= 0) {
        SystemCommon.setExtendMenu("1");
      } else {
        SystemCommon.setExtendMenu("0");
      } 
      if (module.indexOf(",multidepart,") >= 0) {
        SystemCommon.setMultiDepart(1);
      } else {
        SystemCommon.setMultiDepart(0);
      } 
      if (module.indexOf(",qiyongddapp,") > 0)
        SystemCommon.setUseDingDing("1"); 
      if (module.indexOf(",hr,") >= 0 || module.indexOf(",exam,") >= 0 || module.indexOf(",salaryquery,") >= 0 || module.indexOf(",checkon,") >= 0 || 
        module.indexOf(",hr-organization,") >= 0 || module.indexOf(",hr-myinfo,") >= 0 || module.indexOf(",hr-underling,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='personnelManagement'");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='personnelManagement'");
      } 
      if (module.indexOf(",routine,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='officemanager'");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='officemanager'");
      } 
      if (module.indexOf(",doc,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='documentmanager'");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='documentmanager'");
      } 
      if (module.indexOf(",scheme,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='workmanager'");
        if (module.indexOf(",calendar,") >= 0) {
          stmt.executeUpdate("update menu_sys set rightURL='/jsoa/eventAction.do?action=getEventByDay' where menucode='workmanager'");
          stmt.executeUpdate("update menu_short set menu_type=0 where menu_name='新建日程'");
        } else {
          stmt.executeUpdate("update menu_short set menu_type=-1 where menu_name='新建日程'");
          if (module.indexOf(",meeting,") >= 0)
            stmt.executeUpdate("update menu_sys set rightURL='/jsoa/BoardRoomAction.do?action=meetingInformView&type=day&initsort=2' where menucode='workmanager'"); 
        } 
        if (module.indexOf(",project,") >= 0) {
          stmt.executeUpdate("update menu_short set menu_type=0 where menu_name='新建项目'");
        } else {
          stmt.executeUpdate("update menu_short set menu_type=-1 where menu_name='新建项目'");
        } 
        if (module.indexOf(",task,") >= 0) {
          stmt.executeUpdate("update menu_short set menu_type=0 where menu_name='我的任务'");
        } else {
          stmt.executeUpdate("update menu_short set menu_type=-1 where menu_name='我的任务'");
        } 
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='workmanager'");
        stmt.executeUpdate("update menu_short set menu_type=-1 where menu_name='新建项目'");
        stmt.executeUpdate("update menu_short set menu_type=-1 where menu_name='我的任务'");
      } 
      if (module.indexOf(",webmail,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='new_press'");
        stmt.executeUpdate("update menu_short set menu_type=0 where menu_name='新建邮件'");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='new_press'");
        stmt.executeUpdate("update menu_short set menu_type=-1 where menu_name='新建邮件'");
      } 
      if (module.indexOf(",datacollection,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='data_collect'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='09*01*01'");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='data_collect'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='09*01*01'");
      } 
      if (module.indexOf(",salaryquery,") >= 0) {
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*55*10'");
      } else {
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*55*10'");
      } 
      if (module.indexOf(",checkon,") >= 0) {
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*55*09'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*55*11'");
      } else {
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*55*09'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*55*11'");
      } 
      if (module.indexOf(",jsarchives,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='officemanager_dossier'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*23*01'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*23*02'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*23*03'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='07*23*04'");
        SystemCommon.setUseArchives("1");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='officemanager_dossier'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*23*01'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*23*02'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*23*03'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='07*23*04'");
        SystemCommon.setUseArchives("0");
      } 
      if (module.indexOf(",reportmanager,") >= 0) {
        stmt.executeUpdate("update menu_sys set menulevel=0 where menucode='grid_report'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='09*09*01'");
        stmt.executeUpdate("update org_right set right_status=1 where rightcode='09*09*02'");
      } else {
        stmt.executeUpdate("update menu_sys set menulevel=-1 where menucode='grid_report'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='09*09*01'");
        stmt.executeUpdate("update org_right set right_status=0 where rightcode='09*09*02'");
      } 
      if (module.indexOf(",handwrite,") < 0)
        (new SysSetupReader()).setServerOptionsHandWrite(); 
      if (module.indexOf(",isignature,") < 0)
        (new SysSetupReader()).setServerOptionsSignature(); 
      StaticParam sp = new StaticParam();
      try {
        if (dataType.indexOf("mysql") >= 0) {
          stmt.executeUpdate("alter table org_domain add mobile_user bigint default 0");
        } else {
          stmt.executeUpdate("alter table org_domain add mobile_user number(20) default 0");
        } 
      } catch (Exception exception) {}
      SystemCommon.setMobileLogonNum(mobileusernum);
      SystemCommon.setWeixinUserNum(Integer.parseInt(mobileusernum));
      if ("0".equals(mobileusernum))
        stmt.executeUpdate("update org_employee set mailpost='0'"); 
      if (dataType.indexOf("mysql") >= 0) {
        stmt.executeUpdate("update org_domain set domain_usernum=" + usernum + ",mobile_user=" + mobileusernum + ",domain_enddate='" + enddate + " 23:59:59'");
      } else {
        stmt.executeUpdate("update org_domain set domain_usernum=" + usernum + ",domain_enddate=JSDB.FN_STRTODATE('" + enddate + " 23:59:59','L')");
      } 
      int num = 0, i = 0;
      String FN_LEN = "length";
      if (dataType.indexOf("sqlserver") >= 0)
        FN_LEN = "len"; 
      ResultSet rs = stmt.executeQuery("select count(*) from org_employee where " + FN_LEN + "(userpassword)<33");
      if (rs.next())
        num = rs.getInt(1); 
      rs.close();
      String[][] userPass = new String[num][3];
      String password = "";
      int numTemp = 0;
      rs = stmt.executeQuery("select count(*) from org_employee where " + FN_LEN + "(userpassword)<33");
      if (rs.next())
        numTemp = rs.getInt(1); 
      rs.close();
      if (numTemp > 0) {
        if (dataType.indexOf("mysql") >= 0) {
          stmt.executeUpdate("alter table org_employee modify userpassword varchar(36)");
        } else {
          stmt.executeUpdate("alter table org_employee modify userpassword varchar2(36)");
        } 
        rs = stmt.executeQuery("select emp_id,userpassword,empposition from org_employee where " + FN_LEN + "(userpassword)<33 order by emp_id");
        while (rs.next()) {
          String tempId = rs.getString(1);
          String tempPass = rs.getString(2);
          String tempPosition = rs.getString(3);
          if (tempPass == null || "".equals(tempPass) || "null".equals(tempPass))
            tempPass = ""; 
          userPass[i][0] = tempId;
          userPass[i][1] = tempPass;
          userPass[i][2] = tempPosition;
          i++;
        } 
        rs.close();
        MD5 md5 = new MD5();
        PreparedStatement pstmt = conn.prepareStatement("update org_employee set userpassword=? where emp_id=?");
        for (i = 0; i < userPass.length; i++) {
          if ("".equals(userPass[i][1])) {
            pstmt.setString(1, "");
          } else {
            pstmt.setString(1, md5.getNewMD5(userPass[i][1]));
          } 
          pstmt.setString(2, userPass[i][0]);
          pstmt.executeUpdate();
        } 
        pstmt.close();
        Map<Object, Object> positionMap = new HashMap<Object, Object>();
        rs = stmt.executeQuery("select station_name,id from st_station order by id desc");
        while (rs.next())
          positionMap.put(rs.getString(1), rs.getString(2)); 
        pstmt = conn.prepareStatement("update org_employee set emppositionid=? where emp_id=?");
        for (i = 0; i < userPass.length; i++) {
          if (userPass[i][2] != null && !"".equals(userPass[i][2]) && !"null".equals(userPass[i][2]) && positionMap.get(userPass[i][2]) != null) {
            pstmt.setLong(1, Long.valueOf(positionMap.get(userPass[i][2]).toString()).longValue());
            pstmt.setString(2, userPass[i][0]);
            pstmt.executeUpdate();
          } 
        } 
        pstmt.close();
      } 
      if (dataType.indexOf("oracle") >= 0) {
        long oaSeqValue = 0L, seqCurrent = 1L;
        rs = stmt.executeQuery("select seq_seq from oa_seq");
        if (rs.next())
          oaSeqValue = rs.getLong(1); 
        rs.close();
        rs = stmt.executeQuery("select hibernate_sequence.nextval from dual");
        if (rs.next())
          seqCurrent = rs.getLong(1); 
        rs.close();
        if (oaSeqValue > seqCurrent) {
          long scan = oaSeqValue - seqCurrent;
          stmt.executeUpdate("alter sequence HIBERNATE_SEQUENCE increment by " + scan + " nocache");
          stmt.executeQuery("select HIBERNATE_SEQUENCE.nextval from dual");
          stmt.executeUpdate("alter sequence HIBERNATE_SEQUENCE increment by 1 cache 20");
        } 
      } 
      stmt.close();
      conn.close();
    } catch (SQLException ex) {
      System.out.println("数据源初始化错误,请重新启动系统!");
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("您使用的软件未经授权认证,请与供应商联系!");
      System.exit(0);
    } 
    return false;
  }
  
  private String[] getSysInfo() {
    String address = "", maxUserNum = "1", maxOnlineUser = "1", overdate = "1970-01-01", mobileusernum = "0", module = "";
    try {
      TextFile tf = new TextFile();
      String curPath = System.getProperty("user.dir");
      File licFile = new File(String.valueOf(curPath) + "/ithink.lic");
      String mac = "";
      mac = tf.readFile(licFile);
      mac = mac.substring(0, mac.length() - 12);
      int macCharLen = mac.length();
      int charLine = macCharLen / 12;
      int mod = charLine % 12;
      if (mod > 0)
        charLine++; 
      String[] charArr = new String[charLine];
      for (int i = 0; i < charArr.length; i++) {
        if (i == charLine - 1) {
          charArr[i] = mac.substring(i * 12);
        } else {
          charArr[i] = mac.substring(i * 12, (i + 1) * 12);
          charArr[i] = charArr[i].substring(0, 6);
        } 
      } 
      StringBuffer mmac = new StringBuffer();
      for (int j = 0; j < charArr.length; j++)
        mmac.append(charArr[j]); 
      Encrypt crypt = new Encrypt();
      mac = crypt.BASE64Decoder(mmac.toString());
      int macLen = 32;
      if (mac.indexOf("jiusifirstsplit") > 0) {
        macLen = Integer.parseInt(mac.substring(0, 20));
        mac = mac.substring(35);
      } 
      int preLen = Integer.parseInt(mac.substring(0, 2));
      mac = mac.substring(preLen + 3);
      int corpLen = Integer.parseInt(mac.substring(0, 5));
      mac = mac.substring(corpLen + 6);
      address = mac.substring(0, macLen);
      mac = mac.substring(macLen);
      int otherLen = Integer.parseInt(mac.substring(0, 5));
      String other = mac.substring(6, 6 + otherLen);
      other = crypt.deCoderCn(other);
      maxUserNum = other.substring(
          other.indexOf("maxusernum=") + 11, other
          .indexOf("maxonlineuser="));
      maxOnlineUser = other
        .substring(other.indexOf("maxonlineuser=") + 14, other
          .indexOf("overdate="));
      if (other.indexOf("mobileusernum=") > 0) {
        overdate = other.substring(other.indexOf("overdate=") + 9, 
            other.indexOf("mobileusernum="));
        mobileusernum = other.substring(other.indexOf("mobileusernum=") + 14, 
            other.indexOf("ithinkend==="));
      } else {
        overdate = other.substring(other.indexOf("overdate=") + 9, 
            other.indexOf("ithinkend==="));
      } 
      if (other.indexOf("ithinkmodulestart[") > 0)
        module = other.substring(other.indexOf("ithinkmodulestart[") + 18, other.indexOf("]ithinkmoduleend")); 
    } catch (FileNotFoundException ex) {
      address = "-1";
    } catch (Exception ex) {
      address = "0";
    } 
    String[] sysInfo = new String[6];
    sysInfo[0] = address;
    sysInfo[1] = maxUserNum;
    sysInfo[2] = maxOnlineUser;
    sysInfo[3] = overdate;
    sysInfo[4] = mobileusernum;
    sysInfo[5] = module;
    return sysInfo;
  }
}
