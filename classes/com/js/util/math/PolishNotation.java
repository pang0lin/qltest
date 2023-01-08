package com.js.util.math;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PolishNotation {
  public List<String> parseExpression(String expression) {
    StringTokenizer st = new StringTokenizer(expression, "+-*/()", true);
    List<String> temp = new ArrayList<String>();
    while (st.hasMoreElements())
      temp.add((String)st.nextElement()); 
    List<String> polisthNotationlist = new ArrayList<String>();
    Stack<String> stack = new Stack<String>();
    for (String temp_ch : temp) {
      if (isOperator(temp_ch)) {
        if (stack.getIndex() == -1 || "(".equals(temp_ch)) {
          stack.push(temp_ch);
          continue;
        } 
        if (")".equals(temp_ch)) {
          while (!((String)stack.getTop().getContent()).equals("(")) {
            String operator = stack.getTop().getContent();
            polisthNotationlist.add(operator);
            stack.pop();
          } 
          stack.pop();
          continue;
        } 
        if (((String)stack.getTop().getContent()).equals("(")) {
          stack.push(temp_ch);
          continue;
        } 
        while (stack.getIndex() != -1 && !((String)stack.getTop().getContent()).equals("(")) {
          if (isPriority(operatorPriority(stack.getTop().getContent()), operatorPriority(temp_ch))) {
            String operator = stack.getTop().getContent();
            polisthNotationlist.add(operator);
            stack.pop();
            if (stack.getIndex() == -1 || !isPriority(operatorPriority(stack.getTop().getContent()), operatorPriority(temp_ch))) {
              stack.push(temp_ch);
              break;
            } 
            continue;
          } 
          stack.push(temp_ch);
          break;
        } 
        continue;
      } 
      polisthNotationlist.add(temp_ch);
    } 
    while (stack.getIndex() != -1)
      polisthNotationlist.add(stack.pop().getContent()); 
    return polisthNotationlist;
  }
  
  public double calculatePolishNotation(List<String> polishNotation) {
    Stack<String> stack = new Stack<String>();
    for (String str : polishNotation) {
      if (!isOperator(str)) {
        stack.push(str);
        continue;
      } 
      String number1 = stack.pop().getContent();
      String number2 = stack.pop().getContent();
      stack.push(String.valueOf(calculate(str, number2, number1)));
    } 
    return Double.parseDouble(stack.pop().getContent());
  }
  
  public double calculate(String operator, String number1, String number2) {
    char opt = operator.charAt(0);
    double double1 = Double.parseDouble(number1);
    double double2 = Double.parseDouble(number2);
    switch (opt) {
      case '+':
        return double1 + double2;
      case '-':
        return double1 - double2;
      case '*':
        return double1 * double2;
      case '/':
        return double1 / double2;
    } 
    return 0.0D;
  }
  
  public int operatorPriority(String operator) {
    if ("(".equals(operator))
      return 1; 
    if (")".equals(operator))
      return 2; 
    if ("+".equals(operator) || "-".equals(operator))
      return 3; 
    if ("*".equals(operator) || "/".equals(operator))
      return 4; 
    return 0;
  }
  
  public boolean isPriority(int topOperator, int listOperator) {
    if (topOperator >= listOperator)
      return true; 
    return false;
  }
  
  public boolean isOperator(String arg) {
    if ("+-*/()".indexOf(arg) != -1)
      return true; 
    return false;
  }
  
  public String calculate(String expression) {
    return String.valueOf(calculatePolishNotation(parseExpression(expression)));
  }
  
  public static void main(String[] args) {
    PolishNotation pn = new PolishNotation();
    System.out.print(pn.calculate("1/01"));
  }
}
