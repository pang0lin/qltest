package com.js.oa.scheme.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ValueListHandler implements ValueListIterator {
  protected List list = null;
  
  protected ListIterator listIterator = null;
  
  protected int volume = 15;
  
  protected int currentPage = 1;
  
  protected void setList(List list) {
    this.list = list;
    if (list != null)
      this.listIterator = list.listIterator(); 
  }
  
  public Collection getList() {
    return this.list;
  }
  
  public int getSize() throws IteratorException {
    int size = 0;
    if (this.list != null)
      size = this.list.size(); 
    return size;
  }
  
  public Object getCurrentElement() throws IteratorException {
    Object obj = null;
    if (this.list != null) {
      int currIndex = this.listIterator.nextIndex();
      obj = this.list.get(currIndex);
    } else {
      throw new IteratorException();
    } 
    return obj;
  }
  
  public List getPreviousElements(int count) throws IteratorException {
    return getPreviewPage(count);
  }
  
  public List getNextElements(int count) throws IteratorException {
    return getNextPage(count);
  }
  
  public void resetIndex() throws IteratorException {
    if (this.listIterator != null) {
      this.listIterator = this.list.listIterator();
    } else {
      throw new IteratorException();
    } 
  }
  
  public int getTotleNumberOfPages() throws IteratorException {
    if (getSize() % this.volume == 0)
      return getSize() / this.volume; 
    return getSize() / this.volume + 1;
  }
  
  public int getCurrentNumberOfPages() throws IteratorException {
    return this.currentPage;
  }
  
  public List getNextPage(int currentPage) throws IteratorException {
    int i = 0;
    Object object = null;
    LinkedList list = new LinkedList();
    if (currentPage >= getTotleNumberOfPages()) {
      this.currentPage = currentPage;
      return getDesignatedPage(currentPage);
    } 
    this.currentPage = ++currentPage;
    return getDesignatedPage(currentPage);
  }
  
  public List getPreviewPage(int currentPage) throws IteratorException {
    int i = 0;
    Object object = null;
    LinkedList list = new LinkedList();
    if (currentPage <= 1) {
      this.currentPage = currentPage;
      return getDesignatedPage(currentPage);
    } 
    this.currentPage = --currentPage;
    return getDesignatedPage(currentPage);
  }
  
  public List getLastPage() throws IteratorException {
    return getDesignatedPage(getTotleNumberOfPages());
  }
  
  public List getFirstPage() throws IteratorException {
    return getDesignatedPage(1);
  }
  
  public void setPageVolume(int pageVolume) throws IteratorException {
    this.volume = pageVolume;
  }
  
  public List getDesignatedPage(int page) throws IteratorException {
    int i = 0;
    Object object = new Object();
    LinkedList<Object> linkedList = new LinkedList();
    if (page > getTotleNumberOfPages())
      page = getTotleNumberOfPages(); 
    if (page < 1)
      page = 1; 
    int baseNumber = (page - 1) * this.volume;
    if (this.list != null)
      while (i + baseNumber < this.list.size() && i < this.volume) {
        object = this.list.get((page - 1) * this.volume + i);
        linkedList.add(object);
        i++;
      }  
    this.currentPage = page;
    return linkedList;
  }
  
  public void reverse() throws IteratorException {
    Collections.reverse(this.list);
  }
}
