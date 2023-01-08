package com.js.db;

import com.js.util.util.SQLFormat;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class JSPreparedStatement {
  private PreparedStatement pstm = null;
  
  JSPreparedStatement(PreparedStatement pstmArg) {
    this.pstm = pstmArg;
  }
  
  public void addBatch() throws SQLException {
    this.pstm.addBatch();
  }
  
  public void clearParameters() throws SQLException {
    this.pstm.clearParameters();
  }
  
  public boolean execute() throws SQLException {
    return this.pstm.execute();
  }
  
  public ResultSet executeQuery() throws SQLException {
    return null;
  }
  
  public int executeUpdate() throws SQLException {
    return this.pstm.executeUpdate();
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return this.pstm.getMetaData();
  }
  
  public void setArray(int i, Array x) throws SQLException {
    this.pstm.setArray(i, x);
  }
  
  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
    this.pstm.setAsciiStream(parameterIndex, x, length);
  }
  
  public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    this.pstm.setBigDecimal(parameterIndex, x);
  }
  
  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
    this.pstm.setBinaryStream(parameterIndex, x, length);
  }
  
  public void setBlob(int i, Blob x) throws SQLException {
    this.pstm.setBlob(i, x);
  }
  
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    this.pstm.setBoolean(parameterIndex, x);
  }
  
  public void setByte(int parameterIndex, byte x) throws SQLException {
    this.pstm.setByte(parameterIndex, x);
  }
  
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    this.pstm.setBytes(parameterIndex, x);
  }
  
  public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
    this.pstm.setCharacterStream(parameterIndex, reader, length);
  }
  
  public void setClob(int i, Clob x) throws SQLException {
    this.pstm.setClob(i, x);
  }
  
  public void setDate(int parameterIndex, Date x) throws SQLException {
    this.pstm.setDate(parameterIndex, x);
  }
  
  public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
    this.pstm.setDate(parameterIndex, x, cal);
  }
  
  public void setDouble(int parameterIndex, double x) throws SQLException {
    this.pstm.setDouble(parameterIndex, x);
  }
  
  public void setFloat(int parameterIndex, float x) throws SQLException {
    this.pstm.setFloat(parameterIndex, x);
  }
  
  public void setInt(int parameterIndex, int x) throws SQLException {
    this.pstm.setInt(parameterIndex, x);
  }
  
  public void setLong(int parameterIndex, long x) throws SQLException {
    this.pstm.setLong(parameterIndex, x);
  }
  
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    this.pstm.setNull(parameterIndex, sqlType);
  }
  
  public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
    this.pstm.setNull(paramIndex, sqlType, typeName);
  }
  
  public void setObject(int parameterIndex, Object x) throws SQLException {
    this.pstm.setObject(parameterIndex, x);
  }
  
  public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    this.pstm.setObject(parameterIndex, x, targetSqlType);
  }
  
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
    this.pstm.setObject(parameterIndex, x, targetSqlType, scale);
  }
  
  public void setRef(int i, Ref x) throws SQLException {
    this.pstm.setRef(i, x);
  }
  
  public void setShort(int parameterIndex, short x) throws SQLException {
    this.pstm.setShort(parameterIndex, x);
  }
  
  public void setString(int parameterIndex, String x) throws SQLException {
    this.pstm.setString(parameterIndex, SQLFormat.toDbString(x));
  }
  
  public void setTime(int parameterIndex, Time x) throws SQLException {
    this.pstm.setTime(parameterIndex, x);
  }
  
  public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
    this.pstm.setTime(parameterIndex, x, cal);
  }
  
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    this.pstm.setTimestamp(parameterIndex, x);
  }
  
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
    this.pstm.setTimestamp(parameterIndex, x, cal);
  }
  
  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
    this.pstm.setUnicodeStream(parameterIndex, x, length);
  }
  
  public void addBatch(String sql) throws SQLException {
    this.pstm.addBatch(SQLFormat.toDbString(sql));
  }
  
  public void cancel() throws SQLException {
    this.pstm.cancel();
  }
  
  public void clearBatch() throws SQLException {
    this.pstm.clearBatch();
  }
  
  public void clearWarnings() throws SQLException {
    this.pstm.clearWarnings();
  }
  
  public void close() throws SQLException {
    this.pstm.close();
  }
  
  public boolean execute(String sql) throws SQLException {
    return this.pstm.execute(SQLFormat.toDbString(sql));
  }
  
  public int[] executeBatch() throws SQLException {
    return this.pstm.executeBatch();
  }
  
  public ResultSet executeQuery(String sql) throws SQLException {
    return null;
  }
  
  public int executeUpdate(String sql) throws SQLException {
    return this.pstm.executeUpdate(SQLFormat.toDbString(sql));
  }
  
  public Connection getConnection() throws SQLException {
    return this.pstm.getConnection();
  }
  
  public int getFetchDirection() throws SQLException {
    return this.pstm.getFetchDirection();
  }
  
  public int getFetchSize() throws SQLException {
    return this.pstm.getFetchSize();
  }
  
  public int getMaxFieldSize() throws SQLException {
    return this.pstm.getMaxFieldSize();
  }
  
  public int getMaxRows() throws SQLException {
    return this.pstm.getMaxRows();
  }
  
  public boolean getMoreResults() throws SQLException {
    return this.pstm.getMoreResults();
  }
  
  public int getQueryTimeout() throws SQLException {
    return this.pstm.getQueryTimeout();
  }
  
  public ResultSet getResultSet() throws SQLException {
    return null;
  }
  
  public int getResultSetConcurrency() throws SQLException {
    return this.pstm.getResultSetConcurrency();
  }
  
  public int getResultSetType() throws SQLException {
    return this.pstm.getResultSetType();
  }
  
  public int getUpdateCount() throws SQLException {
    return this.pstm.getUpdateCount();
  }
  
  public SQLWarning getWarnings() throws SQLException {
    return this.pstm.getWarnings();
  }
  
  public void setCursorName(String name) throws SQLException {
    this.pstm.setCursorName(name);
  }
  
  public void setEscapeProcessing(boolean enable) throws SQLException {
    this.pstm.setEscapeProcessing(enable);
  }
  
  public void setFetchDirection(int direction) throws SQLException {
    this.pstm.setFetchDirection(direction);
  }
  
  public void setFetchSize(int rows) throws SQLException {
    this.pstm.setFetchSize(rows);
  }
  
  public void setMaxFieldSize(int max) throws SQLException {
    this.pstm.setMaxFieldSize(max);
  }
  
  public void setMaxRows(int max) throws SQLException {
    this.pstm.setMaxRows(max);
  }
  
  public void setQueryTimeout(int seconds) throws SQLException {
    this.pstm.setQueryTimeout(seconds);
  }
  
  public void setURL(int parameterIndex, URL x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setURL() not yet implemented.");
  }
  
  public boolean getMoreResults(int current) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getMoreResults() not yet implemented.");
  }
  
  public ResultSet getGeneratedKeys() throws SQLException {
    throw new UnsupportedOperationException(
        "Method getGeneratedKeys() not yet implemented.");
  }
  
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    throw new UnsupportedOperationException(
        "Method executeUpdate() not yet implemented.");
  }
  
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    throw new UnsupportedOperationException(
        "Method executeUpdate() not yet implemented.");
  }
  
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    throw new UnsupportedOperationException(
        "Method executeUpdate() not yet implemented.");
  }
  
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    throw new UnsupportedOperationException(
        "Method execute() not yet implemented.");
  }
  
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    throw new UnsupportedOperationException(
        "Method execute() not yet implemented.");
  }
  
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    throw new UnsupportedOperationException(
        "Method execute() not yet implemented.");
  }
  
  public int getResultSetHoldability() throws SQLException {
    throw new UnsupportedOperationException(
        "Method getResultSetHoldability() not yet implemented.");
  }
  
  public ParameterMetaData getParameterMetaData() {
    return null;
  }
}
