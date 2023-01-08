package net.jiusi.jsoa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IDBConnection {
  Connection getConnection();
  
  void close(ResultSet paramResultSet, PreparedStatement paramPreparedStatement, Statement paramStatement, Connection paramConnection);
}
