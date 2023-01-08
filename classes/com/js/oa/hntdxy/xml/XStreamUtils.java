package com.js.oa.hntdxy.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.collections.PropertiesConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public final class XStreamUtils {
  private static final XStream xstream = new XStream((HierarchicalStreamDriver)new DomDriver());
  
  static {
    String[] formats = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S" };
    xstream.registerConverter((SingleValueConverter)new DateConverter("yyyy-MM-dd", formats));
    xstream.registerConverter((Converter)new PropertiesConverter());
  }
  
  public static String toXML(Object object, String name) {
    xstream.alias(name, object.getClass());
    return xstream.toXML(object);
  }
  
  public static Object fromXML(String xml) {
    return xstream.fromXML(xml);
  }
  
  public static Object fromXML(InputStream is) {
    return xstream.fromXML(is);
  }
  
  public static InputStream toByteArrayStream(String string) {
    InputStream is = null;
    try {
      is = new ByteArrayInputStream(string.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException unsupportedEncodingException) {}
    return is;
  }
  
  public static String getHeader() {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
  }
}
