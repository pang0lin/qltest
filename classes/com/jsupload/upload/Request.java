package com.jsupload.upload;

import java.util.Enumeration;
import java.util.Hashtable;

public class Request {
  private Hashtable m_parameters = new Hashtable<Object, Object>();
  
  private int m_counter = 0;
  
  protected void putParameter(String name, String value) {
    if (name == null)
      throw new IllegalArgumentException("The name of an element cannot be null."); 
    if (this.m_parameters.containsKey(name)) {
      Hashtable<Integer, String> values = (Hashtable)this.m_parameters.get(name);
      values.put(new Integer(values.size()), value);
    } else {
      Hashtable<Object, Object> values = new Hashtable<Object, Object>();
      values.put(new Integer(0), value);
      this.m_parameters.put(name, values);
      this.m_counter++;
    } 
  }
  
  public String getParameter(String name) {
    if (name == null)
      throw new IllegalArgumentException("Form's name is invalid or does not exist (1305)."); 
    Hashtable values = (Hashtable)this.m_parameters.get(name);
    if (values == null)
      return null; 
    return (String)values.get(new Integer(0));
  }
  
  public Enumeration getParameterNames() {
    return this.m_parameters.keys();
  }
  
  public String[] getParameterValues(String name) {
    if (name == null)
      throw new IllegalArgumentException("Form's name is invalid or does not exist (1305)."); 
    Hashtable values = (Hashtable)this.m_parameters.get(name);
    if (values == null)
      return null; 
    String[] strValues = new String[values.size()];
    for (int i = 0; i < values.size(); i++)
      strValues[i] = (String)values.get(new Integer(i)); 
    return strValues;
  }
}
