package com.js.system.bean.rssmanager;

import com.js.system.vo.rssmanager.CategoryChannelVO;
import com.js.system.vo.rssmanager.CategoryVO;
import com.js.system.vo.rssmanager.ChannelInfoVO;
import com.js.system.vo.rssmanager.ChannelOrderVO;
import com.js.system.vo.rssmanager.ItemStateVO;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBObject;

public interface RssEJB extends EJBObject {
  void delCategory(String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delAllCategory() throws Exception, RemoteException;
  
  CategoryVO getSingleRssCategory(String paramString) throws Exception, RemoteException;
  
  List getSingleRssCategoryByName(String paramString) throws Exception, RemoteException;
  
  List getRssCategoryList() throws Exception, RemoteException;
  
  void saveOrUpdateRssCategory(CategoryVO paramCategoryVO) throws Exception, RemoteException;
  
  void delChannel(String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delAllChannel() throws Exception, RemoteException;
  
  List getRssChannelList() throws Exception, RemoteException;
  
  CategoryChannelVO getSingleRssChannel(String paramString) throws Exception, RemoteException;
  
  List getSingleRssChannelByName(String paramString) throws Exception, RemoteException;
  
  void saveOrUpdateRssChannel(CategoryChannelVO paramCategoryChannelVO) throws Exception, RemoteException;
  
  void saveChannelInfo(ChannelInfoVO paramChannelInfoVO) throws Exception, RemoteException;
  
  void delChannelInfo() throws Exception, RemoteException;
  
  void saveChannelItem(Collection paramCollection) throws Exception, RemoteException;
  
  void delChannelItem(String paramString) throws Exception, RemoteException;
  
  void orderRss(ChannelOrderVO paramChannelOrderVO) throws Exception, RemoteException;
  
  List getMyRssList(String paramString) throws Exception, RemoteException;
  
  List getChannelItemList(String paramString) throws Exception, RemoteException;
  
  List updateItemReadState(String paramString) throws Exception, RemoteException;
  
  void removeRssOrder(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getReadedItemList(String paramString) throws Exception, RemoteException;
  
  void saveOrUpdateItem(ItemStateVO paramItemStateVO) throws Exception, RemoteException;
}
