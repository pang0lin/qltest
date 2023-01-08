package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class HortationPunishClassActionForm extends ActionForm {
  private Long id;
  
  private String hp_name;
  
  private String hp_type;
  
  private String hp_apply;
  
  private String hp_dealwith;
  
  private String hp_describe;
  
  private Long domain;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getId() {
    return this.id;
  }
  
  public String getHp_name() {
    return this.hp_name;
  }
  
  public String getHp_type() {
    return this.hp_type;
  }
  
  public String getHp_apply() {
    return this.hp_apply;
  }
  
  public String getHp_dealwith() {
    return this.hp_dealwith;
  }
  
  public String getHp_describe() {
    return this.hp_describe;
  }
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public void setHp_describe(String hp_describe) {
    this.hp_describe = hp_describe;
  }
  
  public void setHp_dealwith(String hp_dealwith) {
    this.hp_dealwith = hp_dealwith;
  }
  
  public void setHp_apply(String hp_apply) {
    this.hp_apply = hp_apply;
  }
  
  public void setHp_type(String hp_type) {
    this.hp_type = hp_type;
  }
  
  public void setHp_name(String hp_name) {
    this.hp_name = hp_name;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
}
