package com.js.oa.info.infomanager.service;

import com.js.oa.info.infomanager.bean.InfoRssShowEJBean;
import java.util.Map;

public class InfoRssShowBD {
  InfoRssShowEJBean infoBean = new InfoRssShowEJBean();
  
  public Map<String, Object> getInfoMapXml() {
    return this.infoBean.getInfoMapXml();
  }
  
  public Map<String, Object> getInfoListMore(String queryStr) {
    return this.infoBean.getInfoListMore(queryStr);
  }
  
  public Map<String, Object> getInfoContext(String infoId) {
    return this.infoBean.getInfoContext(infoId);
  }
  
  public Integer[] getPageNum(int curPage, int allPage, int showNum) {
    Integer[] showInt = new Integer[showNum];
    if (allPage <= showNum) {
      showInt = new Integer[allPage];
      for (int i = 1; i <= allPage; i++)
        showInt[i - 1] = Integer.valueOf(i); 
    } else {
      showInt = new Integer[showNum];
      int halfNum = showNum / 2;
      for (int i = 1; i <= showNum; i++) {
        if (curPage <= halfNum) {
          showInt[i - 1] = Integer.valueOf(i);
        } else if (curPage >= allPage - halfNum) {
          showInt[i - 1] = Integer.valueOf(allPage - showNum + i);
        } else {
          showInt[i - 1] = Integer.valueOf(curPage - halfNum + i);
        } 
      } 
    } 
    return showInt;
  }
}
