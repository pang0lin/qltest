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
import rtx.rtxsms.util.MD5;

public class mms_GetFileStatus {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static StringHolder errMsg = null;
  
  private static LongHolder status = null;
  
  private static StringHolder title = null;
  
  private static LongHolder size = null;
  
  private static StringHolder createTime = null;
  
  public static mmsResultBean GetFileStatus(long mmsFileID) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    status = new LongHolder();
    title = new StringHolder();
    size = new LongHolder();
    createTime = new StringHolder();
    mmsResultBean mmsBean = new mmsResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      mobset.mms_GetFileStatus(corpID, loginName, password, timeStamp, mmsFileID, status, errMsg, title, size, createTime);
      mmsBean.setErrMsg(errMsg);
      mmsBean.setStatus(status);
      mmsBean.setCreateTime(createTime);
      mmsBean.setSize(size);
      mmsBean.setStatus(status);
      mmsBean.setTitle(title);
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return mmsBean;
  }
}
