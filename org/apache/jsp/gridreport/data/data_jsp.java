/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:46 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gridreport.data;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class data_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n<NewDataSet>\r\n  <Table>\r\n    <CustomerID>BLONP</CustomerID>\r\n    <CompanyName>国皓</CompanyName>\r\n    <ContactName>黄雅玲</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>广发北路 10 号</Address>\r\n    <City>大连</City>\r\n    <Region>东北</Region>\r\n    <PostalCode>565479</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0671) 88601531</Phone>\r\n    <Fax>(0671) 88601532</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>CACTU</CustomerID>\r\n    <CompanyName>威航货运有限公司</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>销售代理</ContactTitle>\r\n    <Address>经七纬二路 13 号</Address>\r\n    <City>大连</City>\r\n    <Region>东北</Region>\r\n    <PostalCode>120412</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(061) 11355555</Phone>\r\n    <Fax>(061) 11354892</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>CENTC</CustomerID>\r\n    <CompanyName>三捷实业</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>英雄山路 84 号</Address>\r\n    <City>大连</City>\r\n    <Region>东北</Region>\r\n    <PostalCode>130083</PostalCode>\r\n");
      out.write("    <Country>中国</Country>\r\n    <Phone>(061) 15553392</Phone>\r\n    <Fax>(061) 15557293</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>HUNGC</CustomerID>\r\n    <CompanyName>五金机械</CompanyName>\r\n    <ContactName>苏先生</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>德昌路甲 29 号</Address>\r\n    <City>大连</City>\r\n    <Region>东北</Region>\r\n    <PostalCode>564576</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(053) 5556874</Phone>\r\n    <Fax>(053) 5552376</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>MEREP</CustomerID>\r\n    <CompanyName>华科</CompanyName>\r\n    <ContactName>吴小姐</ContactName>\r\n    <ContactTitle>市场助理</ContactTitle>\r\n    <Address>和光北路 952 号</Address>\r\n    <City>大连</City>\r\n    <Region>东北</Region>\r\n    <PostalCode>280235</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0514) 5558054</Phone>\r\n    <Fax>(0514) 5558055</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>GREAL</CustomerID>\r\n    <CompanyName>仪和贸易</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>经三纬四路 18 号</Address>\r\n");
      out.write("    <City>北京</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>120475</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(010) 65557555</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FRANK</CustomerID>\r\n    <CompanyName>友恒信托</CompanyName>\r\n    <ContactName>余小姐</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>经二路 9 号</Address>\r\n    <City>秦皇岛</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>500798</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(089) 3877310</Phone>\r\n    <Fax>(089) 3877451</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FOLIG</CustomerID>\r\n    <CompanyName>嘉业</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>助理销售代理</ContactTitle>\r\n    <Address>经三纬二路 8 号</Address>\r\n    <City>石家庄</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>576906</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0321) 20161016</Phone>\r\n    <Fax>(0321) 20161017</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>ANTON</CustomerID>\r\n    <CompanyName>坦森行贸易</CompanyName>\r\n    <ContactName>王炫皓</ContactName>\r\n");
      out.write("    <ContactTitle>物主</ContactTitle>\r\n    <Address>黄台北路 780 号</Address>\r\n    <City>石家庄</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>985060</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0321) 5553932</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>NORTS</CustomerID>\r\n    <CompanyName>富同企业</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>销售员</ContactTitle>\r\n    <Address>广西路 24 号</Address>\r\n    <City>石家庄</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>780008</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(031) 5557733</Phone>\r\n    <Fax>(031) 555-2530</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>MORGK</CustomerID>\r\n    <CompanyName>仲堂企业</CompanyName>\r\n    <ContactName>徐文彬</ContactName>\r\n    <ContactTitle>市场助理</ContactTitle>\r\n    <Address>创业街 57 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>440007</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 34202376</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LILAS</CustomerID>\r\n    <CompanyName>富泰人寿</CompanyName>\r\n");
      out.write("    <ContactName>陈先生</ContactName>\r\n    <ContactTitle>结算经理</ContactTitle>\r\n    <Address>光伦东路 381 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>995085</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 33116954</Phone>\r\n    <Fax>(030) 33117256</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LONEP</CustomerID>\r\n    <CompanyName>正太实业</CompanyName>\r\n    <ContactName>林慧音</ContactName>\r\n    <ContactTitle>销售经理</ContactTitle>\r\n    <Address>花园西街 28 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>440875</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 25559573</Phone>\r\n    <Fax>(030) 25559646</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>HUNGO</CustomerID>\r\n    <CompanyName>师大贸易</CompanyName>\r\n    <ContactName>苏先生</ContactName>\r\n    <ContactTitle>销售员</ContactTitle>\r\n    <Address>黄岗北路 73 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>683045</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 29672542</Phone>\r\n    <Fax>(030) 29673333</Fax>\r\n");
      out.write("  </Table>\r\n  <Table>\r\n    <CustomerID>LAMAI</CustomerID>\r\n    <CompanyName>池春建设</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>销售经理</ContactTitle>\r\n    <Address>青年南街 291 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>502564</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 61776110</Phone>\r\n    <Fax>(030) 61776111</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LAUGB</CustomerID>\r\n    <CompanyName>和福建设</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>市场助理</ContactTitle>\r\n    <Address>创业西路 238 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>055654</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 15553392</Phone>\r\n    <Fax>(030) 15557293</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>ALFKI</CustomerID>\r\n    <CompanyName>三川实业有限公司</CompanyName>\r\n    <ContactName>刘小姐</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>大崇明路 50 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>343567</PostalCode>\r\n");
      out.write("    <Country>中国</Country>\r\n    <Phone>(030) 30074321</Phone>\r\n    <Fax>90460/02</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>ANATR</CustomerID>\r\n    <CompanyName>东南实业</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>承德西路 80 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>234575</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 35554729</Phone>\r\n    <Fax>(030) 3555/3744</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>CHOPS</CustomerID>\r\n    <CompanyName>浩天旅行社</CompanyName>\r\n    <ContactName>方先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>白广路 314 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>234254</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 30076545</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>COMMI</CustomerID>\r\n    <CompanyName>同恒</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>销售员</ContactTitle>\r\n    <Address>七一路 37 号</Address>\r\n    <City>天津</City>\r\n");
      out.write("    <Region>华北</Region>\r\n    <PostalCode>453466</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 35557647</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>BLAUS</CustomerID>\r\n    <CompanyName>森通</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>常保阁东 80 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>787045</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 30058460</Phone>\r\n    <Fax>(030)  33008924</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>EASTC</CustomerID>\r\n    <CompanyName>中通</CompanyName>\r\n    <ContactName>林小姐</ContactName>\r\n    <ContactTitle>销售代理</ContactTitle>\r\n    <Address>光复北路 895 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>809784</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 35550297</Phone>\r\n    <Fax>(030) 35553373</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FISSA</CustomerID>\r\n    <CompanyName>嘉元实业</CompanyName>\r\n    <ContactName>刘小姐</ContactName>\r\n    <ContactTitle>结算经理</ContactTitle>\r\n");
      out.write("    <Address>东湖大街 28 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>458965</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(091) 25559444</Phone>\r\n    <Fax>(091) 25555593</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>GROSR</CustomerID>\r\n    <CompanyName>光远商贸</CompanyName>\r\n    <ContactName>陈先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>成川东街 951 号</Address>\r\n    <City>天津</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>122096</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(030) 32832951</Phone>\r\n    <Fax>(030) 32833397</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>GODOS</CustomerID>\r\n    <CompanyName>建资</CompanyName>\r\n    <ContactName>陈先生</ContactName>\r\n    <ContactTitle>销售经理</ContactTitle>\r\n    <Address>广惠东路 38 号</Address>\r\n    <City>张家口</City>\r\n    <Region>华北</Region>\r\n    <PostalCode>242353</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0922) 5558282</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FRANS</CustomerID>\r\n    <CompanyName>文成</CompanyName>\r\n");
      out.write("    <ContactName>唐小姐</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>临江街 32 号</Address>\r\n    <City>常州</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>820097</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(056) 34988260</Phone>\r\n    <Fax>(056) 34988261</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>DUMON</CustomerID>\r\n    <CompanyName>迈策船舶</CompanyName>\r\n    <ContactName>王俊元</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>沉香街 329 号</Address>\r\n    <City>常州</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>565474</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(056) 40678888</Phone>\r\n    <Fax>(056) 40678989</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>HANAR</CustomerID>\r\n    <CompanyName>实翼</CompanyName>\r\n    <ContactName>谢小姐</ContactName>\r\n    <ContactTitle>结算经理</ContactTitle>\r\n    <Address>永惠西街 392 号</Address>\r\n    <City>南昌</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>674674</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0211) 5550091</Phone>\r\n    <Fax>(0211) 5558765</Fax>\r\n");
      out.write("  </Table>\r\n  <Table>\r\n    <CustomerID>FURIB</CustomerID>\r\n    <CompanyName>康浦</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>销售经理</ContactTitle>\r\n    <Address>授业路 361 号</Address>\r\n    <City>南京</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>964532</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(087) 43542534</Phone>\r\n    <Fax>(087) 43542535</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FOLKO</CustomerID>\r\n    <CompanyName>五洲信托</CompanyName>\r\n    <ContactName>苏先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>沿江北路 942 号</Address>\r\n    <City>南京</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>876060</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(087) 69534671</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FRANR</CustomerID>\r\n    <CompanyName>国银贸易</CompanyName>\r\n    <ContactName>余小姐</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>辅城街 42 号</Address>\r\n    <City>南京</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>546590</PostalCode>\r\n    <Country>中国</Country>\r\n");
      out.write("    <Phone>(087) 40322121</Phone>\r\n    <Fax>(087) 40322120</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>BERGS</CustomerID>\r\n    <CompanyName>通恒机械</CompanyName>\r\n    <ContactName>黄小姐</ContactName>\r\n    <ContactTitle>采购员</ContactTitle>\r\n    <Address>东园西甲 30 号</Address>\r\n    <City>南京</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>798089</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0921) 9123465</Phone>\r\n    <Fax>(0921) 55123467</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LEHMS</CustomerID>\r\n    <CompanyName>幸义房屋</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>七一路 89 号</Address>\r\n    <City>南京</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>167556</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(069) 20245984</Phone>\r\n    <Fax>(069) 20245874</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>MAISD</CustomerID>\r\n    <CompanyName>悦海</CompanyName>\r\n    <ContactName>陈玉美</ContactName>\r\n    <ContactTitle>销售代理</ContactTitle>\r\n    <Address>八一路 384 号</Address>\r\n");
      out.write("    <City>青岛</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>054356</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0217) 2012467</Phone>\r\n    <Fax>(0217) 2012468</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>GOURL</CustomerID>\r\n    <CompanyName>业兴</CompanyName>\r\n    <ContactName>李柏麟</ContactName>\r\n    <ContactTitle>销售员</ContactTitle>\r\n    <Address>淮河路 348 号</Address>\r\n    <City>上海</City>\r\n    <Region>华东</Region>\r\n    <PostalCode>241008</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(021) 85559482</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>DRACD</CustomerID>\r\n    <CompanyName>世邦</CompanyName>\r\n    <ContactName>黎先生</ContactName>\r\n    <ContactTitle>采购员</ContactTitle>\r\n    <Address>光明东路 395 号</Address>\r\n    <City>海口</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>454748</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0241) 10391231</Phone>\r\n    <Fax>(0241) 10594282</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LINOD</CustomerID>\r\n    <CompanyName>保信人寿</CompanyName>\r\n    <ContactName>方先生</ContactName>\r\n");
      out.write("    <ContactTitle>物主</ContactTitle>\r\n    <Address>创业北路 32 号</Address>\r\n    <City>海口</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>301256</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0899) 3345612</Phone>\r\n    <Fax>(0899) 3349393</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>MAGAA</CustomerID>\r\n    <CompanyName>阳林</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>城东大街 47 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>801056</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 36402300</Phone>\r\n    <Fax>(0571) 36402311</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LAZYK</CustomerID>\r\n    <CompanyName>春永建设</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>劳动辅路 395 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>013056</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 35557969</Phone>\r\n    <Fax>(0571) 35556221</Fax>\r\n  </Table>\r\n  <Table>\r\n");
      out.write("    <CustomerID>BSBEV</CustomerID>\r\n    <CompanyName>光明杂志</CompanyName>\r\n    <ContactName>谢丽秋</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>黄石路 50 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>760908</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 45551212</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>AROUT</CustomerID>\r\n    <CompanyName>国顶有限公司</CompanyName>\r\n    <ContactName>方先生</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>天府东街 30 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>890879</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 45557788</Phone>\r\n    <Fax>(0571) 45556750</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>GALED</CustomerID>\r\n    <CompanyName>东旗</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n    <Address>尊石路 238 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>411012</PostalCode>\r\n    <Country>中国</Country>\r\n");
      out.write("    <Phone>(0571) 20334560</Phone>\r\n    <Fax>(0571) 20334561</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>HILAA</CustomerID>\r\n    <CompanyName>远东开发</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>崇盛路 82 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>498045</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 75551340</Phone>\r\n    <Fax>(0571) 75551948</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>ERNSH</CustomerID>\r\n    <CompanyName>正人资源</CompanyName>\r\n    <ContactName>谢小姐</ContactName>\r\n    <ContactTitle>销售经理</ContactTitle>\r\n    <Address>临江东街 62 号</Address>\r\n    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>906853</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 76753425</Phone>\r\n    <Fax>(0571) 76753426</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>FAMIA</CustomerID>\r\n    <CompanyName>红阳事业</CompanyName>\r\n    <ContactName>王先生</ContactName>\r\n    <ContactTitle>市场助理</ContactTitle>\r\n    <Address>外滩西路 238 号</Address>\r\n");
      out.write("    <City>深圳</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>687759</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0571) 75559857</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>CONSH</CustomerID>\r\n    <CompanyName>万海</CompanyName>\r\n    <ContactName>林小姐</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>劳动路 23 号</Address>\r\n    <City>厦门</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>353467</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(071) 45552282</Phone>\r\n    <Fax>(071) 45559199</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LETSS</CustomerID>\r\n    <CompanyName>兴中保险</CompanyName>\r\n    <ContactName>方先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>豪威西路 238 号</Address>\r\n    <City>厦门</City>\r\n    <Region>华南</Region>\r\n    <PostalCode>750165</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(0415) 5555938</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>ISLAT</CustomerID>\r\n    <CompanyName>鑫增贸易</CompanyName>\r\n    <ContactName>周先生</ContactName>\r\n    <ContactTitle>市场经理</ContactTitle>\r\n");
      out.write("    <Address>东府大街 31 号</Address>\r\n    <City>西安</City>\r\n    <Region>西北</Region>\r\n    <PostalCode>502255</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(091) 65558888</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>BOLID</CustomerID>\r\n    <CompanyName>迈多贸易</CompanyName>\r\n    <ContactName>陈先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>临翠大街 80 号</Address>\r\n    <City>西安</City>\r\n    <Region>西北</Region>\r\n    <PostalCode>907987</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(091) 85552282</Phone>\r\n    <Fax>(091) 85559199</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>BONAP</CustomerID>\r\n    <CompanyName>祥通</CompanyName>\r\n    <ContactName>刘先生</ContactName>\r\n    <ContactTitle>物主</ContactTitle>\r\n    <Address>花园东街 90 号</Address>\r\n    <City>重庆</City>\r\n    <Region>西南</Region>\r\n    <PostalCode>567690</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(078) 91244540</Phone>\r\n    <Fax>(078) 91244541</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>BOTTM</CustomerID>\r\n    <CompanyName>广通</CompanyName>\r\n");
      out.write("    <ContactName>王先生</ContactName>\r\n    <ContactTitle>结算经理</ContactTitle>\r\n    <Address>平谷嘉石大街 38 号</Address>\r\n    <City>重庆</City>\r\n    <Region>西南</Region>\r\n    <PostalCode>808059</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(078) 95554729</Phone>\r\n    <Fax>(078) 95553745</Fax>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>KOENE</CustomerID>\r\n    <CompanyName>永业房屋</CompanyName>\r\n    <ContactName>谢丽秋</ContactName>\r\n    <ContactTitle>销售员</ContactTitle>\r\n    <Address>东园大路 78 号</Address>\r\n    <City>重庆</City>\r\n    <Region>西南</Region>\r\n    <PostalCode>101057</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(025) 55509876</Phone>\r\n  </Table>\r\n  <Table>\r\n    <CustomerID>LACOR</CustomerID>\r\n    <CompanyName>霸力建设</CompanyName>\r\n    <ContactName>谢小姐</ContactName>\r\n    <ContactTitle>销售代表</ContactTitle>\r\n    <Address>东岗大路 9 号</Address>\r\n    <City>重庆</City>\r\n    <Region>西南</Region>\r\n    <PostalCode>048766</PostalCode>\r\n    <Country>中国</Country>\r\n    <Phone>(025) 30598410</Phone>\r\n    <Fax>(025) 30598511</Fax>\r\n  </Table>\r\n");
      out.write("</NewDataSet>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}