package rtx.rtxsms.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class AudoiFileFilter extends FileFilter {
  String mid;
  
  String midi;
  
  String amr;
  
  public AudoiFileFilter() {}
  
  public AudoiFileFilter(String ext) {
    String[] temp = ext.split(";");
    this.mid = temp[0];
    this.midi = temp[1];
    this.amr = temp[2];
  }
  
  public boolean accept(File file) {
    if (file.isDirectory())
      return true; 
    String fileName = file.getName();
    int index = fileName.lastIndexOf('.');
    if (index > 0 && index < fileName.length() - 1) {
      String extension = fileName.substring(index + 1).toLowerCase();
      if (extension.equals(this.mid) || extension.equals(this.midi) || extension.equals(this.amr))
        return true; 
    } 
    return false;
  }
  
  public String getDescription() {
    if (this.mid.equals("mid") || this.midi.equals("midi") || this.amr.equals("amr"))
      return "文件(*mid;*.midi;*.amr)"; 
    return "";
  }
}
