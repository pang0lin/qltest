package com.js.oa.form.kq;

import com.js.oa.hr.kq.po.KqChuChaiPO;
import com.js.oa.hr.kq.po.KqQingJiaPO;
import com.js.oa.hr.kq.po.KqWaiChuPO;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class KqFlowImport extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public void importKqInfo() {
    String[] kqInfo = getKqInfo();
    if (!"".equals(kqInfo[0]))
      qingJiaImport(kqInfo[0].replace("（《", "<").replace("》）", ">")); 
    if (!"".equals(kqInfo[1]))
      waiChuImport(kqInfo[1].replace("（《", "<").replace("》）", ">")); 
    if (!"".equals(kqInfo[2]))
      chuChaiImport(kqInfo[2].replace("（《", "<").replace("》）", ">")); 
  }
  
  public void qingJiaImport(String sql) {
    float qingjia_hour = 0.0F;
    List<String[]> qingJiaList = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] qingJia = { "", "", "", "", "0" };
        if (sql.indexOf("qingjia_userId") >= 0)
          qingJia[0] = rs.getString("qingjia_userId"); 
        if (sql.indexOf("qingjia_date") >= 0)
          qingJia[1] = rs.getString("qingjia_date"); 
        if (sql.indexOf("qingjia_start") >= 0)
          qingJia[2] = rs.getString("qingjia_start"); 
        if (sql.indexOf("qingjia_end") >= 0)
          qingJia[3] = rs.getString("qingjia_end"); 
        if (sql.indexOf("qingjia_type") >= 0)
          qingJia[4] = rs.getString("qingjia_type"); 
        qingJiaList.add(qingJia);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    for (int i = 0; i < qingJiaList.size(); i++) {
      String[] qingJia = qingJiaList.get(i);
      KqImportUtil util = new KqImportUtil();
      qingjia_hour = util.getHour(qingJia[2], qingJia[3], "3", qingJia[0]);
      try {
        begin();
        KqQingJiaPO po = new KqQingJiaPO();
        po.setQingJiaEmp(qingJia[0]);
        po.setQingJiaDate(qingJia[1]);
        po.setQingJiaStart(df.parse(qingJia[2]));
        po.setQingJiaEnd(df.parse(qingJia[3]));
        po.setQingJiaHour(Float.valueOf(qingjia_hour));
        po.setQingJiaType(qingJia[4]);
        this.session.save(po);
        this.session.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          this.session.close();
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public void waiChuImport(String sql) {
    float waichu_hour = 0.0F;
    List<String[]> waiChuList = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] waiChu = { "", "", "", "", "0" };
        if (sql.indexOf("waichu_userId") >= 0)
          waiChu[0] = rs.getString("waichu_userId"); 
        if (sql.indexOf("waichu_date") >= 0)
          waiChu[1] = rs.getString("waichu_date"); 
        if (sql.indexOf("waichu_start") >= 0)
          waiChu[2] = rs.getString("waichu_start"); 
        if (sql.indexOf("waichu_end") >= 0)
          waiChu[3] = rs.getString("waichu_end"); 
        if (sql.indexOf("waichu_type") >= 0)
          waiChu[4] = rs.getString("waichu_type"); 
        waiChuList.add(waiChu);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    for (int i = 0; i < waiChuList.size(); i++) {
      String[] waiChu = waiChuList.get(i);
      KqImportUtil util = new KqImportUtil();
      waichu_hour = util.getHour(waiChu[2], waiChu[3], "1", waiChu[0]);
      try {
        begin();
        KqWaiChuPO po = new KqWaiChuPO();
        po.setWaiChuEmp(waiChu[0]);
        po.setWaiChuDate(waiChu[1]);
        po.setWaiChuStart(df.parse(waiChu[2]));
        po.setWaiChuEnd(df.parse(waiChu[3]));
        po.setWaiChuHour(Float.valueOf(waichu_hour));
        po.setWaiChuType(waiChu[4]);
        this.session.save(po);
        this.session.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          this.session.close();
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public void chuChaiImport(String sql) {
    float chuchai_hour = 0.0F;
    List<String[]> chuChaiList = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] chuChai = { "", "", "", "", "0" };
        if (sql.indexOf("chuchai_userId") >= 0)
          chuChai[0] = rs.getString("chuchai_userId"); 
        if (sql.indexOf("chuchai_date") >= 0)
          chuChai[1] = rs.getString("chuchai_date"); 
        if (sql.indexOf("chuchai_start") >= 0)
          chuChai[2] = rs.getString("chuchai_start"); 
        if (sql.indexOf("chuchai_end") >= 0)
          chuChai[3] = rs.getString("chuchai_end"); 
        if (sql.indexOf("chuchai_type") >= 0)
          chuChai[4] = rs.getString("chuchai_type"); 
        chuChaiList.add(chuChai);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    for (int i = 0; i < chuChaiList.size(); i++) {
      String[] chuChai = chuChaiList.get(i);
      KqImportUtil util = new KqImportUtil();
      chuchai_hour = util.getHour(chuChai[2], chuChai[3], "4", chuChai[0]);
      try {
        begin();
        KqChuChaiPO po = new KqChuChaiPO();
        po.setChuChaiEmp(chuChai[0]);
        po.setChuChaiDate(chuChai[1]);
        po.setChuChaiStart(df.parse(chuChai[2]));
        po.setChuChaiEnd(df.parse(chuChai[3]));
        po.setChuChaiHour(Float.valueOf(chuchai_hour));
        po.setChuChaiType(chuChai[4]);
        this.session.save(po);
        this.session.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          this.session.close();
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  private String[] getKqInfo() {
    String[] kqInfo = new String[3];
    String path = System.getProperty("user.dir");
    String configFile = String.valueOf(path) + "/jsconfig/kqconfig.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(configFile);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    Element kq = doc.getRootElement();
    Element kqImport = kq.getChild("kqImport");
    kqInfo[0] = kqImport.getChildText("qingjia");
    kqInfo[1] = kqImport.getChildText("waichu");
    kqInfo[2] = kqImport.getChildText("chuchai");
    return kqInfo;
  }
}
