package com.js.oa.zky.util.word;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZkyWordData {
  DataSourceUtil util = new DataSourceUtil();
  
  private double defen = 0.0D;
  
  public Map<String, String> getGrxx(String jobNumber, String nd) {
    Map<String, String> grxx = new HashMap<String, String>();
    SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    String[] nowDate = sFormat.format(new Date()).split("-");
    grxx.put("年", nowDate[0]);
    grxx.put("月", nowDate[1]);
    grxx.put("日", nowDate[2]);
    String sql = "select grxx_xm,grxx_zc,grxx_yjs,grxx_sxyrzqk,grxx_fs,grxx_jq,grxx_br,grxx_gmsx,grxx_qr,grxx_wr from t_grxx where grxx_gh='" + 
      jobNumber + "' and grxx_nd='" + nd + "' and grxx_mqzt='2'";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      String[] info = list.get(0);
      grxx.put("姓名", info[0]);
      grxx.put("职称", tranSelect("grxx_zc", "t_grxx").get(info[1]));
      grxx.put("研究所", tranSelect("grxx_yjs", "t_grxx").get(info[2]));
      grxx.put("任职情况", tranSelect("grxx_sxyrzqk", "t_grxx").get(info[3]));
      grxx.put("得分", info[4]);
      grxx.put("杰青", info[5]);
      grxx.put("百人", info[6]);
      grxx.put("冠名", info[7]);
      grxx.put("千人", info[8]);
      grxx.put("万人", info[9]);
      setDefen(Double.valueOf(grxx.get("得分")).doubleValue());
    } 
    grxx.put("保密工作情况", "");
    grxx.put("本人签名", "");
    return grxx;
  }
  
  public List<Map<String, String>> getLwzz(String jobNumber, String year) {
    double df = 0.0D;
    List<Double> lwdf = new ArrayList<Double>();
    List<Map<String, String>> lwzz = new ArrayList<Map<String, String>>();
    String sql = "select lwzz_zl,lwzz_zzlx,lwzz_lwzzmc,lwzz_lwlx,lwzz_lwtm,lwzz_zzrs,lwzz_fs from t_lwzz where lwzz_gh='" + 
      jobNumber + "' and lwzz_nd='" + year + "'  and lwzz_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_lwzz_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTranZ = tranSelect("lwzz_zllx", "t_lwzz");
      Map<String, String> mapTranL = tranSelect("lwzz_lwlx", "t_lwzz");
      for (int j = 0; j < list.size(); j++) {
        String[] info = list.get(j);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", j + 1);
        if ("2".equals(info[0])) {
          map.put("论文、著作名称", info[2]);
          map.put("著作类型", mapTranZ.get(info[1]));
          map.put("刊物类型", "");
          df += Double.valueOf(info[6]).doubleValue();
        } else {
          map.put("论文、著作名称", info[4]);
          map.put("著作类型", "");
          map.put("刊物类型", mapTranL.get(info[3]));
          if (info[3].equals("4") || info[4].equals("5")) {
            lwdf.add(Double.valueOf(info[6]));
          } else {
            df += Double.valueOf(info[6]).doubleValue();
          } 
        } 
        map.put("作者人数", info[5]);
        map.put("得分", info[6]);
        lwzz.add(map);
      } 
    } 
    double hydf = 0.0D;
    if (lwdf.size() > 3)
      for (int j = 0; j < lwdf.size() - 1; j++) {
        for (int k = 0; k < lwdf.size() - j - 1; k++) {
          if (((Double)lwdf.get(k)).doubleValue() < ((Double)lwdf.get(k + 1)).doubleValue()) {
            double temp = ((Double)lwdf.get(k)).doubleValue();
            lwdf.set(k, lwdf.get(k + 1));
            lwdf.set(k + 1, Double.valueOf(temp));
          } 
        } 
      }  
    for (int i = 0; i < lwdf.size() && i < 3; i++)
      hydf += ((Double)lwdf.get(i)).doubleValue(); 
    if (hydf > 24.0D)
      hydf = 24.0D; 
    df = hydf + df;
    double qiandf = 0.0D;
    double qudf = 0.0D;
    setDefen((df + qiandf + qudf) / 3.0D);
    return lwzz;
  }
  
  public List<Map<String, String>> getXshy(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> xshy = new ArrayList<Map<String, String>>();
    String sql = "select xshy_hymc,xshy_hylx,xshy_bglx,xshy_zzrs,xshy_fs from t_xshy where xshy_gh='" + 
      jobNumber + "' and xshy_nd='" + year + "' and xshy_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_xshy_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTranH = tranSelect("xshy_hylx", "t_xshy");
      Map<String, String> mapTranB = tranSelect("xshy_bglx", "t_xshy");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("会议名称", info[0]);
        map.put("会议类型", mapTranH.get(info[1]));
        map.put("报告类型", mapTranB.get(info[2]));
        map.put("作者人数", info[3]);
        map.put("得分", info[4]);
        df += Double.valueOf(info[4]).doubleValue();
        xshy.add(map);
      } 
    } 
    setDefen(myRound(df, 2));
    return xshy;
  }
  
  public List<Map<String, String>> getKyxm(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> kyxm = new ArrayList<Map<String, String>>();
    String sql = "select kyxm_cjxmmc,kyxm_xmlx,kyxm_cjfs,kyxm_fs from t_kyxm where kyxm_gh='" + 
      jobNumber + "' and kyxm_nd='" + year + "' and kyxm_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_kyxm_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTranL = tranSelect("kyxm_xmlx", "t_kyxm");
      Map<String, String> mapTranC = tranSelect("kyxm_cjfs", "t_kyxm");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("承担项目名称", info[0]);
        map.put("类型", mapTranL.get(info[1]));
        map.put("参加方式", mapTranC.get(info[2]));
        map.put("得分", info[3]);
        df += Double.valueOf(info[3]).doubleValue();
        kyxm.add(map);
      } 
    } 
    double gldf = getGlfs(jobNumber, year);
    setDefen(df + gldf);
    return kyxm;
  }
  
  public List<Map<String, String>> getYhdjx(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> yhdjx = new ArrayList<Map<String, String>>();
    String sql = "select yhdjx_hjxmmc,yhdjx_jllb,yhdjx_jlmc,yhdjx_wcrs,yhdjx_fs,yhdjx_bdwwcrpm from t_yhdjx where yhdjx_gh='" + 
      jobNumber + "' and yhdjx_nd='" + year + "' and yhdjx_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_yhdjx_id";
    double fdf = 0.0D;
    double sdf = 0.0D;
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTranL = tranSelect("yhdjx_jllb", "t_yhdjx");
      Map<String, String> mapTranD = tranSelect("yhdjx_jlmc", "t_yhdjx");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("获奖名称", info[0]);
        map.put("类型", mapTranL.get(info[1]));
        map.put("等级", mapTranD.get(info[2]));
        map.put("排名1", info[3]);
        map.put("排名2", info[5]);
        map.put("得分", info[4]);
        String mark = String.valueOf(info[1]) + "_" + info[2];
        if ("1_1".equals(map) || "1_2".equals(map) || "1_3".equals(mark)) {
          fdf += Double.valueOf(info[4]).doubleValue();
        } else {
          sdf += Double.valueOf(info[4]).doubleValue();
        } 
        yhdjx.add(map);
      } 
    } 
    double[] fdfs = getWnjx(jobNumber, year, 5);
    double fdf1 = fdfs[0] * 0.8D;
    double fdf2 = fdfs[1] * 0.64D;
    double fdf3 = fdfs[2] * Math.pow(0.8D, 3.0D);
    double fdf4 = fdfs[3] * Math.pow(0.8D, 4.0D);
    double fdf5 = fdfs[4] * Math.pow(0.8D, 5.0D);
    df += fdf + fdf1 + fdf2 + fdf3 + fdf4 + fdf5;
    double[] sdfs = getWnjx(jobNumber, year, 3);
    double sdf1 = sdfs[0] * 0.8D;
    double sdf2 = sdfs[1] * 0.64D;
    double sdf3 = sdfs[2] * Math.pow(0.8D, 3.0D);
    df += sdf + sdf1 + sdf2 + sdf3;
    setDefen(myRound(df, 2));
    return yhdjx;
  }
  
  public List<Map<String, String>> getZzsbjx(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> zzsbjx = new ArrayList<Map<String, String>>();
    String sql = "select zzsbjx_sbjxmc,zzsbjx_lx,zzsbjx_sftgcp,zzsbjx_wcrspm,zzsbjx_fs,'' from t_zzsbjx where zzsbjx_gh='" + 
      jobNumber + "' and zzsbjx_nd='" + year + "' and zzsbjx_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_zzsbjx_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTranL = tranSelect("zzsbjx_lx", "t_zzsbjx");
      Map<String, String> mapTranC = tranSelect("zzsbjx_sftgcp", "t_zzsbjx");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("申报奖项名称", info[0]);
        map.put("类型", mapTranL.get(info[1]));
        map.put("是否通过初评", mapTranC.get(info[2]));
        map.put("排名1", info[3]);
        map.put("排名2", info[5]);
        map.put("得分", info[4]);
        df += Double.valueOf(info[4]).doubleValue();
        zzsbjx.add(map);
      } 
    } 
    setDefen(myRound(df, 2));
    return zzsbjx;
  }
  
  public List<Map<String, String>> getSbzl(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> sbzl = new ArrayList<Map<String, String>>();
    String sql = "select sbzl_sbzlmc,sbzl_sqh,sbzl_wcrspm,sbzl_fs,'' from t_sbzl where sbzl_gh='" + 
      jobNumber + "' and sbzl_nd='" + year + "' and sbzl_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_sbzl_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("登记名称", info[0]);
        map.put("申请号", info[1]);
        map.put("排名1", info[2]);
        map.put("排名2", info[4]);
        map.put("得分", info[3]);
        df += Double.valueOf(info[3]).doubleValue();
        sbzl.add(map);
      }  
    setDefen(myRound(df, 2));
    return sbzl;
  }
  
  public List<Map<String, String>> getRcpy(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> rcpy = new ArrayList<Map<String, String>>();
    String sql = "select rcpy_skxs,rcpy_zdbsrs,rcpy_zdssrs,rcpy_fs from t_rcpy where rcpy_gh='" + 
      jobNumber + "' and rcpy_nd='" + year + "' and rcpy_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_rcpy_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("授课学时数", info[0]);
        map.put("博士数", info[1]);
        map.put("硕士数", info[2]);
        map.put("得分", info[3]);
        df += Double.valueOf(info[3]).doubleValue();
        rcpy.add(map);
      }  
    setDefen(myRound(df, 2));
    return rcpy;
  }
  
  public List<Map<String, String>> getShrzjz(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> shrzjz = new ArrayList<Map<String, String>>();
    String[] sqls = { "select gjzzrz_gjzzmc,gjzzrz_drzwlb,gjzzrz_rzksrq,gjzzrz_rzjsrq,gjzzrz_drzw,gjzzrz_fs from t_gjzzrz where gjzzrz_gh='" + 
        jobNumber + "' and gjzzrz_nd='" + year + "' and gjzzrz_mqzt='2' order by t_gjzzrz_id", 
        "select gjqkrz_qkmc,gjqkrz_drzwlb,gjqkrz_rzksrq,gjqkrz_rzjsrq,gjqkrz_drzw,gjqkrz_fs from t_gjqkrz where gjqkrz_gh='" + 
        jobNumber + "' and gjqkrz_nd='" + year + "' and gjqkrz_mqzt='2' order by t_gjqkrz_id", 
        "select gjhyrz_hymc,gjhyrz_drzwlb,gjhyrz_rzksrq,gjhyrz_rzjsrq,gjhyrz_drzw,gjhyrz_fs from t_gjhyrz where gjhyrz_gh='" + 
        jobNumber + "' and gjhyrz_nd='" + year + "' and gjhyrz_mqzt='2' order by t_gjhyrz_id" };
    for (int j = 0; j < sqls.length; j++) {
      List<String[]> list = this.util.getListQuery(sqls[j]);
      if (list.size() > 0) {
        Map<String, String> mapTran = new HashMap<String, String>();
        if (j == 0) {
          mapTran = tranSelect("gjzzrz_drzw", "t_gjzzrz");
        } else if (j == 1) {
          mapTran = tranSelect("gjqkrz_drzw", "t_gjqkrz");
        } else {
          mapTran = tranSelect("gjhyrz_drzw", "t_gjhyrz");
        } 
        for (int i = 0; i < list.size(); i++) {
          String[] info = list.get(i);
          Map<String, String> map = new HashMap<String, String>();
          map.put("序号", i + 1);
          map.put("社会任职、兼职名称", info[0]);
          map.put("类型", info[1]);
          map.put("起止日期", String.valueOf(info[2]) + "/" + info[3]);
          map.put("担任职务", mapTran.get(info[4]));
          map.put("得分", info[5]);
          df += Double.valueOf(info[5]).doubleValue();
          shrzjz.add(map);
        } 
      } 
    } 
    setDefen(myRound(df, 2));
    return shrzjz;
  }
  
  public List<Map<String, String>> getCf(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> cf = new ArrayList<Map<String, String>>();
    String sql = "select cf_cfgjdq,cf_lx,cf_cfrq,cf_cfts,cf_fs from t_cf where cf_gh='" + 
      jobNumber + "' and cf_nd='" + year + "' and cf_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_cf_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTran = tranSelect("cf_lx", "t_cf");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("出访国家或地区", info[0]);
        map.put("类型", mapTran.get(info[1]));
        map.put("出访日期", info[2]);
        map.put("出访天数", info[3]);
        map.put("得分", info[4]);
        df += Double.valueOf(info[4]).doubleValue();
        cf.add(map);
      } 
    } 
    setDefen(myRound(df, 2));
    return cf;
  }
  
  public List<Map<String, String>> getLf(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> lf = new ArrayList<Map<String, String>>();
    String sql = "select lf_lfwbxm,lf_lx,lf_lfrq,lf_lfts,lf_fs from t_lf where lf_gh='" + 
      jobNumber + "' and lf_nd='" + year + "' and lf_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_lf_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTran = tranSelect("lf_lx", "t_lf");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("来访外宾姓名、工作单位及国家或地区", info[0]);
        map.put("类型", mapTran.get(info[1]));
        map.put("来访日期", info[2]);
        map.put("来访天数", info[3]);
        map.put("得分", info[4]);
        df += Double.valueOf(info[4]).doubleValue();
        lf.add(map);
      } 
    } 
    setDefen(myRound(df, 2));
    return lf;
  }
  
  public List<Map<String, String>> getGjhz(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> gjhz = new ArrayList<Map<String, String>>();
    String sql = "select gjhz_xmmc,gjhz_lb,gjhz_xmzjf,gjhz_fs from t_gjhz where gjhz_gh='" + 
      jobNumber + "' and gjhz_nd='" + year + "' and gjhz_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_gjhz_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0) {
      Map<String, String> mapTran = tranSelect("gjhz_lb", "t_gjhz");
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("国际合作项目名称或实验室名称及合作类型", info[0]);
        map.put("经费来源", mapTran.get(info[1]));
        map.put("赞助经费", info[2]);
        map.put("得分", info[3]);
        df += Double.valueOf(info[3]).doubleValue();
        gjhz.add(map);
      } 
    } 
    setDefen(myRound(df, 2));
    return gjhz;
  }
  
  public List<Map<String, String>> getYdhz(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> ydhz = new ArrayList<Map<String, String>>();
    String sql = "select ydhz_xmmc,ydhz_hzdw,ydhz_xmjf,ydhz_fs from t_ydhz where ydhz_gh='" + 
      jobNumber + "' and ydhz_nd='" + year + "' and ydhz_mqzt='2'";
    sql = String.valueOf(sql) + " order by t_ydhz_id";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("院地重大合作项目名称及联合实验室名称", info[0]);
        map.put("合作单位机构名称", info[1]);
        map.put("对方投入经费", info[2]);
        map.put("得分", info[3]);
        df += Double.valueOf(info[3]).doubleValue();
        ydhz.add(map);
      }  
    setDefen(myRound(df, 2));
    return ydhz;
  }
  
  public List<Map<String, String>> getZxbg(String jobNumber, String year) {
    double df = 0.0D;
    List<Map<String, String>> zxbg = new ArrayList<Map<String, String>>();
    String sql = "select zxbg_zxbgmc,zxbg_psldr,zxbg_dwsx,zxbg_fs from t_zxbg where zxbg_gh='" + 
      jobNumber + "' and zxbg_nd='" + year + "' and zxbg_mqzt='2'";
    List<String[]> list = this.util.getListQuery(sql, "0");
    if (list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        String[] info = list.get(i);
        Map<String, String> map = new HashMap<String, String>();
        map.put("序号", i + 1);
        map.put("咨询报告或建议名称", info[0]);
        map.put("批示领导部门", info[1]);
        map.put("排名1", info[2]);
        map.put("排名2", "");
        map.put("得分", info[3]);
        df += Double.valueOf(info[3]).doubleValue();
        zxbg.add(map);
      }  
    setDefen(myRound(df, 2));
    return zxbg;
  }
  
  public String getJobNumber(String userId) {
    String sql = "SELECT empNumber FROM org_employee WHERE emp_id=" + userId;
    List<String[]> list = this.util.getListQuery(sql, "0");
    String jobNumber = userId;
    if (list.size() > 0)
      jobNumber = ((String[])list.get(0))[0]; 
    return jobNumber;
  }
  
  public Map<String, String> tranSelect(String fieldName, String tableName) {
    String fieldValue = "";
    String sql = "select f.field_value from tfield f join ttable t on f.field_table=t.table_id WHERE f.field_name='" + fieldName + 
      "' AND t.table_name='" + tableName + "'";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        fieldValue = (rs.getString(1) == null) ? "" : rs.getString(1); 
      rs.close();
      if (fieldValue.startsWith("*[") && fieldValue.contains("].*[") && fieldValue.endsWith("]")) {
        String baseId = fieldValue.substring(fieldValue.indexOf("].*[") + 4, fieldValue.length() - 1);
        sql = "SELECT base_value,base_key FROM tbase WHERE base_parent=" + baseId;
        fieldValue = "";
        rs = base.executeQuery(sql);
        while (rs.next())
          fieldValue = String.valueOf(fieldValue) + ((rs.getString(1) == null) ? "" : rs.getString(1)) + "/" + ((rs.getString(2) == null) ? "" : rs.getString(2)) + ";"; 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    Map<String, String> map = new HashMap<String, String>();
    if (!"".equals(fieldValue)) {
      String[] values = fieldValue.split(";");
      for (int i = 0; i < values.length; i++) {
        String[] mapValue = values[i].split("/");
        map.put(mapValue[0], mapValue[1]);
      } 
    } 
    return map;
  }
  
  private double getGlfs(String empNumber, String nd) {
    double fs = 0.0D;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select glfs_fs from t_glfs where glfs_number='" + empNumber + "' and glfs_nd='" + nd + "'");
      if (rs.next())
        fs = rs.getDouble(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return fs;
  }
  
  private double[] getWnjx(String empNum, String nd, int year) {
    Integer ndNum = Integer.valueOf(Integer.valueOf(nd).intValue() - year);
    double[] wnjx = new double[year];
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select jxfs_fs from t_jxfs where jxfs_number='" + empNum + 
          "' and jxfs_nd>='" + ndNum + "' order by jxfs_nd desc");
      int i = 0;
      while (rs.next()) {
        wnjx[i] = 0.0D;
        i++;
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return wnjx;
  }
  
  public double myRound(double number, int index) {
    double temp = Math.pow(10.0D, index);
    return Math.round(number * temp) / temp;
  }
  
  public double getDefen() {
    return this.defen;
  }
  
  public void setDefen(double defen) {
    this.defen = defen;
  }
}
