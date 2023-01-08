package rtx.rtxsms.mobset.mms;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.mmsResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MmsFileGroup;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.util.MD5;

public class mms_UpFile {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static long smilType = 0L;
  
  private static StringHolder errMsg = null;
  
  private static LongHolder mmsFileID = null;
  
  public static mmsResultBean MmsUpFile(String subject, MmsFileGroup[] mmsFileList) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    mmsFileID = new LongHolder();
    mmsResultBean mmsBean = new mmsResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      mobset.mms_UpFile(corpID, loginName, password, timeStamp, subject, smilType, mmsFileList, mmsFileID, errMsg);
      mmsBean.setErrMsg(errMsg);
      mmsBean.setMmsFileID(mmsFileID);
      System.out.println(errMsg.value);
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return mmsBean;
  }
}
