package rtx.rtxsms.tempuri;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.FieldDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class MmsRecvFileGroup implements Serializable {
  private String fileName;
  
  private String fileType;
  
  private String fileID;
  
  private String fileLocation;
  
  private byte[] fileData;
  
  public MmsRecvFileGroup(String fileName, String fileType, String fileID, String fileLocation, byte[] fileData) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileID = fileID;
    this.fileLocation = fileLocation;
    this.fileData = fileData;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public String getFileID() {
    return this.fileID;
  }
  
  public void setFileID(String fileID) {
    this.fileID = fileID;
  }
  
  public String getFileLocation() {
    return this.fileLocation;
  }
  
  public void setFileLocation(String fileLocation) {
    this.fileLocation = fileLocation;
  }
  
  public byte[] getFileData() {
    return this.fileData;
  }
  
  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof MmsRecvFileGroup))
      return false; 
    MmsRecvFileGroup other = (MmsRecvFileGroup)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (((this.fileName == null && other.getFileName() == null) || (
      this.fileName != null && 
      this.fileName.equals(other.getFileName()))) && ((
      this.fileType == null && other.getFileType() == null) || (
      this.fileType != null && 
      this.fileType.equals(other.getFileType()))) && ((
      this.fileID == null && other.getFileID() == null) || (
      this.fileID != null && 
      this.fileID.equals(other.getFileID()))) && ((
      this.fileLocation == null && other.getFileLocation() == null) || (
      this.fileLocation != null && 
      this.fileLocation.equals(other.getFileLocation()))) && ((
      this.fileData == null && other.getFileData() == null) || (
      this.fileData != null && 
      Arrays.equals(this.fileData, other.getFileData()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public synchronized int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    if (getFileName() != null)
      _hashCode += getFileName().hashCode(); 
    if (getFileType() != null)
      _hashCode += getFileType().hashCode(); 
    if (getFileID() != null)
      _hashCode += getFileID().hashCode(); 
    if (getFileLocation() != null)
      _hashCode += getFileLocation().hashCode(); 
    if (getFileData() != null) {
      int i = 0;
      for (; i < Array.getLength(getFileData()); 
        i++) {
        Object obj = Array.get(getFileData(), i);
        if (obj != null && 
          !obj.getClass().isArray())
          _hashCode += obj.hashCode(); 
      } 
    } 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(MmsRecvFileGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "MmsRecvFileGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("fileName");
    elemField.setXmlName(new QName("http://tempuri.org/", "FileName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("fileType");
    elemField.setXmlName(new QName("http://tempuri.org/", "FileType"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("fileID");
    elemField.setXmlName(new QName("http://tempuri.org/", "FileID"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("fileLocation");
    elemField.setXmlName(new QName("http://tempuri.org/", "FileLocation"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("fileData");
    elemField.setXmlName(new QName("http://tempuri.org/", "FileData"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
  
  public MmsRecvFileGroup() {}
}
