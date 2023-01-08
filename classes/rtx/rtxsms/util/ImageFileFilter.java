package rtx.rtxsms.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter {
  String jpg;
  
  String gif;
  
  public ImageFileFilter() {}
  
  public ImageFileFilter(String ext) {
    String[] temp = ext.split(";");
    this.jpg = temp[0];
    this.gif = temp[1];
  }
  
  public boolean accept(File file) {
    if (file.isDirectory())
      return true; 
    String fileName = file.getName();
    int index = fileName.lastIndexOf('.');
    if (index > 0 && index < fileName.length() - 1) {
      String extension = fileName.substring(index + 1).toLowerCase();
      if (extension.equals(this.jpg) || extension.equals(this.gif))
        return true; 
    } 
    return false;
  }
  
  public String getDescription() {
    if (this.jpg.equals("jpg") || this.gif.equals("gif"))
      return "文件(*jpg;*.gif)"; 
    return "";
  }
}
