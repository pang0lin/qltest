package com.js.oa.jsflow.util;

import java.io.File;
import java.util.List;
import java.util.Map;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CreateFlowReportExcel {
  public static void creatExcel(String filePath, List<Map<String, Object>> contList, int maxPointNum, String dateStr) {
    try {
      WritableWorkbook book = Workbook.createWorkbook(new File(filePath));
      WritableSheet sheetOne = book.createSheet("流程用时统计报表", 0);
      WritableFont wf_titleZong = new WritableFont(WritableFont.createFont("宋体"), 18, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableFont wf_title = new WritableFont(WritableFont.createFont("宋体"), 10, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_title = new WritableCellFormat(wf_titleZong);
      wcf_title.setAlignment(Alignment.CENTRE);
      wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);
      WritableCellFormat wcf_title_sub = new WritableCellFormat(wf_title);
      wcf_title_sub.setAlignment(Alignment.CENTRE);
      wcf_title_sub.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_title_sub.setWrap(true);
      wcf_title_sub.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      Label title = new Label(0, 0, "流程用时统计报表" + dateStr, (CellFormat)wcf_title);
      sheetOne.setRowView(0, 500);
      sheetOne.mergeCells(0, 0, 5 * maxPointNum + 7, 0);
      Label title0 = new Label(0, 1, "序号", (CellFormat)wcf_title_sub);
      sheetOne.setColumnView(0, 7);
      sheetOne.mergeCells(0, 1, 0, 2);
      Label title1 = new Label(1, 1, "标题", (CellFormat)wcf_title_sub);
      sheetOne.setColumnView(1, 40);
      sheetOne.mergeCells(1, 1, 1, 2);
      Label title2 = new Label(2, 1, "提交人", (CellFormat)wcf_title_sub);
      sheetOne.mergeCells(2, 1, 2, 2);
      Label title3 = new Label(3, 1, "提交时间", (CellFormat)wcf_title_sub);
      sheetOne.setColumnView(3, 15);
      sheetOne.mergeCells(3, 1, 3, 2);
      Label title4 = new Label(4, 1, "结束时间", (CellFormat)wcf_title_sub);
      sheetOne.setColumnView(4, 15);
      sheetOne.mergeCells(4, 1, 4, 2);
      Label title5 = new Label(5, 1, "总用时\n(小时)", (CellFormat)wcf_title_sub);
      sheetOne.mergeCells(5, 1, 5, 2);
      Label title6 = new Label(6, 1, "标准时间\n(小时)", (CellFormat)wcf_title_sub);
      sheetOne.mergeCells(6, 1, 6, 2);
      Label title7 = new Label(7, 1, "差额\n(小时)", (CellFormat)wcf_title_sub);
      sheetOne.setColumnView(7, 8);
      sheetOne.mergeCells(7, 1, 7, 2);
      sheetOne.addCell((WritableCell)title);
      sheetOne.addCell((WritableCell)title0);
      sheetOne.addCell((WritableCell)title1);
      sheetOne.addCell((WritableCell)title2);
      sheetOne.addCell((WritableCell)title3);
      sheetOne.addCell((WritableCell)title4);
      sheetOne.addCell((WritableCell)title5);
      sheetOne.addCell((WritableCell)title6);
      sheetOne.addCell((WritableCell)title7);
      Label titlePoint = new Label(8, 1, "流 程 步 骤", (CellFormat)wcf_title_sub);
      sheetOne.mergeCells(8, 1, 5 * maxPointNum + 7, 1);
      sheetOne.addCell((WritableCell)titlePoint);
      int columnB = 8;
      int columnE = 12;
      for (int p = 0; p < maxPointNum; p++) {
        Label titlePoint_sub = new Label(columnB, 2, "步骤" + (p + 1), (CellFormat)wcf_title_sub);
        sheetOne.mergeCells(columnB, 2, columnE, 2);
        sheetOne.addCell((WritableCell)titlePoint_sub);
        columnB += 5;
        columnE += 5;
      } 
      WritableFont wf_head = new WritableFont(WritableFont.ARIAL, 10, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableFont wf_table = new WritableFont(WritableFont.ARIAL, 10, 
          WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableFont negative_wf_table = new WritableFont(WritableFont.ARIAL, 10, 
          WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.RED);
      WritableCellFormat wcf_hebing = new WritableCellFormat(wf_table);
      wcf_hebing.setAlignment(Alignment.CENTRE);
      wcf_hebing.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_hebing.setWrap(true);
      wcf_hebing.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      WritableCellFormat negative_wcf_hebing = new WritableCellFormat(negative_wf_table);
      negative_wcf_hebing.setAlignment(Alignment.CENTRE);
      negative_wcf_hebing.setVerticalAlignment(VerticalAlignment.CENTRE);
      negative_wcf_hebing.setWrap(true);
      negative_wcf_hebing.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      WritableCellFormat wcf_table = new WritableCellFormat(wf_head);
      wcf_table.setAlignment(Alignment.CENTRE);
      wcf_table.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
      int rowNum = 3;
      int endRow = 6;
      for (int i = 0; i < contList.size(); i++) {
        Map<String, Object> listMap = contList.get(i);
        Label xuhao = new Label(0, rowNum, (new StringBuilder(String.valueOf(i + 1))).toString(), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(0, rowNum, 0, endRow);
        sheetOne.addCell((WritableCell)xuhao);
        Label biaoti = new Label(1, rowNum, (String)listMap.get("flowTitle"), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(1, rowNum, 1, endRow);
        sheetOne.addCell((WritableCell)biaoti);
        Label tijiaoren = new Label(2, rowNum, (String)listMap.get("submitName"), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(2, rowNum, 2, endRow);
        sheetOne.addCell((WritableCell)tijiaoren);
        Label tijiaoDate = new Label(3, rowNum, (String)listMap.get("submitTime"), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(3, rowNum, 3, endRow);
        sheetOne.addCell((WritableCell)tijiaoDate);
        Label jieshuDate = new Label(4, rowNum, (String)listMap.get("endTime"), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(4, rowNum, 4, endRow);
        sheetOne.addCell((WritableCell)jieshuDate);
        Label zongyongshi = new Label(5, rowNum, (String)listMap.get("zongTime"), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(5, rowNum, 5, endRow);
        sheetOne.addCell((WritableCell)zongyongshi);
        Label biaozhunshijian = new Label(6, rowNum, (String)listMap.get("biaoTime"), (CellFormat)wcf_hebing);
        sheetOne.mergeCells(6, rowNum, 6, endRow);
        sheetOne.addCell((WritableCell)biaozhunshijian);
        Label chae = new Label(7, rowNum, (String)listMap.get("chaeTime"), 
            listMap.get("chaeTime").startsWith("-") ? (CellFormat)negative_wcf_hebing : (CellFormat)wcf_hebing);
        sheetOne.mergeCells(7, rowNum, 7, endRow);
        sheetOne.addCell((WritableCell)chae);
        List<Map<String, String>> pointList = (List<Map<String, String>>)listMap.get("pointMap");
        int columnB_c = 8;
        for (int j = 0; j < pointList.size(); j++) {
          Map<String, String> pointMap = pointList.get(j);
          Label buzhoumingcheng = new Label(columnB_c, rowNum, "步骤名称", (CellFormat)wcf_table);
          sheetOne.setColumnView(columnB_c, 10);
          sheetOne.addCell((WritableCell)buzhoumingcheng);
          Label jiedianming = new Label(columnB_c + 1, rowNum, pointMap.get("pointName"), (CellFormat)wcf_hebing);
          sheetOne.setColumnView(columnB_c + 1, 20);
          sheetOne.addCell((WritableCell)jiedianming);
          Label biaozhunshijian_sub = new Label(columnB_c + 2, rowNum, "标准时间(小时)", (CellFormat)wcf_table);
          sheetOne.setColumnView(columnB_c + 2, 15);
          sheetOne.addCell((WritableCell)biaozhunshijian_sub);
          Label shijishijian_sub = new Label(columnB_c + 3, rowNum, "实际时间(小时)", (CellFormat)wcf_table);
          sheetOne.setColumnView(columnB_c + 3, 15);
          sheetOne.addCell((WritableCell)shijishijian_sub);
          Label chae_sub = new Label(columnB_c + 4, rowNum, "差额(小时)", (CellFormat)wcf_table);
          sheetOne.setColumnView(columnB_c + 4, 11);
          sheetOne.addCell((WritableCell)chae_sub);
          Label xingming_sub = new Label(columnB_c, rowNum + 1, "姓　　名", (CellFormat)wcf_table);
          sheetOne.addCell((WritableCell)xingming_sub);
          Label name_sub = new Label(columnB_c + 1, rowNum + 1, pointMap.get("dealName"), (CellFormat)wcf_hebing);
          sheetOne.addCell((WritableCell)name_sub);
          Label jieshouTime_sub = new Label(columnB_c, rowNum + 2, "接收时间", (CellFormat)wcf_table);
          sheetOne.addCell((WritableCell)jieshouTime_sub);
          Label jieshouTime = new Label(columnB_c + 1, rowNum + 2, pointMap.get("receiveTime"), (CellFormat)wcf_hebing);
          sheetOne.addCell((WritableCell)jieshouTime);
          Label chuliTime_sub = new Label(columnB_c, rowNum + 3, "处理时间", (CellFormat)wcf_table);
          sheetOne.addCell((WritableCell)chuliTime_sub);
          Label chuliTime = new Label(columnB_c + 1, rowNum + 3, pointMap.get("dealTime"), (CellFormat)wcf_hebing);
          sheetOne.addCell((WritableCell)chuliTime);
          Label biaozhun_sub = new Label(columnB_c + 2, rowNum + 1, pointMap.get("pointDoTime"), (CellFormat)wcf_hebing);
          sheetOne.mergeCells(columnB_c + 2, rowNum + 1, columnB_c + 2, rowNum + 3);
          sheetOne.addCell((WritableCell)biaozhun_sub);
          Label shiji_sub = new Label(columnB_c + 3, rowNum + 1, pointMap.get("pointAllTime"), (CellFormat)wcf_hebing);
          sheetOne.mergeCells(columnB_c + 3, rowNum + 1, columnB_c + 3, rowNum + 3);
          sheetOne.addCell((WritableCell)shiji_sub);
          Label chae_s = new Label(columnB_c + 4, rowNum + 1, pointMap.get("pointChaTime"), 
              ((String)pointMap.get("pointChaTime")).startsWith("-") ? (CellFormat)negative_wcf_hebing : (CellFormat)wcf_hebing);
          sheetOne.mergeCells(columnB_c + 4, rowNum + 1, columnB_c + 4, rowNum + 3);
          sheetOne.addCell((WritableCell)chae_s);
          columnB_c += 5;
          pointMap.clear();
        } 
        rowNum += 4;
        endRow += 4;
        pointList.clear();
        listMap.clear();
      } 
      contList.clear();
      book.write();
      book.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
