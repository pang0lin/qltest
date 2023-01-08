package com.js.oa.hr.kq.bry;

import com.js.oa.hr.kq.bry.util.BryGouZao;
import com.js.oa.hr.kq.bry.util.BryUtil;
import com.js.util.util.IO2File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BryJq {
  private Long userId;
  
  private String userName;
  
  private Long orgId;
  
  private String orgName;
  
  private String userNumber;
  
  private Date startDate = null;
  
  private Long startLong;
  
  private Date endDate = null;
  
  private Long endLong;
  
  private float hourLong;
  
  private Integer jqType;
  
  private String jqOtype;
  
  private String jqRemark;
  
  private Integer jqUse;
  
  public BryJq() {}
  
  public BryJq(Long userId, String userName, Long orgId, String orgName, String userNumber, String startStr, String endStr, String jqType, String jqOtype) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    setUserId(userId);
    setUserName(userName);
    setOrgId(orgId);
    setOrgName(orgName);
    setUserNumber(userNumber);
    setStartDate(BryGouZao.getDateYmdhms(startStr));
    setStartLong(Long.valueOf(getStartDate().getTime()));
    setEndDate(BryGouZao.getDateYmdhms(endStr));
    setEndLong(Long.valueOf(getEndDate().getTime()));
    if ("2".equals(jqType)) {
      float sc = 0.0F;
      if ("普通加班".equals(jqOtype)) {
        long sbAMLong = format.parse(String.valueOf(startStr.split(" ")[0]) + " 08:30:00").getTime();
        long xbPMLong = format.parse(String.valueOf(startStr.split(" ")[0]) + " 17:45:00").getTime();
        if (getEndLong().longValue() > getStartLong().longValue()) {
          long stopLong = format.parse(String.valueOf(startStr.split(" ")[0]) + " 23:45:00").getTime();
          long endLong = getEndLong().longValue();
          if (endLong > stopLong)
            endLong = stopLong; 
          if (getStartLong().longValue() < sbAMLong) {
            if (endLong < sbAMLong) {
              sc = (float)(endLong - getStartLong().longValue()) / 3600000.0F;
            } else if (endLong >= sbAMLong && endLong <= xbPMLong) {
              sc = (float)(sbAMLong - getStartLong().longValue()) / 3600000.0F;
            } else if (endLong > xbPMLong) {
              sc = (float)(endLong - getStartLong().longValue() - xbPMLong - sbAMLong) / 3600000.0F;
            } 
          } else if (getStartLong().longValue() >= sbAMLong && getStartLong().longValue() <= xbPMLong) {
            if (endLong > xbPMLong)
              sc = (float)(endLong - xbPMLong) / 3600000.0F; 
          } else if (getStartLong().longValue() > xbPMLong) {
            sc = (float)(endLong - getStartLong().longValue()) / 3600000.0F;
          } 
        } 
      } else {
        try {
          BryUtil util = new BryUtil();
          String[] bStrings = format.format(getStartDate()).split(" ");
          String[] eStrings = format.format(getEndDate()).split(" ");
          if (util.getSInt(bStrings[1]) < util.getSInt(eStrings[1])) {
            Date[] kqData = new Date[4];
            kqData[0] = format.parse(String.valueOf(bStrings[0]) + " 00:00:00");
            kqData[1] = format.parse(String.valueOf(bStrings[0]) + " 11:45:00");
            kqData[2] = format.parse(String.valueOf(bStrings[0]) + " 12:30:00");
            kqData[3] = format.parse(String.valueOf(bStrings[0]) + " 23:59:59");
            sc = util.getDatelong(kqData, getStartDate(), getEndDate());
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
      sc *= 2.0F;
      sc = (float)Math.floor(sc);
      sc /= 2.0F;
      setHourLong(Float.valueOf(String.format("%.2f", new Object[] { Float.valueOf(sc) })).floatValue());
    } else if ("4".equals(jqType)) {
      String[] chuchai = BryGouZao.chuchaiAndTiaoxiu(startStr, endStr, (String)userId);
      setHourLong(Float.valueOf(chuchai[0]).floatValue());
      setJqRemark(chuchai[1]);
    } else if (!"1".equals(jqType)) {
      BryUtil util = new BryUtil();
      float sc = util.jisuanShiChang(startStr, endStr, (String)userId);
      if ("事假".equals(jqOtype) || "病假".equals(jqOtype)) {
        sc = util.getHour(sc, 0.5F);
      } else if ("年假".equals(jqOtype) || "调休".equals(jqOtype)) {
        sc = util.getHour(sc, 4.0F, 5.0F);
      } else if ("产假".equals(jqOtype) || "婚假".equals(jqOtype)) {
        sc = util.getHour(sc, 8.0F);
      } else if ("哺乳假".equals(jqOtype)) {
        sc = util.getHour(sc, 1.0F);
      } else if ("产检".equals(jqOtype)) {
        sc = util.getHour(sc, 4.0F);
      } else if ("其他".equals(jqOtype) || "丧假".equals(jqOtype)) {
        String sd = startStr.split(" ")[0];
        String ed = endStr.split(" ")[0];
        int sm = util.getSInt(startStr.split(" ")[1]);
        int em = util.getSInt(endStr.split(" ")[1]);
        if (sm > 30600 && sm < 42300 && em > 45000 && em < 62100) {
          if (em - sm - 2700 < 14400)
            sc += 4.0F; 
        } else if (em > 30600 && em < 42300 && sm > 45000 && sm < 62100) {
          if (sm - em - 2700 > 14400)
            sc += 4.0F; 
        } else if (sm > 30600 && sm < 42300 && em > 30600 && em < 42300) {
          if (!sd.equals(ed)) {
            int tm = 42300 - sm + em - 27000;
            if (tm < 14400)
              sc += 4.0F; 
          } 
        } else if (sm > 45000 && sm < 62100 && em > 45000 && em < 62100 && 
          !sd.equals(ed)) {
          int tm = 62100 - sm + em - 45000;
          if (tm < 14400)
            sc += 4.0F; 
        } 
        sc = util.getHour(sc, 4.0F);
      } else {
        sc = util.getHour(sc, 1.0F);
      } 
      setHourLong(Float.valueOf(String.format("%.2f", new Object[] { Float.valueOf(sc) })).floatValue());
    } 
    setJqOtype(jqOtype.equals("") ? "0" : jqOtype);
    setJqType(Integer.valueOf(jqType));
    setJqUse(Integer.valueOf(1));
    IO2File.printFile("用户名：" + userName + "   类型：" + jqOtype + "   开始时间：" + format.format(getStartDate()) + 
        "  结束时间：" + format.format(getEndDate()) + "  时长：" + getHourLong(), "宝日医考勤", 3);
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getUserNumber() {
    return this.userNumber;
  }
  
  public void setUserNumber(String userNumber) {
    this.userNumber = userNumber;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Long getStartLong() {
    return this.startLong;
  }
  
  public void setStartLong(Long startLong) {
    this.startLong = startLong;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Long getEndLong() {
    return this.endLong;
  }
  
  public void setEndLong(Long endLong) {
    this.endLong = endLong;
  }
  
  public float getHourLong() {
    return this.hourLong;
  }
  
  public void setHourLong(float hourLong) {
    this.hourLong = hourLong;
  }
  
  public Integer getJqType() {
    return this.jqType;
  }
  
  public void setJqType(Integer jqType) {
    this.jqType = jqType;
  }
  
  public String getJqRemark() {
    return this.jqRemark;
  }
  
  public void setJqRemark(String jqRemark) {
    this.jqRemark = jqRemark;
  }
  
  public Integer getJqUse() {
    return this.jqUse;
  }
  
  public void setJqUse(Integer jqUse) {
    this.jqUse = jqUse;
  }
  
  public String getJqOtype() {
    return this.jqOtype;
  }
  
  public void setJqOtype(String jqOtype) {
    this.jqOtype = jqOtype;
  }
}
