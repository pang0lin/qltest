package com.js.oa.zky.util.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ZkyExcelEdit {
  static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  static Map<String, CellStyle> styleMap = new HashMap<String, CellStyle>();
  
  public static void main(String[] args) {
    ZkyExcelEdit ee = new ZkyExcelEdit();
    ee.testWrite("e:/a.xls", "e:/b.xls");
  }
  
  public void testWrite(String srcFilePath, String tarFilePath) {
    FileOutputStream out = null;
    try {
      Workbook book = getExcelWorkbook(srcFilePath);
      Sheet sheet = getSheetByNum(book, 4);
      System.out.println("sheet名称是：" + sheet.getSheetName());
      List<Map<String, String[]>> list = (new ZkyExcelData()).getExcelData();
      int startRow = 3;
      boolean result = writeToExcel(list, sheet, startRow);
      if (result) {
        out = new FileOutputStream(tarFilePath);
        book.write(out);
        System.out.println("文件写入完成！");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public boolean writeToExcel(List<Map<String, String[]>> list, Sheet sheet, int startRow) {
    boolean result = false;
    try {
      Map<String, String[]> map = null;
      Row row = null;
      for (int i = 0; i < list.size(); i++) {
        map = list.get(i);
        row = sheet.getRow(startRow - 1);
        if (row == null)
          row = sheet.createRow(startRow - 1); 
        startRow++;
        Cell cell = null;
        BigDecimal db = null;
        for (String key : map.keySet()) {
          int colNum = Integer.valueOf(key).intValue() - 1;
          String[] value_type = map.get(key);
          String value = value_type[0];
          String style = value_type[1];
          cell = row.getCell(colNum);
          if (cell == null)
            cell = row.createCell(colNum); 
          CellStyle cellStyle = cell.getCellStyle();
          if (style.equals("GENERAL")) {
            cell.setCellValue(value);
          } else {
            if (style.equals("DOUBLE") || style.equals("INT") || style.equals("PERCENT")) {
              db = new BigDecimal(value, MathContext.UNLIMITED);
              cell.setCellValue(db.doubleValue());
            } else if (style.equals("DATE")) {
              Date date = sFormat.parse(value);
              cell.setCellValue(date);
            } 
            cell.setCellStyle(styleMap.get(style));
          } 
          cell.setCellStyle(cellStyle);
        } 
      } 
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Workbook getExcelWorkbook(String filePath) throws IOException {
    Workbook book = null;
    File file = null;
    FileInputStream fis = null;
    try {
      file = new File(filePath);
      if (!file.exists())
        throw new RuntimeException("文件不存在"); 
      fis = new FileInputStream(file);
      book = WorkbookFactory.create(fis);
      initStyleMap(book);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (fis != null)
        fis.close(); 
    } 
    return book;
  }
  
  public Sheet getSheetByNum(Workbook book, int number) {
    Sheet sheet = null;
    try {
      sheet = book.getSheetAt(number - 1);
      if (sheet == null)
        sheet = book.createSheet("Sheet" + number); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return sheet;
  }
  
  public void initStyleMap(Workbook book) {
    DataFormat hssfDF = book.createDataFormat();
    CellStyle doubleStyle = book.createCellStyle();
    doubleStyle.setDataFormat(hssfDF.getFormat("_ * #,##0.00_ ;_ * \\-#,##0.00_ ;_ * \"-\"??_ ;_ @_ "));
    styleMap.put("DOUBLE", doubleStyle);
    CellStyle intStyle = book.createCellStyle();
    intStyle.setDataFormat(hssfDF.getFormat("0"));
    styleMap.put("INT", intStyle);
    CellStyle yyyyMMddStyle = book.createCellStyle();
    yyyyMMddStyle.setDataFormat(hssfDF.getFormat("yyyy-MM-dd"));
    styleMap.put("DATE", yyyyMMddStyle);
    CellStyle percentStyle = book.createCellStyle();
    percentStyle.setDataFormat(hssfDF.getFormat("0.00%"));
    styleMap.put("PERCENT", percentStyle);
  }
}
