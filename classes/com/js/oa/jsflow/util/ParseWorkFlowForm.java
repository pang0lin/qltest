package com.js.oa.jsflow.util;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.TFieldVO;
import com.js.system.util.SysSetupReader;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ParseWorkFlowForm {
  public String parseForm(String tableId, HttpServletRequest request, List noWriteField) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgName = session.getAttribute("orgName").toString();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    StringBuffer formContent = new StringBuffer();
    TFieldVO fieldVO = null;
    int fieldShow = 0;
    String fieldValue = "";
    String fieldType = "";
    int fieldNull = 0;
    String fieldDefault = "";
    Date now = new Date();
    List<TFieldVO> list = workFlowBD.getFormFields(tableId);
    StringBuffer curFieldStr = new StringBuffer();
    for (int i = 0; i < list.size(); i++) {
      fieldVO = list.get(i);
      fieldType = fieldVO.getFieldType().toUpperCase();
      fieldShow = fieldVO.getFieldShow();
      fieldNull = fieldVO.getFieldNull();
      fieldDefault = fieldVO.getFieldDefault();
      if (fieldDefault == null)
        fieldDefault = ""; 
      formContent.append("<tr id=\"tr_" + fieldVO.getFieldId() + "\" ");
      if (fieldShow == 201 || fieldShow == 204 || fieldShow == 208 || fieldShow == 209)
        formContent.append(" style=\"display:none\" "); 
      formContent.append(">");
      formContent.append("<td width=\"15%\" height=\"22\">");
      formContent.append(String.valueOf(fieldVO.getFieldDesName()) + "：");
      if (fieldNull == 1 && 
        !noWriteField.contains(new Long((new StringBuilder(String.valueOf(fieldVO.getFieldId()))).toString()))) {
        formContent.append("&nbsp;<label class=mustFillcolor>*</label>");
        if (fieldShow == 113 || fieldShow == 205) {
          formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldVO.getFieldName() + "_html\">");
        } else {
          formContent.append("<input type=\"hidden\" name=\"mustWrite\" value=\"" + fieldVO.getFieldName() + "\">");
        } 
      } 
      formContent.append("</td>");
      formContent.append("<td width=\"85%\">");
      curFieldStr.append(String.valueOf(fieldVO.getFieldId()) + ",");
      if (fieldVO.getFieldDesName().equals("WORD编辑") && SysSetupReader.getInstance().hasHandSign(request.getSession(true).getAttribute("domainId").toString())) {
        formContent.append("<span style=cursor:hand onclick=\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + fieldVO.getFieldName() + ".value+'&EditType=1&UserName=" + userName + "&ShowSign=0&CanSave=1&fieldName=" + fieldVO.getFieldName() + "', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0');\"><font color=blue>WORD编辑</font></span>");
        formContent.append("<input type=hidden name=" + fieldVO.getFieldName() + ">");
        formContent.append("<input type=hidden name=fieldName value=" + fieldVO.getFieldName() + "_text>");
        formContent.append("</td>");
        formContent.append("</tr>");
      } else {
        StringBuffer sb;
        String tmp;
        List<String[]> tabList;
        int j;
        formContent.append("<input type=hidden name=fieldType value=" + fieldType + ">");
        switch (fieldShow) {
          case 101:
            if (fieldType.equals("INT") || fieldType.equals("NUMERIC")) {
              formContent.append("<input type=\"text\" name=\"" + fieldVO.getFieldName() + "\" size=\"60\" class=\"inputText\" onblur=\"checkNum(this)\" onfocus=\"changeText()\" value=\"" + fieldDefault + "\">");
            } else {
              formContent.append("<input type=\"text\" name=\"" + fieldVO.getFieldName() + "\" ");
              if (!fieldType.equals("LONG") && !fieldType.equals("CLOB") && !fieldType.equals("TEXT"))
                formContent.append("maxlength=\"" + (fieldVO.getFieldLen() / 2) + "\" "); 
              formContent.append("size=\"60\" class=\"inputText\" value=\"" + fieldDefault + "\" onfocus=\"changeText()\">");
            } 
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 102:
            formContent.append("<input type=\"password\" name=\"" + fieldVO.getFieldName() + "\" maxlength=\"" + (fieldVO.getFieldLen() / 2) + "\" size=\"60\" class=\"inputText\" onfocus=\"changeText()\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 103:
            fieldValue = fieldVO.getFieldValue();
            sb = new StringBuffer();
            if (fieldValue != null && !fieldValue.equals(""))
              if (fieldValue.startsWith("@")) {
                List<Object[]> list1 = getIdValue(fieldValue);
                Object[] obj = (Object[])null;
                for (int k = 0; k < list1.size(); k++) {
                  obj = list1.get(k);
                  formContent.append("<input type=\"radio\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + obj[0] + "\" ");
                  if (k == 0)
                    formContent.append("checked"); 
                  formContent.append(" onclick=\"clickRadio(this)\">" + obj[1] + "&nbsp;");
                  sb.append(obj[0] + "/" + obj[1] + ";");
                } 
                formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldVO.getFieldName() + "\" value=\"" + sb.toString() + "\">");
              } else {
                String[] arrayOfString = fieldValue.split(";");
                for (int k = 0; k < arrayOfString.length; k++) {
                  String checkValue = arrayOfString[k].substring(0, arrayOfString[k].indexOf("/"));
                  String checkText = arrayOfString[k].substring(arrayOfString[k].indexOf("/") + 1, arrayOfString[k].length());
                  formContent.append("<input type=\"radio\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + checkValue + "\" ");
                  if (k == 0)
                    formContent.append("checked"); 
                  formContent.append(" onclick=\"clickRadio(this)\">");
                  formContent.append(String.valueOf(checkText) + "&nbsp;");
                } 
                formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldVO.getFieldName() + "\" value=\"" + fieldValue + "\">");
              }  
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 104:
            fieldValue = fieldVO.getFieldValue();
            sb = new StringBuffer();
            if (fieldValue != null && !fieldValue.equals(""))
              if (fieldValue.startsWith("@")) {
                List<Object[]> list1 = getIdValue(fieldValue);
                Object[] obj = (Object[])null;
                for (int k = 0; k < list1.size(); k++) {
                  obj = list1.get(k);
                  formContent.append("<input type=\"checkbox\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + obj[0] + "\" ");
                  if (k == 0)
                    formContent.append("checked"); 
                  formContent.append(" onclick=\"clickCheckbox()\">" + obj[1] + "&nbsp;");
                  sb.append(obj[0] + "/" + obj[1] + ";");
                } 
                formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldVO.getFieldName() + "\" value=\"" + sb.toString() + "\">");
              } else {
                String[] arrayOfString = fieldValue.split(";");
                for (int k = 0; k < arrayOfString.length; k++) {
                  String checkValue = arrayOfString[k].substring(0, arrayOfString[k].indexOf("/"));
                  String checkText = arrayOfString[k].substring(arrayOfString[k].indexOf("/") + 1, arrayOfString[k].length());
                  formContent.append("<input type=\"checkbox\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + checkValue + "\" ");
                  if (k == 0)
                    formContent.append("checked"); 
                  formContent.append(" onclick=\"clickCheckbox()\">");
                  formContent.append(String.valueOf(checkText) + "&nbsp;");
                } 
                formContent.append("<input type=\"hidden\" name=\"remindCheckBox_" + fieldVO.getFieldName() + "\" value=\"" + fieldValue + "\">");
              }  
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_checkbox\">");
            break;
          case 105:
            fieldValue = fieldVO.getFieldValue();
            sb = new StringBuffer();
            formContent.append("<select name=\"" + fieldVO.getFieldName() + "\" onchange=\"changeSelect()\">");
            if (fieldValue != null && !fieldValue.equals(""))
              if (fieldValue.startsWith("@")) {
                List<Object[]> list1 = getIdValue(fieldValue);
                Object[] obj = (Object[])null;
                for (int k = 0; k < list1.size(); k++) {
                  obj = list1.get(k);
                  formContent.append("<option value=\"" + obj[0] + "\">");
                  formContent.append(obj[1]);
                  formContent.append("</option>");
                  sb.append(obj[0] + "/" + obj[1] + ";");
                } 
              } else {
                String[] arrayOfString = fieldValue.split(";");
                for (int k = 0; k < arrayOfString.length; k++) {
                  String checkValue = arrayOfString[k].substring(0, arrayOfString[k].indexOf("/"));
                  String checkText = arrayOfString[k].substring(arrayOfString[k].indexOf("/") + 1, arrayOfString[k].length());
                  formContent.append("<option value=\"" + checkValue + "\">");
                  formContent.append(checkText);
                  formContent.append("</option>");
                } 
              }  
            formContent.append("</select>");
            if (sb.length() == 0) {
              formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldVO.getFieldName() + "\" value=\"" + fieldValue + "\">");
            } else {
              formContent.append("<input type=\"hidden\" name=\"remindText_" + fieldVO.getFieldName() + "\" value=\"" + sb.toString() + "\">");
            } 
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 106:
            fieldValue = fieldVO.getFieldValue();
            formContent.append("<select name=\"" + fieldVO.getFieldName() + "\" onchange=\"changeSelect()\">");
            tmp = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
            tabList = workFlowBD.getIdValue(tmp.substring(0, tmp.indexOf(".")), tmp.substring(tmp.indexOf(".") + 1, tmp.length()));
            for (j = 0; j < tabList.size(); j++) {
              String[] str = tabList.get(j);
              formContent.append("<option value=\"" + str[0] + "\">");
              formContent.append(str[1]);
              formContent.append("</option>");
            } 
            formContent.append("</select>");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 107:
            formContent.append("<script language=\"javascript\">");
            formContent.append("var dptDate = createDatePicker(\"" + fieldVO.getFieldName() + "\", \"" + (now.getYear() + 1900) + "\", \"" + (now.getMonth() + 1) + "\", \"" + now.getDate() + "\");");
            formContent.append("</script>");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 108:
            formContent.append("<select name=\"" + fieldVO.getFieldName() + "_hour\" onchange=\"changeText()\">");
            for (j = 0; j < 24; j++) {
              formContent.append("<option value=\"" + j + "\" ");
              if (now.getHours() == j)
                formContent.append("selected"); 
              formContent.append(">" + j + "</option>");
            } 
            formContent.append("</select>：");
            formContent.append("<select name=\"" + fieldVO.getFieldName() + "_minute\" onchange=\"changeText()\">");
            for (j = 0; j < 60; j++) {
              formContent.append("<option value=\"" + j + "\" ");
              if (now.getMinutes() == j)
                formContent.append("selected"); 
              formContent.append(">" + j + "</option>");
            } 
            formContent.append("</select>");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_time\">");
            break;
          case 109:
            formContent.append("<script language=\"javascript\">");
            formContent.append("var dptDate = createDatePicker(\"" + fieldVO.getFieldName() + "_day\", \"" + (now.getYear() + 1900) + "\", \"" + (now.getMonth() + 1) + "\", \"" + now.getDate() + "\");");
            formContent.append("</script>");
            formContent.append("&nbsp;&nbsp;<select name=\"" + fieldVO.getFieldName() + "_hour\" onchange=\"changeText()\">");
            for (j = 0; j < 24; j++) {
              formContent.append("<option value=\"" + j + "\" ");
              if (now.getHours() == j)
                formContent.append("selected"); 
              formContent.append(">" + j + "</option>");
            } 
            formContent.append("</select>：");
            formContent.append("<select name=\"" + fieldVO.getFieldName() + "_minute\" onchange=\"changeText()\">");
            for (j = 0; j < 60; j++) {
              formContent.append("<option value=\"" + j + "\" ");
              if (now.getMinutes() == j)
                formContent.append("selected"); 
              formContent.append(">" + j + "</option>");
            } 
            formContent.append("</select>");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_date\">");
            break;
          case 110:
            formContent.append("<textarea name=\"" + fieldVO.getFieldName() + "\" cols=\"60\" Class=\"inputTextarea\" rows=\"4\" ");
            if (!fieldType.equals("LONG") && !fieldType.equals("CLOB") && !fieldType.equals("TEXT"))
              formContent.append("onblur=\"checkTextAreaLen(this," + (fieldVO.getFieldLen() / 2) + ")\""); 
            formContent.append(" onfocus=\"changeTextarea()\"></textarea>");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 113:
            formContent.append("<textarea name=\"" + fieldVO.getFieldName() + "\" style=\"display:'none'\"></textarea>");
            formContent.append("<INPUT type=hidden name=content1>");
            formContent.append("<IFRAME id=" + fieldVO.getFieldName() + "_html src=/jsoa/public/edit/ewebeditor.htm?id=content1&style=coolblue frameborder=0 scrolling=no width=100% height=350></IFRAME>");
            formContent.append("<input type=\"hidden\" name=\"hasHtml\" value=\"" + fieldVO.getFieldName() + "\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_html\">");
            break;
          case 115:
            formContent.append("<table id=\"" + fieldVO.getFieldName() + "_tab\" width=\"80%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#999999\" bgcolor=\"#999999\">");
            formContent.append("<tr bgcolor=\"#CCCCCC\">");
            formContent.append("<td width=\"80%\" align=\"center\">文件名</td>");
            formContent.append("<td width=\"20%\" align=\"center\"><table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            formContent.append("<tr>");
            formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
            formContent.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
            formContent.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
            formContent.append("<a href=\"#\" onclick=\"addFile('" + fieldVO.getFieldName() + "');\"><font color=\"#000000\">添加</font></a></td>");
            formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
            formContent.append("</tr>");
            formContent.append("</table></td></tr>");
            formContent.append("</table>");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_file\">");
            break;
          case 201:
            formContent.append("<input type=\"hidden\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + userId + "\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 202:
            formContent.append("<input type=\"text\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + userName + "\" onfocus=\"changeText()\" class=\"inputText\" size=\"60\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 203:
            formContent.append("<input type=\"text\" size=\"60\" class=\"inputText\" name=\"" + fieldVO.getFieldName() + "\" onchange=\"changeText()\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\" >");
            formContent.append("<input type=\"hidden\" name=\"computeField\" value=\"" + fieldVO.getFieldName() + "\">");
            formContent.append("<input type=\"hidden\" name=\"computeFieldValue\" value=\"" + fieldVO.getFieldValue() + "\">");
            break;
          case 204:
            formContent.append("<input type=\"hidden\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + (new Date()).toLocaleString() + "\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 205:
            formContent.append("<textarea name=\"" + fieldVO.getFieldName() + "\" style=\"display:'none'\"></textarea>");
            formContent.append("<INPUT type=hidden name=content1>");
            formContent.append("<IFRAME id=" + fieldVO.getFieldName() + "_html src=/jsoa/public/edit/ewebeditor.htm?id=content1&style=coolblue frameborder=0 scrolling=no width=100% height=350></IFRAME>");
            formContent.append("<input type=\"hidden\" name=\"hasHtml\" value=\"" + fieldVO.getFieldName() + "\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
            break;
          case 206:
            fieldValue = fieldVO.getFieldValue();
            if (fieldValue != null && !fieldValue.equals("")) {
              int firstPosition = fieldValue.indexOf(";");
              String childTableName = fieldValue.substring(0, firstPosition);
              List<TFieldVO> childDesName = workFlowBD.getChildField(tableId, fieldValue);
              formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_group\">");
              formContent.append("<input type=\"hidden\" name=\"childTable_" + fieldVO.getFieldName() + "\" value=\"" + childTableName + "\">");
              formContent.append("<table id=\"" + fieldVO.getFieldName() + "_group\" width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bordercolor=\"#999999\" bgcolor=\"#999999\">");
              formContent.append("<tr bgcolor=\"#CCCCCC\" height=\"20\">");
              StringBuffer tmpSB = new StringBuffer();
              int k;
              for (k = 0; k < childDesName.size(); k++) {
                TFieldVO childFieldVO = childDesName.get(k);
                tmpSB.append(String.valueOf(childFieldVO.getFieldName()) + ";");
                formContent.append("<td align=\"center\" width=\"" + (90 / (childDesName.size() + 1)) + "%\">" + childFieldVO.getFieldDesName() + "</td>");
              } 
              formContent.append("<input type=\"hidden\" name=\"childField_" + fieldVO.getFieldName() + "\" value=\"" + tmpSB.substring(0, tmpSB.length() - 1).toString() + "\">");
              formContent.append("<td width=\"10%\" align=\"center\">");
              formContent.append("<table width=\"60\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
              formContent.append("<tr>");
              formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
              formContent.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
              formContent.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
              formContent.append("<font color=\"#000000\" style=\"cursor:'hand'\" onclick=\"addGroup_" + fieldVO.getFieldName() + "();\">添加</font></td>");
              formContent.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
              formContent.append("</tr>");
              formContent.append("</table>");
              formContent.append("</td>");
              formContent.append("</tr>");
              formContent.append("<tr bgcolor=#FFFFFF>");
              for (k = 0; k < childDesName.size() + 1; k++) {
                if (k == childDesName.size()) {
                  formContent.append("<td height=20 align=center>");
                  formContent.append("<img src=images/del.gif alt=删除 onclick=delGroupRow('" + fieldVO.getFieldName() + "'," + k + ") style=cursor:hand>");
                  formContent.append("<input type = hidden name = tableRow value = " + k + ">");
                  formContent.append("</td>");
                } else {
                  TFieldVO childFieldVO = childDesName.get(k);
                  formContent.append("<td height=20 align=center>");
                  formContent.append("<input type=text name=" + childFieldVO.getFieldName());
                  if (childFieldVO.getFieldType().toUpperCase().equals("INT") || childFieldVO.getFieldType().toUpperCase().equals("NUMERIC")) {
                    formContent.append(" onblur=if(isNaN(this)) {alert('请输入数字！');this.focus();}");
                  } else if (childFieldVO.getFieldType().toUpperCase().equals("VARCHAR2")) {
                    formContent.append(" maxlength=" + (childFieldVO.getFieldLen() / 2));
                  } 
                  formContent.append(" class=inputText size=10>");
                  formContent.append("</td>");
                } 
              } 
              formContent.append("</tr>");
              formContent.append("</table>\n");
              formContent.append("<script language=\"javascript\">\n");
              formContent.append("function addGroup_" + fieldVO.getFieldName() + "(){\n");
              formContent.append("var tableObj = document.all." + fieldVO.getFieldName() + "_group;\n");
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
                  formContent.append("newNode.cells(" + k + ").innerHTML=\"<img src=images/del.gif alt=删除 onclick=delGroupRow('" + fieldVO.getFieldName() + "',\" + rowNum + \") style=cursor:hand><input type = hidden name = tableRow value = \" + rowNum + \">\";\n");
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
            break;
          case 207:
            formContent.append("<input type=\"text\" name=\"" + fieldVO.getFieldName() + "\" value=\"" + orgName + "\" class=\"inputText\" size=\"60\">");
            formContent.append("<input type=\"hidden\" name=\"fieldName\" value=\"" + fieldVO.getFieldName() + "_text\">");
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
  
  private List getIdValue(String fieldValue) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    fieldValue = fieldValue.substring(1);
    String tmp = fieldValue.substring(fieldValue.indexOf("][") + 2, fieldValue.length() - 1);
    return workFlowBD.getIdValue(tmp.substring(0, tmp.indexOf(".")), tmp.substring(tmp.indexOf(".") + 1, tmp.length()));
  }
}
