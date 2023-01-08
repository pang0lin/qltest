package com.js.oa.scheme.util;

import java.util.List;

public interface ValueListIterator {
  int getSize() throws IteratorException;
  
  Object getCurrentElement() throws IteratorException;
  
  void resetIndex() throws IteratorException;
  
  int getTotleNumberOfPages() throws IteratorException;
  
  int getCurrentNumberOfPages() throws IteratorException;
  
  List getNextPage(int paramInt) throws IteratorException;
  
  List getPreviewPage(int paramInt) throws IteratorException;
  
  List getLastPage() throws IteratorException;
  
  List getFirstPage() throws IteratorException;
  
  void setPageVolume(int paramInt) throws IteratorException;
  
  List getDesignatedPage(int paramInt) throws IteratorException;
  
  void reverse() throws IteratorException;
}
