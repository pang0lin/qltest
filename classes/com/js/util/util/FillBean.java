package com.js.util.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

public class FillBean {
  private static Logger logger = Logger.getLogger(FillBean.class.getName());
  
  public static Object transformOneToOne(ActionForm form, Class<?> valueObjectClass) {
    Object valueObject = null;
    try {
      valueObject = valueObjectClass.newInstance();
      HashMap<Object, Object> formBeanPropertyHashMap = new HashMap<Object, Object>();
      PropertyDescriptor[] formBeanPropertyDescriptor = 
        
        Introspector.getBeanInfo(form.getClass(), ActionForm.class)
        .getPropertyDescriptors();
      for (int i = 0; i < formBeanPropertyDescriptor.length; i++)
        formBeanPropertyHashMap.put(formBeanPropertyDescriptor[i]
            .getName(), 
            formBeanPropertyDescriptor[i]
            .getReadMethod()); 
      PropertyDescriptor[] voPropertyDescriptor = 
        Introspector.getBeanInfo(
          valueObjectClass, Object.class).getPropertyDescriptors();
      for (int j = 0; j < voPropertyDescriptor.length; j++) {
        Method readMethodInFormBean = (Method)formBeanPropertyHashMap.get(
            voPropertyDescriptor[j].getName());
        if (readMethodInFormBean != null) {
          Object[] parameter = new Object[1];
          parameter[0] = readMethodInFormBean.invoke(form, null);
          voPropertyDescriptor[j].getWriteMethod().invoke(valueObject, 
              parameter);
        } 
      } 
      voPropertyDescriptor = (PropertyDescriptor[])null;
      formBeanPropertyDescriptor = (PropertyDescriptor[])null;
      formBeanPropertyHashMap = null;
    } catch (Exception ex) {
      logger.error(
          "Transform data from form bean to value object error:" + 
          ex.getMessage());
    } 
    return valueObject;
  }
  
  public static Object[] transformOneToMany(ActionForm form, Class[] valueObjectClasses) {
    Object[] valueObjects = new Object[valueObjectClasses.length];
    for (int i = 0; i < valueObjectClasses.length; i++)
      valueObjects[i] = transformOneToOne(form, valueObjectClasses[i]); 
    return valueObjects;
  }
  
  public static Object transformOTO(Object form, Class<?> valueObjectClass) {
    Object valueObject = null;
    try {
      valueObject = valueObjectClass.newInstance();
      HashMap<Object, Object> formBeanPropertyHashMap = new HashMap<Object, Object>();
      PropertyDescriptor[] formBeanPropertyDescriptor = 
        
        Introspector.getBeanInfo(form.getClass(), Object.class)
        .getPropertyDescriptors();
      for (int i = 0; i < formBeanPropertyDescriptor.length; i++)
        formBeanPropertyHashMap.put(formBeanPropertyDescriptor[i]
            .getName(), 
            formBeanPropertyDescriptor[i]
            .getReadMethod()); 
      PropertyDescriptor[] voPropertyDescriptor = 
        Introspector.getBeanInfo(
          valueObjectClass, Object.class).getPropertyDescriptors();
      for (int j = 0; j < voPropertyDescriptor.length; j++) {
        Method readMethodInFormBean = (Method)formBeanPropertyHashMap.get(
            voPropertyDescriptor[j].getName());
        if (readMethodInFormBean != null) {
          Object[] parameter = new Object[1];
          parameter[0] = readMethodInFormBean.invoke(form, null);
          voPropertyDescriptor[j].getWriteMethod().invoke(valueObject, 
              parameter);
        } 
      } 
      voPropertyDescriptor = (PropertyDescriptor[])null;
      formBeanPropertyDescriptor = (PropertyDescriptor[])null;
      formBeanPropertyHashMap = null;
    } catch (Exception ex) {
      logger.error(
          "Transform OTO data from form bean to value object error:" + 
          ex.getMessage());
      ex.printStackTrace();
    } 
    return valueObject;
  }
}
