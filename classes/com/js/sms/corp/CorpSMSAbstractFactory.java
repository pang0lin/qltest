package com.js.sms.corp;

import com.js.sms.corp.diy09.Diy09CorpSMSFactory;
import com.js.sms.corp.jiangsu.JiangSuCorpSMSFactory;

public abstract class CorpSMSAbstractFactory {
  public static CorpSMSAbstractFactory CreateFactory() {
    CorpSMSTool.getPropertise();
    switch (CorpSMSTool.CORPMARKER) {
      case 1:
        return new Diy09CorpSMSFactory();
      case 2:
        return new JiangSuCorpSMSFactory();
    } 
    return null;
  }
  
  public abstract ICorpSMSServies createCorpSMSService();
}
