package com.js.oa.crm.util;

import com.js.oa.crm.po.CstSell;
import com.js.oa.crm.po.NumType;
import com.js.oa.relproject.po.RelProActorPO;
import com.js.oa.relproject.po.RelProjectPO;
import com.js.util.util.DateHelper;
import java.util.List;
import java.util.Map;

public class XMLResult {
  private static final int BUFFERSIZE_SHORT = 256;
  
  public static String getResultXML(String status, String info) {
    StringBuffer xml = new StringBuffer(256);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <status>" + status + "</status>\n");
    xml.append("  <info>" + info + "</info>\n");
    xml.append("</result>\n");
    return xml.toString();
  }
  
  public static String getCheck(String head, String icrm, String re, String rep, String action) {
    StringBuffer xml = new StringBuffer(256);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    String info = JDBCManager.get(" count(*),pro_name ", " cst_s ", " pro_num_id=" + head + " and pro_num_incream='" + icrm + "'");
    String report = JDBCManager.get(" count(*),pro_name ", " cst_s ", " report_id=" + re + " and report_num_iden='" + rep + "'");
    String status = "0", infos = "";
    String restate = "0", reinfos = "";
    if (action.equals("save")) {
      if (info != null) {
        status = info.split(";")[0];
        infos = info.split(";")[1];
      } 
      if (!status.equals("0")) {
        String count1 = JDBCManager.getTemp(" count(*),max(desc_incream)+1 ", " cst_incream ", " isauto=" + head);
        String longs = JDBCManager.getInitLongById(head);
        StringBuffer sb = new StringBuffer("");
        String max = count1.split(";")[1];
        for (int i = 0; i < Integer.parseInt(longs) - max.length(); i++)
          sb.append("0"); 
        sb.append(max);
        String count = JDBCManager.get(" count(*),pro_name ", " cst_s ", " pro_num_incream='" + sb.toString() + "' and pro_num_id=" + head);
        if (count != null && Integer.parseInt(count.split(";")[0]) > 0)
          JDBCManager.executeSQL(" insert into cst_incream set isauto='" + head + "',type='0',desc_incream=" + count1.split(";")[1]); 
      } 
    } 
    if (report != null) {
      restate = report.split(";")[0];
      reinfos = report.split(";")[1];
    } 
    if (!restate.equals("0")) {
      String count1 = JDBCManager.getTemp(" count(*),max(desc_incream)+1 ", " cst_incream ", " isauto=" + re);
      String longs = JDBCManager.getInitLongById(re);
      StringBuffer sb = new StringBuffer("");
      String max = count1.split(";")[1];
      for (int i = 0; i < Integer.parseInt(longs) - max.length(); i++)
        sb.append("0"); 
      sb.append(max);
      String count = JDBCManager.get(" count(*),pro_name ", " cst_s ", " report_num_iden='" + sb.toString() + "' and report_id=" + re);
      System.out.println("---count--->>>>" + count);
      if (count != null && Integer.parseInt(count.split(";")[0]) > 0)
        JDBCManager.executeSQL(" insert into cst_incream set isauto='" + re + "',type='0',desc_incream=" + count1.split(";")[1]); 
    } 
    xml.append("  <status>" + status + "</status>\n");
    xml.append("  <info>" + infos + "</info>\n");
    xml.append("  <restate>" + restate + "</restate>\n");
    xml.append("  <reinfo>" + reinfos + "</reinfo>\n");
    xml.append("</result>\n");
    return xml.toString();
  }
  
  public static String getResultXML(NumType clazz, String incream, String num) {
    StringBuffer xml = new StringBuffer(256);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <head>" + clazz.getName() + "</head>\n");
    int longs = clazz.getInitLong();
    int values = clazz.getInitValue();
    StringBuffer in = new StringBuffer("");
    if (incream.equals("-1")) {
      int cha = longs - String.valueOf(values).length();
      if (cha > 0)
        for (int i = 0; i < cha; i++)
          in.append("0");  
      in.append(values);
      xml.append(" <isnum>" + values + "</isnum>\n");
    } else {
      incream = String.valueOf(Integer.valueOf(incream).intValue() + 1);
      int cha = longs - incream.length();
      if (cha > 0)
        for (int i = 0; i < cha; i++)
          in.append("0");  
      in.append(incream);
      xml.append(" <isnum>" + incream + "</isnum>\n");
    } 
    xml.append("  <initLong>" + clazz.getInitLong() + "</initLong>\n");
    xml.append("  <incream>" + in.toString() + "</incream>\n");
    xml.append("</result>\n");
    return xml.toString();
  }
  
  public static String getResultXML(CstSell cstSell, String state, String info) {
    StringBuffer xml = new StringBuffer(256);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <state>" + state + "</state>\n");
    xml.append("  <info>" + info + "</info>\n");
    xml.append("  <name>" + cstSell.getCstName() + "</name>\n");
    xml.append("  <cstid>" + cstSell.getCstId() + "</cstid>\n");
    xml.append("  <comp>" + cstSell.getCompactNum() + "</comp>\n");
    xml.append("  <pro>" + cstSell.getCstProduct() + "</pro>\n");
    xml.append("  <org>" + cstSell.getChargeOrg() + "</org>\n");
    xml.append("  <charge>" + cstSell.getChargeUser() + "</charge>\n");
    xml.append("  <floow>" + cstSell.getFloowUser() + "</floow>\n");
    xml.append("  <hy>" + cstSell.getHangYe() + "</hy>\n");
    xml.append("  <hyn>" + cstSell.getHangYeName() + "</hyn>\n");
    xml.append("</result>\n");
    return xml.toString();
  }
  
  public static String getString(List<RelProActorPO> list) {
    StringBuffer name = new StringBuffer();
    StringBuffer id = new StringBuffer();
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        RelProActorPO rap = list.get(i);
        name.append(rap.getActorName()).append(",");
        id.append(rap.getActorId()).append(",");
      }  
    String a = "", b = "";
    if (!name.toString().equals(""))
      b = name.toString().substring(0, name.toString().length() - 1); 
    return b;
  }
  
  public static String getResultXML(RelProjectPO clazz, Map map) {
    StringBuffer xml = new StringBuffer(256);
    xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
    xml.append("<result>\n");
    xml.append("  <id>" + clazz.getId() + "</id>\n");
    xml.append("  <name>" + clazz.getTitle() + "</name>\n");
    xml.append("  <startTime>" + DateHelper.date2String(clazz.getStartTime(), null) + "</startTime>\n");
    xml.append("  <endTime>" + DateHelper.date2String(clazz.getEndTime(), null) + "</endTime>\n");
    List charge = (List)map.get("chager_user");
    List leade = (List)map.get("leade_user");
    List join = (List)map.get("join_user");
    List about = (List)map.get("about_user");
    xml.append("  <chagerName>" + getString(charge).split(";")[0] + "</chagerName>\n");
    xml.append("  <leaderName>" + getString(leade).split(";")[0] + "</leaderName>\n");
    xml.append("  <joinName>" + getString(join).split(";")[0] + "</joinName>\n");
    xml.append("  <aboutName>" + getString(about).split(";")[0] + "</aboutName>\n");
    xml.append("</result>\n");
    return xml.toString();
  }
}
