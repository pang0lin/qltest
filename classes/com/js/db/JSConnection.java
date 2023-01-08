package com.js.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

public class JSConnection {
  private Connection con = null;
  
  public JSConnection(Connection conArg) {
    this.con = conArg;
  }
  
  public void clearWarnings() throws SQLException {
    this.con.clearWarnings();
  }
  
  public void close() throws SQLException {
    this.con.close();
  }
  
  public void commit() throws SQLException {
    this.con.commit();
  }
  
  public Statement createStatement() throws SQLException {
    return null;
  }
  
  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    return null;
  }
  
  public boolean getAutoCommit() throws SQLException {
    return this.con.getAutoCommit();
  }
  
  public String getCatalog() throws SQLException {
    return this.con.getCatalog();
  }
  
  public DatabaseMetaData getMetaData() throws SQLException {
    return this.con.getMetaData();
  }
  
  public int getTransactionIsolation() throws SQLException {
    return this.con.getTransactionIsolation();
  }
  
  public Map getTypeMap() throws SQLException {
    return this.con.getTypeMap();
  }
  
  public SQLWarning getWarnings() throws SQLException {
    return this.con.getWarnings();
  }
  
  public boolean isClosed() throws SQLException {
    return this.con.isClosed();
  }
  
  public boolean isReadOnly() throws SQLException {
    return this.con.isReadOnly();
  }
  
  public String nativeSQL(String sql) throws SQLException {
    return this.con.nativeSQL(sql);
  }
  
  public CallableStatement prepareCall(String sql) throws SQLException {
    return null;
  }
  
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    return null;
  }
  
  public void rollback() throws SQLException {
    this.con.rollback();
  }
  
  public void setAutoCommit(boolean autoCommit) throws SQLException {
    this.con.setAutoCommit(autoCommit);
  }
  
  public void setCatalog(String catalog) throws SQLException {
    this.con.setCatalog(catalog);
  }
  
  public void setReadOnly(boolean readOnly) throws SQLException {
    this.con.setReadOnly(readOnly);
  }
  
  public void setTransactionIsolation(int level) throws SQLException {
    this.con.setTransactionIsolation(level);
  }
  
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    this.con.setTypeMap(map);
  }
  
  public void setHoldability(int holdability) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setHoldability() not yet implemented.");
  }
  
  public int getHoldability() throws SQLException {
    throw new UnsupportedOperationException(
        "Method getHoldability() not yet implemented.");
  }
  
  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    throw new UnsupportedOperationException(
        "Method createStatement() not yet implemented.");
  }
  
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    throw new UnsupportedOperationException(
        "Method prepareStatement() not yet implemented.");
  }
  
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    throw new UnsupportedOperationException(
        "Method prepareCall() not yet implemented.");
  }
  
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    throw new UnsupportedOperationException(
        "Method prepareStatement() not yet implemented.");
  }
  
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    throw new UnsupportedOperationException(
        "Method prepareStatement() not yet implemented.");
  }
  
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    throw new UnsupportedOperationException(
        "Method prepareStatement() not yet implemented.");
  }
  
  public Savepoint setSavepoint() {
    return null;
  }
  
  public void releaseSavepoint(Savepoint savepoint) {}
  
  public void rollback(Savepoint savepoint) {}
  
  public Savepoint setSavepoint(String name) {
    return null;
  }
}