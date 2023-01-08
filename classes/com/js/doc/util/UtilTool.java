package com.js.doc.util;

import java.util.Vector;

public class UtilTool {
  public static String getDocumentNumber(String id, int bitCount) {
    return "";
  }
  
  public static String[] getNoSpaceArr(String[] arr) {
    Vector<String> vec = new Vector();
    for (int i = 0; i < arr.length; i++) {
      if (!"".equals(arr[i]))
        vec.add(arr[i]); 
    } 
    String[] retArr = new String[vec.size()];
    for (int j = 0; j < vec.size(); j++)
      retArr[j] = vec.get(j); 
    return retArr;
  }
  
  public static String[] getUserArr(String userIds) {
    String[] retArr = { "" };
    if (userIds != null && !"".equals(userIds) && !"null".equals(userIds))
      if (userIds.indexOf("$") == -1) {
        retArr = new String[1];
        retArr[0] = userIds;
      } else {
        String[] tmp = ("$" + userIds + "$").split("\\$\\$");
        retArr = getNoSpaceArr(tmp);
      }  
    return retArr;
  }
}
