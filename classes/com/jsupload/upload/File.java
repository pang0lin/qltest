package com.jsupload.upload;

public class File {
  private SmartUpload m_parent;
  
  private int m_startData = 0;
  
  private int m_endData = 0;
  
  private int m_size = 0;
  
  private String m_fieldname = new String();
  
  private String m_filename = new String();
  
  private String m_fileExt = new String();
  
  private String m_filePathName = new String();
  
  private String m_contentType = new String();
  
  private String m_contentDisp = new String();
  
  private String m_typeMime = new String();
  
  private String m_subTypeMime = new String();
  
  private String m_contentString = new String();
  
  private boolean m_isMissing = true;
  
  public static final int SAVEAS_AUTO = 0;
  
  public static final int SAVEAS_VIRTUAL = 1;
  
  public static final int SAVEAS_PHYSICAL = 2;
  
  public int getSize() {
    return this.m_size;
  }
}
