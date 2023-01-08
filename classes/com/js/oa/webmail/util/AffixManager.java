package com.js.oa.webmail.util;

import com.js.oa.webmail.po.Affix;
import com.js.oa.webmail.po.Attach;
import com.js.oa.webmail.service.AffixBD;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class AffixManager {
  private static AffixManager affixManager;
  
  private static Map affixMap = new HashMap<Object, Object>();
  
  private static Map attachMap = new HashMap<Object, Object>();
  
  public static AffixManager getInstance() {
    if (affixManager == null) {
      affixManager = new AffixManager();
      affixManager.init();
    } 
    return affixManager;
  }
  
  public String getAffixName(String path) {
    String affixName = null;
    try {
      Affix affix = (Affix)affixMap.get(path);
      if (affix != null)
        affixName = affix.getAffixName(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return affixName;
  }
  
  public String getAffixSize(String path) {
    Affix affix = null;
    try {
      affix = (Affix)affixMap.get(path);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return affix.getAffixName();
  }
  
  public String getAttachName(String path) {
    String name = "";
    try {
      List<Attach> list = new ArrayList(attachMap.values());
      for (int i = 0; i < list.size(); i++) {
        Attach temp = list.get(i);
        if (temp.getAttachDisName().equals(path)) {
          name = temp.getAttachName();
          break;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return name;
  }
  
  public List getAffixArray(String maiId) {
    List<String> affList = new ArrayList();
    try {
      List<Affix> list = new ArrayList(affixMap.values());
      for (int i = 0; i < list.size(); i++) {
        Affix temp = list.get(i);
        if (temp.getMailId().equals(maiId))
          affList.add(temp.getAffixPath()); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return affList;
  }
  
  public List getAffixList(String maiId) {
    List<Affix> affList = new ArrayList();
    try {
      List<Affix> list = new ArrayList(affixMap.values());
      for (int i = 0; i < list.size(); i++) {
        Affix temp = list.get(i);
        if (temp.getMailId().equals(maiId))
          affList.add(temp); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return affList;
  }
  
  public List getAttachArray(Long infoId, HttpServletRequest request) {
    List<String> affList = new ArrayList();
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    String tempPath = String.valueOf(request.getSession().getServletContext().getRealPath("/upload/")) + "/" + year + "/webmail/";
    try {
      List<Attach> list = new ArrayList(attachMap.values());
      for (int i = 0; i < list.size(); i++) {
        Attach temp = list.get(i);
        if (temp.getMailInfoId().equals(infoId))
          affList.add(String.valueOf(tempPath) + temp.getAttachDisName()); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return affList;
  }
  
  public List getBakAttahListById(Long infoId) {
    List<Attach> attList = null;
    try {
      if (attachMap != null && attachMap.size() > 0) {
        List<Attach> list = new ArrayList(attachMap.values());
        attList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
          Attach temp = list.get(i);
          if (temp.getMailInfoId().equals(infoId))
            attList.add(temp); 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return attList;
  }
  
  public boolean getMailAffixFlag(String mailId) {
    boolean flag = false;
    try {
      List<Affix> list = new ArrayList(affixMap.values());
      if (list != null && affixMap.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          Affix temp = list.get(i);
          if (temp.getMailId().equals(mailId)) {
            flag = true;
            break;
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public void init() {
    try {
      affixMap.clear();
      attachMap.clear();
      AffixBD aff = new AffixBD();
      List<Affix> list = aff.getAffixList();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          Affix temp = list.get(i);
          affixMap.put(temp.getAffixPath(), temp);
        }  
      List<Attach> list1 = aff.getAttachList();
      if (list1 != null && list1.size() > 0)
        for (int i = 0; i < list1.size(); i++) {
          Attach temp = list1.get(i);
          attachMap.put(temp.getAttachId(), temp);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
