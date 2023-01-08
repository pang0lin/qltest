package com.qq.weixin.mp.config.pojo;

import java.util.List;

public class ComplexMenu extends Menu {
  List<Menu> subMenu;
  
  public List<Menu> getSubMenu() {
    return this.subMenu;
  }
  
  public void setSubMenu(List<Menu> subMenu) {
    this.subMenu = subMenu;
  }
}
