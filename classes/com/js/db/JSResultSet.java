package com.js.db;

import com.js.util.util.SQLFormat;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class JSResultSet {
  ResultSet rs = null;
  
  JSResultSet(ResultSet rsArg) {
    this.rs = rsArg;
  }
  
  public boolean absolute(int row) throws SQLException {
    return this.rs.absolute(row);
  }
  
  public void afterLast() throws SQLException {
    this.rs.afterLast();
  }
  
  public void beforeFirst() throws SQLException {
    this.rs.beforeFirst();
  }
  
  public void cancelRowUpdates() throws SQLException {
    this.rs.cancelRowUpdates();
  }
  
  public void clearWarnings() throws SQLException {
    this.rs.clearWarnings();
  }
  
  public void close() throws SQLException {
    this.rs.close();
  }
  
  public void deleteRow() throws SQLException {
    this.rs.deleteRow();
  }
  
  public int findColumn(String columnName) throws SQLException {
    return this.rs.findColumn(columnName);
  }
  
  public boolean first() throws SQLException {
    return this.rs.first();
  }
  
  public Array getArray(int i) throws SQLException {
    return this.rs.getArray(i);
  }
  
  public Array getArray(String colName) throws SQLException {
    return this.rs.getArray(colName);
  }
  
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    return this.rs.getAsciiStream(columnIndex);
  }
  
  public InputStream getAsciiStream(String columnName) throws SQLException {
    return this.rs.getAsciiStream(columnName);
  }
  
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return this.rs.getBigDecimal(columnIndex);
  }
  
  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
    return this.rs.getBigDecimal(columnIndex, scale);
  }
  
  public BigDecimal getBigDecimal(String columnName) throws SQLException {
    return this.rs.getBigDecimal(columnName);
  }
  
  public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
    return this.rs.getBigDecimal(columnName, scale);
  }
  
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    return this.rs.getBinaryStream(columnIndex);
  }
  
  public InputStream getBinaryStream(String columnName) throws SQLException {
    return this.rs.getBinaryStream(columnName);
  }
  
  public Blob getBlob(int i) throws SQLException {
    return this.rs.getBlob(i);
  }
  
  public Blob getBlob(String colName) throws SQLException {
    return this.rs.getBlob(colName);
  }
  
  public boolean getBoolean(int columnIndex) throws SQLException {
    return this.rs.getBoolean(columnIndex);
  }
  
  public boolean getBoolean(String columnName) throws SQLException {
    return this.rs.getBoolean(columnName);
  }
  
  public byte getByte(int columnIndex) throws SQLException {
    return this.rs.getByte(columnIndex);
  }
  
  public byte getByte(String columnName) throws SQLException {
    return this.rs.getByte(columnName);
  }
  
  public byte[] getBytes(int columnIndex) throws SQLException {
    return this.rs.getBytes(columnIndex);
  }
  
  public byte[] getBytes(String columnName) throws SQLException {
    return this.rs.getBytes(columnName);
  }
  
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    return this.rs.getCharacterStream(columnIndex);
  }
  
  public Reader getCharacterStream(String columnName) throws SQLException {
    return this.rs.getCharacterStream(columnName);
  }
  
  public Clob getClob(int i) throws SQLException {
    return this.rs.getClob(i);
  }
  
  public Clob getClob(String colName) throws SQLException {
    return this.rs.getClob(colName);
  }
  
  public int getConcurrency() throws SQLException {
    return this.rs.getConcurrency();
  }
  
  public String getCursorName() throws SQLException {
    return this.rs.getCursorName();
  }
  
  public Date getDate(int columnIndex) throws SQLException {
    return this.rs.getDate(columnIndex);
  }
  
  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    return this.rs.getDate(columnIndex, cal);
  }
  
  public Date getDate(String columnName) throws SQLException {
    return this.rs.getDate(columnName);
  }
  
  public Date getDate(String columnName, Calendar cal) throws SQLException {
    return this.rs.getDate(columnName, cal);
  }
  
  public double getDouble(int columnIndex) throws SQLException {
    return this.rs.getDouble(columnIndex);
  }
  
  public double getDouble(String columnName) throws SQLException {
    return this.rs.getDouble(columnName);
  }
  
  public int getFetchDirection() throws SQLException {
    return this.rs.getFetchDirection();
  }
  
  public int getFetchSize() throws SQLException {
    return this.rs.getFetchSize();
  }
  
  public float getFloat(int columnIndex) throws SQLException {
    return this.rs.getFloat(columnIndex);
  }
  
  public float getFloat(String columnName) throws SQLException {
    return this.rs.getFloat(columnName);
  }
  
  public int getInt(int columnIndex) throws SQLException {
    return this.rs.getInt(columnIndex);
  }
  
  public int getInt(String columnName) throws SQLException {
    return this.rs.getInt(columnName);
  }
  
  public long getLong(int columnIndex) throws SQLException {
    return this.rs.getLong(columnIndex);
  }
  
  public long getLong(String columnName) throws SQLException {
    return this.rs.getLong(columnName);
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return this.rs.getMetaData();
  }
  
  public Object getObject(int columnIndex) throws SQLException {
    return this.rs.getObject(columnIndex);
  }
  
  public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
    return this.rs.getObject(i, map);
  }
  
  public Object getObject(String columnName) throws SQLException {
    return this.rs.getObject(columnName);
  }
  
  public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
    return this.rs.getObject(colName, map);
  }
  
  public Ref getRef(int i) throws SQLException {
    return this.rs.getRef(i);
  }
  
  public Ref getRef(String colName) throws SQLException {
    return this.rs.getRef(colName);
  }
  
  public int getRow() throws SQLException {
    return this.rs.getRow();
  }
  
  public short getShort(int columnIndex) throws SQLException {
    return this.rs.getShort(columnIndex);
  }
  
  public short getShort(String columnName) throws SQLException {
    return this.rs.getShort(columnName);
  }
  
  public Statement getStatement() throws SQLException {
    return this.rs.getStatement();
  }
  
  public String getString(int columnIndex) throws SQLException {
    return SQLFormat.toPageString(this.rs.getString(columnIndex));
  }
  
  public String getString(String columnName) throws SQLException {
    return SQLFormat.toPageString(this.rs.getString(columnName));
  }
  
  public Time getTime(int columnIndex) throws SQLException {
    return this.rs.getTime(columnIndex);
  }
  
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return this.rs.getTime(columnIndex, cal);
  }
  
  public Time getTime(String columnName) throws SQLException {
    return this.rs.getTime(columnName);
  }
  
  public Time getTime(String columnName, Calendar cal) throws SQLException {
    return this.rs.getTime(columnName, cal);
  }
  
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return this.rs.getTimestamp(columnIndex);
  }
  
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    return this.rs.getTimestamp(columnIndex, cal);
  }
  
  public Timestamp getTimestamp(String columnName) throws SQLException {
    return this.rs.getTimestamp(columnName);
  }
  
  public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
    return this.rs.getTimestamp(columnName, cal);
  }
  
  public int getType() throws SQLException {
    return this.rs.getType();
  }
  
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    return this.rs.getUnicodeStream(columnIndex);
  }
  
  public InputStream getUnicodeStream(String columnName) throws SQLException {
    return this.rs.getUnicodeStream(columnName);
  }
  
  public SQLWarning getWarnings() throws SQLException {
    return this.rs.getWarnings();
  }
  
  public void insertRow() throws SQLException {
    this.rs.insertRow();
  }
  
  public boolean isAfterLast() throws SQLException {
    return this.rs.isAfterLast();
  }
  
  public boolean isBeforeFirst() throws SQLException {
    return this.rs.isBeforeFirst();
  }
  
  public boolean isFirst() throws SQLException {
    return this.rs.isFirst();
  }
  
  public boolean isLast() throws SQLException {
    return this.rs.isLast();
  }
  
  public boolean last() throws SQLException {
    return this.rs.last();
  }
  
  public void moveToCurrentRow() throws SQLException {
    this.rs.moveToCurrentRow();
  }
  
  public void moveToInsertRow() throws SQLException {
    this.rs.moveToInsertRow();
  }
  
  public boolean next() throws SQLException {
    return this.rs.next();
  }
  
  public boolean previous() throws SQLException {
    return this.rs.previous();
  }
  
  public void refreshRow() throws SQLException {
    this.rs.refreshRow();
  }
  
  public boolean relative(int rows) throws SQLException {
    return this.rs.relative(rows);
  }
  
  public boolean rowDeleted() throws SQLException {
    return this.rs.rowDeleted();
  }
  
  public boolean rowInserted() throws SQLException {
    return this.rs.rowInserted();
  }
  
  public boolean rowUpdated() throws SQLException {
    return this.rs.rowUpdated();
  }
  
  public void setFetchDirection(int direction) throws SQLException {
    this.rs.setFetchDirection(direction);
  }
  
  public void setFetchSize(int rows) throws SQLException {
    this.rs.setFetchSize(rows);
  }
  
  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
    this.rs.updateAsciiStream(columnIndex, x, length);
  }
  
  public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
    this.rs.updateAsciiStream(columnName, x, length);
  }
  
  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
    this.rs.updateBigDecimal(columnIndex, x);
  }
  
  public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
    this.rs.updateBigDecimal(columnName, x);
  }
  
  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
    this.rs.updateBinaryStream(columnIndex, x, length);
  }
  
  public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
    this.rs.updateBinaryStream(columnName, x, length);
  }
  
  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    this.rs.updateBoolean(columnIndex, x);
  }
  
  public void updateBoolean(String columnName, boolean x) throws SQLException {
    this.rs.updateBoolean(columnName, x);
  }
  
  public void updateByte(int columnIndex, byte x) throws SQLException {
    this.rs.updateByte(columnIndex, x);
  }
  
  public void updateByte(String columnName, byte x) throws SQLException {
    this.rs.updateByte(columnName, x);
  }
  
  public void updateBytes(int columnIndex, byte[] x) throws SQLException {
    this.rs.updateBytes(columnIndex, x);
  }
  
  public void updateBytes(String columnName, byte[] x) throws SQLException {
    this.rs.updateBytes(columnName, x);
  }
  
  public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
    this.rs.updateCharacterStream(columnIndex, x, length);
  }
  
  public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
    this.rs.updateCharacterStream(columnName, reader, length);
  }
  
  public void updateDate(int columnIndex, Date x) throws SQLException {
    this.rs.updateDate(columnIndex, x);
  }
  
  public void updateDate(String columnName, Date x) throws SQLException {
    this.rs.updateDate(columnName, x);
  }
  
  public void updateDouble(int columnIndex, double x) throws SQLException {
    this.rs.updateDouble(columnIndex, x);
  }
  
  public void updateDouble(String columnName, double x) throws SQLException {
    this.rs.updateDouble(columnName, x);
  }
  
  public void updateFloat(int columnIndex, float x) throws SQLException {
    this.rs.updateFloat(columnIndex, x);
  }
  
  public void updateFloat(String columnName, float x) throws SQLException {
    this.rs.updateFloat(columnName, x);
  }
  
  public void updateInt(int columnIndex, int x) throws SQLException {
    this.rs.updateInt(columnIndex, x);
  }
  
  public void updateInt(String columnName, int x) throws SQLException {
    this.rs.updateInt(columnName, x);
  }
  
  public void updateLong(int columnIndex, long x) throws SQLException {
    this.rs.updateLong(columnIndex, x);
  }
  
  public void updateLong(String columnName, long x) throws SQLException {
    this.rs.updateLong(columnName, x);
  }
  
  public void updateNull(int columnIndex) throws SQLException {
    this.rs.updateNull(columnIndex);
  }
  
  public void updateNull(String columnName) throws SQLException {
    this.rs.updateNull(columnName);
  }
  
  public void updateObject(int columnIndex, Object x) throws SQLException {
    this.rs.updateObject(columnIndex, x);
  }
  
  public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
    this.rs.updateObject(columnIndex, x, scale);
  }
  
  public void updateObject(String columnName, Object x) throws SQLException {
    this.rs.updateObject(columnName, x);
  }
  
  public void updateObject(String columnName, Object x, int scale) throws SQLException {
    this.rs.updateObject(columnName, x, scale);
  }
  
  public void updateRow() throws SQLException {
    this.rs.updateRow();
  }
  
  public void updateShort(int columnIndex, short x) throws SQLException {
    this.rs.updateShort(columnIndex, x);
  }
  
  public void updateShort(String columnName, short x) throws SQLException {
    this.rs.updateShort(columnName, x);
  }
  
  public void updateString(int columnIndex, String x) throws SQLException {
    this.rs.updateString(columnIndex, SQLFormat.toDbString(x));
  }
  
  public void updateString(String columnName, String x) throws SQLException {
    this.rs.updateString(columnName, SQLFormat.toDbString(x));
  }
  
  public void updateTime(int columnIndex, Time x) throws SQLException {
    this.rs.updateTime(columnIndex, x);
  }
  
  public void updateTime(String columnName, Time x) throws SQLException {
    this.rs.updateTime(columnName, x);
  }
  
  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
    this.rs.updateTimestamp(columnIndex, x);
  }
  
  public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
    this.rs.updateTimestamp(columnName, x);
  }
  
  public boolean wasNull() throws SQLException {
    return this.rs.wasNull();
  }
  
  public URL getURL(int columnIndex) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getURL() not yet implemented.");
  }
  
  public URL getURL(String columnName) throws SQLException {
    throw new UnsupportedOperationException(
        "Method getURL() not yet implemented.");
  }
  
  public void updateRef(int columnIndex, Ref x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateRef() not yet implemented.");
  }
  
  public void updateRef(String columnName, Ref x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateRef() not yet implemented.");
  }
  
  public void updateBlob(int columnIndex, Blob x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateBlob() not yet implemented.");
  }
  
  public void updateBlob(String columnName, Blob x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateBlob() not yet implemented.");
  }
  
  public void updateClob(int columnIndex, Clob x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateClob() not yet implemented.");
  }
  
  public void updateClob(String columnName, Clob x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateClob() not yet implemented.");
  }
  
  public void updateArray(int columnIndex, Array x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateArray() not yet implemented.");
  }
  
  public void updateArray(String columnName, Array x) throws SQLException {
    throw new UnsupportedOperationException(
        "Method updateArray() not yet implemented.");
  }
}
