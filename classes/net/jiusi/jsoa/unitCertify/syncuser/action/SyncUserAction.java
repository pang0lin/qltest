package net.jiusi.jsoa.unitCertify.syncuser.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.jiusi.jsoa.unitCertify.syncuser.service.SyncUserService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SyncUserAction extends DispatchAction {
  public void syncUserByHand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/xml; charset=GBK");
    PrintWriter out = response.getWriter();
    SyncUserService syncUserService = new SyncUserService();
    try {
      String orgName = URLDecoder.decode(request.getParameter("orgName"), "UTF-8");
      String re = syncUserService.syncUserByHand(orgName);
      out.print(re);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.flush();
      out.close();
    } 
  }
}
