package com.js.oa.weixin.menu;

import com.qq.weixin.mp.bean.MenuCreator;
import com.qq.weixin.mp.util.WeiXinGlobalNames;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeixinMenuCreatorAction extends Action {
  private static Logger log = Logger.getLogger(WeixinMenuCreatorAction.class);
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.debug("菜单创建尝试中。。。");
    if (MenuCreator.execute(WeiXinGlobalNames.APP_NAME_DBSX)) {
      response.setCharacterEncoding("GBK");
      PrintWriter out = response.getWriter();
      out.print("success");
      out.close();
    } 
    return null;
  }
}
