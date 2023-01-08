package com.js.oa.archives.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesFileAccessoryPO implements Serializable {
  private Long accessoryid;
  
  private String accessoryname;
  
  private String accessorysavename;
  
  private ArchivesFilePO archivesFile;
  
  public ArchivesFileAccessoryPO(Long accessoryid, String accessoryname, String accessorysavename, ArchivesFilePO archivesFile) {
    this.accessoryid = accessoryid;
    this.accessoryname = accessoryname;
    this.accessorysavename = accessorysavename;
    this.archivesFile = archivesFile;
  }
  
  public ArchivesFileAccessoryPO() {}
  
  public Long getAccessoryid() {
    return this.accessoryid;
  }
  
  public void setAccessoryid(Long accessoryid) {
    this.accessoryid = accessoryid;
  }
  
  public String getAccessoryname() {
    return this.accessoryname;
  }
  
  public void setAccessoryname(String accessoryname) {
    this.accessoryname = accessoryname;
  }
  
  public String getAccessorysavename() {
    return this.accessorysavename;
  }
  
  public void setAccessorysavename(String accessorysavename) {
    this.accessorysavename = accessorysavename;
  }
  
  public ArchivesFilePO getArchivesFile() {
    return this.archivesFile;
  }
  
  public void setArchivesFile(ArchivesFilePO archivesFile) {
    this.archivesFile = archivesFile;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("accessoryid", getAccessoryid())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesFileAccessoryPO))
      return false; 
    ArchivesFileAccessoryPO castOther = (ArchivesFileAccessoryPO)other;
    return (new EqualsBuilder())
      .append(getAccessorysavename(), castOther.getAccessorysavename())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getAccessoryid())
      .toHashCode();
  }
}
