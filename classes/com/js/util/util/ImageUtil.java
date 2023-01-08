package com.js.util.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageIO;

public class ImageUtil {
  private Image img = null;
  
  private int width;
  
  private int height;
  
  private String newFileName = "";
  
  public static void main(String[] args) throws Exception {
    System.out.println("开始：" + (new Date()).toLocaleString());
    ImageUtil imgCom = new ImageUtil("D:\\zhs\\apache-tomcat-6.0.32\\webapps\\jsoa\\upload/2015/peopleinfo/2015_1427866361625326897.png", "C:\\8888.png");
    imgCom.resizeFix(64, 64);
    System.out.println("结束：" + (new Date()).toLocaleString());
  }
  
  public ImageUtil(String fileName, String newFileName) throws IOException {
    File file = new File(fileName);
    System.out.println(file.exists());
    this.img = ImageIO.read(file);
    this.width = this.img.getWidth(null);
    this.height = this.img.getHeight(null);
    this.newFileName = newFileName;
  }
  
  public void resizeFix(int w, int h) throws IOException {
    if (this.width / this.height > w / h) {
      resizeByWidth(w);
    } else {
      resizeByHeight(h);
    } 
  }
  
  public void resizeByWidth(int w) throws IOException {
    int h = this.height * w / this.width;
    resize(w, h);
  }
  
  public void resizeByHeight(int h) throws IOException {
    int w = this.width * h / this.height;
    resize(w, h);
  }
  
  public void resize(int w, int h) throws IOException {
    BufferedImage image = new BufferedImage(w, h, 1);
    image.getGraphics().drawImage(this.img, 0, 0, w, h, null);
    File destFile = new File(this.newFileName);
    FileOutputStream out = new FileOutputStream(destFile);
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    encoder.encode(image);
    out.close();
  }
}
