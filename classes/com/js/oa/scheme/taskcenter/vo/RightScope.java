package com.js.oa.scheme.taskcenter.vo;

import java.io.Serializable;

public class RightScope implements Serializable {
  int scopeType;
  
  String scope;
  
  public RightScope(int scopeType, String scope) {
    this.scopeType = scopeType;
    this.scope = scope;
  }
  
  public int getScopeType() {
    return this.scopeType;
  }
  
  public void setScopeType(int scopeType) {
    this.scopeType = scopeType;
  }
  
  public String getScope() {
    return this.scope;
  }
  
  public void setScope(String scope) {
    this.scope = scope;
  }
  
  public RightScope() {}
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof RightScope))
      return false; 
    RightScope rightScope = (RightScope)o;
    if (this.scopeType != rightScope.scopeType)
      return false; 
    if ((this.scope != null) ? !this.scope.equals(rightScope.scope) : (rightScope.scope != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.scopeType;
    result = 29 * result + ((this.scope != null) ? this.scope.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "RightScope{scopeType=" + 
      this.scopeType + 
      ", scope='" + this.scope + "'" + 
      "}";
  }
}
