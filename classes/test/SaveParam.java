package test;

import com.runqian.report4.usermodel.input.AbstractParamProcessor;
import java.sql.Connection;
import java.sql.Statement;

public class SaveParam extends AbstractParamProcessor {
  public void process() throws Exception {
    Connection con = null;
    Statement stmt = null;
    String empID = getParamValue("empID");
    String duty = getParamValue("duty");
    duty = (duty == null) ? "" : duty;
    String area = getParamValue("area");
    area = (area == null) ? "" : area;
    String saveName = getParamValue("saveName");
    String toSave = getParamValue("toSave");
    String listSave = getParamValue("listSave");
    try {
      con = this.context.getConnectionFactory(this.context.getDefDataSourceName())
        .getConnection();
      if ("1".equals(toSave) && !"".equals(saveName)) {
        String saveValue = "empID=" + empID + ";duty=" + duty + 
          ";area=" + area;
        String sql = "INSERT INTO param VALUES ('8.3.raq','" + saveName + 
          "','" + saveValue + "')";
        con.createStatement().execute(sql);
        return;
      } 
    } finally {
      try {
        if (stmt != null)
          stmt.close(); 
        if (con != null)
          con.close(); 
      } catch (Exception exception) {}
    } 
    try {
      if (stmt != null)
        stmt.close(); 
      if (con != null)
        con.close(); 
    } catch (Exception exception) {}
  }
}
