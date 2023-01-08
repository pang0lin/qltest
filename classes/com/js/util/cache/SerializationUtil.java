package com.js.util.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SerializationUtil {
  public static final byte[] serialize(Serializable obj) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    serialize(obj, baos);
    byte[] b = baos.toByteArray();
    baos.close();
    return b;
  }
  
  public static final void serialize(Serializable obj, OutputStream output) throws Exception {
    ObjectOutputStream oos = new ObjectOutputStream(output);
    oos.writeObject(obj);
    oos.close();
  }
  
  public static final Serializable deserialize(byte[] b) throws Exception {
    ByteArrayInputStream bais = new ByteArrayInputStream(b);
    Serializable obj = deserialize(bais);
    bais.close();
    return obj;
  }
  
  public static final Serializable deserialize(InputStream input) throws Exception {
    ObjectInputStream ois = new ObjectInputStream(input);
    Serializable obj = (Serializable)ois.readObject();
    ois.close();
    return obj;
  }
}
