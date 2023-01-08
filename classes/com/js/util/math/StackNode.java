package com.js.util.math;

public class StackNode<T> {
  private T content;
  
  private StackNode<T> link;
  
  public T getContent() {
    return this.content;
  }
  
  public void setContent(T content) {
    this.content = content;
  }
  
  public StackNode<T> getLink() {
    return this.link;
  }
  
  public void setLink(StackNode<T> link) {
    this.link = link;
  }
}
