package com.js.oa.info.channelmanager.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformationChannelPO implements Serializable {
  private Long channelId;
  
  private String channelName;
  
  private Long channelParentId;
  
  private String channelIdString;
  
  private Set information = null;
  
  private int channelNeedCheckup;
  
  private String channelReader;
  
  private String channelReaderGroup;
  
  private String channelReaderOrg;
  
  private String channelIssuer;
  
  private String channelIssuerGroup;
  
  private String channelIssuerOrg;
  
  private int channelLevel;
  
  private int channelType;
  
  private int channelSort;
  
  private String channelIssuerName;
  
  private String channelReaderName;
  
  private Long createdOrg;
  
  private Long createdEmp;
  
  private String createdEmpName;
  
  private int channelShowType;
  
  private int onDesktop;
  
  private int isRollOnDesktop;
  
  private int channelPosition;
  
  private int positionUpDown;
  
  private int onDepaDesk;
  
  private Set channelProcess = null;
  
  private String userDefine;
  
  private Integer infoNum;
  
  private Integer desktopType;
  
  private int includeChild;
  
  private Long domainId;
  
  private String channelManager;
  
  private String channelManagerOrg;
  
  private String channelManagerGroup;
  
  private String channelManagerName;
  
  private String afficheChannelStatus;
  
  private String isAllowReview;
  
  private Long relProjectId;
  
  private Long proClassId;
  
  private Integer publicType;
  
  private Long corpId;
  
  public Integer getPublicType() {
    return this.publicType;
  }
  
  public void setPublicType(Integer publicType) {
    this.publicType = publicType;
  }
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public String getIsAllowReview() {
    return this.isAllowReview;
  }
  
  public void setIsAllowReview(String isAllowReview) {
    this.isAllowReview = isAllowReview;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public Long getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }
  
  public String getChannelName() {
    return this.channelName;
  }
  
  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }
  
  public int getChannelType() {
    return this.channelType;
  }
  
  public void setChannelType(int channelType) {
    this.channelType = channelType;
  }
  
  public Long getChannelParentId() {
    return this.channelParentId;
  }
  
  public void setChannelParentId(Long channelParentId) {
    this.channelParentId = channelParentId;
  }
  
  public int getChannelLevel() {
    return this.channelLevel;
  }
  
  public void setChannelLevel(int channelLevel) {
    this.channelLevel = channelLevel;
  }
  
  public int getChannelSort() {
    return this.channelSort;
  }
  
  public void setChannelSort(int channelSort) {
    this.channelSort = channelSort;
  }
  
  public String getChannelIdString() {
    return this.channelIdString;
  }
  
  public void setChannelIdString(String channelIdString) {
    this.channelIdString = channelIdString;
  }
  
  public int getChannelNeedCheckup() {
    return this.channelNeedCheckup;
  }
  
  public void setChannelNeedCheckup(int channelNeedCheckup) {
    this.channelNeedCheckup = channelNeedCheckup;
  }
  
  public String getChannelReader() {
    return this.channelReader;
  }
  
  public void setChannelReader(String channelReader) {
    this.channelReader = channelReader;
  }
  
  public String getChannelReaderOrg() {
    return this.channelReaderOrg;
  }
  
  public void setChannelReaderOrg(String channelReaderOrg) {
    this.channelReaderOrg = channelReaderOrg;
  }
  
  public String getChannelReaderGroup() {
    return this.channelReaderGroup;
  }
  
  public void setChannelReaderGroup(String channelReaderGroup) {
    this.channelReaderGroup = channelReaderGroup;
  }
  
  public String getChannelIssuer() {
    return this.channelIssuer;
  }
  
  public void setChannelIssuer(String channelIssuer) {
    this.channelIssuer = channelIssuer;
  }
  
  public String getChannelIssuerOrg() {
    return this.channelIssuerOrg;
  }
  
  public void setChannelIssuerOrg(String channelIssuerOrg) {
    this.channelIssuerOrg = channelIssuerOrg;
  }
  
  public String getChannelIssuerGroup() {
    return this.channelIssuerGroup;
  }
  
  public void setChannelIssuerGroup(String channelIssuerGroup) {
    this.channelIssuerGroup = channelIssuerGroup;
  }
  
  public Set getInformation() {
    return this.information;
  }
  
  public void setInformation(Set information) {
    this.information = information;
  }
  
  public String getChannelIssuerName() {
    return this.channelIssuerName;
  }
  
  public void setChannelIssuerName(String channelIssuerName) {
    this.channelIssuerName = channelIssuerName;
  }
  
  public String getChannelReaderName() {
    return this.channelReaderName;
  }
  
  public void setChannelReaderName(String channelReaderName) {
    this.channelReaderName = channelReaderName;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public int getChannelShowType() {
    return this.channelShowType;
  }
  
  public void setChannelShowType(int channelShowType) {
    this.channelShowType = channelShowType;
  }
  
  public int getOnDesktop() {
    return this.onDesktop;
  }
  
  public void setOnDesktop(int onDesktop) {
    this.onDesktop = onDesktop;
  }
  
  public int getIsRollOnDesktop() {
    return this.isRollOnDesktop;
  }
  
  public void setIsRollOnDesktop(int isRollOnDesktop) {
    this.isRollOnDesktop = isRollOnDesktop;
  }
  
  public int getChannelPosition() {
    return this.channelPosition;
  }
  
  public void setChannelPosition(int channelPosition) {
    this.channelPosition = channelPosition;
  }
  
  public int getPositionUpDown() {
    return this.positionUpDown;
  }
  
  public void setPositionUpDown(int positionUpDown) {
    this.positionUpDown = positionUpDown;
  }
  
  public int getOnDepaDesk() {
    return this.onDepaDesk;
  }
  
  public void setOnDepaDesk(int onDepaDesk) {
    this.onDepaDesk = onDepaDesk;
  }
  
  public Set getChannelProcess() {
    return this.channelProcess;
  }
  
  public void setChannelProcess(Set channelProcess) {
    this.channelProcess = channelProcess;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InformationChannelPO))
      return false; 
    InformationChannelPO castOther = (InformationChannelPO)other;
    return (new EqualsBuilder()).append(getChannelId(), castOther.getChannelId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getChannelId()).toHashCode();
  }
  
  public String getUserDefine() {
    return this.userDefine;
  }
  
  public void setUserDefine(String userDefine) {
    this.userDefine = userDefine;
  }
  
  public Integer getInfoNum() {
    return this.infoNum;
  }
  
  public void setInfoNum(Integer infoNum) {
    this.infoNum = infoNum;
  }
  
  public Integer getDesktopType() {
    return this.desktopType;
  }
  
  public void setDesktopType(Integer desktopType) {
    this.desktopType = desktopType;
  }
  
  public int getIncludeChild() {
    return this.includeChild;
  }
  
  public void setIncludeChild(int includeChild) {
    this.includeChild = includeChild;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getChannelManager() {
    return this.channelManager;
  }
  
  public void setChannelManager(String channelManager) {
    this.channelManager = channelManager;
  }
  
  public String getChannelManagerOrg() {
    return this.channelManagerOrg;
  }
  
  public void setChannelManagerOrg(String channelManagerOrg) {
    this.channelManagerOrg = channelManagerOrg;
  }
  
  public String getChannelManagerGroup() {
    return this.channelManagerGroup;
  }
  
  public void setChannelManagerGroup(String channelManagerGroup) {
    this.channelManagerGroup = channelManagerGroup;
  }
  
  public String getChannelManagerName() {
    return this.channelManagerName;
  }
  
  public String getAfficheChannelStatus() {
    return this.afficheChannelStatus;
  }
  
  public void setChannelManagerName(String channelManagerName) {
    this.channelManagerName = channelManagerName;
  }
  
  public void setAfficheChannelStatus(String afficheChannelStatus) {
    this.afficheChannelStatus = afficheChannelStatus;
  }
  
  public Long getProClassId() {
    return this.proClassId;
  }
  
  public void setProClassId(Long proClassId) {
    this.proClassId = proClassId;
  }
}
