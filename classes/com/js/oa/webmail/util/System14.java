package com.js.oa.webmail.util;

import java.util.Vector;

public class System14 {
  private static int IndexOfTag(String aSource, int APos, String[] aTag) {
    int Result = -1;
    for (int i = 0; i < aTag.length; i++) {
      String ff = aSource.substring(APos, APos + aTag[i].length());
      if (ff.equals(aTag[i]))
        return i; 
    } 
    return Result;
  }
  
  public static String ReplaceStr(String aSource, String aTag, String aReplace) {
    return ReplaceStr(aSource, new String[] { aTag }, new String[] { aReplace });
  }
  
  public static String ReplaceStr(String aSource, String[] aTag, String[] aReplace) {
    if (aTag == null)
      throw new StringIndexOutOfBoundsException("鏍囪瀛楃涓蹭负绌猴紒"); 
    if (aReplace == null)
      throw new StringIndexOutOfBoundsException("鐩爣瀛楃涓蹭负绌猴紒"); 
    if (aTag.length != aReplace.length)
      throw new StringIndexOutOfBoundsException("aTag 鍜�aReplace 鐨勫瓧绗︿覆涓暟涓嶅尮閰嶏紒"); 
    StringBuffer Result = new StringBuffer((int)(aSource.length() * 1.5D));
    int fLen = aSource.length();
    int i = 0;
    while (i < fLen) {
      int fIndex = IndexOfTag(aSource, i, aTag);
      if (fIndex >= 0) {
        Result.append(aReplace[fIndex]);
        i += aTag[fIndex].length();
        continue;
      } 
      Result.append(aSource.charAt(i));
      i++;
    } 
    return Result.toString();
  }
  
  public static String[] splitStr(String aSource, String aTag) {
    Vector<String> v = new Vector();
    String[] tag = new String[1];
    tag[0] = aTag;
    int fLen = aSource.length();
    int fCurrent = 0;
    int i = 0;
    while (i < fLen) {
      int fIndex = IndexOfTag(aSource, i, tag);
      if (fIndex >= 0) {
        v.add(aSource.substring(fCurrent, i));
        i += tag[fIndex].length();
        fCurrent = i;
        continue;
      } 
      i++;
    } 
    if (fCurrent != i)
      v.add(aSource.substring(fCurrent, i)); 
    String[] Result = new String[v.size()];
    for (i = 0; i < v.size(); i++)
      Result[i] = v.get(i); 
    return Result;
  }
  
  public static String DelRepeatChar(String strSource, String delStr) {
    String szResult = null;
    String szTemp = String.valueOf(delStr) + delStr;
    do {
    
    } while ((szResult = replace(strSource, szTemp, delStr)).indexOf(szTemp) >= 0);
    return szResult;
  }
  
  public static String replace(String strSource, String strFrom, String strTo) {
    if (strSource == null)
      return null; 
    StringBuffer szDest = new StringBuffer();
    String szSource = strSource;
    int intFromLen = strFrom.length();
    int intPos;
    while ((intPos = szSource.indexOf(strFrom)) != -1) {
      szDest.append(szSource.substring(0, intPos));
      szDest.append(strTo);
      szSource = szSource.substring(intPos + intFromLen);
    } 
    szDest.append(szSource);
    return szDest.toString();
  }
  
  public static String FormatForJs(String szStr) {
    if (szStr == null)
      return "null"; 
    String szJs = szStr;
    szJs = replace(szJs, "\\", "\\\\");
    szJs = replace(szJs, "\r", "\\r");
    szJs = replace(szJs, "\n", "\\n");
    return szJs;
  }
  
  public static void main(String[] args) throws Exception {
    String str = "c:\\programe\\nyyoa\\index\r\n33333";
    System.out.println(FormatForJs(str));
  }
}
