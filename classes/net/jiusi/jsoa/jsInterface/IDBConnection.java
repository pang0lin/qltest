package net.jiusi.jsoa.jsInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IDBConnection {
  static {
    throw new Error("Unresolved compilation problem: \n\tThe declared package \"net.jiusi.jsoa.jsinterface\" does not match the expected package \"net.jiusi.jsoa.jsInterface\"\n");
  }
  
  Connection getConnection();
  
  void close(ResultSet paramResultSet, PreparedStatement paramPreparedStatement, Statement paramStatement, Connection paramConnection);
}
