package com.ali.dingding.message;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;
import com.qq.weixin.mp.service.WeixinMessageUrlGetterFactory;
import java.sql.SQLException;

public class GetMessageUrl {
  public String getUrlByMessageId(String messageId) {
    String url = "";
    String messageType = "";
    String dataId = "";
    String userid = "";
    DbOpt dbopt = null;
    String[][] result = (String[][])null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr2("select message_type, message_toUserId,message_Url,data_Id from sys_messages message where message_id = " + 
          messageId, 4);
      if (result != null && result.length == 1) {
        url = result[0][2];
        messageType = result[0][0];
        dataId = result[0][3];
        userid = result[0][1];
      } 
      WeixinMessageUrlGetter getter = 
        WeixinMessageUrlGetterFactory.getWeixinMessageUrlGetter(messageType);
      url = getter.getWeiXinUrl(url, dataId, userid);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return url;
  }
  
  public static void main(String[] args) {
    GetMessageUrl test = new GetMessageUrl();
    System.out.println(test.getUrlByMessageId("530298"));
  }
}
