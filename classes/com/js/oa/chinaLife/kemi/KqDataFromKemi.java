package com.js.oa.chinaLife.kemi;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.xfire.client.Client;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class KqDataFromKemi {
  private Map<String, String> map = null;
  
  public void getKqData(String startDate, String endDate, int num) {
    if (this.map == null)
      this.map = KemiConfig.getKemiInfo(); 
    int allItemNum = 0;
    int onceItemNum = 100;
    int curPage = 1;
    try {
      do {
        Client c = new Client(new URL(this.map.get("url")));
        Object[] paras = { Integer.valueOf(1), "att_atdrec", "", startDate, endDate, "", Integer.valueOf(onceItemNum), Integer.valueOf(curPage), Integer.valueOf(0), "" };
        Object[] results = c.invoke(this.map.get("method"), paras);
        allItemNum = Integer.valueOf((String)results[1]).intValue();
        IO2File.printFile("返回结果：" + results[0], "kemiKq.txt", 3);
        IO2File.printFile("考勤数据条数：" + results[1], "kemiKq.txt", 3);
        IO2File.printFile("考勤数据：" + results[2], "kemiKq.txt", 3);
        tongbuKq(readXML((String)results[2]), num);
        ++curPage;
      } while ((curPage - 1) * onceItemNum <= allItemNum);
    } catch (Exception e) {
      IO2File.printStackTrace(e, "kemiKq.txt");
      e.printStackTrace();
    } 
  }
  
  public List<String[]> readXML(String xml) {
    List<String[]> list = (List)new ArrayList<String>();
    if (!"".equals(xml)) {
      SAXBuilder builder = new SAXBuilder();
      try {
        Document doc = builder.build(new InputSource(new StringReader(xml)));
        Element tb = doc.getRootElement();
        List<Element> recList = tb.getChildren();
        for (int i = 0; i < recList.size(); i++) {
          Element rec = recList.get(i);
          String[] kq = new String[3];
          kq[0] = rec.getChildText("EmpCode");
          kq[1] = rec.getChildText("RecDate");
          kq[2] = rec.getChildText("RecTime");
          list.add(kq);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public void tongbuKq(List<String[]> list, int num) {
    String kqDate = "";
    List<String[]> kqList = (List)new ArrayList<String>();
    for (int i = 0; i < list.size(); i++) {
      String[] data = list.get(i);
      String empNum = getEmpIdByNum(data[0]);
      if (!"".equals(empNum)) {
        String[] kq = new String[4];
        if ("".equals(kqDate))
          kqDate = data[1]; 
        kq[0] = data[0];
        kq[1] = data[1];
        kq[2] = data[2];
        kq[3] = empNum;
        kqList.add(kq);
      } 
    } 
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    try {
      conn = base.getDataSource().getConnection();
      String sql = "insert into kq_punch (punch_id,punch_emp,punch_date,punch_time,punch_oaId,punch_num,punch_ci) values (hibernate_sequence.nextval,?,?,?,?,?," + 
        num + ")";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      for (int j = 0; j < kqList.size(); j++) {
        String[] kq = kqList.get(j);
        if (num == 1 || (num == 2 && kq[2].compareTo(SystemCommon.getClFirst()) >= 1)) {
          pstmt.setString(1, kq[0]);
          pstmt.setString(2, kq[1]);
          pstmt.setString(3, kq[2]);
          pstmt.setString(4, kq[3]);
          String[] punchtime = kq[2].split(":");
          pstmt.setLong(5, Long.valueOf(punchtime[0]).longValue() * 60L + Long.valueOf(punchtime[1]).longValue());
          pstmt.executeUpdate();
        } 
      } 
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    kqList.clear();
  }
  
  public String getEmpIdByNum(String empNumber) {
    DataSourceBase base = new DataSourceBase();
    String empId = "";
    String sql = "SELECT emp_id FROM org_employee WHERE upper(empnumber)='" + empNumber.toUpperCase() + "' and userisactive=1 AND userisdeleted=0";
    try {
      base.begin();
      ResultSet rs = null;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        empId = rs.getString(1);
      } else {
        System.out.println("请在OA人事管理中设置对应的工号！查询的工号为：" + empNumber);
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empId;
  }
  
  public static void main(String[] args) {
    String s1 = "09:11";
    String s2 = "10:11";
    System.out.println(s2.compareTo(s1));
  }
}
