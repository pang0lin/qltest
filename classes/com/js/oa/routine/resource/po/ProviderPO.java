package com.js.oa.routine.resource.po;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProviderPO {
  private String id;
  
  private String name;
  
  private String contactMethod;
  
  private String contacter;
  
  private String accounts;
  
  private String makeOutInvoiceAddress;
  
  private String consignmentAddress;
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getContactMethod() {
    return this.contactMethod;
  }
  
  public void setContactMethod(String contactMethod) {
    this.contactMethod = contactMethod;
  }
  
  public String getContacter() {
    return this.contacter;
  }
  
  public void setContacter(String contacter) {
    this.contacter = contacter;
  }
  
  public String getAccounts() {
    return this.accounts;
  }
  
  public void setAccounts(String accounts) {
    this.accounts = accounts;
  }
  
  public String getMakeOutInvoiceAddress() {
    return this.makeOutInvoiceAddress;
  }
  
  public void setMakeOutInvoiceAddress(String makeOutInvoiceAddress) {
    this.makeOutInvoiceAddress = makeOutInvoiceAddress;
  }
  
  public String getConsignmentAddress() {
    return this.consignmentAddress;
  }
  
  public void setConsignmentAddress(String consignmentAddress) {
    this.consignmentAddress = consignmentAddress;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ProviderPO))
      return false; 
    ProviderPO castOther = (ProviderPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
