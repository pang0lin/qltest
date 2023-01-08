package com.js.oa.chinaLife.util;

import java.text.SimpleDateFormat;

public class ChinalifePaiban {
  public Long getQjTimeLong(Long[] pbData, Long startTime, Long endTime) {
    int l = pbData.length;
    if (startTime.longValue() <= pbData[0].longValue())
      startTime = pbData[0]; 
    if (endTime.longValue() >= pbData[l - 1].longValue())
      endTime = pbData[l - 1]; 
    if (startTime.longValue() >= pbData[l - 1].longValue() || endTime.longValue() <= pbData[0].longValue())
      return Long.valueOf(0L); 
    int index = -1;
    int i;
    for (i = 0; i < l - 1; i++) {
      if (startTime.longValue() >= pbData[i].longValue() && startTime.longValue() <= pbData[i + 1].longValue() && 
        endTime.longValue() >= pbData[i].longValue() && endTime.longValue() <= pbData[i + 1].longValue()) {
        index = i;
        break;
      } 
    } 
    if (index >= 0 && index % 2 == 0)
      return Long.valueOf(endTime.longValue() - startTime.longValue()); 
    if (index >= 0)
      return Long.valueOf(0L); 
    for (i = 0; i < l - 1; i++) {
      if (startTime.longValue() >= pbData[i].longValue() && startTime.longValue() <= pbData[i + 1].longValue())
        if (i % 2 == 0) {
          for (int j = i + 1; j < l - 1; j++) {
            if (endTime.longValue() >= pbData[j].longValue() && endTime.longValue() <= pbData[j + 1].longValue()) {
              if (j % 2 == 1) {
                if (j == i + 1)
                  return Long.valueOf(pbData[i + 1].longValue() - startTime.longValue()); 
                Long long_ = Long.valueOf(pbData[i + 1].longValue() - startTime.longValue());
                for (int k = i + 2; k <= j; k += 2)
                  long_ = Long.valueOf(long_.longValue() + pbData[k + 1].longValue() - pbData[k].longValue()); 
                return long_;
              } 
              if (j - i == 2)
                return Long.valueOf(pbData[i + 1].longValue() - startTime.longValue() + endTime.longValue() - pbData[j].longValue()); 
              Long rv = Long.valueOf(pbData[i + 1].longValue() - startTime.longValue() + endTime.longValue() - pbData[j].longValue());
              for (int t = i + 2; t < j; t += 2)
                rv = Long.valueOf(rv.longValue() + pbData[t + 1].longValue() - pbData[t].longValue()); 
              return rv;
            } 
          } 
        } else {
          for (int j = i + 1; j < l - 1; j++) {
            if (endTime.longValue() >= pbData[j].longValue() && endTime.longValue() <= pbData[j + 1].longValue()) {
              if (j % 2 == 0) {
                if (j - i == 1)
                  return Long.valueOf(endTime.longValue() - pbData[j].longValue()); 
                Long long_ = Long.valueOf(endTime.longValue() - pbData[j].longValue());
                for (int k = i + 1; k < j; k += 2)
                  long_ = Long.valueOf(long_.longValue() + pbData[k + 1].longValue() - pbData[k].longValue()); 
                return long_;
              } 
              Long rv = Long.valueOf(0L);
              for (int t = i + 1; t < j; t += 2)
                rv = Long.valueOf(rv.longValue() + pbData[t + 1].longValue() - pbData[t].longValue()); 
              return rv;
            } 
          } 
        }  
    } 
    return Long.valueOf(0L);
  }
  
  public Long[] getPbData(Long[] pbDataOld, Long startTime, Long endTime) {
    int l = pbDataOld.length;
    if (startTime.longValue() <= pbDataOld[0].longValue())
      startTime = pbDataOld[0]; 
    if (endTime.longValue() >= pbDataOld[l - 1].longValue())
      endTime = pbDataOld[l - 1]; 
    if (startTime.longValue() >= pbDataOld[l - 1].longValue() || endTime.longValue() <= pbDataOld[0].longValue())
      return pbDataOld; 
    int index = -1;
    int i;
    for (i = 0; i < l - 1; i++) {
      if (startTime.longValue() >= pbDataOld[i].longValue() && startTime.longValue() <= pbDataOld[i + 1].longValue() && 
        endTime.longValue() >= pbDataOld[i].longValue() && endTime.longValue() <= pbDataOld[i + 1].longValue()) {
        index = i;
        break;
      } 
    } 
    if (index >= 0 && index % 2 == 0) {
      Long[] pbData = new Long[l + 2];
      int j;
      for (j = 0; j <= index; j++)
        pbData[j] = pbDataOld[j]; 
      pbData[index + 1] = startTime;
      pbData[index + 2] = endTime;
      for (j = index + 3; j < l + 2; j++)
        pbData[j] = pbDataOld[j - 2]; 
      return pbData;
    } 
    if (index >= 0)
      return pbDataOld; 
    for (i = 0; i < l - 1; i++) {
      if (startTime.longValue() >= pbDataOld[i].longValue() && startTime.longValue() <= pbDataOld[i + 1].longValue())
        if (i % 2 == 0) {
          for (int j = i + 1; j < l - 1; j++) {
            if (endTime.longValue() >= pbDataOld[j].longValue() && endTime.longValue() <= pbDataOld[j + 1].longValue()) {
              if (j % 2 == 1) {
                if (j == i + 1) {
                  pbDataOld[j] = startTime;
                  return pbDataOld;
                } 
                int k = l - j - i + 1;
                Long[] arrayOfLong = new Long[k];
                int m;
                for (m = 0; i <= i; m++)
                  arrayOfLong[m] = pbDataOld[m]; 
                arrayOfLong[i + 1] = startTime;
                for (m = i + 2; m < k; m++)
                  arrayOfLong[m] = pbDataOld[m + j - i + 1]; 
                return arrayOfLong;
              } 
              if (j - i == 2) {
                pbDataOld[i + 1] = startTime;
                pbDataOld[j] = endTime;
                return pbDataOld;
              } 
              int newL = l - j - i + 2;
              Long[] pbData = new Long[newL];
              int t;
              for (t = 0; i <= i; t++)
                pbData[t] = pbDataOld[t]; 
              pbData[i + 1] = startTime;
              pbData[i + 2] = endTime;
              for (t = i + 3; t < newL; t++)
                pbData[t] = pbDataOld[t + j - i + 2]; 
              return pbData;
            } 
          } 
        } else {
          for (int j = i + 1; j < l - 1; j++) {
            if (endTime.longValue() >= pbDataOld[j].longValue() && endTime.longValue() <= pbDataOld[j + 1].longValue()) {
              if (j % 2 == 0) {
                if (j - i == 1) {
                  pbDataOld[j] = endTime;
                  return pbDataOld;
                } 
                int k = l - j - i + 1;
                Long[] arrayOfLong = new Long[k];
                int m;
                for (m = 0; i <= i; m++)
                  arrayOfLong[m] = pbDataOld[m]; 
                arrayOfLong[i + 1] = endTime;
                for (m = i + 2; i < k; m++)
                  arrayOfLong[m] = pbDataOld[m + j - i + 1]; 
                return arrayOfLong;
              } 
              int newL = l - j - i;
              Long[] pbData = new Long[newL];
              int t;
              for (t = 0; i <= i; t++)
                pbData[t] = pbDataOld[t]; 
              for (t = i + 1; i < newL; t++)
                pbData[t] = pbDataOld[t + j - i]; 
              return pbData;
            } 
          } 
        }  
    } 
    return pbDataOld;
  }
  
  public int getTime(Long[] pbData, Long startTime, Long endTime) {
    Long msLong = Long.valueOf(86400000L);
    System.out.println(String.valueOf(startTime.longValue() / msLong.longValue()) + "     " + (endTime.longValue() / msLong.longValue()));
    if (startTime.longValue() / msLong.longValue() == endTime.longValue() / msLong.longValue()) {
      System.out.println(getQjTimeLong(pbData, startTime, endTime).longValue() / 3600000L);
    } else {
      long startDay = startTime.longValue() / msLong.longValue();
      long endDay = endTime.longValue() / msLong.longValue();
      int dayIndex = 0;
      for (long i = startDay; i <= endDay; i++) {
        for (int j = 0; j < pbData.length; j++)
          pbData[j] = Long.valueOf(pbData[j].longValue() + msLong.longValue() * dayIndex); 
        if (i == startDay) {
          System.out.println("第一天：" + (getQjTimeLong(pbData, startTime, pbData[pbData.length - 1]).longValue() / 3600000L));
        } else if (i == endDay) {
          System.out.println("最后天：" + (getQjTimeLong(pbData, pbData[0], endTime).longValue() / 3600000L));
        } else {
          System.out.println("中间天：" + (getQjTimeLong(pbData, pbData[0], pbData[pbData.length - 1]).longValue() / 3600000L));
        } 
        dayIndex = 1;
      } 
      System.out.println(getQjTimeLong(pbData, startTime, endTime).longValue() / 3600000L);
    } 
    return 0;
  }
  
  public static void main(String[] args) {
    Long[] pbData = new Long[4];
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      pbData[0] = Long.valueOf(ymdhms.parse("2014-08-11 09:00:00").getTime());
      pbData[1] = Long.valueOf(ymdhms.parse("2014-08-11 12:00:00").getTime());
      pbData[2] = Long.valueOf(ymdhms.parse("2014-08-11 13:00:00").getTime());
      pbData[3] = Long.valueOf(ymdhms.parse("2014-08-11 17:00:00").getTime());
      Long startTime = Long.valueOf(ymdhms.parse("2014-08-11 14:00:00").getTime());
      Long endTime = Long.valueOf(ymdhms.parse("2014-08-13 15:00:00").getTime());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
