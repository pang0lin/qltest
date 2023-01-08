package com.js.oa.chinaLife.tam;

public class TAMUserOp {
  TAMWebService ts = new TAMWebService();
  
  public boolean addTAMUser(String uid) {
    return this.ts.addTAMUser(uid);
  }
  
  public boolean modifyTAMUser(String uid) {
    return this.ts.modiTAMUser(uid);
  }
  
  public boolean delTAMUser(String uid) {
    return this.ts.delTAMUser(uid);
  }
  
  public static void main(String[] args) {
    TAMUserOp op = new TAMUserOp();
    System.out.println("0");
    op.addTAMUser("lify");
  }
}
