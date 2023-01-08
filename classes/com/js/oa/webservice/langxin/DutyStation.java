package com.js.oa.webservice.langxin;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;

public class DutyStation {
  private String dataBaseType = SystemCommon.getDatabaseType();
  
  public String insertDuty(String duty, DataSourceBase base) throws Exception {
    if (duty != null && !"".equals(duty)) {
      boolean o = true;
      String sql = "SELECT duty_id FROM oa_duty WHERE dutyname='" + duty + "'";
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        o = false; 
      rs.close();
      if (o) {
        if (this.dataBaseType.equals("oracle")) {
          sql = "insert into oa_duty (duty_id,dutyname,domain_id,dutylevel,duty_describe,corpId,dutyno) values (hibernate_sequence.nextval,'" + 

            
            duty + "',0,999,'',0,'')";
        } else {
          sql = "insert into oa_duty (dutyname,domain_id,dutylevel,duty_describe,corpId,dutyno) values ('" + 

            
            duty + "',0,999,'',0,'')";
        } 
        System.out.println("插入职务：" + sql);
        base.executeUpdate(sql);
        IO2File.printFile("插入职务：" + sql, "朗新职务操作");
      } 
      return duty;
    } 
    return "";
  }
  
  public String insertStation(String station, DataSourceBase base) throws Exception {
    if (station != null && "".equals(station)) {
      String id = "";
      String sql = "SELECT id FROM st_station WHERE station_name='" + station + "'";
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        id = rs.getString(1); 
      rs.close();
      if (id.equals("")) {
        if (this.dataBaseType.equals("oracle")) {
          sql = "insert into st_station (id,station_name,domain_id,description,corpId,no ) values (hibernate_sequence.nextval,'" + 
            
            station + "',0,'" + station + "',0,'')";
        } else {
          sql = "insert into st_station (station_name,domain_id,description,corpId,no ) values ('" + 
            station + "',0,'" + station + "',0,'')";
        } 
        base.executeUpdate(sql);
        rs = base.executeQuery("SELECT id FROM st_station WHERE station_name='" + station + "'");
        if (rs.next())
          id = rs.getString(1); 
        rs.close();
        IO2File.printFile("插入岗位：" + sql, "朗新岗位操作");
      } 
      return id;
    } 
    return "-1";
  }
}
