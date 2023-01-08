package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChangeNumberToUP {
  private static final String[] num = new String[] { "零", "壹", "贰", "叁", "肆", "伍", 
      
      "陆", "柒", "捌", "玖" };
  
  private static final String[] bit = new String[] { 
      "圆", "拾", "佰", "仟", "万", "拾", 
      
      "佰", "仟", "亿", "拾", 
      "佰", "仟", 
      
      "万", "拾", "佰", "仟", "亿" };
  
  private static final String[] jf = new String[] { "角", "分" };
  
  public static String praseUpcaseRMB(String integer) throws Exception {
    StringBuilder sbdr = new StringBuilder("");
    int j = integer.length();
    if (j > bit.length)
      throw new Exception("\n只能处理亿万亿以内的数据(含亿万亿)!"); 
    char[] rmb = integer.toCharArray();
    for (int i = 0; i < rmb.length; i++) {
      int numLocate = Integer.parseInt(rmb[i]);
      int bitLocate = j - 1 - i;
      if (numLocate == 0) {
        if (!sbdr.toString().endsWith(num[0]))
          sbdr.append(num[numLocate]); 
      } else {
        if (bit[bitLocate].equals("仟")) {
          String s = sbdr.toString();
          if (!s.endsWith(bit[bitLocate + 1]) && s.length() > 0) {
            if (s.endsWith(num[0]))
              sbdr.deleteCharAt(sbdr.length() - 1); 
            sbdr.append(bit[bitLocate + 1]);
          } 
        } 
        sbdr.append(num[numLocate]);
        sbdr.append(bit[bitLocate]);
      } 
    } 
    if (sbdr.toString().endsWith(num[0])) {
      sbdr.deleteCharAt(sbdr.length() - 1);
      sbdr.append("圆整");
    } else {
      sbdr.append("整");
    } 
    return sbdr.toString();
  }
  
  public static String praseUpcaseRMB(String integer, String decimal) throws Exception {
    String ret = praseUpcaseRMB(integer);
    ret = ret.split("整")[0];
    StringBuilder sbdr = new StringBuilder("");
    sbdr.append(ret);
    char[] rmbjf = decimal.toCharArray();
    for (int i = 0; i < rmbjf.length; i++) {
      int locate = Integer.parseInt(rmbjf[i]);
      if (locate == 0) {
        if (!sbdr.toString().endsWith(num[0]))
          sbdr.append(num[locate]); 
      } else {
        sbdr.append(num[locate]);
        sbdr.append(jf[i]);
      } 
    } 
    return sbdr.toString();
  }
  
  public static String doChangeRMB(String doubleStr) throws Exception {
    String result = null;
    System.out.println(doubleStr.substring(doubleStr.indexOf("."), doubleStr.length()));
    if (doubleStr.contains(".") && !doubleStr.substring(doubleStr.indexOf("."), doubleStr.length()).equals(".0")) {
      int dotloc = doubleStr.indexOf(".");
      int strlen = doubleStr.length();
      String integer = doubleStr.substring(0, dotloc);
      String decimal = doubleStr.substring(dotloc + 1, strlen);
      result = praseUpcaseRMB(integer, decimal);
    } else {
      String integer = doubleStr.replace(".0", "");
      result = praseUpcaseRMB(integer);
    } 
    return result;
  }
  
  public static String doChangeRMB(double rmbDouble) throws Exception {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: dload_0
    //   3: invokestatic rint : (D)D
    //   6: dstore_3
    //   7: dload_3
    //   8: dload_0
    //   9: dcmpl
    //   10: ifle -> 17
    //   13: dload_3
    //   14: dconst_1
    //   15: dsub
    //   16: dstore_3
    //   17: dload_0
    //   18: dload_3
    //   19: dsub
    //   20: dstore #5
    //   22: new java/lang/Long
    //   25: dup
    //   26: dload_3
    //   27: d2l
    //   28: invokespecial <init> : (J)V
    //   31: invokevirtual toString : ()Ljava/lang/String;
    //   34: astore #7
    //   36: new java/lang/StringBuilder
    //   39: dup
    //   40: invokespecial <init> : ()V
    //   43: dload #5
    //   45: ldc2_w 100.0
    //   48: dmul
    //   49: invokestatic round : (D)J
    //   52: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   55: invokevirtual toString : ()Ljava/lang/String;
    //   58: astore #8
    //   60: aload #8
    //   62: ldc '0'
    //   64: invokevirtual equals : (Ljava/lang/Object;)Z
    //   67: ifeq -> 79
    //   70: aload #7
    //   72: invokestatic praseUpcaseRMB : (Ljava/lang/String;)Ljava/lang/String;
    //   75: astore_2
    //   76: goto -> 87
    //   79: aload #7
    //   81: aload #8
    //   83: invokestatic praseUpcaseRMB : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   86: astore_2
    //   87: aload_2
    //   88: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #294	-> 0
    //   #296	-> 2
    //   #298	-> 7
    //   #300	-> 13
    //   #304	-> 17
    //   #308	-> 22
    //   #310	-> 36
    //   #312	-> 60
    //   #314	-> 70
    //   #318	-> 79
    //   #322	-> 87
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	89	0	rmbDouble	D
    //   2	87	2	result	Ljava/lang/String;
    //   7	82	3	theInt	D
    //   22	67	5	theDecimal	D
    //   36	53	7	integer	Ljava/lang/String;
    //   60	29	8	decimal	Ljava/lang/String;
  }
  
  public static void main(String[] args) throws Exception {
    System.out.print("输入小写人民币金额:");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String in = br.readLine();
    String result = doChangeRMB(in);
    System.out.println("\n------------转换结果------------");
    System.out.println(result);
  }
}
