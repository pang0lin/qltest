package cn.zzy.action;

public class AttachmentRequest {
  private long id;
  
  private long size;
  
  private String path;
  
  public String getPath() {
    return this.path;
  }
  
  public void setPath(String path) {
    this.path = path;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getSize() {
    return this.size;
  }
  
  public void setSize(long size) {
    this.size = size;
  }
}
