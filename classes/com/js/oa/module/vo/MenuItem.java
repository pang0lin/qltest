package com.js.oa.module.vo;

public class MenuItem {
  private String menuLevel;
  
  private int menuLocation;
  
  private String action;
  
  private String name;
  
  private String helper;
  
  private int parent;
  
  private int level;
  
  private boolean isLeaf;
  
  public String getMenuLevel() {
    return this.menuLevel;
  }
  
  public void setMenuLevel(String level) {
    this.menuLevel = level;
  }
  
  public int getParent() {
    return this.parent;
  }
  
  public void setMenuLocation(int location) {
    this.menuLocation = location;
  }
  
  public int getMenuLocation() {
    return this.menuLocation;
  }
  
  public String getName() {
    return this.name;
  }
  
  public boolean isIsLeaf() {
    return this.isLeaf;
  }
  
  public String getHelper() {
    return this.helper;
  }
  
  public String getAction() {
    return this.action;
  }
  
  public void setLevel(int level) {
    this.level = level;
  }
  
  public void setParent(int parent) {
    this.parent = parent;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setIsLeaf(boolean isLeaf) {
    this.isLeaf = isLeaf;
  }
  
  public void setHelper(String helper) {
    this.helper = helper;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public int getLevel() {
    return this.level;
  }
  
  public MenuItem() {}
  
  public MenuItem(String oLevel, int location, String action, String name, String helper, int parent, int level, boolean isLeaf) {
    this.menuLevel = oLevel;
    this.menuLocation = location;
    this.action = action;
    this.name = name;
    this.helper = helper;
    this.parent = parent;
    this.level = level;
    this.isLeaf = isLeaf;
  }
}
