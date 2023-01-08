package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmpStatisticsBD;
import com.js.oa.hr.personnelmanager.service.PersonalKindBD;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;

public class EmpStatisticsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    String yearMonth = httpServletRequest.getParameter("yearMonth");
    httpServletRequest.setAttribute("yearMonth", yearMonth);
    if ("statistics".equals(action))
      httpServletRequest.setAttribute("listPersonalKind", (
          
          new PersonalKindBD()).list()); 
    if ("listEmpChange".equals(action))
      listEmpChange(httpServletRequest, 30); 
    if ("listEmpChangeExport".equals(action))
      listEmpChange(httpServletRequest, 999999999); 
    if ("listEmpStruct".equals(action))
      listEmpStruct(httpServletRequest, 30); 
    if ("listEmpStructExport".equals(action))
      listEmpStruct(httpServletRequest, 999999999); 
    if ("listEmpCizhi".equals(action))
      listEmpCizhi(httpServletRequest, 30); 
    if ("listEmpCizhiExport".equals(action))
      listEmpCizhi(httpServletRequest, 999999999); 
    if ("listEmpZhuanzheng".equals(action))
      listEmpZhuanzheng(httpServletRequest, 30); 
    if ("listEmpZhuanzhengExport".equals(action))
      listEmpZhuanzheng(httpServletRequest, 999999999); 
    if ("chart".equals(action)) {
      String type = httpServletRequest.getParameter("chartType");
      String filename = "";
      if ("0".equals(type)) {
        httpServletRequest.setAttribute("filename", getPie(httpServletRequest));
        return actionMapping.findForward("pieChart");
      } 
      if ("1".equals(type)) {
        httpServletRequest.setAttribute("filename", getBar(httpServletRequest));
        return actionMapping.findForward("histogramChart");
      } 
      return actionMapping.findForward("error");
    } 
    return actionMapping.findForward(action);
  }
  
  public void listEmpChange(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map result = null;
    List list = null;
    EmpStatisticsBD bd = new EmpStatisticsBD();
    result = bd.listEmpChange(session.getAttribute("orgId").toString(), session.getAttribute("userId").toString(), "07*55*05", yearMonth, null, new Integer(pageSize), new Integer(currentPage));
    list = (List)result.get("list");
    List list2 = (List)result.get("list2");
    try {
      recordCount = ((Integer)result.get("recordCount")).intValue();
    } catch (Exception e) {
      list = new ArrayList();
      list2 = new ArrayList();
      recordCount = 0;
    } 
    request.setAttribute("list", list);
    request.setAttribute("list2", list2);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,yearMonth");
  }
  
  public void listEmpStruct(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    request.setAttribute("listPersonalKind", (new PersonalKindBD()).list());
    String yearMonth = request.getParameter("yearMonth");
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map result = null;
    List list = null;
    EmpStatisticsBD bd = new EmpStatisticsBD();
    result = bd.listEmpStruct(session.getAttribute("orgId").toString(), session.getAttribute("userId").toString(), "07*55*05", yearMonth, null, new Integer(pageSize), 
        new Integer(currentPage));
    list = (List)result.get("list");
    List list2 = (List)result.get("list2");
    try {
      recordCount = ((Integer)result.get("recordCount")).intValue();
    } catch (Exception e) {
      list = new ArrayList();
      list2 = new ArrayList();
      recordCount = 0;
    } 
    request.setAttribute("list", list);
    request.setAttribute("list2", list2);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,yearMonth");
  }
  
  public void listEmpCizhi(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map result = null;
    List list = null;
    EmpStatisticsBD bd = new EmpStatisticsBD();
    result = bd.listEmpCizhi(session.getAttribute("orgId").toString(), session.getAttribute("userId").toString(), "07*55*05", yearMonth, null, new Integer(pageSize), 
        new Integer(currentPage));
    list = (List)result.get("list");
    try {
      recordCount = ((Integer)result.get("recordCount")).intValue();
    } catch (Exception e) {
      list = new ArrayList();
      recordCount = 0;
    } 
    request.setAttribute("list", list);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,yearMonth");
  }
  
  public void listEmpZhuanzheng(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map result = null;
    List list = null;
    EmpStatisticsBD bd = new EmpStatisticsBD();
    result = bd.listEmpZhuanzheng(session.getAttribute("orgId").toString(), session.getAttribute("userId").toString(), "07*55*05", yearMonth, null, new Integer(pageSize), 
        new Integer(currentPage));
    list = (List)result.get("list");
    List list2 = (List)result.get("list2");
    try {
      recordCount = ((Integer)result.get("recordCount")).intValue();
    } catch (Exception e) {
      list = new ArrayList();
      list2 = new ArrayList();
      recordCount = 0;
    } 
    request.setAttribute("list", list);
    request.setAttribute("list2", list2);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,yearMonth");
  }
  
  private DefaultCategoryDataset getBarDataSet(HttpServletRequest request) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    String[] values = request.getParameterValues("v");
    String[] categories = request.getParameterValues("c");
    String[] items = request.getParameterValues("i");
    try {
      for (int i = 0; i < values.length; i++)
        dataset.addValue(
            Double.parseDouble(values[i]), 

            
            categories[i], items[i]); 
    } catch (Exception ex) {
      System.out.println(ex);
    } 
    return dataset;
  }
  
  private String[] getPie(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    int pSize = 0;
    List<Object[]> pkl = (new PersonalKindBD()).list();
    if (pkl != null && pkl.size() > 0)
      pSize = 1; 
    String[] fileNames = new String[5 + pSize];
    Map result = null;
    EmpStatisticsBD bd = new EmpStatisticsBD();
    result = bd.listEmpStruct(session.getAttribute("orgId").toString(), 
        session.getAttribute("userId").toString(), 
        "07*55*05", yearMonth, null, 
        new Integer(999999999), 
        new Integer(1));
    List<Object[]> list2 = (List)result.get("list2");
    Object[] o = list2.get(0);
    DefaultPieDataset dataset = new DefaultPieDataset();
    String[] title0 = { "硕士及以上", "本科", "专科", "中专", "高中", "初中及以下" };
    for (int i = 0; i < 6; i++)
      dataset.setValue(title0[i], Double.parseDouble(String.valueOf(o[i + 1]))); 
    fileNames[0] = getFileName("按学历分类比例分析图", dataset, session);
    dataset = new DefaultPieDataset();
    String[] title1 = { "高级", "中级", "初级", "无" };
    for (int j = 6; j < 10; j++)
      dataset.setValue(title1[j - 6], Double.parseDouble(String.valueOf(o[j + 1]))); 
    fileNames[1] = getFileName("按职称分比例分析图", dataset, session);
    dataset = new DefaultPieDataset();
    String[] title2 = { "5年以上", "3-5年", "1-3年", "1年以下" };
    for (int k = 10; k < 14; k++)
      dataset.setValue(title2[k - 10], Double.parseDouble(String.valueOf(o[k + 1]))); 
    fileNames[2] = getFileName("按公司工作年限分比例分析图", dataset, session);
    dataset = new DefaultPieDataset();
    String[] title3 = { "40岁以上", "30-39岁", "20-29岁", "19岁以下" };
    for (int m = 14; m < 18; m++)
      dataset.setValue(title3[m - 14], Double.parseDouble(String.valueOf(o[m + 1]))); 
    fileNames[3] = getFileName("按年龄分比例分析图", dataset, session);
    dataset = new DefaultPieDataset();
    String[] title4 = { "男", "女" };
    int n;
    for (n = 18; n < 20; n++)
      dataset.setValue(title4[n - 18], Double.parseDouble(String.valueOf(o[n + 1]))); 
    fileNames[4] = getFileName("按性别分比例分析图", dataset, session);
    if (pSize > 0) {
      dataset = new DefaultPieDataset();
      for (n = 20; n < 20 + pkl.size(); n++) {
        Object[] obj = pkl.get(n - 20);
        dataset.setValue(String.valueOf(obj[1]), Double.parseDouble(String.valueOf(o[n + 1])));
      } 
      fileNames[5] = getFileName("按人员性质分比例分析图", dataset, session);
    } 
    return fileNames;
  }
  
  private String getFileName(String title, DefaultPieDataset dataset, HttpSession session) {
    String filename = "";
    try {
      JFreeChart chart = ChartFactory.createPieChart3D(title, (PieDataset)dataset, true, false, false);
      PiePlot pieplot = (PiePlot)chart.getPlot();
      pieplot.setLabelFont(new Font("宋体", 0, 12));
      pieplot.setNoDataMessage("无数据显示");
      pieplot.setCircular(false);
      pieplot.setLabelGap(0.02D);
      pieplot.setLabelGenerator((PieSectionLabelGenerator)new StandardPieSectionLabelGenerator(
            "{0} ：{1}"));
      filename = ServletUtilities.saveChartAsPNG(chart, 400, 300, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  private String[] getBar(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    String[] titles = { "按学历分类", "按职称分", "按公司工作年限分", "按年龄分", "按性别分", "按人员性质分" };
    int pSize = 0;
    List<Object[]> pkl = (new PersonalKindBD()).list();
    if (pkl != null && pkl.size() > 0)
      pSize = 1; 
    String[] fileNames = new String[5 + pSize];
    Map result = null;
    EmpStatisticsBD bd = new EmpStatisticsBD();
    result = bd.listEmpStruct(session.getAttribute("orgId").toString(), 
        session.getAttribute("userId").toString(), 
        "07*55*05", yearMonth, null, 
        new Integer(999999999), 
        new Integer(1));
    List<Object[]> list2 = (List)result.get("list2");
    Object[] o = list2.get(0);
    double[][] data = new double[6][1];
    String[] title0 = { "硕士及以上", "本科", "专科", "中专", "高中", "初中及以下" };
    for (int i = 0; i < 6; i++)
      data[i][0] = Double.parseDouble(String.valueOf(o[i + 1])); 
    fileNames[0] = getFileName2("按学历分类人员统计图", "学历", data, new String[] { "" }, title0, session);
    data = new double[4][1];
    String[] title1 = { "高级", "中级", "初级", "无" };
    for (int j = 6; j < 10; j++)
      data[j - 6][0] = Double.parseDouble(String.valueOf(o[j + 1])); 
    fileNames[1] = getFileName2("按职称分人员统计图", "职称", data, new String[] { "" }, title1, session);
    data = new double[4][1];
    String[] title2 = { "5年以上", "3-5年", "1-3年", "1年以下" };
    for (int k = 10; k < 14; k++)
      data[k - 10][0] = Double.parseDouble(String.valueOf(o[k + 1])); 
    fileNames[2] = getFileName2("按公司工作年限分人员统计图", "公司工作年限", data, new String[] { "" }, title2, session);
    data = new double[4][1];
    String[] title3 = { "40岁以上", "30-39岁", "20-29岁", "19岁以下" };
    for (int m = 14; m < 18; m++)
      data[m - 14][0] = Double.parseDouble(String.valueOf(o[m + 1])); 
    fileNames[3] = getFileName2("按年龄分人员统计图", "年龄", data, new String[] { "" }, title3, session);
    data = new double[2][1];
    String[] title4 = { "男", "女" };
    for (int n = 18; n < 20; n++)
      data[n - 18][0] = Double.parseDouble(String.valueOf(o[n + 1])); 
    fileNames[4] = getFileName2("按性别分人员统计图", "性别", data, new String[] { "" }, title4, session);
    if (pSize > 0) {
      int ll = pkl.size();
      data = new double[ll][1];
      String[] title5 = new String[ll];
      for (int i1 = 20; i1 < 20 + ll; i1++) {
        Object[] obj = pkl.get(i1 - 20);
        data[i1 - 20][0] = Double.parseDouble(String.valueOf(o[i1 + 1]));
        title5[i1 - 20] = String.valueOf(obj[1]);
      } 
      fileNames[5] = getFileName2("按人员性质分人员统计图", "人员性质", data, new String[] { "" }, title5, session);
    } 
    return fileNames;
  }
  
  private String getFileName2(String title, String subtitle, double[][] data, String[] columnKeys, String[] rowKeys, HttpSession session) {
    String filename = "";
    try {
      CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
          (Comparable[])rowKeys, (Comparable[])columnKeys, data);
      JFreeChart chart = ChartFactory.createBarChart3D(title, 
          subtitle, "人数（个）", dataset, 
          PlotOrientation.VERTICAL, true, true, false);
      CategoryPlot plot = chart.getCategoryPlot();
      plot.setBackgroundPaint(Color.white);
      plot.setDomainGridlinePaint(Color.pink);
      plot.setRangeGridlinePaint(Color.pink);
      BarRenderer3D renderer = new BarRenderer3D();
      renderer.setBaseItemLabelGenerator(
          (CategoryItemLabelGenerator)new StandardCategoryItemLabelGenerator());
      renderer.setBaseItemLabelsVisible(true);
      renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
            ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
      renderer.setItemLabelAnchorOffset(10.0D);
      renderer.setItemMargin(0.0D);
      plot.setRenderer((CategoryItemRenderer)renderer);
      filename = ServletUtilities.saveChartAsPNG(chart, 400, 300, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
}
