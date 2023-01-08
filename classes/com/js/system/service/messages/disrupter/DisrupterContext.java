package com.js.system.service.messages.disrupter;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisrupterContext {
  private static final int ENTRIES = 16;
  
  private static ExecutorService executorService = null;
  
  private static Disruptor<MessagesEvent> disruptor;
  
  private static RingBuffer<MessagesEvent> ringBuffer;
  
  private static DisrupterContext instance;
  
  public static DisrupterContext getInstance() {
    if (instance == null) {
      init();
      instance = new DisrupterContext();
    } 
    return instance;
  }
  
  public static void init() {
    System.out.println("--------------------DisrupterContext初始化了---------------------------------" + new Date());
    executorService = Executors.newCachedThreadPool();
    disruptor = new Disruptor(MessagesEvent.FACTORY, 16, executorService);
    disruptor.handleEventsWith(new EventHandler[] { new MessagesHandler() });
    disruptor.start();
    ringBuffer = disruptor.getRingBuffer();
  }
  
  public void sendMessages(MessagesBean messagesBean) {
    long sequence = ringBuffer.next();
    MessagesEvent messagesEvent = (MessagesEvent)ringBuffer.get(sequence);
    messagesEvent.setMessagesBean(messagesBean);
    ringBuffer.publish(sequence);
  }
  
  public void stop() {
    System.out.println("-----------stop()--------------");
    disruptor.shutdown();
    executorService.shutdownNow();
  }
}
