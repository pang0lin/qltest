package com.js.oa.search.client;

import com.js.oa.search.model.Page;
import com.js.oa.search.model.SearchTbModel;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class SearchClient extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    SearchClientForm searchClientForm = (SearchClientForm)actionForm;
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String keys = searchClientForm.getKeys();
    String classSearch = request.getParameter("classSearch");
    Page pagetemp = new Page();
    String pagerOffset = request.getParameter("pager.offset");
    if (pagerOffset == null)
      pagerOffset = "0"; 
    int offset = Integer.valueOf(pagerOffset).intValue();
    int jumpPge = offset / pagetemp.getRowsPerPage();
    if (jumpPge < 0)
      jumpPge = 0; 
    if (keys == null || "".equals(keys))
      keys = request.getParameter("keys"); 
    if (keys != null)
      keys = keys.trim(); 
    String forwards = "success";
    try {
      Page page = search(keys, String.valueOf(jumpPge + 1), userId, orgId, classSearch);
      request.setAttribute("searchPage", page);
      request.setAttribute("keys", keys);
      request.setAttribute("classSearch", classSearch);
      request.setAttribute("recordCount", String.valueOf(page.getMaxRowCount()));
      request.setAttribute("maxPageItems", String.valueOf(page.getRowsPerPage()));
      request.setAttribute("pageParameters", "keys,classSearch");
    } catch (AxisFault e) {
      e.printStackTrace();
    } 
    return actionMapping.findForward(forwards);
  }
  
  public Page search(String keys, String topage, String userId, String orgId, String classSearch) throws AxisFault {
    SearchServiceImpl.getInstance();
    RPCServiceClient serviceClient = SearchServiceImpl.getServiceClient();
    SearchServiceImpl.getInstance();
    String ifActiveUpdateDelete = SearchServiceImpl.getIfActiveUpdateDelete();
    String method = "";
    if ("no".equals(ifActiveUpdateDelete)) {
      method = "searchOnlyIndex";
    } else {
      method = "searchOnlyIndex";
    } 
    Options options = serviceClient.getOptions();
    options.setTimeOutInMilliSeconds(40000L);
    EndpointReference targetEPR = SearchServiceImpl.getTargetEPR();
    options.setTo(targetEPR);
    Object[] opAddEntryArgs2 = { keys, topage, userId, orgId, classSearch };
    Class[] returnTypes = { String.class };
    QName opAddEntry2 = new QName("http://service.webService.js.com", method);
    Page page = new Page();
    String strXml = (String)serviceClient.invokeBlocking(opAddEntry2, opAddEntryArgs2, returnTypes)[0];
    StringReader read = new StringReader(strXml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      Element rootPage = root.getChild("PageInfor");
      page.setCurPage(Integer.valueOf(rootPage.getChildText("curPage")).intValue());
      page.setMaxPage(Integer.valueOf(rootPage.getChildText("maxPage")).intValue());
      page.setMaxRowCount(Long.valueOf(rootPage.getChildText("maxRowCount")));
      page.setRowsPerPage(Integer.valueOf(rootPage.getChildText("rowsPerPage")).intValue());
      Element rootDataList = root.getChild("DataList");
      List<Element> list = rootDataList.getChildren("Node");
      if (list != null) {
        SearchTbModel[] arr = new SearchTbModel[list.size()];
        for (int i = 0; i < list.size(); i++) {
          Element node = list.get(i);
          SearchTbModel model = new SearchTbModel();
          model.setId(node.getChildText("id"));
          model.setTitle(node.getChildText("title"));
          model.setContent(node.getChildText("content"));
          model.setUrl(node.getChildText("url"));
          model.setDate(node.getChildText("date"));
          arr[i] = model;
        } 
        page.setArrmodel(arr);
      } 
      serviceClient.cleanup();
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return page;
  }
}
