package com.js.oa.dcq.util;

import com.js.util.util.json.JsonUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
  public static void main(String[] args) {
    String json = "{\"accDoc\":[{\"releaseDepName\":\"政府办公室\",\"releaseDepID\":\"C0100\",\"releaseDate\":\"2015-03-28 19:24:29.0\",\"releaseStatus\":\"0\",\"baseDataName\":\"11\",\"oid\":1828108,\"docTitle\":\"小OA发给大OA测试uuu\",\"docNO\":\"东政法[12]号\"},{\"releaseDepName\":\"信息办\",\"releaseDepID\":\"C0200\",\"releaseDate\":\"2015-03-27 17:28:15.0\",\"releaseStatus\":\"0\",\"baseDataName\":\"1\",\"oid\":1826860,\"docTitle\":\"20150227test1111111111111\",\"docNO\":\"东信息办函〔2015〕[2]号\"}],\"execMsg\":\"查询公文待办列表成功!\",\"execCode\":\"0000\"}";
    Map<Object, Object> map = new HashMap<Object, Object>();
    map.put("accDoc", DocumentAccdoc.class);
    Document c = (Document)JsonUtil.getDTO(json, Document.class, map);
    System.out.println(c.getExecCode());
    List<DocumentAccdoc> accDoc = c.getAccDoc();
    for (DocumentAccdoc documentAccdoc : accDoc);
  }
}
