package com.js.util.util;

import java.util.Date;

public class ParameterGenerator {
  private Object[][] parameters = null;
  
  int counter = 0;
  
  public ParameterGenerator(int length) {
    init(length);
  }
  
  private void init(int length) {
    this.parameters = new Object[length][2];
  }
  
  public int put(Object parameterValue, Class parameterType) {
    this.parameters[this.counter][0] = parameterValue;
    this.parameters[this.counter][1] = parameterType;
    this.counter++;
    return this.counter - 1;
  }
  
  public int put(Object parameterValue, String strParameterType) {
    Class<Date> clazz;
    Class<Integer> parameterType = null;
    if (strParameterType != null && strParameterType.equals("Integer")) {
      parameterType = Integer.class;
    } else if (strParameterType != null && strParameterType.equals("Float")) {
      Class<Float> clazz1 = Float.class;
    } else if (strParameterType != null && strParameterType.equals("Long")) {
      Class<Long> clazz1 = Long.class;
    } else if (strParameterType != null && strParameterType.equals("Double")) {
      Class<Double> clazz1 = Double.class;
    } else if (strParameterType != null && strParameterType.equals("Boolean")) {
      Class<Boolean> clazz1 = Boolean.class;
    } else if (strParameterType != null && strParameterType.equals("Byte")) {
      Class<Byte> clazz1 = Byte.class;
    } else if (strParameterType != null && strParameterType.equals("Character")) {
      Class<Character> clazz1 = Character.class;
    } else if (strParameterType != null && strParameterType.equals("Short")) {
      Class<Short> clazz1 = Short.class;
    } else if (strParameterType != null && strParameterType.equals("String")) {
      Class<String> clazz1 = String.class;
    } else if (strParameterType != null && strParameterType.equals("String[]")) {
      Class<String[]> clazz1 = String[].class;
    } else if (strParameterType != null && strParameterType.equals("Class")) {
      Class<Class> clazz1 = Class.class;
    } else if (strParameterType != null && strParameterType.equals("Date")) {
      clazz = Date.class;
    } else {
      return -1;
    } 
    this.parameters[this.counter][0] = parameterValue;
    this.parameters[this.counter][1] = clazz;
    this.counter++;
    return this.counter - 1;
  }
  
  public Object[][] getParameters() {
    return this.parameters;
  }
}
