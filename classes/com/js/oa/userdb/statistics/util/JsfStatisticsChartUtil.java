package com.js.oa.userdb.statistics.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javax.servlet.http.HttpSession;
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

public class JsfStatisticsChartUtil {
  public String getChartBing(String title, DefaultPieDataset dataset, HttpSession session, int w, int h) {
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
      filename = ServletUtilities.saveChartAsPNG(chart, w, h, null, 
          session);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return filename;
  }
  
  public String getChartZhu(String title, String subtitle, String yShow, double[][] data, String[] columnKeys, String[] rowKeys, HttpSession session, String flag, int w, int h) {
    String filename = "";
    try {
      CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
          (Comparable[])rowKeys, (Comparable[])columnKeys, data);
      JFreeChart chart = null;
      if ("shu".equals(flag)) {
        chart = ChartFactory.createBarChart3D(
            title, 
            subtitle, 
            yShow, 
            dataset, 
            PlotOrientation.HORIZONTAL, 
            true, 
            true, 
            false);
      } else {
        chart = ChartFactory.createBarChart3D(
            title, 
            subtitle, 
            yShow, 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            true, 
            false);
      } 
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
  
  public String getChartZhe(String title, String subtitle, String XShow, String YShow, DefaultCategoryDataset linedata, HttpSession session, int width, int hight) {
    String filename = "";
    try {
      JFreeChart chart = ChartFactory.createLineChart(
          title, 
          XShow, 
          YShow, 
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
}
