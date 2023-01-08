package com.js.oa.weixin.document.service;

import com.js.oa.weixin.document.bean.WeiXinReceiveFileBean;
import java.util.Map;

public class WeiXinReceiveFileBD {
  public Map getReceiveFilePaperList(String hql, int beginIndex, int limit) throws Exception {
    Map map = null;
    map = (new WeiXinReceiveFileBean()).getReceiveFilePaperList(hql, beginIndex, limit);
    return map;
  }
  
  public Map getDocinfo(String id) {
    Map<String, String> map = null;
    map = (new WeiXinReceiveFileBean()).getDocInfo(id);
    return map;
  }
}
