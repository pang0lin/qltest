package cn.zzy.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class TjqzMessageTask extends HttpServlet {
  public void init() throws ServletException {
    (new MessageThread()).start();
  }
}
