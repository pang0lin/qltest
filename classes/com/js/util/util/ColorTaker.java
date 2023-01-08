package com.js.util.util;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ColorTaker extends JFrame implements ActionListener, KeyListener {
  Robot robot = null;
  
  Point mousepoint = null;
  
  Integer R = Integer.valueOf(0), G = Integer.valueOf(0), B = Integer.valueOf(0);
  
  Integer X = Integer.valueOf(0);
  
  Integer Y = Integer.valueOf(0);
  
  Color pixel = new Color(0, 0, 0);
  
  String s16 = "";
  
  MouseInfo mouseinfo = null;
  
  JLabel JR = null;
  
  JLabel JG = null;
  
  JLabel JB = null;
  
  JLabel JX = null;
  
  JLabel JY = null;
  
  JLabel J16 = null;
  
  JLabel JCol = null;
  
  JTextField JTFR = null;
  
  JTextField JTFG = null;
  
  JTextField JTFB = null;
  
  JTextField JTFX = null;
  
  JTextField JTFY = null;
  
  JTextField JTF16 = null;
  
  JButton JCopy = null;
  
  JButton JExit = null;
  
  JPanel pix = new JPanel(), zb = new JPanel(), pb = new JPanel();
  
  public void setMenuBar() {
    JMenuBar myBar = new JMenuBar();
    JMenu helpMenu = new JMenu("帮助");
    JMenuItem help_About = new JMenuItem("关于");
    setJMenuBar(myBar);
    myBar.add(helpMenu);
    helpMenu.add(help_About);
    help_About.addActionListener(this);
  }
  
  public void ColorTaker() {
    setTitle("拾色器");
    this.JR = new JLabel("R: ");
    this.JG = new JLabel("G: ");
    this.JB = new JLabel("B: ");
    this.JX = new JLabel("X: ");
    this.JY = new JLabel("Y: ");
    this.J16 = new JLabel("十六进制表示：");
    this.JCol = new JLabel("★★★★★");
    this.JTFR = new JTextField(5);
    this.JTFG = new JTextField(5);
    this.JTFB = new JTextField(5);
    this.JTF16 = new JTextField(6);
    this.JTFX = new JTextField(5);
    this.JTFY = new JTextField(5);
    this.JCopy = new JButton("复制");
    this.JExit = new JButton("退出");
    setFocusable(true);
    addKeyListener(this);
    this.JCopy.addActionListener(this);
    this.JExit.addActionListener(this);
    this.JCopy.addKeyListener(this);
    this.JExit.addKeyListener(this);
    this.JTFR.addKeyListener(this);
    this.JTFG.addKeyListener(this);
    this.JTFB.addKeyListener(this);
    this.JTF16.addKeyListener(this);
    this.JTFX.addKeyListener(this);
    this.JTFY.addKeyListener(this);
    this.pix.setLayout(new FlowLayout());
    this.zb.setLayout(new FlowLayout());
    this.pb.setLayout(new FlowLayout());
    this.pix.add(this.JR);
    this.pix.add(this.JTFR);
    this.pix.add(this.JG);
    this.pix.add(this.JTFG);
    this.pix.add(this.JB);
    this.pix.add(this.JTFB);
    this.zb.add(this.JX);
    this.zb.add(this.JTFX);
    this.zb.add(this.JY);
    this.zb.add(this.JTFY);
    this.pix.add(this.JCol);
    this.pix.add(this.J16);
    this.pix.add(this.JTF16);
    this.pb.add(this.JCopy);
    this.pb.add(this.JExit);
    setLayout(new BorderLayout());
    add(this.pix, "Center");
    add(this.zb, "North");
    add(this.pb, "South");
  }
  
  public static void setClipboard(String str) {
    StringSelection ss = new StringSelection(str);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.JCopy) {
      if (this.s16 == "") {
        JOptionPane.showMessageDialog(null, "请在你想要的颜色上面按Alt键进行选取。", "提示", 1);
      } else {
        setClipboard(this.s16);
        JOptionPane.showMessageDialog(null, "颜色代码 " + this.s16 + " 已经复制到剪贴板中，按Ctrl+V粘贴。", "提示", 1);
      } 
    } else if (e.getSource() == this.JExit) {
      System.exit(0);
    } 
    if (e.getActionCommand() == "关于")
      JOptionPane.showMessageDialog(this, "取色器 1.0\n和PS里的拾色器功能类似，但是使用方便，希望你能喜欢。\n想知道鼠标在的这一点的颜色吗？点下ALT键看看吧。", "关于我", 1); 
  }
  
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == 18)
      try {
        Robot robot = new Robot();
        this.mousepoint = MouseInfo.getPointerInfo().getLocation();
        this.pixel = robot.getPixelColor(this.mousepoint.x, this.mousepoint.y);
        this.X = Integer.valueOf(this.mousepoint.x);
        this.Y = Integer.valueOf(this.mousepoint.y);
        this.R = Integer.valueOf(this.pixel.getRed());
        this.G = Integer.valueOf(this.pixel.getGreen());
        this.B = Integer.valueOf(this.pixel.getBlue());
        this.JTFR.setText(this.R.toString());
        this.JTFG.setText(this.G.toString());
        this.JTFB.setText(this.B.toString());
        this.JTFX.setText(this.X.toString());
        this.JTFY.setText(this.Y.toString());
        this.s16 = "#" + Integer.toHexString(this.R.intValue()) + Integer.toHexString(this.G.intValue()) + Integer.toHexString(this.B.intValue());
        this.JTF16.setText(this.s16);
        Color col = new Color(this.R.intValue(), this.G.intValue(), this.B.intValue());
        this.JCol.setForeground(col);
      } catch (AWTException ex) {
        ex.printStackTrace();
      }  
  }
  
  public void keyTyped(KeyEvent e) {}
  
  public void keyPressed(KeyEvent e) {}
  
  public static void main(String[] args) {
    ColorTaker ct = new ColorTaker();
    ct.setMenuBar();
    ct.setDefaultCloseOperation(3);
    ct.setSize(300, 200);
    ct.setLocation(200, 200);
    ct.setResizable(false);
    ct.ColorTaker();
    ct.setVisible(true);
  }
}
