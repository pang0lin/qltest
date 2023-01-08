package com.js.oa.eform.weixin;

import javax.servlet.http.HttpServletRequest;

public interface ElementHTMLGetter {
  String getHTML(String paramString1, String paramString2, String paramString3, String paramString4, HttpServletRequest paramHttpServletRequest, String paramString5, String[][] paramArrayOfString, int paramInt, String paramString6, boolean paramBoolean);
  
  String getEditHTML(String paramString1, String paramString2, String[][] paramArrayOfString, String paramString3, String paramString4, boolean paramBoolean, int paramInt, String paramString5, String paramString6, HttpServletRequest paramHttpServletRequest, String paramString7, String[] paramArrayOfString1, String paramString8, String paramString9, String paramString10);
  
  String getForeignEditHTMLForWeiXin(HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2, String[][] paramArrayOfString, String paramString3, int paramInt1, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, boolean paramBoolean1, String paramString12, boolean paramBoolean2, int paramInt2, String paramString13, String[] paramArrayOfString1);
}
