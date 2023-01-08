package com.js.oa.chart.workflow.action;

import com.js.oa.chart.workflow.bean.ChartWorkFlowEJBBean;
import com.js.util.config.SystemCommon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
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
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;

public class ChartWorkFlowAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    String yearMonth = httpServletRequest.getParameter("yearMonth");
    httpServletRequest.setAttribute("yearMonth", yearMonth);
    if ("flowAllChart".equals(action)) {
      String type = httpServletRequest.getParameter("chartType");
      String filename = "";
      String fileNames = "";
      Map<String, List<Object[]>> result = null;
      try {
        ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
        ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
        String orgId = session.getAttribute("orgId").toString();
        String userId = session.getAttribute("userId").toString();
        String orgName = httpServletRequest.getParameter("orgName");
        result = bd.listFlowStatus(session.getAttribute("orgId").toString(), 
            session.getAttribute("userId").toString(), 
            "02*05*01", yearMonth, null, 
            new Integer(999999999), 
            new Integer(1));
        httpServletRequest.setAttribute("filenamebing", getFlowStatusChartPie(httpServletRequest, result));
        result = (Map)b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(5), new Integer(1), orgName, " cc1 desc ", "", "org", null, "");
        httpServletRequest.setAttribute("filenamehengzhu_flowStatusOrgTop10_daiban", getFlowStatusOrgDaiBanTop10(httpServletRequest, result, "heng", "部门", 400, 240));
        result = (Map)b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(5), new Integer(1), orgName, " cc1 desc ", "", "emp", null, "");
        httpServletRequest.setAttribute("filenamehengzhu_flowStatusUserTop10_daiban", getFlowStatusUserZheXianTop10(httpServletRequest, result, 400, 240));
        result = b.getProcessEfficBySql(orgId, userId, "02*05*01", new Integer(5), new Integer(1), orgName, " youxiao desc ");
        httpServletRequest.setAttribute("filenamehengzhu_flowStatusTop10_process", getFlowEfficAnalyTop10HengZhu(httpServletRequest, result, 400, 240, "heng"));
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("flowAllChart");
    } 
    if ("flowStatusChart".equals(action)) {
      String type = httpServletRequest.getParameter("chartType");
      String filename = "";
      String fileNames = "";
      Map<String, List<Object[]>> result = null;
      ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
      String flowStatusType = httpServletRequest.getParameter("flowStatusType");
      httpServletRequest.setAttribute("flowStatusType", flowStatusType);
      ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
      String orgId = session.getAttribute("orgId").toString();
      String userId = session.getAttribute("userId").toString();
      String orgName = httpServletRequest.getParameter("orgName");
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      httpServletRequest.setAttribute("offset", Integer.valueOf(offset));
      if ("org".equals(flowStatusType)) {
        try {
          result = bd.listFlowStatus(session.getAttribute("orgId").toString(), 
              session.getAttribute("userId").toString(), 
              "02*05*01", yearMonth, null, 
              new Integer(999999999), 
              new Integer(1));
          httpServletRequest.setAttribute("filenamebing", getFlowStatusChartPie(httpServletRequest, result));
          Map<String, Object> resultTop = b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(5), new Integer(1), orgName, " cc1 desc ", "", flowStatusType, null, "");
          httpServletRequest.setAttribute("filenamehengzhu_flowStatusOrgTop10_daiban", getFlowStatusOrgDaiBanTop10(httpServletRequest, resultTop, "heng", "部门", 360, 240));
          resultTop = b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(5), new Integer(1), orgName, " cc3 desc ", "", flowStatusType, null, "");
          httpServletRequest.setAttribute("filenamehengzhu_flowStatusOrgTop10_zaiban", getFlowStatusOrgTopZaiBan10(httpServletRequest, resultTop, "heng", "部门", 400, 300));
          resultTop = b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(5), new Integer(1), orgName, " cc4 desc ", "", flowStatusType, null, "");
          httpServletRequest.setAttribute("filenamehengzhu_flowStatusOrgTop10_banjie", getFlowStatusOrgTopBanJie10(httpServletRequest, resultTop, "heng", "部门", 400, 300));
        } catch (Exception e) {
          e.printStackTrace();
        } 
        return actionMapping.findForward("flowStatusChart");
      } 
      try {
        result = (Map)b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(10), new Integer(1), orgName, " cc1 desc ", "", flowStatusType, null, "");
        httpServletRequest.setAttribute("filenamehengzhu_flowStatusUserTop10_daiban", getFlowStatusUserDaiBanTop10(httpServletRequest, result, "heng", "用户", 600, 450));
        result = (Map)b.getFlowStatusData(orgId, userId, "02*05*01", new Integer(10), new Integer(1), orgName, " cc3 desc ", "", flowStatusType, null, "");
        httpServletRequest.setAttribute("filenamehengzhu_flowStatusUserTop10_zaiban", getFlowStatusUserZaiBanTop10(httpServletRequest, result, "heng", "用户", 600, 450));
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("filenamehengzhu_flowStatusUserTop10");
    } 
    if ("flowStatusData".equals(action)) {
      flowStatusData(httpServletRequest, 30, "02*05*01");
      return actionMapping.findForward("flowStatusData");
    } 
    if ("flowStatusDataList".equals(action)) {
      flowStatusData(httpServletRequest, 15, "02*05*01");
      return actionMapping.findForward(action);
    } 
    if ("flowStatusExport".equals(action)) {
      flowStatusData(httpServletRequest, 999999999, "02*05*01");
      return actionMapping.findForward("flowStatusExport");
    } 
    if ("flowDateSpanData".equals(action)) {
      try {
        flowDateSpanData(httpServletRequest, 30);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowDateSpanDataList".equals(action)) {
      try {
        flowDateSpanData(httpServletRequest, 15);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowDateSpanChart".equals(action)) {
      String type = httpServletRequest.getParameter("chartType");
      Map<String, Object> result = null;
      try {
        String orgId = session.getAttribute("orgId").toString();
        String userId = session.getAttribute("userId").toString();
        Object object = session.getAttribute("browseRange");
        String flowStatus = httpServletRequest.getParameter("flowStatus");
        String searchOrg = httpServletRequest.getParameter("searchOrg");
        String oprStartTime = httpServletRequest.getParameter("oprStartTime");
        String searchOrgName = httpServletRequest.getParameter("searchOrgName");
        String oprEndTime = httpServletRequest.getParameter("oprEndTime");
        String searchTime = httpServletRequest.getParameter("searchTime");
        httpServletRequest.setAttribute("flowStatus", flowStatus);
        httpServletRequest.setAttribute("searchOrg", searchOrg);
        httpServletRequest.setAttribute("searchOrg", searchOrg);
        httpServletRequest.setAttribute("searchOrgName", searchOrgName);
        httpServletRequest.setAttribute("oprStartTime", oprStartTime);
        httpServletRequest.setAttribute("oprEndTime", oprEndTime);
        httpServletRequest.setAttribute("searchTime", searchTime);
        String orderBy = httpServletRequest.getParameter("orderBy");
        String sortType = httpServletRequest.getParameter("sortType");
        httpServletRequest.setAttribute("sortType", sortType);
        httpServletRequest.setAttribute("orderBy", orderBy);
        if (orderBy != null && sortType != null)
          orderBy = String.valueOf(orderBy) + " " + sortType; 
        int pageSize = 15;
        int offset = 0;
        if (httpServletRequest.getParameter("pager.offset") != null)
          offset = Integer.parseInt(httpServletRequest.getParameter(
                "pager.offset")); 
        httpServletRequest.setAttribute("offset", Integer.valueOf(offset));
        int currentPage = offset / pageSize + 1;
        ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
        result = bd.getFlowDateSpanData(orgId, userId, (String)object, Integer.valueOf(pageSize), new Integer(currentPage), 
            flowStatus, searchOrg, oprStartTime, oprEndTime, searchTime, orderBy, null);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("filenamezhe", getFlowDateSpanLineChart(httpServletRequest, result, 1000, 450));
      return actionMapping.findForward(action);
    } 
    if ("flowDateSpanExport".equals(action)) {
      flowDateSpanData(httpServletRequest, 999999999);
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyListData".equals(action)) {
      try {
        flowEfficAnalyListData(httpServletRequest, 15);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyData".equals(action)) {
      try {
        flowEfficAnalyData(httpServletRequest, 15);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyDataList".equals(action)) {
      try {
        flowEfficAnalyData(httpServletRequest, 15);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyUserDataList".equals(action)) {
      try {
        flowEfficAnalyUserData(httpServletRequest, 15);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyChart".equals(action)) {
      String processId = httpServletRequest.getParameter("processId");
      Map<String, Object> result = null;
      try {
        ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
        result = bd.getFlowEfficAnalyData(session.getAttribute("orgId").toString(), 
            session.getAttribute("userId").toString(), null, 
            new Integer(999999999), new Integer(1), 
            processId, null);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("filenamezhu", getFlowEfficAnalyZhuChart(httpServletRequest, result));
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyDataExport".equals(action)) {
      try {
        flowEfficAnalyData(httpServletRequest, 999999999);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyUserChart".equals(action)) {
      String activityId = httpServletRequest.getParameter("activityId");
      Map<String, Object> result = null;
      try {
        ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
        result = bd.getFlowEfficAnalyUserData(session.getAttribute("orgId").toString(), 
            session.getAttribute("userId").toString(), null, 
            new Integer(999999999), new Integer(1), 
            activityId, null);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("filenamezhu", getFlowEfficAnalyUserZhuChart(httpServletRequest, result));
      return actionMapping.findForward(action);
    } 
    if ("flowEfficAnalyUserDataExport".equals(action)) {
      try {
        flowEfficAnalyUserData(httpServletRequest, 999999999);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    return actionMapping.findForward(action);
  }
  
  private String getFlowStatusChartPie(HttpServletRequest request, Map result) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    String fileNames = "";
    try {
      List<Object[]> list2 = (List)result.get("list2");
      Object[] o = list2.get(0);
      DefaultPieDataset dataset = new DefaultPieDataset();
      String[] title0 = { "待办流程", "超期未办流程", "在办流程", "办结流程" };
      for (int i = 0; i < 4; i++)
        dataset.setValue(title0[i], Double.parseDouble(String.valueOf(o[i]))); 
      fileNames = getFlowStatusChartFileName("", dataset, session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return fileNames;
  }
  
  private String getFlowStatusChartFileName(String title, DefaultPieDataset dataset, HttpSession session) {
    String filename = "";
    try {
      JFreeChart chart = ChartFactory.createPieChart3D(title, (PieDataset)dataset, true, false, false);
      Font font = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(font); 
      PiePlot pieplot = (PiePlot)chart.getPlot();
      pieplot.setLabelFont(font);
      pieplot.setLabelBackgroundPaint(Color.white);
      pieplot.setNoDataMessage("无数据显示");
      pieplot.setNoDataMessageFont(font);
      chart.setBackgroundPaint(Color.WHITE);
      pieplot.setCircular(false);
      pieplot.setLabelGap(0.02D);
      pieplot.setLabelGenerator((PieSectionLabelGenerator)new StandardPieSectionLabelGenerator(
            "{0} ：{1}"));
      pieplot.setOutlinePaint(Color.WHITE);
      pieplot.setShadowPaint(Color.WHITE);
      pieplot.setSectionPaint(0, Color.decode("#FF5555"));
      pieplot.setSectionPaint(1, Color.decode("#FF55FF"));
      pieplot.setSectionPaint(2, Color.decode("#5555FF"));
      pieplot.setSectionPaint(3, Color.decode("#55FF55"));
      filename = ServletUtilities.saveChartAsPNG(chart, 400, 240, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  private String getFlowStatusChartBar(HttpServletRequest request, Map result, String flag, int w, int h) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    String fileNames = "";
    ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
    List<Object[]> list2 = (List)result.get("list2");
    Object[] o = list2.get(0);
    double[][] data = new double[3][1];
    String[] title0 = { "待办流程", "在办流程", "办结流程" };
    for (int i = 0; i < 3; i++)
      data[i][0] = Double.parseDouble(String.valueOf(o[i])); 
    fileNames = getFlowStatusChartFileName2("按流程（任务）状态分类", "流程状态", "个数（个）", data, new String[] { "" }, title0, session, flag, w, h);
    return fileNames;
  }
  
  private String getFlowStatusChartFileName2(String title, String subtitle, String yShow, double[][] data, String[] columnKeys, String[] rowKeys, HttpSession session, String flag, int w, int h) {
    String filename = "";
    try {
      if (data.length == 0) {
        data = new double[][] { { 0.0D } };
        columnKeys = new String[] { "暂无数据" };
        rowKeys = new String[] { "您没有查看权限或者暂无数据信息" };
      } 
      CategoryDataset dataset = DatasetUtilities.createCategoryDataset((Comparable[])rowKeys, (Comparable[])columnKeys, data);
      JFreeChart chart = null;
      if ("shu".equals(flag)) {
        chart = ChartFactory.createBarChart3D(
            title, 
            subtitle, 
            yShow, 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            true, 
            false);
      } else {
        chart = ChartFactory.createBarChart3D(
            title, 
            subtitle, 
            yShow, 
            dataset, 
            PlotOrientation.HORIZONTAL, 
            true, 
            true, 
            false);
      } 
      Font font = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(font); 
      CategoryPlot plot = chart.getCategoryPlot();
      plot.setNoDataMessageFont(font);
      CategoryAxis axis = plot.getDomainAxis();
      axis.setLabelFont(font);
      axis.setTickLabelFont(font);
      ValueAxis valueAxis = plot.getRangeAxis();
      valueAxis.setLabelFont(font);
      valueAxis.setTickLabelFont(font);
      plot.setBackgroundPaint(Color.white);
      chart.setBackgroundPaint(Color.WHITE);
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
      renderer.setMaximumBarWidth(0.2D);
      plot.setRenderer((CategoryItemRenderer)renderer);
      filename = ServletUtilities.saveChartAsPNG(chart, w, h, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  private String getFlowStatusChartXY(HttpServletRequest request, Map result) {
    HttpSession session = request.getSession(true);
    String yearMonth = request.getParameter("yearMonth");
    String fileNames = "";
    ChartWorkFlowEJBBean bd = new ChartWorkFlowEJBBean();
    List<Object[]> list2 = (List)result.get("list2");
    Object[] o = list2.get(0);
    double[][] data = new double[3][1];
    for (int i = 0; i < 3; i++)
      data[i][0] = Double.parseDouble(String.valueOf(o[i])); 
    DefaultCategoryDataset linedata = new DefaultCategoryDataset();
    linedata.addValue(new Double(data[0][0]), "个数（个）", "待办流程");
    linedata.addValue(new Double(data[1][0]), "个数（个）", "在办流程");
    linedata.addValue(new Double(data[2][0]), "个数（个）", "办结流程");
    fileNames = getFlowStatusChartFileName3("按流程（任务）状态分类", "流程状态", linedata, session, 400, 300);
    return fileNames;
  }
  
  private String getFlowDateSpanLineChart(HttpServletRequest request, Map resultMap, int w, int h) {
    HttpSession session = request.getSession(true);
    String fileNames = "";
    String flowStatus = (request.getParameter("flowStatus") == null) ? ((request.getAttribute("flowStatus") == null) ? "" : request.getAttribute("flowStatus").toString()) : request.getParameter("flowStatus");
    if ("0".equals(flowStatus)) {
      flowStatus = "（待办）";
    } else if ("1".equals(flowStatus)) {
      flowStatus = "（在办）";
    } else if ("2".equals(flowStatus)) {
      flowStatus = "（办结）";
    } else if ("3".equals(flowStatus)) {
      flowStatus = "（超期未办）";
    } else {
      flowStatus = "（待办）";
    } 
    DefaultCategoryDataset linedata = new DefaultCategoryDataset();
    List<Map> resultList = (List)resultMap.get("resultList");
    List<E> tableHeadList = (List)resultMap.get("tableHeadList");
    Map sumMap = (Map)resultMap.get("sumMap");
    if (resultList != null && tableHeadList != null)
      for (int i = 0; i < tableHeadList.size(); i++) {
        for (int j = 0; j < resultList.size(); j++) {
          Map map = resultList.get(j);
          Integer data = (Integer)map.get("m" + (i + 1));
          Double d = new Double(data.intValue());
          String x = map.get("orgName").toString();
          String y = tableHeadList.get(i).toString();
          linedata.addValue(d, x, y);
        } 
      }  
    fileNames = getLineChartFileName_NO3D("时间分布分析" + flowStatus, "时间", "个数（个）", linedata, session, w, h);
    return fileNames;
  }
  
  private String getFlowStatusChartFileName3(String title, String subtitle, DefaultCategoryDataset linedata, HttpSession session, int width, int hight) {
    String filename = "";
    try {
      JFreeChart chart = ChartFactory.createLineChart(
          title, 
          "", 
          "个数（个）", 
          (CategoryDataset)linedata, 
          PlotOrientation.VERTICAL, 
          true, 
          true, 
          false);
      chart.setBackgroundPaint(Color.WHITE);
      Font f = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(f); 
      CategoryPlot plot = chart.getCategoryPlot();
      plot.getDomainAxis().setLabelFont(f);
      plot.getDomainAxis().setTickLabelFont(f);
      plot.getRangeAxis().setLabelFont(f);
      plot.setNoDataMessageFont(f);
      plot.setBackgroundPaint(Color.WHITE);
      plot.setRangeGridlinePaint(Color.RED);
      plot.setRangeGridlinesVisible(true);
      plot.setDomainGridlinePaint(Color.RED);
      plot.setDomainGridlinesVisible(true);
      LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
      BasicStroke realLine = new BasicStroke(1.6F);
      float[] dashes = { 8.0F };
      BasicStroke brokenLine = new BasicStroke(1.6F, 
          2, 
          0, 
          8.0F, 
          dashes, 
          0.0F);
      renderer.setSeriesStroke(3, realLine);
      renderer.setItemLabelsVisible(true);
      filename = ServletUtilities.saveChartAsPNG(chart, width, hight, null, session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  private String getLineChartFileName_NO3D(String title, String xTitle, String yTitle, DefaultCategoryDataset linedata, HttpSession session, int width, int hight) {
    String filename = "";
    try {
      JFreeChart chart = ChartFactory.createLineChart(
          title, 
          xTitle, 
          yTitle, 
          (CategoryDataset)linedata, 
          PlotOrientation.VERTICAL, 
          true, 
          true, 
          false);
      Font f = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(f); 
      CategoryPlot plot = chart.getCategoryPlot();
      plot.getDomainAxis().setLabelFont(f);
      plot.getDomainAxis().setTickLabelFont(f);
      plot.getRangeAxis().setLabelFont(f);
      plot.setNoDataMessageFont(f);
      plot.setBackgroundPaint(Color.WHITE);
      plot.setRangeGridlinePaint(Color.RED);
      plot.setRangeGridlinesVisible(true);
      plot.setDomainGridlinePaint(Color.RED);
      plot.setDomainGridlinesVisible(true);
      LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
      BasicStroke realLine = new BasicStroke(1.6F);
      float[] dashes = { 8.0F };
      BasicStroke brokenLine = new BasicStroke(1.6F, 
          2, 
          0, 
          8.0F, 
          dashes, 
          0.0F);
      renderer.setSeriesStroke(3, realLine);
      renderer.setItemLabelsVisible(true);
      filename = ServletUtilities.saveChartAsPNG(chart, width, hight, null, session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  public void flowStatusData(HttpServletRequest request, int page_size, String rightCode) {
    HttpSession session = request.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    String orgName = request.getParameter("orgName");
    String userName = request.getParameter("userName");
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    String searchTime = request.getParameter("searchTime");
    String oprStartTime = request.getParameter("oprStartTime");
    String oprEndTime = request.getParameter("oprEndTime");
    String flowStatusType = request.getParameter("flowStatusType");
    request.setAttribute("flowStatusType", flowStatusType);
    String databaseType = SystemCommon.getDatabaseType();
    String where = "";
    if (searchTime != null && "1".equals(searchTime)) {
      if (oprStartTime != null && !"".equals(oprStartTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and w.worksubmittime >=to_date('" + dateFormart(oprStartTime, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and w.worksubmittime >='" + dateFormart(oprStartTime, "yyyy/MM/dd") + "'";
        }  
      if (oprEndTime != null && !"".equals(oprEndTime))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and w.worksubmittime <=to_date('" + dateFormart(oprEndTime, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and w.worksubmittime <='" + dateFormart(oprEndTime, "yyyy/MM/dd") + "'";
        }  
    } 
    String itemIds = (request.getParameter("itemIds") == null) ? "" : request.getParameter("itemIds");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null)
      orderBy = String.valueOf(orderBy) + " " + sortType; 
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map<String, Object> result = null;
    List list = null;
    ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
    try {
      result = b.getFlowStatusData(orgId, userId, rightCode, Integer.valueOf(pageSize), new Integer(currentPage), orgName, orderBy, where, flowStatusType, userName, itemIds);
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    list = (List)result.get("reList");
    List reCountList = (List)result.get("reCountList");
    try {
      recordCount = ((Integer)result.get("recordCount")).intValue();
    } catch (Exception e) {
      list = new ArrayList();
      recordCount = 0;
    } 
    String url = "";
    Enumeration<String> pNames = request.getParameterNames();
    while (pNames.hasMoreElements()) {
      String name = pNames.nextElement();
      if (!name.equals("pager.offset") && !name.equals("action")) {
        String value = (request.getParameter(name) == null) ? "" : request.getParameter(name);
        url = String.valueOf(url) + "&" + name + "=" + value;
      } 
    } 
    request.setAttribute("urlPara", url.replace("/", "-"));
    request.setAttribute("list", list);
    request.setAttribute("reCountList", reCountList);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,orgName,orderBy,sortType,searchTime,oprStartTime,oprEndTime,flowStatusType,userName");
  }
  
  public void flowDateSpanData(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    Object object = session.getAttribute("browseRange");
    String flowStatus = request.getParameter("flowStatus");
    String searchOrgName = request.getParameter("searchOrgName");
    String searchOrg = request.getParameter("searchOrg");
    String oprStartTime = request.getParameter("oprStartTime");
    String oprEndTime = request.getParameter("oprEndTime");
    String searchTime = request.getParameter("searchTime");
    String itemIds = request.getParameter("itemIds");
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null)
      orderBy = String.valueOf(orderBy) + " " + sortType; 
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map<String, Object> resultMap = null;
    List resultList = null;
    List tableHeadList = null;
    ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
    try {
      resultMap = b.getFlowDateSpanData(orgId, userId, (String)object, Integer.valueOf(pageSize), new Integer(currentPage), flowStatus, searchOrg, oprStartTime, oprEndTime, searchTime, orderBy, itemIds);
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    resultList = (List)resultMap.get("resultList");
    tableHeadList = (List)resultMap.get("tableHeadList");
    Map sumMap = (Map)resultMap.get("sumMap");
    recordCount = ((Integer)resultMap.get("recordCount")).intValue();
    request.setAttribute("resultList", resultList);
    request.setAttribute("tableHeadList", tableHeadList);
    request.setAttribute("sumMap", sumMap);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("flowStatus", flowStatus);
    request.setAttribute("pageParameters", "action,orgId,userId,range,flowStatus,searchOrgName,searchOrg,oprStartTime,oprEndTime,searchTime,orderBy,sortType");
  }
  
  public void flowEfficAnalyListData(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    Object object = session.getAttribute("browseRange");
    String processName = request.getParameter("processName");
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null)
      orderBy = String.valueOf(orderBy) + " " + sortType; 
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")) && 
      !"null".equals(request.getParameter("pager.offset")))
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map resultMap = null;
    List resultList = null;
    ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
    try {
      resultMap = b.getProcessEfficBySql(orgId, userId, (String)object, Integer.valueOf(pageSize), new Integer(currentPage), processName, orderBy);
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    resultList = (List)resultMap.get("resultList");
    recordCount = ((Integer)resultMap.get("recordCount")).intValue();
    request.setAttribute("resultList", resultList);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,processName,orderBy,sortType");
  }
  
  public void flowEfficAnalyData(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    Object object = session.getAttribute("browseRange");
    String processId = request.getParameter("processId");
    String processName = request.getParameter("processName");
    String parentpageroffset = request.getParameter("parent.pager.offset");
    request.setAttribute("parentpageroffset", parentpageroffset);
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null)
      orderBy = String.valueOf(orderBy) + " " + sortType; 
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map<String, Object> resultMap = null;
    List<Map> resultList = null;
    ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
    try {
      resultMap = b.getFlowEfficAnalyData(orgId, userId, (String)object, Integer.valueOf(pageSize), new Integer(currentPage), processId, orderBy);
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    resultList = (List)resultMap.get("resultList");
    float maxTime = (new Float("0.00")).floatValue();
    if (resultList != null && resultList.size() > 0)
      for (int i = 0; i < resultList.size(); i++) {
        Map tempMap = resultList.get(i);
        float maxTemp = Float.parseFloat(tempMap.get("youxiao").toString()) + Float.parseFloat(tempMap.get("wuxiao").toString());
        if (i == 0) {
          maxTime = maxTemp;
        } else if (maxTemp > maxTime) {
          maxTime = maxTemp;
        } 
      }  
    request.setAttribute("maxTime", Float.valueOf(maxTime));
    recordCount = ((Integer)resultMap.get("recordCount")).intValue();
    request.setAttribute("resultList", resultList);
    request.setAttribute("processName", processName);
    request.setAttribute("processId", processId);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("youxiaosum", resultMap.get("youxiaosum"));
    request.setAttribute("wuxiaosum", resultMap.get("wuxiaosum"));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,processId,processName,parent.pager.offset,orderBy,sortType");
  }
  
  public void flowEfficAnalyUserData(HttpServletRequest request, int page_size) {
    HttpSession session = request.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    Object object = session.getAttribute("browseRange");
    String activityId = request.getParameter("activityId");
    String activityName = request.getParameter("activityName");
    String processId = request.getParameter("processId");
    String processName = request.getParameter("processName");
    String parentpageroffset = request.getParameter("parent.pager.offset");
    request.setAttribute("parentpageroffset", parentpageroffset);
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null)
      orderBy = String.valueOf(orderBy) + " " + sortType; 
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map<String, Object> resultMap = null;
    List<Map> resultList = null;
    ChartWorkFlowEJBBean b = new ChartWorkFlowEJBBean();
    try {
      resultMap = b.getFlowEfficAnalyUserData(orgId, userId, (String)object, Integer.valueOf(pageSize), new Integer(currentPage), activityId, orderBy);
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    resultList = (List)resultMap.get("resultList");
    float maxTime = (new Float("0.00")).floatValue();
    if (resultList != null && resultList.size() > 0)
      for (int i = 0; i < resultList.size(); i++) {
        Map tempMap = resultList.get(i);
        float maxTemp = Float.parseFloat(tempMap.get("youxiao").toString()) + Float.parseFloat(tempMap.get("wuxiao").toString());
        if (i == 0) {
          maxTime = maxTemp;
        } else if (maxTemp > maxTime) {
          maxTime = maxTemp;
        } 
      }  
    request.setAttribute("maxTime", Float.valueOf(maxTime));
    recordCount = ((Integer)resultMap.get("recordCount")).intValue();
    request.setAttribute("resultList", resultList);
    request.setAttribute("activityName", activityName);
    request.setAttribute("activityId", activityId);
    request.setAttribute("processName", processName);
    request.setAttribute("processId", processId);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("youxiaosum", resultMap.get("youxiaosum"));
    request.setAttribute("wuxiaosum", resultMap.get("wuxiaosum"));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,activityId,activityName,processId,processName,parent.pager.offset,orderBy,sortType");
  }
  
  private String getFlowEfficAnalyZhuChart(HttpServletRequest request, Map result) {
    HttpSession session = request.getSession(true);
    String processName = request.getParameter("processName");
    String processId = request.getParameter("processId");
    request.setAttribute("processName", processName);
    request.setAttribute("processId", processId);
    String parentpageroffset = request.getParameter("parent.pager.offset");
    request.setAttribute("parentpageroffset", parentpageroffset);
    String pageroffset = request.getParameter("pager.offset");
    request.setAttribute("pageroffset", pageroffset);
    String fileNames = "";
    List<Map> resultList = (List)result.get("resultList");
    int recordCount = ((Integer)result.get("recordCount")).intValue();
    double[][] data = new double[2][recordCount];
    String[] columnKeys = new String[recordCount];
    String categoryAxisLabel = "节点";
    String valueAxisLabel = "时间（小时）";
    if (resultList != null)
      for (int k = 0; k < 2; k++) {
        for (int i = 0; i < resultList.size(); i++) {
          Map tempMap = resultList.get(i);
          if (k == 0) {
            data[k][i] = Double.valueOf(tempMap.get("youxiao").toString()).doubleValue();
            String tempName = tempMap.get("activityName").toString();
            for (int j = 0; j < columnKeys.length; j++) {
              if (tempName.equals(columnKeys[j])) {
                tempName = String.valueOf(tempName) + "(" + i + ")";
                break;
              } 
            } 
            columnKeys[i] = tempName;
          } else {
            data[k][i] = Double.valueOf(tempMap.get("wuxiao").toString()).doubleValue();
          } 
        } 
      }  
    int width = resultList.size() * 200;
    if (width < 600)
      width = 600; 
    fileNames = getCreateZhuChart("流程（" + processName + "）节点效率分析", categoryAxisLabel, valueAxisLabel, 
        data, new String[] { "有效", "无效" }, columnKeys, session, width, 450);
    return fileNames;
  }
  
  private String getFlowEfficAnalyUserZhuChart(HttpServletRequest request, Map result) {
    HttpSession session = request.getSession(true);
    String processName = request.getParameter("processName");
    String processId = request.getParameter("processId");
    request.setAttribute("processName", processName);
    request.setAttribute("processId", processId);
    String activityName = request.getParameter("activityName");
    String activityId = request.getParameter("activityId");
    request.setAttribute("activityName", activityName);
    request.setAttribute("activityId", activityId);
    String parentpageroffset = request.getParameter("parent.pager.offset");
    request.setAttribute("parentpageroffset", parentpageroffset);
    String pageroffset = request.getParameter("pager.offset");
    request.setAttribute("pageroffset", pageroffset);
    String fileNames = "";
    List<Map> resultList = (List)result.get("resultList");
    int recordCount = ((Integer)result.get("recordCount")).intValue();
    double[][] data = new double[2][recordCount];
    String[] columnKeys = new String[recordCount];
    String categoryAxisLabel = "用户";
    String valueAxisLabel = "时间（小时）";
    if (resultList != null)
      for (int k = 0; k < 2; k++) {
        for (int i = 0; i < resultList.size(); i++) {
          Map tempMap = resultList.get(i);
          if (k == 0) {
            data[k][i] = Double.valueOf(tempMap.get("youxiao").toString()).doubleValue();
            String tempName = tempMap.get("empName").toString();
            for (int j = 0; j < columnKeys.length; j++) {
              if (tempName.equals(columnKeys[j])) {
                tempName = String.valueOf(tempName) + "(" + i + ")";
                break;
              } 
            } 
            columnKeys[i] = tempName;
          } else {
            data[k][i] = Double.valueOf(tempMap.get("wuxiao").toString()).doubleValue();
          } 
        } 
      }  
    fileNames = getCreateZhuChart("流程节点（" + activityName + "）效率分析", categoryAxisLabel, valueAxisLabel, 
        data, new String[] { "有效", "无效" }, columnKeys, session, 600, 450);
    return fileNames;
  }
  
  private String getFlowEfficAnalyZhuChartProcess(HttpServletRequest request, Map result, int w, int h) {
    HttpSession session = request.getSession(true);
    List<Map> resultList = (List)result.get("resultList");
    int recordCount = ((Integer)result.get("recordCount")).intValue();
    double[][] data = new double[2][resultList.size()];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = "流程";
    String valueAxisLabel = "时间（小时）";
    if (resultList != null)
      for (int k = 0; k < 2; k++) {
        for (int i = 0; i < resultList.size(); i++) {
          Map tempMap = resultList.get(i);
          if (k == 0) {
            data[k][i] = Double.valueOf(tempMap.get("youxiao").toString()).doubleValue();
            String tempName = tempMap.get("porcessName").toString();
            for (int j = 0; j < columnKeys.length; j++) {
              if (tempName.equals(columnKeys[j])) {
                tempName = String.valueOf(tempName) + "(" + i + ")";
                break;
              } 
            } 
            columnKeys[i] = tempName;
          } else {
            data[k][i] = Double.valueOf(tempMap.get("wuxiao").toString()).doubleValue();
          } 
        } 
      }  
    String fileNames = getCreateZhuChart("", categoryAxisLabel, valueAxisLabel, 
        data, new String[] { "有效", "无效" }, columnKeys, session, w, h);
    return fileNames;
  }
  
  public String getFlowEfficAnalyTop10HengZhu(HttpServletRequest request, Map result, int w, int h, String flag) {
    HttpSession session = request.getSession(true);
    List<Map> resultList = (List)result.get("resultList");
    int recordCount = ((Integer)result.get("recordCount")).intValue();
    double[][] data = new double[resultList.size()][1];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = "流程";
    String valueAxisLabel = "时间（小时）";
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Map tempMap = resultList.get(i);
        data[i][0] = Double.valueOf(tempMap.get("youxiao").toString()).doubleValue();
        String tempName = tempMap.get("porcessName").toString();
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        columnKeys[i] = tempName;
      }  
    String fileNames = getFlowStatusChartFileName2("", "", valueAxisLabel, 
        data, new String[] { "" }, columnKeys, session, flag, w, h);
    return fileNames;
  }
  
  public String getFlowStatusUserZheXianTop10(HttpServletRequest request, Map result, int w, int h) {
    HttpSession session = request.getSession(true);
    String fileNames = "";
    List<Object[]> resultList = (List)result.get("reList");
    int recordCount = ((Integer)result.get("recordCount")).intValue();
    double data = (new Double("0.00")).doubleValue();
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = "流程";
    String valueAxisLabel = "时间（小时）";
    DefaultCategoryDataset linedata = new DefaultCategoryDataset();
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Object[] obj = resultList.get(i);
        data = Double.valueOf((obj[1] != null) ? obj[1].toString() : "0").doubleValue();
        String tempName = (obj[0] != null) ? obj[0].toString() : "";
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        linedata.addValue(data, "个数（个）", tempName);
      }  
    fileNames = getFlowStatusChartFileName3("", "", linedata, session, w, h);
    return fileNames;
  }
  
  private String getFlowStatusUserDaiBanTop10(HttpServletRequest request, Map result, String type, String flowStatusType, int w, int h) {
    HttpSession session = request.getSession(true);
    List<Object[]> resultList = (List)result.get("reList");
    double[][] data = new double[resultList.size()][1];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = flowStatusType;
    String valueAxisLabel = "个数（个）";
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Object[] obj = resultList.get(i);
        data[i][0] = Double.valueOf((obj[1] != null) ? obj[1].toString() : "0").doubleValue();
        String tempName = (obj[0] != null) ? obj[0].toString() : "";
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        columnKeys[i] = tempName;
      }  
    String fileNames = getFlowStatusChartFileName2("", "待办流程", valueAxisLabel, data, new String[] { "" }, columnKeys, session, "shu", w, h);
    return fileNames;
  }
  
  private String getFlowStatusUserZaiBanTop10(HttpServletRequest request, Map result, String type, String flowStatusType, int w, int h) {
    HttpSession session = request.getSession(true);
    List<Object[]> resultList = (List)result.get("reList");
    double[][] data = new double[resultList.size()][1];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = flowStatusType;
    String valueAxisLabel = "个数（个）";
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Object[] obj = resultList.get(i);
        data[i][0] = Double.valueOf((obj[3] != null) ? obj[3].toString() : "0").doubleValue();
        String tempName = (obj[0] != null) ? obj[0].toString() : "";
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        columnKeys[i] = tempName;
      }  
    String fileNames = getFlowStatusChartFileName2("", "在办流程", valueAxisLabel, data, new String[] { "" }, columnKeys, session, "shu", w, h);
    return fileNames;
  }
  
  private String getFlowStatusOrgDaiBanTop10(HttpServletRequest request, Map result, String type, String flowStatusType, int w, int h) {
    HttpSession session = request.getSession(true);
    List<Object[]> resultList = (List)result.get("reList");
    double[][] data = new double[resultList.size()][1];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = flowStatusType;
    String valueAxisLabel = "个数（个）";
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Object[] obj = resultList.get(i);
        data[i][0] = Double.valueOf((obj[1] != null) ? obj[1].toString() : "0").doubleValue();
        String tempName = (obj[0] != null) ? obj[0].toString() : "";
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        columnKeys[i] = tempName;
      }  
    String fileNames = getFlowStatusChartFileName2("", "待办流程", valueAxisLabel, data, new String[] { "" }, columnKeys, session, "shu", w, h);
    return fileNames;
  }
  
  private String getFlowStatusOrgTopZaiBan10(HttpServletRequest request, Map result, String type, String flowStatusType, int w, int h) {
    HttpSession session = request.getSession(true);
    List<Object[]> resultList = (List)result.get("reList");
    double[][] data = new double[resultList.size()][1];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = flowStatusType;
    String valueAxisLabel = "个数（个）";
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Object[] obj = resultList.get(i);
        data[i][0] = Double.valueOf((obj[3] != null) ? obj[3].toString() : "0").doubleValue();
        String tempName = (obj[0] != null) ? obj[0].toString() : "";
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        columnKeys[i] = tempName;
      }  
    String fileNames = getFlowStatusChartFileName2("", "在办流程", valueAxisLabel, data, new String[] { "" }, columnKeys, session, "shu", w, h);
    return fileNames;
  }
  
  private String getFlowStatusOrgTopBanJie10(HttpServletRequest request, Map result, String type, String flowStatusType, int w, int h) {
    HttpSession session = request.getSession(true);
    List<Object[]> resultList = (List)result.get("reList");
    double[][] data = new double[resultList.size()][1];
    String[] columnKeys = new String[resultList.size()];
    String categoryAxisLabel = flowStatusType;
    String valueAxisLabel = "个数（个）";
    if (resultList != null)
      for (int i = 0; i < resultList.size(); i++) {
        Object[] obj = resultList.get(i);
        data[i][0] = Double.valueOf((obj[4] != null) ? obj[4].toString() : "0").doubleValue();
        String tempName = (obj[0] != null) ? obj[0].toString() : "";
        for (int j = 0; j < columnKeys.length; j++) {
          if (tempName.equals(columnKeys[j])) {
            tempName = String.valueOf(tempName) + "(" + i + ")";
            break;
          } 
        } 
        columnKeys[i] = tempName;
      }  
    String fileNames = getFlowStatusChartFileName2("", "办结流程", valueAxisLabel, data, new String[] { "" }, columnKeys, session, "shu", w, h);
    return fileNames;
  }
  
  private String getCreateZhuChart(String title, String categoryAxisLabel, String valueAxisLabel, double[][] data, String[] rowKeys, String[] columnKeys, HttpSession session, int width, int height) {
    String filename = "";
    try {
      CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
          (Comparable[])rowKeys, (Comparable[])columnKeys, data);
      JFreeChart chart = ChartFactory.createBarChart3D(
          title, 
          categoryAxisLabel, 
          valueAxisLabel, 
          dataset, 
          PlotOrientation.VERTICAL, 
          true, 
          true, 
          false);
      Font font = new Font("宋体", 0, 12);
      chart.getTitle().setFont(new Font("宋体", 0, 18));
      CategoryPlot plot = chart.getCategoryPlot();
      plot.setNoDataMessageFont(font);
      CategoryAxis axis = plot.getDomainAxis();
      axis.setLabelFont(font);
      axis.setTickLabelFont(font);
      ValueAxis valueAxis = plot.getRangeAxis();
      valueAxis.setLabelFont(font);
      valueAxis.setTickLabelFont(font);
      for (int i = 0; i < chart.getSubtitles().size(); i++)
        chart.getLegend(i).setItemFont(font); 
      plot.setBackgroundPaint(new Color(238, 244, 255));
      chart.setBackgroundPaint(Color.WHITE);
      ValueAxis rangeAxis = plot.getRangeAxis();
      rangeAxis.setUpperMargin(0.15D);
      rangeAxis.setLowerMargin(0.15D);
      plot.setRangeAxis(rangeAxis);
      BarRenderer3D renderer = new BarRenderer3D();
      renderer.setItemMargin(0.0D);
      renderer.setItemLabelGenerator((CategoryItemLabelGenerator)new StandardCategoryItemLabelGenerator());
      renderer.setItemLabelFont(new Font("楷体", 0, 12));
      renderer.setItemLabelsVisible(true);
      plot.setRenderer((CategoryItemRenderer)renderer);
      filename = ServletUtilities.saveChartAsPNG(chart, width, height, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
