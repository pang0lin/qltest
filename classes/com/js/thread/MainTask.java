package com.js.thread;

import com.js.ldap.MSAD;
import com.js.ldap.MSADNoCert;
import com.js.ldap.OAToAD;
import com.js.ldap.OpenLDAP;
import com.js.oa.chinaLife.util.ChinaLifeKq;
import com.js.oa.database.util.CustomTask;
import com.js.oa.database.util.TaskFactory;
import com.js.oa.database.util.XmlUtil;
import com.js.oa.dcq.bean.BusinessShowBean;
import com.js.oa.form.hqzd.SignUtil;
import com.js.oa.form.pengchi.CreateProcessForOB;
import com.js.oa.form.pengchi.CreateProcessForOBKS;
import com.js.oa.form.pengchi.CreateProcessForOBOP;
import com.js.oa.form.pengchi.CreateProcessForOBOPKS;
import com.js.oa.form.pengchi.CreateProcessForOF;
import com.js.oa.form.pengchi.CreateProcessForOFKS;
import com.js.oa.form.pengchi.CreateProcessForOG;
import com.js.oa.form.pengchi.CreateProcessForOGKS;
import com.js.oa.form.pengchi.CreateProcessForONOP;
import com.js.oa.form.pengchi.CreateProcessForONOPKS;
import com.js.oa.form.pengchi.CreateProcessForOQ;
import com.js.oa.form.pengchi.CreateProcessForOQKS;
import com.js.oa.form.pengchi.CreateProcessForOROP;
import com.js.oa.form.pengchi.CreateProcessForOROPKS;
import com.js.oa.form.pengchi.CreateProcessForOS;
import com.js.oa.form.pengchi.CreateProcessForOSKS;
import com.js.oa.form.pengchi.CreateProcessForWX;
import com.js.oa.form.txfh.ProgramStatusUtil;
import com.js.oa.haier.service.ERPStackService;
import com.js.oa.hr.kq.bean.BhwKqBean;
import com.js.oa.hr.kq.bry.BryTongji;
import com.js.oa.hr.kq.bry.KqTran;
import com.js.oa.hr.kq.util.ImportInfo;
import com.js.oa.hr.kq.util.WanXieKq;
import com.js.oa.hr.personnelmanager.util.EmpRemindUtil;
import com.js.oa.hr.subsidiarywork.service.DesktopBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.info.util.ChannelCache;
import com.js.oa.logon.service.LogonBD;
import com.js.oa.message.bean.LanDaReceviceSMSBean;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.oasysremind.bean.RemindOnTime;
import com.js.oa.routine.officeroom.util.UpdateTask;
import com.js.oa.security.log.util.LogService;
import com.js.oa.webservice.szgt.SZGTService;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.rssmanager.RssChannelBD;
import com.js.system.util.OperateUserFromBase;
import com.js.system.vo.rssmanager.CategoryChannelVO;
import com.js.system.vo.rssmanager.ChannelItemVO;
import com.js.util.config.SysConfigReader;
import com.js.util.config.SystemCommon;
import com.js.util.rss.RomeRss;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import com.js.util.util.TranDataBaseConfig;
import com.js.util.util.TranDataFromOtherDataBaseToOA;
import com.sun.syndication.feed.synd.SyndEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;
import javax.sql.DataSource;
import net.jiusi.jsoa.unitCertify.syncuser.service.SyncUserService;
import org.dom4j.DocumentException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class MainTask extends TimerTask {
  private static String sendSMS;
  
  private int exeNum = -1;
  
  private boolean remind = false;
  
  private int helpNum = 0;
  
  private int ADNum = 1;
  
  private int openNum = 1;
  
  private int dcqCount = 0;
  
  private int bhwCount = 0;
  
  private int pengJdeCount = 0;
  
  private int ldSmsCount = 0;
  
  Map map = canUseAD();
  
  String useLDAP = (String)this.map.get("useLDAP");
  
  String isAuto = (String)this.map.get("isAuto");
  
  String interval = (String)this.map.get("interval");
  
  String staticTime = (String)this.map.get("staticTime");
  
  String to = (String)this.map.get("to");
  
  private Map<String, String> openDSMap = (new OpenLDAP()).getLdapInfo();
  
  private SimpleDateFormat hmformat = new SimpleDateFormat("HH:mm");
  
  public void run() {
    this.helpNum++;
    this.ADNum++;
    this.openNum++;
    try {
      if (canSendSMS()) {
        SMSTask task = new SMSTask();
        task.execute();
      } 
      if (!"0".equals(this.openDSMap.get("ldapUse")) && "1".equals(this.openDSMap.get("AutoSync"))) {
        if (this.hmformat.format(new Date()).equals(this.openDSMap.get("staticTime")))
          if ("2".equals(this.openDSMap.get("ldapUse"))) {
            (new OperateUserFromBase()).userAndOrg();
          } else {
            (new OperateUserFromBase()).getUserInfoFromOtherBase();
          }  
        if (!"".equals(this.openDSMap.get("AutoSync")) && this.openNum == Integer.valueOf(this.openDSMap.get("interval")).intValue()) {
          if ("2".equals(this.openDSMap.get("ldapUse"))) {
            (new OperateUserFromBase()).userAndOrg();
          } else {
            (new OperateUserFromBase()).getUserInfoFromOtherBase();
          } 
          this.openNum = 0;
        } 
      } 
      if ("1".equals(this.useLDAP) && "1".equals(this.isAuto)) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (sdf.format(date).toString().equals(this.staticTime)) {
          System.out.println("开始与AD同步");
          try {
            if ("AD".equalsIgnoreCase(this.to)) {
              OAToAD msad = new OAToAD();
              msad.insertGroup();
              msad.createUsers();
            } else {
              MSAD ms = new MSAD();
              if (MSAD.getUseCert() == 0) {
                MSADNoCert adNoCert = new MSADNoCert();
                adNoCert.queryItem("");
              } else {
                ms.queryItem("");
              } 
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
        if (!"".equals(this.interval) && this.ADNum == Integer.parseInt(this.interval)) {
          this.ADNum = 0;
          System.out.println("开始与AD同步");
          try {
            if ("AD".equalsIgnoreCase(this.to)) {
              OAToAD msad = new OAToAD();
              msad.insertGroup();
              msad.createUsers();
            } else {
              MSAD ms = new MSAD();
              ms.queryItem("");
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
      } 
      Date nowDate = new Date();
      if ("true".equals(SystemCommon.getHqzdUpdata()) && 
        "00:00".equals(this.hmformat.format(nowDate))) {
        System.out.println(String.valueOf(this.hmformat.format(nowDate)) + " 定期同步环球智达数据！");
        SignUtil ss = new SignUtil();
        ss.postData();
      } 
      "01:30".equals(this.hmformat.format(nowDate));
      TranDataFromOtherDataBaseToOA tran = new TranDataFromOtherDataBaseToOA();
      TranDataBaseConfig config = tran.readConfig();
      if (config.getUse().equals("1") && (
        "," + config.getTranTime() + ",").contains("," + this.hmformat.format(nowDate) + ",")) {
        System.out.println(String.valueOf(this.hmformat.format(nowDate)) + " 定期同步数据！");
        tran.getDataFromOtherDataBase();
      } 
      if ("01:45".equals(this.hmformat.format(nowDate)) && SystemCommon.getCheckon()) {
        ImportInfo ik = new ImportInfo();
        try {
          System.out.println("开始同步考勤");
          ik.importData("", null);
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      if (SystemCommon.getWxkjKq() == 1 && SystemCommon.getWxTime().equals(this.hmformat.format(nowDate))) {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        (new WanXieKq()).mainFunction(ymd.format(nowDate));
      } 
      if ("chinaLife".equals(SystemCommon.getCustomerName()) && "1".equals(SystemCommon.getClkq())) {
        String nowTime = this.hmformat.format(nowDate);
        if (SystemCommon.getTbUser().equals(nowTime) || SystemCommon.getClFirst().equals(nowTime) || 
          SystemCommon.getClSecond().equals(nowTime)) {
          ChinaLifeKq kq = new ChinaLifeKq();
          String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
          if (SystemCommon.getTbUser().equals(nowTime)) {
            IO2File.printFile("开始同步考勤人员数据", "kemiKq.txt");
            kq.insertUserKqDefault(dateStr);
          } 
          if (SystemCommon.getClFirst().equals(nowTime)) {
            IO2File.printFile("开始同步上午考勤数据", "kemiKq.txt");
            kq.kqTongbu(1, dateStr);
          } 
          if (SystemCommon.getClSecond().equals(nowTime)) {
            String[] nowTimeStrs = nowTime.split(":");
            int now = Integer.valueOf(nowTimeStrs[0]).intValue() * 60 + Integer.valueOf(nowTimeStrs[1]).intValue();
            if (now >= 0 && now <= 540)
              dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date((new Date()).getTime() - 86400000L)); 
            IO2File.printFile("开始同步下午考勤数据", "kemiKq.txt");
            kq.kqTongbu(2, dateStr);
          } 
        } 
      } else if ("bry".equals(SystemCommon.getCustomerName())) {
        String nowTime = this.hmformat.format(nowDate);
        if (nowTime.equals("00:45")) {
          IO2File.printFile("宝日医同步考勤人员信息……………………");
          (new KqTran()).userDataTran();
        } 
        if (nowTime.equals("23:15")) {
          IO2File.printFile("宝日医同步考勤数据……………………");
          String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
          KqTran kqTran = new KqTran();
          kqTran.kqDataTran(dateStr);
          kqTran.kqTran(dateStr);
        } 
        if (nowTime.equals("23:45")) {
          String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
          IO2File.printFile("宝日医计算考勤统计……………………");
          String[] dates = dateStr.split("-");
          if (Integer.valueOf(dates[2]).intValue() > 25) {
            (new BryTongji()).kqTongbu(String.valueOf(dateStr.substring(0, 8)) + "26", dateStr);
          } else {
            int m = Integer.valueOf(dates[1]).intValue();
            if (m == 1) {
              dates[0] = (new StringBuilder(String.valueOf(Integer.valueOf(dates[0]).intValue() - 1))).toString();
              dates[1] = "12";
            } else if (m <= 10) {
              dates[1] = "0" + (m - 1);
            } else {
              dates[1] = m - 1;
            } 
            (new BryTongji()).kqTongbu(String.valueOf(dates[0]) + "-" + dates[1] + "-26", dateStr);
          } 
        } 
        if (nowTime.equals("09:00")) {
          IO2File.printFile("宝日医考勤打卡提醒……………………");
          (new BryTongji()).daKaRemind();
        } 
      } else if ("landaedu".equals(SystemCommon.getCustomerName())) {
        try {
          this.ldSmsCount++;
          if ("1".equals(this.openDSMap.get("AutoSync"))) {
            String nowTime = this.hmformat.format(nowDate);
            if (nowTime.equals("00:00"))
              (new OperateUserFromBase()).userAndOrg(); 
          } 
          int ldSmsC = Integer.valueOf(SysConfigReader.readConfigValue("LDSMS", "min")).intValue();
          if (ldSmsC == this.ldSmsCount) {
            boolean flag = (new LanDaReceviceSMSBean()).receviceSMS();
            this.ldSmsCount = 0;
          } 
        } catch (Exception err) {
          err.printStackTrace();
          this.ldSmsCount = 0;
        } 
      } else if ("dcq".equals(SystemCommon.getCustomerName())) {
        this.dcqCount++;
        try {
          if (15 == this.dcqCount) {
            File file = new File(getClass().getClassLoader().getResource("/").getPath());
            String classPath = file.getCanonicalPath();
            String realPath = classPath.substring(0, classPath.indexOf("WEB-INF"));
            boolean flag = (new BusinessShowBean()).getBusinessMessage("1cd11d8875c9bd7d5a3dd37756cab950", "C3800", realPath);
            System.out.println(new Date() + "==定时获取数据==" + flag);
            this.dcqCount = 0;
          } 
        } catch (IOException e) {
          e.printStackTrace();
        } 
      } else if ("bhw".equals(SystemCommon.getCustomerName())) {
        this.bhwCount++;
        if (120 == this.bhwCount) {
          (new BhwKqBean()).getInfos();
          System.out.println("百合网考勤数据同步：" + nowDate);
          this.bhwCount = 0;
        } 
      } else if ("haier".equals(SystemCommon.getCustomerName())) {
        String nowTime = this.hmformat.format(nowDate);
        String firstTimeStr = SystemCommon.getFirSync();
        String secondTimeStr = SystemCommon.getSecSync();
        if (nowTime.equals(firstTimeStr)) {
          (new ERPStackService()).addDataAndSendEmail();
          System.out.println("海尔库存信息更新:" + nowDate);
        } 
        if (nowTime.equals(secondTimeStr)) {
          (new ERPStackService()).addDataAndSendEmail();
          System.out.println("海尔库存信息更新:" + nowDate);
        } 
      } else if ("shenzhougaotie".equals(SystemCommon.getCustomerName())) {
        if ("01:00".equals(this.hmformat.format(nowDate))) {
          String result = (new SZGTService()).getDatas();
          System.out.println(nowDate + ">>神舟高铁组织用户同步：" + result);
        } 
      } else if ("TXFH".equals(SystemCommon.getCustomerName())) {
        System.out.println("-----天下凤凰凌晨读取项目状态开始------");
        if ("00:00".equals(this.hmformat.format(nowDate))) {
          ProgramStatusUtil stautil = new ProgramStatusUtil();
          stautil.setProgramStatus();
        } 
      } else if ("pengchi".equals(SystemCommon.getCustomerName())) {
        this.pengJdeCount++;
        int pengJde = 10;
        if (SysConfigReader.readConfigValue("PCJDE", "min") != null && !"".equals(SysConfigReader.readConfigValue("PCJDE", "min")))
          pengJde = Integer.valueOf(SysConfigReader.readConfigValue("PCJDE", "min")).intValue(); 
        if (this.pengJdeCount == pengJde) {
          try {
            CreateProcessForOF of = new CreateProcessForOF();
            of.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOQ oq = new CreateProcessForOQ();
            oq.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOB ob = new CreateProcessForOB();
            ob.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOG og = new CreateProcessForOG();
            og.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOS os = new CreateProcessForOS();
            os.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOBOP op = new CreateProcessForOBOP();
            op.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOROP orop = new CreateProcessForOROP();
            orop.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForONOP onop = new CreateProcessForONOP();
            onop.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOBKS obks = new CreateProcessForOBKS();
            obks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOFKS ofks = new CreateProcessForOFKS();
            ofks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOQKS oqks = new CreateProcessForOQKS();
            oqks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOSKS osks = new CreateProcessForOSKS();
            osks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOGKS ogks = new CreateProcessForOGKS();
            ogks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOBOPKS opks = new CreateProcessForOBOPKS();
            opks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForOROPKS oropks = new CreateProcessForOROPKS();
            oropks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForONOPKS onopks = new CreateProcessForONOPKS();
            onopks.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          try {
            CreateProcessForWX wx = new CreateProcessForWX();
            wx.startProcess();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          this.pengJdeCount = 0;
        } 
      } 
      Calendar nowCalendar = Calendar.getInstance();
      int hour = nowCalendar.get(11);
      if ((hour == 2 && this.exeNum == 0) || this.exeNum == -1)
        this.remind = true; 
      if (hour != 2)
        this.exeNum = 0; 
      if (this.remind) {
        System.out.println("凌晨2点开始执行后台任务......");
        (new InformationBD()).resetOrderCode();
        updateAllRss();
        System.out.println("更新RSS.........................");
        DesktopBD desktopBD = new DesktopBD();
        desktopBD.executeWishRemind();
        System.out.println("生日节日提醒......................");
        try {
          EmpRemindUtil.weekEmpRemind();
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
        System.out.println("人事转正等提醒......................");
        MessagesBD mbd = new MessagesBD();
        try {
          mbd.clearMessageStatus();
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
        System.out.println("清理30前的系统消息......................");
        deleteSysMessages();
        (new UpdateTask()).updateOverDue("");
        System.out.println("转移当天日志到历史表中......................");
        LogService.doMethod();
        System.out.println("定时会议的邮件提醒.............");
        (new MeetingRemindSendEmail()).execute();
        InformationBD idb = new InformationBD();
        idb.resetOrderCode();
        ChannelCache.initChannel(null);
        if ((hour == 2 && this.exeNum == 0) || (hour == 2 && this.exeNum == -1)) {
          RemindOnTime remindOnTime2 = new RemindOnTime();
          remindOnTime2.doRemind();
          this.exeNum = 1;
        } 
        this.remind = false;
        (new OACollectEJBBean()).doRemindSys();
        if ("1".equals(SystemCommon.getUnitCertifySwitch()) && "1".equals(SystemCommon.getIfAutoSyncUserUnitCertify())) {
          SyncUserService syncUserService = new SyncUserService();
          syncUserService.syncUserByHand(SystemCommon.getOrganizationNameUnitCertify());
        } 
        System.out.println("凌晨2点开始执行后台任务......结束 时间：" + (new Date()).toLocaleString());
      } 
      if (this.helpNum == 15) {
        this.helpNum = 0;
        VBHelperTask();
      } 
      if (this.helpNum % 2 == 0)
        initSideLineOrg(); 
    } catch (Exception ex) {
      ex.printStackTrace();
      this.helpNum = 0;
      this.ADNum = 0;
      this.openNum = 0;
    } 
  }
  
  public Map canUseAD() {
    Map<Object, Object> map = new HashMap<Object, Object>();
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String useLDAP = "";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element ldapConfig = doc.getRootElement().getChild("LdapConfig");
    if (ldapConfig != null) {
      useLDAP = ldapConfig.getAttribute("use").getValue();
      map.put("useLDAP", useLDAP);
      String to = "";
      if (ldapConfig.getAttribute("to") != null)
        to = ldapConfig.getAttribute("to").getValue(); 
      SystemCommon.setTo(to);
      map.put("to", to);
      Element autoSync = ldapConfig.getChild("AutoSync");
      String isAuto = autoSync.getAttribute("value").getValue();
      String interval = autoSync.getAttribute("interval").getValue();
      String staticTime = autoSync.getAttribute("staticTime").getValue();
      map.put("isAuto", isAuto);
      map.put("interval", interval);
      map.put("staticTime", staticTime);
    } 
    return map;
  }
  
  public boolean canSendSMS() {
    boolean canSend = false;
    if (sendSMS == null)
      try {
        String path = System.getProperty("user.dir");
        String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        FileInputStream configFileInputStream = new FileInputStream(
            new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(configFileInputStream);
        Element root = doc.getRootElement();
        Element node = root.getChild("SMSServer");
        sendSMS = node.getAttributeValue("type");
      } catch (Exception ex) {
        ex.printStackTrace();
      }  
    if (sendSMS != null && "1".equals(sendSMS))
      canSend = true; 
    return canSend;
  }
  
  public void backupDatabase() {
    CustomTask ct = TaskFactory.getTask();
    if (ct != null) {
      XmlUtil xmlUtil = new XmlUtil();
      String type = "";
      String maxdate = "";
      try {
        type = xmlUtil.searchBackupType();
      } catch (DocumentException documentException) {}
      try {
        maxdate = xmlUtil.searchLastBackupDate();
      } catch (DocumentException e) {
        e.printStackTrace();
      } 
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String date = formatter.format(new Date());
      Calendar calendar = Calendar.getInstance();
      if (maxdate.equals("")) {
        try {
          ct.execute();
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if (type.equals("0")) {
        try {
          Date parse1 = formatter.parse(maxdate);
          Date parse3 = formatter.parse(date);
          if (parse1.before(parse3))
            ct.execute(); 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else if (type.equals("1")) {
        try {
          Date parse1 = formatter.parse(maxdate);
          calendar.setTime(parse1);
          calendar.add(3, 1);
          Date parse2 = calendar.getTime();
          Date parse3 = formatter.parse(date);
          if (parse2.before(parse3) || parse2.equals(parse3))
            ct.execute(); 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else {
        try {
          Date parse1 = formatter.parse(maxdate);
          calendar.setTime(parse1);
          calendar.add(2, 1);
          Date parse2 = calendar.getTime();
          Date parse3 = formatter.parse(date);
          if (parse2.before(parse3) || parse2.equals(parse3))
            ct.execute(); 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  private void deleteSysMessages() {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    int timeScan = -30;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(6);
    calendar.set(6, i + timeScan);
    String nowString = formatter.format(calendar.getTime());
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        stmt.executeUpdate("delete from sys_messages where message_date_begin<'" + nowString + "'");
      } else {
        stmt.executeUpdate("delete from sys_messages where message_date_begin<jsdb.FN_STRTODATE('" + nowString + "','S')");
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  private void updateAllRss() {
    RssChannelBD rcb = new RssChannelBD();
    List<CategoryChannelVO> channelList = rcb.getRssChannelList();
    if (channelList != null && channelList.size() > 0)
      for (int i = 0; i < channelList.size(); i++) {
        CategoryChannelVO ccv = channelList.get(i);
        List itemList = setChannelItemVO(ccv.getChannelId(), ccv.getChannelUrl());
        rcb.saveChannelItem(itemList);
      }  
  }
  
  private List setChannelItemVO(Long channelId, String url) {
    List<ChannelItemVO> list = null;
    ChannelItemVO itemVO = null;
    String format = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    try {
      RomeRss romeRss = new RomeRss(url);
      List<SyndEntry> listItem = romeRss.getChannelItemList();
      if (listItem != null && listItem.size() > 0) {
        list = new ArrayList();
        for (int i = 0; i < listItem.size(); i++) {
          SyndEntry entry = listItem.get(i);
          itemVO = new ChannelItemVO();
          itemVO.setChannelId(channelId);
          itemVO.setItemDesc(entry.getDescription().getValue().trim());
          itemVO.setItemLink(entry.getLink());
          itemVO.setItemTitle(entry.getTitle());
          if (entry.getPublishedDate() == null) {
            itemVO.setPubDate(sdf.format(new Date()));
          } else {
            itemVO.setPubDate(sdf.format(entry.getPublishedDate()));
          } 
          list.add(itemVO);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  private void VBHelperTask() {
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(12, -15);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String empId = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        rs = stmt.executeQuery("select online_user.USER_ID from sec_onlineuser online_user where online_user.USER_UPDATETIME < TO_DATE('" + format.format(calendar.getTime()) + "', 'YYYY-MM-DD HH24:MI:SS') and online_user.ishelper=1");
      } else {
        rs = stmt.executeQuery("select online.USER_ID  from sec_onlineuser online where online.USER_UPDATETIME < '" + format.format(calendar.getTime()) + "' and online.ishelper=1 ");
      } 
      while (rs.next()) {
        empId = rs.getString(1);
        LogonBD logonBD = new LogonBD();
        MessagesBD messagesBD = new MessagesBD();
        logonBD.delForVb(empId);
        messagesBD.setMessageStatus(empId, "0");
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public void initSideLineOrg() {
    Connection conn = null;
    Statement stmt = null;
    String sql = "SELECT emp.emp_id,emp.sidelineorg,emp.sidelineorgname FROM org_employee emp JOIN org_organization_user ou ON emp.emp_id=ou.emp_id WHERE emp.sidelineorg IS NOT NULL AND emp.sidelineorg<>''";
    try {
      List<String[]> list = (List)new ArrayList<String>();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String empId = rs.getString(1);
        String sidelineOrg = rs.getString(2);
        String sidelineOrgName = rs.getString(3);
        if (sidelineOrg != null && !"null".equals(sidelineOrg) && sidelineOrg.length() > 1) {
          if (sidelineOrg.indexOf("*") >= 0)
            sidelineOrg = sidelineOrg.substring(1, sidelineOrg.length() - 1); 
          if (sidelineOrgName.endsWith(","))
            sidelineOrgName = sidelineOrgName.substring(0, sidelineOrgName.length() - 1); 
          String[] sidelineOrgArr = sidelineOrg.split("\\*\\*");
          String[] sidelineOrgNameArr = sidelineOrgName.split(",");
          for (int j = 0; j < sidelineOrgArr.length; j++) {
            list.add(new String[] { empId, sidelineOrgArr[j], sidelineOrgNameArr[j] });
          } 
        } 
      } 
      rs.close();
      int i;
      for (i = 0; i < list.size(); i++) {
        String[] arr = list.get(i);
        sql = "select emp_id from org_sideline where emp_id=" + arr[0] + " and org_id=" + arr[1];
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
          rs.close();
          sql = "update org_sideline set org_name='" + arr[2] + "' where emp_id=" + arr[0] + " and org_id=" + arr[1];
          stmt.executeUpdate(sql);
        } else {
          rs.close();
          sql = "insert into org_sideline(emp_id,org_id,org_name) values(" + arr[0] + "," + arr[1] + ",'" + arr[2] + "')";
          stmt.executeUpdate(sql);
        } 
      } 
      list.clear();
      if (SystemCommon.getDatabaseType().indexOf("mysql") >= 0) {
        sql = "SELECT os.emp_id,os.org_id FROM org_sideline os LEFT JOIN org_employee oe ON os.emp_id=oe.emp_id WHERE (oe.sidelineorg IS NULL OR oe.sidelineorg NOT LIKE CONCAT('%*',os.org_id,'*%'))";
      } else {
        sql = "SELECT os.emp_id,os.org_id FROM org_sideline os LEFT JOIN org_employee oe ON os.emp_id=oe.emp_id WHERE (oe.sidelineorg IS NULL OR oe.sidelineorg NOT LIKE '%*'||os.org_id||'*%')";
      } 
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2) });
      } 
      rs.close();
      for (i = 0; i < list.size(); i++) {
        String[] arr = list.get(i);
        stmt.executeUpdate("delete from org_sideline where emp_id=" + arr[0] + " and org_id=" + arr[1]);
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
}
