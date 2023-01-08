package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_115 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    type = "<input type=hidden name=" + field + "_type id=" + field + 
      "_type value=file>";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<IFRAME name=" + 
        field + 
        " id=" + 
        field + 
        " src='/jsoa/weixin/workflow/eform/upload.jsp?fileName=" + 
        field + 
        "_fileName&saveName=" + 
        field + 
        "_saveName' frameborder=0 marginwidth='0' marginheight='0'" + 
        " scrolling='auto' width='98%' height='150' style=display:'none'></IFRAME>";
    } else {
      html = "<input class=btnButton4font type=\\\"button\\\" value=\\\"上传\\\" onclick=\\\"selectFile('" + 
        field + "_fileName', '" + field + "_saveName', '" + field + "_fileList', '0', '*')\\\">";
    } 
    html = String.valueOf(html) + "<input type=hidden name=" + field + "_fileName id=" + field + 
      "_fileName>";
    html = String.valueOf(html) + "<input type=hidden name=" + field + "_saveName id=" + field + 
      "_saveName>";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "_fileName><label class=mustFillcolor>*</label>"; 
    html = String.valueOf(html) + "<div id=\\\"" + field + "_fileList\\\" class=\\\"fileList\\\"></div>";
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "')){\n document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    String fileListHtml = "";
    int height = 25;
    if (fieldValue != null && (fieldValue.split(";")).length >= 2) {
      if (fieldValue.split(";")[0] != null && (
        fieldValue.split(";")[0].split(",")).length > 0)
        height = (fieldValue.split(";")[0].split(",")).length * height; 
    } else if (fieldValue != null && (fieldValue.split(":")).length >= 2 && 
      fieldValue.split(":")[0] != null && (
      fieldValue.split(":")[0].split(",")).length > 0) {
      height = (fieldValue.split(";")[0].split(",")).length * height;
    } 
    if (height < 30)
      height = 30; 
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      String saveNameStr = "";
      String fileNameStr = "";
      if (fieldValue.length() > 0) {
        String[] saveNameArr = new String[0];
        String[] fileNameArr = new String[0];
        if (fieldValue.indexOf(";") >= 0) {
          String saveNameTemp = fieldValue.substring(0, fieldValue.indexOf(";"));
          String fileNameTemp = fieldValue.substring(fieldValue.indexOf(";") + 1);
          saveNameArr = saveNameTemp.split(",");
          fileNameArr = fileNameTemp.split(",");
        } 
        for (int i = 0; i < saveNameArr.length; i++) {
          if (!"".equals(saveNameArr[i])) {
            saveNameStr = String.valueOf(saveNameStr) + ";" + saveNameArr[i] + ";";
            fileNameStr = String.valueOf(fileNameStr) + ";" + fileNameArr[i] + ";";
          } 
        } 
      } 
      if (fieldValue.indexOf(";") > 0) {
        String[] file = fieldValue.split(";");
        String[] saveName = file[0].split(",");
        String[] fileName = file[1].split(",");
        for (int i = 0; i < saveName.length; i++)
          html = String.valueOf(html) + "<div align=\"left\" title=\"点击下载\">&nbsp;<a  href=\\\"javascript:showHtmlObject(\\'" + saveName[i].toString() + "\\',\\'0\\',\\'customform\\')\\\" >" + fileName[i] + "</a></div>"; 
      } 
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "')){\ndocument.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + html + "';}";
    } else {
      String saveNameStr = "";
      String fileNameStr = "";
      if (fieldValue.length() > 0) {
        String saveNameTemp = fieldValue.substring(0, fieldValue.indexOf(";"));
        String fileNameTemp = fieldValue.substring(fieldValue.indexOf(";") + 1);
        String[] saveNameArr = saveNameTemp.split(",");
        String[] fileNameArr = fileNameTemp.split(",");
        for (int i = 0; i < saveNameArr.length; i++) {
          if (!"".equals(saveNameArr[i])) {
            saveNameStr = String.valueOf(saveNameStr) + ";" + saveNameArr[i] + ";";
            fileNameStr = String.valueOf(fileNameStr) + ";" + fileNameArr[i] + ";";
            fileListHtml = String.valueOf(fileListHtml) + "<div id=\\\"load_" + i + "\\\">" + fileNameArr[i];
            fileListHtml = String.valueOf(fileListHtml) + "<a href=\\\"javascript:showHtmlObject(\\'" + saveNameArr[i] + "\\',\\'" + 
              fileNameArr[i] + "\\',\\'customform\\');\\\">查看</a>";
            fileListHtml = String.valueOf(fileListHtml) + "<a href=\\\"javascript:deleteFile(\\'load_" + i + "\\', \\'" + fileNameArr[i] + 
              "\\', \\'" + saveNameArr[i] + "\\', \\'" + field + "_fileName\\', \\'" + field + 
              "_saveName\\', \\'" + field + "_fileList\\');\\\">删除</a>";
            fileListHtml = String.valueOf(fileListHtml) + "</div>";
          } 
        } 
      } 
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=file>";
      html = "<input class=btnButton4font type=\\\"button\\\" value=\\\"上传\\\" onclick=\\\"selectFile(\\'" + 
        field + "_fileName\\', \\'" + field + "_saveName\\', \\'" + field + 
        "_fileList\\', \\'0\\', \\'*\\')\\\">";
      html = String.valueOf(html) + ((fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + field + "_fileName><label class=mustFillcolor>*</label>") : "");
      html = String.valueOf(html) + "<input type=hidden name=" + field + "_fileName id=" + field + "_fileName>";
      html = String.valueOf(html) + "<input type=hidden name=" + field + "_saveName id=" + field + "_saveName>";
      html = String.valueOf(html) + "<div id=\\\"" + field + "_fileList\\\" class=\\\"fileList\\\">" + fileListHtml + "</div>";
      html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + 
        "';document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+='" + html + "';}";
      if (!"".equals(fileNameStr)) {
        html = String.valueOf(html) + "document.getElementById('" + field + "_fileName').value='" + fileNameStr + "';";
        html = String.valueOf(html) + "document.getElementById('" + field + "_saveName').value='" + saveNameStr + "';";
      } 
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    return html;
  }
}
