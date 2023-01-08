package com.js.util.util.eval;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtil {
  private static ExpressionUtil util = null;
  
  private IOperator operator;
  
  public static ExpressionUtil getInstance() {
    if (util == null)
      util = new ExpressionUtil(); 
    util.setOperator(new DefaultOperator());
    return util;
  }
  
  public String eval(String exp) throws Exception {
    String re = null;
    if (exp == null || "".equals(exp))
      return re; 
    String src = init(exp);
    while (src.indexOf(')') > 0) {
      int end = src.indexOf(')');
      int begin = src.substring(0, end).lastIndexOf('(');
      String noBracketStr = src.substring(begin + 1, end);
      src = String.valueOf(src.substring(0, begin)) + evalNoBracket(noBracketStr) + src.substring(end + 1);
    } 
    re = evalNoBracket(src);
    return re;
  }
  
  private String init(String exp) throws Exception {
    String re = null;
    int count = 0;
    List<String> lefts = new LinkedList();
    for (int index = 0; index < exp.length(); index++) {
      if (exp.charAt(index) == '(')
        lefts.add((new Integer(index)).toString()); 
      if (exp.charAt(index) == ')') {
        String suitMAN = lefts.get(lefts.size() - 1);
        int indexMAN = Integer.parseInt(suitMAN);
        lefts.remove(lefts.size() - 1);
        if (exp.length() - index <= 3 && indexMAN < 3)
          count++; 
      } 
    } 
    re = exp.substring(count, exp.length() - count);
    re = convertPercent(re);
    return re;
  }
  
  private String convertPercent(String src) throws Exception {
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    Pattern percentPattern = Pattern.compile("\\d{1,}[\\.]?\\d{0,}%");
    Matcher m = percentPattern.matcher(src);
    StringBuffer re = new StringBuffer();
    while (m.find()) {
      try {
        Number n = percentFormat.parse(m.group());
        m.appendReplacement(re, String.valueOf(n.doubleValue()));
      } catch (Exception e) {
        throw e;
      } 
    } 
    m.appendTail(re);
    return re.toString();
  }
  
  private Object[] divide(String exp) {
    List<String> els = new ArrayList();
    String operand = "";
    String calMethod = "";
    for (int i = 0; i < exp.length(); i++) {
      char c = exp.charAt(i);
      if (isAsciiPunctuation(c)) {
        calMethod = String.valueOf(calMethod) + c;
        if (operand != "") {
          els.add(operand);
          operand = "";
        } 
      } else {
        operand = String.valueOf(operand) + c;
        if (calMethod != "") {
          els.add(calMethod);
          calMethod = "";
        } 
      } 
    } 
    if (operand != "") {
      els.add(operand);
      operand = "";
    } 
    return els.toArray();
  }
  
  private String evalNoBracket(String exp) {
    String re = null;
    if (exp == null || "".equals(exp))
      return re; 
    List<String> suffixExpElements = new ArrayList();
    Stack<String> calMethods = new Stack();
    Object[] els = divide(exp);
    if (els.length == 1)
      return (String)els[0]; 
    for (int i = 0; i < els.length; i++) {
      String el = (String)els[i];
      if (this.operator.valiIsCalMethod(el)) {
        String preCalMethod = calMethods.isEmpty() ? null : calMethods.peek();
        if (preCalMethod != null && compareCalMethodPri(preCalMethod, el) >= 0) {
          suffixExpElements.add(preCalMethod);
          calMethods.pop();
        } 
        calMethods.push(el);
      } else {
        suffixExpElements.add(el);
      } 
    } 
    while (!calMethods.isEmpty())
      suffixExpElements.add(calMethods.pop()); 
    re = calBySuffixExpElements(suffixExpElements);
    return re;
  }
  
  private boolean isAsciiPunctuation(char c) {
    return !((c < ' ' || c > '/' || c == '.') && (c < ':' || c > '@') && (c < '[' || c > '`') && (c < '{' || c > ''));
  }
  
  private String calBySuffixExpElements(List suffixExpElements) {
    String re = null;
    Stack<String> calSta = new Stack();
    for (Iterator<String> iter = suffixExpElements.iterator(); iter.hasNext(); ) {
      String el = iter.next();
      if (this.operator.valiIsCalMethod(el)) {
        String op2 = calSta.pop();
        String op1 = calSta.pop();
        calSta.push(this.operator.calAtom(op1, op2, el));
        continue;
      } 
      calSta.push(el);
    } 
    re = calSta.pop();
    return re;
  }
  
  private int compareCalMethodPri(String s1, String s2) {
    return this.operator.getCalMethodPri(s1) - this.operator.getCalMethodPri(s2);
  }
  
  public IOperator getOperator() {
    return this.operator;
  }
  
  public void setOperator(IOperator operator) {
    this.operator = operator;
  }
  
  public static void main(String[] args) throws Exception {
    ExpressionUtil u = getInstance();
    System.out.println("t1:" + u.eval("丁力==丁力"));
    System.out.println("t3:" + u.eval("(77>=77)&&((哎==哎)||(33==8))"));
  }
}
