package com.js.oa.hr.finance.service;

import com.js.oa.hr.finance.bean.FFieldEJBBean;
import com.js.oa.hr.finance.bean.FTableEJBBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Workbook;
import jxl.format.Alignment;
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

public class FTableService {
  public String[][] getSimpleTableInfo(String tableName) {
    String[][] list = (String[][])null;
    FTableEJBBean bean = new FTableEJBBean();
    try {
      list = bean.getSimpleTableInfo(tableName);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public String[][] getAllFieldInfo(String tableName) {
    String[][] list = (String[][])null;
    FTableEJBBean bean = new FTableEJBBean();
    try {
      list = bean.getAllFieldInfo(tableName);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public boolean updateFTableinfo(HttpServletRequest request) {
    boolean re = true;
    int rei = 1;
    try {
      FTableEJBBean bean = new FTableEJBBean();
      String tableName = request.getParameter("tableName");
      String tableDesname = request.getParameter("tableDesname");
      String tableId = request.getParameter("tableId");
      HttpSession session = request.getSession(true);
      String userId = session.getAttribute("userId").toString();
      String orgId = session.getAttribute("orgId").toString();
      if (tableName != null && !"".equals(tableName)) {
        re = bean.updateByYourYuanShengSql("update f_table set table_desname='" + tableDesname + "' where table_name='" + tableName + "' and table_id=" + tableId);
        String[] fieldId = request.getParameterValues("fieldId");
        String[] fieldName = request.getParameterValues("fieldName");
        String[] fieldDesName = request.getParameterValues("fieldDesName");
        String[] fieldType = request.getParameterValues("fieldType");
        String[] fieldLen = request.getParameterValues("fieldLen");
        String[] fieldOrder = request.getParameterValues("fieldOrder");
        String[] fieldDefault = request.getParameterValues("fieldDefault");
        String[] fieldListShow = request.getParameterValues("fieldListShow");
        String delFieldSysName = bean.getShouldDelId(tableName, fieldId);
        String oprContent = "";
        if (delFieldSysName != null && !"".equals(delFieldSysName)) {
          oprContent = String.valueOf(oprContent) + "删除字段：" + delFieldSysName + " ";
          bean.batchDeleteField(tableName, delFieldSysName.split(","));
        } 
        if (fieldName != null)
          for (int i = 0; i < fieldName.length; i++) {
            if (fieldName[i] != null && fieldName[i].trim().length() > 0 && (
              "".equals(fieldName[i]) || delFieldSysName.indexOf(fieldName[i]) < 0))
              if ("-1".equals(fieldId[i])) {
                rei = bean.addField(tableName, fieldDesName[i], fieldName[i], fieldType[i], fieldLen[i], "1", fieldDefault[i], 
                    fieldListShow[i], "0", "1", "0", userId, orgId, fieldOrder[i]);
              } else {
                rei = bean.updateSimpleField(fieldId[i], tableName, fieldDesName[i], fieldName[i], fieldType[i], fieldLen[i], "1", fieldDefault[i], 
                    fieldListShow[i], "0", "1", "0", userId, orgId, fieldOrder[i]);
              }  
          }  
      } else {
        re = false;
      } 
      if (re && "f_salary".equals(tableName)) {
        creatExcelForSalary(request);
      } else if (re && "f_tax".equals(tableName)) {
        creatExcelForTax(request);
      } else if (re && "f_expense".equals(tableName)) {
        creatExcelForExpense(request);
      } 
    } catch (Exception e) {
      re = false;
      e.printStackTrace();
    } 
    if (rei != 1)
      re = false; 
    return re;
  }
  
  public static void creatExcelForSalary(HttpServletRequest request) {
    try {
      String tableName = request.getParameter("tableName");
      String fileName = request.getParameter("tableName").replace("_", "");
      String filePath = request.getSession().getServletContext().getRealPath("/template/" + fileName + ".xls");
      List<Object[]> tempList = new ArrayList();
      if ("f_salary".equals(tableName)) {
        FFieldEJBBean bean = new FFieldEJBBean();
        String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType from com.js.oa.hr.finance.po.FField po  where po.tableName='f_salary' and po.fieldIsSys=0 and po.fieldListShow=1 order by po.fieldOrder asc";
        tempList = bean.getListByYourSQL(sql);
      } 
      Object[] element = new Object[4];
      element[0] = "0";
      element[1] = "zhanghao";
      element[2] = "帐号";
      element[3] = "varchar";
      tempList.add(1, element);
      WritableWorkbook book = Workbook.createWorkbook(new File(filePath));
      WritableSheet sheetOne = book.createSheet("用户工资模版", 0);
      WritableFont wf_titleZong = new WritableFont(WritableFont.createFont("宋体"), 16, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.RED);
      WritableCellFormat wcf_title = new WritableCellFormat(wf_titleZong);
      wcf_title.setAlignment(Alignment.LEFT);
      wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);
      String datestr = "请按照每列的说明填写数据内容，严禁修改格式（如删除、修改列标题），任意添加、删除列都会导致数据匹配失败!第一行至第四行为说明和实例不得删除，请从第五行开始填写。";
      Label title = new Label(0, 0, datestr, (CellFormat)wcf_title);
      sheetOne.setRowView(0, 600);
      sheetOne.mergeCells(0, 0, tempList.size() - 1, 0);
      sheetOne.addCell((WritableCell)title);
      String strTwo = "对应字段所在列";
      Label titleTwo = new Label(0, 1, strTwo, (CellFormat)wcf_title);
      sheetOne.setRowView(1, 600);
      sheetOne.mergeCells(1, 0, tempList.size() - 1, 0);
      sheetOne.addCell((WritableCell)titleTwo);
      String strTwo2 = "2";
      Label titleTwo2 = new Label(1, 1, strTwo2, (CellFormat)wcf_title);
      sheetOne.setRowView(1, 600);
      sheetOne.mergeCells(1, 0, 30, 0);
      sheetOne.addCell((WritableCell)titleTwo2);
      WritableFont ziduan = new WritableFont(WritableFont.createFont("宋体"), 12, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_ziduan = new WritableCellFormat(ziduan);
      wcf_ziduan.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_ziduan.setAlignment(Alignment.CENTRE);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          sheetOne.addCell((WritableCell)new Label(i, 2, (String)obj[2], (CellFormat)wcf_ziduan));
          sheetOne.setRowView(2 + i, 500);
          sheetOne.setColumnView(i, 25);
        }  
      WritableFont exzam = new WritableFont(WritableFont.createFont("宋体"), 10, 
          WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_exzam = new WritableCellFormat(exzam);
      wcf_exzam.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_exzam.setAlignment(Alignment.CENTRE);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (i == 0) {
            sheetOne.addCell((WritableCell)new Label(i, 3, "张三", (CellFormat)wcf_exzam));
          } else if (i == 1) {
            sheetOne.addCell((WritableCell)new Label(i, 3, "zhangsan", (CellFormat)wcf_exzam));
          } else {
            sheetOne.addCell((WritableCell)new Label(i, 3, "0", (CellFormat)wcf_exzam));
          } 
          sheetOne.setRowView(2 + i, 500);
          sheetOne.setColumnView(i, 25);
        }  
      book.write();
      book.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void creatExcelForTax(HttpServletRequest request) {
    try {
      String tableName = request.getParameter("tableName");
      String fileName = request.getParameter("tableName").replace("_", "");
      String filePath = request.getSession().getServletContext().getRealPath("/template/" + fileName + ".xls");
      List<Object[]> tempList = new ArrayList();
      if ("f_tax".equals(tableName)) {
        FFieldEJBBean bean = new FFieldEJBBean();
        String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType from com.js.oa.hr.finance.po.FField po  where po.tableName='f_tax' and po.fieldIsSys=0 and po.fieldListShow=1 order by po.fieldOrder asc";
        tempList = bean.getListByYourSQL(sql);
      } 
      Object[] element = new Object[4];
      element[0] = "0";
      element[1] = "zhanghao";
      element[2] = "帐号";
      element[3] = "varchar";
      tempList.add(1, element);
      WritableWorkbook book = Workbook.createWorkbook(new File(filePath));
      WritableSheet sheetOne = book.createSheet("计税管理模版", 0);
      WritableFont wf_titleZong = new WritableFont(WritableFont.createFont("宋体"), 16, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.RED);
      WritableCellFormat wcf_title = new WritableCellFormat(wf_titleZong);
      wcf_title.setAlignment(Alignment.LEFT);
      wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);
      String datestr = "请按照每列的说明填写数据内容，严禁修改格式（如删除、修改列标题），任意添加、删除列都会导致数据匹配失败!第一行至第四行为说明和实例不得删除，请从第五行开始填写。";
      Label title = new Label(0, 0, datestr, (CellFormat)wcf_title);
      sheetOne.setRowView(0, 600);
      sheetOne.mergeCells(0, 0, tempList.size() - 1, 0);
      sheetOne.addCell((WritableCell)title);
      String strTwo = "对应字段所在列";
      Label titleTwo = new Label(0, 1, strTwo, (CellFormat)wcf_title);
      sheetOne.setRowView(1, 600);
      sheetOne.mergeCells(1, 0, tempList.size() - 1, 0);
      sheetOne.addCell((WritableCell)titleTwo);
      String strTwo2 = "2";
      Label titleTwo2 = new Label(1, 1, strTwo2, (CellFormat)wcf_title);
      sheetOne.setRowView(1, 600);
      sheetOne.mergeCells(1, 0, 30, 0);
      sheetOne.addCell((WritableCell)titleTwo2);
      WritableFont ziduan = new WritableFont(WritableFont.createFont("宋体"), 12, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_ziduan = new WritableCellFormat(ziduan);
      wcf_ziduan.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_ziduan.setAlignment(Alignment.CENTRE);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          sheetOne.addCell((WritableCell)new Label(i, 2, (String)obj[2], (CellFormat)wcf_ziduan));
          sheetOne.setRowView(2 + i, 500);
          sheetOne.setColumnView(i, 25);
        }  
      WritableFont exzam = new WritableFont(WritableFont.createFont("宋体"), 10, 
          WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_exzam = new WritableCellFormat(exzam);
      wcf_exzam.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_exzam.setAlignment(Alignment.CENTRE);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (i == 0) {
            sheetOne.addCell((WritableCell)new Label(i, 3, "张三", (CellFormat)wcf_exzam));
          } else if (i == 1) {
            sheetOne.addCell((WritableCell)new Label(i, 3, "zhangsan", (CellFormat)wcf_exzam));
          } else {
            sheetOne.addCell((WritableCell)new Label(i, 3, "0", (CellFormat)wcf_exzam));
          } 
          sheetOne.setRowView(2 + i, 500);
          sheetOne.setColumnView(i, 25);
        }  
      book.write();
      book.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void creatExcelForExpense(HttpServletRequest request) {
    try {
      String tableName = request.getParameter("tableName");
      String fileName = request.getParameter("tableName").replace("_", "");
      String filePath = request.getSession().getServletContext().getRealPath("/template/" + fileName + ".xls");
      List<Object[]> tempList = new ArrayList();
      if ("f_expense".equals(tableName)) {
        FFieldEJBBean bean = new FFieldEJBBean();
        String sql = "select po.fieldId,po.fieldName,po.fieldDesname,po.fieldType from com.js.oa.hr.finance.po.FField po  where po.tableName='f_expense' and po.fieldIsSys=0 and po.fieldListShow=1 order by po.fieldOrder asc";
        tempList = bean.getListByYourSQL(sql);
      } 
      Object[] element = new Object[4];
      element[0] = "0";
      element[1] = "zhanghao";
      element[2] = "帐号";
      element[3] = "varchar";
      tempList.add(1, element);
      WritableWorkbook book = Workbook.createWorkbook(new File(filePath));
      WritableSheet sheetOne = book.createSheet("报销管理模版", 0);
      WritableFont wf_titleZong = new WritableFont(WritableFont.createFont("宋体"), 16, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.RED);
      WritableCellFormat wcf_title = new WritableCellFormat(wf_titleZong);
      wcf_title.setAlignment(Alignment.LEFT);
      wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);
      String datestr = "请按照每列的说明填写数据内容，严禁修改格式（如删除、修改列标题），任意添加、删除列都会导致数据匹配失败!第一行至第四行为说明和实例不得删除，请从第五行开始填写。";
      Label title = new Label(0, 0, datestr, (CellFormat)wcf_title);
      sheetOne.setRowView(0, 600);
      sheetOne.mergeCells(0, 0, tempList.size() - 1, 0);
      sheetOne.addCell((WritableCell)title);
      String strTwo = "对应字段所在列";
      Label titleTwo = new Label(0, 1, strTwo, (CellFormat)wcf_title);
      sheetOne.setRowView(1, 600);
      sheetOne.mergeCells(1, 0, tempList.size() - 1, 0);
      sheetOne.addCell((WritableCell)titleTwo);
      String strTwo2 = "2";
      Label titleTwo2 = new Label(1, 1, strTwo2, (CellFormat)wcf_title);
      sheetOne.setRowView(1, 600);
      sheetOne.mergeCells(1, 0, 30, 0);
      sheetOne.addCell((WritableCell)titleTwo2);
      WritableFont ziduan = new WritableFont(WritableFont.createFont("宋体"), 12, 
          WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_ziduan = new WritableCellFormat(ziduan);
      wcf_ziduan.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_ziduan.setAlignment(Alignment.CENTRE);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          sheetOne.addCell((WritableCell)new Label(i, 2, (String)obj[2], (CellFormat)wcf_ziduan));
          sheetOne.setRowView(2 + i, 500);
          sheetOne.setColumnView(i, 25);
        }  
      WritableFont exzam = new WritableFont(WritableFont.createFont("宋体"), 10, 
          WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
          Colour.BLACK);
      WritableCellFormat wcf_exzam = new WritableCellFormat(exzam);
      wcf_exzam.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_exzam.setAlignment(Alignment.CENTRE);
      if (tempList != null && tempList.size() > 0)
        for (int i = 0; i < tempList.size(); i++) {
          Object[] obj = tempList.get(i);
          if (i == 0) {
            sheetOne.addCell((WritableCell)new Label(i, 3, "张三", (CellFormat)wcf_exzam));
          } else if (i == 1) {
            sheetOne.addCell((WritableCell)new Label(i, 3, "zhangsan", (CellFormat)wcf_exzam));
          } else {
            sheetOne.addCell((WritableCell)new Label(i, 3, "0", (CellFormat)wcf_exzam));
          } 
          sheetOne.setRowView(2 + i, 500);
          sheetOne.setColumnView(i, 25);
        }  
      book.write();
      book.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
