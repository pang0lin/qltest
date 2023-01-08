package com.qq.weixin.mp.push;

public class MsgTypeDict {
  private static final String[][] MATRIX = new String[][] { { "Info", "基础消息" }, { "RelProject", "关联项目" }, { "Event", "事件触发" }, { "jsflow", "工作流程" }, { "Chat", "聊天窗口" }, { "collect", "文档收集" } };
  
  public static String lookUp(String key) {
    String res = "";
    for (int i = 0; i < MATRIX.length; i++) {
      if (MATRIX[i][0].equals(key)) {
        res = MATRIX[i][1];
        break;
      } 
    } 
    return res;
  }
}
