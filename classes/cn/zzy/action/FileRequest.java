package cn.zzy.action;

public class FileRequest {
  private long id;
  
  private String name;
  
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
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public long getSize() {
    return this.size;
  }
  
  public void setSize(long size) {
    this.size = size;
  }
}
