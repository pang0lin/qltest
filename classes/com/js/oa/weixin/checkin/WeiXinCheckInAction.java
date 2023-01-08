package com.js.oa.weixin.checkin;

import com.ibm.icu.text.SimpleDateFormat;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.wap.util.WapUtil;
import com.qq.weixin.mp.util.PositionUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinCheckInAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String tag = "success";
    String message = "";
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    String userAccount = session.getAttribute("userAccount")
      .toString();
    String userName = session.getAttribute("userName").toString();
    if ("checkin".equalsIgnoreCase(action)) {
      CheckInThread thread = new CheckInThread(userAccount, userName);
      thread.start();
    } else {
      if ("picsysphoto".equalsIgnoreCase(action)) {
        String picurl = (String)request.getAttribute("picUrl");
        OutsideCkeckInThread thread = new OutsideCkeckInThread(userAccount, userName, picurl);
        thread.start();
        return null;
      } 
      if ("list".equalsIgnoreCase(action)) {
        tag = "list";
        String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
        if (keyword != null && !"".equals(keyword))
          try {
            keyword = URLDecoder.decode(keyword, "utf-8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }  
        String para = " distinct bpo.checkinTime,bpo.position";
        String from = " com.js.oa.hr.kq.po.KqCheckinInfoPO bpo,com.js.system.vo.usermanager.EmployeeVO emp ";
        String where = " where 1=1 and bpo.userId=emp.userAccounts and bpo.userId='" + userAccount + "'";
        if (keyword != null && !"".equals(keyword))
          where = String.valueOf(where) + " and bpo.position like '%" + keyword + "%' "; 
        where = String.valueOf(where) + " order by bpo.checkinTime desc";
        int pageSize = WapUtil.LIMITED;
        int offset = 0;
        if (request.getParameter("beginIndex") != null)
          offset = Integer.parseInt(request.getParameter("beginIndex")); 
        int currentPage = offset / pageSize + 1;
        Page page = new Page(para, from, where);
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List list = page.getResultList();
        int recordCount = page.getRecordCount();
        String pageCount = String.valueOf(page.getPageCount());
        request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
        request.setAttribute("keyword", keyword);
        request.setAttribute("result", list);
      } else if ("outsidelist".equalsIgnoreCase(action)) {
        tag = "outsidelist";
        String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
        if (keyword != null && !"".equals(keyword))
          try {
            keyword = URLDecoder.decode(keyword, "utf-8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }  
        String para = " distinct bpo.checkinTime,bpo.position,bpo.imageurl,bpo.id";
        String from = " com.js.oa.hr.kq.po.KqOutsideCheckinInfoPO bpo,com.js.system.vo.usermanager.EmployeeVO emp ";
        String where = " where 1=1 and bpo.userId=emp.userAccounts and bpo.userId='" + userAccount + "'";
        where = String.valueOf(where) + " and bpo.position is not null ";
        if (keyword != null && !"".equals(keyword))
          where = String.valueOf(where) + " and bpo.position like '%" + keyword + "%' "; 
        where = String.valueOf(where) + " order by bpo.checkinTime desc";
        int pageSize = WapUtil.LIMITED;
        int offset = 0;
        if (request.getParameter("beginIndex") != null)
          offset = Integer.parseInt(request.getParameter("beginIndex")); 
        int currentPage = offset / pageSize + 1;
        Page page = new Page(para, from, where);
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List list = page.getResultList();
        int recordCount = page.getRecordCount();
        String pageCount = String.valueOf(page.getPageCount());
        request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
        request.setAttribute("keyword", keyword);
        request.setAttribute("result", list);
      } else if ("outsidereadinfo".equalsIgnoreCase(action)) {
        tag = "outsidereadinfo";
        String readId = request.getParameter("readId");
        request.setAttribute("readId", readId);
        if (readId != null && !"".equals(readId)) {
          String sql = "";
          String dataBaseType = 
            SystemCommon.getDatabaseType();
          if (dataBaseType.indexOf("oracle") >= 0) {
            sql = "select to_char(checkintime,'yyyy-mm-dd hh24:mi:ss'),position,imageurl,id,userid,customName,customCompany,reason,customAddress,customType,customBz from  kq_OutsideCheckininfo where userid='" + 
              userAccount + 
              "' and id=" + readId + " order by checkintime desc ";
          } else {
            sql = "select DATE_FORMAT(checkintime,'%y-%m-%d %k:%i:%s'),position,imageurl,id,userid,customName,customCompany,reason,customAddress,customType,customBz  from kq_OutsideCheckininfo where userid='" + 
              userAccount + 
              "' and id=" + readId + " order by checkintime desc ";
          } 
          String[][] resultInfo = (String[][])null;
          DbOpt db = new DbOpt();
          try {
            resultInfo = db.executeQueryToStrArr2(sql, 11);
            db.close();
          } catch (SQLException e) {
            e.printStackTrace();
          } catch (Exception e) {
            e.printStackTrace();
          } 
          request.setAttribute("result", resultInfo);
        } 
      } else if ("outsideUpdate".equals(action)) {
        tag = "outsideUpdate";
        String outsideId = request.getParameter("outsideId");
        String flag = "0";
        request.setAttribute("outsideId", outsideId);
        String dateTime = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime = df.format(new Date());
        if (outsideId != null && !"".equals(outsideId)) {
          String customName = request.getParameter("customName");
          if (customName != null && !"".equals(customName))
            try {
              customName = URLDecoder.decode(customName, "utf-8");
            } catch (UnsupportedEncodingException e1) {
              e1.printStackTrace();
            }  
          String customCompany = request.getParameter("customCompany");
          if (customCompany != null && !"".equals(customCompany))
            try {
              customCompany = URLDecoder.decode(customCompany, "utf-8");
            } catch (UnsupportedEncodingException e1) {
              e1.printStackTrace();
            }  
          String customAddress = request.getParameter("customAddress");
          if (customAddress != null && !"".equals(customAddress))
            try {
              customAddress = URLDecoder.decode(customAddress, "utf-8");
            } catch (UnsupportedEncodingException e1) {
              e1.printStackTrace();
            }  
          String reason = request.getParameter("reason");
          if (reason != null && !"".equals(reason))
            try {
              reason = URLDecoder.decode(reason, "utf-8");
            } catch (UnsupportedEncodingException e1) {
              e1.printStackTrace();
            }  
          String customType = request.getParameter("customType");
          String customBz = request.getParameter("customBz");
          if (customBz != null && !"".equals(customBz))
            try {
              customBz = URLDecoder.decode(customBz, "utf-8");
            } catch (UnsupportedEncodingException e1) {
              e1.printStackTrace();
            }  
          String dataBaseType = 
            SystemCommon.getDatabaseType();
          String sql = "update kq_OutsideCheckininfo set ";
          if (dataBaseType.indexOf("oracle") >= 0) {
            sql = String.valueOf(sql) + " customName='" + customName + "'";
            sql = String.valueOf(sql) + " ,customCompany='" + customCompany + "'";
            sql = String.valueOf(sql) + " ,customAddress='" + customAddress + "'";
            sql = String.valueOf(sql) + " ,customType='" + customType + "'";
            sql = String.valueOf(sql) + " ,reason='" + reason + "'";
            sql = String.valueOf(sql) + " ,customBz='" + customBz + "'";
            sql = String.valueOf(sql) + " ,rechecktime=JSDB.FN_STRTODATE('" + dateTime + "','') ";
            sql = String.valueOf(sql) + " ,ismark='1'";
            sql = String.valueOf(sql) + " where id=" + outsideId;
          } else {
            sql = String.valueOf(sql) + " customName='" + customName + "'";
            sql = String.valueOf(sql) + " ,customCompany='" + customCompany + "'";
            sql = String.valueOf(sql) + " ,customAddress='" + customAddress + "'";
            sql = String.valueOf(sql) + " ,customType='" + customType + "'";
            sql = String.valueOf(sql) + " ,reason='" + reason + "'";
            sql = String.valueOf(sql) + " ,customBz='" + customBz + "'";
            sql = String.valueOf(sql) + " ,rechecktime='" + dateTime + "'";
            sql = String.valueOf(sql) + " ,ismark='1'";
            sql = String.valueOf(sql) + " where id=" + outsideId;
          } 
          DbOpt db = new DbOpt();
          try {
            db.executeUpdate(sql);
            db.executeUpdate("delete from kq_OutsideCheckininfo where userid='" + userAccount + "' and weidu='' and jingdu='' ");
            flag = "1";
            db.close();
          } catch (SQLException e) {
            flag = "0";
            e.printStackTrace();
          } catch (Exception e) {
            flag = "0";
            e.printStackTrace();
          } 
        } 
        request.setAttribute("flag", flag);
      } else if ("reCheck".equals(action)) {
        tag = "reCheck";
        String latitude = request.getParameter("latitudeid");
        String longitude = request.getParameter("longitudeid");
        String positionString = PositionUtil.getPosition(latitude, longitude);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String checktime = dateTimeFormat.format(new Date());
        System.out.println("latitude:" + latitude);
        System.out.println("longitude:" + longitude);
        System.out.println("positionString:" + positionString);
        request.setAttribute("checktime", checktime);
        request.setAttribute("POSITION", positionString);
      } 
    } 
    if (!"".equals(message))
      System.out.println(message); 
    return actionMapping.findForward(tag);
  }
}
