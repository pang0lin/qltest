package com.js.oa.form.pengchi;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class WorkflowForPZBL extends Workflow {
  public String complete(HttpServletRequest request) {
    List<Map<String, Object>> zblist = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> mxlist = new ArrayList<Map<String, Object>>();
    String result = super.complete(request);
    String recordId = request.getParameter("recordId");
    String userAccount = request.getSession().getAttribute("userAccount").toString();
    String sql = "SELECT jst_3460_id,gys,zzpd,ckbm,CONCAT(YEAR(str_to_date(IFNULL(sqrq,now()),'%Y-%m-%d'))-1900,DATE_FORMAT(str_to_date(IFNULL(sqrq,now()),'%Y-%m-%d'),'%j')) as sqrq, CONCAT(YEAR(now())-1900,DAYOFYEAR(now())) as today,DATE_FORMAT(NOW(),'%H%i%s') as time from jst_3460   where jst_3460_id=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SYEDUS", "OA");
        map.put("SYEDBT", rs.getString("jst_3460_id"));
        map.put("SYEDTN", "0");
        map.put("SYEDLN", Integer.valueOf(0));
        if (rs.getString("zzpd").equals("特采") || rs.getString("zzpd").equals("分选")) {
          map.put("SYEDCT", "OM");
        } else if (rs.getString("zzpd").equals("退货")) {
          map.put("SYEDCT", "OJ");
        } 
        map.put("SYTYTN", "850");
        map.put("SYEDFT", " ");
        map.put("SYEDDT", "0");
        map.put("SYDRIN", "1");
        map.put("SYEDDL", Integer.valueOf(0));
        map.put("SYEDSP", " ");
        map.put("SYPNID", " ");
        map.put("SYTNAC", "A");
        String ddgsbm = getInfo(rs.getString("ckbm"), "ddgs");
        map.put("SYKCOO", ddgsbm);
        map.put("SYDOCO", " ");
        if (rs.getString("zzpd").equals("特采") || rs.getString("zzpd").equals("分选")) {
          map.put("SYDCTO", "OM");
        } else if (rs.getString("zzpd").equals("退货")) {
          map.put("SYDCTO", "OJ");
        } 
        map.put("SYSFXO", "000");
        map.put("SYMCU", rs.getString("ckbm"));
        map.put("SYOKCO", " ");
        map.put("SYOORN", " ");
        map.put("SYOCTO", " ");
        map.put("SYRKCO", " ");
        map.put("SYRORN", " ");
        map.put("SYRCTO", " ");
        String gysbm = getInfo(rs.getString("gys"), "gys");
        map.put("SYAN8", gysbm);
        map.put("SYSHAN", Integer.valueOf(0));
        map.put("SYDRQJ", Long.valueOf(rs.getLong("sqrq")));
        map.put("SYTRDJ", Long.valueOf(rs.getLong("today")));
        map.put("SYPDDJ", Integer.valueOf(0));
        map.put("SYOPDJ", Integer.valueOf(0));
        map.put("SYADDJ", Integer.valueOf(0));
        map.put("SYCNDJ", Integer.valueOf(0));
        map.put("SYPEFJ", Integer.valueOf(0));
        map.put("SYPPDJ", Integer.valueOf(0));
        map.put("SYPSDJ", Integer.valueOf(0));
        map.put("SYVR01", " ");
        map.put("SYVR02", " ");
        map.put("SYDEL1", " ");
        map.put("SYDEL2", " ");
        map.put("SYRMK", " ");
        map.put("SYDESC", " ");
        map.put("SYINMG", " ");
        map.put("SYASN", " ");
        map.put("SYPRGP", " ");
        map.put("SYPTC", " ");
        map.put("SYEXR1", " ");
        map.put("SYTXA1", " ");
        map.put("SYTXCT", " ");
        map.put("SYHOLD", " ");
        map.put("SYATXT", " ");
        map.put("SYINVC", Integer.valueOf(0));
        map.put("SYNTR", " ");
        map.put("SYCNID", " ");
        map.put("SYFRTH", " ");
        map.put("SYZON", " ");
        map.put("SYANBY", Integer.valueOf(0));
        map.put("SYANCR", Integer.valueOf(0));
        map.put("SYMOT", " ");
        map.put("SYCOT", " ");
        map.put("SYRCD", " ");
        map.put("SYFRTC", " ");
        map.put("SYFUF1", "Y");
        map.put("SYFUF2", "Y");
        map.put("SYOTOT", Integer.valueOf(0));
        map.put("SYPCRT", Integer.valueOf(0));
        map.put("SYRTNR", " ");
        map.put("SYWUMD", " ");
        map.put("SYVUMD", " ");
        map.put("SYPURG", " ");
        map.put("SYLGCT", " ");
        map.put("SYPROM", "1");
        map.put("SYMATY", " ");
        map.put("SYOSTS", " ");
        map.put("SYAVCH", "N");
        map.put("SYPRPY", " ");
        map.put("SYCRMD", " ");
        map.put("SYPRP5", " ");
        map.put("SYARTG", " ");
        map.put("SYCORD", Integer.valueOf(0));
        map.put("SYCRRM", "F");
        map.put("SYCRCD", "CNY");
        map.put("SYCRR", Integer.valueOf(0));
        map.put("SYLNGP", " ");
        map.put("SYFAP", Integer.valueOf(0));
        map.put("SYORBY", " ");
        map.put("SYTKBY", " ");
        map.put("SYURCD", " ");
        map.put("SYURDT", Integer.valueOf(0));
        map.put("SYURAT", Integer.valueOf(0));
        map.put("SYURAB", Integer.valueOf(0));
        map.put("SYURRF", " ");
        map.put("SYTORG", userAccount);
        map.put("SYUSER", "JDE");
        map.put("SYPID", "OA");
        map.put("SYJOBN", "JDEWEB");
        map.put("SYUPMJ", Long.valueOf(rs.getLong("today")));
        map.put("SYTDAY", Long.valueOf(rs.getLong("time")));
        map.put("SYVR03", " ");
        map.put("SYRSHT", Integer.valueOf(0));
        map.put("SYMKFR", Integer.valueOf(0));
        map.put("SYDRQT", Integer.valueOf(0));
        map.put("SYDOC1", Integer.valueOf(0));
        map.put("SYDCT4", " ");
        map.put("SYBTAN", Integer.valueOf(0));
        map.put("SYBCRC", " ");
        map.put("SYPOHP01", " ");
        map.put("SYPOHP02", " ");
        map.put("SYPOHP03", " ");
        map.put("SYPOHP04", " ");
        map.put("SYPOHP05", " ");
        map.put("SYPOHP06", " ");
        map.put("SYPOHP07", " ");
        map.put("SYPOHP08", " ");
        map.put("SYPOHP09", " ");
        map.put("SYPOHP10", " ");
        map.put("SYPOHP11", " ");
        map.put("SYPOHP12", " ");
        map.put("SYPOHC01", " ");
        map.put("SYPOHC02", " ");
        map.put("SYPOHC03", " ");
        map.put("SYPOHC04", " ");
        map.put("SYPOHC05", " ");
        map.put("SYPOHC06", " ");
        map.put("SYPOHC07", " ");
        map.put("SYPOHC08", " ");
        map.put("SYPOHC09", " ");
        map.put("SYPOHC10", " ");
        map.put("SYPOHC11", " ");
        map.put("SYPOHC12", " ");
        map.put("SYPOHD01", Integer.valueOf(0));
        map.put("SYPOHD02", Integer.valueOf(0));
        map.put("SYPOHAB01", Integer.valueOf(0));
        map.put("SYPOHAB02", Integer.valueOf(0));
        map.put("SYCUKID", Integer.valueOf(0));
        map.put("SYPOHP13", " ");
        map.put("SYPOHU01", "");
        map.put("SYPOHU02", "");
        map.put("SYRETI", " ");
        map.put("SYCLASS01", " ");
        map.put("SYCLASS02", " ");
        map.put("SYCLASS03", " ");
        map.put("SYCLASS04", " ");
        map.put("SYCLASS05", " ");
        zblist.add(map);
      } 
      rs.close();
      pstmt.close();
      String sql2 = "SELECT ckbm,gys,gysmcqc,lh,dw,sl,fy,kk,zzpd,CONCAT(YEAR(STR_TO_DATE(IFNULL(yqrq,NOW()),'%Y-%m-%d'))-1900,DATE_FORMAT(STR_TO_DATE(IFNULL(yqrq,NOW()),'%Y-%m-%d'),'%j')) AS yqrqCONCAT(YEAR(now())-1900,DATE_FORMAT(now(),'%j')) as today,DATE_FORMAT(NOW(),'%H%i%s') as time from jst_3460  WHERE jst_3460_id=?";
      pstmt = conn.prepareStatement(sql2);
      pstmt.setString(1, recordId);
      rs2 = pstmt.executeQuery();
      while (rs2.next()) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("SZEDUS", "OA");
        map.put("SZEDBT", recordId);
        map.put("SZEDTN", "0");
        map.put("SZEDLN", Integer.valueOf(1000));
        map.put("SZEDCT", " ");
        map.put("SZTYTN", "850");
        map.put("SZEDFT", " ");
        map.put("SZEDDT", "0");
        map.put("SZDRIN", "1");
        map.put("SZEDDL", "0");
        map.put("SZEDSP", " ");
        map.put("SZPNID", " ");
        map.put("SZTNAC", "A");
        String ddgsbm = getInfo(rs2.getString("ckbm"), "ddgs");
        map.put("SZKCOO", ddgsbm);
        map.put("SZDOCO", "0");
        map.put("SZDCTO", "0L");
        map.put("SZSFXO", "00");
        map.put("SZLNID", "1000");
        map.put("SZMCU", rs2.getString("ckbm"));
        map.put("SZCO", " ");
        map.put("SZOKCO", " ");
        map.put("SZOORN", " ");
        map.put("SZOCTO", " ");
        map.put("SZOGNO", "0");
        map.put("SZRKCO", " ");
        map.put("SZRORN", " ");
        map.put("SZRCTO", " ");
        map.put("SZRLLN", "0");
        map.put("SZDMCT", " ");
        map.put("SZDMCS", "0");
        map.put("SZBALU", "N");
        String gysbm = getInfo(rs2.getString("gysmcqc"), "gys");
        map.put("SZAN8", gysbm);
        map.put("SZSHAN", "0");
        map.put("SZDRQJ", Long.valueOf(rs.getLong("yqrq")));
        map.put("SZTRDJ", Long.valueOf(rs2.getLong("today")));
        map.put("SZPDDJ", "0");
        map.put("SZOPDJ", "0");
        map.put("SZADDJ", "0");
        map.put("SZCNDJ", "0");
        map.put("SZPEFJ", "0");
        map.put("SZPPDJ", "0");
        map.put("SZPSDJ", "0");
        map.put("SZDSVJ", "0");
        map.put("SZDGL", "0");
        map.put("SZPN", "0");
        map.put("SZVR01", " ");
        map.put("SZVR02", " ");
        String dxmh = getInfo(rs2.getString("lh"), "dxmh");
        map.put("SZITM", dxmh);
        map.put("SZLITM", rs2.getString("lh"));
        String jlh = getInfo(rs2.getString("lh"), "dsxmh");
        map.put("SZAITM", jlh);
        map.put("SZLOCN", " ");
        map.put("SZLOTN", " ");
        map.put("SZFRGD", " ");
        map.put("SZTHGD", " ");
        map.put("SZFRMP", "0");
        map.put("SZTHRP", "0");
        map.put("SZDSC1", " ");
        map.put("SZDSC2", " ");
        map.put("SZLNTY", "C");
        map.put("SZNXTR", " ");
        map.put("SZLTTR", " ");
        map.put("SZRLIT", " ");
        map.put("SZPDS1", " ");
        map.put("SZPDS2", " ");
        map.put("SZPDS3", " ");
        map.put("SZPDS4", " ");
        map.put("SZPDS5", " ");
        map.put("SZPDP1", " ");
        map.put("SZPDP2", " ");
        map.put("SZPDP3", " ");
        map.put("SZPDP4", " ");
        map.put("SZPDP5", " ");
        String dw = getInfo(rs2.getString("lh"), "dw");
        map.put("SZUOM", dw);
        String sl = String.valueOf(rs2.getLong("sl") * 10000L);
        if (!sl.startsWith("-"))
          sl = "-" + sl; 
        map.put("SZUORG", sl);
        map.put("SZUCHG", "0");
        map.put("SZUOPN", "0");
        map.put("SZUREC", "0");
        map.put("SZCREC", "0");
        map.put("SZURLV", "0");
        map.put("SZOTQY", " ");
        map.put("SZPRRC", Double.valueOf(((rs2.getFloat("fy") + rs2.getFloat("kk")) / rs2.getFloat("sl")) * 10000.0D));
        map.put("SZAEXP", Double.valueOf((rs2.getFloat("fy") + rs2.getFloat("kk")) * 100.0D));
        map.put("SZACHG", "0");
        map.put("SZAOPN", "0");
        map.put("SZAREC", "0");
        map.put("SZARLV", "0");
        map.put("SZFTN1", "0");
        map.put("SZTRLV", "0");
        map.put("SZPROV", " ");
        map.put("SZAMC3", "0");
        map.put("SZECST", "0");
        map.put("SZCSTO", " ");
        map.put("SZCSMP", " ");
        map.put("SZINMG", " ");
        map.put("SZASN", " ");
        map.put("SZPRGR", " ");
        map.put("SZCLVL", " ");
        map.put("SZCATN", " ");
        map.put("SZDSPR", "0");
        map.put("SZPTC", " ");
        map.put("SZTX", " ");
        map.put("SZEXR1", " ");
        map.put("SZTXA1", " ");
        map.put("SZATXT", " ");
        map.put("SZCNID", " ");
        map.put("SZCDCD", " ");
        map.put("SZNTR", " ");
        map.put("SZFRTH", " ");
        map.put("SZFRTC", " ");
        map.put("SZZON", " ");
        map.put("SZFRAT", " ");
        map.put("SZRATT", "F");
        map.put("SZANBY", "0");
        map.put("SZANCR", "0");
        map.put("SZMOT", " ");
        map.put("SZCOT", " ");
        map.put("SZSHCM", " ");
        map.put("SZSHCN", " ");
        map.put("SZUOM1", " ");
        map.put("SZPQOR", "0");
        map.put("SZUOM2", " ");
        map.put("SZSQOR", "0");
        map.put("SZUOM3", " ");
        map.put("SZITWT", "0");
        map.put("SZWTUM", " ");
        map.put("SZITVL", "0");
        map.put("SZVLUM", " ");
        map.put("SZGLC", " ");
        map.put("SZCTRY", "20");
        map.put("SZFY", "0");
        map.put("SZSTTS", "S");
        map.put("SZRCD", " ");
        map.put("SZFUF1", "Y");
        map.put("SZFUF2", "Y");
        map.put("SZGRWT", "0");
        map.put("SZGWUM", " ");
        map.put("SZLT", "AA");
        map.put("SZANI", " ");
        map.put("SZAID", " ");
        map.put("SZOMCU", " ");
        map.put("SZOBJ", " ");
        map.put("SZSUB", " ");
        map.put("SZSBLT", " ");
        map.put("SZSBL", " ");
        map.put("SZASID", " ");
        map.put("SZCCMP", "0");
        map.put("SZTAG", " ");
        map.put("SZWR01", " ");
        map.put("SZPL", " ");
        map.put("SZELEV", " ");
        map.put("SZR001", " ");
        map.put("SZRTNR", " ");
        map.put("SZLCOD", " ");
        map.put("SZPURG", " ");
        map.put("SZPROM", "1");
        map.put("SZFNLP", " ");
        map.put("SZAVCH", " ");
        map.put("SZPRPY", " ");
        map.put("SZUNCD", " ");
        map.put("SZMATY", " ");
        map.put("SZRTGC", " ");
        map.put("SZRCPF", " ");
        map.put("SZPS01", " ");
        map.put("SZPS02", " ");
        map.put("SZPS03", " ");
        map.put("SZPS04", " ");
        map.put("SZPS05", " ");
        map.put("SZPS06", " ");
        map.put("SZPS07", " ");
        map.put("SZPS08", " ");
        map.put("SZPS09", " ");
        map.put("SZPS10", " ");
        map.put("SZCRMD", " ");
        map.put("SZARTG", " ");
        map.put("SZCORD", "0");
        if (rs2.getString("zzpd").equals("特采") || rs2.getString("zzpd").equals("分选")) {
          map.put("SZCHDT", "OM");
        } else if (rs2.getString("zzpd").equals("退货")) {
          map.put("SZCHDT", "OJ");
        } 
        map.put("SZDOCC", "0");
        map.put("SZCHLN", "0");
        map.put("SZCRCD", " ");
        map.put("SZCRR", "0");
        map.put("SZFRRC", "0");
        map.put("SZFEA", "0");
        map.put("SZFUC", "0");
        map.put("SZFEC", "0");
        map.put("SZFCHG", "0");
        map.put("SZFAP", "0");
        map.put("SZFREC", "0");
        map.put("SZURCD", "0");
        map.put("SZURDT", "0");
        map.put("SZURAT", "0");
        map.put("SZURAB", "0");
        map.put("SZURRF", " ");
        map.put("SZTORG", " ");
        map.put("SZUSER", "JDE");
        map.put("SZPID", "OA");
        map.put("SZJOBN", "OA");
        map.put("SZUPMJ", Long.valueOf(rs2.getLong("today")));
        map.put("SZTDAY", Long.valueOf(rs2.getLong("time")));
        map.put("SZVR05", " ");
        map.put("SZVR04", " ");
        map.put("SZVR03", " ");
        map.put("SZSHPN", "0");
        map.put("SZSHMT", " ");
        map.put("SZRSHT", "0");
        map.put("SZPRJM", "0");
        map.put("SZOSFX", " ");
        map.put("SZMKFR", "0");
        map.put("SZMERL", " ");
        map.put("SZMCLN", "0");
        map.put("SZMACT", " ");
        map.put("SZLDNM", "0");
        map.put("SZKTLN", "0");
        map.put("SZFTRL", "0");
        map.put("SZDUAL", " ");
        map.put("SZDRQT", "0");
        map.put("SZDLEJ", "0");
        map.put("SZCTAM", "0");
        map.put("SZCPNT", "0");
        map.put("SZCHT", "0");
        map.put("SZCHRT", "0");
        map.put("SZCHRS", " ");
        map.put("SZCHRD", "0");
        map.put("SZCHMJ", "0");
        map.put("SZCHID", " ");
        map.put("SZBTAN", "0");
        map.put("SZBCRC", " ");
        map.put("SZPMTN", " ");
        map.put("SZUKID", "0");
        map.put("SZPODC01", " ");
        map.put("SZPODC02", " ");
        map.put("SZPODC03", " ");
        map.put("SZPODC04", " ");
        map.put("SZJBCD", " ");
        map.put("SZSRQTY", "0");
        map.put("SZSRUOM", " ");
        map.put("SZPMPN", " ");
        map.put("SZPNS", "0");
        mxlist.add(map);
      } 
      rs2.close();
      pstmt.close();
      conn.close();
    } catch (Exception e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    int cnt = 0;
    int cnt2 = 0;
    if (zblist.size() > 0)
      cnt = insertTable(zblist); 
    if (mxlist.size() > 0)
      cnt2 = insertMxTable(mxlist); 
    if (cnt == 0) {
      System.out.println("插入中间表-主表失败(可能原因连接报错)");
    } else if (cnt == zblist.size()) {
      System.out.println("全部插入中间表-主表成功（共" + cnt + "条数据）");
    } else {
      System.out.println("部分插入中间表-主表（共计" + zblist.size() + "条数据，插入成功" + cnt + "条）");
    } 
    if (cnt2 == 0) {
      System.out.println("插入中间表-明细表失败(可能原因连接报错)");
    } else if (cnt == mxlist.size()) {
      System.out.println("全部插入中间表-明细表成功（共" + cnt + "条数据）");
    } else {
      System.out.println("部分插入中间表-明细表（共计" + mxlist.size() + "条数据，插入成功" + cnt + "条）");
    } 
    return result;
  }
  
  private int insertTable(List<Map<String, Object>> list) {
    int cnt = 0;
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      StringBuffer sqlBuffer = new StringBuffer();
      for (Map<String, Object> map : list) {
        sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into PRODDTA.F4301Z1@jdedblink(SYEDUS,SYEDBT,SYEDTN,SYEDLN,SYEDCT,SYTYTN,SYEDFT,SYEDDT,");
        sqlBuffer.append("SYDRIN,SYEDDL,SYEDSP,SYPNID,SYTNAC,SYKCOO,SYDOCO,SYDCTO,");
        sqlBuffer.append("SYSFXO,SYMCU,SYOKCO,SYOORN,SYOCTO,SYRKCO,SYRORN,SYRCTO,");
        sqlBuffer.append("SYAN8,SYSHAN,SYDRQJ,SYTRDJ,SYPDDJ,SYOPDJ,SYADDJ,SYCNDJ,");
        sqlBuffer.append("SYPEFJ,SYPPDJ,SYPSDJ,SYVR01,SYVR02,SYDEL1,SYDEL2,SYRMK,");
        sqlBuffer.append("SYDESC,SYINMG,SYASN,SYPRGP,SYPTC,SYEXR1,SYTXA1,SYTXCT,");
        sqlBuffer.append("SYHOLD,SYATXT,SYINVC,SYNTR,SYCNID,SYFRTH,SYZON,SYANBY,");
        sqlBuffer.append("SYANCR,SYMOT,SYCOT,SYRCD,SYFRTC,SYFUF1,SYFUF2,SYOTOT,");
        sqlBuffer.append("SYPCRT,SYRTNR,SYWUMD,SYVUMD,SYPURG,SYLGCT,SYPROM,SYMATY,");
        sqlBuffer.append("SYOSTS,SYAVCH,SYPRPY,SYCRMD,SYPRP5,SYARTG,SYCORD,SYCRRM,");
        sqlBuffer.append("SYCRCD,SYCRR,SYLNGP,SYFAP,SYORBY,SYTKBY,SYURCD,SYURDT,");
        sqlBuffer.append("SYURAT,SYURAB,SYURRF,SYTORG,SYUSER,SYPID,SYJOBN,SYUPMJ,");
        sqlBuffer.append("SYTDAY,SYVR03,SYRSHT,SYMKFR,SYDRQT,SYDOC1,SYDCT4,SYBTAN,");
        sqlBuffer.append("SYBCRC,SYPOHP01,SYPOHP02,SYPOHP03,SYPOHP04,SYPOHP05,SYPOHP06,SYPOHP07,");
        sqlBuffer.append("SYPOHP08,SYPOHP09,SYPOHP10,SYPOHP11,SYPOHP12,SYPOHC01,SYPOHC02,SYPOHC03,");
        sqlBuffer.append("SYPOHC04,SYPOHC05,SYPOHC06,SYPOHC07,SYPOHC08,SYPOHC09,SYPOHC10,SYPOHC11,");
        sqlBuffer.append("SYPOHC12,SYPOHD01,SYPOHD02,SYPOHAB01,SYPOHAB02,SYCUKID,SYPOHP13,SYPOHU01,");
        sqlBuffer.append("SYPOHU02,SYRETI,SYCLASS01,SYCLASS02,SYCLASS03,SYCLASS04,SYCLASS05) values( ");
        sqlBuffer.append("'" + map.get("SYEDUS") + "','" + map.get("SYEDBT") + "','" + map.get("SYEDTN") + "','" + map.get("SYEDLN") + "',");
        sqlBuffer.append("'" + map.get("SYEDCT") + "','" + map.get("SYTYTN") + "','" + map.get("SYEDFT") + "','" + map.get("SYEDDT") + "',");
        sqlBuffer.append("'" + map.get("SYDRIN") + "','" + map.get("SYEDDL") + "','" + map.get("SYEDSP") + "','" + map.get("SYPNID") + "',");
        sqlBuffer.append("'" + map.get("SYTNAC") + "','" + map.get("SYKCOO") + "','" + map.get("SYDOCO") + "','" + map.get("SYDCTO") + "',");
        sqlBuffer.append("'" + map.get("SYSFXO") + "','" + map.get("SYMCU") + "','" + map.get("SYOKCO") + "','" + map.get("SYOORN") + "',");
        sqlBuffer.append("'" + map.get("SYOCTO") + "','" + map.get("SYRKCO") + "','" + map.get("SYRORN") + "','" + map.get("SYRCTO") + "',");
        sqlBuffer.append("'" + map.get("SYAN8") + "','" + map.get("SYSHAN") + "','" + map.get("SYDRQJ") + "','" + map.get("SYTRDJ") + "',");
        sqlBuffer.append("'" + map.get("SYPDDJ") + "','" + map.get("SYOPDJ") + "','" + map.get("SYADDJ") + "','" + map.get("SYCNDJ") + "',");
        sqlBuffer.append("'" + map.get("SYPEFJ") + "','" + map.get("SYPPDJ") + "','" + map.get("SYPSDJ") + "','" + map.get("SYVR01") + "',");
        sqlBuffer.append("'" + map.get("SYVR02") + "','" + map.get("SYDEL1") + "','" + map.get("SYDEL2") + "','" + map.get("SYRMK") + "',");
        sqlBuffer.append("'" + map.get("SYDESC") + "','" + map.get("SYINMG") + "','" + map.get("SYASN") + "','" + map.get("SYPRGP") + "',");
        sqlBuffer.append("'" + map.get("SYPTC") + "','" + map.get("SYEXR1") + "','" + map.get("SYTXA1") + "','" + map.get("SYTXCT") + "',");
        sqlBuffer.append("'" + map.get("SYHOLD") + "','" + map.get("SYATXT") + "','" + map.get("SYINVC") + "','" + map.get("SYNTR") + "',");
        sqlBuffer.append("'" + map.get("SYCNID") + "','" + map.get("SYFRTH") + "','" + map.get("SYZON") + "','" + map.get("SYANBY") + "',");
        sqlBuffer.append("'" + map.get("SYANCR") + "','" + map.get("SYMOT") + "','" + map.get("SYCOT") + "','" + map.get("SYRCD") + "',");
        sqlBuffer.append("'" + map.get("SYFRTC") + "','" + map.get("SYFUF1") + "','" + map.get("SYFUF2") + "','" + map.get("SYOTOT") + "',");
        sqlBuffer.append("'" + map.get("SYPCRT") + "','" + map.get("SYRTNR") + "','" + map.get("SYWUMD") + "','" + map.get("SYVUMD") + "',");
        sqlBuffer.append("'" + map.get("SYPURG") + "','" + map.get("SYLGCT") + "','" + map.get("SYPROM") + "','" + map.get("SYMATY") + "',");
        sqlBuffer.append("'" + map.get("SYOSTS") + "','" + map.get("SYAVCH") + "','" + map.get("SYPRPY") + "','" + map.get("SYCRMD") + "',");
        sqlBuffer.append("'" + map.get("SYPRP5") + "','" + map.get("SYARTG") + "','" + map.get("SYCORD") + "','" + map.get("SYCRRM") + "',");
        sqlBuffer.append("'" + map.get("SYCRCD") + "','" + map.get("SYCRR") + "','" + map.get("SYLNGP") + "','" + map.get("SYFAP") + "',");
        sqlBuffer.append("'" + map.get("SYORBY") + "','" + map.get("SYTKBY") + "','" + map.get("SYURCD") + "','" + map.get("SYURDT") + "',");
        sqlBuffer.append("'" + map.get("SYURAT") + "','" + map.get("SYURAB") + "','" + map.get("SYURRF") + "','" + map.get("SYTORG") + "',");
        sqlBuffer.append("'" + map.get("SYUSER") + "','" + map.get("SYPID") + "','" + map.get("SYJOBN") + "','" + map.get("SYUPMJ") + "',");
        sqlBuffer.append("'" + map.get("SYTDAY") + "','" + map.get("SYVR03") + "','" + map.get("SYRSHT") + "','" + map.get("SYMKFR") + "',");
        sqlBuffer.append("'" + map.get("SYDRQT") + "','" + map.get("SYDOC1") + "','" + map.get("SYDCT4") + "','" + map.get("SYBTAN") + "',");
        sqlBuffer.append("'" + map.get("SYBCRC") + "','" + map.get("SYPOHP01") + "','" + map.get("SYPOHP02") + "','" + map.get("SYPOHP03") + "',");
        sqlBuffer.append("'" + map.get("SYPOHP04") + "','" + map.get("SYPOHP05") + "','" + map.get("SYPOHP06") + "','" + map.get("SYPOHP07") + "',");
        sqlBuffer.append("'" + map.get("SYPOHP08") + "','" + map.get("SYPOHP09") + "','" + map.get("SYPOHP10") + "','" + map.get("SYPOHP11") + "',");
        sqlBuffer.append("'" + map.get("SYPOHP12") + "','" + map.get("SYPOHC01") + "','" + map.get("SYPOHC02") + "','" + map.get("SYPOHC03") + "',");
        sqlBuffer.append("'" + map.get("SYPOHC04") + "','" + map.get("SYPOHC05") + "','" + map.get("SYPOHC06") + "','" + map.get("SYPOHC07") + "',");
        sqlBuffer.append("'" + map.get("SYPOHC08") + "','" + map.get("SYPOHC09") + "','" + map.get("SYPOHC10") + "','" + map.get("SYPOHC11") + "',");
        sqlBuffer.append("'" + map.get("SYPOHC12") + "','" + map.get("SYPOHD01") + "','" + map.get("SYPOHD02") + "','" + map.get("SYPOHAB01") + "',");
        sqlBuffer.append("'" + map.get("SYPOHAB02") + "','" + map.get("SYCUKID") + "','" + map.get("SYPOHP13") + "','" + map.get("SYPOHU01") + "',");
        sqlBuffer.append("'" + map.get("SYPOHU02") + "','" + map.get("SYRETI") + "','" + map.get("SYCLASS01") + "','" + map.get("SYCLASS02") + "',");
        sqlBuffer.append("'" + map.get("SYCLASS03") + "','" + map.get("SYCLASS04") + "','" + map.get("SYCLASS05") + "')");
        System.out.println("异常处理主表回写sql:" + sqlBuffer.toString());
        pstmt = conn.prepareStatement(sqlBuffer.toString());
        try {
          boolean boo = pstmt.execute();
          if (boo)
            cnt++; 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        pstmt.close();
      } 
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return cnt;
  }
  
  private int insertMxTable(List<Map<String, Object>> list) {
    int cnt = 0;
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      StringBuffer sqlBuffer = new StringBuffer();
      for (Map<String, Object> map : list) {
        sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into PRODDTA.F4311Z1@jdedblink(SZEDUS,SZEDBT,SZEDTN,SZEDLN,SZEDCT,SZTYTN,SZEDFT,SZEDDT,");
        sqlBuffer.append("SZDRIN,SZEDDL,SZEDSP,SZPNID,SZTNAC,SZKCOO,SZDOCO,SZDCTO,");
        sqlBuffer.append("SZSFXO,SZLNID,SZMCU,SZCO,SZOKCO,SZOORN,SZOCTO,SZOGNO,");
        sqlBuffer.append("SZRKCO,SZRORN,SZRCTO,SZRLLN,SZDMCT,SZDMCS,SZBALU,SZAN8,");
        sqlBuffer.append("SZSHAN,SZDRQJ,SZTRDJ,SZPDDJ,SZOPDJ,SZADDJ,SZCNDJ,SZPEFJ,");
        sqlBuffer.append("SZPPDJ,SZPSDJ,SZDSVJ,SZDGL,SZPN,SZVR01,SZVR02,SZITM,SZLITM,SZAITM,");
        sqlBuffer.append("SZLOCN,SZLOTN,SZFRGD,SZTHGD,SZFRMP,SZTHRP,SZDSC1,SZDSC2,SZLNTY,SZNXTR,");
        sqlBuffer.append("SZLTTR,SZRLIT,SZPDS1,SZPDS2,SZPDS3,SZPDS4,SZPDS5,SZPDP1,SZPDP2,SZPDP3,");
        sqlBuffer.append("SZPDP4,SZPDP5,SZUOM,SZUORG,SZUCHG,SZUOPN,SZUREC,SZCREC,SZURLV,SZOTQY,");
        sqlBuffer.append("SZPRRC,SZAEXP,SZACHG,SZAOPN,SZAREC,SZARLV,SZFTN1,SZTRLV,SZPROV,SZAMC3,");
        sqlBuffer.append("SZECST,SZCSTO,SZCSMP,SZINMG,SZASN,SZPRGR,SZCLVL,SZCATN,SZDSPR,SZPTC,");
        sqlBuffer.append("SZTX,SZEXR1,SZTXA1,SZATXT,SZCNID,SZCDCD,SZNTR,SZFRTH,SZFRTC,SZZON,");
        sqlBuffer.append("SZFRAT,SZRATT,SZANBY,SZANCR,SZMOT,SZCOT,SZSHCM,SZSHCN,SZUOM1,SZPQOR,");
        sqlBuffer.append("SZUOM2,SZSQOR,SZUOM3,SZITWT,SZWTUM,SZITVL,SZVLUM,SZGLC,SZCTRY,SZFY,");
        sqlBuffer.append("SZSTTS,SZRCD,SZFUF1,SZFUF2,SZGRWT,SZGWUM,SZLT,SZANI,SZAID,SZOMCU,");
        sqlBuffer.append("SZOBJ,SZSUB,SZSBLT,SZSBL,SZASID,SZCCMP,SZTAG,SZWR01,SZPL,SZELEV,");
        sqlBuffer.append("SZR001,SZRTNR,SZLCOD,SZPURG,SZPROM,SZFNLP,SZAVCH,SZPRPY,SZUNCD,SZMATY,");
        sqlBuffer.append("SZRTGC,SZRCPF,SZPS01,SZPS02,SZPS03,SZPS04,SZPS05,SZPS06,SZPS07,SZPS08,");
        sqlBuffer.append("SZPS09,SZPS10,SZCRMD,SZARTG,SZCORD,SZCHDT,SZDOCC,SZCHLN,SZCRCD,SZCRR,");
        sqlBuffer.append("SZFRRC,SZFEA,\tSZFUC,SZFEC,SZFCHG,SZFAP,SZFREC,SZURCD,SZURDT,SZURAT,");
        sqlBuffer.append("SZURAB,SZURRF,SZTORG,SZUSER,SZPID,SZJOBN,SZUPMJ,SZTDAY,SZVR05,SZVR04,");
        sqlBuffer.append("SZVR03,SZSHPN,SZSHMT,SZRSHT,SZPRJM,SZOSFX,SZMKFR,SZMERL,SZMCLN,SZMACT,");
        sqlBuffer.append("SZLDNM,SZKTLN,SZFTRL,SZDUAL,SZDRQT,SZDLEJ,SZCTAM,SZCPNT,SZCHT,SZCHRT,");
        sqlBuffer.append("SZCHRS,SZCHRD,SZCHMJ,SZCHID,SZBTAN,SZBCRC,SZPMTN,SZUKID,SZPODC01,SZPODC02,");
        sqlBuffer.append("SZPODC03,SZPODC04,SZJBCD,SZSRQTY,SZSRUOM,SZPMPN,SZPNS) values(");
        sqlBuffer.append("'" + map.get("SZEDUS") + "','" + map.get("SZEDBT") + "','" + map.get("SZEDTN") + "','" + map.get("SZEDLN") + "','" + map.get("SZEDCT") + "',");
        sqlBuffer.append("'" + map.get("SZTYTN") + "','" + map.get("SZEDFT") + "','" + map.get("SZEDDT") + "','" + map.get("SZDRIN") + "','" + map.get("SZEDDL") + "',");
        sqlBuffer.append("'" + map.get("SZEDSP") + "','" + map.get("SZPNID") + "','" + map.get("SZTNAC") + "','" + map.get("SZKCOO") + "','" + map.get("SZDOCO") + "',");
        sqlBuffer.append("'" + map.get("SZDCTO") + "','" + map.get("SZSFXO") + "','" + map.get("SZLNID") + "','" + map.get("SZMCU") + "','" + map.get("SZCO") + "',");
        sqlBuffer.append("'" + map.get("SZOKCO") + "','" + map.get("SZOORN") + "','" + map.get("SZOCTO") + "','" + map.get("SZOGNO") + "','" + map.get("SZRKCO") + "',");
        sqlBuffer.append("'" + map.get("SZRORN") + "','" + map.get("SZRCTO") + "','" + map.get("SZRLLN") + "','" + map.get("SZDMCT") + "','" + map.get("SZDMCS") + "',");
        sqlBuffer.append("'" + map.get("SZBALU") + "','" + map.get("SZAN8") + "','" + map.get("SZSHAN") + "','" + map.get("SZDRQJ") + "','" + map.get("SZTRDJ") + "',");
        sqlBuffer.append("'" + map.get("SZPDDJ") + "','" + map.get("SZOPDJ") + "','" + map.get("SZADDJ") + "','" + map.get("SZCNDJ") + "','" + map.get("SZPEFJ") + "',");
        sqlBuffer.append("'" + map.get("SZPPDJ") + "','" + map.get("SZPSDJ") + "','" + map.get("SZDSVJ") + "','" + map.get("SZDGL") + "','" + map.get("SZPN") + "',");
        sqlBuffer.append("'" + map.get("SZVR01") + "','" + map.get("SZVR02") + "','" + map.get("SZITM") + "','" + map.get("SZLITM") + "','" + map.get("SZAITM") + "',");
        sqlBuffer.append("'" + map.get("SZLOCN") + "','" + map.get("SZLOTN") + "','" + map.get("SZFRGD") + "','" + map.get("SZTHGD") + "','" + map.get("SZFRMP") + "',");
        sqlBuffer.append("'" + map.get("SZTHRP") + "','" + map.get("SZDSC1") + "','" + map.get("SZDSC2") + "','" + map.get("SZLNTY") + "','" + map.get("SZNXTR") + "',");
        sqlBuffer.append("'" + map.get("SZLTTR") + "','" + map.get("SZRLIT") + "','" + map.get("SZPDS1") + "','" + map.get("SZPDS2") + "','" + map.get("SZPDS3") + "',");
        sqlBuffer.append("'" + map.get("SZPDS4") + "','" + map.get("SZPDS5") + "','" + map.get("SZPDP1") + "','" + map.get("SZPDP2") + "','" + map.get("SZPDP3") + "',");
        sqlBuffer.append("'" + map.get("SZPDP4") + "','" + map.get("SZPDP5") + "','" + map.get("SZUOM") + "','" + map.get("SZUORG") + "','" + map.get("SZUCHG") + "',");
        sqlBuffer.append("'" + map.get("SZUOPN") + "','" + map.get("SZUREC") + "','" + map.get("SZCREC") + "','" + map.get("SZURLV") + "','" + map.get("SZOTQY") + "',");
        sqlBuffer.append("'" + map.get("SZPRRC") + "','" + map.get("SZAEXP") + "','" + map.get("SZACHG") + "','" + map.get("SZAOPN") + "','" + map.get("SZAREC") + "',");
        sqlBuffer.append("'" + map.get("SZARLV") + "','" + map.get("SZFTN1") + "','" + map.get("SZTRLV") + "','" + map.get("SZPROV") + "','" + map.get("SZAMC3") + "',");
        sqlBuffer.append("'" + map.get("SZECST") + "','" + map.get("SZCSTO") + "','" + map.get("SZCSMP") + "','" + map.get("SZINMG") + "','" + map.get("SZASN") + "',");
        sqlBuffer.append("'" + map.get("SZPRGR") + "','" + map.get("SZCLVL") + "','" + map.get("SZCATN") + "','" + map.get("SZDSPR") + "','" + map.get("SZPTC") + "',");
        sqlBuffer.append("'" + map.get("SZTX") + "','" + map.get("SZEXR1") + "','" + map.get("SZTXA1") + "','" + map.get("SZATXT") + "','" + map.get("SZCNID") + "',");
        sqlBuffer.append("'" + map.get("SZCDCD") + "','" + map.get("SZNTR") + "','" + map.get("SZFRTH") + "','" + map.get("SZFRTC") + "','" + map.get("SZZON") + "',");
        sqlBuffer.append("'" + map.get("SZFRAT") + "','" + map.get("SZRATT") + "','" + map.get("SZANBY") + "','" + map.get("SZANCR") + "','" + map.get("SZMOT") + "',");
        sqlBuffer.append("'" + map.get("SZCOT") + "','" + map.get("SZSHCM") + "','" + map.get("SZSHCN") + "','" + map.get("SZUOM1") + "','" + map.get("SZPQOR") + "',");
        sqlBuffer.append("'" + map.get("SZUOM2") + "','" + map.get("SZSQOR") + "','" + map.get("SZUOM3") + "','" + map.get("SZITWT") + "','" + map.get("SZWTUM") + "',");
        sqlBuffer.append("'" + map.get("SZITVL") + "','" + map.get("SZVLUM") + "','" + map.get("SZGLC") + "','" + map.get("SZCTRY") + "','" + map.get("SZFY") + "',");
        sqlBuffer.append("'" + map.get("SZSTTS") + "','" + map.get("SZRCD") + "','" + map.get("SZFUF1") + "','" + map.get("SZFUF2") + "','" + map.get("SZGRWT") + "',");
        sqlBuffer.append("'" + map.get("SZGWUM") + "','" + map.get("SZLT") + "','" + map.get("SZANI") + "','" + map.get("SZAID") + "','" + map.get("SZOMCU") + "',");
        sqlBuffer.append("'" + map.get("SZOBJ") + "','" + map.get("SZSUB") + "','" + map.get("SZSBLT") + "','" + map.get("SZSBL") + "','" + map.get("SZASID") + "',");
        sqlBuffer.append("'" + map.get("SZCCMP") + "','" + map.get("SZTAG") + "','" + map.get("SZWR01") + "','" + map.get("SZPL") + "','" + map.get("SZELEV") + "',");
        sqlBuffer.append("'" + map.get("SZR001") + "','" + map.get("SZRTNR") + "','" + map.get("SZLCOD") + "','" + map.get("SZPURG") + "','" + map.get("SZPROM") + "',");
        sqlBuffer.append("'" + map.get("SZFNLP") + "','" + map.get("SZAVCH") + "','" + map.get("SZPRPY") + "','" + map.get("SZUNCD") + "','" + map.get("SZMATY") + "',");
        sqlBuffer.append("'" + map.get("SZRTGC") + "','" + map.get("SZRCPF") + "','" + map.get("SZPS01") + "','" + map.get("SZPS02") + "','" + map.get("SZPS03") + "',");
        sqlBuffer.append("'" + map.get("SZPS04") + "','" + map.get("SZPS05") + "','" + map.get("SZPS06") + "','" + map.get("SZPS07") + "','" + map.get("SZPS08") + "',");
        sqlBuffer.append("'" + map.get("SZPS09") + "','" + map.get("SZPS10") + "','" + map.get("SZCRMD") + "','" + map.get("SZARTG") + "','" + map.get("SZCORD") + "',");
        sqlBuffer.append("'" + map.get("SZCHDT") + "','" + map.get("SZDOCC") + "','" + map.get("SZCHLN") + "','" + map.get("SZCRCD") + "','" + map.get("SZCRR") + "',");
        sqlBuffer.append("'" + map.get("SZFRRC") + "','" + map.get("SZFEA") + "','" + map.get("SZFUC") + "','" + map.get("SZFEC") + "','" + map.get("SZFCHG") + "',");
        sqlBuffer.append("'" + map.get("SZFAP") + "','" + map.get("SZFREC") + "','" + map.get("SZURCD") + "','" + map.get("SZURDT") + "','" + map.get("SZURAT") + "',");
        sqlBuffer.append("'" + map.get("SZURAB") + "','" + map.get("SZURRF") + "','" + map.get("SZTORG") + "','" + map.get("SZUSER") + "','" + map.get("SZPID") + "',");
        sqlBuffer.append("'" + map.get("SZJOBN") + "','" + map.get("SZUPMJ") + "','" + map.get("SZTDAY") + "','" + map.get("SZVR05") + "','" + map.get("SZVR04") + "',");
        sqlBuffer.append("'" + map.get("SZVR03") + "','" + map.get("SZSHPN") + "','" + map.get("SZSHMT") + "','" + map.get("SZRSHT") + "','" + map.get("SZPRJM") + "',");
        sqlBuffer.append("'" + map.get("SZOSFX") + "','" + map.get("SZMKFR") + "','" + map.get("SZMERL") + "','" + map.get("SZMCLN") + "','" + map.get("SZMACT") + "',");
        sqlBuffer.append("'" + map.get("SZLDNM") + "','" + map.get("SZKTLN") + "','" + map.get("SZFTRL") + "','" + map.get("SZDUAL") + "','" + map.get("SZDRQT") + "',");
        sqlBuffer.append("'" + map.get("SZDLEJ") + "','" + map.get("SZCTAM") + "','" + map.get("SZCPNT") + "','" + map.get("SZCHT") + "','" + map.get("SZCHRT") + "',");
        sqlBuffer.append("'" + map.get("SZCHRS") + "','" + map.get("SZCHRD") + "','" + map.get("SZCHMJ") + "','" + map.get("SZCHID") + "','" + map.get("SZBTAN") + "',");
        sqlBuffer.append("'" + map.get("SZBCRC") + "','" + map.get("SZPMTN") + "','" + map.get("SZUKID") + "','" + map.get("SZPODC01") + "','" + map.get("SZPODC02") + "',");
        sqlBuffer.append("'" + map.get("SZPODC03") + "','" + map.get("SZPODC04") + "','" + map.get("SZJBCD") + "','" + map.get("SZSRQTY") + "','" + map.get("SZSRUOM") + "',");
        sqlBuffer.append("'" + map.get("SZPMPN") + "','" + map.get("SZPNS") + "')");
        System.out.println("异常处理明细回写sql:" + sqlBuffer.toString());
        pstmt = conn.prepareStatement(sqlBuffer.toString());
        try {
          boolean boo = pstmt.execute();
          if (boo)
            cnt++; 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        pstmt.close();
      } 
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return cnt;
  }
  
  public String getInfo(String para, String flag) {
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String result = "";
    try {
      String sql = "";
      if (flag.equals("ddgs")) {
        sql = "select MCCO from PRODDTA.f0006@jdedblink where TRIM(MCMCU)='" + para + "'";
      } else if (flag.equals("dxmh")) {
        sql = "select IMITM from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + para + "'";
      } else if (flag.equals("dsxmh")) {
        sql = "select IMAITM from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + para + "'";
      } else if (flag.equals("dw")) {
        sql = "select IMUOM1 from PRODDTA.F4101@jdedblink where TRIM(IMLITM)='" + para + "'";
      } else if (flag.equals("gys")) {
        sql = "select ABAN8 from PRODDTA.F0101@jdedblink  where ABAT1='V' and TRIM(ABALPH)='" + para + "'";
      } 
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
}
