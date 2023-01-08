package com.js.oa.search.client.disrupter;

import com.lmax.disruptor.EventFactory;

class SearchEvent {
  public static final EventFactory<SearchEvent> FACTORY = new EventFactory<SearchEvent>() {
      public SearchEvent newInstance() {
        return new SearchEvent();
      }
    };
  
  private SearchBean searchBean;
  
  public SearchBean getSearchBean() {
    return this.searchBean;
  }
  
  public void setSearchBean(SearchBean searchBean) {
    this.searchBean = searchBean;
  }
}
