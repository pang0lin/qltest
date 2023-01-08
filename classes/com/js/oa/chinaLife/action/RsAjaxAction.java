package com.js.oa.chinaLife.action;

import com.js.oa.chinaLife.bean.KqBean;
import com.js.oa.chinaLife.bean.QingJiaBean;
import com.js.oa.chinaLife.bean.ReYuanBean;
import com.js.oa.chinaLife.util.IsHoliday;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RsAjaxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String result = "";
    String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    response.setHeader("Content-Type", "text/html; charset=GBK");
    try {
      if ("isHoliday".equals(flag)) {
        Date date = new Date(Long.valueOf(request.getParameter("dateStr")).longValue());
        String dateStr = ymd.format(date);
        result = IsHoliday.isHoliday(dateStr);
      } else if ("reYuan".equals(flag)) {
        String userId = request.getParameter("userId");
        result = (new ReYuanBean()).getReYuan(userId);
      } else if ("qingjia".equals(flag)) {
        String userId = request.getParameter("userId");
        result = (new QingJiaBean()).getNianjia(userId, (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString());
      } else if ("reYuanErWei".equals(flag)) {
        String userId = request.getParameter("userId");
        String tableName = request.getParameter("tableName");
        result = (new ReYuanBean()).erWeiBiao(userId, tableName);
      } else if ("kaoqin".equals(flag)) {
        String userId = request.getParameter("userId");
        result = (new KqBean()).getKqOnload(userId, (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString());
      } else if ("danhao".equals(flag)) {
        String recordId = request.getParameter("recordId");
        result = (new QingJiaBean()).getDanhao(recordId);
      } else if ("buqian".equals(flag)) {
        String userId = request.getParameter("u");
        String beginDate = request.getParameter("b");
        String endDate = request.getParameter("e");
        result = (new QingJiaBean()).buqianYanzheng(userId, beginDate, endDate);
      } 
      response.getWriter().write(result);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
}
