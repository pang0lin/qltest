package com.js.system.service.sysclientemail;

import com.js.system.bean.sysclientemail.SysClientEmailEJBBean;
import com.js.system.vo.sysclientemail.SysClientEmailVO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class SysClientEmailBD {
  private static Logger logger = Logger.getLogger(SysClientEmailBD.class.getName());
  
  SysClientEmailEJBBean sysClientEmailEJBBean = new SysClientEmailEJBBean();
  
  public List<String> searchEmailUrl() throws Exception {
    List<String> list = new ArrayList<String>();
    list = this.sysClientEmailEJBBean.searchEmailUrl();
    return list;
  }
  
  public void add(SysClientEmailVO sysClientEmailVO) throws Exception {
    this.sysClientEmailEJBBean.add(sysClientEmailVO);
  }
}
