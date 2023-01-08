package com.js.util.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelOperate {
  private static String firstSheetName = "";
  
  public static void main(String[] args) {
    File file = new File("e:\\测试.xls");
    String[][] result = getData(file, 0);
    int rowLength = result.length;
    for (int i = 0; i < rowLength; i++) {
      for (int j = 0; j < (result[i]).length; j++)
        System.out.print(String.valueOf(result[i][j].trim()) + "\t"); 
      System.out.println();
    } 
  }
  
  public static String[][] getData(File file) {
    return getData(file, 0);
  }
  
  public static String[][] getData(File file, int ignoreRows) {
    return getData(file, 0, "").get(firstSheetName);
  }
  
  public static Map<String, String[][]> getData(File file, int ignoreRows, String flag) {
    Map<String, String[][]> map = (Map)new HashMap<String, String>();
    int rowSize = 0;
    BufferedInputStream in = null;
    POIFSFileSystem fs = null;
    HSSFWorkbook wb = null;
    try {
      in = new BufferedInputStream(new FileInputStream(file));
      fs = new POIFSFileSystem(in);
      wb = new HSSFWorkbook(fs);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    HSSFCell cell = null;
    for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
      List<String[]> result = (List)new ArrayList<String>();
      HSSFSheet st = wb.getSheetAt(sheetIndex);
      if (sheetIndex == 0)
        firstSheetName = st.getSheetName(); 
      for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
        HSSFRow row = st.getRow(rowIndex);
        if (row != null) {
          int tempRowSize = row.getLastCellNum() + 1;
          if (tempRowSize > rowSize)
            rowSize = tempRowSize; 
          String[] values = new String[rowSize];
          Arrays.fill((Object[])values, "");
          boolean hasValue = false;
          for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex = (short)(columnIndex + 1)) {
            String value = "";
            cell = row.getCell(columnIndex);
            if (cell != null)
              switch (cell.getCellType()) {
                case 1:
                  value = cell.getStringCellValue();
                  break;
                case 0:
                  if (HSSFDateUtil.isCellDateFormatted((Cell)cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                      value = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
                      break;
                    } 
                    value = "";
                    break;
                  } 
                  value = (new DecimalFormat("0.00")).format(cell.getNumericCellValue());
                  if (value.indexOf(".00") > 0)
                    value = value.substring(0, value.indexOf(".00")); 
                  break;
                case 2:
                  try {
                    value = String.valueOf(cell.getNumericCellValue());
                  } catch (IllegalStateException e) {
                    value = cell.getStringCellValue();
                  } 
                  break;
                case 3:
                  break;
                case 5:
                  value = "";
                  break;
                case 4:
                  value = cell.getBooleanCellValue() ? "Y" : "N";
                  break;
                default:
                  value = "";
                  break;
              }  
            values[columnIndex] = rightTrim(value);
            hasValue = true;
          } 
          if (hasValue)
            result.add(values); 
        } 
      } 
      String[][] returnArray = new String[result.size()][rowSize];
      for (int i = 0; i < returnArray.length; i++)
        returnArray[i] = result.get(i); 
      map.put(st.getSheetName(), returnArray);
    } 
    try {
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return map;
  }
  
  public static String rightTrim(String str) {
    if (str == null)
      return ""; 
    int length = str.length();
    for (int i = length - 1; i >= 0 && 
      str.charAt(i) == ' '; i--)
      length--; 
    return str.substring(0, length);
  }
}
