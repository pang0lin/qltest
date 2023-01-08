package com.js.oa.form.kq;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaiBanUtil {
  private String databaseType = SystemCommon.getDatabaseType();
  
  String[] dateStrs = null;
  
  String[] dateHour = null;
  
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  SimpleDateFormat riqiFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
  
  public long getHour(String[][] paibanAll, String begin, String end) throws Exception {
    String dateStr = ",";
    String longStr = ",";
    String[] paiban = (String[])null;
    long dayLong = 86400000L;
    long returnValue = 0L;
    Date beginDate = this.format.parse(String.valueOf(begin) + ":00");
    Date endDate = this.format.parse(String.valueOf(end) + ":00");
    Calendar beginCal = Calendar.getInstance();
    beginCal.setTimeInMillis(beginDate.getTime());
    int beginWeek = beginCal.get(7) - 1;
    Calendar endCal = Calendar.getInstance();
    endCal.setTimeInMillis(endDate.getTime());
    int endWeek = endCal.get(7) - 1;
    long beginMore = (beginDate.getTime() + 28800000L) % dayLong;
    long endMore = (endDate.getTime() + 28800000L) % dayLong;
    if (beginDate.getTime() - beginMore == endDate.getTime() - endMore) {
      long[] paibanLong = new long[6];
      paiban = getPaiBan(paibanAll, beginDate);
      char[] week = paiban[6].toCharArray();
      if (week[beginWeek] == '1')
        paibanLong = getPaiBan(beginDate, paiban); 
      long zhengtian = getBetweenHour(paibanLong);
      long beginLong = getBeginHour(paibanLong, endDate);
      long endLong = getEndHour(paibanLong, beginDate);
      returnValue = zhengtian - beginLong - endLong;
      if (returnValue != 0L) {
        dateStr = String.valueOf(dateStr) + this.riqiFormat.format(beginDate) + ",";
        longStr = String.valueOf(longStr) + ((float)returnValue / (float)getBetweenHour(paibanLong) * Float.valueOf(paiban[7]).floatValue()) + ",";
      } else {
        dateStr = String.valueOf(dateStr) + this.riqiFormat.format(beginDate) + ",";
        longStr = String.valueOf(longStr) + "0,";
      } 
    } else {
      long riqiLong = beginDate.getTime() - beginMore;
      long[] paibanLong = new long[6];
      paiban = getPaiBan(paibanAll, beginDate);
      char[] week = paiban[6].toCharArray();
      if (week[beginWeek] == '1')
        paibanLong = getPaiBan(beginDate, paiban); 
      long firstLong = getBeginHour(paibanLong, beginDate);
      if (firstLong != 0L) {
        dateStr = String.valueOf(dateStr) + this.riqiFormat.format(beginDate) + ",";
        longStr = String.valueOf(longStr) + ((float)firstLong / (float)getBetweenHour(paibanLong) * Float.valueOf(paiban[7]).floatValue()) + ",";
      } 
      long betweenLong = 0L;
      for (long l = riqiLong + dayLong; l < endDate.getTime() - endMore; l += dayLong) {
        Calendar betweenCal = Calendar.getInstance();
        betweenCal.setTimeInMillis(l + 43200000L);
        int betweenWeek = betweenCal.get(7) - 1;
        paiban = getPaiBan(paibanAll, new Date(l));
        week = paiban[6].toCharArray();
        if (week[betweenWeek] == '1') {
          paibanLong = getPaiBan(new Date(l), paiban);
          long between = getBetweenHour(paibanLong);
          betweenLong += between;
          if (between != 0L) {
            dateStr = String.valueOf(dateStr) + this.riqiFormat.format(new Date(l)) + ",";
            longStr = String.valueOf(longStr) + ((float)between / (float)getBetweenHour(paibanLong) * Float.valueOf(paiban[7]).floatValue()) + ",";
          } 
        } 
      } 
      paiban = getPaiBan(paibanAll, endDate);
      week = paiban[6].toCharArray();
      if (week[endWeek] == '1')
        paibanLong = getPaiBan(endDate, paiban); 
      long lastLong = getEndHour(paibanLong, endDate);
      if (lastLong != 0L) {
        dateStr = String.valueOf(dateStr) + this.riqiFormat.format(endDate) + ",";
        longStr = String.valueOf(longStr) + ((float)lastLong / (float)getBetweenHour(paibanLong) * Float.valueOf(paiban[7]).floatValue()) + ",";
      } 
      returnValue = firstLong + betweenLong + lastLong;
    } 
    if (dateStr.length() > 1)
      setDateStrs(dateStr.substring(1, dateStr.length() - 1).split(",")); 
    if (longStr.length() > 1)
      setDateHour(longStr.substring(1, longStr.length() - 1).split(",")); 
    return returnValue;
  }
  
  private long[] getPaiBan(Date curDate, String[] paiban) {
    String riqi = this.riqiFormat.format(curDate);
    long[] paibanLong = new long[6];
    try {
      paibanLong[0] = paiban[0].equals("0") ? curDate.getTime() : this.format.parse(String.valueOf(riqi) + " " + paiban[0] + ":00").getTime();
      paibanLong[1] = paiban[1].equals("0") ? paibanLong[0] : this.format.parse(String.valueOf(riqi) + " " + paiban[1] + ":00").getTime();
      paibanLong[2] = paiban[2].equals("0") ? paibanLong[1] : this.format.parse(String.valueOf(riqi) + " " + paiban[2] + ":00").getTime();
      paibanLong[3] = paiban[3].equals("0") ? paibanLong[2] : this.format.parse(String.valueOf(riqi) + " " + paiban[3] + ":00").getTime();
      paibanLong[4] = paiban[4].equals("0") ? paibanLong[3] : this.format.parse(String.valueOf(riqi) + " " + paiban[4] + ":00").getTime();
      paibanLong[5] = paiban[5].equals("0") ? paibanLong[4] : this.format.parse(String.valueOf(riqi) + " " + paiban[5] + ":00").getTime();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return paibanLong;
  }
  
  private long getBeginHour(long[] paibanLong, Date beginDate) {
    long timeLong = 0L;
    long beginLong = beginDate.getTime();
    if (paibanLong[0] != 0L && paibanLong[0] >= beginLong) {
      timeLong = paibanLong[1] - paibanLong[0] + paibanLong[3] - paibanLong[2] + paibanLong[5] - paibanLong[4];
    } else if (paibanLong[0] != 0L && paibanLong[1] != 0L && 
      beginLong > paibanLong[0] && beginLong <= paibanLong[1]) {
      timeLong = paibanLong[1] - beginLong + paibanLong[3] - paibanLong[2] + paibanLong[5] - paibanLong[4];
    } else if (paibanLong[1] != 0L && paibanLong[2] != 0L && 
      beginLong > paibanLong[1] && beginLong <= paibanLong[2]) {
      timeLong = paibanLong[3] - paibanLong[2] + paibanLong[5] - paibanLong[4];
    } else if (paibanLong[2] != 0L && paibanLong[3] != 0L && 
      beginLong > paibanLong[2] && beginLong <= paibanLong[3]) {
      timeLong = paibanLong[3] - beginLong + paibanLong[5] - paibanLong[4];
    } else if (paibanLong[3] != 0L && paibanLong[4] != 0L && 
      beginLong > paibanLong[3] && beginLong <= paibanLong[4]) {
      timeLong = paibanLong[5] - paibanLong[4];
    } else if (paibanLong[4] != 0L && paibanLong[5] != 0L && 
      beginLong > paibanLong[4] && beginLong <= paibanLong[5]) {
      timeLong = paibanLong[5] - beginLong;
    } else {
      timeLong = 0L;
    } 
    return timeLong;
  }
  
  private long getBetweenHour(long[] paibanLong) {
    long timeLong = paibanLong[1] - paibanLong[0] + paibanLong[3] - paibanLong[2] + paibanLong[5] - paibanLong[4];
    return timeLong;
  }
  
  private long getEndHour(long[] paibanLong, Date endDate) {
    long timeLong = 0L;
    long endLong = endDate.getTime();
    if (paibanLong[0] != 0L && paibanLong[0] >= endLong) {
      timeLong = 0L;
    } else if (paibanLong[0] != 0L && paibanLong[1] != 0L && 
      paibanLong[0] < endLong && paibanLong[1] >= endLong) {
      timeLong = endLong - paibanLong[0];
    } else if (paibanLong[1] != 0L && paibanLong[2] != 0L && 
      paibanLong[1] < endLong && paibanLong[2] >= endLong) {
      timeLong = paibanLong[1] - paibanLong[0];
    } else if (paibanLong[2] != 0L && paibanLong[3] != 0L && 
      paibanLong[2] < endLong && paibanLong[3] >= endLong) {
      timeLong = paibanLong[1] - paibanLong[0] + endLong - paibanLong[2];
    } else if (paibanLong[3] != 0L && paibanLong[4] != 0L && 
      paibanLong[3] < endLong && paibanLong[4] >= endLong) {
      timeLong = paibanLong[1] - paibanLong[0] + paibanLong[3] - paibanLong[2];
    } else if (paibanLong[4] != 0L && paibanLong[5] != 0L && 
      paibanLong[4] < endLong && paibanLong[5] >= endLong) {
      timeLong = paibanLong[1] - paibanLong[0] + paibanLong[3] - paibanLong[2] + endLong - paibanLong[4];
    } else {
      timeLong = paibanLong[1] - paibanLong[0] + paibanLong[3] - paibanLong[2] + paibanLong[5] - paibanLong[4];
    } 
    return timeLong;
  }
  
  public String[] getPaiBan(String[][] paibanAll, String riqi) {
    try {
      return getPaiBan(paibanAll, this.riqiFormat.parse(riqi));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public String[] getPaiBan(String[][] paibanAll, Date riqi) throws Exception {
    String[] returnPaibai = new String[(paibanAll[0]).length];
    Calendar riqiCal = Calendar.getInstance();
    riqiCal.setTimeInMillis(riqi.getTime());
    int riqiWeek = riqiCal.get(7) - 1;
    String riqiStr = this.riqiFormat.format(riqi);
    for (int j = 0; j < (paibanAll[0]).length; ) {
      returnPaibai[j] = paibanAll[0][j];
      j++;
    } 
    for (int i = 0; i < paibanAll.length; i++) {
      String[] paibai = paibanAll[i];
      char[] week = paibai[6].toCharArray();
      if (week[riqiWeek] == '1') {
        for (int k = 0; k < paibai.length; ) {
          returnPaibai[k] = paibai[k];
          k++;
        } 
        break;
      } 
    } 
    String[] holidayInfo = getHoliday(riqiStr);
    if (!"2".equals(holidayInfo[2])) {
      Date holidayBegin = this.format.parse(holidayInfo[0]);
      Date holidayEnd = this.format.parse(holidayInfo[1]);
      if (!"".equals(holidayInfo[3])) {
        String[] holidayPaiban = holidayPaiBan(holidayInfo[3]);
        if (holidayPaiban != null)
          for (int k = 0; k < returnPaibai.length; k++) {
            if (k != 6)
              returnPaibai[k] = holidayPaiban[k]; 
          }  
      } 
      long[] paibanlong = getPaiBan(this.riqiFormat.parse(riqiStr), returnPaibai);
      char[] week = returnPaibai[6].toCharArray();
      if (holidayBegin.getTime() <= paibanlong[0] && paibanlong[5] <= holidayEnd.getTime()) {
        if ("0".equals(holidayInfo[2])) {
          week[riqiWeek] = '0';
        } else if ("1".equals(holidayInfo[2])) {
          week[riqiWeek] = '1';
        } 
        returnPaibai[6] = new String(week);
      } else if (!"".equals(holidayInfo[3])) {
        week[riqiWeek] = '1';
      } else {
        Date rqBeginDate = this.format.parse(String.valueOf(riqiStr) + " 00:00:00");
        Date rqEndDate = this.format.parse(String.valueOf(riqiStr) + " 23:59:00");
        long holidayBeginLong = holidayBegin.getTime();
        long holidayEndLong = holidayEnd.getTime();
        long[] holidayLong = { rqBeginDate.getTime(), paibanlong[0], paibanlong[1], paibanlong[2], paibanlong[3], 
            paibanlong[4], paibanlong[5], rqEndDate.getTime() };
        int beginIndex = -1;
        int endIndex = -1;
        int k;
        for (k = 0; k < holidayLong.length - 1; k++) {
          if (holidayBeginLong >= holidayLong[k] && holidayBeginLong <= holidayLong[k + 1] && beginIndex == -1)
            beginIndex = k; 
          if (holidayEndLong >= holidayLong[k] && holidayEndLong <= holidayLong[k + 1] && endIndex == -1)
            endIndex = k; 
          if (beginIndex != -1 && endIndex != -1)
            break; 
        } 
        if ("1".equals(holidayInfo[2])) {
          if (beginIndex == endIndex && (beginIndex == 0 || endIndex == 6)) {
            week[riqiWeek] = '0';
          } else {
            if (beginIndex % 2 != 0) {
              returnPaibai[beginIndex - 1] = this.timeFormat.format(holidayBegin);
              beginIndex--;
            } 
            if (endIndex % 2 != 0) {
              returnPaibai[endIndex] = this.timeFormat.format(holidayEnd);
            } else {
              endIndex--;
            } 
            for (k = endIndex + 1; k < 6; k++)
              returnPaibai[k] = "0"; 
            week[riqiWeek] = '1';
          } 
        } else if (beginIndex == endIndex) {
          week[riqiWeek] = '1';
        } else {
          for (k = 0; k <= 6; k++) {
            if (beginIndex == k) {
              for (int m = k + 1; m <= 6; m++) {
                if (m == endIndex)
                  if (k % 2 == 0 && m % 2 == 0) {
                    for (int x = k + 1; x <= m; x++)
                      returnPaibai[x - 1] = "0"; 
                  } else if (k % 2 == 1 && m % 2 == 1) {
                    returnPaibai[k] = this.timeFormat.format(holidayBegin);
                    returnPaibai[m - 1] = this.timeFormat.format(holidayEnd);
                    for (int x = k + 2; x < m; x++)
                      returnPaibai[x - 1] = "0"; 
                  } else if (k % 2 == 0 && m % 2 == 1) {
                    returnPaibai[m - 1] = this.timeFormat.format(holidayEnd);
                    for (int x = k + 1; x < m; x++)
                      returnPaibai[x - 1] = "0"; 
                  } else if (k % 2 == 1 && m % 2 == 0) {
                    returnPaibai[k] = this.timeFormat.format(holidayBegin);
                    for (int x = k + 2; x <= m; x++)
                      returnPaibai[x - 1] = "0"; 
                  }  
              } 
              break;
            } 
          } 
          week[riqiWeek] = '1';
        } 
        if ("0".equals(returnPaibai[0]) && "0".equals(returnPaibai[1]) && "0".equals(returnPaibai[2]) && 
          "0".equals(returnPaibai[3]) && "0".equals(returnPaibai[4]) && "0".equals(returnPaibai[5])) {
          week[riqiWeek] = '0';
        } else {
          String[] paibanTemp = { "0", "0", "0", "0", "0", "0" };
          int m;
          for (m = 0; m < 6; m++) {
            if (!"0".equals(returnPaibai[m]))
              for (int n = 0; n < paibanTemp.length; n++) {
                if ("0".equals(paibanTemp[n])) {
                  paibanTemp[n] = returnPaibai[m];
                  break;
                } 
              }  
          } 
          for (m = 0; m < paibanTemp.length; m++)
            returnPaibai[m] = paibanTemp[m]; 
        } 
        returnPaibai[6] = new String(week);
      } 
    } 
    return returnPaibai;
  }
  
  public String[] holidayPaiBan(String paibanId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String[] holidayPaiban = (String[])null;
    try {
      base.begin();
      String sql = "SELECT duty_time1,duty_time2,duty_time3,duty_time4,duty_time5,duty_time6,workday,dutyhour,ifPunch,minNum,minNumDown FROM kq_duty_set where id=" + 
        paibanId;
      rs = base.executeQuery(sql);
      if (rs.next())
        holidayPaiban = new String[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
            rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10) }; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return holidayPaiban;
  }
  
  public String[] getHoliday(String riqi) {
    String begin = String.valueOf(riqi) + " 00:00:00";
    String end = String.valueOf(riqi) + " 23:59:59";
    String[] flag = { begin, end, "2", "" };
    String holidaySql = "";
    if (this.databaseType.indexOf("mysql") >= 0)
      holidaySql = "SELECT begin_date,end_date,TYPE,paibanSet FROM kq_holiday WHERE (begin_date LIKE '%" + riqi + "%' OR end_date LIKE '%" + riqi + "%') " + 
        "OR ('" + riqi + "' BETWEEN begin_date AND end_date) ORDER BY begin_date"; 
    if (this.databaseType.indexOf("oracle") >= 0)
      holidaySql = "SELECT begin_date,end_date,TYPE,paibanSet FROM kq_holiday WHERE (( to_char(begin_date,'yyyy-mm-dd hh:mm:ss') LIKE '%" + riqi + "%' " + 
        "OR to_char(end_date,'yyyy-mm-dd hh:mm:ss') LIKE '%" + riqi + "%') " + 
        "OR ('" + riqi + "' BETWEEN to_char(begin_date,'yyyy-mm-dd hh:mm:ss') AND to_char(end_date,'yyyy-mm-dd hh:mm:ss')))"; 
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(holidaySql);
      if (rs.next()) {
        flag[0] = rs.getString(1);
        flag[1] = rs.getString(2);
        flag[2] = rs.getString(3);
        flag[3] = rs.getString(4);
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return flag;
  }
  
  public String[] getDateStrs() {
    return this.dateStrs;
  }
  
  public void setDateStrs(String[] dateStrs) {
    this.dateStrs = dateStrs;
  }
  
  public String[] getDateHour() {
    return this.dateHour;
  }
  
  public void setDateHour(String[] dateHour) {
    this.dateHour = dateHour;
  }
  
  public static void main(String[] args) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    String[] paiban = { "8:30", "12:00", "13:00", "17:30", "0", "0", "0111110", "1.0" };
    String[] returnPaibai = { "8:30", "12:00", "13:00", "17:30", "0", "0", "0111110", "1.0" };
    String flag = "1";
    try {
      Date holidayBegin = format.parse("2013-10-10 7:30:00");
      Date holidayEnd = format.parse("2013-10-10 18:30:00");
      Date rqBeginDate = format.parse("2013-10-10 00:00:00");
      Date rqEndDate = format.parse("2013-10-10 23:59:00");
      long[] paibanlong = (new PaiBanUtil()).getPaiBan(rqBeginDate, paiban);
      long holidayBeginLong = holidayBegin.getTime();
      long holidayEndLong = holidayEnd.getTime();
      long[] holidayLong = { rqBeginDate.getTime(), paibanlong[0], paibanlong[1], paibanlong[2], paibanlong[3], 
          paibanlong[4], paibanlong[5], rqEndDate.getTime() };
      int beginIndex = -1;
      int endIndex = -1;
      for (int i = 0; i < holidayLong.length - 1; i++) {
        if (holidayBeginLong >= holidayLong[i] && holidayBeginLong <= holidayLong[i + 1] && beginIndex == -1)
          beginIndex = i; 
        if (holidayEndLong >= holidayLong[i] && holidayEndLong <= holidayLong[i + 1] && endIndex == -1)
          endIndex = i; 
        if (beginIndex != -1 && endIndex != -1)
          break; 
      } 
      char[] week = returnPaibai[6].toCharArray();
      Calendar riqiCal = Calendar.getInstance();
      riqiCal.setTimeInMillis(rqBeginDate.getTime());
      int riqiWeek = riqiCal.get(7) - 1;
      if ("1".equals(flag)) {
        if (beginIndex == endIndex && (beginIndex == 0 || endIndex == 6)) {
          week[riqiWeek] = '0';
        } else {
          if (beginIndex % 2 != 0) {
            returnPaibai[beginIndex - 1] = timeFormat.format(holidayBegin);
            beginIndex--;
          } 
          if (endIndex % 2 != 0) {
            returnPaibai[endIndex] = timeFormat.format(holidayEnd);
          } else {
            endIndex--;
          } 
          for (int k = endIndex + 1; k < returnPaibai.length - 2; k++)
            returnPaibai[k] = "0"; 
        } 
      } else if (beginIndex == endIndex) {
        week[riqiWeek] = '1';
      } else {
        for (int k = 0; k <= 6; k++) {
          if (beginIndex == k) {
            for (int m = k + 1; m <= 6; m++) {
              if (m == endIndex)
                if (k % 2 == 0 && m % 2 == 0) {
                  for (int x = k + 1; x <= m; x++)
                    returnPaibai[x - 1] = "0"; 
                } else if (k % 2 == 1 && m % 2 == 1) {
                  returnPaibai[k] = timeFormat.format(holidayBegin);
                  returnPaibai[m - 1] = timeFormat.format(holidayEnd);
                  for (int x = k + 2; x < m; x++)
                    returnPaibai[x - 1] = "0"; 
                } else if (k % 2 == 0 && m % 2 == 1) {
                  returnPaibai[m - 1] = timeFormat.format(holidayEnd);
                  for (int x = k + 1; x < m; x++)
                    returnPaibai[x - 1] = "0"; 
                } else if (k % 2 == 1 && m % 2 == 0) {
                  returnPaibai[k] = timeFormat.format(holidayBegin);
                  for (int x = k + 2; x <= m; x++)
                    returnPaibai[x - 1] = "0"; 
                }  
            } 
            break;
          } 
        } 
      } 
      int j;
      for (j = 0; j < returnPaibai.length; j++)
        System.out.println(returnPaibai[j]); 
      System.out.println();
      if ("0".equals(returnPaibai[0]) && "0".equals(returnPaibai[1]) && "0".equals(returnPaibai[2]) && 
        "0".equals(returnPaibai[3]) && "0".equals(returnPaibai[4]) && "0".equals(returnPaibai[5])) {
        week[riqiWeek] = '0';
      } else {
        String[] paibanTemp = { "0", "0", "0", "0", "0", "0" };
        int k;
        for (k = 0; k < returnPaibai.length - 2; k++) {
          if (!"0".equals(returnPaibai[k]))
            for (int m = 0; m < paibanTemp.length; m++) {
              if ("0".equals(paibanTemp[m])) {
                paibanTemp[m] = returnPaibai[k];
                break;
              } 
            }  
        } 
        for (k = 0; k < paibanTemp.length; k++)
          returnPaibai[k] = paibanTemp[k]; 
      } 
      returnPaibai[6] = new String(week);
      for (j = 0; j < returnPaibai.length; j++)
        System.out.println(returnPaibai[j]); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    System.exit(0);
  }
}
