package com.js.oa.info.infomanager.action;

import com.js.oa.info.infomanager.service.InfoRssShowBD;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoRssShowAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String[] queryStr = request.getQueryString().split("_");
    String action = "infoShow";
    InfoRssShowBD infobd = new InfoRssShowBD();
    if (queryStr[0].equals("info.xml")) {
      action = "infoXml";
      Map<String, Object> infoMap = infobd.getInfoMapXml();
      request.setAttribute("infoMap", infoMap);
    } else if (queryStr[0].equals("list")) {
      action = "infoList";
      Map<String, Object> infoMap = infobd.getInfoListMore(request.getQueryString());
      request.setAttribute("infoMap", infoMap);
      String queryString = request.getQueryString();
      if (queryString.indexOf("_") <= 0)
        queryString = String.valueOf(queryString) + "_"; 
      request.setAttribute("queryStr", queryString);
      int curPage = Integer.valueOf((String)infoMap.get("curPage")).intValue();
      int allPage = Integer.valueOf((String)infoMap.get("allPageNum")).intValue();
      request.setAttribute("showNum", infobd.getPageNum(curPage, allPage, 8));
    } else {
      Map<String, Object> info = infobd.getInfoContext(queryStr[0]);
      request.setAttribute("info", info);
    } 
    return actionMapping.findForward(action);
  }
}
