package com.js.sms.corp.jiangsu;

import com.linkage.netmsg.server.AnswerBean;
import com.linkage.netmsg.server.ReceiveMsg;
import com.linkage.netmsg.server.ReturnMsgBean;
import com.linkage.netmsg.server.UpMsgBean;

public class ReceiveDemo extends ReceiveMsg {
  public void getAnswer(AnswerBean answerBean) {
    super.getAnswer(answerBean);
    String seqIdString = answerBean.getSeqId();
    int status = answerBean.getStatus();
    String msgId = answerBean.getMsgId();
  }
  
  public void getUpMsg(UpMsgBean upMsgBean) {
    super.getUpMsg(upMsgBean);
    String sequenceId = upMsgBean.getSequenceId();
    String sendNum = upMsgBean.getSendNum();
    String receiveNum = upMsgBean.getReceiveNum();
    String msgRecTime = upMsgBean.getMsgRecTime();
    String msgContent = upMsgBean.getMsgContent();
  }
  
  public void getReturnMsg(ReturnMsgBean returnMsgBean) {
    super.getReturnMsg(returnMsgBean);
    String sequenceId = returnMsgBean.getSequenceId();
    String msgId = returnMsgBean.getMsgId();
    String sendNum = returnMsgBean.getSendNum();
    String receiveNum = returnMsgBean.getReceiveNum();
    String submitTime = returnMsgBean.getSubmitTime();
    String sendTime = returnMsgBean.getSendTime();
    String msgStatus = returnMsgBean.getMsgStatus();
    int msgErrStatus = returnMsgBean.getMsgErrStatus();
  }
}
