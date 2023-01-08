package com.js.oa.scheme.event.service;

import com.js.oa.scheme.event.bean.EventEJBBean;
import com.js.oa.scheme.event.bean.EventEJBHome;
import com.js.oa.scheme.event.vo.EventVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.hibernate.HibernateException;

public class EventBD {
  private Integer volume = new Integer(15);
  
  private Integer recordCount;
  
  public boolean addEvent(EventVO event) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(event, EventVO.class);
      result = ((Boolean)ejbProxy.invoke("addEvent", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add event Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteEvent(Long userId, Long eventId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(eventId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteEvent", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete event Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchEvent(Long userId, String eventIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(eventIds, "String");
      result = ((Boolean)ejbProxy.invoke("deleteBatchEvent", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete event Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteAllEvent(Long userId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteAllEvent", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete event Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyEvent(EventVO event) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(event, EventVO.class);
      result = ((Boolean)ejbProxy.invoke("modifyEvent", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify event Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public EventVO selectSingleEvent(Long eventId, Long userId, Long domainId) {
    EventVO event = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(eventId, "Long");
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      event = (EventVO)ejbProxy.invoke("selectSingleEvent", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Single event Exception: " + 
          ex.getMessage());
    } 
    return event;
  }
  
  public List selectAllEvent(Long userId, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectAllEvent", pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getEventRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    setRecordCount(ret);
    return result;
  }
  
  public List selectAllEvent(Long userId, Integer currentPage, Long domainId, String toUserId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(toUserId, "String");
      result = (List)ejbProxy.invoke("selectAllEvent", pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      pg.put(toUserId, "String");
      ret = (Integer)ejbProxy.invoke("getEventRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    setRecordCount(ret);
    return result;
  }
  
  public List searchEvent(Long userId, Integer currentPage, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, int flag) {
    List result = new ArrayList();
    try {
      result = (new EventEJBBean()).searchEvent(userId, currentPage, this.volume, content, 
          domainId, startTime, endTime, startDate, endDate, eventType, eventTitle, flag);
      setRecordCount((new EventEJBBean()).getSearchEventRecordCount(userId, content, domainId, 
            startTime, endTime, startDate, endDate, eventType, eventTitle, null, flag));
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public List searchEvent(Long userId, Integer currentPage, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(11);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(content, "String");
      pg.put(domainId, "Long");
      pg.put(startTime, "Long");
      pg.put(endTime, "Long");
      pg.put(startDate, "String");
      pg.put(endDate, "String");
      pg.put(eventType, "String");
      pg.put(eventTitle, "String");
      result = (List)ejbProxy.invoke("searchEvent", pg.getParameters());
      pg = new ParameterGenerator(9);
      pg.put(userId, "Long");
      pg.put(content, "String");
      pg.put(domainId, "Long");
      pg.put(startTime, "Long");
      pg.put(endTime, "Long");
      pg.put(startDate, "String");
      pg.put(endDate, "String");
      pg.put(eventType, "String");
      pg.put(eventTitle, "String");
      ret = (Integer)ejbProxy.invoke("getSearchEventRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    setRecordCount(ret);
    return result;
  }
  
  public List searchEvent(Long userId, Integer currentPage, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String toUserId, int flag) throws HibernateException {
    List result = (new EventEJBBean()).searchEvent(userId, currentPage, this.volume, content, domainId, 
        startTime, endTime, startDate, endDate, eventType, eventTitle, toUserId, flag);
    setRecordCount((new EventEJBBean()).getSearchEventRecordCount(userId, content, domainId, startTime, endTime, 
          startDate, endDate, eventType, eventTitle, toUserId, flag));
    return result;
  }
  
  public List searchEvent(Long userId, Integer currentPage, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String toUserId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(12);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(content, "String");
      pg.put(domainId, "Long");
      pg.put(startTime, "Long");
      pg.put(endTime, "Long");
      pg.put(startDate, "String");
      pg.put(endDate, "String");
      pg.put(eventType, "String");
      pg.put(eventTitle, "String");
      pg.put(toUserId, "String");
      result = (List)ejbProxy.invoke("searchEvent", pg.getParameters());
      pg = new ParameterGenerator(10);
      pg.put(userId, "Long");
      pg.put(content, "String");
      pg.put(domainId, "Long");
      pg.put(startTime, "Long");
      pg.put(endTime, "Long");
      pg.put(startDate, "String");
      pg.put(endDate, "String");
      pg.put(eventType, "String");
      pg.put(eventTitle, "String");
      pg.put(toUserId, "String");
      ret = (Integer)ejbProxy.invoke("getSearchEventRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    setRecordCount(ret);
    return result;
  }
  
  public List searchEventByEmpName(String userId, Integer currentPage, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String empName, int flag) throws HibernateException {
    List result = (new EventEJBBean()).searchEventByName(userId, currentPage, this.volume, 
        content, domainId, startTime, endTime, startDate, endDate, eventType, eventTitle, empName, flag);
    setRecordCount((new EventEJBBean()).getSearchEventRecordCountByName(userId, content, domainId, startTime, 
          endTime, startDate, endDate, eventType, eventTitle, empName, flag));
    return result;
  }
  
  public List searchEventByEmpName(String userId, Integer currentPage, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String empName) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(12);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "String");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(content, "String");
      pg.put(domainId, "Long");
      pg.put(startTime, "Long");
      pg.put(endTime, "Long");
      pg.put(startDate, "String");
      pg.put(endDate, "String");
      pg.put(eventType, "String");
      pg.put(eventTitle, "String");
      pg.put(empName, "String");
      result = (List)ejbProxy.invoke("searchEventByName", pg.getParameters());
      pg = new ParameterGenerator(10);
      pg.put(userId, "String");
      pg.put(content, "String");
      pg.put(domainId, "Long");
      pg.put(startTime, "Long");
      pg.put(endTime, "Long");
      pg.put(startDate, "String");
      pg.put(endDate, "String");
      pg.put(eventType, "String");
      pg.put(eventTitle, "String");
      pg.put(empName, "String");
      ret = (Integer)ejbProxy.invoke("getSearchEventRecordCountByName", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    setRecordCount(ret);
    return result;
  }
  
  public List getEventByDay(Long userId, Long time, Long domainId, String eventTitle, String eventContent, int flag) throws Exception {
    return (new EventEJBBean()).getEventByDay(userId, time, domainId, eventTitle, eventContent, flag);
  }
  
  public List getEventByDay(Long userId, Long time, Long domainId, String eventTitle, String eventContent) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(time, "Long");
      pg.put(domainId, "Long");
      pg.put(eventTitle, "String");
      pg.put(eventContent, "String");
      result = (List)ejbProxy.invoke("getEventByDay", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getDeskEvent(Long userId, Long time, Long domainId, String eventTitle, String evenContent) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(time, "Long");
      pg.put(domainId, "Long");
      pg.put(eventTitle, "String");
      pg.put(evenContent, "String");
      result = (List)ejbProxy.invoke("getDeskEvent", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(Integer recordCount) {
    this.recordCount = recordCount;
  }
  
  public int getVolume() {
    return this.volume.intValue();
  }
  
  public void setVolume(int volume) {
    this.volume = new Integer(volume);
  }
  
  public static void main(String[] args) {
    EventBD eventBD = new EventBD();
    Map map = eventBD.getAllRemindEvents("18137, 0", null, 
        new Long((new Date()).getTime()));
    List l = (List)map.get("0");
  }
  
  public List getPopEvent(Long userId, Long time, Long domainId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(time, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("getPopRemindEvent", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select pop event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } finally {}
    return result;
  }
  
  public Integer popedEvent(Long eventId, Long userId) {
    Integer result = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(eventId, "Long");
      pg.put(userId, "Long");
      result = (Integer)ejbProxy.invoke("popedEvent", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select poped event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } finally {}
    return result;
  }
  
  public Map getAllRemindEvents(String userIds, String domainId, Long time) {
    Map<Object, Object> result = new HashMap<Object, Object>(0);
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userIds, String.class);
      pg.put(domainId, String.class);
      pg.put(time, Long.class);
      result = (Map<Object, Object>)ejbProxy.invoke("getAllRemindEvents", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select getAllRemindEvents Exception: " + 
          ex.getMessage());
      ex.printStackTrace();
    } finally {}
    return result;
  }
  
  public List getDDEvents(Long userId, String beginDate, String endDate, Long domainId, String evenTitile, String eventContent, int flag) throws HibernateException {
    return (new EventEJBBean()).getDDEvents(userId, beginDate, endDate, domainId, evenTitile, eventContent, flag);
  }
  
  public List getDDEvents(Long userId, String beginDate, String endDate, Long domainId, String evenTitile, String eventContent) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", 
          "EventEJBLocal", EventEJBHome.class);
      pg.put(userId, "Long");
      pg.put(beginDate, "String");
      pg.put(endDate, "String");
      pg.put(domainId, "Long");
      pg.put(evenTitile, "String");
      pg.put(eventContent, "String");
      result = (List)ejbProxy.invoke("getDDEvents", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select dd event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    return result;
  }
  
  public List selectAllEmpEvent(String userIds, Integer currentPage, Long domainId, String eventTitle, String eventContent, int flag) {
    List result = new ArrayList();
    try {
      result = (new EventEJBBean()).selectAllEmpEvent(userIds, currentPage, currentPage, domainId, eventTitle, eventContent, flag);
      setRecordCount((new EventEJBBean()).getEventUderlingRecordCount(userIds, domainId, eventTitle, eventContent, flag));
    } catch (HibernateException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public List selectAllEmpEvent(String userIds, Integer currentPage, Long domainId, String eventTitle, String eventContent) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("EventEJB", "EventEJBLocal", EventEJBHome.class);
      pg.put(userIds, "String");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(eventTitle, "String");
      pg.put(eventContent, "String");
      result = (List)ejbProxy.invoke("selectAllEmpEvent", pg.getParameters());
      pg = new ParameterGenerator(4);
      pg.put(userIds, "String");
      pg.put(domainId, "Long");
      pg.put(eventTitle, "String");
      pg.put(eventContent, "String");
      ret = (Integer)ejbProxy.invoke("getEventUderlingRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All event Exception: " + ex.getMessage());
      ex.printStackTrace();
    } 
    setRecordCount(ret);
    return result;
  }
  
  public String getByIds(Long id) throws HibernateException {
    String result = "N";
    EventEJBBean eventBD = new EventEJBBean();
    result = eventBD.getByIds(id);
    return result;
  }
  
  public static String checkDayByYearAndMonth(String year, String month, String day) {
    try {
      int y = Integer.parseInt(year);
      int m = Integer.parseInt(month);
      int d = Integer.parseInt(day);
      int temp = 1;
      if (m == 2) {
        if ((y % 4 == 0 || y % 400 == 0) && y % 100 != 0) {
          temp = 29;
        } else {
          temp = 28;
        } 
      } else if (m == 4 || m == 6 || m == 9 || m == 11) {
        temp = 30;
      } else {
        temp = 31;
      } 
      if (d > temp)
        return (new StringBuilder(String.valueOf(temp))).toString(); 
    } catch (Exception e) {
      return "1";
    } 
    return day;
  }
  
  public List getRelationEmp(String userId, String orgIdString) {
    return (new EventEJBBean()).getRelationEmp(userId, orgIdString);
  }
}
