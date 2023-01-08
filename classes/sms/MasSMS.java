package sms;

import com.js.util.util.PropertiesTrans;
import com.roya.mas.platform.business.SiMockStub;
import com.roya.mas.platform.schema.sms.DeliveryInformation;
import com.roya.mas.platform.schema.sms.GetReceivedSmsRequest;
import com.roya.mas.platform.schema.sms.GetSmsDeliveryStatusRequest;
import com.roya.mas.platform.schema.sms.MessageFormat;
import com.roya.mas.platform.schema.sms.SMSMessage;
import com.roya.mas.platform.schema.sms.SendMethodType;
import com.roya.mas.platform.schema.sms.SendSmsRequest;
import com.roya.mas.platform.schema.sms.SendSmsResponse;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.rpc.Service;
import org.apache.axis.client.Service;
import org.apache.axis.types.URI;

public class MasSMS {
  private static String[] mas = null;
  
  public static boolean sendSMS(String phone, String msg) {
    sendSMSReturn(phone, msg);
    return true;
  }
  
  public static String sendSMSReturn(String phone, String msg) {
    if (!phone.startsWith("tel:"))
      phone = "tel:" + phone; 
    getMas();
    String requestIdentifier = "";
    try {
      URL url = new URL(mas[1]);
      SiMockStub stub = new SiMockStub(url, (Service)new Service());
      SendSmsRequest s = new SendSmsRequest();
      s.setApplicationID(mas[0]);
      s.setDeliveryResultRequest(Boolean.valueOf(mas[5]).booleanValue());
      s.setExtendCode(mas[4]);
      String message = "【OA】" + msg;
      s.setMessage(message);
      s.setMessageFormat(MessageFormat.fromValue(mas[3]));
      s.setSendMethod(SendMethodType.fromValue(mas[2]));
      String[] a = phone.split(";");
      int leng = a.length;
      URI[] ary = new URI[leng];
      for (int i = 0; i < leng; i++) {
        URI temp = new URI(a[i]);
        ary[i] = temp;
      } 
      s.setDestinationAddresses(ary);
      SendSmsResponse rep = stub.sendSms(s);
      requestIdentifier = rep.getRequestIdentifier();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return requestIdentifier;
  }
  
  public static String getSmsDeliveryStatus(String requestIdentifier) {
    Map<String, String> status = new HashMap<String, String>();
    status.put("Delivered".toLowerCase(), "3000");
    status.put("DeliveryImpossible".toLowerCase(), "3002");
    status.put("CANNOTFINDROUTER_ERROR".toLowerCase(), "1007");
    status.put("DeliveryToTerminal".toLowerCase(), "4000");
    status.put("DeliveryNotificationNotSupported".toLowerCase(), "1000");
    status.put("KeyWordFilterFailed".toLowerCase(), "2002");
    getMas();
    String result = "0";
    try {
      URL url = new URL(mas[1]);
      Service service = new Service();
      SiMockStub stub = new SiMockStub(url, (Service)service);
      GetSmsDeliveryStatusRequest req = new GetSmsDeliveryStatusRequest();
      req.setApplicationID(mas[0]);
      req.setRequestIdentifier(requestIdentifier);
      DeliveryInformation[] d = stub.getSmsDeliveryStatus(req);
      if (d != null)
        for (int i = 0; i < d.length; i++) {
          DeliveryInformation dd = d[i];
          String s = dd.getDeliveryStatus().toString();
          result = (status.get(s.toLowerCase()) == null) ? "0" : status.get(s.toLowerCase());
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String[] getMas() {
    if (mas == null)
      try {
        mas = new String[] { "", "", "", "", "", "" };
        mas[0] = PropertiesTrans.getValueByKey("ApplicationID");
        mas[1] = PropertiesTrans.getValueByKey("Masurl");
        mas[2] = PropertiesTrans.getValueByKey("SendMethod");
        mas[3] = PropertiesTrans.getValueByKey("MessageFormat");
        mas[4] = PropertiesTrans.getValueByKey("ExtendCode");
        mas[5] = PropertiesTrans.getValueByKey("DeliveryResultRequest");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return mas;
  }
  
  public static void setMas(String[] mas) {
    MasSMS.mas = mas;
  }
  
  public static void main(String[] args) {
    String in = "74f34e3c-193d-4334-8ce3-165247ee326c";
    getSmsDeliveryStatus(in);
    System.exit(0);
  }
  
  public static String sendSMSReturn(String phone, String msg, String extendCode) {
    if (!phone.startsWith("tel:"))
      phone = "tel:" + phone; 
    extendCode = extendCode.substring(8);
    getMas();
    String requestIdentifier = "";
    try {
      URL url = new URL(mas[1]);
      SiMockStub stub = new SiMockStub(url, (Service)new Service());
      SendSmsRequest s = new SendSmsRequest();
      s.setApplicationID(mas[0]);
      s.setDeliveryResultRequest(Boolean.valueOf(mas[5]).booleanValue());
      s.setExtendCode(extendCode);
      String message = "【OA】" + msg;
      s.setMessage(message);
      s.setMessageFormat(MessageFormat.fromValue(mas[3]));
      s.setSendMethod(SendMethodType.fromValue(mas[2]));
      String[] a = phone.split(";");
      int leng = a.length;
      URI[] ary = new URI[leng];
      for (int i = 0; i < leng; i++) {
        URI temp = new URI(a[i]);
        ary[i] = temp;
      } 
      s.setDestinationAddresses(ary);
      SendSmsResponse rep = stub.sendSms(s);
      requestIdentifier = rep.getRequestIdentifier();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return requestIdentifier;
  }
  
  public static List<Map<String, String>> getReceivedSms() throws Exception {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    getMas();
    try {
      URL url = new URL(mas[1]);
      Service service = new Service();
      SiMockStub stub = new SiMockStub(url, (Service)service);
      GetReceivedSmsRequest parameter = new GetReceivedSmsRequest();
      parameter.setApplicationID(mas[0]);
      SMSMessage[] smsMessages = stub.getReceivedSms(parameter);
      System.out.println("收到回复短信条数：" + smsMessages.length + "条");
      int n = 0;
      if (smsMessages != null) {
        for (int i = 0; i < smsMessages.length; i++) {
          Map<String, String> map = new HashMap<String, String>();
          SMSMessage smsMessage = smsMessages[i];
          String msg = smsMessage.getMessage();
          URI phoneUrl = smsMessage.getSenderAddress();
          String phone = phoneUrl.toString();
          phone = phone.replace("tel:", "");
          URI extendCodeUri = smsMessage.getSmsServiceActivationNumber();
          String extendCode = extendCodeUri.toString();
          extendCode = extendCode.replace("tel:10657300230901", "");
          if (extendCode.length() != 6) {
            n++;
          } else {
            map.put("msg", msg);
            map.put("phone", phone);
            map.put("extendCode", extendCode);
            list.add(map);
          } 
        } 
        System.out.println("无效回复短信条数为" + n + "条！");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return list;
  }
  
  public static List<Map<String, String>> getReceivedSms1() throws Exception {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    Map<String, String> map = new HashMap<String, String>();
    map.put("msg", "1111111");
    map.put("phone", "15269130635");
    map.put("extendCode", "100112");
    list.add(map);
    return list;
  }
}
