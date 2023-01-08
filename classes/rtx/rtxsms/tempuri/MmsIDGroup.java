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

public class MmsIDGroup implements Serializable {
  private String mobile;
  
  private long mmsID;
  
  public MmsIDGroup(String mobile, long mmsID) {
    this.mobile = mobile;
    this.mmsID = mmsID;
  }
  
  public String getMobile() {
    return this.mobile;
  }
  
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  
  public long getMmsID() {
    return this.mmsID;
  }
  
  public void setMmsID(long mmsID) {
    this.mmsID = mmsID;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof MmsIDGroup))
      return false; 
    MmsIDGroup other = (MmsIDGroup)obj;
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
      this.mmsID == other.getMmsID());
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
    _hashCode += (new Long(getMmsID())).hashCode();
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(MmsIDGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "MmsIDGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("mobile");
    elemField.setXmlName(new QName("http://tempuri.org/", "Mobile"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("mmsID");
    elemField.setXmlName(new QName("http://tempuri.org/", "MmsID"));
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
  
  public MmsIDGroup() {}
}
