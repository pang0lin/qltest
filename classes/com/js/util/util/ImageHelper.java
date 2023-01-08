package com.js.util.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHelper {
  public static void scale(String srcImageFile, String result, int scale, boolean flag) {
    try {
      BufferedImage src = ImageIO.read(new File(srcImageFile));
      int width = src.getWidth();
      int height = src.getHeight();
      if (flag) {
        width *= scale;
        height *= scale;
      } else {
        width /= scale;
        height /= scale;
      } 
      Image image = src.getScaledInstance(width, height, 1);
      BufferedImage tag = new BufferedImage(width, height, 1);
      Graphics g = tag.getGraphics();
      g.drawImage(image, 0, 0, null);
      g.dispose();
      ImageIO.write(tag, "JPEG", new File(result));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void cut(String srcImageFile, String descDir, int destWidth, int destHeight) {
    try {
      BufferedImage bi = ImageIO.read(new File(srcImageFile));
      int srcWidth = bi.getHeight();
      int srcHeight = bi.getWidth();
      if (srcWidth > destWidth && srcHeight > destHeight) {
        Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
        destWidth = 200;
        destHeight = 150;
        int cols = 0;
        int rows = 0;
        if (srcWidth % destWidth == 0) {
          cols = srcWidth / destWidth;
        } else {
          cols = (int)Math.floor((srcWidth / destWidth)) + 1;
        } 
        if (srcHeight % destHeight == 0) {
          rows = srcHeight / destHeight;
        } else {
          rows = (int)Math.floor((srcHeight / destHeight)) + 1;
        } 
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            ImageFilter cropFilter = new CropImageFilter(j * 200, i * 150, 
                destWidth, destHeight);
            Image img = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(image.getSource(), 
                  cropFilter));
            BufferedImage tag = new BufferedImage(destWidth, 
                destHeight, 1);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(String.valueOf(descDir) + 
                  "pre_map_" + i + "_" + j + ".jpg"));
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void main(String[] args) {
    scale("C:/index.gif", "C:/index1.gif", 2, false);
    String curPath = System.getProperty("user.dir");
    curPath = curPath.substring(0, curPath.length() - 3);
    System.out.println(curPath);
  }
}
