package com.js.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class EditorTag extends TagSupport {
  private static final long serialVersionUID = 1L;
  
  private String basePath;
  
  private String container;
  
  private String toolBar;
  
  private String uploadImage;
  
  private String uploadFlash;
  
  private String uploadLimitImgSize;
  
  public int doStartTag() throws JspException {
    try {
      StringBuffer start = new StringBuffer();
      start.append("<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
      start.append("\t<tr valign=\"top\">\n");
      start.append("\t  <td>\n");
      start.append("\t\t<script type=\"text/javascript\" src=\"/jsoa/public/fckeditor/V3X.js\"></script>\n");
      start.append("\t\t<script type=\"text/javascript\" src=\"/jsoa/public/fckeditor/fckeditor.js\"></script>\n");
      start.append("\t  <div id=\"RTEEditorDiv\">\n");
      start.append("\t\t<textarea id=\"content\" name=\"content\" style=\"width:100%;height:100%\"></textarea>\n");
      start.append("\t  </div>\n");
      start.append("\t  <script type=\"text/javascript\">\n");
      start.append("\t\t\tvar sBasePath=\"" + this.basePath + "\";\n");
      start.append("\t\t\tvar v3x = new V3X();\n");
      start.append("\t\t\tv3x.loadScriptFile(sBasePath+\"zh-cn.js\");\n");
      start.append("      \tvar oFCKeditor = new FCKeditor(\"" + this.container + "\");\n");
      start.append("      \toFCKeditor.BasePath = sBasePath;\n");
      start.append("      \toFCKeditor.Config[\"DefaultLanguage\"]=\"zh-cn\";\n");
      start.append("      \toFCKeditor.ToolbarSet=\"" + this.toolBar + "\";\n");
      start.append("      \toFCKeditor.Config[\"ImageUploadURL\"]=\"" + this.uploadImage + "\";\n");
      start.append("      \toFCKeditor.Config[\"FlashUploadURL\"]=\"" + this.uploadFlash + "\";\n");
      start.append("      \toFCKeditor.Config[\"ImageUploadMaxFileSize\"]=\"" + this.uploadLimitImgSize + "\";\n");
      start.append("     \t \toFCKeditor.ReplaceTextarea();\n");
      this.pageContext.getOut().write(start.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return 1;
  }
  
  public int doEndTag() throws JspException {
    try {
      StringBuffer end = new StringBuffer();
      end.append("\t</script>\n");
      end.append("  </td>\n");
      end.append(" </tr>\n");
      end.append(" </table>\n");
      this.pageContext.getOut().write(end.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return 6;
  }
  
  public int doAfterBody() throws JspException {
    return 1;
  }
  
  public String getBasePath() {
    return this.basePath;
  }
  
  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }
  
  public String getContainer() {
    return this.container;
  }
  
  public void setContainer(String container) {
    this.container = container;
  }
  
  public String getToolBar() {
    return this.toolBar;
  }
  
  public void setToolBar(String toolBar) {
    this.toolBar = toolBar;
  }
  
  public String getUploadImage() {
    return this.uploadImage;
  }
  
  public void setUploadImage(String uploadImage) {
    this.uploadImage = uploadImage;
  }
  
  public String getUploadFlash() {
    return this.uploadFlash;
  }
  
  public void setUploadFlash(String uploadFlash) {
    this.uploadFlash = uploadFlash;
  }
  
  public String getUploadLimitImgSize() {
    return this.uploadLimitImgSize;
  }
  
  public void setUploadLimitImgSize(String uploadLimitImgSize) {
    this.uploadLimitImgSize = uploadLimitImgSize;
  }
}
