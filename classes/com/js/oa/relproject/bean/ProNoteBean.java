package com.js.oa.relproject.bean;

import com.js.oa.relproject.po.ProNotePO;
import com.js.util.hibernate.HibernateBase;

public class ProNoteBean extends HibernateBase {
  public boolean saveNote(ProNotePO po) {
    boolean res = false;
    try {
      begin();
      this.session.save(po);
      this.session.flush();
      this.session.close();
      res = true;
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return res;
  }
  
  public ProNotePO load(Long noteId) {
    ProNotePO po = new ProNotePO();
    try {
      begin();
      po = (ProNotePO)this.session.load(ProNotePO.class, noteId);
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return po;
  }
  
  public void saveProNote(String noteId, String content) {
    ProNotePO po = new ProNotePO();
    try {
      begin();
      po = (ProNotePO)this.session.load(ProNotePO.class, Long.valueOf(noteId));
      po.setContent(content);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public void deleteProNote(String noteId) {
    ProNotePO po = new ProNotePO();
    try {
      begin();
      po = (ProNotePO)this.session.load(ProNotePO.class, Long.valueOf(noteId));
      this.session.delete(po);
      this.session.flush();
      this.session.close();
    } catch (Exception ex) {
      if (this.session != null)
        try {
          this.session.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
}
