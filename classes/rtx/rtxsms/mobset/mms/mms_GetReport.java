package rtx.rtxsms.mobset.mms;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.mmsResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsReportListHolder;
import rtx.rtxsms.util.MD5;

public class mms_GetReport {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static StringHolder errMsg = null;
  
  private static LongHolder count = null;
  
  private static ArrayOfMmsReportListHolder mmsReportList = null;
  
  public static mmsResultBean getReport(String mmsIDs) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    count = new LongHolder();
    mmsReportList = new ArrayOfMmsReportListHolder();
    mmsResultBean mmsBean = new mmsResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      System.out.println(mmsReportList.value);
      mobset.mms_GetReport(corpID, loginName, password, timeStamp, count, errMsg, mmsReportList);
      mmsBean.setErrMsg(errMsg);
      if (mmsReportList != null)
        mmsBean.setMmsReportList(mmsReportList); 
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return mmsBean;
  }
}
