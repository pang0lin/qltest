package com.js.oa.jsflow.util.mark;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class BookMarks {
  private HashMap<String, BookMark> _bookmarks = null;
  
  public BookMarks(XWPFDocument document) {
    this._bookmarks = new HashMap<String, BookMark>();
    procParaList(document.getParagraphs());
    List<XWPFTable> tableList = document.getTables();
    for (XWPFTable table : tableList) {
      List<XWPFTableRow> rowList = table.getRows();
      for (XWPFTableRow row : rowList) {
        List<XWPFTableCell> cellList = row.getTableCells();
        for (XWPFTableCell cell : cellList)
          procParaList(cell); 
      } 
    } 
  }
  
  public BookMark getBookmark(String bookmarkName) {
    BookMark bookmark = null;
    if (this._bookmarks.containsKey(bookmarkName))
      bookmark = this._bookmarks.get(bookmarkName); 
    return bookmark;
  }
  
  public Collection<BookMark> getBookmarkList() {
    return this._bookmarks.values();
  }
  
  public Iterator<String> getNameIterator() {
    return this._bookmarks.keySet().iterator();
  }
  
  private void procParaList(XWPFTableCell cell) {
    List<XWPFParagraph> paragraphList = cell.getParagraphs();
    for (XWPFParagraph paragraph : paragraphList) {
      List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();
      for (CTBookmark bookmark : bookmarkList)
        this._bookmarks.put(bookmark.getName(), 
            new BookMark(bookmark, paragraph, cell)); 
    } 
  }
  
  private void procParaList(List<XWPFParagraph> paragraphList, XWPFTableRow tableRow) {
    NamedNodeMap attributes = null;
    Node colFirstNode = null;
    Node colLastNode = null;
    int firstColIndex = 0;
    int lastColIndex = 0;
    for (XWPFParagraph paragraph : paragraphList) {
      List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();
      for (CTBookmark bookmark : bookmarkList) {
        attributes = bookmark.getDomNode().getAttributes();
        if (attributes != null) {
          colFirstNode = attributes.getNamedItem("w:colFirst");
          colLastNode = attributes.getNamedItem("w:colLast");
          if (colFirstNode != null && colLastNode != null) {
            firstColIndex = Integer.parseInt(colFirstNode.getNodeValue());
            lastColIndex = Integer.parseInt(colLastNode.getNodeValue());
            if (firstColIndex == lastColIndex) {
              this._bookmarks.put(bookmark.getName(), 
                  new BookMark(bookmark, paragraph, 
                    tableRow.getCell(firstColIndex)));
              continue;
            } 
            System.out.println("This bookmark " + bookmark.getName() + 
                " identifies a number of cells in the " + 
                "table. That condition is not handled yet.");
            continue;
          } 
          this._bookmarks.put(bookmark.getName(), 
              new BookMark(bookmark, paragraph, tableRow.getCell(1)));
          continue;
        } 
        this._bookmarks.put(bookmark.getName(), 
            new BookMark(bookmark, paragraph, tableRow.getCell(1)));
      } 
    } 
  }
  
  private void procParaList(List<XWPFParagraph> paragraphList) {
    for (XWPFParagraph paragraph : paragraphList) {
      List<CTBookmark> bookmarkList = paragraph.getCTP().getBookmarkStartList();
      for (CTBookmark bookmark : bookmarkList)
        this._bookmarks.put(bookmark.getName(), 
            new BookMark(bookmark, paragraph)); 
    } 
  }
}
