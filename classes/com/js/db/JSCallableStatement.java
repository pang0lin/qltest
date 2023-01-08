package com.js.db;

import com.js.util.util.SQLFormat;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class JSCallableStatement {
  private CallableStatement cstm = null;
  
  JSCallableStatement(CallableStatement cstmArg) {
    this.cstm = cstmArg;
  }
  
  public Array getArray(int i) throws SQLException {
    return this.cstm.getArray(i);
  }
  
  public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
    return this.cstm.getBigDecimal(parameterIndex);
  }
  
  public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
    return this.cstm.getBigDecimal(parameterIndex, scale);
  }
  
  public Blob getBlob(int i) throws SQLException {
    return this.cstm.getBlob(i);
  }
  
  public boolean getBoolean(int parameterIndex) throws SQLException {
    return this.cstm.getBoolean(parameterIndex);
  }
  
  public byte getByte(int parameterIndex) throws SQLException {
    return this.cstm.getByte(parameterIndex);
  }
  
  public byte[] getBytes(int parameterIndex) throws SQLException {
    return this.cstm.getBytes(parameterIndex);
  }
  
  public Clob getClob(int i) throws SQLException {
    return this.cstm.getClob(i);
  }
  
  public Date getDate(int parameterIndex) throws SQLException {
    return this.cstm.getDate(parameterIndex);
  }
  
  public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
    return this.cstm.getDate(parameterIndex, cal);
  }
  
  public double getDouble(int parameterIndex) throws SQLException {
    return this.cstm.getDouble(parameterIndex);
  }
  
  public float getFloat(int parameterIndex) throws SQLException {
    return this.cstm.getFloat(parameterIndex);
  }
  
  public int getInt(int parameterIndex) throws SQLException {
    return this.cstm.getInt(parameterIndex);
  }
  
  public long getLong(int parameterIndex) throws SQLException {
    return this.cstm.getLong(parameterIndex);
  }
  
  public Object getObject(int parameterIndex) throws SQLException {
    return this.cstm.getObject(parameterIndex);
  }
  
  public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
    return this.cstm.getObject(i, map);
  }
  
  public Ref getRef(int i) throws SQLException {
    return this.cstm.getRef(i);
  }
  
  public short getShort(int parameterIndex) throws SQLException {
    return this.cstm.getShort(parameterIndex);
  }
  
  public String getString(int parameterIndex) throws SQLException {
    return SQLFormat.toPageString(this.cstm.getString(parameterIndex));
  }
  
  public Time getTime(int parameterIndex) throws SQLException {
    return this.cstm.getTime(parameterIndex);
  }
  
  public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
    return this.cstm.getTime(parameterIndex, cal);
  }
  
  public Timestamp getTimestamp(int parameterIndex) throws SQLException {
    return this.cstm.getTimestamp(parameterIndex);
  }
  
  public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
    return this.cstm.getTimestamp(parameterIndex, cal);
  }
  
  public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
    this.cstm.registerOutParameter(parameterIndex, sqlType);
  }
  
  public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
    this.cstm.registerOutParameter(parameterIndex, sqlType, scale);
  }
  
  public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
    this.cstm.registerOutParameter(paramIndex, sqlType, typeName);
  }
  
  public boolean wasNull() throws SQLException {
    return this.cstm.wasNull();
  }
  
  public void addBatch() throws SQLException {
    this.cstm.addBatch();
  }
  
  public void clearParameters() throws SQLException {
    this.cstm.clearParameters();
  }
  
  public boolean execute() throws SQLException {
    return this.cstm.execute();
  }
  
  public ResultSet executeQuery() throws SQLException {
    return null;
  }
  
  public int executeUpdate() throws SQLException {
    return this.cstm.executeUpdate();
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return this.cstm.getMetaData();
  }
  
  public void setArray(int i, Array x) throws SQLException {
    this.cstm.setArray(i, x);
  }
  
  public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
    this.cstm.setAsciiStream(parameterIndex, x, length);
  }
  
  public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
    this.cstm.setBigDecimal(parameterIndex, x);
  }
  
  public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
    this.cstm.setBinaryStream(parameterIndex, x, length);
  }
  
  public void setBlob(int i, Blob x) throws SQLException {
    this.cstm.setBlob(i, x);
  }
  
  public void setBoolean(int parameterIndex, boolean x) throws SQLException {
    this.cstm.setBoolean(parameterIndex, x);
  }
  
  public void setByte(int parameterIndex, byte x) throws SQLException {
    this.cstm.setByte(parameterIndex, x);
  }
  
  public void setBytes(int parameterIndex, byte[] x) throws SQLException {
    this.cstm.setBytes(parameterIndex, x);
  }
  
  public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
    this.cstm.setCharacterStream(parameterIndex, reader, length);
  }
  
  public void setClob(int i, Clob x) throws SQLException {
    this.cstm.setClob(i, x);
  }
  
  public void setDate(int parameterIndex, Date x) throws SQLException {
    this.cstm.setDate(parameterIndex, x);
  }
  
  public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
    this.cstm.setDate(parameterIndex, x, cal);
  }
  
  public void setDouble(int parameterIndex, double x) throws SQLException {
    this.cstm.setDouble(parameterIndex, x);
  }
  
  public void setFloat(int parameterIndex, float x) throws SQLException {
    this.cstm.setFloat(parameterIndex, x);
  }
  
  public void setInt(int parameterIndex, int x) throws SQLException {
    this.cstm.setInt(parameterIndex, x);
  }
  
  public void setLong(int parameterIndex, long x) throws SQLException {
    this.cstm.setLong(parameterIndex, x);
  }
  
  public void setNull(int parameterIndex, int sqlType) throws SQLException {
    this.cstm.setNull(parameterIndex, sqlType);
  }
  
  public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
    this.cstm.setNull(paramIndex, sqlType, typeName);
  }
  
  public void setObject(int parameterIndex, Object x) throws SQLException {
    this.cstm.setObject(parameterIndex, x);
  }
  
  public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
    this.cstm.setObject(parameterIndex, x, targetSqlType);
  }
  
  public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
    this.cstm.setObject(parameterIndex, x, targetSqlType, scale);
  }
  
  public void setRef(int i, Ref x) throws SQLException {
    this.cstm.setRef(i, x);
  }
  
  public void setShort(int parameterIndex, short x) throws SQLException {
    this.cstm.setShort(parameterIndex, x);
  }
  
  public void setString(int parameterIndex, String x) throws SQLException {
    this.cstm.setString(parameterIndex, SQLFormat.toDbString(x));
  }
  
  public void setTime(int parameterIndex, Time x) throws SQLException {
    this.cstm.setTime(parameterIndex, x);
  }
  
  public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
    this.cstm.setTime(parameterIndex, x, cal);
  }
  
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    this.cstm.setTimestamp(parameterIndex, x);
  }
  
  public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
    this.cstm.setTimestamp(parameterIndex, x, cal);
  }
  
  public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
    this.cstm.setUnicodeStream(parameterIndex, x, length);
  }
  
  public void addBatch(String sql) throws SQLException {
    this.cstm.addBatch(SQLFormat.toDbString(sql));
  }
  
  public void cancel() throws SQLException {
    this.cstm.cancel();
  }
  
  public void clearBatch() throws SQLException {
    this.cstm.clearBatch();
  }
  
  public void clearWarnings() throws SQLException {
    this.cstm.clearWarnings();
  }
  
  public void close() throws SQLException {
    this.cstm.close();
  }
  
  public boolean execute(String sql) throws SQLException {
    return this.cstm.execute(SQLFormat.toDbString(sql));
  }
  
  public int[] executeBatch() throws SQLException {
    return this.cstm.executeBatch();
  }
  
  public ResultSet executeQuery(String sql) throws SQLException {
    return null;
  }
  
  public int executeUpdate(String sql) throws SQLException {
    return this.cstm.executeUpdate(SQLFormat.toDbString(sql));
  }
  
  public Connection getConnection() throws SQLException {
    return this.cstm.getConnection();
  }
  
  public int getFetchDirection() throws SQLException {
    return this.cstm.getFetchDirection();
  }
  
  public int getFetchSize() throws SQLException {
    return this.cstm.getFetchSize();
  }
  
  public int getMaxFieldSize() throws SQLException {
    return this.cstm.getMaxFieldSize();
  }
  
  public int getMaxRows() throws SQLException {
    return this.cstm.getMaxRows();
  }
  
  public boolean getMoreResults() throws SQLException {
    return this.cstm.getMoreResults();
  }
  
  public int getQueryTimeout() throws SQLException {
    return this.cstm.getQueryTimeout();
  }
  
  public ResultSet getResultSet() throws SQLException {
    return null;
  }
  
  public int getResultSetConcurrency() throws SQLException {
    return this.cstm.getResultSetConcurrency();
  }
  
  public int getResultSetType() throws SQLException {
    return this.cstm.getResultSetType();
  }
  
  public int getUpdateCount() throws SQLException {
    return this.cstm.getUpdateCount();
  }
  
  public SQLWarning getWarnings() throws SQLException {
    return this.cstm.getWarnings();
  }
  
  public void setCursorName(String name) throws SQLException {
    this.cstm.setCursorName(name);
  }
  
  public void setEscapeProcessing(boolean enable) throws SQLException {
    this.cstm.setEscapeProcessing(enable);
  }
  
  public void setFetchDirection(int direction) throws SQLException {
    this.cstm.setFetchDirection(direction);
  }
  
  public void setFetchSize(int rows) throws SQLException {
    this.cstm.setFetchSize(rows);
  }
  
  public void setMaxFieldSize(int max) throws SQLException {
    this.cstm.setMaxFieldSize(max);
  }
  
  public void setMaxRows(int max) throws SQLException {
    this.cstm.setMaxRows(max);
  }
  
  public void setQueryTimeout(int seconds) throws SQLException {
    this.cstm.setQueryTimeout(seconds);
  }
  
  public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
    throw new UnsupportedOperationException(
        "Method registerOutParameter() not yet implemented.");
  }
  
  public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
    throw new UnsupportedOperationException(
        "Method registerOutParameter() not yet implemented.");
  }
  
  public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method registerOutParameter() not yet implemented.");
  }
  
  public URL getURL(int parameterIndex) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getURL() not yet implemented.");
  }
  
  public void setURL(String parameterName, URL val) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setURL() not yet implemented.");
  }
  
  public void setNull(String parameterName, int sqlType) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setNull() not yet implemented.");
  }
  
  public void setBoolean(String parameterName, boolean x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setBoolean() not yet implemented.");
  }
  
  public void setByte(String parameterName, byte x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setByte() not yet implemented.");
  }
  
  public void setShort(String parameterName, short x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setShort() not yet implemented.");
  }
  
  public void setInt(String parameterName, int x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setInt() not yet implemented.");
  }
  
  public void setLong(String parameterName, long x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setLong() not yet implemented.");
  }
  
  public void setFloat(String parameterName, float x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setFloat() not yet implemented.");
  }
  
  public void setDouble(String parameterName, double x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setDouble() not yet implemented.");
  }
  
  public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setBigDecimal() not yet implemented.");
  }
  
  public void setString(String parameterName, String x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setString() not yet implemented.");
  }
  
  public void setBytes(String parameterName, byte[] x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setBytes() not yet implemented.");
  }
  
  public void setDate(String parameterName, Date x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setDate() not yet implemented.");
  }
  
  public void setTime(String parameterName, Time x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setTime() not yet implemented.");
  }
  
  public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setTimestamp() not yet implemented.");
  }
  
  public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setAsciiStream() not yet implemented.");
  }
  
  public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setBinaryStream() not yet implemented.");
  }
  
  public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setObject() not yet implemented.");
  }
  
  public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setObject() not yet implemented.");
  }
  
  public void setObject(String parameterName, Object x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setObject() not yet implemented.");
  }
  
  public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setCharacterStream() not yet implemented.");
  }
  
  public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setDate() not yet implemented.");
  }
  
  public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setTime() not yet implemented.");
  }
  
  public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setTimestamp() not yet implemented.");
  }
  
  public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method setNull() not yet implemented.");
  }
  
  public String getString(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getString() not yet implemented.");
  }
  
  public boolean getBoolean(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getBoolean() not yet implemented.");
  }
  
  public byte getByte(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getByte() not yet implemented.");
  }
  
  public short getShort(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getShort() not yet implemented.");
  }
  
  public int getInt(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getInt() not yet implemented.");
  }
  
  public long getLong(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getLong() not yet implemented.");
  }
  
  public float getFloat(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getFloat() not yet implemented.");
  }
  
  public double getDouble(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getDouble() not yet implemented.");
  }
  
  public byte[] getBytes(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getBytes() not yet implemented.");
  }
  
  public Date getDate(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getDate() not yet implemented.");
  }
  
  public Time getTime(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getTime() not yet implemented.");
  }
  
  public Timestamp getTimestamp(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getTimestamp() not yet implemented.");
  }
  
  public Object getObject(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getObject() not yet implemented.");
  }
  
  public BigDecimal getBigDecimal(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getBigDecimal() not yet implemented.");
  }
  
  public Object getObject(String parameterName, Map map) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getObject() not yet implemented.");
  }
  
  public Ref getRef(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getRef() not yet implemented.");
  }
  
  public Blob getBlob(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getBlob() not yet implemented.");
  }
  
  public Clob getClob(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getClob() not yet implemented.");
  }
  
  public Array getArray(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getArray() not yet implemented.");
  }
  
  public Date getDate(String parameterName, Calendar cal) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getDate() not yet implemented.");
  }
  
  public Time getTime(String parameterName, Calendar cal) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getTime() not yet implemented.");
  }
  
  public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getTimestamp() not yet implemented.");
  }
  
  public URL getURL(String parameterName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getURL() not yet implemented.");
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
