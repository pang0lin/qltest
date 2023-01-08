package com.js.oa.hr.finance.util;

import java.util.Random;

public class PubUtil {
  public String getPageJsp(String content) {
    if (content != null) {
      content = content.replace("&lt;", "<");
      content = content.replace("&gt;", ">");
    } 
    String jsp = "";
    StringBuffer sb = new StringBuffer("");
    sb.append("<%@ page contentType=\"text/html; charset=GBK\"%>\n");
    sb.append("<%@ page import=\"java.util.*\" %>\n");
    sb.append("<%");
    sb.append("response.setHeader(\"Cache-Control\",\"no-store\");\n");
    sb.append("response.setHeader(\"Pragma\",\"no-cache\");\n");
    sb.append("response.setDateHeader (\"Expires\", 0);\n");
    sb.append("Map map=(request.getAttribute(\"map\")==null)?new HashMap():(HashMap)request.getAttribute(\"map\");\n");
    sb.append("%>\n");
    sb.append("<html>\n");
    sb.append("<head>\n");
    sb.append("<title>显示表单</title>\n");
    sb.append("<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\n");
    sb.append("<script src=\"js/js.js\" language=\"javascript\"></script>\n");
    sb.append("<SCRIPT language=\"javascript\" src=\"/jsoa/js/checkText.js\"></SCRIPT>\n");
    sb.append("<link href=\"/jsoa/skin/<%=session.getAttribute( \"skin \")%>/style-<%=session.getAttribute( \"browserVersion \")%>.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
    sb.append("<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\n");
    sb.append("</head>\n");
    sb.append("<body leftmargin=\"0\" topmargin=\"0\" class=\"MainFrameBox Pupwin\">\n");
    sb.append("<form action=\"\" method=\"post\" >\n");
    sb.append("<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"docBoxNoPanel\">\n");
    sb.append("  \t<tr ><td  valign=\"top\">\n");
    sb.append("\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\n");
    sb.append("\t\t\t\t<tr><td>\n<!- html设置表单内容开始 -->\n");
    sb.append(content);
    sb.append("\n<!- html设置表单内容结束 -->\n");
    sb.append("\t\t\t\t</td></tr>\n");
    sb.append("  \t\t</table>\n");
    sb.append("\t\t\t<br/>\n");
    sb.append("\t\t\t<table width=\"100% \" border=\"0\">\n");
    sb.append("\t\t\t<tr>\n");
    sb.append("  \t\t<td>\n");
    sb.append("\t\t\t<input type=button class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=退出 />\n");
    sb.append("\t\t\t</td></tr>\n");
    sb.append("\t\t</table>\n");
    sb.append("\t</td>\n");
    sb.append("  </tr>\n");
    sb.append("</table>\n");
    sb.append("</body>\n");
    sb.append("</html>\n");
    sb.append("<script src=\"/jsoa/js/util.js\"></script>");
    jsp = String.valueOf(jsp) + sb;
    return jsp;
  }
  
  public int getRandomNumber(int n) {
    int temp = 0;
    int min = (int)Math.pow(10.0D, (n - 1));
    int max = (int)Math.pow(10.0D, n);
    Random rand = new Random();
    do {
      temp = rand.nextInt(max);
    } while (temp < min);
    return temp;
  }
}
