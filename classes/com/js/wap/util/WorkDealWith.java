package com.js.wap.util;

import com.js.util.config.DocFieldMap;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkDealWith {
  public List<String[]> getInfoList(String workId, String tableId) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    String sql = "";
    try {
      dataSourceBase.begin();
      sql = "select channel_id,informationtitle,informationsubtitle,informationkey,informationsummary,informationcontent,informationstatus,informationauthor,informationissuer,informationissueorg,informationreadername,otherchannel,informationvalidtype,validbegintime,validendtime from oa_information where information_id=" + 

        
        workId;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String[] lanmu = { "", "栏目", "", "", "", "", getInfoChannel(rs.getString(1)) };
        list.add(lanmu);
        String[] biaoti = { "", "标题", "", "", "", "", rs.getString(2) };
        list.add(biaoti);
        String[] chakan = { "", "查看人", "", "", "", "", rs.getString(11) };
        list.add(chakan);
        String[] neirong = { "", "内容", "", "", "", "", "<div style='overflow:auto;'>" + rs.getString(6) + "</div>" };
        list.add(neirong);
        String[] fubiaoti = { "", "副标题", "", "", "", "", rs.getString(3) };
        list.add(fubiaoti);
        String[] guanjianzi = { "", "关键字", "", "", "", "", rs.getString(3) };
        list.add(guanjianzi);
        String[] fulanmu = { "", "同时发布到", "", "", "", "", "" };
        if (!"0".equals(rs.getString(12).substring(1, rs.getString(12).length() - 1))) {
          String id = rs.getString(12).substring(1, rs.getString(12).length() - 1);
          fulanmu[6] = getInfoChannel(id);
        } 
        list.add(fulanmu);
        String[] zuozhe = { "", "作者", "", "", "", "", rs.getString(8) };
        list.add(zuozhe);
        String[] zhaiyao = { "", "摘要", "", "", "", "", rs.getString(5) };
        list.add(zhaiyao);
        String[] youxiaoqi = { "", "有效期", "", "", "", "", "永久" };
        if ("1".equals(rs.getString(13)))
          youxiaoqi[6] = "短期<br/>开始：" + rs.getDate(14).toString() + "<br/>结束：" + rs.getDate(15).toString(); 
        list.add(youxiaoqi);
        String[] faburen = { "", "发布人", "", "", "", "", rs.getString(9) };
        list.add(faburen);
        String[] faburenbumen = { "", "发布人部门", "", "", "", "", rs.getString(10) };
        list.add(faburenbumen);
      } 
      String[] fujian = { "", "附件", "", "", "", "", getInformationAttachfile(workId, tableId) };
      list.add(fujian);
      list.add(getComment(workId, tableId));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public String getInformationAttachfile(String informationId, String tableId) {
    String sql = "select ACCESSORYNAME,ACCESSORYSAVENAME from oa_informationaccessory where INFORMATION_ID=" + informationId;
    DataSourceBase dataSourceBase = new DataSourceBase();
    StringBuffer string = new StringBuffer("");
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        string.append("<a onclick=javascript:showHtmlObject('" + rs.getString(2) + "','0','information');>" + rs.getString(1) + "</a></br>"); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return (String)string;
  }
  
  public List<String[]> getBoradRoomApply(String workId, String tableId) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select a.motif,b.name,a.emceename,a.attendee,a.applyempname,a.applyorgname,a.depict,       a.attendeeleader,a.nonvoting,a.noteperson,a.linktelephone,a.applydate,a.personnum,       a.boardroomcode,a.boardequipment,a.boardroomapplyid ,a.relproject_id,a.regularId   from oa_boardroomapply a,       oa_boardroom b   where a.boardroomid=b.boardroomid     and a.boardroomapplyid=" + 




        
        workId;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String[] zhuti = { "", "会议主题", "", "", "", "", (rs.getString(1) == null) ? "" : rs.getString(1) };
        String[] huiyishi = { "", "会议室", "", "", "", "", (rs.getString(2) == null) ? "" : rs.getString(2) };
        String[] zhuchiren = { "", "主持人", "", "", "", "", (rs.getString(3) == null) ? "" : rs.getString(3) };
        String[] chuxiren = { "", "会议出席人", "", "", "", "", (rs.getString(4) == null) ? "" : rs.getString(4) };
        String[] shenrengren = { "", "预定者", "", "", "", "", (rs.getString(5) == null) ? "" : rs.getString(5) };
        String[] bumen = { "", "预定部门", "", "", "", "", (rs.getString(6) == null) ? "" : rs.getString(6) };
        String[] miaoshu = { "", "会议描述", "", "", "", "", (rs.getString(7) == null) ? "" : rs.getString(7) };
        String[] lingdao = { "", "出席领导", "", "", "", "", (rs.getString(8) == null) ? "" : rs.getString(8) };
        String[] liexiren = { "", "会议列席人", "", "", "", "", (rs.getString(9) == null) ? "" : rs.getString(9) };
        String[] jiluren = { "", "记录人", "", "", "", "", (rs.getString(10) == null) ? "" : rs.getString(10) };
        String[] dianhua = { "", "联系电话", "", "", "", "", (rs.getString(11) == null) ? "" : rs.getString(11) };
        String[] riqi = { "", "申请日期", "", "", "", "", rs.getDate(12).toString().substring(0, 10) };
        String[] renshu = { "", "容纳人数", "", "", "", "", (rs.getString(13) == null) ? "" : rs.getString(13) };
        String[] huiyibianhao = { "", "会议编号", "", "", "", "", (rs.getString(14) == null) ? "" : rs.getString(14) };
        String regularId = (new StringBuilder(String.valueOf(rs.getString(18)))).toString();
        list.add(zhuti);
        list.add(huiyibianhao);
        list.add(zhuchiren);
        list.add(renshu);
        list.add(lingdao);
        list.add(jiluren);
        if (regularId != null && !"".equals(regularId) && !"null".equals(regularId)) {
          list.add(MeetingTimeDingqi(regularId));
        } else {
          list.add(MeetingTime(rs.getString(16)));
        } 
        list.add(huiyishi);
        if ("-1".equals(rs.getString("relproject_id"))) {
          String[] xiangmu = { "", "相关项目", "", "", "", "", "未设置" };
          list.add(xiangmu);
        } else {
          sql = "select title  from pro_body   where id='" + 
            
            rs.getString("relproject_id") + "'";
          ResultSet rs1 = null;
          DataSourceBase dsb = new DataSourceBase();
          try {
            dsb.begin();
            rs1 = dsb.executeQuery(sql);
            if (rs1.next()) {
              String[] xiangmu = { "", "相关项目", "", "", "", "", rs1.getString("title") };
              list.add(xiangmu);
            } else {
              String[] xiangmu = { "", "相关项目", "", "", "", "", "项目不存在" };
              list.add(xiangmu);
            } 
          } finally {
            if (rs1 != null)
              rs1.close(); 
            dsb.end();
          } 
        } 
        list.add(shenrengren);
        list.add(bumen);
        list.add(riqi);
        list.add(dianhua);
        list.add(chuxiren);
        list.add(liexiren);
        list.add(miaoshu);
        list.add(meetOther(rs.getString(16), 1));
        list.add(meetOther("-1" + rs.getString(15) + "-1", 2));
        String[] comment = getComment(workId, tableId);
        if (!"".equals(comment[6]))
          list.add(comment); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public List<String[]> getVehApply(String wordId, String tableId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    List<String[]> list = (List)new ArrayList<String>();
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("1", "普通用车");
    map.put("2", "接待用车");
    map.put("3", "大型活动用车");
    map.put("4", "其他用车");
    String sql = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "select b.name,a.orgname,a.empname,a.destination,a.reason,a.startdate,a.starttime,a.enddate,a.endtime,IFNULL(a.personnum,0) personnum,a.motorman,a.voiturestyle,a.remark from veh_apply a,veh_info b where a.voitureid=b.voitureid and a.applyid=" + 
          
          wordId;
      } else {
        sql = "select b.name,a.orgname,a.empname,a.destination,a.reason,a.startdate,a.starttime,a.enddate,a.endtime,nvl(a.personnum,0) personnum,a.motorman,a.voiturestyle,a.remark from veh_apply a,veh_info b where a.voitureid=b.voitureid and a.applyid=" + 
          
          wordId;
      } 
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String[] mingcheng = { "", "车辆名称", "", "", "", "", rs.getString(1) };
        String[] bumen = { "", "申请部门", "", "", "", "", rs.getString(2) };
        String[] shenqingren = { "", "申请人", "", "", "", "", rs.getString(3) };
        String[] mudidi = { "", "目的地", "", "", "", "", rs.getString(4) };
        String[] shiyou = { "", "事由", "", "", "", "", rs.getString(5) };
        String[] shijian = { "", "预计用车时间", "", "", "", "", "开始：" + rs.getDate(6).toString() + " " + rs.getString(7) + 
            "<br/>结束：" + rs.getDate(8).toString() + " " + rs.getString(9) };
        String[] renshu = { "", "跟车人数", "", "", "", "", (new StringBuilder(String.valueOf(rs.getInt(10)))).toString() };
        String[] siji = { "", "司机", "", "", "", "", rs.getString(11) };
        String[] leixing = { "", "用车类型", "", "", "", "", map.get(rs.getString(12)).toString() };
        String[] beizhu = { "", "备注", "", "", "", "", rs.getString(13) };
        String[] yijian = getComment(wordId, tableId);
        list.add(mingcheng);
        list.add(bumen);
        list.add(shenqingren);
        list.add(mudidi);
        list.add(renshu);
        list.add(siji);
        list.add(shijian);
        list.add(leixing);
        list.add(shiyou);
        list.add(beizhu);
        if (!"".equals(yijian[6]))
          list.add(yijian); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public List<String[]> getEquipApply(String workId, String tableId) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select c.name,c.code,a.empname,b.orgnamestring,a.startdate,a.starttime,a.enddate,a.endtime,a.purpose from oa_equipmentapply a,org_organization b,oa_equipment c where a.equipmentid=c.equipmentid and a.orgid=b.org_id and a.equipmentapplyid=" + 
        
        workId;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String[] mingcheng = { "", "名称", "", "", "", "", rs.getString(1) };
        String[] bianma = { "", "编码", "", "", "", "", rs.getString(2) };
        String[] jieyongren = { "", "借用人", "", "", "", "", rs.getString(3) };
        String[] jieyongbumen = { "", "借用部门", "", "", "", "", rs.getString(4) };
        String[] shijian = { "", "借用时间", "", "", "", "", "开始：" + rs.getDate(5).toString() + " " + getTime(rs.getInt(6)) + 
            "<br/>结束：" + rs.getDate(7).toString() + " " + getTime(rs.getInt(8)) };
        String[] yongtu = { "", "用途", "", "", "", "", rs.getString(9) };
        String[] yijian = getComment(workId, tableId);
        list.add(mingcheng);
        list.add(bianma);
        list.add(jieyongren);
        list.add(jieyongbumen);
        list.add(shijian);
        list.add(yongtu);
        if (!"".equals(yijian[6]))
          list.add(yijian); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public List<String[]> getSendFile(String wordId, String tableId) {
    String show = getShow("0");
    List<String[]> list = (List)new ArrayList<String>();
    String text = "";
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "", field = "", sqlString = "";
    StringBuffer sqlBuffer = new StringBuffer("select ");
    StringBuffer sendfile = new StringBuffer("");
    String[] fields = (String[])null;
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select govformId from form_content where govFormType=0";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        sendfile.append(String.valueOf(rs.getString(1)) + ","); 
      if (sendfile.toString().indexOf(tableId) < 0) {
        field = "documentSendFileTitle,accessoryName,sendFileDepartWord,documentSendFileByteNumber,zjkySeq,documentSendFileSecurityGrade,zjkySecrecyterm,sendFileGrade,zjkyContentLevel,documentSendFileTopicWord,toPerson1,toPerson2,toPersonInner,documentSendFileWriteOrg,documentSendFileCounterSign,sendFileDraft,field9,documentSendFileAssumeUnit,sendFileMassDraft,sendFileProveDraft,sendFileReadComment,documentSendFileCheckCommit,documentSendFileSendFile,documentSendFilePrintNumber,documentSendFileSendTime";
        fields = field.split(",");
      } else {
        sql = "select govCheckField from form_content where govFormType=0 and govFormId=" + tableId;
        rs = dataSourceBase.executeQuery(sql);
        if (rs.next()) {
          field = rs.getString(1).substring(0, rs.getString(1).length() - 1);
          fields = field.split(";");
        } 
      } 
      DocFieldMap docFieldMap = new DocFieldMap();
      Map map = DocFieldMap.getSendDocMap();
      Map nameMap = DocFieldMap.getNameMap();
      for (int i = 0; i < fields.length; i++)
        sqlBuffer.append((new StringBuilder()).append(map.get(fields[i])).append(",").toString()); 
      sql = "select sendfile_text from doc_documentsendfile where documentsendfile_id=" + wordId;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String srcFive = "/0000";
        if (rs.getString(1) != null && rs.getString(1).length() > 6 && rs.getString(1).substring(4, 5).equals("_")) {
          srcFive = "/" + rs.getString(1).substring(0, 4);
        } else {
          srcFive = "/0000";
        } 
        String html = "<a href=\"/jsoa/upload/govdocumentmanager/" + rs.getString(1) + ".doc\">查看正文</a>";
        String[] doc = { "", "正文", "", "", "", "", html };
        list.add(doc);
      } 
      Map map2 = getDocComment(wordId, tableId);
      sqlString = String.valueOf(sqlBuffer.toString().substring(0, sqlBuffer.toString().length() - 1)) + " from doc_documentsendfile where " + 
        "documentsendfile_id=" + wordId;
      rs = dataSourceBase.executeQuery(sqlString);
      if (rs.next())
        for (int j = 0; j < fields.length; j++) {
          String[] doc = { "", "", "", "", "", "", "" };
          doc[1] = nameMap.get((new StringBuilder(String.valueOf(fields[j]))).toString()).toString();
          if (rs.getString(j + 1) != null && !"null".equals(rs.getString(j + 1))) {
            if ("accessoryName".equalsIgnoreCase(fields[j]) && !"".equals(rs.getString(j + 1))) {
              doc[6] = getDownLoad(fields[j], wordId);
            } else {
              text = rs.getString(j + 1);
              if (text.endsWith("00:00:00.0"))
                text = text.substring(0, 10); 
              doc[6] = text;
            } 
          } else if (show.indexOf(fields[j]) >= 0) {
            text = (map2.get(fields[j]) == null) ? "" : map2.get(fields[j]).toString();
            doc[6] = text;
          } 
          list.add(doc);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  private String getTypeDefineName(String pt_typedefine) {
    String sql = "select typedefine_name from ST_TYPEDEFINE where id=" + pt_typedefine;
    DataSourceBase dsb = new DataSourceBase();
    ResultSet rs1 = null;
    try {
      dsb.begin();
      rs1 = dsb.executeQuery(sql);
      if (rs1.next())
        return rs1.getString("typedefine_name"); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs1 != null)
        try {
          rs1.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      dsb.end();
    } 
    return "";
  }
  
  public List<String[]> getIntoStockList(String workId, String tableId) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase dataSourceBase = new DataSourceBase();
    String type = "";
    String sql = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select a.pt_serial,b.stock_name,a.pt_orgname,a.pt_typedefine,       a.pt_supp,a.pt_date,a.pt_handlename,a.invoiceno,a.remark,       c.empName,a.make_date   from ST_PTMASTER a,ST_STOCK b,ORG_EMPLOYEE c   where a.pt_no='" + 


        
        workId + "'" + 
        "      and a.pt_stock = b.stock_id" + 
        "      and a.make_man = c.emp_id";
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        if (rs.getString("pt_serial").startsWith("RKD")) {
          type = "入库";
        } else {
          type = "退货";
        } 
        String[] pt_serial = { "", String.valueOf(type) + "单号", "", "", "", "", rs.getString("pt_serial") };
        String[] stock_name = { "", String.valueOf(type) + "仓库", "", "", "", "", rs.getString("stock_name") };
        String[] pt_orgname = { "", String.valueOf(type) + "部门", "", "", "", "", rs.getString("pt_orgname") };
        String pt_typedefine = rs.getString("pt_typedefine");
        String[] pt_supp = { "", "入库".equals(type) ? "供货单位" : "退货单位", "", "", "", "", rs.getString("pt_supp") };
        String[] pt_date = { "", String.valueOf(type) + "日期", "", "", "", "", rs.getDate("pt_date").toString() };
        String[] pt_handlename = { "", "经手人", "", "", "", "", rs.getString("pt_handlename") };
        String[] invoiceno = { "", "发票号码", "", "", "", "", rs.getString("invoiceno") };
        String[] remark = { "", "备注", "", "", "", "", rs.getString("remark") };
        String[] empName = { "", "制单人", "", "", "", "", rs.getString("empName") };
        String[] make_date = { "", "制单日期", "", "", "", "", rs.getDate("make_Date").toString() };
        list.add(pt_serial);
        list.add(stock_name);
        list.add(pt_orgname);
        if (pt_typedefine != null && !"".equals(pt_typedefine)) {
          String[] type1 = { "", String.valueOf(type) + "类别", "", "", "", "", getTypeDefineName(pt_typedefine) };
          list.add(type1);
        } 
        list.add(pt_supp);
        list.add(pt_date);
        list.add(pt_handlename);
        list.add(invoiceno);
        list.add(remark);
        list.add(empName);
        list.add(make_date);
        sql = "select * from st_ptdetail where pt_no=" + workId;
        DataSourceBase dsb1 = new DataSourceBase();
        dsb1.begin();
        ResultSet rs2 = null;
        rs2 = dsb1.executeQuery(sql);
        StringBuffer detailContent = new StringBuffer("<table borderColor='#000000' borderColorDark='#e1e1e1' border='1' cellSpacing='0' cellPadding='1' style='border-collapse:collapse;'>                                             <tr><td align='center'>材料编号</td><td align='center'>材料名称</td><td align='center'>规格型号</td><td align='center'>计量单位</td><td align='center'>数量</td><td align='center'>单价</td><td align='center'>金额</td></tr>");
        int sum_amount = 0;
        float sum_goods_money = 0.0F;
        while (rs2.next()) {
          detailContent.append("<tr>");
          detailContent.append("<td>" + rs2.getString("goods_id").substring(rs2.getString("goods_id").indexOf("_") + 1) + "</td>");
          detailContent.append("<td>" + rs2.getString("goods_name") + "</td>");
          detailContent.append("<td>" + rs2.getString("goods_specs") + "</td>");
          detailContent.append("<td>" + rs2.getString("goods_unit") + "</td>");
          int count = rs2.getInt("amount");
          sum_amount += count;
          detailContent.append("<td>" + (new StringBuilder(String.valueOf(count))).toString().replaceAll("-", "") + "</td>");
          detailContent.append("<td>" + rs2.getDouble("mcost") + "</td>");
          float money = rs2.getFloat("goods_money");
          sum_goods_money += money;
          detailContent.append("<td>" + (new StringBuilder(String.valueOf(money))).toString().replaceAll("-", "") + "</td>");
          detailContent.append("</tr>");
        } 
        detailContent.append("<tr>");
        detailContent.append("<td align='center'>合计</td>");
        detailContent.append("<td></td>");
        detailContent.append("<td></td>");
        detailContent.append("<td></td>");
        detailContent.append("<td>" + (new StringBuilder(String.valueOf(sum_amount))).toString().replaceAll("-", "") + "</td>");
        detailContent.append("<td align='center'></td>");
        detailContent.append("<td>" + (new StringBuilder(String.valueOf(sum_goods_money))).toString().replaceAll("-", "") + "</td>");
        detailContent.append("</tr>");
        detailContent.append("</table>");
        rs2.close();
        dsb1.end();
        String[] items = { "", String.valueOf(type) + "明细", "", "", "", "", detailContent.toString() };
        list.add(items);
        String[] psyj = getComment(workId, tableId);
        if (!"".equals(psyj[6]))
          list.add(psyj); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public List<String[]> getOutStockList(String workId, String tableId) {
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase dataSourceBase = new DataSourceBase();
    String type = "";
    String sql = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select a.ss_serial,b.stock_name,a.ss_orgname,a.ss_typedefine,       a.ss_date,a.ss_useman,a.remark,       c.empName,a.make_date   from ST_SSMASTER a,ST_STOCK b,ORG_EMPLOYEE c   where a.ss_no='" + 


        
        workId + "'" + 
        "      and a.ss_stock = b.stock_id" + 
        "      and a.make_man = c.emp_id";
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        if (rs.getString("ss_serial").startsWith("CKD")) {
          type = "出库";
        } else {
          type = "退库";
        } 
        String[] pt_serial = { "", String.valueOf(type) + "单号", "", "", "", "", rs.getString("ss_serial") };
        String[] stock_name = { "", String.valueOf(type) + "仓库", "", "", "", "", rs.getString("stock_name") };
        String[] pt_orgname = { "", String.valueOf(type) + "单位", "", "", "", "", rs.getString("ss_orgname") };
        String ss_typedefine = rs.getString("ss_typedefine");
        String[] pt_date = { "", String.valueOf(type) + "日期", "", "", "", "", rs.getDate("ss_date").toString() };
        String[] pt_handlename = { "", "出库".equals(type) ? "领用人" : "退库人", "", "", "", "", rs.getString("ss_useman") };
        String[] remark = { "", "备注", "", "", "", "", rs.getString("remark") };
        String[] empName = { "", "制单人", "", "", "", "", rs.getString("empName") };
        String[] make_date = { "", "制单日期", "", "", "", "", rs.getDate("make_Date").toString() };
        list.add(pt_serial);
        list.add(stock_name);
        list.add(pt_orgname);
        if (ss_typedefine != null && !"".equals(ss_typedefine)) {
          String[] type1 = { "", String.valueOf(type) + "类别", "", "", "", "", getTypeDefineName(ss_typedefine) };
          list.add(type1);
        } 
        list.add(pt_date);
        list.add(pt_handlename);
        list.add(remark);
        list.add(empName);
        list.add(make_date);
        sql = "select * from st_ssdetail where ss_no=" + workId;
        DataSourceBase dsb1 = new DataSourceBase();
        dsb1.begin();
        ResultSet rs2 = null;
        rs2 = dsb1.executeQuery(sql);
        StringBuffer detailContent = new StringBuffer("<table borderColor='#000000' borderColorDark='#e1e1e1' border='1' cellSpacing='0' cellPadding='1' style='border-collapse:collapse;'>                                             <tr><td align='center'>材料编号</td><td align='center'>材料名称</td><td align='center'>规格型号</td><td align='center'>计量单位</td><td align='center'>数量</td><td align='center'>单价</td><td align='center'>金额</td></tr>");
        int sum_amount = 0;
        float sum_goods_money = 0.0F;
        while (rs2.next()) {
          detailContent.append("<tr>");
          detailContent.append("<td>" + rs2.getString("goods_id").substring(rs2.getString("goods_id").indexOf("_") + 1) + "</td>");
          detailContent.append("<td>" + rs2.getString("goods_name") + "</td>");
          detailContent.append("<td>" + rs2.getString("goods_specs") + "</td>");
          detailContent.append("<td>" + rs2.getString("goods_unit") + "</td>");
          int count = rs2.getInt("ss_amount");
          sum_amount += count;
          detailContent.append("<td>" + (new StringBuilder(String.valueOf(count))).toString().replaceAll("-", "") + "</td>");
          detailContent.append("<td>" + rs2.getDouble("price") + "</td>");
          float money = rs2.getFloat("goods_money");
          sum_goods_money += money;
          detailContent.append("<td>" + (new StringBuilder(String.valueOf(money))).toString().replaceAll("-", "") + "</td>");
          detailContent.append("</tr>");
        } 
        detailContent.append("<tr>");
        detailContent.append("<td align='center'>合计</td>");
        detailContent.append("<td></td>");
        detailContent.append("<td></td>");
        detailContent.append("<td></td>");
        detailContent.append("<td>" + (new StringBuilder(String.valueOf(sum_amount))).toString().replaceAll("-", "") + "</td>");
        detailContent.append("<td align='center'></td>");
        detailContent.append("<td>" + (new StringBuilder(String.valueOf(sum_goods_money))).toString().replaceAll("-", "") + "</td>");
        detailContent.append("</tr>");
        detailContent.append("</table>");
        rs2.close();
        dsb1.end();
        String[] items = { "", String.valueOf(type) + "明细", "", "", "", "", detailContent.toString() };
        list.add(items);
        String[] psyj = getComment(workId, tableId);
        if (!"".equals(psyj[6]))
          list.add(psyj); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public List<String[]> getArchivesList(String workId, String tableId) {
    List<String[]> list = (List)new ArrayList<String>();
    String sql = "";
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select b.filename,a.empname,a.orgname,a.borrowcount,a.borrowintent,borrowdate    from OA_ARCHIVESBORROW a,OA_ARCHIVESFILE b   where a.file_id = b.file_id      and a.borrow_id=" + 

        
        workId;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        String[] filename = { "", "文件标题", "", "", "", "", rs.getString("filename") };
        String[] empname = { "", "借阅人姓名", "", "", "", "", rs.getString("empname") };
        String[] orgname = { "", "借阅人部门", "", "", "", "", rs.getString("orgname") };
        String[] borrowcount = { "", "借阅份数", "", "", "", "", (new StringBuilder(String.valueOf(rs.getInt("borrowcount")))).toString() };
        String[] borrowintent = { "", "借阅目的说明", "", "", "", "", rs.getString("borrowintent") };
        String[] borrowdate = { "", "借阅日期", "", "", "", "", rs.getDate("borrowdate").toString() };
        list.add(filename);
        list.add(empname);
        list.add(orgname);
        list.add(borrowcount);
        list.add(borrowintent);
        list.add(borrowdate);
      } 
      String[] psyj = getComment(workId, tableId);
      if (!"".equals(psyj[6]))
        list.add(psyj); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      dataSourceBase.end();
    } 
    return list;
  }
  
  public List<String[]> getReceiveList(String workId, String tableId) {
    String show = getShow("1");
    List<String[]> list = (List)new ArrayList<String>();
    String text = "";
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "", field = "", sqlString = "";
    StringBuffer sqlBuffer = new StringBuffer("select ");
    StringBuffer receivefile = new StringBuffer("");
    String[] fields = (String[])null;
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select govformId from form_content where govFormType=1";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        receivefile.append(String.valueOf(rs.getString(1)) + ","); 
      if (receivefile.toString().indexOf(tableId) < 0) {
        field = "accessoryName1,accessoryName2,createdDate,zjkySeq,receiveFileSendFileUnit,receiveFileFileNumber,receiveFileTitle,receiveFileSafetyGrade,receiveFileQuantity,field4,receiveFileReceiveDate,receiveFileType,zjkyType,zjkykeepTerm,receiveFileDoComment,receiveFileLeaderComment,receiveFileSettleLeaderComment,receiveFileSettleComment,field9,receiveFileTransAuditComment,receiveFileMemo";
        fields = field.split(",");
      } else {
        sql = "select govCheckField from form_content where govFormType=1 and govFormId=" + tableId;
        rs = dataSourceBase.executeQuery(sql);
        if (rs.next()) {
          field = rs.getString(1).substring(0, rs.getString(1).length() - 1);
          fields = field.split(";");
        } 
      } 
      DocFieldMap docFieldMap = new DocFieldMap();
      Map receiveMap = DocFieldMap.getReceiveMap();
      Map receiveNameMap = DocFieldMap.getReceiveNameMap();
      for (int i = 0; i < fields.length; i++)
        sqlBuffer.append((new StringBuilder()).append(receiveMap.get(fields[i])).append(",").toString()); 
      Map map2 = getDocComment(workId, tableId);
      sqlString = String.valueOf(sqlBuffer.toString().substring(0, sqlBuffer.toString().length() - 1)) + " from doc_receivefile where " + 
        " receivefile_id=" + workId;
      rs = dataSourceBase.executeQuery(sqlString);
      if (rs.next())
        for (int j = 0; j < fields.length; j++) {
          String[] doc = { "", "", "", "", "", "", "" };
          doc[1] = receiveNameMap.get((new StringBuilder(String.valueOf(fields[j]))).toString()).toString();
          if (rs.getString(j + 1) != null && !"null".equals(rs.getString(j + 1))) {
            if ((fields[j].equalsIgnoreCase("accessoryName1") || "accessoryName2".equalsIgnoreCase(fields[j])) && !"".equals(rs.getString(j + 1))) {
              doc[6] = getDownLoad(fields[j], workId);
            } else {
              text = rs.getString(j + 1);
              if (text.endsWith("00:00:00.0"))
                text = text.substring(0, 10); 
              doc[6] = text;
            } 
          } else if (show.indexOf(fields[j]) >= 0) {
            text = (map2.get(fields[j]) == null) ? "" : map2.get(fields[j]).toString();
            doc[6] = text;
          } 
          list.add(doc);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public String getInfoChannel(String channelId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String channel = "知识管理.", sql = "", idString = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select channelidString from oa_informationchannel where channel_id=" + channelId;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        idString = rs.getString(1);
        String[] ids = idString.split("\\$");
        for (int i = 1; i < ids.length; i += 2) {
          sql = "select channelname from oa_informationchannel where channel_id=" + ids[i];
          rs = dataSourceBase.executeQuery(sql);
          if (rs.next())
            channel = String.valueOf(channel) + rs.getString(1) + "."; 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return channel.substring(0, channel.length() - 1);
  }
  
  public String[] MeetingTime(String id) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String[] times = { "", "会议时间", "", "", "", "", "" };
    String sql = "", riqi = "", begintime = "", endtime = "";
    String shijianduan = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select meetingtime,starttime,endtime from oa_boardroom_meetingtime where applyid=" + id + " ORDER BY id";
      rs = dataSourceBase.executeQuery(sql);
      int i = 0;
      while (rs.next()) {
        i++;
        riqi = rs.getDate(1).toString().substring(0, 10);
        begintime = getTime(rs.getInt(2));
        endtime = getTime(rs.getInt(3));
        shijianduan = String.valueOf(shijianduan) + "[" + i + "]开始：" + riqi + " " + begintime + " 结束：" + riqi + " " + endtime + "</br>";
      } 
      times[6] = shijianduan;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return times;
  }
  
  public String[] MeetingTimeDingqi(String id) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String[] times = { "", "会议时间", "", "", "", "", "" };
    String sql = "", riqiBegin = "", riqiEnd = "", begintime = "", endtime = "", meetingCircle = "", everyMeetingBegin = "", everyMeetingEnd = "", meetingLength = "";
    String meetString = "";
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select meetingDateBegin,meetingDateEnd,everyMeetingBeginTime,everyMeetingEndTime,meetingCircle,everyMeetingBegin,everyMeetingEnd,meetingLength from oa_boardroomregular where regularId=" + id;
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next()) {
        riqiBegin = rs.getString(1).toString();
        riqiEnd = rs.getString(2).toString();
        begintime = getTimeDingqi(rs.getInt(3));
        endtime = getTimeDingqi(rs.getInt(4));
        meetingCircle = rs.getString(5);
        everyMeetingBegin = rs.getString(6);
        everyMeetingEnd = rs.getString(7);
        meetingLength = rs.getString(8);
      } 
      if ("1".equals(meetingCircle) && !"".equals(everyMeetingBegin)) {
        meetString = String.valueOf(meetString) + "会议周期：每周";
        if ("1".equals(meetingLength)) {
          meetString = String.valueOf(meetString) + " 当天</br>";
        } else {
          meetString = String.valueOf(meetString) + " 多天</br>";
        } 
        meetString = String.valueOf(meetString) + "会议时间段:" + riqiBegin + "到" + riqiEnd + "</br>";
        meetString = String.valueOf(meetString) + "开会时间:每周 ";
        if ("1".equals(meetingLength)) {
          String week = "一";
          if ("1".equals(everyMeetingBegin)) {
            week = "一";
          } else if ("2".equals(everyMeetingBegin)) {
            week = "二";
          } else if ("3".equals(everyMeetingBegin)) {
            week = "三";
          } else if ("4".equals(everyMeetingBegin)) {
            week = "四";
          } else if ("5".equals(everyMeetingBegin)) {
            week = "五";
          } else if ("6".equals(everyMeetingBegin)) {
            week = "六";
          } else if ("7".equals(everyMeetingBegin)) {
            week = "日";
          } 
          meetString = String.valueOf(meetString) + " 周" + week + " 从" + begintime + "到" + endtime;
        } else {
          String weekBegin = "一";
          String weekEnd = "二";
          if ("1".equals(everyMeetingBegin)) {
            weekBegin = "一";
          } else if ("2".equals(everyMeetingBegin)) {
            weekBegin = "二";
          } else if ("3".equals(everyMeetingBegin)) {
            weekBegin = "三";
          } else if ("4".equals(everyMeetingBegin)) {
            weekBegin = "四";
          } else if ("5".equals(everyMeetingBegin)) {
            weekBegin = "五";
          } else if ("6".equals(everyMeetingBegin)) {
            weekBegin = "六";
          } else if ("7".equals(everyMeetingBegin)) {
            weekBegin = "日";
          } 
          if ("1".equals(everyMeetingEnd)) {
            weekEnd = "一";
          } else if ("2".equals(everyMeetingEnd)) {
            weekEnd = "二";
          } else if ("3".equals(everyMeetingEnd)) {
            weekEnd = "三";
          } else if ("4".equals(everyMeetingEnd)) {
            weekEnd = "四";
          } else if ("5".equals(everyMeetingEnd)) {
            weekEnd = "五";
          } else if ("6".equals(everyMeetingEnd)) {
            weekEnd = "六";
          } else if ("7".equals(everyMeetingEnd)) {
            weekEnd = "日";
          } 
          meetString = String.valueOf(meetString) + " 从周" + weekBegin + begintime + "到周" + weekEnd + endtime;
        } 
      } else if ("2".equals(meetingCircle)) {
        meetString = String.valueOf(meetString) + "会议周期：每月";
        if ("1".equals(meetingLength)) {
          meetString = String.valueOf(meetString) + "当天</br>";
          meetString = String.valueOf(meetString) + "会议时间段:" + riqiBegin + "到" + riqiEnd + "</br>";
          meetString = String.valueOf(meetString) + "开会时间:";
          meetString = String.valueOf(meetString) + everyMeetingBegin + "日";
          meetString = String.valueOf(meetString) + "从" + begintime + "到" + endtime;
        } else {
          meetString = String.valueOf(meetString) + " 多天</br>";
          meetString = String.valueOf(meetString) + "会议时间段:" + riqiBegin + "到" + riqiEnd + "</br>";
          meetString = String.valueOf(meetString) + "开会时间:";
          meetString = String.valueOf(meetString) + "从" + everyMeetingBegin + "日 " + begintime + "到" + everyMeetingEnd + "日 " + endtime;
        } 
      } else if ("3".equals(meetingCircle)) {
        meetString = String.valueOf(meetString) + "会议周期：每年";
        if ("1".equals(meetingLength)) {
          meetString = String.valueOf(meetString) + "当天</br>";
          meetString = String.valueOf(meetString) + "会议时间段:" + riqiBegin + "到" + riqiEnd + "</br>";
          meetString = String.valueOf(meetString) + "开会时间:";
          meetString = String.valueOf(meetString) + everyMeetingBegin.replace("-", "月") + "日";
          meetString = String.valueOf(meetString) + "从" + begintime + "到" + endtime;
        } else {
          meetString = String.valueOf(meetString) + " 多天</br>";
          meetString = String.valueOf(meetString) + "会议时间段:" + riqiBegin + "到" + riqiEnd + "</br>";
          meetString = String.valueOf(meetString) + "开会时间:";
          meetString = String.valueOf(meetString) + "从" + everyMeetingBegin.replace("-", "月") + "日 " + begintime + "到" + everyMeetingEnd.replace("-", "月") + "日 " + endtime;
        } 
      } 
      times[6] = meetString;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return times;
  }
  
  public String[] meetOther(String id, int type) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    String[] other = { "", "", "", "", "", "", "" };
    if (type == 1) {
      other[1] = "附件";
      sql = "select name,savename from oa_bdroomappaccessory where boardroomapplyid=" + id;
    } else {
      other[1] = "会议设备";
      sql = "select equname from oa_bdroomequ where equid in (" + id + ")";
    } 
    StringBuffer string = new StringBuffer("");
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        String src = "0000";
        if (rs.getString(2) != null && rs.getString(2).length() > 6 && rs.getString(2).substring(4, 5).equals("_")) {
          src = rs.getString(2).substring(0, 4);
        } else {
          src = "0000";
        } 
        if (type == 1) {
          string.append("<a onclick=javascript:showHtmlObject('" + rs.getString(2) + "','0','boardroom');>" + rs.getString(1) + "</a>,");
          continue;
        } 
        string.append(String.valueOf(rs.getString(1)) + ",");
      } 
      if (string.toString().length() >= 1)
        other[6] = string.toString().substring(0, string.toString().length() - 1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return other;
  }
  
  public String[] getComment(String workId, String tableId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    StringBuffer string = new StringBuffer("");
    String[] comment = { "", "批示意见", "", "", "", "", "" };
    ResultSet rs = null;
    try {
      sql = "select a.activityname,b.dealwithemployeecomment,b.dealwithtime,c.empname,b.wf_dealwith_id from jsf_dealwith a,jsf_dealwithcomment b,org_employee c where a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id=c.emp_id and a.databaserecord_id=" + 
        
        workId + 
        
        " and a.databasetable_id=" + tableId + " order by a.wf_dealwith_id,b.dealwithtime";
      dataSourceBase.begin();
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        String content = rs.getString(2);
        if (content != null) {
          content = content.toLowerCase().equals("null") ? "" : content;
        } else {
          content = "";
        } 
        string.append("<h4>" + rs.getString(1) + "</h4>")
          .append(String.valueOf(content) + "<br/>")
          .append("<div align=\"right\">" + rs.getString(4) + " ")
          .append(String.valueOf(rs.getDate(3).toString()) + " " + rs.getTime(3).toString() + "</div><br/>");
      } 
      if (!"".equals(string.toString()))
        comment[6] = string.toString(); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return comment;
  }
  
  public Map getDocComment(String workId, String tableId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    Map<Object, Object> map = new HashMap<Object, Object>();
    StringBuffer string = new StringBuffer("");
    ResultSet rs = null;
    try {
      sql = "select a.activityname,b.dealwithemployeecomment,b.dealwithtime,c.empname,b.commentfield,b.standforusername from jsf_dealwith a,jsf_dealwithcomment b,org_employee c where a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id=c.emp_id and a.databaserecord_id=" + 

        
        workId + " and databasetable_id=" + 
        tableId + " and commentisback <> 1 order by a.wf_dealwith_id,b.dealwithtime";
      dataSourceBase.begin();
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next()) {
        String comment = rs.getString(2);
        String standforUserName = rs.getString(6);
        if (comment == null || comment.equalsIgnoreCase("null"))
          comment = ""; 
        string.append(String.valueOf(comment) + "<br/>")
          .append("<div align=\"right\">" + rs.getString(4) + " ")
          .append(rs.getDate(3).toString())
          .append(rs.getTime(3).toString());
        if ("1".equals(SystemCommon.getDocShowDbrName()) && standforUserName != null && !"".equals(standforUserName))
          string.append("(").append(standforUserName).append("代办)"); 
        string.append("</div><br/>");
        if (map.containsKey(rs.getString(5))) {
          map.put(rs.getString(5), (new StringBuilder()).append(map.get(rs.getString(5))).append(string.toString()).toString());
        } else {
          map.put(rs.getString(5), string.toString());
        } 
        string = new StringBuffer("");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return map;
  }
  
  public String getDownLoad(String label, String workId) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    StringBuffer html = new StringBuffer("");
    String[] name = (String[])null;
    String[] savename = (String[])null;
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      if ("accessoryName1".equalsIgnoreCase(label)) {
        sql = "select accessoryName,accessorysavename from doc_receivefile where receivefile_id=" + workId;
      } else if ("accessoryName2".equalsIgnoreCase(label)) {
        sql = "select accessoryName_file,accessorysavename_file from doc_receivefile where receivefile_id=" + workId;
      } else if ("accessoryName".equalsIgnoreCase(label)) {
        sql = "select accessoryname,accessorysavename from doc_documentsendfile where documentsendfile_id=" + workId;
      } 
      rs = dataSourceBase.executeQuery(sql);
      if (rs.next())
        if (rs.getString(1).indexOf("|") >= 0) {
          name = rs.getString(1).split("\\|");
          savename = rs.getString(2).split("\\|");
          for (int i = 0; i < name.length; i++) {
            String srcTwo = "/0000";
            if (savename[i] != null && savename[i].length() > 6 && savename[i].substring(4, 5).equals("_")) {
              srcTwo = "/" + savename[i].substring(0, 4);
            } else {
              srcTwo = "/0000";
            } 
            html.append("<a onclick=javascript:showHtmlObject('" + savename[i] + "','0','govdocumentmanager');>" + name[i] + "</a><br/>");
          } 
        } else {
          String srcThree = "/0000";
          if (rs.getString(2) != null && rs.getString(2).length() > 6 && rs.getString(2).substring(4, 5).equals("_")) {
            srcThree = "/" + rs.getString(2).substring(0, 4);
          } else {
            srcThree = "/0000";
          } 
          html.append("<a onclick=javascript:showHtmlObject('" + rs.getString(2) + "','0','govdocumentmanager');>" + rs.getString(1) + "</a><br/>");
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return html.toString();
  }
  
  public String getShow(String type) {
    DataSourceBase dataSourceBase = new DataSourceBase();
    String sql = "";
    StringBuffer show = new StringBuffer("");
    ResultSet rs = null;
    try {
      dataSourceBase.begin();
      sql = "select gffname from form_initfield where gffdisplaytype=10 and govformtype=" + type;
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        show.append(String.valueOf(rs.getString(1)) + ","); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return show.toString();
  }
  
  public String getTime(int time) {
    int hour = time / 3600;
    int min = (time - hour * 3600) / 60;
    if (min < 10)
      return String.valueOf(hour) + ":0" + min; 
    return String.valueOf(hour) + ":" + min;
  }
  
  public String getTimeDingqi(int time) {
    int hour = time / 60;
    int min = time - hour * 60;
    if (min < 10)
      return String.valueOf(hour) + ":0" + min; 
    return String.valueOf(hour) + ":" + min;
  }
}
