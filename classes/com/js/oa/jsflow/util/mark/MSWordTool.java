package com.js.oa.jsflow.util.mark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class MSWordTool {
  private XWPFDocument document = null;
  
  private BookMarks bookMarks = null;
  
  public void setTemplate(String templatePath) {
    try {
      this.document = new XWPFDocument(POIXMLDocument.openPackage(templatePath));
      this.bookMarks = new BookMarks(this.document);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void replaceBookMark(Map<String, String> indicator) {
    Iterator<String> bookMarkIter = this.bookMarks.getNameIterator();
    while (bookMarkIter.hasNext()) {
      String bookMarkName = bookMarkIter.next();
      BookMark bookMark = this.bookMarks.getBookmark(bookMarkName);
      if (indicator.get(bookMarkName) != null)
        bookMark.insertTextAtBookMark(indicator.get(bookMarkName), 1); 
    } 
  }
  
  public int fillTableAtBookMark(String bookMarkName, int addRowNum) {
    int rowNum = 0;
    BookMark bookMark = this.bookMarks.getBookmark(bookMarkName);
    if (bookMark.isInTable()) {
      XWPFTable table = bookMark.getContainerTable();
      XWPFTableRow row = bookMark.getContainerTableRow();
      for (int i = 0; i < table.getNumberOfRows(); i++) {
        if (table.getRow(i).equals(row)) {
          rowNum = i;
          break;
        } 
      } 
      XWPFTableRow copyRow = table.getRow(rowNum);
      for (int j = rowNum; j < rowNum + addRowNum; j++)
        table.addRow(copyRow, j); 
      table.removeRow(rowNum);
    } 
    return rowNum;
  }
  
  public void replaceText(Map<String, String> bookmarkMap, String bookMarkName) {
    BookMark bookMark = this.bookMarks.getBookmark(bookMarkName);
    XWPFTable table = bookMark.getContainerTable();
    if (table != null) {
      int rcount = table.getNumberOfRows();
      for (int i = 0; i < rcount; i++) {
        XWPFTableRow row = table.getRow(i);
        List<XWPFTableCell> cells = row.getTableCells();
        for (XWPFTableCell c : cells) {
          if (bookmarkMap.get(c.getText()) != null) {
            String key = c.getText();
            c.removeParagraph(0);
            c.setText(bookmarkMap.get(key));
          } 
        } 
      } 
    } 
  }
  
  public String saveAs(String filePath) {
    File newFile = new File(filePath);
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(newFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
    try {
      this.document.write(fos);
      fos.flush();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return filePath;
  }
  
  public void replaceTextToRow(int rowNum, List<Map<String, String>> context, String bookMarkStr) {
    BookMark bookMark = this.bookMarks.getBookmark(bookMarkStr);
    if (bookMark.isInTable()) {
      XWPFTable table = bookMark.getContainerTable();
      for (int i = rowNum; i < rowNum + context.size(); i++) {
        Map<String, String> contMap = context.get(i - rowNum);
        XWPFTableRow row = table.getRow(i);
        List<XWPFTableCell> cells = row.getTableCells();
        for (XWPFTableCell cell : cells) {
          if (contMap.get(cell.getText()) != null) {
            String key = cell.getText();
            cell.removeParagraph(0);
            XWPFParagraph newPara = new XWPFParagraph(cell.getCTTc().addNewP(), (IBody)cell);
            XWPFRun run = newPara.createRun();
            newPara.setAlignment(ParagraphAlignment.CENTER);
            run.getCTR().addNewRPr().addNewColor().setVal("000000");
            run.setUnderline(UnderlinePatterns.NONE);
            run.setBold(false);
            run.setItalic(false);
            run.setFontFamily("宋体");
            run.setText(contMap.get(key));
          } 
        } 
      } 
    } 
  }
}
