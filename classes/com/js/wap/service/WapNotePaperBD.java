package com.js.wap.service;

import com.js.wap.bean.WapNotePaperBean;
import java.util.Map;

public class WapNotePaperBD {
  public Map getNotePaperList(String hql, int beginIndex, int limit) throws Exception {
    Map map = null;
    map = (new WapNotePaperBean()).getNotePaperList(hql, beginIndex, limit);
    return map;
  }
  
  public String getNoteContent(String noteId) throws Exception {
    String content = (new WapNotePaperBean()).getNoteContent(noteId);
    return content;
  }
  
  public void delete(String noteId) throws Exception {
    (new WapNotePaperBean()).delete(noteId);
  }
}
