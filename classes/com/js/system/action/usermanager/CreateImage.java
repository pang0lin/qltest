package com.js.system.action.usermanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateImage extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("image/jpeg");
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    HttpSession session = request.getSession();
    int width = 60, height = 20;
    BufferedImage image = new BufferedImage(width, height, 1);
    Graphics g = image.getGraphics();
    Random random = new Random();
    g.setColor(getRandColor(200, 250));
    g.fillRect(0, 0, width, height);
    g.setFont(new Font("Times New Roman", 1, 18));
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 155; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
      g.drawLine(x, y, x + xl, y + yl);
    } 
    String sRand = "";
    String randChar = "A01h234fjBdCnDEFG67f8oHIJabcKLMNOijklmPQRSTrsUVtWXpqYZ590";
    for (int j = 0; j < 4; j++) {
      String rand = String.valueOf(randChar.charAt(random.nextInt(10)));
      sRand = String.valueOf(sRand) + rand;
      g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
      g.drawString(rand, 13 * j + 6, 16);
    } 
    session.setAttribute("sessionCode_", sRand);
    g.dispose();
    ServletOutputStream responseOutputStream = response.getOutputStream();
    ImageIO.write(image, "JPEG", (OutputStream)responseOutputStream);
    responseOutputStream.flush();
    responseOutputStream.close();
  }
  
  Color getRandColor(int fc, int bc) {
    Random random = new Random();
    if (fc > 255)
      fc = 255; 
    if (bc > 255)
      bc = 255; 
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }
  
  public String getServletInfo() {
    return "Short description";
  }
}
