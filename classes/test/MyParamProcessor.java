package test;

import com.runqian.report4.usermodel.input.AbstractParamProcessor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyParamProcessor extends AbstractParamProcessor {
  public void process() throws Exception {
    boolean canEdit = false;
    Connection con = null;
    Statement stmt = null;
    try {
      con = this.context.getConnectionFactory(this.context.getDefDataSourceName())
        .getConnection();
      String empID = getParamValue("empID");
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * from 雇员 where 雇员ID='" + empID + "'");
      if (rs.next()) {
        String isMODITY = rs.getString("ISMODITY");
        if ("1".equals(isMODITY))
          canEdit = false; 
      } 
    } finally {
      try {
        System.out.println("canEdit=========>" + canEdit);
        if (stmt != null)
          stmt.close(); 
        if (con != null)
          con.close(); 
      } catch (Exception exception) {}
    } 
    if (!canEdit)
      throw new Exception("该填报表已经接受审核，不可修改!"); 
  }
}
