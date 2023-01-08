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

public class SmsRecvGroup implements Serializable {
  private String mobile;
  
  private String recvNum;
  
  private String addNum;
  
  private String recvTime;
  
  private String content;
  
  public SmsRecvGroup(String mobile, String recvNum, String addNum, String recvTime, String content) {
    this.mobile = mobile;
    this.recvNum = recvNum;
    this.addNum = addNum;
    this.recvTime = recvTime;
    this.content = content;
  }
  
  public String getMobile() {
    return this.mobile;
  }
  
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  
  public String getRecvNum() {
    return this.recvNum;
  }
  
  public void setRecvNum(String recvNum) {
    this.recvNum = recvNum;
  }
  
  public String getAddNum() {
    return this.addNum;
  }
  
  public void setAddNum(String addNum) {
    this.addNum = addNum;
  }
  
  public String getRecvTime() {
    return this.recvTime;
  }
  
  public void setRecvTime(String recvTime) {
    this.recvTime = recvTime;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof SmsRecvGroup))
      return false; 
    SmsRecvGroup other = (SmsRecvGroup)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.mobile == null && other.getMobile() == null) || (
      this.mobile != null && 
      this.mobile.equals(other.getMobile()))) && ((
      this.recvNum == null && other.getRecvNum() == null) || (
      this.recvNum != null && 
      this.recvNum.equals(other.getRecvNum()))) && ((
      this.addNum == null && other.getAddNum() == null) || (
      this.addNum != null && 
      this.addNum.equals(other.getAddNum()))) && ((
      this.recvTime == null && other.getRecvTime() == null) || (
      this.recvTime != null && 
      this.recvTime.equals(other.getRecvTime()))) && ((
      this.content == null && other.getContent() == null) || (
      this.content != null && 
      this.content.equals(other.getContent()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public synchronized int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getMobile() != null)
      _hashCode += getMobile().hashCode(); 
    if (getRecvNum() != null)
      _hashCode += getRecvNum().hashCode(); 
    if (getAddNum() != null)
      _hashCode += getAddNum().hashCode(); 
    if (getRecvTime() != null)
      _hashCode += getRecvTime().hashCode(); 
    if (getContent() != null)
      _hashCode += getContent().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(SmsRecvGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "SmsRecvGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("mobile");
    elemField.setXmlName(new QName("http://tempuri.org/", "Mobile"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("recvNum");
    elemField.setXmlName(new QName("http://tempuri.org/", "RecvNum"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("addNum");
    elemField.setXmlName(new QName("http://tempuri.org/", "AddNum"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("recvTime");
    elemField.setXmlName(new QName("http://tempuri.org/", "RecvTime"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("content");
    elemField.setXmlName(new QName("http://tempuri.org/", "Content"));
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
  
  public SmsRecvGroup() {}
}
