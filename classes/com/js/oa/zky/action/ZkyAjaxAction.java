package com.js.oa.zky.action;

import com.js.oa.zky.service.ZkyAjaxBD;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ZkyAjaxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    if (request.getParameter("flag") == null) {
      int num = Integer.valueOf(request.getParameter("num")).intValue();
      Map<String, String[]> map = (Map)new HashMap<String, String>();
      try {
        Enumeration<String> pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
          String name = pNames.nextElement();
          if ("fields".equals(name) || "pageId".equals(name) || "recordId".equals(name) || "num".equals(name)) {
            String[] values = new String[num];
            for (int j = 0; j < num; j++)
              values[j] = (request.getParameter(name) == null) ? "" : request.getParameter(name); 
            map.put(name, values);
            continue;
          } 
          String value = URLDecoder.decode((request.getParameter(name) == null) ? "" : request.getParameter(name), "utf-8");
          if ("".equals(value)) {
            map.put(name, new String[num]);
            continue;
          } 
          String[] xStrings = value.split("\\[@\\]");
          map.put(name, xStrings);
        } 
        String writeStr = ",";
        ZkyAjaxBD bd = new ZkyAjaxBD();
        for (int i = 0; i < num; i++) {
          Map<String, String> jmap = new HashMap<String, String>();
          for (String mapKey : map.keySet()) {
            String[] mapValue = map.get(mapKey);
            jmap.put(mapKey, mapValue[i]);
          } 
          writeStr = String.valueOf(writeStr) + bd.recordIsExist(jmap) + ",";
        } 
        response.getWriter().write(writeStr);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if ("number".equals(request.getParameter("flag"))) {
      String empId = request.getParameter("empId");
      ZkyAjaxBD bd = new ZkyAjaxBD();
      try {
        response.getWriter().write(bd.getNumber(empId));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } else if ("hidden".equals(request.getParameter("flag"))) {
      String empId = request.getParameter("u");
      String tableName = request.getParameter("t");
      ZkyAjaxBD bd = new ZkyAjaxBD();
      try {
        response.getWriter().write(bd.getHidden(empId, tableName));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return null;
  }
}
