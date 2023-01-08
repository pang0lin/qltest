package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.util.config.SystemCommon;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_109 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    String timeString = "";
    boolean showDefaultTime = false;
    Date now = new Date();
    type = "<input type=hidden name=" + field + "_type id=" + field + 
      "_type value=datetime>";
    if (fieldTemp[0][14] != null)
      if ("".equals(fieldTemp[0][14])) {
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeString = ymdhms.format(now);
        showDefaultTime = true;
      } else if (fieldTemp[0][14].toString().indexOf("#") > 0) {
        if ("1".equals(fieldTemp[0][14].toString().split("#")[0])) {
          showDefaultTime = true;
          if ((fieldTemp[0][14].toString().split("#")).length > 1 && !"".equals(fieldTemp[0][14].toString().split("#")[1]))
            timeString = fieldTemp[0][14].toString().split("#")[1]; 
        } 
      }  
    String readMinutes = "", readSecond = "", readHours = "", readDate = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      if (showDefaultTime && 
        "".equals(timeString)) {
        readMinutes = (now.getMinutes() > 9) ? 
          String.valueOf(now.getMinutes()) : (
          "0" + String.valueOf(now.getMinutes()));
        readSecond = (now.getSeconds() > 9) ? 
          String.valueOf(now.getSeconds()) : (
          "0" + String.valueOf(now.getSeconds()));
        readHours = (now.getHours() > 9) ? 
          String.valueOf(now.getHours()) : (
          "0" + String.valueOf(now.getHours()));
        readDate = String.valueOf(String.valueOf(now.getYear() + 1900)) + 
          "-" + ((now.getMonth() >= 9) ? 
          String.valueOf(now.getMonth() + 1) : ("0" + 
          String.valueOf(now.getMonth() + 1))) + "-" + (
          (now.getDate() > 9) ? 
          String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate())));
        timeString = String.valueOf(readDate) + " " + readHours + ":" + readMinutes + ":" + readSecond;
      } 
      type = String.valueOf(type) + "<span>" + timeString + "</span>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + " name=" + field + 
        " value='" + readDate + "'>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + "hours name=" + field + 
        "hours value='" + readHours + "'>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + "minutes name=" + field + 
        "minutes value='" + readMinutes + "'>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + "second name=" + field + 
        "second value='" + readSecond + "'>";
    } else {
      String dateString = "";
      int nowHour = 0;
      int nowMinute = 0;
      int nowSecond = 0;
      if (showDefaultTime)
        if ("".equals(timeString)) {
          dateString = String.valueOf(String.valueOf(now.getYear() + 1900)) + "-" + ((now.getMonth() >= 9) ? String.valueOf(now.getMonth() + 1) : ("0" + String.valueOf(now.getMonth() + 1))) + 
            "-" + ((now.getDate() > 9) ? String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate())));
          nowHour = now.getHours();
          nowMinute = now.getMinutes();
          nowSecond = now.getSeconds();
        } else {
          dateString = timeString.split(" ")[0];
          nowHour = Integer.parseInt(timeString.split(" ")[1].split(":")[0]);
          nowMinute = Integer.parseInt(timeString.split(" ")[1].split(":")[1]);
          nowSecond = Integer.parseInt(timeString.split(" ")[1].split(":")[2]);
        }  
      type = String.valueOf(type) + "<input type=text readonly=readonly data-role=datebox  size=12 style='font-size:1em;float:left;'  onmouseover=setStyle(this) onmouseout=setStyle(this) name=" + 
        field + 
        " id=" + 
        field + 
        "  value='" + 
        dateString + 
        "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');\\\">";
      type = String.valueOf(type) + "<div class=ui-select>日</div>&nbsp;<select name=" + field + 
        "hours style=font-size:1em; id=" + field + "hours>";
      int i;
      for (i = 0; i < 24; i++) {
        String hours = "";
        if (i >= 0 && i < 10) {
          hours = "0" + String.valueOf(i);
        } else {
          hours = String.valueOf(i);
        } 
        type = String.valueOf(type) + "<option value=" + String.valueOf(hours) + (
          (i == nowHour) ? " selected" : "") + ">" + 
          String.valueOf(hours) + "</option>";
      } 
      type = String.valueOf(type) + "</select>";
      type = String.valueOf(type) + "<div class=ui-select>时&nbsp;</div><select name=" + field + 
        "minutes style=font-size:1em; id=" + field + "minutes>";
      for (i = 0; i < 60; i++) {
        String minute = "";
        if (i >= 0 && i < 10) {
          minute = "0" + String.valueOf(i);
        } else {
          minute = String.valueOf(i);
        } 
        type = String.valueOf(type) + "<option value=" + String.valueOf(minute) + (
          (i == nowMinute) ? " selected" : "") + ">" + 
          String.valueOf(minute) + "</option>";
      } 
      type = String.valueOf(type) + "</select><div class=ui-select>分&nbsp;</div>";
      type = String.valueOf(type) + "<select name=" + field + "second style=font-size:1em; id=" + 
        field + "second>";
      for (i = 0; i < 60; i++) {
        String second = "";
        if (i >= 0 && i < 10) {
          second = "0" + String.valueOf(i);
        } else {
          second = String.valueOf(i);
        } 
        type = String.valueOf(type) + "<option value=" + String.valueOf(second) + (
          (i == nowSecond) ? " selected" : "") + ">" + 
          String.valueOf(second) + "</option>";
      } 
      type = String.valueOf(type) + "</select><div class=ui-select>秒&nbsp;</div>";
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      type = String.valueOf(type) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML=\"" + type + "\";}";
    if (!"".equals(fieldTemp[0][4]) && 
      !"null".equalsIgnoreCase((new StringBuilder(String.valueOf(fieldTemp[0][4]))).toString()))
      html = String.valueOf(html) + "\ndocument.getElementById('dateCompare').value=document.getElementById('dateCompare').value+'{" + 
        fieldTemp[0][4] + "}';\n"; 
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    Date now = new Date();
    String timeString = "";
    type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=datetime>";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      String readDate = "", readMinutes = "", readHours = "", readSecond = "";
      if (!"00:00:00".trim().equals(fieldValue) && !"0:0:00".trim().equals(fieldValue))
        if (fieldValue != null && !"null".equals(fieldValue)) {
          if (!"".equals(fieldValue) && !"::".equals(fieldValue))
            if (fieldValue.indexOf(" ::") > 0) {
              readDate = fieldValue.substring(0, fieldValue.indexOf(" "));
              timeString = String.valueOf(readDate) + " 00:00:00";
            } else if (fieldValue.indexOf(":") > 0) {
              readDate = fieldValue.substring(0, fieldValue.indexOf(" "));
              readHours = fieldValue.substring(fieldValue.indexOf(" ") + 1);
              readHours = readHours.substring(0, readHours.indexOf(":"));
              if ((fieldValue.trim().split(" ")[1].split(":")).length >= 3) {
                readMinutes = fieldValue.substring(fieldValue.indexOf(":") + 1, fieldValue.lastIndexOf(":"));
                readSecond = fieldValue.substring(fieldValue.lastIndexOf(":") + 1);
              } else {
                readMinutes = fieldValue.substring(fieldValue.indexOf(":") + 1);
                readSecond = "00";
              } 
              timeString = String.valueOf(readDate) + " " + readHours + ":" + readMinutes + ":" + ((readSecond == null) ? "00" : readSecond);
            } else {
              timeString = String.valueOf(fieldValue) + " 00:00:00";
            }  
        } else {
          readMinutes = (now.getMinutes() > 9) ? 
            String.valueOf(now.getMinutes()) : (
            "0" + String.valueOf(now.getMinutes()));
          readSecond = (now.getSeconds() > 9) ? 
            String.valueOf(now.getSeconds()) : (
            "0" + String.valueOf(now.getSeconds()));
          readHours = (now.getHours() > 9) ? 
            String.valueOf(now.getHours()) : (
            "0" + String.valueOf(now.getHours()));
          readDate = String.valueOf(String.valueOf(now.getYear() + 1900)) + 
            "-" + ((now.getMonth() >= 9) ? 
            String.valueOf(now.getMonth() + 1) : ("0" + 
            String.valueOf(now.getMonth() + 1))) + "-" + (
            (now.getDate() > 9) ? 
            String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate())));
          timeString = String.valueOf(readDate) + " " + readHours + ":" + readMinutes + ":" + ((readSecond == null) ? "00" : readSecond);
        }  
      type = String.valueOf(type) + "<span>" + timeString + "</span>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + " name=" + field + " value='" + readDate + "'>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + "hours name=" + field + "hours value='" + readHours + "'>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + "minutes name=" + field + "minutes value='" + readMinutes + "'>";
      type = String.valueOf(type) + "<input type=hidden id=" + field + "second name=" + field + "second value='" + readSecond + "'>";
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML=\"" + type + "\";}";
    } else {
      type = String.valueOf(type) + 
        "<input type=text readonly=readonly style='font-size:1em;float:left;'  data-role=datebox onmouseover=setStyle(this) onmouseout=setStyle(this) size=12 name=" + 
        field + " id=" + field + 
        "  value='" + ((
        
        fieldValue == null || 
        fieldValue.trim().length() < 9 || 
        fieldValue.indexOf(" ") < 0) ? fieldValue : 
        fieldValue.split(" ")[0]) + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
      int hours = -1;
      int minutes = -1;
      int second = -1;
      boolean flagstrhours = false;
      if (fieldValue != null && 
        fieldValue.trim().length() >= 12 && 
        fieldValue.indexOf(" ") > 0)
        if (fieldValue.indexOf(" ::") > 0) {
          hours = 0;
          minutes = 0;
          second = 0;
        } else if (fieldValue.indexOf(":") > 0) {
          hours = Integer.parseInt(fieldValue.trim()
              .split(" ")[1].split(":")[0]);
          minutes = Integer.parseInt(fieldValue.trim()
              .split(" ")[1].split(":")[1]);
          if ((fieldValue.trim().split(" ")[1].split(":")).length >= 3) {
            second = Integer.parseInt(fieldValue.trim()
                .split(" ")[1].split(":")[2]);
            flagstrhours = true;
          } 
        } else {
          hours = 0;
          minutes = 0;
          second = 0;
        }  
      if (hours == 24)
        hours = 0; 
      type = String.valueOf(type) + "<div class=ui-select>日&nbsp;</div><select style=font-size:1em; name=" + field + "hours id=" + field + "hours>";
      int i;
      for (i = 0; i < 24; i++) {
        String strhours = "";
        if (i >= 0 && i < 10) {
          strhours = "0" + String.valueOf(i);
        } else {
          strhours = String.valueOf(i);
        } 
        if (flagstrhours) {
          type = String.valueOf(type) + "<option value=" + String.valueOf(("".equals(strhours) || strhours == null) ? "00" : strhours) + (
            (i == hours) ? " selected" : "") + ">" + 
            String.valueOf(("".equals(strhours) || strhours == null) ? "00" : strhours) + "</option>";
        } else {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strhours) + (
            (i == hours) ? " selected" : "") + ">" + 
            String.valueOf(strhours) + "</option>";
        } 
      } 
      type = String.valueOf(type) + "</select>";
      type = String.valueOf(type) + "<div class=ui-select>时&nbsp;</div><select style=font-size:1em; name=" + field + "minutes id=" + field + "minutes>";
      for (i = 0; i < 60; i++) {
        String strminutes = "";
        if (i >= 0 && i < 10) {
          strminutes = "0" + String.valueOf(i);
        } else {
          strminutes = String.valueOf(i);
        } 
        if (flagstrhours) {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strminutes) + (
            (i == minutes) ? " selected" : "") + ">" + 
            String.valueOf(strminutes) + "</option>";
        } else {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strminutes) + (
            (i == minutes) ? " selected" : "") + ">" + 
            String.valueOf(strminutes) + "</option>";
        } 
      } 
      type = String.valueOf(type) + "</select><div class=ui-select>分</div>";
      if (flagstrhours) {
        type = String.valueOf(type) + "<select name=" + field + "second style=font-size:1em; id=" + field + "second>";
        for (i = 0; i < 60; i++) {
          String strsecond = "";
          if (i >= 0 && i < 10) {
            strsecond = "0" + String.valueOf(i);
          } else {
            strsecond = String.valueOf(i);
          } 
          type = String.valueOf(type) + "<option value=" + String.valueOf(strsecond) + (
            (i == second) ? " selected" : 
            "") + ">" + String.valueOf(strsecond) + 
            "</option>";
        } 
        type = String.valueOf(type) + "</select><div class=ui-select>秒&nbsp;</div>";
      } 
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML=\"" + type + "\";}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      if (SystemCommon.getSYWorkflowHR() == 1) {
        String typeValue = "<input type=hidden name=" + field + " id=" + field + " value='" + fieldValue + "'>";
        html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
          fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
        html = String.valueOf(html) + "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
          fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + typeValue + "\";}";
      } else {
        type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=\\'" + fieldValue + "\\'>";
        html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
          fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
      } 
    } else {
      Date now = new Date();
      String dateString = "";
      int nowHour = 0;
      int nowMinute = 0;
      int nowSecond = 0;
      if (isHide) {
        dateString = String.valueOf(String.valueOf(now.getYear() + 1900)) + "-" + ((now.getMonth() >= 9) ? String.valueOf(now.getMonth() + 1) : ("0" + String.valueOf(now.getMonth() + 1))) + 
          "-" + ((now.getDate() > 9) ? String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate())));
        nowHour = now.getHours();
        nowMinute = now.getMinutes();
        nowSecond = now.getSeconds();
      } 
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=datetime>";
      type = String.valueOf(type) + "<input type=text readonly=readonly  data-role=datebox onmouseover=setStyle(this) onmouseout=setStyle(this) size=12 name=" + 
        field + " id=" + field + " onclick=setDay(this) value='";
      if (fieldValue == null || "".equals(fieldValue) || "null".equals(fieldValue)) {
        type = String.valueOf(type) + fieldValue + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
          fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + field + 
          "><label class=mustFillcolor>*</label>") : "");
      } else {
        type = String.valueOf(type) + fieldValue.split(" ")[0] + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
          fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + field + 
          "><label class=mustFillcolor>*</label>") : "");
      } 
      int hours = -1;
      int minutes = -1;
      int second = -1;
      boolean flagstrhours = false;
      if (fieldValue != null && 
        fieldValue.trim().length() >= 12 && 
        fieldValue.indexOf(" ") > 0 && 
        fieldValue.indexOf(":") > 0) {
        hours = Integer.parseInt(fieldValue.trim()
            .split(" ")[1].split(":")[0]);
        minutes = Integer.parseInt(fieldValue.trim()
            .split(" ")[1].split(":")[1]);
        if ((fieldValue.trim().split(" ")[1].split(":")).length == 3) {
          second = Integer.parseInt(fieldValue.trim()
              .split(" ")[1].split(":")[2]);
          flagstrhours = true;
        } 
      } 
      if (hours == 24)
        hours = 0; 
      type = String.valueOf(type) + "<div class=ui-select>日&nbsp;</div><select name=" + field + "hours id=" + field + "hours>";
      int i;
      for (i = 0; i < 24; i++) {
        String strhours = "";
        if (i >= 0 && i < 10) {
          strhours = "0" + String.valueOf(i);
        } else {
          strhours = String.valueOf(i);
        } 
        if (flagstrhours) {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strhours) + (
            (i == hours) ? " selected" : "") + ">" + 
            String.valueOf(strhours) + "</option>";
        } else {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strhours) + (
            (i == nowHour) ? " selected" : "") + ">" + 
            String.valueOf(strhours) + "</option>";
        } 
      } 
      System.out.println(type);
      type = String.valueOf(type) + "</select>";
      type = String.valueOf(type) + "<div class=ui-select>时&nbsp;</div><select name=" + field + "minutes id=" + field + "minutes>";
      for (i = 0; i < 60; i++) {
        String strminutes = "";
        if (i >= 0 && i < 10) {
          strminutes = "0" + String.valueOf(i);
        } else {
          strminutes = String.valueOf(i);
        } 
        if (flagstrhours) {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strminutes) + (
            (i == minutes) ? " selected" : "") + ">" + 
            String.valueOf(strminutes) + "</option>";
        } else {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strminutes) + (
            (i == nowMinute) ? " selected" : "") + ">" + 
            String.valueOf(strminutes) + "</option>";
        } 
      } 
      type = String.valueOf(type) + "</select><div class=ui-select>分</div>";
      type = String.valueOf(type) + "<select name=" + field + "second style=font-size:1em; id=" + field + "second>";
      for (i = 0; i < 60; i++) {
        String strsecond = "";
        if (i >= 0 && i < 10) {
          strsecond = "0" + String.valueOf(i);
        } else {
          strsecond = String.valueOf(i);
        } 
        if (flagstrhours) {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strsecond) + (
            (i == second) ? " selected" : 
            "") + ">" + String.valueOf(strsecond) + 
            "</option>";
        } else {
          type = String.valueOf(type) + "<option value=" + String.valueOf(strsecond) + (
            (i == nowSecond) ? " selected" : 
            "") + ">" + String.valueOf(strsecond) + 
            "</option>";
        } 
      } 
      type = String.valueOf(type) + "</select>秒&nbsp;";
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML=\"" + type + "\";}";
    } 
    return html;
  }
}
