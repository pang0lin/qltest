package com.js.oa.haier.service;

import com.js.oa.haier.bean.ERPStackEJBBean;
import com.js.oa.haier.po.ERPStockPO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import com.js.util.util.DataSourceBase;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ERPStackService {
  ERPStackEJBBean eRPStackEJBBean = new ERPStackEJBBean();
  
  public void addDataAndSendEmail2() {}
  
  public void addDataAndSendEmail() {
    try {
      this.eRPStackEJBBean.deleteERPStack();
      System.out.println("开始更新数据到OA！");
      long start = System.currentTimeMillis();
      List<ERPStockPO> erpStack = getHaierERPStack();
      List<ERPStockPO> newStack = new ArrayList<ERPStockPO>();
      List<String> thFromOa = this.eRPStackEJBBean.getThFromOa();
      for (ERPStockPO erpStockPO : erpStack) {
        String erPth = erpStockPO.getERPth();
        for (String string : thFromOa) {
          if (erPth.contains(string))
            newStack.add(erpStockPO); 
        } 
      } 
      this.eRPStackEJBBean.saveERPStack(newStack);
      this.eRPStackEJBBean.deleteData();
      List<String[]> list = this.eRPStackEJBBean.getDataFromHaier("SELECT PRD_NO,name FROM prdt WHERE knd='2'", 2);
      this.eRPStackEJBBean.saveDataToOa(list, "insert into HAIER_SPECIALMODELS（machinetype,machinetypename） values(?,?)", 2);
      List<String[]> prdt = this.eRPStackEJBBean.getDataFromHaier("SELECT PRDT.PRD_NO 图号,PRDT.NAME 名称,PRDT.NOUSE_DD 停用日期 FROM PRDT", 3);
      this.eRPStackEJBBean.saveDataToOa(prdt, "insert into HAIER_PRDT（PRD_NO,NAME,NOUSE_DD） values(?,?,?)", 3);
      List<String[]> zcno = this.eRPStackEJBBean.getDataFromHaier("SELECT ZC_NO.ZC_NO 制程代号,ZC_NO.NAME 名称,ZC_NO.STOP_ID 停用否 FROM ZC_NO WHERE ZC_NO.STOP_ID IS NULL", 3);
      this.eRPStackEJBBean.saveDataToOa(zcno, "insert into HAIER_ZC_NO（ZC_NO,NAME,STOP_ID） values(?,?,?)", 3);
      List<String[]> cust = this.eRPStackEJBBean.getDataFromHaier("SELECT CUST.CUS_NO 客户编码,CUST.NAME 客户名称,CUST.OBJ_ID 客户类别 FROM CUST where (CUST.OBJ_ID=2 or CUST.OBJ_ID=3) and CUST.END_DD IS NULL", 3);
      this.eRPStackEJBBean.saveDataToOa(cust, "insert into HAIER_CUST（CUS_NO,NAME,OBJ_ID） values(?,?,?)", 3);
      List<String[]> indx = this.eRPStackEJBBean.getDataFromHaier("SELECT INDX.IDX_NO,INDX.NAME FROM INDX", 2);
      this.eRPStackEJBBean.saveDataToOa(indx, "insert into HAIER_INDX（IDX_NO,NAME） values(?,?)", 2);
      List<String[]> salm = this.eRPStackEJBBean.getDataFromHaier("SELECT SALM.SAL_NO,SALM.NAME FROM SALM WHERE SALM.DUT_OT_D IS NULL", 2);
      this.eRPStackEJBBean.saveDataToOa(salm, "insert into HAIER_SALM（SAL_NO,NAME） values(?,?)", 2);
      List<String[]> mywh = this.eRPStackEJBBean.getDataFromHaier("SELECT MY_WH.WH,MY_WH.NAME,dep FROM MY_WH where MY_WH.NAME not like '%停用%' and MY_WH.NAME not like '%作废%' and MY_WH.NAME !=''", 3);
      this.eRPStackEJBBean.saveDataToOa(mywh, "insert into HAIER_MY_WH（WH,NAME,dep） values(?,?,?)", 3);
      long end = System.currentTimeMillis();
      System.out.println("耗时：" + (end - start) + "毫秒");
      System.out.println("数据更新到OA完成！");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public List<ERPStockPO> getHaierERPStack() {
    List<ERPStockPO> listStack = new ArrayList<ERPStockPO>();
    CallableStatement csmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource("jdbc/haiererp").getConnection();
      csmt = conn.prepareCall("{call shy_oa_eo_stock}");
      rs = csmt.executeQuery();
      while (rs.next()) {
        ERPStockPO po = new ERPStockPO();
        po.setERPth(rs.getString(1));
        po.setERPMC(rs.getString(2));
        po.setZCKKC(Long.valueOf(rs.getLong(3)));
        po.setSHKC(Long.valueOf(rs.getLong(4)));
        po.setWFL(Long.valueOf(rs.getLong(5)));
        listStack.add(po);
      } 
      rs.close();
      csmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e2) {
          e2.printStackTrace();
        }  
      if (csmt != null)
        try {
          csmt.close();
        } catch (SQLException e3) {
          e3.printStackTrace();
        }  
    } 
    return listStack;
  }
  
  private void sendEmail(EmployeeVO employee) {
    try {
      boolean sendEmailFlag = false;
      List<ERPStockPO> rpStack = this.eRPStackEJBBean.getOAERPStack();
      StringBuffer sbf = new StringBuffer();
      for (int i = 0; i < rpStack.size(); i++) {
        ERPStockPO erp = rpStack.get(i);
        Long zckkc = erp.getZCKKC();
        Long shkc = erp.getSHKC();
        Long wfl = erp.getWFL();
        if (zckkc.longValue() <= 0L || shkc.longValue() <= 0L || wfl.longValue() <= 0L) {
          sendEmailFlag = true;
          sbf.append("主材库库存：" + erp.getERPth() + "售后库存:" + erp.getZCKKC() + ",未发量：" + erp.getWFL());
          sbf.append("\r\n");
        } 
      } 
      if (sendEmailFlag) {
        Mail wm = new Mail();
        wm.setSendTo(employee.getEmpEmail());
        wm.setSubjectTitle("");
        wm.setBoby(sbf.toString());
        MailSender.send(wm, MailConfig.getEmailSMTP(), MailConfig.getEmailCount(), MailConfig.getEmailPWD(), MailConfig.getEmailPort(), MailConfig.getEncryptionType());
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
