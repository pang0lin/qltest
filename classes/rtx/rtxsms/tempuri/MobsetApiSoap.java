package rtx.rtxsms.tempuri;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsIDListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsRecvFileGroupHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfMmsReportListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsIDListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsRecvListHolder;
import rtx.rtxsms.tempuri.holders.ArrayOfSmsReportListHolder;

public interface MobsetApiSoap extends Remote {
  void sms_Send(long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong2, MobileListGroup[] paramArrayOfMobileListGroup, String paramString6, LongHolder paramLongHolder, StringHolder paramStringHolder, ArrayOfSmsIDListHolder paramArrayOfSmsIDListHolder) throws RemoteException;
  
  void sms_GetRecv(long paramLong, String paramString1, String paramString2, String paramString3, LongHolder paramLongHolder, StringHolder paramStringHolder, ArrayOfSmsRecvListHolder paramArrayOfSmsRecvListHolder) throws RemoteException;
  
  void sms_GetReport(long paramLong, String paramString1, String paramString2, String paramString3, LongHolder paramLongHolder, StringHolder paramStringHolder, ArrayOfSmsReportListHolder paramArrayOfSmsReportListHolder) throws RemoteException;
  
  void sms_GetSign(long paramLong, String paramString1, String paramString2, String paramString3, LongHolder paramLongHolder, StringHolder paramStringHolder1, StringHolder paramStringHolder2) throws RemoteException;
  
  void sms_GetBalance(long paramLong, String paramString1, String paramString2, String paramString3, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void mms_UpFile(long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong2, MmsFileGroup[] paramArrayOfMmsFileGroup, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void mms_GetFileStatus(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder1, StringHolder paramStringHolder1, StringHolder paramStringHolder2, LongHolder paramLongHolder2, StringHolder paramStringHolder3) throws RemoteException;
  
  void mms_Send(long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, MobileListGroup[] paramArrayOfMobileListGroup, long paramLong2, LongHolder paramLongHolder, StringHolder paramStringHolder, ArrayOfMmsIDListHolder paramArrayOfMmsIDListHolder) throws RemoteException;
  
  void mms_GetReport(long paramLong, String paramString1, String paramString2, String paramString3, LongHolder paramLongHolder, StringHolder paramStringHolder, ArrayOfMmsReportListHolder paramArrayOfMmsReportListHolder) throws RemoteException;
  
  void mms_GetRecv(long paramLong, String paramString1, String paramString2, String paramString3, LongHolder paramLongHolder, StringHolder paramStringHolder1, StringHolder paramStringHolder2, StringHolder paramStringHolder3, StringHolder paramStringHolder4, StringHolder paramStringHolder5, StringHolder paramStringHolder6, ArrayOfMmsRecvFileGroupHolder paramArrayOfMmsRecvFileGroupHolder) throws RemoteException;
  
  void task_UpFile(long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong2, byte[] paramArrayOfbyte, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_DelFile(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_SmsSend(long paramLong1, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong2, long paramLong3, String paramString5, MobileFileGroup[] paramArrayOfMobileFileGroup, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_GetSmsStatus(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder1, StringHolder paramStringHolder1, LongHolder paramLongHolder2, LongHolder paramLongHolder3, StringHolder paramStringHolder2, StringHolder paramStringHolder3) throws RemoteException;
  
  void task_SmsStop(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_SmsStart(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_MmsSend(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, long paramLong3, String paramString4, MobileFileGroup[] paramArrayOfMobileFileGroup, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_GetMmsStatus(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder1, StringHolder paramStringHolder1, LongHolder paramLongHolder2, LongHolder paramLongHolder3, StringHolder paramStringHolder2, StringHolder paramStringHolder3) throws RemoteException;
  
  void task_MmsStop(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
  
  void task_MmsStart(long paramLong1, String paramString1, String paramString2, String paramString3, long paramLong2, LongHolder paramLongHolder, StringHolder paramStringHolder) throws RemoteException;
}
