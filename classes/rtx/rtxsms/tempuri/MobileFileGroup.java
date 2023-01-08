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

public class MobileFileGroup implements Serializable {
  private long taskFileType;
  
  private String taskFileID;
  
  public MobileFileGroup(long taskFileType, String taskFileID) {
    this.taskFileType = taskFileType;
    this.taskFileID = taskFileID;
  }
  
  public long getTaskFileType() {
    return this.taskFileType;
  }
  
  public void setTaskFileType(long taskFileType) {
    this.taskFileType = taskFileType;
  }
  
  public String getTaskFileID() {
    return this.taskFileID;
  }
  
  public void setTaskFileID(String taskFileID) {
    this.taskFileID = taskFileID;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof MobileFileGroup))
      return false; 
    MobileFileGroup other = (MobileFileGroup)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (this.taskFileType == other.getTaskFileType() && ((
      this.taskFileID == null && other.getTaskFileID() == null) || (
      this.taskFileID != null && 
      this.taskFileID.equals(other.getTaskFileID()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public synchronized int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    _hashCode += (new Long(getTaskFileType())).hashCode();
    if (getTaskFileID() != null)
      _hashCode += getTaskFileID().hashCode(); 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(MobileFileGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "MobileFileGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("taskFileType");
    elemField.setXmlName(new QName("http://tempuri.org/", "TaskFileType"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("taskFileID");
    elemField.setXmlName(new QName("http://tempuri.org/", "TaskFileID"));
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
  
  public MobileFileGroup() {}
}
