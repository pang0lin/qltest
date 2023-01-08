package com.js.oa.search.client.disrupter;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchContext {
  private static final int ENTRIES = 16;
  
  private static ExecutorService executorService = null;
  
  private static Disruptor<SearchEvent> disruptor;
  
  private static RingBuffer<SearchEvent> ringBuffer;
  
  private static SearchContext instance;
  
  public static SearchContext getInstance() {
    if (instance == null) {
      init();
      instance = new SearchContext();
    } 
    return instance;
  }
  
  public static void init() {
    System.out.println("--------------------DisrupterContext初始化了---------------------------------" + new Date());
    executorService = Executors.newCachedThreadPool();
    disruptor = new Disruptor(SearchEvent.FACTORY, 16, executorService);
    disruptor.handleEventsWith(new EventHandler[] { new SearchHandler() });
    disruptor.start();
    ringBuffer = disruptor.getRingBuffer();
  }
  
  public void sendSearch(SearchBean searchBean) {
    long sequence = ringBuffer.next();
    SearchEvent searchEvent = (SearchEvent)ringBuffer.get(sequence);
    searchEvent.setSearchBean(searchBean);
    ringBuffer.publish(sequence);
  }
  
  public void stop() {
    System.out.println("-----------stop()--------------");
    disruptor.shutdown();
    executorService.shutdownNow();
  }
}
