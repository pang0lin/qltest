package com.js.oa.jsflow.util.mark;

import java.util.List;
import java.util.Stack;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BookMark {
  public static final int INSERT_AFTER = 0;
  
  public static final int INSERT_BEFORE = 1;
  
  public static final int REPLACE = 2;
  
  public static final String RUN_NODE_NAME = "w:r";
  
  public static final String TEXT_NODE_NAME = "w:t";
  
  public static final String BOOKMARK_START_TAG = "bookmarkStart";
  
  public static final String BOOKMARK_END_TAG = "bookmarkEnd";
  
  public static final String BOOKMARK_ID_ATTR_NAME = "w:id";
  
  public static final String STYLE_NODE_NAME = "w:rPr";
  
  private CTBookmark _ctBookmark = null;
  
  private XWPFParagraph _para = null;
  
  private XWPFTableCell _tableCell = null;
  
  private String _bookmarkName = null;
  
  private boolean _isCell = false;
  
  public BookMark(CTBookmark ctBookmark, XWPFParagraph para) {
    this._ctBookmark = ctBookmark;
    this._para = para;
    this._bookmarkName = ctBookmark.getName();
    this._tableCell = null;
    this._isCell = false;
  }
  
  public BookMark(CTBookmark ctBookmark, XWPFParagraph para, XWPFTableCell tableCell) {
    this(ctBookmark, para);
    this._tableCell = tableCell;
    this._isCell = true;
  }
  
  public boolean isInTable() {
    return this._isCell;
  }
  
  public XWPFTable getContainerTable() {
    return this._tableCell.getTableRow().getTable();
  }
  
  public XWPFTableRow getContainerTableRow() {
    return this._tableCell.getTableRow();
  }
  
  public String getBookmarkName() {
    return this._bookmarkName;
  }
  
  public void insertTextAtBookMark(String bookmarkValue, int where) {
    if (this._isCell) {
      handleBookmarkedCells(bookmarkValue, where);
    } else {
      XWPFRun run = this._para.createRun();
      run.setText(bookmarkValue);
      switch (where) {
        case 0:
          insertAfterBookmark(run);
          break;
        case 1:
          insertBeforeBookmark(run);
          break;
        case 2:
          replaceBookmark(run);
          break;
      } 
    } 
  }
  
  private void insertAfterBookmark(XWPFRun run) {
    Node nextNode = null;
    Node insertBeforeNode = null;
    Node styleNode = null;
    int bookmarkStartID = 0;
    int bookmarkEndID = -1;
    bookmarkStartID = this._ctBookmark.getId().intValue();
    nextNode = this._ctBookmark.getDomNode();
    while (bookmarkStartID != bookmarkEndID) {
      nextNode = nextNode.getNextSibling();
      if (nextNode.getNodeName().contains("bookmarkEnd")) {
        try {
          bookmarkEndID = Integer.parseInt(
              nextNode.getAttributes().getNamedItem(
                "w:id").getNodeValue());
        } catch (NumberFormatException nfe) {
          bookmarkEndID = bookmarkStartID;
        } 
        continue;
      } 
      if (nextNode.getNodeName().equals("w:r"))
        styleNode = getStyleNode(nextNode); 
    } 
    insertBeforeNode = nextNode.getNextSibling();
    if (styleNode != null)
      run.getCTR().getDomNode().insertBefore(
          styleNode.cloneNode(true), run.getCTR().getDomNode().getFirstChild()); 
    if (insertBeforeNode != null)
      this._para.getCTP().getDomNode().insertBefore(
          run.getCTR().getDomNode(), insertBeforeNode); 
  }
  
  private void insertBeforeBookmark(XWPFRun run) {
    Node insertBeforeNode = null;
    Node childNode = null;
    Node styleNode = null;
    insertBeforeNode = this._ctBookmark.getDomNode();
    childNode = insertBeforeNode.getPreviousSibling();
    if (childNode != null) {
      styleNode = getStyleNode(childNode);
      if (styleNode != null)
        run.getCTR().getDomNode().insertBefore(
            styleNode.cloneNode(true), run.getCTR().getDomNode().getFirstChild()); 
    } 
    this._para.getCTP().getDomNode().insertBefore(
        run.getCTR().getDomNode(), insertBeforeNode);
  }
  
  private void replaceBookmark(XWPFRun run) {
    Node nextNode = null;
    Node styleNode = null;
    Node lastRunNode = null;
    Node toDelete = null;
    NodeList childNodes = null;
    Stack<Node> nodeStack = null;
    boolean textNodeFound = false;
    boolean foundNested = true;
    int bookmarkStartID = 0;
    int bookmarkEndID = -1;
    int numChildNodes = 0;
    nodeStack = new Stack<Node>();
    bookmarkStartID = this._ctBookmark.getId().intValue();
    nextNode = this._ctBookmark.getDomNode();
    nodeStack.push(nextNode);
    while (bookmarkStartID != bookmarkEndID) {
      nextNode = nextNode.getNextSibling();
      nodeStack.push(nextNode);
      if (nextNode.getNodeName().contains("bookmarkEnd"))
        try {
          bookmarkEndID = Integer.parseInt(
              nextNode.getAttributes().getNamedItem(
                "w:id").getNodeValue());
        } catch (NumberFormatException nfe) {
          bookmarkEndID = bookmarkStartID;
        }  
    } 
    if (!nodeStack.isEmpty()) {
      lastRunNode = nodeStack.peek();
      if (lastRunNode.getNodeName().equals("w:r")) {
        styleNode = getStyleNode(lastRunNode);
        if (styleNode != null)
          run.getCTR().getDomNode().insertBefore(
              styleNode.cloneNode(true), run.getCTR().getDomNode().getFirstChild()); 
      } 
      deleteChildNodes(nodeStack);
    } 
    this._para.getCTP().getDomNode().insertBefore(
        run.getCTR().getDomNode(), nextNode);
  }
  
  private void deleteChildNodes(Stack<Node> nodeStack) {
    Node toDelete = null;
    int bookmarkStartID = 0;
    int bookmarkEndID = 0;
    boolean inNestedBookmark = false;
    for (int i = 1; i < nodeStack.size(); i++) {
      toDelete = nodeStack.elementAt(i);
      if (toDelete.getNodeName().contains("bookmarkStart")) {
        bookmarkStartID = Integer.parseInt(
            toDelete.getAttributes().getNamedItem("w:id").getNodeValue());
        inNestedBookmark = true;
      } else if (toDelete.getNodeName().contains("bookmarkEnd")) {
        bookmarkEndID = Integer.parseInt(
            toDelete.getAttributes().getNamedItem("w:id").getNodeValue());
        if (bookmarkEndID == bookmarkStartID)
          inNestedBookmark = false; 
      } else if (!inNestedBookmark) {
        this._para.getCTP().getDomNode().removeChild(toDelete);
      } 
    } 
  }
  
  private Node getStyleNode(Node parentNode) {
    Node childNode = null;
    Node styleNode = null;
    if (parentNode != null)
      if (parentNode.getNodeName().equalsIgnoreCase("w:r") && 
        parentNode.hasChildNodes()) {
        childNode = parentNode.getFirstChild();
        if (childNode.getNodeName().equals("w:rPr")) {
          styleNode = childNode;
        } else {
          while ((childNode = childNode.getNextSibling()) != null) {
            if (childNode.getNodeName().equals("w:rPr")) {
              styleNode = childNode;
              childNode = null;
            } 
          } 
        } 
      }  
    return styleNode;
  }
  
  public String getBookmarkText() throws XmlException {
    StringBuilder builder = null;
    if (this._tableCell != null) {
      builder = new StringBuilder(this._tableCell.getText());
    } else {
      builder = getTextFromBookmark();
    } 
    return (builder == null) ? null : builder.toString();
  }
  
  private StringBuilder getTextFromBookmark() throws XmlException {
    int startBookmarkID = 0;
    int endBookmarkID = -1;
    Node nextNode = null;
    Node childNode = null;
    CTText text = null;
    StringBuilder builder = null;
    String rawXML = null;
    startBookmarkID = this._ctBookmark.getId().intValue();
    nextNode = this._ctBookmark.getDomNode();
    builder = new StringBuilder();
    while (startBookmarkID != endBookmarkID) {
      nextNode = nextNode.getNextSibling();
      if (nextNode.getNodeName().contains("bookmarkEnd")) {
        try {
          endBookmarkID = Integer.parseInt(
              nextNode.getAttributes()
              .getNamedItem("w:id").getNodeValue());
        } catch (NumberFormatException nfe) {
          endBookmarkID = startBookmarkID;
        } 
        continue;
      } 
      if (nextNode.getNodeName().equals("w:r") && 
        nextNode.hasChildNodes())
        builder.append(getTextFromChildNodes(nextNode)); 
    } 
    return builder;
  }
  
  private String getTextFromChildNodes(Node node) throws XmlException {
    NodeList childNodes = null;
    Node childNode = null;
    CTText text = null;
    StringBuilder builder = new StringBuilder();
    int numChildNodes = 0;
    childNodes = node.getChildNodes();
    numChildNodes = childNodes.getLength();
    for (int i = 0; i < numChildNodes; i++) {
      childNode = childNodes.item(i);
      if (childNode.getNodeName().equals("w:t"))
        if (childNode.getNodeType() == 3) {
          builder.append(childNode.getNodeValue());
        } else {
          text = CTText.Factory.parse(childNode);
          builder.append(text.getStringValue());
        }  
    } 
    return builder.toString();
  }
  
  private void handleBookmarkedCells(String bookmarkValue, int where) {
    List<XWPFParagraph> paraList = null;
    List<XWPFRun> runs = null;
    XWPFParagraph para = null;
    XWPFRun readRun = null;
    paraList = this._tableCell.getParagraphs();
    for (int i = 0; i < paraList.size(); i++)
      this._tableCell.removeParagraph(i); 
    para = this._tableCell.addParagraph();
    para.createRun().setText(bookmarkValue);
  }
}
