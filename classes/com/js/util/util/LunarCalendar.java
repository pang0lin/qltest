package com.js.util.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LunarCalendar {
  private int lunarYear;
  
  private int lunarMonth;
  
  private int lunarDay;
  
  private boolean lunarLeap;
  
  private static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
  
  private static int[] lunarInfo = new int[] { 
      19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 
      19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 
      18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 
      25958, 54432, 59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 
      54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 
      27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728, 27296, 
      44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 
      38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 103846, 
      38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 
      19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 
      51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 
      43344, 46240, 47780, 44368, 21977, 19360, 42416, 86390, 21168, 43312, 
      31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 
      23200, 30371, 38608, 19415, 19152, 42192, 118966, 53840, 54560, 56645, 
      46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936, 44448, 
      84835 };
  
  public int getYearDays(int year) {
    int sum = 348;
    for (int i = 32768; i > 8; ) {
      sum += ((lunarInfo[year - 1900] & i) > 0) ? 1 : 0;
      i >>= 1;
    } 
    return sum + getLeapDays(year);
  }
  
  public int getLeapDays(int year) {
    if (getLeapMonth(year) > 0)
      return ((lunarInfo[year - 1900] & 0x10000) > 0) ? 30 : 29; 
    return 0;
  }
  
  public int getLeapMonth(int year) {
    return lunarInfo[year - 1900] & 0xF;
  }
  
  public int getMonthDays(int year, int month) {
    return ((lunarInfo[year - 1900] & 65536 >> month) > 0) ? 30 : 29;
  }
  
  public void setLunar(Calendar date) {
    int temp = 0;
    Date baseDate = null;
    try {
      baseDate = chineseDateFormat.parse("1900年1月31日");
    } catch (Exception exception) {}
    int offset = (int)((date.getTime().getTime() - baseDate.getTime()) / 
      86400000L);
    int dayCyl = offset + 40;
    int monCyl = 14;
    int i;
    for (i = 1900; i < 2050 && offset > 0; i++) {
      temp = getYearDays(i);
      offset -= temp;
      monCyl += 12;
    } 
    if (offset < 0) {
      offset += temp;
      i--;
      monCyl -= 12;
    } 
    int year = i;
    int yearCyl = i - 1864;
    int leap = getLeapMonth(i);
    boolean isLeap = false;
    for (i = 1; i < 13 && offset > 0; i++) {
      if (leap > 0 && i == leap + 1 && !isLeap) {
        i--;
        isLeap = true;
        temp = getLeapDays(year);
      } else {
        temp = getMonthDays(year, i);
      } 
      if (isLeap && i == leap + 1)
        isLeap = false; 
      offset -= temp;
      if (!isLeap)
        monCyl++; 
    } 
    if (offset == 0 && leap > 0 && i == leap + 1)
      if (isLeap) {
        isLeap = false;
      } else {
        isLeap = true;
        i--;
        monCyl--;
      }  
    if (offset < 0) {
      offset += temp;
      i--;
      monCyl--;
    } 
    int month = i;
    int day = offset + 1;
    this.lunarYear = year;
    this.lunarMonth = month;
    this.lunarDay = day;
    this.lunarLeap = isLeap;
  }
  
  public int getScan(int minYear, int minMonth, int minDay, boolean isLeap, int maxYear, int maxMonth, int maxDay) {
    int offset = 0;
    int yearScan = maxYear - minYear;
    if (yearScan > 0) {
      int i;
      for (i = minMonth; i <= 12; i++)
        offset += getMonthDays(minYear, i); 
      offset -= minDay;
      if (!isLeap && getLeapMonth(minYear) >= minMonth)
        offset += getLeapDays(minYear); 
      for (i = 1; i < yearScan; i++)
        offset += getYearDays(minYear + i); 
      for (i = 1; i < maxMonth; i++)
        offset += getMonthDays(maxYear, i); 
      if (getLeapMonth(maxYear) < maxMonth)
        offset += getLeapDays(maxYear); 
      offset += maxDay;
    } else {
      for (int i = minMonth; i < maxMonth; i++)
        offset += getMonthDays(minYear, i); 
      int leap = getLeapMonth(minYear);
      if (!isLeap && leap >= minMonth && leap < maxMonth)
        offset += getLeapDays(maxYear); 
      offset -= minDay;
      offset += maxDay;
    } 
    return offset;
  }
  
  public int getLunarYear() {
    return this.lunarYear;
  }
  
  public int getLunarMonth() {
    return this.lunarMonth;
  }
  
  public int getLunarDay() {
    return this.lunarDay;
  }
  
  public boolean getLunarLeap() {
    return this.lunarLeap;
  }
}
