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

public class SmsIDGroup implements Serializable {
  private String mobile;
  
  private long smsID;
  
  public SmsIDGroup(String mobile, long smsID) {
    this.mobile = mobile;
    this.smsID = smsID;
  }
  
  public String getMobile() {
    return this.mobile;
  }
  
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  
  public long getSmsID() {
    return this.smsID;
  }
  
  public void setSmsID(long smsID) {
    this.smsID = smsID;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof SmsIDGroup))
      return false; 
    SmsIDGroup other = (SmsIDGroup)obj;
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
      this.mobile.equals(other.getMobile()))) && 
      this.smsID == other.getSmsID());
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
    _hashCode += (new Long(getSmsID())).hashCode();
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(SmsIDGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "SmsIDGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("mobile");
    elemField.setXmlName(new QName("http://tempuri.org/", "Mobile"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("smsID");
    elemField.setXmlName(new QName("http://tempuri.org/", "SmsID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
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
  
  public SmsIDGroup() {}
}
