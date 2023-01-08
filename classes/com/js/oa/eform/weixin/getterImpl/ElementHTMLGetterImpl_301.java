package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_301 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    String temp = fieldTemp[0][6];
    String money = "", unit = "", upperField = "";
    if (temp.indexOf("[") == 0 && temp.indexOf("][") > 0) {
      upperField = temp.substring(1, temp.indexOf("]["));
      if (upperField.indexOf(";;") > 0) {
        money = upperField.substring(0, upperField.indexOf(";;"));
        unit = upperField.substring(upperField.indexOf(";;") + 2);
      } else if (upperField.indexOf(";;") == 0) {
        unit = upperField.substring(2);
      } else {
        money = upperField;
      } 
      upperField = temp.substring(temp.indexOf("][") + 2, 
          temp.length() - 1);
    } else if (temp.indexOf(";;") > 0) {
      money = temp.substring(0, temp.indexOf(";;"));
      unit = temp.substring(temp.indexOf(";;") + 2);
    } else if (temp.indexOf(";;") == 0) {
      unit = temp.substring(2);
    } else {
      money = temp;
    } 
    if (!"".equals(money))
      html = "<div style=\\\"float:left;padding-top:2px;font-size:1em;\\\">" + 
        money + "</div>"; 
    html = String.valueOf(html) + "<div style=\\\"float:left;width:60%;font-size:1em;\\\">";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = String.valueOf(html) + "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text class=flowInput id=" + field + 
        " name=" + field + " value=\"" + fieldTemp[0][4] + 
        "\" readonly>";
    } else {
      html = String.valueOf(html) + "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + 
        " name=" + 
        field + 
        " " + (
        isNum ? ("  onblur=checkNum(this);checkSize(this);setComputeForeignFieldNew(this);setTotalValue();accountToUpper(this,\\\"" + 
        upperField + "\\\");") : (
        " onblur=checkSize(this);accountToUpper(this,\\\"" + 
        upperField + "\\\");")) + " value=\"" + 
        fieldTemp[0][4] + "\">";
    } 
    html = String.valueOf(html) + "</div>";
    if (!"".equals(unit))
      html = String.valueOf(html) + "<div style=\\\"float:left;padding-top:2px;font-size:1em;\\\">" + 
        unit + "</div>"; 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    temp = fieldTemp[0][6];
    DecimalFormat df = new DecimalFormat("0.00");
    String money = "", unit = "", upperField = "";
    if (temp.indexOf("[") == 0 && temp.indexOf("][") > 0) {
      upperField = temp.substring(1, temp.indexOf("]["));
      if (upperField.indexOf(";;") > 0) {
        money = upperField.substring(0, upperField.indexOf(";;"));
        unit = upperField.substring(upperField.indexOf(";;") + 2);
      } else if (upperField.indexOf(";;") == 0) {
        unit = upperField.substring(2);
      } else {
        money = upperField;
      } 
      upperField = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
    } else if (temp.indexOf(";;") > 0) {
      money = temp.substring(0, temp.indexOf(";;"));
      unit = temp.substring(temp.indexOf(";;") + 2);
    } else if (temp.indexOf(";;") == 0) {
      unit = temp.substring(2);
    } else {
      money = temp;
    } 
    if (!"".equals(money))
      html = "<div style=\\\"float:left;font-size:1em;\\\">" + money + "</div>"; 
    html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\">";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      if (!"".equals(money))
        html = "<div style=\\\"float:left;font-size:1em;\\\">" + money + "</div>"; 
      html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\">";
      if (request.getParameter("resubmit") == null || (request.getParameter("resubmit") != null && 
        !request.getParameter("resubmit").equals("1")))
        type = ""; 
      html = String.valueOf(html) + "<div name=moneyNum id=moneyNum style=\\\"font-size:1em;\\\"><input type=hidden name=" + 
        field + " id=" + field + " value=\\\"" + fieldValue + "\\\">";
      if (fieldTemp[0][2].equals("1000001"))
        html = String.valueOf(html) + ((fieldValue == null || 
          fieldValue.length() < 1) ? fieldValue : df.format(Double.valueOf(fieldValue))); 
      if (fieldTemp[0][2].equals("1000000"))
        html = String.valueOf(html) + ((fieldValue == null || 
          fieldValue.length() < 1) ? fieldValue : (String)Integer.valueOf(fieldValue)); 
      html = String.valueOf(html) + "</div><div name=moneyChar id=moneyChar style=display:none>" + 
        changeToBig(fieldValue) + "</div>";
      html = String.valueOf(html) + "</div>";
      if (!"".equals(unit))
        html = String.valueOf(html) + "<div style=\\\"float:left;\\\">" + unit + "</div>"; 
    } else {
      if (!"".equals(money))
        html = "<div style=\\\"float:left;padding-top:2px;font-size:1em;\\\">" + money + "</div>"; 
      html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\">";
      html = String.valueOf(html) + 
        "<input  type=text  style=font-size:1em; class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + 
        field + " value=\"" + fieldValue + "\" " + (
        isNum ? ("  onblur=checkNum(this);checkSize(this);setComputeForeignFieldNew(this);setTotalValue();accountToUpper(this,\\\"" + upperField + "\\\");") : (" onblur=checkSize(this);setTotalValue();accountToUpper(this,\\\"" + upperField + "\\\");")) + 
        ">";
      html = String.valueOf(html) + "</div>";
      if (!"".equals(unit))
        html = String.valueOf(html) + "<div style=\\\"float:left;padding-top:2px;font-size:1em;\\\">" + unit + "</div>"; 
      html = String.valueOf(html) + ((fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + 
      "-" + field + "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML='" + type + 
      "';document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML+='" + html + "';}";
    return html;
  }
  
  private String changeToBig(String input) {
    String s1 = "零壹贰叁肆伍陆柒捌玖";
    String s4 = "分角整元拾佰仟万拾佰仟亿拾佰仟";
    String temp = "";
    String result = "";
    if (input == null || input.length() < 1)
      return ""; 
    temp = input.trim();
    try {
      float f = Float.parseFloat(temp);
    } catch (Exception e) {
      return "";
    } 
    int len = 0;
    if (String.valueOf(temp).startsWith("-"))
      temp = String.valueOf(temp).replace("-", ""); 
    if (temp.indexOf(".") == -1) {
      len = temp.length();
    } else {
      len = temp.indexOf(".");
    } 
    if (len > s4.length() - 3)
      return ""; 
    int n2 = 0;
    String num = "";
    String unit = "";
    int add = 0;
    if (len > 7)
      return input; 
    if (len == 7) {
      add = 3;
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(0)));
      result = result.concat(s1.substring(n1, n1 + 1)).concat("佰");
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) == 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(2))) == 0)
        result = result.concat("万"); 
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) == 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(2))) != 0) {
        n1 = Integer.parseInt(String.valueOf(temp.charAt(2)));
        result = result.concat("零").concat(s1.substring(n1, n1 + 1))
          .concat("万");
      } 
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) != 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(2))) == 0) {
        n1 = Integer.parseInt(String.valueOf(temp.charAt(1)));
        result = result.concat(s1.substring(n1, n1 + 1)).concat("拾万");
      } 
      len = 4;
    } 
    if (len == 6) {
      add = 2;
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(0)));
      result = result.concat(s1.substring(n1, n1 + 1)).concat("拾");
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) == 0)
        result = result.concat("万"); 
      if (Integer.parseInt(String.valueOf(temp.charAt(1))) != 0) {
        n1 = Integer.parseInt(String.valueOf(temp.charAt(1)));
        result = result.concat(s1.substring(n1, n1 + 1)).concat("万");
      } 
      len = 4;
    } 
    if (len == 5) {
      add = 1;
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(0)));
      result = result.concat(s1.substring(n1, n1 + 1)).concat("万");
      len = 4;
    } 
    int i;
    for (i = 0; i < len; i++) {
      int n1 = Integer.parseInt(String.valueOf(temp.charAt(i + add)));
      num = s1.substring(n1, n1 + 1);
      if (n1 != 0) {
        n1 = len - i + 2;
        unit = s4.substring(n1, n1 + 1);
      } else {
        unit = "";
      } 
      if (i != 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(i - 1 + add))) == 0 && 
        Integer.parseInt(String.valueOf(temp.charAt(i + add))) == 0) {
        num = "";
        unit = "";
      } 
      result = result.concat(num).concat(unit);
    } 
    while (result.endsWith("零") && result.length() > "零".length())
      result = result.substring(0, result.length() - "零".length()); 
    result = result.concat("元");
    if (temp.indexOf(".") == -1) {
      len = temp.length();
      result = result.concat("整");
    } else {
      len = temp.indexOf(".");
    } 
    if (len < temp.length()) {
      unit = "";
      for (i = 0; i < temp.length() - len - 1; i++) {
        int n1 = Integer.parseInt(String.valueOf(temp.charAt(i + len + 1)));
        num = s1.substring(n1, n1 + 1);
        if (n1 != 0) {
          if (i == 0) {
            unit = "角";
          } else if (i == 1) {
            unit = "分";
          } else {
            unit = "";
          } 
        } else {
          unit = "";
        } 
        result = result.concat(num).concat(unit);
      } 
    } 
    while (result.endsWith("零") && result.length() > "零".length())
      result = result.substring(0, result.length() - "零".length()); 
    result = result.replaceAll("元元", "元");
    if (String.valueOf(temp).startsWith("-"))
      result = "负" + result; 
    return result;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    String onblur = "";
    if ("1".equals(isTotalField))
      onblur = "setSTotalValue(this);"; 
    if (computeFieldStr.indexOf(field) >= 0)
      onblur = String.valueOf(onblur) + "setComputeForeignFieldNew(this);"; 
    if (isHide) {
      html = "<div name=moneyNum id=moneyNum>";
      if (fieldTemp[index][2].equals("1000001"))
        html = String.valueOf(html) + ((fieldValue == null || 
          fieldValue.length() < 1) ? fieldValue : NumberFormat.getInstance().format(Double.valueOf(fieldValue))); 
      if (fieldTemp[index][2].equals("1000000"))
        html = String.valueOf(html) + ((fieldValue == null || 
          fieldValue.length() < 1) ? fieldValue : (String)Integer.valueOf(fieldValue)); 
      html = String.valueOf(html) + "</div><div name=moneyChar id=moneyChar style=display:none>" + changeToBig(fieldValue) + "</div>";
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
    } else {
      html = 
        "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "% type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + 
        field + " value=\"" + fieldValue + "\" " + (
        isNum ? ("  onblur=checkNum(this);checkSize(this);" + onblur) : " onblur=checkSize(this);") + 
        ">" + (
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + 
      "';document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
    return html;
  }
}
