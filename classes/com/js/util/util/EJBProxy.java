package com.js.util.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EJBProxy {
  Object classInstance = null;
  
  public EJBProxy() {}
  
  public EJBProxy(String projectName, String remoteJndiName, String localJndiName, Class remoteHomeInterface) throws Exception {
    init(projectName, remoteJndiName, localJndiName, remoteHomeInterface);
  }
  
  public EJBProxy(String remoteJndiName, String localJndiName, Class remoteHomeInterface) throws Exception {
    init("", remoteJndiName, localJndiName, remoteHomeInterface);
  }
  
  public void init(String projectName, String remoteJndiName, String localJndiName, Class remoteHomeInterface) throws Exception {
    try {
      String classPath = remoteHomeInterface.getName();
      classPath = classPath.substring(0, classPath.lastIndexOf("."));
      classPath = String.valueOf(classPath) + "." + remoteJndiName + "Bean";
      this.classInstance = Class.forName(classPath).newInstance();
    } catch (Exception ex) {
      System.out.println("EJBProxy ex:" + ex);
      throw ex;
    } 
  }
  
  public void init(String projectName, String remoteJndiName, Class remoteHomeInterface) throws Exception {
    try {
      String classPath = remoteHomeInterface.getName();
      classPath = classPath.substring(0, classPath.lastIndexOf("."));
      classPath = String.valueOf(classPath) + "." + remoteJndiName + "Bean";
      this.classInstance = Class.forName(classPath).newInstance();
    } catch (Exception ex) {
      System.out.println("EJBProxy ex:" + ex);
      throw ex;
    } 
  }
  
  public Object invoke(String methodName, Object[] parameters) throws Exception {
    Object result = null;
    try {
      Class[] parameterClasses = (Class[])null;
      if (parameters != null) {
        parameterClasses = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++)
          parameterClasses[i] = parameters[i].getClass(); 
      } 
      Method method = this.classInstance.getClass().getMethod(methodName, parameterClasses);
      result = method.invoke(this.classInstance, parameters);
    } catch (NoSuchMethodException e) {
      System.out.println("Can not find \"" + methodName + "\" method in EJB:" + e.getMessage());
      e.printStackTrace();
      throw e;
    } catch (IllegalAccessException e) {
      System.out.println("Can not invoke \"" + methodName + "\" method in EJB:" + e.getMessage());
      e.printStackTrace();
      throw e;
    } catch (InvocationTargetException e) {
      System.out.println("Can not invoke \"" + methodName + "\" method in EJB:" + e.getMessage());
      e.printStackTrace();
      System.out.println(e.getTargetException().getMessage());
      throw e;
    } 
    return result;
  }
  
  public Object invoke(String methodName, Object[][] parameters) throws Exception {
    Object result = null;
    try {
      Class[] parameterClasses = (Class[])null;
      Object[] parameterValues = (Object[])null;
      if (parameters != null) {
        parameterClasses = new Class[parameters.length];
        parameterValues = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
          parameterClasses[i] = (Class)parameters[i][1];
          parameterValues[i] = parameters[i][0];
        } 
      } 
      Method method = this.classInstance.getClass().getMethod(methodName, parameterClasses);
      result = method.invoke(this.classInstance, parameterValues);
    } catch (NoSuchMethodException e) {
      System.out.println("Can not find \"" + methodName + "\" method in EJB:" + e.getMessage());
      e.printStackTrace();
      throw e;
    } catch (IllegalAccessException e) {
      System.out.println("Can not invoke \"" + methodName + "\" method in EJB:" + e.getMessage());
      e.printStackTrace();
      throw e;
    } catch (InvocationTargetException e) {
      System.out.println("Can not invoke \"" + methodName + "\" method in EJB:" + e.getMessage());
      e.printStackTrace();
      System.out.println(e.getTargetException().getMessage());
      throw e;
    } catch (Exception e) {
      throw e;
    } 
    return result;
  }
  
  public void close() {}
}
