package com.js.oa.hr.kq.szgt.service;

import com.js.util.util.DataSourceBase;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class KqDataService {
  public String getData(String paraInfo) {
    StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?><kqdata>");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String period = getPeriod(paraInfo);
      if (!"".equals(period)) {
        xml.append("<period>").append(period).append("</period>");
        String sql = "select oe.guid,oo.guid,duty_sum,nosign_sum,shouldduty,signonduty,businesstrip";
        sql = String.valueOf(sql) + ",overtime,leaveinlieu,annualleave,sickleave";
        sql = String.valueOf(sql) + ",workinjury,marriageleave,maternityleave,funeralleave";
        sql = String.valueOf(sql) + ",unpaidsick,unpaidleave,stopwork,absenteeism";
        sql = String.valueOf(sql) + ",annualremain,annualsum,unpaidsicksum,unpaidleavesum ";
        sql = String.valueOf(sql) + "from skq_report kq left join org_employee oe on kq.emp_id=oe.emp_id left join org_organization oo on kq.org_id=oo.org_id where kq.data_lock=1 and kq.report_month=?";
        conn = (new DataSourceBase()).getDataSource().getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, period);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
          xml.append("<data>");
          xml.append("<emp_guid>").append(rs.getString(1)).append("</emp_guid>");
          xml.append("<org_guid>").append(rs.getString(2)).append("</org_guid>");
          xml.append("<dutysum>").append(rs.getString(3)).append("</dutysum>");
          xml.append("<nosignsum>").append(rs.getString(4)).append("</nosignsum>");
          xml.append("<shouldduty>").append(rs.getString(5)).append("</shouldduty>");
          xml.append("<signonduty>").append(rs.getString(6)).append("</signonduty>");
          xml.append("<businesstrip>").append(rs.getString(7)).append("</businesstrip>");
          xml.append("<overtime>").append(rs.getString(8)).append("</overtime>");
          xml.append("<leaveinlieu>").append(rs.getString(9)).append("</leaveinlieu>");
          xml.append("<annualleave>").append(rs.getString(10)).append("</annualleave>");
          xml.append("<sickleave>").append(rs.getString(11)).append("</sickleave>");
          xml.append("<workinjury>").append(rs.getString(12)).append("</workinjury>");
          xml.append("<marriageleave>").append(rs.getString(13)).append("</marriageleave>");
          xml.append("<maternityleave>").append(rs.getString(14)).append("</maternityleave>");
          xml.append("<funeralleave>").append(rs.getString(15)).append("</funeralleave>");
          xml.append("<unpaidsick>").append(rs.getString(16)).append("</unpaidsick>");
          xml.append("<unpaidleave>").append(rs.getString(17)).append("</unpaidleave>");
          xml.append("<stopwork>").append(rs.getString(18)).append("</stopwork>");
          xml.append("<absenteeism>").append(rs.getString(19)).append("</absenteeism>");
          xml.append("<annualremain>").append(rs.getString(20)).append("</annualremain>");
          xml.append("<annualsum>").append(rs.getString(21)).append("</annualsum>");
          xml.append("<unpaidsicksum>").append(rs.getString(22)).append("</unpaidsicksum>");
          xml.append("<unpaidleavesum>").append(rs.getString(23)).append("</unpaidleavesum>");
          xml.append("</data>");
        } 
        rs.close();
      } 
      xml.append("</kqdata>");
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return xml.toString();
  }
  
  private String getPeriod(String info) {
    String period = "190001";
    StringReader read = new StringReader(info);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    sb.setExpandEntities(false);
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      Element node = root.getChild("period");
      if (node != null)
        period = node.getText(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return period;
  }
}
