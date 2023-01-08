package com.js.sms.corp.jiangsu;

import com.js.sms.corp.CorpSMSAbstractFactory;
import com.js.sms.corp.ICorpSMSServies;

public class JiangSuCorpSMSFactory extends CorpSMSAbstractFactory {
  public ICorpSMSServies createCorpSMSService() {
    return new JiangSuCorpSMSServies();
  }
}
