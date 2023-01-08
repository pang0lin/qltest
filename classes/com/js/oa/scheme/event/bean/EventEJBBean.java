package com.js.oa.scheme.event.bean;

import com.js.oa.scheme.event.po.EventAttenderPO;
import com.js.oa.scheme.event.po.EventPO;
import com.js.oa.scheme.event.vo.EventVO;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.TransformObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class EventEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getDeskEvent(Long userId, Long time, Long domainId, String eventTitle, String evenContent) throws Exception {
    List result = new ArrayList();
    try {
      begin();
      result.addAll(selectCircleEvent(userId, time, domainId, eventTitle, evenContent));
      result.addAll(selectEventFullDay2(userId, time, domainId));
      result.addAll(getNormalEvent2(userId, time, domainId));
      result.addAll(getRemindEvent(userId, time, domainId));
    } catch (HibernateException ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  private List getRemindEvent(Long userId, Long time, Long domainId) throws HibernateException {
    List<EventVO> result = new ArrayList();
    Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId =" + 
        
        userId + 
        " and eventAttender.eventIsEcho =0 " + 

        
        " and event.eventRemind=1 " + 
        " and (event.eventBeginDate-event.eventRemindTime*1000)<=" + 
        time + 
        
        " and event.eventEndDate>=" + time + (
        (domainId != null) ? (
        " and event.eventDomainId = " + 
        domainId) : "") + 
        " order by event.eventBeginDate desc,event.eventBeginTime");
    List<EventPO> list = query.list();
    EventVO event = null;
    EventPO eventPO = null;
    for (int i = 0; i < list.size(); i++) {
      eventPO = list.get(i);
      event = (EventVO)TransformObject.getInstance().transformObject(
          eventPO, EventVO.class);
      Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          
          event.getEventId() + 
          " and eventAttender.empId =" + 
          userId + (
          (domainId != null) ? (
          " and eventAttender.eventDomainId = " + domainId) : ""));
      List<EventAttenderPO> list1 = query1.list();
      event.setIsViewed(Boolean.FALSE);
      event.setIsAffirmRemind(Boolean.FALSE);
      if (list1 != null && list1.size() > 0) {
        EventAttenderPO eventAttender = list1.get(0);
        if (eventAttender.getEventIsViewed().intValue() == 1)
          event.setIsViewed(Boolean.TRUE); 
        if (eventAttender.getAffirmRemind().intValue() == 1)
          event.setIsAffirmRemind(Boolean.TRUE); 
      } 
      event.setCanDelete(Boolean.FALSE);
      event.setCanModify(Boolean.FALSE);
      event.setIsShare(Boolean.FALSE);
      if (event.getEventEmpId().equals(userId)) {
        event.setCanDelete(Boolean.TRUE);
        event.setCanModify(Boolean.TRUE);
      } else {
        event.setCanDelete(Boolean.TRUE);
        event.setIsShare(Boolean.TRUE);
      } 
      result.add(event);
    } 
    return result;
  }
  
  private List getNormalEvent2(Long userId, Long time, Long domainId) throws HibernateException {
    List<EventVO> result = new ArrayList();
    Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId =" + 
        
        userId + 
        " and eventAttender.eventIsEcho =0 " + 
        
        " and event.eventRemind=0 " + 
        
        "and event.eventFullDay=0 " + 
        "and event.eventBeginDate<=" + time + 
        " and event.eventEndDate>=" + time + (
        (domainId != null) ? (
        " and event.eventDomainId = " + 
        domainId) : "") + 
        " order by event.eventBeginDate desc,event.eventBeginTime");
    List<EventPO> list = query.list();
    EventVO event = null;
    EventPO eventPO = null;
    for (int i = 0; i < list.size(); i++) {
      eventPO = list.get(i);
      event = (EventVO)TransformObject.getInstance().transformObject(
          eventPO, EventVO.class);
      Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          
          event.getEventId() + 
          " and eventAttender.empId =" + 
          userId);
      List<EventAttenderPO> list1 = query1.list();
      event.setIsViewed(Boolean.FALSE);
      event.setIsAffirmRemind(Boolean.FALSE);
      if (list1 != null) {
        EventAttenderPO eventAttender = list1.get(0);
        if (eventAttender.getEventIsViewed().intValue() == 1)
          event.setIsViewed(Boolean.TRUE); 
        if (eventAttender.getAffirmRemind().intValue() == 1)
          event.setIsAffirmRemind(Boolean.TRUE); 
      } 
      event.setCanDelete(Boolean.FALSE);
      event.setCanModify(Boolean.FALSE);
      event.setIsShare(Boolean.FALSE);
      if (event.getEventEmpId().equals(userId)) {
        event.setCanDelete(Boolean.TRUE);
        event.setCanModify(Boolean.TRUE);
      } else {
        event.setCanDelete(Boolean.TRUE);
        event.setIsShare(Boolean.TRUE);
      } 
      result.add(event);
    } 
    return result;
  }
  
  private List selectEventFullDay2(Long userId, Long time, Long domainId) throws HibernateException {
    List<EventVO> result = new ArrayList();
    Query query = this.session.createQuery("select event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId =" + 
        
        userId + 
        
        " and event.eventRemind=0 " + 
        
        " and event.eventBeginDate <=" + 
        time + 
        " and event.eventEndDate >=" + 
        time + 
        " and event.eventFullDay = 1 " + (
        (domainId != null) ? (
        " and event.eventDomainId = " + 
        domainId) : "") + 
        " order by event.eventBeginDate desc,event.eventBeginTime");
    List<EventPO> list = query.list();
    EventVO event = null;
    EventPO eventPO = null;
    for (int i = 0; i < list.size(); i++) {
      eventPO = list.get(i);
      event = (EventVO)TransformObject.getInstance().transformObject(
          eventPO, EventVO.class);
      Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          
          event.getEventId() + 
          " and eventAttender.empId =" + 
          userId);
      List<EventAttenderPO> list1 = query1.list();
      event.setIsViewed(Boolean.FALSE);
      event.setIsAffirmRemind(Boolean.FALSE);
      if (list1 != null) {
        EventAttenderPO eventAttender = list1.get(0);
        if (eventAttender.getEventIsViewed().intValue() == 1)
          event.setIsViewed(Boolean.TRUE); 
        if (eventAttender.getAffirmRemind().intValue() == 1)
          event.setIsAffirmRemind(Boolean.TRUE); 
      } 
      event.setCanDelete(Boolean.FALSE);
      event.setCanModify(Boolean.FALSE);
      event.setIsShare(Boolean.FALSE);
      if (event.getEventEmpId().equals(userId)) {
        event.setCanDelete(Boolean.TRUE);
        event.setCanModify(Boolean.TRUE);
      } else {
        event.setCanDelete(Boolean.TRUE);
        event.setIsShare(Boolean.TRUE);
      } 
      result.add(event);
    } 
    return result;
  }
  
  public List getEventByDay(Long userId, Long time, Long domainId, String eventTitle, String eventContent) throws Exception {
    return getEventByDay(userId, time, domainId, eventTitle, eventContent, 0);
  }
  
  public List getEventByDay(Long userId, Long time, Long domainId, String eventTitle, String eventContent, int flag) throws Exception {
    List result = new ArrayList();
    try {
      begin();
      result.addAll(selectCircleEvent(userId, time, domainId, eventTitle, eventContent, flag));
      result.addAll(selectEventFullDay(userId, time, domainId, eventTitle, eventContent, flag));
      result.addAll(getNormalEvent(userId, time, domainId, eventTitle, eventContent, flag));
    } catch (HibernateException ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  private List getNormalEvent(Long userId, Long time, Long domainId, String eventTitle, String evenContent) throws HibernateException {
    return getNormalEvent(userId, time, domainId, eventTitle, evenContent, 0);
  }
  
  private List getNormalEvent(Long userId, Long time, Long domainId, String eventTitle, String evenContent, int flag) throws HibernateException {
    List<EventVO> result = new ArrayList();
    StringBuffer appendSql = new StringBuffer();
    if (eventTitle != null && !eventTitle.equals(""))
      appendSql.append(" and event.eventTitle like '%" + eventTitle + "%'"); 
    if (evenContent != null && !evenContent.equals(""))
      appendSql.append(" and event.eventContent like '%" + evenContent + "%'"); 
    Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where " + ((
        
        "qhcsj".equals(SystemCommon.getCustomerName()) && 1 == flag) ? (
        " (event.eventEmpId=" + userId + " or event.attendEmp like '%$" + userId + "$%') and event.openEvent=1 ") : ("eventAttender.empId =" + userId)) + 
        " and eventAttender.eventIsEcho =0 " + "and event.eventFullDay=0 " + 
        "and event.eventBeginDate<=" + time + " and event.eventEndDate>=" + time + (
        (domainId != null) ? (" and event.eventDomainId=" + String.valueOf(domainId.intValue())) : "") + 
        appendSql.toString() + " order by event.eventBeginDate desc,event.eventBeginTime");
    List<EventPO> list = query.list();
    EventVO event = null;
    EventPO eventPO = null;
    for (int i = 0; i < list.size(); i++) {
      eventPO = list.get(i);
      event = (EventVO)TransformObject.getInstance().transformObject(eventPO, EventVO.class);
      Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          event.getEventId() + " and eventAttender.empId =" + userId);
      List<EventAttenderPO> list1 = query1.list();
      event.setIsViewed(Boolean.FALSE);
      event.setIsAffirmRemind(Boolean.FALSE);
      if (list1 != null) {
        EventAttenderPO eventAttender = list1.get(0);
        if (eventAttender.getEventIsViewed() != null && 
          eventAttender.getEventIsViewed().intValue() == 1)
          event.setIsViewed(Boolean.TRUE); 
        if (eventAttender.getAffirmRemind() != null && 
          eventAttender.getAffirmRemind().intValue() == 1)
          event.setIsAffirmRemind(Boolean.TRUE); 
      } 
      event.setCanDelete(Boolean.FALSE);
      event.setCanModify(Boolean.FALSE);
      event.setIsShare(Boolean.FALSE);
      if (event.getEventEmpId().equals(userId)) {
        event.setCanDelete(Boolean.TRUE);
        event.setCanModify(Boolean.TRUE);
      } else {
        event.setCanDelete(Boolean.TRUE);
        event.setIsShare(Boolean.TRUE);
      } 
      result.add(event);
    } 
    return result;
  }
  
  private List selectEventFullDay(Long userId, Long time, Long domainId, String eventTitle, String evenContent) throws HibernateException {
    return selectEventFullDay(userId, time, domainId, eventTitle, evenContent, 0);
  }
  
  private List selectEventFullDay(Long userId, Long time, Long domainId, String eventTitle, String evenContent, int flag) throws HibernateException {
    List<EventVO> result = new ArrayList();
    StringBuffer appendSql = new StringBuffer("");
    if (eventTitle != null && !eventTitle.equals(""))
      appendSql.append(" and event.eventTitle like '%" + eventTitle + "%'"); 
    if (evenContent != null && !evenContent.equals(""))
      appendSql.append(" and event.eventContent like '%" + evenContent + "%'"); 
    Query query = this.session.createQuery("select event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId =" + 
        userId + " and event.eventBeginDate <=" + time + " and event.eventEndDate >=" + time + (
        (domainId != null) ? (" and event.eventDomainId=" + String.valueOf(domainId.intValue())) : "") + appendSql.toString() + 
        " and event.eventFullDay = 1" + ((
        "qhcsj".equals(SystemCommon.getCustomerName()) && 1 == flag) ? " and event.openEvent=1 " : "") + 
        " order by event.eventBeginDate desc,event.eventBeginTime");
    List<EventPO> list = query.list();
    EventVO event = null;
    EventPO eventPO = null;
    for (int i = 0; i < list.size(); i++) {
      eventPO = list.get(i);
      event = (EventVO)TransformObject.getInstance().transformObject(
          eventPO, EventVO.class);
      Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          event.getEventId() + " and eventAttender.empId =" + userId);
      List<EventAttenderPO> list1 = query1.list();
      event.setIsViewed(Boolean.FALSE);
      event.setIsAffirmRemind(Boolean.FALSE);
      if (list1 != null) {
        EventAttenderPO eventAttender = list1.get(0);
        if (eventAttender.getEventIsViewed() != null && 
          eventAttender.getEventIsViewed().intValue() == 1)
          event.setIsViewed(Boolean.TRUE); 
        if (eventAttender.getAffirmRemind() != null && 
          eventAttender.getAffirmRemind().intValue() == 1)
          event.setIsAffirmRemind(Boolean.TRUE); 
      } 
      event.setCanDelete(Boolean.FALSE);
      event.setCanModify(Boolean.FALSE);
      event.setIsShare(Boolean.FALSE);
      if (event.getEventEmpId().equals(userId)) {
        event.setCanDelete(Boolean.TRUE);
        event.setCanModify(Boolean.TRUE);
      } else {
        event.setCanDelete(Boolean.TRUE);
        event.setIsShare(Boolean.TRUE);
      } 
      result.add(event);
    } 
    return result;
  }
  
  public List selectCircleEvent(Long userId, Long time, Long domainId, String eventTitle, String eventContent) throws Exception {
    return selectCircleEvent(userId, time, domainId, eventTitle, eventContent, 0);
  }
  
  public List selectCircleEvent(Long userId, Long time, Long domainId, String eventTitle, String eventContent, int flag) throws Exception {
    // Byte code:
    //   0: aload_0
    //   1: getfield session : Lnet/sf/hibernate/Session;
    //   4: ifnonnull -> 11
    //   7: aload_0
    //   8: invokevirtual begin : ()V
    //   11: new java/util/ArrayList
    //   14: dup
    //   15: invokespecial <init> : ()V
    //   18: astore #7
    //   20: new java/util/GregorianCalendar
    //   23: dup
    //   24: getstatic java/util/Locale.CHINESE : Ljava/util/Locale;
    //   27: invokespecial <init> : (Ljava/util/Locale;)V
    //   30: astore #8
    //   32: aload #8
    //   34: aload_2
    //   35: invokevirtual longValue : ()J
    //   38: invokevirtual setTimeInMillis : (J)V
    //   41: aload #8
    //   43: invokevirtual getTime : ()Ljava/util/Date;
    //   46: astore #9
    //   48: aload #8
    //   50: iconst_1
    //   51: invokevirtual get : (I)I
    //   54: istore #10
    //   56: aload #8
    //   58: iconst_2
    //   59: invokevirtual get : (I)I
    //   62: istore #11
    //   64: aload #8
    //   66: iconst_5
    //   67: invokevirtual get : (I)I
    //   70: istore #12
    //   72: aload #8
    //   74: bipush #7
    //   76: invokevirtual get : (I)I
    //   79: istore #13
    //   81: new java/lang/StringBuffer
    //   84: dup
    //   85: ldc ''
    //   87: invokespecial <init> : (Ljava/lang/String;)V
    //   90: astore #14
    //   92: aload #4
    //   94: ifnull -> 137
    //   97: aload #4
    //   99: ldc ''
    //   101: invokevirtual equals : (Ljava/lang/Object;)Z
    //   104: ifne -> 137
    //   107: aload #14
    //   109: new java/lang/StringBuilder
    //   112: dup
    //   113: ldc_w ' and event.eventTitle like '%'
    //   116: invokespecial <init> : (Ljava/lang/String;)V
    //   119: aload #4
    //   121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: ldc_w '%''
    //   127: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: invokevirtual toString : ()Ljava/lang/String;
    //   133: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   136: pop
    //   137: aload #5
    //   139: ifnull -> 182
    //   142: aload #5
    //   144: ldc ''
    //   146: invokevirtual equals : (Ljava/lang/Object;)Z
    //   149: ifne -> 182
    //   152: aload #14
    //   154: new java/lang/StringBuilder
    //   157: dup
    //   158: ldc_w ' and event.eventContent like '%'
    //   161: invokespecial <init> : (Ljava/lang/String;)V
    //   164: aload #5
    //   166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: ldc_w '%''
    //   172: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: invokevirtual toString : ()Ljava/lang/String;
    //   178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   181: pop
    //   182: aload_0
    //   183: getfield session : Lnet/sf/hibernate/Session;
    //   186: new java/lang/StringBuilder
    //   189: dup
    //   190: ldc 'select event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId ='
    //   192: invokespecial <init> : (Ljava/lang/String;)V
    //   195: aload_1
    //   196: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   199: ldc_w ' and event.onTimeMode > 0'
    //   202: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: ldc_w 'qhcsj'
    //   208: invokestatic getCustomerName : ()Ljava/lang/String;
    //   211: invokevirtual equals : (Ljava/lang/Object;)Z
    //   214: ifeq -> 229
    //   217: iconst_1
    //   218: iload #6
    //   220: if_icmpne -> 229
    //   223: ldc_w ' and event.openEvent=1 '
    //   226: goto -> 231
    //   229: ldc ''
    //   231: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: aload_3
    //   235: ifnull -> 264
    //   238: new java/lang/StringBuilder
    //   241: dup
    //   242: ldc_w ' and event.eventDomainId='
    //   245: invokespecial <init> : (Ljava/lang/String;)V
    //   248: aload_3
    //   249: invokevirtual intValue : ()I
    //   252: invokestatic valueOf : (I)Ljava/lang/String;
    //   255: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: invokevirtual toString : ()Ljava/lang/String;
    //   261: goto -> 266
    //   264: ldc ''
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: aload #14
    //   271: invokevirtual toString : ()Ljava/lang/String;
    //   274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: invokevirtual toString : ()Ljava/lang/String;
    //   280: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   285: astore #15
    //   287: aload #15
    //   289: invokeinterface list : ()Ljava/util/List;
    //   294: astore #16
    //   296: aconst_null
    //   297: astore #17
    //   299: aconst_null
    //   300: astore #18
    //   302: new java/lang/StringBuilder
    //   305: dup
    //   306: ldc_w 'set.orgIds='*-1*' or set.userIds like '%$'
    //   309: invokespecial <init> : (Ljava/lang/String;)V
    //   312: aload_1
    //   313: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   316: ldc_w '$%' '
    //   319: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: invokevirtual toString : ()Ljava/lang/String;
    //   325: astore #19
    //   327: new java/lang/StringBuilder
    //   330: dup
    //   331: invokespecial <init> : ()V
    //   334: aload_1
    //   335: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   338: invokevirtual toString : ()Ljava/lang/String;
    //   341: invokestatic getOrgIdByEmpId : (Ljava/lang/String;)Ljava/lang/String;
    //   344: invokestatic getOrgIdStringByOrgId : (Ljava/lang/String;)Ljava/lang/String;
    //   347: ldc_w '$'
    //   350: ldc_w ','
    //   353: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   356: ldc_w ','
    //   359: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   362: astore #20
    //   364: iconst_1
    //   365: istore #21
    //   367: goto -> 410
    //   370: new java/lang/StringBuilder
    //   373: dup
    //   374: aload #19
    //   376: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   379: invokespecial <init> : (Ljava/lang/String;)V
    //   382: ldc_w ' or set.orgIds like '%*'
    //   385: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   388: aload #20
    //   390: iload #21
    //   392: aaload
    //   393: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   396: ldc_w '*%''
    //   399: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: invokevirtual toString : ()Ljava/lang/String;
    //   405: astore #19
    //   407: iinc #21, 2
    //   410: iload #21
    //   412: aload #20
    //   414: arraylength
    //   415: if_icmplt -> 370
    //   418: new java/lang/StringBuilder
    //   421: dup
    //   422: invokespecial <init> : ()V
    //   425: aload_1
    //   426: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   429: invokevirtual toString : ()Ljava/lang/String;
    //   432: invokestatic getGroupIdByEmpId : (Ljava/lang/String;)Ljava/lang/String;
    //   435: ldc_w ','
    //   438: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   441: astore #21
    //   443: iconst_0
    //   444: istore #22
    //   446: goto -> 502
    //   449: ldc ''
    //   451: aload #21
    //   453: iload #22
    //   455: aaload
    //   456: invokevirtual equals : (Ljava/lang/Object;)Z
    //   459: ifne -> 499
    //   462: new java/lang/StringBuilder
    //   465: dup
    //   466: aload #19
    //   468: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   471: invokespecial <init> : (Ljava/lang/String;)V
    //   474: ldc_w ' or set.groupIds like '%@'
    //   477: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   480: aload #21
    //   482: iload #22
    //   484: aaload
    //   485: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: ldc_w '@%''
    //   491: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   494: invokevirtual toString : ()Ljava/lang/String;
    //   497: astore #19
    //   499: iinc #22, 1
    //   502: iload #22
    //   504: aload #21
    //   506: arraylength
    //   507: if_icmplt -> 449
    //   510: aload_0
    //   511: getfield session : Lnet/sf/hibernate/Session;
    //   514: new java/lang/StringBuilder
    //   517: dup
    //   518: ldc_w 'select set.workday from com.js.oa.hr.kq.po.KqDutySetPO set where '
    //   521: invokespecial <init> : (Ljava/lang/String;)V
    //   524: aload #19
    //   526: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   529: ldc_w ' order by set.id'
    //   532: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   535: invokevirtual toString : ()Ljava/lang/String;
    //   538: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   543: astore #22
    //   545: aload #22
    //   547: invokeinterface list : ()Ljava/util/List;
    //   552: astore #23
    //   554: iconst_0
    //   555: istore #24
    //   557: goto -> 9383
    //   560: aload #16
    //   562: iload #24
    //   564: invokeinterface get : (I)Ljava/lang/Object;
    //   569: checkcast com/js/oa/scheme/event/po/EventPO
    //   572: astore #17
    //   574: aload #17
    //   576: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   579: invokevirtual intValue : ()I
    //   582: iconst_1
    //   583: if_icmpne -> 1963
    //   586: aload #17
    //   588: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   591: ldc_w '1'
    //   594: invokevirtual equals : (Ljava/lang/Object;)Z
    //   597: ifeq -> 1188
    //   600: aload #23
    //   602: invokeinterface size : ()I
    //   607: ifle -> 9380
    //   610: aload #23
    //   612: iconst_0
    //   613: invokeinterface get : (I)Ljava/lang/Object;
    //   618: invokevirtual toString : ()Ljava/lang/String;
    //   621: astore #25
    //   623: aload #17
    //   625: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   628: aload #9
    //   630: invokevirtual compareTo : (Ljava/util/Date;)I
    //   633: ifgt -> 9380
    //   636: aload #17
    //   638: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   641: invokevirtual intValue : ()I
    //   644: iconst_m1
    //   645: if_icmpne -> 884
    //   648: aload #25
    //   650: iload #13
    //   652: iconst_1
    //   653: isub
    //   654: invokevirtual charAt : (I)C
    //   657: bipush #49
    //   659: if_icmpne -> 9380
    //   662: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   665: aload #17
    //   667: ldc com/js/oa/scheme/event/vo/EventVO
    //   669: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   672: checkcast com/js/oa/scheme/event/vo/EventVO
    //   675: astore #18
    //   677: aload_0
    //   678: getfield session : Lnet/sf/hibernate/Session;
    //   681: new java/lang/StringBuilder
    //   684: dup
    //   685: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   687: invokespecial <init> : (Ljava/lang/String;)V
    //   690: aload #18
    //   692: invokevirtual getEventId : ()Ljava/lang/Long;
    //   695: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   698: ldc ' and eventAttender.empId ='
    //   700: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   703: aload_1
    //   704: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   707: invokevirtual toString : ()Ljava/lang/String;
    //   710: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   715: astore #26
    //   717: aload #26
    //   719: invokeinterface list : ()Ljava/util/List;
    //   724: astore #27
    //   726: aload #18
    //   728: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   731: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   734: aload #18
    //   736: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   739: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   742: aload #27
    //   744: ifnull -> 800
    //   747: aload #27
    //   749: iconst_0
    //   750: invokeinterface get : (I)Ljava/lang/Object;
    //   755: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   758: astore #28
    //   760: aload #28
    //   762: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   765: invokevirtual intValue : ()I
    //   768: iconst_1
    //   769: if_icmpne -> 780
    //   772: aload #18
    //   774: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   777: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   780: aload #28
    //   782: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   785: invokevirtual intValue : ()I
    //   788: iconst_1
    //   789: if_icmpne -> 800
    //   792: aload #18
    //   794: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   797: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   800: aload #18
    //   802: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   805: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   808: aload #18
    //   810: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   813: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   816: aload #18
    //   818: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   821: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   824: aload #18
    //   826: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   829: aload_1
    //   830: invokevirtual equals : (Ljava/lang/Object;)Z
    //   833: ifeq -> 855
    //   836: aload #18
    //   838: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   841: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   844: aload #18
    //   846: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   849: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   852: goto -> 871
    //   855: aload #18
    //   857: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   860: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   863: aload #18
    //   865: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   868: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   871: aload #7
    //   873: aload #18
    //   875: invokeinterface add : (Ljava/lang/Object;)Z
    //   880: pop
    //   881: goto -> 9380
    //   884: aload #17
    //   886: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   889: invokevirtual intValue : ()I
    //   892: ifne -> 1144
    //   895: aload #17
    //   897: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   900: aload #9
    //   902: invokevirtual compareTo : (Ljava/util/Date;)I
    //   905: iflt -> 9380
    //   908: aload #25
    //   910: iload #13
    //   912: iconst_1
    //   913: isub
    //   914: invokevirtual charAt : (I)C
    //   917: bipush #49
    //   919: if_icmpne -> 9380
    //   922: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   925: aload #17
    //   927: ldc com/js/oa/scheme/event/vo/EventVO
    //   929: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   932: checkcast com/js/oa/scheme/event/vo/EventVO
    //   935: astore #18
    //   937: aload_0
    //   938: getfield session : Lnet/sf/hibernate/Session;
    //   941: new java/lang/StringBuilder
    //   944: dup
    //   945: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   947: invokespecial <init> : (Ljava/lang/String;)V
    //   950: aload #18
    //   952: invokevirtual getEventId : ()Ljava/lang/Long;
    //   955: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   958: ldc ' and eventAttender.empId ='
    //   960: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   963: aload_1
    //   964: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   967: invokevirtual toString : ()Ljava/lang/String;
    //   970: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   975: astore #26
    //   977: aload #26
    //   979: invokeinterface list : ()Ljava/util/List;
    //   984: astore #27
    //   986: aload #18
    //   988: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   991: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   994: aload #18
    //   996: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   999: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1002: aload #27
    //   1004: ifnull -> 1060
    //   1007: aload #27
    //   1009: iconst_0
    //   1010: invokeinterface get : (I)Ljava/lang/Object;
    //   1015: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1018: astore #28
    //   1020: aload #28
    //   1022: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1025: invokevirtual intValue : ()I
    //   1028: iconst_1
    //   1029: if_icmpne -> 1040
    //   1032: aload #18
    //   1034: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1037: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1040: aload #28
    //   1042: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1045: invokevirtual intValue : ()I
    //   1048: iconst_1
    //   1049: if_icmpne -> 1060
    //   1052: aload #18
    //   1054: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1057: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1060: aload #18
    //   1062: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1065: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1068: aload #18
    //   1070: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1073: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1076: aload #18
    //   1078: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1081: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1084: aload #18
    //   1086: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1089: aload_1
    //   1090: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1093: ifeq -> 1115
    //   1096: aload #18
    //   1098: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1101: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1104: aload #18
    //   1106: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1109: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1112: goto -> 1131
    //   1115: aload #18
    //   1117: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1120: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1123: aload #18
    //   1125: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1128: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1131: aload #7
    //   1133: aload #18
    //   1135: invokeinterface add : (Ljava/lang/Object;)Z
    //   1140: pop
    //   1141: goto -> 9380
    //   1144: aload #17
    //   1146: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1149: invokevirtual intValue : ()I
    //   1152: ifle -> 9380
    //   1155: aload #17
    //   1157: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   1160: invokevirtual intValue : ()I
    //   1163: istore #26
    //   1165: aload #17
    //   1167: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   1170: invokevirtual getTime : ()J
    //   1173: lstore #27
    //   1175: aload #25
    //   1177: iload #13
    //   1179: iconst_1
    //   1180: isub
    //   1181: invokevirtual charAt : (I)C
    //   1184: pop
    //   1185: goto -> 9380
    //   1188: aload #17
    //   1190: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   1193: aload #9
    //   1195: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1198: ifgt -> 9380
    //   1201: aload #17
    //   1203: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1206: invokevirtual intValue : ()I
    //   1209: iconst_m1
    //   1210: if_icmpne -> 1435
    //   1213: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1216: aload #17
    //   1218: ldc com/js/oa/scheme/event/vo/EventVO
    //   1220: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1223: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1226: astore #18
    //   1228: aload_0
    //   1229: getfield session : Lnet/sf/hibernate/Session;
    //   1232: new java/lang/StringBuilder
    //   1235: dup
    //   1236: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1238: invokespecial <init> : (Ljava/lang/String;)V
    //   1241: aload #18
    //   1243: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1246: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1249: ldc ' and eventAttender.empId ='
    //   1251: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1254: aload_1
    //   1255: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1258: invokevirtual toString : ()Ljava/lang/String;
    //   1261: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1266: astore #25
    //   1268: aload #25
    //   1270: invokeinterface list : ()Ljava/util/List;
    //   1275: astore #26
    //   1277: aload #18
    //   1279: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1282: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1285: aload #18
    //   1287: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1290: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1293: aload #26
    //   1295: ifnull -> 1351
    //   1298: aload #26
    //   1300: iconst_0
    //   1301: invokeinterface get : (I)Ljava/lang/Object;
    //   1306: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1309: astore #27
    //   1311: aload #27
    //   1313: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1316: invokevirtual intValue : ()I
    //   1319: iconst_1
    //   1320: if_icmpne -> 1331
    //   1323: aload #18
    //   1325: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1328: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1331: aload #27
    //   1333: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1336: invokevirtual intValue : ()I
    //   1339: iconst_1
    //   1340: if_icmpne -> 1351
    //   1343: aload #18
    //   1345: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1348: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1351: aload #18
    //   1353: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1356: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1359: aload #18
    //   1361: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1364: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1367: aload #18
    //   1369: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1372: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1375: aload #18
    //   1377: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1380: aload_1
    //   1381: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1384: ifeq -> 1406
    //   1387: aload #18
    //   1389: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1392: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1395: aload #18
    //   1397: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1400: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1403: goto -> 1422
    //   1406: aload #18
    //   1408: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1411: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1414: aload #18
    //   1416: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1419: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1422: aload #7
    //   1424: aload #18
    //   1426: invokeinterface add : (Ljava/lang/Object;)Z
    //   1431: pop
    //   1432: goto -> 9380
    //   1435: aload #17
    //   1437: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1440: invokevirtual intValue : ()I
    //   1443: ifne -> 1681
    //   1446: aload #17
    //   1448: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   1451: aload #9
    //   1453: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1456: iflt -> 9380
    //   1459: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1462: aload #17
    //   1464: ldc com/js/oa/scheme/event/vo/EventVO
    //   1466: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1469: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1472: astore #18
    //   1474: aload_0
    //   1475: getfield session : Lnet/sf/hibernate/Session;
    //   1478: new java/lang/StringBuilder
    //   1481: dup
    //   1482: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1484: invokespecial <init> : (Ljava/lang/String;)V
    //   1487: aload #18
    //   1489: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1492: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1495: ldc ' and eventAttender.empId ='
    //   1497: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1500: aload_1
    //   1501: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1504: invokevirtual toString : ()Ljava/lang/String;
    //   1507: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1512: astore #25
    //   1514: aload #25
    //   1516: invokeinterface list : ()Ljava/util/List;
    //   1521: astore #26
    //   1523: aload #18
    //   1525: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1528: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1531: aload #18
    //   1533: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1536: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1539: aload #26
    //   1541: ifnull -> 1597
    //   1544: aload #26
    //   1546: iconst_0
    //   1547: invokeinterface get : (I)Ljava/lang/Object;
    //   1552: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1555: astore #27
    //   1557: aload #27
    //   1559: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1562: invokevirtual intValue : ()I
    //   1565: iconst_1
    //   1566: if_icmpne -> 1577
    //   1569: aload #18
    //   1571: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1574: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1577: aload #27
    //   1579: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1582: invokevirtual intValue : ()I
    //   1585: iconst_1
    //   1586: if_icmpne -> 1597
    //   1589: aload #18
    //   1591: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1594: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1597: aload #18
    //   1599: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1602: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1605: aload #18
    //   1607: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1610: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1613: aload #18
    //   1615: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1618: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1621: aload #18
    //   1623: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1626: aload_1
    //   1627: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1630: ifeq -> 1652
    //   1633: aload #18
    //   1635: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1638: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1641: aload #18
    //   1643: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1646: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1649: goto -> 1668
    //   1652: aload #18
    //   1654: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1657: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1660: aload #18
    //   1662: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1665: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1668: aload #7
    //   1670: aload #18
    //   1672: invokeinterface add : (Ljava/lang/Object;)Z
    //   1677: pop
    //   1678: goto -> 9380
    //   1681: aload #17
    //   1683: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1686: invokevirtual intValue : ()I
    //   1689: ifle -> 9380
    //   1692: aload #17
    //   1694: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   1697: aload #17
    //   1699: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1702: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   1705: ifgt -> 9380
    //   1708: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1711: aload #17
    //   1713: ldc com/js/oa/scheme/event/vo/EventVO
    //   1715: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1718: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1721: astore #18
    //   1723: aload_0
    //   1724: getfield session : Lnet/sf/hibernate/Session;
    //   1727: new java/lang/StringBuilder
    //   1730: dup
    //   1731: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1733: invokespecial <init> : (Ljava/lang/String;)V
    //   1736: aload #18
    //   1738: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1741: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1744: ldc ' and eventAttender.empId ='
    //   1746: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1749: aload_1
    //   1750: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1753: invokevirtual toString : ()Ljava/lang/String;
    //   1756: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1761: astore #25
    //   1763: aload #25
    //   1765: invokeinterface list : ()Ljava/util/List;
    //   1770: astore #26
    //   1772: aload #18
    //   1774: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1777: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1780: aload #18
    //   1782: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1785: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1788: aload #26
    //   1790: ifnull -> 1846
    //   1793: aload #26
    //   1795: iconst_0
    //   1796: invokeinterface get : (I)Ljava/lang/Object;
    //   1801: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1804: astore #27
    //   1806: aload #27
    //   1808: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1811: invokevirtual intValue : ()I
    //   1814: iconst_1
    //   1815: if_icmpne -> 1826
    //   1818: aload #18
    //   1820: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1823: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1826: aload #27
    //   1828: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1831: invokevirtual intValue : ()I
    //   1834: iconst_1
    //   1835: if_icmpne -> 1846
    //   1838: aload #18
    //   1840: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1843: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1846: aload #18
    //   1848: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1851: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1854: aload #18
    //   1856: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1859: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1862: aload #18
    //   1864: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1867: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1870: aload #18
    //   1872: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1875: aload_1
    //   1876: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1879: ifeq -> 1901
    //   1882: aload #18
    //   1884: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1887: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1890: aload #18
    //   1892: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1895: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1898: goto -> 1917
    //   1901: aload #18
    //   1903: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1906: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1909: aload #18
    //   1911: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1914: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1917: aload #7
    //   1919: aload #18
    //   1921: invokeinterface add : (Ljava/lang/Object;)Z
    //   1926: pop
    //   1927: aload #17
    //   1929: new java/lang/Integer
    //   1932: dup
    //   1933: aload #17
    //   1935: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   1938: invokevirtual intValue : ()I
    //   1941: iconst_1
    //   1942: iadd
    //   1943: invokespecial <init> : (I)V
    //   1946: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   1949: aload_0
    //   1950: getfield session : Lnet/sf/hibernate/Session;
    //   1953: aload #17
    //   1955: invokeinterface update : (Ljava/lang/Object;)V
    //   1960: goto -> 9380
    //   1963: aload #17
    //   1965: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   1968: invokevirtual intValue : ()I
    //   1971: iconst_2
    //   1972: if_icmpne -> 2827
    //   1975: aload #17
    //   1977: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1980: invokevirtual intValue : ()I
    //   1983: iconst_m1
    //   1984: if_icmpne -> 2239
    //   1987: aload #17
    //   1989: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   1992: aload #9
    //   1994: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1997: ifgt -> 9380
    //   2000: aload #17
    //   2002: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2005: iload #13
    //   2007: iconst_1
    //   2008: isub
    //   2009: invokevirtual charAt : (I)C
    //   2012: bipush #49
    //   2014: if_icmpne -> 9380
    //   2017: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2020: aload #17
    //   2022: ldc com/js/oa/scheme/event/vo/EventVO
    //   2024: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2027: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2030: astore #18
    //   2032: aload_0
    //   2033: getfield session : Lnet/sf/hibernate/Session;
    //   2036: new java/lang/StringBuilder
    //   2039: dup
    //   2040: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2042: invokespecial <init> : (Ljava/lang/String;)V
    //   2045: aload #18
    //   2047: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2050: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2053: ldc ' and eventAttender.empId ='
    //   2055: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2058: aload_1
    //   2059: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2062: invokevirtual toString : ()Ljava/lang/String;
    //   2065: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2070: astore #25
    //   2072: aload #25
    //   2074: invokeinterface list : ()Ljava/util/List;
    //   2079: astore #26
    //   2081: aload #18
    //   2083: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2086: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2089: aload #18
    //   2091: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2094: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2097: aload #26
    //   2099: ifnull -> 2155
    //   2102: aload #26
    //   2104: iconst_0
    //   2105: invokeinterface get : (I)Ljava/lang/Object;
    //   2110: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2113: astore #27
    //   2115: aload #27
    //   2117: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2120: invokevirtual intValue : ()I
    //   2123: iconst_1
    //   2124: if_icmpne -> 2135
    //   2127: aload #18
    //   2129: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2132: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2135: aload #27
    //   2137: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2140: invokevirtual intValue : ()I
    //   2143: iconst_1
    //   2144: if_icmpne -> 2155
    //   2147: aload #18
    //   2149: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2152: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2155: aload #18
    //   2157: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2160: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2163: aload #18
    //   2165: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2168: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2171: aload #18
    //   2173: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2176: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2179: aload #18
    //   2181: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2184: aload_1
    //   2185: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2188: ifeq -> 2210
    //   2191: aload #18
    //   2193: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2196: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2199: aload #18
    //   2201: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2204: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2207: goto -> 2226
    //   2210: aload #18
    //   2212: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2215: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2218: aload #18
    //   2220: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2223: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2226: aload #7
    //   2228: aload #18
    //   2230: invokeinterface add : (Ljava/lang/Object;)Z
    //   2235: pop
    //   2236: goto -> 9380
    //   2239: aload #17
    //   2241: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2244: invokevirtual intValue : ()I
    //   2247: ifne -> 2515
    //   2250: aload #17
    //   2252: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   2255: aload #9
    //   2257: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2260: ifgt -> 9380
    //   2263: aload #17
    //   2265: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2268: iload #13
    //   2270: iconst_1
    //   2271: isub
    //   2272: invokevirtual charAt : (I)C
    //   2275: bipush #49
    //   2277: if_icmpne -> 9380
    //   2280: aload #17
    //   2282: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   2285: aload #9
    //   2287: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2290: iflt -> 9380
    //   2293: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2296: aload #17
    //   2298: ldc com/js/oa/scheme/event/vo/EventVO
    //   2300: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2303: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2306: astore #18
    //   2308: aload_0
    //   2309: getfield session : Lnet/sf/hibernate/Session;
    //   2312: new java/lang/StringBuilder
    //   2315: dup
    //   2316: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2318: invokespecial <init> : (Ljava/lang/String;)V
    //   2321: aload #18
    //   2323: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2326: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2329: ldc ' and eventAttender.empId ='
    //   2331: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2334: aload_1
    //   2335: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2338: invokevirtual toString : ()Ljava/lang/String;
    //   2341: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2346: astore #25
    //   2348: aload #25
    //   2350: invokeinterface list : ()Ljava/util/List;
    //   2355: astore #26
    //   2357: aload #18
    //   2359: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2362: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2365: aload #18
    //   2367: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2370: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2373: aload #26
    //   2375: ifnull -> 2431
    //   2378: aload #26
    //   2380: iconst_0
    //   2381: invokeinterface get : (I)Ljava/lang/Object;
    //   2386: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2389: astore #27
    //   2391: aload #27
    //   2393: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2396: invokevirtual intValue : ()I
    //   2399: iconst_1
    //   2400: if_icmpne -> 2411
    //   2403: aload #18
    //   2405: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2408: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2411: aload #27
    //   2413: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2416: invokevirtual intValue : ()I
    //   2419: iconst_1
    //   2420: if_icmpne -> 2431
    //   2423: aload #18
    //   2425: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2428: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2431: aload #18
    //   2433: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2436: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2439: aload #18
    //   2441: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2444: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2447: aload #18
    //   2449: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2452: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2455: aload #18
    //   2457: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2460: aload_1
    //   2461: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2464: ifeq -> 2486
    //   2467: aload #18
    //   2469: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2472: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2475: aload #18
    //   2477: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2480: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2483: goto -> 2502
    //   2486: aload #18
    //   2488: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2491: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2494: aload #18
    //   2496: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2499: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2502: aload #7
    //   2504: aload #18
    //   2506: invokeinterface add : (Ljava/lang/Object;)Z
    //   2511: pop
    //   2512: goto -> 9380
    //   2515: aload #17
    //   2517: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2520: invokevirtual intValue : ()I
    //   2523: ifle -> 9380
    //   2526: aload #17
    //   2528: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   2531: aload #9
    //   2533: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2536: ifgt -> 9380
    //   2539: aload #17
    //   2541: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2544: iload #13
    //   2546: iconst_1
    //   2547: isub
    //   2548: invokevirtual charAt : (I)C
    //   2551: bipush #49
    //   2553: if_icmpne -> 9380
    //   2556: aload #17
    //   2558: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   2561: aload #17
    //   2563: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2566: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   2569: ifge -> 9380
    //   2572: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2575: aload #17
    //   2577: ldc com/js/oa/scheme/event/vo/EventVO
    //   2579: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2582: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2585: astore #18
    //   2587: aload_0
    //   2588: getfield session : Lnet/sf/hibernate/Session;
    //   2591: new java/lang/StringBuilder
    //   2594: dup
    //   2595: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2597: invokespecial <init> : (Ljava/lang/String;)V
    //   2600: aload #18
    //   2602: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2605: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2608: ldc ' and eventAttender.empId ='
    //   2610: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2613: aload_1
    //   2614: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2617: invokevirtual toString : ()Ljava/lang/String;
    //   2620: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2625: astore #25
    //   2627: aload #25
    //   2629: invokeinterface list : ()Ljava/util/List;
    //   2634: astore #26
    //   2636: aload #18
    //   2638: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2641: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2644: aload #18
    //   2646: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2649: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2652: aload #26
    //   2654: ifnull -> 2710
    //   2657: aload #26
    //   2659: iconst_0
    //   2660: invokeinterface get : (I)Ljava/lang/Object;
    //   2665: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2668: astore #27
    //   2670: aload #27
    //   2672: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2675: invokevirtual intValue : ()I
    //   2678: iconst_1
    //   2679: if_icmpne -> 2690
    //   2682: aload #18
    //   2684: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2687: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2690: aload #27
    //   2692: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2695: invokevirtual intValue : ()I
    //   2698: iconst_1
    //   2699: if_icmpne -> 2710
    //   2702: aload #18
    //   2704: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2707: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2710: aload #18
    //   2712: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2715: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2718: aload #18
    //   2720: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2723: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2726: aload #18
    //   2728: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2731: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2734: aload #18
    //   2736: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2739: aload_1
    //   2740: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2743: ifeq -> 2765
    //   2746: aload #18
    //   2748: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2751: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2754: aload #18
    //   2756: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2759: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2762: goto -> 2781
    //   2765: aload #18
    //   2767: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2770: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2773: aload #18
    //   2775: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2778: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2781: aload #7
    //   2783: aload #18
    //   2785: invokeinterface add : (Ljava/lang/Object;)Z
    //   2790: pop
    //   2791: aload #17
    //   2793: new java/lang/Integer
    //   2796: dup
    //   2797: aload #17
    //   2799: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   2802: invokevirtual intValue : ()I
    //   2805: iconst_1
    //   2806: iadd
    //   2807: invokespecial <init> : (I)V
    //   2810: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   2813: aload_0
    //   2814: getfield session : Lnet/sf/hibernate/Session;
    //   2817: aload #17
    //   2819: invokeinterface update : (Ljava/lang/Object;)V
    //   2824: goto -> 9380
    //   2827: aload #17
    //   2829: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   2832: invokevirtual intValue : ()I
    //   2835: iconst_3
    //   2836: if_icmpne -> 8479
    //   2839: aload #17
    //   2841: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2844: invokevirtual intValue : ()I
    //   2847: iconst_m1
    //   2848: if_icmpne -> 4625
    //   2851: aload #17
    //   2853: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   2856: aload #9
    //   2858: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2861: ifgt -> 3099
    //   2864: aload #17
    //   2866: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2869: invokestatic parseInt : (Ljava/lang/String;)I
    //   2872: iload #12
    //   2874: if_icmpne -> 3099
    //   2877: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2880: aload #17
    //   2882: ldc com/js/oa/scheme/event/vo/EventVO
    //   2884: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2887: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2890: astore #18
    //   2892: aload_0
    //   2893: getfield session : Lnet/sf/hibernate/Session;
    //   2896: new java/lang/StringBuilder
    //   2899: dup
    //   2900: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2902: invokespecial <init> : (Ljava/lang/String;)V
    //   2905: aload #18
    //   2907: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2910: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2913: ldc ' and eventAttender.empId ='
    //   2915: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2918: aload_1
    //   2919: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2922: invokevirtual toString : ()Ljava/lang/String;
    //   2925: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2930: astore #25
    //   2932: aload #25
    //   2934: invokeinterface list : ()Ljava/util/List;
    //   2939: astore #26
    //   2941: aload #18
    //   2943: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2946: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2949: aload #18
    //   2951: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2954: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2957: aload #26
    //   2959: ifnull -> 3015
    //   2962: aload #26
    //   2964: iconst_0
    //   2965: invokeinterface get : (I)Ljava/lang/Object;
    //   2970: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2973: astore #27
    //   2975: aload #27
    //   2977: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2980: invokevirtual intValue : ()I
    //   2983: iconst_1
    //   2984: if_icmpne -> 2995
    //   2987: aload #18
    //   2989: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2992: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2995: aload #27
    //   2997: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3000: invokevirtual intValue : ()I
    //   3003: iconst_1
    //   3004: if_icmpne -> 3015
    //   3007: aload #18
    //   3009: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3012: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3015: aload #18
    //   3017: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3020: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3023: aload #18
    //   3025: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3028: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3031: aload #18
    //   3033: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3036: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3039: aload #18
    //   3041: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3044: aload_1
    //   3045: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3048: ifeq -> 3070
    //   3051: aload #18
    //   3053: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3056: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3059: aload #18
    //   3061: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3064: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3067: goto -> 3086
    //   3070: aload #18
    //   3072: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3075: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3078: aload #18
    //   3080: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3083: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3086: aload #7
    //   3088: aload #18
    //   3090: invokeinterface add : (Ljava/lang/Object;)Z
    //   3095: pop
    //   3096: goto -> 9380
    //   3099: aload #17
    //   3101: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   3104: aload #9
    //   3106: invokevirtual compareTo : (Ljava/util/Date;)I
    //   3109: ifgt -> 9380
    //   3112: aload #17
    //   3114: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   3117: ldc_w '31'
    //   3120: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3123: ifeq -> 3880
    //   3126: iload #11
    //   3128: tableswitch default -> 3877, 1 -> 3184, 2 -> 3877, 3 -> 3651, 4 -> 3877, 5 -> 3651, 6 -> 3877, 7 -> 3877, 8 -> 3651, 9 -> 3877, 10 -> 3651
    //   3184: aload_0
    //   3185: iload #10
    //   3187: invokespecial isLeapYear : (I)Z
    //   3190: ifeq -> 3422
    //   3193: iload #12
    //   3195: bipush #29
    //   3197: if_icmpne -> 9380
    //   3200: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3203: aload #17
    //   3205: ldc com/js/oa/scheme/event/vo/EventVO
    //   3207: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3210: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3213: astore #18
    //   3215: aload_0
    //   3216: getfield session : Lnet/sf/hibernate/Session;
    //   3219: new java/lang/StringBuilder
    //   3222: dup
    //   3223: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3225: invokespecial <init> : (Ljava/lang/String;)V
    //   3228: aload #18
    //   3230: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3233: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3236: ldc ' and eventAttender.empId ='
    //   3238: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3241: aload_1
    //   3242: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3245: invokevirtual toString : ()Ljava/lang/String;
    //   3248: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3253: astore #25
    //   3255: aload #25
    //   3257: invokeinterface list : ()Ljava/util/List;
    //   3262: astore #26
    //   3264: aload #18
    //   3266: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3269: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3272: aload #18
    //   3274: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3277: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3280: aload #26
    //   3282: ifnull -> 3338
    //   3285: aload #26
    //   3287: iconst_0
    //   3288: invokeinterface get : (I)Ljava/lang/Object;
    //   3293: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3296: astore #27
    //   3298: aload #27
    //   3300: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3303: invokevirtual intValue : ()I
    //   3306: iconst_1
    //   3307: if_icmpne -> 3318
    //   3310: aload #18
    //   3312: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3315: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3318: aload #27
    //   3320: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3323: invokevirtual intValue : ()I
    //   3326: iconst_1
    //   3327: if_icmpne -> 3338
    //   3330: aload #18
    //   3332: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3335: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3338: aload #18
    //   3340: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3343: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3346: aload #18
    //   3348: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3351: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3354: aload #18
    //   3356: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3359: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3362: aload #18
    //   3364: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3367: aload_1
    //   3368: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3371: ifeq -> 3393
    //   3374: aload #18
    //   3376: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3379: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3382: aload #18
    //   3384: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3387: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3390: goto -> 3409
    //   3393: aload #18
    //   3395: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3398: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3401: aload #18
    //   3403: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3406: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3409: aload #7
    //   3411: aload #18
    //   3413: invokeinterface add : (Ljava/lang/Object;)Z
    //   3418: pop
    //   3419: goto -> 9380
    //   3422: iload #12
    //   3424: bipush #28
    //   3426: if_icmpne -> 9380
    //   3429: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3432: aload #17
    //   3434: ldc com/js/oa/scheme/event/vo/EventVO
    //   3436: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3439: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3442: astore #18
    //   3444: aload_0
    //   3445: getfield session : Lnet/sf/hibernate/Session;
    //   3448: new java/lang/StringBuilder
    //   3451: dup
    //   3452: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3454: invokespecial <init> : (Ljava/lang/String;)V
    //   3457: aload #18
    //   3459: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3462: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3465: ldc ' and eventAttender.empId ='
    //   3467: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3470: aload_1
    //   3471: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3474: invokevirtual toString : ()Ljava/lang/String;
    //   3477: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3482: astore #25
    //   3484: aload #25
    //   3486: invokeinterface list : ()Ljava/util/List;
    //   3491: astore #26
    //   3493: aload #18
    //   3495: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3498: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3501: aload #18
    //   3503: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3506: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3509: aload #26
    //   3511: ifnull -> 3567
    //   3514: aload #26
    //   3516: iconst_0
    //   3517: invokeinterface get : (I)Ljava/lang/Object;
    //   3522: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3525: astore #27
    //   3527: aload #27
    //   3529: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3532: invokevirtual intValue : ()I
    //   3535: iconst_1
    //   3536: if_icmpne -> 3547
    //   3539: aload #18
    //   3541: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3544: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3547: aload #27
    //   3549: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3552: invokevirtual intValue : ()I
    //   3555: iconst_1
    //   3556: if_icmpne -> 3567
    //   3559: aload #18
    //   3561: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3564: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3567: aload #18
    //   3569: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3572: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3575: aload #18
    //   3577: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3580: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3583: aload #18
    //   3585: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3588: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3591: aload #18
    //   3593: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3596: aload_1
    //   3597: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3600: ifeq -> 3622
    //   3603: aload #18
    //   3605: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3608: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3611: aload #18
    //   3613: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3616: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3619: goto -> 3638
    //   3622: aload #18
    //   3624: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3627: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3630: aload #18
    //   3632: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3635: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3638: aload #7
    //   3640: aload #18
    //   3642: invokeinterface add : (Ljava/lang/Object;)Z
    //   3647: pop
    //   3648: goto -> 9380
    //   3651: iload #12
    //   3653: bipush #30
    //   3655: if_icmpne -> 9380
    //   3658: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3661: aload #17
    //   3663: ldc com/js/oa/scheme/event/vo/EventVO
    //   3665: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3668: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3671: astore #18
    //   3673: aload_0
    //   3674: getfield session : Lnet/sf/hibernate/Session;
    //   3677: new java/lang/StringBuilder
    //   3680: dup
    //   3681: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3683: invokespecial <init> : (Ljava/lang/String;)V
    //   3686: aload #18
    //   3688: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3691: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3694: ldc ' and eventAttender.empId ='
    //   3696: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3699: aload_1
    //   3700: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3703: invokevirtual toString : ()Ljava/lang/String;
    //   3706: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3711: astore #25
    //   3713: aload #25
    //   3715: invokeinterface list : ()Ljava/util/List;
    //   3720: astore #26
    //   3722: aload #18
    //   3724: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3727: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3730: aload #18
    //   3732: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3735: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3738: aload #26
    //   3740: ifnull -> 3796
    //   3743: aload #26
    //   3745: iconst_0
    //   3746: invokeinterface get : (I)Ljava/lang/Object;
    //   3751: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3754: astore #27
    //   3756: aload #27
    //   3758: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3761: invokevirtual intValue : ()I
    //   3764: iconst_1
    //   3765: if_icmpne -> 3776
    //   3768: aload #18
    //   3770: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3773: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3776: aload #27
    //   3778: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3781: invokevirtual intValue : ()I
    //   3784: iconst_1
    //   3785: if_icmpne -> 3796
    //   3788: aload #18
    //   3790: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3793: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3796: aload #18
    //   3798: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3801: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3804: aload #18
    //   3806: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3809: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3812: aload #18
    //   3814: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3817: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3820: aload #18
    //   3822: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3825: aload_1
    //   3826: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3829: ifeq -> 3851
    //   3832: aload #18
    //   3834: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3837: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3840: aload #18
    //   3842: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3845: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3848: goto -> 3867
    //   3851: aload #18
    //   3853: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3856: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3859: aload #18
    //   3861: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3864: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3867: aload #7
    //   3869: aload #18
    //   3871: invokeinterface add : (Ljava/lang/Object;)Z
    //   3876: pop
    //   3877: goto -> 9380
    //   3880: aload #17
    //   3882: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   3885: ldc_w '30'
    //   3888: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3891: ifeq -> 4367
    //   3894: iload #11
    //   3896: iconst_1
    //   3897: if_icmpne -> 9380
    //   3900: aload_0
    //   3901: iload #10
    //   3903: invokespecial isLeapYear : (I)Z
    //   3906: ifeq -> 4138
    //   3909: iload #12
    //   3911: bipush #29
    //   3913: if_icmpne -> 9380
    //   3916: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3919: aload #17
    //   3921: ldc com/js/oa/scheme/event/vo/EventVO
    //   3923: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3926: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3929: astore #18
    //   3931: aload_0
    //   3932: getfield session : Lnet/sf/hibernate/Session;
    //   3935: new java/lang/StringBuilder
    //   3938: dup
    //   3939: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3941: invokespecial <init> : (Ljava/lang/String;)V
    //   3944: aload #18
    //   3946: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3949: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3952: ldc ' and eventAttender.empId ='
    //   3954: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3957: aload_1
    //   3958: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3961: invokevirtual toString : ()Ljava/lang/String;
    //   3964: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3969: astore #25
    //   3971: aload #25
    //   3973: invokeinterface list : ()Ljava/util/List;
    //   3978: astore #26
    //   3980: aload #18
    //   3982: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3985: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3988: aload #18
    //   3990: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3993: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3996: aload #26
    //   3998: ifnull -> 4054
    //   4001: aload #26
    //   4003: iconst_0
    //   4004: invokeinterface get : (I)Ljava/lang/Object;
    //   4009: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4012: astore #27
    //   4014: aload #27
    //   4016: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4019: invokevirtual intValue : ()I
    //   4022: iconst_1
    //   4023: if_icmpne -> 4034
    //   4026: aload #18
    //   4028: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4031: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4034: aload #27
    //   4036: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4039: invokevirtual intValue : ()I
    //   4042: iconst_1
    //   4043: if_icmpne -> 4054
    //   4046: aload #18
    //   4048: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4051: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4054: aload #18
    //   4056: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4059: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4062: aload #18
    //   4064: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4067: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4070: aload #18
    //   4072: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4075: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4078: aload #18
    //   4080: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4083: aload_1
    //   4084: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4087: ifeq -> 4109
    //   4090: aload #18
    //   4092: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4095: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4098: aload #18
    //   4100: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4103: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4106: goto -> 4125
    //   4109: aload #18
    //   4111: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4114: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4117: aload #18
    //   4119: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4122: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4125: aload #7
    //   4127: aload #18
    //   4129: invokeinterface add : (Ljava/lang/Object;)Z
    //   4134: pop
    //   4135: goto -> 9380
    //   4138: iload #12
    //   4140: bipush #28
    //   4142: if_icmpne -> 9380
    //   4145: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4148: aload #17
    //   4150: ldc com/js/oa/scheme/event/vo/EventVO
    //   4152: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4155: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4158: astore #18
    //   4160: aload_0
    //   4161: getfield session : Lnet/sf/hibernate/Session;
    //   4164: new java/lang/StringBuilder
    //   4167: dup
    //   4168: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4170: invokespecial <init> : (Ljava/lang/String;)V
    //   4173: aload #18
    //   4175: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4178: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4181: ldc ' and eventAttender.empId ='
    //   4183: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4186: aload_1
    //   4187: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4190: invokevirtual toString : ()Ljava/lang/String;
    //   4193: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4198: astore #25
    //   4200: aload #25
    //   4202: invokeinterface list : ()Ljava/util/List;
    //   4207: astore #26
    //   4209: aload #18
    //   4211: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4214: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4217: aload #18
    //   4219: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4222: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4225: aload #26
    //   4227: ifnull -> 4283
    //   4230: aload #26
    //   4232: iconst_0
    //   4233: invokeinterface get : (I)Ljava/lang/Object;
    //   4238: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4241: astore #27
    //   4243: aload #27
    //   4245: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4248: invokevirtual intValue : ()I
    //   4251: iconst_1
    //   4252: if_icmpne -> 4263
    //   4255: aload #18
    //   4257: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4260: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4263: aload #27
    //   4265: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4268: invokevirtual intValue : ()I
    //   4271: iconst_1
    //   4272: if_icmpne -> 4283
    //   4275: aload #18
    //   4277: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4280: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4283: aload #18
    //   4285: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4288: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4291: aload #18
    //   4293: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4296: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4299: aload #18
    //   4301: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4304: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4307: aload #18
    //   4309: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4312: aload_1
    //   4313: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4316: ifeq -> 4338
    //   4319: aload #18
    //   4321: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4324: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4327: aload #18
    //   4329: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4332: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4335: goto -> 4354
    //   4338: aload #18
    //   4340: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4343: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4346: aload #18
    //   4348: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4351: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4354: aload #7
    //   4356: aload #18
    //   4358: invokeinterface add : (Ljava/lang/Object;)Z
    //   4363: pop
    //   4364: goto -> 9380
    //   4367: aload #17
    //   4369: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   4372: ldc_w '29'
    //   4375: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4378: ifeq -> 9380
    //   4381: iload #11
    //   4383: iconst_1
    //   4384: if_icmpne -> 9380
    //   4387: aload_0
    //   4388: iload #10
    //   4390: invokespecial isLeapYear : (I)Z
    //   4393: ifne -> 9380
    //   4396: iload #12
    //   4398: bipush #28
    //   4400: if_icmpne -> 9380
    //   4403: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4406: aload #17
    //   4408: ldc com/js/oa/scheme/event/vo/EventVO
    //   4410: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4413: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4416: astore #18
    //   4418: aload_0
    //   4419: getfield session : Lnet/sf/hibernate/Session;
    //   4422: new java/lang/StringBuilder
    //   4425: dup
    //   4426: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4428: invokespecial <init> : (Ljava/lang/String;)V
    //   4431: aload #18
    //   4433: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4436: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4439: ldc ' and eventAttender.empId ='
    //   4441: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4444: aload_1
    //   4445: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4448: invokevirtual toString : ()Ljava/lang/String;
    //   4451: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4456: astore #25
    //   4458: aload #25
    //   4460: invokeinterface list : ()Ljava/util/List;
    //   4465: astore #26
    //   4467: aload #18
    //   4469: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4472: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4475: aload #18
    //   4477: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4480: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4483: aload #26
    //   4485: ifnull -> 4541
    //   4488: aload #26
    //   4490: iconst_0
    //   4491: invokeinterface get : (I)Ljava/lang/Object;
    //   4496: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4499: astore #27
    //   4501: aload #27
    //   4503: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4506: invokevirtual intValue : ()I
    //   4509: iconst_1
    //   4510: if_icmpne -> 4521
    //   4513: aload #18
    //   4515: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4518: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4521: aload #27
    //   4523: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4526: invokevirtual intValue : ()I
    //   4529: iconst_1
    //   4530: if_icmpne -> 4541
    //   4533: aload #18
    //   4535: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4538: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4541: aload #18
    //   4543: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4546: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4549: aload #18
    //   4551: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4554: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4557: aload #18
    //   4559: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4562: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4565: aload #18
    //   4567: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4570: aload_1
    //   4571: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4574: ifeq -> 4596
    //   4577: aload #18
    //   4579: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4582: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4585: aload #18
    //   4587: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4590: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4593: goto -> 4612
    //   4596: aload #18
    //   4598: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4601: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4604: aload #18
    //   4606: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4609: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4612: aload #7
    //   4614: aload #18
    //   4616: invokeinterface add : (Ljava/lang/Object;)Z
    //   4621: pop
    //   4622: goto -> 9380
    //   4625: aload #17
    //   4627: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   4630: invokevirtual intValue : ()I
    //   4633: ifne -> 6433
    //   4636: aload #17
    //   4638: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   4641: aload #9
    //   4643: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4646: ifgt -> 4897
    //   4649: aload #17
    //   4651: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   4654: invokestatic parseInt : (Ljava/lang/String;)I
    //   4657: iload #12
    //   4659: if_icmpne -> 4897
    //   4662: aload #17
    //   4664: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   4667: aload #9
    //   4669: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4672: iflt -> 4897
    //   4675: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4678: aload #17
    //   4680: ldc com/js/oa/scheme/event/vo/EventVO
    //   4682: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4685: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4688: astore #18
    //   4690: aload_0
    //   4691: getfield session : Lnet/sf/hibernate/Session;
    //   4694: new java/lang/StringBuilder
    //   4697: dup
    //   4698: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4700: invokespecial <init> : (Ljava/lang/String;)V
    //   4703: aload #18
    //   4705: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4708: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4711: ldc ' and eventAttender.empId ='
    //   4713: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4716: aload_1
    //   4717: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4720: invokevirtual toString : ()Ljava/lang/String;
    //   4723: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4728: astore #25
    //   4730: aload #25
    //   4732: invokeinterface list : ()Ljava/util/List;
    //   4737: astore #26
    //   4739: aload #18
    //   4741: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4744: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4747: aload #18
    //   4749: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4752: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4755: aload #26
    //   4757: ifnull -> 4813
    //   4760: aload #26
    //   4762: iconst_0
    //   4763: invokeinterface get : (I)Ljava/lang/Object;
    //   4768: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4771: astore #27
    //   4773: aload #27
    //   4775: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4778: invokevirtual intValue : ()I
    //   4781: iconst_1
    //   4782: if_icmpne -> 4793
    //   4785: aload #18
    //   4787: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4790: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4793: aload #27
    //   4795: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4798: invokevirtual intValue : ()I
    //   4801: iconst_1
    //   4802: if_icmpne -> 4813
    //   4805: aload #18
    //   4807: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4810: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4813: aload #18
    //   4815: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4818: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4821: aload #18
    //   4823: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4826: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4829: aload #18
    //   4831: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4834: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4837: aload #18
    //   4839: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4842: aload_1
    //   4843: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4846: ifeq -> 4868
    //   4849: aload #18
    //   4851: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4854: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4857: aload #18
    //   4859: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4862: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4865: goto -> 4884
    //   4868: aload #18
    //   4870: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4873: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4876: aload #18
    //   4878: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4881: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4884: aload #7
    //   4886: aload #18
    //   4888: invokeinterface add : (Ljava/lang/Object;)Z
    //   4893: pop
    //   4894: goto -> 9380
    //   4897: aload #17
    //   4899: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   4902: aload #9
    //   4904: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4907: ifgt -> 9380
    //   4910: aload #17
    //   4912: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   4915: aload #9
    //   4917: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4920: iflt -> 9380
    //   4923: aload #17
    //   4925: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   4928: ldc_w '31'
    //   4931: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4934: ifeq -> 5688
    //   4937: iload #11
    //   4939: tableswitch default -> 5685, 1 -> 4992, 2 -> 5685, 3 -> 5459, 4 -> 5685, 5 -> 5459, 6 -> 5685, 7 -> 5685, 8 -> 5459, 9 -> 5685, 10 -> 5459
    //   4992: aload_0
    //   4993: iload #10
    //   4995: invokespecial isLeapYear : (I)Z
    //   4998: ifeq -> 5230
    //   5001: iload #12
    //   5003: bipush #29
    //   5005: if_icmpne -> 9380
    //   5008: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5011: aload #17
    //   5013: ldc com/js/oa/scheme/event/vo/EventVO
    //   5015: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5018: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5021: astore #18
    //   5023: aload_0
    //   5024: getfield session : Lnet/sf/hibernate/Session;
    //   5027: new java/lang/StringBuilder
    //   5030: dup
    //   5031: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5033: invokespecial <init> : (Ljava/lang/String;)V
    //   5036: aload #18
    //   5038: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5041: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5044: ldc ' and eventAttender.empId ='
    //   5046: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5049: aload_1
    //   5050: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5053: invokevirtual toString : ()Ljava/lang/String;
    //   5056: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5061: astore #25
    //   5063: aload #25
    //   5065: invokeinterface list : ()Ljava/util/List;
    //   5070: astore #26
    //   5072: aload #18
    //   5074: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5077: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5080: aload #18
    //   5082: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5085: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5088: aload #26
    //   5090: ifnull -> 5146
    //   5093: aload #26
    //   5095: iconst_0
    //   5096: invokeinterface get : (I)Ljava/lang/Object;
    //   5101: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5104: astore #27
    //   5106: aload #27
    //   5108: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5111: invokevirtual intValue : ()I
    //   5114: iconst_1
    //   5115: if_icmpne -> 5126
    //   5118: aload #18
    //   5120: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5123: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5126: aload #27
    //   5128: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5131: invokevirtual intValue : ()I
    //   5134: iconst_1
    //   5135: if_icmpne -> 5146
    //   5138: aload #18
    //   5140: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5143: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5146: aload #18
    //   5148: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5151: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5154: aload #18
    //   5156: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5159: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5162: aload #18
    //   5164: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5167: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5170: aload #18
    //   5172: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5175: aload_1
    //   5176: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5179: ifeq -> 5201
    //   5182: aload #18
    //   5184: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5187: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5190: aload #18
    //   5192: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5195: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5198: goto -> 5217
    //   5201: aload #18
    //   5203: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5206: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5209: aload #18
    //   5211: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5214: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5217: aload #7
    //   5219: aload #18
    //   5221: invokeinterface add : (Ljava/lang/Object;)Z
    //   5226: pop
    //   5227: goto -> 9380
    //   5230: iload #12
    //   5232: bipush #28
    //   5234: if_icmpne -> 9380
    //   5237: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5240: aload #17
    //   5242: ldc com/js/oa/scheme/event/vo/EventVO
    //   5244: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5247: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5250: astore #18
    //   5252: aload_0
    //   5253: getfield session : Lnet/sf/hibernate/Session;
    //   5256: new java/lang/StringBuilder
    //   5259: dup
    //   5260: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5262: invokespecial <init> : (Ljava/lang/String;)V
    //   5265: aload #18
    //   5267: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5270: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5273: ldc ' and eventAttender.empId ='
    //   5275: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5278: aload_1
    //   5279: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5282: invokevirtual toString : ()Ljava/lang/String;
    //   5285: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5290: astore #25
    //   5292: aload #25
    //   5294: invokeinterface list : ()Ljava/util/List;
    //   5299: astore #26
    //   5301: aload #18
    //   5303: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5306: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5309: aload #18
    //   5311: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5314: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5317: aload #26
    //   5319: ifnull -> 5375
    //   5322: aload #26
    //   5324: iconst_0
    //   5325: invokeinterface get : (I)Ljava/lang/Object;
    //   5330: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5333: astore #27
    //   5335: aload #27
    //   5337: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5340: invokevirtual intValue : ()I
    //   5343: iconst_1
    //   5344: if_icmpne -> 5355
    //   5347: aload #18
    //   5349: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5352: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5355: aload #27
    //   5357: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5360: invokevirtual intValue : ()I
    //   5363: iconst_1
    //   5364: if_icmpne -> 5375
    //   5367: aload #18
    //   5369: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5372: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5375: aload #18
    //   5377: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5380: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5383: aload #18
    //   5385: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5388: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5391: aload #18
    //   5393: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5396: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5399: aload #18
    //   5401: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5404: aload_1
    //   5405: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5408: ifeq -> 5430
    //   5411: aload #18
    //   5413: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5416: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5419: aload #18
    //   5421: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5424: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5427: goto -> 5446
    //   5430: aload #18
    //   5432: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5435: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5438: aload #18
    //   5440: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5443: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5446: aload #7
    //   5448: aload #18
    //   5450: invokeinterface add : (Ljava/lang/Object;)Z
    //   5455: pop
    //   5456: goto -> 9380
    //   5459: iload #12
    //   5461: bipush #30
    //   5463: if_icmpne -> 9380
    //   5466: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5469: aload #17
    //   5471: ldc com/js/oa/scheme/event/vo/EventVO
    //   5473: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5476: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5479: astore #18
    //   5481: aload_0
    //   5482: getfield session : Lnet/sf/hibernate/Session;
    //   5485: new java/lang/StringBuilder
    //   5488: dup
    //   5489: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5491: invokespecial <init> : (Ljava/lang/String;)V
    //   5494: aload #18
    //   5496: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5499: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5502: ldc ' and eventAttender.empId ='
    //   5504: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5507: aload_1
    //   5508: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5511: invokevirtual toString : ()Ljava/lang/String;
    //   5514: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5519: astore #25
    //   5521: aload #25
    //   5523: invokeinterface list : ()Ljava/util/List;
    //   5528: astore #26
    //   5530: aload #18
    //   5532: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5535: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5538: aload #18
    //   5540: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5543: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5546: aload #26
    //   5548: ifnull -> 5604
    //   5551: aload #26
    //   5553: iconst_0
    //   5554: invokeinterface get : (I)Ljava/lang/Object;
    //   5559: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5562: astore #27
    //   5564: aload #27
    //   5566: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5569: invokevirtual intValue : ()I
    //   5572: iconst_1
    //   5573: if_icmpne -> 5584
    //   5576: aload #18
    //   5578: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5581: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5584: aload #27
    //   5586: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5589: invokevirtual intValue : ()I
    //   5592: iconst_1
    //   5593: if_icmpne -> 5604
    //   5596: aload #18
    //   5598: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5601: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5604: aload #18
    //   5606: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5609: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5612: aload #18
    //   5614: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5617: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5620: aload #18
    //   5622: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5625: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5628: aload #18
    //   5630: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5633: aload_1
    //   5634: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5637: ifeq -> 5659
    //   5640: aload #18
    //   5642: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5645: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5648: aload #18
    //   5650: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5653: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5656: goto -> 5675
    //   5659: aload #18
    //   5661: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5664: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5667: aload #18
    //   5669: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5672: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5675: aload #7
    //   5677: aload #18
    //   5679: invokeinterface add : (Ljava/lang/Object;)Z
    //   5684: pop
    //   5685: goto -> 9380
    //   5688: aload #17
    //   5690: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   5693: ldc_w '30'
    //   5696: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5699: ifeq -> 6175
    //   5702: iload #11
    //   5704: iconst_1
    //   5705: if_icmpne -> 9380
    //   5708: aload_0
    //   5709: iload #10
    //   5711: invokespecial isLeapYear : (I)Z
    //   5714: ifeq -> 5946
    //   5717: iload #12
    //   5719: bipush #29
    //   5721: if_icmpne -> 9380
    //   5724: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5727: aload #17
    //   5729: ldc com/js/oa/scheme/event/vo/EventVO
    //   5731: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5734: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5737: astore #18
    //   5739: aload_0
    //   5740: getfield session : Lnet/sf/hibernate/Session;
    //   5743: new java/lang/StringBuilder
    //   5746: dup
    //   5747: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5749: invokespecial <init> : (Ljava/lang/String;)V
    //   5752: aload #18
    //   5754: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5757: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5760: ldc ' and eventAttender.empId ='
    //   5762: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5765: aload_1
    //   5766: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5769: invokevirtual toString : ()Ljava/lang/String;
    //   5772: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5777: astore #25
    //   5779: aload #25
    //   5781: invokeinterface list : ()Ljava/util/List;
    //   5786: astore #26
    //   5788: aload #18
    //   5790: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5793: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5796: aload #18
    //   5798: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5801: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5804: aload #26
    //   5806: ifnull -> 5862
    //   5809: aload #26
    //   5811: iconst_0
    //   5812: invokeinterface get : (I)Ljava/lang/Object;
    //   5817: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5820: astore #27
    //   5822: aload #27
    //   5824: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5827: invokevirtual intValue : ()I
    //   5830: iconst_1
    //   5831: if_icmpne -> 5842
    //   5834: aload #18
    //   5836: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5839: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5842: aload #27
    //   5844: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5847: invokevirtual intValue : ()I
    //   5850: iconst_1
    //   5851: if_icmpne -> 5862
    //   5854: aload #18
    //   5856: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5859: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5862: aload #18
    //   5864: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5867: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5870: aload #18
    //   5872: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5875: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5878: aload #18
    //   5880: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5883: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5886: aload #18
    //   5888: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5891: aload_1
    //   5892: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5895: ifeq -> 5917
    //   5898: aload #18
    //   5900: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5903: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5906: aload #18
    //   5908: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5911: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5914: goto -> 5933
    //   5917: aload #18
    //   5919: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5922: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5925: aload #18
    //   5927: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5930: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5933: aload #7
    //   5935: aload #18
    //   5937: invokeinterface add : (Ljava/lang/Object;)Z
    //   5942: pop
    //   5943: goto -> 9380
    //   5946: iload #12
    //   5948: bipush #28
    //   5950: if_icmpne -> 9380
    //   5953: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5956: aload #17
    //   5958: ldc com/js/oa/scheme/event/vo/EventVO
    //   5960: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5963: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5966: astore #18
    //   5968: aload_0
    //   5969: getfield session : Lnet/sf/hibernate/Session;
    //   5972: new java/lang/StringBuilder
    //   5975: dup
    //   5976: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5978: invokespecial <init> : (Ljava/lang/String;)V
    //   5981: aload #18
    //   5983: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5986: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5989: ldc ' and eventAttender.empId ='
    //   5991: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5994: aload_1
    //   5995: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5998: invokevirtual toString : ()Ljava/lang/String;
    //   6001: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6006: astore #25
    //   6008: aload #25
    //   6010: invokeinterface list : ()Ljava/util/List;
    //   6015: astore #26
    //   6017: aload #18
    //   6019: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6022: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6025: aload #18
    //   6027: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6030: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6033: aload #26
    //   6035: ifnull -> 6091
    //   6038: aload #26
    //   6040: iconst_0
    //   6041: invokeinterface get : (I)Ljava/lang/Object;
    //   6046: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6049: astore #27
    //   6051: aload #27
    //   6053: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6056: invokevirtual intValue : ()I
    //   6059: iconst_1
    //   6060: if_icmpne -> 6071
    //   6063: aload #18
    //   6065: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6068: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6071: aload #27
    //   6073: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6076: invokevirtual intValue : ()I
    //   6079: iconst_1
    //   6080: if_icmpne -> 6091
    //   6083: aload #18
    //   6085: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6088: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6091: aload #18
    //   6093: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6096: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6099: aload #18
    //   6101: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6104: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6107: aload #18
    //   6109: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6112: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6115: aload #18
    //   6117: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   6120: aload_1
    //   6121: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6124: ifeq -> 6146
    //   6127: aload #18
    //   6129: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6132: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6135: aload #18
    //   6137: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6140: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6143: goto -> 6162
    //   6146: aload #18
    //   6148: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6151: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6154: aload #18
    //   6156: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6159: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6162: aload #7
    //   6164: aload #18
    //   6166: invokeinterface add : (Ljava/lang/Object;)Z
    //   6171: pop
    //   6172: goto -> 9380
    //   6175: aload #17
    //   6177: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   6180: ldc_w '29'
    //   6183: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6186: ifeq -> 9380
    //   6189: iload #11
    //   6191: iconst_1
    //   6192: if_icmpne -> 9380
    //   6195: aload_0
    //   6196: iload #10
    //   6198: invokespecial isLeapYear : (I)Z
    //   6201: ifne -> 9380
    //   6204: iload #12
    //   6206: bipush #28
    //   6208: if_icmpne -> 9380
    //   6211: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6214: aload #17
    //   6216: ldc com/js/oa/scheme/event/vo/EventVO
    //   6218: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6221: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6224: astore #18
    //   6226: aload_0
    //   6227: getfield session : Lnet/sf/hibernate/Session;
    //   6230: new java/lang/StringBuilder
    //   6233: dup
    //   6234: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6236: invokespecial <init> : (Ljava/lang/String;)V
    //   6239: aload #18
    //   6241: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6244: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6247: ldc ' and eventAttender.empId ='
    //   6249: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6252: aload_1
    //   6253: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6256: invokevirtual toString : ()Ljava/lang/String;
    //   6259: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6264: astore #25
    //   6266: aload #25
    //   6268: invokeinterface list : ()Ljava/util/List;
    //   6273: astore #26
    //   6275: aload #18
    //   6277: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6280: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6283: aload #18
    //   6285: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6288: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6291: aload #26
    //   6293: ifnull -> 6349
    //   6296: aload #26
    //   6298: iconst_0
    //   6299: invokeinterface get : (I)Ljava/lang/Object;
    //   6304: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6307: astore #27
    //   6309: aload #27
    //   6311: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6314: invokevirtual intValue : ()I
    //   6317: iconst_1
    //   6318: if_icmpne -> 6329
    //   6321: aload #18
    //   6323: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6326: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6329: aload #27
    //   6331: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6334: invokevirtual intValue : ()I
    //   6337: iconst_1
    //   6338: if_icmpne -> 6349
    //   6341: aload #18
    //   6343: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6346: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6349: aload #18
    //   6351: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6354: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6357: aload #18
    //   6359: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6362: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6365: aload #18
    //   6367: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6370: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6373: aload #18
    //   6375: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   6378: aload_1
    //   6379: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6382: ifeq -> 6404
    //   6385: aload #18
    //   6387: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6390: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6393: aload #18
    //   6395: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6398: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6401: goto -> 6420
    //   6404: aload #18
    //   6406: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6409: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6412: aload #18
    //   6414: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6417: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6420: aload #7
    //   6422: aload #18
    //   6424: invokeinterface add : (Ljava/lang/Object;)Z
    //   6429: pop
    //   6430: goto -> 9380
    //   6433: aload #17
    //   6435: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   6438: invokevirtual intValue : ()I
    //   6441: ifle -> 9380
    //   6444: aload #17
    //   6446: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   6449: aload #9
    //   6451: invokevirtual compareTo : (Ljava/util/Date;)I
    //   6454: ifgt -> 6741
    //   6457: aload #17
    //   6459: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   6462: invokestatic parseInt : (Ljava/lang/String;)I
    //   6465: iload #12
    //   6467: if_icmpne -> 6741
    //   6470: aload #17
    //   6472: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   6475: aload #17
    //   6477: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6480: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   6483: ifle -> 6741
    //   6486: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6489: aload #17
    //   6491: ldc com/js/oa/scheme/event/vo/EventVO
    //   6493: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6496: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6499: astore #18
    //   6501: aload_0
    //   6502: getfield session : Lnet/sf/hibernate/Session;
    //   6505: new java/lang/StringBuilder
    //   6508: dup
    //   6509: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6511: invokespecial <init> : (Ljava/lang/String;)V
    //   6514: aload #18
    //   6516: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6519: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6522: ldc ' and eventAttender.empId ='
    //   6524: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6527: aload_1
    //   6528: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6531: invokevirtual toString : ()Ljava/lang/String;
    //   6534: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6539: astore #25
    //   6541: aload #25
    //   6543: invokeinterface list : ()Ljava/util/List;
    //   6548: astore #26
    //   6550: aload #18
    //   6552: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6555: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6558: aload #18
    //   6560: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6563: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6566: aload #26
    //   6568: ifnull -> 6624
    //   6571: aload #26
    //   6573: iconst_0
    //   6574: invokeinterface get : (I)Ljava/lang/Object;
    //   6579: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6582: astore #27
    //   6584: aload #27
    //   6586: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6589: invokevirtual intValue : ()I
    //   6592: iconst_1
    //   6593: if_icmpne -> 6604
    //   6596: aload #18
    //   6598: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6601: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6604: aload #27
    //   6606: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6609: invokevirtual intValue : ()I
    //   6612: iconst_1
    //   6613: if_icmpne -> 6624
    //   6616: aload #18
    //   6618: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6621: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6624: aload #18
    //   6626: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6629: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6632: aload #18
    //   6634: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6637: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6640: aload #18
    //   6642: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6645: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6648: aload #18
    //   6650: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   6653: aload_1
    //   6654: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6657: ifeq -> 6679
    //   6660: aload #18
    //   6662: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6665: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6668: aload #18
    //   6670: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6673: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6676: goto -> 6695
    //   6679: aload #18
    //   6681: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6684: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6687: aload #18
    //   6689: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6692: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6695: aload #7
    //   6697: aload #18
    //   6699: invokeinterface add : (Ljava/lang/Object;)Z
    //   6704: pop
    //   6705: aload #17
    //   6707: new java/lang/Integer
    //   6710: dup
    //   6711: aload #17
    //   6713: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6716: invokevirtual intValue : ()I
    //   6719: iconst_1
    //   6720: iadd
    //   6721: invokespecial <init> : (I)V
    //   6724: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   6727: aload_0
    //   6728: getfield session : Lnet/sf/hibernate/Session;
    //   6731: aload #17
    //   6733: invokeinterface update : (Ljava/lang/Object;)V
    //   6738: goto -> 9380
    //   6741: aload #17
    //   6743: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   6746: aload #9
    //   6748: invokevirtual compareTo : (Ljava/util/Date;)I
    //   6751: ifgt -> 9380
    //   6754: aload #17
    //   6756: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   6759: aload #17
    //   6761: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6764: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   6767: ifle -> 9380
    //   6770: aload #17
    //   6772: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   6775: ldc_w '31'
    //   6778: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6781: ifeq -> 7635
    //   6784: iload #11
    //   6786: tableswitch default -> 7632, 1 -> 6840, 2 -> 7632, 3 -> 7373, 4 -> 7632, 5 -> 7373, 6 -> 7632, 7 -> 7632, 8 -> 7373, 9 -> 7632, 10 -> 7373
    //   6840: aload_0
    //   6841: iload #10
    //   6843: invokespecial isLeapYear : (I)Z
    //   6846: ifeq -> 7111
    //   6849: iload #12
    //   6851: bipush #29
    //   6853: if_icmpne -> 9380
    //   6856: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6859: aload #17
    //   6861: ldc com/js/oa/scheme/event/vo/EventVO
    //   6863: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6866: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6869: astore #18
    //   6871: aload_0
    //   6872: getfield session : Lnet/sf/hibernate/Session;
    //   6875: new java/lang/StringBuilder
    //   6878: dup
    //   6879: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6881: invokespecial <init> : (Ljava/lang/String;)V
    //   6884: aload #18
    //   6886: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6889: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6892: ldc ' and eventAttender.empId ='
    //   6894: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6897: aload_1
    //   6898: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6901: invokevirtual toString : ()Ljava/lang/String;
    //   6904: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6909: astore #25
    //   6911: aload #25
    //   6913: invokeinterface list : ()Ljava/util/List;
    //   6918: astore #26
    //   6920: aload #18
    //   6922: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6925: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6928: aload #18
    //   6930: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6933: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6936: aload #26
    //   6938: ifnull -> 6994
    //   6941: aload #26
    //   6943: iconst_0
    //   6944: invokeinterface get : (I)Ljava/lang/Object;
    //   6949: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6952: astore #27
    //   6954: aload #27
    //   6956: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6959: invokevirtual intValue : ()I
    //   6962: iconst_1
    //   6963: if_icmpne -> 6974
    //   6966: aload #18
    //   6968: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6971: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6974: aload #27
    //   6976: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6979: invokevirtual intValue : ()I
    //   6982: iconst_1
    //   6983: if_icmpne -> 6994
    //   6986: aload #18
    //   6988: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6991: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6994: aload #18
    //   6996: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6999: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7002: aload #18
    //   7004: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7007: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7010: aload #18
    //   7012: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7015: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7018: aload #18
    //   7020: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7023: aload_1
    //   7024: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7027: ifeq -> 7049
    //   7030: aload #18
    //   7032: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7035: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7038: aload #18
    //   7040: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7043: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7046: goto -> 7065
    //   7049: aload #18
    //   7051: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7054: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7057: aload #18
    //   7059: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7062: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7065: aload #7
    //   7067: aload #18
    //   7069: invokeinterface add : (Ljava/lang/Object;)Z
    //   7074: pop
    //   7075: aload #17
    //   7077: new java/lang/Integer
    //   7080: dup
    //   7081: aload #17
    //   7083: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7086: invokevirtual intValue : ()I
    //   7089: iconst_1
    //   7090: iadd
    //   7091: invokespecial <init> : (I)V
    //   7094: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7097: aload_0
    //   7098: getfield session : Lnet/sf/hibernate/Session;
    //   7101: aload #17
    //   7103: invokeinterface update : (Ljava/lang/Object;)V
    //   7108: goto -> 9380
    //   7111: iload #12
    //   7113: bipush #28
    //   7115: if_icmpne -> 9380
    //   7118: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7121: aload #17
    //   7123: ldc com/js/oa/scheme/event/vo/EventVO
    //   7125: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7128: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7131: astore #18
    //   7133: aload_0
    //   7134: getfield session : Lnet/sf/hibernate/Session;
    //   7137: new java/lang/StringBuilder
    //   7140: dup
    //   7141: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7143: invokespecial <init> : (Ljava/lang/String;)V
    //   7146: aload #18
    //   7148: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7151: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7154: ldc ' and eventAttender.empId ='
    //   7156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7159: aload_1
    //   7160: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7163: invokevirtual toString : ()Ljava/lang/String;
    //   7166: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7171: astore #25
    //   7173: aload #25
    //   7175: invokeinterface list : ()Ljava/util/List;
    //   7180: astore #26
    //   7182: aload #18
    //   7184: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7187: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7190: aload #18
    //   7192: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7195: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7198: aload #26
    //   7200: ifnull -> 7256
    //   7203: aload #26
    //   7205: iconst_0
    //   7206: invokeinterface get : (I)Ljava/lang/Object;
    //   7211: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7214: astore #27
    //   7216: aload #27
    //   7218: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7221: invokevirtual intValue : ()I
    //   7224: iconst_1
    //   7225: if_icmpne -> 7236
    //   7228: aload #18
    //   7230: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7233: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7236: aload #27
    //   7238: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7241: invokevirtual intValue : ()I
    //   7244: iconst_1
    //   7245: if_icmpne -> 7256
    //   7248: aload #18
    //   7250: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7253: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7256: aload #18
    //   7258: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7261: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7264: aload #18
    //   7266: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7269: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7272: aload #18
    //   7274: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7277: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7280: aload #18
    //   7282: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7285: aload_1
    //   7286: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7289: ifeq -> 7311
    //   7292: aload #18
    //   7294: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7297: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7300: aload #18
    //   7302: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7305: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7308: goto -> 7327
    //   7311: aload #18
    //   7313: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7316: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7319: aload #18
    //   7321: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7324: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7327: aload #7
    //   7329: aload #18
    //   7331: invokeinterface add : (Ljava/lang/Object;)Z
    //   7336: pop
    //   7337: aload #17
    //   7339: new java/lang/Integer
    //   7342: dup
    //   7343: aload #17
    //   7345: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7348: invokevirtual intValue : ()I
    //   7351: iconst_1
    //   7352: iadd
    //   7353: invokespecial <init> : (I)V
    //   7356: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7359: aload_0
    //   7360: getfield session : Lnet/sf/hibernate/Session;
    //   7363: aload #17
    //   7365: invokeinterface update : (Ljava/lang/Object;)V
    //   7370: goto -> 9380
    //   7373: iload #12
    //   7375: bipush #30
    //   7377: if_icmpne -> 9380
    //   7380: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7383: aload #17
    //   7385: ldc com/js/oa/scheme/event/vo/EventVO
    //   7387: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7390: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7393: astore #18
    //   7395: aload_0
    //   7396: getfield session : Lnet/sf/hibernate/Session;
    //   7399: new java/lang/StringBuilder
    //   7402: dup
    //   7403: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7405: invokespecial <init> : (Ljava/lang/String;)V
    //   7408: aload #18
    //   7410: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7413: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7416: ldc ' and eventAttender.empId ='
    //   7418: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7421: aload_1
    //   7422: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7425: invokevirtual toString : ()Ljava/lang/String;
    //   7428: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7433: astore #25
    //   7435: aload #25
    //   7437: invokeinterface list : ()Ljava/util/List;
    //   7442: astore #26
    //   7444: aload #18
    //   7446: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7449: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7452: aload #18
    //   7454: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7457: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7460: aload #26
    //   7462: ifnull -> 7518
    //   7465: aload #26
    //   7467: iconst_0
    //   7468: invokeinterface get : (I)Ljava/lang/Object;
    //   7473: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7476: astore #27
    //   7478: aload #27
    //   7480: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7483: invokevirtual intValue : ()I
    //   7486: iconst_1
    //   7487: if_icmpne -> 7498
    //   7490: aload #18
    //   7492: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7495: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7498: aload #27
    //   7500: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7503: invokevirtual intValue : ()I
    //   7506: iconst_1
    //   7507: if_icmpne -> 7518
    //   7510: aload #18
    //   7512: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7515: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7518: aload #18
    //   7520: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7523: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7526: aload #18
    //   7528: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7531: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7534: aload #18
    //   7536: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7539: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7542: aload #18
    //   7544: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7547: aload_1
    //   7548: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7551: ifeq -> 7573
    //   7554: aload #18
    //   7556: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7559: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7562: aload #18
    //   7564: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7567: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7570: goto -> 7589
    //   7573: aload #18
    //   7575: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7578: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7581: aload #18
    //   7583: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7586: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7589: aload #7
    //   7591: aload #18
    //   7593: invokeinterface add : (Ljava/lang/Object;)Z
    //   7598: pop
    //   7599: aload #17
    //   7601: new java/lang/Integer
    //   7604: dup
    //   7605: aload #17
    //   7607: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7610: invokevirtual intValue : ()I
    //   7613: iconst_1
    //   7614: iadd
    //   7615: invokespecial <init> : (I)V
    //   7618: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7621: aload_0
    //   7622: getfield session : Lnet/sf/hibernate/Session;
    //   7625: aload #17
    //   7627: invokeinterface update : (Ljava/lang/Object;)V
    //   7632: goto -> 9380
    //   7635: aload #17
    //   7637: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   7640: ldc_w '30'
    //   7643: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7646: ifeq -> 8188
    //   7649: iload #11
    //   7651: iconst_1
    //   7652: if_icmpne -> 9380
    //   7655: aload_0
    //   7656: iload #10
    //   7658: invokespecial isLeapYear : (I)Z
    //   7661: ifeq -> 7926
    //   7664: iload #12
    //   7666: bipush #29
    //   7668: if_icmpne -> 9380
    //   7671: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7674: aload #17
    //   7676: ldc com/js/oa/scheme/event/vo/EventVO
    //   7678: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7681: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7684: astore #18
    //   7686: aload_0
    //   7687: getfield session : Lnet/sf/hibernate/Session;
    //   7690: new java/lang/StringBuilder
    //   7693: dup
    //   7694: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7696: invokespecial <init> : (Ljava/lang/String;)V
    //   7699: aload #18
    //   7701: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7704: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7707: ldc ' and eventAttender.empId ='
    //   7709: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7712: aload_1
    //   7713: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7716: invokevirtual toString : ()Ljava/lang/String;
    //   7719: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7724: astore #25
    //   7726: aload #25
    //   7728: invokeinterface list : ()Ljava/util/List;
    //   7733: astore #26
    //   7735: aload #18
    //   7737: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7740: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7743: aload #18
    //   7745: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7748: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7751: aload #26
    //   7753: ifnull -> 7809
    //   7756: aload #26
    //   7758: iconst_0
    //   7759: invokeinterface get : (I)Ljava/lang/Object;
    //   7764: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7767: astore #27
    //   7769: aload #27
    //   7771: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7774: invokevirtual intValue : ()I
    //   7777: iconst_1
    //   7778: if_icmpne -> 7789
    //   7781: aload #18
    //   7783: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7786: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7789: aload #27
    //   7791: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7794: invokevirtual intValue : ()I
    //   7797: iconst_1
    //   7798: if_icmpne -> 7809
    //   7801: aload #18
    //   7803: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7806: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7809: aload #18
    //   7811: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7814: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7817: aload #18
    //   7819: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7822: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7825: aload #18
    //   7827: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7830: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7833: aload #18
    //   7835: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7838: aload_1
    //   7839: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7842: ifeq -> 7864
    //   7845: aload #18
    //   7847: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7850: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7853: aload #18
    //   7855: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7858: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7861: goto -> 7880
    //   7864: aload #18
    //   7866: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7869: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7872: aload #18
    //   7874: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7877: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7880: aload #7
    //   7882: aload #18
    //   7884: invokeinterface add : (Ljava/lang/Object;)Z
    //   7889: pop
    //   7890: aload #17
    //   7892: new java/lang/Integer
    //   7895: dup
    //   7896: aload #17
    //   7898: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7901: invokevirtual intValue : ()I
    //   7904: iconst_1
    //   7905: iadd
    //   7906: invokespecial <init> : (I)V
    //   7909: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7912: aload_0
    //   7913: getfield session : Lnet/sf/hibernate/Session;
    //   7916: aload #17
    //   7918: invokeinterface update : (Ljava/lang/Object;)V
    //   7923: goto -> 9380
    //   7926: iload #12
    //   7928: bipush #28
    //   7930: if_icmpne -> 9380
    //   7933: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7936: aload #17
    //   7938: ldc com/js/oa/scheme/event/vo/EventVO
    //   7940: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7943: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7946: astore #18
    //   7948: aload_0
    //   7949: getfield session : Lnet/sf/hibernate/Session;
    //   7952: new java/lang/StringBuilder
    //   7955: dup
    //   7956: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7958: invokespecial <init> : (Ljava/lang/String;)V
    //   7961: aload #18
    //   7963: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7966: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7969: ldc ' and eventAttender.empId ='
    //   7971: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7974: aload_1
    //   7975: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7978: invokevirtual toString : ()Ljava/lang/String;
    //   7981: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7986: astore #25
    //   7988: aload #25
    //   7990: invokeinterface list : ()Ljava/util/List;
    //   7995: astore #26
    //   7997: aload #18
    //   7999: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8002: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8005: aload #18
    //   8007: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8010: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8013: aload #26
    //   8015: ifnull -> 8071
    //   8018: aload #26
    //   8020: iconst_0
    //   8021: invokeinterface get : (I)Ljava/lang/Object;
    //   8026: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8029: astore #27
    //   8031: aload #27
    //   8033: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8036: invokevirtual intValue : ()I
    //   8039: iconst_1
    //   8040: if_icmpne -> 8051
    //   8043: aload #18
    //   8045: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8048: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8051: aload #27
    //   8053: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8056: invokevirtual intValue : ()I
    //   8059: iconst_1
    //   8060: if_icmpne -> 8071
    //   8063: aload #18
    //   8065: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8068: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8071: aload #18
    //   8073: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8076: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8079: aload #18
    //   8081: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8084: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8087: aload #18
    //   8089: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8092: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8095: aload #18
    //   8097: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   8100: aload_1
    //   8101: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8104: ifeq -> 8126
    //   8107: aload #18
    //   8109: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8112: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8115: aload #18
    //   8117: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8120: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8123: goto -> 8142
    //   8126: aload #18
    //   8128: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8131: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8134: aload #18
    //   8136: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8139: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8142: aload #7
    //   8144: aload #18
    //   8146: invokeinterface add : (Ljava/lang/Object;)Z
    //   8151: pop
    //   8152: aload #17
    //   8154: new java/lang/Integer
    //   8157: dup
    //   8158: aload #17
    //   8160: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   8163: invokevirtual intValue : ()I
    //   8166: iconst_1
    //   8167: iadd
    //   8168: invokespecial <init> : (I)V
    //   8171: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   8174: aload_0
    //   8175: getfield session : Lnet/sf/hibernate/Session;
    //   8178: aload #17
    //   8180: invokeinterface update : (Ljava/lang/Object;)V
    //   8185: goto -> 9380
    //   8188: aload #17
    //   8190: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   8193: ldc_w '29'
    //   8196: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8199: ifeq -> 9380
    //   8202: iload #11
    //   8204: iconst_1
    //   8205: if_icmpne -> 9380
    //   8208: aload_0
    //   8209: iload #10
    //   8211: invokespecial isLeapYear : (I)Z
    //   8214: ifne -> 9380
    //   8217: iload #12
    //   8219: bipush #28
    //   8221: if_icmpne -> 9380
    //   8224: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   8227: aload #17
    //   8229: ldc com/js/oa/scheme/event/vo/EventVO
    //   8231: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   8234: checkcast com/js/oa/scheme/event/vo/EventVO
    //   8237: astore #18
    //   8239: aload_0
    //   8240: getfield session : Lnet/sf/hibernate/Session;
    //   8243: new java/lang/StringBuilder
    //   8246: dup
    //   8247: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   8249: invokespecial <init> : (Ljava/lang/String;)V
    //   8252: aload #18
    //   8254: invokevirtual getEventId : ()Ljava/lang/Long;
    //   8257: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8260: ldc ' and eventAttender.empId ='
    //   8262: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   8265: aload_1
    //   8266: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8269: invokevirtual toString : ()Ljava/lang/String;
    //   8272: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   8277: astore #25
    //   8279: aload #25
    //   8281: invokeinterface list : ()Ljava/util/List;
    //   8286: astore #26
    //   8288: aload #18
    //   8290: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8293: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8296: aload #18
    //   8298: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8301: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8304: aload #26
    //   8306: ifnull -> 8362
    //   8309: aload #26
    //   8311: iconst_0
    //   8312: invokeinterface get : (I)Ljava/lang/Object;
    //   8317: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8320: astore #27
    //   8322: aload #27
    //   8324: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8327: invokevirtual intValue : ()I
    //   8330: iconst_1
    //   8331: if_icmpne -> 8342
    //   8334: aload #18
    //   8336: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8339: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8342: aload #27
    //   8344: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8347: invokevirtual intValue : ()I
    //   8350: iconst_1
    //   8351: if_icmpne -> 8362
    //   8354: aload #18
    //   8356: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8359: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8362: aload #18
    //   8364: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8367: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8370: aload #18
    //   8372: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8375: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8378: aload #18
    //   8380: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8383: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8386: aload #18
    //   8388: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   8391: aload_1
    //   8392: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8395: ifeq -> 8417
    //   8398: aload #18
    //   8400: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8403: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8406: aload #18
    //   8408: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8411: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8414: goto -> 8433
    //   8417: aload #18
    //   8419: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8422: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8425: aload #18
    //   8427: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8430: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8433: aload #7
    //   8435: aload #18
    //   8437: invokeinterface add : (Ljava/lang/Object;)Z
    //   8442: pop
    //   8443: aload #17
    //   8445: new java/lang/Integer
    //   8448: dup
    //   8449: aload #17
    //   8451: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   8454: invokevirtual intValue : ()I
    //   8457: iconst_1
    //   8458: iadd
    //   8459: invokespecial <init> : (I)V
    //   8462: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   8465: aload_0
    //   8466: getfield session : Lnet/sf/hibernate/Session;
    //   8469: aload #17
    //   8471: invokeinterface update : (Ljava/lang/Object;)V
    //   8476: goto -> 9380
    //   8479: aload #17
    //   8481: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   8484: invokevirtual intValue : ()I
    //   8487: iconst_4
    //   8488: if_icmpne -> 9380
    //   8491: aload #17
    //   8493: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   8496: ldc_w '\$'
    //   8499: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   8502: astore #25
    //   8504: aload #17
    //   8506: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8509: invokevirtual intValue : ()I
    //   8512: iconst_m1
    //   8513: if_icmpne -> 8777
    //   8516: aload #17
    //   8518: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   8521: aload #9
    //   8523: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8526: ifgt -> 9380
    //   8529: aload #25
    //   8531: iconst_0
    //   8532: aaload
    //   8533: invokestatic parseInt : (Ljava/lang/String;)I
    //   8536: iload #11
    //   8538: iconst_1
    //   8539: iadd
    //   8540: if_icmpne -> 9380
    //   8543: aload #25
    //   8545: iconst_1
    //   8546: aaload
    //   8547: invokestatic parseInt : (Ljava/lang/String;)I
    //   8550: iload #12
    //   8552: if_icmpne -> 9380
    //   8555: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   8558: aload #17
    //   8560: ldc com/js/oa/scheme/event/vo/EventVO
    //   8562: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   8565: checkcast com/js/oa/scheme/event/vo/EventVO
    //   8568: astore #18
    //   8570: aload_0
    //   8571: getfield session : Lnet/sf/hibernate/Session;
    //   8574: new java/lang/StringBuilder
    //   8577: dup
    //   8578: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   8580: invokespecial <init> : (Ljava/lang/String;)V
    //   8583: aload #18
    //   8585: invokevirtual getEventId : ()Ljava/lang/Long;
    //   8588: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8591: ldc ' and eventAttender.empId ='
    //   8593: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   8596: aload_1
    //   8597: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8600: invokevirtual toString : ()Ljava/lang/String;
    //   8603: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   8608: astore #26
    //   8610: aload #26
    //   8612: invokeinterface list : ()Ljava/util/List;
    //   8617: astore #27
    //   8619: aload #18
    //   8621: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8624: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8627: aload #18
    //   8629: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8632: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8635: aload #27
    //   8637: ifnull -> 8693
    //   8640: aload #27
    //   8642: iconst_0
    //   8643: invokeinterface get : (I)Ljava/lang/Object;
    //   8648: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8651: astore #28
    //   8653: aload #28
    //   8655: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8658: invokevirtual intValue : ()I
    //   8661: iconst_1
    //   8662: if_icmpne -> 8673
    //   8665: aload #18
    //   8667: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8670: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8673: aload #28
    //   8675: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8678: invokevirtual intValue : ()I
    //   8681: iconst_1
    //   8682: if_icmpne -> 8693
    //   8685: aload #18
    //   8687: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8690: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8693: aload #18
    //   8695: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8698: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8701: aload #18
    //   8703: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8706: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8709: aload #18
    //   8711: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8714: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8717: aload #18
    //   8719: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   8722: aload_1
    //   8723: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8726: ifeq -> 8748
    //   8729: aload #18
    //   8731: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8734: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8737: aload #18
    //   8739: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8742: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8745: goto -> 8764
    //   8748: aload #18
    //   8750: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8753: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8756: aload #18
    //   8758: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8761: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8764: aload #7
    //   8766: aload #18
    //   8768: invokeinterface add : (Ljava/lang/Object;)Z
    //   8773: pop
    //   8774: goto -> 9380
    //   8777: aload #17
    //   8779: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8782: invokevirtual intValue : ()I
    //   8785: ifne -> 9062
    //   8788: aload #17
    //   8790: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   8793: aload #9
    //   8795: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8798: ifgt -> 9380
    //   8801: aload #25
    //   8803: iconst_0
    //   8804: aaload
    //   8805: invokestatic parseInt : (Ljava/lang/String;)I
    //   8808: iload #11
    //   8810: iconst_1
    //   8811: iadd
    //   8812: if_icmpne -> 9380
    //   8815: aload #25
    //   8817: iconst_1
    //   8818: aaload
    //   8819: invokestatic parseInt : (Ljava/lang/String;)I
    //   8822: iload #12
    //   8824: if_icmpne -> 9380
    //   8827: aload #17
    //   8829: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   8832: aload #9
    //   8834: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8837: iflt -> 9380
    //   8840: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   8843: aload #17
    //   8845: ldc com/js/oa/scheme/event/vo/EventVO
    //   8847: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   8850: checkcast com/js/oa/scheme/event/vo/EventVO
    //   8853: astore #18
    //   8855: aload_0
    //   8856: getfield session : Lnet/sf/hibernate/Session;
    //   8859: new java/lang/StringBuilder
    //   8862: dup
    //   8863: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   8865: invokespecial <init> : (Ljava/lang/String;)V
    //   8868: aload #18
    //   8870: invokevirtual getEventId : ()Ljava/lang/Long;
    //   8873: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8876: ldc ' and eventAttender.empId ='
    //   8878: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   8881: aload_1
    //   8882: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8885: invokevirtual toString : ()Ljava/lang/String;
    //   8888: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   8893: astore #26
    //   8895: aload #26
    //   8897: invokeinterface list : ()Ljava/util/List;
    //   8902: astore #27
    //   8904: aload #18
    //   8906: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8909: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8912: aload #18
    //   8914: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8917: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8920: aload #27
    //   8922: ifnull -> 8978
    //   8925: aload #27
    //   8927: iconst_0
    //   8928: invokeinterface get : (I)Ljava/lang/Object;
    //   8933: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8936: astore #28
    //   8938: aload #28
    //   8940: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8943: invokevirtual intValue : ()I
    //   8946: iconst_1
    //   8947: if_icmpne -> 8958
    //   8950: aload #18
    //   8952: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8955: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8958: aload #28
    //   8960: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8963: invokevirtual intValue : ()I
    //   8966: iconst_1
    //   8967: if_icmpne -> 8978
    //   8970: aload #18
    //   8972: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8975: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8978: aload #18
    //   8980: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8983: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8986: aload #18
    //   8988: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8991: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8994: aload #18
    //   8996: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8999: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   9002: aload #18
    //   9004: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   9007: aload_1
    //   9008: invokevirtual equals : (Ljava/lang/Object;)Z
    //   9011: ifeq -> 9033
    //   9014: aload #18
    //   9016: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9019: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   9022: aload #18
    //   9024: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9027: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   9030: goto -> 9049
    //   9033: aload #18
    //   9035: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9038: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   9041: aload #18
    //   9043: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9046: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   9049: aload #7
    //   9051: aload #18
    //   9053: invokeinterface add : (Ljava/lang/Object;)Z
    //   9058: pop
    //   9059: goto -> 9380
    //   9062: aload #17
    //   9064: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   9067: invokevirtual intValue : ()I
    //   9070: ifle -> 9380
    //   9073: aload #17
    //   9075: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   9078: aload #9
    //   9080: invokevirtual compareTo : (Ljava/util/Date;)I
    //   9083: ifgt -> 9380
    //   9086: aload #25
    //   9088: iconst_0
    //   9089: aaload
    //   9090: invokestatic parseInt : (Ljava/lang/String;)I
    //   9093: iload #11
    //   9095: iconst_1
    //   9096: iadd
    //   9097: if_icmpne -> 9380
    //   9100: aload #25
    //   9102: iconst_1
    //   9103: aaload
    //   9104: invokestatic parseInt : (Ljava/lang/String;)I
    //   9107: iload #12
    //   9109: if_icmpne -> 9380
    //   9112: aload #17
    //   9114: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   9117: aload #17
    //   9119: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   9122: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   9125: ifle -> 9380
    //   9128: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   9131: aload #17
    //   9133: ldc com/js/oa/scheme/event/vo/EventVO
    //   9135: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   9138: checkcast com/js/oa/scheme/event/vo/EventVO
    //   9141: astore #18
    //   9143: aload_0
    //   9144: getfield session : Lnet/sf/hibernate/Session;
    //   9147: new java/lang/StringBuilder
    //   9150: dup
    //   9151: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   9153: invokespecial <init> : (Ljava/lang/String;)V
    //   9156: aload #18
    //   9158: invokevirtual getEventId : ()Ljava/lang/Long;
    //   9161: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   9164: ldc ' and eventAttender.empId ='
    //   9166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   9169: aload_1
    //   9170: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   9173: invokevirtual toString : ()Ljava/lang/String;
    //   9176: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   9181: astore #26
    //   9183: aload #26
    //   9185: invokeinterface list : ()Ljava/util/List;
    //   9190: astore #27
    //   9192: aload #18
    //   9194: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   9197: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   9200: aload #18
    //   9202: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   9205: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   9208: aload #27
    //   9210: ifnull -> 9266
    //   9213: aload #27
    //   9215: iconst_0
    //   9216: invokeinterface get : (I)Ljava/lang/Object;
    //   9221: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   9224: astore #28
    //   9226: aload #28
    //   9228: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   9231: invokevirtual intValue : ()I
    //   9234: iconst_1
    //   9235: if_icmpne -> 9246
    //   9238: aload #18
    //   9240: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9243: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   9246: aload #28
    //   9248: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   9251: invokevirtual intValue : ()I
    //   9254: iconst_1
    //   9255: if_icmpne -> 9266
    //   9258: aload #18
    //   9260: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9263: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   9266: aload #18
    //   9268: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   9271: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   9274: aload #18
    //   9276: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   9279: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   9282: aload #18
    //   9284: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   9287: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   9290: aload #18
    //   9292: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   9295: aload_1
    //   9296: invokevirtual equals : (Ljava/lang/Object;)Z
    //   9299: ifeq -> 9321
    //   9302: aload #18
    //   9304: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9307: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   9310: aload #18
    //   9312: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9315: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   9318: goto -> 9337
    //   9321: aload #18
    //   9323: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9326: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   9329: aload #18
    //   9331: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   9334: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   9337: aload #7
    //   9339: aload #18
    //   9341: invokeinterface add : (Ljava/lang/Object;)Z
    //   9346: pop
    //   9347: aload #17
    //   9349: new java/lang/Integer
    //   9352: dup
    //   9353: aload #17
    //   9355: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   9358: invokevirtual intValue : ()I
    //   9361: iconst_1
    //   9362: iadd
    //   9363: invokespecial <init> : (I)V
    //   9366: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   9369: aload_0
    //   9370: getfield session : Lnet/sf/hibernate/Session;
    //   9373: aload #17
    //   9375: invokeinterface update : (Ljava/lang/Object;)V
    //   9380: iinc #24, 1
    //   9383: iload #24
    //   9385: aload #16
    //   9387: invokeinterface size : ()I
    //   9392: if_icmplt -> 560
    //   9395: aload_0
    //   9396: getfield session : Lnet/sf/hibernate/Session;
    //   9399: invokeinterface flush : ()V
    //   9404: aload #7
    //   9406: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #471	-> 0
    //   #472	-> 7
    //   #473	-> 11
    //   #474	-> 20
    //   #475	-> 32
    //   #476	-> 41
    //   #477	-> 48
    //   #478	-> 56
    //   #479	-> 64
    //   #480	-> 72
    //   #481	-> 81
    //   #482	-> 92
    //   #483	-> 107
    //   #484	-> 137
    //   #485	-> 152
    //   #490	-> 182
    //   #491	-> 195
    //   #492	-> 205
    //   #493	-> 234
    //   #490	-> 280
    //   #494	-> 287
    //   #495	-> 296
    //   #496	-> 299
    //   #499	-> 302
    //   #501	-> 327
    //   #502	-> 364
    //   #503	-> 370
    //   #502	-> 407
    //   #506	-> 418
    //   #507	-> 443
    //   #508	-> 449
    //   #509	-> 462
    //   #507	-> 499
    //   #513	-> 510
    //   #514	-> 545
    //   #515	-> 554
    //   #516	-> 560
    //   #518	-> 574
    //   #519	-> 586
    //   #520	-> 600
    //   #521	-> 610
    //   #522	-> 623
    //   #523	-> 636
    //   #524	-> 648
    //   #525	-> 662
    //   #526	-> 677
    //   #527	-> 690
    //   #526	-> 710
    //   #528	-> 717
    //   #529	-> 726
    //   #530	-> 734
    //   #531	-> 742
    //   #532	-> 747
    //   #533	-> 760
    //   #534	-> 772
    //   #536	-> 780
    //   #537	-> 792
    //   #540	-> 800
    //   #541	-> 808
    //   #542	-> 816
    //   #543	-> 824
    //   #544	-> 836
    //   #545	-> 844
    //   #547	-> 855
    //   #548	-> 863
    //   #550	-> 871
    //   #552	-> 884
    //   #554	-> 895
    //   #555	-> 900
    //   #554	-> 902
    //   #556	-> 908
    //   #558	-> 922
    //   #560	-> 925
    //   #559	-> 929
    //   #557	-> 932
    //   #561	-> 937
    //   #562	-> 950
    //   #561	-> 970
    //   #563	-> 977
    //   #564	-> 986
    //   #565	-> 994
    //   #566	-> 1002
    //   #567	-> 1007
    //   #568	-> 1020
    //   #569	-> 1032
    //   #571	-> 1040
    //   #572	-> 1052
    //   #575	-> 1060
    //   #576	-> 1068
    //   #577	-> 1076
    //   #578	-> 1084
    //   #579	-> 1096
    //   #580	-> 1104
    //   #582	-> 1115
    //   #583	-> 1123
    //   #585	-> 1131
    //   #588	-> 1144
    //   #589	-> 1155
    //   #590	-> 1165
    //   #591	-> 1170
    //   #590	-> 1173
    //   #592	-> 1175
    //   #602	-> 1188
    //   #604	-> 1201
    //   #605	-> 1213
    //   #606	-> 1216
    //   #605	-> 1223
    //   #607	-> 1228
    //   #609	-> 1241
    //   #610	-> 1249
    //   #611	-> 1254
    //   #607	-> 1261
    //   #612	-> 1268
    //   #613	-> 1277
    //   #614	-> 1285
    //   #615	-> 1293
    //   #616	-> 1298
    //   #617	-> 1311
    //   #618	-> 1323
    //   #620	-> 1331
    //   #621	-> 1343
    //   #624	-> 1351
    //   #625	-> 1359
    //   #626	-> 1367
    //   #627	-> 1375
    //   #628	-> 1387
    //   #629	-> 1395
    //   #631	-> 1406
    //   #632	-> 1414
    //   #634	-> 1422
    //   #635	-> 1435
    //   #636	-> 1446
    //   #637	-> 1459
    //   #638	-> 1474
    //   #640	-> 1487
    //   #641	-> 1495
    //   #638	-> 1507
    //   #642	-> 1514
    //   #643	-> 1523
    //   #644	-> 1531
    //   #645	-> 1539
    //   #647	-> 1544
    //   #646	-> 1555
    //   #648	-> 1557
    //   #649	-> 1562
    //   #648	-> 1566
    //   #650	-> 1569
    //   #652	-> 1577
    //   #653	-> 1582
    //   #652	-> 1586
    //   #654	-> 1589
    //   #657	-> 1597
    //   #658	-> 1605
    //   #659	-> 1613
    //   #660	-> 1621
    //   #661	-> 1633
    //   #662	-> 1641
    //   #664	-> 1652
    //   #665	-> 1660
    //   #667	-> 1668
    //   #669	-> 1681
    //   #670	-> 1692
    //   #671	-> 1699
    //   #670	-> 1702
    //   #673	-> 1708
    //   #674	-> 1711
    //   #673	-> 1715
    //   #672	-> 1718
    //   #675	-> 1723
    //   #677	-> 1736
    //   #678	-> 1744
    //   #675	-> 1756
    //   #679	-> 1763
    //   #680	-> 1772
    //   #681	-> 1780
    //   #682	-> 1788
    //   #684	-> 1793
    //   #683	-> 1804
    //   #685	-> 1806
    //   #686	-> 1811
    //   #685	-> 1815
    //   #687	-> 1818
    //   #689	-> 1826
    //   #690	-> 1831
    //   #689	-> 1835
    //   #691	-> 1838
    //   #694	-> 1846
    //   #695	-> 1854
    //   #696	-> 1862
    //   #697	-> 1870
    //   #698	-> 1882
    //   #699	-> 1890
    //   #701	-> 1901
    //   #702	-> 1909
    //   #704	-> 1917
    //   #705	-> 1927
    //   #706	-> 1935
    //   #705	-> 1946
    //   #707	-> 1949
    //   #714	-> 1963
    //   #715	-> 1975
    //   #716	-> 1987
    //   #718	-> 2000
    //   #719	-> 2017
    //   #720	-> 2020
    //   #719	-> 2027
    //   #721	-> 2032
    //   #723	-> 2045
    //   #724	-> 2053
    //   #725	-> 2058
    //   #721	-> 2065
    //   #726	-> 2072
    //   #727	-> 2081
    //   #728	-> 2089
    //   #729	-> 2097
    //   #731	-> 2102
    //   #730	-> 2113
    //   #732	-> 2115
    //   #733	-> 2123
    //   #732	-> 2124
    //   #734	-> 2127
    //   #736	-> 2135
    //   #737	-> 2147
    //   #740	-> 2155
    //   #741	-> 2163
    //   #742	-> 2171
    //   #743	-> 2179
    //   #744	-> 2191
    //   #745	-> 2199
    //   #747	-> 2210
    //   #748	-> 2218
    //   #750	-> 2226
    //   #752	-> 2239
    //   #753	-> 2250
    //   #755	-> 2263
    //   #756	-> 2280
    //   #758	-> 2293
    //   #759	-> 2296
    //   #758	-> 2303
    //   #760	-> 2308
    //   #762	-> 2321
    //   #763	-> 2329
    //   #764	-> 2334
    //   #760	-> 2341
    //   #765	-> 2348
    //   #766	-> 2357
    //   #767	-> 2365
    //   #768	-> 2373
    //   #770	-> 2378
    //   #769	-> 2389
    //   #771	-> 2391
    //   #772	-> 2399
    //   #771	-> 2400
    //   #773	-> 2403
    //   #775	-> 2411
    //   #776	-> 2423
    //   #779	-> 2431
    //   #780	-> 2439
    //   #781	-> 2447
    //   #782	-> 2455
    //   #783	-> 2467
    //   #784	-> 2475
    //   #786	-> 2486
    //   #787	-> 2494
    //   #789	-> 2502
    //   #791	-> 2515
    //   #792	-> 2526
    //   #794	-> 2539
    //   #795	-> 2551
    //   #794	-> 2553
    //   #796	-> 2556
    //   #797	-> 2563
    //   #796	-> 2566
    //   #798	-> 2572
    //   #799	-> 2575
    //   #798	-> 2582
    //   #800	-> 2587
    //   #802	-> 2600
    //   #803	-> 2608
    //   #804	-> 2613
    //   #800	-> 2620
    //   #805	-> 2627
    //   #806	-> 2636
    //   #807	-> 2644
    //   #808	-> 2652
    //   #810	-> 2657
    //   #809	-> 2668
    //   #811	-> 2670
    //   #812	-> 2678
    //   #811	-> 2679
    //   #813	-> 2682
    //   #815	-> 2690
    //   #816	-> 2698
    //   #815	-> 2699
    //   #817	-> 2702
    //   #820	-> 2710
    //   #821	-> 2718
    //   #822	-> 2726
    //   #823	-> 2734
    //   #824	-> 2746
    //   #825	-> 2754
    //   #827	-> 2765
    //   #828	-> 2773
    //   #830	-> 2781
    //   #831	-> 2791
    //   #832	-> 2799
    //   #833	-> 2805
    //   #831	-> 2810
    //   #834	-> 2813
    //   #840	-> 2827
    //   #841	-> 2839
    //   #842	-> 2851
    //   #844	-> 2864
    //   #846	-> 2877
    //   #847	-> 2880
    //   #846	-> 2887
    //   #848	-> 2892
    //   #850	-> 2905
    //   #851	-> 2913
    //   #852	-> 2918
    //   #848	-> 2925
    //   #853	-> 2932
    //   #854	-> 2941
    //   #855	-> 2949
    //   #856	-> 2957
    //   #858	-> 2962
    //   #857	-> 2973
    //   #859	-> 2975
    //   #860	-> 2983
    //   #859	-> 2984
    //   #861	-> 2987
    //   #863	-> 2995
    //   #864	-> 3007
    //   #867	-> 3015
    //   #868	-> 3023
    //   #869	-> 3031
    //   #870	-> 3039
    //   #871	-> 3051
    //   #872	-> 3059
    //   #874	-> 3070
    //   #875	-> 3078
    //   #877	-> 3086
    //   #879	-> 3099
    //   #880	-> 3104
    //   #879	-> 3106
    //   #881	-> 3112
    //   #882	-> 3126
    //   #884	-> 3184
    //   #885	-> 3193
    //   #888	-> 3200
    //   #889	-> 3203
    //   #890	-> 3205
    //   #889	-> 3207
    //   #886	-> 3210
    //   #891	-> 3215
    //   #893	-> 3228
    //   #894	-> 3236
    //   #895	-> 3241
    //   #891	-> 3248
    //   #896	-> 3255
    //   #897	-> 3264
    //   #898	-> 3272
    //   #899	-> 3274
    //   #898	-> 3277
    //   #900	-> 3280
    //   #903	-> 3285
    //   #901	-> 3296
    //   #904	-> 3298
    //   #905	-> 3300
    //   #906	-> 3303
    //   #904	-> 3307
    //   #907	-> 3310
    //   #908	-> 3312
    //   #907	-> 3315
    //   #910	-> 3318
    //   #911	-> 3320
    //   #912	-> 3323
    //   #910	-> 3327
    //   #913	-> 3330
    //   #914	-> 3332
    //   #913	-> 3335
    //   #917	-> 3338
    //   #918	-> 3346
    //   #919	-> 3354
    //   #920	-> 3362
    //   #921	-> 3367
    //   #920	-> 3368
    //   #922	-> 3374
    //   #923	-> 3382
    //   #925	-> 3393
    //   #926	-> 3401
    //   #928	-> 3409
    //   #931	-> 3422
    //   #934	-> 3429
    //   #935	-> 3432
    //   #936	-> 3434
    //   #935	-> 3436
    //   #932	-> 3439
    //   #937	-> 3444
    //   #939	-> 3457
    //   #940	-> 3465
    //   #941	-> 3470
    //   #937	-> 3477
    //   #942	-> 3484
    //   #943	-> 3493
    //   #944	-> 3501
    //   #945	-> 3503
    //   #944	-> 3506
    //   #946	-> 3509
    //   #949	-> 3514
    //   #947	-> 3525
    //   #950	-> 3527
    //   #951	-> 3529
    //   #952	-> 3532
    //   #950	-> 3536
    //   #953	-> 3539
    //   #954	-> 3541
    //   #953	-> 3544
    //   #956	-> 3547
    //   #957	-> 3549
    //   #958	-> 3552
    //   #956	-> 3556
    //   #959	-> 3559
    //   #960	-> 3561
    //   #959	-> 3564
    //   #963	-> 3567
    //   #964	-> 3575
    //   #965	-> 3583
    //   #966	-> 3591
    //   #967	-> 3596
    //   #966	-> 3597
    //   #968	-> 3603
    //   #969	-> 3611
    //   #971	-> 3622
    //   #972	-> 3630
    //   #974	-> 3638
    //   #977	-> 3648
    //   #982	-> 3651
    //   #984	-> 3658
    //   #985	-> 3661
    //   #986	-> 3663
    //   #985	-> 3665
    //   #983	-> 3668
    //   #987	-> 3673
    //   #989	-> 3686
    //   #990	-> 3694
    //   #991	-> 3699
    //   #987	-> 3706
    //   #992	-> 3713
    //   #993	-> 3722
    //   #994	-> 3730
    //   #995	-> 3738
    //   #997	-> 3743
    //   #998	-> 3745
    //   #996	-> 3754
    //   #999	-> 3756
    //   #1000	-> 3761
    //   #999	-> 3765
    //   #1001	-> 3768
    //   #1003	-> 3776
    //   #1004	-> 3781
    //   #1003	-> 3785
    //   #1005	-> 3788
    //   #1006	-> 3790
    //   #1005	-> 3793
    //   #1009	-> 3796
    //   #1010	-> 3804
    //   #1011	-> 3812
    //   #1012	-> 3820
    //   #1013	-> 3832
    //   #1014	-> 3840
    //   #1016	-> 3851
    //   #1017	-> 3859
    //   #1019	-> 3867
    //   #1023	-> 3877
    //   #1024	-> 3880
    //   #1025	-> 3885
    //   #1024	-> 3888
    //   #1026	-> 3894
    //   #1027	-> 3900
    //   #1028	-> 3909
    //   #1031	-> 3916
    //   #1032	-> 3919
    //   #1033	-> 3921
    //   #1032	-> 3923
    //   #1029	-> 3926
    //   #1034	-> 3931
    //   #1036	-> 3944
    //   #1037	-> 3952
    //   #1038	-> 3957
    //   #1034	-> 3964
    //   #1039	-> 3971
    //   #1040	-> 3980
    //   #1041	-> 3988
    //   #1042	-> 3990
    //   #1041	-> 3993
    //   #1043	-> 3996
    //   #1045	-> 4001
    //   #1046	-> 4003
    //   #1044	-> 4012
    //   #1047	-> 4014
    //   #1048	-> 4016
    //   #1049	-> 4019
    //   #1047	-> 4023
    //   #1050	-> 4026
    //   #1051	-> 4028
    //   #1050	-> 4031
    //   #1053	-> 4034
    //   #1054	-> 4036
    //   #1055	-> 4039
    //   #1053	-> 4043
    //   #1056	-> 4046
    //   #1057	-> 4048
    //   #1056	-> 4051
    //   #1060	-> 4054
    //   #1061	-> 4062
    //   #1062	-> 4070
    //   #1063	-> 4078
    //   #1064	-> 4083
    //   #1063	-> 4084
    //   #1065	-> 4090
    //   #1066	-> 4098
    //   #1068	-> 4109
    //   #1069	-> 4117
    //   #1071	-> 4125
    //   #1074	-> 4138
    //   #1077	-> 4145
    //   #1078	-> 4148
    //   #1079	-> 4150
    //   #1078	-> 4152
    //   #1075	-> 4155
    //   #1080	-> 4160
    //   #1082	-> 4173
    //   #1083	-> 4181
    //   #1084	-> 4186
    //   #1080	-> 4193
    //   #1085	-> 4200
    //   #1086	-> 4209
    //   #1087	-> 4217
    //   #1088	-> 4219
    //   #1087	-> 4222
    //   #1089	-> 4225
    //   #1091	-> 4230
    //   #1092	-> 4232
    //   #1090	-> 4241
    //   #1093	-> 4243
    //   #1094	-> 4245
    //   #1095	-> 4248
    //   #1093	-> 4252
    //   #1096	-> 4255
    //   #1097	-> 4257
    //   #1096	-> 4260
    //   #1099	-> 4263
    //   #1100	-> 4265
    //   #1101	-> 4268
    //   #1099	-> 4272
    //   #1102	-> 4275
    //   #1103	-> 4277
    //   #1102	-> 4280
    //   #1106	-> 4283
    //   #1107	-> 4291
    //   #1108	-> 4299
    //   #1109	-> 4307
    //   #1110	-> 4312
    //   #1109	-> 4313
    //   #1111	-> 4319
    //   #1112	-> 4327
    //   #1114	-> 4338
    //   #1115	-> 4346
    //   #1117	-> 4354
    //   #1121	-> 4367
    //   #1122	-> 4372
    //   #1121	-> 4375
    //   #1123	-> 4381
    //   #1124	-> 4387
    //   #1125	-> 4396
    //   #1128	-> 4403
    //   #1129	-> 4406
    //   #1130	-> 4408
    //   #1129	-> 4410
    //   #1126	-> 4413
    //   #1131	-> 4418
    //   #1133	-> 4431
    //   #1134	-> 4439
    //   #1135	-> 4444
    //   #1131	-> 4451
    //   #1136	-> 4458
    //   #1137	-> 4467
    //   #1138	-> 4475
    //   #1139	-> 4477
    //   #1138	-> 4480
    //   #1140	-> 4483
    //   #1142	-> 4488
    //   #1143	-> 4490
    //   #1141	-> 4499
    //   #1144	-> 4501
    //   #1145	-> 4503
    //   #1146	-> 4506
    //   #1144	-> 4510
    //   #1147	-> 4513
    //   #1148	-> 4515
    //   #1147	-> 4518
    //   #1150	-> 4521
    //   #1151	-> 4523
    //   #1152	-> 4526
    //   #1150	-> 4530
    //   #1153	-> 4533
    //   #1154	-> 4535
    //   #1153	-> 4538
    //   #1157	-> 4541
    //   #1158	-> 4549
    //   #1159	-> 4557
    //   #1160	-> 4565
    //   #1161	-> 4570
    //   #1160	-> 4571
    //   #1162	-> 4577
    //   #1163	-> 4585
    //   #1165	-> 4596
    //   #1166	-> 4604
    //   #1168	-> 4612
    //   #1176	-> 4625
    //   #1177	-> 4636
    //   #1179	-> 4649
    //   #1180	-> 4662
    //   #1182	-> 4675
    //   #1183	-> 4678
    //   #1182	-> 4685
    //   #1184	-> 4690
    //   #1186	-> 4703
    //   #1187	-> 4711
    //   #1188	-> 4716
    //   #1184	-> 4723
    //   #1189	-> 4730
    //   #1190	-> 4739
    //   #1191	-> 4747
    //   #1192	-> 4755
    //   #1194	-> 4760
    //   #1193	-> 4771
    //   #1195	-> 4773
    //   #1196	-> 4781
    //   #1195	-> 4782
    //   #1197	-> 4785
    //   #1199	-> 4793
    //   #1200	-> 4805
    //   #1203	-> 4813
    //   #1204	-> 4821
    //   #1205	-> 4829
    //   #1206	-> 4837
    //   #1207	-> 4849
    //   #1208	-> 4857
    //   #1210	-> 4868
    //   #1211	-> 4876
    //   #1213	-> 4884
    //   #1215	-> 4897
    //   #1216	-> 4902
    //   #1215	-> 4904
    //   #1217	-> 4910
    //   #1220	-> 4923
    //   #1221	-> 4937
    //   #1223	-> 4992
    //   #1224	-> 5001
    //   #1227	-> 5008
    //   #1228	-> 5011
    //   #1229	-> 5013
    //   #1228	-> 5015
    //   #1225	-> 5018
    //   #1230	-> 5023
    //   #1232	-> 5036
    //   #1233	-> 5044
    //   #1234	-> 5049
    //   #1230	-> 5056
    //   #1235	-> 5063
    //   #1236	-> 5072
    //   #1237	-> 5080
    //   #1238	-> 5082
    //   #1237	-> 5085
    //   #1239	-> 5088
    //   #1242	-> 5093
    //   #1240	-> 5104
    //   #1243	-> 5106
    //   #1244	-> 5108
    //   #1245	-> 5111
    //   #1243	-> 5115
    //   #1246	-> 5118
    //   #1247	-> 5120
    //   #1246	-> 5123
    //   #1249	-> 5126
    //   #1250	-> 5128
    //   #1251	-> 5131
    //   #1249	-> 5135
    //   #1252	-> 5138
    //   #1253	-> 5140
    //   #1252	-> 5143
    //   #1256	-> 5146
    //   #1257	-> 5154
    //   #1258	-> 5162
    //   #1259	-> 5170
    //   #1260	-> 5175
    //   #1259	-> 5176
    //   #1261	-> 5182
    //   #1262	-> 5190
    //   #1264	-> 5201
    //   #1265	-> 5209
    //   #1267	-> 5217
    //   #1270	-> 5230
    //   #1273	-> 5237
    //   #1274	-> 5240
    //   #1275	-> 5242
    //   #1274	-> 5244
    //   #1271	-> 5247
    //   #1276	-> 5252
    //   #1278	-> 5265
    //   #1279	-> 5273
    //   #1280	-> 5278
    //   #1276	-> 5285
    //   #1281	-> 5292
    //   #1282	-> 5301
    //   #1283	-> 5309
    //   #1284	-> 5311
    //   #1283	-> 5314
    //   #1285	-> 5317
    //   #1288	-> 5322
    //   #1286	-> 5333
    //   #1289	-> 5335
    //   #1290	-> 5337
    //   #1291	-> 5340
    //   #1289	-> 5344
    //   #1292	-> 5347
    //   #1293	-> 5349
    //   #1292	-> 5352
    //   #1295	-> 5355
    //   #1296	-> 5357
    //   #1297	-> 5360
    //   #1295	-> 5364
    //   #1298	-> 5367
    //   #1299	-> 5369
    //   #1298	-> 5372
    //   #1302	-> 5375
    //   #1303	-> 5383
    //   #1304	-> 5391
    //   #1305	-> 5399
    //   #1306	-> 5404
    //   #1305	-> 5405
    //   #1307	-> 5411
    //   #1308	-> 5419
    //   #1310	-> 5430
    //   #1311	-> 5438
    //   #1313	-> 5446
    //   #1316	-> 5456
    //   #1321	-> 5459
    //   #1323	-> 5466
    //   #1324	-> 5469
    //   #1325	-> 5471
    //   #1324	-> 5473
    //   #1322	-> 5476
    //   #1326	-> 5481
    //   #1328	-> 5494
    //   #1329	-> 5502
    //   #1330	-> 5507
    //   #1326	-> 5514
    //   #1331	-> 5521
    //   #1332	-> 5530
    //   #1333	-> 5538
    //   #1334	-> 5546
    //   #1336	-> 5551
    //   #1337	-> 5553
    //   #1335	-> 5562
    //   #1338	-> 5564
    //   #1339	-> 5569
    //   #1338	-> 5573
    //   #1340	-> 5576
    //   #1342	-> 5584
    //   #1343	-> 5589
    //   #1342	-> 5593
    //   #1344	-> 5596
    //   #1345	-> 5598
    //   #1344	-> 5601
    //   #1348	-> 5604
    //   #1349	-> 5612
    //   #1350	-> 5620
    //   #1351	-> 5628
    //   #1352	-> 5640
    //   #1353	-> 5648
    //   #1355	-> 5659
    //   #1356	-> 5667
    //   #1358	-> 5675
    //   #1362	-> 5685
    //   #1363	-> 5688
    //   #1364	-> 5693
    //   #1363	-> 5696
    //   #1365	-> 5702
    //   #1366	-> 5708
    //   #1367	-> 5717
    //   #1370	-> 5724
    //   #1371	-> 5727
    //   #1372	-> 5729
    //   #1371	-> 5731
    //   #1368	-> 5734
    //   #1373	-> 5739
    //   #1375	-> 5752
    //   #1376	-> 5760
    //   #1377	-> 5765
    //   #1373	-> 5772
    //   #1378	-> 5779
    //   #1379	-> 5788
    //   #1380	-> 5796
    //   #1381	-> 5798
    //   #1380	-> 5801
    //   #1382	-> 5804
    //   #1384	-> 5809
    //   #1385	-> 5811
    //   #1383	-> 5820
    //   #1386	-> 5822
    //   #1387	-> 5824
    //   #1388	-> 5827
    //   #1386	-> 5831
    //   #1389	-> 5834
    //   #1390	-> 5836
    //   #1389	-> 5839
    //   #1392	-> 5842
    //   #1393	-> 5844
    //   #1394	-> 5847
    //   #1392	-> 5851
    //   #1395	-> 5854
    //   #1396	-> 5856
    //   #1395	-> 5859
    //   #1399	-> 5862
    //   #1400	-> 5870
    //   #1401	-> 5878
    //   #1402	-> 5886
    //   #1403	-> 5891
    //   #1402	-> 5892
    //   #1404	-> 5898
    //   #1405	-> 5906
    //   #1407	-> 5917
    //   #1408	-> 5925
    //   #1410	-> 5933
    //   #1413	-> 5946
    //   #1416	-> 5953
    //   #1417	-> 5956
    //   #1418	-> 5958
    //   #1417	-> 5960
    //   #1414	-> 5963
    //   #1419	-> 5968
    //   #1421	-> 5981
    //   #1422	-> 5989
    //   #1423	-> 5994
    //   #1419	-> 6001
    //   #1424	-> 6008
    //   #1425	-> 6017
    //   #1426	-> 6025
    //   #1427	-> 6027
    //   #1426	-> 6030
    //   #1428	-> 6033
    //   #1430	-> 6038
    //   #1431	-> 6040
    //   #1429	-> 6049
    //   #1432	-> 6051
    //   #1433	-> 6053
    //   #1434	-> 6056
    //   #1432	-> 6060
    //   #1435	-> 6063
    //   #1436	-> 6065
    //   #1435	-> 6068
    //   #1438	-> 6071
    //   #1439	-> 6073
    //   #1440	-> 6076
    //   #1438	-> 6080
    //   #1441	-> 6083
    //   #1442	-> 6085
    //   #1441	-> 6088
    //   #1445	-> 6091
    //   #1446	-> 6099
    //   #1447	-> 6107
    //   #1448	-> 6115
    //   #1449	-> 6120
    //   #1448	-> 6121
    //   #1450	-> 6127
    //   #1451	-> 6135
    //   #1453	-> 6146
    //   #1454	-> 6154
    //   #1456	-> 6162
    //   #1460	-> 6175
    //   #1461	-> 6180
    //   #1460	-> 6183
    //   #1462	-> 6189
    //   #1463	-> 6195
    //   #1464	-> 6204
    //   #1467	-> 6211
    //   #1468	-> 6214
    //   #1469	-> 6216
    //   #1468	-> 6218
    //   #1465	-> 6221
    //   #1470	-> 6226
    //   #1472	-> 6239
    //   #1473	-> 6247
    //   #1474	-> 6252
    //   #1470	-> 6259
    //   #1475	-> 6266
    //   #1476	-> 6275
    //   #1477	-> 6283
    //   #1478	-> 6285
    //   #1477	-> 6288
    //   #1479	-> 6291
    //   #1481	-> 6296
    //   #1482	-> 6298
    //   #1480	-> 6307
    //   #1483	-> 6309
    //   #1484	-> 6311
    //   #1485	-> 6314
    //   #1483	-> 6318
    //   #1486	-> 6321
    //   #1487	-> 6323
    //   #1486	-> 6326
    //   #1489	-> 6329
    //   #1490	-> 6331
    //   #1491	-> 6334
    //   #1489	-> 6338
    //   #1492	-> 6341
    //   #1493	-> 6343
    //   #1492	-> 6346
    //   #1496	-> 6349
    //   #1497	-> 6357
    //   #1498	-> 6365
    //   #1499	-> 6373
    //   #1500	-> 6378
    //   #1499	-> 6379
    //   #1501	-> 6385
    //   #1502	-> 6393
    //   #1504	-> 6404
    //   #1505	-> 6412
    //   #1507	-> 6420
    //   #1514	-> 6433
    //   #1515	-> 6444
    //   #1517	-> 6457
    //   #1518	-> 6470
    //   #1519	-> 6475
    //   #1522	-> 6486
    //   #1523	-> 6489
    //   #1524	-> 6491
    //   #1523	-> 6493
    //   #1520	-> 6496
    //   #1525	-> 6501
    //   #1527	-> 6514
    //   #1528	-> 6522
    //   #1529	-> 6527
    //   #1525	-> 6534
    //   #1530	-> 6541
    //   #1531	-> 6550
    //   #1532	-> 6558
    //   #1533	-> 6566
    //   #1535	-> 6571
    //   #1534	-> 6582
    //   #1536	-> 6584
    //   #1537	-> 6592
    //   #1536	-> 6593
    //   #1538	-> 6596
    //   #1540	-> 6604
    //   #1541	-> 6616
    //   #1544	-> 6624
    //   #1545	-> 6632
    //   #1546	-> 6640
    //   #1547	-> 6648
    //   #1548	-> 6660
    //   #1549	-> 6668
    //   #1551	-> 6679
    //   #1552	-> 6687
    //   #1554	-> 6695
    //   #1555	-> 6705
    //   #1556	-> 6713
    //   #1555	-> 6724
    //   #1557	-> 6727
    //   #1559	-> 6741
    //   #1560	-> 6746
    //   #1559	-> 6748
    //   #1561	-> 6754
    //   #1562	-> 6759
    //   #1563	-> 6770
    //   #1564	-> 6784
    //   #1566	-> 6840
    //   #1567	-> 6849
    //   #1570	-> 6856
    //   #1571	-> 6859
    //   #1572	-> 6861
    //   #1571	-> 6863
    //   #1568	-> 6866
    //   #1573	-> 6871
    //   #1575	-> 6884
    //   #1576	-> 6892
    //   #1577	-> 6897
    //   #1573	-> 6904
    //   #1578	-> 6911
    //   #1579	-> 6920
    //   #1580	-> 6928
    //   #1581	-> 6930
    //   #1580	-> 6933
    //   #1582	-> 6936
    //   #1585	-> 6941
    //   #1583	-> 6952
    //   #1586	-> 6954
    //   #1587	-> 6956
    //   #1588	-> 6959
    //   #1586	-> 6963
    //   #1589	-> 6966
    //   #1590	-> 6968
    //   #1589	-> 6971
    //   #1592	-> 6974
    //   #1593	-> 6976
    //   #1594	-> 6979
    //   #1592	-> 6983
    //   #1595	-> 6986
    //   #1596	-> 6988
    //   #1595	-> 6991
    //   #1599	-> 6994
    //   #1600	-> 7002
    //   #1601	-> 7010
    //   #1602	-> 7018
    //   #1603	-> 7023
    //   #1602	-> 7024
    //   #1604	-> 7030
    //   #1605	-> 7038
    //   #1607	-> 7049
    //   #1608	-> 7057
    //   #1610	-> 7065
    //   #1611	-> 7075
    //   #1612	-> 7077
    //   #1613	-> 7083
    //   #1614	-> 7086
    //   #1611	-> 7091
    //   #1615	-> 7097
    //   #1618	-> 7111
    //   #1621	-> 7118
    //   #1622	-> 7121
    //   #1623	-> 7123
    //   #1622	-> 7125
    //   #1619	-> 7128
    //   #1624	-> 7133
    //   #1626	-> 7146
    //   #1627	-> 7154
    //   #1628	-> 7159
    //   #1624	-> 7166
    //   #1629	-> 7173
    //   #1630	-> 7182
    //   #1631	-> 7190
    //   #1632	-> 7192
    //   #1631	-> 7195
    //   #1633	-> 7198
    //   #1636	-> 7203
    //   #1634	-> 7214
    //   #1637	-> 7216
    //   #1638	-> 7218
    //   #1639	-> 7221
    //   #1637	-> 7225
    //   #1640	-> 7228
    //   #1641	-> 7230
    //   #1640	-> 7233
    //   #1643	-> 7236
    //   #1644	-> 7238
    //   #1645	-> 7241
    //   #1643	-> 7245
    //   #1646	-> 7248
    //   #1647	-> 7250
    //   #1646	-> 7253
    //   #1650	-> 7256
    //   #1651	-> 7264
    //   #1652	-> 7272
    //   #1653	-> 7280
    //   #1654	-> 7285
    //   #1653	-> 7286
    //   #1655	-> 7292
    //   #1656	-> 7300
    //   #1658	-> 7311
    //   #1659	-> 7319
    //   #1661	-> 7327
    //   #1662	-> 7337
    //   #1663	-> 7339
    //   #1664	-> 7345
    //   #1665	-> 7348
    //   #1662	-> 7353
    //   #1666	-> 7359
    //   #1669	-> 7370
    //   #1674	-> 7373
    //   #1676	-> 7380
    //   #1677	-> 7383
    //   #1678	-> 7385
    //   #1677	-> 7387
    //   #1675	-> 7390
    //   #1679	-> 7395
    //   #1681	-> 7408
    //   #1682	-> 7416
    //   #1683	-> 7421
    //   #1679	-> 7428
    //   #1684	-> 7435
    //   #1685	-> 7444
    //   #1686	-> 7452
    //   #1687	-> 7460
    //   #1689	-> 7465
    //   #1690	-> 7467
    //   #1688	-> 7476
    //   #1691	-> 7478
    //   #1692	-> 7483
    //   #1691	-> 7487
    //   #1693	-> 7490
    //   #1695	-> 7498
    //   #1696	-> 7503
    //   #1695	-> 7507
    //   #1697	-> 7510
    //   #1698	-> 7512
    //   #1697	-> 7515
    //   #1701	-> 7518
    //   #1702	-> 7526
    //   #1703	-> 7534
    //   #1704	-> 7542
    //   #1705	-> 7554
    //   #1706	-> 7562
    //   #1708	-> 7573
    //   #1709	-> 7581
    //   #1711	-> 7589
    //   #1712	-> 7599
    //   #1713	-> 7605
    //   #1714	-> 7610
    //   #1713	-> 7614
    //   #1712	-> 7618
    //   #1715	-> 7621
    //   #1719	-> 7632
    //   #1720	-> 7635
    //   #1721	-> 7640
    //   #1720	-> 7643
    //   #1722	-> 7649
    //   #1723	-> 7655
    //   #1724	-> 7664
    //   #1727	-> 7671
    //   #1728	-> 7674
    //   #1729	-> 7676
    //   #1728	-> 7678
    //   #1725	-> 7681
    //   #1730	-> 7686
    //   #1732	-> 7699
    //   #1733	-> 7707
    //   #1734	-> 7712
    //   #1730	-> 7719
    //   #1735	-> 7726
    //   #1736	-> 7735
    //   #1737	-> 7743
    //   #1738	-> 7745
    //   #1737	-> 7748
    //   #1739	-> 7751
    //   #1741	-> 7756
    //   #1742	-> 7758
    //   #1740	-> 7767
    //   #1743	-> 7769
    //   #1744	-> 7771
    //   #1745	-> 7774
    //   #1743	-> 7778
    //   #1746	-> 7781
    //   #1747	-> 7783
    //   #1746	-> 7786
    //   #1749	-> 7789
    //   #1750	-> 7791
    //   #1751	-> 7794
    //   #1749	-> 7798
    //   #1752	-> 7801
    //   #1753	-> 7803
    //   #1752	-> 7806
    //   #1756	-> 7809
    //   #1757	-> 7817
    //   #1758	-> 7825
    //   #1759	-> 7833
    //   #1760	-> 7838
    //   #1759	-> 7839
    //   #1761	-> 7845
    //   #1762	-> 7853
    //   #1764	-> 7864
    //   #1765	-> 7872
    //   #1767	-> 7880
    //   #1768	-> 7890
    //   #1769	-> 7892
    //   #1770	-> 7898
    //   #1771	-> 7901
    //   #1768	-> 7906
    //   #1772	-> 7912
    //   #1775	-> 7926
    //   #1778	-> 7933
    //   #1779	-> 7936
    //   #1780	-> 7938
    //   #1779	-> 7940
    //   #1776	-> 7943
    //   #1781	-> 7948
    //   #1783	-> 7961
    //   #1784	-> 7969
    //   #1785	-> 7974
    //   #1781	-> 7981
    //   #1786	-> 7988
    //   #1787	-> 7997
    //   #1788	-> 8005
    //   #1789	-> 8007
    //   #1788	-> 8010
    //   #1790	-> 8013
    //   #1792	-> 8018
    //   #1793	-> 8020
    //   #1791	-> 8029
    //   #1794	-> 8031
    //   #1795	-> 8033
    //   #1796	-> 8036
    //   #1794	-> 8040
    //   #1797	-> 8043
    //   #1798	-> 8045
    //   #1797	-> 8048
    //   #1800	-> 8051
    //   #1801	-> 8053
    //   #1802	-> 8056
    //   #1800	-> 8060
    //   #1803	-> 8063
    //   #1804	-> 8065
    //   #1803	-> 8068
    //   #1807	-> 8071
    //   #1808	-> 8079
    //   #1809	-> 8087
    //   #1810	-> 8095
    //   #1811	-> 8100
    //   #1810	-> 8101
    //   #1812	-> 8107
    //   #1813	-> 8115
    //   #1815	-> 8126
    //   #1816	-> 8134
    //   #1818	-> 8142
    //   #1819	-> 8152
    //   #1820	-> 8154
    //   #1821	-> 8160
    //   #1822	-> 8163
    //   #1819	-> 8168
    //   #1823	-> 8174
    //   #1827	-> 8188
    //   #1828	-> 8193
    //   #1827	-> 8196
    //   #1829	-> 8202
    //   #1830	-> 8208
    //   #1831	-> 8217
    //   #1834	-> 8224
    //   #1835	-> 8227
    //   #1836	-> 8229
    //   #1835	-> 8231
    //   #1832	-> 8234
    //   #1837	-> 8239
    //   #1839	-> 8252
    //   #1840	-> 8260
    //   #1841	-> 8265
    //   #1837	-> 8272
    //   #1842	-> 8279
    //   #1843	-> 8288
    //   #1844	-> 8296
    //   #1845	-> 8298
    //   #1844	-> 8301
    //   #1846	-> 8304
    //   #1848	-> 8309
    //   #1849	-> 8311
    //   #1847	-> 8320
    //   #1850	-> 8322
    //   #1851	-> 8324
    //   #1852	-> 8327
    //   #1850	-> 8331
    //   #1853	-> 8334
    //   #1854	-> 8336
    //   #1853	-> 8339
    //   #1856	-> 8342
    //   #1857	-> 8344
    //   #1858	-> 8347
    //   #1856	-> 8351
    //   #1859	-> 8354
    //   #1860	-> 8356
    //   #1859	-> 8359
    //   #1863	-> 8362
    //   #1864	-> 8370
    //   #1865	-> 8378
    //   #1866	-> 8386
    //   #1867	-> 8391
    //   #1866	-> 8392
    //   #1868	-> 8398
    //   #1869	-> 8406
    //   #1871	-> 8417
    //   #1872	-> 8425
    //   #1874	-> 8433
    //   #1875	-> 8443
    //   #1876	-> 8445
    //   #1877	-> 8451
    //   #1878	-> 8454
    //   #1875	-> 8459
    //   #1879	-> 8465
    //   #1889	-> 8479
    //   #1890	-> 8491
    //   #1891	-> 8504
    //   #1892	-> 8516
    //   #1893	-> 8529
    //   #1894	-> 8543
    //   #1895	-> 8555
    //   #1896	-> 8558
    //   #1895	-> 8565
    //   #1897	-> 8570
    //   #1899	-> 8583
    //   #1900	-> 8591
    //   #1901	-> 8596
    //   #1897	-> 8603
    //   #1902	-> 8610
    //   #1903	-> 8619
    //   #1904	-> 8627
    //   #1905	-> 8635
    //   #1907	-> 8640
    //   #1906	-> 8651
    //   #1908	-> 8653
    //   #1909	-> 8661
    //   #1908	-> 8662
    //   #1910	-> 8665
    //   #1912	-> 8673
    //   #1913	-> 8685
    //   #1916	-> 8693
    //   #1917	-> 8701
    //   #1918	-> 8709
    //   #1919	-> 8717
    //   #1920	-> 8729
    //   #1921	-> 8737
    //   #1923	-> 8748
    //   #1924	-> 8756
    //   #1926	-> 8764
    //   #1928	-> 8777
    //   #1929	-> 8788
    //   #1930	-> 8801
    //   #1931	-> 8815
    //   #1932	-> 8827
    //   #1934	-> 8840
    //   #1935	-> 8843
    //   #1934	-> 8850
    //   #1936	-> 8855
    //   #1938	-> 8868
    //   #1939	-> 8876
    //   #1940	-> 8881
    //   #1936	-> 8888
    //   #1941	-> 8895
    //   #1942	-> 8904
    //   #1943	-> 8912
    //   #1944	-> 8920
    //   #1946	-> 8925
    //   #1945	-> 8936
    //   #1947	-> 8938
    //   #1948	-> 8946
    //   #1947	-> 8947
    //   #1949	-> 8950
    //   #1951	-> 8958
    //   #1952	-> 8970
    //   #1955	-> 8978
    //   #1956	-> 8986
    //   #1957	-> 8994
    //   #1958	-> 9002
    //   #1959	-> 9014
    //   #1960	-> 9022
    //   #1962	-> 9033
    //   #1963	-> 9041
    //   #1965	-> 9049
    //   #1967	-> 9062
    //   #1968	-> 9073
    //   #1969	-> 9086
    //   #1970	-> 9100
    //   #1971	-> 9112
    //   #1972	-> 9114
    //   #1974	-> 9128
    //   #1975	-> 9131
    //   #1974	-> 9138
    //   #1976	-> 9143
    //   #1978	-> 9156
    //   #1979	-> 9164
    //   #1980	-> 9169
    //   #1976	-> 9176
    //   #1981	-> 9183
    //   #1982	-> 9192
    //   #1983	-> 9200
    //   #1984	-> 9208
    //   #1986	-> 9213
    //   #1985	-> 9224
    //   #1987	-> 9226
    //   #1988	-> 9234
    //   #1987	-> 9235
    //   #1989	-> 9238
    //   #1991	-> 9246
    //   #1992	-> 9258
    //   #1995	-> 9266
    //   #1996	-> 9274
    //   #1997	-> 9282
    //   #1998	-> 9290
    //   #1999	-> 9302
    //   #2000	-> 9310
    //   #2002	-> 9321
    //   #2003	-> 9329
    //   #2005	-> 9337
    //   #2006	-> 9347
    //   #2007	-> 9358
    //   #2008	-> 9361
    //   #2006	-> 9366
    //   #2009	-> 9369
    //   #515	-> 9380
    //   #2014	-> 9395
    //   #2015	-> 9404
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	9407	0	this	Lcom/js/oa/scheme/event/bean/EventEJBBean;
    //   0	9407	1	userId	Ljava/lang/Long;
    //   0	9407	2	time	Ljava/lang/Long;
    //   0	9407	3	domainId	Ljava/lang/Long;
    //   0	9407	4	eventTitle	Ljava/lang/String;
    //   0	9407	5	eventContent	Ljava/lang/String;
    //   0	9407	6	flag	I
    //   20	9387	7	result	Ljava/util/List;
    //   32	9375	8	currentCalendar	Ljava/util/GregorianCalendar;
    //   48	9359	9	currentDate	Ljava/util/Date;
    //   56	9351	10	year	I
    //   64	9343	11	month	I
    //   72	9335	12	day	I
    //   81	9326	13	weekday	I
    //   92	9315	14	appendSql	Ljava/lang/StringBuffer;
    //   287	9120	15	query	Lnet/sf/hibernate/Query;
    //   296	9111	16	list	Ljava/util/List;
    //   299	9108	17	eventPO	Lcom/js/oa/scheme/event/po/EventPO;
    //   302	9105	18	event	Lcom/js/oa/scheme/event/vo/EventVO;
    //   327	9080	19	setWhere	Ljava/lang/String;
    //   364	9043	20	orgIdString	[Ljava/lang/String;
    //   367	51	21	i	I
    //   443	8964	21	groups	[Ljava/lang/String;
    //   446	64	22	i	I
    //   545	8862	22	workDayQuery	Lnet/sf/hibernate/Query;
    //   554	8853	23	workDayList	Ljava/util/List;
    //   557	8838	24	i	I
    //   623	562	25	workDay	Ljava/lang/String;
    //   717	164	26	query1	Lnet/sf/hibernate/Query;
    //   726	155	27	list1	Ljava/util/List;
    //   760	40	28	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   977	164	26	query1	Lnet/sf/hibernate/Query;
    //   986	155	27	list1	Ljava/util/List;
    //   1020	40	28	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1165	20	26	count	I
    //   1175	10	27	startTime	J
    //   1268	167	25	query1	Lnet/sf/hibernate/Query;
    //   1277	158	26	list1	Ljava/util/List;
    //   1311	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1514	164	25	query1	Lnet/sf/hibernate/Query;
    //   1523	155	26	list1	Ljava/util/List;
    //   1557	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1763	197	25	query1	Lnet/sf/hibernate/Query;
    //   1772	188	26	list1	Ljava/util/List;
    //   1806	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2072	164	25	query1	Lnet/sf/hibernate/Query;
    //   2081	155	26	list1	Ljava/util/List;
    //   2115	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2348	164	25	query1	Lnet/sf/hibernate/Query;
    //   2357	155	26	list1	Ljava/util/List;
    //   2391	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2627	197	25	query1	Lnet/sf/hibernate/Query;
    //   2636	188	26	list1	Ljava/util/List;
    //   2670	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2932	167	25	query1	Lnet/sf/hibernate/Query;
    //   2941	158	26	list1	Ljava/util/List;
    //   2975	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3255	164	25	query1	Lnet/sf/hibernate/Query;
    //   3264	155	26	list1	Ljava/util/List;
    //   3298	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3484	164	25	query1	Lnet/sf/hibernate/Query;
    //   3493	155	26	list1	Ljava/util/List;
    //   3527	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3713	164	25	query1	Lnet/sf/hibernate/Query;
    //   3722	155	26	list1	Ljava/util/List;
    //   3756	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3971	164	25	query1	Lnet/sf/hibernate/Query;
    //   3980	155	26	list1	Ljava/util/List;
    //   4014	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4200	164	25	query1	Lnet/sf/hibernate/Query;
    //   4209	155	26	list1	Ljava/util/List;
    //   4243	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4458	164	25	query1	Lnet/sf/hibernate/Query;
    //   4467	155	26	list1	Ljava/util/List;
    //   4501	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4730	167	25	query1	Lnet/sf/hibernate/Query;
    //   4739	158	26	list1	Ljava/util/List;
    //   4773	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5063	164	25	query1	Lnet/sf/hibernate/Query;
    //   5072	155	26	list1	Ljava/util/List;
    //   5106	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5292	164	25	query1	Lnet/sf/hibernate/Query;
    //   5301	155	26	list1	Ljava/util/List;
    //   5335	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5521	164	25	query1	Lnet/sf/hibernate/Query;
    //   5530	155	26	list1	Ljava/util/List;
    //   5564	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5779	164	25	query1	Lnet/sf/hibernate/Query;
    //   5788	155	26	list1	Ljava/util/List;
    //   5822	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6008	164	25	query1	Lnet/sf/hibernate/Query;
    //   6017	155	26	list1	Ljava/util/List;
    //   6051	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6266	164	25	query1	Lnet/sf/hibernate/Query;
    //   6275	155	26	list1	Ljava/util/List;
    //   6309	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6541	200	25	query1	Lnet/sf/hibernate/Query;
    //   6550	191	26	list1	Ljava/util/List;
    //   6584	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6911	197	25	query1	Lnet/sf/hibernate/Query;
    //   6920	188	26	list1	Ljava/util/List;
    //   6954	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7173	197	25	query1	Lnet/sf/hibernate/Query;
    //   7182	188	26	list1	Ljava/util/List;
    //   7216	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7435	197	25	query1	Lnet/sf/hibernate/Query;
    //   7444	188	26	list1	Ljava/util/List;
    //   7478	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7726	197	25	query1	Lnet/sf/hibernate/Query;
    //   7735	188	26	list1	Ljava/util/List;
    //   7769	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7988	197	25	query1	Lnet/sf/hibernate/Query;
    //   7997	188	26	list1	Ljava/util/List;
    //   8031	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   8279	197	25	query1	Lnet/sf/hibernate/Query;
    //   8288	188	26	list1	Ljava/util/List;
    //   8322	40	27	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   8504	876	25	monthDay	[Ljava/lang/String;
    //   8610	164	26	query1	Lnet/sf/hibernate/Query;
    //   8619	155	27	list1	Ljava/util/List;
    //   8653	40	28	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   8895	164	26	query1	Lnet/sf/hibernate/Query;
    //   8904	155	27	list1	Ljava/util/List;
    //   8938	40	28	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   9183	197	26	query1	Lnet/sf/hibernate/Query;
    //   9192	188	27	list1	Ljava/util/List;
    //   9226	40	28	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
  }
  
  private boolean isLeapYear(int year) {
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
      return true; 
    return false;
  }
  
  public Boolean addEvent(EventVO event) throws Exception {
    String urlaction = (event.getOnTimeMode().intValue() == 0) ? "selectSingleEvent" : "selectSingleEchoEvent";
    Boolean result = new Boolean(false);
    StringBuffer empIdStr = new StringBuffer();
    try {
      begin();
      EventPO eventPO = (EventPO)TransformObject.getInstance().transformObject(event, EventPO.class);
      if (eventPO.getEventRemind() == null)
        eventPO.setEventRemind(new Integer(0)); 
      Long eventId = (Long)this.session.save(eventPO);
      UserBD userBD = new UserBD();
      String attEmp = event.getAttendEmp();
      String[] attEmps = (String[])null;
      if (attEmp.indexOf("$") >= 0) {
        String attEmp0 = attEmp.substring(attEmp.indexOf("$"), attEmp.lastIndexOf("$") + 1);
        empIdStr.append(attEmp0);
      } 
      if (attEmp.indexOf("*") >= 0) {
        String attEmp1 = StaticParam.getEmpIdByCondStr(attEmp.substring(attEmp.indexOf("*") + 1, attEmp.lastIndexOf("*")), "org");
        empIdStr.append(attEmp1);
      } 
      if (attEmp.indexOf("@") >= 0) {
        String attEmp2 = StaticParam.getEmpIdByCondStr(attEmp.substring(attEmp.indexOf("@") + 1, attEmp.lastIndexOf("@")), "group");
        empIdStr.append(attEmp2);
      } 
      boolean flag = false;
      if (!empIdStr.toString().equals("")) {
        attEmps = empIdStr.toString().substring(1, empIdStr.toString().length() - 1).split("\\$\\$");
        for (int i = 0; i < attEmps.length; i++) {
          if (event.getEventEmpId().toString().equals(attEmps[i]))
            flag = true; 
          Date endDate = null;
          if (event.getOnTimeMode().intValue() == 0) {
            if (event.getEventBeginTime() == null || event.getEventBeginTime().toString().equals("")) {
              endDate = new Date();
              endDate.setHours(23);
              endDate.setMinutes(59);
            } else {
              endDate = new Date(event.getEventEndDate().longValue() + (event.getEventEndTime().intValue() * 1000));
            } 
          } else {
            Calendar tmp = Calendar.getInstance();
            tmp.set(2050, 12, 12);
            endDate = tmp.getTime();
          } 
          String url = "eventAction.do?action=" + urlaction + "&eventId=" + eventId + "&fromdesktop=1";
          String title = event.getEventTitle();
          String beginFormat = event.getEventBeginDateFormat();
          String endFormat = event.getEventEndDateFormat();
          if (beginFormat.equals(endFormat)) {
            title = String.valueOf(title) + "[" + beginFormat + " " + event.getEventBeginTimeFormat() + " 至 " + event.getEventEndTimeFormat() + "]";
          } else {
            title = String.valueOf(title) + "[" + beginFormat + " " + event.getEventBeginTimeFormat() + "至" + endFormat + " " + event.getEventEndTimeFormat() + "]";
          } 
          RemindUtil.sendMessageToUsers(title, url, attEmps[i], "Event", new Date(), endDate, userBD.getUserNameById(event.getEventEmpId().toString()), eventId);
          if (event.getEventRemind().intValue() == 1)
            if (event.getEventBeginDate() != null) {
              long eventBeginTime = event.getEventBeginDate().longValue();
              if (event.getEventBeginTime() != null)
                eventBeginTime += (event.getEventBeginTime().intValue() * 1000); 
              Date remindDate = new Date(eventBeginTime);
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(remindDate);
              calendar.add(12, (int)-(event.getEventRemindTime().longValue() / 60L));
              if (event.getOnTimeMode().intValue() == 0) {
                if (event.getOnTimeMode().intValue() == 0) {
                  long eventEndTime = event.getEventEndDate().longValue();
                  if (event.getEventEndTime() != null)
                    eventEndTime += (event.getEventEndTime().intValue() * 1000); 
                  endDate = new Date(eventEndTime);
                } else {
                  Calendar tmp = Calendar.getInstance();
                  tmp.set(2050, 12, 12);
                  endDate = tmp.getTime();
                } 
                url = "eventAction.do?action=" + urlaction + "&eventId=" + eventId + "&fromdesktop=1";
                title = event.getEventTitle();
                if (beginFormat.equals(endFormat)) {
                  title = String.valueOf(title) + "[" + beginFormat + " " + event.getEventBeginTimeFormat() + " 至 " + event.getEventEndTimeFormat() + "]";
                } else {
                  title = String.valueOf(title) + "[" + beginFormat + " " + event.getEventBeginTimeFormat() + "至" + endFormat + " " + event.getEventEndTimeFormat() + "]";
                } 
                RemindUtil.sendMessageToUsers(title, url, attEmps[i], "Event", calendar.getTime(), endDate, userBD.getUserNameById(event.getEventEmpId().toString()), eventId);
              } 
            }  
        } 
      } 
      if (!flag && 
        event.getEventRemind().intValue() == 1) {
        Calendar calendar = Calendar.getInstance();
        Date endDate = null;
        if (event.getOnTimeMode().intValue() == 0) {
          long eventBeginTime = event.getEventBeginDate().longValue();
          if (event.getEventBeginTime() != null)
            eventBeginTime += (event.getEventBeginTime().intValue() * 1000); 
          Date remindDate = new Date(eventBeginTime);
          calendar.setTime(remindDate);
          calendar.add(12, (int)-(event.getEventRemindTime().longValue() / 60L));
        } else {
          calendar.setTime(event.getEchoBeginTime());
        } 
        if (event.getOnTimeMode().intValue() == 0) {
          if (event.getEventEndTime() != null) {
            endDate = new Date(event.getEventEndDate().longValue() + (event.getEventEndTime().intValue() * 1000));
          } else {
            endDate = new Date(event.getEventEndDate().longValue() + 86400000L);
          } 
        } else {
          Calendar tmp = Calendar.getInstance();
          tmp.set(2050, 12, 12);
          endDate = tmp.getTime();
        } 
        String url = "eventAction.do?action=" + urlaction + "&eventId=" + eventId + "&fromdesktop=1";
        String title = event.getEventTitle();
        String beginFormat = event.getEventBeginDateFormat();
        String endFormat = event.getEventEndDateFormat();
        if (beginFormat.equals(endFormat)) {
          title = String.valueOf(title) + "[" + beginFormat + " " + event.getEventBeginTimeFormat() + " 至 " + event.getEventEndTimeFormat() + "]";
        } else {
          title = String.valueOf(title) + "[" + beginFormat + " " + event.getEventBeginTimeFormat() + "至" + endFormat + " " + event.getEventEndTimeFormat() + "]";
        } 
        RemindUtil.sendMessageToUsers(title, url, event.getEventEmpId().toString(), "Event", calendar.getTime(), endDate, userBD.getUserNameById(event.getEventEmpId().toString()), eventId);
      } 
      if (event.getAttendEmp() != null && !event.getAttendEmp().trim().equals("")) {
        String[] userIds = (new ConversionString(empIdStr.toString())).getUserIdString().split(",");
        addEventAttender(eventPO, userIds);
      } else {
        EventAttenderPO eventAttender = new EventAttenderPO();
        eventAttender.setEmpId(eventPO.getEventEmpId());
        eventAttender.setEvent(eventPO);
        eventAttender.setEventIsViewed(new Integer(1));
        eventAttender.setEventIsPoped(new Integer(0));
        if (eventPO.getEventRemind() != null) {
          eventAttender.setAffirmRemind(new Integer(0));
        } else {
          eventAttender.setAffirmRemind(new Integer(1));
        } 
        if (eventPO.getOnTimeMode() != null)
          if (!eventPO.getOnTimeMode().equals(new Integer(0))) {
            eventAttender.setEventIsEcho(new Integer(1));
          } else {
            eventAttender.setEventIsEcho(new Integer(0));
          }  
        this.session.save(eventAttender);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception ex) {
      System.out.println("add Event Exception: " + ex.getMessage());
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void addEventAttender(EventPO eventPO, String[] userIds) throws HibernateException, NumberFormatException {
    EventAttenderPO eventAttender = null;
    for (int i = 0; i < userIds.length; i++) {
      if (!eventPO.getEventEmpId().equals(Long.valueOf(userIds[i]))) {
        eventAttender = new EventAttenderPO();
        eventAttender.setEmpId(Long.valueOf(userIds[i]));
        eventAttender.setEvent(eventPO);
        eventAttender.setEventIsViewed(new Integer(0));
        if (eventPO.getEventRemind() != null)
          if (eventPO.getEventRemind().equals(new Integer(0))) {
            eventAttender.setAffirmRemind(new Integer(0));
          } else {
            eventAttender.setAffirmRemind(new Integer(1));
          }  
        if (eventPO.getOnTimeMode() != null)
          if (!eventPO.getOnTimeMode().equals(new Integer(0))) {
            eventAttender.setEventIsEcho(new Integer(1));
          } else {
            eventAttender.setEventIsEcho(new Integer(0));
          }  
        eventAttender.setEventIsViewed(new Integer(0));
        this.session.save(eventAttender);
      } 
    } 
    eventAttender = new EventAttenderPO();
    eventAttender.setEmpId(eventPO.getEventEmpId());
    eventAttender.setEvent(eventPO);
    eventAttender.setEventIsViewed(new Integer(1));
    if (eventPO.getEventRemind() != null) {
      eventAttender.setAffirmRemind(new Integer(0));
    } else {
      eventAttender.setAffirmRemind(new Integer(1));
    } 
    if (eventPO.getOnTimeMode() != null)
      if (!eventPO.getOnTimeMode().equals(new Integer(0))) {
        eventAttender.setEventIsEcho(new Integer(1));
      } else {
        eventAttender.setEventIsEcho(new Integer(0));
      }  
    this.session.save(eventAttender);
  }
  
  public List selectAllEvent(Long userId, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    return selectAllEvent(userId, currentPage, volume, domainId, (String)null);
  }
  
  public List selectAllEvent(Long userId, Integer currentPage, Integer volume, Long domainId, String toUserId) throws HibernateException {
    List<EventVO> result = new ArrayList();
    try {
      begin();
      StringBuffer sql = new StringBuffer("select distinct event from EventPO event join event.eventAttenders eventAttenders ");
      sql.append(" where eventAttenders.empId=" + userId);
      if (domainId != null)
        sql.append(" and event.eventDomainId=" + String.valueOf(domainId.intValue())); 
      if (toUserId != null) {
        sql.append(" and event.eventEmpId=" + toUserId + " ");
      } else if (currentPage.intValue() == -1) {
        sql.append(" and event.eventEmpId=" + volume);
      } 
      sql.append("  order by event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc ");
      Query query = this.session.createQuery(sql.toString());
      if (currentPage.intValue() != -1) {
        query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
        query.setMaxResults(volume.intValue());
      } else {
        query.setFirstResult(0);
        query.setMaxResults(7);
      } 
      List list = query.list();
      Iterator<EventPO> iterator = list.iterator();
      EventPO event = null;
      while (iterator != null && iterator.hasNext()) {
        event = iterator.next();
        EventVO eventVO = (EventVO)TransformObject.getInstance()
          .transformObject(event, EventVO.class);
        Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            
            eventVO.getEventId() + 
            " and eventAttender.empId =" + 
            userId);
        List<EventAttenderPO> list1 = query1.list();
        eventVO.setIsViewed(Boolean.FALSE);
        eventVO.setIsAffirmRemind(Boolean.FALSE);
        if (list1 != null) {
          EventAttenderPO eventAttender = list1.get(
              0);
          if (eventAttender.getEventIsViewed() != null && 
            eventAttender.getEventIsViewed().intValue() == 1)
            eventVO.setIsViewed(Boolean.TRUE); 
          if (eventAttender.getAffirmRemind() != null && 
            eventAttender.getAffirmRemind().intValue() == 1)
            eventVO.setIsAffirmRemind(Boolean.TRUE); 
        } 
        eventVO.setCanDelete(Boolean.FALSE);
        eventVO.setCanModify(Boolean.FALSE);
        eventVO.setIsShare(Boolean.FALSE);
        if (event.getEventEmpId() != null && 
          event.getEventEmpId().equals(userId)) {
          eventVO.setCanDelete(Boolean.TRUE);
          eventVO.setCanModify(Boolean.TRUE);
        } else {
          eventVO.setCanDelete(Boolean.TRUE);
          eventVO.setIsShare(Boolean.TRUE);
        } 
        result.add(eventVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select All Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getEventRecordCount(Long userId, Long domainId) throws HibernateException {
    return getEventRecordCount(userId, domainId, (String)null);
  }
  
  public Integer getEventRecordCount(Long userId, Long domainId, String toUserId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String toStr = "";
      if (toUserId != null)
        toStr = " and event.eventEmpId=" + toUserId + " "; 
      result = this.session.iterate("select distinct count(*) from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId = " + 
          
          userId + toStr + (
          (domainId != null) ? (
          " and event.eventDomainId=" + 
          String.valueOf(domainId
            .intValue())) : 
          "")).next();
    } catch (HibernateException ex) {
      System.out.println("get Event Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public EventVO selectSingleEvent(Long eventId, Long userId, Long domainId) throws HibernateException {
    EventVO event = null;
    try {
      begin();
      EventPO eventPO = (EventPO)this.session.load(EventPO.class, eventId);
      event = (EventVO)TransformObject.getInstance().transformObject(
          eventPO, EventVO.class);
      Query query = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          
          eventId + 
          " and eventAttender.empId = " + 
          userId + (
          (domainId != null) ? (
          " and eventAttender.event.eventDomainId=" + domainId) : ""));
      List<EventAttenderPO> list = query.list();
      if (list != null && list.size() > 0) {
        EventAttenderPO eventAttender = list.get(0);
        if (eventAttender.getEventIsViewed() != null && 
          eventAttender.getEventIsViewed().intValue() == 0)
          eventAttender.setEventIsViewed(new Integer(1)); 
        if (eventAttender.getAffirmRemind() != null && 
          eventAttender.getAffirmRemind().intValue() == 1)
          eventAttender.setAffirmRemind(new Integer(0)); 
      } 
      event.setCanDelete(Boolean.FALSE);
      event.setCanModify(Boolean.FALSE);
      event.setIsShare(Boolean.FALSE);
      if (event.getEventEmpId().equals(userId)) {
        event.setCanDelete(Boolean.TRUE);
        event.setCanModify(Boolean.TRUE);
      } else {
        event.setCanDelete(Boolean.TRUE);
        event.setIsShare(Boolean.TRUE);
      } 
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("select Single event Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return event;
  }
  
  public Boolean deleteEvent(Long userId, Long eventId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      delEvent(userId, eventId);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void delEvent(Long userId, Long eventId) throws HibernateException {
    EventPO eventPO = null;
    try {
      eventPO = (EventPO)this.session.load(EventPO.class, eventId);
    } catch (Exception exception) {}
    if (eventPO != null)
      if (!eventPO.getEventEmpId().equals(userId)) {
        this.session.delete(
            "from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            eventId + 
            " and eventAttender.empId = " + userId);
      } else {
        Iterator<EventAttenderPO> iter = eventPO.getEventAttenders().iterator();
        while (iter.hasNext())
          this.session.delete(iter.next()); 
        this.session.delete(eventPO);
      }  
  }
  
  public Boolean deleteBatchEvent(Long userId, String eventIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] eventIdArray = eventIds.split(",");
      for (int i = 0; i < eventIdArray.length; i++)
        delEvent(userId, Long.valueOf(eventIdArray[i])); 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete Batch Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteAllEvent(Long userId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Query query = this.session.createQuery("select distinct eventAttender.event.eventId from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.empId = " + 
          
          userId);
      List<E> list = query.list();
      Long eventId = null;
      for (int i = 0; i < list.size(); i++) {
        eventId = Long.valueOf(list.get(i).toString());
        delEvent(userId, eventId);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete Batch Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean modifyEvent(EventVO event) throws HibernateException {
    String urlaction = (event.getOnTimeMode().intValue() == 0) ? "selectSingleEvent" : "selectSingleEchoEvent";
    Boolean result = new Boolean(false);
    boolean flag = false;
    try {
      begin();
      EventPO eventPO = (EventPO)this.session.load(EventPO.class, 
          event.getEventId());
      StringBuffer empIdStr = new StringBuffer();
      if (event.getEventEmpId().equals(eventPO.getEventEmpId())) {
        setVOToPO(event, eventPO);
        Long eventId = (Long)this.session.save(eventPO);
        try {
          RemindUtil.deleteMsg(eventId.toString(), "Event");
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (event.getAttendEmp() != null && 
          !event.getAttendEmp().trim().equals("")) {
          String[] userIds = (new ConversionString(event.getAttendEmp()))
            .getUserIdString().split(",");
          addEventAttender(eventPO, userIds);
        } else {
          EventAttenderPO eventAttender = new EventAttenderPO();
          eventAttender.setEmpId(eventPO.getEventEmpId());
          eventAttender.setEvent(eventPO);
          eventAttender.setEventIsPoped(new Integer(0));
          if (eventPO.getEventRemind() != null) {
            eventAttender.setAffirmRemind(new Integer(0));
          } else {
            eventAttender.setAffirmRemind(new Integer(1));
          } 
          if (eventPO.getOnTimeMode() != null)
            if (!eventPO.getOnTimeMode().equals(new Integer(0))) {
              eventAttender.setEventIsEcho(new Integer(1));
            } else {
              eventAttender.setEventIsEcho(new Integer(0));
            }  
          eventAttender.setEventIsViewed(new Integer(1));
          this.session.save(eventAttender);
        } 
        UserBD userBD = new UserBD();
        String attEmp = event.getAttendEmp();
        if (attEmp != null && !"".equals(attEmp) && !"null".equals(attEmp)) {
          String[] attEmps = (String[])null;
          if (attEmp.indexOf("$") >= 0) {
            String attEmp0 = attEmp.substring(attEmp.indexOf("$"), attEmp.lastIndexOf("$") + 1);
            empIdStr.append(attEmp0);
          } 
          if (attEmp.indexOf("*") >= 0) {
            String attEmp1 = StaticParam.getEmpIdByCondStr(attEmp.substring(attEmp.indexOf("*") + 1, attEmp.lastIndexOf("*")), "org");
            empIdStr.append(attEmp1);
          } 
          if (attEmp.indexOf("@") >= 0) {
            String attEmp2 = StaticParam.getEmpIdByCondStr(attEmp.substring(attEmp.indexOf("@") + 1, attEmp.lastIndexOf("@")), "group");
            empIdStr.append(attEmp2);
          } 
          if (!empIdStr.toString().equals("")) {
            attEmps = empIdStr.toString().substring(1, empIdStr.toString().length() - 1).split("\\$\\$");
            for (int i = 0; i < attEmps.length; i++) {
              if (event.getEventEmpId().toString().equals(attEmps[i]))
                flag = true; 
              Date endDate = null;
              if (event.getOnTimeMode().intValue() == 0) {
                if (event.getEventBeginTime() == null || event.getEventBeginTime().toString().equals("")) {
                  endDate = new Date();
                  endDate.setHours(23);
                  endDate.setMinutes(59);
                } else {
                  endDate = new Date(event.getEventEndDate().longValue() + (event.getEventEndTime().intValue() * 1000));
                } 
              } else {
                Calendar tmp = Calendar.getInstance();
                tmp.set(2050, 12, 12);
                endDate = tmp.getTime();
              } 
              String url = "eventAction.do?action=selectSingleEvent&eventId=" + eventId + "&fromdesktop=1";
              String title = event.getEventTitle();
              RemindUtil.sendMessageToUsers(title, url, attEmps[i], "Event", new Date(), endDate, userBD.getUserNameById(event.getEventEmpId().toString()), eventId);
              if (event.getEventRemind().intValue() == 1) {
                long eventBeginTime = event.getEventBeginDate().longValue();
                if (event.getEventBeginTime() != null)
                  eventBeginTime += (event.getEventBeginTime().intValue() * 1000); 
                Date remindDate = new Date(eventBeginTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(remindDate);
                calendar.add(12, (int)-(event.getEventRemindTime().longValue() / 60L));
                if (event.getOnTimeMode().intValue() == 0) {
                  long eventEndTime = event.getEventEndDate().longValue();
                  if (event.getEventEndTime() != null)
                    eventEndTime += (event.getEventEndTime().intValue() * 1000); 
                  endDate = new Date(eventEndTime);
                } else {
                  Calendar tmp = Calendar.getInstance();
                  tmp.set(2050, 12, 12);
                  endDate = tmp.getTime();
                } 
                url = "eventAction.do?action=selectSingleEvent&eventId=" + eventId + "&fromdesktop=1";
                title = event.getEventTitle();
                RemindUtil.sendMessageToUsers(title, url, attEmps[i], "Event", calendar.getTime(), endDate, userBD.getUserNameById(event.getEventEmpId().toString()), eventId);
              } 
            } 
          } 
        } 
        if (!flag) {
          Date endDate = null;
          String url = "eventAction.do?action=selectSingleEvent&eventId=" + eventId + "&fromdesktop=1";
          String title = event.getEventTitle();
          if (event.getEventRemind().intValue() == 1) {
            long eventBeginTime = event.getEventBeginDate().longValue();
            if (event.getEventBeginTime() != null)
              eventBeginTime += (event.getEventBeginTime().intValue() * 1000); 
            Date remindDate = new Date(eventBeginTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(remindDate);
            calendar.add(12, (int)-(event.getEventRemindTime().longValue() / 60L));
            if (event.getOnTimeMode().intValue() == 0) {
              long eventEndTime = event.getEventEndDate().longValue();
              if (event.getEventEndTime() != null)
                eventEndTime += (event.getEventEndTime().intValue() * 1000); 
              endDate = new Date(eventEndTime);
            } else {
              Calendar tmp = Calendar.getInstance();
              tmp.set(2050, 12, 12);
              endDate = tmp.getTime();
            } 
            url = "eventAction.do?action=selectSingleEvent&eventId=" + eventId + "&fromdesktop=1";
            title = event.getEventTitle();
            RemindUtil.sendMessageToUsers(title, url, event.getEventEmpId().toString(), "Event", calendar.getTime(), endDate, userBD.getUserNameById(event.getEventEmpId().toString()), eventId);
          } 
        } 
        this.session.flush();
      } 
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("modify Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void setVOToPO(EventVO event, EventPO eventPO) throws HibernateException {
    deleteEventAttender(eventPO.getEventId());
    eventPO.setAttendEmp(event.getAttendEmp());
    eventPO.setAttendName(event.getAttendName());
    eventPO.setEchoBeginTime(event.getEchoBeginTime());
    eventPO.setEchoCounter(event.getEchoCounter());
    eventPO.setEchoEndTime(event.getEchoEndTime());
    eventPO.setEchoMode(event.getEchoMode());
    eventPO.setEventAddress(event.getEventAddress());
    eventPO.setEventBeginDate(event.getEventBeginDate());
    eventPO.setEventBeginTime(event.getEventBeginTime());
    eventPO.setEventContent(event.getEventContent());
    eventPO.setEventDate(event.getEventDate());
    eventPO.setEventEndDate(event.getEventEndDate());
    eventPO.setEventEndTime(event.getEventEndTime());
    eventPO.setEventFullDay(event.getEventFullDay());
    eventPO.setEventLastTime(event.getEventLastTime());
    eventPO.setEventRemind(event.getEventRemind());
    eventPO.setEventRemindTime(event.getEventRemindTime());
    eventPO.setEventTitle(event.getEventTitle());
    eventPO.setOnTimeContent(event.getOnTimeContent());
    eventPO.setOnTimeMode(event.getOnTimeMode());
    eventPO.setRelProjectId(event.getRelProjectId());
    eventPO.setPopRemindMode(event.getPopRemindMode());
    eventPO.setNoteRemindMode(event.getNoteRemindMode());
    eventPO.setOpenEvent(event.getOpenEvent());
  }
  
  private void deleteEventAttender(Long eventId) throws HibernateException {
    this.session.delete(
        "from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId =" + 
        eventId);
  }
  
  public List selectEventByTime(Long userId, Long eventTime, Long domainId) throws HibernateException {
    List result = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery("select distinct optionset.workingDay from com.js.oa.personalwork.setup.po.OptionSetPO optionset where optionset.empId =" + 
          userId);
      if (query.list().size() > 0) {
        String workingDay = query.list().get(0).toString();
        GregorianCalendar calender = new GregorianCalendar(
            Locale.CHINESE);
        calender.setTime(new Date(eventTime.longValue()));
        int week = calender.get(7);
        int dayOfMonth = calender.get(5);
        int month = calender.get(2);
        query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttender.empId =" + 
            
            userId + 
            " and eventAttender.eventIsEcho = 1" + (
            (domainId != null) ? (
            " and event.eventDomainId=" + 
            String.valueOf(domainId.longValue())) : 
            ""));
        List<EventPO> list = query.list();
        for (int i = 0; i < list.size(); i++) {
          EventPO eventPO = list.get(i);
          if (eventPO.getOnTimeMode().equals(new Integer(1))) {
            setResultByDay(eventTime, result, workingDay, week, 
                eventPO);
          } else if (eventPO.getEchoMode().equals(new Integer(2))) {
            setResultByweek(eventTime, result, week, eventPO);
          } else if (!eventPO.getEchoMode().equals(new Integer(3))) {
            eventPO.getEchoMode().equals(new Integer(4));
          } 
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("select Event by Time Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void setResultByweek(Long eventTime, List result, int week, EventPO eventPO) {
    if (eventPO.getEventContent().charAt(week - 1) == '1')
      if (eventPO.getEchoMode().equals(new Integer(0))) {
        addEventToList(result, eventPO);
      } else if (eventTime.longValue() >= eventPO.getEchoBeginTime().getTime() && 
        eventTime.longValue() <= eventPO.getEchoEndTime().getTime()) {
        addEventToList(result, eventPO);
      }  
  }
  
  private void addEventToList(List<EventVO> result, EventPO eventPO) {
    EventVO event = 
      (EventVO)TransformObject.getInstance()
      .transformObject(eventPO, 
        EventVO.class);
    result.add(event);
  }
  
  private void setResultByDay(Long eventTime, List result, String workingDay, int week, EventPO eventPO) {
    if (eventPO.getEventContent().equals("1")) {
      if (eventPO.getEchoMode().equals(new Integer(-1))) {
        if (workingDay.charAt(week - 1) == '1')
          addEventToList(result, eventPO); 
      } else if (eventTime.longValue() >= eventPO.getEchoBeginTime().getTime() && 
        eventTime.longValue() <= eventPO.getEchoEndTime().getTime()) {
        addEventToList(result, eventPO);
      } 
    } else if (eventPO.getEchoMode().equals(new Integer(-1))) {
      addEventToList(result, eventPO);
    } else if (eventTime.longValue() >= eventPO.getEchoBeginTime().getTime() && 
      eventTime.longValue() <= eventPO.getEchoEndTime().getTime()) {
      addEventToList(result, eventPO);
    } 
  }
  
  public List searchEvent(Long userId, Integer currentPage, Integer volume, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle) throws HibernateException {
    return searchEvent(userId, currentPage, volume, content, domainId, startTime, 
        endTime, startDate, endDate, eventType, eventTitle, (String)null);
  }
  
  public List searchEvent(Long userId, Integer currentPage, Integer volume, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, int flag) throws HibernateException {
    return searchEvent(userId, currentPage, volume, content, domainId, startTime, 
        endTime, startDate, endDate, eventType, eventTitle, (String)null, flag);
  }
  
  public List searchEvent(Long userId, Integer currentPage, Integer volume, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String toUserId) throws HibernateException {
    return searchEvent(userId, currentPage, volume, content, domainId, 
        startTime, endTime, startDate, endDate, eventType, eventTitle, toUserId, 0);
  }
  
  public List searchEvent(Long userId, Integer currentPage, Integer volume, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String toUserId, int flagInt) throws HibernateException {
    List<EventVO> result = new ArrayList();
    String databaseType = 
      SystemCommon.getDatabaseType();
    StringBuffer sb = new StringBuffer("");
    int type = -1;
    if (eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)) {
      type = Integer.parseInt(eventType);
      if (type == 0) {
        sb.append(" and event.onTimeMode=0 ");
      } else if (type == 1) {
        sb.append(" and event.onTimeMode!=0 ");
      } 
    } 
    if (startTime != null)
      if (type == 0) {
        sb.append(" and ( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
      } else if (type == 1) {
        sb.append(" and ( ");
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(" (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(") ");
      } else {
        sb.append(" and (( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
        if (startTime != null || endTime != null)
          sb.append(" or ( "); 
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(" (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(")) ");
      }  
    boolean flag = false;
    if (startTime != null || (
      startTime == null && eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)))
      flag = true; 
    String toString = "";
    if (toUserId != null)
      toString = " and event.eventEmpId=" + toUserId + " "; 
    try {
      begin();
      Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId = " + 
          userId + ((
          "qhcsj".equals(SystemCommon.getCustomerName()) && flagInt == 1) ? " and event.openEvent=1 " : "") + 
          toString + ((
          eventTitle != null && !"".equals(eventTitle) && !"null".equalsIgnoreCase(eventTitle)) ? (
          " and event.eventTitle like '%" + eventTitle + "%'") : "") + ((
          content != null && !"".equals(content) && !"null".equalsIgnoreCase(content)) ? (
          " and event.eventContent like '%" + content + "%'") : "") + (
          
          flag ? sb.toString() : " ") + (
          (domainId != null) ? (
          " and event.eventDomainId=" + 
          String.valueOf(domainId.intValue())) : 
          "") + 
          "  order by event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc");
      query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
      query.setMaxResults(volume.intValue());
      List list = query.list();
      Iterator<EventPO> iterator = list.iterator();
      EventPO event = null;
      while (iterator != null && iterator.hasNext()) {
        event = iterator.next();
        EventVO eventVO = (EventVO)TransformObject.getInstance()
          .transformObject(event, EventVO.class);
        Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            
            eventVO.getEventId() + 
            " and eventAttender.empId =" + 
            userId);
        List<EventAttenderPO> list1 = query1.list();
        eventVO.setIsViewed(Boolean.FALSE);
        eventVO.setIsAffirmRemind(Boolean.FALSE);
        if (list1 != null) {
          EventAttenderPO eventAttender = list1.get(
              0);
          if (eventAttender.getEventIsViewed() != null && 
            eventAttender.getEventIsViewed().intValue() == 1)
            eventVO.setIsViewed(Boolean.TRUE); 
          if (eventAttender.getAffirmRemind() != null && 
            eventAttender.getAffirmRemind().intValue() == 1)
            eventVO.setIsAffirmRemind(Boolean.TRUE); 
        } 
        eventVO.setCanDelete(Boolean.FALSE);
        eventVO.setCanModify(Boolean.FALSE);
        eventVO.setIsShare(Boolean.FALSE);
        if (event.getAttendEmp() != null && !event.getAttendEmp().equals("")) {
          eventVO.setCanDelete(Boolean.TRUE);
          eventVO.setIsShare(Boolean.TRUE);
        } else {
          eventVO.setCanDelete(Boolean.TRUE);
          eventVO.setCanModify(Boolean.TRUE);
        } 
        result.add(eventVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select All Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchEventByName(String userId, Integer currentPage, Integer volume, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String empName) throws HibernateException {
    return searchEventByName(userId, currentPage, volume, content, domainId, startTime, 
        endTime, startDate, endDate, eventType, eventTitle, empName, 0);
  }
  
  public List searchEventByName(String userId, Integer currentPage, Integer volume, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String empName, int flagInt) throws HibernateException {
    List<EventVO> result = new ArrayList();
    String databaseType = 
      SystemCommon.getDatabaseType();
    StringBuffer sb = new StringBuffer("");
    int type = -1;
    if (eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)) {
      type = Integer.parseInt(eventType);
      if (type == 0) {
        sb.append(" and event.onTimeMode=0 ");
      } else if (type == 1) {
        sb.append(" and event.onTimeMode!=0 ");
      } 
    } 
    if (startTime != null)
      if (type == 0) {
        sb.append(" and ( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
      } else if (type == 1) {
        sb.append(" and ( ");
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(
                " (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(") ");
      } else {
        sb.append(" and (( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
        if (startTime != null || endTime != null)
          sb.append(" or ( "); 
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(
                " (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(")) ");
      }  
    boolean flag = false;
    if (startTime != null || (
      startTime == null && eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)))
      flag = true; 
    try {
      begin();
      Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId in(" + 
          userId + ")" + ((
          "qhcsj".equals(SystemCommon.getCustomerName()) && 1 == flagInt) ? " and event.openEvent=1 " : "") + (
          flag ? sb.toString() : " ") + (
          (domainId != null) ? (
          " and event.eventDomainId=" + 
          String.valueOf(domainId.intValue())) : 
          "") + "  order by event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc");
      query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
      query.setMaxResults(volume.intValue());
      List list = query.list();
      Iterator<EventPO> iterator = list.iterator();
      EventPO event = null;
      while (iterator != null && iterator.hasNext()) {
        event = iterator.next();
        EventVO eventVO = (EventVO)TransformObject.getInstance()
          .transformObject(event, EventVO.class);
        Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            
            eventVO.getEventId() + 
            " and eventAttender.empId in(" + userId + ")");
        List<EventAttenderPO> list1 = query1.list();
        eventVO.setIsViewed(Boolean.FALSE);
        eventVO.setIsAffirmRemind(Boolean.FALSE);
        if (list1 != null) {
          EventAttenderPO eventAttender = list1.get(
              0);
          if (eventAttender.getEventIsViewed() != null && 
            eventAttender.getEventIsViewed().intValue() == 1)
            eventVO.setIsViewed(Boolean.TRUE); 
          if (eventAttender.getAffirmRemind() != null && 
            eventAttender.getAffirmRemind().intValue() == 1)
            eventVO.setIsAffirmRemind(Boolean.TRUE); 
        } 
        eventVO.setCanDelete(Boolean.FALSE);
        eventVO.setCanModify(Boolean.FALSE);
        eventVO.setIsShare(Boolean.FALSE);
        int i = 0;
        if (i < (userId.split(",")).length)
          if (event.getAttendEmp() != null && !event.getAttendEmp().equals("")) {
            eventVO.setCanDelete(Boolean.TRUE);
            eventVO.setIsShare(Boolean.TRUE);
          } else {
            eventVO.setCanDelete(Boolean.TRUE);
            eventVO.setCanModify(Boolean.TRUE);
          }  
        result.add(eventVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select All Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getSearchEventRecordCount(Long userId, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, int flag) throws HibernateException {
    return getSearchEventRecordCount(userId, content, domainId, startTime, endTime, startDate, endDate, eventType, eventTitle, (String)null, flag);
  }
  
  public Integer getSearchEventRecordCount(Long userId, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle) throws HibernateException {
    return getSearchEventRecordCount(userId, content, domainId, startTime, endTime, startDate, endDate, eventType, eventTitle, (String)null, 0);
  }
  
  public Integer getSearchEventRecordCount(Long userId, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String toUserId) throws HibernateException {
    return getSearchEventRecordCount(userId, content, domainId, startTime, endTime, startDate, endDate, eventType, eventTitle, toUserId, 0);
  }
  
  public Integer getSearchEventRecordCount(Long userId, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String toUserId, int flagInt) throws HibernateException {
    Integer result = new Integer(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    StringBuffer sb = new StringBuffer("");
    int type = -1;
    if (eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)) {
      type = Integer.parseInt(eventType);
      if (type == 0) {
        sb.append(" and event.onTimeMode=0 ");
      } else if (type == 1) {
        sb.append(" and event.onTimeMode!=0 ");
      } 
    } 
    if (startTime != null)
      if (type == 0) {
        sb.append(" and ( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
      } else if (type == 1) {
        sb.append(" and ( ");
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(
                " (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(") ");
      } else {
        sb.append(" and (( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
        if (startTime != null || endTime != null)
          sb.append(" or ( "); 
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(
                " (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(")) ");
      }  
    boolean flag = false;
    if (startTime != null || (
      startTime == null && eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)))
      flag = true; 
    String toString = "";
    if (toUserId != null)
      toString = " and event.eventEmpId=" + toUserId + " "; 
    try {
      begin();
      result = this.session.iterate("select count(event.eventId) from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId = " + 
          
          userId + toString + 
          " and (event.eventTitle like '%" + (
          (eventTitle == null) ? "" : eventTitle) + 
          "%' and event.eventContent like '%" + (
          (content == null) ? "" : content) + "%') " + ((
          "qhcsj".equals(SystemCommon.getCustomerName()) && flagInt == 1) ? " and event.openEvent=1 " : "") + (
          flag ? sb.toString() : " ") + (
          (domainId != null) ? (
          " and event.eventDomainId=" + 
          String.valueOf(domainId
            .intValue())) : 
          ""))
        .next();
    } catch (HibernateException ex) {
      System.out.println("get Event Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getSearchEventRecordCountByName(String userId, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String empName) throws HibernateException {
    return getSearchEventRecordCountByName(userId, content, domainId, startTime, endTime, 
        startDate, endDate, eventType, eventTitle, empName, 0);
  }
  
  public Integer getSearchEventRecordCountByName(String userId, String content, Long domainId, Long startTime, Long endTime, String startDate, String endDate, String eventType, String eventTitle, String empName, int flagInt) throws HibernateException {
    Integer result = new Integer(0);
    String databaseType = 
      SystemCommon.getDatabaseType();
    StringBuffer sb = new StringBuffer("");
    int type = -1;
    if (eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)) {
      type = Integer.parseInt(eventType);
      if (type == 0) {
        sb.append(" and event.onTimeMode=0 ");
      } else if (type == 1) {
        sb.append(" and event.onTimeMode!=0 ");
      } 
    } 
    if (startTime != null)
      if (type == 0) {
        sb.append(" and ( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
      } else if (type == 1) {
        sb.append(" and ( ");
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(
                " (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(") ");
      } else {
        sb.append(" and (( ");
        if (startTime != null) {
          sb.append(" (event.onTimeMode=0 and event.eventBeginDate>=");
          sb.append(startTime);
          sb.append(") ");
        } 
        if (endTime != null) {
          if (startTime != null)
            sb.append(" and "); 
          sb.append(" (event.onTimeMode=0 and event.eventEndDate<=");
          sb.append(endTime);
          sb.append(") ");
        } 
        sb.append(") ");
        if (startTime != null || endTime != null)
          sb.append(" or ( "); 
        if (startDate != null) {
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and to_char(event.echoBeginTime, 'yyyy-MM-dd')>='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and char(event.echoBeginTime)>='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(
                " (event.onTimeMode!=0 and event.echoBeginTime>='");
          } else {
            sb.append(
                " (event.onTimeMode!=0 and convert(char(10),event.echoBeginTime,20)>='");
          } 
          sb.append(startDate);
          sb.append("') ");
        } 
        if (endDate != null) {
          if (startDate != null)
            sb.append(" and "); 
          if (databaseType.indexOf("oracle") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and to_char(event.echoEndTime, 'yyyy-MM-dd')<='");
          } else if (databaseType.indexOf("db2") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and char(event.echoEndTime)<='");
          } else if (databaseType.indexOf("mysql") > -1) {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and event.echoEndTime<='");
          } else {
            sb.append(" (event.onTimeMode!=0 and (event.echoMode=-1 or (event.echoMode!=-1 and convert(char(10),event.echoEndTime,20)<='");
          } 
          sb.append(endDate);
          sb.append("'))) ");
        } 
        sb.append(")) ");
      }  
    boolean flag = false;
    if (startTime != null || (
      startTime == null && eventType != null && !"".equals(eventType) && 
      !"null".equals(eventType)))
      flag = true; 
    try {
      begin();
      result = this.session.iterate("select  count(event.eventId) from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId in(" + 
          userId + " ) " + ((
          "qhcsj".equals(SystemCommon.getCustomerName()) && 1 == flagInt) ? " and event.openEvent=1 " : "") + (
          flag ? sb.toString() : " ") + (
          (domainId != null) ? (" and event.eventDomainId=" + String.valueOf(domainId.intValue())) : "")).next();
    } catch (HibernateException ex) {
      System.out.println("get Event Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List getPopRemindEvent(Long userId, Long time, Long domainId) throws HibernateException {
    List<EventVO> result = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId =" + 
          
          userId + 
          " and eventAttender.eventIsEcho =0 " + 
          " and eventAttender.eventIsPoped != 1 " + 
          
          " and event.eventRemind=1 " + 
          " and (event.eventBeginDate + event.eventBeginTime*1000 - event.eventRemindTime*1000)<=" + 
          time + 
          " and (event.eventBeginDate + event.eventBeginTime*1000 - " + 
          time + ")>0" + 
          " and event.eventEndDate + event.eventEndTime*1000>=" + 
          time + (
          (domainId != null) ? (
          " and event.eventDomainId = " + 
          domainId) : "") + 
          " order by event.eventBeginDate desc,event.eventBeginTime");
      List<EventPO> list = query.list();
      EventVO event = null;
      EventPO eventPO = null;
      for (int i = 0; i < list.size(); i++) {
        eventPO = list.get(i);
        event = (EventVO)TransformObject.getInstance().transformObject(
            eventPO, EventVO.class);
        Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            
            event.getEventId() + 
            " and eventAttender.empId =" + 
            userId + (
            (domainId != null) ? (
            " and eventAttender.eventDomainId = " + domainId) : ""));
        List<EventAttenderPO> list1 = query1.list();
        event.setIsViewed(Boolean.FALSE);
        event.setIsAffirmRemind(Boolean.FALSE);
        if (list1 != null && list1.size() > 0) {
          EventAttenderPO eventAttender = list1.get(
              0);
          if (eventAttender.getEventIsViewed().intValue() == 1)
            event.setIsViewed(Boolean.TRUE); 
          if (eventAttender.getAffirmRemind().intValue() == 1)
            event.setIsAffirmRemind(Boolean.TRUE); 
        } 
        event.setCanDelete(Boolean.FALSE);
        event.setCanModify(Boolean.FALSE);
        event.setIsShare(Boolean.FALSE);
        if (event.getEventEmpId().equals(userId)) {
          event.setCanDelete(Boolean.TRUE);
          event.setCanModify(Boolean.TRUE);
        } else {
          event.setCanDelete(Boolean.TRUE);
          event.setIsShare(Boolean.TRUE);
        } 
        result.add(event);
      } 
    } catch (HibernateException ex) {
      System.out.println("get Event Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer popedEvent(Long eventId, Long userId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      Query query = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
          
          eventId + 
          " and eventAttender.empId =" + 
          userId);
      List<EventAttenderPO> list = query.list();
      if (list != null) {
        EventAttenderPO eventAttenderPO = list.get(0);
        eventAttenderPO.setEventIsPoped(new Integer(1));
        this.session.update(eventAttenderPO);
      } 
      this.session.flush();
      result = new Integer(1);
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("modify Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Map getAllRemindEvents(String userIds, String domainId, Long time) throws HibernateException {
    Map<Object, Object> result = new HashMap<Object, Object>();
    String databaseType = 
      SystemCommon.getDatabaseType();
    String substr = "";
    String tonumber = "";
    String sysdate = "";
    if (databaseType.indexOf("oracle") > -1) {
      substr = "substr";
      tonumber = "to_number(";
      sysdate = "sysdate";
    } else if (databaseType.indexOf("db2") >= 0) {
      substr = "substr";
      tonumber = "cast(";
      sysdate = "current date";
    } else if (databaseType.indexOf("mysql") >= 0) {
      substr = "substring";
      tonumber = "cast(";
      sysdate = "NOW()";
    } else {
      substr = "substring";
      tonumber = "convert(int,";
      sysdate = "getdate()";
    } 
    GregorianCalendar c = new GregorianCalendar(Locale.CHINESE);
    int day_of_week = c.get(7);
    int day_of_month = c.get(5);
    int month = c.get(2) + 1;
    long begin_time = (new Date(c.get(1) - 1900, 
        c.get(2), 
        c.get(5))).getTime();
    StringBuffer buffer = new StringBuffer();
    buffer.append("select eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender ")
      .append("where eventAttender.empId in (").append(userIds)
      .append(")")
      .append(" and eventAttender.event.eventRemind = 1")
      
      .append(" and ((eventAttender.event.eventFullDay = 1")
      .append("       and (eventAttender.event.eventBeginDate - eventAttender.event.eventRemindTime*1000)<=" + 
        time)
      .append("       and (eventAttender.event.eventBeginDate - " + 
        time + ")>0")
      .append("      ) or (eventAttender.event.eventFullDay = 0")
      .append("            and ((eventAttender.event.onTimeMode = 0")
      .append("                  and (eventAttender.event.eventBeginDate + eventAttender.event.eventBeginTime*1000 - eventAttender.event.eventRemindTime*1000)<=" + 
        time)
      .append("                  and (eventAttender.event.eventBeginDate + eventAttender.event.eventBeginTime*1000 - " + 
        time + ")>0")
      .append(
        "                 ) or (eventAttender.event.onTimeMode in (1,2,3,4)")
      .append(
        "                       and (eventAttender.event.echoMode = -1")
      .append(
        "                            or (eventAttender.event.echoMode != -1")

      
      .append("                               )")
      .append("                           )")


      
      .append("                       and (" + begin_time + " + eventAttender.event.eventBeginTime*1000 - eventAttender.event.eventRemindTime*1000)<=" + 
        time)
      .append("                       and (" + begin_time + 
        " + eventAttender.event.eventBeginTime*1000 - " + time + 
        ")>0")
      
      .append(
        "                       and ((eventAttender.event.onTimeMode = 1")


      
      .append(
        "                            ) or (eventAttender.event.onTimeMode = 2")
      .append("                                  and " + substr + 
        "(eventAttender.event.onTimeContent," + day_of_week + 
        ",1) = '1'")


      
      .append(
        "                            ) or (eventAttender.event.onTimeMode = 3");
    if (databaseType.indexOf("db2") >= 0) {
      buffer.append("                                  and " + tonumber + 
          "eventAttender.event.onTimeContent as int) = " + 
          day_of_month);
    } else if (databaseType.indexOf("mysql") >= 0) {
      buffer.append("                                  and " + tonumber + 
          "eventAttender.event.onTimeContent as decimal) = " + 
          day_of_month);
    } else {
      buffer.append("                                  and " + tonumber + 
          "eventAttender.event.onTimeContent) = " + 
          day_of_month);
    } 
    buffer.append(
        "                            ) or (eventAttender.event.onTimeMode = 4");
    if (databaseType.indexOf("db2") >= 0) {
      buffer.append("                                  and " + tonumber + 
          substr + 
          "(eventAttender.event.onTimeContent,1,2) as int) = " + 
          month);
      buffer.append("                                  and " + tonumber + 
          substr + 
          "(eventAttender.event.onTimeContent,4,4) as int) = " + 
          day_of_month);
    } else if (databaseType.indexOf("mysql") >= 0) {
      buffer.append("                                  and " + tonumber + 
          substr + 
          "(eventAttender.event.onTimeContent,1,2) as decimal) = " + 
          month);
      buffer.append("                                  and " + tonumber + 
          substr + 
          "(eventAttender.event.onTimeContent,4,4) as decimal) = " + 
          day_of_month);
    } else {
      buffer.append("                                  and " + tonumber + 
          substr + 
          "(eventAttender.event.onTimeContent,1,2)) = " + month);
      buffer.append("                                  and " + tonumber + 
          substr + 
          "(eventAttender.event.onTimeContent,4,4)) = " + 
          day_of_month);
    } 
    buffer.append("                            )")
      .append("                           )")
      .append("                 )")
      .append("                )")
      .append("      )")
      .append("     )")
      
      .append(" order by eventAttender.empId,eventAttender.event.eventBeginDate desc,eventAttender.event.eventBeginTime");
    try {
      begin();
      Query query2 = this.session.createQuery(buffer.toString());
      List<EventAttenderPO> list2 = query2.list();
      EventAttenderPO eventAttenderPO = null;
      EventPO eventPO = null;
      String oldId = "";
      List<EventPO> list = new ArrayList();
      for (int i = 0; i < list2.size(); i++) {
        eventAttenderPO = list2.get(i);
        eventPO = eventAttenderPO.getEvent();
        String empId = eventAttenderPO.getEmpId().toString();
        if (!oldId.equals(empId)) {
          result.put(oldId, list);
          list = new ArrayList();
          list.add(eventPO);
          oldId = empId;
        } else {
          list.add(eventPO);
        } 
      } 
      if (list2.size() > 0)
        result.put(oldId, list); 
    } catch (HibernateException ex) {
      System.out.println("get Event Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } catch (Exception err) {
      err.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List getDDEvents(Long userId, String beginDate, String endDate, Long domainId, String evenTitle, String evenContent) throws HibernateException {
    return getDDEvents(userId, beginDate, endDate, domainId, evenTitle, evenContent, 0);
  }
  
  public List getDDEvents(Long userId, String beginDate, String endDate, Long domainId, String evenTitle, String evenContent, int flag) throws HibernateException {
    List<EventVO> result = new ArrayList();
    String databaseType = 
      SystemCommon.getDatabaseType();
    int begin_y = Integer.parseInt(beginDate.substring(0, 
          beginDate.indexOf("-")));
    int begin_m = Integer.parseInt(beginDate.substring(beginDate.indexOf(
            "-") + 1, beginDate.lastIndexOf("-")));
    int begin_d = Integer.parseInt(beginDate.substring(beginDate
          .lastIndexOf("-") + 1));
    long begin_time = (new Date(begin_y - 1900, begin_m - 1, begin_d))
      .getTime();
    int end_y = Integer.parseInt(endDate.substring(0, endDate.indexOf("-")));
    int end_m = Integer.parseInt(endDate.substring(endDate.indexOf("-") + 1, 
          endDate.lastIndexOf("-")));
    int end_d = Integer.parseInt(endDate.substring(endDate.lastIndexOf("-") + 
          1));
    long end_time = (new Date(end_y - 1900, end_m - 1, end_d)).getTime();
    StringBuffer sb = new StringBuffer();
    sb.append("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender ");
    sb.append(" where " + (("qhcsj".equals(SystemCommon.getCustomerName()) && 1 == flag) ? (
        " (event.eventEmpId=" + userId + " or event.attendEmp like '%$" + userId + "$%') and event.openEvent=1 ") : (
        " eventAttender.empId =" + userId)) + " ");
    sb.append("   and ((event.onTimeMode=0");
    sb.append("        and (" + begin_time + "<=event.eventEndDate");
    sb.append("              or (event.eventBeginDate<=" + end_time + 
        " and " + end_time + "<=event.eventEndDate)))");
    sb.append("    or (event.onTimeMode>0");
    if (databaseType.indexOf("oracle") > -1) {
      sb.append(
          "        and to_char(event.echoBeginTime, 'yyyy-MM-dd')<= '" + 
          endDate + "'))");
    } else if (databaseType.indexOf("db2") >= 0) {
      sb.append("        and char(event.echoBeginTime)<= '" + endDate + 
          "'))");
    } else if (databaseType.indexOf("mysql") >= 0) {
      sb.append(
          "        and event.echoBeginTime<= '" + 
          endDate + "'))");
    } else {
      sb.append(
          "        and convert(char(10),event.echoBeginTime,20)<= '" + 
          endDate + "'))");
    } 
    sb.append((domainId != null) ? (
        " and event.eventDomainId=" + 
        String.valueOf(domainId.intValue())) : "");
    if (evenTitle != null && !evenTitle.equals(""))
      sb.append(" and event.eventTitle like '%" + evenTitle + "%'"); 
    if (evenContent != null && !evenContent.equals(""))
      sb.append(" and event.eventContent like '%" + evenContent + "%'"); 
    sb.append(" order by event.eventFullDay desc, event.eventBeginTime asc");
    try {
      begin();
      Query query = this.session.createQuery(sb.toString());
      List<EventPO> list = query.list();
      EventVO event = null;
      EventPO eventPO = null;
      for (int i = 0; i < list.size(); i++) {
        eventPO = list.get(i);
        event = (EventVO)TransformObject.getInstance().transformObject(
            eventPO, EventVO.class);
        Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            
            event.getEventId() + 
            " and eventAttender.empId =" + 
            userId);
        List<EventAttenderPO> list1 = query1.list();
        event.setIsViewed(Boolean.FALSE);
        event.setIsAffirmRemind(Boolean.FALSE);
        if (list1 != null) {
          EventAttenderPO eventAttender = list1.get(
              0);
          if (eventAttender.getEventIsViewed() != null && 
            eventAttender.getEventIsViewed().intValue() == 1)
            event.setIsViewed(Boolean.TRUE); 
          if (eventAttender.getAffirmRemind() != null && 
            eventAttender.getAffirmRemind().intValue() == 1)
            event.setIsAffirmRemind(Boolean.TRUE); 
        } 
        event.setCanDelete(Boolean.FALSE);
        event.setCanModify(Boolean.FALSE);
        event.setIsShare(Boolean.FALSE);
        if (event.getEventEmpId().equals(userId)) {
          event.setCanDelete(Boolean.TRUE);
          event.setCanModify(Boolean.TRUE);
        } else {
          event.setCanDelete(Boolean.TRUE);
          event.setIsShare(Boolean.TRUE);
        } 
        result.add(event);
      } 
    } catch (HibernateException ex) {
      System.out.println("getDDEvents Exception: " + 
          ex.getMessage());
      throw ex;
    } catch (Exception err) {
      err.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectAllEmpEvent(String userIds, Integer currentPage, Integer volume, Long domainId, String eventTitle, String eventContent) throws HibernateException {
    return selectAllEmpEvent(userIds, currentPage, volume, domainId, eventTitle, eventContent, 0);
  }
  
  public List selectAllEmpEvent(String userIds, Integer currentPage, Integer volume, Long domainId, String eventTitle, String eventContent, int flag) throws HibernateException {
    List<EventVO> result = new ArrayList();
    try {
      begin();
      StringBuffer appendSql = new StringBuffer("");
      if (eventTitle != null && !eventTitle.equals(""))
        appendSql.append(" and event.eventTitle like '%" + eventTitle + "%'"); 
      if (eventContent != null && !eventContent.equals(""))
        appendSql.append(" and event.eventContent like '%" + eventContent + "%'"); 
      Query query = this.session.createQuery("select distinct event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId in (" + 
          
          userIds + ")" + ((
          "qhcsj".equals(SystemCommon.getCustomerName()) && flag == 1) ? " and event.openEvent=1 " : "") + (
          (domainId != null) ? (
          " and event.eventDomainId=" + 
          String.valueOf(domainId.intValue())) : 
          "") + appendSql.toString() + 
          " order by event.eventBeginDate desc,event.eventBeginTime,event.echoBeginTime desc,event.echoEndTime desc");
      query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
      query.setMaxResults(volume.intValue());
      List list = query.list();
      Iterator<EventPO> iterator = list.iterator();
      EventPO event = null;
      while (iterator != null && iterator.hasNext()) {
        event = iterator.next();
        EventVO eventVO = (EventVO)TransformObject.getInstance()
          .transformObject(event, EventVO.class);
        Query query1 = this.session.createQuery("select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = " + 
            
            eventVO.getEventId() + 
            " and eventAttender.empId in (" + userIds + ")");
        List<EventAttenderPO> list1 = query1.list();
        eventVO.setIsViewed(Boolean.FALSE);
        eventVO.setIsAffirmRemind(Boolean.FALSE);
        if (list1 != null) {
          EventAttenderPO eventAttender = list1.get(
              0);
          if (eventAttender.getEventIsViewed() != null && 
            eventAttender.getEventIsViewed().intValue() == 1)
            eventVO.setIsViewed(Boolean.TRUE); 
          if (eventAttender.getAffirmRemind() != null && 
            eventAttender.getAffirmRemind().intValue() == 1)
            eventVO.setIsAffirmRemind(Boolean.TRUE); 
        } 
        eventVO.setCanDelete(Boolean.FALSE);
        eventVO.setCanModify(Boolean.FALSE);
        eventVO.setIsShare(Boolean.FALSE);
        result.add(eventVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select All Event Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getEventUderlingRecordCount(String userIds, Long domainId, String eventTitle, String evenContent) throws HibernateException {
    return getEventUderlingRecordCount(userIds, domainId, eventTitle, evenContent, 0);
  }
  
  public Integer getEventUderlingRecordCount(String userIds, Long domainId, String eventTitle, String evenContent, int flag) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      StringBuffer appendSql = new StringBuffer("");
      if (eventTitle != null && !eventTitle.equals(""))
        appendSql.append(" and event.eventTitle like '%" + eventTitle + "%'"); 
      if (evenContent != null && !evenContent.equals(""))
        appendSql.append(" and event.eventContent like '%" + evenContent + "%'"); 
      result = this.session.iterate("select count(distinct event.eventId) from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttenders where eventAttenders.empId in( " + 
          
          userIds + ")" + ((
          "qhcsj".equals(SystemCommon.getCustomerName()) && flag == 1) ? " and event.openEvent=1 " : "") + (
          (domainId != null) ? (
          " and event.eventDomainId=" + 
          String.valueOf(domainId.intValue())) : 
          "") + appendSql.toString()).next();
    } catch (HibernateException ex) {
      System.out.println("get Event Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String getByIds(Long id) throws HibernateException {
    String result = "N";
    try {
      begin();
      List list = this.session.createQuery("select event from com.js.oa.scheme.event.po.EventPO event where event.eventId =" + id).list();
      if (!list.isEmpty())
        result = "Y"; 
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private List selectCircleEvent1(Long userId, Long time, Long domainId) throws HibernateException {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #4
    //   9: new java/util/GregorianCalendar
    //   12: dup
    //   13: getstatic java/util/Locale.CHINESE : Ljava/util/Locale;
    //   16: invokespecial <init> : (Ljava/util/Locale;)V
    //   19: astore #5
    //   21: aload #5
    //   23: aload_2
    //   24: invokevirtual longValue : ()J
    //   27: invokevirtual setTimeInMillis : (J)V
    //   30: aload #5
    //   32: invokevirtual getTime : ()Ljava/util/Date;
    //   35: astore #6
    //   37: aload #5
    //   39: iconst_1
    //   40: invokevirtual get : (I)I
    //   43: istore #7
    //   45: aload #5
    //   47: iconst_2
    //   48: invokevirtual get : (I)I
    //   51: istore #8
    //   53: aload #5
    //   55: iconst_5
    //   56: invokevirtual get : (I)I
    //   59: istore #9
    //   61: aload #5
    //   63: bipush #7
    //   65: invokevirtual get : (I)I
    //   68: istore #10
    //   70: aload_0
    //   71: getfield session : Lnet/sf/hibernate/Session;
    //   74: new java/lang/StringBuilder
    //   77: dup
    //   78: ldc 'select event from com.js.oa.scheme.event.po.EventPO event join event.eventAttenders eventAttender where eventAttender.empId ='
    //   80: invokespecial <init> : (Ljava/lang/String;)V
    //   83: aload_1
    //   84: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   87: ldc_w ' and event.onTimeMode > 0'
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: aload_3
    //   94: ifnull -> 123
    //   97: new java/lang/StringBuilder
    //   100: dup
    //   101: ldc_w ' and event.eventDomainId='
    //   104: invokespecial <init> : (Ljava/lang/String;)V
    //   107: aload_3
    //   108: invokevirtual intValue : ()I
    //   111: invokestatic valueOf : (I)Ljava/lang/String;
    //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: invokevirtual toString : ()Ljava/lang/String;
    //   120: goto -> 125
    //   123: ldc ''
    //   125: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: invokevirtual toString : ()Ljava/lang/String;
    //   131: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   136: astore #11
    //   138: aload #11
    //   140: invokeinterface list : ()Ljava/util/List;
    //   145: astore #12
    //   147: aconst_null
    //   148: astore #13
    //   150: aconst_null
    //   151: astore #14
    //   153: aload_0
    //   154: getfield session : Lnet/sf/hibernate/Session;
    //   157: new java/lang/StringBuilder
    //   160: dup
    //   161: ldc_w 'select distinct optionset.workingDay from com.js.oa.personalwork.setup.po.OptionSetPO optionset where optionset.empId ='
    //   164: invokespecial <init> : (Ljava/lang/String;)V
    //   167: aload_1
    //   168: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   171: invokevirtual toString : ()Ljava/lang/String;
    //   174: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   179: astore #15
    //   181: aload #15
    //   183: invokeinterface list : ()Ljava/util/List;
    //   188: astore #16
    //   190: iconst_0
    //   191: istore #17
    //   193: goto -> 8902
    //   196: aload #12
    //   198: iload #17
    //   200: invokeinterface get : (I)Ljava/lang/Object;
    //   205: checkcast com/js/oa/scheme/event/po/EventPO
    //   208: astore #13
    //   210: aload #13
    //   212: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   215: invokevirtual intValue : ()I
    //   218: iconst_1
    //   219: if_icmpne -> 1584
    //   222: aload #13
    //   224: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   227: ldc_w '1'
    //   230: invokevirtual equals : (Ljava/lang/Object;)Z
    //   233: ifeq -> 820
    //   236: aload #16
    //   238: invokeinterface size : ()I
    //   243: ifle -> 8899
    //   246: aload #16
    //   248: iconst_0
    //   249: invokeinterface get : (I)Ljava/lang/Object;
    //   254: invokevirtual toString : ()Ljava/lang/String;
    //   257: astore #18
    //   259: aload #13
    //   261: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   264: aload #6
    //   266: invokevirtual compareTo : (Ljava/util/Date;)I
    //   269: ifgt -> 8899
    //   272: aload #13
    //   274: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   277: invokevirtual intValue : ()I
    //   280: iconst_m1
    //   281: if_icmpne -> 518
    //   284: aload #18
    //   286: iload #10
    //   288: invokevirtual charAt : (I)C
    //   291: bipush #49
    //   293: if_icmpne -> 8899
    //   296: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   299: aload #13
    //   301: ldc com/js/oa/scheme/event/vo/EventVO
    //   303: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   306: checkcast com/js/oa/scheme/event/vo/EventVO
    //   309: astore #14
    //   311: aload_0
    //   312: getfield session : Lnet/sf/hibernate/Session;
    //   315: new java/lang/StringBuilder
    //   318: dup
    //   319: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   321: invokespecial <init> : (Ljava/lang/String;)V
    //   324: aload #14
    //   326: invokevirtual getEventId : ()Ljava/lang/Long;
    //   329: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   332: ldc ' and eventAttender.empId ='
    //   334: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: aload_1
    //   338: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   341: invokevirtual toString : ()Ljava/lang/String;
    //   344: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   349: astore #19
    //   351: aload #19
    //   353: invokeinterface list : ()Ljava/util/List;
    //   358: astore #20
    //   360: aload #14
    //   362: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   365: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   368: aload #14
    //   370: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   373: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   376: aload #20
    //   378: ifnull -> 434
    //   381: aload #12
    //   383: iconst_0
    //   384: invokeinterface get : (I)Ljava/lang/Object;
    //   389: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   392: astore #21
    //   394: aload #21
    //   396: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   399: invokevirtual intValue : ()I
    //   402: iconst_1
    //   403: if_icmpne -> 414
    //   406: aload #14
    //   408: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   411: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   414: aload #21
    //   416: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   419: invokevirtual intValue : ()I
    //   422: iconst_1
    //   423: if_icmpne -> 434
    //   426: aload #14
    //   428: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   431: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   434: aload #14
    //   436: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   439: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   442: aload #14
    //   444: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   447: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   450: aload #14
    //   452: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   455: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   458: aload #14
    //   460: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   463: aload_1
    //   464: invokevirtual equals : (Ljava/lang/Object;)Z
    //   467: ifeq -> 489
    //   470: aload #14
    //   472: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   475: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   478: aload #14
    //   480: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   483: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   486: goto -> 505
    //   489: aload #14
    //   491: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   494: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   497: aload #14
    //   499: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   502: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   505: aload #4
    //   507: aload #14
    //   509: invokeinterface add : (Ljava/lang/Object;)Z
    //   514: pop
    //   515: goto -> 8899
    //   518: aload #13
    //   520: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   523: invokevirtual intValue : ()I
    //   526: ifne -> 776
    //   529: aload #13
    //   531: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   534: aload #6
    //   536: invokevirtual compareTo : (Ljava/util/Date;)I
    //   539: iflt -> 8899
    //   542: aload #18
    //   544: iload #10
    //   546: invokevirtual charAt : (I)C
    //   549: bipush #49
    //   551: if_icmpne -> 8899
    //   554: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   557: aload #13
    //   559: ldc com/js/oa/scheme/event/vo/EventVO
    //   561: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   564: checkcast com/js/oa/scheme/event/vo/EventVO
    //   567: astore #14
    //   569: aload_0
    //   570: getfield session : Lnet/sf/hibernate/Session;
    //   573: new java/lang/StringBuilder
    //   576: dup
    //   577: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   579: invokespecial <init> : (Ljava/lang/String;)V
    //   582: aload #14
    //   584: invokevirtual getEventId : ()Ljava/lang/Long;
    //   587: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   590: ldc ' and eventAttender.empId ='
    //   592: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   595: aload_1
    //   596: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   599: invokevirtual toString : ()Ljava/lang/String;
    //   602: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   607: astore #19
    //   609: aload #19
    //   611: invokeinterface list : ()Ljava/util/List;
    //   616: astore #20
    //   618: aload #14
    //   620: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   623: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   626: aload #14
    //   628: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   631: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   634: aload #20
    //   636: ifnull -> 692
    //   639: aload #12
    //   641: iconst_0
    //   642: invokeinterface get : (I)Ljava/lang/Object;
    //   647: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   650: astore #21
    //   652: aload #21
    //   654: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   657: invokevirtual intValue : ()I
    //   660: iconst_1
    //   661: if_icmpne -> 672
    //   664: aload #14
    //   666: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   669: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   672: aload #21
    //   674: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   677: invokevirtual intValue : ()I
    //   680: iconst_1
    //   681: if_icmpne -> 692
    //   684: aload #14
    //   686: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   689: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   692: aload #14
    //   694: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   697: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   700: aload #14
    //   702: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   705: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   708: aload #14
    //   710: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   713: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   716: aload #14
    //   718: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   721: aload_1
    //   722: invokevirtual equals : (Ljava/lang/Object;)Z
    //   725: ifeq -> 747
    //   728: aload #14
    //   730: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   733: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   736: aload #14
    //   738: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   741: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   744: goto -> 763
    //   747: aload #14
    //   749: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   752: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   755: aload #14
    //   757: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   760: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   763: aload #4
    //   765: aload #14
    //   767: invokeinterface add : (Ljava/lang/Object;)Z
    //   772: pop
    //   773: goto -> 8899
    //   776: aload #13
    //   778: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   781: invokevirtual intValue : ()I
    //   784: ifle -> 8899
    //   787: aload #13
    //   789: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   792: invokevirtual intValue : ()I
    //   795: istore #19
    //   797: aload #13
    //   799: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   802: invokevirtual getTime : ()J
    //   805: lstore #20
    //   807: aload #18
    //   809: iload #10
    //   811: iconst_1
    //   812: isub
    //   813: invokevirtual charAt : (I)C
    //   816: pop
    //   817: goto -> 8899
    //   820: aload #13
    //   822: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   825: aload #6
    //   827: invokevirtual compareTo : (Ljava/util/Date;)I
    //   830: ifgt -> 8899
    //   833: aload #13
    //   835: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   838: invokevirtual intValue : ()I
    //   841: iconst_m1
    //   842: if_icmpne -> 1067
    //   845: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   848: aload #13
    //   850: ldc com/js/oa/scheme/event/vo/EventVO
    //   852: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   855: checkcast com/js/oa/scheme/event/vo/EventVO
    //   858: astore #14
    //   860: aload_0
    //   861: getfield session : Lnet/sf/hibernate/Session;
    //   864: new java/lang/StringBuilder
    //   867: dup
    //   868: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   870: invokespecial <init> : (Ljava/lang/String;)V
    //   873: aload #14
    //   875: invokevirtual getEventId : ()Ljava/lang/Long;
    //   878: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   881: ldc ' and eventAttender.empId ='
    //   883: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   886: aload_1
    //   887: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   890: invokevirtual toString : ()Ljava/lang/String;
    //   893: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   898: astore #18
    //   900: aload #18
    //   902: invokeinterface list : ()Ljava/util/List;
    //   907: astore #19
    //   909: aload #14
    //   911: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   914: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   917: aload #14
    //   919: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   922: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   925: aload #19
    //   927: ifnull -> 983
    //   930: aload #19
    //   932: iconst_0
    //   933: invokeinterface get : (I)Ljava/lang/Object;
    //   938: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   941: astore #20
    //   943: aload #20
    //   945: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   948: invokevirtual intValue : ()I
    //   951: iconst_1
    //   952: if_icmpne -> 963
    //   955: aload #14
    //   957: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   960: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   963: aload #20
    //   965: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   968: invokevirtual intValue : ()I
    //   971: iconst_1
    //   972: if_icmpne -> 983
    //   975: aload #14
    //   977: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   980: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   983: aload #14
    //   985: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   988: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   991: aload #14
    //   993: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   996: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   999: aload #14
    //   1001: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1004: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1007: aload #14
    //   1009: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1012: aload_1
    //   1013: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1016: ifeq -> 1038
    //   1019: aload #14
    //   1021: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1024: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1027: aload #14
    //   1029: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1032: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1035: goto -> 1054
    //   1038: aload #14
    //   1040: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1043: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1046: aload #14
    //   1048: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1051: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1054: aload #4
    //   1056: aload #14
    //   1058: invokeinterface add : (Ljava/lang/Object;)Z
    //   1063: pop
    //   1064: goto -> 8899
    //   1067: aload #13
    //   1069: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1072: invokevirtual intValue : ()I
    //   1075: ifne -> 1313
    //   1078: aload #13
    //   1080: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   1083: aload #6
    //   1085: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1088: iflt -> 8899
    //   1091: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1094: aload #13
    //   1096: ldc com/js/oa/scheme/event/vo/EventVO
    //   1098: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1101: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1104: astore #14
    //   1106: aload_0
    //   1107: getfield session : Lnet/sf/hibernate/Session;
    //   1110: new java/lang/StringBuilder
    //   1113: dup
    //   1114: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1116: invokespecial <init> : (Ljava/lang/String;)V
    //   1119: aload #14
    //   1121: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1124: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1127: ldc ' and eventAttender.empId ='
    //   1129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1132: aload_1
    //   1133: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1136: invokevirtual toString : ()Ljava/lang/String;
    //   1139: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1144: astore #18
    //   1146: aload #18
    //   1148: invokeinterface list : ()Ljava/util/List;
    //   1153: astore #19
    //   1155: aload #14
    //   1157: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1160: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1163: aload #14
    //   1165: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1168: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1171: aload #19
    //   1173: ifnull -> 1229
    //   1176: aload #19
    //   1178: iconst_0
    //   1179: invokeinterface get : (I)Ljava/lang/Object;
    //   1184: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1187: astore #20
    //   1189: aload #20
    //   1191: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1194: invokevirtual intValue : ()I
    //   1197: iconst_1
    //   1198: if_icmpne -> 1209
    //   1201: aload #14
    //   1203: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1206: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1209: aload #20
    //   1211: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1214: invokevirtual intValue : ()I
    //   1217: iconst_1
    //   1218: if_icmpne -> 1229
    //   1221: aload #14
    //   1223: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1226: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1229: aload #14
    //   1231: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1234: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1237: aload #14
    //   1239: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1242: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1245: aload #14
    //   1247: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1250: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1253: aload #14
    //   1255: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1258: aload_1
    //   1259: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1262: ifeq -> 1284
    //   1265: aload #14
    //   1267: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1270: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1273: aload #14
    //   1275: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1278: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1281: goto -> 1300
    //   1284: aload #14
    //   1286: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1289: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1292: aload #14
    //   1294: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1297: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1300: aload #4
    //   1302: aload #14
    //   1304: invokeinterface add : (Ljava/lang/Object;)Z
    //   1309: pop
    //   1310: goto -> 8899
    //   1313: aload #13
    //   1315: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1318: invokevirtual intValue : ()I
    //   1321: ifle -> 8899
    //   1324: aload #13
    //   1326: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   1329: aload #13
    //   1331: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1334: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   1337: ifgt -> 8899
    //   1340: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1343: aload #13
    //   1345: ldc com/js/oa/scheme/event/vo/EventVO
    //   1347: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1350: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1353: astore #14
    //   1355: aload_0
    //   1356: getfield session : Lnet/sf/hibernate/Session;
    //   1359: new java/lang/StringBuilder
    //   1362: dup
    //   1363: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1365: invokespecial <init> : (Ljava/lang/String;)V
    //   1368: aload #14
    //   1370: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1373: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1376: ldc ' and eventAttender.empId ='
    //   1378: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1381: aload_1
    //   1382: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1385: invokevirtual toString : ()Ljava/lang/String;
    //   1388: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1393: astore #18
    //   1395: aload #18
    //   1397: invokeinterface list : ()Ljava/util/List;
    //   1402: astore #19
    //   1404: aload #14
    //   1406: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1409: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1412: aload #14
    //   1414: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1417: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1420: aload #19
    //   1422: ifnull -> 1478
    //   1425: aload #19
    //   1427: iconst_0
    //   1428: invokeinterface get : (I)Ljava/lang/Object;
    //   1433: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1436: astore #20
    //   1438: aload #20
    //   1440: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1443: invokevirtual intValue : ()I
    //   1446: iconst_1
    //   1447: if_icmpne -> 1458
    //   1450: aload #14
    //   1452: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1455: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1458: aload #20
    //   1460: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1463: invokevirtual intValue : ()I
    //   1466: iconst_1
    //   1467: if_icmpne -> 1478
    //   1470: aload #14
    //   1472: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1475: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1478: aload #14
    //   1480: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1483: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1486: aload #14
    //   1488: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1491: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1494: aload #14
    //   1496: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1499: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1502: aload #14
    //   1504: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1507: aload_1
    //   1508: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1511: ifeq -> 1533
    //   1514: aload #14
    //   1516: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1519: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1522: aload #14
    //   1524: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1527: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1530: goto -> 1549
    //   1533: aload #14
    //   1535: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1538: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1541: aload #14
    //   1543: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1546: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1549: aload #4
    //   1551: aload #14
    //   1553: invokeinterface add : (Ljava/lang/Object;)Z
    //   1558: pop
    //   1559: aload #13
    //   1561: new java/lang/Integer
    //   1564: dup
    //   1565: aload #13
    //   1567: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   1570: invokevirtual intValue : ()I
    //   1573: iconst_1
    //   1574: iadd
    //   1575: invokespecial <init> : (I)V
    //   1578: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   1581: goto -> 8899
    //   1584: aload #13
    //   1586: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   1589: invokevirtual intValue : ()I
    //   1592: iconst_2
    //   1593: if_icmpne -> 2437
    //   1596: aload #13
    //   1598: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1601: invokevirtual intValue : ()I
    //   1604: iconst_m1
    //   1605: if_icmpne -> 1860
    //   1608: aload #13
    //   1610: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   1613: aload #6
    //   1615: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1618: ifgt -> 8899
    //   1621: aload #13
    //   1623: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   1626: iload #10
    //   1628: iconst_1
    //   1629: isub
    //   1630: invokevirtual charAt : (I)C
    //   1633: bipush #49
    //   1635: if_icmpne -> 8899
    //   1638: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1641: aload #13
    //   1643: ldc com/js/oa/scheme/event/vo/EventVO
    //   1645: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1648: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1651: astore #14
    //   1653: aload_0
    //   1654: getfield session : Lnet/sf/hibernate/Session;
    //   1657: new java/lang/StringBuilder
    //   1660: dup
    //   1661: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1663: invokespecial <init> : (Ljava/lang/String;)V
    //   1666: aload #14
    //   1668: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1671: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1674: ldc ' and eventAttender.empId ='
    //   1676: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1679: aload_1
    //   1680: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1683: invokevirtual toString : ()Ljava/lang/String;
    //   1686: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1691: astore #18
    //   1693: aload #18
    //   1695: invokeinterface list : ()Ljava/util/List;
    //   1700: astore #19
    //   1702: aload #14
    //   1704: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1707: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1710: aload #14
    //   1712: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1715: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1718: aload #19
    //   1720: ifnull -> 1776
    //   1723: aload #19
    //   1725: iconst_0
    //   1726: invokeinterface get : (I)Ljava/lang/Object;
    //   1731: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   1734: astore #20
    //   1736: aload #20
    //   1738: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   1741: invokevirtual intValue : ()I
    //   1744: iconst_1
    //   1745: if_icmpne -> 1756
    //   1748: aload #14
    //   1750: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1753: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1756: aload #20
    //   1758: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   1761: invokevirtual intValue : ()I
    //   1764: iconst_1
    //   1765: if_icmpne -> 1776
    //   1768: aload #14
    //   1770: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1773: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1776: aload #14
    //   1778: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1781: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1784: aload #14
    //   1786: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1789: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1792: aload #14
    //   1794: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1797: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1800: aload #14
    //   1802: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   1805: aload_1
    //   1806: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1809: ifeq -> 1831
    //   1812: aload #14
    //   1814: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1817: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1820: aload #14
    //   1822: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1825: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   1828: goto -> 1847
    //   1831: aload #14
    //   1833: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1836: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1839: aload #14
    //   1841: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1844: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   1847: aload #4
    //   1849: aload #14
    //   1851: invokeinterface add : (Ljava/lang/Object;)Z
    //   1856: pop
    //   1857: goto -> 8899
    //   1860: aload #13
    //   1862: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   1865: invokevirtual intValue : ()I
    //   1868: ifne -> 2136
    //   1871: aload #13
    //   1873: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   1876: aload #6
    //   1878: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1881: ifgt -> 8899
    //   1884: aload #13
    //   1886: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   1889: iload #10
    //   1891: iconst_1
    //   1892: isub
    //   1893: invokevirtual charAt : (I)C
    //   1896: bipush #49
    //   1898: if_icmpne -> 8899
    //   1901: aload #13
    //   1903: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   1906: aload #6
    //   1908: invokevirtual compareTo : (Ljava/util/Date;)I
    //   1911: iflt -> 8899
    //   1914: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1917: aload #13
    //   1919: ldc com/js/oa/scheme/event/vo/EventVO
    //   1921: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1924: checkcast com/js/oa/scheme/event/vo/EventVO
    //   1927: astore #14
    //   1929: aload_0
    //   1930: getfield session : Lnet/sf/hibernate/Session;
    //   1933: new java/lang/StringBuilder
    //   1936: dup
    //   1937: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   1939: invokespecial <init> : (Ljava/lang/String;)V
    //   1942: aload #14
    //   1944: invokevirtual getEventId : ()Ljava/lang/Long;
    //   1947: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1950: ldc ' and eventAttender.empId ='
    //   1952: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1955: aload_1
    //   1956: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1959: invokevirtual toString : ()Ljava/lang/String;
    //   1962: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1967: astore #18
    //   1969: aload #18
    //   1971: invokeinterface list : ()Ljava/util/List;
    //   1976: astore #19
    //   1978: aload #14
    //   1980: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1983: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   1986: aload #14
    //   1988: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1991: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   1994: aload #19
    //   1996: ifnull -> 2052
    //   1999: aload #19
    //   2001: iconst_0
    //   2002: invokeinterface get : (I)Ljava/lang/Object;
    //   2007: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2010: astore #20
    //   2012: aload #20
    //   2014: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2017: invokevirtual intValue : ()I
    //   2020: iconst_1
    //   2021: if_icmpne -> 2032
    //   2024: aload #14
    //   2026: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2029: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2032: aload #20
    //   2034: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2037: invokevirtual intValue : ()I
    //   2040: iconst_1
    //   2041: if_icmpne -> 2052
    //   2044: aload #14
    //   2046: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2049: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2052: aload #14
    //   2054: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2057: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2060: aload #14
    //   2062: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2065: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2068: aload #14
    //   2070: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2073: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2076: aload #14
    //   2078: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2081: aload_1
    //   2082: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2085: ifeq -> 2107
    //   2088: aload #14
    //   2090: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2093: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2096: aload #14
    //   2098: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2101: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2104: goto -> 2123
    //   2107: aload #14
    //   2109: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2112: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2115: aload #14
    //   2117: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2120: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2123: aload #4
    //   2125: aload #14
    //   2127: invokeinterface add : (Ljava/lang/Object;)Z
    //   2132: pop
    //   2133: goto -> 8899
    //   2136: aload #13
    //   2138: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2141: invokevirtual intValue : ()I
    //   2144: ifle -> 8899
    //   2147: aload #13
    //   2149: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   2152: aload #6
    //   2154: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2157: ifgt -> 8899
    //   2160: aload #13
    //   2162: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2165: iload #10
    //   2167: iconst_1
    //   2168: isub
    //   2169: invokevirtual charAt : (I)C
    //   2172: bipush #49
    //   2174: if_icmpne -> 8899
    //   2177: aload #13
    //   2179: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   2182: aload #13
    //   2184: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2187: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   2190: ifge -> 8899
    //   2193: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2196: aload #13
    //   2198: ldc com/js/oa/scheme/event/vo/EventVO
    //   2200: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2203: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2206: astore #14
    //   2208: aload_0
    //   2209: getfield session : Lnet/sf/hibernate/Session;
    //   2212: new java/lang/StringBuilder
    //   2215: dup
    //   2216: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2218: invokespecial <init> : (Ljava/lang/String;)V
    //   2221: aload #14
    //   2223: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2226: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2229: ldc ' and eventAttender.empId ='
    //   2231: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2234: aload_1
    //   2235: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2238: invokevirtual toString : ()Ljava/lang/String;
    //   2241: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2246: astore #18
    //   2248: aload #18
    //   2250: invokeinterface list : ()Ljava/util/List;
    //   2255: astore #19
    //   2257: aload #14
    //   2259: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2262: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2265: aload #14
    //   2267: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2270: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2273: aload #19
    //   2275: ifnull -> 2331
    //   2278: aload #19
    //   2280: iconst_0
    //   2281: invokeinterface get : (I)Ljava/lang/Object;
    //   2286: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2289: astore #20
    //   2291: aload #20
    //   2293: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2296: invokevirtual intValue : ()I
    //   2299: iconst_1
    //   2300: if_icmpne -> 2311
    //   2303: aload #14
    //   2305: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2308: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2311: aload #20
    //   2313: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2316: invokevirtual intValue : ()I
    //   2319: iconst_1
    //   2320: if_icmpne -> 2331
    //   2323: aload #14
    //   2325: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2328: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2331: aload #14
    //   2333: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2336: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2339: aload #14
    //   2341: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2344: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2347: aload #14
    //   2349: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2352: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2355: aload #14
    //   2357: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2360: aload_1
    //   2361: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2364: ifeq -> 2386
    //   2367: aload #14
    //   2369: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2372: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2375: aload #14
    //   2377: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2380: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2383: goto -> 2402
    //   2386: aload #14
    //   2388: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2391: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2394: aload #14
    //   2396: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2399: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2402: aload #4
    //   2404: aload #14
    //   2406: invokeinterface add : (Ljava/lang/Object;)Z
    //   2411: pop
    //   2412: aload #13
    //   2414: new java/lang/Integer
    //   2417: dup
    //   2418: aload #13
    //   2420: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   2423: invokevirtual intValue : ()I
    //   2426: iconst_1
    //   2427: iadd
    //   2428: invokespecial <init> : (I)V
    //   2431: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   2434: goto -> 8899
    //   2437: aload #13
    //   2439: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   2442: invokevirtual intValue : ()I
    //   2445: iconst_3
    //   2446: if_icmpne -> 8009
    //   2449: aload #13
    //   2451: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   2454: invokevirtual intValue : ()I
    //   2457: iconst_m1
    //   2458: if_icmpne -> 4233
    //   2461: aload #13
    //   2463: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   2466: aload #6
    //   2468: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2471: ifgt -> 2709
    //   2474: aload #13
    //   2476: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2479: invokestatic parseInt : (Ljava/lang/String;)I
    //   2482: iload #9
    //   2484: if_icmpne -> 2709
    //   2487: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2490: aload #13
    //   2492: ldc com/js/oa/scheme/event/vo/EventVO
    //   2494: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2497: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2500: astore #14
    //   2502: aload_0
    //   2503: getfield session : Lnet/sf/hibernate/Session;
    //   2506: new java/lang/StringBuilder
    //   2509: dup
    //   2510: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2512: invokespecial <init> : (Ljava/lang/String;)V
    //   2515: aload #14
    //   2517: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2520: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2523: ldc ' and eventAttender.empId ='
    //   2525: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2528: aload_1
    //   2529: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2532: invokevirtual toString : ()Ljava/lang/String;
    //   2535: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2540: astore #18
    //   2542: aload #18
    //   2544: invokeinterface list : ()Ljava/util/List;
    //   2549: astore #19
    //   2551: aload #14
    //   2553: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2556: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2559: aload #14
    //   2561: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2564: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2567: aload #19
    //   2569: ifnull -> 2625
    //   2572: aload #19
    //   2574: iconst_0
    //   2575: invokeinterface get : (I)Ljava/lang/Object;
    //   2580: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2583: astore #20
    //   2585: aload #20
    //   2587: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2590: invokevirtual intValue : ()I
    //   2593: iconst_1
    //   2594: if_icmpne -> 2605
    //   2597: aload #14
    //   2599: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2602: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2605: aload #20
    //   2607: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2610: invokevirtual intValue : ()I
    //   2613: iconst_1
    //   2614: if_icmpne -> 2625
    //   2617: aload #14
    //   2619: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2622: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2625: aload #14
    //   2627: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2630: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2633: aload #14
    //   2635: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2638: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2641: aload #14
    //   2643: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2646: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2649: aload #14
    //   2651: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2654: aload_1
    //   2655: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2658: ifeq -> 2680
    //   2661: aload #14
    //   2663: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2666: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2669: aload #14
    //   2671: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2674: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2677: goto -> 2696
    //   2680: aload #14
    //   2682: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2685: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2688: aload #14
    //   2690: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2693: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2696: aload #4
    //   2698: aload #14
    //   2700: invokeinterface add : (Ljava/lang/Object;)Z
    //   2705: pop
    //   2706: goto -> 8899
    //   2709: aload #13
    //   2711: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   2714: aload #6
    //   2716: invokevirtual compareTo : (Ljava/util/Date;)I
    //   2719: ifgt -> 8899
    //   2722: aload #13
    //   2724: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   2727: ldc_w '31'
    //   2730: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2733: ifeq -> 3488
    //   2736: iload #8
    //   2738: tableswitch default -> 3485, 1 -> 2792, 2 -> 3485, 3 -> 3259, 4 -> 3485, 5 -> 3259, 6 -> 3485, 7 -> 3485, 8 -> 3259, 9 -> 3485, 10 -> 3259
    //   2792: aload_0
    //   2793: iload #7
    //   2795: invokespecial isLeapYear : (I)Z
    //   2798: ifeq -> 3030
    //   2801: iload #9
    //   2803: bipush #29
    //   2805: if_icmpne -> 8899
    //   2808: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2811: aload #13
    //   2813: ldc com/js/oa/scheme/event/vo/EventVO
    //   2815: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2818: checkcast com/js/oa/scheme/event/vo/EventVO
    //   2821: astore #14
    //   2823: aload_0
    //   2824: getfield session : Lnet/sf/hibernate/Session;
    //   2827: new java/lang/StringBuilder
    //   2830: dup
    //   2831: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   2833: invokespecial <init> : (Ljava/lang/String;)V
    //   2836: aload #14
    //   2838: invokevirtual getEventId : ()Ljava/lang/Long;
    //   2841: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2844: ldc ' and eventAttender.empId ='
    //   2846: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2849: aload_1
    //   2850: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2853: invokevirtual toString : ()Ljava/lang/String;
    //   2856: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2861: astore #18
    //   2863: aload #18
    //   2865: invokeinterface list : ()Ljava/util/List;
    //   2870: astore #19
    //   2872: aload #14
    //   2874: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2877: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2880: aload #14
    //   2882: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2885: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2888: aload #19
    //   2890: ifnull -> 2946
    //   2893: aload #19
    //   2895: iconst_0
    //   2896: invokeinterface get : (I)Ljava/lang/Object;
    //   2901: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   2904: astore #20
    //   2906: aload #20
    //   2908: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   2911: invokevirtual intValue : ()I
    //   2914: iconst_1
    //   2915: if_icmpne -> 2926
    //   2918: aload #14
    //   2920: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2923: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   2926: aload #20
    //   2928: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   2931: invokevirtual intValue : ()I
    //   2934: iconst_1
    //   2935: if_icmpne -> 2946
    //   2938: aload #14
    //   2940: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2943: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   2946: aload #14
    //   2948: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2951: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2954: aload #14
    //   2956: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2959: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2962: aload #14
    //   2964: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2967: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   2970: aload #14
    //   2972: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   2975: aload_1
    //   2976: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2979: ifeq -> 3001
    //   2982: aload #14
    //   2984: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2987: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2990: aload #14
    //   2992: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   2995: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   2998: goto -> 3017
    //   3001: aload #14
    //   3003: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3006: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3009: aload #14
    //   3011: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3014: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3017: aload #4
    //   3019: aload #14
    //   3021: invokeinterface add : (Ljava/lang/Object;)Z
    //   3026: pop
    //   3027: goto -> 8899
    //   3030: iload #9
    //   3032: bipush #28
    //   3034: if_icmpne -> 8899
    //   3037: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3040: aload #13
    //   3042: ldc com/js/oa/scheme/event/vo/EventVO
    //   3044: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3047: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3050: astore #14
    //   3052: aload_0
    //   3053: getfield session : Lnet/sf/hibernate/Session;
    //   3056: new java/lang/StringBuilder
    //   3059: dup
    //   3060: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3062: invokespecial <init> : (Ljava/lang/String;)V
    //   3065: aload #14
    //   3067: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3070: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3073: ldc ' and eventAttender.empId ='
    //   3075: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3078: aload_1
    //   3079: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3082: invokevirtual toString : ()Ljava/lang/String;
    //   3085: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3090: astore #18
    //   3092: aload #18
    //   3094: invokeinterface list : ()Ljava/util/List;
    //   3099: astore #19
    //   3101: aload #14
    //   3103: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3106: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3109: aload #14
    //   3111: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3114: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3117: aload #19
    //   3119: ifnull -> 3175
    //   3122: aload #19
    //   3124: iconst_0
    //   3125: invokeinterface get : (I)Ljava/lang/Object;
    //   3130: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3133: astore #20
    //   3135: aload #20
    //   3137: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3140: invokevirtual intValue : ()I
    //   3143: iconst_1
    //   3144: if_icmpne -> 3155
    //   3147: aload #14
    //   3149: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3152: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3155: aload #20
    //   3157: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3160: invokevirtual intValue : ()I
    //   3163: iconst_1
    //   3164: if_icmpne -> 3175
    //   3167: aload #14
    //   3169: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3172: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3175: aload #14
    //   3177: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3180: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3183: aload #14
    //   3185: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3188: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3191: aload #14
    //   3193: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3196: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3199: aload #14
    //   3201: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3204: aload_1
    //   3205: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3208: ifeq -> 3230
    //   3211: aload #14
    //   3213: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3216: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3219: aload #14
    //   3221: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3224: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3227: goto -> 3246
    //   3230: aload #14
    //   3232: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3235: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3238: aload #14
    //   3240: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3243: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3246: aload #4
    //   3248: aload #14
    //   3250: invokeinterface add : (Ljava/lang/Object;)Z
    //   3255: pop
    //   3256: goto -> 8899
    //   3259: iload #9
    //   3261: bipush #30
    //   3263: if_icmpne -> 8899
    //   3266: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3269: aload #13
    //   3271: ldc com/js/oa/scheme/event/vo/EventVO
    //   3273: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3276: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3279: astore #14
    //   3281: aload_0
    //   3282: getfield session : Lnet/sf/hibernate/Session;
    //   3285: new java/lang/StringBuilder
    //   3288: dup
    //   3289: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3291: invokespecial <init> : (Ljava/lang/String;)V
    //   3294: aload #14
    //   3296: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3299: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3302: ldc ' and eventAttender.empId ='
    //   3304: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3307: aload_1
    //   3308: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3311: invokevirtual toString : ()Ljava/lang/String;
    //   3314: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3319: astore #18
    //   3321: aload #18
    //   3323: invokeinterface list : ()Ljava/util/List;
    //   3328: astore #19
    //   3330: aload #14
    //   3332: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3335: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3338: aload #14
    //   3340: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3343: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3346: aload #19
    //   3348: ifnull -> 3404
    //   3351: aload #19
    //   3353: iconst_0
    //   3354: invokeinterface get : (I)Ljava/lang/Object;
    //   3359: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3362: astore #20
    //   3364: aload #20
    //   3366: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3369: invokevirtual intValue : ()I
    //   3372: iconst_1
    //   3373: if_icmpne -> 3384
    //   3376: aload #14
    //   3378: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3381: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3384: aload #20
    //   3386: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3389: invokevirtual intValue : ()I
    //   3392: iconst_1
    //   3393: if_icmpne -> 3404
    //   3396: aload #14
    //   3398: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3401: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3404: aload #14
    //   3406: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3409: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3412: aload #14
    //   3414: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3417: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3420: aload #14
    //   3422: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3425: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3428: aload #14
    //   3430: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3433: aload_1
    //   3434: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3437: ifeq -> 3459
    //   3440: aload #14
    //   3442: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3445: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3448: aload #14
    //   3450: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3453: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3456: goto -> 3475
    //   3459: aload #14
    //   3461: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3464: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3467: aload #14
    //   3469: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3472: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3475: aload #4
    //   3477: aload #14
    //   3479: invokeinterface add : (Ljava/lang/Object;)Z
    //   3484: pop
    //   3485: goto -> 8899
    //   3488: aload #13
    //   3490: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   3493: ldc_w '30'
    //   3496: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3499: ifeq -> 3975
    //   3502: iload #8
    //   3504: iconst_1
    //   3505: if_icmpne -> 8899
    //   3508: aload_0
    //   3509: iload #7
    //   3511: invokespecial isLeapYear : (I)Z
    //   3514: ifeq -> 3746
    //   3517: iload #9
    //   3519: bipush #29
    //   3521: if_icmpne -> 8899
    //   3524: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3527: aload #13
    //   3529: ldc com/js/oa/scheme/event/vo/EventVO
    //   3531: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3534: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3537: astore #14
    //   3539: aload_0
    //   3540: getfield session : Lnet/sf/hibernate/Session;
    //   3543: new java/lang/StringBuilder
    //   3546: dup
    //   3547: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3549: invokespecial <init> : (Ljava/lang/String;)V
    //   3552: aload #14
    //   3554: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3557: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3560: ldc ' and eventAttender.empId ='
    //   3562: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3565: aload_1
    //   3566: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3569: invokevirtual toString : ()Ljava/lang/String;
    //   3572: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3577: astore #18
    //   3579: aload #18
    //   3581: invokeinterface list : ()Ljava/util/List;
    //   3586: astore #19
    //   3588: aload #14
    //   3590: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3593: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3596: aload #14
    //   3598: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3601: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3604: aload #19
    //   3606: ifnull -> 3662
    //   3609: aload #19
    //   3611: iconst_0
    //   3612: invokeinterface get : (I)Ljava/lang/Object;
    //   3617: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3620: astore #20
    //   3622: aload #20
    //   3624: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3627: invokevirtual intValue : ()I
    //   3630: iconst_1
    //   3631: if_icmpne -> 3642
    //   3634: aload #14
    //   3636: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3639: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3642: aload #20
    //   3644: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3647: invokevirtual intValue : ()I
    //   3650: iconst_1
    //   3651: if_icmpne -> 3662
    //   3654: aload #14
    //   3656: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3659: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3662: aload #14
    //   3664: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3667: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3670: aload #14
    //   3672: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3675: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3678: aload #14
    //   3680: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3683: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3686: aload #14
    //   3688: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3691: aload_1
    //   3692: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3695: ifeq -> 3717
    //   3698: aload #14
    //   3700: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3703: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3706: aload #14
    //   3708: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3711: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3714: goto -> 3733
    //   3717: aload #14
    //   3719: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3722: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3725: aload #14
    //   3727: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3730: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3733: aload #4
    //   3735: aload #14
    //   3737: invokeinterface add : (Ljava/lang/Object;)Z
    //   3742: pop
    //   3743: goto -> 8899
    //   3746: iload #9
    //   3748: bipush #28
    //   3750: if_icmpne -> 8899
    //   3753: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   3756: aload #13
    //   3758: ldc com/js/oa/scheme/event/vo/EventVO
    //   3760: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   3763: checkcast com/js/oa/scheme/event/vo/EventVO
    //   3766: astore #14
    //   3768: aload_0
    //   3769: getfield session : Lnet/sf/hibernate/Session;
    //   3772: new java/lang/StringBuilder
    //   3775: dup
    //   3776: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   3778: invokespecial <init> : (Ljava/lang/String;)V
    //   3781: aload #14
    //   3783: invokevirtual getEventId : ()Ljava/lang/Long;
    //   3786: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3789: ldc ' and eventAttender.empId ='
    //   3791: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3794: aload_1
    //   3795: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3798: invokevirtual toString : ()Ljava/lang/String;
    //   3801: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3806: astore #18
    //   3808: aload #18
    //   3810: invokeinterface list : ()Ljava/util/List;
    //   3815: astore #19
    //   3817: aload #14
    //   3819: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3822: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3825: aload #14
    //   3827: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3830: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3833: aload #19
    //   3835: ifnull -> 3891
    //   3838: aload #19
    //   3840: iconst_0
    //   3841: invokeinterface get : (I)Ljava/lang/Object;
    //   3846: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   3849: astore #20
    //   3851: aload #20
    //   3853: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   3856: invokevirtual intValue : ()I
    //   3859: iconst_1
    //   3860: if_icmpne -> 3871
    //   3863: aload #14
    //   3865: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3868: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   3871: aload #20
    //   3873: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   3876: invokevirtual intValue : ()I
    //   3879: iconst_1
    //   3880: if_icmpne -> 3891
    //   3883: aload #14
    //   3885: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3888: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   3891: aload #14
    //   3893: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3896: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3899: aload #14
    //   3901: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3904: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3907: aload #14
    //   3909: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   3912: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3915: aload #14
    //   3917: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   3920: aload_1
    //   3921: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3924: ifeq -> 3946
    //   3927: aload #14
    //   3929: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3932: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3935: aload #14
    //   3937: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3940: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   3943: goto -> 3962
    //   3946: aload #14
    //   3948: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3951: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   3954: aload #14
    //   3956: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   3959: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   3962: aload #4
    //   3964: aload #14
    //   3966: invokeinterface add : (Ljava/lang/Object;)Z
    //   3971: pop
    //   3972: goto -> 8899
    //   3975: aload #13
    //   3977: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   3980: ldc_w '29'
    //   3983: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3986: ifeq -> 8899
    //   3989: iload #8
    //   3991: iconst_1
    //   3992: if_icmpne -> 8899
    //   3995: aload_0
    //   3996: iload #7
    //   3998: invokespecial isLeapYear : (I)Z
    //   4001: ifne -> 8899
    //   4004: iload #9
    //   4006: bipush #28
    //   4008: if_icmpne -> 8899
    //   4011: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4014: aload #13
    //   4016: ldc com/js/oa/scheme/event/vo/EventVO
    //   4018: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4021: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4024: astore #14
    //   4026: aload_0
    //   4027: getfield session : Lnet/sf/hibernate/Session;
    //   4030: new java/lang/StringBuilder
    //   4033: dup
    //   4034: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4036: invokespecial <init> : (Ljava/lang/String;)V
    //   4039: aload #14
    //   4041: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4044: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4047: ldc ' and eventAttender.empId ='
    //   4049: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4052: aload_1
    //   4053: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4056: invokevirtual toString : ()Ljava/lang/String;
    //   4059: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4064: astore #18
    //   4066: aload #18
    //   4068: invokeinterface list : ()Ljava/util/List;
    //   4073: astore #19
    //   4075: aload #14
    //   4077: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4080: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4083: aload #14
    //   4085: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4088: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4091: aload #19
    //   4093: ifnull -> 4149
    //   4096: aload #19
    //   4098: iconst_0
    //   4099: invokeinterface get : (I)Ljava/lang/Object;
    //   4104: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4107: astore #20
    //   4109: aload #20
    //   4111: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4114: invokevirtual intValue : ()I
    //   4117: iconst_1
    //   4118: if_icmpne -> 4129
    //   4121: aload #14
    //   4123: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4126: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4129: aload #20
    //   4131: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4134: invokevirtual intValue : ()I
    //   4137: iconst_1
    //   4138: if_icmpne -> 4149
    //   4141: aload #14
    //   4143: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4146: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4149: aload #14
    //   4151: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4154: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4157: aload #14
    //   4159: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4162: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4165: aload #14
    //   4167: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4170: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4173: aload #14
    //   4175: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4178: aload_1
    //   4179: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4182: ifeq -> 4204
    //   4185: aload #14
    //   4187: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4190: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4193: aload #14
    //   4195: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4198: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4201: goto -> 4220
    //   4204: aload #14
    //   4206: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4209: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4212: aload #14
    //   4214: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4217: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4220: aload #4
    //   4222: aload #14
    //   4224: invokeinterface add : (Ljava/lang/Object;)Z
    //   4229: pop
    //   4230: goto -> 8899
    //   4233: aload #13
    //   4235: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   4238: invokevirtual intValue : ()I
    //   4241: ifne -> 6041
    //   4244: aload #13
    //   4246: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   4249: aload #6
    //   4251: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4254: ifgt -> 4505
    //   4257: aload #13
    //   4259: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   4262: invokestatic parseInt : (Ljava/lang/String;)I
    //   4265: iload #9
    //   4267: if_icmpne -> 4505
    //   4270: aload #13
    //   4272: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   4275: aload #6
    //   4277: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4280: iflt -> 4505
    //   4283: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4286: aload #13
    //   4288: ldc com/js/oa/scheme/event/vo/EventVO
    //   4290: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4293: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4296: astore #14
    //   4298: aload_0
    //   4299: getfield session : Lnet/sf/hibernate/Session;
    //   4302: new java/lang/StringBuilder
    //   4305: dup
    //   4306: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4308: invokespecial <init> : (Ljava/lang/String;)V
    //   4311: aload #14
    //   4313: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4316: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4319: ldc ' and eventAttender.empId ='
    //   4321: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4324: aload_1
    //   4325: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4328: invokevirtual toString : ()Ljava/lang/String;
    //   4331: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4336: astore #18
    //   4338: aload #18
    //   4340: invokeinterface list : ()Ljava/util/List;
    //   4345: astore #19
    //   4347: aload #14
    //   4349: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4352: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4355: aload #14
    //   4357: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4360: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4363: aload #19
    //   4365: ifnull -> 4421
    //   4368: aload #19
    //   4370: iconst_0
    //   4371: invokeinterface get : (I)Ljava/lang/Object;
    //   4376: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4379: astore #20
    //   4381: aload #20
    //   4383: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4386: invokevirtual intValue : ()I
    //   4389: iconst_1
    //   4390: if_icmpne -> 4401
    //   4393: aload #14
    //   4395: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4398: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4401: aload #20
    //   4403: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4406: invokevirtual intValue : ()I
    //   4409: iconst_1
    //   4410: if_icmpne -> 4421
    //   4413: aload #14
    //   4415: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4418: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4421: aload #14
    //   4423: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4426: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4429: aload #14
    //   4431: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4434: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4437: aload #14
    //   4439: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4442: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4445: aload #14
    //   4447: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4450: aload_1
    //   4451: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4454: ifeq -> 4476
    //   4457: aload #14
    //   4459: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4462: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4465: aload #14
    //   4467: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4470: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4473: goto -> 4492
    //   4476: aload #14
    //   4478: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4481: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4484: aload #14
    //   4486: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4489: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4492: aload #4
    //   4494: aload #14
    //   4496: invokeinterface add : (Ljava/lang/Object;)Z
    //   4501: pop
    //   4502: goto -> 8899
    //   4505: aload #13
    //   4507: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   4510: aload #6
    //   4512: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4515: ifgt -> 8899
    //   4518: aload #13
    //   4520: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   4523: aload #6
    //   4525: invokevirtual compareTo : (Ljava/util/Date;)I
    //   4528: iflt -> 8899
    //   4531: aload #13
    //   4533: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   4536: ldc_w '31'
    //   4539: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4542: ifeq -> 5296
    //   4545: iload #8
    //   4547: tableswitch default -> 5293, 1 -> 4600, 2 -> 5293, 3 -> 5067, 4 -> 5293, 5 -> 5067, 6 -> 5293, 7 -> 5293, 8 -> 5067, 9 -> 5293, 10 -> 5067
    //   4600: aload_0
    //   4601: iload #7
    //   4603: invokespecial isLeapYear : (I)Z
    //   4606: ifeq -> 4838
    //   4609: iload #9
    //   4611: bipush #29
    //   4613: if_icmpne -> 8899
    //   4616: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4619: aload #13
    //   4621: ldc com/js/oa/scheme/event/vo/EventVO
    //   4623: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4626: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4629: astore #14
    //   4631: aload_0
    //   4632: getfield session : Lnet/sf/hibernate/Session;
    //   4635: new java/lang/StringBuilder
    //   4638: dup
    //   4639: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4641: invokespecial <init> : (Ljava/lang/String;)V
    //   4644: aload #14
    //   4646: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4649: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4652: ldc ' and eventAttender.empId ='
    //   4654: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4657: aload_1
    //   4658: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4661: invokevirtual toString : ()Ljava/lang/String;
    //   4664: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4669: astore #18
    //   4671: aload #18
    //   4673: invokeinterface list : ()Ljava/util/List;
    //   4678: astore #19
    //   4680: aload #14
    //   4682: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4685: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4688: aload #14
    //   4690: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4693: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4696: aload #19
    //   4698: ifnull -> 4754
    //   4701: aload #19
    //   4703: iconst_0
    //   4704: invokeinterface get : (I)Ljava/lang/Object;
    //   4709: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4712: astore #20
    //   4714: aload #20
    //   4716: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4719: invokevirtual intValue : ()I
    //   4722: iconst_1
    //   4723: if_icmpne -> 4734
    //   4726: aload #14
    //   4728: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4731: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4734: aload #20
    //   4736: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4739: invokevirtual intValue : ()I
    //   4742: iconst_1
    //   4743: if_icmpne -> 4754
    //   4746: aload #14
    //   4748: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4751: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4754: aload #14
    //   4756: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4759: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4762: aload #14
    //   4764: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4767: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4770: aload #14
    //   4772: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4775: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4778: aload #14
    //   4780: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   4783: aload_1
    //   4784: invokevirtual equals : (Ljava/lang/Object;)Z
    //   4787: ifeq -> 4809
    //   4790: aload #14
    //   4792: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4795: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4798: aload #14
    //   4800: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4803: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4806: goto -> 4825
    //   4809: aload #14
    //   4811: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4814: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4817: aload #14
    //   4819: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4822: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   4825: aload #4
    //   4827: aload #14
    //   4829: invokeinterface add : (Ljava/lang/Object;)Z
    //   4834: pop
    //   4835: goto -> 8899
    //   4838: iload #9
    //   4840: bipush #28
    //   4842: if_icmpne -> 8899
    //   4845: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   4848: aload #13
    //   4850: ldc com/js/oa/scheme/event/vo/EventVO
    //   4852: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   4855: checkcast com/js/oa/scheme/event/vo/EventVO
    //   4858: astore #14
    //   4860: aload_0
    //   4861: getfield session : Lnet/sf/hibernate/Session;
    //   4864: new java/lang/StringBuilder
    //   4867: dup
    //   4868: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   4870: invokespecial <init> : (Ljava/lang/String;)V
    //   4873: aload #14
    //   4875: invokevirtual getEventId : ()Ljava/lang/Long;
    //   4878: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4881: ldc ' and eventAttender.empId ='
    //   4883: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4886: aload_1
    //   4887: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   4890: invokevirtual toString : ()Ljava/lang/String;
    //   4893: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   4898: astore #18
    //   4900: aload #18
    //   4902: invokeinterface list : ()Ljava/util/List;
    //   4907: astore #19
    //   4909: aload #14
    //   4911: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4914: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4917: aload #14
    //   4919: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4922: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4925: aload #19
    //   4927: ifnull -> 4983
    //   4930: aload #19
    //   4932: iconst_0
    //   4933: invokeinterface get : (I)Ljava/lang/Object;
    //   4938: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   4941: astore #20
    //   4943: aload #20
    //   4945: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   4948: invokevirtual intValue : ()I
    //   4951: iconst_1
    //   4952: if_icmpne -> 4963
    //   4955: aload #14
    //   4957: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4960: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   4963: aload #20
    //   4965: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   4968: invokevirtual intValue : ()I
    //   4971: iconst_1
    //   4972: if_icmpne -> 4983
    //   4975: aload #14
    //   4977: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   4980: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   4983: aload #14
    //   4985: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4988: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   4991: aload #14
    //   4993: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   4996: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   4999: aload #14
    //   5001: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5004: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5007: aload #14
    //   5009: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5012: aload_1
    //   5013: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5016: ifeq -> 5038
    //   5019: aload #14
    //   5021: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5024: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5027: aload #14
    //   5029: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5032: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5035: goto -> 5054
    //   5038: aload #14
    //   5040: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5043: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5046: aload #14
    //   5048: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5051: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5054: aload #4
    //   5056: aload #14
    //   5058: invokeinterface add : (Ljava/lang/Object;)Z
    //   5063: pop
    //   5064: goto -> 8899
    //   5067: iload #9
    //   5069: bipush #30
    //   5071: if_icmpne -> 8899
    //   5074: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5077: aload #13
    //   5079: ldc com/js/oa/scheme/event/vo/EventVO
    //   5081: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5084: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5087: astore #14
    //   5089: aload_0
    //   5090: getfield session : Lnet/sf/hibernate/Session;
    //   5093: new java/lang/StringBuilder
    //   5096: dup
    //   5097: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5099: invokespecial <init> : (Ljava/lang/String;)V
    //   5102: aload #14
    //   5104: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5107: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5110: ldc ' and eventAttender.empId ='
    //   5112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5115: aload_1
    //   5116: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5119: invokevirtual toString : ()Ljava/lang/String;
    //   5122: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5127: astore #18
    //   5129: aload #18
    //   5131: invokeinterface list : ()Ljava/util/List;
    //   5136: astore #19
    //   5138: aload #14
    //   5140: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5143: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5146: aload #14
    //   5148: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5151: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5154: aload #19
    //   5156: ifnull -> 5212
    //   5159: aload #19
    //   5161: iconst_0
    //   5162: invokeinterface get : (I)Ljava/lang/Object;
    //   5167: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5170: astore #20
    //   5172: aload #20
    //   5174: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5177: invokevirtual intValue : ()I
    //   5180: iconst_1
    //   5181: if_icmpne -> 5192
    //   5184: aload #14
    //   5186: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5189: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5192: aload #20
    //   5194: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5197: invokevirtual intValue : ()I
    //   5200: iconst_1
    //   5201: if_icmpne -> 5212
    //   5204: aload #14
    //   5206: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5209: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5212: aload #14
    //   5214: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5217: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5220: aload #14
    //   5222: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5225: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5228: aload #14
    //   5230: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5233: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5236: aload #14
    //   5238: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5241: aload_1
    //   5242: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5245: ifeq -> 5267
    //   5248: aload #14
    //   5250: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5253: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5256: aload #14
    //   5258: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5261: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5264: goto -> 5283
    //   5267: aload #14
    //   5269: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5272: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5275: aload #14
    //   5277: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5280: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5283: aload #4
    //   5285: aload #14
    //   5287: invokeinterface add : (Ljava/lang/Object;)Z
    //   5292: pop
    //   5293: goto -> 8899
    //   5296: aload #13
    //   5298: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   5301: ldc_w '30'
    //   5304: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5307: ifeq -> 5783
    //   5310: iload #8
    //   5312: iconst_1
    //   5313: if_icmpne -> 8899
    //   5316: aload_0
    //   5317: iload #7
    //   5319: invokespecial isLeapYear : (I)Z
    //   5322: ifeq -> 5554
    //   5325: iload #9
    //   5327: bipush #29
    //   5329: if_icmpne -> 8899
    //   5332: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5335: aload #13
    //   5337: ldc com/js/oa/scheme/event/vo/EventVO
    //   5339: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5342: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5345: astore #14
    //   5347: aload_0
    //   5348: getfield session : Lnet/sf/hibernate/Session;
    //   5351: new java/lang/StringBuilder
    //   5354: dup
    //   5355: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5357: invokespecial <init> : (Ljava/lang/String;)V
    //   5360: aload #14
    //   5362: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5365: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5368: ldc ' and eventAttender.empId ='
    //   5370: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5373: aload_1
    //   5374: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5377: invokevirtual toString : ()Ljava/lang/String;
    //   5380: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5385: astore #18
    //   5387: aload #18
    //   5389: invokeinterface list : ()Ljava/util/List;
    //   5394: astore #19
    //   5396: aload #14
    //   5398: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5401: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5404: aload #14
    //   5406: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5409: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5412: aload #19
    //   5414: ifnull -> 5470
    //   5417: aload #19
    //   5419: iconst_0
    //   5420: invokeinterface get : (I)Ljava/lang/Object;
    //   5425: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5428: astore #20
    //   5430: aload #20
    //   5432: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5435: invokevirtual intValue : ()I
    //   5438: iconst_1
    //   5439: if_icmpne -> 5450
    //   5442: aload #14
    //   5444: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5447: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5450: aload #20
    //   5452: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5455: invokevirtual intValue : ()I
    //   5458: iconst_1
    //   5459: if_icmpne -> 5470
    //   5462: aload #14
    //   5464: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5467: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5470: aload #14
    //   5472: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5475: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5478: aload #14
    //   5480: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5483: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5486: aload #14
    //   5488: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5491: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5494: aload #14
    //   5496: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5499: aload_1
    //   5500: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5503: ifeq -> 5525
    //   5506: aload #14
    //   5508: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5511: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5514: aload #14
    //   5516: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5519: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5522: goto -> 5541
    //   5525: aload #14
    //   5527: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5530: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5533: aload #14
    //   5535: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5538: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5541: aload #4
    //   5543: aload #14
    //   5545: invokeinterface add : (Ljava/lang/Object;)Z
    //   5550: pop
    //   5551: goto -> 8899
    //   5554: iload #9
    //   5556: bipush #28
    //   5558: if_icmpne -> 8899
    //   5561: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5564: aload #13
    //   5566: ldc com/js/oa/scheme/event/vo/EventVO
    //   5568: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5571: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5574: astore #14
    //   5576: aload_0
    //   5577: getfield session : Lnet/sf/hibernate/Session;
    //   5580: new java/lang/StringBuilder
    //   5583: dup
    //   5584: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5586: invokespecial <init> : (Ljava/lang/String;)V
    //   5589: aload #14
    //   5591: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5594: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5597: ldc ' and eventAttender.empId ='
    //   5599: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5602: aload_1
    //   5603: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5606: invokevirtual toString : ()Ljava/lang/String;
    //   5609: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5614: astore #18
    //   5616: aload #18
    //   5618: invokeinterface list : ()Ljava/util/List;
    //   5623: astore #19
    //   5625: aload #14
    //   5627: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5630: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5633: aload #14
    //   5635: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5638: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5641: aload #19
    //   5643: ifnull -> 5699
    //   5646: aload #19
    //   5648: iconst_0
    //   5649: invokeinterface get : (I)Ljava/lang/Object;
    //   5654: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5657: astore #20
    //   5659: aload #20
    //   5661: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5664: invokevirtual intValue : ()I
    //   5667: iconst_1
    //   5668: if_icmpne -> 5679
    //   5671: aload #14
    //   5673: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5676: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5679: aload #20
    //   5681: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5684: invokevirtual intValue : ()I
    //   5687: iconst_1
    //   5688: if_icmpne -> 5699
    //   5691: aload #14
    //   5693: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5696: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5699: aload #14
    //   5701: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5704: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5707: aload #14
    //   5709: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5712: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5715: aload #14
    //   5717: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5720: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5723: aload #14
    //   5725: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5728: aload_1
    //   5729: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5732: ifeq -> 5754
    //   5735: aload #14
    //   5737: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5740: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5743: aload #14
    //   5745: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5748: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5751: goto -> 5770
    //   5754: aload #14
    //   5756: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5759: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5762: aload #14
    //   5764: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5767: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5770: aload #4
    //   5772: aload #14
    //   5774: invokeinterface add : (Ljava/lang/Object;)Z
    //   5779: pop
    //   5780: goto -> 8899
    //   5783: aload #13
    //   5785: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   5788: ldc_w '29'
    //   5791: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5794: ifeq -> 8899
    //   5797: iload #8
    //   5799: iconst_1
    //   5800: if_icmpne -> 8899
    //   5803: aload_0
    //   5804: iload #7
    //   5806: invokespecial isLeapYear : (I)Z
    //   5809: ifne -> 8899
    //   5812: iload #9
    //   5814: bipush #28
    //   5816: if_icmpne -> 8899
    //   5819: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   5822: aload #13
    //   5824: ldc com/js/oa/scheme/event/vo/EventVO
    //   5826: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   5829: checkcast com/js/oa/scheme/event/vo/EventVO
    //   5832: astore #14
    //   5834: aload_0
    //   5835: getfield session : Lnet/sf/hibernate/Session;
    //   5838: new java/lang/StringBuilder
    //   5841: dup
    //   5842: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   5844: invokespecial <init> : (Ljava/lang/String;)V
    //   5847: aload #14
    //   5849: invokevirtual getEventId : ()Ljava/lang/Long;
    //   5852: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5855: ldc ' and eventAttender.empId ='
    //   5857: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5860: aload_1
    //   5861: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   5864: invokevirtual toString : ()Ljava/lang/String;
    //   5867: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   5872: astore #18
    //   5874: aload #18
    //   5876: invokeinterface list : ()Ljava/util/List;
    //   5881: astore #19
    //   5883: aload #14
    //   5885: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5888: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5891: aload #14
    //   5893: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5896: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5899: aload #19
    //   5901: ifnull -> 5957
    //   5904: aload #19
    //   5906: iconst_0
    //   5907: invokeinterface get : (I)Ljava/lang/Object;
    //   5912: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   5915: astore #20
    //   5917: aload #20
    //   5919: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   5922: invokevirtual intValue : ()I
    //   5925: iconst_1
    //   5926: if_icmpne -> 5937
    //   5929: aload #14
    //   5931: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5934: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   5937: aload #20
    //   5939: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   5942: invokevirtual intValue : ()I
    //   5945: iconst_1
    //   5946: if_icmpne -> 5957
    //   5949: aload #14
    //   5951: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5954: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   5957: aload #14
    //   5959: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5962: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   5965: aload #14
    //   5967: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5970: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   5973: aload #14
    //   5975: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   5978: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   5981: aload #14
    //   5983: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   5986: aload_1
    //   5987: invokevirtual equals : (Ljava/lang/Object;)Z
    //   5990: ifeq -> 6012
    //   5993: aload #14
    //   5995: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   5998: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6001: aload #14
    //   6003: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6006: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6009: goto -> 6028
    //   6012: aload #14
    //   6014: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6017: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6020: aload #14
    //   6022: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6025: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6028: aload #4
    //   6030: aload #14
    //   6032: invokeinterface add : (Ljava/lang/Object;)Z
    //   6037: pop
    //   6038: goto -> 8899
    //   6041: aload #13
    //   6043: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   6046: invokevirtual intValue : ()I
    //   6049: ifle -> 8899
    //   6052: aload #13
    //   6054: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   6057: aload #6
    //   6059: invokevirtual compareTo : (Ljava/util/Date;)I
    //   6062: ifgt -> 6338
    //   6065: aload #13
    //   6067: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   6070: invokestatic parseInt : (Ljava/lang/String;)I
    //   6073: iload #9
    //   6075: if_icmpne -> 6338
    //   6078: aload #13
    //   6080: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   6083: aload #13
    //   6085: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6088: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   6091: ifle -> 6338
    //   6094: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6097: aload #13
    //   6099: ldc com/js/oa/scheme/event/vo/EventVO
    //   6101: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6104: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6107: astore #14
    //   6109: aload_0
    //   6110: getfield session : Lnet/sf/hibernate/Session;
    //   6113: new java/lang/StringBuilder
    //   6116: dup
    //   6117: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6119: invokespecial <init> : (Ljava/lang/String;)V
    //   6122: aload #14
    //   6124: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6127: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6130: ldc ' and eventAttender.empId ='
    //   6132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6135: aload_1
    //   6136: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6139: invokevirtual toString : ()Ljava/lang/String;
    //   6142: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6147: astore #18
    //   6149: aload #18
    //   6151: invokeinterface list : ()Ljava/util/List;
    //   6156: astore #19
    //   6158: aload #14
    //   6160: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6163: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6166: aload #14
    //   6168: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6171: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6174: aload #19
    //   6176: ifnull -> 6232
    //   6179: aload #19
    //   6181: iconst_0
    //   6182: invokeinterface get : (I)Ljava/lang/Object;
    //   6187: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6190: astore #20
    //   6192: aload #20
    //   6194: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6197: invokevirtual intValue : ()I
    //   6200: iconst_1
    //   6201: if_icmpne -> 6212
    //   6204: aload #14
    //   6206: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6209: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6212: aload #20
    //   6214: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6217: invokevirtual intValue : ()I
    //   6220: iconst_1
    //   6221: if_icmpne -> 6232
    //   6224: aload #14
    //   6226: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6229: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6232: aload #14
    //   6234: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6237: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6240: aload #14
    //   6242: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6245: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6248: aload #14
    //   6250: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6253: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6256: aload #14
    //   6258: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   6261: aload_1
    //   6262: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6265: ifeq -> 6287
    //   6268: aload #14
    //   6270: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6273: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6276: aload #14
    //   6278: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6281: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6284: goto -> 6303
    //   6287: aload #14
    //   6289: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6292: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6295: aload #14
    //   6297: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6300: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6303: aload #4
    //   6305: aload #14
    //   6307: invokeinterface add : (Ljava/lang/Object;)Z
    //   6312: pop
    //   6313: aload #13
    //   6315: new java/lang/Integer
    //   6318: dup
    //   6319: aload #13
    //   6321: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6324: invokevirtual intValue : ()I
    //   6327: iconst_1
    //   6328: iadd
    //   6329: invokespecial <init> : (I)V
    //   6332: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   6335: goto -> 8899
    //   6338: aload #13
    //   6340: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   6343: aload #6
    //   6345: invokevirtual compareTo : (Ljava/util/Date;)I
    //   6348: ifgt -> 8899
    //   6351: aload #13
    //   6353: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   6356: aload #13
    //   6358: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6361: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   6364: ifle -> 8899
    //   6367: aload #13
    //   6369: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   6372: ldc_w '31'
    //   6375: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6378: ifeq -> 7198
    //   6381: iload #8
    //   6383: tableswitch default -> 7195, 1 -> 6436, 2 -> 7195, 3 -> 6947, 4 -> 7195, 5 -> 6947, 6 -> 7195, 7 -> 7195, 8 -> 6947, 9 -> 7195, 10 -> 6947
    //   6436: aload_0
    //   6437: iload #7
    //   6439: invokespecial isLeapYear : (I)Z
    //   6442: ifeq -> 6696
    //   6445: iload #9
    //   6447: bipush #29
    //   6449: if_icmpne -> 8899
    //   6452: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6455: aload #13
    //   6457: ldc com/js/oa/scheme/event/vo/EventVO
    //   6459: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6462: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6465: astore #14
    //   6467: aload_0
    //   6468: getfield session : Lnet/sf/hibernate/Session;
    //   6471: new java/lang/StringBuilder
    //   6474: dup
    //   6475: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6477: invokespecial <init> : (Ljava/lang/String;)V
    //   6480: aload #14
    //   6482: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6485: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6488: ldc ' and eventAttender.empId ='
    //   6490: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6493: aload_1
    //   6494: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6497: invokevirtual toString : ()Ljava/lang/String;
    //   6500: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6505: astore #18
    //   6507: aload #18
    //   6509: invokeinterface list : ()Ljava/util/List;
    //   6514: astore #19
    //   6516: aload #14
    //   6518: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6521: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6524: aload #14
    //   6526: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6529: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6532: aload #19
    //   6534: ifnull -> 6590
    //   6537: aload #19
    //   6539: iconst_0
    //   6540: invokeinterface get : (I)Ljava/lang/Object;
    //   6545: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6548: astore #20
    //   6550: aload #20
    //   6552: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6555: invokevirtual intValue : ()I
    //   6558: iconst_1
    //   6559: if_icmpne -> 6570
    //   6562: aload #14
    //   6564: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6567: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6570: aload #20
    //   6572: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6575: invokevirtual intValue : ()I
    //   6578: iconst_1
    //   6579: if_icmpne -> 6590
    //   6582: aload #14
    //   6584: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6587: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6590: aload #14
    //   6592: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6595: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6598: aload #14
    //   6600: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6603: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6606: aload #14
    //   6608: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6611: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6614: aload #14
    //   6616: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   6619: aload_1
    //   6620: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6623: ifeq -> 6645
    //   6626: aload #14
    //   6628: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6631: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6634: aload #14
    //   6636: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6639: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6642: goto -> 6661
    //   6645: aload #14
    //   6647: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6650: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6653: aload #14
    //   6655: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6658: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6661: aload #4
    //   6663: aload #14
    //   6665: invokeinterface add : (Ljava/lang/Object;)Z
    //   6670: pop
    //   6671: aload #13
    //   6673: new java/lang/Integer
    //   6676: dup
    //   6677: aload #13
    //   6679: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6682: invokevirtual intValue : ()I
    //   6685: iconst_1
    //   6686: iadd
    //   6687: invokespecial <init> : (I)V
    //   6690: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   6693: goto -> 8899
    //   6696: iload #9
    //   6698: bipush #28
    //   6700: if_icmpne -> 8899
    //   6703: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6706: aload #13
    //   6708: ldc com/js/oa/scheme/event/vo/EventVO
    //   6710: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6713: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6716: astore #14
    //   6718: aload_0
    //   6719: getfield session : Lnet/sf/hibernate/Session;
    //   6722: new java/lang/StringBuilder
    //   6725: dup
    //   6726: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6728: invokespecial <init> : (Ljava/lang/String;)V
    //   6731: aload #14
    //   6733: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6736: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6739: ldc ' and eventAttender.empId ='
    //   6741: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6744: aload_1
    //   6745: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6748: invokevirtual toString : ()Ljava/lang/String;
    //   6751: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   6756: astore #18
    //   6758: aload #18
    //   6760: invokeinterface list : ()Ljava/util/List;
    //   6765: astore #19
    //   6767: aload #14
    //   6769: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6772: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6775: aload #14
    //   6777: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6780: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6783: aload #19
    //   6785: ifnull -> 6841
    //   6788: aload #19
    //   6790: iconst_0
    //   6791: invokeinterface get : (I)Ljava/lang/Object;
    //   6796: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   6799: astore #20
    //   6801: aload #20
    //   6803: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   6806: invokevirtual intValue : ()I
    //   6809: iconst_1
    //   6810: if_icmpne -> 6821
    //   6813: aload #14
    //   6815: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6818: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   6821: aload #20
    //   6823: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   6826: invokevirtual intValue : ()I
    //   6829: iconst_1
    //   6830: if_icmpne -> 6841
    //   6833: aload #14
    //   6835: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6838: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   6841: aload #14
    //   6843: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6846: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6849: aload #14
    //   6851: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6854: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6857: aload #14
    //   6859: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   6862: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6865: aload #14
    //   6867: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   6870: aload_1
    //   6871: invokevirtual equals : (Ljava/lang/Object;)Z
    //   6874: ifeq -> 6896
    //   6877: aload #14
    //   6879: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6882: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6885: aload #14
    //   6887: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6890: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   6893: goto -> 6912
    //   6896: aload #14
    //   6898: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6901: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   6904: aload #14
    //   6906: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   6909: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   6912: aload #4
    //   6914: aload #14
    //   6916: invokeinterface add : (Ljava/lang/Object;)Z
    //   6921: pop
    //   6922: aload #13
    //   6924: new java/lang/Integer
    //   6927: dup
    //   6928: aload #13
    //   6930: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   6933: invokevirtual intValue : ()I
    //   6936: iconst_1
    //   6937: iadd
    //   6938: invokespecial <init> : (I)V
    //   6941: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   6944: goto -> 8899
    //   6947: iload #9
    //   6949: bipush #30
    //   6951: if_icmpne -> 8899
    //   6954: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   6957: aload #13
    //   6959: ldc com/js/oa/scheme/event/vo/EventVO
    //   6961: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   6964: checkcast com/js/oa/scheme/event/vo/EventVO
    //   6967: astore #14
    //   6969: aload_0
    //   6970: getfield session : Lnet/sf/hibernate/Session;
    //   6973: new java/lang/StringBuilder
    //   6976: dup
    //   6977: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   6979: invokespecial <init> : (Ljava/lang/String;)V
    //   6982: aload #14
    //   6984: invokevirtual getEventId : ()Ljava/lang/Long;
    //   6987: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6990: ldc ' and eventAttender.empId ='
    //   6992: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   6995: aload_1
    //   6996: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   6999: invokevirtual toString : ()Ljava/lang/String;
    //   7002: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7007: astore #18
    //   7009: aload #18
    //   7011: invokeinterface list : ()Ljava/util/List;
    //   7016: astore #19
    //   7018: aload #14
    //   7020: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7023: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7026: aload #14
    //   7028: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7031: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7034: aload #19
    //   7036: ifnull -> 7092
    //   7039: aload #19
    //   7041: iconst_0
    //   7042: invokeinterface get : (I)Ljava/lang/Object;
    //   7047: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7050: astore #20
    //   7052: aload #20
    //   7054: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7057: invokevirtual intValue : ()I
    //   7060: iconst_1
    //   7061: if_icmpne -> 7072
    //   7064: aload #14
    //   7066: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7069: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7072: aload #20
    //   7074: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7077: invokevirtual intValue : ()I
    //   7080: iconst_1
    //   7081: if_icmpne -> 7092
    //   7084: aload #14
    //   7086: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7089: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7092: aload #14
    //   7094: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7097: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7100: aload #14
    //   7102: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7105: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7108: aload #14
    //   7110: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7113: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7116: aload #14
    //   7118: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7121: aload_1
    //   7122: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7125: ifeq -> 7147
    //   7128: aload #14
    //   7130: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7133: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7136: aload #14
    //   7138: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7141: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7144: goto -> 7163
    //   7147: aload #14
    //   7149: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7152: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7155: aload #14
    //   7157: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7160: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7163: aload #4
    //   7165: aload #14
    //   7167: invokeinterface add : (Ljava/lang/Object;)Z
    //   7172: pop
    //   7173: aload #13
    //   7175: new java/lang/Integer
    //   7178: dup
    //   7179: aload #13
    //   7181: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7184: invokevirtual intValue : ()I
    //   7187: iconst_1
    //   7188: iadd
    //   7189: invokespecial <init> : (I)V
    //   7192: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7195: goto -> 8899
    //   7198: aload #13
    //   7200: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   7203: ldc_w '30'
    //   7206: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7209: ifeq -> 7729
    //   7212: iload #8
    //   7214: iconst_1
    //   7215: if_icmpne -> 8899
    //   7218: aload_0
    //   7219: iload #7
    //   7221: invokespecial isLeapYear : (I)Z
    //   7224: ifeq -> 7478
    //   7227: iload #9
    //   7229: bipush #29
    //   7231: if_icmpne -> 8899
    //   7234: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7237: aload #13
    //   7239: ldc com/js/oa/scheme/event/vo/EventVO
    //   7241: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7244: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7247: astore #14
    //   7249: aload_0
    //   7250: getfield session : Lnet/sf/hibernate/Session;
    //   7253: new java/lang/StringBuilder
    //   7256: dup
    //   7257: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7259: invokespecial <init> : (Ljava/lang/String;)V
    //   7262: aload #14
    //   7264: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7267: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7270: ldc ' and eventAttender.empId ='
    //   7272: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7275: aload_1
    //   7276: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7279: invokevirtual toString : ()Ljava/lang/String;
    //   7282: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7287: astore #18
    //   7289: aload #18
    //   7291: invokeinterface list : ()Ljava/util/List;
    //   7296: astore #19
    //   7298: aload #14
    //   7300: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7303: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7306: aload #14
    //   7308: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7311: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7314: aload #19
    //   7316: ifnull -> 7372
    //   7319: aload #19
    //   7321: iconst_0
    //   7322: invokeinterface get : (I)Ljava/lang/Object;
    //   7327: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7330: astore #20
    //   7332: aload #20
    //   7334: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7337: invokevirtual intValue : ()I
    //   7340: iconst_1
    //   7341: if_icmpne -> 7352
    //   7344: aload #14
    //   7346: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7349: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7352: aload #20
    //   7354: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7357: invokevirtual intValue : ()I
    //   7360: iconst_1
    //   7361: if_icmpne -> 7372
    //   7364: aload #14
    //   7366: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7369: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7372: aload #14
    //   7374: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7377: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7380: aload #14
    //   7382: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7385: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7388: aload #14
    //   7390: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7393: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7396: aload #14
    //   7398: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7401: aload_1
    //   7402: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7405: ifeq -> 7427
    //   7408: aload #14
    //   7410: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7413: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7416: aload #14
    //   7418: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7421: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7424: goto -> 7443
    //   7427: aload #14
    //   7429: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7432: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7435: aload #14
    //   7437: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7440: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7443: aload #4
    //   7445: aload #14
    //   7447: invokeinterface add : (Ljava/lang/Object;)Z
    //   7452: pop
    //   7453: aload #13
    //   7455: new java/lang/Integer
    //   7458: dup
    //   7459: aload #13
    //   7461: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7464: invokevirtual intValue : ()I
    //   7467: iconst_1
    //   7468: iadd
    //   7469: invokespecial <init> : (I)V
    //   7472: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7475: goto -> 8899
    //   7478: iload #9
    //   7480: bipush #28
    //   7482: if_icmpne -> 8899
    //   7485: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7488: aload #13
    //   7490: ldc com/js/oa/scheme/event/vo/EventVO
    //   7492: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7495: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7498: astore #14
    //   7500: aload_0
    //   7501: getfield session : Lnet/sf/hibernate/Session;
    //   7504: new java/lang/StringBuilder
    //   7507: dup
    //   7508: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7510: invokespecial <init> : (Ljava/lang/String;)V
    //   7513: aload #14
    //   7515: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7518: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7521: ldc ' and eventAttender.empId ='
    //   7523: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7526: aload_1
    //   7527: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7530: invokevirtual toString : ()Ljava/lang/String;
    //   7533: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7538: astore #18
    //   7540: aload #18
    //   7542: invokeinterface list : ()Ljava/util/List;
    //   7547: astore #19
    //   7549: aload #14
    //   7551: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7554: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7557: aload #14
    //   7559: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7562: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7565: aload #19
    //   7567: ifnull -> 7623
    //   7570: aload #19
    //   7572: iconst_0
    //   7573: invokeinterface get : (I)Ljava/lang/Object;
    //   7578: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7581: astore #20
    //   7583: aload #20
    //   7585: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7588: invokevirtual intValue : ()I
    //   7591: iconst_1
    //   7592: if_icmpne -> 7603
    //   7595: aload #14
    //   7597: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7600: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7603: aload #20
    //   7605: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7608: invokevirtual intValue : ()I
    //   7611: iconst_1
    //   7612: if_icmpne -> 7623
    //   7615: aload #14
    //   7617: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7620: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7623: aload #14
    //   7625: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7628: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7631: aload #14
    //   7633: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7636: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7639: aload #14
    //   7641: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7644: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7647: aload #14
    //   7649: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7652: aload_1
    //   7653: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7656: ifeq -> 7678
    //   7659: aload #14
    //   7661: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7664: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7667: aload #14
    //   7669: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7672: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7675: goto -> 7694
    //   7678: aload #14
    //   7680: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7683: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7686: aload #14
    //   7688: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7691: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7694: aload #4
    //   7696: aload #14
    //   7698: invokeinterface add : (Ljava/lang/Object;)Z
    //   7703: pop
    //   7704: aload #13
    //   7706: new java/lang/Integer
    //   7709: dup
    //   7710: aload #13
    //   7712: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7715: invokevirtual intValue : ()I
    //   7718: iconst_1
    //   7719: iadd
    //   7720: invokespecial <init> : (I)V
    //   7723: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   7726: goto -> 8899
    //   7729: aload #13
    //   7731: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   7734: ldc_w '29'
    //   7737: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7740: ifeq -> 8899
    //   7743: iload #8
    //   7745: iconst_1
    //   7746: if_icmpne -> 8899
    //   7749: aload_0
    //   7750: iload #7
    //   7752: invokespecial isLeapYear : (I)Z
    //   7755: ifne -> 8899
    //   7758: iload #9
    //   7760: bipush #28
    //   7762: if_icmpne -> 8899
    //   7765: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   7768: aload #13
    //   7770: ldc com/js/oa/scheme/event/vo/EventVO
    //   7772: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   7775: checkcast com/js/oa/scheme/event/vo/EventVO
    //   7778: astore #14
    //   7780: aload_0
    //   7781: getfield session : Lnet/sf/hibernate/Session;
    //   7784: new java/lang/StringBuilder
    //   7787: dup
    //   7788: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   7790: invokespecial <init> : (Ljava/lang/String;)V
    //   7793: aload #14
    //   7795: invokevirtual getEventId : ()Ljava/lang/Long;
    //   7798: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7801: ldc ' and eventAttender.empId ='
    //   7803: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   7806: aload_1
    //   7807: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   7810: invokevirtual toString : ()Ljava/lang/String;
    //   7813: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   7818: astore #18
    //   7820: aload #18
    //   7822: invokeinterface list : ()Ljava/util/List;
    //   7827: astore #19
    //   7829: aload #14
    //   7831: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7834: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7837: aload #14
    //   7839: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7842: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7845: aload #19
    //   7847: ifnull -> 7903
    //   7850: aload #19
    //   7852: iconst_0
    //   7853: invokeinterface get : (I)Ljava/lang/Object;
    //   7858: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   7861: astore #20
    //   7863: aload #20
    //   7865: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   7868: invokevirtual intValue : ()I
    //   7871: iconst_1
    //   7872: if_icmpne -> 7883
    //   7875: aload #14
    //   7877: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7880: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   7883: aload #20
    //   7885: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   7888: invokevirtual intValue : ()I
    //   7891: iconst_1
    //   7892: if_icmpne -> 7903
    //   7895: aload #14
    //   7897: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7900: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   7903: aload #14
    //   7905: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7908: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7911: aload #14
    //   7913: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7916: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7919: aload #14
    //   7921: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   7924: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7927: aload #14
    //   7929: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   7932: aload_1
    //   7933: invokevirtual equals : (Ljava/lang/Object;)Z
    //   7936: ifeq -> 7958
    //   7939: aload #14
    //   7941: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7944: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7947: aload #14
    //   7949: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7952: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   7955: goto -> 7974
    //   7958: aload #14
    //   7960: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7963: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   7966: aload #14
    //   7968: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   7971: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   7974: aload #4
    //   7976: aload #14
    //   7978: invokeinterface add : (Ljava/lang/Object;)Z
    //   7983: pop
    //   7984: aload #13
    //   7986: new java/lang/Integer
    //   7989: dup
    //   7990: aload #13
    //   7992: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   7995: invokevirtual intValue : ()I
    //   7998: iconst_1
    //   7999: iadd
    //   8000: invokespecial <init> : (I)V
    //   8003: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   8006: goto -> 8899
    //   8009: aload #13
    //   8011: invokevirtual getOnTimeMode : ()Ljava/lang/Integer;
    //   8014: invokevirtual intValue : ()I
    //   8017: iconst_4
    //   8018: if_icmpne -> 8899
    //   8021: aload #13
    //   8023: invokevirtual getOnTimeContent : ()Ljava/lang/String;
    //   8026: ldc_w '\$'
    //   8029: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   8032: astore #18
    //   8034: aload #13
    //   8036: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8039: invokevirtual intValue : ()I
    //   8042: iconst_m1
    //   8043: if_icmpne -> 8307
    //   8046: aload #13
    //   8048: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   8051: aload #6
    //   8053: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8056: ifgt -> 8899
    //   8059: aload #18
    //   8061: iconst_0
    //   8062: aaload
    //   8063: invokestatic parseInt : (Ljava/lang/String;)I
    //   8066: iload #8
    //   8068: iconst_1
    //   8069: iadd
    //   8070: if_icmpne -> 8899
    //   8073: aload #18
    //   8075: iconst_1
    //   8076: aaload
    //   8077: invokestatic parseInt : (Ljava/lang/String;)I
    //   8080: iload #9
    //   8082: if_icmpne -> 8899
    //   8085: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   8088: aload #13
    //   8090: ldc com/js/oa/scheme/event/vo/EventVO
    //   8092: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   8095: checkcast com/js/oa/scheme/event/vo/EventVO
    //   8098: astore #14
    //   8100: aload_0
    //   8101: getfield session : Lnet/sf/hibernate/Session;
    //   8104: new java/lang/StringBuilder
    //   8107: dup
    //   8108: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   8110: invokespecial <init> : (Ljava/lang/String;)V
    //   8113: aload #14
    //   8115: invokevirtual getEventId : ()Ljava/lang/Long;
    //   8118: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8121: ldc ' and eventAttender.empId ='
    //   8123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   8126: aload_1
    //   8127: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8130: invokevirtual toString : ()Ljava/lang/String;
    //   8133: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   8138: astore #19
    //   8140: aload #19
    //   8142: invokeinterface list : ()Ljava/util/List;
    //   8147: astore #20
    //   8149: aload #14
    //   8151: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8154: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8157: aload #14
    //   8159: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8162: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8165: aload #20
    //   8167: ifnull -> 8223
    //   8170: aload #20
    //   8172: iconst_0
    //   8173: invokeinterface get : (I)Ljava/lang/Object;
    //   8178: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8181: astore #21
    //   8183: aload #21
    //   8185: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8188: invokevirtual intValue : ()I
    //   8191: iconst_1
    //   8192: if_icmpne -> 8203
    //   8195: aload #14
    //   8197: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8200: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8203: aload #21
    //   8205: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8208: invokevirtual intValue : ()I
    //   8211: iconst_1
    //   8212: if_icmpne -> 8223
    //   8215: aload #14
    //   8217: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8220: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8223: aload #14
    //   8225: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8228: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8231: aload #14
    //   8233: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8236: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8239: aload #14
    //   8241: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8244: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8247: aload #14
    //   8249: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   8252: aload_1
    //   8253: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8256: ifeq -> 8278
    //   8259: aload #14
    //   8261: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8264: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8267: aload #14
    //   8269: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8272: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8275: goto -> 8294
    //   8278: aload #14
    //   8280: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8283: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8286: aload #14
    //   8288: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8291: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8294: aload #4
    //   8296: aload #14
    //   8298: invokeinterface add : (Ljava/lang/Object;)Z
    //   8303: pop
    //   8304: goto -> 8899
    //   8307: aload #13
    //   8309: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8312: invokevirtual intValue : ()I
    //   8315: ifne -> 8592
    //   8318: aload #13
    //   8320: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   8323: aload #6
    //   8325: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8328: ifgt -> 8899
    //   8331: aload #18
    //   8333: iconst_0
    //   8334: aaload
    //   8335: invokestatic parseInt : (Ljava/lang/String;)I
    //   8338: iload #8
    //   8340: iconst_1
    //   8341: iadd
    //   8342: if_icmpne -> 8899
    //   8345: aload #18
    //   8347: iconst_1
    //   8348: aaload
    //   8349: invokestatic parseInt : (Ljava/lang/String;)I
    //   8352: iload #9
    //   8354: if_icmpne -> 8899
    //   8357: aload #13
    //   8359: invokevirtual getEchoEndTime : ()Ljava/util/Date;
    //   8362: aload #6
    //   8364: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8367: iflt -> 8899
    //   8370: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   8373: aload #13
    //   8375: ldc com/js/oa/scheme/event/vo/EventVO
    //   8377: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   8380: checkcast com/js/oa/scheme/event/vo/EventVO
    //   8383: astore #14
    //   8385: aload_0
    //   8386: getfield session : Lnet/sf/hibernate/Session;
    //   8389: new java/lang/StringBuilder
    //   8392: dup
    //   8393: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   8395: invokespecial <init> : (Ljava/lang/String;)V
    //   8398: aload #14
    //   8400: invokevirtual getEventId : ()Ljava/lang/Long;
    //   8403: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8406: ldc ' and eventAttender.empId ='
    //   8408: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   8411: aload_1
    //   8412: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8415: invokevirtual toString : ()Ljava/lang/String;
    //   8418: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   8423: astore #19
    //   8425: aload #19
    //   8427: invokeinterface list : ()Ljava/util/List;
    //   8432: astore #20
    //   8434: aload #14
    //   8436: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8439: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8442: aload #14
    //   8444: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8447: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8450: aload #20
    //   8452: ifnull -> 8508
    //   8455: aload #20
    //   8457: iconst_0
    //   8458: invokeinterface get : (I)Ljava/lang/Object;
    //   8463: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8466: astore #21
    //   8468: aload #21
    //   8470: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8473: invokevirtual intValue : ()I
    //   8476: iconst_1
    //   8477: if_icmpne -> 8488
    //   8480: aload #14
    //   8482: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8485: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8488: aload #21
    //   8490: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8493: invokevirtual intValue : ()I
    //   8496: iconst_1
    //   8497: if_icmpne -> 8508
    //   8500: aload #14
    //   8502: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8505: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8508: aload #14
    //   8510: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8513: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8516: aload #14
    //   8518: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8521: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8524: aload #14
    //   8526: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8529: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8532: aload #14
    //   8534: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   8537: aload_1
    //   8538: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8541: ifeq -> 8563
    //   8544: aload #14
    //   8546: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8549: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8552: aload #14
    //   8554: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8557: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8560: goto -> 8579
    //   8563: aload #14
    //   8565: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8568: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8571: aload #14
    //   8573: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8576: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8579: aload #4
    //   8581: aload #14
    //   8583: invokeinterface add : (Ljava/lang/Object;)Z
    //   8588: pop
    //   8589: goto -> 8899
    //   8592: aload #13
    //   8594: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8597: invokevirtual intValue : ()I
    //   8600: ifle -> 8899
    //   8603: aload #13
    //   8605: invokevirtual getEchoBeginTime : ()Ljava/util/Date;
    //   8608: aload #6
    //   8610: invokevirtual compareTo : (Ljava/util/Date;)I
    //   8613: ifgt -> 8899
    //   8616: aload #18
    //   8618: iconst_0
    //   8619: aaload
    //   8620: invokestatic parseInt : (Ljava/lang/String;)I
    //   8623: iload #8
    //   8625: iconst_1
    //   8626: iadd
    //   8627: if_icmpne -> 8899
    //   8630: aload #18
    //   8632: iconst_1
    //   8633: aaload
    //   8634: invokestatic parseInt : (Ljava/lang/String;)I
    //   8637: iload #9
    //   8639: if_icmpne -> 8899
    //   8642: aload #13
    //   8644: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8647: aload #13
    //   8649: invokevirtual getEchoCounter : ()Ljava/lang/Integer;
    //   8652: invokevirtual compareTo : (Ljava/lang/Integer;)I
    //   8655: ifle -> 8899
    //   8658: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   8661: aload #13
    //   8663: ldc com/js/oa/scheme/event/vo/EventVO
    //   8665: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   8668: checkcast com/js/oa/scheme/event/vo/EventVO
    //   8671: astore #14
    //   8673: aload_0
    //   8674: getfield session : Lnet/sf/hibernate/Session;
    //   8677: new java/lang/StringBuilder
    //   8680: dup
    //   8681: ldc 'select distinct eventAttender from com.js.oa.scheme.event.po.EventAttenderPO eventAttender where eventAttender.event.eventId = '
    //   8683: invokespecial <init> : (Ljava/lang/String;)V
    //   8686: aload #14
    //   8688: invokevirtual getEventId : ()Ljava/lang/Long;
    //   8691: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8694: ldc ' and eventAttender.empId ='
    //   8696: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   8699: aload_1
    //   8700: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   8703: invokevirtual toString : ()Ljava/lang/String;
    //   8706: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   8711: astore #19
    //   8713: aload #19
    //   8715: invokeinterface list : ()Ljava/util/List;
    //   8720: astore #20
    //   8722: aload #14
    //   8724: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8727: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8730: aload #14
    //   8732: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8735: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8738: aload #20
    //   8740: ifnull -> 8796
    //   8743: aload #20
    //   8745: iconst_0
    //   8746: invokeinterface get : (I)Ljava/lang/Object;
    //   8751: checkcast com/js/oa/scheme/event/po/EventAttenderPO
    //   8754: astore #21
    //   8756: aload #21
    //   8758: invokevirtual getEventIsViewed : ()Ljava/lang/Integer;
    //   8761: invokevirtual intValue : ()I
    //   8764: iconst_1
    //   8765: if_icmpne -> 8776
    //   8768: aload #14
    //   8770: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8773: invokevirtual setIsViewed : (Ljava/lang/Boolean;)V
    //   8776: aload #21
    //   8778: invokevirtual getAffirmRemind : ()Ljava/lang/Integer;
    //   8781: invokevirtual intValue : ()I
    //   8784: iconst_1
    //   8785: if_icmpne -> 8796
    //   8788: aload #14
    //   8790: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8793: invokevirtual setIsAffirmRemind : (Ljava/lang/Boolean;)V
    //   8796: aload #14
    //   8798: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8801: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8804: aload #14
    //   8806: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8809: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8812: aload #14
    //   8814: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   8817: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8820: aload #14
    //   8822: invokevirtual getEventEmpId : ()Ljava/lang/Long;
    //   8825: aload_1
    //   8826: invokevirtual equals : (Ljava/lang/Object;)Z
    //   8829: ifeq -> 8851
    //   8832: aload #14
    //   8834: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8837: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8840: aload #14
    //   8842: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8845: invokevirtual setCanModify : (Ljava/lang/Boolean;)V
    //   8848: goto -> 8867
    //   8851: aload #14
    //   8853: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8856: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   8859: aload #14
    //   8861: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   8864: invokevirtual setIsShare : (Ljava/lang/Boolean;)V
    //   8867: aload #4
    //   8869: aload #14
    //   8871: invokeinterface add : (Ljava/lang/Object;)Z
    //   8876: pop
    //   8877: aload #13
    //   8879: new java/lang/Integer
    //   8882: dup
    //   8883: aload #13
    //   8885: invokevirtual getEchoMode : ()Ljava/lang/Integer;
    //   8888: invokevirtual intValue : ()I
    //   8891: iconst_1
    //   8892: iadd
    //   8893: invokespecial <init> : (I)V
    //   8896: invokevirtual setEchoCounter : (Ljava/lang/Integer;)V
    //   8899: iinc #17, 1
    //   8902: iload #17
    //   8904: aload #12
    //   8906: invokeinterface size : ()I
    //   8911: if_icmplt -> 196
    //   8914: aload_0
    //   8915: getfield session : Lnet/sf/hibernate/Session;
    //   8918: invokeinterface flush : ()V
    //   8923: aload #4
    //   8925: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #4637	-> 0
    //   #4638	-> 9
    //   #4639	-> 13
    //   #4638	-> 16
    //   #4640	-> 21
    //   #4641	-> 30
    //   #4642	-> 37
    //   #4643	-> 45
    //   #4644	-> 53
    //   #4645	-> 61
    //   #4650	-> 70
    //   #4651	-> 83
    //   #4652	-> 93
    //   #4650	-> 131
    //   #4654	-> 138
    //   #4655	-> 147
    //   #4656	-> 150
    //   #4658	-> 153
    //   #4659	-> 167
    //   #4658	-> 174
    //   #4660	-> 181
    //   #4661	-> 190
    //   #4662	-> 196
    //   #4664	-> 210
    //   #4665	-> 222
    //   #4666	-> 236
    //   #4667	-> 246
    //   #4668	-> 259
    //   #4669	-> 264
    //   #4668	-> 266
    //   #4670	-> 272
    //   #4671	-> 284
    //   #4673	-> 296
    //   #4674	-> 299
    //   #4673	-> 303
    //   #4672	-> 306
    //   #4675	-> 311
    //   #4677	-> 324
    //   #4678	-> 332
    //   #4679	-> 337
    //   #4675	-> 344
    //   #4680	-> 351
    //   #4681	-> 360
    //   #4682	-> 368
    //   #4683	-> 376
    //   #4685	-> 381
    //   #4686	-> 383
    //   #4685	-> 384
    //   #4684	-> 392
    //   #4687	-> 394
    //   #4688	-> 399
    //   #4687	-> 403
    //   #4689	-> 406
    //   #4691	-> 414
    //   #4692	-> 419
    //   #4691	-> 423
    //   #4693	-> 426
    //   #4694	-> 428
    //   #4693	-> 431
    //   #4697	-> 434
    //   #4698	-> 442
    //   #4699	-> 450
    //   #4700	-> 458
    //   #4701	-> 470
    //   #4702	-> 478
    //   #4704	-> 489
    //   #4705	-> 497
    //   #4707	-> 505
    //   #4709	-> 518
    //   #4711	-> 529
    //   #4712	-> 534
    //   #4711	-> 536
    //   #4713	-> 542
    //   #4715	-> 554
    //   #4717	-> 557
    //   #4716	-> 561
    //   #4714	-> 564
    //   #4718	-> 569
    //   #4720	-> 582
    //   #4721	-> 590
    //   #4722	-> 595
    //   #4718	-> 602
    //   #4723	-> 609
    //   #4724	-> 618
    //   #4725	-> 626
    //   #4726	-> 634
    //   #4728	-> 639
    //   #4729	-> 641
    //   #4727	-> 650
    //   #4730	-> 652
    //   #4731	-> 657
    //   #4730	-> 661
    //   #4732	-> 664
    //   #4734	-> 672
    //   #4735	-> 677
    //   #4734	-> 681
    //   #4736	-> 684
    //   #4737	-> 686
    //   #4736	-> 689
    //   #4740	-> 692
    //   #4741	-> 700
    //   #4742	-> 708
    //   #4743	-> 716
    //   #4744	-> 728
    //   #4745	-> 736
    //   #4747	-> 747
    //   #4748	-> 755
    //   #4750	-> 763
    //   #4753	-> 776
    //   #4754	-> 787
    //   #4755	-> 797
    //   #4756	-> 802
    //   #4755	-> 805
    //   #4757	-> 807
    //   #4767	-> 820
    //   #4769	-> 833
    //   #4770	-> 845
    //   #4771	-> 848
    //   #4770	-> 855
    //   #4772	-> 860
    //   #4774	-> 873
    //   #4775	-> 881
    //   #4776	-> 886
    //   #4772	-> 893
    //   #4777	-> 900
    //   #4778	-> 909
    //   #4779	-> 917
    //   #4780	-> 925
    //   #4782	-> 930
    //   #4781	-> 941
    //   #4783	-> 943
    //   #4784	-> 951
    //   #4783	-> 952
    //   #4785	-> 955
    //   #4787	-> 963
    //   #4788	-> 971
    //   #4787	-> 972
    //   #4789	-> 975
    //   #4792	-> 983
    //   #4793	-> 991
    //   #4794	-> 999
    //   #4795	-> 1007
    //   #4796	-> 1019
    //   #4797	-> 1027
    //   #4799	-> 1038
    //   #4800	-> 1046
    //   #4802	-> 1054
    //   #4803	-> 1067
    //   #4804	-> 1078
    //   #4805	-> 1083
    //   #4804	-> 1085
    //   #4807	-> 1091
    //   #4808	-> 1094
    //   #4807	-> 1098
    //   #4806	-> 1101
    //   #4809	-> 1106
    //   #4811	-> 1119
    //   #4812	-> 1127
    //   #4809	-> 1139
    //   #4813	-> 1146
    //   #4814	-> 1155
    //   #4815	-> 1163
    //   #4816	-> 1171
    //   #4818	-> 1176
    //   #4817	-> 1187
    //   #4819	-> 1189
    //   #4820	-> 1194
    //   #4819	-> 1198
    //   #4821	-> 1201
    //   #4823	-> 1209
    //   #4824	-> 1214
    //   #4823	-> 1218
    //   #4825	-> 1221
    //   #4828	-> 1229
    //   #4829	-> 1237
    //   #4830	-> 1245
    //   #4831	-> 1253
    //   #4832	-> 1265
    //   #4833	-> 1273
    //   #4835	-> 1284
    //   #4836	-> 1292
    //   #4838	-> 1300
    //   #4840	-> 1313
    //   #4841	-> 1324
    //   #4842	-> 1331
    //   #4841	-> 1334
    //   #4844	-> 1340
    //   #4845	-> 1343
    //   #4844	-> 1347
    //   #4843	-> 1350
    //   #4846	-> 1355
    //   #4848	-> 1368
    //   #4849	-> 1376
    //   #4846	-> 1388
    //   #4850	-> 1395
    //   #4851	-> 1404
    //   #4852	-> 1412
    //   #4853	-> 1420
    //   #4855	-> 1425
    //   #4854	-> 1436
    //   #4856	-> 1438
    //   #4857	-> 1443
    //   #4856	-> 1447
    //   #4858	-> 1450
    //   #4860	-> 1458
    //   #4861	-> 1463
    //   #4860	-> 1467
    //   #4862	-> 1470
    //   #4865	-> 1478
    //   #4866	-> 1486
    //   #4867	-> 1494
    //   #4868	-> 1502
    //   #4869	-> 1514
    //   #4870	-> 1522
    //   #4872	-> 1533
    //   #4873	-> 1541
    //   #4875	-> 1549
    //   #4876	-> 1559
    //   #4877	-> 1567
    //   #4876	-> 1578
    //   #4885	-> 1584
    //   #4886	-> 1596
    //   #4887	-> 1608
    //   #4889	-> 1621
    //   #4890	-> 1638
    //   #4891	-> 1641
    //   #4890	-> 1648
    //   #4892	-> 1653
    //   #4894	-> 1666
    //   #4895	-> 1674
    //   #4896	-> 1679
    //   #4892	-> 1686
    //   #4897	-> 1693
    //   #4898	-> 1702
    //   #4899	-> 1710
    //   #4900	-> 1718
    //   #4902	-> 1723
    //   #4901	-> 1734
    //   #4903	-> 1736
    //   #4904	-> 1744
    //   #4903	-> 1745
    //   #4905	-> 1748
    //   #4907	-> 1756
    //   #4908	-> 1768
    //   #4911	-> 1776
    //   #4912	-> 1784
    //   #4913	-> 1792
    //   #4914	-> 1800
    //   #4915	-> 1812
    //   #4916	-> 1820
    //   #4918	-> 1831
    //   #4919	-> 1839
    //   #4921	-> 1847
    //   #4923	-> 1860
    //   #4924	-> 1871
    //   #4926	-> 1884
    //   #4927	-> 1901
    //   #4929	-> 1914
    //   #4930	-> 1917
    //   #4929	-> 1924
    //   #4931	-> 1929
    //   #4933	-> 1942
    //   #4934	-> 1950
    //   #4935	-> 1955
    //   #4931	-> 1962
    //   #4936	-> 1969
    //   #4937	-> 1978
    //   #4938	-> 1986
    //   #4939	-> 1994
    //   #4941	-> 1999
    //   #4940	-> 2010
    //   #4942	-> 2012
    //   #4943	-> 2020
    //   #4942	-> 2021
    //   #4944	-> 2024
    //   #4946	-> 2032
    //   #4947	-> 2044
    //   #4950	-> 2052
    //   #4951	-> 2060
    //   #4952	-> 2068
    //   #4953	-> 2076
    //   #4954	-> 2088
    //   #4955	-> 2096
    //   #4957	-> 2107
    //   #4958	-> 2115
    //   #4960	-> 2123
    //   #4962	-> 2136
    //   #4963	-> 2147
    //   #4965	-> 2160
    //   #4966	-> 2172
    //   #4965	-> 2174
    //   #4967	-> 2177
    //   #4968	-> 2184
    //   #4967	-> 2187
    //   #4969	-> 2193
    //   #4970	-> 2196
    //   #4969	-> 2203
    //   #4971	-> 2208
    //   #4973	-> 2221
    //   #4974	-> 2229
    //   #4975	-> 2234
    //   #4971	-> 2241
    //   #4976	-> 2248
    //   #4977	-> 2257
    //   #4978	-> 2265
    //   #4979	-> 2273
    //   #4981	-> 2278
    //   #4980	-> 2289
    //   #4982	-> 2291
    //   #4983	-> 2299
    //   #4982	-> 2300
    //   #4984	-> 2303
    //   #4986	-> 2311
    //   #4987	-> 2319
    //   #4986	-> 2320
    //   #4988	-> 2323
    //   #4991	-> 2331
    //   #4992	-> 2339
    //   #4993	-> 2347
    //   #4994	-> 2355
    //   #4995	-> 2367
    //   #4996	-> 2375
    //   #4998	-> 2386
    //   #4999	-> 2394
    //   #5001	-> 2402
    //   #5002	-> 2412
    //   #5003	-> 2420
    //   #5004	-> 2426
    //   #5002	-> 2431
    //   #5011	-> 2437
    //   #5012	-> 2449
    //   #5013	-> 2461
    //   #5015	-> 2474
    //   #5017	-> 2487
    //   #5018	-> 2490
    //   #5017	-> 2497
    //   #5019	-> 2502
    //   #5021	-> 2515
    //   #5022	-> 2523
    //   #5023	-> 2528
    //   #5019	-> 2535
    //   #5024	-> 2542
    //   #5025	-> 2551
    //   #5026	-> 2559
    //   #5027	-> 2567
    //   #5029	-> 2572
    //   #5028	-> 2583
    //   #5030	-> 2585
    //   #5031	-> 2593
    //   #5030	-> 2594
    //   #5032	-> 2597
    //   #5034	-> 2605
    //   #5035	-> 2617
    //   #5038	-> 2625
    //   #5039	-> 2633
    //   #5040	-> 2641
    //   #5041	-> 2649
    //   #5042	-> 2661
    //   #5043	-> 2669
    //   #5045	-> 2680
    //   #5046	-> 2688
    //   #5048	-> 2696
    //   #5050	-> 2709
    //   #5051	-> 2714
    //   #5050	-> 2716
    //   #5052	-> 2722
    //   #5053	-> 2736
    //   #5055	-> 2792
    //   #5056	-> 2801
    //   #5059	-> 2808
    //   #5060	-> 2811
    //   #5061	-> 2813
    //   #5060	-> 2815
    //   #5057	-> 2818
    //   #5062	-> 2823
    //   #5064	-> 2836
    //   #5065	-> 2844
    //   #5066	-> 2849
    //   #5062	-> 2856
    //   #5067	-> 2863
    //   #5068	-> 2872
    //   #5069	-> 2880
    //   #5070	-> 2882
    //   #5069	-> 2885
    //   #5071	-> 2888
    //   #5074	-> 2893
    //   #5072	-> 2904
    //   #5075	-> 2906
    //   #5076	-> 2908
    //   #5077	-> 2911
    //   #5075	-> 2915
    //   #5078	-> 2918
    //   #5079	-> 2920
    //   #5078	-> 2923
    //   #5081	-> 2926
    //   #5082	-> 2928
    //   #5083	-> 2931
    //   #5081	-> 2935
    //   #5084	-> 2938
    //   #5085	-> 2940
    //   #5084	-> 2943
    //   #5088	-> 2946
    //   #5089	-> 2954
    //   #5090	-> 2962
    //   #5091	-> 2970
    //   #5092	-> 2975
    //   #5091	-> 2976
    //   #5093	-> 2982
    //   #5094	-> 2990
    //   #5096	-> 3001
    //   #5097	-> 3009
    //   #5099	-> 3017
    //   #5102	-> 3030
    //   #5105	-> 3037
    //   #5106	-> 3040
    //   #5107	-> 3042
    //   #5106	-> 3044
    //   #5103	-> 3047
    //   #5108	-> 3052
    //   #5110	-> 3065
    //   #5111	-> 3073
    //   #5112	-> 3078
    //   #5108	-> 3085
    //   #5113	-> 3092
    //   #5114	-> 3101
    //   #5115	-> 3109
    //   #5116	-> 3111
    //   #5115	-> 3114
    //   #5117	-> 3117
    //   #5120	-> 3122
    //   #5118	-> 3133
    //   #5121	-> 3135
    //   #5122	-> 3137
    //   #5123	-> 3140
    //   #5121	-> 3144
    //   #5124	-> 3147
    //   #5125	-> 3149
    //   #5124	-> 3152
    //   #5127	-> 3155
    //   #5128	-> 3157
    //   #5129	-> 3160
    //   #5127	-> 3164
    //   #5130	-> 3167
    //   #5131	-> 3169
    //   #5130	-> 3172
    //   #5134	-> 3175
    //   #5135	-> 3183
    //   #5136	-> 3191
    //   #5137	-> 3199
    //   #5138	-> 3204
    //   #5137	-> 3205
    //   #5139	-> 3211
    //   #5140	-> 3219
    //   #5142	-> 3230
    //   #5143	-> 3238
    //   #5145	-> 3246
    //   #5148	-> 3256
    //   #5153	-> 3259
    //   #5155	-> 3266
    //   #5156	-> 3269
    //   #5157	-> 3271
    //   #5156	-> 3273
    //   #5154	-> 3276
    //   #5158	-> 3281
    //   #5160	-> 3294
    //   #5161	-> 3302
    //   #5162	-> 3307
    //   #5158	-> 3314
    //   #5163	-> 3321
    //   #5164	-> 3330
    //   #5165	-> 3338
    //   #5166	-> 3346
    //   #5168	-> 3351
    //   #5169	-> 3353
    //   #5167	-> 3362
    //   #5170	-> 3364
    //   #5171	-> 3369
    //   #5170	-> 3373
    //   #5172	-> 3376
    //   #5174	-> 3384
    //   #5175	-> 3389
    //   #5174	-> 3393
    //   #5176	-> 3396
    //   #5177	-> 3398
    //   #5176	-> 3401
    //   #5180	-> 3404
    //   #5181	-> 3412
    //   #5182	-> 3420
    //   #5183	-> 3428
    //   #5184	-> 3440
    //   #5185	-> 3448
    //   #5187	-> 3459
    //   #5188	-> 3467
    //   #5190	-> 3475
    //   #5194	-> 3485
    //   #5195	-> 3488
    //   #5196	-> 3493
    //   #5195	-> 3496
    //   #5197	-> 3502
    //   #5198	-> 3508
    //   #5199	-> 3517
    //   #5202	-> 3524
    //   #5203	-> 3527
    //   #5204	-> 3529
    //   #5203	-> 3531
    //   #5200	-> 3534
    //   #5205	-> 3539
    //   #5207	-> 3552
    //   #5208	-> 3560
    //   #5209	-> 3565
    //   #5205	-> 3572
    //   #5210	-> 3579
    //   #5211	-> 3588
    //   #5212	-> 3596
    //   #5213	-> 3598
    //   #5212	-> 3601
    //   #5214	-> 3604
    //   #5216	-> 3609
    //   #5217	-> 3611
    //   #5215	-> 3620
    //   #5218	-> 3622
    //   #5219	-> 3624
    //   #5220	-> 3627
    //   #5218	-> 3631
    //   #5221	-> 3634
    //   #5222	-> 3636
    //   #5221	-> 3639
    //   #5224	-> 3642
    //   #5225	-> 3644
    //   #5226	-> 3647
    //   #5224	-> 3651
    //   #5227	-> 3654
    //   #5228	-> 3656
    //   #5227	-> 3659
    //   #5231	-> 3662
    //   #5232	-> 3670
    //   #5233	-> 3678
    //   #5234	-> 3686
    //   #5235	-> 3691
    //   #5234	-> 3692
    //   #5236	-> 3698
    //   #5237	-> 3706
    //   #5239	-> 3717
    //   #5240	-> 3725
    //   #5242	-> 3733
    //   #5245	-> 3746
    //   #5248	-> 3753
    //   #5249	-> 3756
    //   #5250	-> 3758
    //   #5249	-> 3760
    //   #5246	-> 3763
    //   #5251	-> 3768
    //   #5253	-> 3781
    //   #5254	-> 3789
    //   #5255	-> 3794
    //   #5251	-> 3801
    //   #5256	-> 3808
    //   #5257	-> 3817
    //   #5258	-> 3825
    //   #5259	-> 3827
    //   #5258	-> 3830
    //   #5260	-> 3833
    //   #5262	-> 3838
    //   #5263	-> 3840
    //   #5261	-> 3849
    //   #5264	-> 3851
    //   #5265	-> 3853
    //   #5266	-> 3856
    //   #5264	-> 3860
    //   #5267	-> 3863
    //   #5268	-> 3865
    //   #5267	-> 3868
    //   #5270	-> 3871
    //   #5271	-> 3873
    //   #5272	-> 3876
    //   #5270	-> 3880
    //   #5273	-> 3883
    //   #5274	-> 3885
    //   #5273	-> 3888
    //   #5277	-> 3891
    //   #5278	-> 3899
    //   #5279	-> 3907
    //   #5280	-> 3915
    //   #5281	-> 3920
    //   #5280	-> 3921
    //   #5282	-> 3927
    //   #5283	-> 3935
    //   #5285	-> 3946
    //   #5286	-> 3954
    //   #5288	-> 3962
    //   #5292	-> 3975
    //   #5293	-> 3980
    //   #5292	-> 3983
    //   #5294	-> 3989
    //   #5295	-> 3995
    //   #5296	-> 4004
    //   #5299	-> 4011
    //   #5300	-> 4014
    //   #5301	-> 4016
    //   #5300	-> 4018
    //   #5297	-> 4021
    //   #5302	-> 4026
    //   #5304	-> 4039
    //   #5305	-> 4047
    //   #5306	-> 4052
    //   #5302	-> 4059
    //   #5307	-> 4066
    //   #5308	-> 4075
    //   #5309	-> 4083
    //   #5310	-> 4085
    //   #5309	-> 4088
    //   #5311	-> 4091
    //   #5313	-> 4096
    //   #5314	-> 4098
    //   #5312	-> 4107
    //   #5315	-> 4109
    //   #5316	-> 4111
    //   #5317	-> 4114
    //   #5315	-> 4118
    //   #5318	-> 4121
    //   #5319	-> 4123
    //   #5318	-> 4126
    //   #5321	-> 4129
    //   #5322	-> 4131
    //   #5323	-> 4134
    //   #5321	-> 4138
    //   #5324	-> 4141
    //   #5325	-> 4143
    //   #5324	-> 4146
    //   #5328	-> 4149
    //   #5329	-> 4157
    //   #5330	-> 4165
    //   #5331	-> 4173
    //   #5332	-> 4178
    //   #5331	-> 4179
    //   #5333	-> 4185
    //   #5334	-> 4193
    //   #5336	-> 4204
    //   #5337	-> 4212
    //   #5339	-> 4220
    //   #5347	-> 4233
    //   #5348	-> 4244
    //   #5350	-> 4257
    //   #5351	-> 4270
    //   #5353	-> 4283
    //   #5354	-> 4286
    //   #5353	-> 4293
    //   #5355	-> 4298
    //   #5357	-> 4311
    //   #5358	-> 4319
    //   #5359	-> 4324
    //   #5355	-> 4331
    //   #5360	-> 4338
    //   #5361	-> 4347
    //   #5362	-> 4355
    //   #5363	-> 4363
    //   #5365	-> 4368
    //   #5364	-> 4379
    //   #5366	-> 4381
    //   #5367	-> 4389
    //   #5366	-> 4390
    //   #5368	-> 4393
    //   #5370	-> 4401
    //   #5371	-> 4413
    //   #5374	-> 4421
    //   #5375	-> 4429
    //   #5376	-> 4437
    //   #5377	-> 4445
    //   #5378	-> 4457
    //   #5379	-> 4465
    //   #5381	-> 4476
    //   #5382	-> 4484
    //   #5384	-> 4492
    //   #5386	-> 4505
    //   #5387	-> 4510
    //   #5386	-> 4512
    //   #5388	-> 4518
    //   #5391	-> 4531
    //   #5392	-> 4545
    //   #5394	-> 4600
    //   #5395	-> 4609
    //   #5398	-> 4616
    //   #5399	-> 4619
    //   #5400	-> 4621
    //   #5399	-> 4623
    //   #5396	-> 4626
    //   #5401	-> 4631
    //   #5403	-> 4644
    //   #5404	-> 4652
    //   #5405	-> 4657
    //   #5401	-> 4664
    //   #5406	-> 4671
    //   #5407	-> 4680
    //   #5408	-> 4688
    //   #5409	-> 4690
    //   #5408	-> 4693
    //   #5410	-> 4696
    //   #5413	-> 4701
    //   #5411	-> 4712
    //   #5414	-> 4714
    //   #5415	-> 4716
    //   #5416	-> 4719
    //   #5414	-> 4723
    //   #5417	-> 4726
    //   #5418	-> 4728
    //   #5417	-> 4731
    //   #5420	-> 4734
    //   #5421	-> 4736
    //   #5422	-> 4739
    //   #5420	-> 4743
    //   #5423	-> 4746
    //   #5424	-> 4748
    //   #5423	-> 4751
    //   #5427	-> 4754
    //   #5428	-> 4762
    //   #5429	-> 4770
    //   #5430	-> 4778
    //   #5431	-> 4783
    //   #5430	-> 4784
    //   #5432	-> 4790
    //   #5433	-> 4798
    //   #5435	-> 4809
    //   #5436	-> 4817
    //   #5438	-> 4825
    //   #5441	-> 4838
    //   #5444	-> 4845
    //   #5445	-> 4848
    //   #5446	-> 4850
    //   #5445	-> 4852
    //   #5442	-> 4855
    //   #5447	-> 4860
    //   #5449	-> 4873
    //   #5450	-> 4881
    //   #5451	-> 4886
    //   #5447	-> 4893
    //   #5452	-> 4900
    //   #5453	-> 4909
    //   #5454	-> 4917
    //   #5455	-> 4919
    //   #5454	-> 4922
    //   #5456	-> 4925
    //   #5459	-> 4930
    //   #5457	-> 4941
    //   #5460	-> 4943
    //   #5461	-> 4945
    //   #5462	-> 4948
    //   #5460	-> 4952
    //   #5463	-> 4955
    //   #5464	-> 4957
    //   #5463	-> 4960
    //   #5466	-> 4963
    //   #5467	-> 4965
    //   #5468	-> 4968
    //   #5466	-> 4972
    //   #5469	-> 4975
    //   #5470	-> 4977
    //   #5469	-> 4980
    //   #5473	-> 4983
    //   #5474	-> 4991
    //   #5475	-> 4999
    //   #5476	-> 5007
    //   #5477	-> 5012
    //   #5476	-> 5013
    //   #5478	-> 5019
    //   #5479	-> 5027
    //   #5481	-> 5038
    //   #5482	-> 5046
    //   #5484	-> 5054
    //   #5487	-> 5064
    //   #5492	-> 5067
    //   #5494	-> 5074
    //   #5495	-> 5077
    //   #5496	-> 5079
    //   #5495	-> 5081
    //   #5493	-> 5084
    //   #5497	-> 5089
    //   #5499	-> 5102
    //   #5500	-> 5110
    //   #5501	-> 5115
    //   #5497	-> 5122
    //   #5502	-> 5129
    //   #5503	-> 5138
    //   #5504	-> 5146
    //   #5505	-> 5154
    //   #5507	-> 5159
    //   #5508	-> 5161
    //   #5506	-> 5170
    //   #5509	-> 5172
    //   #5510	-> 5177
    //   #5509	-> 5181
    //   #5511	-> 5184
    //   #5513	-> 5192
    //   #5514	-> 5197
    //   #5513	-> 5201
    //   #5515	-> 5204
    //   #5516	-> 5206
    //   #5515	-> 5209
    //   #5519	-> 5212
    //   #5520	-> 5220
    //   #5521	-> 5228
    //   #5522	-> 5236
    //   #5523	-> 5248
    //   #5524	-> 5256
    //   #5526	-> 5267
    //   #5527	-> 5275
    //   #5529	-> 5283
    //   #5533	-> 5293
    //   #5534	-> 5296
    //   #5535	-> 5301
    //   #5534	-> 5304
    //   #5536	-> 5310
    //   #5537	-> 5316
    //   #5538	-> 5325
    //   #5541	-> 5332
    //   #5542	-> 5335
    //   #5543	-> 5337
    //   #5542	-> 5339
    //   #5539	-> 5342
    //   #5544	-> 5347
    //   #5546	-> 5360
    //   #5547	-> 5368
    //   #5548	-> 5373
    //   #5544	-> 5380
    //   #5549	-> 5387
    //   #5550	-> 5396
    //   #5551	-> 5404
    //   #5552	-> 5406
    //   #5551	-> 5409
    //   #5553	-> 5412
    //   #5555	-> 5417
    //   #5556	-> 5419
    //   #5554	-> 5428
    //   #5557	-> 5430
    //   #5558	-> 5432
    //   #5559	-> 5435
    //   #5557	-> 5439
    //   #5560	-> 5442
    //   #5561	-> 5444
    //   #5560	-> 5447
    //   #5563	-> 5450
    //   #5564	-> 5452
    //   #5565	-> 5455
    //   #5563	-> 5459
    //   #5566	-> 5462
    //   #5567	-> 5464
    //   #5566	-> 5467
    //   #5570	-> 5470
    //   #5571	-> 5478
    //   #5572	-> 5486
    //   #5573	-> 5494
    //   #5574	-> 5499
    //   #5573	-> 5500
    //   #5575	-> 5506
    //   #5576	-> 5514
    //   #5578	-> 5525
    //   #5579	-> 5533
    //   #5581	-> 5541
    //   #5584	-> 5554
    //   #5587	-> 5561
    //   #5588	-> 5564
    //   #5589	-> 5566
    //   #5588	-> 5568
    //   #5585	-> 5571
    //   #5590	-> 5576
    //   #5592	-> 5589
    //   #5593	-> 5597
    //   #5594	-> 5602
    //   #5590	-> 5609
    //   #5595	-> 5616
    //   #5596	-> 5625
    //   #5597	-> 5633
    //   #5598	-> 5635
    //   #5597	-> 5638
    //   #5599	-> 5641
    //   #5601	-> 5646
    //   #5602	-> 5648
    //   #5600	-> 5657
    //   #5603	-> 5659
    //   #5604	-> 5661
    //   #5605	-> 5664
    //   #5603	-> 5668
    //   #5606	-> 5671
    //   #5607	-> 5673
    //   #5606	-> 5676
    //   #5609	-> 5679
    //   #5610	-> 5681
    //   #5611	-> 5684
    //   #5609	-> 5688
    //   #5612	-> 5691
    //   #5613	-> 5693
    //   #5612	-> 5696
    //   #5616	-> 5699
    //   #5617	-> 5707
    //   #5618	-> 5715
    //   #5619	-> 5723
    //   #5620	-> 5728
    //   #5619	-> 5729
    //   #5621	-> 5735
    //   #5622	-> 5743
    //   #5624	-> 5754
    //   #5625	-> 5762
    //   #5627	-> 5770
    //   #5631	-> 5783
    //   #5632	-> 5788
    //   #5631	-> 5791
    //   #5633	-> 5797
    //   #5634	-> 5803
    //   #5635	-> 5812
    //   #5638	-> 5819
    //   #5639	-> 5822
    //   #5640	-> 5824
    //   #5639	-> 5826
    //   #5636	-> 5829
    //   #5641	-> 5834
    //   #5643	-> 5847
    //   #5644	-> 5855
    //   #5645	-> 5860
    //   #5641	-> 5867
    //   #5646	-> 5874
    //   #5647	-> 5883
    //   #5648	-> 5891
    //   #5649	-> 5893
    //   #5648	-> 5896
    //   #5650	-> 5899
    //   #5652	-> 5904
    //   #5653	-> 5906
    //   #5651	-> 5915
    //   #5654	-> 5917
    //   #5655	-> 5919
    //   #5656	-> 5922
    //   #5654	-> 5926
    //   #5657	-> 5929
    //   #5658	-> 5931
    //   #5657	-> 5934
    //   #5660	-> 5937
    //   #5661	-> 5939
    //   #5662	-> 5942
    //   #5660	-> 5946
    //   #5663	-> 5949
    //   #5664	-> 5951
    //   #5663	-> 5954
    //   #5667	-> 5957
    //   #5668	-> 5965
    //   #5669	-> 5973
    //   #5670	-> 5981
    //   #5671	-> 5986
    //   #5670	-> 5987
    //   #5672	-> 5993
    //   #5673	-> 6001
    //   #5675	-> 6012
    //   #5676	-> 6020
    //   #5678	-> 6028
    //   #5685	-> 6041
    //   #5686	-> 6052
    //   #5688	-> 6065
    //   #5689	-> 6078
    //   #5690	-> 6083
    //   #5693	-> 6094
    //   #5694	-> 6097
    //   #5695	-> 6099
    //   #5694	-> 6101
    //   #5691	-> 6104
    //   #5696	-> 6109
    //   #5698	-> 6122
    //   #5699	-> 6130
    //   #5700	-> 6135
    //   #5696	-> 6142
    //   #5701	-> 6149
    //   #5702	-> 6158
    //   #5703	-> 6166
    //   #5704	-> 6174
    //   #5706	-> 6179
    //   #5705	-> 6190
    //   #5707	-> 6192
    //   #5708	-> 6200
    //   #5707	-> 6201
    //   #5709	-> 6204
    //   #5711	-> 6212
    //   #5712	-> 6224
    //   #5715	-> 6232
    //   #5716	-> 6240
    //   #5717	-> 6248
    //   #5718	-> 6256
    //   #5719	-> 6268
    //   #5720	-> 6276
    //   #5722	-> 6287
    //   #5723	-> 6295
    //   #5725	-> 6303
    //   #5726	-> 6313
    //   #5727	-> 6321
    //   #5726	-> 6332
    //   #5730	-> 6338
    //   #5731	-> 6343
    //   #5730	-> 6345
    //   #5732	-> 6351
    //   #5733	-> 6356
    //   #5734	-> 6367
    //   #5735	-> 6381
    //   #5737	-> 6436
    //   #5738	-> 6445
    //   #5741	-> 6452
    //   #5742	-> 6455
    //   #5743	-> 6457
    //   #5742	-> 6459
    //   #5739	-> 6462
    //   #5744	-> 6467
    //   #5746	-> 6480
    //   #5747	-> 6488
    //   #5748	-> 6493
    //   #5744	-> 6500
    //   #5749	-> 6507
    //   #5750	-> 6516
    //   #5751	-> 6524
    //   #5752	-> 6526
    //   #5751	-> 6529
    //   #5753	-> 6532
    //   #5756	-> 6537
    //   #5754	-> 6548
    //   #5757	-> 6550
    //   #5758	-> 6552
    //   #5759	-> 6555
    //   #5757	-> 6559
    //   #5760	-> 6562
    //   #5761	-> 6564
    //   #5760	-> 6567
    //   #5763	-> 6570
    //   #5764	-> 6572
    //   #5765	-> 6575
    //   #5763	-> 6579
    //   #5766	-> 6582
    //   #5767	-> 6584
    //   #5766	-> 6587
    //   #5770	-> 6590
    //   #5771	-> 6598
    //   #5772	-> 6606
    //   #5773	-> 6614
    //   #5774	-> 6619
    //   #5773	-> 6620
    //   #5775	-> 6626
    //   #5776	-> 6634
    //   #5778	-> 6645
    //   #5779	-> 6653
    //   #5781	-> 6661
    //   #5782	-> 6671
    //   #5783	-> 6673
    //   #5784	-> 6679
    //   #5785	-> 6682
    //   #5782	-> 6687
    //   #5789	-> 6696
    //   #5792	-> 6703
    //   #5793	-> 6706
    //   #5794	-> 6708
    //   #5793	-> 6710
    //   #5790	-> 6713
    //   #5795	-> 6718
    //   #5797	-> 6731
    //   #5798	-> 6739
    //   #5799	-> 6744
    //   #5795	-> 6751
    //   #5800	-> 6758
    //   #5801	-> 6767
    //   #5802	-> 6775
    //   #5803	-> 6777
    //   #5802	-> 6780
    //   #5804	-> 6783
    //   #5807	-> 6788
    //   #5805	-> 6799
    //   #5808	-> 6801
    //   #5809	-> 6803
    //   #5810	-> 6806
    //   #5808	-> 6810
    //   #5811	-> 6813
    //   #5812	-> 6815
    //   #5811	-> 6818
    //   #5814	-> 6821
    //   #5815	-> 6823
    //   #5816	-> 6826
    //   #5814	-> 6830
    //   #5817	-> 6833
    //   #5818	-> 6835
    //   #5817	-> 6838
    //   #5821	-> 6841
    //   #5822	-> 6849
    //   #5823	-> 6857
    //   #5824	-> 6865
    //   #5825	-> 6870
    //   #5824	-> 6871
    //   #5826	-> 6877
    //   #5827	-> 6885
    //   #5829	-> 6896
    //   #5830	-> 6904
    //   #5832	-> 6912
    //   #5833	-> 6922
    //   #5834	-> 6924
    //   #5835	-> 6930
    //   #5836	-> 6933
    //   #5833	-> 6938
    //   #5840	-> 6944
    //   #5845	-> 6947
    //   #5847	-> 6954
    //   #5848	-> 6957
    //   #5849	-> 6959
    //   #5848	-> 6961
    //   #5846	-> 6964
    //   #5850	-> 6969
    //   #5852	-> 6982
    //   #5853	-> 6990
    //   #5854	-> 6995
    //   #5850	-> 7002
    //   #5855	-> 7009
    //   #5856	-> 7018
    //   #5857	-> 7026
    //   #5858	-> 7034
    //   #5860	-> 7039
    //   #5861	-> 7041
    //   #5859	-> 7050
    //   #5862	-> 7052
    //   #5863	-> 7057
    //   #5862	-> 7061
    //   #5864	-> 7064
    //   #5866	-> 7072
    //   #5867	-> 7077
    //   #5866	-> 7081
    //   #5868	-> 7084
    //   #5869	-> 7086
    //   #5868	-> 7089
    //   #5872	-> 7092
    //   #5873	-> 7100
    //   #5874	-> 7108
    //   #5875	-> 7116
    //   #5876	-> 7128
    //   #5877	-> 7136
    //   #5879	-> 7147
    //   #5880	-> 7155
    //   #5882	-> 7163
    //   #5883	-> 7173
    //   #5884	-> 7179
    //   #5885	-> 7184
    //   #5884	-> 7188
    //   #5883	-> 7192
    //   #5890	-> 7195
    //   #5891	-> 7198
    //   #5892	-> 7203
    //   #5891	-> 7206
    //   #5893	-> 7212
    //   #5894	-> 7218
    //   #5895	-> 7227
    //   #5898	-> 7234
    //   #5899	-> 7237
    //   #5900	-> 7239
    //   #5899	-> 7241
    //   #5896	-> 7244
    //   #5901	-> 7249
    //   #5903	-> 7262
    //   #5904	-> 7270
    //   #5905	-> 7275
    //   #5901	-> 7282
    //   #5906	-> 7289
    //   #5907	-> 7298
    //   #5908	-> 7306
    //   #5909	-> 7308
    //   #5908	-> 7311
    //   #5910	-> 7314
    //   #5912	-> 7319
    //   #5913	-> 7321
    //   #5911	-> 7330
    //   #5914	-> 7332
    //   #5915	-> 7334
    //   #5916	-> 7337
    //   #5914	-> 7341
    //   #5917	-> 7344
    //   #5918	-> 7346
    //   #5917	-> 7349
    //   #5920	-> 7352
    //   #5921	-> 7354
    //   #5922	-> 7357
    //   #5920	-> 7361
    //   #5923	-> 7364
    //   #5924	-> 7366
    //   #5923	-> 7369
    //   #5927	-> 7372
    //   #5928	-> 7380
    //   #5929	-> 7388
    //   #5930	-> 7396
    //   #5931	-> 7401
    //   #5930	-> 7402
    //   #5932	-> 7408
    //   #5933	-> 7416
    //   #5935	-> 7427
    //   #5936	-> 7435
    //   #5938	-> 7443
    //   #5939	-> 7453
    //   #5940	-> 7455
    //   #5941	-> 7461
    //   #5942	-> 7464
    //   #5939	-> 7469
    //   #5946	-> 7478
    //   #5949	-> 7485
    //   #5950	-> 7488
    //   #5951	-> 7490
    //   #5950	-> 7492
    //   #5947	-> 7495
    //   #5952	-> 7500
    //   #5954	-> 7513
    //   #5955	-> 7521
    //   #5956	-> 7526
    //   #5952	-> 7533
    //   #5957	-> 7540
    //   #5958	-> 7549
    //   #5959	-> 7557
    //   #5960	-> 7559
    //   #5959	-> 7562
    //   #5961	-> 7565
    //   #5963	-> 7570
    //   #5964	-> 7572
    //   #5962	-> 7581
    //   #5965	-> 7583
    //   #5966	-> 7585
    //   #5967	-> 7588
    //   #5965	-> 7592
    //   #5968	-> 7595
    //   #5969	-> 7597
    //   #5968	-> 7600
    //   #5971	-> 7603
    //   #5972	-> 7605
    //   #5973	-> 7608
    //   #5971	-> 7612
    //   #5974	-> 7615
    //   #5975	-> 7617
    //   #5974	-> 7620
    //   #5978	-> 7623
    //   #5979	-> 7631
    //   #5980	-> 7639
    //   #5981	-> 7647
    //   #5982	-> 7652
    //   #5981	-> 7653
    //   #5983	-> 7659
    //   #5984	-> 7667
    //   #5986	-> 7678
    //   #5987	-> 7686
    //   #5989	-> 7694
    //   #5990	-> 7704
    //   #5991	-> 7706
    //   #5992	-> 7712
    //   #5993	-> 7715
    //   #5990	-> 7720
    //   #5998	-> 7729
    //   #5999	-> 7734
    //   #5998	-> 7737
    //   #6000	-> 7743
    //   #6001	-> 7749
    //   #6002	-> 7758
    //   #6005	-> 7765
    //   #6006	-> 7768
    //   #6007	-> 7770
    //   #6006	-> 7772
    //   #6003	-> 7775
    //   #6008	-> 7780
    //   #6010	-> 7793
    //   #6011	-> 7801
    //   #6012	-> 7806
    //   #6008	-> 7813
    //   #6013	-> 7820
    //   #6014	-> 7829
    //   #6015	-> 7837
    //   #6016	-> 7839
    //   #6015	-> 7842
    //   #6017	-> 7845
    //   #6019	-> 7850
    //   #6020	-> 7852
    //   #6018	-> 7861
    //   #6021	-> 7863
    //   #6022	-> 7865
    //   #6023	-> 7868
    //   #6021	-> 7872
    //   #6024	-> 7875
    //   #6025	-> 7877
    //   #6024	-> 7880
    //   #6027	-> 7883
    //   #6028	-> 7885
    //   #6029	-> 7888
    //   #6027	-> 7892
    //   #6030	-> 7895
    //   #6031	-> 7897
    //   #6030	-> 7900
    //   #6034	-> 7903
    //   #6035	-> 7911
    //   #6036	-> 7919
    //   #6037	-> 7927
    //   #6038	-> 7932
    //   #6037	-> 7933
    //   #6039	-> 7939
    //   #6040	-> 7947
    //   #6042	-> 7958
    //   #6043	-> 7966
    //   #6045	-> 7974
    //   #6046	-> 7984
    //   #6047	-> 7986
    //   #6048	-> 7992
    //   #6049	-> 7995
    //   #6046	-> 8000
    //   #6060	-> 8009
    //   #6061	-> 8021
    //   #6062	-> 8034
    //   #6063	-> 8046
    //   #6064	-> 8059
    //   #6065	-> 8073
    //   #6066	-> 8085
    //   #6067	-> 8088
    //   #6066	-> 8095
    //   #6068	-> 8100
    //   #6070	-> 8113
    //   #6071	-> 8121
    //   #6072	-> 8126
    //   #6068	-> 8133
    //   #6073	-> 8140
    //   #6074	-> 8149
    //   #6075	-> 8157
    //   #6076	-> 8165
    //   #6078	-> 8170
    //   #6077	-> 8181
    //   #6079	-> 8183
    //   #6080	-> 8191
    //   #6079	-> 8192
    //   #6081	-> 8195
    //   #6083	-> 8203
    //   #6084	-> 8215
    //   #6087	-> 8223
    //   #6088	-> 8231
    //   #6089	-> 8239
    //   #6090	-> 8247
    //   #6091	-> 8259
    //   #6092	-> 8267
    //   #6094	-> 8278
    //   #6095	-> 8286
    //   #6097	-> 8294
    //   #6099	-> 8307
    //   #6100	-> 8318
    //   #6101	-> 8331
    //   #6102	-> 8345
    //   #6103	-> 8357
    //   #6105	-> 8370
    //   #6106	-> 8373
    //   #6105	-> 8380
    //   #6107	-> 8385
    //   #6109	-> 8398
    //   #6110	-> 8406
    //   #6111	-> 8411
    //   #6107	-> 8418
    //   #6112	-> 8425
    //   #6113	-> 8434
    //   #6114	-> 8442
    //   #6115	-> 8450
    //   #6117	-> 8455
    //   #6116	-> 8466
    //   #6118	-> 8468
    //   #6119	-> 8476
    //   #6118	-> 8477
    //   #6120	-> 8480
    //   #6122	-> 8488
    //   #6123	-> 8500
    //   #6126	-> 8508
    //   #6127	-> 8516
    //   #6128	-> 8524
    //   #6129	-> 8532
    //   #6130	-> 8544
    //   #6131	-> 8552
    //   #6133	-> 8563
    //   #6134	-> 8571
    //   #6136	-> 8579
    //   #6138	-> 8592
    //   #6139	-> 8603
    //   #6140	-> 8616
    //   #6141	-> 8630
    //   #6142	-> 8642
    //   #6143	-> 8644
    //   #6145	-> 8658
    //   #6146	-> 8661
    //   #6145	-> 8668
    //   #6147	-> 8673
    //   #6149	-> 8686
    //   #6150	-> 8694
    //   #6151	-> 8699
    //   #6147	-> 8706
    //   #6152	-> 8713
    //   #6153	-> 8722
    //   #6154	-> 8730
    //   #6155	-> 8738
    //   #6157	-> 8743
    //   #6156	-> 8754
    //   #6158	-> 8756
    //   #6159	-> 8764
    //   #6158	-> 8765
    //   #6160	-> 8768
    //   #6162	-> 8776
    //   #6163	-> 8788
    //   #6166	-> 8796
    //   #6167	-> 8804
    //   #6168	-> 8812
    //   #6169	-> 8820
    //   #6170	-> 8832
    //   #6171	-> 8840
    //   #6173	-> 8851
    //   #6174	-> 8859
    //   #6176	-> 8867
    //   #6177	-> 8877
    //   #6178	-> 8888
    //   #6179	-> 8891
    //   #6177	-> 8896
    //   #4661	-> 8899
    //   #6185	-> 8914
    //   #6186	-> 8923
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	8926	0	this	Lcom/js/oa/scheme/event/bean/EventEJBBean;
    //   0	8926	1	userId	Ljava/lang/Long;
    //   0	8926	2	time	Ljava/lang/Long;
    //   0	8926	3	domainId	Ljava/lang/Long;
    //   9	8917	4	result	Ljava/util/List;
    //   21	8905	5	currentCalendar	Ljava/util/GregorianCalendar;
    //   37	8889	6	currentDate	Ljava/util/Date;
    //   45	8881	7	year	I
    //   53	8873	8	month	I
    //   61	8865	9	day	I
    //   70	8856	10	weekday	I
    //   138	8788	11	query	Lnet/sf/hibernate/Query;
    //   147	8779	12	list	Ljava/util/List;
    //   150	8776	13	eventPO	Lcom/js/oa/scheme/event/po/EventPO;
    //   153	8773	14	event	Lcom/js/oa/scheme/event/vo/EventVO;
    //   181	8745	15	workDayQuery	Lnet/sf/hibernate/Query;
    //   190	8736	16	workDayList	Ljava/util/List;
    //   193	8721	17	i	I
    //   259	558	18	workDay	Ljava/lang/String;
    //   351	164	19	query1	Lnet/sf/hibernate/Query;
    //   360	155	20	list1	Ljava/util/List;
    //   394	40	21	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   609	164	19	query1	Lnet/sf/hibernate/Query;
    //   618	155	20	list1	Ljava/util/List;
    //   652	40	21	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   797	20	19	count	I
    //   807	10	20	startTime	J
    //   900	167	18	query1	Lnet/sf/hibernate/Query;
    //   909	158	19	list1	Ljava/util/List;
    //   943	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1146	164	18	query1	Lnet/sf/hibernate/Query;
    //   1155	155	19	list1	Ljava/util/List;
    //   1189	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1395	186	18	query1	Lnet/sf/hibernate/Query;
    //   1404	177	19	list1	Ljava/util/List;
    //   1438	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1693	164	18	query1	Lnet/sf/hibernate/Query;
    //   1702	155	19	list1	Ljava/util/List;
    //   1736	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   1969	164	18	query1	Lnet/sf/hibernate/Query;
    //   1978	155	19	list1	Ljava/util/List;
    //   2012	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2248	186	18	query1	Lnet/sf/hibernate/Query;
    //   2257	177	19	list1	Ljava/util/List;
    //   2291	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2542	167	18	query1	Lnet/sf/hibernate/Query;
    //   2551	158	19	list1	Ljava/util/List;
    //   2585	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   2863	164	18	query1	Lnet/sf/hibernate/Query;
    //   2872	155	19	list1	Ljava/util/List;
    //   2906	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3092	164	18	query1	Lnet/sf/hibernate/Query;
    //   3101	155	19	list1	Ljava/util/List;
    //   3135	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3321	164	18	query1	Lnet/sf/hibernate/Query;
    //   3330	155	19	list1	Ljava/util/List;
    //   3364	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3579	164	18	query1	Lnet/sf/hibernate/Query;
    //   3588	155	19	list1	Ljava/util/List;
    //   3622	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   3808	164	18	query1	Lnet/sf/hibernate/Query;
    //   3817	155	19	list1	Ljava/util/List;
    //   3851	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4066	164	18	query1	Lnet/sf/hibernate/Query;
    //   4075	155	19	list1	Ljava/util/List;
    //   4109	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4338	167	18	query1	Lnet/sf/hibernate/Query;
    //   4347	158	19	list1	Ljava/util/List;
    //   4381	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4671	164	18	query1	Lnet/sf/hibernate/Query;
    //   4680	155	19	list1	Ljava/util/List;
    //   4714	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   4900	164	18	query1	Lnet/sf/hibernate/Query;
    //   4909	155	19	list1	Ljava/util/List;
    //   4943	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5129	164	18	query1	Lnet/sf/hibernate/Query;
    //   5138	155	19	list1	Ljava/util/List;
    //   5172	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5387	164	18	query1	Lnet/sf/hibernate/Query;
    //   5396	155	19	list1	Ljava/util/List;
    //   5430	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5616	164	18	query1	Lnet/sf/hibernate/Query;
    //   5625	155	19	list1	Ljava/util/List;
    //   5659	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   5874	164	18	query1	Lnet/sf/hibernate/Query;
    //   5883	155	19	list1	Ljava/util/List;
    //   5917	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6149	189	18	query1	Lnet/sf/hibernate/Query;
    //   6158	180	19	list1	Ljava/util/List;
    //   6192	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6507	186	18	query1	Lnet/sf/hibernate/Query;
    //   6516	177	19	list1	Ljava/util/List;
    //   6550	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   6758	186	18	query1	Lnet/sf/hibernate/Query;
    //   6767	177	19	list1	Ljava/util/List;
    //   6801	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7009	186	18	query1	Lnet/sf/hibernate/Query;
    //   7018	177	19	list1	Ljava/util/List;
    //   7052	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7289	186	18	query1	Lnet/sf/hibernate/Query;
    //   7298	177	19	list1	Ljava/util/List;
    //   7332	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7540	186	18	query1	Lnet/sf/hibernate/Query;
    //   7549	177	19	list1	Ljava/util/List;
    //   7583	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   7820	186	18	query1	Lnet/sf/hibernate/Query;
    //   7829	177	19	list1	Ljava/util/List;
    //   7863	40	20	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   8034	865	18	monthDay	[Ljava/lang/String;
    //   8140	164	19	query1	Lnet/sf/hibernate/Query;
    //   8149	155	20	list1	Ljava/util/List;
    //   8183	40	21	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   8425	164	19	query1	Lnet/sf/hibernate/Query;
    //   8434	155	20	list1	Ljava/util/List;
    //   8468	40	21	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
    //   8713	186	19	query1	Lnet/sf/hibernate/Query;
    //   8722	177	20	list1	Ljava/util/List;
    //   8756	40	21	eventAttender	Lcom/js/oa/scheme/event/po/EventAttenderPO;
  }
  
  public List<String[]> getRelationEmp(String userId, String orgIdString) {
    String dataBaseType = SystemCommon.getDatabaseType();
    List<String[]> userList = (List)new ArrayList<String>();
    DataSourceUtil util = new DataSourceUtil();
    String userIdsql = "";
    try {
      String sql = "select eventRelationEmpId from oa_netemp where user_id=" + userId;
      String scope = "";
      List<String> relationempId = util.getQuery(sql, "");
      if (relationempId.size() > 0) {
        scope = relationempId.get(0);
        if (!scope.equals("")) {
          String[] group = (String[])null;
          String[] org = (String[])null;
          String emp = null;
          userIdsql = "SELECT e.emp_id,e.empName FROM org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON o.org_id=ou.ORG_ID WHERE (e.USERISACTIVE = 1 and e.USERISDELETED = 0 and e.emp_id<>" + 
            
            userId + ") and (1<>1 ";
          if (scope.contains("@")) {
            group = scope.substring(scope.indexOf("@") + 1, scope.lastIndexOf("@")).split("\\@\\@");
            sql = "SELECT groupuserstring FROM org_group where 1<>1 ";
            for (int i = 0; i < group.length; i++)
              sql = String.valueOf(sql) + " or group_id = " + group[i]; 
            List<String> groupUser = util.getQuery(sql, "");
            for (int j = 0; j < groupUser.size(); j++) {
              if (dataBaseType.equals("mysql")) {
                userIdsql = String.valueOf(userIdsql) + " or '" + (String)groupUser.get(j) + "' like CONCAT('%$',e.emp_id,'$%') ";
              } else {
                userIdsql = String.valueOf(userIdsql) + " or '" + (String)groupUser.get(j) + "' like '%$'||e.emp_id||'$%' ";
              } 
            } 
          } 
          if (scope.contains("*")) {
            org = scope.substring(scope.indexOf("*") + 1, scope.lastIndexOf("*")).split("\\*\\*");
            for (int i = 0; i < org.length; i++)
              userIdsql = String.valueOf(userIdsql) + " or o.ORGIDSTRING LIKE '%$" + org[i] + "$%' "; 
          } 
          if (scope.contains("$")) {
            emp = "$" + scope.substring(scope.indexOf("$") + 1, scope.lastIndexOf("$")) + "$";
            userIdsql = String.valueOf(userIdsql) + " or '" + emp + "' like CONCAT('%$',e.emp_id,'$%') ";
          } 
          userIdsql = String.valueOf(userIdsql) + ")";
        } else {
          userIdsql = "select emp_id,empName from org_employee where USERISACTIVE = 1 and USERISDELETED = 0 and emp_id<>" + userId;
        } 
      } else {
        userIdsql = "select emp_id,empName from org_employee where USERISACTIVE = 1 and USERISDELETED = 0 and emp_id<>" + userId;
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return util.getListQuery(userIdsql, "");
  }
}
