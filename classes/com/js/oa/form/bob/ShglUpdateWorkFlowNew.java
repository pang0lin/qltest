package com.js.oa.form.bob;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ShglUpdateWorkFlowNew extends Workflow {
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      String xiugaiMain = "jst_3069";
      String xiugaiSub = "bank_thshxg_zb";
      String recordId = request.getParameter("recordId");
      String dataSql = "select bank_shhylb,bank_ID,bank_zdr,bank_rq,bank_fzh,bank_pimc,bank_zcdwqc,bank_dz,bank_cs,bank_shlxrxm,bank_lxrdh,bank_xyksrq,bank_xyjsrq,bank_yhnr,bank_fzhlxr,bank_fzhlxdh,bank_xyfj,bank_zj,bank_dwdh,bank_mdsl,bank_lczt,bank_shdj,bank_zh from " + 

        
        xiugaiMain + " where " + xiugaiMain + "_id=" + recordId;
      DataSourceBase base = new DataSourceBase();
      String bank_ID = "";
      try {
        base.begin();
        String updateSql = "";
        ResultSet rs = base.executeQuery(dataSql);
        if (rs.next()) {
          bank_ID = (rs.getString("bank_ID") == null) ? "" : rs.getString("bank_ID");
          if (bank_ID.contains("@@$@@"))
            bank_ID = bank_ID.split("\\@\\@\\$\\@\\@")[1]; 
          if (!bank_ID.equals("")) {
            updateSql = "update bank_thsh set bank_shhylb='" + (
              (rs.getString("bank_shhylb") == null) ? "" : rs.getString("bank_shhylb")) + "'," + 
              "bank_zdr='" + ((rs.getString("bank_zdr") == null) ? "" : rs.getString("bank_zdr")) + "'," + 
              "bank_rq='" + ((rs.getString("bank_rq") == null) ? "" : rs.getString("bank_rq")) + "'," + 
              "bank_fzh='" + ((rs.getString("bank_fzh") == null) ? "" : rs.getString("bank_fzh")) + "'," + 
              "bank_pimc='" + ((rs.getString("bank_pimc") == null) ? "" : rs.getString("bank_pimc")) + "'," + 
              "bank_zcdwqc='" + ((rs.getString("bank_zcdwqc") == null) ? "" : rs.getString("bank_zcdwqc")) + "'," + 
              "bank_dz='" + ((rs.getString("bank_dz") == null) ? "" : rs.getString("bank_dz")) + "'," + 
              "bank_cs='" + ((rs.getString("bank_cs") == null) ? "" : rs.getString("bank_cs")) + "'," + 
              "bank_shlxrxm='" + ((rs.getString("bank_shlxrxm") == null) ? "" : rs.getString("bank_shlxrxm")) + "'," + 
              "bank_lxrdh='" + ((rs.getString("bank_lxrdh") == null) ? "" : rs.getString("bank_lxrdh")) + "'," + 
              "bank_xyksrq='" + ((rs.getString("bank_xyksrq") == null) ? "" : rs.getString("bank_xyksrq")) + "'," + 
              "bank_xyjsrq='" + ((rs.getString("bank_xyjsrq") == null) ? "" : rs.getString("bank_xyjsrq")) + "'," + 
              "bank_yhnr='" + ((rs.getString("bank_yhnr") == null) ? "" : rs.getString("bank_yhnr")) + "'," + 
              "bank_fzhlxr='" + ((rs.getString("bank_fzhlxr") == null) ? "" : rs.getString("bank_fzhlxr")) + "'," + 
              "bank_fzhlxdh='" + ((rs.getString("bank_fzhlxdh") == null) ? "" : rs.getString("bank_fzhlxdh")) + "'," + 
              "bank_xyfj='" + ((rs.getString("bank_xyfj") == null) ? "" : rs.getString("bank_xyfj")) + "'," + 
              "bank_zj='" + ((rs.getString("bank_zj") == null) ? "" : rs.getString("bank_zj")) + "'," + 
              "bank_dwdh='" + ((rs.getString("bank_dwdh") == null) ? "" : rs.getString("bank_dwdh")) + "'," + 
              "bank_mdsl='" + ((rs.getString("bank_mdsl") == null) ? "" : rs.getString("bank_mdsl")) + "'," + 
              "bank_lczt='" + ((rs.getString("bank_lczt") == null) ? "" : rs.getString("bank_lczt")) + "'," + 
              "bank_shdj='" + ((rs.getString("bank_shdj") == null) ? "" : rs.getString("bank_shdj")) + "'," + 
              "bank_zh='" + ((rs.getString("bank_zh") == null) ? "" : rs.getString("bank_zh")) + "'";
            updateSql = String.valueOf(updateSql) + " where bank_ID='" + bank_ID + "'";
          } 
        } 
        rs.close();
        if (!"".equals(updateSql)) {
          IO2File.printFile("北京银行特惠商户修改sql：" + updateSql, "特惠商户", 3);
          base.executeUpdate(updateSql);
        } 
        Long bank_thsh_id = Long.valueOf(0L);
        dataSql = "select bank_thsh_id from bank_thsh where bank_ID='" + bank_ID + "'";
        IO2File.printFile("获得recordId：" + dataSql, "特惠商户", 3);
        rs = base.executeQuery(dataSql);
        if (rs.next())
          bank_thsh_id = Long.valueOf(rs.getLong(1)); 
        rs.close();
        dataSql = "select " + xiugaiSub + "_owner," + xiugaiSub + "_date," + xiugaiSub + "_org,bank_dmdz,bank_dmdh,bank_xh," + 
          xiugaiSub + "_foreignkey from " + xiugaiSub + " where " + xiugaiSub + "_foreignkey=" + recordId;
        IO2File.printFile("删除子表中数据：delete from bank_thshxz_zb where bank_thshxz_zb_foreignkey=" + bank_thsh_id, "特惠商户", 3);
        base.executeSQL("delete from bank_thshxz_zb where bank_thshxz_zb_foreignkey=" + bank_thsh_id);
        List<String> sqlList = new ArrayList<String>();
        rs = base.executeQuery(dataSql);
        while (rs.next())
          sqlList.add("insert into bank_thshxz_zb (bank_thshxz_zb_id,bank_thshxz_zb_owner,bank_thshxz_zb_date,bank_thshxz_zb_org,bank_dmdz,bank_dmdh,bank_xh,bank_thshxz_zb_foreignkey) values(hibernate_sequence.nextval," + (
              
              (rs.getString(xiugaiSub + "_owner") == null) ? "" : rs.getString(xiugaiSub + "_owner")) + "," + 
              "sysdate," + (
              (rs.getString(xiugaiSub + "_org") == null) ? "" : rs.getString(xiugaiSub + "_org")) + "," + 
              "'" + ((rs.getString("bank_dmdz") == null) ? "" : rs.getString("bank_dmdz")) + "'," + 
              "'" + ((rs.getString("bank_dmdh") == null) ? "" : rs.getString("bank_dmdh")) + "',0," + 
              bank_thsh_id + ") "); 
        rs.close();
        for (int i = 0; i < sqlList.size(); i++) {
          IO2File.printFile("北京银行特惠商户子表修改sql：" + (String)sqlList.get(i), "特惠商户", 3);
          base.addBatch(sqlList.get(i));
        } 
        base.executeBatch();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
    return result;
  }
}
