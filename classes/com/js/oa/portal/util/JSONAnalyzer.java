package com.js.oa.portal.util;

import com.js.oa.portal.pojo.Tybg;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONAnalyzer {
  public static void main(String[] args) {
    String jsonStr = "[{'OBJID':19142837,'TITLE':'1111','INPUTID':495,'WRITETIME':{'date':30,'day':5,'hours':14,'minutes':48,'month':4,'nanos':0,'seconds':56,'time':1401432536000,'timezoneOffset':-480,'year':114},'DOCTYPENAME':'其他研究报告','ORIGINALAUTHOR':'测试','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':null,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19142827,'TITLE':'asdf','INPUTID':1,'WRITETIME':{'date':10,'day':1,'hours':11,'minutes':19,'month':11,'nanos':0,'seconds':23,'time':1355109563000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'公司深度研究','ORIGINALAUTHOR':'管理员','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':null,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19138447,'TITLE':'晨报20120813','INPUTID':719,'WRITETIME':{'date':13,'day':1,'hours':9,'minutes':13,'month':7,'nanos':0,'seconds':7,'time':1344820387000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'肖志光、刘志波','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19139052,'TITLE':'晨报20120814','INPUTID':719,'WRITETIME':{'date':14,'day':2,'hours':8,'minutes':59,'month':7,'nanos':0,'seconds':4,'time':1344905944000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'肖志光、刘志波','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19139408,'TITLE':'晨报20120815','INPUTID':719,'WRITETIME':{'date':15,'day':3,'hours':8,'minutes':58,'month':7,'nanos':0,'seconds':44,'time':1344992324000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'肖志光、刘志波','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19139877,'TITLE':'晨报20120816','INPUTID':719,'WRITETIME':{'date':16,'day':4,'hours':9,'minutes':0,'month':7,'nanos':0,'seconds':53,'time':1345078853000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'肖志光、刘志波','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19140279,'TITLE':'晨报20120817','INPUTID':719,'WRITETIME':{'date':17,'day':5,'hours':8,'minutes':58,'month':7,'nanos':0,'seconds':57,'time':1345165137000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'肖志光、刘志波','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19140833,'TITLE':'晨报20120820','INPUTID':720,'WRITETIME':{'date':20,'day':1,'hours':9,'minutes':19,'month':7,'nanos':0,'seconds':0,'time':1345425540000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'刘志波 朱元琪','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19141544,'TITLE':'晨报20120821','INPUTID':720,'WRITETIME':{'date':21,'day':2,'hours':8,'minutes':57,'month':7,'nanos':0,'seconds':33,'time':1345510653000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'刘志波 朱元琪','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19142052,'TITLE':'晨报20120822','INPUTID':720,'WRITETIME':{'date':22,'day':3,'hours':8,'minutes':56,'month':7,'nanos':0,'seconds':31,'time':1345596991000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'刘志波 朱元琪','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':0,'DOCVERSION':null},{'OBJID':19142490,'TITLE':'晨报20120823','INPUTID':720,'WRITETIME':{'date':23,'day':4,'hours':8,'minutes':57,'month':7,'nanos':0,'seconds':51,'time':1345683471000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'日报','ORIGINALAUTHOR':'刘志波 朱元琪','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19140858,'TITLE':'基金投资部周报（20120810-20120817）','INPUTID':714,'WRITETIME':{'date':20,'day':1,'hours':9,'minutes':28,'month':7,'nanos':0,'seconds':59,'time':1345426139000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'周报','ORIGINALAUTHOR':'龙南质','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19138481,'TITLE':'宏观数据点评-2012年7月','INPUTID':669,'WRITETIME':{'date':13,'day':1,'hours':0,'minutes':0,'month':7,'nanos':0,'seconds':0,'time':1344787200000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观及策略','ORIGINALAUTHOR':'李荟','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':0,'DOCVERSION':null},{'OBJID':19138504,'TITLE':'经济继续下滑 政策亟需加码','INPUTID':560,'WRITETIME':{'date':13,'day':1,'hours':9,'minutes':58,'month':7,'nanos':0,'seconds':46,'time':1344823126000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观形势短评','ORIGINALAUTHOR':'王罡','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19138465,'TITLE':'美国“财政悬崖”问题解析','INPUTID':302,'WRITETIME':{'date':13,'day':1,'hours':9,'minutes':20,'month':7,'nanos':0,'seconds':48,'time':1344820848000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观专题研究','ORIGINALAUTHOR':'董葳','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19139128,'TITLE':'经济转型的路径分析——来自日本的经验和证据','INPUTID':627,'WRITETIME':{'date':14,'day':2,'hours':10,'minutes':24,'month':7,'nanos':0,'seconds':38,'time':1344911078000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观专题研究','ORIGINALAUTHOR':'段彦飞','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19140087,'TITLE':'政策利率调整的周期分析','INPUTID':627,'WRITETIME':{'date':16,'day':4,'hours':14,'minutes':37,'month':7,'nanos':0,'seconds':5,'time':1345099025000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观专题研究','ORIGINALAUTHOR':'段彦飞','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19141281,'TITLE':'存款准备金率调整的影响因素及近期趋势','INPUTID':305,'WRITETIME':{'date':20,'day':1,'hours':16,'minutes':29,'month':7,'nanos':0,'seconds':3,'time':1345451343000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观专题研究','ORIGINALAUTHOR':'李彪，段彦飞','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19141285,'TITLE':'近期国内出口形势分析及展望','INPUTID':302,'WRITETIME':{'date':20,'day':1,'hours':16,'minutes':38,'month':7,'nanos':0,'seconds':54,'time':1345451934000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观专题研究','ORIGINALAUTHOR':'董葳、叶丹','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null},{'OBJID':19142602,'TITLE':'房价短期上涨承压 未来延续缓升态势','INPUTID':719,'WRITETIME':{'date':23,'day':4,'hours':10,'minutes':23,'month':7,'nanos':0,'seconds':4,'time':1345688584000,'timezoneOffset':-480,'year':112},'DOCTYPENAME':'宏观专题研究','ORIGINALAUTHOR':'刘志波','STATUS':200,'BROKERNAME':'内部','INVESTRANK':null,'ATTACHMENTFLAG':1,'COMMENTFLAG':null,'DOCVERSION':null}]";
    getTybgList(jsonStr);
  }
  
  public static List<Tybg> getTybgList(String jsonStr) {
    List<Tybg> results = new ArrayList<Tybg>();
    JSONArray jsonArr = JSONArray.fromObject(jsonStr);
    for (int i = 0; i < jsonArr.size(); i++) {
      Tybg obj = new Tybg();
      JSONObject jsonObject = jsonArr.getJSONObject(i);
      obj.setObjid(jsonObject.getString("OBJID"));
      obj.setTitle(jsonObject.getString("TITLE"));
      obj.setInputid(jsonObject.getString("INPUTID"));
      obj.setDocTypeName(jsonObject.getString("DOCTYPENAME"));
      obj.setOriginalAuthor(jsonObject.getString("ORIGINALAUTHOR"));
      obj.setStatus(jsonObject.getString("STATUS"));
      obj.setBrokerName(jsonObject.getString("BROKERNAME"));
      obj.setInveStrank(jsonObject.getString("INVESTRANK"));
      obj.setAttachmentFlag(jsonObject.getString("ATTACHMENTFLAG"));
      obj.setCommentFlag(jsonObject.getString("COMMENTFLAG"));
      obj.setDocVersion(jsonObject.getString("DOCVERSION"));
      String year = (new StringBuilder(String.valueOf(jsonObject.getJSONObject("WRITETIME").getInt("year") + 1900))).toString();
      String month = (new StringBuilder(String.valueOf(jsonObject.getJSONObject("WRITETIME").getInt("month") + 1))).toString();
      String day = (new StringBuilder(String.valueOf(jsonObject.getJSONObject("WRITETIME").getInt("date")))).toString();
      month = "0" + month;
      month = month.substring(month.length() - 2, month.length());
      day = "0" + day;
      day = day.substring(day.length() - 2, day.length());
      obj.setWriteTime(String.valueOf(month) + "-" + day);
      results.add(obj);
    } 
    return results;
  }
}