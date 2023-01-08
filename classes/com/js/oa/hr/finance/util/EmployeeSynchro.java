package com.js.oa.hr.finance.util;

import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class EmployeeSynchro {
  static String ENDDA = "99991231";
  
  public static void main(String[] args) {}
  
  public String sysnchroEmp(HttpServletRequest request, String table_name) {
    String result = "";
    String recordId = request.getParameter("recordId");
    String sql = "select * from " + table_name + " where " + table_name + "_id=" + recordId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      EmployeeVO employee = new EmployeeVO();
      EmployeeVO oldEmployee = new EmployeeVO();
      EmployeeOtherInfoVO empOtherInfo = new EmployeeOtherInfoVO();
      EmployeeOtherInfoVO oldEmpOtherInfo = new EmployeeOtherInfoVO();
      String czlx = "";
      String czyy = "";
      if (rs.next()) {
        String userId = "";
        if ("rs_rzxx".equalsIgnoreCase(table_name)) {
          userId = (rs.getString("RS_RZXX_RYID") == null) ? "0" : rs
            .getString("RS_RZXX_RYID");
        } else {
          userId = (rs.getString("RS_RZXX_ID") == null) ? "0" : rs
            .getString("RS_RZXX_ID");
        } 
        czlx = rs.getString("RS_RZXX_CZLX");
        czyy = rs.getString("RS_RZXX_CZYY");
        List<Object[]> list = (new NewEmployeeBD()).selectSingle(
            Long.valueOf(userId));
        if (list.size() > 0) {
          Object[] object = list.get(0);
          employee = (EmployeeVO)object[0];
          empOtherInfo = (EmployeeOtherInfoVO)list.get(1);
        } 
        List<Object[]> list1 = (new NewEmployeeBD()).selectSingle(
            Long.valueOf(userId));
        if (list1.size() > 0) {
          Object[] object = list1.get(0);
          oldEmployee = (EmployeeVO)object[0];
          oldEmpOtherInfo = (EmployeeOtherInfoVO)list1.get(1);
        } 
        String xm = rs.getString("RS_RZXX_XM");
        xm = xm.split(";")[0];
        employee.setEmpName(xm);
        String xb = rs.getString("RS_RZXX_XB");
        if (xb == null || "".equals(xb))
          xb = "0"; 
        employee.setEmpSex(Integer.valueOf(xb).byteValue());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String csny = rs.getString("RS_RZXX_CSNY");
        employee.setEmpBirth(sdf.parse(csny));
        String mz = rs.getString("RS_RZXX_MZ");
        employee.setEmpNation(mz);
        empOtherInfo.setCym(rs.getString("RS_RZXX_ZYM"));
        employee.setEmpNativePlace(rs.getString("RS_RZXX_JTZZ"));
        empOtherInfo.setCsd(rs.getString("RS_RZXX_CSD"));
        empOtherInfo.setHkszd(rs.getString("RS_RZXX_HZSZD"));
        empOtherInfo.setHkszdyzbm(rs.getString("hkszdyzbm"));
        empOtherInfo.setJzdyzbm(rs.getString("jzdyzbm"));
        employee.setEmpAddress(rs.getString("RS_RZXX_GG"));
        employee.setEmpPolity(rs.getString("RS_RZXX_ZZMM"));
        employee.setEmpIdCard(rs.getString("RS_RZXX_SFZ"));
        empOtherInfo.setHyzk(rs.getString("RS_RZXX_HYZK"));
        empOtherInfo.setJkzk(rs.getString("RS_RZXX_JKZK"));
        empOtherInfo.setZyjszc(rs.getString("RS_RZXX_ZYCC"));
        empOtherInfo.setZyzg(rs.getString("RS_RZXX_ZYZG"));
        employee.setEmpDescribe(rs.getString("RS_RZXX_TC"));
        employee.setEmpInterest(rs.getString("RS_RZXX_XQAH"));
        employee.setEmpBusinessPhone(rs.getString("RS_RZXX_BGDH"));
        employee.setEmpEmail(rs.getString("RS_RZXX_DZYJ"));
        employee.setEmpMobilePhone(rs.getString("RS_RZXX_SJHM"));
        empOtherInfo.setQrz_zgxl(rs.getString("RS_RZXX_ZGXL"));
        empOtherInfo.setQrz_zgxw(rs.getString("RS_RZXX_ZGXW"));
        empOtherInfo.setQrz_byyxx(rs.getString("RS_RZXX_BYYX"));
        empOtherInfo.setQrz_zy(rs.getString("RS_RZXX_ZY"));
        empOtherInfo.setZzjy_zgxl(rs.getString("RS_RZXX_ZGXL1"));
        empOtherInfo.setZzjy_zgxw(rs.getString("RS_RZXX_ZGXW1"));
        empOtherInfo.setZzjy_byyxx(rs.getString("RS_RZXX_BYYX1"));
        empOtherInfo.setZzjy_zy(rs.getString("RS_RZXX_ZY1"));
        employee.setEmpFireDate(sdf.parse(rs.getString("RS_RZXX_GZSJ")));
        employee.setIntoCompanyDate(sdf.parse(rs
              .getString("RS_RZXX_RSSJ")));
        empOtherInfo.setCs(rs.getString("RS_RZXX_CS"));
        employee.setEmpPosition(rs.getString("RS_RZXX_GW"));
        employee.setEmpDuty(rs.getString("RS_RZXX_ZW"));
        employee.setEmpDutyLevel(rs.getString("RS_RZXX_ZJ"));
        empOtherInfo.setRxzsj(rs.getString("RS_RZXX_RXZSH"));
        empOtherInfo.setRxjsj(rs.getString("RS_RZXX_RXJSJ"));
        empOtherInfo.setWyhrz(rs.getString("RS_RZXX_WYH"));
        empOtherInfo.setSyqqx(rs.getString("RS_RZXX_SYQ"));
        empOtherInfo.setSyqksrq(rs.getString("RS_RZXX_KSSJ"));
        empOtherInfo.setSyqjsrq(rs.getString("RS_RZXX_JSSJ"));
        empOtherInfo.setPo_xm(rs.getString("RS_RZXX_POXM"));
        empOtherInfo.setPo_csny(rs.getString("RS_RZXX_POCS"));
        empOtherInfo.setPo_mz(rs.getString("RS_RZXX_POMZ"));
        empOtherInfo.setPo_jg(rs.getString("RS_RZXX_POJG"));
        empOtherInfo.setPo_zzmm(rs.getString("RS_RZXX_POZJMM"));
        empOtherInfo.setPo_cjgzsj(rs.getString("RS_RZXX_SJ"));
        empOtherInfo.setPo_xjzcs(rs.getString("RS_RZXX_XJ"));
        empOtherInfo.setPo_hkszd(rs.getString("RS_RZXX_POHK"));
        empOtherInfo.setPo_xl(rs.getString("RS_RZXX_POXL"));
        empOtherInfo.setPo_zyjszc(rs.getString("RS_RZXX_POZYJ"));
        empOtherInfo.setPo_byyxx(rs.getString("RS_RZXX_POYX"));
        empOtherInfo.setPo_zy(rs.getString("RS_RZXX_POZY"));
        empOtherInfo.setPo_gzdwjbm(rs.getString("RS_RZXX_PODW"));
        empOtherInfo.setPo_zw(rs.getString("RS_RZXX_POZW"));
        empOtherInfo.setDt_rdsj(rs.getString("RS_RZXX_PORT"));
        empOtherInfo.setDt_dnzw(rs.getString("RS_RZXX_POD"));
        empOtherInfo.setDt_ssdzb(rs.getString("RS_RZXX_POS"));
        empOtherInfo.setDt_zzgxszdw(rs.getString("RS_RZXX_POZZ"));
        empOtherInfo.setSap_ID(rs.getString("RS_RZXX_SAP"));
        empOtherInfo.setHkxz(rs.getString("HKXZ"));
        empOtherInfo.setZw(rs.getString("RS_RZXX_ZHIWU"));
        empOtherInfo.setRszfw(rs.getString("RS_RZXX_RSZFW"));
      } 
      if (employee.getEmpId() > 0L)
        result = sysnchroEmp(employee, empOtherInfo, oldEmployee, 
            oldEmpOtherInfo, czlx, czyy, table_name, recordId); 
    } catch (Exception e) {
      e.printStackTrace();
      result = e.getMessage();
    } 
    return result;
  }
  
  public String sysnchroEmp(EmployeeVO employee, EmployeeOtherInfoVO empOtherInfo, EmployeeVO oldEmployee, EmployeeOtherInfoVO oldEmpOtherInfo, String czlx, String czyy, String table_name, String recordId) {
    // Byte code:
    //   0: aconst_null
    //   1: astore #9
    //   3: ldc ''
    //   5: astore #10
    //   7: invokestatic connect : ()Lcom/sap/conn/jco/JCoDestination;
    //   10: astore #11
    //   12: new java/util/Date
    //   15: dup
    //   16: invokespecial <init> : ()V
    //   19: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   22: astore #12
    //   24: aload_2
    //   25: invokevirtual getSap_ID : ()Ljava/lang/String;
    //   28: astore #13
    //   30: aload #11
    //   32: invokeinterface getRepository : ()Lcom/sap/conn/jco/JCoRepository;
    //   37: ldc_w 'Z_UPDATE_PERSON'
    //   40: invokeinterface getFunction : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoFunction;
    //   45: astore #9
    //   47: aload #9
    //   49: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   54: ldc_w 'ZT0000'
    //   57: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   62: astore #14
    //   64: aload #14
    //   66: invokeinterface appendRow : ()V
    //   71: aload #14
    //   73: ldc_w 'INFTY'
    //   76: ldc_w '0000'
    //   79: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   84: aload #14
    //   86: ldc_w 'PERNR'
    //   89: aload #13
    //   91: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   96: aload #14
    //   98: ldc_w 'ENDDA'
    //   101: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   104: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   109: ldc_w 'Z1'
    //   112: aload #5
    //   114: invokevirtual equals : (Ljava/lang/Object;)Z
    //   117: ifeq -> 140
    //   120: aload #14
    //   122: ldc_w 'BEGDA'
    //   125: aload_1
    //   126: invokevirtual getEmpFireDate : ()Ljava/util/Date;
    //   129: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   132: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   137: goto -> 152
    //   140: aload #14
    //   142: ldc_w 'BEGDA'
    //   145: aload #12
    //   147: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   152: aload #14
    //   154: ldc_w 'MASSN'
    //   157: aload #5
    //   159: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   164: aload #14
    //   166: ldc_w 'MASSG'
    //   169: aload #6
    //   171: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   176: aload #14
    //   178: ldc_w 'STAT2'
    //   181: ldc_w '3'
    //   184: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   189: aload #14
    //   191: ldc_w 'STAT3'
    //   194: ldc_w '2'
    //   197: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   202: aload_3
    //   203: ifnull -> 250
    //   206: aload_2
    //   207: invokevirtual getRszfw : ()Ljava/lang/String;
    //   210: aload #4
    //   212: invokevirtual getRszfw : ()Ljava/lang/String;
    //   215: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   218: ifne -> 250
    //   221: aload_2
    //   222: invokevirtual getZw : ()Ljava/lang/String;
    //   225: aload #4
    //   227: invokevirtual getZw : ()Ljava/lang/String;
    //   230: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   233: ifne -> 250
    //   236: aload_1
    //   237: invokevirtual getEmpPosition : ()Ljava/lang/String;
    //   240: aload_3
    //   241: invokevirtual getEmpPosition : ()Ljava/lang/String;
    //   244: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   247: ifeq -> 507
    //   250: aload #9
    //   252: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   257: ldc_w 'ZT0001'
    //   260: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   265: astore #15
    //   267: aload #15
    //   269: invokeinterface appendRow : ()V
    //   274: aload #15
    //   276: ldc_w 'INFTY'
    //   279: ldc_w '0001'
    //   282: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   287: aload #15
    //   289: ldc_w 'PERNR'
    //   292: aload #13
    //   294: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   299: aload #15
    //   301: ldc_w 'ENDDA'
    //   304: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   307: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   312: aload #15
    //   314: ldc_w 'BEGDA'
    //   317: aload #12
    //   319: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   324: aload #15
    //   326: ldc_w 'BUKRS'
    //   329: ldc_w 'Z100'
    //   332: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   337: aload #15
    //   339: ldc_w 'WERKS'
    //   342: ldc_w 'A000'
    //   345: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   350: aload #15
    //   352: ldc_w 'PERSG'
    //   355: ldc_w '1'
    //   358: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   363: aload #15
    //   365: ldc_w 'PERSK'
    //   368: ldc_w '10'
    //   371: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   376: aload #15
    //   378: ldc_w 'BTRTL'
    //   381: aload_2
    //   382: invokevirtual getRszfw : ()Ljava/lang/String;
    //   385: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   390: aload #15
    //   392: ldc_w 'ABKRS'
    //   395: ldc_w 'A京'
    //   398: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   403: ldc '0'
    //   405: astore #16
    //   407: new com/js/oa/userdb/util/DbOpt
    //   410: dup
    //   411: invokespecial <init> : ()V
    //   414: astore #17
    //   416: aload #17
    //   418: new java/lang/StringBuilder
    //   421: dup
    //   422: ldc_w 'select min(orgserial) from org_organization  where org_id in(select org_id from org_organization_user where emp_id='
    //   425: invokespecial <init> : (Ljava/lang/String;)V
    //   428: aload_1
    //   429: invokevirtual getEmpId : ()J
    //   432: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   435: ldc_w ')'
    //   438: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   441: invokevirtual toString : ()Ljava/lang/String;
    //   444: invokevirtual executeQueryToStr : (Ljava/lang/String;)Ljava/lang/String;
    //   447: astore #16
    //   449: aload #17
    //   451: invokevirtual close : ()V
    //   454: goto -> 464
    //   457: astore #18
    //   459: aload #18
    //   461: invokevirtual printStackTrace : ()V
    //   464: aload #15
    //   466: ldc_w 'ORGEH'
    //   469: aload #16
    //   471: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   476: aload #15
    //   478: ldc_w 'PLANS'
    //   481: aload_2
    //   482: invokevirtual getZw : ()Ljava/lang/String;
    //   485: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   490: aload #15
    //   492: ldc_w 'STELL'
    //   495: aload_1
    //   496: invokevirtual getEmpPosition : ()Ljava/lang/String;
    //   499: invokestatic getGwbm : (Ljava/lang/String;)Ljava/lang/String;
    //   502: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   507: aload_3
    //   508: ifnull -> 629
    //   511: aload_1
    //   512: invokevirtual getEmpName : ()Ljava/lang/String;
    //   515: aload_3
    //   516: invokevirtual getEmpName : ()Ljava/lang/String;
    //   519: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   522: ifne -> 629
    //   525: new java/lang/StringBuilder
    //   528: dup
    //   529: aload_1
    //   530: invokevirtual getEmpSex : ()B
    //   533: invokestatic valueOf : (I)Ljava/lang/String;
    //   536: invokespecial <init> : (Ljava/lang/String;)V
    //   539: invokevirtual toString : ()Ljava/lang/String;
    //   542: new java/lang/StringBuilder
    //   545: dup
    //   546: aload_3
    //   547: invokevirtual getEmpSex : ()B
    //   550: invokestatic valueOf : (I)Ljava/lang/String;
    //   553: invokespecial <init> : (Ljava/lang/String;)V
    //   556: invokevirtual toString : ()Ljava/lang/String;
    //   559: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   562: ifne -> 629
    //   565: aload_1
    //   566: invokevirtual getEmpBirth : ()Ljava/util/Date;
    //   569: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   572: aload_3
    //   573: invokevirtual getEmpBirth : ()Ljava/util/Date;
    //   576: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   579: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   582: ifne -> 629
    //   585: aload_1
    //   586: invokevirtual getEmpNation : ()Ljava/lang/String;
    //   589: aload_3
    //   590: invokevirtual getEmpNation : ()Ljava/lang/String;
    //   593: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   596: ifne -> 629
    //   599: aload_2
    //   600: invokevirtual getHkxz : ()Ljava/lang/String;
    //   603: aload #4
    //   605: invokevirtual getHkxz : ()Ljava/lang/String;
    //   608: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   611: ifne -> 629
    //   614: aload_2
    //   615: invokevirtual getHyzk : ()Ljava/lang/String;
    //   618: aload #4
    //   620: invokevirtual getHyzk : ()Ljava/lang/String;
    //   623: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   626: ifeq -> 1022
    //   629: aload #9
    //   631: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   636: ldc_w 'ZT0002'
    //   639: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   644: astore #15
    //   646: aload #15
    //   648: invokeinterface appendRow : ()V
    //   653: aload #15
    //   655: ldc_w 'INFTY'
    //   658: ldc_w '0002'
    //   661: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   666: aload #15
    //   668: ldc_w 'PERNR'
    //   671: aload #13
    //   673: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   678: aload #15
    //   680: ldc_w 'ENDDA'
    //   683: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   686: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   691: aload #15
    //   693: ldc_w 'BEGDA'
    //   696: aload #12
    //   698: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   703: aload #15
    //   705: ldc_w 'NACHN'
    //   708: aload_1
    //   709: invokevirtual getEmpName : ()Ljava/lang/String;
    //   712: iconst_0
    //   713: iconst_1
    //   714: invokevirtual substring : (II)Ljava/lang/String;
    //   717: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   722: aload #15
    //   724: ldc_w 'VORNA'
    //   727: aload_1
    //   728: invokevirtual getEmpName : ()Ljava/lang/String;
    //   731: iconst_1
    //   732: invokevirtual substring : (I)Ljava/lang/String;
    //   735: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   740: aload #15
    //   742: ldc_w 'RUFNM'
    //   745: aload_2
    //   746: invokevirtual getCym : ()Ljava/lang/String;
    //   749: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   754: aload #15
    //   756: ldc_w 'GBLND'
    //   759: ldc_w 'CN'
    //   762: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   767: aload #15
    //   769: ldc_w 'GESCH'
    //   772: ldc_w 'xb'
    //   775: new java/lang/StringBuilder
    //   778: dup
    //   779: aload_1
    //   780: invokevirtual getEmpSex : ()B
    //   783: invokestatic valueOf : (I)Ljava/lang/String;
    //   786: invokespecial <init> : (Ljava/lang/String;)V
    //   789: invokevirtual toString : ()Ljava/lang/String;
    //   792: ldc_w '1'
    //   795: invokestatic getSAPCode : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   798: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   803: aload #15
    //   805: ldc_w 'GBDAT'
    //   808: aload_1
    //   809: invokevirtual getEmpBirth : ()Ljava/util/Date;
    //   812: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   815: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   820: aload #15
    //   822: ldc_w 'GBDEP'
    //   825: ldc_w '11'
    //   828: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   833: aload_2
    //   834: invokevirtual getCsd : ()Ljava/lang/String;
    //   837: astore #16
    //   839: aload #16
    //   841: ifnull -> 864
    //   844: aload #16
    //   846: invokevirtual length : ()I
    //   849: bipush #40
    //   851: if_icmple -> 864
    //   854: aload #16
    //   856: iconst_0
    //   857: bipush #40
    //   859: invokevirtual substring : (II)Ljava/lang/String;
    //   862: astore #16
    //   864: ldc ''
    //   866: aload #16
    //   868: invokevirtual equals : (Ljava/lang/Object;)Z
    //   871: ifeq -> 879
    //   874: ldc_w '未填写'
    //   877: astore #16
    //   879: aload #15
    //   881: ldc_w 'GBORT'
    //   884: ldc_w '11'
    //   887: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   892: aload #15
    //   894: ldc_w 'NATIO'
    //   897: ldc_w 'CN'
    //   900: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   905: aload_1
    //   906: invokevirtual getEmpAddress : ()Ljava/lang/String;
    //   909: astore #17
    //   911: aload #17
    //   913: ifnull -> 936
    //   916: aload #17
    //   918: invokevirtual length : ()I
    //   921: bipush #18
    //   923: if_icmple -> 936
    //   926: aload #17
    //   928: iconst_0
    //   929: bipush #18
    //   931: invokevirtual substring : (II)Ljava/lang/String;
    //   934: astore #17
    //   936: ldc ''
    //   938: aload #17
    //   940: invokevirtual equals : (Ljava/lang/Object;)Z
    //   943: ifeq -> 951
    //   946: ldc_w '未填写'
    //   949: astore #17
    //   951: aload #15
    //   953: ldc_w 'ZZ_JIGN'
    //   956: aload #17
    //   958: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   963: aload #15
    //   965: ldc_w 'FAMST'
    //   968: ldc_w 'hyzk'
    //   971: aload_2
    //   972: invokevirtual getHyzk : ()Ljava/lang/String;
    //   975: ldc_w '1'
    //   978: invokestatic getSAPCode : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   981: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   986: aload #15
    //   988: ldc_w 'RACKY'
    //   991: ldc_w 'mz'
    //   994: aload_1
    //   995: invokevirtual getEmpNation : ()Ljava/lang/String;
    //   998: ldc_w '01'
    //   1001: invokestatic getSAPCode : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1004: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1009: aload #15
    //   1011: ldc_w 'ZZ_HKSZD'
    //   1014: ldc_w '01'
    //   1017: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1022: aload_3
    //   1023: ifnull -> 1070
    //   1026: aload_1
    //   1027: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1030: aload_3
    //   1031: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1034: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1037: ifne -> 1070
    //   1040: aload_2
    //   1041: invokevirtual getHkszd : ()Ljava/lang/String;
    //   1044: aload #4
    //   1046: invokevirtual getHkszd : ()Ljava/lang/String;
    //   1049: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1052: ifne -> 1070
    //   1055: aload_2
    //   1056: invokevirtual getHkszdyzbm : ()Ljava/lang/String;
    //   1059: aload #4
    //   1061: invokevirtual getHkszdyzbm : ()Ljava/lang/String;
    //   1064: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1067: ifeq -> 1225
    //   1070: aload #9
    //   1072: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   1077: ldc_w 'ZT0006'
    //   1080: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   1085: astore #15
    //   1087: aload #15
    //   1089: invokeinterface appendRow : ()V
    //   1094: aload #15
    //   1096: ldc_w 'INFTY'
    //   1099: ldc_w '0006'
    //   1102: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1107: aload #15
    //   1109: ldc_w 'PERNR'
    //   1112: aload #13
    //   1114: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1119: aload #15
    //   1121: ldc_w 'ENDDA'
    //   1124: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   1127: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1132: aload #15
    //   1134: ldc_w 'BEGDA'
    //   1137: aload #12
    //   1139: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1144: aload #15
    //   1146: ldc_w 'ANSSA'
    //   1149: ldc_w '0004'
    //   1152: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1157: aload #15
    //   1159: ldc_w 'SUBTY'
    //   1162: ldc_w '0004'
    //   1165: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1170: aload #15
    //   1172: ldc_w 'STRAS'
    //   1175: aload_2
    //   1176: invokevirtual getHkszd : ()Ljava/lang/String;
    //   1179: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1184: aload #15
    //   1186: ldc_w 'TELNR'
    //   1189: aload_1
    //   1190: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1193: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1198: aload #15
    //   1200: ldc_w 'PSTLZ'
    //   1203: aload_2
    //   1204: invokevirtual getHkszdyzbm : ()Ljava/lang/String;
    //   1207: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1212: aload #15
    //   1214: ldc_w 'STATE'
    //   1217: ldc_w '11'
    //   1220: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1225: aload_3
    //   1226: ifnull -> 1331
    //   1229: aload_1
    //   1230: invokevirtual getIntoCompanyDate : ()Ljava/util/Date;
    //   1233: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1236: aload_3
    //   1237: invokevirtual getIntoCompanyDate : ()Ljava/util/Date;
    //   1240: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1243: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1246: ifne -> 1331
    //   1249: aload_1
    //   1250: invokevirtual getEmpFireDate : ()Ljava/util/Date;
    //   1253: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1256: aload_3
    //   1257: invokevirtual getEmpFireDate : ()Ljava/util/Date;
    //   1260: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1263: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1266: ifne -> 1331
    //   1269: aload_1
    //   1270: invokevirtual getZhuanzhengDate : ()Ljava/util/Date;
    //   1273: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1276: aload_3
    //   1277: invokevirtual getZhuanzhengDate : ()Ljava/util/Date;
    //   1280: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1283: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1286: ifne -> 1331
    //   1289: aload_2
    //   1290: invokevirtual getRxzsj : ()Ljava/lang/String;
    //   1293: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   1296: aload #4
    //   1298: invokevirtual getRxzsj : ()Ljava/lang/String;
    //   1301: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   1304: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1307: ifne -> 1331
    //   1310: aload_2
    //   1311: invokevirtual getRxjsj : ()Ljava/lang/String;
    //   1314: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   1317: aload #4
    //   1319: invokevirtual getRxjsj : ()Ljava/lang/String;
    //   1322: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   1325: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1328: ifeq -> 1555
    //   1331: aload #9
    //   1333: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   1338: ldc_w 'ZT0041'
    //   1341: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   1346: astore #15
    //   1348: aload #15
    //   1350: invokeinterface appendRow : ()V
    //   1355: aload #15
    //   1357: ldc_w 'INFTY'
    //   1360: ldc_w '0041'
    //   1363: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1368: aload #15
    //   1370: ldc_w 'PERNR'
    //   1373: aload #13
    //   1375: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1380: aload #15
    //   1382: ldc_w 'ENDDA'
    //   1385: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   1388: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1393: aload #15
    //   1395: ldc_w 'BEGDA'
    //   1398: aload #12
    //   1400: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1405: aload #15
    //   1407: ldc_w 'DAR01'
    //   1410: ldc_w 'Z3'
    //   1413: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1418: aload #15
    //   1420: ldc_w 'DAT01'
    //   1423: aload_1
    //   1424: invokevirtual getIntoCompanyDate : ()Ljava/util/Date;
    //   1427: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1430: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1435: aload #15
    //   1437: ldc_w 'DAR02'
    //   1440: ldc_w 'ZA'
    //   1443: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1448: aload #15
    //   1450: ldc_w 'DAT02'
    //   1453: aload_1
    //   1454: invokevirtual getZhuanzhengDate : ()Ljava/util/Date;
    //   1457: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1460: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1465: aload #15
    //   1467: ldc_w 'DAR03'
    //   1470: ldc_w 'Z7'
    //   1473: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1478: aload #15
    //   1480: ldc_w 'DAT03'
    //   1483: aload_2
    //   1484: invokevirtual getRxzsj : ()Ljava/lang/String;
    //   1487: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   1490: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1495: aload #15
    //   1497: ldc_w 'DAR04'
    //   1500: ldc_w 'Z8'
    //   1503: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1508: aload #15
    //   1510: ldc_w 'DAT04'
    //   1513: aload_2
    //   1514: invokevirtual getRxjsj : ()Ljava/lang/String;
    //   1517: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   1520: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1525: aload #15
    //   1527: ldc_w 'DAR05'
    //   1530: ldc_w 'ZL'
    //   1533: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1538: aload #15
    //   1540: ldc_w 'DAT05'
    //   1543: aload_1
    //   1544: invokevirtual getEmpFireDate : ()Ljava/util/Date;
    //   1547: invokestatic getDateString : (Ljava/util/Date;)Ljava/lang/String;
    //   1550: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1555: aload_3
    //   1556: ifnull -> 1601
    //   1559: aload_1
    //   1560: invokevirtual getEmpBusinessPhone : ()Ljava/lang/String;
    //   1563: aload_3
    //   1564: invokevirtual getEmpBusinessPhone : ()Ljava/lang/String;
    //   1567: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1570: ifne -> 1601
    //   1573: aload_1
    //   1574: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1577: aload_3
    //   1578: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1581: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1584: ifne -> 1601
    //   1587: aload_1
    //   1588: invokevirtual getEmpEmail : ()Ljava/lang/String;
    //   1591: aload_1
    //   1592: invokevirtual getEmpEmail : ()Ljava/lang/String;
    //   1595: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1598: ifeq -> 1963
    //   1601: aload #9
    //   1603: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   1608: ldc_w 'ZT0105'
    //   1611: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   1616: astore #15
    //   1618: aload_3
    //   1619: ifnull -> 1636
    //   1622: aload_1
    //   1623: invokevirtual getEmpBusinessPhone : ()Ljava/lang/String;
    //   1626: aload_3
    //   1627: invokevirtual getEmpBusinessPhone : ()Ljava/lang/String;
    //   1630: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1633: ifeq -> 1733
    //   1636: aload #15
    //   1638: invokeinterface appendRow : ()V
    //   1643: aload #15
    //   1645: ldc_w 'INFTY'
    //   1648: ldc_w '0105'
    //   1651: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1656: aload #15
    //   1658: ldc_w 'PERNR'
    //   1661: aload #13
    //   1663: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1668: aload #15
    //   1670: ldc_w 'ENDDA'
    //   1673: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   1676: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1681: aload #15
    //   1683: ldc_w 'BEGDA'
    //   1686: aload #12
    //   1688: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1693: aload #15
    //   1695: ldc_w 'SUBTY'
    //   1698: ldc_w 'OPHN'
    //   1701: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1706: aload #15
    //   1708: ldc_w 'USRTY'
    //   1711: ldc_w 'OPHN'
    //   1714: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1719: aload #15
    //   1721: ldc_w 'USRID'
    //   1724: aload_1
    //   1725: invokevirtual getEmpBusinessPhone : ()Ljava/lang/String;
    //   1728: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1733: aload_3
    //   1734: ifnull -> 1751
    //   1737: aload_1
    //   1738: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1741: aload_3
    //   1742: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1745: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1748: ifeq -> 1848
    //   1751: aload #15
    //   1753: invokeinterface appendRow : ()V
    //   1758: aload #15
    //   1760: ldc_w 'INFTY'
    //   1763: ldc_w '0105'
    //   1766: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1771: aload #15
    //   1773: ldc_w 'PERNR'
    //   1776: aload #13
    //   1778: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1783: aload #15
    //   1785: ldc_w 'ENDDA'
    //   1788: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   1791: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1796: aload #15
    //   1798: ldc_w 'BEGDA'
    //   1801: aload #12
    //   1803: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1808: aload #15
    //   1810: ldc_w 'SUBTY'
    //   1813: ldc_w 'CELL'
    //   1816: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1821: aload #15
    //   1823: ldc_w 'USRTY'
    //   1826: ldc_w 'CELL'
    //   1829: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1834: aload #15
    //   1836: ldc_w 'USRID'
    //   1839: aload_1
    //   1840: invokevirtual getEmpMobilePhone : ()Ljava/lang/String;
    //   1843: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1848: aload_3
    //   1849: ifnull -> 1866
    //   1852: aload_1
    //   1853: invokevirtual getEmpEmail : ()Ljava/lang/String;
    //   1856: aload_3
    //   1857: invokevirtual getEmpEmail : ()Ljava/lang/String;
    //   1860: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1863: ifeq -> 1963
    //   1866: aload #15
    //   1868: invokeinterface appendRow : ()V
    //   1873: aload #15
    //   1875: ldc_w 'INFTY'
    //   1878: ldc_w '0105'
    //   1881: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1886: aload #15
    //   1888: ldc_w 'PERNR'
    //   1891: aload #13
    //   1893: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1898: aload #15
    //   1900: ldc_w 'ENDDA'
    //   1903: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   1906: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1911: aload #15
    //   1913: ldc_w 'BEGDA'
    //   1916: aload #12
    //   1918: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1923: aload #15
    //   1925: ldc_w 'USRTY'
    //   1928: ldc_w 'MAIL'
    //   1931: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1936: aload #15
    //   1938: ldc_w 'SUBTY'
    //   1941: ldc_w 'MAIL'
    //   1944: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1949: aload #15
    //   1951: ldc_w 'USRID_LONG'
    //   1954: aload_1
    //   1955: invokevirtual getEmpEmail : ()Ljava/lang/String;
    //   1958: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   1963: aload_3
    //   1964: ifnull -> 1981
    //   1967: aload_1
    //   1968: invokevirtual getEmpIdCard : ()Ljava/lang/String;
    //   1971: aload_3
    //   1972: invokevirtual getEmpIdCard : ()Ljava/lang/String;
    //   1975: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1978: ifeq -> 2108
    //   1981: aload #9
    //   1983: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   1988: ldc_w 'ZT0185'
    //   1991: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   1996: astore #15
    //   1998: aload #15
    //   2000: invokeinterface appendRow : ()V
    //   2005: aload #15
    //   2007: ldc_w 'INFTY'
    //   2010: ldc_w '0185'
    //   2013: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2018: aload #15
    //   2020: ldc_w 'PERNR'
    //   2023: aload #13
    //   2025: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2030: aload #15
    //   2032: ldc_w 'ENDDA'
    //   2035: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   2038: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2043: aload #15
    //   2045: ldc_w 'BEGDA'
    //   2048: aload #12
    //   2050: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2055: aload #15
    //   2057: ldc_w 'ICTYP'
    //   2060: ldc_w '01'
    //   2063: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2068: aload #15
    //   2070: ldc_w 'SUBTY'
    //   2073: ldc_w '01'
    //   2076: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2081: aload #15
    //   2083: ldc_w 'ICNUM'
    //   2086: aload_1
    //   2087: invokevirtual getEmpIdCard : ()Ljava/lang/String;
    //   2090: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2095: aload #15
    //   2097: ldc_w 'ASTAT'
    //   2100: ldc_w '2'
    //   2103: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2108: new com/js/oa/userdb/util/DbOpt
    //   2111: dup
    //   2112: invokespecial <init> : ()V
    //   2115: astore #15
    //   2117: aconst_null
    //   2118: checkcast [[Ljava/lang/String;
    //   2121: astore #16
    //   2123: ldc ''
    //   2125: astore #17
    //   2127: ldc 'rs_rzxx'
    //   2129: aload #7
    //   2131: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   2134: ifeq -> 2194
    //   2137: new java/lang/StringBuilder
    //   2140: dup
    //   2141: ldc_w 'select znxx_csmy,znxx_xm,ZNXX_GX from RS_RZ_ZNXX where RS_RZ_ZNXX_foreignkey = '
    //   2144: invokespecial <init> : (Ljava/lang/String;)V
    //   2147: aload #8
    //   2149: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2152: ldc_w ' union all'
    //   2155: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2158: ldc_w '     select GNW_CSNY,GNW_XM,GNW_GX from ORG_EMPLOYEE_GNWGX where ORG_EMPLOYEE_GNWGX_foreignkey = '
    //   2161: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2164: aload #8
    //   2166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2169: ldc_w ' union all'
    //   2172: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2175: ldc_w '     select QTCY_CSNY,QTCY_XM,QTCY_GX from ORG_EMPLOYEE_ where ORG_EMPLOYEE__foreignkey = '
    //   2178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2181: aload #8
    //   2183: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2186: invokevirtual toString : ()Ljava/lang/String;
    //   2189: astore #17
    //   2191: goto -> 2248
    //   2194: new java/lang/StringBuilder
    //   2197: dup
    //   2198: ldc_w 'select znxx_csmy,znxx_xm,ZNXX_GX from jst_3019 where jst_3019_foreignkey = '
    //   2201: invokespecial <init> : (Ljava/lang/String;)V
    //   2204: aload #8
    //   2206: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2209: ldc_w ' union all'
    //   2212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2215: ldc_w '     select GNW_CSNY,GNW_XM,GNW_GX from jst_3027 where jst_3027_foreignkey = '
    //   2218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2221: aload #8
    //   2223: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2226: ldc_w ' union all'
    //   2229: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2232: ldc_w '     select QTCY_CSNY,QTCY_XM,QTCY_GX from jst_3024 where jst_3024_foreignkey = '
    //   2235: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2238: aload #8
    //   2240: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2243: invokevirtual toString : ()Ljava/lang/String;
    //   2246: astore #17
    //   2248: aload #15
    //   2250: aload #17
    //   2252: iconst_3
    //   2253: invokevirtual executeQueryToStrArr2 : (Ljava/lang/String;I)[[Ljava/lang/String;
    //   2256: astore #16
    //   2258: aload #15
    //   2260: invokevirtual close : ()V
    //   2263: goto -> 2273
    //   2266: astore #17
    //   2268: aload #17
    //   2270: invokevirtual printStackTrace : ()V
    //   2273: aload #16
    //   2275: ifnull -> 2683
    //   2278: aload #16
    //   2280: arraylength
    //   2281: ifle -> 2683
    //   2284: aload #9
    //   2286: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   2291: ldc_w 'ZT0021'
    //   2294: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   2299: astore #17
    //   2301: iconst_0
    //   2302: istore #18
    //   2304: goto -> 2675
    //   2307: aload #17
    //   2309: invokeinterface appendRow : ()V
    //   2314: aload #17
    //   2316: ldc_w 'INFTY'
    //   2319: ldc_w '0021'
    //   2322: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2327: aload #17
    //   2329: ldc_w 'PERNR'
    //   2332: aload #13
    //   2334: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2339: aload #17
    //   2341: ldc_w 'ENDDA'
    //   2344: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   2347: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2352: aload #17
    //   2354: ldc_w 'BEGDA'
    //   2357: aload #12
    //   2359: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2364: aload #17
    //   2366: ldc_w 'FGBDT'
    //   2369: aload #16
    //   2371: iload #18
    //   2373: aaload
    //   2374: iconst_0
    //   2375: aaload
    //   2376: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   2379: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2384: aload #17
    //   2386: ldc_w 'FANAT'
    //   2389: ldc_w 'CN'
    //   2392: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2397: ldc_w '1'
    //   2400: astore #19
    //   2402: ldc_w '岳父'
    //   2405: aload #16
    //   2407: iload #18
    //   2409: aaload
    //   2410: iconst_2
    //   2411: aaload
    //   2412: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2415: ifne -> 2578
    //   2418: ldc_w '公公'
    //   2421: aload #16
    //   2423: iload #18
    //   2425: aaload
    //   2426: iconst_2
    //   2427: aaload
    //   2428: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2431: ifne -> 2578
    //   2434: ldc_w '兄'
    //   2437: aload #16
    //   2439: iload #18
    //   2441: aaload
    //   2442: iconst_2
    //   2443: aaload
    //   2444: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2447: ifne -> 2578
    //   2450: ldc_w '弟'
    //   2453: aload #16
    //   2455: iload #18
    //   2457: aaload
    //   2458: iconst_2
    //   2459: aaload
    //   2460: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2463: ifne -> 2578
    //   2466: ldc_w '儿子'
    //   2469: aload #16
    //   2471: iload #18
    //   2473: aaload
    //   2474: iconst_2
    //   2475: aaload
    //   2476: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2479: ifne -> 2578
    //   2482: ldc_w '养父'
    //   2485: aload #16
    //   2487: iload #18
    //   2489: aaload
    //   2490: iconst_2
    //   2491: aaload
    //   2492: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2495: ifne -> 2578
    //   2498: ldc_w '父亲'
    //   2501: aload #16
    //   2503: iload #18
    //   2505: aaload
    //   2506: iconst_2
    //   2507: aaload
    //   2508: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2511: ifne -> 2578
    //   2514: ldc_w '养子'
    //   2517: aload #16
    //   2519: iload #18
    //   2521: aaload
    //   2522: iconst_2
    //   2523: aaload
    //   2524: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2527: ifne -> 2578
    //   2530: ldc_w '伯父'
    //   2533: aload #16
    //   2535: iload #18
    //   2537: aaload
    //   2538: iconst_2
    //   2539: aaload
    //   2540: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2543: ifne -> 2578
    //   2546: ldc_w '叔父'
    //   2549: aload #16
    //   2551: iload #18
    //   2553: aaload
    //   2554: iconst_2
    //   2555: aaload
    //   2556: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2559: ifne -> 2578
    //   2562: ldc_w '舅舅'
    //   2565: aload #16
    //   2567: iload #18
    //   2569: aaload
    //   2570: iconst_2
    //   2571: aaload
    //   2572: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2575: ifeq -> 2586
    //   2578: ldc_w '1'
    //   2581: astore #19
    //   2583: goto -> 2591
    //   2586: ldc_w '2'
    //   2589: astore #19
    //   2591: aload #17
    //   2593: ldc_w 'FASEX'
    //   2596: aload #19
    //   2598: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2603: aload #17
    //   2605: ldc_w 'FAVOR'
    //   2608: aload #16
    //   2610: iload #18
    //   2612: aaload
    //   2613: iconst_1
    //   2614: aaload
    //   2615: iconst_1
    //   2616: invokevirtual substring : (I)Ljava/lang/String;
    //   2619: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2624: aload #17
    //   2626: ldc_w 'FANAM'
    //   2629: aload #16
    //   2631: iload #18
    //   2633: aaload
    //   2634: iconst_1
    //   2635: aaload
    //   2636: iconst_0
    //   2637: iconst_1
    //   2638: invokevirtual substring : (II)Ljava/lang/String;
    //   2641: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2646: aload #17
    //   2648: ldc_w 'FAMSA'
    //   2651: ldc_w 'gx'
    //   2654: aload #16
    //   2656: iload #18
    //   2658: aaload
    //   2659: iconst_2
    //   2660: aaload
    //   2661: ldc_w '2'
    //   2664: invokestatic getSAPCode : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2667: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2672: iinc #18, 1
    //   2675: iload #18
    //   2677: aload #16
    //   2679: arraylength
    //   2680: if_icmplt -> 2307
    //   2683: aload_2
    //   2684: invokevirtual getPo_xm : ()Ljava/lang/String;
    //   2687: astore #17
    //   2689: aload #17
    //   2691: ifnull -> 2920
    //   2694: ldc ''
    //   2696: aload #17
    //   2698: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2701: ifne -> 2920
    //   2704: ldc ''
    //   2706: astore #18
    //   2708: ldc ''
    //   2710: astore #19
    //   2712: ldc '0'
    //   2714: aload_1
    //   2715: invokevirtual getEmpSex : ()B
    //   2718: invokestatic valueOf : (B)Ljava/lang/Byte;
    //   2721: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2724: ifeq -> 2740
    //   2727: ldc_w '2'
    //   2730: astore #18
    //   2732: ldc_w '妻子'
    //   2735: astore #19
    //   2737: goto -> 2750
    //   2740: ldc_w '1'
    //   2743: astore #18
    //   2745: ldc_w '丈夫'
    //   2748: astore #19
    //   2750: aload #9
    //   2752: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   2757: ldc_w 'ZT0021'
    //   2760: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   2765: astore #20
    //   2767: aload #20
    //   2769: invokeinterface appendRow : ()V
    //   2774: aload #20
    //   2776: ldc_w 'INFTY'
    //   2779: ldc_w '0021'
    //   2782: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2787: aload #20
    //   2789: ldc_w 'PERNR'
    //   2792: aload #13
    //   2794: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2799: aload #20
    //   2801: ldc_w 'ENDDA'
    //   2804: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   2807: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2812: aload #20
    //   2814: ldc_w 'BEGDA'
    //   2817: aload #12
    //   2819: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2824: aload #20
    //   2826: ldc_w 'FGBDT'
    //   2829: aload_2
    //   2830: invokevirtual getPo_csny : ()Ljava/lang/String;
    //   2833: invokestatic getDateStringFromString : (Ljava/lang/String;)Ljava/lang/String;
    //   2836: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2841: aload #20
    //   2843: ldc_w 'FANAT'
    //   2846: ldc_w 'CN'
    //   2849: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2854: aload #20
    //   2856: ldc_w 'FASEX'
    //   2859: aload #18
    //   2861: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2866: aload #20
    //   2868: ldc_w 'FAVOR'
    //   2871: aload #17
    //   2873: iconst_1
    //   2874: invokevirtual substring : (I)Ljava/lang/String;
    //   2877: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2882: aload #20
    //   2884: ldc_w 'FANAM'
    //   2887: aload #17
    //   2889: iconst_0
    //   2890: iconst_1
    //   2891: invokevirtual substring : (II)Ljava/lang/String;
    //   2894: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2899: aload #20
    //   2901: ldc_w 'FAMSA'
    //   2904: ldc_w 'gx'
    //   2907: aload #19
    //   2909: ldc_w '1'
    //   2912: invokestatic getSAPCode : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2915: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2920: aload_3
    //   2921: ifnull -> 2939
    //   2924: aload_2
    //   2925: invokevirtual getZw : ()Ljava/lang/String;
    //   2928: aload #4
    //   2930: invokevirtual getZw : ()Ljava/lang/String;
    //   2933: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   2936: ifeq -> 3040
    //   2939: aload #9
    //   2941: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   2946: ldc_w 'ZT9009'
    //   2949: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   2954: astore #18
    //   2956: aload #18
    //   2958: invokeinterface appendRow : ()V
    //   2963: aload #18
    //   2965: ldc_w 'INFTY'
    //   2968: ldc_w '9009'
    //   2971: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2976: aload #18
    //   2978: ldc_w 'PERNR'
    //   2981: aload #13
    //   2983: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   2988: aload #18
    //   2990: ldc_w 'ENDDA'
    //   2993: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   2996: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3001: aload #18
    //   3003: ldc_w 'BEGDA'
    //   3006: aload #12
    //   3008: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3013: aload #18
    //   3015: ldc_w 'ZZ_ZHWI'
    //   3018: aload_2
    //   3019: invokevirtual getZw : ()Ljava/lang/String;
    //   3022: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3027: aload #18
    //   3029: ldc_w 'ZZ_XAMU'
    //   3032: ldc_w '4'
    //   3035: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3040: aload_1
    //   3041: invokevirtual getEmpDuty : ()Ljava/lang/String;
    //   3044: ifnull -> 3383
    //   3047: ldc ''
    //   3049: aload_1
    //   3050: invokevirtual getEmpDuty : ()Ljava/lang/String;
    //   3053: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3056: ifne -> 3383
    //   3059: aload_3
    //   3060: ifnull -> 3077
    //   3063: aload_1
    //   3064: invokevirtual getEmpDuty : ()Ljava/lang/String;
    //   3067: aload_3
    //   3068: invokevirtual getEmpDuty : ()Ljava/lang/String;
    //   3071: invokestatic notEqu : (Ljava/lang/String;Ljava/lang/String;)Z
    //   3074: ifeq -> 3383
    //   3077: aload #9
    //   3079: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   3084: ldc_w 'ZT9010'
    //   3087: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   3092: astore #18
    //   3094: aload #18
    //   3096: invokeinterface appendRow : ()V
    //   3101: aload #18
    //   3103: ldc_w 'INFTY'
    //   3106: ldc_w '9010'
    //   3109: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3114: aload #18
    //   3116: ldc_w 'PERNR'
    //   3119: aload #13
    //   3121: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3126: aload #18
    //   3128: ldc_w 'ENDDA'
    //   3131: getstatic com/js/oa/hr/finance/util/EmployeeSynchro.ENDDA : Ljava/lang/String;
    //   3134: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3139: aload #18
    //   3141: ldc_w 'BEGDA'
    //   3144: aload #12
    //   3146: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3151: aload_1
    //   3152: invokevirtual getEmpDuty : ()Ljava/lang/String;
    //   3155: astore #19
    //   3157: aload #19
    //   3159: invokestatic getZwbm : (Ljava/lang/String;)Ljava/lang/String;
    //   3162: astore #20
    //   3164: ldc_w 'A'
    //   3167: astore #21
    //   3169: aload #20
    //   3171: ldc_w '-'
    //   3174: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3177: iconst_1
    //   3178: aaload
    //   3179: astore #22
    //   3181: aload #20
    //   3183: ldc_w '-'
    //   3186: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3189: iconst_2
    //   3190: aaload
    //   3191: astore #23
    //   3193: aload #20
    //   3195: ldc_w '-'
    //   3198: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3201: iconst_3
    //   3202: aaload
    //   3203: astore #24
    //   3205: ldc_w '2'
    //   3208: astore #25
    //   3210: aload #20
    //   3212: ldc_w '-'
    //   3215: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3218: iconst_0
    //   3219: aaload
    //   3220: astore #26
    //   3222: ldc_w '资产'
    //   3225: astore #27
    //   3227: aload #19
    //   3229: ldc_w '-'
    //   3232: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3235: iconst_0
    //   3236: aaload
    //   3237: astore #28
    //   3239: aload #19
    //   3241: ldc_w '-'
    //   3244: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3247: iconst_1
    //   3248: aaload
    //   3249: astore #29
    //   3251: aload #19
    //   3253: ldc_w '-'
    //   3256: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   3259: iconst_2
    //   3260: aaload
    //   3261: astore #30
    //   3263: aload #18
    //   3265: ldc_w 'ZZ_GS'
    //   3268: aload #21
    //   3270: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3275: aload #18
    //   3277: ldc_w 'ZZ_QF'
    //   3280: aload #22
    //   3282: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3287: aload #18
    //   3289: ldc_w 'ZZ_XF'
    //   3292: aload #23
    //   3294: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3299: aload #18
    //   3301: ldc_w 'ZZ_ZW'
    //   3304: aload #24
    //   3306: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3311: aload #18
    //   3313: ldc_w 'ZZ_CJ'
    //   3316: aload #25
    //   3318: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3323: aload #18
    //   3325: ldc_w 'ZZ_JB'
    //   3328: aload #26
    //   3330: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3335: aload #18
    //   3337: ldc_w 'ZZ_GS_TXT'
    //   3340: aload #27
    //   3342: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3347: aload #18
    //   3349: ldc_w 'ZZ_QF_TXT'
    //   3352: aload #28
    //   3354: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3359: aload #18
    //   3361: ldc_w 'ZZ_XF_TXT'
    //   3364: aload #29
    //   3366: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3371: aload #18
    //   3373: ldc_w 'ZZ_ZW_TXT'
    //   3376: aload #30
    //   3378: invokeinterface setValue : (Ljava/lang/String;Ljava/lang/String;)V
    //   3383: aload #9
    //   3385: invokeinterface getTableParameterList : ()Lcom/sap/conn/jco/JCoParameterList;
    //   3390: ldc_w 'IT_LOG'
    //   3393: invokeinterface getTable : (Ljava/lang/String;)Lcom/sap/conn/jco/JCoTable;
    //   3398: astore #18
    //   3400: aload #9
    //   3402: aload #11
    //   3404: invokeinterface execute : (Lcom/sap/conn/jco/JCoDestination;)V
    //   3409: ldc ''
    //   3411: astore #19
    //   3413: iconst_0
    //   3414: istore #20
    //   3416: goto -> 3489
    //   3419: aload #18
    //   3421: iload #20
    //   3423: invokeinterface setRow : (I)V
    //   3428: iload #20
    //   3430: ifle -> 3456
    //   3433: new java/lang/StringBuilder
    //   3436: dup
    //   3437: aload #19
    //   3439: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3442: invokespecial <init> : (Ljava/lang/String;)V
    //   3445: ldc_w '\n'
    //   3448: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3451: invokevirtual toString : ()Ljava/lang/String;
    //   3454: astore #19
    //   3456: new java/lang/StringBuilder
    //   3459: dup
    //   3460: aload #19
    //   3462: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3465: invokespecial <init> : (Ljava/lang/String;)V
    //   3468: aload #18
    //   3470: ldc_w 'ZMESS'
    //   3473: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
    //   3478: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3481: invokevirtual toString : ()Ljava/lang/String;
    //   3484: astore #19
    //   3486: iinc #20, 1
    //   3489: iload #20
    //   3491: aload #18
    //   3493: invokeinterface getNumRows : ()I
    //   3498: if_icmplt -> 3419
    //   3501: ldc ''
    //   3503: aload #19
    //   3505: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3508: ifne -> 3515
    //   3511: aload #19
    //   3513: astore #10
    //   3515: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   3518: new java/lang/StringBuilder
    //   3521: dup
    //   3522: ldc_w '修改用户返回信息为：'
    //   3525: invokespecial <init> : (Ljava/lang/String;)V
    //   3528: aload #19
    //   3530: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3533: invokevirtual toString : ()Ljava/lang/String;
    //   3536: invokevirtual println : (Ljava/lang/String;)V
    //   3539: goto -> 3556
    //   3542: astore #12
    //   3544: aload #12
    //   3546: invokevirtual printStackTrace : ()V
    //   3549: aload #12
    //   3551: invokevirtual getMessage : ()Ljava/lang/String;
    //   3554: astore #10
    //   3556: aload #10
    //   3558: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #200	-> 0
    //   #201	-> 3
    //   #203	-> 7
    //   #206	-> 12
    //   #207	-> 24
    //   #209	-> 30
    //   #210	-> 37
    //   #209	-> 40
    //   #213	-> 47
    //   #214	-> 54
    //   #213	-> 57
    //   #215	-> 64
    //   #216	-> 71
    //   #217	-> 84
    //   #218	-> 96
    //   #219	-> 109
    //   #220	-> 120
    //   #221	-> 125
    //   #220	-> 132
    //   #223	-> 140
    //   #225	-> 152
    //   #226	-> 164
    //   #227	-> 176
    //   #228	-> 189
    //   #230	-> 202
    //   #231	-> 206
    //   #232	-> 210
    //   #231	-> 215
    //   #233	-> 221
    //   #234	-> 236
    //   #235	-> 240
    //   #234	-> 244
    //   #236	-> 250
    //   #237	-> 257
    //   #236	-> 260
    //   #238	-> 267
    //   #239	-> 274
    //   #240	-> 287
    //   #241	-> 299
    //   #242	-> 312
    //   #243	-> 324
    //   #244	-> 337
    //   #245	-> 350
    //   #246	-> 363
    //   #247	-> 376
    //   #248	-> 390
    //   #249	-> 403
    //   #250	-> 407
    //   #252	-> 416
    //   #253	-> 418
    //   #255	-> 428
    //   #253	-> 441
    //   #252	-> 447
    //   #256	-> 449
    //   #257	-> 457
    //   #258	-> 459
    //   #261	-> 464
    //   #262	-> 476
    //   #263	-> 490
    //   #266	-> 507
    //   #267	-> 511
    //   #268	-> 525
    //   #269	-> 542
    //   #268	-> 559
    //   #270	-> 565
    //   #271	-> 585
    //   #272	-> 589
    //   #271	-> 593
    //   #273	-> 599
    //   #274	-> 614
    //   #275	-> 629
    //   #276	-> 636
    //   #275	-> 639
    //   #277	-> 646
    //   #278	-> 653
    //   #279	-> 666
    //   #280	-> 678
    //   #281	-> 691
    //   #282	-> 703
    //   #283	-> 722
    //   #284	-> 740
    //   #285	-> 754
    //   #286	-> 767
    //   #287	-> 775
    //   #286	-> 795
    //   #288	-> 803
    //   #289	-> 808
    //   #288	-> 815
    //   #290	-> 820
    //   #291	-> 833
    //   #292	-> 839
    //   #293	-> 854
    //   #295	-> 864
    //   #296	-> 874
    //   #298	-> 879
    //   #299	-> 892
    //   #300	-> 905
    //   #301	-> 911
    //   #302	-> 926
    //   #304	-> 936
    //   #305	-> 946
    //   #307	-> 951
    //   #308	-> 963
    //   #309	-> 971
    //   #308	-> 978
    //   #310	-> 986
    //   #311	-> 994
    //   #310	-> 1001
    //   #312	-> 1009
    //   #316	-> 1022
    //   #317	-> 1026
    //   #318	-> 1040
    //   #319	-> 1044
    //   #318	-> 1049
    //   #320	-> 1055
    //   #321	-> 1059
    //   #320	-> 1064
    //   #322	-> 1070
    //   #323	-> 1077
    //   #322	-> 1080
    //   #324	-> 1087
    //   #325	-> 1094
    //   #326	-> 1107
    //   #327	-> 1119
    //   #328	-> 1132
    //   #329	-> 1144
    //   #330	-> 1157
    //   #331	-> 1170
    //   #332	-> 1184
    //   #333	-> 1198
    //   #334	-> 1212
    //   #338	-> 1225
    //   #339	-> 1229
    //   #340	-> 1236
    //   #339	-> 1243
    //   #341	-> 1249
    //   #342	-> 1256
    //   #341	-> 1263
    //   #343	-> 1269
    //   #344	-> 1276
    //   #343	-> 1283
    //   #345	-> 1289
    //   #346	-> 1296
    //   #345	-> 1304
    //   #347	-> 1310
    //   #348	-> 1317
    //   #347	-> 1325
    //   #349	-> 1331
    //   #350	-> 1338
    //   #349	-> 1341
    //   #351	-> 1348
    //   #352	-> 1355
    //   #353	-> 1368
    //   #354	-> 1380
    //   #355	-> 1393
    //   #356	-> 1405
    //   #357	-> 1418
    //   #358	-> 1423
    //   #357	-> 1430
    //   #359	-> 1435
    //   #360	-> 1448
    //   #361	-> 1453
    //   #360	-> 1460
    //   #362	-> 1465
    //   #363	-> 1478
    //   #364	-> 1483
    //   #363	-> 1490
    //   #365	-> 1495
    //   #366	-> 1508
    //   #367	-> 1513
    //   #366	-> 1520
    //   #368	-> 1525
    //   #369	-> 1538
    //   #370	-> 1543
    //   #369	-> 1550
    //   #374	-> 1555
    //   #375	-> 1559
    //   #376	-> 1563
    //   #375	-> 1567
    //   #377	-> 1573
    //   #378	-> 1587
    //   #379	-> 1601
    //   #380	-> 1608
    //   #379	-> 1611
    //   #381	-> 1618
    //   #382	-> 1622
    //   #383	-> 1626
    //   #382	-> 1630
    //   #384	-> 1636
    //   #385	-> 1643
    //   #386	-> 1656
    //   #387	-> 1668
    //   #388	-> 1681
    //   #389	-> 1693
    //   #390	-> 1706
    //   #391	-> 1719
    //   #393	-> 1733
    //   #394	-> 1737
    //   #395	-> 1741
    //   #394	-> 1745
    //   #396	-> 1751
    //   #397	-> 1758
    //   #398	-> 1771
    //   #399	-> 1783
    //   #400	-> 1796
    //   #401	-> 1808
    //   #402	-> 1821
    //   #403	-> 1834
    //   #405	-> 1848
    //   #406	-> 1852
    //   #407	-> 1856
    //   #406	-> 1860
    //   #408	-> 1866
    //   #409	-> 1873
    //   #410	-> 1886
    //   #411	-> 1898
    //   #412	-> 1911
    //   #413	-> 1923
    //   #414	-> 1936
    //   #415	-> 1949
    //   #420	-> 1963
    //   #421	-> 1967
    //   #422	-> 1971
    //   #421	-> 1975
    //   #423	-> 1981
    //   #424	-> 1988
    //   #423	-> 1991
    //   #425	-> 1998
    //   #426	-> 2005
    //   #427	-> 2018
    //   #428	-> 2030
    //   #429	-> 2043
    //   #430	-> 2055
    //   #431	-> 2068
    //   #432	-> 2081
    //   #433	-> 2095
    //   #437	-> 2108
    //   #438	-> 2117
    //   #440	-> 2123
    //   #441	-> 2127
    //   #442	-> 2137
    //   #443	-> 2152
    //   #444	-> 2158
    //   #445	-> 2169
    //   #446	-> 2175
    //   #442	-> 2186
    //   #448	-> 2194
    //   #449	-> 2209
    //   #450	-> 2215
    //   #451	-> 2226
    //   #452	-> 2232
    //   #448	-> 2243
    //   #454	-> 2248
    //   #455	-> 2250
    //   #454	-> 2256
    //   #456	-> 2258
    //   #457	-> 2266
    //   #458	-> 2268
    //   #460	-> 2273
    //   #461	-> 2284
    //   #462	-> 2291
    //   #461	-> 2294
    //   #463	-> 2301
    //   #464	-> 2307
    //   #465	-> 2314
    //   #466	-> 2327
    //   #467	-> 2339
    //   #468	-> 2352
    //   #469	-> 2364
    //   #470	-> 2369
    //   #469	-> 2379
    //   #472	-> 2384
    //   #473	-> 2397
    //   #474	-> 2402
    //   #475	-> 2434
    //   #476	-> 2466
    //   #477	-> 2498
    //   #478	-> 2530
    //   #479	-> 2562
    //   #480	-> 2578
    //   #482	-> 2586
    //   #484	-> 2591
    //   #485	-> 2603
    //   #486	-> 2624
    //   #487	-> 2646
    //   #463	-> 2672
    //   #491	-> 2683
    //   #492	-> 2689
    //   #493	-> 2704
    //   #494	-> 2708
    //   #495	-> 2712
    //   #496	-> 2727
    //   #497	-> 2732
    //   #499	-> 2740
    //   #500	-> 2745
    //   #503	-> 2750
    //   #504	-> 2757
    //   #503	-> 2760
    //   #505	-> 2767
    //   #506	-> 2774
    //   #507	-> 2787
    //   #508	-> 2799
    //   #509	-> 2812
    //   #510	-> 2824
    //   #511	-> 2829
    //   #510	-> 2836
    //   #512	-> 2841
    //   #513	-> 2854
    //   #514	-> 2866
    //   #515	-> 2882
    //   #516	-> 2899
    //   #522	-> 2920
    //   #523	-> 2924
    //   #524	-> 2939
    //   #525	-> 2946
    //   #524	-> 2949
    //   #526	-> 2956
    //   #527	-> 2963
    //   #528	-> 2976
    //   #529	-> 2988
    //   #530	-> 3001
    //   #531	-> 3013
    //   #532	-> 3027
    //   #536	-> 3040
    //   #537	-> 3047
    //   #538	-> 3059
    //   #539	-> 3063
    //   #540	-> 3077
    //   #541	-> 3084
    //   #540	-> 3092
    //   #542	-> 3094
    //   #543	-> 3101
    //   #544	-> 3114
    //   #545	-> 3126
    //   #546	-> 3139
    //   #547	-> 3151
    //   #548	-> 3157
    //   #549	-> 3164
    //   #550	-> 3169
    //   #551	-> 3181
    //   #552	-> 3193
    //   #553	-> 3205
    //   #554	-> 3210
    //   #555	-> 3222
    //   #556	-> 3227
    //   #557	-> 3239
    //   #558	-> 3251
    //   #559	-> 3263
    //   #560	-> 3275
    //   #561	-> 3287
    //   #562	-> 3299
    //   #563	-> 3311
    //   #564	-> 3323
    //   #565	-> 3335
    //   #566	-> 3347
    //   #567	-> 3359
    //   #568	-> 3371
    //   #573	-> 3383
    //   #574	-> 3390
    //   #573	-> 3393
    //   #575	-> 3400
    //   #577	-> 3409
    //   #578	-> 3413
    //   #579	-> 3419
    //   #580	-> 3428
    //   #581	-> 3433
    //   #582	-> 3456
    //   #578	-> 3486
    //   #584	-> 3501
    //   #585	-> 3511
    //   #587	-> 3515
    //   #589	-> 3542
    //   #590	-> 3544
    //   #591	-> 3549
    //   #593	-> 3556
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	3559	0	this	Lcom/js/oa/hr/finance/util/EmployeeSynchro;
    //   0	3559	1	employee	Lcom/js/system/vo/usermanager/EmployeeVO;
    //   0	3559	2	empOtherInfo	Lcom/js/system/vo/usermanager/EmployeeOtherInfoVO;
    //   0	3559	3	oldEmployee	Lcom/js/system/vo/usermanager/EmployeeVO;
    //   0	3559	4	oldEmpOtherInfo	Lcom/js/system/vo/usermanager/EmployeeOtherInfoVO;
    //   0	3559	5	czlx	Ljava/lang/String;
    //   0	3559	6	czyy	Ljava/lang/String;
    //   0	3559	7	table_name	Ljava/lang/String;
    //   0	3559	8	recordId	Ljava/lang/String;
    //   3	3556	9	function	Lcom/sap/conn/jco/JCoFunction;
    //   7	3552	10	result	Ljava/lang/String;
    //   12	3547	11	destination	Lcom/sap/conn/jco/JCoDestination;
    //   24	3518	12	currentDate	Ljava/lang/String;
    //   30	3512	13	empId	Ljava/lang/String;
    //   64	3478	14	t_ZT0000	Lcom/sap/conn/jco/JCoTable;
    //   267	240	15	t_ZT0001	Lcom/sap/conn/jco/JCoTable;
    //   407	100	16	orgSerial	Ljava/lang/String;
    //   416	91	17	db	Lcom/js/oa/userdb/util/DbOpt;
    //   459	5	18	e	Ljava/lang/Exception;
    //   646	376	15	t_ZT0002	Lcom/sap/conn/jco/JCoTable;
    //   839	183	16	csd	Ljava/lang/String;
    //   911	111	17	jg	Ljava/lang/String;
    //   1087	138	15	t_ZT0006	Lcom/sap/conn/jco/JCoTable;
    //   1348	207	15	t_ZT0041	Lcom/sap/conn/jco/JCoTable;
    //   1618	345	15	t_ZT0105	Lcom/sap/conn/jco/JCoTable;
    //   1998	110	15	t_ZT0185	Lcom/sap/conn/jco/JCoTable;
    //   2117	1425	15	db1	Lcom/js/oa/userdb/util/DbOpt;
    //   2123	1419	16	results	[[Ljava/lang/String;
    //   2127	139	17	getZnxxSQL	Ljava/lang/String;
    //   2268	5	17	e	Ljava/lang/Exception;
    //   2301	382	17	t_ZT0021	Lcom/sap/conn/jco/JCoTable;
    //   2304	379	18	i	I
    //   2402	270	19	xb	Ljava/lang/String;
    //   2689	853	17	poxm	Ljava/lang/String;
    //   2708	212	18	poxb	Ljava/lang/String;
    //   2712	208	19	gx	Ljava/lang/String;
    //   2767	153	20	t_ZT0021	Lcom/sap/conn/jco/JCoTable;
    //   2956	84	18	t_ZT9009	Lcom/sap/conn/jco/JCoTable;
    //   3094	289	18	t_ZT9010	Lcom/sap/conn/jco/JCoTable;
    //   3157	226	19	dutyName	Ljava/lang/String;
    //   3164	219	20	dutyId	Ljava/lang/String;
    //   3169	214	21	gs	Ljava/lang/String;
    //   3181	202	22	qf	Ljava/lang/String;
    //   3193	190	23	xf	Ljava/lang/String;
    //   3205	178	24	zw	Ljava/lang/String;
    //   3210	173	25	cj	Ljava/lang/String;
    //   3222	161	26	jb	Ljava/lang/String;
    //   3227	156	27	gsmc	Ljava/lang/String;
    //   3239	144	28	qfmc	Ljava/lang/String;
    //   3251	132	29	xfmc	Ljava/lang/String;
    //   3263	120	30	zwmc	Ljava/lang/String;
    //   3400	142	18	t_IT_LOG	Lcom/sap/conn/jco/JCoTable;
    //   3413	129	19	log	Ljava/lang/String;
    //   3416	85	20	i	I
    //   3544	12	12	e	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   12	3539	3542	java/lang/Exception
    //   416	454	457	java/lang/Exception
    //   2123	2263	2266	java/lang/Exception
  }
  
  public static String getStr(String str, int length) {
    String result = "";
    if (str != null && !"".equals(str))
      if (str.length() > length) {
        result = str.substring(0, length);
      } else {
        result = str;
      }  
    return result;
  }
  
  public static boolean notEqu(String str1, String str2) {
    boolean equ = false;
    if (str1 == null && str2 == null) {
      equ = true;
    } else if (str1 != null && str1.equals(str2)) {
      equ = true;
    } else {
      equ = false;
    } 
    return !equ;
  }
  
  public static String getZhiWeiMcByID(String id, String defaultValue) {
    String result = defaultValue;
    DbOpt db = new DbOpt();
    try {
      result = db
        .executeQueryToStr("select name from temp_rsotherinfo  where type='zw' and id='" + 
          id + "'");
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String getZwbm(String zwmc) {
    String result = "";
    if (zwmc != null && !"".equals(zwmc)) {
      String sql = "select dutyno from oa_duty where dutyname='" + zwmc + 
        "'";
      DbOpt db = new DbOpt();
      try {
        result = db.executeQueryToStr(sql);
        db.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public static String getGwbm(String gwmc) {
    String result = "";
    if (gwmc != null && !"".equals(gwmc)) {
      String sql = "select no from st_station where station_name='" + 
        gwmc + "'";
      DbOpt db = new DbOpt();
      try {
        result = db.executeQueryToStr(sql);
        db.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
  
  public static String getDateString(Date date) {
    String dateStr = "";
    if (date == null)
      date = new Date(); 
    String year = (new StringBuilder(String.valueOf(date.getYear() + 1900))).toString();
    String month = "0" + (date.getMonth() + 1);
    if (month.length() > 2)
      month = month.substring(month.length() - 2, month.length()); 
    String day = "0" + date.getDate();
    if (day.length() > 2)
      day = day.substring(day.length() - 2, day.length()); 
    dateStr = String.valueOf(year) + month + day;
    return dateStr;
  }
  
  public static String getDateStringFromString(String date) {
    String splitStr = "/";
    if (date.indexOf("/") > 0) {
      splitStr = "/";
    } else {
      splitStr = "-";
    } 
    String dateStr = "";
    if (date == null || "".equals(date) || (date.split(splitStr)).length < 3)
      return getDateString(new Date()); 
    String year = date.split(splitStr)[0];
    String month = "0" + date.split(splitStr)[1];
    if (month.length() > 2)
      month = month.substring(month.length() - 2, month.length()); 
    String day = "0" + date.split(splitStr)[2];
    if (day.length() > 2)
      day = day.substring(day.length() - 2, day.length()); 
    dateStr = String.valueOf(year) + month + day;
    return dateStr;
  }
}
