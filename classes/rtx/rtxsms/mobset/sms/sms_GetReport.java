package rtx.rtxsms.mobset.sms;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.msmResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsReportListHolder;
import rtx.rtxsms.util.MD5;

public class sms_GetReport {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static StringHolder errMsg = null;
  
  private static ArrayOfSmsReportListHolder smsReportList = null;
  
  private static LongHolder count = null;
  
  public static msmResultBean getReport(String reportList) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    count = new LongHolder();
    msmResultBean msmBean = new msmResultBean();
    smsReportList = new ArrayOfSmsReportListHolder();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      mobset.sms_GetReport(corpID, loginName, password, timeStamp, count, errMsg, smsReportList);
      msmBean.setErrMsg(errMsg);
      if (smsReportList != null)
        msmBean.setSmsReportList(smsReportList); 
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return msmBean;
  }
}
