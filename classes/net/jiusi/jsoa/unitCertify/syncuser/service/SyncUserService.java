package net.jiusi.jsoa.unitCertify.syncuser.service;

import com.js.system.bean.usermanager.UserEJBBean;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.jiusi.jsoa.service.SyncOrganizationEmp;
import net.jiusi.jsoa.service.dao.OrgDao;
import net.jiusi.jsoa.service.pojo.OrganizationPojo;
import net.jiusi.jsoa.unitCertify.syncuser.bean.SyncUserBean;
import net.jiusi.jsoa.unitCertify.util.CommonUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class SyncUserService {
  public String syncUserByHand(String orgName) {
    String re = "1";
    String ipPort = SystemCommon.getIpPortUnitCertify();
    String charset = "UTF-8";
    String url = "";
    try {
      url = "http://" + ipPort + "/GetCommonUserInfoByDeptNameAction.a?get_info_key=94a0dac6-d161-11e1-bdaf-7e872d49e8af" + 
        "&organization_name=" + URLEncoder.encode(orgName, charset);
      CommonUtil util = new CommonUtil();
      String xml = util.getStrByUrl(url);
      StringReader read = new StringReader(xml);
      InputSource source = new InputSource(read);
      SAXBuilder sb = new SAXBuilder();
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      List<Element> list = root.getChildren("deptuserinfo");
      System.out.println("共查询出：" + list.size() + "条数据。");
      if (list != null) {
        OrgDao orgDao = new OrgDao();
        SyncOrganizationEmp syncOrganizationEmp = new SyncOrganizationEmp();
        SyncUserBean bean = new SyncUserBean();
        OrganizationPojo orgPo = orgDao.findOrgByOrgSerial(String.valueOf(orgName) + "-初始部门");
        String re3 = "";
        if (orgPo == null) {
          syncOrganizationEmp.addOrganization(String.valueOf(orgName) + "-初始部门", String.valueOf(orgName) + "-初始部门", String.valueOf(orgName) + "-初始部门", "", "", "");
          orgPo = orgDao.findOrgByOrgSerial(String.valueOf(orgName) + "-初始部门");
        } 
        String sql = "";
        List<Object[]> tempList = null;
        String userAccoutsDelStr = "";
        EmployeeVO employeeVO = null;
        UserEJBBean userEJBBean = new UserEJBBean();
        for (int i = 0; i < list.size(); i++) {
          Element node = list.get(i);
          String userAccounts = node.getChildText("teacher-account");
          if ("".equals(userAccoutsDelStr)) {
            userAccoutsDelStr = "'" + userAccounts + "'";
          } else {
            userAccoutsDelStr = String.valueOf(userAccoutsDelStr) + ",'" + userAccounts + "'";
          } 
          if (userAccounts != null && !"".equals(userAccounts)) {
            sql = "select po.empId,po.empName from com.js.system.vo.usermanager.EmployeeVO po where 1=1  and po.userAccounts='" + 
              
              userAccounts + "'";
            tempList = bean.getListByYourSQL(sql);
            String userPassword = "111111";
            String empName = node.getChildText("teacher-name");
            String empSex = node.getChildText("sex");
            if ("".equals(empSex) || "男".equals(empSex)) {
              empSex = "0";
            } else {
              empSex = "1";
            } 
            String orgSerial = "";
            String iPContrl = "1";
            String ipContrlBeginTime = "2013-01-01 00:00:00";
            String ipContrlEndTime = "2050-01-01 00:00:00";
            String re2 = "";
            url = "http://" + ipPort + "/authInfoAction.do?becom_auth_username=" + userAccounts + "&userinfoparameter=" + 
              "user_name,user_type,name,email,birthday,sex,telephone,card_id,culture_grade,job_number," + 
              "study_number,town,link_address";
            String xmlUserInfo = util.getStrByUrl(url);
            StringReader readUserInfo = new StringReader(xmlUserInfo);
            InputSource sourceUserInfo = new InputSource(readUserInfo);
            SAXBuilder sbUserInfo = new SAXBuilder();
            Document docUserInfo = sbUserInfo.build(sourceUserInfo);
            Element rootUserInfo = docUserInfo.getRootElement();
            String user_name = rootUserInfo.getChildText("user_name");
            if (user_name != null && !"".equals(user_name)) {
              employeeVO = new EmployeeVO();
              employeeVO.setEmpName(rootUserInfo.getChildText("name"));
              if (rootUserInfo.getChildText("birthday") != null && !"".equals(rootUserInfo.getChildText("birthday"))) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if ("".equals(rootUserInfo.getChildText("birthday"))) {
                  employeeVO.setEmpBirth(format.parse(rootUserInfo.getChildText("birthday")));
                } else {
                  employeeVO.setEmpBirth(new Date());
                } 
              } 
              employeeVO.setEmpMobilePhone(rootUserInfo.getChildText("telephone"));
              employeeVO.setEmpHeight((short)0);
              employeeVO.setEmpWeight((short)0);
              employeeVO.setEmpAddress(rootUserInfo.getChildText("link_address"));
              employeeVO.setEmpIdCard(rootUserInfo.getChildText("card_id"));
              employeeVO.setEmpStatus((byte)0);
              employeeVO.setUserAccounts(rootUserInfo.getChildText("user_name"));
              employeeVO.setEmpLeaderId("");
              employeeVO.setEmpLeaderName("");
              employeeVO.setUserIsActive((byte)1);
              employeeVO.setUserIsDeleted((byte)0);
              employeeVO.setUserIsFormalUser(Integer.valueOf(1));
              employeeVO.setUserIsSuper((byte)1);
              employeeVO.setUserSuperBegin((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(ipContrlBeginTime));
              employeeVO.setUserSuperEnd((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(ipContrlEndTime));
              employeeVO.setBrowseRange("0");
              employeeVO.setBrowseRangeName("");
              employeeVO.setKeyValidate("0");
              employeeVO.setKeySerial("");
              employeeVO.setDomainId("0");
              employeeVO.setSkin("blue");
              employeeVO.setMailboxSize("100");
              employeeVO.setNetDiskSize("100");
              employeeVO.setSidelineOrg("");
              employeeVO.setSidelineOrgName("");
              employeeVO.setEmpDutyLevel("-1");
              employeeVO.setEmpEmail(rootUserInfo.getChildText("email"));
              employeeVO.setEmpSex(Byte.valueOf(empSex).byteValue());
              employeeVO.setUserPassword(userPassword);
            } 
            String[] orgId = { String.valueOf(orgPo.getOrgId()) };
            String[] rightId = new String[0];
            String[] rightScopeType = new String[0];
            String[] rightScopeScope = new String[0];
            String[] roleId = new String[0];
            if (user_name != null && !"".equals(user_name))
              if (tempList != null && tempList.size() > 0) {
                Object[] obj = tempList.get(0);
                re2 = bean.updateEmpInfo(Integer.valueOf(obj[0].toString()), employeeVO);
              } else {
                re2 = userEJBBean.add(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, "", roleId).toString();
              }  
          } 
        } 
        if (userAccoutsDelStr != null && !"".equals(userAccoutsDelStr)) {
          sql = "select po.empId,po.userAccounts from com.js.system.vo.usermanager.EmployeeVO po where po.userAccounts not in(" + 
            userAccoutsDelStr + ")";
          tempList = bean.getListByYourSQL(sql);
          if (tempList != null && tempList.size() > 0)
            for (int k = 0; k < tempList.size(); k++)
              Object[] arrayOfObject = tempList.get(k);  
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      re = "0";
    } 
    return re;
  }
}
