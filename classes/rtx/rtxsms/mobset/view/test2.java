package rtx.rtxsms.mobset.view;

import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.bean.msmResultBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;
import rtx.rtxsms.mobset.sms.sms_Send;
import rtx.rtxsms.tempuri.SmsIDGroup;

public class test2 {
  public static void main(String[] args) {
    DataObjectBean bean = DataObjectFactory.getInstance();
    bean.setCordId("122859");
    bean.setUserName("72740685");
    bean.setPasswd("141171");
    msmResultBean smsBean = sms_Send.SendMsg("139;138101183176;", "你好测试");
    SmsIDGroup[] smsIDGroup = (smsBean.getSmsIDList()).value;
    String msgId = "";
    if (smsIDGroup != null)
      for (int i = 0; i < smsIDGroup.length; i++) {
        msgId = String.valueOf(msgId) + Long.valueOf(smsIDGroup[i].getSmsID()).toString();
        msgId = String.valueOf(msgId) + ";";
      }  
    System.out.println(msgId);
    System.out.println((smsBean.getErrMsg()).value);
  }
}
