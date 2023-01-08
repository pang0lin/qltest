package com.js.oa.jsflow.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

public class FormReflection {
  public Object execute(String className, String methodName, HttpServletRequest httpServletRequest) {
    Object result = null;
    try {
      Class<?> cls = Class.forName(className);
      Constructor<?> ct = cls.getConstructor(null);
      Class[] arg = new Class[1];
      arg[0] = HttpServletRequest.class;
      Method meth = cls.getMethod(methodName, arg);
      Object retobj = ct.newInstance(null);
      Object[] arglist = new Object[1];
      arglist[0] = httpServletRequest;
      result = meth.invoke(retobj, arglist);
    } catch (Throwable e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Object execute(String className, String methodName, HttpServletRequest httpServletRequest, String[] paras) {
    Object result = null;
    try {
      Class<?> cls = Class.forName(className);
      Constructor<?> ct = cls.getConstructor(null);
      int paraLen = 0;
      if (paras != null)
        paraLen = paras.length; 
      Class[] arg = new Class[paraLen + 1];
      arg[0] = HttpServletRequest.class;
      for (int i = 1; i <= paraLen; i++)
        arg[i] = String.class; 
      Object[] arglist = new Object[paraLen + 1];
      arglist[0] = httpServletRequest;
      for (int j = 1; j <= paraLen; j++)
        arglist[j] = paras[j - 1]; 
      Method meth = cls.getMethod(methodName, arg);
      Object retobj = ct.newInstance(null);
      result = meth.invoke(retobj, arglist);
    } catch (Throwable e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Object execute(String className, String methodName, String[] paras) {
    Object result = null;
    try {
      Class<?> cls = Class.forName(className);
      Constructor<?> ct = cls.getConstructor(null);
      int paraLen = 0;
      if (paras != null)
        paraLen = paras.length; 
      Class[] arg = new Class[paraLen];
      for (int i = 0; i < paraLen; i++)
        arg[i] = String.class; 
      Object[] arglist = new Object[paraLen];
      for (int j = 0; j < paraLen; j++)
        arglist[j] = paras[j]; 
      Method meth = cls.getMethod(methodName, arg);
      Object retobj = ct.newInstance(null);
      result = meth.invoke(retobj, arglist);
    } catch (Throwable e) {
      e.printStackTrace();
    } 
    return result;
  }
}
