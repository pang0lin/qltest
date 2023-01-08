package com.js.oa.jsflow.po;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkFlowChannelPO implements Serializable {
  private Long workFlowChannelId;
  
  private Long processId;
  
  private InformationChannelPO channel = null;
  
  public Long getWorkFlowChannelId() {
    return this.workFlowChannelId;
  }
  
  public void setWorkFlowChannelId(Long workFlowChannelId) {
    this.workFlowChannelId = workFlowChannelId;
  }
  
  public InformationChannelPO getChannel() {
    return this.channel;
  }
  
  public void setChannel(InformationChannelPO channel) {
    this.channel = channel;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkFlowChannelPO))
      return false; 
    WorkFlowChannelPO castOther = (WorkFlowChannelPO)other;
    return (new EqualsBuilder()).append(getWorkFlowChannelId(), castOther.getWorkFlowChannelId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWorkFlowChannelId()).toHashCode();
  }
}
