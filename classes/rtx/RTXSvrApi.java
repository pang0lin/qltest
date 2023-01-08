package rtx;

import com.js.util.config.SystemCommon;
import com.js.util.util.RTXStrSingleton;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.msmResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.mobset.sms.sms_Send;

public class RTXSvrApi {
  static int PRO_ADDUSER = 1;
  
  static int PRO_DELUSER = 2;
  
  static int PRO_GETUSERDETAILINFO = 4;
  
  static int PRO_SETUSERDETAILINFO = 5;
  
  static int PRO_GETUSERSMPLINFO = 6;
  
  static int PRO_SETUSERSMPLINFO = 3;
  
  static int PRO_SETUSERPRIVILEGE = 7;
  
  static int PRO_IFEXIST = 8;
  
  static int PRO_TRANUSER = 9;
  
  static int PRO_ADDDEPT = 257;
  
  static int PRO_DELDEPT = 258;
  
  static int PRO_SETDEPT = 259;
  
  static int PRO_GETCHILDDEPT = 260;
  
  static int PRO_GETDEPTALLUSER = 261;
  
  static int PRO_SETDEPTPRIVILEGE = 262;
  
  static int PRO_GETDEPTSMPLINFO = 263;
  
  static int PRO_SMS_LOGON = 4096;
  
  static int PRO_SMS_SEND = 4097;
  
  static int PRO_SMS_NICKLIST = 4098;
  
  static int PRO_SMS_FUNCLIST = 4099;
  
  static int PRO_SMS_CHECK = 4100;
  
  static int PRO_SYS_GETSESSIONKEY = 8192;
  
  static int PRO_SYS_GETUSERSTATUS = 8193;
  
  static int PRO_SYS_SENDIM = 8194;
  
  static int PRO_SYS_SESSIONKEYVERIFY = 8195;
  
  static int PRO_EXT_NOTIFY = 8448;
  
  static int PRO_IMPORTUSER = 1;
  
  static int PRO_EXMPORTUSER = 2;
  
  static String OBJNAME_RTXEXT = "EXTTOOLS";
  
  static String OBJNAME_RTXSYS = "SYSTOOLS";
  
  static String OBJNAME_DEPTMANAGER = "DEPTMANAGER";
  
  static String OBJNAME_USERMANAGER = "USERMANAGER";
  
  static String OBJNAME_SMSMANAGER = "SMSOBJECT";
  
  static String OBJNAME_USERSYNC = "USERSYNC";
  
  static String KEY_TYPE = "TYPE";
  
  static String KEY_USERID = "USERID";
  
  static String KEY_USERNAME = "USERNAME";
  
  static String KEY_UINTYPE = "UINTYPE";
  
  static String KEY_UIN = "UIN";
  
  static String KEY_NICK = "NICK";
  
  static String KEY_MOBILE = "MOBILE";
  
  static String KEY_OUTERUIN = "OUTERUIN";
  
  static String KEY_LASTMODIFYTIME = "LASTMODIFYTIME";
  
  static String KEY_FACE = "FACE";
  
  static String KEY_PASSWORD = "PWD";
  
  static String KEY_AGE = "AGE";
  
  static String KEY_GENDER = "GENDER";
  
  static String KEY_BIRTHDAY = "BIRTHDAY";
  
  static String KEY_BLOODTYPE = "BLOODTYPE";
  
  static String KEY_CONSTELLATION = "CONSTELLATION";
  
  static String KEY_COLLAGE = "COLLAGE";
  
  static String KEY_HOMEPAGE = "HOMEPAGE";
  
  static String KEY_EMAIL = "EMAIL";
  
  static String KEY_PHONE = "PHONE";
  
  static String KEY_FAX = "FAX";
  
  static String KEY_ADDRESS = "ADDRESS";
  
  static String KEY_POSTCODE = "POSTCODE";
  
  static String KEY_COUNTRY = "COUNTRY";
  
  static String KEY_PROVINCE = "PROVINCE";
  
  static String KEY_CITY = "CITY";
  
  static String KEY_MEMO = "MEMO";
  
  static String KEY_STREET = "STREET";
  
  static String KEY_MOBILETYPE = "MOBILETYPE";
  
  static String KEY_AUTHTYPE = "AUTHTYPE";
  
  static String KEY_POSITION = "POSITION";
  
  static String KEY_OPENGSMINFO = "OPENGSMINFO";
  
  static String KEY_OPENCONTACTINFO = "OPENCONTACTINFO";
  
  static String KEY_PUBOUTUIN = "PUBOUTUIN";
  
  static String KEY_PUBOUTNICK = "PUBOUTNICK";
  
  static String KEY_PUBOUTNAME = "PUBOUTNAME";
  
  static String KEY_PUBOUTDEPT = "PUBOUTDEPT";
  
  static String KEY_PUBOUTPOSITION = "PUBOUTPOSITION";
  
  static String KEY_PUBOUTINFO = "PUBOUTINFO";
  
  static String KEY_OUTERPUBLISH = "OUTERPUBLISH";
  
  static String KEY_LDAPID = "LDAPID";
  
  static String KEY_DEPTID = "DEPTID";
  
  static String KEY_PDEPTID = "PDEPTID";
  
  static String KEY_SORTID = "SORTID";
  
  static String KEY_NAME = "NAME";
  
  static String KEY_INFO = "INFO";
  
  static String KEY_COMPLETEDELBS = "COMPLETEDELBS";
  
  static String KEY_DENY = "DENY";
  
  static String KEY_ALLOW = "ALLOW";
  
  static String KEY_SESSIONKEY = "SESSIONKEY";
  
  static String KEY_MODIFYMODE = "MODIFYMODE";
  
  static String KEY_DATA = "DATA";
  
  static String KEY_SENDER = "SENDER";
  
  static String KEY_FUNNO = "FUNCNO";
  
  static String KEY_RECEIVER = "RECEIVER";
  
  static String KEY_RECEIVERUIN = "RECEIVERUIN";
  
  static String KEY_SMS = "SMS";
  
  static String KEY_CUT = "CUT";
  
  static String KEY_NOTITLE = "NOTITLE";
  
  static String KEY_DELFLAG = "DELFLAG";
  
  static String KEY_RECVUSERS = "RECVUSERS";
  
  static String KEY_IMMSG = "IMMSG";
  
  static String KEY_MSGID = "MSGID";
  
  static String KEY_MSGINFO = "MSGINFO";
  
  static String KEY_ASSISTANTTYPE = "ASSTYPE";
  
  static String KEY_TITLE = "TITLE";
  
  static String KEY_DELAYTIME = "DELAYTIME";
  
  static String KEY_RESULT_INCODE = "INNERCODE";
  
  static String KEY_RESULT_ERR_INFO = "ERR_INFO";
  
  static String KEY_RESULT_CODE = "CODE";
  
  static String KEY_RESULT_TYPE = "TYPE";
  
  static String KEY_RESULT_NAME = "NAME";
  
  static String KEY_RESULT_VALUE = "VALUE";
  
  static String KEY_RESULT_VARIANT = "VARIANT";
  
  static String domain;
  
  static String useRTX;
  
  static String port;
  
  private int iObj = 0;
  
  private int iProp = 0;
  
  private int iCode = 0;
  
  private int iResult = 0;
  
  RTXStrSingleton rStrSingleton = RTXStrSingleton.getInstance();
  
  static {
    System.loadLibrary("SDKAPIJava");
  }
  
  public native boolean Init();
  
  public native void UnInit();
  
  public native String GetError(int paramInt);
  
  public native String GetVersion();
  
  public native int GetNewObject(String paramString);
  
  public native String GetObjectName(int paramInt);
  
  public native int SetObjectName(int paramInt, String paramString);
  
  public native int GetNewPropertys();
  
  public native int IsHandle(int paramInt);
  
  public native int AddRefHandle(int paramInt);
  
  public native int ReleaseHandle(int paramInt);
  
  public native int AddProperty(int paramInt, String paramString1, String paramString2);
  
  public native int ClearProperty(int paramInt1, int paramInt2);
  
  public native int RemoveProperty(int paramInt, String paramString);
  
  public native String GetProperty(int paramInt, String paramString);
  
  public native int SetServerIP(int paramInt, String paramString);
  
  public native String GetServerIP(int paramInt);
  
  public native int GetServerPort(int paramInt);
  
  public native int SetServerPort(int paramInt1, int paramInt2);
  
  public native int GetPropertysCount(int paramInt);
  
  public native int GetPropertysItem(int paramInt1, int paramInt2);
  
  public native int Call(int paramInt1, int paramInt2, int paramInt3);
  
  public native int GetResultPropertys(int paramInt);
  
  public native int GetResultInt(int paramInt);
  
  public native String GetResultString(int paramInt);
  
  public String GetPropertyItemName(int iHandle) {
    return GetProperty(iHandle, KEY_RESULT_NAME);
  }
  
  public String GetPropertyItemValue(int iHandle) {
    return GetProperty(iHandle, KEY_RESULT_VALUE);
  }
  
  public int GetResultInnerCode(int iHandle) {
    String sz = GetProperty(iHandle, KEY_RESULT_INCODE);
    return Integer.parseInt(sz);
  }
  
  public int GetResultCode(int iHandle) {
    String sz = GetProperty(iHandle, KEY_RESULT_CODE);
    return Integer.parseInt(sz);
  }
  
  public int GetResultType(int iHandle) {
    String sz = GetProperty(iHandle, KEY_RESULT_TYPE);
    return Integer.parseInt(sz);
  }
  
  public String GetResultErrString(int iHandle) {
    String sz = GetProperty(iHandle, KEY_RESULT_ERR_INFO);
    return sz;
  }
  
  public int QueryUserState(String szUser) {
    svrInit(OBJNAME_RTXSYS);
    AddProperty(this.iProp, KEY_USERNAME, szUser);
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    int iResult = Call(this.iObj, this.iProp, PRO_SYS_GETUSERSTATUS);
    int iCode = GetResultCode(iResult);
    release();
    return iCode;
  }
  
  public int GetUserInfo(String szUser) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, szUser);
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    int iResult = Call(this.iObj, this.iProp, PRO_GETUSERDETAILINFO);
    int iCode = GetResultCode(iResult);
    if (iCode == 0) {
      int iProps = GetResultPropertys(iResult);
      int iCount = GetPropertysCount(iProps);
      for (int i = 0; i < iCount; i++) {
        int iHandle = GetPropertysItem(iProps, i);
        ReleaseHandle(iHandle);
      } 
    } 
    release();
    return iCode;
  }
  
  public void getDeptInfo(String deptId) {
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_DEPTID, deptId);
    int iResult = Call(this.iObj, this.iProp, PRO_GETDEPTSMPLINFO);
    int iProps = GetResultPropertys(iResult);
    int iCount = GetPropertysCount(iProps);
    for (int i = 0; i < iCount; i++) {
      int iHandle = GetPropertysItem(iProps, i);
      ReleaseHandle(iHandle);
    } 
    release();
  }
  
  private void svrInit(String objName) {
    this.iObj = GetNewObject(objName);
    this.iProp = GetNewPropertys();
  }
  
  private void release() {
    ReleaseHandle(this.iObj);
    ReleaseHandle(this.iProp);
    ReleaseHandle(this.iResult);
  }
  
  public int saveUser(RTXUserVO rtxUser) {
    if (userIsExist(rtxUser.getNick()) != 0) {
      this.iCode = addUser(rtxUser);
    } else {
      this.iCode = updateUser(rtxUser);
    } 
    return this.iCode;
  }
  
  public int deleteUser(String nick) {
    if (nick == null || "".equals(nick))
      return -1; 
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, nick);
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    this.iResult = Call(this.iObj, this.iProp, PRO_DELUSER);
    this.iCode = GetResultInnerCode(this.iResult);
    if (this.iCode != 0)
      System.out.println("错误代码:" + this.iCode + "\t" + "错误信息："); 
    release();
    return this.iCode;
  }
  
  public int deleteUserByUIN(String UIN) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_UIN, UIN);
    this.iResult = Call(this.iObj, this.iProp, PRO_DELUSER);
    this.iCode = GetResultCode(this.iResult);
    release();
    return this.iCode;
  }
  
  public int addUser(RTXUserVO rtxUser) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_NICK, rtxUser.getNick());
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    AddProperty(this.iProp, KEY_DEPTID, rtxUser.getDept());
    AddProperty(this.iProp, KEY_NAME, rtxUser.getName());
    AddProperty(this.iProp, KEY_GENDER, rtxUser.getSex());
    if (rtxUser.getBirthday() != null && !"".equals(rtxUser.getBirthday()) && !"null".equals(rtxUser.getBirthday()))
      AddProperty(this.iProp, KEY_BIRTHDAY, rtxUser.getBirthday()); 
    AddProperty(this.iProp, KEY_PASSWORD, "888888");
    if (rtxUser.getMobile() == null || "null".equals(rtxUser.getMobile())) {
      AddProperty(this.iProp, KEY_MOBILE, "");
    } else {
      AddProperty(this.iProp, KEY_MOBILE, rtxUser.getMobile());
    } 
    this.iResult = Call(this.iObj, this.iProp, PRO_ADDUSER);
    this.iCode = GetResultCode(this.iResult);
    release();
    return this.iCode;
  }
  
  public int updateUser(RTXUserVO rtxUser) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, rtxUser.getNick());
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    this.iResult = Call(this.iObj, this.iProp, PRO_GETUSERSMPLINFO);
    this.iCode = GetResultInnerCode(this.iResult);
    if (this.iCode != 0) {
      System.out.println("错误代码:" + this.iCode + "\t" + "错误信息：" + GetResultErrString(this.iResult));
      release();
      return this.iCode;
    } 
    int iProps = GetResultPropertys(this.iResult);
    int iCount = GetPropertysCount(iProps);
    for (int i = 0; i < iCount; i++) {
      int iHandle = GetPropertysItem(iProps, i);
      if (i != 9) {
        AddProperty(this.iProp, GetPropertyItemName(iHandle), GetPropertyItemValue(iHandle));
        ReleaseHandle(iHandle);
      } 
    } 
    if (rtxUser.getDept() != null && !"".equals(rtxUser.getDept()) && !"null".equals(rtxUser.getDept()))
      AddProperty(this.iProp, KEY_DEPTID, rtxUser.getDept()); 
    if (rtxUser.getName() != null && !"".equals(rtxUser.getName()) && !"null".equals(rtxUser.getName()))
      AddProperty(this.iProp, KEY_NAME, rtxUser.getName()); 
    if (rtxUser.getSex() != null && !"".equals(rtxUser.getSex()) && !"null".equals(rtxUser.getSex()))
      AddProperty(this.iProp, KEY_GENDER, rtxUser.getSex()); 
    if (rtxUser.getBirthday() != null && !"".equals(rtxUser.getBirthday()) && !"null".equals(rtxUser.getBirthday()))
      AddProperty(this.iProp, KEY_BIRTHDAY, rtxUser.getBirthday()); 
    if (rtxUser.getMobile() != null && !"".equals(rtxUser.getMobile()) && !"null".equals(rtxUser.getMobile()))
      AddProperty(this.iProp, KEY_MOBILE, rtxUser.getMobile()); 
    if (rtxUser.getPhone() != null && !"".equals(rtxUser.getPhone()) && !"null".equals(rtxUser.getPhone()))
      AddProperty(this.iProp, KEY_PHONE, rtxUser.getPhone()); 
    if (rtxUser.getEmail() != null && !"".equals(rtxUser.getEmail()) && !"null".equals(rtxUser.getEmail()))
      AddProperty(this.iProp, KEY_EMAIL, rtxUser.getEmail()); 
    int iResult = Call(this.iObj, this.iProp, PRO_SETUSERSMPLINFO);
    int innerCode = GetResultInnerCode(iResult);
    if (innerCode != 0)
      System.out.println("错误代码:" + innerCode + "\t" + "错误信息：" + GetResultErrString(iResult)); 
    release();
    return innerCode;
  }
  
  public int saveDept(RTXDeptVO rtxDept) {
    if (rtxDept.getDeptId() == null)
      return -1; 
    int iRetCode = deptIsExist(rtxDept.getDeptId());
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_DEPTID, rtxDept.getDeptId());
    AddProperty(this.iProp, KEY_NAME, rtxDept.getDeptName());
    AddProperty(this.iProp, KEY_INFO, rtxDept.getDeptInfo());
    AddProperty(this.iProp, KEY_PDEPTID, rtxDept.getDeptParentId());
    if (iRetCode != 0) {
      this.iResult = Call(this.iObj, this.iProp, PRO_SETDEPT);
    } else {
      this.iResult = Call(this.iObj, this.iProp, PRO_ADDDEPT);
    } 
    iRetCode = GetResultCode(this.iResult);
    if (iRetCode != 0)
      System.out.println("error:" + GetResultErrString(this.iResult) + 
          " resultCode:" + iRetCode); 
    release();
    return iRetCode;
  }
  
  public int addDept(String deptId, String DetpInfo, String DeptName, String ParentDeptId) {
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_DEPTID, deptId);
    AddProperty(this.iProp, KEY_NAME, DeptName);
    AddProperty(this.iProp, KEY_INFO, DetpInfo);
    AddProperty(this.iProp, KEY_PDEPTID, ParentDeptId);
    this.iResult = Call(this.iObj, this.iProp, PRO_ADDDEPT);
    int innerCode = GetResultInnerCode(this.iResult);
    if (innerCode != 0)
      System.out.println("错误代码:" + innerCode + "\t" + "错误信息：" + GetResultErrString(this.iResult)); 
    release();
    return innerCode;
  }
  
  public int setDept(String deptId, String DetpInfo, String DeptName, String ParentDeptId) {
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_DEPTID, deptId);
    AddProperty(this.iProp, KEY_NAME, DeptName);
    AddProperty(this.iProp, KEY_INFO, DetpInfo);
    AddProperty(this.iProp, KEY_PDEPTID, ParentDeptId);
    this.iResult = Call(this.iObj, this.iProp, PRO_SETDEPT);
    int innerCode = GetResultInnerCode(this.iResult);
    if (innerCode != 0)
      System.out.println("错误代码:" + innerCode + "\t" + "错误信息：" + GetResultErrString(this.iResult)); 
    release();
    return innerCode;
  }
  
  public int deleteDept(String deptId, String type) {
    if (deptId == null || "".equals(deptId))
      return -100; 
    String[] user = getDeptUser(deptId);
    if (user != null)
      for (int i = 0; i < user.length; i++)
        deleteUserByUIN(user[i]);  
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_DEPTID, deptId);
    AddProperty(this.iProp, KEY_COMPLETEDELBS, type);
    this.iResult = Call(this.iObj, this.iProp, PRO_DELDEPT);
    this.iCode = GetResultCode(this.iResult);
    if (this.iCode != 0)
      System.out.println("错误结果:" + GetResultErrString(this.iResult)); 
    release();
    return this.iCode;
  }
  
  public int deptIsExist(String deptId) {
    if (deptId == null || "".equals(deptId))
      return 0; 
    String pdeptid = "0";
    String pdeptname = "tempdept";
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_PDEPTID, pdeptid);
    AddProperty(this.iProp, KEY_DEPTID, deptId);
    AddProperty(this.iProp, KEY_NAME, pdeptname);
    this.iResult = Call(this.iObj, this.iProp, PRO_ADDDEPT);
    int innerCode = GetResultInnerCode(this.iResult);
    if (innerCode == -5) {
      release();
      return innerCode;
    } 
    if (innerCode == 0) {
      Call(this.iObj, this.iProp, PRO_DELDEPT);
      release();
      return innerCode;
    } 
    System.out.println("错误代码:" + innerCode + "\t" + GetResultErrString(this.iResult));
    release();
    return innerCode;
  }
  
  public String[] getDeptUser(String deptId) {
    String[] user = (String[])null;
    svrInit(OBJNAME_DEPTMANAGER);
    AddProperty(this.iProp, KEY_DEPTID, deptId);
    this.iResult = Call(this.iObj, this.iProp, PRO_GETDEPTALLUSER);
    int iProps = GetResultPropertys(this.iResult);
    int iCount = GetPropertysCount(iProps);
    if (iCount > 0) {
      user = new String[iCount];
      for (int i = 0; i < iCount; i++) {
        int iHandler = GetPropertysItem(iProps, i);
        user[i] = GetPropertyItemName(iHandler);
        ReleaseHandle(iHandler);
      } 
    } 
    release();
    return user;
  }
  
  public int userIsExist(String nick) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, nick);
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    this.iResult = Call(this.iObj, this.iProp, PRO_IFEXIST);
    this.iCode = GetResultCode(this.iResult);
    int result = GetResultInt(this.iResult);
    if (this.iCode != 0)
      System.out.println("错误结果:" + GetResultErrString(this.iResult)); 
    release();
    if (this.iCode != 0)
      return this.iCode; 
    return result;
  }
  
  public int userIsOnline(String nick) {
    if (nick != null && !"null".equals(nick)) {
      svrInit(OBJNAME_RTXSYS);
      AddProperty(this.iProp, KEY_USERNAME, nick);
      AddProperty(this.iProp, KEY_UINTYPE, "Account");
      int iResult = Call(this.iObj, this.iProp, PRO_SYS_GETUSERSTATUS);
      int innerCode = GetResultInnerCode(iResult);
      int iRTXState = GetResultInt(iResult);
      if (innerCode != 0) {
        System.out.println("错误代码:" + innerCode + "\t" + "错误信息：" + GetResultErrString(iResult));
        release();
        return innerCode;
      } 
      release();
      return iRTXState;
    } 
    return 0;
  }
  
  public String nickToUIN(String nick) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, nick);
    AddProperty(this.iProp, KEY_TYPE, "0");
    this.iResult = Call(this.iObj, this.iProp, PRO_TRANUSER);
    this.iCode = GetResultCode(this.iResult);
    if (this.iCode == 0) {
      nick = GetResultString(this.iResult);
    } else {
      nick = "";
    } 
    release();
    return nick;
  }
  
  public String UINToNick(String UIN) {
    if (UIN == null || "".equals(UIN))
      return ""; 
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, UIN);
    AddProperty(this.iProp, KEY_TYPE, "1");
    this.iResult = Call(this.iObj, this.iProp, PRO_TRANUSER);
    this.iCode = GetResultCode(this.iResult);
    if (this.iCode == 0) {
      UIN = GetResultString(this.iResult);
    } else {
      UIN = "";
    } 
    release();
    return UIN;
  }
  
  public String getSessionKey(String nick) {
    if (ifUseRTX()) {
      svrInit(OBJNAME_RTXSYS);
      AddProperty(this.iProp, KEY_USERNAME, nick);
      this.iResult = Call(this.iObj, this.iProp, PRO_SYS_GETSESSIONKEY);
      this.iCode = GetResultCode(this.iResult);
      if (this.iCode == 0) {
        nick = GetResultString(this.iResult);
      } else {
        nick = "";
      } 
      release();
      return nick;
    } 
    return "";
  }
  
  public int sendNotify(String receivers, String title, String msg, String url, String type, String delayTime) {
    if (url != null && !"".equals(url) && !"null".equals(url)) {
      String[] strs = url.split("\\?");
      String[] strs1 = strs[0].split("\\.");
      String rtxMsgAction = strs1[0];
      String suffix = strs1[1];
      String newUrl = this.rStrSingleton.getRtxStr("OAServerStr");
      if (this.rStrSingleton.getRtxMsgStatus())
        msg = "[" + msg + "|" + newUrl + "rtxMsgAction=" + rtxMsgAction + "&suffix=" + suffix + "&rtxMsg=yes&" + strs[1] + "&date=" + Calendar.getInstance().getTimeInMillis() + "]"; 
    } 
    if (receivers == null || "null".equals(receivers) || "".equals(receivers))
      return -1; 
    if (title == null || "null".equals(title) || "".equals(title))
      return -1; 
    if (msg == null || "null".equals(msg) || "".equals(msg))
      return -1; 
    if (ifUseRTX()) {
      svrInit(OBJNAME_RTXEXT);
      AddProperty(this.iProp, KEY_USERNAME, receivers);
      AddProperty(this.iProp, KEY_TITLE, title);
      AddProperty(this.iProp, KEY_MSGINFO, msg);
      AddProperty(this.iProp, KEY_TYPE, type);
      AddProperty(this.iProp, KEY_MSGID, "0");
      AddProperty(this.iProp, KEY_ASSISTANTTYPE, "0");
      AddProperty(this.iProp, KEY_UINTYPE, "Account");
      if (!"0".equals(delayTime))
        AddProperty(this.iProp, "DELAYTIME", delayTime); 
      this.iResult = Call(this.iObj, this.iProp, PRO_EXT_NOTIFY);
      this.iCode = GetResultCode(this.iResult);
      release();
    } 
    return this.iCode;
  }
  
  public int SendIM(String sender, String pwd, String receivers, String msg) {
    if (ifUseRTX()) {
      svrInit(OBJNAME_RTXSYS);
      AddProperty(this.iProp, KEY_SENDER, sender);
      AddProperty(this.iProp, KEY_RECVUSERS, receivers);
      AddProperty(this.iProp, KEY_IMMSG, msg);
      AddProperty(this.iProp, "SDKPASSWORD", pwd);
      this.iResult = Call(this.iObj, this.iProp, PRO_SYS_SENDIM);
      this.iCode = GetResultCode(this.iResult);
      release();
    } 
    return this.iCode;
  }
  
  public int sendSm(String sender, String receiver, String smInfo, int autoCut) {
    if (ifUseRTX()) {
      svrInit(OBJNAME_SMSMANAGER);
      AddProperty(this.iProp, KEY_SENDER, sender);
      AddProperty(this.iProp, KEY_RECEIVER, receiver);
      AddProperty(this.iProp, KEY_SMS, smInfo);
      AddProperty(this.iProp, KEY_CUT, String.valueOf(autoCut));
      AddProperty(this.iProp, KEY_UINTYPE, "Account");
      this.iResult = Call(this.iObj, this.iProp, PRO_SMS_SEND);
      this.iCode = GetResultCode(this.iResult);
      release();
    } 
    return this.iCode;
  }
  
  public int sendSms(String mobile, String msg) {
    int rs = 0;
    if ("1".equals(SystemCommon.getRtxSmsUse())) {
      DataObjectBean bean = DataObjectFactory.getInstance();
      bean.setCordId(SystemCommon.getRtxCordId());
      bean.setUserName(SystemCommon.getRtxUserName());
      bean.setPasswd(SystemCommon.getRtxPassword());
      msmResultBean smsBean = sms_Send.SendMsg(String.valueOf(mobile) + ";", msg);
      Long result = Long.valueOf((smsBean.getErrCode()).value);
      System.out.println("result:--" + result);
      rs = 1;
    } else {
      rs = 0;
    } 
    return rs;
  }
  
  public int sendSm(String sender, String receiver, String smInfo, int autoCut, int noTitle) {
    if (ifUseRTX()) {
      svrInit(OBJNAME_SMSMANAGER);
      AddProperty(this.iProp, KEY_SENDER, sender);
      AddProperty(this.iProp, KEY_RECEIVER, receiver);
      AddProperty(this.iProp, KEY_SMS, smInfo);
      AddProperty(this.iProp, KEY_CUT, String.valueOf(autoCut));
      AddProperty(this.iProp, KEY_NOTITLE, String.valueOf(noTitle));
      this.iResult = Call(this.iObj, this.iProp, PRO_SMS_SEND);
      this.iCode = GetResultCode(this.iResult);
      release();
    } 
    return this.iCode;
  }
  
  public String getServerIP() {
    svrInit(OBJNAME_RTXSYS);
    String ip = GetServerIP(this.iObj);
    release();
    return ip;
  }
  
  public String getServerDomain() {
    if (domain == null)
      initConfig(); 
    return domain;
  }
  
  public String getServerport() {
    if (port == null)
      initConfig(); 
    return port;
  }
  
  public boolean ifUseRTX() {
    if (useRTX == null)
      initConfig(); 
    if ("0".equals(useRTX))
      return false; 
    return true;
  }
  
  public void initConfig() {
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      FileInputStream configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("RtxServer");
      domain = node.getAttributeValue("domain");
      useRTX = node.getAttributeValue("use");
      port = node.getAttributeValue("port");
    } catch (Exception ex) {
      domain = getServerIP();
      port = getServerport();
    } 
  }
  
  public int getServerPort() {
    svrInit(OBJNAME_RTXSYS);
    int port = GetServerPort(this.iObj);
    release();
    return port;
  }
  
  public String GetDeptName(String deptID) {
    svrInit(OBJNAME_DEPTMANAGER);
    String info = null;
    AddProperty(this.iProp, KEY_DEPTID, deptID);
    int iResult = Call(this.iObj, this.iProp, PRO_GETDEPTSMPLINFO);
    this.iCode = GetResultInnerCode(iResult);
    if (this.iCode == 0) {
      int iProps = GetResultPropertys(iResult);
      int iCount = GetPropertysCount(iProps);
      System.out.println("GetResultPropertys:" + iProps + "\t" + "GetPropertysCount:" + iCount);
      int iHandle = GetPropertysItem(iProps, 2);
      info = GetPropertyItemValue(iHandle);
      ReleaseHandle(iHandle);
    } 
    release();
    return info;
  }
  
  public int setUserDetailInfo(RTXUserVO rtxUser) {
    svrInit(OBJNAME_USERMANAGER);
    AddProperty(this.iProp, KEY_USERNAME, rtxUser.getNick());
    AddProperty(this.iProp, KEY_UINTYPE, "Account");
    this.iResult = Call(this.iObj, this.iProp, PRO_GETUSERDETAILINFO);
    this.iCode = GetResultInnerCode(this.iResult);
    if (this.iCode != 0) {
      System.out.println("错误代码:" + this.iCode + "\t" + "错误信息：" + GetResultErrString(this.iResult));
      release();
      return this.iCode;
    } 
    int iProps = GetResultPropertys(this.iResult);
    int iCount = GetPropertysCount(iProps);
    for (int i = 0; i < iCount; i++) {
      int iHandle = GetPropertysItem(iProps, i);
      AddProperty(this.iProp, GetPropertyItemName(iHandle), GetPropertyItemValue(iHandle));
      ReleaseHandle(iHandle);
    } 
    if (rtxUser.getAddress() != null && !"".equals(rtxUser.getAddress()) && !"null".equals(rtxUser.getAddress()))
      AddProperty(this.iProp, KEY_ADDRESS, rtxUser.getAddress()); 
    if (rtxUser.getBloodType() != null && !"".equals(rtxUser.getBloodType()) && !"null".equals(rtxUser.getBloodType()))
      AddProperty(this.iProp, KEY_BLOODTYPE, rtxUser.getBloodType()); 
    if (rtxUser.getCountry() != null && !"".equals(rtxUser.getCountry()) && !"null".equals(rtxUser.getCountry()))
      AddProperty(this.iProp, KEY_COUNTRY, rtxUser.getCountry()); 
    if (rtxUser.getProvince() != null && !"".equals(rtxUser.getProvince()) && !"null".equals(rtxUser.getProvince()))
      AddProperty(this.iProp, KEY_PROVINCE, rtxUser.getProvince()); 
    if (rtxUser.getCity() != null && !"".equals(rtxUser.getCity()) && !"null".equals(rtxUser.getCity()))
      AddProperty(this.iProp, KEY_CITY, rtxUser.getCity()); 
    if (rtxUser.getPostcode() != null && !"".equals(rtxUser.getPostcode()) && !"null".equals(rtxUser.getPostcode()))
      AddProperty(this.iProp, KEY_POSTCODE, rtxUser.getPostcode()); 
    if (rtxUser.getHomePage() != null && !"".equals(rtxUser.getHomePage()) && !"null".equals(rtxUser.getHomePage()))
      AddProperty(this.iProp, KEY_HOMEPAGE, rtxUser.getHomePage()); 
    if (rtxUser.getPhone() != null && !"".equals(rtxUser.getPhone()) && !"null".equals(rtxUser.getPhone()))
      AddProperty(this.iProp, KEY_PHONE, rtxUser.getPhone()); 
    if (rtxUser.getMobile() != null && !"".equals(rtxUser.getMobile()) && !"null".equals(rtxUser.getMobile()))
      AddProperty(this.iProp, KEY_MOBILE, rtxUser.getMobile()); 
    if (rtxUser.getMemo() != null && !"".equals(rtxUser.getMemo()) && !"null".equals(rtxUser.getMemo()))
      AddProperty(this.iProp, KEY_MEMO, rtxUser.getMemo()); 
    if (rtxUser.getPosition() != null && !"".equals(rtxUser.getPosition()) && !"null".equals(rtxUser.getPosition()))
      AddProperty(this.iProp, KEY_POSITION, rtxUser.getPosition()); 
    if (rtxUser.getFax() != null && !"".equals(rtxUser.getFax()) && !"null".equals(rtxUser.getFax()))
      AddProperty(this.iProp, KEY_FAX, rtxUser.getFax()); 
    if (rtxUser.getAge() != null && !"".equals(rtxUser.getAge()) && !"null".equals(rtxUser.getAge()))
      AddProperty(this.iProp, KEY_AGE, rtxUser.getAge()); 
    if (rtxUser.getSex() != null && !"".equals(rtxUser.getSex()) && !"null".equals(rtxUser.getSex()))
      AddProperty(this.iProp, KEY_GENDER, rtxUser.getSex()); 
    if (rtxUser.getBirthday() != null && !"".equals(rtxUser.getBirthday()) && !"null".equals(rtxUser.getBirthday()))
      AddProperty(this.iProp, KEY_BIRTHDAY, rtxUser.getBirthday()); 
    if (rtxUser.getCollage() != null && !"".equals(rtxUser.getCollage()) && !"null".equals(rtxUser.getCollage()))
      AddProperty(this.iProp, KEY_COLLAGE, rtxUser.getCollage()); 
    this.iResult = Call(this.iObj, this.iProp, PRO_SETUSERDETAILINFO);
    this.iCode = GetResultInnerCode(this.iResult);
    if (this.iCode != 0)
      System.out.println("错误代码:" + this.iCode + "\t" + "错误信息：" + GetResultErrString(this.iResult)); 
    release();
    return this.iCode;
  }
}
