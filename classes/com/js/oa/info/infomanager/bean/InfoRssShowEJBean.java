package com.js.oa.info.infomanager.bean;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoRssShowEJBean {
  public Map<String, Object> getInfoMapXml() {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    List<String[]> infoList = (List)new ArrayList<String>();
    String allSql = "SELECT count(information_id) FROM oa_information WHERE transmittowebsite=1";
    String sql = "SELECT information_id,channel_id,informationtitle,INFORMATIONSUBTITLE,informationkey,informationsummary,informationauthor,informationissuetime  FROM oa_information WHERE transmittowebsite=1 and INFORMATIONSTATUS=0 order by information_id desc limit " + 
      SystemCommon.getShowWebsiteNum();
    if (SystemCommon.getDatabaseType().equals("oracle"))
      sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT information_id,channel_id,informationtitle,INFORMATIONSUBTITLE,informationkey,informationsummary,informationauthor,informationissuetime FROM oa_information WHERE transmittowebsite=1 and INFORMATIONSTATUS=0 ORDER BY information_id) A )WHERE RN <" + 
        SystemCommon.getShowWebsiteNum(); 
    DataSourceBase base = new DataSourceBase();
    int allNum = 0;
    try {
      base.begin();
      ResultSet rs = base.executeQuery(allSql);
      if (rs.next())
        allNum = rs.getInt(1); 
      rs = base.executeQuery(sql);
      while (rs.next()) {
        String[] info = { rs.getString(1), 
            rs.getString(2), 
            rs.getString(3), 
            rs.getString(4), 
            rs.getString(5), 
            rs.getString(6), 
            rs.getString(7), 
            rs.getString(8) };
        infoList.add(info);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    returnMap.put("infoList", infoList);
    returnMap.put("allNum", Integer.valueOf(allNum));
    return returnMap;
  }
  
  public Map<String, Object> getInfoListMore(String queryStr) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    int pageShowNum = SystemCommon.getShowMoreNum();
    try {
      queryStr = URLDecoder.decode(queryStr, "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    String[] queryStrs = queryStr.split("_");
    String param = (queryStrs.length > 1) ? queryStrs[1] : "";
    int pageNum = (queryStrs.length > 2) ? Integer.valueOf(queryStrs[queryStrs.length - 1]).intValue() : 1;
    returnMap.put("curPage", Integer.valueOf(pageNum));
    List<String[]> infoList = (List)new ArrayList<String>();
    String allNum = "SELECT count(information_id) FROM oa_information WHERE transmittowebsite=1";
    String sql = "SELECT information_id,channel_id,informationtitle,INFORMATIONSUBTITLE,informationkey,informationsummary,informationauthor,informationissuetime,informationissuer FROM oa_information WHERE transmittowebsite=1 and INFORMATIONSTATUS=0";
    if (queryStrs.length > 1 && !queryStrs[1].equals("")) {
      allNum = String.valueOf(allNum) + " and informationtitle like ?";
      sql = String.valueOf(sql) + " and informationtitle like ?";
    } 
    sql = String.valueOf(sql) + " order by information_id desc";
    if (SystemCommon.getDatabaseType().equals("oracle")) {
      sql = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (" + sql + ") A ) WHERE RN between " + (pageShowNum * (pageNum - 1)) + " and " + (pageShowNum * pageNum);
    } else {
      sql = String.valueOf(sql) + " limit " + (pageShowNum * (pageNum - 1)) + "," + pageShowNum;
    } 
    int allRow = 0;
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    try {
      conn = base.getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(allNum);
      if (queryStrs.length > 1 && !queryStrs[1].equals(""))
        pstmt.setString(1, "'%" + param + "%'"); 
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        allRow = rs.getInt(1); 
      rs.close();
      pstmt = conn.prepareStatement(sql);
      if (queryStrs.length > 1 && !queryStrs[1].equals(""))
        pstmt.setString(1, "'%" + param + "%'"); 
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] info = { rs.getString(1), 
            rs.getString(2), 
            rs.getString(3), 
            rs.getString(4), 
            rs.getString(5), 
            rs.getString(6), 
            rs.getString(7), 
            rs.getString(8), 
            rs.getString(9) };
        infoList.add(info);
      } 
      rs.close();
      returnMap.put("infoList", infoList);
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    returnMap.put("allRow", Integer.valueOf(allRow));
    returnMap.put("allPageNum", Integer.valueOf((allRow % pageShowNum == 0) ? (allRow / pageShowNum) : (allRow / pageShowNum + 1)));
    return returnMap;
  }
  
  public Map<String, Object> getInfoContext(String infoId) {
    Map<String, Object> returnMap = new HashMap<String, Object>();
    String sql = "SELECT information_id,channel_id,informationtitle,INFORMATIONSUBTITLE,informationkey,INFORMATIONCONTENT,informationauthor,informationissuetime FROM oa_information WHERE information_id=" + 
      infoId;
    String accessorySql = "SELECT accessoryName,accessorysavename FROM oa_informationaccessory WHERE information_id=" + infoId;
    DataSourceBase base = new DataSourceBase();
    String[] info = (String[])null;
    List<String[]> accessory = (List)new ArrayList<String>();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        info = new String[] { rs.getString(3), 
            rs.getString(4), 
            rs.getString(6) }; 
      rs.close();
      rs = base.executeQuery(accessorySql);
      while (rs.next()) {
        accessory.add(new String[] { rs.getString(1), 
              rs.getString(2) });
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    returnMap.put("info", info);
    returnMap.put("accessory", accessory);
    return returnMap;
  }
}
