package rtx.rtxsms.mobset.task;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.taskResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MobileFileGroup;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.util.MD5;

public class task_MmsSend {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static String atTime = "2010-05-14 10:30:00";
  
  private static long priority = 0L;
  
  private static StringHolder errMsg = null;
  
  private static LongHolder taskMmsID = null;
  
  public static taskResultBean taskSmsSend(MobileFileGroup[] mobileList, long mmsFileID) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    taskMmsID = new LongHolder();
    taskResultBean taskBean = new taskResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      mobset.task_MmsSend(corpID, loginName, password, timeStamp, mmsFileID, priority, atTime, mobileList, taskMmsID, errMsg);
      taskBean.setErrMsg(errMsg);
      taskBean.setTaskSmsID(taskMmsID);
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return taskBean;
  }
}
