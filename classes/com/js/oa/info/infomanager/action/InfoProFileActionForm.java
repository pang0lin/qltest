package com.js.oa.info.infomanager.action;

import org.apache.struts.action.ActionForm;

public class InfoProFileActionForm extends ActionForm {
  private String proFile;
  
  private String fileName;
  
  private String fileNum;
  
  private String character;
  
  private String version;
  
  private String fileDate;
  
  private String reviewDate;
  
  private String department;
  
  private String departmentId;
  
  private String author;
  
  private String authorId;
  
  private String viewManId;
  
  private String viewMan;
  
  public String getProFile() {
    return this.proFile;
  }
  
  public void setProFile(String proFile) {
    this.proFile = proFile;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileNum() {
    return this.fileNum;
  }
  
  public void setFileNum(String fileNum) {
    this.fileNum = fileNum;
  }
  
  public String getCharacter() {
    return this.character;
  }
  
  public void setCharacter(String character) {
    this.character = character;
  }
  
  public String getFileDate() {
    return this.fileDate;
  }
  
  public void setFileDate(String fileDate) {
    this.fileDate = fileDate;
  }
  
  public String getReviewDate() {
    return this.reviewDate;
  }
  
  public void setReviewDate(String reviewDate) {
    this.reviewDate = reviewDate;
  }
  
  public String getDepartment() {
    return this.department;
  }
  
  public void setDepartment(String department) {
    this.department = department;
  }
  
  public String getAuthor() {
    return this.author;
  }
  
  public void setAuthor(String author) {
    this.author = author;
  }
  
  public String getViewMan() {
    return this.viewMan;
  }
  
  public void setViewMan(String viewMan) {
    this.viewMan = viewMan;
  }
  
  public String getViewManId() {
    return this.viewManId;
  }
  
  public void setViewManId(String viewManId) {
    this.viewManId = viewManId;
  }
  
  public String getVersion() {
    return this.version;
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
  
  public String getAuthorId() {
    return this.authorId;
  }
  
  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
  
  public String getDepartmentId() {
    return this.departmentId;
  }
  
  public void setDepartmentId(String departmentId) {
    this.departmentId = departmentId;
  }
}
