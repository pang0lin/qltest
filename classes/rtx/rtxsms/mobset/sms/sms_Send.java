package rtx.rtxsms.mobset.sms;

import com.js.util.config.SystemCommon;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.msmResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MobileListGroup;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsIDListHolder;
import rtx.rtxsms.util.MD5;
import rtx.rtxsms.util.StringUtils;

public class sms_Send {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514170214";
  
  private static String addNum = "";
  
  private static String timer = "2012-04-1 17:10:00";
  
  private static long longSms = 1L;
  
  private static MobileListGroup[] mobileList;
  
  private static StringHolder errMsg = null;
  
  private static ArrayOfSmsIDListHolder smsIDList = null;
  
  private static LongHolder count = null;
  
  public static msmResultBean SendMsg(String mobiles, String content) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    smsIDList = new ArrayOfSmsIDListHolder();
    count = new LongHolder();
    msmResultBean msmBean = new msmResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(SystemCommon.getRtxCordId())).longValue();
    loginName = SystemCommon.getRtxUserName();
    password = SystemCommon.getRtxPassword();
    String[] mobileArray = StringUtils.replace(mobiles, "；", ";").split(";");
    mobileList = new MobileListGroup[mobileArray.length];
    for (int i = 0; i < mobileList.length; i++) {
      mobileList[i] = new MobileListGroup();
      mobileList[i].setMobile(mobileArray[i]);
    } 
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      mobset.sms_Send(corpID, loginName, password, timeStamp, addNum, timer, longSms, mobileList, content, count, errMsg, smsIDList);
      msmBean.setErrMsg(errMsg);
      msmBean.setErrCode(count);
      msmBean.setMobileList(mobileList);
      msmBean.setSmsIDList(smsIDList);
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return msmBean;
  }
}
