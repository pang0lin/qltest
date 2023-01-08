package rtx.rtxsms.tempuri;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsIDListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsRecvFileGroupHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsReportListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsIDListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsRecvListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsReportListHolder;

public class MobsetApiSoapStub extends Stub implements MobsetApiSoap {
  private Vector cachedSerClasses = new Vector();
  
  private Vector cachedSerQNames = new Vector();
  
  private Vector cachedSerFactories = new Vector();
  
  private Vector cachedDeserFactories = new Vector();
  
  static OperationDesc[] _operations = new OperationDesc[20];
  
  static {
    _initOperationDesc1();
    _initOperationDesc2();
  }
  
  private static void _initOperationDesc1() {
    OperationDesc oper = new OperationDesc();
    oper.setName("Sms_Send");
    ParameterDesc param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "AddNum"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Timer"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LongSms"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MobileList"), (byte)1, new QName("http://tempuri.org/", "ArrayOfMobileList"), MobileListGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MobileListGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Content"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Count"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "SmsIDList"), (byte)2, new QName("http://tempuri.org/", "ArrayOfSmsIDList"), SmsIDGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "SmsIDGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[0] = oper;
    oper = new OperationDesc();
    oper.setName("Sms_GetRecv");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Count"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "SmsRecvList"), (byte)2, new QName("http://tempuri.org/", "ArrayOfSmsRecvList"), SmsRecvGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "SmsRecvGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[1] = oper;
    oper = new OperationDesc();
    oper.setName("Sms_GetReport");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Count"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "SmsReportList"), (byte)2, new QName("http://tempuri.org/", "ArrayOfSmsReportList"), SmsReportGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "SmsReportGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[2] = oper;
    oper = new OperationDesc();
    oper.setName("Sms_GetSign");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrCode"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Sign"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[3] = oper;
    oper = new OperationDesc();
    oper.setName("Sms_GetBalance");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Balance"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[4] = oper;
    oper = new OperationDesc();
    oper.setName("Mms_UpFile");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Subject"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "SmilType"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsFileList"), (byte)1, new QName("http://tempuri.org/", "ArrayOfMmsFileGroup"), MmsFileGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MmsFileGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsFileID"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[5] = oper;
    oper = new OperationDesc();
    oper.setName("Mms_GetFileStatus");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsFileID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Status"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Title"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Size"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "CreateTime"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[6] = oper;
    oper = new OperationDesc();
    oper.setName("Mms_Send");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "AddNum"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Timer"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MobileList"), (byte)1, new QName("http://tempuri.org/", "ArrayOfMobileList"), MobileListGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MobileListGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsFileID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Count"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsIDList"), (byte)2, new QName("http://tempuri.org/", "ArrayOfMmsIDList"), MmsIDGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MmsIDGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[7] = oper;
    oper = new OperationDesc();
    oper.setName("Mms_GetReport");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Count"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsReportList"), (byte)2, new QName("http://tempuri.org/", "ArrayOfMmsReportList"), MmsReportGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MmsReportGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[8] = oper;
    oper = new OperationDesc();
    oper.setName("Mms_GetRecv");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Count"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Mobile"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "RecvNum"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "AddNum"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Subject"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "RecvTime"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsRecvFileList"), (byte)2, new QName("http://tempuri.org/", "ArrayOfMmsRecvFileGroup"), MmsRecvFileGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MmsRecvFileGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[9] = oper;
  }
  
  private static void _initOperationDesc2() {
    OperationDesc oper = new OperationDesc();
    oper.setName("Task_UpFile");
    ParameterDesc param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Subject"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "AutoDelete"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "FileData"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), byte[].class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskFileID"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[10] = oper;
    oper = new OperationDesc();
    oper.setName("Task_DelFile");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskFileID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrCode"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[11] = oper;
    oper = new OperationDesc();
    oper.setName("Task_SmsSend");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Content"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LongSms"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Priority"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "AtTime"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MobileList"), (byte)1, new QName("http://tempuri.org/", "ArrayOfMobileFileGroup"), MobileFileGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MobileFileGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskSmsID"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[12] = oper;
    oper = new OperationDesc();
    oper.setName("Task_GetSmsStatus");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskSmsID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Status"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MobileCount"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "YFMobileCount"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "BeginTime"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "EndTime"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[13] = oper;
    oper = new OperationDesc();
    oper.setName("Task_SmsStop");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskSmsID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrCode"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[14] = oper;
    oper = new OperationDesc();
    oper.setName("Task_SmsStart");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskSmsID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrCode"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[15] = oper;
    oper = new OperationDesc();
    oper.setName("Task_MmsSend");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MmsFileID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Priority"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "AtTime"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MobileList"), (byte)1, new QName("http://tempuri.org/", "ArrayOfMobileFileGroup"), MobileFileGroup[].class, false, false);
    param.setItemQName(new QName("http://tempuri.org/", "MobileFileGroup"));
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskMmsID"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[16] = oper;
    oper = new OperationDesc();
    oper.setName("Task_GetMmsStatus");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskMmsID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Status"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "MobileCount"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "YFMobileCount"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "BeginTime"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "EndTime"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[17] = oper;
    oper = new OperationDesc();
    oper.setName("Task_MmsStop");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskMmsID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrCode"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[18] = oper;
    oper = new OperationDesc();
    oper.setName("Task_MmsStart");
    param = new ParameterDesc(new QName("http://tempuri.org/", "CorpID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "LoginName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "Password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TimeStamp"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "TaskMmsID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrCode"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
    oper.addParameter(param);
    param = new ParameterDesc(new QName("http://tempuri.org/", "ErrMsg"), (byte)2, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
    param.setOmittable(true);
    oper.addParameter(param);
    oper.setReturnType(XMLType.AXIS_VOID);
    oper.setStyle(Style.WRAPPED);
    oper.setUse(Use.LITERAL);
    _operations[19] = oper;
  }
  
  public MobsetApiSoapStub() throws AxisFault {
    this((Service)null);
  }
  
  public MobsetApiSoapStub(URL endpointURL, Service service) throws AxisFault {
    this(service);
    this.cachedEndpoint = endpointURL;
  }
  
  public MobsetApiSoapStub(Service service) throws AxisFault {
    if (service == null) {
      this.service = (Service)new Service();
    } else {
      this.service = service;
    } 
    ((Service)this.service).setTypeMappingVersion("1.2");
    Class<BeanSerializerFactory> beansf = BeanSerializerFactory.class;
    Class<BeanDeserializerFactory> beandf = BeanDeserializerFactory.class;
    Class<EnumSerializerFactory> enumsf = EnumSerializerFactory.class;
    Class<EnumDeserializerFactory> enumdf = EnumDeserializerFactory.class;
    Class<ArraySerializerFactory> arraysf = ArraySerializerFactory.class;
    Class<ArrayDeserializerFactory> arraydf = ArrayDeserializerFactory.class;
    Class<SimpleSerializerFactory> simplesf = SimpleSerializerFactory.class;
    Class<SimpleDeserializerFactory> simpledf = SimpleDeserializerFactory.class;
    Class<SimpleListSerializerFactory> simplelistsf = SimpleListSerializerFactory.class;
    Class<SimpleListDeserializerFactory> simplelistdf = SimpleListDeserializerFactory.class;
    QName qName = new QName("http://tempuri.org/", "ArrayOfMmsFileGroup");
    this.cachedSerQNames.add(qName);
    Class<MmsFileGroup[]> cls = MmsFileGroup[].class;
    this.cachedSerClasses.add(cls);
    qName = new QName("http://tempuri.org/", "MmsFileGroup");
    QName qName2 = new QName("http://tempuri.org/", "MmsFileGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfMmsIDList");
    this.cachedSerQNames.add(qName);
    Class<MmsIDGroup[]> clazz16 = MmsIDGroup[].class;
    this.cachedSerClasses.add(clazz16);
    qName = new QName("http://tempuri.org/", "MmsIDGroup");
    qName2 = new QName("http://tempuri.org/", "MmsIDGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfMmsRecvFileGroup");
    this.cachedSerQNames.add(qName);
    Class<MmsRecvFileGroup[]> clazz15 = MmsRecvFileGroup[].class;
    this.cachedSerClasses.add(clazz15);
    qName = new QName("http://tempuri.org/", "MmsRecvFileGroup");
    qName2 = new QName("http://tempuri.org/", "MmsRecvFileGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfMmsReportList");
    this.cachedSerQNames.add(qName);
    Class<MmsReportGroup[]> clazz14 = MmsReportGroup[].class;
    this.cachedSerClasses.add(clazz14);
    qName = new QName("http://tempuri.org/", "MmsReportGroup");
    qName2 = new QName("http://tempuri.org/", "MmsReportGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfMobileFileGroup");
    this.cachedSerQNames.add(qName);
    Class<MobileFileGroup[]> clazz13 = MobileFileGroup[].class;
    this.cachedSerClasses.add(clazz13);
    qName = new QName("http://tempuri.org/", "MobileFileGroup");
    qName2 = new QName("http://tempuri.org/", "MobileFileGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfMobileList");
    this.cachedSerQNames.add(qName);
    Class<MobileListGroup[]> clazz12 = MobileListGroup[].class;
    this.cachedSerClasses.add(clazz12);
    qName = new QName("http://tempuri.org/", "MobileListGroup");
    qName2 = new QName("http://tempuri.org/", "MobileListGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfSmsIDList");
    this.cachedSerQNames.add(qName);
    Class<SmsIDGroup[]> clazz11 = SmsIDGroup[].class;
    this.cachedSerClasses.add(clazz11);
    qName = new QName("http://tempuri.org/", "SmsIDGroup");
    qName2 = new QName("http://tempuri.org/", "SmsIDGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfSmsRecvList");
    this.cachedSerQNames.add(qName);
    Class<SmsRecvGroup[]> clazz10 = SmsRecvGroup[].class;
    this.cachedSerClasses.add(clazz10);
    qName = new QName("http://tempuri.org/", "SmsRecvGroup");
    qName2 = new QName("http://tempuri.org/", "SmsRecvGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "ArrayOfSmsReportList");
    this.cachedSerQNames.add(qName);
    Class<SmsReportGroup[]> clazz9 = SmsReportGroup[].class;
    this.cachedSerClasses.add(clazz9);
    qName = new QName("http://tempuri.org/", "SmsReportGroup");
    qName2 = new QName("http://tempuri.org/", "SmsReportGroup");
    this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
    this.cachedDeserFactories.add(new ArrayDeserializerFactory());
    qName = new QName("http://tempuri.org/", "MmsFileGroup");
    this.cachedSerQNames.add(qName);
    Class<MmsFileGroup> clazz8 = MmsFileGroup.class;
    this.cachedSerClasses.add(clazz8);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "MmsIDGroup");
    this.cachedSerQNames.add(qName);
    Class<MmsIDGroup> clazz7 = MmsIDGroup.class;
    this.cachedSerClasses.add(clazz7);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "MmsRecvFileGroup");
    this.cachedSerQNames.add(qName);
    Class<MmsRecvFileGroup> clazz6 = MmsRecvFileGroup.class;
    this.cachedSerClasses.add(clazz6);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "MmsReportGroup");
    this.cachedSerQNames.add(qName);
    Class<MmsReportGroup> clazz5 = MmsReportGroup.class;
    this.cachedSerClasses.add(clazz5);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "MobileFileGroup");
    this.cachedSerQNames.add(qName);
    Class<MobileFileGroup> clazz4 = MobileFileGroup.class;
    this.cachedSerClasses.add(clazz4);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "MobileListGroup");
    this.cachedSerQNames.add(qName);
    Class<MobileListGroup> clazz3 = MobileListGroup.class;
    this.cachedSerClasses.add(clazz3);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "SmsIDGroup");
    this.cachedSerQNames.add(qName);
    Class<SmsIDGroup> clazz2 = SmsIDGroup.class;
    this.cachedSerClasses.add(clazz2);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "SmsRecvGroup");
    this.cachedSerQNames.add(qName);
    Class<SmsRecvGroup> clazz1 = SmsRecvGroup.class;
    this.cachedSerClasses.add(clazz1);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
    qName = new QName("http://tempuri.org/", "SmsReportGroup");
    this.cachedSerQNames.add(qName);
    Class<SmsReportGroup> clazz = SmsReportGroup.class;
    this.cachedSerClasses.add(clazz);
    this.cachedSerFactories.add(beansf);
    this.cachedDeserFactories.add(beandf);
  }
  
  protected Call createCall() throws RemoteException {
    try {
      Call _call = _createCall();
      if (this.maintainSessionSet)
        _call.setMaintainSession(this.maintainSession); 
      if (this.cachedUsername != null)
        _call.setUsername(this.cachedUsername); 
      if (this.cachedPassword != null)
        _call.setPassword(this.cachedPassword); 
      if (this.cachedEndpoint != null)
        _call.setTargetEndpointAddress(this.cachedEndpoint); 
      if (this.cachedTimeout != null)
        _call.setTimeout(this.cachedTimeout); 
      if (this.cachedPortName != null)
        _call.setPortName(this.cachedPortName); 
      Enumeration<Object> keys = this.cachedProperties.keys();
      while (keys.hasMoreElements()) {
        String key = (String)keys.nextElement();
        _call.setProperty(key, this.cachedProperties.get(key));
      } 
      synchronized (this) {
        if (firstCall()) {
          _call.setEncodingStyle(null);
          for (int i = 0; i < this.cachedSerFactories.size(); i++) {
            Class cls = this.cachedSerClasses.get(i);
            QName qName = 
              this.cachedSerQNames.get(i);
            Object x = this.cachedSerFactories.get(i);
            if (x instanceof Class) {
              Class sf = 
                this.cachedSerFactories.get(i);
              Class df = 
                this.cachedDeserFactories.get(i);
              _call.registerTypeMapping(cls, qName, sf, df, false);
            } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
              SerializerFactory sf = 
                this.cachedSerFactories.get(i);
              DeserializerFactory df = 
                this.cachedDeserFactories.get(i);
              _call.registerTypeMapping(cls, qName, sf, df, false);
            } 
          } 
        } 
      } 
      return _call;
    } catch (Throwable _t) {
      throw new AxisFault("Failure trying to get the Call object", _t);
    } 
  }
  
  public void sms_Send(long corpID, String loginName, String password, String timeStamp, String addNum, String timer, long longSms, MobileListGroup[] mobileList, String content, LongHolder count, StringHolder errMsg, ArrayOfSmsIDListHolder smsIDList) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[0]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Sms_Send");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Sms_Send"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, addNum, timer, new Long(longSms), mobileList, content });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        count.value = ((Long)_output.get(new QName("http://tempuri.org/", "Count"))).longValue();
      } catch (Exception _exception) {
        count.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Count")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        smsIDList.value = (SmsIDGroup[])_output.get(new QName("http://tempuri.org/", "SmsIDList"));
      } catch (Exception _exception) {
        smsIDList.value = (SmsIDGroup[])JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "SmsIDList")), SmsIDGroup[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void sms_GetRecv(long corpID, String loginName, String password, String timeStamp, LongHolder count, StringHolder errMsg, ArrayOfSmsRecvListHolder smsRecvList) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[1]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Sms_GetRecv");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Sms_GetRecv"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        count.value = ((Long)_output.get(new QName("http://tempuri.org/", "Count"))).longValue();
      } catch (Exception _exception) {
        count.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Count")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        smsRecvList.value = (SmsRecvGroup[])_output.get(new QName("http://tempuri.org/", "SmsRecvList"));
      } catch (Exception _exception) {
        smsRecvList.value = (SmsRecvGroup[])JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "SmsRecvList")), SmsRecvGroup[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void sms_GetReport(long corpID, String loginName, String password, String timeStamp, LongHolder count, StringHolder errMsg, ArrayOfSmsReportListHolder smsReportList) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[2]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Sms_GetReport");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Sms_GetReport"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        count.value = ((Long)_output.get(new QName("http://tempuri.org/", "Count"))).longValue();
      } catch (Exception _exception) {
        count.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Count")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        smsReportList.value = (SmsReportGroup[])_output.get(new QName("http://tempuri.org/", "SmsReportList"));
      } catch (Exception _exception) {
        smsReportList.value = (SmsReportGroup[])JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "SmsReportList")), SmsReportGroup[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void sms_GetSign(long corpID, String loginName, String password, String timeStamp, LongHolder errCode, StringHolder errMsg, StringHolder sign) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[3]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Sms_GetSign");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Sms_GetSign"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        errCode.value = ((Long)_output.get(new QName("http://tempuri.org/", "ErrCode"))).longValue();
      } catch (Exception _exception) {
        errCode.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrCode")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        sign.value = (String)_output.get(new QName("http://tempuri.org/", "Sign"));
      } catch (Exception _exception) {
        sign.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Sign")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void sms_GetBalance(long corpID, String loginName, String password, String timeStamp, LongHolder balance, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[4]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Sms_GetBalance");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Sms_GetBalance"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        balance.value = ((Long)_output.get(new QName("http://tempuri.org/", "Balance"))).longValue();
      } catch (Exception _exception) {
        balance.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Balance")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void mms_UpFile(long corpID, String loginName, String password, String timeStamp, String subject, long smilType, MmsFileGroup[] mmsFileList, LongHolder mmsFileID, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[5]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Mms_UpFile");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Mms_UpFile"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, subject, new Long(smilType), mmsFileList });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        mmsFileID.value = ((Long)_output.get(new QName("http://tempuri.org/", "MmsFileID"))).longValue();
      } catch (Exception _exception) {
        mmsFileID.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "MmsFileID")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void mms_GetFileStatus(long corpID, String loginName, String password, String timeStamp, long mmsFileID, LongHolder status, StringHolder errMsg, StringHolder title, LongHolder size, StringHolder createTime) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[6]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Mms_GetFileStatus");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Mms_GetFileStatus"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(mmsFileID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        status.value = ((Long)_output.get(new QName("http://tempuri.org/", "Status"))).longValue();
      } catch (Exception _exception) {
        status.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Status")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        title.value = (String)_output.get(new QName("http://tempuri.org/", "Title"));
      } catch (Exception _exception) {
        title.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Title")), String.class);
      } 
      try {
        size.value = ((Long)_output.get(new QName("http://tempuri.org/", "Size"))).longValue();
      } catch (Exception _exception) {
        size.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Size")), long.class)).longValue();
      } 
      try {
        createTime.value = (String)_output.get(new QName("http://tempuri.org/", "CreateTime"));
      } catch (Exception _exception) {
        createTime.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "CreateTime")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void mms_Send(long corpID, String loginName, String password, String timeStamp, String addNum, String timer, MobileListGroup[] mobileList, long mmsFileID, LongHolder count, StringHolder errMsg, ArrayOfMmsIDListHolder mmsIDList) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[7]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Mms_Send");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Mms_Send"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, addNum, timer, mobileList, new Long(mmsFileID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        count.value = ((Long)_output.get(new QName("http://tempuri.org/", "Count"))).longValue();
      } catch (Exception _exception) {
        count.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Count")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        mmsIDList.value = (MmsIDGroup[])_output.get(new QName("http://tempuri.org/", "MmsIDList"));
      } catch (Exception _exception) {
        mmsIDList.value = (MmsIDGroup[])JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "MmsIDList")), MmsIDGroup[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void mms_GetReport(long corpID, String loginName, String password, String timeStamp, LongHolder count, StringHolder errMsg, ArrayOfMmsReportListHolder mmsReportList) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[8]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Mms_GetReport");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Mms_GetReport"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        count.value = ((Long)_output.get(new QName("http://tempuri.org/", "Count"))).longValue();
      } catch (Exception _exception) {
        count.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Count")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        mmsReportList.value = (MmsReportGroup[])_output.get(new QName("http://tempuri.org/", "MmsReportList"));
      } catch (Exception _exception) {
        mmsReportList.value = (MmsReportGroup[])JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "MmsReportList")), MmsReportGroup[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void mms_GetRecv(long corpID, String loginName, String password, String timeStamp, LongHolder count, StringHolder errMsg, StringHolder mobile, StringHolder recvNum, StringHolder addNum, StringHolder subject, StringHolder recvTime, ArrayOfMmsRecvFileGroupHolder mmsRecvFileList) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[9]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Mms_GetRecv");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Mms_GetRecv"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        count.value = ((Long)_output.get(new QName("http://tempuri.org/", "Count"))).longValue();
      } catch (Exception _exception) {
        count.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Count")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        mobile.value = (String)_output.get(new QName("http://tempuri.org/", "Mobile"));
      } catch (Exception _exception) {
        mobile.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Mobile")), String.class);
      } 
      try {
        recvNum.value = (String)_output.get(new QName("http://tempuri.org/", "RecvNum"));
      } catch (Exception _exception) {
        recvNum.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "RecvNum")), String.class);
      } 
      try {
        addNum.value = (String)_output.get(new QName("http://tempuri.org/", "AddNum"));
      } catch (Exception _exception) {
        addNum.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "AddNum")), String.class);
      } 
      try {
        subject.value = (String)_output.get(new QName("http://tempuri.org/", "Subject"));
      } catch (Exception _exception) {
        subject.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Subject")), String.class);
      } 
      try {
        recvTime.value = (String)_output.get(new QName("http://tempuri.org/", "RecvTime"));
      } catch (Exception _exception) {
        recvTime.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "RecvTime")), String.class);
      } 
      try {
        mmsRecvFileList.value = (MmsRecvFileGroup[])_output.get(new QName("http://tempuri.org/", "MmsRecvFileList"));
      } catch (Exception _exception) {
        mmsRecvFileList.value = (MmsRecvFileGroup[])JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "MmsRecvFileList")), MmsRecvFileGroup[].class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_UpFile(long corpID, String loginName, String password, String timeStamp, String subject, long autoDelete, byte[] fileData, LongHolder taskFileID, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[10]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_UpFile");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_UpFile"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, subject, new Long(autoDelete), fileData });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        taskFileID.value = ((Long)_output.get(new QName("http://tempuri.org/", "TaskFileID"))).longValue();
      } catch (Exception _exception) {
        taskFileID.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "TaskFileID")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_DelFile(long corpID, String loginName, String password, String timeStamp, long taskFileID, LongHolder errCode, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[11]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_DelFile");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_DelFile"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskFileID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        errCode.value = ((Long)_output.get(new QName("http://tempuri.org/", "ErrCode"))).longValue();
      } catch (Exception _exception) {
        errCode.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrCode")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_SmsSend(long corpID, String loginName, String password, String timeStamp, String content, long longSms, long priority, String atTime, MobileFileGroup[] mobileList, LongHolder taskSmsID, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[12]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_SmsSend");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_SmsSend"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, content, new Long(longSms), new Long(priority), atTime, mobileList });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        taskSmsID.value = ((Long)_output.get(new QName("http://tempuri.org/", "TaskSmsID"))).longValue();
      } catch (Exception _exception) {
        taskSmsID.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "TaskSmsID")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_GetSmsStatus(long corpID, String loginName, String password, String timeStamp, long taskSmsID, LongHolder status, StringHolder errMsg, LongHolder mobileCount, LongHolder YFMobileCount, StringHolder beginTime, StringHolder endTime) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[13]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_GetSmsStatus");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_GetSmsStatus"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskSmsID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        status.value = ((Long)_output.get(new QName("http://tempuri.org/", "Status"))).longValue();
      } catch (Exception _exception) {
        status.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Status")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        mobileCount.value = ((Long)_output.get(new QName("http://tempuri.org/", "MobileCount"))).longValue();
      } catch (Exception _exception) {
        mobileCount.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "MobileCount")), long.class)).longValue();
      } 
      try {
        YFMobileCount.value = ((Long)_output.get(new QName("http://tempuri.org/", "YFMobileCount"))).longValue();
      } catch (Exception _exception) {
        YFMobileCount.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "YFMobileCount")), long.class)).longValue();
      } 
      try {
        beginTime.value = (String)_output.get(new QName("http://tempuri.org/", "BeginTime"));
      } catch (Exception _exception) {
        beginTime.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "BeginTime")), String.class);
      } 
      try {
        endTime.value = (String)_output.get(new QName("http://tempuri.org/", "EndTime"));
      } catch (Exception _exception) {
        endTime.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "EndTime")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_SmsStop(long corpID, String loginName, String password, String timeStamp, long taskSmsID, LongHolder errCode, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[14]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_SmsStop");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_SmsStop"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskSmsID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        errCode.value = ((Long)_output.get(new QName("http://tempuri.org/", "ErrCode"))).longValue();
      } catch (Exception _exception) {
        errCode.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrCode")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_SmsStart(long corpID, String loginName, String password, String timeStamp, long taskSmsID, LongHolder errCode, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[15]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_SmsStart");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_SmsStart"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskSmsID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        errCode.value = ((Long)_output.get(new QName("http://tempuri.org/", "ErrCode"))).longValue();
      } catch (Exception _exception) {
        errCode.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrCode")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_MmsSend(long corpID, String loginName, String password, String timeStamp, long mmsFileID, long priority, String atTime, MobileFileGroup[] mobileList, LongHolder taskMmsID, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[16]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_MmsSend");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_MmsSend"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(mmsFileID), new Long(priority), atTime, mobileList });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        taskMmsID.value = ((Long)_output.get(new QName("http://tempuri.org/", "TaskMmsID"))).longValue();
      } catch (Exception _exception) {
        taskMmsID.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "TaskMmsID")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_GetMmsStatus(long corpID, String loginName, String password, String timeStamp, long taskMmsID, LongHolder status, StringHolder errMsg, LongHolder mobileCount, LongHolder YFMobileCount, StringHolder beginTime, StringHolder endTime) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[17]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_GetMmsStatus");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_GetMmsStatus"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskMmsID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        status.value = ((Long)_output.get(new QName("http://tempuri.org/", "Status"))).longValue();
      } catch (Exception _exception) {
        status.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "Status")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
      try {
        mobileCount.value = ((Long)_output.get(new QName("http://tempuri.org/", "MobileCount"))).longValue();
      } catch (Exception _exception) {
        mobileCount.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "MobileCount")), long.class)).longValue();
      } 
      try {
        YFMobileCount.value = ((Long)_output.get(new QName("http://tempuri.org/", "YFMobileCount"))).longValue();
      } catch (Exception _exception) {
        YFMobileCount.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "YFMobileCount")), long.class)).longValue();
      } 
      try {
        beginTime.value = (String)_output.get(new QName("http://tempuri.org/", "BeginTime"));
      } catch (Exception _exception) {
        beginTime.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "BeginTime")), String.class);
      } 
      try {
        endTime.value = (String)_output.get(new QName("http://tempuri.org/", "EndTime"));
      } catch (Exception _exception) {
        endTime.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "EndTime")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_MmsStop(long corpID, String loginName, String password, String timeStamp, long taskMmsID, LongHolder errCode, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[18]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_MmsStop");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_MmsStop"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskMmsID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        errCode.value = ((Long)_output.get(new QName("http://tempuri.org/", "ErrCode"))).longValue();
      } catch (Exception _exception) {
        errCode.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrCode")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
  
  public void task_MmsStart(long corpID, String loginName, String password, String timeStamp, long taskMmsID, LongHolder errCode, StringHolder errMsg) throws RemoteException {
    if (this.cachedEndpoint == null)
      throw new NoEndPointException(); 
    Call _call = createCall();
    _call.setOperation(_operations[19]);
    _call.setUseSOAPAction(true);
    _call.setSOAPActionURI("http://tempuri.org/Task_MmsStart");
    _call.setEncodingStyle(null);
    _call.setProperty("sendXsiTypes", Boolean.FALSE);
    _call.setProperty("sendMultiRefs", Boolean.FALSE);
    _call.setSOAPVersion((SOAPConstants)SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new QName("http://tempuri.org/", "Task_MmsStart"));
    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      Object _resp = _call.invoke(new Object[] { new Long(corpID), loginName, password, timeStamp, new Long(taskMmsID) });
      if (_resp instanceof RemoteException)
        throw (RemoteException)_resp; 
      extractAttachments(_call);
      Map _output = _call.getOutputParams();
      try {
        errCode.value = ((Long)_output.get(new QName("http://tempuri.org/", "ErrCode"))).longValue();
      } catch (Exception _exception) {
        errCode.value = ((Long)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrCode")), long.class)).longValue();
      } 
      try {
        errMsg.value = (String)_output.get(new QName("http://tempuri.org/", "ErrMsg"));
      } catch (Exception _exception) {
        errMsg.value = (String)JavaUtils.convert(_output.get(new QName("http://tempuri.org/", "ErrMsg")), String.class);
      } 
    } catch (AxisFault axisFaultException) {
      throw axisFaultException;
    } 
  }
}
