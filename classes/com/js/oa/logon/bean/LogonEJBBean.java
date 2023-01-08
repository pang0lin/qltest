package com.js.oa.logon.bean;

import com.js.oa.security.log.service.LogBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LogonEJBBean extends HibernateBase {
  public HashMap logon(String userAccount, String userPassword, String userIP, String serverIP, String sessionId, String domainAccount, String needPass) throws Exception {
    // Byte code:
    //   0: new java/util/HashMap
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #8
    //   9: ldc ''
    //   11: astore #9
    //   13: ldc ''
    //   15: astore #10
    //   17: ldc ''
    //   19: astore #11
    //   21: ldc ''
    //   23: astore #12
    //   25: aconst_null
    //   26: astore #14
    //   28: new com/js/oa/security/log/service/LogBD
    //   31: dup
    //   32: invokespecial <init> : ()V
    //   35: astore #15
    //   37: new java/util/Date
    //   40: dup
    //   41: invokespecial <init> : ()V
    //   44: astore #16
    //   46: ldc ''
    //   48: astore #17
    //   50: ldc ''
    //   52: astore #18
    //   54: ldc ''
    //   56: astore #19
    //   58: ldc ''
    //   60: astore #20
    //   62: aload_0
    //   63: invokevirtual begin : ()V
    //   66: ldc '-1'
    //   68: astore #21
    //   70: ldc '0'
    //   72: astore #22
    //   74: aload #6
    //   76: ifnull -> 335
    //   79: ldc ''
    //   81: aload #6
    //   83: invokevirtual equals : (Ljava/lang/Object;)Z
    //   86: ifne -> 335
    //   89: aload_0
    //   90: getfield session : Lnet/sf/hibernate/Session;
    //   93: new java/lang/StringBuilder
    //   96: dup
    //   97: ldc 'select a.id,a.userNum,a.domainType,a.noLog,a.inUse,a.domainEndDate from com.js.system.vo.organizationmanager.DomainVO a where a.domainAccount=''
    //   99: invokespecial <init> : (Ljava/lang/String;)V
    //   102: aload #6
    //   104: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: ldc '' and a.inUse=1'
    //   109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: invokevirtual toString : ()Ljava/lang/String;
    //   115: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   120: astore #23
    //   122: aload #23
    //   124: invokeinterface hasNext : ()Z
    //   129: ifeq -> 335
    //   132: aload #23
    //   134: invokeinterface next : ()Ljava/lang/Object;
    //   139: checkcast [Ljava/lang/Object;
    //   142: astore #24
    //   144: aload #24
    //   146: iconst_0
    //   147: aaload
    //   148: invokevirtual toString : ()Ljava/lang/String;
    //   151: astore #21
    //   153: aload #24
    //   155: iconst_1
    //   156: aaload
    //   157: ifnonnull -> 165
    //   160: ldc '0'
    //   162: goto -> 172
    //   165: aload #24
    //   167: iconst_1
    //   168: aaload
    //   169: invokevirtual toString : ()Ljava/lang/String;
    //   172: astore #22
    //   174: aload #8
    //   176: ldc 'domainType'
    //   178: aload #24
    //   180: iconst_2
    //   181: aaload
    //   182: ifnonnull -> 190
    //   185: ldc ''
    //   187: goto -> 197
    //   190: aload #24
    //   192: iconst_2
    //   193: aaload
    //   194: invokevirtual toString : ()Ljava/lang/String;
    //   197: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   200: pop
    //   201: aload #8
    //   203: ldc 'noLog'
    //   205: aload #24
    //   207: iconst_3
    //   208: aaload
    //   209: ifnonnull -> 217
    //   212: ldc ''
    //   214: goto -> 224
    //   217: aload #24
    //   219: iconst_3
    //   220: aaload
    //   221: invokevirtual toString : ()Ljava/lang/String;
    //   224: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   227: pop
    //   228: aload #24
    //   230: iconst_4
    //   231: aaload
    //   232: ifnonnull -> 240
    //   235: ldc '0'
    //   237: goto -> 247
    //   240: aload #24
    //   242: iconst_4
    //   243: aaload
    //   244: invokevirtual toString : ()Ljava/lang/String;
    //   247: astore #25
    //   249: aload #24
    //   251: iconst_5
    //   252: aaload
    //   253: checkcast java/util/Date
    //   256: astore #26
    //   258: ldc '0'
    //   260: aload #25
    //   262: invokevirtual equals : (Ljava/lang/Object;)Z
    //   265: ifeq -> 281
    //   268: aload #8
    //   270: ldc 'error'
    //   272: ldc 'domainNotInUse'
    //   274: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   277: pop
    //   278: goto -> 3868
    //   281: aload #26
    //   283: ifnull -> 335
    //   286: aload #26
    //   288: bipush #23
    //   290: invokevirtual setHours : (I)V
    //   293: aload #26
    //   295: bipush #59
    //   297: invokevirtual setMinutes : (I)V
    //   300: aload #26
    //   302: bipush #59
    //   304: invokevirtual setSeconds : (I)V
    //   307: new java/util/Date
    //   310: dup
    //   311: invokespecial <init> : ()V
    //   314: aload #26
    //   316: invokevirtual after : (Ljava/util/Date;)Z
    //   319: ifeq -> 335
    //   322: aload #8
    //   324: ldc 'error'
    //   326: ldc 'domainOverDate'
    //   328: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   331: pop
    //   332: goto -> 3868
    //   335: ldc '-1'
    //   337: aload #21
    //   339: invokevirtual equals : (Ljava/lang/Object;)Z
    //   342: ifeq -> 358
    //   345: aload #8
    //   347: ldc 'error'
    //   349: ldc 'domainError'
    //   351: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   354: pop
    //   355: goto -> 3868
    //   358: ldc '0'
    //   360: aload #22
    //   362: invokevirtual equals : (Ljava/lang/Object;)Z
    //   365: ifeq -> 381
    //   368: aload #8
    //   370: ldc 'error'
    //   372: ldc 'userNumError'
    //   374: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   377: pop
    //   378: goto -> 3868
    //   381: invokestatic getDatabaseType : ()Ljava/lang/String;
    //   384: astore #23
    //   386: aload #23
    //   388: ldc 'mysql'
    //   390: invokevirtual indexOf : (Ljava/lang/String;)I
    //   393: iflt -> 427
    //   396: aload_0
    //   397: getfield session : Lnet/sf/hibernate/Session;
    //   400: new java/lang/StringBuilder
    //   403: dup
    //   404: ldc 'select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0 and user.domainId='
    //   406: invokespecial <init> : (Ljava/lang/String;)V
    //   409: aload #21
    //   411: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: invokevirtual toString : ()Ljava/lang/String;
    //   417: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   422: astore #24
    //   424: goto -> 496
    //   427: aload #23
    //   429: ldc 'oracle'
    //   431: invokevirtual indexOf : (Ljava/lang/String;)I
    //   434: iflt -> 468
    //   437: aload_0
    //   438: getfield session : Lnet/sf/hibernate/Session;
    //   441: new java/lang/StringBuilder
    //   444: dup
    //   445: ldc 'select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin') and user.userIsFormalUser=1 and user.empId>0  and user.domainId='
    //   447: invokespecial <init> : (Ljava/lang/String;)V
    //   450: aload #21
    //   452: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   455: invokevirtual toString : ()Ljava/lang/String;
    //   458: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   463: astore #24
    //   465: goto -> 496
    //   468: aload_0
    //   469: getfield session : Lnet/sf/hibernate/Session;
    //   472: new java/lang/StringBuilder
    //   475: dup
    //   476: ldc 'select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0  and user.domainId='
    //   478: invokespecial <init> : (Ljava/lang/String;)V
    //   481: aload #21
    //   483: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   486: invokevirtual toString : ()Ljava/lang/String;
    //   489: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   494: astore #24
    //   496: ldc '0'
    //   498: astore #25
    //   500: aload #24
    //   502: invokeinterface hasNext : ()Z
    //   507: ifeq -> 522
    //   510: aload #24
    //   512: invokeinterface next : ()Ljava/lang/Object;
    //   517: invokevirtual toString : ()Ljava/lang/String;
    //   520: astore #25
    //   522: ldc '0'
    //   524: invokestatic getLicType : ()Ljava/lang/String;
    //   527: invokevirtual equals : (Ljava/lang/Object;)Z
    //   530: ifeq -> 596
    //   533: aload #25
    //   535: invokestatic parseInt : (Ljava/lang/String;)I
    //   538: aload #22
    //   540: invokestatic parseInt : (Ljava/lang/String;)I
    //   543: if_icmple -> 694
    //   546: aload_1
    //   547: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   550: ldc 'admin'
    //   552: invokevirtual equals : (Ljava/lang/Object;)Z
    //   555: ifne -> 570
    //   558: aload_1
    //   559: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   562: ldc 'system'
    //   564: invokevirtual equals : (Ljava/lang/Object;)Z
    //   567: ifeq -> 583
    //   570: aload #8
    //   572: ldc 'userNumOver'
    //   574: ldc '1'
    //   576: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   579: pop
    //   580: goto -> 694
    //   583: aload #8
    //   585: ldc 'error'
    //   587: ldc 'userNumError'
    //   589: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   592: pop
    //   593: goto -> 3868
    //   596: aload_0
    //   597: getfield session : Lnet/sf/hibernate/Session;
    //   600: ldc 'select count(user.userId) from com.js.oa.online.po.SecurityOnlineuser user'
    //   602: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   607: astore #24
    //   609: aload #24
    //   611: invokeinterface hasNext : ()Z
    //   616: ifeq -> 631
    //   619: aload #24
    //   621: invokeinterface next : ()Ljava/lang/Object;
    //   626: invokevirtual toString : ()Ljava/lang/String;
    //   629: astore #25
    //   631: aload #25
    //   633: invokestatic parseInt : (Ljava/lang/String;)I
    //   636: aload #22
    //   638: invokestatic parseInt : (Ljava/lang/String;)I
    //   641: if_icmple -> 694
    //   644: aload_1
    //   645: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   648: ldc 'admin'
    //   650: invokevirtual equals : (Ljava/lang/Object;)Z
    //   653: ifne -> 668
    //   656: aload_1
    //   657: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   660: ldc 'system'
    //   662: invokevirtual equals : (Ljava/lang/Object;)Z
    //   665: ifeq -> 681
    //   668: aload #8
    //   670: ldc 'userNumOver'
    //   672: ldc '1'
    //   674: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   677: pop
    //   678: goto -> 694
    //   681: aload #8
    //   683: ldc 'error'
    //   685: ldc 'userNumError'
    //   687: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   690: pop
    //   691: goto -> 3868
    //   694: aload_1
    //   695: ifnull -> 993
    //   698: ldc 'admin'
    //   700: aload_1
    //   701: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   704: invokevirtual equals : (Ljava/lang/Object;)Z
    //   707: ifeq -> 993
    //   710: ldc '0'
    //   712: astore #12
    //   714: aload_0
    //   715: getfield session : Lnet/sf/hibernate/Session;
    //   718: new java/lang/StringBuilder
    //   721: dup
    //   722: ldc 'SELECT emp.userPassword,emp.keyValidate,emp.keySerial,emp.skin,emp.empId,emp.empName,emp.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.userAccounts='admin' and emp.domainId='
    //   724: invokespecial <init> : (Ljava/lang/String;)V
    //   727: aload #21
    //   729: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   732: invokevirtual toString : ()Ljava/lang/String;
    //   735: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   740: invokeinterface list : ()Ljava/util/List;
    //   745: astore #14
    //   747: aload #14
    //   749: iconst_0
    //   750: invokeinterface get : (I)Ljava/lang/Object;
    //   755: checkcast [Ljava/lang/Object;
    //   758: astore #13
    //   760: aload #13
    //   762: bipush #6
    //   764: aaload
    //   765: aload_1
    //   766: invokevirtual equals : (Ljava/lang/Object;)Z
    //   769: ifne -> 785
    //   772: aload #8
    //   774: ldc 'error'
    //   776: ldc 'ifCaps'
    //   778: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   781: pop
    //   782: goto -> 3868
    //   785: aload #13
    //   787: iconst_5
    //   788: aaload
    //   789: invokevirtual toString : ()Ljava/lang/String;
    //   792: astore #9
    //   794: new com/js/util/util/MD5
    //   797: dup
    //   798: invokespecial <init> : ()V
    //   801: aload #13
    //   803: iconst_0
    //   804: aaload
    //   805: invokevirtual toString : ()Ljava/lang/String;
    //   808: aload_2
    //   809: invokevirtual equals : (Ljava/lang/String;Ljava/lang/String;)Z
    //   812: ifne -> 909
    //   815: aload #8
    //   817: ldc 'error'
    //   819: ldc 'password'
    //   821: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   824: pop
    //   825: new java/util/Date
    //   828: dup
    //   829: invokespecial <init> : ()V
    //   832: astore #26
    //   834: ldc 'oa_index'
    //   836: astore #17
    //   838: ldc '首页'
    //   840: astore #18
    //   842: ldc '0'
    //   844: astore #19
    //   846: ldc '密码错误'
    //   848: astore #20
    //   850: new java/lang/StringBuilder
    //   853: dup
    //   854: new java/util/Date
    //   857: dup
    //   858: invokespecial <init> : ()V
    //   861: invokevirtual getTime : ()J
    //   864: invokestatic valueOf : (J)Ljava/lang/String;
    //   867: invokespecial <init> : (Ljava/lang/String;)V
    //   870: invokevirtual toString : ()Ljava/lang/String;
    //   873: iconst_4
    //   874: invokevirtual substring : (I)Ljava/lang/String;
    //   877: astore #27
    //   879: aload #15
    //   881: aload #27
    //   883: aload #9
    //   885: ldc ''
    //   887: aload #17
    //   889: aload #18
    //   891: aload #16
    //   893: aload #26
    //   895: aload #19
    //   897: aload #20
    //   899: aload_3
    //   900: ldc '0'
    //   902: invokevirtual log : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   905: pop
    //   906: goto -> 3868
    //   909: ldc '1'
    //   911: aload #13
    //   913: iconst_1
    //   914: aaload
    //   915: invokevirtual toString : ()Ljava/lang/String;
    //   918: invokevirtual equals : (Ljava/lang/Object;)Z
    //   921: ifeq -> 936
    //   924: aload #8
    //   926: ldc 'keySerial'
    //   928: aload #13
    //   930: iconst_2
    //   931: aaload
    //   932: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   935: pop
    //   936: aload #8
    //   938: ldc 'userAccount'
    //   940: ldc 'admin'
    //   942: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   945: pop
    //   946: aload #8
    //   948: ldc 'skin'
    //   950: aload #13
    //   952: iconst_3
    //   953: aaload
    //   954: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   957: pop
    //   958: aload #8
    //   960: ldc 'userId'
    //   962: aload #13
    //   964: iconst_4
    //   965: aaload
    //   966: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   969: pop
    //   970: aload #8
    //   972: ldc 'userName'
    //   974: aload #9
    //   976: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   979: pop
    //   980: aload #8
    //   982: ldc 'domainId'
    //   984: aload #21
    //   986: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   989: pop
    //   990: goto -> 3814
    //   993: aload_1
    //   994: ifnull -> 1312
    //   997: ldc 'audit-admin'
    //   999: aload_1
    //   1000: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   1003: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1006: ifeq -> 1312
    //   1009: iconst_1
    //   1010: invokestatic getAudit : ()I
    //   1013: if_icmpeq -> 1029
    //   1016: aload #8
    //   1018: ldc 'error'
    //   1020: ldc 'noaudit'
    //   1022: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1025: pop
    //   1026: goto -> 3868
    //   1029: ldc '-99'
    //   1031: astore #12
    //   1033: aload_0
    //   1034: getfield session : Lnet/sf/hibernate/Session;
    //   1037: new java/lang/StringBuilder
    //   1040: dup
    //   1041: ldc 'SELECT emp.userPassword,emp.keyValidate,emp.keySerial,emp.skin,emp.empId,emp.empName,emp.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.userAccounts='audit-admin' and emp.domainId='
    //   1043: invokespecial <init> : (Ljava/lang/String;)V
    //   1046: aload #21
    //   1048: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1051: invokevirtual toString : ()Ljava/lang/String;
    //   1054: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1059: invokeinterface list : ()Ljava/util/List;
    //   1064: astore #14
    //   1066: aload #14
    //   1068: iconst_0
    //   1069: invokeinterface get : (I)Ljava/lang/Object;
    //   1074: checkcast [Ljava/lang/Object;
    //   1077: astore #13
    //   1079: aload #13
    //   1081: bipush #6
    //   1083: aaload
    //   1084: aload_1
    //   1085: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1088: ifne -> 1104
    //   1091: aload #8
    //   1093: ldc 'error'
    //   1095: ldc 'ifCaps'
    //   1097: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1100: pop
    //   1101: goto -> 3868
    //   1104: aload #13
    //   1106: iconst_5
    //   1107: aaload
    //   1108: invokevirtual toString : ()Ljava/lang/String;
    //   1111: astore #9
    //   1113: new com/js/util/util/MD5
    //   1116: dup
    //   1117: invokespecial <init> : ()V
    //   1120: aload #13
    //   1122: iconst_0
    //   1123: aaload
    //   1124: invokevirtual toString : ()Ljava/lang/String;
    //   1127: aload_2
    //   1128: invokevirtual equals : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1131: ifne -> 1228
    //   1134: aload #8
    //   1136: ldc 'error'
    //   1138: ldc 'password'
    //   1140: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1143: pop
    //   1144: new java/util/Date
    //   1147: dup
    //   1148: invokespecial <init> : ()V
    //   1151: astore #26
    //   1153: ldc 'oa_index'
    //   1155: astore #17
    //   1157: ldc '首页'
    //   1159: astore #18
    //   1161: ldc '0'
    //   1163: astore #19
    //   1165: ldc '密码错误'
    //   1167: astore #20
    //   1169: new java/lang/StringBuilder
    //   1172: dup
    //   1173: new java/util/Date
    //   1176: dup
    //   1177: invokespecial <init> : ()V
    //   1180: invokevirtual getTime : ()J
    //   1183: invokestatic valueOf : (J)Ljava/lang/String;
    //   1186: invokespecial <init> : (Ljava/lang/String;)V
    //   1189: invokevirtual toString : ()Ljava/lang/String;
    //   1192: iconst_4
    //   1193: invokevirtual substring : (I)Ljava/lang/String;
    //   1196: astore #27
    //   1198: aload #15
    //   1200: aload #27
    //   1202: aload #9
    //   1204: ldc ''
    //   1206: aload #17
    //   1208: aload #18
    //   1210: aload #16
    //   1212: aload #26
    //   1214: aload #19
    //   1216: aload #20
    //   1218: aload_3
    //   1219: ldc '0'
    //   1221: invokevirtual log : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   1224: pop
    //   1225: goto -> 3868
    //   1228: ldc '1'
    //   1230: aload #13
    //   1232: iconst_1
    //   1233: aaload
    //   1234: invokevirtual toString : ()Ljava/lang/String;
    //   1237: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1240: ifeq -> 1255
    //   1243: aload #8
    //   1245: ldc 'keySerial'
    //   1247: aload #13
    //   1249: iconst_2
    //   1250: aaload
    //   1251: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1254: pop
    //   1255: aload #8
    //   1257: ldc 'userAccount'
    //   1259: ldc 'audit-admin'
    //   1261: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1264: pop
    //   1265: aload #8
    //   1267: ldc 'skin'
    //   1269: aload #13
    //   1271: iconst_3
    //   1272: aaload
    //   1273: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1276: pop
    //   1277: aload #8
    //   1279: ldc 'userId'
    //   1281: aload #13
    //   1283: iconst_4
    //   1284: aaload
    //   1285: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1288: pop
    //   1289: aload #8
    //   1291: ldc 'userName'
    //   1293: aload #9
    //   1295: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1298: pop
    //   1299: aload #8
    //   1301: ldc 'domainId'
    //   1303: aload #21
    //   1305: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1308: pop
    //   1309: goto -> 3814
    //   1312: aload_0
    //   1313: getfield session : Lnet/sf/hibernate/Session;
    //   1316: ldc 'SELECT employee.userIsActive,employee.userIsSuper,employee.userPassword,employee.empId,employee.empName,employee.browseRange,org.orgId,org.orgIdString,employee.keyValidate,employee.keySerial,employee.userSuperBegin,employee.userSuperEnd,employee.userSimpleName,org.orgSerial,org.orgSimpleName,employee.skin,employee.empDuty,employee.empEnglishName,employee.userAccounts,employee.grantRange,employee.sidelineOrg,employee.guid,org.guid FROM com.js.system.vo.usermanager.EmployeeVO employee join employee.organizations org WHERE employee.userAccounts=:userAccount and employee.userIsDeleted=0 and employee.domainId=:domainId'
    //   1318: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1323: ldc 'userAccount'
    //   1325: aload_1
    //   1326: invokeinterface setString : (Ljava/lang/String;Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1331: ldc 'domainId'
    //   1333: aload #21
    //   1335: invokeinterface setString : (Ljava/lang/String;Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1340: invokeinterface list : ()Ljava/util/List;
    //   1345: astore #14
    //   1347: aload #14
    //   1349: ifnull -> 3814
    //   1352: aload #14
    //   1354: invokeinterface size : ()I
    //   1359: ifle -> 3814
    //   1362: aload #14
    //   1364: iconst_0
    //   1365: invokeinterface get : (I)Ljava/lang/Object;
    //   1370: checkcast [Ljava/lang/Object;
    //   1373: astore #13
    //   1375: ldc '0'
    //   1377: aload #13
    //   1379: iconst_0
    //   1380: aaload
    //   1381: invokevirtual toString : ()Ljava/lang/String;
    //   1384: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1387: ifeq -> 1403
    //   1390: aload #8
    //   1392: ldc 'error'
    //   1394: ldc 'active'
    //   1396: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1399: pop
    //   1400: goto -> 3868
    //   1403: aload #13
    //   1405: bipush #18
    //   1407: aaload
    //   1408: aload_1
    //   1409: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1412: ifne -> 1428
    //   1415: aload #8
    //   1417: ldc 'error'
    //   1419: ldc 'ifCaps'
    //   1421: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1424: pop
    //   1425: goto -> 3868
    //   1428: aload #8
    //   1430: ldc 'guid'
    //   1432: aload #13
    //   1434: bipush #21
    //   1436: aaload
    //   1437: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1440: pop
    //   1441: aload #8
    //   1443: ldc 'orgGUID'
    //   1445: aload #13
    //   1447: bipush #22
    //   1449: aaload
    //   1450: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1453: pop
    //   1454: ldc '9'
    //   1456: aload #7
    //   1458: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1461: ifne -> 1906
    //   1464: invokestatic getInstance : ()Ljava/util/Calendar;
    //   1467: astore #26
    //   1469: ldc '1'
    //   1471: aload #13
    //   1473: iconst_1
    //   1474: aaload
    //   1475: invokevirtual toString : ()Ljava/lang/String;
    //   1478: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1481: ifeq -> 1546
    //   1484: aload #13
    //   1486: bipush #10
    //   1488: aaload
    //   1489: ifnull -> 1546
    //   1492: aload #13
    //   1494: bipush #10
    //   1496: aaload
    //   1497: checkcast java/util/Date
    //   1500: astore #27
    //   1502: aload #13
    //   1504: bipush #11
    //   1506: aaload
    //   1507: checkcast java/util/Date
    //   1510: astore #28
    //   1512: aload #26
    //   1514: invokevirtual getTimeInMillis : ()J
    //   1517: aload #28
    //   1519: invokevirtual getTime : ()J
    //   1522: lcmp
    //   1523: ifgt -> 1540
    //   1526: aload #26
    //   1528: invokevirtual getTimeInMillis : ()J
    //   1531: aload #27
    //   1533: invokevirtual getTime : ()J
    //   1536: lcmp
    //   1537: ifge -> 1546
    //   1540: aload #13
    //   1542: iconst_1
    //   1543: ldc '0'
    //   1545: aastore
    //   1546: ldc '0'
    //   1548: aload #13
    //   1550: iconst_1
    //   1551: aaload
    //   1552: invokevirtual toString : ()Ljava/lang/String;
    //   1555: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1558: ifeq -> 1906
    //   1561: new java/lang/StringBuilder
    //   1564: dup
    //   1565: aload #26
    //   1567: iconst_1
    //   1568: invokevirtual get : (I)I
    //   1571: invokestatic valueOf : (I)Ljava/lang/String;
    //   1574: invokespecial <init> : (Ljava/lang/String;)V
    //   1577: ldc_w '/'
    //   1580: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1583: aload #26
    //   1585: iconst_2
    //   1586: invokevirtual get : (I)I
    //   1589: iconst_1
    //   1590: iadd
    //   1591: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1594: ldc_w '/'
    //   1597: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1600: aload #26
    //   1602: iconst_5
    //   1603: invokevirtual get : (I)I
    //   1606: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1609: invokevirtual toString : ()Ljava/lang/String;
    //   1612: astore #27
    //   1614: aload_3
    //   1615: ldc_w '\.'
    //   1618: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1621: astore #28
    //   1623: new java/lang/StringBuffer
    //   1626: dup
    //   1627: bipush #16
    //   1629: invokespecial <init> : (I)V
    //   1632: astore #29
    //   1634: iconst_0
    //   1635: istore #30
    //   1637: goto -> 1691
    //   1640: iconst_3
    //   1641: aload #28
    //   1643: iload #30
    //   1645: aaload
    //   1646: invokevirtual length : ()I
    //   1649: isub
    //   1650: istore #31
    //   1652: goto -> 1666
    //   1655: aload #29
    //   1657: ldc '0'
    //   1659: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1662: pop
    //   1663: iinc #31, -1
    //   1666: iload #31
    //   1668: ifgt -> 1655
    //   1671: aload #29
    //   1673: aload #28
    //   1675: iload #30
    //   1677: aaload
    //   1678: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1681: ldc_w '.'
    //   1684: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1687: pop
    //   1688: iinc #30, 1
    //   1691: iload #30
    //   1693: iconst_4
    //   1694: if_icmplt -> 1640
    //   1697: aload #29
    //   1699: invokevirtual toString : ()Ljava/lang/String;
    //   1702: iconst_0
    //   1703: bipush #15
    //   1705: invokevirtual substring : (II)Ljava/lang/String;
    //   1708: astore_3
    //   1709: ldc ''
    //   1711: astore #32
    //   1713: aload #23
    //   1715: ldc 'mysql'
    //   1717: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1720: iflt -> 1794
    //   1723: new java/lang/StringBuilder
    //   1726: dup
    //   1727: ldc_w 'SELECT COUNT(*) FROM com.js.oa.security.ip.po.IPPO ip WHERE ip.domainId='
    //   1730: invokespecial <init> : (Ljava/lang/String;)V
    //   1733: aload #21
    //   1735: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1738: ldc_w ' and ip.ipIsOpen=1 AND (''
    //   1741: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1744: aload #27
    //   1746: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1749: ldc_w ''>=ip.ipOpenBeginTime AND ''
    //   1752: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1755: aload #27
    //   1757: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1760: ldc_w ''<=ip.ipOpenEndTime AND (ip.ipAddressBegin=''
    //   1763: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1766: aload_3
    //   1767: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1770: ldc_w '' OR ''
    //   1773: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1776: aload_3
    //   1777: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1780: ldc_w '' BETWEEN ip.ipAddressBegin AND ip.ipAddressEnd))'
    //   1783: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1786: invokevirtual toString : ()Ljava/lang/String;
    //   1789: astore #32
    //   1791: goto -> 1862
    //   1794: new java/lang/StringBuilder
    //   1797: dup
    //   1798: ldc_w 'SELECT COUNT(*) FROM com.js.oa.security.ip.po.IPPO ip WHERE ip.domainId='
    //   1801: invokespecial <init> : (Ljava/lang/String;)V
    //   1804: aload #21
    //   1806: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1809: ldc_w ' and ip.ipIsOpen=1 AND (JSDB.FN_STRTODATE(''
    //   1812: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1815: aload #27
    //   1817: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1820: ldc_w '','S')>=ip.ipOpenBeginTime AND JSDB.FN_STRTODATE(''
    //   1823: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1826: aload #27
    //   1828: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1831: ldc_w '','S')<=ip.ipOpenEndTime AND (ip.ipAddressBegin=''
    //   1834: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1837: aload_3
    //   1838: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1841: ldc_w '' OR ''
    //   1844: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1847: aload_3
    //   1848: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1851: ldc_w '' BETWEEN ip.ipAddressBegin AND ip.ipAddressEnd))'
    //   1854: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1857: invokevirtual toString : ()Ljava/lang/String;
    //   1860: astore #32
    //   1862: aload_0
    //   1863: getfield session : Lnet/sf/hibernate/Session;
    //   1866: aload #32
    //   1868: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   1873: invokeinterface next : ()Ljava/lang/Object;
    //   1878: checkcast java/lang/Integer
    //   1881: invokevirtual intValue : ()I
    //   1884: istore #33
    //   1886: iload #33
    //   1888: iconst_1
    //   1889: if_icmpge -> 1906
    //   1892: aload #8
    //   1894: ldc 'error'
    //   1896: ldc_w 'ip'
    //   1899: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1902: pop
    //   1903: goto -> 3868
    //   1906: aload #13
    //   1908: iconst_4
    //   1909: aaload
    //   1910: invokevirtual toString : ()Ljava/lang/String;
    //   1913: astore #9
    //   1915: ldc '1'
    //   1917: aload #7
    //   1919: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1922: ifeq -> 2040
    //   1925: new com/js/util/util/MD5
    //   1928: dup
    //   1929: invokespecial <init> : ()V
    //   1932: aload #13
    //   1934: iconst_2
    //   1935: aaload
    //   1936: invokevirtual toString : ()Ljava/lang/String;
    //   1939: aload_2
    //   1940: invokevirtual equals : (Ljava/lang/String;Ljava/lang/String;)Z
    //   1943: ifne -> 2040
    //   1946: aload #8
    //   1948: ldc 'error'
    //   1950: ldc 'password'
    //   1952: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1955: pop
    //   1956: new java/util/Date
    //   1959: dup
    //   1960: invokespecial <init> : ()V
    //   1963: astore #26
    //   1965: ldc 'oa_index'
    //   1967: astore #17
    //   1969: ldc '首页'
    //   1971: astore #18
    //   1973: ldc '0'
    //   1975: astore #19
    //   1977: ldc '密码错误'
    //   1979: astore #20
    //   1981: new java/lang/StringBuilder
    //   1984: dup
    //   1985: new java/util/Date
    //   1988: dup
    //   1989: invokespecial <init> : ()V
    //   1992: invokevirtual getTime : ()J
    //   1995: invokestatic valueOf : (J)Ljava/lang/String;
    //   1998: invokespecial <init> : (Ljava/lang/String;)V
    //   2001: invokevirtual toString : ()Ljava/lang/String;
    //   2004: iconst_4
    //   2005: invokevirtual substring : (I)Ljava/lang/String;
    //   2008: astore #27
    //   2010: aload #15
    //   2012: aload #27
    //   2014: aload #9
    //   2016: ldc ''
    //   2018: aload #17
    //   2020: aload #18
    //   2022: aload #16
    //   2024: aload #26
    //   2026: aload #19
    //   2028: aload #20
    //   2030: aload_3
    //   2031: ldc '0'
    //   2033: invokevirtual log : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   2036: pop
    //   2037: goto -> 3868
    //   2040: ldc '1'
    //   2042: aload #13
    //   2044: bipush #8
    //   2046: aaload
    //   2047: invokevirtual toString : ()Ljava/lang/String;
    //   2050: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2053: ifeq -> 2069
    //   2056: aload #8
    //   2058: ldc 'keySerial'
    //   2060: aload #13
    //   2062: bipush #9
    //   2064: aaload
    //   2065: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2068: pop
    //   2069: aload #8
    //   2071: ldc 'domainId'
    //   2073: aload #21
    //   2075: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2078: pop
    //   2079: aload #13
    //   2081: iconst_3
    //   2082: aaload
    //   2083: invokevirtual toString : ()Ljava/lang/String;
    //   2086: astore #12
    //   2088: aload #8
    //   2090: ldc 'userId'
    //   2092: aload #12
    //   2094: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2097: pop
    //   2098: aload #8
    //   2100: ldc 'userName'
    //   2102: aload #9
    //   2104: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2107: pop
    //   2108: aload #13
    //   2110: iconst_5
    //   2111: aaload
    //   2112: ifnull -> 2145
    //   2115: ldc '0'
    //   2117: aload #13
    //   2119: iconst_5
    //   2120: aaload
    //   2121: invokevirtual toString : ()Ljava/lang/String;
    //   2124: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2127: ifeq -> 2145
    //   2130: aload #8
    //   2132: ldc_w 'browseRange'
    //   2135: ldc_w '*0*'
    //   2138: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2141: pop
    //   2142: goto -> 2158
    //   2145: aload #8
    //   2147: ldc_w 'browseRange'
    //   2150: aload #13
    //   2152: iconst_5
    //   2153: aaload
    //   2154: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2157: pop
    //   2158: aload #8
    //   2160: ldc_w 'orgId'
    //   2163: aload #13
    //   2165: bipush #6
    //   2167: aaload
    //   2168: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2171: pop
    //   2172: aload #8
    //   2174: ldc_w 'userSimpleName'
    //   2177: aload #13
    //   2179: bipush #12
    //   2181: aaload
    //   2182: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2185: pop
    //   2186: aload #8
    //   2188: ldc_w 'orgSerial'
    //   2191: aload #13
    //   2193: bipush #13
    //   2195: aaload
    //   2196: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2199: pop
    //   2200: aload #8
    //   2202: ldc_w 'orgSimpleName'
    //   2205: aload #13
    //   2207: bipush #14
    //   2209: aaload
    //   2210: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2213: pop
    //   2214: aload #8
    //   2216: ldc 'skin'
    //   2218: aload #13
    //   2220: bipush #15
    //   2222: aaload
    //   2223: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2226: pop
    //   2227: aload #8
    //   2229: ldc_w 'empEnglishName'
    //   2232: aload #13
    //   2234: bipush #17
    //   2236: aaload
    //   2237: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2240: pop
    //   2241: aload #8
    //   2243: ldc_w 'grantRange'
    //   2246: aload #13
    //   2248: bipush #19
    //   2250: aaload
    //   2251: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2254: pop
    //   2255: aload #13
    //   2257: bipush #7
    //   2259: aaload
    //   2260: checkcast java/lang/String
    //   2263: astore #26
    //   2265: aload #8
    //   2267: ldc_w 'orgIdString'
    //   2270: aload #26
    //   2272: ldc_w '$'
    //   2275: ldc_w '_'
    //   2278: invokestatic splitOrgIdString : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2281: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2284: pop
    //   2285: ldc ''
    //   2287: astore #27
    //   2289: ldc ''
    //   2291: astore #27
    //   2293: aload #23
    //   2295: ldc 'mysql'
    //   2297: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2300: iflt -> 2332
    //   2303: new java/lang/StringBuilder
    //   2306: dup
    //   2307: ldc_w 'SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   2310: invokespecial <init> : (Ljava/lang/String;)V
    //   2313: aload #26
    //   2315: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2318: ldc_w '' LIKE concat('%$', org.orgId, '$%') and org.orgId<>-1 ORDER BY org.orgLevel'
    //   2321: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2324: invokevirtual toString : ()Ljava/lang/String;
    //   2327: astore #27
    //   2329: goto -> 2398
    //   2332: aload #23
    //   2334: ldc_w 'db2'
    //   2337: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2340: iflt -> 2372
    //   2343: new java/lang/StringBuilder
    //   2346: dup
    //   2347: ldc_w 'SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%'),''
    //   2350: invokespecial <init> : (Ljava/lang/String;)V
    //   2353: aload #26
    //   2355: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2358: ldc_w '')>0  and org.orgId<>-1 ORDER BY org.orgLevel'
    //   2361: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2364: invokevirtual toString : ()Ljava/lang/String;
    //   2367: astore #27
    //   2369: goto -> 2398
    //   2372: new java/lang/StringBuilder
    //   2375: dup
    //   2376: ldc_w 'SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   2379: invokespecial <init> : (Ljava/lang/String;)V
    //   2382: aload #26
    //   2384: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2387: ldc_w '' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%')  and org.orgId<>-1 ORDER BY org.orgLevel'
    //   2390: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2393: invokevirtual toString : ()Ljava/lang/String;
    //   2396: astore #27
    //   2398: ldc ''
    //   2400: astore #28
    //   2402: ldc ''
    //   2404: astore #29
    //   2406: ldc ''
    //   2408: astore #30
    //   2410: aconst_null
    //   2411: astore #32
    //   2413: aload_0
    //   2414: getfield session : Lnet/sf/hibernate/Session;
    //   2417: aload #27
    //   2419: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2424: invokeinterface list : ()Ljava/util/List;
    //   2429: astore #14
    //   2431: ldc ''
    //   2433: astore #26
    //   2435: iconst_0
    //   2436: istore #33
    //   2438: goto -> 2548
    //   2441: aload #14
    //   2443: iload #33
    //   2445: invokeinterface get : (I)Ljava/lang/Object;
    //   2450: checkcast [Ljava/lang/Object;
    //   2453: astore #31
    //   2455: new java/lang/StringBuilder
    //   2458: dup
    //   2459: aload #26
    //   2461: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2464: invokespecial <init> : (Ljava/lang/String;)V
    //   2467: aload #31
    //   2469: iconst_0
    //   2470: aaload
    //   2471: invokevirtual toString : ()Ljava/lang/String;
    //   2474: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2477: invokevirtual toString : ()Ljava/lang/String;
    //   2480: astore #26
    //   2482: new java/lang/StringBuilder
    //   2485: dup
    //   2486: aload #26
    //   2488: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2491: invokespecial <init> : (Ljava/lang/String;)V
    //   2494: ldc_w '.'
    //   2497: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2500: invokevirtual toString : ()Ljava/lang/String;
    //   2503: astore #26
    //   2505: iload #33
    //   2507: ifne -> 2522
    //   2510: aload #31
    //   2512: iconst_1
    //   2513: aaload
    //   2514: invokevirtual toString : ()Ljava/lang/String;
    //   2517: astore #29
    //   2519: goto -> 2545
    //   2522: iload #33
    //   2524: aload #14
    //   2526: invokeinterface size : ()I
    //   2531: iconst_1
    //   2532: isub
    //   2533: if_icmpne -> 2545
    //   2536: aload #31
    //   2538: iconst_1
    //   2539: aaload
    //   2540: invokevirtual toString : ()Ljava/lang/String;
    //   2543: astore #30
    //   2545: iinc #33, 1
    //   2548: iload #33
    //   2550: aload #14
    //   2552: invokeinterface size : ()I
    //   2557: if_icmplt -> 2441
    //   2560: aload #14
    //   2562: invokeinterface size : ()I
    //   2567: iconst_2
    //   2568: if_icmpge -> 2575
    //   2571: aload #29
    //   2573: astore #30
    //   2575: aload #8
    //   2577: ldc_w 'rootCorpId'
    //   2580: aload #28
    //   2582: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2585: pop
    //   2586: aload #8
    //   2588: ldc_w 'corpId'
    //   2591: aload #29
    //   2593: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2596: pop
    //   2597: aload #8
    //   2599: ldc_w 'departId'
    //   2602: aload #30
    //   2604: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2607: pop
    //   2608: aload #13
    //   2610: bipush #20
    //   2612: aaload
    //   2613: ifnull -> 3200
    //   2616: ldc ''
    //   2618: aload #13
    //   2620: bipush #20
    //   2622: aaload
    //   2623: invokevirtual toString : ()Ljava/lang/String;
    //   2626: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2629: ifne -> 3200
    //   2632: ldc_w 'null'
    //   2635: aload #13
    //   2637: bipush #20
    //   2639: aaload
    //   2640: invokevirtual toString : ()Ljava/lang/String;
    //   2643: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2646: ifne -> 3200
    //   2649: aload #13
    //   2651: bipush #20
    //   2653: aaload
    //   2654: invokevirtual toString : ()Ljava/lang/String;
    //   2657: astore #33
    //   2659: aload #33
    //   2661: iconst_1
    //   2662: aload #33
    //   2664: invokevirtual length : ()I
    //   2667: iconst_1
    //   2668: isub
    //   2669: invokevirtual substring : (II)Ljava/lang/String;
    //   2672: astore #33
    //   2674: aload #33
    //   2676: ldc_w '\*\*'
    //   2679: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   2682: astore #36
    //   2684: aload #36
    //   2686: iconst_0
    //   2687: aaload
    //   2688: astore #34
    //   2690: new java/util/ArrayList
    //   2693: dup
    //   2694: invokespecial <init> : ()V
    //   2697: astore #32
    //   2699: iconst_0
    //   2700: istore #40
    //   2702: goto -> 2993
    //   2705: ldc ''
    //   2707: astore #39
    //   2709: iconst_3
    //   2710: anewarray java/lang/Object
    //   2713: astore #41
    //   2715: aload #41
    //   2717: iconst_0
    //   2718: aload #36
    //   2720: iload #40
    //   2722: aaload
    //   2723: aastore
    //   2724: new java/lang/StringBuilder
    //   2727: dup
    //   2728: ldc_w 'SELECT org.orgIdString FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgId='
    //   2731: invokespecial <init> : (Ljava/lang/String;)V
    //   2734: aload #36
    //   2736: iload #40
    //   2738: aaload
    //   2739: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2742: invokevirtual toString : ()Ljava/lang/String;
    //   2745: astore #27
    //   2747: aload_0
    //   2748: getfield session : Lnet/sf/hibernate/Session;
    //   2751: aload #27
    //   2753: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2758: invokeinterface iterate : ()Ljava/util/Iterator;
    //   2763: astore #37
    //   2765: aload #37
    //   2767: invokeinterface hasNext : ()Z
    //   2772: ifeq -> 2990
    //   2775: aload #37
    //   2777: invokeinterface next : ()Ljava/lang/Object;
    //   2782: invokevirtual toString : ()Ljava/lang/String;
    //   2785: astore #38
    //   2787: aload #23
    //   2789: ldc 'mysql'
    //   2791: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2794: iflt -> 2826
    //   2797: new java/lang/StringBuilder
    //   2800: dup
    //   2801: ldc_w 'SELECT org.orgSimpleName,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   2804: invokespecial <init> : (Ljava/lang/String;)V
    //   2807: aload #38
    //   2809: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2812: ldc_w '' LIKE concat('%$', org.orgId, '$%') and org.orgId<>-1 ORDER BY org.orgLevel'
    //   2815: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2818: invokevirtual toString : ()Ljava/lang/String;
    //   2821: astore #27
    //   2823: goto -> 2852
    //   2826: new java/lang/StringBuilder
    //   2829: dup
    //   2830: ldc_w 'SELECT org.orgSimpleName,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   2833: invokespecial <init> : (Ljava/lang/String;)V
    //   2836: aload #38
    //   2838: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2841: ldc_w '' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%')  and org.orgId<>-1 ORDER BY org.orgLevel'
    //   2844: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2847: invokevirtual toString : ()Ljava/lang/String;
    //   2850: astore #27
    //   2852: aload_0
    //   2853: getfield session : Lnet/sf/hibernate/Session;
    //   2856: aload #27
    //   2858: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   2863: invokeinterface list : ()Ljava/util/List;
    //   2868: astore #42
    //   2870: aload #42
    //   2872: ifnull -> 2990
    //   2875: aload #42
    //   2877: invokeinterface size : ()I
    //   2882: ifle -> 2990
    //   2885: iconst_0
    //   2886: istore #44
    //   2888: goto -> 2962
    //   2891: aload #42
    //   2893: iload #44
    //   2895: invokeinterface get : (I)Ljava/lang/Object;
    //   2900: checkcast [Ljava/lang/Object;
    //   2903: astore #45
    //   2905: aload #45
    //   2907: iconst_1
    //   2908: aaload
    //   2909: invokevirtual toString : ()Ljava/lang/String;
    //   2912: astore #43
    //   2914: ldc ''
    //   2916: aload #39
    //   2918: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2921: ifeq -> 2931
    //   2924: aload #43
    //   2926: astore #39
    //   2928: goto -> 2959
    //   2931: new java/lang/StringBuilder
    //   2934: dup
    //   2935: aload #39
    //   2937: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2940: invokespecial <init> : (Ljava/lang/String;)V
    //   2943: ldc_w '.'
    //   2946: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2949: aload #43
    //   2951: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2954: invokevirtual toString : ()Ljava/lang/String;
    //   2957: astore #39
    //   2959: iinc #44, 1
    //   2962: iload #44
    //   2964: aload #42
    //   2966: invokeinterface size : ()I
    //   2971: if_icmplt -> 2891
    //   2974: aload #41
    //   2976: iconst_1
    //   2977: aload #39
    //   2979: aastore
    //   2980: aload #32
    //   2982: aload #41
    //   2984: invokeinterface add : (Ljava/lang/Object;)Z
    //   2989: pop
    //   2990: iinc #40, 1
    //   2993: iload #40
    //   2995: aload #36
    //   2997: arraylength
    //   2998: if_icmplt -> 2705
    //   3001: new java/lang/StringBuilder
    //   3004: dup
    //   3005: ldc_w 'SELECT org.orgIdString FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE org.orgId='
    //   3008: invokespecial <init> : (Ljava/lang/String;)V
    //   3011: aload #34
    //   3013: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3016: invokevirtual toString : ()Ljava/lang/String;
    //   3019: astore #27
    //   3021: aload_0
    //   3022: getfield session : Lnet/sf/hibernate/Session;
    //   3025: aload #27
    //   3027: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3032: invokeinterface iterate : ()Ljava/util/Iterator;
    //   3037: invokeinterface next : ()Ljava/lang/Object;
    //   3042: invokevirtual toString : ()Ljava/lang/String;
    //   3045: astore #40
    //   3047: aload #23
    //   3049: ldc 'mysql'
    //   3051: invokevirtual indexOf : (Ljava/lang/String;)I
    //   3054: iflt -> 3086
    //   3057: new java/lang/StringBuilder
    //   3060: dup
    //   3061: ldc_w 'SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   3064: invokespecial <init> : (Ljava/lang/String;)V
    //   3067: aload #40
    //   3069: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3072: ldc_w '' LIKE concat('%$', org.orgId, '$%') and org.orgParentOrgId=0'
    //   3075: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3078: invokevirtual toString : ()Ljava/lang/String;
    //   3081: astore #27
    //   3083: goto -> 3152
    //   3086: aload #23
    //   3088: ldc_w 'db2'
    //   3091: invokevirtual indexOf : (Ljava/lang/String;)I
    //   3094: iflt -> 3126
    //   3097: new java/lang/StringBuilder
    //   3100: dup
    //   3101: ldc_w 'SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%'),''
    //   3104: invokespecial <init> : (Ljava/lang/String;)V
    //   3107: aload #40
    //   3109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3112: ldc_w '')>0  and org.orgParentOrgId=0'
    //   3115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3118: invokevirtual toString : ()Ljava/lang/String;
    //   3121: astore #27
    //   3123: goto -> 3152
    //   3126: new java/lang/StringBuilder
    //   3129: dup
    //   3130: ldc_w 'SELECT org.orgId FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   3133: invokespecial <init> : (Ljava/lang/String;)V
    //   3136: aload #40
    //   3138: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3141: ldc_w '' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%')  and org.orgParentOrgId=0'
    //   3144: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3147: invokevirtual toString : ()Ljava/lang/String;
    //   3150: astore #27
    //   3152: aload_0
    //   3153: getfield session : Lnet/sf/hibernate/Session;
    //   3156: aload #27
    //   3158: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3163: invokeinterface iterate : ()Ljava/util/Iterator;
    //   3168: invokeinterface next : ()Ljava/lang/Object;
    //   3173: invokevirtual toString : ()Ljava/lang/String;
    //   3176: astore #35
    //   3178: aload #8
    //   3180: ldc_w 'sidelineDepartId'
    //   3183: aload #34
    //   3185: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3188: pop
    //   3189: aload #8
    //   3191: ldc_w 'sidelineCorpId'
    //   3194: aload #35
    //   3196: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3199: pop
    //   3200: aload #26
    //   3202: ldc_w '.'
    //   3205: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   3208: ifeq -> 3226
    //   3211: aload #26
    //   3213: iconst_0
    //   3214: aload #26
    //   3216: invokevirtual length : ()I
    //   3219: iconst_1
    //   3220: isub
    //   3221: invokevirtual substring : (II)Ljava/lang/String;
    //   3224: astore #26
    //   3226: aload #8
    //   3228: ldc_w 'orgName'
    //   3231: aload #26
    //   3233: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3236: pop
    //   3237: aload #26
    //   3239: astore #11
    //   3241: aload #32
    //   3243: ifnull -> 3491
    //   3246: aload #23
    //   3248: ldc 'mysql'
    //   3250: invokevirtual indexOf : (Ljava/lang/String;)I
    //   3253: iflt -> 3291
    //   3256: new java/lang/StringBuilder
    //   3259: dup
    //   3260: ldc_w 'SELECT org.orgSimpleName,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   3263: invokespecial <init> : (Ljava/lang/String;)V
    //   3266: aload #13
    //   3268: bipush #7
    //   3270: aaload
    //   3271: checkcast java/lang/String
    //   3274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3277: ldc_w '' LIKE concat('%$', org.orgId, '$%') and org.orgId<>-1 ORDER BY org.orgLevel'
    //   3280: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3283: invokevirtual toString : ()Ljava/lang/String;
    //   3286: astore #27
    //   3288: goto -> 3323
    //   3291: new java/lang/StringBuilder
    //   3294: dup
    //   3295: ldc_w 'SELECT org.orgSimpleName,org.orgName FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   3298: invokespecial <init> : (Ljava/lang/String;)V
    //   3301: aload #13
    //   3303: bipush #7
    //   3305: aaload
    //   3306: checkcast java/lang/String
    //   3309: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3312: ldc_w '' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%')  and org.orgId<>-1 ORDER BY org.orgLevel'
    //   3315: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3318: invokevirtual toString : ()Ljava/lang/String;
    //   3321: astore #27
    //   3323: ldc ''
    //   3325: astore #33
    //   3327: aload_0
    //   3328: getfield session : Lnet/sf/hibernate/Session;
    //   3331: aload #27
    //   3333: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   3338: invokeinterface list : ()Ljava/util/List;
    //   3343: astore #34
    //   3345: aload #34
    //   3347: ifnull -> 3480
    //   3350: aload #34
    //   3352: invokeinterface size : ()I
    //   3357: ifle -> 3480
    //   3360: iconst_0
    //   3361: istore #36
    //   3363: goto -> 3437
    //   3366: aload #34
    //   3368: iload #36
    //   3370: invokeinterface get : (I)Ljava/lang/Object;
    //   3375: checkcast [Ljava/lang/Object;
    //   3378: astore #37
    //   3380: aload #37
    //   3382: iconst_1
    //   3383: aaload
    //   3384: invokevirtual toString : ()Ljava/lang/String;
    //   3387: astore #35
    //   3389: ldc ''
    //   3391: aload #33
    //   3393: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3396: ifeq -> 3406
    //   3399: aload #35
    //   3401: astore #33
    //   3403: goto -> 3434
    //   3406: new java/lang/StringBuilder
    //   3409: dup
    //   3410: aload #33
    //   3412: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3415: invokespecial <init> : (Ljava/lang/String;)V
    //   3418: ldc_w '.'
    //   3421: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3424: aload #35
    //   3426: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3429: invokevirtual toString : ()Ljava/lang/String;
    //   3432: astore #33
    //   3434: iinc #36, 1
    //   3437: iload #36
    //   3439: aload #34
    //   3441: invokeinterface size : ()I
    //   3446: if_icmplt -> 3366
    //   3449: iconst_3
    //   3450: anewarray java/lang/Object
    //   3453: astore #36
    //   3455: aload #36
    //   3457: iconst_0
    //   3458: aload #13
    //   3460: bipush #6
    //   3462: aaload
    //   3463: aastore
    //   3464: aload #36
    //   3466: iconst_1
    //   3467: aload #33
    //   3469: aastore
    //   3470: aload #32
    //   3472: iconst_0
    //   3473: aload #36
    //   3475: invokeinterface add : (ILjava/lang/Object;)V
    //   3480: aload #8
    //   3482: ldc_w 'sidelineList'
    //   3485: aload #32
    //   3487: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3490: pop
    //   3491: aload #8
    //   3493: ldc_w 'sysManager'
    //   3496: ldc '0'
    //   3498: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3501: pop
    //   3502: aload_0
    //   3503: getfield session : Lnet/sf/hibernate/Session;
    //   3506: new java/lang/StringBuilder
    //   3509: dup
    //   3510: ldc_w 'select r.rightId FROM com.js.system.vo.rolemanager.RightScopeVO rightScope join rightScope.employee emp join rightScope.right r WHERE r.rightCode='00*01*01' AND emp.empId='
    //   3513: invokespecial <init> : (Ljava/lang/String;)V
    //   3516: aload #13
    //   3518: iconst_3
    //   3519: aaload
    //   3520: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3523: invokevirtual toString : ()Ljava/lang/String;
    //   3526: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   3531: astore #33
    //   3533: aload #33
    //   3535: invokeinterface hasNext : ()Z
    //   3540: ifeq -> 3557
    //   3543: aload #8
    //   3545: ldc_w 'sysManager'
    //   3548: ldc '1'
    //   3550: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3553: pop
    //   3554: goto -> 3689
    //   3557: iconst_0
    //   3558: istore #34
    //   3560: aload_0
    //   3561: getfield session : Lnet/sf/hibernate/Session;
    //   3564: new java/lang/StringBuilder
    //   3567: dup
    //   3568: ldc_w 'select r.rightId FROM com.js.system.vo.rolemanager.RightScopeVO rightScope join rightScope.employee emp join rightScope.right r WHERE r.rightCode='00*01*02' AND emp.empId='
    //   3571: invokespecial <init> : (Ljava/lang/String;)V
    //   3574: aload #13
    //   3576: iconst_3
    //   3577: aaload
    //   3578: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3581: invokevirtual toString : ()Ljava/lang/String;
    //   3584: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   3589: astore #33
    //   3591: aload #33
    //   3593: invokeinterface hasNext : ()Z
    //   3598: ifeq -> 3616
    //   3601: aload #8
    //   3603: ldc_w 'sysManager'
    //   3606: ldc_w '2'
    //   3609: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3612: pop
    //   3613: iconst_1
    //   3614: istore #34
    //   3616: aload_0
    //   3617: getfield session : Lnet/sf/hibernate/Session;
    //   3620: new java/lang/StringBuilder
    //   3623: dup
    //   3624: ldc_w 'select r.rightId FROM com.js.system.vo.rolemanager.RightScopeVO rightScope join rightScope.employee emp join rightScope.right r WHERE r.rightCode='00*01*03' AND emp.empId='
    //   3627: invokespecial <init> : (Ljava/lang/String;)V
    //   3630: aload #13
    //   3632: iconst_3
    //   3633: aaload
    //   3634: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   3637: invokevirtual toString : ()Ljava/lang/String;
    //   3640: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   3645: astore #33
    //   3647: aload #33
    //   3649: invokeinterface hasNext : ()Z
    //   3654: ifeq -> 3689
    //   3657: iload #34
    //   3659: ifeq -> 3677
    //   3662: aload #8
    //   3664: ldc_w 'sysManager'
    //   3667: ldc_w '23'
    //   3670: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3673: pop
    //   3674: goto -> 3689
    //   3677: aload #8
    //   3679: ldc_w 'sysManager'
    //   3682: ldc_w '3'
    //   3685: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3688: pop
    //   3689: aload #8
    //   3691: ldc_w 'dutyName'
    //   3694: aload #13
    //   3696: bipush #16
    //   3698: aaload
    //   3699: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3702: pop
    //   3703: aload #13
    //   3705: bipush #16
    //   3707: aaload
    //   3708: ifnull -> 3724
    //   3711: aload #13
    //   3713: bipush #16
    //   3715: aaload
    //   3716: invokevirtual toString : ()Ljava/lang/String;
    //   3719: astore #10
    //   3721: goto -> 3728
    //   3724: ldc ''
    //   3726: astore #10
    //   3728: aload #8
    //   3730: ldc_w 'dutyLevel'
    //   3733: ldc '0'
    //   3735: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3738: pop
    //   3739: aload #13
    //   3741: bipush #16
    //   3743: aaload
    //   3744: ifnull -> 3814
    //   3747: aload_0
    //   3748: getfield session : Lnet/sf/hibernate/Session;
    //   3751: new java/lang/StringBuilder
    //   3754: dup
    //   3755: ldc_w 'select d.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO d where d.dutyName=''
    //   3758: invokespecial <init> : (Ljava/lang/String;)V
    //   3761: aload #13
    //   3763: bipush #16
    //   3765: aaload
    //   3766: invokevirtual toString : ()Ljava/lang/String;
    //   3769: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3772: ldc_w '''
    //   3775: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3778: invokevirtual toString : ()Ljava/lang/String;
    //   3781: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   3786: astore #33
    //   3788: aload #33
    //   3790: invokeinterface hasNext : ()Z
    //   3795: ifeq -> 3814
    //   3798: aload #8
    //   3800: ldc_w 'dutyLevel'
    //   3803: aload #33
    //   3805: invokeinterface next : ()Ljava/lang/Object;
    //   3810: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3813: pop
    //   3814: ldc ''
    //   3816: aload #12
    //   3818: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3821: ifne -> 3868
    //   3824: aload_0
    //   3825: aload #5
    //   3827: aload #12
    //   3829: aload #9
    //   3831: aload_1
    //   3832: aload_3
    //   3833: aload #4
    //   3835: aload #21
    //   3837: aload #10
    //   3839: aload #11
    //   3841: invokespecial log : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3844: goto -> 3868
    //   3847: astore #21
    //   3849: aload_0
    //   3850: getfield session : Lnet/sf/hibernate/Session;
    //   3853: invokeinterface close : ()Ljava/sql/Connection;
    //   3858: pop
    //   3859: aload #21
    //   3861: invokevirtual printStackTrace : ()V
    //   3864: aload #21
    //   3866: athrow
    //   3867: pop
    //   3868: aload_0
    //   3869: getfield session : Lnet/sf/hibernate/Session;
    //   3872: invokeinterface close : ()Ljava/sql/Connection;
    //   3877: pop
    //   3878: aload #8
    //   3880: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #43	-> 0
    //   #44	-> 9
    //   #45	-> 13
    //   #46	-> 17
    //   #47	-> 21
    //   #49	-> 25
    //   #51	-> 28
    //   #52	-> 37
    //   #53	-> 46
    //   #54	-> 50
    //   #55	-> 54
    //   #56	-> 58
    //   #58	-> 62
    //   #60	-> 66
    //   #61	-> 70
    //   #62	-> 74
    //   #63	-> 89
    //   #64	-> 122
    //   #65	-> 132
    //   #66	-> 144
    //   #67	-> 153
    //   #68	-> 174
    //   #69	-> 201
    //   #70	-> 228
    //   #72	-> 249
    //   #74	-> 258
    //   #75	-> 268
    //   #76	-> 278
    //   #78	-> 281
    //   #79	-> 286
    //   #80	-> 293
    //   #81	-> 300
    //   #82	-> 307
    //   #83	-> 322
    //   #84	-> 332
    //   #90	-> 335
    //   #91	-> 345
    //   #92	-> 355
    //   #95	-> 358
    //   #96	-> 368
    //   #97	-> 378
    //   #100	-> 381
    //   #103	-> 386
    //   #104	-> 396
    //   #105	-> 427
    //   #106	-> 437
    //   #108	-> 468
    //   #111	-> 496
    //   #112	-> 500
    //   #113	-> 510
    //   #116	-> 522
    //   #118	-> 533
    //   #119	-> 546
    //   #120	-> 570
    //   #122	-> 583
    //   #123	-> 593
    //   #127	-> 596
    //   #128	-> 609
    //   #129	-> 619
    //   #131	-> 631
    //   #132	-> 644
    //   #133	-> 668
    //   #135	-> 681
    //   #136	-> 691
    //   #141	-> 694
    //   #142	-> 710
    //   #143	-> 714
    //   #144	-> 747
    //   #146	-> 760
    //   #147	-> 772
    //   #148	-> 782
    //   #150	-> 785
    //   #151	-> 794
    //   #152	-> 815
    //   #154	-> 825
    //   #155	-> 834
    //   #156	-> 838
    //   #157	-> 842
    //   #158	-> 846
    //   #159	-> 850
    //   #160	-> 879
    //   #161	-> 906
    //   #163	-> 909
    //   #164	-> 924
    //   #166	-> 936
    //   #167	-> 946
    //   #168	-> 958
    //   #169	-> 970
    //   #170	-> 980
    //   #171	-> 993
    //   #173	-> 1009
    //   #174	-> 1016
    //   #175	-> 1026
    //   #177	-> 1029
    //   #178	-> 1033
    //   #179	-> 1066
    //   #181	-> 1079
    //   #182	-> 1091
    //   #183	-> 1101
    //   #185	-> 1104
    //   #186	-> 1113
    //   #187	-> 1134
    //   #189	-> 1144
    //   #190	-> 1153
    //   #191	-> 1157
    //   #192	-> 1161
    //   #193	-> 1165
    //   #194	-> 1169
    //   #195	-> 1198
    //   #196	-> 1225
    //   #198	-> 1228
    //   #199	-> 1243
    //   #201	-> 1255
    //   #202	-> 1265
    //   #203	-> 1277
    //   #204	-> 1289
    //   #205	-> 1299
    //   #208	-> 1312
    //   #214	-> 1323
    //   #215	-> 1331
    //   #216	-> 1340
    //   #208	-> 1345
    //   #218	-> 1347
    //   #219	-> 1362
    //   #222	-> 1375
    //   #223	-> 1390
    //   #224	-> 1400
    //   #226	-> 1403
    //   #227	-> 1415
    //   #228	-> 1425
    //   #231	-> 1428
    //   #232	-> 1441
    //   #235	-> 1454
    //   #237	-> 1464
    //   #236	-> 1467
    //   #239	-> 1469
    //   #240	-> 1484
    //   #241	-> 1492
    //   #242	-> 1502
    //   #243	-> 1512
    //   #244	-> 1526
    //   #245	-> 1540
    //   #249	-> 1546
    //   #250	-> 1561
    //   #251	-> 1583
    //   #252	-> 1600
    //   #250	-> 1609
    //   #255	-> 1614
    //   #256	-> 1623
    //   #258	-> 1634
    //   #259	-> 1640
    //   #260	-> 1652
    //   #261	-> 1655
    //   #262	-> 1663
    //   #260	-> 1666
    //   #264	-> 1671
    //   #258	-> 1688
    //   #266	-> 1697
    //   #267	-> 1709
    //   #269	-> 1713
    //   #270	-> 1723
    //   #271	-> 1733
    //   #272	-> 1738
    //   #273	-> 1744
    //   #274	-> 1749
    //   #275	-> 1755
    //   #276	-> 1760
    //   #277	-> 1766
    //   #278	-> 1780
    //   #270	-> 1786
    //   #281	-> 1794
    //   #282	-> 1804
    //   #283	-> 1809
    //   #284	-> 1815
    //   #285	-> 1820
    //   #286	-> 1826
    //   #287	-> 1831
    //   #288	-> 1837
    //   #289	-> 1851
    //   #281	-> 1857
    //   #292	-> 1862
    //   #293	-> 1873
    //   #292	-> 1884
    //   #294	-> 1886
    //   #295	-> 1892
    //   #296	-> 1903
    //   #300	-> 1906
    //   #302	-> 1915
    //   #303	-> 1925
    //   #304	-> 1946
    //   #306	-> 1956
    //   #307	-> 1965
    //   #308	-> 1969
    //   #309	-> 1973
    //   #310	-> 1977
    //   #311	-> 1981
    //   #312	-> 2010
    //   #313	-> 2037
    //   #316	-> 2040
    //   #319	-> 2056
    //   #324	-> 2069
    //   #325	-> 2079
    //   #326	-> 2088
    //   #327	-> 2098
    //   #328	-> 2108
    //   #329	-> 2130
    //   #331	-> 2145
    //   #334	-> 2158
    //   #335	-> 2172
    //   #336	-> 2186
    //   #337	-> 2200
    //   #338	-> 2214
    //   #340	-> 2227
    //   #341	-> 2241
    //   #343	-> 2255
    //   #344	-> 2265
    //   #346	-> 2285
    //   #348	-> 2289
    //   #353	-> 2293
    //   #354	-> 2303
    //   #355	-> 2313
    //   #356	-> 2318
    //   #354	-> 2324
    //   #357	-> 2332
    //   #358	-> 2343
    //   #359	-> 2353
    //   #360	-> 2358
    //   #358	-> 2364
    //   #363	-> 2372
    //   #364	-> 2382
    //   #365	-> 2387
    //   #363	-> 2393
    //   #368	-> 2398
    //   #369	-> 2402
    //   #370	-> 2406
    //   #373	-> 2410
    //   #375	-> 2413
    //   #376	-> 2431
    //   #377	-> 2435
    //   #378	-> 2441
    //   #379	-> 2455
    //   #380	-> 2482
    //   #390	-> 2505
    //   #391	-> 2510
    //   #392	-> 2522
    //   #393	-> 2536
    //   #377	-> 2545
    //   #396	-> 2560
    //   #397	-> 2571
    //   #399	-> 2575
    //   #400	-> 2586
    //   #401	-> 2597
    //   #404	-> 2608
    //   #406	-> 2649
    //   #408	-> 2659
    //   #409	-> 2674
    //   #410	-> 2684
    //   #413	-> 2690
    //   #416	-> 2699
    //   #418	-> 2705
    //   #419	-> 2709
    //   #420	-> 2715
    //   #422	-> 2724
    //   #423	-> 2734
    //   #422	-> 2742
    //   #424	-> 2747
    //   #425	-> 2765
    //   #426	-> 2775
    //   #428	-> 2787
    //   #429	-> 2797
    //   #430	-> 2807
    //   #431	-> 2812
    //   #429	-> 2818
    //   #433	-> 2826
    //   #434	-> 2836
    //   #435	-> 2841
    //   #433	-> 2847
    //   #437	-> 2852
    //   #439	-> 2870
    //   #440	-> 2885
    //   #441	-> 2891
    //   #447	-> 2905
    //   #449	-> 2914
    //   #450	-> 2924
    //   #452	-> 2931
    //   #440	-> 2959
    //   #456	-> 2974
    //   #457	-> 2980
    //   #416	-> 2990
    //   #464	-> 3001
    //   #465	-> 3021
    //   #467	-> 3047
    //   #468	-> 3057
    //   #469	-> 3067
    //   #470	-> 3072
    //   #468	-> 3078
    //   #471	-> 3086
    //   #472	-> 3097
    //   #473	-> 3107
    //   #475	-> 3112
    //   #472	-> 3118
    //   #478	-> 3126
    //   #479	-> 3136
    //   #480	-> 3141
    //   #478	-> 3147
    //   #482	-> 3152
    //   #484	-> 3178
    //   #485	-> 3189
    //   #489	-> 3200
    //   #490	-> 3226
    //   #491	-> 3237
    //   #493	-> 3241
    //   #495	-> 3246
    //   #496	-> 3256
    //   #497	-> 3266
    //   #498	-> 3277
    //   #496	-> 3283
    //   #500	-> 3291
    //   #501	-> 3301
    //   #502	-> 3312
    //   #500	-> 3318
    //   #504	-> 3323
    //   #505	-> 3327
    //   #507	-> 3345
    //   #509	-> 3360
    //   #510	-> 3366
    //   #517	-> 3380
    //   #519	-> 3389
    //   #520	-> 3399
    //   #522	-> 3406
    //   #509	-> 3434
    //   #526	-> 3449
    //   #527	-> 3455
    //   #528	-> 3464
    //   #529	-> 3470
    //   #532	-> 3480
    //   #536	-> 3491
    //   #538	-> 3502
    //   #539	-> 3533
    //   #540	-> 3543
    //   #542	-> 3557
    //   #543	-> 3560
    //   #544	-> 3591
    //   #545	-> 3601
    //   #546	-> 3613
    //   #548	-> 3616
    //   #549	-> 3647
    //   #550	-> 3657
    //   #551	-> 3662
    //   #553	-> 3677
    //   #558	-> 3689
    //   #559	-> 3703
    //   #560	-> 3711
    //   #563	-> 3724
    //   #566	-> 3728
    //   #567	-> 3739
    //   #568	-> 3747
    //   #569	-> 3788
    //   #570	-> 3798
    //   #578	-> 3814
    //   #579	-> 3824
    //   #581	-> 3847
    //   #582	-> 3849
    //   #583	-> 3859
    //   #584	-> 3864
    //   #585	-> 3867
    //   #586	-> 3868
    //   #587	-> 3878
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	3881	0	this	Lcom/js/oa/logon/bean/LogonEJBBean;
    //   0	3881	1	userAccount	Ljava/lang/String;
    //   0	3881	2	userPassword	Ljava/lang/String;
    //   0	3881	3	userIP	Ljava/lang/String;
    //   0	3881	4	serverIP	Ljava/lang/String;
    //   0	3881	5	sessionId	Ljava/lang/String;
    //   0	3881	6	domainAccount	Ljava/lang/String;
    //   0	3881	7	needPass	Ljava/lang/String;
    //   9	3872	8	userInfo	Ljava/util/HashMap;
    //   13	3868	9	userName	Ljava/lang/String;
    //   17	3864	10	dutyName	Ljava/lang/String;
    //   21	3860	11	orgName	Ljava/lang/String;
    //   25	3856	12	userId	Ljava/lang/String;
    //   760	233	13	obj	[Ljava/lang/Object;
    //   1079	233	13	obj	[Ljava/lang/Object;
    //   1375	2439	13	obj	[Ljava/lang/Object;
    //   28	3853	14	list	Ljava/util/List;
    //   37	3844	15	logBD	Lcom/js/oa/security/log/service/LogBD;
    //   46	3835	16	startDate	Ljava/util/Date;
    //   50	3831	17	moduleCode	Ljava/lang/String;
    //   54	3827	18	moduleName	Ljava/lang/String;
    //   58	3823	19	oprType	Ljava/lang/String;
    //   62	3819	20	oprContent	Ljava/lang/String;
    //   70	3777	21	domainId	Ljava/lang/String;
    //   74	3773	22	userNum	Ljava/lang/String;
    //   122	213	23	iter	Ljava/util/Iterator;
    //   144	191	24	tmpObj	[Ljava/lang/Object;
    //   249	86	25	domainInUse	Ljava/lang/String;
    //   258	77	26	domainEndDate	Ljava/util/Date;
    //   386	3461	23	databaseType	Ljava/lang/String;
    //   424	3	24	tmpIter	Ljava/util/Iterator;
    //   465	3	24	tmpIter	Ljava/util/Iterator;
    //   496	3351	24	tmpIter	Ljava/util/Iterator;
    //   500	3347	25	tmpUserNum	Ljava/lang/String;
    //   834	75	26	endDate	Ljava/util/Date;
    //   879	30	27	tempid	Ljava/lang/String;
    //   1153	75	26	endDate	Ljava/util/Date;
    //   1198	30	27	tempid	Ljava/lang/String;
    //   1469	437	26	date	Ljava/util/Calendar;
    //   1502	44	27	superBegin	Ljava/util/Date;
    //   1512	34	28	superEnd	Ljava/util/Date;
    //   1614	292	27	today	Ljava/lang/String;
    //   1623	283	28	ipAddr	[Ljava/lang/String;
    //   1634	272	29	ip	Ljava/lang/StringBuffer;
    //   1637	269	30	i	I
    //   1652	39	31	len	I
    //   1713	193	32	queryString	Ljava/lang/String;
    //   1886	20	33	count	I
    //   1965	75	26	endDate	Ljava/util/Date;
    //   2010	30	27	tempid	Ljava/lang/String;
    //   2265	1549	26	orgIdString	Ljava/lang/String;
    //   2289	1525	27	tmpSql	Ljava/lang/String;
    //   2402	1412	28	rootCorpId	Ljava/lang/String;
    //   2406	1408	29	corpId	Ljava/lang/String;
    //   2410	1404	30	departId	Ljava/lang/String;
    //   2455	93	31	orgObj	[Ljava/lang/Object;
    //   2413	1401	32	sidelineList	Ljava/util/List;
    //   2438	122	33	i	I
    //   2659	541	33	sidelineOrg	Ljava/lang/String;
    //   2690	510	34	sidelineDepartId	Ljava/lang/String;
    //   3178	22	35	sidelineCorpId	Ljava/lang/String;
    //   2684	516	36	sidelineOrgArr	[Ljava/lang/String;
    //   2765	228	37	it	Ljava/util/Iterator;
    //   2787	203	38	tmpSidelineIdString	Ljava/lang/String;
    //   2709	284	39	singleSidelineSimpleName	Ljava/lang/String;
    //   2702	299	40	si	I
    //   2715	275	41	singleSidelineOrg	[Ljava/lang/Object;
    //   2870	120	42	simpleNameList	Ljava/util/List;
    //   2914	48	43	tmpSingleSidelineSimpleName	Ljava/lang/String;
    //   2888	86	44	sni	I
    //   2905	54	45	tmpSimpleNameObject	[Ljava/lang/Object;
    //   3047	153	40	sidelineOrgIdString	Ljava/lang/String;
    //   3327	164	33	orgSimpleName	Ljava/lang/String;
    //   3345	146	34	simpleNameList	Ljava/util/List;
    //   3389	48	35	tmpSingleSidelineSimpleName	Ljava/lang/String;
    //   3363	86	36	sni	I
    //   3380	54	37	tmpSimpleNameObject	[Ljava/lang/Object;
    //   3455	25	36	currentOrg	[Ljava/lang/Object;
    //   3533	281	33	iter	Ljava/util/Iterator;
    //   3560	129	34	flag	Z
    //   3849	18	21	e	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   66	3844	3847	java/lang/Exception
    //   66	3867	3867	finally
  }
  
  public HashMap logon2(String userAccount, String userPassword, String userIP, String serverIP, String sessionId, String domainAccount, String needPass) throws Exception {
    // Byte code:
    //   0: new java/util/HashMap
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #8
    //   9: ldc ''
    //   11: astore #9
    //   13: ldc ''
    //   15: astore #10
    //   17: ldc ''
    //   19: astore #11
    //   21: ldc ''
    //   23: astore #12
    //   25: aconst_null
    //   26: astore #14
    //   28: aload_0
    //   29: invokevirtual begin : ()V
    //   32: ldc '-1'
    //   34: astore #15
    //   36: ldc '0'
    //   38: astore #16
    //   40: aload #6
    //   42: ifnull -> 301
    //   45: ldc ''
    //   47: aload #6
    //   49: invokevirtual equals : (Ljava/lang/Object;)Z
    //   52: ifne -> 301
    //   55: aload_0
    //   56: getfield session : Lnet/sf/hibernate/Session;
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: ldc 'select a.id,a.userNum,a.domainType,a.noLog,a.inUse,a.domainEndDate from com.js.system.vo.organizationmanager.DomainVO a where a.domainAccount=''
    //   65: invokespecial <init> : (Ljava/lang/String;)V
    //   68: aload #6
    //   70: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: ldc '' and a.inUse=1'
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual toString : ()Ljava/lang/String;
    //   81: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   86: astore #17
    //   88: aload #17
    //   90: invokeinterface hasNext : ()Z
    //   95: ifeq -> 301
    //   98: aload #17
    //   100: invokeinterface next : ()Ljava/lang/Object;
    //   105: checkcast [Ljava/lang/Object;
    //   108: astore #18
    //   110: aload #18
    //   112: iconst_0
    //   113: aaload
    //   114: invokevirtual toString : ()Ljava/lang/String;
    //   117: astore #15
    //   119: aload #18
    //   121: iconst_1
    //   122: aaload
    //   123: ifnonnull -> 131
    //   126: ldc '0'
    //   128: goto -> 138
    //   131: aload #18
    //   133: iconst_1
    //   134: aaload
    //   135: invokevirtual toString : ()Ljava/lang/String;
    //   138: astore #16
    //   140: aload #8
    //   142: ldc 'domainType'
    //   144: aload #18
    //   146: iconst_2
    //   147: aaload
    //   148: ifnonnull -> 156
    //   151: ldc ''
    //   153: goto -> 163
    //   156: aload #18
    //   158: iconst_2
    //   159: aaload
    //   160: invokevirtual toString : ()Ljava/lang/String;
    //   163: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   166: pop
    //   167: aload #8
    //   169: ldc 'noLog'
    //   171: aload #18
    //   173: iconst_3
    //   174: aaload
    //   175: ifnonnull -> 183
    //   178: ldc ''
    //   180: goto -> 190
    //   183: aload #18
    //   185: iconst_3
    //   186: aaload
    //   187: invokevirtual toString : ()Ljava/lang/String;
    //   190: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   193: pop
    //   194: aload #18
    //   196: iconst_4
    //   197: aaload
    //   198: ifnonnull -> 206
    //   201: ldc '0'
    //   203: goto -> 213
    //   206: aload #18
    //   208: iconst_4
    //   209: aaload
    //   210: invokevirtual toString : ()Ljava/lang/String;
    //   213: astore #19
    //   215: aload #18
    //   217: iconst_5
    //   218: aaload
    //   219: checkcast java/util/Date
    //   222: astore #20
    //   224: ldc '0'
    //   226: aload #19
    //   228: invokevirtual equals : (Ljava/lang/Object;)Z
    //   231: ifeq -> 247
    //   234: aload #8
    //   236: ldc 'error'
    //   238: ldc 'domainNotInUse'
    //   240: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   243: pop
    //   244: goto -> 2445
    //   247: aload #20
    //   249: ifnull -> 301
    //   252: aload #20
    //   254: bipush #23
    //   256: invokevirtual setHours : (I)V
    //   259: aload #20
    //   261: bipush #59
    //   263: invokevirtual setMinutes : (I)V
    //   266: aload #20
    //   268: bipush #59
    //   270: invokevirtual setSeconds : (I)V
    //   273: new java/util/Date
    //   276: dup
    //   277: invokespecial <init> : ()V
    //   280: aload #20
    //   282: invokevirtual after : (Ljava/util/Date;)Z
    //   285: ifeq -> 301
    //   288: aload #8
    //   290: ldc 'error'
    //   292: ldc 'domainOverDate'
    //   294: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   297: pop
    //   298: goto -> 2445
    //   301: ldc '-1'
    //   303: aload #15
    //   305: invokevirtual equals : (Ljava/lang/Object;)Z
    //   308: ifeq -> 324
    //   311: aload #8
    //   313: ldc 'error'
    //   315: ldc 'domainError'
    //   317: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   320: pop
    //   321: goto -> 2445
    //   324: ldc '0'
    //   326: aload #16
    //   328: invokevirtual equals : (Ljava/lang/Object;)Z
    //   331: ifeq -> 347
    //   334: aload #8
    //   336: ldc 'error'
    //   338: ldc 'userNumError'
    //   340: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   343: pop
    //   344: goto -> 2445
    //   347: invokestatic getDatabaseType : ()Ljava/lang/String;
    //   350: astore #17
    //   352: aload #17
    //   354: ldc 'mysql'
    //   356: invokevirtual indexOf : (Ljava/lang/String;)I
    //   359: iflt -> 393
    //   362: aload_0
    //   363: getfield session : Lnet/sf/hibernate/Session;
    //   366: new java/lang/StringBuilder
    //   369: dup
    //   370: ldc 'select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0 and user.domainId='
    //   372: invokespecial <init> : (Ljava/lang/String;)V
    //   375: aload #15
    //   377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: invokevirtual toString : ()Ljava/lang/String;
    //   383: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   388: astore #18
    //   390: goto -> 463
    //   393: aload #17
    //   395: ldc 'oracle'
    //   397: invokevirtual indexOf : (Ljava/lang/String;)I
    //   400: iflt -> 435
    //   403: aload_0
    //   404: getfield session : Lnet/sf/hibernate/Session;
    //   407: new java/lang/StringBuilder
    //   410: dup
    //   411: ldc_w 'select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin') and user.userIsFormalUser=1 and user.empId>0 and user.domainId='
    //   414: invokespecial <init> : (Ljava/lang/String;)V
    //   417: aload #15
    //   419: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   422: invokevirtual toString : ()Ljava/lang/String;
    //   425: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   430: astore #18
    //   432: goto -> 463
    //   435: aload_0
    //   436: getfield session : Lnet/sf/hibernate/Session;
    //   439: new java/lang/StringBuilder
    //   442: dup
    //   443: ldc 'select count(user.empId) from com.js.system.vo.usermanager.EmployeeVO user where user.userIsDeleted=0 and (user.userAccounts is not null and user.userAccounts<>'admin' and user.userAccounts<>'') and user.userIsFormalUser=1 and user.empId>0 and user.domainId='
    //   445: invokespecial <init> : (Ljava/lang/String;)V
    //   448: aload #15
    //   450: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   453: invokevirtual toString : ()Ljava/lang/String;
    //   456: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   461: astore #18
    //   463: ldc '0'
    //   465: astore #19
    //   467: aload #18
    //   469: invokeinterface hasNext : ()Z
    //   474: ifeq -> 489
    //   477: aload #18
    //   479: invokeinterface next : ()Ljava/lang/Object;
    //   484: invokevirtual toString : ()Ljava/lang/String;
    //   487: astore #19
    //   489: ldc '0'
    //   491: invokestatic getLicType : ()Ljava/lang/String;
    //   494: invokevirtual equals : (Ljava/lang/Object;)Z
    //   497: ifeq -> 563
    //   500: aload #19
    //   502: invokestatic parseInt : (Ljava/lang/String;)I
    //   505: aload #16
    //   507: invokestatic parseInt : (Ljava/lang/String;)I
    //   510: if_icmple -> 661
    //   513: aload_1
    //   514: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   517: ldc 'admin'
    //   519: invokevirtual equals : (Ljava/lang/Object;)Z
    //   522: ifne -> 537
    //   525: aload_1
    //   526: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   529: ldc 'system'
    //   531: invokevirtual equals : (Ljava/lang/Object;)Z
    //   534: ifeq -> 550
    //   537: aload #8
    //   539: ldc 'userNumOver'
    //   541: ldc '1'
    //   543: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   546: pop
    //   547: goto -> 661
    //   550: aload #8
    //   552: ldc 'error'
    //   554: ldc 'userNumError'
    //   556: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   559: pop
    //   560: goto -> 2445
    //   563: aload_0
    //   564: getfield session : Lnet/sf/hibernate/Session;
    //   567: ldc 'select count(user.userId) from com.js.oa.online.po.SecurityOnlineuser user'
    //   569: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   574: astore #18
    //   576: aload #18
    //   578: invokeinterface hasNext : ()Z
    //   583: ifeq -> 598
    //   586: aload #18
    //   588: invokeinterface next : ()Ljava/lang/Object;
    //   593: invokevirtual toString : ()Ljava/lang/String;
    //   596: astore #19
    //   598: aload #19
    //   600: invokestatic parseInt : (Ljava/lang/String;)I
    //   603: aload #16
    //   605: invokestatic parseInt : (Ljava/lang/String;)I
    //   608: if_icmple -> 661
    //   611: aload_1
    //   612: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   615: ldc 'admin'
    //   617: invokevirtual equals : (Ljava/lang/Object;)Z
    //   620: ifne -> 635
    //   623: aload_1
    //   624: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   627: ldc 'system'
    //   629: invokevirtual equals : (Ljava/lang/Object;)Z
    //   632: ifeq -> 648
    //   635: aload #8
    //   637: ldc 'userNumOver'
    //   639: ldc '1'
    //   641: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   644: pop
    //   645: goto -> 661
    //   648: aload #8
    //   650: ldc 'error'
    //   652: ldc 'userNumError'
    //   654: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   657: pop
    //   658: goto -> 2445
    //   661: ldc 'admin'
    //   663: aload_1
    //   664: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   667: invokevirtual equals : (Ljava/lang/Object;)Z
    //   670: ifeq -> 868
    //   673: ldc '0'
    //   675: astore #12
    //   677: aload_0
    //   678: getfield session : Lnet/sf/hibernate/Session;
    //   681: new java/lang/StringBuilder
    //   684: dup
    //   685: ldc 'SELECT emp.userPassword,emp.keyValidate,emp.keySerial,emp.skin,emp.empId,emp.empName,emp.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.userAccounts='admin' and emp.domainId='
    //   687: invokespecial <init> : (Ljava/lang/String;)V
    //   690: aload #15
    //   692: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   695: invokevirtual toString : ()Ljava/lang/String;
    //   698: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   703: invokeinterface list : ()Ljava/util/List;
    //   708: astore #14
    //   710: aload #14
    //   712: iconst_0
    //   713: invokeinterface get : (I)Ljava/lang/Object;
    //   718: checkcast [Ljava/lang/Object;
    //   721: astore #13
    //   723: aload #13
    //   725: bipush #6
    //   727: aaload
    //   728: aload_1
    //   729: invokevirtual equals : (Ljava/lang/Object;)Z
    //   732: ifne -> 748
    //   735: aload #8
    //   737: ldc 'error'
    //   739: ldc 'ifCaps'
    //   741: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   744: pop
    //   745: goto -> 2445
    //   748: aload #13
    //   750: iconst_0
    //   751: aaload
    //   752: invokevirtual toString : ()Ljava/lang/String;
    //   755: aload_2
    //   756: invokevirtual equals : (Ljava/lang/Object;)Z
    //   759: ifne -> 775
    //   762: aload #8
    //   764: ldc 'error'
    //   766: ldc 'password'
    //   768: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   771: pop
    //   772: goto -> 2445
    //   775: ldc '1'
    //   777: aload #13
    //   779: iconst_1
    //   780: aaload
    //   781: invokevirtual toString : ()Ljava/lang/String;
    //   784: invokevirtual equals : (Ljava/lang/Object;)Z
    //   787: ifeq -> 802
    //   790: aload #8
    //   792: ldc 'keySerial'
    //   794: aload #13
    //   796: iconst_2
    //   797: aaload
    //   798: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   801: pop
    //   802: aload #8
    //   804: ldc 'userAccount'
    //   806: ldc 'admin'
    //   808: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   811: pop
    //   812: aload #8
    //   814: ldc 'skin'
    //   816: aload #13
    //   818: iconst_3
    //   819: aaload
    //   820: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   823: pop
    //   824: aload #8
    //   826: ldc 'userId'
    //   828: aload #13
    //   830: iconst_4
    //   831: aaload
    //   832: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   835: pop
    //   836: aload #13
    //   838: iconst_5
    //   839: aaload
    //   840: invokevirtual toString : ()Ljava/lang/String;
    //   843: astore #9
    //   845: aload #8
    //   847: ldc 'userName'
    //   849: aload #9
    //   851: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   854: pop
    //   855: aload #8
    //   857: ldc 'domainId'
    //   859: aload #15
    //   861: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   864: pop
    //   865: goto -> 2445
    //   868: aload_0
    //   869: getfield session : Lnet/sf/hibernate/Session;
    //   872: ldc_w 'SELECT employee.userIsActive,employee.userIsSuper,employee.userPassword,employee.empId,employee.empName,employee.browseRange,org.orgId,org.orgIdString,employee.keyValidate,employee.keySerial,employee.userSuperBegin,employee.userSuperEnd,employee.userSimpleName,org.orgSerial,org.orgSimpleName,employee.skin,employee.empDuty,employee.empEnglishName,employee.userAccounts FROM com.js.system.vo.usermanager.EmployeeVO employee join employee.organizations org WHERE employee.userAccounts=:userAccount and employee.userIsDeleted=0 and employee.domainId=:domainId'
    //   875: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   880: ldc 'userAccount'
    //   882: aload_1
    //   883: invokeinterface setString : (Ljava/lang/String;Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   888: ldc 'domainId'
    //   890: aload #15
    //   892: invokeinterface setString : (Ljava/lang/String;Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   897: invokeinterface list : ()Ljava/util/List;
    //   902: astore #14
    //   904: aload #14
    //   906: ifnull -> 2445
    //   909: aload #14
    //   911: invokeinterface size : ()I
    //   916: ifle -> 2445
    //   919: aload #14
    //   921: iconst_0
    //   922: invokeinterface get : (I)Ljava/lang/Object;
    //   927: checkcast [Ljava/lang/Object;
    //   930: astore #13
    //   932: ldc '0'
    //   934: aload #13
    //   936: iconst_0
    //   937: aaload
    //   938: invokevirtual toString : ()Ljava/lang/String;
    //   941: invokevirtual equals : (Ljava/lang/Object;)Z
    //   944: ifeq -> 960
    //   947: aload #8
    //   949: ldc 'error'
    //   951: ldc 'active'
    //   953: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   956: pop
    //   957: goto -> 2445
    //   960: aload #13
    //   962: bipush #18
    //   964: aaload
    //   965: aload_1
    //   966: invokevirtual equals : (Ljava/lang/Object;)Z
    //   969: ifne -> 985
    //   972: aload #8
    //   974: ldc 'error'
    //   976: ldc 'ifCaps'
    //   978: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   981: pop
    //   982: goto -> 2445
    //   985: ldc '9'
    //   987: aload #7
    //   989: invokevirtual equals : (Ljava/lang/Object;)Z
    //   992: ifne -> 1437
    //   995: invokestatic getInstance : ()Ljava/util/Calendar;
    //   998: astore #20
    //   1000: ldc '1'
    //   1002: aload #13
    //   1004: iconst_1
    //   1005: aaload
    //   1006: invokevirtual toString : ()Ljava/lang/String;
    //   1009: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1012: ifeq -> 1077
    //   1015: aload #13
    //   1017: bipush #10
    //   1019: aaload
    //   1020: ifnull -> 1077
    //   1023: aload #13
    //   1025: bipush #10
    //   1027: aaload
    //   1028: checkcast java/util/Date
    //   1031: astore #21
    //   1033: aload #13
    //   1035: bipush #11
    //   1037: aaload
    //   1038: checkcast java/util/Date
    //   1041: astore #22
    //   1043: aload #20
    //   1045: invokevirtual getTimeInMillis : ()J
    //   1048: aload #22
    //   1050: invokevirtual getTime : ()J
    //   1053: lcmp
    //   1054: ifgt -> 1071
    //   1057: aload #20
    //   1059: invokevirtual getTimeInMillis : ()J
    //   1062: aload #21
    //   1064: invokevirtual getTime : ()J
    //   1067: lcmp
    //   1068: ifge -> 1077
    //   1071: aload #13
    //   1073: iconst_1
    //   1074: ldc '0'
    //   1076: aastore
    //   1077: ldc '0'
    //   1079: aload #13
    //   1081: iconst_1
    //   1082: aaload
    //   1083: invokevirtual toString : ()Ljava/lang/String;
    //   1086: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1089: ifeq -> 1437
    //   1092: new java/lang/StringBuilder
    //   1095: dup
    //   1096: aload #20
    //   1098: iconst_1
    //   1099: invokevirtual get : (I)I
    //   1102: invokestatic valueOf : (I)Ljava/lang/String;
    //   1105: invokespecial <init> : (Ljava/lang/String;)V
    //   1108: ldc_w '/'
    //   1111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1114: aload #20
    //   1116: iconst_2
    //   1117: invokevirtual get : (I)I
    //   1120: iconst_1
    //   1121: iadd
    //   1122: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1125: ldc_w '/'
    //   1128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1131: aload #20
    //   1133: iconst_5
    //   1134: invokevirtual get : (I)I
    //   1137: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1140: invokevirtual toString : ()Ljava/lang/String;
    //   1143: astore #21
    //   1145: aload_3
    //   1146: ldc_w '\.'
    //   1149: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1152: astore #22
    //   1154: new java/lang/StringBuffer
    //   1157: dup
    //   1158: bipush #16
    //   1160: invokespecial <init> : (I)V
    //   1163: astore #23
    //   1165: iconst_0
    //   1166: istore #24
    //   1168: goto -> 1222
    //   1171: iconst_3
    //   1172: aload #22
    //   1174: iload #24
    //   1176: aaload
    //   1177: invokevirtual length : ()I
    //   1180: isub
    //   1181: istore #25
    //   1183: goto -> 1197
    //   1186: aload #23
    //   1188: ldc '0'
    //   1190: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1193: pop
    //   1194: iinc #25, -1
    //   1197: iload #25
    //   1199: ifgt -> 1186
    //   1202: aload #23
    //   1204: aload #22
    //   1206: iload #24
    //   1208: aaload
    //   1209: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1212: ldc_w '.'
    //   1215: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1218: pop
    //   1219: iinc #24, 1
    //   1222: iload #24
    //   1224: iconst_4
    //   1225: if_icmplt -> 1171
    //   1228: aload #23
    //   1230: invokevirtual toString : ()Ljava/lang/String;
    //   1233: iconst_0
    //   1234: bipush #15
    //   1236: invokevirtual substring : (II)Ljava/lang/String;
    //   1239: astore_3
    //   1240: ldc ''
    //   1242: astore #26
    //   1244: aload #17
    //   1246: ldc 'mysql'
    //   1248: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1251: iflt -> 1325
    //   1254: new java/lang/StringBuilder
    //   1257: dup
    //   1258: ldc_w 'SELECT COUNT(*) FROM com.js.oa.security.ip.po.IPPO ip WHERE ip.domainId='
    //   1261: invokespecial <init> : (Ljava/lang/String;)V
    //   1264: aload #15
    //   1266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1269: ldc_w ' and ip.ipIsOpen=1 AND (''
    //   1272: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1275: aload #21
    //   1277: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1280: ldc_w ''>=ip.ipOpenBeginTime AND ''
    //   1283: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1286: aload #21
    //   1288: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1291: ldc_w ''<=ip.ipOpenEndTime AND (ip.ipAddressBegin=''
    //   1294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1297: aload_3
    //   1298: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1301: ldc_w '' OR ''
    //   1304: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1307: aload_3
    //   1308: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1311: ldc_w '' BETWEEN ip.ipAddressBegin AND ip.ipAddressEnd))'
    //   1314: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1317: invokevirtual toString : ()Ljava/lang/String;
    //   1320: astore #26
    //   1322: goto -> 1393
    //   1325: new java/lang/StringBuilder
    //   1328: dup
    //   1329: ldc_w 'SELECT COUNT(*) FROM com.js.oa.security.ip.po.IPPO ip WHERE ip.domainId='
    //   1332: invokespecial <init> : (Ljava/lang/String;)V
    //   1335: aload #15
    //   1337: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1340: ldc_w ' and ip.ipIsOpen=1 AND (JSDB.FN_STRTODATE(''
    //   1343: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1346: aload #21
    //   1348: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1351: ldc_w '','S')>=ip.ipOpenBeginTime AND JSDB.FN_STRTODATE(''
    //   1354: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1357: aload #21
    //   1359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1362: ldc_w '','S')<=ip.ipOpenEndTime AND (ip.ipAddressBegin=''
    //   1365: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1368: aload_3
    //   1369: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1372: ldc_w '' OR ''
    //   1375: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1378: aload_3
    //   1379: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1382: ldc_w '' BETWEEN ip.ipAddressBegin AND ip.ipAddressEnd))'
    //   1385: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1388: invokevirtual toString : ()Ljava/lang/String;
    //   1391: astore #26
    //   1393: aload_0
    //   1394: getfield session : Lnet/sf/hibernate/Session;
    //   1397: aload #26
    //   1399: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   1404: invokeinterface next : ()Ljava/lang/Object;
    //   1409: checkcast java/lang/Integer
    //   1412: invokevirtual intValue : ()I
    //   1415: istore #27
    //   1417: iload #27
    //   1419: iconst_1
    //   1420: if_icmpge -> 1437
    //   1423: aload #8
    //   1425: ldc 'error'
    //   1427: ldc_w 'ip'
    //   1430: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1433: pop
    //   1434: goto -> 2445
    //   1437: ldc '1'
    //   1439: aload #7
    //   1441: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1444: ifeq -> 1474
    //   1447: aload #13
    //   1449: iconst_2
    //   1450: aaload
    //   1451: invokevirtual toString : ()Ljava/lang/String;
    //   1454: aload_2
    //   1455: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1458: ifne -> 1474
    //   1461: aload #8
    //   1463: ldc 'error'
    //   1465: ldc 'password'
    //   1467: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1470: pop
    //   1471: goto -> 2445
    //   1474: ldc '1'
    //   1476: aload #13
    //   1478: bipush #8
    //   1480: aaload
    //   1481: invokevirtual toString : ()Ljava/lang/String;
    //   1484: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1487: ifeq -> 1503
    //   1490: aload #8
    //   1492: ldc 'keySerial'
    //   1494: aload #13
    //   1496: bipush #9
    //   1498: aaload
    //   1499: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1502: pop
    //   1503: aload #8
    //   1505: ldc 'domainId'
    //   1507: aload #15
    //   1509: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1512: pop
    //   1513: aload #13
    //   1515: iconst_3
    //   1516: aaload
    //   1517: invokevirtual toString : ()Ljava/lang/String;
    //   1520: astore #12
    //   1522: aload #8
    //   1524: ldc 'userId'
    //   1526: aload #12
    //   1528: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1531: pop
    //   1532: aload #13
    //   1534: iconst_4
    //   1535: aaload
    //   1536: invokevirtual toString : ()Ljava/lang/String;
    //   1539: astore #9
    //   1541: aload #8
    //   1543: ldc 'userName'
    //   1545: aload #9
    //   1547: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1550: pop
    //   1551: aload #8
    //   1553: ldc_w 'browseRange'
    //   1556: aload #13
    //   1558: iconst_5
    //   1559: aaload
    //   1560: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1563: pop
    //   1564: aload #8
    //   1566: ldc_w 'orgId'
    //   1569: aload #13
    //   1571: bipush #6
    //   1573: aaload
    //   1574: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1577: pop
    //   1578: aload #8
    //   1580: ldc_w 'userSimpleName'
    //   1583: aload #13
    //   1585: bipush #12
    //   1587: aaload
    //   1588: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1591: pop
    //   1592: aload #8
    //   1594: ldc_w 'orgSerial'
    //   1597: aload #13
    //   1599: bipush #13
    //   1601: aaload
    //   1602: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1605: pop
    //   1606: aload #8
    //   1608: ldc_w 'orgSimpleName'
    //   1611: aload #13
    //   1613: bipush #14
    //   1615: aaload
    //   1616: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1619: pop
    //   1620: aload #8
    //   1622: ldc 'skin'
    //   1624: aload #13
    //   1626: bipush #15
    //   1628: aaload
    //   1629: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1632: pop
    //   1633: aload #8
    //   1635: ldc_w 'empEnglishName'
    //   1638: aload #13
    //   1640: bipush #17
    //   1642: aaload
    //   1643: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1646: pop
    //   1647: aload #13
    //   1649: bipush #7
    //   1651: aaload
    //   1652: checkcast java/lang/String
    //   1655: astore #20
    //   1657: aload #8
    //   1659: ldc_w 'orgIdString'
    //   1662: aload #20
    //   1664: ldc_w '$'
    //   1667: ldc_w '_'
    //   1670: invokestatic splitOrgIdString : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1673: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1676: pop
    //   1677: ldc ''
    //   1679: astore #21
    //   1681: aload #17
    //   1683: ldc 'mysql'
    //   1685: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1688: iflt -> 1720
    //   1691: new java/lang/StringBuilder
    //   1694: dup
    //   1695: ldc_w 'SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   1698: invokespecial <init> : (Ljava/lang/String;)V
    //   1701: aload #20
    //   1703: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1706: ldc_w '' LIKE concat('%$', org.orgId, '$%') and org.orgId<>-1 ORDER BY org.orgLevel'
    //   1709: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1712: invokevirtual toString : ()Ljava/lang/String;
    //   1715: astore #21
    //   1717: goto -> 1786
    //   1720: aload #17
    //   1722: ldc_w 'db2'
    //   1725: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1728: iflt -> 1760
    //   1731: new java/lang/StringBuilder
    //   1734: dup
    //   1735: ldc_w 'SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%'),''
    //   1738: invokespecial <init> : (Ljava/lang/String;)V
    //   1741: aload #20
    //   1743: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1746: ldc_w '')>0  and org.orgId<>-1 ORDER BY org.orgLevel'
    //   1749: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1752: invokevirtual toString : ()Ljava/lang/String;
    //   1755: astore #21
    //   1757: goto -> 1786
    //   1760: new java/lang/StringBuilder
    //   1763: dup
    //   1764: ldc_w 'SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE ''
    //   1767: invokespecial <init> : (Ljava/lang/String;)V
    //   1770: aload #20
    //   1772: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1775: ldc_w '' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)), '$%')  and org.orgId<>-1 ORDER BY org.orgLevel'
    //   1778: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1781: invokevirtual toString : ()Ljava/lang/String;
    //   1784: astore #21
    //   1786: ldc ''
    //   1788: astore #22
    //   1790: ldc ''
    //   1792: astore #23
    //   1794: ldc ''
    //   1796: astore #24
    //   1798: aload_0
    //   1799: getfield session : Lnet/sf/hibernate/Session;
    //   1802: aload #21
    //   1804: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1809: invokeinterface list : ()Ljava/util/List;
    //   1814: astore #14
    //   1816: ldc ''
    //   1818: astore #20
    //   1820: iconst_0
    //   1821: istore #26
    //   1823: goto -> 2012
    //   1826: aload #14
    //   1828: iload #26
    //   1830: invokeinterface get : (I)Ljava/lang/Object;
    //   1835: checkcast [Ljava/lang/Object;
    //   1838: astore #25
    //   1840: new java/lang/StringBuilder
    //   1843: dup
    //   1844: aload #20
    //   1846: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1849: invokespecial <init> : (Ljava/lang/String;)V
    //   1852: aload #25
    //   1854: iconst_0
    //   1855: aaload
    //   1856: invokevirtual toString : ()Ljava/lang/String;
    //   1859: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1862: invokevirtual toString : ()Ljava/lang/String;
    //   1865: astore #20
    //   1867: new java/lang/StringBuilder
    //   1870: dup
    //   1871: aload #20
    //   1873: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1876: invokespecial <init> : (Ljava/lang/String;)V
    //   1879: ldc_w '.'
    //   1882: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1885: invokevirtual toString : ()Ljava/lang/String;
    //   1888: astore #20
    //   1890: aload #25
    //   1892: iconst_2
    //   1893: aaload
    //   1894: ifnonnull -> 1905
    //   1897: aload #25
    //   1899: iconst_2
    //   1900: iconst_1
    //   1901: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1904: aastore
    //   1905: aload #22
    //   1907: ldc ''
    //   1909: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1912: ifeq -> 1939
    //   1915: ldc '0'
    //   1917: aload #25
    //   1919: iconst_2
    //   1920: aaload
    //   1921: invokevirtual toString : ()Ljava/lang/String;
    //   1924: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1927: ifeq -> 1939
    //   1930: aload #25
    //   1932: iconst_1
    //   1933: aaload
    //   1934: invokevirtual toString : ()Ljava/lang/String;
    //   1937: astore #22
    //   1939: aload #23
    //   1941: ldc ''
    //   1943: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1946: ifeq -> 1974
    //   1949: ldc_w '3'
    //   1952: aload #25
    //   1954: iconst_2
    //   1955: aaload
    //   1956: invokevirtual toString : ()Ljava/lang/String;
    //   1959: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1962: ifeq -> 1974
    //   1965: aload #25
    //   1967: iconst_1
    //   1968: aaload
    //   1969: invokevirtual toString : ()Ljava/lang/String;
    //   1972: astore #23
    //   1974: aload #24
    //   1976: ldc ''
    //   1978: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1981: ifeq -> 2009
    //   1984: ldc_w '2'
    //   1987: aload #25
    //   1989: iconst_2
    //   1990: aaload
    //   1991: invokevirtual toString : ()Ljava/lang/String;
    //   1994: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1997: ifeq -> 2009
    //   2000: aload #25
    //   2002: iconst_1
    //   2003: aaload
    //   2004: invokevirtual toString : ()Ljava/lang/String;
    //   2007: astore #24
    //   2009: iinc #26, 1
    //   2012: iload #26
    //   2014: aload #14
    //   2016: invokeinterface size : ()I
    //   2021: if_icmplt -> 1826
    //   2024: aload #8
    //   2026: ldc_w 'rootCorpId'
    //   2029: aload #22
    //   2031: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2034: pop
    //   2035: aload #8
    //   2037: ldc_w 'corpId'
    //   2040: aload #23
    //   2042: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2045: pop
    //   2046: aload #8
    //   2048: ldc_w 'departId'
    //   2051: aload #24
    //   2053: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2056: pop
    //   2057: aload #20
    //   2059: ldc_w '.'
    //   2062: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   2065: ifeq -> 2083
    //   2068: aload #20
    //   2070: iconst_0
    //   2071: aload #20
    //   2073: invokevirtual length : ()I
    //   2076: iconst_1
    //   2077: isub
    //   2078: invokevirtual substring : (II)Ljava/lang/String;
    //   2081: astore #20
    //   2083: aload #8
    //   2085: ldc_w 'orgName'
    //   2088: aload #20
    //   2090: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2093: pop
    //   2094: aload #20
    //   2096: astore #11
    //   2098: aload #8
    //   2100: ldc_w 'sysManager'
    //   2103: ldc '0'
    //   2105: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2108: pop
    //   2109: aload_0
    //   2110: getfield session : Lnet/sf/hibernate/Session;
    //   2113: new java/lang/StringBuilder
    //   2116: dup
    //   2117: ldc_w 'select r.rightId FROM com.js.system.vo.rolemanager.RightScopeVO rightScope join rightScope.employee emp join rightScope.right r WHERE r.rightCode='00*01*01' AND emp.empId='
    //   2120: invokespecial <init> : (Ljava/lang/String;)V
    //   2123: aload #13
    //   2125: iconst_3
    //   2126: aaload
    //   2127: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2130: invokevirtual toString : ()Ljava/lang/String;
    //   2133: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   2138: astore #26
    //   2140: aload #26
    //   2142: invokeinterface hasNext : ()Z
    //   2147: ifeq -> 2164
    //   2150: aload #8
    //   2152: ldc_w 'sysManager'
    //   2155: ldc '1'
    //   2157: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2160: pop
    //   2161: goto -> 2296
    //   2164: iconst_0
    //   2165: istore #27
    //   2167: aload_0
    //   2168: getfield session : Lnet/sf/hibernate/Session;
    //   2171: new java/lang/StringBuilder
    //   2174: dup
    //   2175: ldc_w 'select r.rightId FROM com.js.system.vo.rolemanager.RightScopeVO rightScope join rightScope.employee emp join rightScope.right r WHERE r.rightCode='00*01*02' AND emp.empId='
    //   2178: invokespecial <init> : (Ljava/lang/String;)V
    //   2181: aload #13
    //   2183: iconst_3
    //   2184: aaload
    //   2185: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2188: invokevirtual toString : ()Ljava/lang/String;
    //   2191: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   2196: astore #26
    //   2198: aload #26
    //   2200: invokeinterface hasNext : ()Z
    //   2205: ifeq -> 2223
    //   2208: aload #8
    //   2210: ldc_w 'sysManager'
    //   2213: ldc_w '2'
    //   2216: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2219: pop
    //   2220: iconst_1
    //   2221: istore #27
    //   2223: aload_0
    //   2224: getfield session : Lnet/sf/hibernate/Session;
    //   2227: new java/lang/StringBuilder
    //   2230: dup
    //   2231: ldc_w 'select r.rightId FROM com.js.system.vo.rolemanager.RightScopeVO rightScope join rightScope.employee emp join rightScope.right r WHERE r.rightCode='00*01*03' AND emp.empId='
    //   2234: invokespecial <init> : (Ljava/lang/String;)V
    //   2237: aload #13
    //   2239: iconst_3
    //   2240: aaload
    //   2241: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2244: invokevirtual toString : ()Ljava/lang/String;
    //   2247: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   2252: astore #26
    //   2254: aload #26
    //   2256: invokeinterface hasNext : ()Z
    //   2261: ifeq -> 2296
    //   2264: iload #27
    //   2266: ifeq -> 2284
    //   2269: aload #8
    //   2271: ldc_w 'sysManager'
    //   2274: ldc_w '23'
    //   2277: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2280: pop
    //   2281: goto -> 2296
    //   2284: aload #8
    //   2286: ldc_w 'sysManager'
    //   2289: ldc_w '3'
    //   2292: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2295: pop
    //   2296: aload #8
    //   2298: ldc_w 'dutyName'
    //   2301: aload #13
    //   2303: bipush #16
    //   2305: aaload
    //   2306: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2309: pop
    //   2310: aload #13
    //   2312: bipush #16
    //   2314: aaload
    //   2315: ifnull -> 2331
    //   2318: aload #13
    //   2320: bipush #16
    //   2322: aaload
    //   2323: invokevirtual toString : ()Ljava/lang/String;
    //   2326: astore #10
    //   2328: goto -> 2335
    //   2331: ldc ''
    //   2333: astore #10
    //   2335: aload #8
    //   2337: ldc_w 'dutyLevel'
    //   2340: ldc '0'
    //   2342: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2345: pop
    //   2346: aload #13
    //   2348: bipush #16
    //   2350: aaload
    //   2351: ifnull -> 2445
    //   2354: aload_0
    //   2355: getfield session : Lnet/sf/hibernate/Session;
    //   2358: new java/lang/StringBuilder
    //   2361: dup
    //   2362: ldc_w 'select d.dutyLevel from com.js.oa.hr.officemanager.po.DutyPO d where d.dutyName=''
    //   2365: invokespecial <init> : (Ljava/lang/String;)V
    //   2368: aload #13
    //   2370: bipush #16
    //   2372: aaload
    //   2373: invokevirtual toString : ()Ljava/lang/String;
    //   2376: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2379: ldc_w '''
    //   2382: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2385: invokevirtual toString : ()Ljava/lang/String;
    //   2388: invokeinterface iterate : (Ljava/lang/String;)Ljava/util/Iterator;
    //   2393: astore #26
    //   2395: aload #26
    //   2397: invokeinterface hasNext : ()Z
    //   2402: ifeq -> 2445
    //   2405: aload #8
    //   2407: ldc_w 'dutyLevel'
    //   2410: aload #26
    //   2412: invokeinterface next : ()Ljava/lang/Object;
    //   2417: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2420: pop
    //   2421: goto -> 2445
    //   2424: astore #15
    //   2426: aload_0
    //   2427: getfield session : Lnet/sf/hibernate/Session;
    //   2430: invokeinterface close : ()Ljava/sql/Connection;
    //   2435: pop
    //   2436: aload #15
    //   2438: invokevirtual printStackTrace : ()V
    //   2441: aload #15
    //   2443: athrow
    //   2444: pop
    //   2445: aload_0
    //   2446: getfield session : Lnet/sf/hibernate/Session;
    //   2449: invokeinterface close : ()Ljava/sql/Connection;
    //   2454: pop
    //   2455: aload #8
    //   2457: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #604	-> 0
    //   #605	-> 9
    //   #606	-> 13
    //   #607	-> 17
    //   #608	-> 21
    //   #610	-> 25
    //   #612	-> 28
    //   #614	-> 32
    //   #615	-> 36
    //   #616	-> 40
    //   #617	-> 55
    //   #618	-> 88
    //   #619	-> 98
    //   #620	-> 110
    //   #621	-> 119
    //   #622	-> 140
    //   #623	-> 167
    //   #624	-> 194
    //   #626	-> 215
    //   #628	-> 224
    //   #629	-> 234
    //   #630	-> 244
    //   #632	-> 247
    //   #633	-> 252
    //   #634	-> 259
    //   #635	-> 266
    //   #636	-> 273
    //   #637	-> 288
    //   #638	-> 298
    //   #644	-> 301
    //   #645	-> 311
    //   #646	-> 321
    //   #649	-> 324
    //   #650	-> 334
    //   #651	-> 344
    //   #654	-> 347
    //   #657	-> 352
    //   #658	-> 362
    //   #659	-> 393
    //   #660	-> 403
    //   #662	-> 435
    //   #663	-> 463
    //   #664	-> 467
    //   #665	-> 477
    //   #668	-> 489
    //   #670	-> 500
    //   #671	-> 513
    //   #672	-> 537
    //   #674	-> 550
    //   #675	-> 560
    //   #679	-> 563
    //   #680	-> 576
    //   #681	-> 586
    //   #683	-> 598
    //   #684	-> 611
    //   #685	-> 635
    //   #687	-> 648
    //   #688	-> 658
    //   #693	-> 661
    //   #694	-> 673
    //   #695	-> 677
    //   #696	-> 710
    //   #698	-> 723
    //   #699	-> 735
    //   #700	-> 745
    //   #702	-> 748
    //   #703	-> 762
    //   #704	-> 772
    //   #706	-> 775
    //   #707	-> 790
    //   #709	-> 802
    //   #710	-> 812
    //   #711	-> 824
    //   #712	-> 836
    //   #713	-> 845
    //   #714	-> 855
    //   #716	-> 868
    //   #717	-> 880
    //   #718	-> 888
    //   #719	-> 897
    //   #716	-> 902
    //   #721	-> 904
    //   #722	-> 919
    //   #725	-> 932
    //   #726	-> 947
    //   #727	-> 957
    //   #729	-> 960
    //   #730	-> 972
    //   #731	-> 982
    //   #735	-> 985
    //   #737	-> 995
    //   #736	-> 998
    //   #739	-> 1000
    //   #740	-> 1015
    //   #741	-> 1023
    //   #742	-> 1033
    //   #743	-> 1043
    //   #744	-> 1057
    //   #745	-> 1071
    //   #749	-> 1077
    //   #750	-> 1092
    //   #751	-> 1114
    //   #752	-> 1131
    //   #750	-> 1140
    //   #754	-> 1145
    //   #755	-> 1154
    //   #757	-> 1165
    //   #758	-> 1171
    //   #759	-> 1183
    //   #760	-> 1186
    //   #761	-> 1194
    //   #759	-> 1197
    //   #763	-> 1202
    //   #757	-> 1219
    //   #765	-> 1228
    //   #766	-> 1240
    //   #768	-> 1244
    //   #769	-> 1254
    //   #770	-> 1264
    //   #771	-> 1269
    //   #772	-> 1275
    //   #773	-> 1280
    //   #774	-> 1286
    //   #775	-> 1291
    //   #776	-> 1297
    //   #777	-> 1311
    //   #769	-> 1317
    //   #780	-> 1325
    //   #781	-> 1335
    //   #782	-> 1340
    //   #783	-> 1346
    //   #784	-> 1351
    //   #785	-> 1357
    //   #786	-> 1362
    //   #787	-> 1368
    //   #788	-> 1382
    //   #780	-> 1388
    //   #791	-> 1393
    //   #792	-> 1404
    //   #791	-> 1415
    //   #793	-> 1417
    //   #794	-> 1423
    //   #795	-> 1434
    //   #801	-> 1437
    //   #802	-> 1447
    //   #803	-> 1461
    //   #804	-> 1471
    //   #807	-> 1474
    //   #811	-> 1490
    //   #816	-> 1503
    //   #817	-> 1513
    //   #818	-> 1522
    //   #819	-> 1532
    //   #820	-> 1541
    //   #821	-> 1551
    //   #822	-> 1564
    //   #823	-> 1578
    //   #824	-> 1592
    //   #825	-> 1606
    //   #826	-> 1620
    //   #828	-> 1633
    //   #830	-> 1647
    //   #831	-> 1657
    //   #834	-> 1677
    //   #836	-> 1681
    //   #837	-> 1691
    //   #838	-> 1701
    //   #839	-> 1706
    //   #837	-> 1712
    //   #840	-> 1720
    //   #841	-> 1731
    //   #842	-> 1741
    //   #844	-> 1746
    //   #841	-> 1752
    //   #847	-> 1760
    //   #848	-> 1770
    //   #849	-> 1775
    //   #847	-> 1781
    //   #852	-> 1786
    //   #853	-> 1790
    //   #854	-> 1794
    //   #857	-> 1798
    //   #858	-> 1816
    //   #859	-> 1820
    //   #860	-> 1826
    //   #861	-> 1840
    //   #862	-> 1867
    //   #863	-> 1890
    //   #864	-> 1897
    //   #866	-> 1905
    //   #867	-> 1930
    //   #869	-> 1939
    //   #870	-> 1965
    //   #872	-> 1974
    //   #873	-> 2000
    //   #859	-> 2009
    //   #876	-> 2024
    //   #877	-> 2035
    //   #878	-> 2046
    //   #880	-> 2057
    //   #881	-> 2070
    //   #882	-> 2083
    //   #883	-> 2094
    //   #886	-> 2098
    //   #888	-> 2109
    //   #889	-> 2140
    //   #890	-> 2150
    //   #892	-> 2164
    //   #893	-> 2167
    //   #894	-> 2198
    //   #895	-> 2208
    //   #896	-> 2220
    //   #898	-> 2223
    //   #899	-> 2254
    //   #900	-> 2264
    //   #901	-> 2269
    //   #903	-> 2284
    //   #908	-> 2296
    //   #909	-> 2310
    //   #910	-> 2318
    //   #913	-> 2331
    //   #916	-> 2335
    //   #917	-> 2346
    //   #918	-> 2354
    //   #919	-> 2395
    //   #920	-> 2405
    //   #928	-> 2424
    //   #929	-> 2426
    //   #930	-> 2436
    //   #931	-> 2441
    //   #932	-> 2444
    //   #933	-> 2445
    //   #934	-> 2455
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	2458	0	this	Lcom/js/oa/logon/bean/LogonEJBBean;
    //   0	2458	1	userAccount	Ljava/lang/String;
    //   0	2458	2	userPassword	Ljava/lang/String;
    //   0	2458	3	userIP	Ljava/lang/String;
    //   0	2458	4	serverIP	Ljava/lang/String;
    //   0	2458	5	sessionId	Ljava/lang/String;
    //   0	2458	6	domainAccount	Ljava/lang/String;
    //   0	2458	7	needPass	Ljava/lang/String;
    //   9	2449	8	userInfo	Ljava/util/HashMap;
    //   13	2445	9	userName	Ljava/lang/String;
    //   17	2441	10	dutyName	Ljava/lang/String;
    //   21	2437	11	orgName	Ljava/lang/String;
    //   25	2433	12	userId	Ljava/lang/String;
    //   723	145	13	obj	[Ljava/lang/Object;
    //   932	1489	13	obj	[Ljava/lang/Object;
    //   28	2430	14	list	Ljava/util/List;
    //   36	2388	15	domainId	Ljava/lang/String;
    //   40	2384	16	userNum	Ljava/lang/String;
    //   88	213	17	iter	Ljava/util/Iterator;
    //   110	191	18	tmpObj	[Ljava/lang/Object;
    //   215	86	19	domainInUse	Ljava/lang/String;
    //   224	77	20	domainEndDate	Ljava/util/Date;
    //   352	2072	17	databaseType	Ljava/lang/String;
    //   390	3	18	tmpIter	Ljava/util/Iterator;
    //   432	3	18	tmpIter	Ljava/util/Iterator;
    //   463	1961	18	tmpIter	Ljava/util/Iterator;
    //   467	1957	19	tmpUserNum	Ljava/lang/String;
    //   1000	437	20	date	Ljava/util/Calendar;
    //   1033	44	21	superBegin	Ljava/util/Date;
    //   1043	34	22	superEnd	Ljava/util/Date;
    //   1145	292	21	today	Ljava/lang/String;
    //   1154	283	22	ipAddr	[Ljava/lang/String;
    //   1165	272	23	ip	Ljava/lang/StringBuffer;
    //   1168	269	24	i	I
    //   1183	39	25	len	I
    //   1244	193	26	queryString	Ljava/lang/String;
    //   1417	20	27	count	I
    //   1657	764	20	orgIdString	Ljava/lang/String;
    //   1681	740	21	tmpSql	Ljava/lang/String;
    //   1790	631	22	rootCorpId	Ljava/lang/String;
    //   1794	627	23	corpId	Ljava/lang/String;
    //   1798	623	24	departId	Ljava/lang/String;
    //   1840	172	25	orgObj	[Ljava/lang/Object;
    //   1823	201	26	i	I
    //   2140	281	26	iter	Ljava/util/Iterator;
    //   2167	129	27	flag	Z
    //   2426	18	15	e	Ljava/lang/Exception;
    // Exception table:
    //   from	to	target	type
    //   32	2421	2424	java/lang/Exception
    //   32	2444	2444	finally
  }
  
  private void log(String sessionId, String userId, String userName, String userAccount, String userIP, String serverIP, String domainId, String dutyName, String orgName) {
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate("update org_employee set userOnline='1'  where EMP_ID =" + userId);
      ResultSet rs = stmt.executeQuery("select count(id) from sec_onlineuser where user_id=" + userId + " and ishelper=0");
      boolean userExist = false;
      if (rs.next() && 
        rs.getInt(1) > 0)
        userExist = true; 
      StringBuffer sql = null;
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        if (userExist) {
          sql = new StringBuffer("update sec_onlineuser set ");
          sql.append("user_ip='").append(userIP)
            .append("',session_id='").append(sessionId)
            .append("' where user_id=").append(userId);
        } else {
          sql = new StringBuffer("insert into sec_onlineuser(user_id,user_name,user_account,user_ip,server_ip,user_status,user_logtime,user_updatetime,domain_id,session_id,duty_name,org_name,ishelper) values(");
          sql.append(userId).append(",'")
            .append(userName).append("','")
            .append(userAccount).append("','")
            .append(userIP).append("','")
            .append(serverIP).append("',1,now(),now(),")
            .append(domainId).append(",'")
            .append(sessionId).append("','")
            .append(dutyName).append("','")
            .append(orgName).append("',0)");
        } 
      } else if (databaseType.indexOf("oracle") >= 0) {
        if (userExist) {
          sql = new StringBuffer("update sec_onlineuser set ");
          sql.append("user_ip='").append(userIP)
            .append("',session_id='").append(sessionId)
            .append("' where user_id=").append(userId);
        } else {
          sql = new StringBuffer("insert into sec_onlineuser(id,user_id,user_name,user_account,user_ip,server_ip,user_status,user_logtime,user_updatetime,domain_id,session_id,duty_name,org_name,ishelper) values(");
          sql.append("hibernate_sequence.nextval,")
            .append(userId).append(",'")
            .append(userName).append("','")
            .append(userAccount).append("','")
            .append(userIP).append("','")
            .append(serverIP).append("',1,sysdate,sysdate,")
            .append(domainId).append(",'")
            .append(sessionId).append("','")
            .append(dutyName).append("','")
            .append(orgName).append("',0)");
        } 
      } else if (databaseType.indexOf("sqlserver") >= 0) {
        if (userExist) {
          sql = new StringBuffer("update sec_onlineuser set ");
          sql.append("user_ip='").append(userIP)
            .append("',session_id='").append(sessionId)
            .append("' where user_id=").append(userId);
        } else {
          sql = new StringBuffer("insert into sec_onlineuser(user_id,user_name,user_account,user_ip,server_ip,user_status,user_logtime,user_updatetime,domain_id,session_id,duty_name,org_name,ishelper) values(");
          sql.append(userId).append(",'")
            .append(userName).append("','")
            .append(userAccount).append("','")
            .append(userIP).append("','")
            .append(serverIP).append("',1,getdate(),getdate(),")
            .append(domainId).append(",'")
            .append(sessionId).append("','")
            .append(dutyName).append("','")
            .append(orgName).append("',0)");
        } 
      } else if (databaseType.indexOf("db2") >= 0) {
        if (userExist) {
          sql = new StringBuffer("updsate sec_onlineuser set ");
          sql.append("user_ip='").append(userIP)
            .append("',session_id='").append(sessionId)
            .append("' where user_id=").append(userId);
        } else {
          sql.append(userId).append(",'")
            .append(userName).append("','")
            .append(userAccount).append("','")
            .append(userIP).append("','")
            .append(serverIP).append("',1,current timestamp,current timestamp,")
            .append(domainId).append(",'")
            .append(sessionId).append("','")
            .append(dutyName).append("','")
            .append(orgName).append("',0)");
        } 
      } 
      stmt.executeUpdate(sql.toString());
      stmt.close();
      conn.close();
      Date date = new Date();
      LogBD bd = new LogBD();
      bd.log(userId, userName, orgName, "oa_index", "首页", date, date, "0", "", userIP, domainId);
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public String validateIp(String userAccount, String userPassword, String userIP, String sessionId) throws Exception {
    String re = "Y";
    String userName = "";
    String dutyName = "";
    String orgName = "";
    String userId = "0";
    String orgIdString = "";
    List<Object[]> list = null;
    begin();
    if (StaticParam.isFirstTimeLoginValidate("0") && isFirstTimeLogin(userAccount)) {
      re = "首次登录请重置密码!";
    } else if (isPasswordOutOfDate(userAccount, "0")) {
      re = "您的密码已过期,请重置密码!";
    } 
    if ("Y".equals(re)) {
      if (userAccount.toLowerCase().equals("admin")) {
        list = this.session.createQuery("SELECT emp.userPassword,emp.keyValidate,emp.keySerial,emp.skin,emp.empId,emp.empName FROM com.js.system.vo.usermanager.EmployeeVO emp WHERE emp.userAccounts='admin' and emp.domainId=0").list();
        Object[] obj = list.get(0);
        userId = obj[4].toString();
        userName = obj[5].toString();
        if (!obj[0].toString().equals(userPassword)) {
          re = "密码错误！";
          userId = "0";
        } 
      } else {
        list = this.session.createQuery("SELECT employee.userIsActive,employee.userIsSuper,employee.userPassword,employee.empId,employee.empName,employee.browseRange,org.orgId,org.orgIdString,employee.keyValidate,employee.keySerial,employee.userSuperBegin,employee.userSuperEnd,employee.userSimpleName,org.orgSerial,org.orgSimpleName,employee.skin,employee.empDuty,employee.empEnglishName FROM com.js.system.vo.usermanager.EmployeeVO employee join employee.organizations org WHERE employee.userAccounts='" + 
            userAccount + "' and employee.userIsDeleted=0 and employee.domainId=0").list();
        if (list != null && list.size() > 0) {
          Object[] obj = list.get(0);
          userId = obj[3].toString();
          userName = obj[4].toString();
          orgIdString = obj[7].toString();
          dutyName = obj[16].toString();
          if ("0".equals(obj[0].toString())) {
            re = "该用户已被禁用！";
            userId = "0";
          } 
          Calendar date = 
            Calendar.getInstance();
          if ("1".equals(obj[1].toString()) && 
            obj[10] != null) {
            Date superBegin = (Date)obj[10];
            Date superEnd = (Date)obj[11];
            if (date.getTimeInMillis() > superEnd.getTime() || 
              date.getTimeInMillis() < superBegin.getTime())
              obj[1] = "0"; 
          } 
          if ("0".equals(obj[1].toString())) {
            String today = String.valueOf(date.get(1)) + "/" + (
              date.get(2) + 1) + "/" + 
              date.get(5);
            String[] ipAddr = userIP.split("\\.");
            StringBuffer ip = new StringBuffer(16);
            for (int i = 0; i < 4; i++) {
              int len = 3 - ipAddr[i].length();
              while (len > 0) {
                ip.append("0");
                len--;
              } 
              ip.append(ipAddr[i]).append(".");
            } 
            userIP = ip.toString().substring(0, 15);
            String queryString = "";
            String databaseType = 
              SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              queryString = "SELECT COUNT(*) FROM com.js.oa.security.ip.po.IPPO ip WHERE ip.domainId=0 and ip.ipIsOpen=1 AND ('" + 
                
                today + 
                "'>=ip.ipOpenBeginTime AND '" + 
                today + 
                "'<=ip.ipOpenEndTime AND (ip.ipAddressBegin='" + 
                userIP + "' OR '" + userIP + 
                "' BETWEEN ip.ipAddressBegin AND ip.ipAddressEnd))";
            } else {
              queryString = "SELECT COUNT(*) FROM com.js.oa.security.ip.po.IPPO ip WHERE ip.domainId=0 and ip.ipIsOpen=1 AND (JSDB.FN_STRTODATE('" + 
                
                today + 
                "','S')>=ip.ipOpenBeginTime AND JSDB.FN_STRTODATE('" + 
                today + 
                "','S')<=ip.ipOpenEndTime AND (ip.ipAddressBegin='" + 
                userIP + "' OR '" + userIP + 
                "' BETWEEN ip.ipAddressBegin AND ip.ipAddressEnd))";
            } 
            int count = ((Integer)this.session.iterate(queryString)
              .next()).intValue();
            if (count < 1) {
              re = "对不起，你的IP不在可访问范围内！";
              userId = "0";
            } 
          } 
          if (!obj[2].toString().equals(userPassword)) {
            re = "密码错误！";
            userId = "0";
          } 
        } else {
          re = "用户名或密码错误！";
          userId = "0";
        } 
      } 
      if ("Y".equals(re)) {
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        tmpSql = "SELECT org.orgName,org.orgId,org.orgType FROM com.js.system.vo.organizationmanager.OrganizationVO org WHERE '" + 
          orgIdString + 
          "' LIKE concat('%$', org.orgId, '$%') and org.orgId<>-1 ORDER BY org.orgLevel";
        String rootCorpId = "";
        String corpId = "";
        String departId = "";
        list = this.session.createQuery(tmpSql).list();
        orgIdString = "";
        for (int i = 0; i < list.size(); i++) {
          Object[] orgObj = list.get(i);
          orgIdString = String.valueOf(orgIdString) + orgObj[0].toString();
          orgIdString = String.valueOf(orgIdString) + ".";
          if (rootCorpId.equals("") && "0".equals(orgObj[2].toString()))
            rootCorpId = orgObj[1].toString(); 
          if (corpId.equals("") && "3".equals(orgObj[2].toString()))
            corpId = orgObj[1].toString(); 
          if (departId.equals("") && "2".equals(orgObj[2].toString()))
            departId = orgObj[1].toString(); 
        } 
        if (orgIdString.endsWith("."))
          orgIdString = orgIdString.substring(0, orgIdString.length() - 1); 
        orgName = orgIdString;
        String serverIP = "";
        try {
          serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception exception) {}
        Connection conn = null;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("delete from sec_onlineuser where user_id=" + userId + " and ishelper=1");
        stmt.executeUpdate("update org_employee set userOnline='1'  where EMP_ID =" + userId);
        StringBuffer sql = new StringBuffer("insert into sec_onlineuser(user_id,user_name,user_account,user_ip,server_ip,user_status,user_logtime,user_updatetime,domain_id,session_id,duty_name,org_name,ishelper) values(");
        sql.append(userId).append(",'")
          .append(userName).append("','")
          .append(userAccount).append("','")
          .append(userIP).append("','")
          .append(serverIP).append("',1,now(),now(),")
          .append("0").append(",'")
          .append(sessionId).append("','")
          .append(dutyName).append("','")
          .append(orgName).append("',")
          .append(1).append(")");
        stmt.execute(sql.toString());
        Date date = new Date();
        LogBD bd = new LogBD();
        bd.log(userId, userName, orgName, "oa_index", "首页", date, date, "0", "", userIP, "0");
        stmt.close();
        conn.close();
      } 
    } 
    return "<validat>" + re + "</validat><userId>" + userId + "</userId><userName>" + userName + "</userName>";
  }
  
  public void delForVb(String userId) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("delete from sec_onlineuser where user_id=" + Long.valueOf(userId) + " and ishelper=1");
      stmt.executeUpdate("update org_employee set userOnline='0'  where EMP_ID =" + userId);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
  }
  
  public void updateUserTime(String userId, String sessionId) throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("update sec_onlineuser set USER_UPDATETIME= now()  where USER_ID =" + userId + " and SESSION_ID like '" + sessionId + "' and ishelper=1");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
    } 
  }
  
  private boolean isFirstTimeLogin(String userAccount) throws Exception {
    boolean flag = StaticParam.isFirstTimeLogin(userAccount);
    return flag;
  }
  
  private boolean isPasswordOutOfDate(String userAccount, String domainId) throws Exception {
    String pwd_date = StaticParam.isValidatePasswordOutDate(domainId);
    if (!pwd_date.equals("0")) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String currentDate = DateHelper.date2String(new Date(), "yyyy-MM-dd HH:mm");
      String last_modify_pwd_date = StaticParam.getLastModifyPwdDate(userAccount);
      int dateDiffer = (int)DateHelper.getDistance(sdf.parse(last_modify_pwd_date), sdf.parse(currentDate));
      int date_num = Integer.parseInt(pwd_date);
      if (dateDiffer >= date_num)
        return true; 
      return false;
    } 
    return false;
  }
  
  public String getUserAccountByNumber(String userNumber) {
    String account = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      String sql = "select useraccounts from org_employee where empnumber=? and userisactive=1 and userisdeleted=0";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userNumber);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        account = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return account;
  }
}
