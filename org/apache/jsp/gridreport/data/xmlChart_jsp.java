/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gridreport.data;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceBase;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletResponse;
import java.util.zip.DeflaterOutputStream;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.util.*;

public final class xmlChart_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {



//判断是否包含特殊字符
public static boolean HasSpecialChar(String text)
{
    if (text == null) 
        return false;
    
    boolean ret = false;     
    int len = text.length();
    for (int i = 0; i < len; ++i)
    {
        char c = text.charAt(i);
        if (c == '&' ||  c == '<' || c == '>' || c == '"')
        {
            ret = true;
            break;
        }
    }
    
    return ret;
}

//对数据中的特殊字符进行编码
public static String HTMLEncode(String text)
{
    int len = text.length();
    StringBuffer results = new StringBuffer(len + 20);
    char[] orig = text.toCharArray();
    
    int beg = 0;
    for (int i = 0; i < len; ++i)
    {
        char c = text.charAt(i);
        switch (c) 
        {
            case '&':
                if (i > beg) 
                    results.append(orig, beg, i - beg);
                beg = i + 1;
                results.append("&amp;");
                break;
            case '<':
                if (i > beg) 
                    results.append(orig, beg, i - beg);
                beg = i + 1;
                results.append("&lt;");
                break;
            case '>':
                if (i > beg) 
                    results.append(orig, beg, i - beg);
                beg = i + 1;
                results.append("&gt;");
                break;
            case '"':
                if (i > beg) 
                    results.append(orig, beg, i - beg);
                beg = i + 1;
                results.append("&quot;");
                break;
        }
    }
    
    results.append(orig, beg, len - beg);
    
    return results.toString();
}



//简要说明：
//<!--连接数据库，产生Grid++Report需要的XML格式报表数据-->
//<!--必须安装相应数据库的JDBC驱动-->

//！注意：定义 oracle 数据库访问参数，应修改为与实际一致
public class oracle_jdbc_param{   
	public final static String driver = "oracle.jdbc.driver.OracleDriver";   
	public final static String url = "jdbc:oracle:thin:@localhost:1521";   
	public final static String user = "hr";   
	public final static String password = "hr";   
}   

//！注意：定义 mysql 数据库访问参数，应修改为与实际一致
public class mysql_jdbc_param{   
	public final static String driver = "com.mysql.jdbc.Driver";   
	public final static String url = "jdbc:mysql://192.168.0.110:3309/jsdb?useUnicode=true&characterEncoding=utf8";   //"jdbc:mysql://localhost/gridreport?user=root&password=&useUnicode=true&characterEncoding=utf8"
	public final static String user = "jsdb";   
	public final static String password = "12345678";   
}   

//！注意：定义 mssql 数据库访问参数，应修改为与实际一致
public class mssql_jdbc_param{   
	public final static String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";   //mssql2000 jdbc
	public final static String url = "jdbc:microsoft:sqlserver://localhost;DatabaseName=gridreport"; //mssql2000 jdbc 
	//如果是应用mssql20005的jdbc驱动，应该注视掉上面两行，而去掉下面两行的注视。说明：mssql2000 jdbc可以连接mssql20005 数据库
	//public final static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";    //mssql2005 jdbc
	//public final static String url = "jdbc:sqlserver://localhost;DatabaseName=gridreport"; //mssql2005 jdbc   
	public final static String user = "sa";   
	public final static String password = "";   
}   

//！注意：定义 odbc 数据库访问参数，应修改为与实际一致
public class odbc_jdbc_param{   
	public final static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";   
	public final static String url = "jdbc:odbc:webreport";   
	public final static String user = "sa";   
	public final static String password = "";   
}   

//！注意：如果是 oracle 数据库，请将 extends 后的类名改为 oracle_jdbc_param，并将 oracle_jdbc_param 中的参数改为与实际一致
//！注意：如果是 mysql 数据库，请将 extends 后的类名改为 mysql_jdbc_param，并将 mysql_jdbc_param 中的参数改为与实际一致
//！注意：如果是 mssql 数据库，请将 extends 后的类名改为 mssql_jdbc_param，并将 mssql_jdbc_param 中的参数改为与实际一致
//！注意：如果是 odbc 数据源，请将 extends 后的类名改为 odbc_jdbc_param，并将 odbc_jdbc_param 中的参数改为与实际一致
//public class jdbc_param extends oracle_jdbc_param 
public class jdbc_param extends mysql_jdbc_param 
//public class jdbc_param extends mssql_jdbc_param 
//public class jdbc_param extends odbc_jdbc_param 
{
}

//方法简要说明
//1. GenNodeXmlData：产生报表需要的XML节点类型数据
//2. GenFullReportData：根据RecordsetQuerySQL产生提供给报表生成需要的XML数据，并同时将ParameterPart中的报表参数数据一起打包，参数ToCompress指定是否压缩数据 
//3. GenReportParameterData：根据ParameterQuerySQL获取的报表参数数据一起打包 
         
//将产生的报表文本数据(XML文本 或 JSON文本)发送给客户端，可以对数据进行压缩
public static void ResponseText(HttpServletResponse response, String DataText, boolean ToCompress) throws Exception
{
    response.resetBuffer();
    
    if ( ToCompress )
    {
        byte[] RawData = DataText.getBytes(); //DataText.toString().getBytes();
        
        //写入特有的压缩头部信息，以便报表客户端插件能识别数据
        response.addHeader("gr_zip_type", "deflate");                           //指定压缩方法
        response.addIntHeader("gr_zip_size", RawData.length);                   //指定数据的原始长度
        response.addHeader("gr_zip_encode", response.getCharacterEncoding());   //指定数据的编码方式 utf-8 utf-16 ...
	
        //压缩数据并输出
        ServletOutputStream bos = response.getOutputStream();
        DeflaterOutputStream zos = new DeflaterOutputStream(bos);
        zos.write(RawData);
        zos.close();
        bos.flush();
    }
    else
    {
        PrintWriter pw = response.getWriter();
        pw.print(DataText); //pw.print(DataText.toString());
        pw.close();  //终止后续不必要内容输出
    }
}

/////////////////////////////////////////////////////////////////////////////////////////
//产生报表需要的XML节点类型数据，节点类型数据产生数据量比属性类型数据的要大
public static void GenNodeXmlData(HttpServletResponse response, String QuerySQL, boolean ToCompress){
	GenNodeXmlData(response,QuerySQL,"system",ToCompress);
}
public static void GenNodeXmlData(HttpServletResponse response, String QuerySQL,String sourceBase, boolean ToCompress){
    try{
        try{
            //Class.forName(jdbc_param.driver); // Class.forName 装载驱动程序 
            //Connection con=DriverManager.getConnection(jdbc_param.url, jdbc_param.user, jdbc_param.password); //用适当的驱动程序类与 DBMS 建立一个连接
            Connection con = null;
            if("system".equals(sourceBase)){
            	con = new DataSourceBase().getDataSource().getConnection();
            }else{
            	con = new DataSourceBase().getDataSource(sourceBase).getConnection();
            }
            
            Statement stmt=con.createStatement();                         //用于发送简单的SQL语句
            ResultSet rs=stmt.executeQuery(QuerySQL);

            ResultSetMetaData rsmd = rs.getMetaData();
            int ColCount = rsmd.getColumnCount();
            
            StringBuffer XmlText = new StringBuffer ("<xml>\n");
            
            while( rs.next() ) 
            {
                XmlText.append("<row>");
                for (int i=1; i<=ColCount; i++)
                {
                    XmlText.append("<");
                    XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
                    XmlText.append(">");
                    
                    int ColType = rsmd.getColumnType(i);
                    if (ColType == Types.LONGVARBINARY || ColType == Types.VARBINARY || ColType == Types.BINARY || ColType == Types.BLOB)
                    {
                        byte[] BinData = rs.getBytes(i);
                        if ( !rs.wasNull() )
                            XmlText.append( (new sun.misc.BASE64Encoder()).encode( BinData ) );
                    }
                    else
                    {
                        String Val = rs.getString(i);
                        if ( !rs.wasNull() )
                        {
                            if ( HasSpecialChar(Val) )
                                XmlText.append( HTMLEncode(Val) );
                            else
                                XmlText.append(Val);
                        }
                    }
                    
                    XmlText.append("</");
                    XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
                    XmlText.append(">");
                }
                XmlText.append("</row>\n");
		    }
            rs.close();
            stmt.close();
            con.close();
            
            XmlText.append("</xml>\n");
            System.out.println("报表数据：\n"+XmlText.toString());
            ResponseText(response, XmlText.toString(), ToCompress);
        }
        catch(Exception e)
        {
            //output error message
            PrintWriter pw = response.getWriter();
            pw.print(e.toString());
        }
    }
    catch(Exception e)
    {
    }
}

/////////////////////////////////////////////////////////////////////////////////////////
//产生报表需要的XML节点类型数据
//根据RecordsetQuerySQL产生提供给报表生成需要的XML数据，并同时将ParameterPart中的报表参数数据一起打包，参数ToCompress指定是否压缩数据
public static void GenFullReportData(HttpServletResponse response, String QuerySQL, String ParameterPart, boolean ToCompress)
{
    try
    {
        try
        {
            Class.forName(jdbc_param.driver); // Class.forName 装载驱动程序 
            Connection con=DriverManager.getConnection(jdbc_param.url, jdbc_param.user, jdbc_param.password); //用适当的驱动程序类与 DBMS 建立一个连接
            
            Statement stmt=con.createStatement();                         //用于发送简单的SQL语句
            ResultSet rs=stmt.executeQuery(QuerySQL);

            ResultSetMetaData rsmd = rs.getMetaData();
            int ColCount = rsmd.getColumnCount();
            
            //StringBuffer XmlText = new StringBuffer ("<xml>\n");
            StringBuffer XmlText = new StringBuffer("<report>\n<xml>\n");
            
            while( rs.next() ) 
            {
                XmlText.append("<row>");
                for (int i=1; i<=ColCount; i++)
                {
                    XmlText.append("<");
                    XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
                    XmlText.append(">");
                    
                    int ColType = rsmd.getColumnType(i);
                    if (ColType == Types.LONGVARBINARY || ColType == Types.VARBINARY || ColType == Types.BINARY || ColType == Types.BLOB)
                    {
                        byte[] BinData = rs.getBytes(i);
                        if ( !rs.wasNull() )
                            XmlText.append( (new sun.misc.BASE64Encoder()).encode( BinData ) );
                    }
                    else
                    {
                        String Val = rs.getString(i);
                        if ( !rs.wasNull() )
                        {
                            if ( HasSpecialChar(Val) )
                                XmlText.append( HTMLEncode(Val) );
                            else
                                XmlText.append(Val);
                        }
                    }
                    
                    XmlText.append("</");
                    XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
                    XmlText.append(">");
                }
                XmlText.append("</row>\n");
		    }
            rs.close();
            stmt.close();
            con.close();
            
            XmlText.append("</xml>\n");
            XmlText.append(ParameterPart);
            XmlText.append("</report>");
            
            ResponseText(response, XmlText.toString(), ToCompress);
        }
        catch(Exception e)
        {
            //output error message
            PrintWriter pw = response.getWriter();
            pw.print(e.toString());
        }
    }
    catch(Exception e)
    {
    }
}

/////////////////////////////////////////////////////////////////////////////////////////
//产生报表参数的XML节点类型数据
//根据ParameterQuerySQL获取的报表参数数据一起打包
public static String GenReportParameterData(String ParameterQuerySQL)
{
    StringBuffer XmlText = new StringBuffer ("<_grparam>\n");
    
    try
    {
        Class.forName(jdbc_param.driver); // Class.forName 装载驱动程序 
        Connection con=DriverManager.getConnection(jdbc_param.url, jdbc_param.user, jdbc_param.password); //用适当的驱动程序类与 DBMS 建立一个连接
        
        Statement stmt=con.createStatement();                         //用于发送简单的SQL语句
        ResultSet rs=stmt.executeQuery(ParameterQuerySQL);

        ResultSetMetaData rsmd = rs.getMetaData();
        rs.next();
        
        int ColCount = rsmd.getColumnCount();
        for (int i=1; i<=ColCount; i++)
        {
            XmlText.append("<");
            XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
            XmlText.append(">");
            
            int ColType = rsmd.getColumnType(i);
            if (ColType == Types.LONGVARBINARY || ColType == Types.VARBINARY || ColType == Types.BINARY || ColType == Types.BLOB)
            {
                byte[] BinData = rs.getBytes(i);
                if ( !rs.wasNull() )
                    XmlText.append( (new sun.misc.BASE64Encoder()).encode( BinData ) );
            }
            else
            {
				String Val = rs.getString(i);
				if ( !rs.wasNull() )
				{
					if ( HasSpecialChar(Val) )
						XmlText.append( HTMLEncode(Val) );
					else
						XmlText.append(Val);
				}
			}
            
            XmlText.append("</");
            XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
            XmlText.append(">\n");
        }
            
        rs.close();
        stmt.close();
        con.close();
    }
    catch(Exception e)
    {
        //output error message
        XmlText.append(e.toString());
    }
    
    XmlText.append("</_grparam>\n");
    
    return XmlText.toString();
}

/////////////////////////////////////////////////////////////////////////////////////////
//产生报表参数的XML节点类型数据，并响应给客户端
public static void GenParameterXmlData(HttpServletResponse response, String QuerySQL)
{
    try
    {
        StringBuffer XmlText = new StringBuffer("<report>\n");
  		String ParameterPart = GenReportParameterData(QuerySQL);
        XmlText.append(ParameterPart);
        XmlText.append("</report>");
		
        ResponseText(response, XmlText.toString(), false);
    }
    catch(Exception e)
    {
    }
}

//获取 Count(*) SQL 查询到的数据行数
//参数 QuerySQL 指定获取报表数据的查询SQL
public static int BatchGetDataCount(String QuerySQL)
{
    int Total = 0;
    try
    {
        Class.forName(jdbc_param.driver); // Class.forName 装载驱动程序 
        Connection con=DriverManager.getConnection(jdbc_param.url, jdbc_param.user, jdbc_param.password); //用适当的驱动程序类与 DBMS 建立一个连接
        Statement stmt=con.createStatement();                         //用于发送简单的SQL语句
        ResultSet rs=stmt.executeQuery(QuerySQL);

        if  ( rs.next() )
			Total = rs.getInt(1);
            
        rs.close();
        con.close();
    }
    catch(Exception e)
    {
    }
	return Total;
}

/////////////////////////////////////////////////////////////////////////////////////////
//分批取数: 产生报表需要的XML节点类型数据
//参数 SessionItemName 指定在 Session 中记录 ResultSet 对象的名称，应该保证每个报表用不同的名称
//参数 QuerySQL 指定获取报表数据的查询SQL
//参数 StartNo 指定本次取数的第一条记录的序号，从0开始；当为0时，表示是取第一批次的数据
//参数 WantRows 指定本次取数希望获取的记录条数，当为0时，自动按100获取
//参数 ToCompress 指定是否对XML数据进行压缩
//此函数目前不需要用到，所以注释掉
/*public static void BatchGenXmlData(HttpServletResponse response, HttpSession session, String SessionItemName, String QuerySQL, int StartNo, int WantRows, boolean ToCompress)
{
    try
    {
        try
        {
            response.resetBuffer();
            
            //首先从Session中获取ResultSet，如果不存在，则进行创建打开
            ResultSet rs = (ResultSet)session.getAttribute(SessionItemName);
            if (rs == null)
            {
				Class.forName(jdbc_param.driver); // Class.forName 装载驱动程序 
				Connection con=DriverManager.getConnection(jdbc_param.url, jdbc_param.user, jdbc_param.password); //用适当的驱动程序类与 DBMS 建立一个连接
            
				Statement stmt=con.createStatement();                         //用于发送简单的SQL语句
				rs=stmt.executeQuery(QuerySQL);

				session.setAttribute(SessionItemName, rs);
            }
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int ColCount = rsmd.getColumnCount();
            
            StringBuffer XmlText = new StringBuffer("<xml>\n");
            
            rs.beforeFirst();
            rs.relative(StartNo);
            int ReadedCount = 0;
            while(rs.next() && ReadedCount<WantRows) 
            {
                XmlText.append("<row>");
                for (int i=1; i<=ColCount; i++)
                {
                    XmlText.append("<");
                    XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
                    XmlText.append(">");
                    
                    int ColType = rsmd.getColumnType(i);
                    if (ColType == Types.LONGVARBINARY || ColType == Types.VARBINARY || ColType == Types.BINARY || ColType == Types.BLOB)
                    {
                        byte[] BinData = rs.getBytes(i);
                        if ( !rs.wasNull() )
                            XmlText.append( (new sun.misc.BASE64Encoder()).encode( BinData ) );
                    }
                    else
                    {
                        String Val = rs.getString(i);
                        if ( !rs.wasNull() )
                        {
                            if ( HasSpecialChar(Val) )
                                XmlText.append( HTMLEncode(Val) );
                            else
                                XmlText.append(Val);
                        }
                    }
                    
                    XmlText.append("</");
                    XmlText.append(rsmd.getColumnLabel(i)); //getColumnName
                    XmlText.append(">");
                }
                XmlText.append("</row>\n");
                
                ++ReadedCount;
		    }
		    
			//如果没有取得数据，说明数据已经取完，则清理掉Session中记录的数据
            if (ReadedCount == 0)
            {
				rs.close();
				//stmt.close();
				//con.close();
				
				session.removeAttribute(SessionItemName);
            }
            
            XmlText.append("</xml>\n");
            
            if ( ToCompress )
	        {
                byte[] RawData = XmlText.toString().getBytes();
                
	            //写入特有的压缩头部信息，以便报表客户端插件能识别数据
                response.addHeader("gr_zip_type", "deflate");                                      //指定压缩方法
                response.addIntHeader("gr_zip_size", RawData.length);                    //指定数据的原始长度
                response.addHeader("gr_zip_encode", response.getCharacterEncoding());   //指定数据的编码方式 utf-8 utf-16 ...
        	
	            //压缩数据并输出
                ServletOutputStream bos = response.getOutputStream();
                DeflaterOutputStream zos = new DeflaterOutputStream(bos);
                zos.write(RawData);
                zos.close();
                bos.flush();
	        }
	        else
	        {
                PrintWriter pw = response.getWriter();
                pw.print(XmlText.toString());
                pw.close();  //终止后续不必要内容输出
            }
        }
        catch(Exception e)
        {
            //output error message
            PrintWriter pw = response.getWriter();
            pw.print(e.toString());
        }
    }
    catch(Exception e)
    {
    }
}*/


  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/gridreport/data/GenXmlData.jsp", Long.valueOf(1499751408000L));
    _jspx_dependants.put("/gridreport/data/grcommon.jsp", Long.valueOf(1499751408000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.lang");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("java.io");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("javax.servlet.jsp.JspWriter");
    _jspx_imports_classes.add("javax.servlet.http.HttpServletResponse");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
    _jspx_imports_classes.add("java.util.zip.DeflaterOutputStream");
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n\r\n");
      out.write(' ');
      out.write('\r');
      out.write('\n');
      out.write(' ');
      out.write('\r');
      out.write('\n');

request.setCharacterEncoding("GBK");
String QuerySQL = "";
String sourceBase = "report";
String reportId = request.getParameter("reportId");
String chartName = request.getParameter("chartName");
String where = "";
String dataBaseType = "mysql";
DataSourceBase base = new DataSourceBase();
try{
	base.begin();
	DatabaseMetaData md = base.getDataSource().getConnection().getMetaData();
	dataBaseType = md.getDatabaseProductName().toLowerCase();
	//System.out.println(dataBaseType);
	String sql = "SELECT chartSql,sourceBase FROM oa_report WHERE reportId="+reportId;
	ResultSet rs = base.executeQuery(sql);
	if(rs.next()){
		QuerySQL = rs.getString(1).replaceAll("[\\t\\n\\r]","").replaceAll("：",":").replaceAll("；",";").replaceAll("）",")").replaceAll("（","(");//将内容区域的回车换行去除 ;
		sourceBase = rs.getString(2);
	}
	rs.close();
	String[] sqls = QuerySQL.split(";");
	for(int i=0;i<sqls.length;i++){//对sql语句进行解析，获得图表名和对应的sql语句
		if(sqls[i].indexOf(":")>=0){
			String[] result = sqls[i].split(":");
			if(chartName.equals(result[0])){
				QuerySQL = result[1];
				break;
			}
		}
	}
	if(dataBaseType.equals("mysql")){
		sql = "SELECT beReplaceString,replaceString FROM oa_reportreplace WHERE reportId="+reportId+" and "+
				" concat(',',sqlType,',') like'%,"+chartName+",%'";
	}else{
		sql = "SELECT beReplaceString,replaceString FROM oa_reportreplace WHERE reportId="+reportId+" and "+
				" ','||sqlType||',' like'%,"+chartName+",%'";
	}
	rs = base.executeQuery(sql);
	while(rs.next()){
		String name = rs.getString(1);
		if(request.getParameter(name)!=null&&!"".equals(request.getParameter(name))){
			String replaceSql = rs.getString(2);
			where += " and "+replaceSql.replace("@$@"+name+"@$@",java.net.URLDecoder.decode(request.getParameter(name),"utf-8"));
		}
	}
}catch(Exception e){
	e.printStackTrace();
}finally{
	base.end();
}
if(!"".equals(where)){
	String byStr = "";
	if(reportSql.toLowerCase().indexOf(" group by ")>0){
		byStr = reportSql.substring(reportSql.toLowerCase().indexOf(" group by "));
		reportSql = reportSql.substring(0,reportSql.toLowerCase().indexOf(" group by "));
	}else if(reportSql.toLowerCase().indexOf(" order by ")>0){
		byStr = reportSql.substring(reportSql.toLowerCase().indexOf(" order by "));
		reportSql = reportSql.substring(0,reportSql.toLowerCase().indexOf(" order by "));
	}
	if(QuerySQL.toLowerCase().indexOf("where 1=1")>0){
		QuerySQL = QuerySQL.substring(0,QuerySQL.toLowerCase().indexOf("where 1=1")+9)+" "+where+" "+QuerySQL.substring(QuerySQL.toLowerCase().indexOf("where 1=1")+9);
	}else if(QuerySQL.toLowerCase().indexOf(" where ")>0){
		QuerySQL = QuerySQL.substring(0,QuerySQL.toLowerCase().indexOf(" where ")+7)+" 1=1 "+where +" and "+QuerySQL.substring(QuerySQL.toLowerCase().indexOf(" where ")+7);
	}else{
		QuerySQL = QuerySQL + " where 1=1 "+where;
	}
	reportSql += " "+byStr;
}
if(QuerySQL.startsWith("(模式1)")){
	QuerySQL = QuerySQL.substring(5);
}
System.out.println("查询sql:"+QuerySQL);
GenNodeXmlData(response,QuerySQL,sourceBase,false);

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
