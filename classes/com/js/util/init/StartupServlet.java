package com.js.util.init;

import com.js.oa.jsflow.uponline.UponlineThread;
import com.js.oa.webservice.util.SecurityUtilReader;
import com.js.system.util.SystemInit;
import com.js.thread.FlowHandleThread;
import com.js.thread.MainThread;
import com.js.util.config.SystemCommon;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class StartupServlet extends HttpServlet {
  private static final Logger log = Logger.getLogger(StartupServlet.class);
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}
  
  public void init() throws ServletException {
    try {
      doBind();
    } catch (Exception ex) {
      log.error("doBind error:" + ex);
      ex.printStackTrace();
    } 
    initData();
    if ("1".equals(SystemCommon.getExecuteLoopTask())) {
      MainThread mt = new MainThread();
      mt.start();
      FlowHandleThread fThread = new FlowHandleThread();
      fThread.start();
      UponlineThread mts = new UponlineThread();
      mts.start();
    } 
    SecurityUtilReader.init();
    super.init();
  }
  
  public void doBind() throws Exception {
    Properties environment = null;
    InitialContext context = null;
    String SESSION_FACTORY_JNDI = null;
    String URL = null;
    String factory = null;
    String curPath = System.getProperty("user.dir");
    String file = String.valueOf(curPath) + "/jsconfig/hibernate.property.xml";
    FileInputStream configFileInputStream = new FileInputStream(new File(file));
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(configFileInputStream);
    Element node = doc.getRootElement().getChild("Hibernate");
    URL = node.getAttributeValue("url");
    SESSION_FACTORY_JNDI = node.getAttributeValue("name");
    factory = node.getAttributeValue("factory");
    String server = node.getAttributeValue("server");
    if ("tongweb5".equals(server)) {
      Configuration conf = (new Configuration()).configure("/hibernate.cfg.xml");
      SessionFactory sf = conf.buildSessionFactory();
    } else {
      try {
        environment = new Properties();
        environment.put(
            "java.naming.factory.initial", factory);
        environment.put("java.naming.provider.url", URL);
        log.info("Constructing an Initial Directory Context object");
        context = new InitialContext(environment);
        Configuration conf = (new Configuration()).configure("/hibernate.cfg.xml");
        SessionFactory sf = conf.buildSessionFactory();
        if (sf == null)
          throw new Exception("SessionFactory cannot be built!"); 
        try {
          if (context.lookup(SESSION_FACTORY_JNDI) != null) {
            context.rebind(SESSION_FACTORY_JNDI, sf);
          } else {
            context.bind(SESSION_FACTORY_JNDI, sf);
          } 
        } catch (NamingException nameEx) {
          context.bind(SESSION_FACTORY_JNDI, sf);
        } 
      } catch (NamingException nameExp) {
        throw nameExp;
      } catch (Exception excp) {
        throw excp;
      } finally {
        if (context != null)
          try {
            context.close();
            context = null;
          } catch (NamingException nameExp) {
            throw new Exception(
                "NamingException for context close: " + 
                nameExp.getMessage());
          }  
        environment = null;
      } 
    } 
  }
  
  private boolean initData() {
    SystemInit si = new SystemInit();
    return si.initData();
  }
}
