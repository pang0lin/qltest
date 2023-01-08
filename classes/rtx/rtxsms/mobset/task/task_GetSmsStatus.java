package rtx.rtxsms.mobset.task;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.taskResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.util.MD5;

public class task_GetSmsStatus {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static StringHolder errMsg = null;
  
  private static LongHolder status = null;
  
  private static LongHolder mobileCount = null;
  
  private static LongHolder YFMobileCount = null;
  
  private static StringHolder beginTime = null;
  
  private static StringHolder endTime = null;
  
  public static taskResultBean taskGetSmsStatus(long taskSmsID) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    status = new LongHolder();
    mobileCount = new LongHolder();
    YFMobileCount = new LongHolder();
    beginTime = new StringHolder();
    endTime = new StringHolder();
    taskResultBean taskBean = new taskResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      mobset.task_GetSmsStatus(corpID, loginName, password, timeStamp, taskSmsID, status, errMsg, mobileCount, YFMobileCount, beginTime, endTime);
      taskBean.setErrMsg(errMsg);
      taskBean.setStatus(status);
      taskBean.setMobileCount(mobileCount);
      taskBean.setYFMobileCount(YFMobileCount);
      taskBean.setBeginTime(beginTime);
      taskBean.setEndTime(endTime);
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return taskBean;
  }
}
