package test;

import com.runqian.report4.usermodel.input.AbstractInputListener;
import com.runqian.report4.usermodel.input.InputSQL;

public class PrintSQLInputListener extends AbstractInputListener {
  public void beforeSave() throws Exception {}
  
  public void afterSave() throws Exception {
    InputSQL[] abc = getInputSql();
    for (int i = 0; i < (getInputSql()).length; i++) {
      String sql = abc[i].getSql();
      if (sql.matches("insert.*")) {
        System.out.println("这是insert语句" + sql);
      } else {
        System.out.println("这是update语句" + sql);
      } 
    } 
  }
}
