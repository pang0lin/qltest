package com.js.oa.weixin.common.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.examples.html.HSSFHtmlHelper;
import org.apache.poi.ss.examples.html.HtmlHelper;
import org.apache.poi.ss.examples.html.XSSFHtmlHelper;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel2HtmlUtil {
  private final Workbook wb = null;
  
  private final Appendable output;
  
  private boolean completeHTML;
  
  private Formatter out = null;
  
  private boolean gotBounds;
  
  private int firstColumn;
  
  private int endColumn;
  
  private HtmlHelper helper = null;
  
  private static final String DEFAULTS_CLASS = "excelDefaults";
  
  private static final String COL_HEAD_CLASS = "colHeader";
  
  private static final String ROW_HEAD_CLASS = "rowHeader";
  
  private static final Map<Short, String> ALIGN = mapFor(new Object[] { 
        Short.valueOf((short)1), "left", Short.valueOf((short)2), "center", Short.valueOf((short)3), "right", Short.valueOf((short)4), "left", Short.valueOf((short)5), "left", 
        Short.valueOf((short)6), "center" });
  
  private static final Map<Short, String> VERTICAL_ALIGN = mapFor(new Object[] { Short.valueOf((short)2), "bottom", Short.valueOf((short)1), "middle", Short.valueOf((short)0), "top" });
  
  private static final Map<Short, String> BORDER = mapFor(new Object[] { 
        Short.valueOf((short)9), "dashed 1pt", Short.valueOf((short)11), "dashed 1pt", Short.valueOf((short)3), "dashed 1pt", Short.valueOf((short)7), "dotted 1pt", Short.valueOf((short)6), "double 3pt", 
        Short.valueOf((short)4), "solid 1px", Short.valueOf((short)2), "solid 2pt", Short.valueOf((short)10), "dashed 2pt", Short.valueOf((short)12), "dashed 2pt", Short.valueOf((short)8), "dashed 2pt", 
        Short.valueOf((short)0), "none", Short.valueOf((short)13), "dashed 2pt", Short.valueOf((short)5), "solid 3pt", Short.valueOf((short)1), "dashed 1pt" });
  
  private static <K, V> Map<K, V> mapFor(Object... mapping) {
    Map<K, V> map = new HashMap<K, V>();
    for (int i = 0; i < mapping.length; i += 2)
      map.put((K)mapping[i], (V)mapping[i + 1]); 
    return map;
  }
  
  public static Excel2HtmlUtil create(Workbook wb, Appendable output) {
    return new Excel2HtmlUtil(wb, output);
  }
  
  public static Excel2HtmlUtil create(String path, Appendable output) throws IOException {
    return create(new FileInputStream(path), output);
  }
  
  public static Excel2HtmlUtil create(InputStream in, Appendable output) throws IOException {
    try {
      Workbook wb = WorkbookFactory.create(in);
      return create(wb, output);
    } catch (InvalidFormatException e) {
      throw new IllegalArgumentException(
          "Cannot create workbook from stream", e);
    } 
  }
  
  private Excel2HtmlUtil(Workbook wb, Appendable output) {
    if (wb == null)
      throw new NullPointerException("wb"); 
    if (output == null)
      throw new NullPointerException("output"); 
    this.wb = wb;
    this.output = output;
    setupColorMap();
  }
  
  private void setupColorMap() {
    if (this.wb instanceof HSSFWorkbook) {
      this.helper = (HtmlHelper)new HSSFHtmlHelper((HSSFWorkbook)this.wb);
    } else if (this.wb instanceof XSSFWorkbook) {
      this.helper = (HtmlHelper)new XSSFHtmlHelper((XSSFWorkbook)this.wb);
    } else {
      throw new IllegalArgumentException("unknown workbook type: " + 
          this.wb.getClass().getSimpleName());
    } 
  }
  
  public void setCompleteHTML(boolean completeHTML) {
    this.completeHTML = completeHTML;
  }
  
  public String printPage() throws IOException {
    try {
      ensureOut();
      if (this.completeHTML) {
        this.out.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n", new Object[0]);
        this.out.format("<html>%n", new Object[0]);
        this.out.format("<head>%n", new Object[0]);
        this.out.format("</head>%n", new Object[0]);
        this.out.format("<body>%n", new Object[0]);
      } 
      print();
      if (this.completeHTML) {
        this.out.format("</body>%n", new Object[0]);
        this.out.format("</html>%n", new Object[0]);
      } 
    } finally {
      if (this.out != null)
        this.out.close(); 
      if (this.output instanceof Closeable) {
        Closeable closeable = (Closeable)this.output;
        closeable.close();
      } 
    } 
    return new String("");
  }
  
  public void print() {
    printInlineStyle();
    printSheets();
  }
  
  private void printInlineStyle() {
    this.out.format("<style type=\"text/css\">%n", new Object[0]);
    printStyles();
    this.out.format("</style>%n", new Object[0]);
  }
  
  private void ensureOut() {
    if (this.out == null)
      this.out = new Formatter(this.output); 
  }
  
  public void printStyles() {
    ensureOut();
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(getClass()
            .getResourceAsStream("excelStyle.css")));
      String line;
      while ((line = in.readLine()) != null) {
        this.out.format("%s%n", new Object[] { line });
      } 
    } catch (IOException e) {
      String line;
      throw new IllegalStateException("Reading standard css", line);
    } finally {
      if (in != null)
        try {
          in.close();
        } catch (IOException e) {
          throw new IllegalStateException("Reading standard css", e);
        }  
    } 
    Set<CellStyle> seen = new HashSet<CellStyle>();
    for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
      Sheet sheet = this.wb.getSheetAt(i);
      Iterator<Row> rows = sheet.rowIterator();
      while (rows.hasNext()) {
        Row row = rows.next();
        for (Cell cell : row) {
          CellStyle style = cell.getCellStyle();
          if (!seen.contains(style)) {
            printStyle(style);
            seen.add(style);
          } 
        } 
      } 
    } 
  }
  
  private void printStyle(CellStyle style) {
    this.out.format(".%s .%s {%n", new Object[] { "excelDefaults", styleName(style) });
    styleContents(style);
    this.out.format("}%n", new Object[0]);
  }
  
  private void styleContents(CellStyle style) {
    styleOut("text-align", Short.valueOf(style.getAlignment()), ALIGN);
    styleOut("vertical-align", Short.valueOf(style.getAlignment()), VERTICAL_ALIGN);
    fontStyle(style);
    borderStyles(style);
    this.helper.colorStyles(style, this.out);
  }
  
  private void borderStyles(CellStyle style) {
    styleOut("border-left", Short.valueOf(style.getBorderLeft()), BORDER);
    styleOut("border-right", Short.valueOf(style.getBorderRight()), BORDER);
    styleOut("border-top", Short.valueOf(style.getBorderTop()), BORDER);
    styleOut("border-bottom", Short.valueOf(style.getBorderBottom()), BORDER);
  }
  
  private void fontStyle(CellStyle style) {
    Font font = this.wb.getFontAt(style.getFontIndex());
    if (font.getBoldweight() >= 400)
      this.out.format("  font-weight: bold;%n", new Object[0]); 
    if (font.getItalic())
      this.out.format("  font-style: italic;%n", new Object[0]); 
    int fontheight = font.getFontHeightInPoints();
    if (fontheight == 9)
      fontheight = 10; 
    this.out.format("  font-size: %dpt;%n", new Object[] { Integer.valueOf(fontheight) });
  }
  
  private String styleName(CellStyle style) {
    if (style == null)
      style = this.wb.getCellStyleAt((short)0); 
    StringBuilder sb = new StringBuilder();
    Formatter fmt = new Formatter(sb);
    fmt.format("style_%02x", new Object[] { Short.valueOf(style.getIndex()) });
    return fmt.toString();
  }
  
  private <K> void styleOut(String attr, K key, Map<K, String> mapping) {
    String value = mapping.get(key);
    if (value != null)
      this.out.format("  %s: %s;%n", new Object[] { attr, value }); 
  }
  
  private static int ultimateCellType(Cell c) {
    int type = c.getCellType();
    if (type == 2)
      type = c.getCachedFormulaResultType(); 
    return type;
  }
  
  private void printSheets() {
    ensureOut();
    for (int s = 0; s < this.wb.getNumberOfSheets(); s++) {
      Sheet sheet = this.wb.getSheetAt(s);
      printSheet(sheet, s);
    } 
  }
  
  public void printSheet(Sheet sheet, int s) {
    ensureOut();
    this.out.format("<table style='background:#C6C3C6;border:2px;' ><tr><td><font style='font-weight:900;font-size:24px;' > sheet" + s + "</font></td></tr></table>", new Object[] { "excelDefaults" });
    this.out.format("<table class=%s>%n", new Object[] { "excelDefaults" });
    this.out.format("<table class=%s>%n", new Object[] { "excelDefaults" });
    printCols(sheet);
    printSheetContent(sheet);
    this.out.format("</table>%n", new Object[0]);
  }
  
  private void printCols(Sheet sheet) {
    this.out.format("<col/>%n", new Object[0]);
    ensureColumnBounds(sheet);
    for (int i = this.firstColumn; i < this.endColumn; i++)
      this.out.format("<col/>%n", new Object[0]); 
  }
  
  private void ensureColumnBounds(Sheet sheet) {
    if (this.gotBounds)
      return; 
    Iterator<Row> iter = sheet.rowIterator();
    this.firstColumn = iter.hasNext() ? Integer.MAX_VALUE : 0;
    this.endColumn = 0;
    while (iter.hasNext()) {
      Row row = iter.next();
      short firstCell = row.getFirstCellNum();
      if (firstCell >= 0) {
        this.firstColumn = Math.min(this.firstColumn, firstCell);
        this.endColumn = Math.max(this.endColumn, row.getLastCellNum());
      } 
    } 
    this.gotBounds = true;
  }
  
  private void printColumnHeads() {
    this.out.format("<thead>%n", new Object[0]);
    this.out.format("  <tr class=%s>%n", new Object[] { "colHeader" });
    this.out.format("    <th class=%s>&#x25CA;</th>%n", new Object[] { "colHeader" });
    StringBuilder colName = new StringBuilder();
    for (int i = this.firstColumn; i < this.endColumn; i++) {
      colName.setLength(0);
      int cnum = i;
      while (true) {
        colName.insert(0, (char)(65 + cnum % 26));
        cnum /= 26;
        if (cnum <= 0) {
          this.out.format("    <th class=%s>%s</th>%n", new Object[] { "colHeader", colName });
          break;
        } 
      } 
    } 
    this.out.format("  </tr>%n", new Object[0]);
    this.out.format("</thead>%n", new Object[0]);
  }
  
  private void printSheetContent(Sheet sheet) {
    printColumnHeads();
    this.out.format("<tbody>%n", new Object[0]);
    Iterator<Row> rows = sheet.rowIterator();
    while (rows.hasNext()) {
      Row row = rows.next();
      this.out.format("  <tr>%n", new Object[0]);
      this.out.format("    <td class=%s>%d</td>%n", new Object[] { "rowHeader", 
            Integer.valueOf(row.getRowNum() + 1) });
      for (int i = this.firstColumn; i < this.endColumn; i++) {
        String content = "&nbsp;";
        String attrs = "";
        CellStyle style = null;
        if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {
          Cell cell = row.getCell(i);
          if (cell != null)
            try {
              style = cell.getCellStyle();
              attrs = tagStyle(cell, style);
              CellFormat cf = CellFormat.getInstance(style
                  .getDataFormatString());
              CellFormatResult result = cf.apply(cell);
              content = result.text;
              if (content.equals(""))
                content = "&nbsp;"; 
            } catch (Exception err) {
              err.printStackTrace();
            }  
        } 
        this.out.format("    <td class=%s %s>%s</td>%n", new Object[] { styleName(style), 
              attrs, content });
      } 
      this.out.format("  </tr>%n", new Object[0]);
    } 
    this.out.format("</tbody>%n", new Object[0]);
  }
  
  private String tagStyle(Cell cell, CellStyle style) {
    if (style.getAlignment() == 0)
      switch (ultimateCellType(cell)) {
        case 1:
          return "style=\"text-align: left;\"";
        case 4:
        case 5:
          return "style=\"text-align: center;\"";
      }  
    return "";
  }
  
  public static void main(String[] args) throws Exception {
    Excel2HtmlUtil toHtml = create("C:\\数据库基本信息1.xlsx", new PrintWriter(new FileWriter("C:\\2.html")));
    toHtml.setCompleteHTML(true);
    toHtml.printPage();
  }
}
