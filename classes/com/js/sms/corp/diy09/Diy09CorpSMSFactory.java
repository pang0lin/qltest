package com.js.sms.corp.diy09;

import com.js.sms.corp.CorpSMSAbstractFactory;
import com.js.sms.corp.ICorpSMSServies;

public class Diy09CorpSMSFactory extends CorpSMSAbstractFactory {
  public ICorpSMSServies createCorpSMSService() {
    return new Diy09CorpSMSServies();
  }
}
