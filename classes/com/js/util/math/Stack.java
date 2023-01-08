package com.js.util.math;

public class Stack<T> {
  private StackNode<T> top;
  
  private int index = -1;
  
  public void push(T content) {
    StackNode<T> node = new StackNode<T>();
    node.setContent(content);
    node.setLink(this.top);
    this.top = node;
    this.index++;
  }
  
  public StackNode<T> pop() {
    if (this.index == -1) {
      System.out.println("stack hasn't content");
      return null;
    } 
    StackNode<T> node = getTop();
    this.top = node.getLink();
    this.index--;
    return node;
  }
  
  public StackNode<T> getTop() {
    return this.top;
  }
  
  public int getIndex() {
    return this.index;
  }
}
