package com.jsupload.upload;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

public class Files {
  private SmartUpload m_parent;
  
  private Hashtable m_files = new Hashtable<Object, Object>();
  
  private int m_counter = 0;
  
  protected void addFile(File newFile) {
    if (newFile == null)
      throw new IllegalArgumentException("newFile cannot be null."); 
    this.m_files.put(new Integer(this.m_counter), newFile);
    this.m_counter++;
  }
  
  public File getFile(int index) {
    if (index < 0)
      throw new IllegalArgumentException("File's index cannot be a negative value (1210)."); 
    File retval = (File)this.m_files.get(new Integer(index));
    if (retval == null)
      throw new IllegalArgumentException("Files' name is invalid or does not exist (1205)."); 
    return retval;
  }
  
  public int getCount() {
    return this.m_counter;
  }
  
  public long getSize() throws IOException {
    long tmp = 0L;
    for (int i = 0; i < this.m_counter; i++)
      tmp += getFile(i).getSize(); 
    return tmp;
  }
  
  public Collection getCollection() {
    return this.m_files.values();
  }
  
  public Enumeration getEnumeration() {
    return this.m_files.elements();
  }
}
