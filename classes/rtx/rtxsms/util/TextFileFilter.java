package rtx.rtxsms.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class TextFileFilter extends FileFilter {
  String txt;
  
  String smil;
  
  public TextFileFilter() {}
  
  public TextFileFilter(String ext) {
    String[] temp = ext.split(";");
    this.txt = temp[0];
    this.smil = temp[1];
  }
  
  public boolean accept(File file) {
    if (file.isDirectory())
      return true; 
    String fileName = file.getName();
    int index = fileName.lastIndexOf('.');
    if (index > 0 && index < fileName.length() - 1) {
      String extension = fileName.substring(index + 1).toLowerCase();
      if (extension.equals(this.txt) || extension.equals(this.smil))
        return true; 
    } 
    return false;
  }
  
  public String getDescription() {
    if (this.txt.equals("txt") || this.smil.equals("smil"))
      return "文件(*txt;*.smil)"; 
    return "";
  }
}
