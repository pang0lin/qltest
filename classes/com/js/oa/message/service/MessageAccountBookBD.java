package com.js.oa.message.service;

import com.js.oa.message.bean.MsAccountBookEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;

public class MessageAccountBookBD {
  public String getMeAccountInfo(String domainId) {
    String result = "";
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("MsAccountBookEJB", "MsAccountBookEJBLocal", MsAccountBookEJBHome.class);
      p.put(domainId, String.class);
      result = ejbProxy.invoke("getMeAccountInfo", p.getParameters()).toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
}
