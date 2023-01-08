package com.js.oa.messageWall.po;

import java.io.Serializable;

public class MessageWallGrantPO implements Serializable {
  private long id;
  
  private String wallname;
  
  private String wallmanagerid;
  
  private String wallmanager;
  
  private String wallreader;
  
  private String wallreadorg;
  
  private String wallreadgroup;
  
  private String wallreadname;
  
  private long wallmaxnum;
  
  private long createdemp;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getWallname() {
    return this.wallname;
  }
  
  public void setWallname(String wallname) {
    this.wallname = wallname;
  }
  
  public String getWallmanagerid() {
    return this.wallmanagerid;
  }
  
  public void setWallmanagerid(String wallmanagerid) {
    this.wallmanagerid = wallmanagerid;
  }
  
  public String getWallmanager() {
    return this.wallmanager;
  }
  
  public void setWallmanager(String wallmanager) {
    this.wallmanager = wallmanager;
  }
  
  public String getWallreader() {
    return this.wallreader;
  }
  
  public void setWallreader(String wallreader) {
    this.wallreader = wallreader;
  }
  
  public String getWallreadorg() {
    return this.wallreadorg;
  }
  
  public void setWallreadorg(String wallreadorg) {
    this.wallreadorg = wallreadorg;
  }
  
  public String getWallreadgroup() {
    return this.wallreadgroup;
  }
  
  public void setWallreadgroup(String wallreadgroup) {
    this.wallreadgroup = wallreadgroup;
  }
  
  public String getWallreadname() {
    return this.wallreadname;
  }
  
  public void setWallreadname(String wallreadname) {
    this.wallreadname = wallreadname;
  }
  
  public long getWallmaxnum() {
    return this.wallmaxnum;
  }
  
  public void setWallmaxnum(long wallmaxnum) {
    this.wallmaxnum = wallmaxnum;
  }
  
  public long getCreatedemp() {
    return this.createdemp;
  }
  
  public void setCreatedemp(long createdemp) {
    this.createdemp = createdemp;
  }
  
  public MessageWallGrantPO() {}
  
  public MessageWallGrantPO(long id, String wallname, String wallmanagerid, String wallmanager, String wallreader, String wallreadorg, String wallreadgroup, String wallreadname, long wallmaxnum, long createdemp) {
    this.id = id;
    this.wallname = wallname;
    this.wallmanagerid = wallmanagerid;
    this.wallmanager = wallmanager;
    this.wallreader = wallreader;
    this.wallreadorg = wallreadorg;
    this.wallreadgroup = wallreadgroup;
    this.wallreadname = wallreadname;
    this.wallmaxnum = wallmaxnum;
    this.createdemp = createdemp;
  }
}
