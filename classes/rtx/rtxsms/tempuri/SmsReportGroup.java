package rtx.rtxsms.tempuri;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.FieldDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class SmsReportGroup implements Serializable {
  private long smsID;
  
  private long status;
  
  private String reportTime;
  
  private String exStatus;
  
  public SmsReportGroup(long smsID, long status, String reportTime, String exStatus) {
    this.smsID = smsID;
    this.status = status;
    this.reportTime = reportTime;
    this.exStatus = exStatus;
  }
  
  public long getSmsID() {
    return this.smsID;
  }
  
  public void setSmsID(long smsID) {
    this.smsID = smsID;
  }
  
  public long getStatus() {
    return this.status;
  }
  
  public void setStatus(long status) {
    this.status = status;
  }
  
  public String getReportTime() {
    return this.reportTime;
  }
  
  public void setReportTime(String reportTime) {
    this.reportTime = reportTime;
  }
  
  public String getExStatus() {
    return this.exStatus;
  }
  
  public void setExStatus(String exStatus) {
    this.exStatus = exStatus;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof SmsReportGroup))
      return false; 
    SmsReportGroup other = (SmsReportGroup)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (this.smsID == other.getSmsID() && 
      this.status == other.getStatus() && ((
      this.reportTime == null && other.getReportTime() == null) || (
      this.reportTime != null && 
      this.reportTime.equals(other.getReportTime()))) && ((
      this.exStatus == null && other.getExStatus() == null) || (
      this.exStatus != null && 
      this.exStatus.equals(other.getExStatus()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public synchronized int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    _hashCode += (new Long(getSmsID())).hashCode();
    _hashCode += (new Long(getStatus())).hashCode();
    if (getReportTime() != null)
      _hashCode += getReportTime().hashCode(); 
    if (getExStatus() != null)
      _hashCode += getExStatus().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(SmsReportGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "SmsReportGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("smsID");
    elemField.setXmlName(new QName("http://tempuri.org/", "SmsID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("status");
    elemField.setXmlName(new QName("http://tempuri.org/", "Status"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("reportTime");
    elemField.setXmlName(new QName("http://tempuri.org/", "ReportTime"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("exStatus");
    elemField.setXmlName(new QName("http://tempuri.org/", "ExStatus"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
  }
  
  public static TypeDesc getTypeDesc() {
    return typeDesc;
  }
  
  public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
    return 
      (Serializer)new BeanSerializer(
        _javaType, _xmlType, typeDesc);
  }
  
  public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
    return 
      (Deserializer)new BeanDeserializer(
        _javaType, _xmlType, typeDesc);
  }
  
  public SmsReportGroup() {}
}
