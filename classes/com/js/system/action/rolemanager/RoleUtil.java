package com.js.system.action.rolemanager;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.config.ZyPojo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleUtil {
  public static List<ZyPojo> getZyRoleList(String empId) {
    List<ZyPojo> list = new ArrayList<ZyPojo>();
    for (int i = 0; i < SystemCommon.getZyMaps().size(); i++) {
      ZyPojo pojo = SystemCommon.getZyMaps().get(i);
      DbOpt db = new DbOpt();
      try {
        String count = db.executeQueryToStr("select count(*) n from org_role where role_id=" + pojo.getRoleid() + " and roleuserid like '%$" + empId + "$%'");
        if (!"0".equals(count))
          list.add(pojo); 
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
}
