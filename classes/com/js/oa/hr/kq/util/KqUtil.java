package com.js.oa.hr.kq.util;

import com.js.oa.hr.kq.po.KqDutySetPO;
import com.js.oa.hr.kq.po.KqRecordPO;
import com.js.oa.hr.kq.service.KqDutySetBD;
import com.js.oa.hr.kq.service.KqHolidayBD;
import com.js.oa.hr.kq.service.KqNosignBD;
import com.js.oa.hr.kq.service.KqRecordBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.SysSetupReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KqUtil {
  public static void creatRecord() throws Exception {
    SysSetupReader sysRed = SysSetupReader.getInstance();
    String kqflag = sysRed.getKq("0");
    if ("1".equals(kqflag)) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date nowDate = new Date();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(nowDate);
      calendar.add(5, -1);
      KqHolidayBD kqHolidayBD = new KqHolidayBD();
      boolean isHoliday = kqHolidayBD.searchByDate(format.format(calendar.getTime()));
      if (isHoliday) {
        UserBD userBD = new UserBD();
        List<Object[]> empList = new ArrayList();
        empList = userBD.selectEmpForRecord();
        if (!empList.isEmpty() && empList.size() > 0) {
          KqRecordBD kqRecordBD = new KqRecordBD();
          for (int i = 0; i < empList.size(); i++) {
            Object[] obj = empList.get(i);
            KqDutySetBD kqDutySetBD = new KqDutySetBD();
            KqDutySetPO kqDutySetPO = kqDutySetBD.searchByUserId(Long.valueOf(obj[0].toString()).longValue());
            if ("1".equals(String.valueOf(kqDutySetPO.getWorkday().charAt(calendar.get(7) - 1)))) {
              KqNosignBD kqNosignBD = new KqNosignBD();
              boolean isNosign = false;
              for (int j = 0; j < 6; j++) {
                String dutyTime = "0";
                if (j == 0) {
                  dutyTime = kqDutySetPO.getDutyTime1();
                } else if (j == 1) {
                  dutyTime = kqDutySetPO.getDutyTime2();
                } else if (j == 2) {
                  dutyTime = kqDutySetPO.getDutyTime3();
                } else if (j == 3) {
                  dutyTime = kqDutySetPO.getDutyTime4();
                } else if (j == 4) {
                  dutyTime = kqDutySetPO.getDutyTime5();
                } else if (j == 5) {
                  dutyTime = kqDutySetPO.getDutyTime6();
                } 
                if (!"0".equals(dutyTime)) {
                  KqRecordPO kqRecordPO = new KqRecordPO();
                  kqRecordPO.setUserId(Long.valueOf(obj[0].toString()).longValue());
                  kqRecordPO.setOrgId(Long.valueOf(obj[1].toString()).longValue());
                  kqRecordPO.setRecordSeq(j + 1);
                  kqRecordPO.setDutyName(kqDutySetPO.getDutyName());
                  if (j % 2 == 0) {
                    kqRecordPO.setDutyType(1);
                  } else {
                    kqRecordPO.setDutyType(2);
                  } 
                  String standard = dutyTime;
                  String[] standards = standard.split(":");
                  calendar.set(11, Integer.valueOf(standards[0].toString()).intValue());
                  calendar.set(12, Integer.valueOf(standards[1].toString()).intValue());
                  kqRecordPO.setDutyTime(calendar.getTime());
                  isNosign = kqNosignBD.isNosignByUser(Long.valueOf(obj[0].toString()).longValue(), j + 1, "", "");
                  if (isNosign) {
                    kqRecordPO.setRecordStatus(0);
                    kqRecordPO.setNosign(1);
                    kqRecordPO.setTimeDiff(0L);
                    kqRecordPO.setRecordTime(calendar.getTime());
                  } else {
                    kqRecordPO.setRecordStatus(-1);
                    kqRecordPO.setNosign(0);
                  } 
                  kqRecordBD.add(kqRecordPO);
                } 
              } 
            } 
          } 
        } 
      } 
    } 
  }
}
