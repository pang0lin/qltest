package com.qq.weixin.mp.pojo.push;

public class NewsPush extends Push {
  private final String msgtype = "news";
  
  private News news;
  
  public News getNews() {
    return this.news;
  }
  
  public void setNews(News news) {
    this.news = news;
  }
  
  public String getMsgtype() {
    return "news";
  }
  
  public class News {
    private Article[] articles;
    
    public Article[] getArticles() {
      return this.articles;
    }
    
    public void setArticles(Article[] articles) {
      this.articles = articles;
    }
    
    public class Article extends TitleDescription {
      private String url;
      
      private String picurl;
      
      public String getUrl() {
        return this.url;
      }
      
      public void setUrl(String url) {
        this.url = url;
      }
      
      public String getPicurl() {
        return this.picurl;
      }
      
      public void setPicurl(String picurl) {
        this.picurl = picurl;
      }
    }
  }
}
