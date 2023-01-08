package com.js.oa.info.infomanager.action;

import com.js.oa.info.templatemanager.service.TemplateBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoIframeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InfoIframeActionForm infoIframeActionForm = (InfoIframeActionForm)actionForm;
    String tag = "iframe";
    String templateId = httpServletRequest.getParameter("templateId");
    TemplateBD templateBD = new TemplateBD();
    infoIframeActionForm.setTemplateContent(templateBD.getTemplateContent(templateId));
    return actionMapping.findForward(tag);
  }
}
