package com.js.oa.jsflow.util;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.TFieldVO;
import com.js.system.util.SysSetupReader;
import com.js.util.util.BASE64;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ParseFormWithValue {
  public String parseForm(String tableId, String recordId, String writeFieldString, boolean writeScope, String fileServer, HttpServletRequest httpServletRequest, String pageId) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    StringBuffer formContent = new StringBuffer();
    System.out.println("ParseFormWithValue.parseForm start");
    String fieldName = "";
    int fieldId = 0;
    String fieldDesName = "";
    int fieldShow = 0;
    String fieldValue = "";
    int fieldNull = 0;
    String fieldType = "";
    int fieldLen = 0;
    String userValue = "";
    StringBuffer curFieldStr = new StringBuffer();
    List<String[]> fieldList = workFlowBD.getFieldAndValue(tableId, recordId, pageId);
    String[] tmp = (String[])null;
    for (int i = 0; fieldList != null && i < fieldList.size(); i++) {
      tmp = fieldList.get(i);
      fieldName = tmp[0];
      fieldId = Integer.parseInt(tmp[1]);
      fieldDesName = tmp[2];
      fieldShow = Integer.parseInt(tmp[3]);
      fieldValue = tmp[4];
      userValue = tmp[5];
      if (userValue == null || userValue.toUpperCase().equals("NULL"))
        userValue = ""; 
      curFieldStr.append(String.valueOf(fieldId) + ",");
      fieldNull = Integer.parseInt(tmp[6]);
      fieldType = tmp[7].toUpperCase();
      fieldLen = Integer.parseInt(tmp[8]);
      formContent.append("<tr id=\"tr_" + fieldId + "\" ");
      if (fieldShow == 201 || fieldShow == 204 || fieldShow == 208 || fieldShow == 209)
        formContent.append("style=\"display:none\""); 
      formContent.append(">");
      formContent.append("<td width=\"15%\" height=\"22\">");
      formContent.append(String.valueOf(fieldDesName) + ":");
      formContent.append("</td>");
      formContent.append("<td width=\"85%\">");
      if (fieldDesName.equals("WORD编辑") && SysSetupReader.getInstance().hasHandSign(httpServletRequest.getSession(true).getAttribute("domainId").toString())) {
        formContent.append("<span style=cursor:hand onclick=\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + fieldName + ".value+'&EditType=1&UserName=" + httpServletRequest.getSession(true).getAttribute("userName") + "&ShowSign=0&CanSave=1&fieldName=" + fieldName + "', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0');\"><font color=blue>WORD编辑</font></span>");
        formContent.append("<input type=hidden name=" + fieldName + " value=" + userValue + ">");
        formContent.append("<input type=hidden name=fieldName value=" + fieldName + "_text>");
        formContent.append("</td>");
        formContent.append("</tr>");
      } else {
        StringBuffer sb;
        String dataStr;
        List<String[]> tabList;
        int j;
        BASE64 base64 = null;
        String myHtmlValue, tmp2[];
        boolean canModify;
        formContent.append("<input type=hidden name=fieldType value=" + fieldType + ">");
        switch (fieldShow) {
          case 101:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              if (fieldType.equals("INT") || fieldType.equals("NUMERIC")) {
                formContent.append("<input type=\"text\" name=\"" + fieldName + "\" size=\"60\" class=\"inputText\" value=\"" + userValue + "\" onfocus=\"changeText()\" onblur=\"checkNum(this)\">");
              } else {
                formContent.append("<input type=\"text\" name=\"" + fieldName + "\" size=\"60\" class=\"inputText\" value=\"" + userValue + "\" ");
                if (!fieldType.equals("LONG") && !fieldType.equals("CLOB") && !fieldType.equals("TEXT"))
                  formContent.append("maxlength=\"" + (fieldLen / 2) + "\" "); 
                formContent.append(" onfocus=\"changeText()\">");
              } 
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 102:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<input type=\"password\" name=\"" + fieldName + "\" value=\"" + userValue + "\" size=\"60\" class=\"inputText\" maxlength=\"" + (fieldLen / 2) + "\" onfocus=\"changeText()\">");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 103:
            sb = new StringBuffer();
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              if (!fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
                if (fieldValue.startsWith("@")) {
                  List<Object[]> list = getIdValue(fieldValue);
                  Object[] obj = (Object[])null;
                  for (int k = 0; k < list.size(); k++) {
                    obj = list.get(k);
                    formContent.append("<input type=\"radio\" name=\"" + fieldName + "\" value=\"" + obj[0] + "\" ");
                    if (userValue.equals(obj[0]))
                      formContent.append("checked"); 
                    formContent.append(" onclick=\"clickRadio(this)\">" + obj[1] + "&nbsp;");
                    sb.append(obj[0] + "/" + obj[1] + ";");
                  } 
                  formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + sb.toString() + "\">");
                } else {
                  String[] radioTmp = fieldValue.split(";");
                  for (int k = 0; k < radioTmp.length; k++) {
                    String checkValue = radioTmp[k].substring(0, radioTmp[k].indexOf("/"));
                    String checkText = radioTmp[k].substring(radioTmp[k].indexOf("/") + 1, radioTmp[k].length());
                    formContent.append("<input type=\"radio\" name=\"" + fieldName + "\" value=\"" + checkValue + "\" ");
                    if (checkValue.equals(userValue))
                      formContent.append("checked"); 
                    formContent.append(" onclick=\"clickRadio(this)\">" + checkText + "&nbsp;");
                  } 
                  formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + fieldValue + "\">");
                } 
                if (fieldNull == 1)
                  formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
              } 
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              break;
            } 
            if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL"))
              if (fieldValue.startsWith("@")) {
                List list = getIdValue(fieldValue);
                Object[] obj = (Object[])null;
                for (int k = 0; k < list.size(); k++) {
                  if (userValue.equals(obj[0]))
                    formContent.append(obj[1]); 
                  sb.append(obj[0] + "/" + obj[1] + ";");
                } 
                formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + sb.toString() + "\">");
              } else {
                String[] radioTmp = fieldValue.split(";");
                for (int k = 0; k < radioTmp.length; k++) {
                  String checkValue = radioTmp[k].substring(0, radioTmp[k].indexOf("/"));
                  String checkText = radioTmp[k].substring(radioTmp[k].indexOf("/") + 1, radioTmp[k].length());
                  if (checkValue.equals(userValue)) {
                    formContent.append(checkText);
                    break;
                  } 
                } 
                formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + fieldValue + "\">");
              }  
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 104:
            sb = new StringBuffer();
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
                if (fieldValue.startsWith("@")) {
                  List<Object[]> list = getIdValue(fieldValue);
                  Object[] obj = (Object[])null;
                  for (int k = 0; k < list.size(); k++) {
                    obj = list.get(k);
                    formContent.append("<input type=\"checkbox\" name=\"" + fieldName + "\" value=\"" + obj[0] + "\" ");
                    if (("," + userValue + ",").indexOf("," + obj[0] + ",") >= 0)
                      formContent.append("checked"); 
                    formContent.append(" onclick=\"clickCheckbox()\">" + obj[1] + "&nbsp;");
                    sb.append(obj[0] + "/" + obj[1] + ";");
                  } 
                  formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldName + "\" value=\"" + sb.toString() + "\">");
                } else {
                  String[] checkTmp = fieldValue.split(";");
                  for (int k = 0; k < checkTmp.length; k++) {
                    String checkValue = checkTmp[k].substring(0, checkTmp[k].indexOf("/"));
                    String checkText = checkTmp[k].substring(checkTmp[k].indexOf("/") + 1, checkTmp[k].length());
                    formContent.append("<input type=\"checkbox\" name=\"" + fieldName + "\" value=\"" + checkValue + "\" ");
                    if (("," + userValue + ",").indexOf("," + checkValue + ",") >= 0)
                      formContent.append("checked"); 
                    formContent.append(" onclick=\"clickCheckbox()\">" + checkText + "&nbsp;");
                  } 
                  formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldName + "\" value=\"" + fieldValue + "\">");
                } 
                if (fieldNull == 1) {
                  formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                  formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
                } 
              } 
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_checkbox\">");
              break;
            } 
            if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL"))
              if (fieldValue.startsWith("@")) {
                List<Object[]> list = getIdValue(fieldValue);
                Object[] obj = (Object[])null;
                for (int k = 0; k < list.size(); k++) {
                  obj = list.get(k);
                  if (("," + userValue + ",").indexOf("," + obj[0] + ",") >= 0)
                    formContent.append(obj[1] + "&nbsp;"); 
                  sb.append(obj[0] + "/" + obj[1] + ";");
                } 
                formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldName + "\" value=\"" + sb.toString() + "\">");
              } else {
                String[] checkTmp = fieldValue.split(";");
                for (int k = 0; k < checkTmp.length; k++) {
                  String checkValue = checkTmp[k].substring(0, checkTmp[k].indexOf("/"));
                  String checkText = checkTmp[k].substring(checkTmp[k].indexOf("/") + 1, checkTmp[k].length());
                  if (("," + userValue + ",").indexOf("," + checkValue + ",") >= 0)
                    formContent.append(String.valueOf(checkText) + "&nbsp;"); 
                } 
                formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldName + "\" value=\"" + fieldValue + "\">");
              }  
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 105:
            sb = new StringBuffer();
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
                formContent.append("<select name=\"" + fieldName + "\" onchange=\"changeSelect()\">");
                if (fieldValue.startsWith("@")) {
                  List<Object[]> list = getIdValue(fieldValue);
                  Object[] obj = (Object[])null;
                  for (int k = 0; k < list.size(); k++) {
                    obj = list.get(k);
                    formContent.append("<option value=\"" + obj[0] + "\" ");
                    if (userValue.equals(obj[0]))
                      formContent.append("selected"); 
                    formContent.append(">" + obj[1] + "</option>");
                    sb.append(obj[0] + "/" + obj[1] + ";");
                  } 
                } else {
                  String[] selectTmp = fieldValue.split(";");
                  for (int k = 0; k < selectTmp.length; k++) {
                    String checkValue = selectTmp[k].substring(0, selectTmp[k].indexOf("/"));
                    String checkText = selectTmp[k].substring(selectTmp[k].indexOf("/") + 1, selectTmp[k].length());
                    formContent.append("<option value=\"" + checkValue + "\" ");
                    if (checkValue.equals(userValue))
                      formContent.append("selected"); 
                    formContent.append(">" + checkText + "</option>");
                  } 
                } 
                formContent.append("</select>");
                if (sb.length() == 0) {
                  formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + fieldValue + "\">");
                } else {
                  formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + sb.toString() + "\">");
                } 
                if (fieldNull == 1)
                  formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
                formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              } 
              break;
            } 
            if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
              if (fieldValue.startsWith("@")) {
                List<Object[]> list = getIdValue(fieldValue);
                Object[] obj = (Object[])null;
                for (int k = 0; k < list.size(); k++) {
                  obj = list.get(k);
                  if (userValue.equals(obj[0]))
                    formContent.append(obj[0]); 
                  sb.append(obj[0] + "/" + obj[1] + ";");
                } 
              } else {
                String[] selectTmp = fieldValue.split(";");
                for (int k = 0; k < selectTmp.length; k++) {
                  String checkValue = selectTmp[k].substring(0, selectTmp[k].indexOf("/"));
                  String checkText = selectTmp[k].substring(selectTmp[k].indexOf("/") + 1, selectTmp[k].length());
                  if (checkValue.equals(userValue)) {
                    formContent.append(checkText);
                    break;
                  } 
                } 
              } 
              if (sb.length() == 0) {
                formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + fieldValue + "\">");
              } else {
                formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + sb.toString() + "\">");
              } 
            } 
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 106:
            dataStr = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
            tabList = workFlowBD.getIdValue(dataStr.substring(0, dataStr.indexOf(".")), dataStr.substring(dataStr.indexOf(".") + 1, dataStr.length()));
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<select name=\"" + fieldName + "\" onchange=\"changeSelect()\">");
              for (int k = 0; k < tabList.size(); k++) {
                String[] str2 = tabList.get(k);
                formContent.append("<option value=\"" + str2[0] + "\" ");
                if (userValue.equals(str2[0]))
                  formContent.append("selected"); 
                formContent.append(">" + str2[1] + "</option>");
              } 
              formContent.append("</select>");
              if (fieldNull == 1)
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              break;
            } 
            for (j = 0; j < tabList.size(); j++) {
              String[] str2 = tabList.get(j);
              if (userValue.equals(str2[0]))
                formContent.append(str2[1]); 
            } 
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 107:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              String[] dateTmp = userValue.split("/");
              formContent.append("<script language=\"javascript\">");
              formContent.append("var dptDate = createDatePicker(\"" + fieldName + "\",\"" + dateTmp[0] + "\",\"" + dateTmp[1] + "\",\"" + dateTmp[2] + "\");");
              formContent.append("</script>");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              if (fieldNull == 1)
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 108:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              String hour = userValue.substring(0, userValue.indexOf(":"));
              String minute = userValue.substring(userValue.indexOf(":") + 1, userValue.length());
              formContent.append("<select name=\"" + fieldName + "_hour\" onchange=\"changeSelect()\">");
              int k;
              for (k = 0; k < 24; k++) {
                formContent.append("<option value=\"" + k + "\" ");
                if (hour.equals((new StringBuilder(String.valueOf(k))).toString()))
                  formContent.append("selected"); 
                formContent.append(">" + k + "</option>");
              } 
              formContent.append("</select>：");
              formContent.append("<select name=\"" + fieldName + "_minute\" onchange=\"changeSelect()\">");
              for (k = 0; k < 60; k++) {
                formContent.append("<option value=\"" + k + "\" ");
                if (minute.equals((new StringBuilder(String.valueOf(k))).toString()))
                  formContent.append("selected"); 
                formContent.append(">" + k + "</option>");
              } 
              formContent.append("</select>");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_time\">");
              if (fieldNull == 1)
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 109:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              String[] day = userValue.substring(0, userValue.indexOf(" ")).split("/");
              String[] time = userValue.substring(userValue.indexOf(" ") + 1, userValue.length()).split(":");
              formContent.append("<script language=\"javascript\">");
              formContent.append("var dptDate = createDatePicker(\"" + fieldName + "_day\",\"" + day[0] + "\",\"" + day[1] + "\",\"" + day[2] + "\");");
              formContent.append("</script>");
              formContent.append("&nbsp;&nbsp;<select name=\"" + fieldName + "_hour\" onchange=\"changeSelect()\">");
              int k;
              for (k = 0; k < 24; k++) {
                formContent.append("<option value=\"" + k + "\" ");
                if (time[0].equals((new StringBuilder(String.valueOf(k))).toString()))
                  formContent.append("selected"); 
                formContent.append(">" + k + "</option>");
              } 
              formContent.append("</select>：");
              formContent.append("<select name=\"" + fieldName + "_minute\" onchange=\"changeSelect()\">");
              for (k = 0; k < 60; k++) {
                formContent.append("<option value=\"" + k + "\" ");
                if (time[1].equals((new StringBuilder(String.valueOf(k))).toString()))
                  formContent.append("selected"); 
                formContent.append(">" + k + "</option>");
              } 
              formContent.append("</select>:");
              formContent.append("<select name=\"" + fieldName + "_second\" onchange=\"changeSelect()\">");
              for (k = 0; k < 60; k++) {
                formContent.append("<option value=\"" + ((k < 10) ? (48 + k) : k) + "\" ");
                if (time[1].equals((new StringBuilder(String.valueOf(k))).toString()))
                  formContent.append("selected"); 
                formContent.append(">" + ((k < 10) ? (48 + k) : k) + "</option>");
              } 
              formContent.append("</select>");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_date\">");
              if (fieldNull == 1)
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 110:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<textarea name=\"" + fieldName + "\" cols=\"60\" Class=\"inputTextarea\" rows=\"4\" onfocus=\"changeTextarea()\" ");
              if (!fieldType.equals("LONG")) {
                formContent.append("onblur=\"checkTextArea(this, '" + fieldDesName + "', " + (fieldLen / 2) + ")\"");
              } else {
                formContent.append("onblur=\"checkTextArea(this, '" + fieldDesName + "', 2000)\"");
              } 
              formContent.append(">" + userValue + "</textarea>");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 113:
            base64 = new BASE64();
            myHtmlValue = base64.BASE64Decoder(userValue);
            if (myHtmlValue.endsWith("_@ISBASE64CODE@")) {
              myHtmlValue = myHtmlValue.substring(0, myHtmlValue.length() - 15);
            } else {
              myHtmlValue = userValue;
            } 
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<textarea name=\"" + fieldName + "\" style=\"display:'none'\">" + myHtmlValue + "</textarea>");
              formContent.append("<INPUT type=hidden name=content1>");
              formContent.append("<IFRAME id=" + fieldName + "_html src=/jsoa/public/edit/ewebeditor.htm?id=content1&style=coolblue frameborder=0 scrolling=no width=100% height=350></IFRAME>");
              formContent.append("<input type=\"hidden\" name=\"hasHtml\" value=\"" + fieldName + "\">");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_html\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "_html\">");
              } 
              break;
            } 
            formContent.append(myHtmlValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 115:
            tmp2 = new String[] { "" };
            if (userValue != null && !userValue.equals("") && !userValue.toUpperCase().equals("NULL"))
              if (userValue.indexOf("::") >= 0) {
                tmp2 = userValue.split("::");
              } else {
                tmp2[0] = userValue;
              }  
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<table id=\"" + fieldName + "_tab\" width=\"80%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#999999\" bgcolor=\"#999999\">");
              formContent.append("<tr bgcolor=\"#CCCCCC\">");
              formContent.append("<td width=\"80%\" align=\"center\">文件名</td>");
              formContent.append("<td width=\"20%\" align=\"center\"><table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
              formContent.append("<tr>");
              formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
              formContent.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
              formContent.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
              formContent.append("<font color=\"#000000\" style=\"cursor:'hand'\" onclick=\"addFile('" + fieldName + "');\">添加</font></td>");
              formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
              formContent.append("</tr>");
              formContent.append("</table></td></tr>");
              for (int k = 0; k < tmp2.length; k++) {
                if (!tmp2[k].equals("")) {
                  formContent.append("<tr bgcolor=\"#FFFFFF\" bordercolor=\"#999999\">");
                  formContent.append("<td height=\"22\">");
                  formContent.append("<a href=" + fileServer + "/download.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + tmp2[k].substring(tmp2[k].indexOf(":") + 1) + "&name=" + tmp2[k].substring(0, tmp2[k].indexOf(":")) + "&path=workflow") + ">");
                  formContent.append(tmp2[k].substring(0, tmp2[k].indexOf(":")));
                  formContent.append("</a>");
                  formContent.append("<input type=\"hidden\" name=\"" + fieldName + "_fileName\" value=\"" + tmp2[k].substring(0, tmp2[k].indexOf(":")) + "\">");
                  formContent.append("<input type=\"hidden\" name=\"" + fieldName + "_saveName\" value=\"" + tmp2[k].substring(tmp2[k].indexOf(":") + 1) + "\">");
                  formContent.append("</td>");
                  formContent.append("<td align=\"center\">");
                  formContent.append("<img src=\"images/del.gif\" alt=\"删除\" style=\"cursor:hand\" onclick=\"deleteRow(" + (k + 1) + ",'" + fieldName + "','" + tmp2[k].substring(tmp2[k].indexOf(":") + 1) + "');\">");
                  formContent.append("<input type=hidden name=tableRow value=" + (k + 1) + ">");
                  formContent.append("</td>");
                  formContent.append("</tr>");
                } 
              } 
              formContent.append("</table>");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_file\">");
              break;
            } 
            if (tmp2 != null)
              for (int k = 0; k < tmp2.length; k++) {
                if (!tmp2[k].equals("")) {
                  formContent.append("<a href=" + fileServer + "/public/jsp/download.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + tmp2[k].substring(tmp2[k].indexOf(":") + 1) + "&name=" + tmp2[k].substring(0, tmp2[k].indexOf(":")) + "&path=workflow") + ">");
                  formContent.append(String.valueOf(tmp2[k].substring(0, tmp2[k].indexOf(":"))) + "&nbsp;&nbsp;");
                  formContent.append("</a>");
                } 
              }  
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 201:
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
            break;
          case 202:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<input type=\"text\" name=\"" + fieldName + "\" value=\"" + userValue + "\" class=\"inputText\" onfocus=\"changeText()\" size=\"60\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
            } else {
              formContent.append(userValue);
              formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            } 
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
            break;
          case 203:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<input type=\"text\" size=\"60\" class=\"inputText\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\" >");
              formContent.append("<input type=\"hidden\" name=\"computeField\" value=\"" + fieldName + "\">");
              formContent.append("<input type=\"hidden\" name=\"computeFieldValue\" value=\"" + fieldValue + "\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\" >");
            formContent.append("<input type=\"hidden\" name=\"computeField\" value=\"" + fieldName + "\">");
            formContent.append("<input type=\"hidden\" name=\"computeFieldValue\" value=\"" + fieldValue + "\">");
            break;
          case 204:
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 205:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<textarea name=\"" + fieldName + "\" style=\"display:'none'\">" + userValue + "</textarea>");
              formContent.append("<INPUT type=hidden name=content1>");
              formContent.append("<IFRAME id=" + fieldName + "_html src=/jsoa/public/edit/ewebeditor.htm?id=content1&style=coolblue frameborder=0 scrolling=no width=100% height=350></IFRAME>");
              formContent.append("<input type=\"hidden\" name=\"hasHtml\" value=\"" + fieldName + "\">");
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "_html\">");
              } 
              break;
            } 
            formContent.append(userValue);
            formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            break;
          case 206:
            canModify = false;
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope)
              canModify = true; 
            if (fieldValue != null && !fieldValue.equals("")) {
              int firstPosition = fieldValue.indexOf(";");
              String childTableName = fieldValue.substring(0, firstPosition);
              List<TFieldVO> childDesName = workFlowBD.getChildField(tableId, fieldValue);
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_group\">");
              formContent.append("<input type=\"hidden\" name=\"childTable_" + fieldName + "\" value=\"" + childTableName + "\">");
              formContent.append("<table id=\"" + fieldName + "_group\" width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#999999\" bgcolor=\"#999999\">");
              formContent.append("<tr bgcolor=\"#CCCCCC\" height=\"20\">");
              StringBuffer tmpSB = new StringBuffer();
              ArrayList<String> childList = new ArrayList();
              int k;
              for (k = 0; k < childDesName.size(); k++) {
                TFieldVO childFieldVO = childDesName.get(k);
                childList.add(childFieldVO.getFieldName());
                tmpSB.append(String.valueOf(childFieldVO.getFieldName()) + ";");
                formContent.append("<td align=\"center\" width=\"" + (90 / (childDesName.size() + 1)) + "%\">" + childFieldVO.getFieldDesName() + "</td>");
              } 
              formContent.append("<input type=\"hidden\" name=\"childField_" + fieldName + "\" value=\"" + tmpSB.substring(0, tmpSB.length() - 1).toString() + "\">");
              if (canModify) {
                formContent.append("<td width=\"10%\" align=\"center\">");
                formContent.append("<table width=\"60\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                formContent.append("<tr>");
                formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
                formContent.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
                formContent.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
                formContent.append("<font color=\"#000000\" style=\"cursor:'hand'\" onclick=\"addGroup_" + fieldName + "();\">添加</font></td>");
                formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
                formContent.append("</tr>");
                formContent.append("</table>");
                formContent.append("</td>\n");
                formContent.append("<script language=\"javascript\">\n");
                formContent.append("function addGroup_" + fieldName + "(){\n");
                formContent.append("var tableObj = document.all." + fieldName + "_group;\n");
                formContent.append("tableObj.insertRow();\n");
                formContent.append("var rowNum = tableObj.rows.length - 1;\n");
                formContent.append("var newNode = tableObj.rows(rowNum);\n");
                formContent.append("newNode.bgColor = \"#FFFFFF\";\n");
                formContent.append("newNode.bordercolor = \"#999999\";\n");
                for (k = 0; k < childDesName.size() + 1; k++) {
                  formContent.append("newNode.insertCell();\n");
                  formContent.append("newNode.cells(" + k + ").height = \"20\";\n");
                  formContent.append("newNode.cells(" + k + ").align = \"center\";\n");
                  if (k == childDesName.size()) {
                    formContent.append("newNode.cells(" + k + ").innerHTML=\"<img src=images/del.gif alt=删除 onclick=delGroupRow('" + fieldName + "',\" + rowNum + \") style=cursor:hand><input type=hidden name=tableRow value=\" + rowNum + \">\";\n");
                  } else {
                    TFieldVO childFieldVO = childDesName.get(k);
                    formContent.append("newNode.cells(" + k + ").innerHTML=\"<input type=text name=" + childFieldVO.getFieldName());
                    if (childFieldVO.getFieldType().toUpperCase().equals("INT") || childFieldVO.getFieldType().toUpperCase().equals("NUMERIC")) {
                      formContent.append(" onblur=if(isNaN(this)) {alert('请输入数字！');this.focus();}");
                    } else if (childFieldVO.getFieldType().toUpperCase().equals("VARCHAR2")) {
                      formContent.append(" maxlength=" + (childFieldVO.getFieldLen() / 2));
                    } 
                    formContent.append(" class=inputText size=10>\";\n");
                  } 
                } 
                formContent.append("}\n");
                formContent.append("</script>\n");
              } 
              List<String[]> childTableValue = workFlowBD.getChildTableValue(childTableName, childList, tableId, recordId);
              if (childTableValue != null && childTableValue.size() > 0)
                for (int m = 0; m < childTableValue.size(); m++) {
                  formContent.append("<tr bgColor=\"#FFFFFF\" bordercolor=\"#999999\">");
                  String[] childTableValueArray = childTableValue.get(m);
                  for (int n = 0; n < childTableValueArray.length; n++) {
                    TFieldVO childFieldVO = childDesName.get(n);
                    if (canModify) {
                      formContent.append("<td align=\"center\"><input type=\"text\" name=\"" + childFieldVO.getFieldName() + "\" value=\"" + childTableValueArray[n] + "\"");
                      if (childFieldVO.getFieldType().toUpperCase().equals("INT") || childFieldVO.getFieldType().toUpperCase().equals("NUMERIC")) {
                        formContent.append(" onblur=if(isNaN(this)) {alert('请输入数字！');this.focus();}");
                      } else if (childFieldVO.getFieldType().toUpperCase().equals("VARCHAR2")) {
                        formContent.append(" maxlength=" + (childFieldVO.getFieldLen() / 2));
                      } 
                      formContent.append(" class=\"inputText\" size=\"10\" ></td>");
                    } else {
                      formContent.append("<td>" + childTableValueArray[n] + "</td>");
                      formContent.append("<input type=\"hidden\" name=\"" + childFieldVO.getFieldName() + "\" value=\"" + childTableValueArray[n] + "\" >");
                    } 
                  } 
                  if (canModify)
                    formContent.append("<td align=\"center\"><img src=\"images/del.gif\" alt=\"删除\" onclick=delGroupRow('" + fieldName + "'," + (m + 1) + ") style=\"cursor:hand\"><input type=\"hidden\" name=\"tableRow\" value=\"" + (m + 1) + "\"></td>"); 
                  formContent.append("</tr>");
                }  
              formContent.append("</tr>");
              formContent.append("</table>");
            } 
            break;
          case 207:
            if (writeFieldString.indexOf("$" + fieldName + "$") >= 0 || writeScope) {
              formContent.append("<input type=\"text\" name=\"" + fieldName + "\" value=\"" + userValue + "\" class=\"inputText\" size=\"60\">");
              if (fieldNull == 1) {
                formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
                formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
              } 
            } else {
              formContent.append(userValue);
              formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
            } 
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
            break;
        } 
        formContent.append("</td>");
        formContent.append("</tr>");
      } 
    } 
    curFieldStr.append("-1");
    formContent.append("<input type=hidden name=curFieldStr value=" + curFieldStr.toString() + ">");
    return formContent.toString();
  }
  
  public String parseForm(String tableId, String recordId, List noWriteField, String pageId) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    StringBuffer formContent = new StringBuffer();
    String fieldName = "", fieldDesName = "", fieldValue = "", fieldType = "", userValue = "";
    int fieldId = 0, fieldShow = 0, fieldNull = 0, fieldLen = 0;
    List<String[]> fieldList = workFlowBD.getFieldAndValue(tableId, recordId, pageId);
    String[] tmp = (String[])null;
    for (int i = 0; i < fieldList.size(); i++) {
      StringBuffer sb;
      String dataStr;
      List<String[]> tabList;
      int j;
      String dateTmp[], hour, minute;
      int k;
      String[] day, time;
      int m;
      String[] tmp2;
      int n;
      boolean canModify;
      tmp = fieldList.get(i);
      fieldName = tmp[0];
      fieldId = Integer.parseInt(tmp[1]);
      fieldDesName = tmp[2];
      fieldShow = Integer.parseInt(tmp[3]);
      fieldValue = tmp[4];
      userValue = tmp[5];
      if (userValue == null || userValue.toUpperCase().equals("NULL"))
        userValue = ""; 
      fieldNull = Integer.parseInt(tmp[6]);
      fieldType = tmp[7].toUpperCase();
      fieldLen = Integer.parseInt(tmp[8]);
      formContent.append("<tr id=\"tr_" + fieldId + "\" ");
      if (fieldShow == 201 || fieldShow == 204 || fieldShow == 208 || fieldShow == 209)
        formContent.append("style=\"display:none\""); 
      formContent.append(">");
      formContent.append("<td width=\"15%\" height=\"22\">");
      formContent.append(String.valueOf(fieldDesName) + ":");
      formContent.append("</td>");
      formContent.append("<td width=\"85%\">");
      switch (fieldShow) {
        case 101:
          if (fieldType.equals("INT") || fieldType.equals("NUMERIC")) {
            formContent.append("<input type=\"text\" name=\"" + fieldName + "\" size=\"60\" class=\"inputText\" value=\"" + userValue + "\" onfocus=\"changeText()\" onblur=\"checkNum(this)\">");
          } else {
            formContent.append("<input type=\"text\" name=\"" + fieldName + "\" size=\"60\" class=\"inputText\" value=\"" + userValue + "\" maxlength=\"" + (fieldLen / 2) + "\" onfocus=\"changeText()\">");
          } 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          break;
        case 102:
          formContent.append("<input type=\"password\" name=\"" + fieldName + "\" value=\"" + userValue + "\" size=\"60\" class=\"inputText\" maxlength=\"" + (fieldLen / 2) + "\" onfocus=\"changeText()\">");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          break;
        case 103:
          sb = new StringBuffer();
          if (!fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
            if (fieldValue.startsWith("@")) {
              List<Object[]> list = getIdValue(fieldValue);
              Object[] obj = (Object[])null;
              for (int i1 = 0; i1 < list.size(); i1++) {
                obj = list.get(i1);
                formContent.append("<input type=\"radio\" name=\"" + fieldName + "\" value=\"" + obj[0] + "\" ");
                if (userValue.equals(obj[0]))
                  formContent.append("checked"); 
                formContent.append(" onclick=\"clickRadio(this)\">" + obj[1] + "&nbsp;");
                sb.append(obj[0] + "/" + obj[1] + ";");
              } 
              formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + sb.toString() + "\">");
            } else {
              String[] radioTmp = fieldValue.split(";");
              for (int i1 = 0; i1 < radioTmp.length; i1++) {
                String checkValue = radioTmp[i1].substring(0, radioTmp[i1].indexOf("/"));
                String checkText = radioTmp[i1].substring(radioTmp[i1].indexOf("/") + 1, radioTmp[i1].length());
                formContent.append("<input type=\"radio\" name=\"" + fieldName + "\" value=\"" + checkValue + "\" ");
                if (checkValue.equals(userValue))
                  formContent.append("checked"); 
                formContent.append(" onclick=\"clickRadio(this)\">" + checkText + "&nbsp;");
              } 
              formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + fieldValue + "\">");
            } 
            if (fieldNull == 1)
              formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
          } 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          break;
        case 104:
          sb = new StringBuffer();
          if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
            if (fieldValue.startsWith("@")) {
              List<Object[]> list = getIdValue(fieldValue);
              Object[] obj = (Object[])null;
              for (int i1 = 0; i1 < list.size(); i1++) {
                obj = list.get(i1);
                formContent.append("<input type=\"checkbox\" name=\"" + fieldName + "\" value=\"" + obj[0] + "\" ");
                if (("," + userValue + ",").indexOf("," + obj[0] + ",") >= 0)
                  formContent.append("checked"); 
                formContent.append(" onclick=\"clickCheckbox()\">" + obj[1] + "&nbsp;");
                sb.append(obj[0] + "/" + obj[1] + ";");
              } 
              formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldName + "\" value=\"" + sb.toString() + "\">");
            } else {
              String[] checkTmp = fieldValue.split(";");
              for (int i1 = 0; i1 < checkTmp.length; i1++) {
                String checkValue = checkTmp[i1].substring(0, checkTmp[i1].indexOf("/"));
                String checkText = checkTmp[i1].substring(checkTmp[i1].indexOf("/") + 1, checkTmp[i1].length());
                formContent.append("<input type=\"checkbox\" name=\"" + fieldName + "\" value=\"" + checkValue + "\" ");
                if (("," + userValue + ",").indexOf("," + checkValue + ",") >= 0)
                  formContent.append("checked"); 
                formContent.append(" onclick=\"clickCheckbox()\">" + checkText + "&nbsp;");
              } 
              formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldName + "\" value=\"" + fieldValue + "\">");
            } 
            if (fieldNull == 1) {
              formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
              formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
            } 
          } 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_checkbox\">");
          break;
        case 105:
          sb = new StringBuffer();
          if (fieldValue != null && !fieldValue.equals("") && !fieldValue.toUpperCase().equals("NULL")) {
            formContent.append("<select name=\"" + fieldName + "\" onchange=\"changeSelect()\">");
            if (fieldValue.startsWith("@")) {
              List<Object[]> list = getIdValue(fieldValue);
              Object[] obj = (Object[])null;
              for (int i1 = 0; i1 < list.size(); i1++) {
                obj = list.get(i1);
                formContent.append("<option value=\"" + obj[0] + "\" ");
                if (userValue.equals(obj[0]))
                  formContent.append("selected"); 
                formContent.append(">" + obj[1] + "</option>");
                sb.append(obj[0] + "/" + obj[1] + ";");
              } 
            } else {
              String[] selectTmp = fieldValue.split(";");
              for (int i1 = 0; i1 < selectTmp.length; i1++) {
                String checkValue = selectTmp[i1].substring(0, selectTmp[i1].indexOf("/"));
                String checkText = selectTmp[i1].substring(selectTmp[i1].indexOf("/") + 1, selectTmp[i1].length());
                formContent.append("<option value=\"" + checkValue + "\" ");
                if (checkValue.equals(userValue))
                  formContent.append("selected"); 
                formContent.append(">" + checkText + "</option>");
              } 
            } 
            formContent.append("</select>");
            if (sb.length() == 0) {
              formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + fieldValue + "\">");
            } else {
              formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldName + "\" value=\"" + sb.toString() + "\">");
            } 
            if (fieldNull == 1)
              formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          } 
          break;
        case 106:
          dataStr = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
          tabList = workFlowBD.getIdValue(dataStr.substring(0, dataStr.indexOf(".")), dataStr.substring(dataStr.indexOf(".") + 1, dataStr.length()));
          formContent.append("<select name=\"" + fieldName + "\" onchange=\"changeSelect()\">");
          for (j = 0; j < tabList.size(); j++) {
            String[] str2 = tabList.get(j);
            formContent.append("<option value=\"" + str2[0] + "\" ");
            if (userValue.equals(str2[0]))
              formContent.append("selected"); 
            formContent.append(">" + str2[1] + "</option>");
          } 
          formContent.append("</select>");
          if (fieldNull == 1)
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          break;
        case 107:
          dateTmp = userValue.split("/");
          formContent.append("<script language=\"javascript\">");
          formContent.append("var dptDate = createDatePicker(\"" + fieldName + "\",\"" + dateTmp[0] + "\",\"" + dateTmp[1] + "\",\"" + dateTmp[2] + "\");");
          formContent.append("</script>");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          if (fieldNull == 1)
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
          break;
        case 108:
          hour = userValue.substring(0, userValue.indexOf(":"));
          minute = userValue.substring(userValue.indexOf(":") + 1, userValue.length());
          formContent.append("<select name=\"" + fieldName + "_hour\" onchange=\"changeSelect()\">");
          for (k = 0; k < 24; k++) {
            formContent.append("<option value=\"" + k + "\" ");
            if (hour.equals((new StringBuilder(String.valueOf(k))).toString()))
              formContent.append("selected"); 
            formContent.append(">" + k + "</option>");
          } 
          formContent.append("</select>：");
          formContent.append("<select name=\"" + fieldName + "_minute\" onchange=\"changeSelect()\">");
          for (k = 0; k < 60; k++) {
            formContent.append("<option value=\"" + k + "\" ");
            if (minute.equals((new StringBuilder(String.valueOf(k))).toString()))
              formContent.append("selected"); 
            formContent.append(">" + k + "</option>");
          } 
          formContent.append("</select>");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_time\">");
          if (fieldNull == 1)
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
          break;
        case 109:
          day = userValue.substring(0, userValue.indexOf(" ")).split("/");
          time = userValue.substring(userValue.indexOf(" ") + 1, userValue.length()).split(":");
          formContent.append("<script language=\"javascript\">");
          formContent.append("var dptDate = createDatePicker(\"" + fieldName + "_day\",\"" + day[0] + "\",\"" + day[1] + "\",\"" + day[2] + "\");");
          formContent.append("</script>");
          formContent.append("&nbsp;&nbsp;<select name=\"" + fieldName + "_hour\" onchange=\"changeSelect()\">");
          for (m = 0; m < 24; m++) {
            formContent.append("<option value=\"" + m + "\" ");
            if (time[0].equals((new StringBuilder(String.valueOf(m))).toString()))
              formContent.append("selected"); 
            formContent.append(">" + m + "</option>");
          } 
          formContent.append("</select>：");
          formContent.append("<select name=\"" + fieldName + "_minute\" onchange=\"changeSelect()\">");
          for (m = 0; m < 60; m++) {
            formContent.append("<option value=\"" + m + "\" ");
            if (time[1].equals((new StringBuilder(String.valueOf(m))).toString()))
              formContent.append("selected"); 
            formContent.append(">" + m + "</option>");
          } 
          formContent.append("</select>:");
          formContent.append("<select name=\"" + fieldName + "_second\" onchange=\"changeSelect()\">");
          for (m = 0; m < 60; m++) {
            formContent.append("<option value=\"" + ((m < 10) ? (48 + m) : m) + "\" ");
            if (time[1].equals((new StringBuilder(String.valueOf(m))).toString()))
              formContent.append("selected"); 
            formContent.append(">" + ((m < 10) ? (48 + m) : m) + "</option>");
          } 
          formContent.append("</select>");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_date\">");
          if (fieldNull == 1)
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>"); 
          break;
        case 110:
          formContent.append("<textarea name=\"" + fieldName + "\" cols=\"60\" Class=\"inputTextarea\" rows=\"4\" onfocus=\"changeTextarea()\">" + userValue + "</textarea>");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          break;
        case 113:
          formContent.append("<textarea name=\"" + fieldName + "\" style=\"display:'none'\">" + userValue + "</textarea>");
          formContent.append("<INPUT type=hidden name=content1>");
          formContent.append("<IFRAME id=" + fieldName + "_html src=/jsoa/public/edit/ewebeditor.htm?id=content1&style=coolblue frameborder=0 scrolling=no width=100% height=350></IFRAME>");
          formContent.append("<input type=\"hidden\" name=\"hasHtml\" value=\"" + fieldName + "\">");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "_html\">");
          } 
          break;
        case 115:
          tmp2 = new String[] { "" };
          if (userValue != null && !userValue.equals("") && !userValue.toUpperCase().equals("NULL"))
            if (userValue.indexOf("::") >= 0) {
              tmp2 = userValue.split("::");
            } else {
              tmp2[0] = userValue;
            }  
          formContent.append("<table id=\"" + fieldName + "_tab\" width=\"80%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#999999\" bgcolor=\"#999999\">");
          formContent.append("<tr bgcolor=\"#CCCCCC\">");
          formContent.append("<td width=\"80%\" align=\"center\">文件名</td>");
          formContent.append("<td width=\"20%\" align=\"center\"><table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          formContent.append("<tr>");
          formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
          formContent.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          formContent.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
          formContent.append("<font color=\"#000000\" style=\"cursor:'hand'\" onclick=\"addFile('" + fieldName + "');\">添加</font></td>");
          formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
          formContent.append("</tr>");
          formContent.append("</table></td></tr>");
          for (n = 0; n < tmp2.length; n++) {
            if (!tmp2[n].equals("")) {
              formContent.append("<tr bgcolor=\"#FFFFFF\" bordercolor=\"#999999\">");
              formContent.append("<td height=\"22\">");
              formContent.append("<a href=/jsoa/public/jsp/download.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + tmp2[n].substring(tmp2[n].indexOf(":") + 1) + "&name=" + tmp2[n].substring(0, tmp2[n].indexOf(":")) + "&path=workflow") + ">");
              formContent.append(tmp2[n].substring(0, tmp2[n].indexOf(":")));
              formContent.append("</a>");
              formContent.append("<input type=\"hidden\" name=\"" + fieldName + "_fileName\" value=\"" + tmp2[n].substring(0, tmp2[n].indexOf(":")) + "\">");
              formContent.append("<input type=\"hidden\" name=\"" + fieldName + "_saveName\" value=\"" + tmp2[n].substring(tmp2[n].indexOf(":") + 1) + "\">");
              formContent.append("</td>");
              formContent.append("<td align=\"center\">");
              formContent.append("<img src=\"images/del.gif\" alt=\"删除\" style=\"cursor:hand\" onclick=\"deleteRow(" + (n + 1) + ",'" + fieldName + "','" + tmp2[n].substring(tmp2[n].indexOf(":") + 1) + "');\">");
              formContent.append("<input type=hidden name=tableRow value=" + (n + 1) + ">");
              formContent.append("</td>");
              formContent.append("</tr>");
            } 
          } 
          formContent.append("</table>");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_file\">");
          break;
        case 201:
          formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          break;
        case 202:
          formContent.append("<input type=\"text\" name=\"" + fieldName + "\" value=\"" + userValue + "\" class=\"inputText\" size=\"60\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          break;
        case 203:
          formContent.append("<input type=\"text\" size=\"60\" class=\"inputText\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\" >");
          formContent.append("<input type=\"hidden\" name=\"computeField\" value=\"" + fieldName + "\">");
          formContent.append("<input type=\"hidden\" name=\"computeFieldValue\" value=\"" + fieldValue + "\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          break;
        case 204:
          formContent.append("<input type=\"hidden\" name=\"" + fieldName + "\" value=\"" + userValue + "\">");
          break;
        case 205:
          formContent.append("<textarea name=\"" + fieldName + "\" style=\"display:'none'\">" + userValue + "</textarea>");
          formContent.append("<INPUT type=hidden name=content1>");
          formContent.append("<IFRAME id=" + fieldName + "_html src=/jsoa/public/edit/ewebeditor.htm?id=content1&style=coolblue frameborder=0 scrolling=no width=100% height=350></IFRAME>");
          formContent.append("<input type=\"hidden\" name=\"hasHtml\" value=\"" + fieldName + "\">");
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "_html\">");
          } 
          break;
        case 206:
          canModify = true;
          if (fieldValue != null && !fieldValue.equals("")) {
            int firstPosition = fieldValue.indexOf(";");
            String childTableName = fieldValue.substring(0, firstPosition);
            List<TFieldVO> childDesName = workFlowBD.getChildField(tableId, fieldValue);
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_group\">");
            formContent.append("<input type=\"hidden\" name=\"childTable_" + fieldName + "\" value=\"" + childTableName + "\">");
            formContent.append("<table id=\"" + fieldName + "_group\" width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#999999\" bgcolor=\"#999999\">");
            formContent.append("<tr bgcolor=\"#CCCCCC\" height=\"20\">");
            StringBuffer tmpSB = new StringBuffer();
            ArrayList<String> childList = new ArrayList();
            int i1;
            for (i1 = 0; i1 < childDesName.size(); i1++) {
              TFieldVO childFieldVO = childDesName.get(i1);
              childList.add(childFieldVO.getFieldName());
              tmpSB.append(String.valueOf(childFieldVO.getFieldName()) + ";");
              formContent.append("<td align=\"center\" width=\"" + (90 / (childDesName.size() + 1)) + "%\">" + childFieldVO.getFieldDesName() + "</td>");
            } 
            formContent.append("<input type=\"hidden\" name=\"childField_" + fieldName + "\" value=\"" + tmpSB.substring(0, tmpSB.length() - 1).toString() + "\">");
            if (canModify) {
              formContent.append("<td width=\"10%\" align=\"center\">");
              formContent.append("<table width=\"60\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
              formContent.append("<tr>");
              formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
              formContent.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
              formContent.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
              formContent.append("<font color=\"#000000\" style=\"cursor:'hand'\" onclick=\"addGroup_" + fieldName + "();\">添加</font></td>");
              formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
              formContent.append("</tr>");
              formContent.append("</table>");
              formContent.append("</td>\n");
              formContent.append("<script language=\"javascript\">\n");
              formContent.append("function addGroup_" + fieldName + "(){\n");
              formContent.append("var tableObj = document.all." + fieldName + "_group;\n");
              formContent.append("tableObj.insertRow();\n");
              formContent.append("var rowNum = tableObj.rows.length - 1;\n");
              formContent.append("var newNode = tableObj.rows(rowNum);\n");
              formContent.append("newNode.bgColor = \"#FFFFFF\";\n");
              formContent.append("newNode.bordercolor = \"#999999\";\n");
              for (i1 = 0; i1 < childDesName.size() + 1; i1++) {
                formContent.append("newNode.insertCell();\n");
                formContent.append("newNode.cells(" + i1 + ").height = \"20\";\n");
                formContent.append("newNode.cells(" + i1 + ").align = \"center\";\n");
                if (i1 == childDesName.size()) {
                  formContent.append("newNode.cells(" + i1 + ").innerHTML=\"<img src=images/del.gif alt=删除 onclick=delGroupRow('" + fieldName + "',\" + rowNum + \") style=cursor:hand><input type=hidden name=tableRow value=\" + rowNum + \">\";\n");
                } else {
                  TFieldVO childFieldVO = childDesName.get(i1);
                  formContent.append("newNode.cells(" + i1 + ").innerHTML=\"<input type=text name=" + childFieldVO.getFieldName());
                  if (childFieldVO.getFieldType().toUpperCase().equals("INT") || childFieldVO.getFieldType().toUpperCase().equals("NUMERIC")) {
                    formContent.append(" onblur=if(isNaN(this)) {alert('请输入数字！');this.focus();}");
                  } else if (childFieldVO.getFieldType().toUpperCase().equals("VARCHAR2")) {
                    formContent.append(" maxlength=" + (childFieldVO.getFieldLen() / 2));
                  } 
                  formContent.append(" class=inputText size=10>\";\n");
                } 
              } 
              formContent.append("}\n");
              formContent.append("</script>\n");
            } 
            List<String[]> childTableValue = workFlowBD.getChildTableValue(childTableName, childList, tableId, recordId);
            if (childTableValue != null && childTableValue.size() > 0)
              for (int i2 = 0; i2 < childTableValue.size(); i2++) {
                formContent.append("<tr bgColor=\"#FFFFFF\" bordercolor=\"#999999\">");
                String[] childTableValueArray = childTableValue.get(i2);
                for (int i3 = 0; i3 < childTableValueArray.length; i3++) {
                  TFieldVO childFieldVO = childDesName.get(i3);
                  if (canModify) {
                    formContent.append("<td align=\"center\"><input type=\"text\" name=\"" + childFieldVO.getFieldName() + "\" value=\"" + childTableValueArray[i3] + "\"");
                    if (childFieldVO.getFieldType().toUpperCase().equals("INT") || childFieldVO.getFieldType().toUpperCase().equals("NUMERIC")) {
                      formContent.append(" onblur=if(isNaN(this)) {alert('请输入数字！');this.focus();}");
                    } else if (childFieldVO.getFieldType().toUpperCase().equals("VARCHAR2")) {
                      formContent.append(" maxlength=" + (childFieldVO.getFieldLen() / 2));
                    } 
                    formContent.append(" class=\"inputText\" size=\"10\" ></td>");
                  } else {
                    formContent.append("<td>" + childTableValueArray[i3] + "</td>");
                    formContent.append("<input type=\"hidden\" name=\"" + childFieldVO.getFieldName() + "\" value=\"" + childTableValueArray[i3] + "\" >");
                  } 
                } 
                if (canModify)
                  formContent.append("<td align=\"center\"><img src=\"images/del.gif\" alt=\"删除\" onclick=delGroupRow('" + fieldName + "'," + (i2 + 1) + ") style=\"cursor:hand\"><input type=\"hidden\" name=\"tableRow\" value=\"" + (i2 + 1) + "\"></td>"); 
                formContent.append("</tr>");
              }  
            formContent.append("</tr>");
            formContent.append("</table>");
          } 
          break;
        case 207:
          formContent.append("<input type=\"text\" name=\"" + fieldName + "\" value=\"" + userValue + "\" class=\"inputText\" size=\"60\">");
          if (fieldNull == 1) {
            formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
            formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldName + "\">");
          } 
          formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldName + "_text\">");
          break;
      } 
      formContent.append("</td>");
      formContent.append("</tr>");
    } 
    return formContent.toString();
  }
  
  private List getIdValue(String fieldValue) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    fieldValue = fieldValue.substring(1);
    String tmp = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
    return workFlowBD.getIdValue(tmp.substring(0, tmp.indexOf(".")), tmp.substring(tmp.indexOf(".") + 1, tmp.length()));
  }
}
