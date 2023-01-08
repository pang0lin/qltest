package rtx.rtxsms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
  public static final String replace(String line, String oldString, String newString) {
    if (line == null)
      return null; 
    int i = 0;
    if ((i = line.indexOf(oldString, i)) >= 0) {
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ((i = line.indexOf(oldString, i)) > 0) {
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      } 
      buf.append(line2, j, line2.length - j);
      return buf.toString();
    } 
    return line;
  }
  
  public static final String replace(String line, String oldString, String newString, int[] count) {
    if (line == null)
      return null; 
    int i = 0;
    if ((i = line.indexOf(oldString, i)) >= 0) {
      int counter = 0;
      counter++;
      char[] line2 = line.toCharArray();
      char[] newString2 = newString.toCharArray();
      int oLength = oldString.length();
      StringBuffer buf = new StringBuffer(line2.length);
      buf.append(line2, 0, i).append(newString2);
      i += oLength;
      int j = i;
      while ((i = line.indexOf(oldString, i)) > 0) {
        counter++;
        buf.append(line2, j, i - j).append(newString2);
        i += oLength;
        j = i;
      } 
      buf.append(line2, j, line2.length - j);
      count[0] = counter;
      return buf.toString();
    } 
    return line;
  }
  
  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches())
      return false; 
    if ("".equals(str))
      return false; 
    return true;
  }
  
  public static String getTaskStatue(long statue) {
    if (0L == statue)
      return "0- 正在上传"; 
    if (1L == statue)
      return "1- 待发送"; 
    if (2L == statue)
      return "2- 审核中"; 
    if (3L == statue)
      return "3- 审核失败"; 
    if (4L == statue)
      return "4- 正在发送"; 
    if (5L == statue)
      return "5- 余额不足"; 
    if (6L == statue)
      return "6- 强制停止"; 
    if (7L == statue)
      return "7- 发送完成"; 
    if (8L == statue)
      return "8- 用户审核"; 
    if (9L == statue)
      return "9- 用户审核2"; 
    if (10L == statue)
      return "10- 用户审核失败"; 
    return "其他错误：" + statue;
  }
}
