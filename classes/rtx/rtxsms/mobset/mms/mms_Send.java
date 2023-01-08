package rtx.rtxsms.mobset.mms;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.mmsResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.tempuri.MobileListGroup;
import rtx.rtxsms.tempuri.MobsetApiSoap;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsIDListHolder;
import rtx.rtxsms.util.MD5;
import rtx.rtxsms.util.StringUtils;

public class mms_Send {
  private static long corpID;
  
  private static String loginName;
  
  private static String password;
  
  private static String timeStamp = "0514094912";
  
  private static String addNum;
  
  private static String timer = "";
  
  private static MobileListGroup[] mobileList;
  
  private static StringHolder errMsg = null;
  
  private static ArrayOfMmsIDListHolder mmsIDList = null;
  
  private static LongHolder count = null;
  
  public static mmsResultBean SendMsg(String mobiles, long mmsFileID) {
    MobsetApiSoap mobset = DataObjectFactory.getMobsetApi();
    Date aDate = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
    timeStamp = formatter.format(aDate);
    errMsg = new StringHolder();
    mmsIDList = new ArrayOfMmsIDListHolder();
    count = new LongHolder();
    mmsResultBean mmsBean = new mmsResultBean();
    DataObjectBean bean = DataObjectFactory.getInstance();
    corpID = (new Long(bean.getCordId())).longValue();
    loginName = bean.getUserName();
    password = bean.getPasswd();
    String[] mobileArray = StringUtils.replace(mobiles, "；", ";").split(";");
    mobileList = new MobileListGroup[mobileArray.length];
    for (int i = 0; i < mobileList.length; i++) {
      mobileList[i] = new MobileListGroup();
      mobileList[i].setMobile(mobileArray[i]);
    } 
    MD5 md5 = new MD5();
    password = md5.getMD5ofStr(String.valueOf(corpID) + password + timeStamp);
    try {
      System.out.println(mobset);
      mobset.mms_Send(corpID, loginName, password, timeStamp, addNum, timer, mobileList, mmsFileID, count, errMsg, mmsIDList);
      mmsBean.setErrMsg(errMsg);
      mmsBean.setMmsIDList(mmsIDList);
      System.out.println(errMsg.value);
    } catch (RemoteException e) {
      e.printStackTrace();
    } 
    return mmsBean;
  }
}
