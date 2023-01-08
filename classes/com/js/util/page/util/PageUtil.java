package com.js.util.page.util;

import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class PageUtil {
  protected int allPageNum = 0;
  
  protected int allItem = 0;
  
  protected int curPageNum = 0;
  
  protected int everyNum = 15;
  
  protected List<Object> list = null;
  
  protected Integer[] pageNum;
  
  protected String pageUrl = "";
  
  protected void getData(String select, String from, String where, String orderBy, int curPage) {}
  
  public List<Object> list(HttpServletRequest request, String select, String from, String where, String orderBy) {
    List<Object> dataList = null;
    try {
      int curPage = (request.getParameter("pager.offset") == null || "".equals(request.getParameter("pager.offset")) || 
        "null".equalsIgnoreCase(request.getParameter("pager.offset"))) ? 1 : 
        Integer.valueOf(request.getParameter("pager.offset")).intValue();
      getData(select, from, where, orderBy, curPage);
      String doUrl = String.valueOf(request.getRequestURI()) + "?1=1";
      String url = "";
      Enumeration<String> pNames = request.getParameterNames();
      while (pNames.hasMoreElements()) {
        String name = pNames.nextElement();
        if (!name.equals("pager.offset")) {
          String value = (request.getParameter(name) == null) ? "" : request.getParameter(name);
          url = String.valueOf(url) + "&" + name + "=" + value;
        } 
      } 
      setPageUrl((String.valueOf(doUrl) + url).replace("&1=1", "").replace("?1=1&", "?").replace("请点击选择", ""));
      request.setAttribute("paramUrl", url.replace("&1=1", ""));
      dataList = getList();
      setList(null);
      request.setAttribute("pageUtil", this);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return dataList;
  }
  
  protected void getPageDate() {
    this.allPageNum = (this.allItem % this.everyNum == 0) ? (this.allItem / this.everyNum) : (this.allItem / this.everyNum + 1);
    this.pageNum = getPageNum(this.curPageNum, this.allPageNum, 10);
  }
  
  protected Integer[] getPageNum(int curPage, int allPage, int showNum) {
    Integer[] showInt = new Integer[showNum];
    if (allPage <= showNum) {
      showInt = new Integer[allPage];
      for (int i = 1; i <= allPage; i++)
        showInt[i - 1] = Integer.valueOf(i); 
    } else {
      showInt = new Integer[showNum];
      int halfNum = showNum / 2;
      for (int i = 1; i <= showNum; i++) {
        if (curPage <= halfNum) {
          showInt[i - 1] = Integer.valueOf(i);
        } else if (curPage >= allPage - halfNum) {
          showInt[i - 1] = Integer.valueOf(allPage - showNum + i);
        } else {
          showInt[i - 1] = Integer.valueOf(curPage - halfNum + i);
        } 
      } 
    } 
    return showInt;
  }
  
  public String getPageUrl() {
    return this.pageUrl;
  }
  
  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }
  
  public int getAllPageNum() {
    return this.allPageNum;
  }
  
  public void setAllPageNum(int allPageNum) {
    this.allPageNum = allPageNum;
  }
  
  public int getAllItem() {
    return this.allItem;
  }
  
  public void setAllItem(int allItem) {
    this.allItem = allItem;
  }
  
  public int getCurPageNum() {
    return this.curPageNum;
  }
  
  public void setCurPageNum(int curPageNum) {
    this.curPageNum = curPageNum;
  }
  
  public int getEveryNum() {
    return this.everyNum;
  }
  
  public void setEveryNum(int everyNum) {
    this.everyNum = everyNum;
  }
  
  public List<Object> getList() {
    return this.list;
  }
  
  public void setList(List<Object> list) {
    this.list = list;
  }
  
  public Integer[] getPageNum() {
    return this.pageNum;
  }
  
  public void setPageNum(Integer[] pageNum) {
    this.pageNum = pageNum;
  }
}
