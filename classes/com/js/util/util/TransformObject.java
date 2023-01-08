package com.js.util.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class TransformObject {
  private static TransformObject ourInstance;
  
  Logger logger = (Logger)Logger.getInstance(TransformObject.class.getName());
  
  public static synchronized TransformObject getInstance() {
    if (ourInstance == null)
      ourInstance = new TransformObject(); 
    return ourInstance;
  }
  
  public Object transformObject(Object o, Class<?> aClass) {
    Object valueObject = null;
    try {
      valueObject = aClass.newInstance();
      HashMap<Object, Object> hm = new HashMap<Object, Object>();
      PropertyDescriptor[] pd = 
        
        Introspector.getBeanInfo(o.getClass(), Object.class)
        .getPropertyDescriptors();
      for (int i = 0; i < pd.length; i++)
        hm.put(pd[i].getName(), pd[i].getReadMethod()); 
      PropertyDescriptor[] vopd = Introspector.getBeanInfo(aClass, 
          Object.class).getPropertyDescriptors();
      for (int j = 0; j < vopd.length; j++) {
        Method oMethod = (Method)hm.get(vopd[j].getName());
        if (oMethod != null) {
          Object[] parameter = new Object[1];
          parameter[0] = oMethod.invoke(o, null);
          vopd[j].getWriteMethod().invoke(valueObject, 
              parameter);
        } 
      } 
    } catch (Exception ex) {
      this.logger.error(
          "Transform error:" + 
          ex.getMessage());
    } 
    return valueObject;
  }
  
  public void transformDiffValue(Object srcObject, Object desObject) {
    HashMap<Object, Object> hm = new HashMap<Object, Object>();
    PropertyDescriptor[] srcPD = (PropertyDescriptor[])null;
    try {
      srcPD = 
        
        Introspector.getBeanInfo(srcObject.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException ex) {
      System.out.println("get Source Bean Information Exception: " + 
          ex.getMessage());
    } 
    for (int i = 0; i < srcPD.length; i++)
      hm.put(srcPD[i].getName(), srcPD[i].getReadMethod()); 
    PropertyDescriptor[] desPD = (PropertyDescriptor[])null;
    try {
      desPD = Introspector.getBeanInfo(desObject.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException ex) {
      System.out.println("get destination bean Information Exception: " + 
          ex.getMessage());
    } 
    try {
      for (int j = 0; j < desPD.length; j++) {
        Method method = (Method)hm.get(desPD[j].getName());
        if (method != null) {
          Object[] o = new Object[1];
          o[0] = method.invoke(srcObject, null);
          Object[] o1 = new Object[1];
          o1[0] = method.invoke(desObject, null);
          if (o[0] != null && !o[0].equals(o1[0]))
            desPD[j].getWriteMethod().invoke(desObject, o); 
        } 
      } 
    } catch (InvocationTargetException ex) {
      System.out.println("Invocation Target Exception: " + ex.getMessage());
    } catch (IllegalArgumentException ex) {
      System.out.println("Illegal Argument Exception: " + ex.getMessage());
    } catch (IllegalAccessException ex) {
      System.out.println("Illegal Access Exception: " + ex.getMessage());
    } 
  }
  
  public void transformValue(Object srcObject, Object desObject) {
    HashMap<Object, Object> hm = new HashMap<Object, Object>();
    PropertyDescriptor[] srcPD = (PropertyDescriptor[])null;
    try {
      srcPD = 
        
        Introspector.getBeanInfo(srcObject.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException ex) {
      System.out.println("get Source Bean Information Exception: " + 
          ex.getMessage());
    } 
    for (int i = 0; i < srcPD.length; i++)
      hm.put(srcPD[i].getName(), srcPD[i].getReadMethod()); 
    PropertyDescriptor[] desPD = (PropertyDescriptor[])null;
    try {
      desPD = Introspector.getBeanInfo(desObject.getClass(), Object.class)
        .getPropertyDescriptors();
    } catch (IntrospectionException ex) {
      System.out.println("get destination bean Information Exception: " + 
          ex.getMessage());
    } 
    try {
      for (int j = 0; j < desPD.length; j++) {
        Method method = (Method)hm.get(desPD[j].getName());
        if (method != null) {
          Object[] o = new Object[1];
          o[0] = method.invoke(srcObject, null);
          desPD[j].getWriteMethod().invoke(desObject, o);
        } 
      } 
    } catch (InvocationTargetException ex) {
      System.out.println("Invocation Target Exception: " + ex.getMessage());
    } catch (IllegalArgumentException ex) {
      System.out.println("Illegal Argument Exception: " + ex.getMessage());
    } catch (IllegalAccessException ex) {
      System.out.println("Illegal Access Exception: " + ex.getMessage());
    } 
  }
}
