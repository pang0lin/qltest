package com.js.oa.archives.vo;

import com.js.oa.archives.po.ArchivesPigeonholeSetPO;
import java.io.Serializable;

public class ArchivesPigeonholeSetVO implements Serializable {
  private Long pigeonholeSetId;
  
  private String pigeonholeSetType;
  
  private String pigeonholeSetPlace;
  
  private Integer isHold;
  
  private Integer isSendMessage;
  
  public ArchivesPigeonholeSetVO() {}
  
  public Integer getIsHold() {
    return this.isHold;
  }
  
  public void setIsHold(Integer isHold) {
    this.isHold = isHold;
  }
  
  public Integer getIsSendMessage() {
    return this.isSendMessage;
  }
  
  public void setIsSendMessage(Integer isSendMessage) {
    this.isSendMessage = isSendMessage;
  }
  
  public Long getPigeonholeSetId() {
    return this.pigeonholeSetId;
  }
  
  public void setPigeonholeSetId(Long pigeonholeSetId) {
    this.pigeonholeSetId = pigeonholeSetId;
  }
  
  public String getPigeonholeSetPlace() {
    return this.pigeonholeSetPlace;
  }
  
  public void setPigeonholeSetPlace(String pigeonholeSetPlace) {
    this.pigeonholeSetPlace = pigeonholeSetPlace;
  }
  
  public String getPigeonholeSetType() {
    return this.pigeonholeSetType;
  }
  
  public void setPigeonholeSetType(String pigeonholeSetType) {
    this.pigeonholeSetType = pigeonholeSetType;
  }
  
  public ArchivesPigeonholeSetVO(Long pigeonholeSetId, String pigeonholeSetType, String pigeonholeSetPlace, Integer isHold, Integer isSendMessage) {
    this.pigeonholeSetId = pigeonholeSetId;
    this.pigeonholeSetType = pigeonholeSetType;
    this.pigeonholeSetPlace = pigeonholeSetPlace;
    this.isHold = isHold;
    this.isSendMessage = isSendMessage;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof ArchivesPigeonholeSetVO))
      return false; 
    ArchivesPigeonholeSetVO archivesPigeonholeSetVO = (ArchivesPigeonholeSetVO)o;
    if (!this.pigeonholeSetId.equals(archivesPigeonholeSetVO.pigeonholeSetId))
      return false; 
    if (!this.pigeonholeSetType.equals(archivesPigeonholeSetVO.pigeonholeSetType))
      return false; 
    if (!this.pigeonholeSetPlace.equals(archivesPigeonholeSetVO.pigeonholeSetPlace))
      return false; 
    if (!this.isHold.equals(archivesPigeonholeSetVO.isHold))
      return false; 
    if (!this.isSendMessage.equals(archivesPigeonholeSetVO.isSendMessage))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.pigeonholeSetId.hashCode();
    return result;
  }
  
  public ArchivesPigeonholeSetVO conversionPO(ArchivesPigeonholeSetPO archivesPigeonholeSetPO) {
    this.pigeonholeSetId = archivesPigeonholeSetPO.getPigeonholeSetId();
    this.pigeonholeSetType = archivesPigeonholeSetPO.getPigeonholeSetType();
    this.pigeonholeSetPlace = archivesPigeonholeSetPO.getPigeonholeSetPlace();
    this.isHold = archivesPigeonholeSetPO.getIsHold();
    this.isSendMessage = archivesPigeonholeSetPO.getIsSendMessage();
    return this;
  }
}
