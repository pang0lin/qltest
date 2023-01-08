package com.js.ldap.supervise.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class SuperviseAction extends Action {
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  private SimpleDateFormat sdf = null;
  
  private SimpleDateFormat format = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    // Byte code:
    //   0: aload_2
    //   1: checkcast com/js/ldap/supervise/action/SuperviseForm
    //   4: astore #5
    //   6: aload_3
    //   7: ldc 'type'
    //   9: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   14: astore #6
    //   16: aload_3
    //   17: ldc 'type'
    //   19: aload #6
    //   21: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   26: ldc ''
    //   28: astore #7
    //   30: ldc ''
    //   32: astore #8
    //   34: ldc '0'
    //   36: astore #9
    //   38: aload_3
    //   39: invokeinterface getSession : ()Ljavax/servlet/http/HttpSession;
    //   44: ldc 'userAccount'
    //   46: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/Object;
    //   51: checkcast java/lang/String
    //   54: astore #10
    //   56: new java/util/ArrayList
    //   59: dup
    //   60: invokespecial <init> : ()V
    //   63: astore #11
    //   65: ldc '1'
    //   67: aload #6
    //   69: invokevirtual equals : (Ljava/lang/Object;)Z
    //   72: ifeq -> 1207
    //   75: aload_3
    //   76: ldc '/uploadtemplate/supervisemonthtemplate.xls'
    //   78: invokeinterface getRealPath : (Ljava/lang/String;)Ljava/lang/String;
    //   83: astore #7
    //   85: aload #5
    //   87: invokevirtual getFile : ()Lorg/apache/struts/upload/FormFile;
    //   90: aload #7
    //   92: invokestatic uploadFile : (Lorg/apache/struts/upload/FormFile;Ljava/lang/String;)V
    //   95: new java/io/FileInputStream
    //   98: dup
    //   99: new java/io/File
    //   102: dup
    //   103: aload #7
    //   105: invokespecial <init> : (Ljava/lang/String;)V
    //   108: invokespecial <init> : (Ljava/io/File;)V
    //   111: astore #12
    //   113: aload_0
    //   114: aload #12
    //   116: invokestatic getWorkbook : (Ljava/io/InputStream;)Ljxl/Workbook;
    //   119: putfield workbook : Ljxl/Workbook;
    //   122: goto -> 190
    //   125: astore #13
    //   127: new java/lang/StringBuilder
    //   130: dup
    //   131: aload #8
    //   133: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   136: invokespecial <init> : (Ljava/lang/String;)V
    //   139: ldc '选择的模版不正确！<br>'
    //   141: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: invokevirtual toString : ()Ljava/lang/String;
    //   147: astore #8
    //   149: ldc '1'
    //   151: aload #9
    //   153: invokevirtual equals : (Ljava/lang/Object;)Z
    //   156: ifne -> 163
    //   159: ldc '1'
    //   161: astore #9
    //   163: aload_3
    //   164: ldc 'succeed'
    //   166: aload #9
    //   168: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   173: aload_3
    //   174: ldc 'message'
    //   176: aload #8
    //   178: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   183: aload_1
    //   184: ldc 'input'
    //   186: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   189: areturn
    //   190: aload_0
    //   191: aload_0
    //   192: getfield workbook : Ljxl/Workbook;
    //   195: iconst_0
    //   196: invokevirtual getSheet : (I)Ljxl/Sheet;
    //   199: putfield sheet : Ljxl/Sheet;
    //   202: aload_0
    //   203: getfield sheet : Ljxl/Sheet;
    //   206: ifnull -> 1170
    //   209: aload_0
    //   210: getfield sheet : Ljxl/Sheet;
    //   213: invokeinterface getRows : ()I
    //   218: istore #13
    //   220: iconst_3
    //   221: istore #14
    //   223: goto -> 1114
    //   226: new java/util/HashMap
    //   229: dup
    //   230: invokespecial <init> : ()V
    //   233: astore #15
    //   235: ldc '0'
    //   237: astore #9
    //   239: aload_0
    //   240: getfield sheet : Ljxl/Sheet;
    //   243: iconst_0
    //   244: iload #14
    //   246: invokeinterface getCell : (II)Ljxl/Cell;
    //   251: invokeinterface getContents : ()Ljava/lang/String;
    //   256: invokevirtual trim : ()Ljava/lang/String;
    //   259: astore #16
    //   261: aload_0
    //   262: getfield sheet : Ljxl/Sheet;
    //   265: iconst_1
    //   266: iload #14
    //   268: invokeinterface getCell : (II)Ljxl/Cell;
    //   273: invokeinterface getContents : ()Ljava/lang/String;
    //   278: invokevirtual trim : ()Ljava/lang/String;
    //   281: astore #17
    //   283: aload_0
    //   284: getfield sheet : Ljxl/Sheet;
    //   287: iconst_2
    //   288: iload #14
    //   290: invokeinterface getCell : (II)Ljxl/Cell;
    //   295: invokeinterface getContents : ()Ljava/lang/String;
    //   300: invokevirtual trim : ()Ljava/lang/String;
    //   303: astore #18
    //   305: aload_0
    //   306: getfield sheet : Ljxl/Sheet;
    //   309: iconst_3
    //   310: iload #14
    //   312: invokeinterface getCell : (II)Ljxl/Cell;
    //   317: invokeinterface getContents : ()Ljava/lang/String;
    //   322: invokevirtual trim : ()Ljava/lang/String;
    //   325: astore #19
    //   327: aload_0
    //   328: getfield sheet : Ljxl/Sheet;
    //   331: iconst_4
    //   332: iload #14
    //   334: invokeinterface getCell : (II)Ljxl/Cell;
    //   339: invokeinterface getContents : ()Ljava/lang/String;
    //   344: invokevirtual trim : ()Ljava/lang/String;
    //   347: astore #20
    //   349: aload_0
    //   350: getfield sheet : Ljxl/Sheet;
    //   353: iconst_5
    //   354: iload #14
    //   356: invokeinterface getCell : (II)Ljxl/Cell;
    //   361: invokeinterface getContents : ()Ljava/lang/String;
    //   366: invokevirtual trim : ()Ljava/lang/String;
    //   369: astore #21
    //   371: aload_0
    //   372: getfield sheet : Ljxl/Sheet;
    //   375: bipush #6
    //   377: iload #14
    //   379: invokeinterface getCell : (II)Ljxl/Cell;
    //   384: invokeinterface getContents : ()Ljava/lang/String;
    //   389: invokevirtual trim : ()Ljava/lang/String;
    //   392: astore #22
    //   394: ldc ''
    //   396: aload #16
    //   398: invokevirtual equals : (Ljava/lang/Object;)Z
    //   401: ifeq -> 445
    //   404: new java/lang/StringBuilder
    //   407: dup
    //   408: aload #8
    //   410: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   413: invokespecial <init> : (Ljava/lang/String;)V
    //   416: ldc '第'
    //   418: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   421: iload #14
    //   423: iconst_1
    //   424: iadd
    //   425: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   428: ldc '行【督察月份】为空！<br>'
    //   430: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   433: invokevirtual toString : ()Ljava/lang/String;
    //   436: astore #8
    //   438: ldc '1'
    //   440: astore #9
    //   442: goto -> 1111
    //   445: aload_0
    //   446: aload #16
    //   448: invokevirtual trim : ()Ljava/lang/String;
    //   451: aload #6
    //   453: invokespecial formatDate : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   456: astore #23
    //   458: ldc '0'
    //   460: aload #23
    //   462: invokevirtual equals : (Ljava/lang/Object;)Z
    //   465: ifeq -> 509
    //   468: new java/lang/StringBuilder
    //   471: dup
    //   472: aload #8
    //   474: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   477: invokespecial <init> : (Ljava/lang/String;)V
    //   480: ldc '第'
    //   482: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   485: iload #14
    //   487: iconst_1
    //   488: iadd
    //   489: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   492: ldc '行暂未识别的导入！<br>'
    //   494: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   497: invokevirtual toString : ()Ljava/lang/String;
    //   500: astore #8
    //   502: ldc '1'
    //   504: astore #9
    //   506: goto -> 1111
    //   509: ldc '1'
    //   511: aload #23
    //   513: invokevirtual equals : (Ljava/lang/Object;)Z
    //   516: ifeq -> 560
    //   519: new java/lang/StringBuilder
    //   522: dup
    //   523: aload #8
    //   525: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   528: invokespecial <init> : (Ljava/lang/String;)V
    //   531: ldc '第'
    //   533: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   536: iload #14
    //   538: iconst_1
    //   539: iadd
    //   540: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   543: ldc '行【督察月份】格式无法识别！<br>'
    //   545: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   548: invokevirtual toString : ()Ljava/lang/String;
    //   551: astore #8
    //   553: ldc '1'
    //   555: astore #9
    //   557: goto -> 1111
    //   560: ldc '2'
    //   562: aload #23
    //   564: invokevirtual equals : (Ljava/lang/Object;)Z
    //   567: ifeq -> 611
    //   570: new java/lang/StringBuilder
    //   573: dup
    //   574: aload #8
    //   576: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   579: invokespecial <init> : (Ljava/lang/String;)V
    //   582: ldc '第'
    //   584: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   587: iload #14
    //   589: iconst_1
    //   590: iadd
    //   591: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   594: ldc '行【督察月份】数据转换异常！<br>'
    //   596: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   599: invokevirtual toString : ()Ljava/lang/String;
    //   602: astore #8
    //   604: ldc '1'
    //   606: astore #9
    //   608: goto -> 1111
    //   611: aload #15
    //   613: ldc 'dbyf'
    //   615: aload #23
    //   617: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   622: pop
    //   623: ldc ''
    //   625: aload #17
    //   627: invokevirtual equals : (Ljava/lang/Object;)Z
    //   630: ifeq -> 648
    //   633: aload #15
    //   635: ldc 'xh'
    //   637: ldc '0'
    //   639: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   644: pop
    //   645: goto -> 734
    //   648: aload #17
    //   650: invokevirtual trim : ()Ljava/lang/String;
    //   653: invokestatic parseInt : (Ljava/lang/String;)I
    //   656: istore #23
    //   658: aload #15
    //   660: ldc 'xh'
    //   662: new java/lang/StringBuilder
    //   665: dup
    //   666: iload #23
    //   668: invokestatic valueOf : (I)Ljava/lang/String;
    //   671: invokespecial <init> : (Ljava/lang/String;)V
    //   674: invokevirtual toString : ()Ljava/lang/String;
    //   677: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   682: pop
    //   683: goto -> 734
    //   686: astore #23
    //   688: aload #23
    //   690: invokevirtual printStackTrace : ()V
    //   693: new java/lang/StringBuilder
    //   696: dup
    //   697: aload #8
    //   699: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   702: invokespecial <init> : (Ljava/lang/String;)V
    //   705: ldc '第'
    //   707: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   710: iload #14
    //   712: iconst_1
    //   713: iadd
    //   714: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   717: ldc '行【序号】数据转换异常！<br>'
    //   719: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   722: invokevirtual toString : ()Ljava/lang/String;
    //   725: astore #8
    //   727: ldc '1'
    //   729: astore #9
    //   731: goto -> 1111
    //   734: ldc ''
    //   736: aload #18
    //   738: invokevirtual equals : (Ljava/lang/Object;)Z
    //   741: ifeq -> 785
    //   744: new java/lang/StringBuilder
    //   747: dup
    //   748: aload #8
    //   750: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   753: invokespecial <init> : (Ljava/lang/String;)V
    //   756: ldc '第'
    //   758: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   761: iload #14
    //   763: iconst_1
    //   764: iadd
    //   765: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   768: ldc '行【工作任务】为空！<br>'
    //   770: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   773: invokevirtual toString : ()Ljava/lang/String;
    //   776: astore #8
    //   778: ldc '1'
    //   780: astore #9
    //   782: goto -> 1111
    //   785: aload #15
    //   787: ldc 'gzrw'
    //   789: aload #18
    //   791: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   796: pop
    //   797: ldc ''
    //   799: aload #19
    //   801: invokevirtual equals : (Ljava/lang/Object;)Z
    //   804: ifeq -> 848
    //   807: new java/lang/StringBuilder
    //   810: dup
    //   811: aload #8
    //   813: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   816: invokespecial <init> : (Ljava/lang/String;)V
    //   819: ldc '第'
    //   821: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   824: iload #14
    //   826: iconst_1
    //   827: iadd
    //   828: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   831: ldc '行【责任领导】为空！<br>'
    //   833: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   836: invokevirtual toString : ()Ljava/lang/String;
    //   839: astore #8
    //   841: ldc '1'
    //   843: astore #9
    //   845: goto -> 1111
    //   848: aload #15
    //   850: ldc 'zrld'
    //   852: aload #19
    //   854: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   859: pop
    //   860: ldc ''
    //   862: aload #20
    //   864: invokevirtual equals : (Ljava/lang/Object;)Z
    //   867: ifeq -> 911
    //   870: new java/lang/StringBuilder
    //   873: dup
    //   874: aload #8
    //   876: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   879: invokespecial <init> : (Ljava/lang/String;)V
    //   882: ldc '第'
    //   884: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   887: iload #14
    //   889: iconst_1
    //   890: iadd
    //   891: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   894: ldc '行【责任部门】为空！<br>'
    //   896: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   899: invokevirtual toString : ()Ljava/lang/String;
    //   902: astore #8
    //   904: ldc '1'
    //   906: astore #9
    //   908: goto -> 1111
    //   911: aload #15
    //   913: ldc 'zrbm'
    //   915: aload #20
    //   917: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   922: pop
    //   923: ldc ''
    //   925: aload #21
    //   927: invokevirtual equals : (Ljava/lang/Object;)Z
    //   930: ifeq -> 948
    //   933: aload #15
    //   935: ldc 'wcqk'
    //   937: ldc ''
    //   939: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   944: pop
    //   945: goto -> 1089
    //   948: ldc '正在进行'
    //   950: aload #21
    //   952: invokevirtual equals : (Ljava/lang/Object;)Z
    //   955: ifeq -> 973
    //   958: aload #15
    //   960: ldc 'wcqk'
    //   962: ldc '正在进行'
    //   964: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   969: pop
    //   970: goto -> 1089
    //   973: ldc '完成'
    //   975: aload #21
    //   977: invokevirtual equals : (Ljava/lang/Object;)Z
    //   980: ifeq -> 998
    //   983: aload #15
    //   985: ldc 'wcqk'
    //   987: ldc '完成'
    //   989: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   994: pop
    //   995: goto -> 1089
    //   998: ldc '未完成'
    //   1000: aload #21
    //   1002: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1005: ifeq -> 1023
    //   1008: aload #15
    //   1010: ldc 'wcqk'
    //   1012: ldc '未完成'
    //   1014: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1019: pop
    //   1020: goto -> 1089
    //   1023: ldc '正在筹备'
    //   1025: aload #21
    //   1027: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1030: ifeq -> 1048
    //   1033: aload #15
    //   1035: ldc 'wcqk'
    //   1037: ldc '正在筹备'
    //   1039: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1044: pop
    //   1045: goto -> 1089
    //   1048: new java/lang/StringBuilder
    //   1051: dup
    //   1052: aload #8
    //   1054: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1057: invokespecial <init> : (Ljava/lang/String;)V
    //   1060: ldc '第'
    //   1062: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1065: iload #14
    //   1067: iconst_1
    //   1068: iadd
    //   1069: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1072: ldc '行【完成情况】填写错误！<br>'
    //   1074: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1077: invokevirtual toString : ()Ljava/lang/String;
    //   1080: astore #8
    //   1082: ldc '1'
    //   1084: astore #9
    //   1086: goto -> 1111
    //   1089: aload #15
    //   1091: ldc 'jysm'
    //   1093: aload #22
    //   1095: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1100: pop
    //   1101: aload #11
    //   1103: aload #15
    //   1105: invokeinterface add : (Ljava/lang/Object;)Z
    //   1110: pop
    //   1111: iinc #14, 1
    //   1114: iload #14
    //   1116: iload #13
    //   1118: if_icmplt -> 226
    //   1121: aload #11
    //   1123: invokeinterface size : ()I
    //   1128: ifle -> 1170
    //   1131: new java/lang/StringBuilder
    //   1134: dup
    //   1135: aload #8
    //   1137: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1140: invokespecial <init> : (Ljava/lang/String;)V
    //   1143: aload #11
    //   1145: ldc '1'
    //   1147: aload #10
    //   1149: invokestatic dataCombination : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1155: invokevirtual toString : ()Ljava/lang/String;
    //   1158: astore #8
    //   1160: goto -> 1170
    //   1163: astore #14
    //   1165: aload #14
    //   1167: invokevirtual printStackTrace : ()V
    //   1170: aload_3
    //   1171: ldc 'message'
    //   1173: aload #8
    //   1175: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   1180: aload_3
    //   1181: ldc 'newType'
    //   1183: aload #6
    //   1185: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   1190: aload_3
    //   1191: ldc 'succeed'
    //   1193: aload #9
    //   1195: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   1200: aload_1
    //   1201: ldc 'result'
    //   1203: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   1206: areturn
    //   1207: ldc '2'
    //   1209: aload #6
    //   1211: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1214: ifeq -> 2291
    //   1217: aload_3
    //   1218: ldc '/uploadtemplate/superviseyeartemplate.xls'
    //   1220: invokeinterface getRealPath : (Ljava/lang/String;)Ljava/lang/String;
    //   1225: astore #7
    //   1227: aload #5
    //   1229: invokevirtual getFile : ()Lorg/apache/struts/upload/FormFile;
    //   1232: aload #7
    //   1234: invokestatic uploadFile : (Lorg/apache/struts/upload/FormFile;Ljava/lang/String;)V
    //   1237: new java/io/FileInputStream
    //   1240: dup
    //   1241: new java/io/File
    //   1244: dup
    //   1245: aload #7
    //   1247: invokespecial <init> : (Ljava/lang/String;)V
    //   1250: invokespecial <init> : (Ljava/io/File;)V
    //   1253: astore #12
    //   1255: aload_0
    //   1256: aload #12
    //   1258: invokestatic getWorkbook : (Ljava/io/InputStream;)Ljxl/Workbook;
    //   1261: putfield workbook : Ljxl/Workbook;
    //   1264: goto -> 1332
    //   1267: astore #13
    //   1269: new java/lang/StringBuilder
    //   1272: dup
    //   1273: aload #8
    //   1275: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1278: invokespecial <init> : (Ljava/lang/String;)V
    //   1281: ldc '选择的模版不正确！<br>'
    //   1283: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1286: invokevirtual toString : ()Ljava/lang/String;
    //   1289: astore #8
    //   1291: ldc '1'
    //   1293: aload #9
    //   1295: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1298: ifne -> 1305
    //   1301: ldc '1'
    //   1303: astore #9
    //   1305: aload_3
    //   1306: ldc 'succeed'
    //   1308: aload #9
    //   1310: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   1315: aload_3
    //   1316: ldc 'message'
    //   1318: aload #8
    //   1320: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   1325: aload_1
    //   1326: ldc 'input'
    //   1328: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   1331: areturn
    //   1332: aload_0
    //   1333: aload_0
    //   1334: getfield workbook : Ljxl/Workbook;
    //   1337: iconst_0
    //   1338: invokevirtual getSheet : (I)Ljxl/Sheet;
    //   1341: putfield sheet : Ljxl/Sheet;
    //   1344: aload_0
    //   1345: getfield sheet : Ljxl/Sheet;
    //   1348: ifnull -> 2254
    //   1351: aload_0
    //   1352: getfield sheet : Ljxl/Sheet;
    //   1355: invokeinterface getRows : ()I
    //   1360: istore #13
    //   1362: iconst_3
    //   1363: istore #14
    //   1365: goto -> 2198
    //   1368: new java/util/HashMap
    //   1371: dup
    //   1372: invokespecial <init> : ()V
    //   1375: astore #15
    //   1377: ldc '0'
    //   1379: astore #9
    //   1381: aload_0
    //   1382: getfield sheet : Ljxl/Sheet;
    //   1385: iconst_0
    //   1386: iload #14
    //   1388: invokeinterface getCell : (II)Ljxl/Cell;
    //   1393: invokeinterface getContents : ()Ljava/lang/String;
    //   1398: invokevirtual trim : ()Ljava/lang/String;
    //   1401: astore #16
    //   1403: aload_0
    //   1404: getfield sheet : Ljxl/Sheet;
    //   1407: iconst_1
    //   1408: iload #14
    //   1410: invokeinterface getCell : (II)Ljxl/Cell;
    //   1415: invokeinterface getContents : ()Ljava/lang/String;
    //   1420: invokevirtual trim : ()Ljava/lang/String;
    //   1423: astore #17
    //   1425: aload_0
    //   1426: getfield sheet : Ljxl/Sheet;
    //   1429: iconst_2
    //   1430: iload #14
    //   1432: invokeinterface getCell : (II)Ljxl/Cell;
    //   1437: invokeinterface getContents : ()Ljava/lang/String;
    //   1442: invokevirtual trim : ()Ljava/lang/String;
    //   1445: astore #18
    //   1447: aload_0
    //   1448: getfield sheet : Ljxl/Sheet;
    //   1451: iconst_3
    //   1452: iload #14
    //   1454: invokeinterface getCell : (II)Ljxl/Cell;
    //   1459: invokeinterface getContents : ()Ljava/lang/String;
    //   1464: invokevirtual trim : ()Ljava/lang/String;
    //   1467: astore #19
    //   1469: aload_0
    //   1470: getfield sheet : Ljxl/Sheet;
    //   1473: iconst_4
    //   1474: iload #14
    //   1476: invokeinterface getCell : (II)Ljxl/Cell;
    //   1481: invokeinterface getContents : ()Ljava/lang/String;
    //   1486: invokevirtual trim : ()Ljava/lang/String;
    //   1489: astore #20
    //   1491: aload_0
    //   1492: getfield sheet : Ljxl/Sheet;
    //   1495: iconst_5
    //   1496: iload #14
    //   1498: invokeinterface getCell : (II)Ljxl/Cell;
    //   1503: invokeinterface getContents : ()Ljava/lang/String;
    //   1508: invokevirtual trim : ()Ljava/lang/String;
    //   1511: astore #21
    //   1513: aload_0
    //   1514: getfield sheet : Ljxl/Sheet;
    //   1517: bipush #6
    //   1519: iload #14
    //   1521: invokeinterface getCell : (II)Ljxl/Cell;
    //   1526: invokeinterface getContents : ()Ljava/lang/String;
    //   1531: invokevirtual trim : ()Ljava/lang/String;
    //   1534: astore #22
    //   1536: ldc ''
    //   1538: aload #16
    //   1540: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1543: ifeq -> 1587
    //   1546: new java/lang/StringBuilder
    //   1549: dup
    //   1550: aload #8
    //   1552: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1555: invokespecial <init> : (Ljava/lang/String;)V
    //   1558: ldc '第'
    //   1560: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1563: iload #14
    //   1565: iconst_1
    //   1566: iadd
    //   1567: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1570: ldc '行【督办年份】为空！<br>'
    //   1572: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1575: invokevirtual toString : ()Ljava/lang/String;
    //   1578: astore #8
    //   1580: ldc '1'
    //   1582: astore #9
    //   1584: goto -> 2195
    //   1587: aload #16
    //   1589: invokevirtual trim : ()Ljava/lang/String;
    //   1592: invokevirtual length : ()I
    //   1595: iconst_4
    //   1596: if_icmpne -> 1617
    //   1599: aload #15
    //   1601: ldc 'dbnf'
    //   1603: aload #16
    //   1605: invokevirtual trim : ()Ljava/lang/String;
    //   1608: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1613: pop
    //   1614: goto -> 1795
    //   1617: aload_0
    //   1618: aload #16
    //   1620: invokevirtual trim : ()Ljava/lang/String;
    //   1623: aload #6
    //   1625: invokespecial formatDate : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1628: astore #23
    //   1630: ldc '0'
    //   1632: aload #23
    //   1634: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1637: ifeq -> 1681
    //   1640: new java/lang/StringBuilder
    //   1643: dup
    //   1644: aload #8
    //   1646: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1649: invokespecial <init> : (Ljava/lang/String;)V
    //   1652: ldc '第'
    //   1654: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1657: iload #14
    //   1659: iconst_1
    //   1660: iadd
    //   1661: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1664: ldc '行暂未识别的导入！<br>'
    //   1666: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1669: invokevirtual toString : ()Ljava/lang/String;
    //   1672: astore #8
    //   1674: ldc '1'
    //   1676: astore #9
    //   1678: goto -> 2195
    //   1681: ldc '1'
    //   1683: aload #23
    //   1685: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1688: ifeq -> 1732
    //   1691: new java/lang/StringBuilder
    //   1694: dup
    //   1695: aload #8
    //   1697: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1700: invokespecial <init> : (Ljava/lang/String;)V
    //   1703: ldc '第'
    //   1705: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1708: iload #14
    //   1710: iconst_1
    //   1711: iadd
    //   1712: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1715: ldc '行【督办年份】格式无法识别！<br>'
    //   1717: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1720: invokevirtual toString : ()Ljava/lang/String;
    //   1723: astore #8
    //   1725: ldc '1'
    //   1727: astore #9
    //   1729: goto -> 2195
    //   1732: ldc '2'
    //   1734: aload #23
    //   1736: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1739: ifeq -> 1783
    //   1742: new java/lang/StringBuilder
    //   1745: dup
    //   1746: aload #8
    //   1748: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1751: invokespecial <init> : (Ljava/lang/String;)V
    //   1754: ldc '第'
    //   1756: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1759: iload #14
    //   1761: iconst_1
    //   1762: iadd
    //   1763: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1766: ldc '行【督办年份】数据转换异常！<br>'
    //   1768: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1771: invokevirtual toString : ()Ljava/lang/String;
    //   1774: astore #8
    //   1776: ldc '1'
    //   1778: astore #9
    //   1780: goto -> 2195
    //   1783: aload #15
    //   1785: ldc 'dbnf'
    //   1787: aload #23
    //   1789: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1794: pop
    //   1795: ldc ''
    //   1797: aload #17
    //   1799: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1802: ifeq -> 1846
    //   1805: new java/lang/StringBuilder
    //   1808: dup
    //   1809: aload #8
    //   1811: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1814: invokespecial <init> : (Ljava/lang/String;)V
    //   1817: ldc '第'
    //   1819: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1822: iload #14
    //   1824: iconst_1
    //   1825: iadd
    //   1826: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1829: ldc '行【负责领导】为空！<br>'
    //   1831: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1834: invokevirtual toString : ()Ljava/lang/String;
    //   1837: astore #8
    //   1839: ldc '1'
    //   1841: astore #9
    //   1843: goto -> 2195
    //   1846: aload #15
    //   1848: ldc_w 'fzld'
    //   1851: aload #17
    //   1853: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1858: pop
    //   1859: ldc ''
    //   1861: aload #18
    //   1863: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1866: ifeq -> 1911
    //   1869: new java/lang/StringBuilder
    //   1872: dup
    //   1873: aload #8
    //   1875: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1878: invokespecial <init> : (Ljava/lang/String;)V
    //   1881: ldc '第'
    //   1883: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1886: iload #14
    //   1888: iconst_1
    //   1889: iadd
    //   1890: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1893: ldc_w '行【责任单位】为空！<br>'
    //   1896: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1899: invokevirtual toString : ()Ljava/lang/String;
    //   1902: astore #8
    //   1904: ldc '1'
    //   1906: astore #9
    //   1908: goto -> 2195
    //   1911: aload #15
    //   1913: ldc_w 'zrdw'
    //   1916: aload #18
    //   1918: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1923: pop
    //   1924: ldc ''
    //   1926: aload #19
    //   1928: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1931: ifeq -> 1975
    //   1934: new java/lang/StringBuilder
    //   1937: dup
    //   1938: aload #8
    //   1940: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1943: invokespecial <init> : (Ljava/lang/String;)V
    //   1946: ldc '第'
    //   1948: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1951: iload #14
    //   1953: iconst_1
    //   1954: iadd
    //   1955: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   1958: ldc '行【工作任务】为空！<br>'
    //   1960: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1963: invokevirtual toString : ()Ljava/lang/String;
    //   1966: astore #8
    //   1968: ldc '1'
    //   1970: astore #9
    //   1972: goto -> 2195
    //   1975: aload #15
    //   1977: ldc 'gzrw'
    //   1979: aload #19
    //   1981: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1986: pop
    //   1987: aload #15
    //   1989: ldc_w 'wcsj'
    //   1992: aload #21
    //   1994: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   1999: pop
    //   2000: aload #15
    //   2002: ldc_w 'phdw'
    //   2005: aload #20
    //   2007: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2012: pop
    //   2013: ldc ''
    //   2015: aload #22
    //   2017: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2020: ifeq -> 2039
    //   2023: aload #15
    //   2025: ldc_w 'jzqk'
    //   2028: ldc ''
    //   2030: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2035: pop
    //   2036: goto -> 2185
    //   2039: ldc '正在进行'
    //   2041: aload #22
    //   2043: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2046: ifeq -> 2065
    //   2049: aload #15
    //   2051: ldc_w 'jzqk'
    //   2054: ldc '正在进行'
    //   2056: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2061: pop
    //   2062: goto -> 2185
    //   2065: ldc '完成'
    //   2067: aload #22
    //   2069: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2072: ifeq -> 2091
    //   2075: aload #15
    //   2077: ldc_w 'jzqk'
    //   2080: ldc '完成'
    //   2082: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2087: pop
    //   2088: goto -> 2185
    //   2091: ldc '未完成'
    //   2093: aload #22
    //   2095: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2098: ifeq -> 2117
    //   2101: aload #15
    //   2103: ldc_w 'jzqk'
    //   2106: ldc '未完成'
    //   2108: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2113: pop
    //   2114: goto -> 2185
    //   2117: ldc '正在筹备'
    //   2119: aload #22
    //   2121: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2124: ifeq -> 2143
    //   2127: aload #15
    //   2129: ldc_w 'jzqk'
    //   2132: ldc '正在筹备'
    //   2134: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2139: pop
    //   2140: goto -> 2185
    //   2143: new java/lang/StringBuilder
    //   2146: dup
    //   2147: aload #8
    //   2149: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2152: invokespecial <init> : (Ljava/lang/String;)V
    //   2155: ldc '第'
    //   2157: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2160: iload #14
    //   2162: iconst_1
    //   2163: iadd
    //   2164: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2167: ldc_w '行【进展情况】填写错误！<br>'
    //   2170: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2173: invokevirtual toString : ()Ljava/lang/String;
    //   2176: astore #8
    //   2178: ldc '1'
    //   2180: astore #9
    //   2182: goto -> 2195
    //   2185: aload #11
    //   2187: aload #15
    //   2189: invokeinterface add : (Ljava/lang/Object;)Z
    //   2194: pop
    //   2195: iinc #14, 1
    //   2198: iload #14
    //   2200: iload #13
    //   2202: if_icmplt -> 1368
    //   2205: aload #11
    //   2207: invokeinterface size : ()I
    //   2212: ifle -> 2254
    //   2215: new java/lang/StringBuilder
    //   2218: dup
    //   2219: aload #8
    //   2221: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2224: invokespecial <init> : (Ljava/lang/String;)V
    //   2227: aload #11
    //   2229: ldc '2'
    //   2231: aload #10
    //   2233: invokestatic dataCombination : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2239: invokevirtual toString : ()Ljava/lang/String;
    //   2242: astore #8
    //   2244: goto -> 2254
    //   2247: astore #14
    //   2249: aload #14
    //   2251: invokevirtual printStackTrace : ()V
    //   2254: aload_3
    //   2255: ldc 'message'
    //   2257: aload #8
    //   2259: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2264: aload_3
    //   2265: ldc 'newType'
    //   2267: aload #6
    //   2269: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2274: aload_3
    //   2275: ldc 'succeed'
    //   2277: aload #9
    //   2279: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2284: aload_1
    //   2285: ldc 'result'
    //   2287: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   2290: areturn
    //   2291: ldc_w '3'
    //   2294: aload #6
    //   2296: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2299: ifeq -> 3119
    //   2302: aload_3
    //   2303: ldc_w '/uploadtemplate/supervisetasktemplate.xls'
    //   2306: invokeinterface getRealPath : (Ljava/lang/String;)Ljava/lang/String;
    //   2311: astore #7
    //   2313: aload #5
    //   2315: invokevirtual getFile : ()Lorg/apache/struts/upload/FormFile;
    //   2318: aload #7
    //   2320: invokestatic uploadFile : (Lorg/apache/struts/upload/FormFile;Ljava/lang/String;)V
    //   2323: new java/io/FileInputStream
    //   2326: dup
    //   2327: new java/io/File
    //   2330: dup
    //   2331: aload #7
    //   2333: invokespecial <init> : (Ljava/lang/String;)V
    //   2336: invokespecial <init> : (Ljava/io/File;)V
    //   2339: astore #12
    //   2341: aload_0
    //   2342: aload #12
    //   2344: invokestatic getWorkbook : (Ljava/io/InputStream;)Ljxl/Workbook;
    //   2347: putfield workbook : Ljxl/Workbook;
    //   2350: goto -> 2418
    //   2353: astore #13
    //   2355: new java/lang/StringBuilder
    //   2358: dup
    //   2359: aload #8
    //   2361: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2364: invokespecial <init> : (Ljava/lang/String;)V
    //   2367: ldc '选择的模版不正确！<br>'
    //   2369: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2372: invokevirtual toString : ()Ljava/lang/String;
    //   2375: astore #8
    //   2377: ldc '1'
    //   2379: aload #9
    //   2381: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2384: ifne -> 2391
    //   2387: ldc '1'
    //   2389: astore #9
    //   2391: aload_3
    //   2392: ldc 'succeed'
    //   2394: aload #9
    //   2396: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2401: aload_3
    //   2402: ldc 'message'
    //   2404: aload #8
    //   2406: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   2411: aload_1
    //   2412: ldc 'input'
    //   2414: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   2417: areturn
    //   2418: aload_0
    //   2419: aload_0
    //   2420: getfield workbook : Ljxl/Workbook;
    //   2423: iconst_0
    //   2424: invokevirtual getSheet : (I)Ljxl/Sheet;
    //   2427: putfield sheet : Ljxl/Sheet;
    //   2430: aload_0
    //   2431: getfield sheet : Ljxl/Sheet;
    //   2434: ifnull -> 3082
    //   2437: aload_0
    //   2438: getfield sheet : Ljxl/Sheet;
    //   2441: invokeinterface getRows : ()I
    //   2446: istore #13
    //   2448: iconst_3
    //   2449: istore #14
    //   2451: goto -> 3025
    //   2454: new java/util/HashMap
    //   2457: dup
    //   2458: invokespecial <init> : ()V
    //   2461: astore #15
    //   2463: ldc '0'
    //   2465: astore #9
    //   2467: aload_0
    //   2468: getfield sheet : Ljxl/Sheet;
    //   2471: iconst_0
    //   2472: iload #14
    //   2474: invokeinterface getCell : (II)Ljxl/Cell;
    //   2479: invokeinterface getContents : ()Ljava/lang/String;
    //   2484: invokevirtual trim : ()Ljava/lang/String;
    //   2487: astore #16
    //   2489: aload_0
    //   2490: getfield sheet : Ljxl/Sheet;
    //   2493: iconst_1
    //   2494: iload #14
    //   2496: invokeinterface getCell : (II)Ljxl/Cell;
    //   2501: invokeinterface getContents : ()Ljava/lang/String;
    //   2506: invokevirtual trim : ()Ljava/lang/String;
    //   2509: astore #17
    //   2511: aload_0
    //   2512: getfield sheet : Ljxl/Sheet;
    //   2515: iconst_2
    //   2516: iload #14
    //   2518: invokeinterface getCell : (II)Ljxl/Cell;
    //   2523: invokeinterface getContents : ()Ljava/lang/String;
    //   2528: invokevirtual trim : ()Ljava/lang/String;
    //   2531: astore #18
    //   2533: aload_0
    //   2534: getfield sheet : Ljxl/Sheet;
    //   2537: iconst_3
    //   2538: iload #14
    //   2540: invokeinterface getCell : (II)Ljxl/Cell;
    //   2545: invokeinterface getContents : ()Ljava/lang/String;
    //   2550: invokevirtual trim : ()Ljava/lang/String;
    //   2553: astore #19
    //   2555: aload_0
    //   2556: getfield sheet : Ljxl/Sheet;
    //   2559: iconst_4
    //   2560: iload #14
    //   2562: invokeinterface getCell : (II)Ljxl/Cell;
    //   2567: invokeinterface getContents : ()Ljava/lang/String;
    //   2572: invokevirtual trim : ()Ljava/lang/String;
    //   2575: astore #20
    //   2577: aload_0
    //   2578: getfield sheet : Ljxl/Sheet;
    //   2581: iconst_5
    //   2582: iload #14
    //   2584: invokeinterface getCell : (II)Ljxl/Cell;
    //   2589: invokeinterface getContents : ()Ljava/lang/String;
    //   2594: invokevirtual trim : ()Ljava/lang/String;
    //   2597: astore #21
    //   2599: aload_0
    //   2600: getfield sheet : Ljxl/Sheet;
    //   2603: bipush #6
    //   2605: iload #14
    //   2607: invokeinterface getCell : (II)Ljxl/Cell;
    //   2612: invokeinterface getContents : ()Ljava/lang/String;
    //   2617: invokevirtual trim : ()Ljava/lang/String;
    //   2620: astore #22
    //   2622: aload_0
    //   2623: getfield sheet : Ljxl/Sheet;
    //   2626: bipush #6
    //   2628: iload #14
    //   2630: invokeinterface getCell : (II)Ljxl/Cell;
    //   2635: invokeinterface getContents : ()Ljava/lang/String;
    //   2640: invokevirtual trim : ()Ljava/lang/String;
    //   2643: astore #23
    //   2645: aload #15
    //   2647: ldc_w 'phdw'
    //   2650: aload #16
    //   2652: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2657: pop
    //   2658: ldc ''
    //   2660: aload #17
    //   2662: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2665: ifeq -> 2710
    //   2668: new java/lang/StringBuilder
    //   2671: dup
    //   2672: aload #8
    //   2674: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2677: invokespecial <init> : (Ljava/lang/String;)V
    //   2680: ldc '第'
    //   2682: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2685: iload #14
    //   2687: iconst_1
    //   2688: iadd
    //   2689: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2692: ldc_w '行【责任单位】为空！<br>'
    //   2695: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2698: invokevirtual toString : ()Ljava/lang/String;
    //   2701: astore #8
    //   2703: ldc '1'
    //   2705: astore #9
    //   2707: goto -> 3022
    //   2710: aload #15
    //   2712: ldc_w 'zrdw'
    //   2715: aload #17
    //   2717: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2722: pop
    //   2723: ldc ''
    //   2725: aload #18
    //   2727: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2730: ifeq -> 2775
    //   2733: new java/lang/StringBuilder
    //   2736: dup
    //   2737: aload #8
    //   2739: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2742: invokespecial <init> : (Ljava/lang/String;)V
    //   2745: ldc '第'
    //   2747: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2750: iload #14
    //   2752: iconst_1
    //   2753: iadd
    //   2754: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2757: ldc_w '行【督办事项】为空！<br>'
    //   2760: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2763: invokevirtual toString : ()Ljava/lang/String;
    //   2766: astore #8
    //   2768: ldc '1'
    //   2770: astore #9
    //   2772: goto -> 3022
    //   2775: aload #15
    //   2777: ldc_w 'dbsx'
    //   2780: aload #18
    //   2782: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2787: pop
    //   2788: ldc ''
    //   2790: aload #19
    //   2792: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2795: ifeq -> 2840
    //   2798: new java/lang/StringBuilder
    //   2801: dup
    //   2802: aload #8
    //   2804: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2807: invokespecial <init> : (Ljava/lang/String;)V
    //   2810: ldc '第'
    //   2812: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2815: iload #14
    //   2817: iconst_1
    //   2818: iadd
    //   2819: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2822: ldc_w '行【工作要求】为空！<br>'
    //   2825: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2828: invokevirtual toString : ()Ljava/lang/String;
    //   2831: astore #8
    //   2833: ldc '1'
    //   2835: astore #9
    //   2837: goto -> 3022
    //   2840: aload #15
    //   2842: ldc_w 'gzyq'
    //   2845: aload #19
    //   2847: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2852: pop
    //   2853: aload #15
    //   2855: ldc_w 'wcsx'
    //   2858: aload #20
    //   2860: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2865: pop
    //   2866: aload #15
    //   2868: ldc_w 'lsqk'
    //   2871: aload #21
    //   2873: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2878: pop
    //   2879: ldc ''
    //   2881: aload #22
    //   2883: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2886: ifeq -> 2905
    //   2889: aload #15
    //   2891: ldc_w 'bljg'
    //   2894: ldc ''
    //   2896: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2901: pop
    //   2902: goto -> 2999
    //   2905: ldc '完成'
    //   2907: aload #22
    //   2909: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2912: ifeq -> 2931
    //   2915: aload #15
    //   2917: ldc_w 'bljg'
    //   2920: ldc '完成'
    //   2922: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2927: pop
    //   2928: goto -> 2999
    //   2931: ldc '未完成'
    //   2933: aload #22
    //   2935: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2938: ifeq -> 2957
    //   2941: aload #15
    //   2943: ldc_w 'bljg'
    //   2946: ldc '未完成'
    //   2948: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   2953: pop
    //   2954: goto -> 2999
    //   2957: new java/lang/StringBuilder
    //   2960: dup
    //   2961: aload #8
    //   2963: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   2966: invokespecial <init> : (Ljava/lang/String;)V
    //   2969: ldc '第'
    //   2971: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2974: iload #14
    //   2976: iconst_1
    //   2977: iadd
    //   2978: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2981: ldc_w '行【办理结果】填写错误！<br>'
    //   2984: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2987: invokevirtual toString : ()Ljava/lang/String;
    //   2990: astore #8
    //   2992: ldc '1'
    //   2994: astore #9
    //   2996: goto -> 3022
    //   2999: aload #15
    //   3001: ldc_w 'sx'
    //   3004: aload #23
    //   3006: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   3011: pop
    //   3012: aload #11
    //   3014: aload #15
    //   3016: invokeinterface add : (Ljava/lang/Object;)Z
    //   3021: pop
    //   3022: iinc #14, 1
    //   3025: iload #14
    //   3027: iload #13
    //   3029: if_icmplt -> 2454
    //   3032: aload #11
    //   3034: invokeinterface size : ()I
    //   3039: ifle -> 3082
    //   3042: new java/lang/StringBuilder
    //   3045: dup
    //   3046: aload #8
    //   3048: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   3051: invokespecial <init> : (Ljava/lang/String;)V
    //   3054: aload #11
    //   3056: ldc_w '3'
    //   3059: aload #10
    //   3061: invokestatic dataCombination : (Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   3064: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3067: invokevirtual toString : ()Ljava/lang/String;
    //   3070: astore #8
    //   3072: goto -> 3082
    //   3075: astore #14
    //   3077: aload #14
    //   3079: invokevirtual printStackTrace : ()V
    //   3082: aload_3
    //   3083: ldc 'message'
    //   3085: aload #8
    //   3087: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3092: aload_3
    //   3093: ldc 'newType'
    //   3095: aload #6
    //   3097: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3102: aload_3
    //   3103: ldc 'succeed'
    //   3105: aload #9
    //   3107: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3112: aload_1
    //   3113: ldc 'result'
    //   3115: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   3118: areturn
    //   3119: ldc '0'
    //   3121: aload #9
    //   3123: invokevirtual equals : (Ljava/lang/Object;)Z
    //   3126: ifeq -> 3134
    //   3129: ldc_w '导入成功！'
    //   3132: astore #8
    //   3134: new java/io/File
    //   3137: dup
    //   3138: aload #7
    //   3140: invokespecial <init> : (Ljava/lang/String;)V
    //   3143: astore #12
    //   3145: aload #12
    //   3147: invokevirtual exists : ()Z
    //   3150: ifeq -> 3159
    //   3153: aload #12
    //   3155: invokevirtual delete : ()Z
    //   3158: pop
    //   3159: aload_3
    //   3160: ldc 'message'
    //   3162: aload #8
    //   3164: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3169: aload_3
    //   3170: ldc 'newType'
    //   3172: aload #6
    //   3174: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3179: aload_3
    //   3180: ldc 'succeed'
    //   3182: aload #9
    //   3184: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/Object;)V
    //   3189: aload_1
    //   3190: ldc 'input'
    //   3192: invokevirtual findForward : (Ljava/lang/String;)Lorg/apache/struts/action/ActionForward;
    //   3195: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #43	-> 0
    //   #44	-> 6
    //   #45	-> 16
    //   #46	-> 26
    //   #47	-> 30
    //   #48	-> 34
    //   #49	-> 38
    //   #50	-> 56
    //   #51	-> 65
    //   #52	-> 75
    //   #53	-> 85
    //   #54	-> 95
    //   #56	-> 113
    //   #57	-> 125
    //   #58	-> 127
    //   #59	-> 149
    //   #60	-> 159
    //   #62	-> 163
    //   #63	-> 173
    //   #64	-> 183
    //   #66	-> 190
    //   #68	-> 202
    //   #69	-> 209
    //   #71	-> 220
    //   #72	-> 226
    //   #73	-> 235
    //   #74	-> 239
    //   #75	-> 261
    //   #76	-> 283
    //   #77	-> 305
    //   #78	-> 327
    //   #79	-> 349
    //   #80	-> 371
    //   #82	-> 394
    //   #83	-> 404
    //   #84	-> 438
    //   #85	-> 442
    //   #87	-> 445
    //   #88	-> 458
    //   #89	-> 468
    //   #90	-> 502
    //   #91	-> 506
    //   #92	-> 509
    //   #93	-> 519
    //   #94	-> 553
    //   #95	-> 557
    //   #96	-> 560
    //   #97	-> 570
    //   #98	-> 604
    //   #99	-> 608
    //   #101	-> 611
    //   #104	-> 623
    //   #105	-> 633
    //   #108	-> 648
    //   #109	-> 658
    //   #110	-> 686
    //   #111	-> 688
    //   #112	-> 693
    //   #113	-> 727
    //   #114	-> 731
    //   #117	-> 734
    //   #118	-> 744
    //   #119	-> 778
    //   #120	-> 782
    //   #122	-> 785
    //   #124	-> 797
    //   #125	-> 807
    //   #126	-> 841
    //   #127	-> 845
    //   #129	-> 848
    //   #131	-> 860
    //   #132	-> 870
    //   #133	-> 904
    //   #134	-> 908
    //   #136	-> 911
    //   #138	-> 923
    //   #139	-> 933
    //   #141	-> 948
    //   #142	-> 958
    //   #143	-> 973
    //   #144	-> 983
    //   #145	-> 998
    //   #146	-> 1008
    //   #147	-> 1023
    //   #148	-> 1033
    //   #150	-> 1048
    //   #151	-> 1082
    //   #152	-> 1086
    //   #155	-> 1089
    //   #156	-> 1101
    //   #71	-> 1111
    //   #158	-> 1121
    //   #160	-> 1131
    //   #162	-> 1163
    //   #163	-> 1165
    //   #167	-> 1170
    //   #168	-> 1180
    //   #169	-> 1190
    //   #170	-> 1200
    //   #172	-> 1207
    //   #174	-> 1217
    //   #175	-> 1227
    //   #176	-> 1237
    //   #178	-> 1255
    //   #179	-> 1267
    //   #180	-> 1269
    //   #181	-> 1291
    //   #182	-> 1301
    //   #184	-> 1305
    //   #185	-> 1315
    //   #186	-> 1325
    //   #188	-> 1332
    //   #190	-> 1344
    //   #191	-> 1351
    //   #193	-> 1362
    //   #194	-> 1368
    //   #195	-> 1377
    //   #196	-> 1381
    //   #197	-> 1403
    //   #198	-> 1425
    //   #199	-> 1447
    //   #200	-> 1469
    //   #201	-> 1491
    //   #202	-> 1513
    //   #204	-> 1536
    //   #205	-> 1546
    //   #206	-> 1580
    //   #207	-> 1584
    //   #209	-> 1587
    //   #210	-> 1599
    //   #212	-> 1617
    //   #213	-> 1630
    //   #214	-> 1640
    //   #215	-> 1674
    //   #216	-> 1678
    //   #217	-> 1681
    //   #218	-> 1691
    //   #219	-> 1725
    //   #220	-> 1729
    //   #221	-> 1732
    //   #222	-> 1742
    //   #223	-> 1776
    //   #224	-> 1780
    //   #226	-> 1783
    //   #231	-> 1795
    //   #232	-> 1805
    //   #233	-> 1839
    //   #234	-> 1843
    //   #236	-> 1846
    //   #238	-> 1859
    //   #239	-> 1869
    //   #240	-> 1904
    //   #241	-> 1908
    //   #243	-> 1911
    //   #245	-> 1924
    //   #246	-> 1934
    //   #247	-> 1968
    //   #248	-> 1972
    //   #250	-> 1975
    //   #252	-> 1987
    //   #253	-> 2000
    //   #254	-> 2013
    //   #255	-> 2023
    //   #257	-> 2039
    //   #258	-> 2049
    //   #259	-> 2065
    //   #260	-> 2075
    //   #261	-> 2091
    //   #262	-> 2101
    //   #263	-> 2117
    //   #264	-> 2127
    //   #266	-> 2143
    //   #267	-> 2178
    //   #268	-> 2182
    //   #272	-> 2185
    //   #193	-> 2195
    //   #274	-> 2205
    //   #276	-> 2215
    //   #278	-> 2247
    //   #279	-> 2249
    //   #283	-> 2254
    //   #284	-> 2264
    //   #285	-> 2274
    //   #286	-> 2284
    //   #289	-> 2291
    //   #291	-> 2302
    //   #292	-> 2313
    //   #293	-> 2323
    //   #295	-> 2341
    //   #296	-> 2353
    //   #297	-> 2355
    //   #298	-> 2377
    //   #299	-> 2387
    //   #301	-> 2391
    //   #302	-> 2401
    //   #303	-> 2411
    //   #305	-> 2418
    //   #307	-> 2430
    //   #308	-> 2437
    //   #310	-> 2448
    //   #311	-> 2454
    //   #312	-> 2463
    //   #313	-> 2467
    //   #314	-> 2489
    //   #315	-> 2511
    //   #316	-> 2533
    //   #317	-> 2555
    //   #318	-> 2577
    //   #319	-> 2599
    //   #320	-> 2622
    //   #322	-> 2645
    //   #323	-> 2658
    //   #324	-> 2668
    //   #325	-> 2703
    //   #326	-> 2707
    //   #328	-> 2710
    //   #330	-> 2723
    //   #331	-> 2733
    //   #332	-> 2768
    //   #333	-> 2772
    //   #335	-> 2775
    //   #337	-> 2788
    //   #338	-> 2798
    //   #339	-> 2833
    //   #340	-> 2837
    //   #342	-> 2840
    //   #344	-> 2853
    //   #345	-> 2866
    //   #346	-> 2879
    //   #347	-> 2889
    //   #349	-> 2905
    //   #350	-> 2915
    //   #351	-> 2931
    //   #352	-> 2941
    //   #354	-> 2957
    //   #355	-> 2992
    //   #356	-> 2996
    //   #359	-> 2999
    //   #360	-> 3012
    //   #310	-> 3022
    //   #362	-> 3032
    //   #364	-> 3042
    //   #366	-> 3075
    //   #367	-> 3077
    //   #371	-> 3082
    //   #372	-> 3092
    //   #373	-> 3102
    //   #374	-> 3112
    //   #378	-> 3119
    //   #379	-> 3129
    //   #381	-> 3134
    //   #382	-> 3145
    //   #383	-> 3153
    //   #385	-> 3159
    //   #386	-> 3169
    //   #387	-> 3179
    //   #388	-> 3189
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	3196	0	this	Lcom/js/ldap/supervise/action/SuperviseAction;
    //   0	3196	1	actionMapping	Lorg/apache/struts/action/ActionMapping;
    //   0	3196	2	actionForm	Lorg/apache/struts/action/ActionForm;
    //   0	3196	3	httpServletRequest	Ljavax/servlet/http/HttpServletRequest;
    //   0	3196	4	httpServletResponse	Ljavax/servlet/http/HttpServletResponse;
    //   6	3190	5	uploadForm	Lcom/js/ldap/supervise/action/SuperviseForm;
    //   16	3180	6	type	Ljava/lang/String;
    //   30	3166	7	realPath	Ljava/lang/String;
    //   34	3162	8	message	Ljava/lang/String;
    //   38	3158	9	succeed	Ljava/lang/String;
    //   56	3140	10	userAccount	Ljava/lang/String;
    //   65	3131	11	resultList	Ljava/util/List;
    //   113	1094	12	file	Ljava/io/FileInputStream;
    //   127	63	13	e	Ljava/lang/Exception;
    //   220	950	13	rows	I
    //   223	898	14	i	I
    //   235	876	15	infoMap	Ljava/util/Map;
    //   261	850	16	dbyf	Ljava/lang/String;
    //   283	828	17	xh	Ljava/lang/String;
    //   305	806	18	gzrw	Ljava/lang/String;
    //   327	784	19	zrld	Ljava/lang/String;
    //   349	762	20	zrbm	Ljava/lang/String;
    //   371	740	21	wcqk	Ljava/lang/String;
    //   394	717	22	jysm	Ljava/lang/String;
    //   458	165	23	res	Ljava/lang/String;
    //   658	28	23	xhInt	I
    //   688	46	23	e	Ljava/lang/Exception;
    //   1165	5	14	ex	Ljava/lang/Exception;
    //   1255	1036	12	file	Ljava/io/FileInputStream;
    //   1269	63	13	e	Ljava/lang/Exception;
    //   1362	892	13	rows	I
    //   1365	840	14	i	I
    //   1377	818	15	infoMap	Ljava/util/Map;
    //   1403	792	16	dbnf	Ljava/lang/String;
    //   1425	770	17	fzld	Ljava/lang/String;
    //   1447	748	18	zrdw	Ljava/lang/String;
    //   1469	726	19	gzrw	Ljava/lang/String;
    //   1491	704	20	phdw	Ljava/lang/String;
    //   1513	682	21	wcsj	Ljava/lang/String;
    //   1536	659	22	jzqk	Ljava/lang/String;
    //   1630	165	23	res	Ljava/lang/String;
    //   2249	5	14	ex	Ljava/lang/Exception;
    //   2341	778	12	file	Ljava/io/FileInputStream;
    //   2355	63	13	e	Ljava/lang/Exception;
    //   2448	634	13	rows	I
    //   2451	581	14	i	I
    //   2463	559	15	infoMap	Ljava/util/Map;
    //   2489	533	16	phdw	Ljava/lang/String;
    //   2511	511	17	zrdw	Ljava/lang/String;
    //   2533	489	18	dbsx	Ljava/lang/String;
    //   2555	467	19	gzyq	Ljava/lang/String;
    //   2577	445	20	wcsx	Ljava/lang/String;
    //   2599	423	21	lsqk	Ljava/lang/String;
    //   2622	400	22	bljg	Ljava/lang/String;
    //   2645	377	23	sx	Ljava/lang/String;
    //   3077	5	14	ex	Ljava/lang/Exception;
    //   3145	51	12	file	Ljava/io/File;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   65	3131	11	resultList	Ljava/util/List<Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;>;
    //   235	876	15	infoMap	Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;
    //   1377	818	15	infoMap	Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;
    //   2463	559	15	infoMap	Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;
    // Exception table:
    //   from	to	target	type
    //   113	122	125	java/lang/Exception
    //   220	1160	1163	java/lang/Exception
    //   648	683	686	java/lang/Exception
    //   1255	1264	1267	java/lang/Exception
    //   1362	2244	2247	java/lang/Exception
    //   2341	2350	2353	java/lang/Exception
    //   2448	3072	3075	java/lang/Exception
  }
  
  public static void uploadFile(FormFile file, String dir) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new BufferedInputStream(file.getInputStream());
      File file1 = new File(dir);
      if (file1.exists())
        file1.delete(); 
      out = new BufferedOutputStream(new FileOutputStream(file1));
      byte[] buffered = new byte[8192];
      int size = 0;
      while ((size = in.read(buffered, 0, 8192)) != -1)
        out.write(buffered, 0, size); 
      out.flush();
      in.close();
      out.close();
    } catch (Exception e) {
      in.close();
      out.close();
    } 
  }
  
  private String formatDate(String from, String type) {
    String to = "";
    this.sdf = null;
    if ("1".equals(type)) {
      this.sdf = new SimpleDateFormat("yyyy-MM");
    } else if ("2".equals(type)) {
      this.sdf = new SimpleDateFormat("yyyy");
    } 
    if (this.sdf == null) {
      to = "0";
    } else {
      this.format = null;
      try {
        if (from.indexOf("年") > -1) {
          this.format = new SimpleDateFormat("yyyy年MM月");
        } else if (from.indexOf("/") > -1) {
          this.format = new SimpleDateFormat("yyyy/MM");
        } else if (from.indexOf("-") > -1) {
          this.format = new SimpleDateFormat("yyyy-MM");
        } 
        if (this.format == null) {
          to = "1";
        } else {
          to = this.sdf.format(this.format.parse(from));
        } 
      } catch (Exception e) {
        to = "2";
      } 
    } 
    return to;
  }
}
