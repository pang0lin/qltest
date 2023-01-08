package com.js.oa.chinaLife.xingzheng;

import com.js.oa.chinaLife.tbUser.UserData;
import com.js.util.util.DataSourceBase;

public class TongBuUser {
  public void addUser(UserData user) {
    String sql = "insert into temp_XingZheng (UserCode,UserName,ComCode,DeptCode,LabCode,IVCode,UserCodeSAP,Duty,Rank,IdCardNo,EMail,Phone,BankCode,AccountCode,BankInfo,Province,City) values ('" + 
      
      user.getUserCode() + "','" + user.getUserName() + "','" + user.getComCode() + "','" + user.getDeptCode() + 
      "','" + user.getLabCode() + "','" + user.getIvCode() + "','" + user.getUserCodeSAP() + "','" + user.getDuty() + 
      "','" + user.getRank() + "','" + user.getIdCardNo() + "','" + user.geteMail() + "','" + user.getPhone() + "','" + 
      user.getBankCode() + "','" + user.getAccountCode() + "','" + user.getBankaInfo() + "','" + user.getProvince() + "','" + 
      user.getCity() + "')";
    opDataSource(sql);
  }
  
  public void updateUser(UserData user) {
    String sql = "update temp_XingZheng set UserCode='" + user.getUserCode() + "',UserName='" + user.getUserName() + "'," + 
      "ComCode='" + user.getComCode() + "',DeptCode='" + user.getDeptCode() + "',LabCode='" + user.getLabCode() + "'," + 
      "IVCode='" + user.getIvCode() + "',UserCodeSAP='" + user.getUserCodeSAP() + "',Duty='" + user.getDuty() + "'," + 
      "Rank='" + user.getRank() + "',IdCardNo='" + user.getIdCardNo() + "',EMail='" + user.geteMail() + "'," + 
      "Phone='" + user.getPhone() + "',BankCode='" + user.getBankCode() + "',AccountCode='" + user.getAccountCode() + "'," + 
      "BankInfo='" + user.getBankaInfo() + "',Province='" + user.getProvince() + "',City='" + user.getCity() + "' " + 
      "where UserCode='" + user.getUserCode() + "'";
    opDataSource(sql);
  }
  
  public void deleteUser(UserData user) {
    String sql = "delete from temp_XingZheng where UserCode='" + user.getUserCode() + "'";
    opDataSource(sql);
  }
  
  public int opDataSource(String sql) {
    int op = 0;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      base.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return op;
  }
}
