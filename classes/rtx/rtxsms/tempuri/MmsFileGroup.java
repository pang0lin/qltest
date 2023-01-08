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

public class MmsFileGroup implements Serializable {
  private long playTime;
  
  private String image_FileName;
  
  private byte[] image_FileData;
  
  private String text_FileName;
  
  private String text_Content;
  
  private String audio_FileName;
  
  private byte[] audio_FileData;
  
  public MmsFileGroup(long playTime, String image_FileName, byte[] image_FileData, String text_FileName, String text_Content, String audio_FileName, byte[] audio_FileData) {
    this.playTime = playTime;
    this.image_FileName = image_FileName;
    this.image_FileData = image_FileData;
    this.text_FileName = text_FileName;
    this.text_Content = text_Content;
    this.audio_FileName = audio_FileName;
    this.audio_FileData = audio_FileData;
  }
  
  public long getPlayTime() {
    return this.playTime;
  }
  
  public void setPlayTime(long playTime) {
    this.playTime = playTime;
  }
  
  public String getImage_FileName() {
    return this.image_FileName;
  }
  
  public void setImage_FileName(String image_FileName) {
    this.image_FileName = image_FileName;
  }
  
  public byte[] getImage_FileData() {
    return this.image_FileData;
  }
  
  public void setImage_FileData(byte[] image_FileData) {
    this.image_FileData = image_FileData;
  }
  
  public String getText_FileName() {
    return this.text_FileName;
  }
  
  public void setText_FileName(String text_FileName) {
    this.text_FileName = text_FileName;
  }
  
  public String getText_Content() {
    return this.text_Content;
  }
  
  public void setText_Content(String text_Content) {
    this.text_Content = text_Content;
  }
  
  public String getAudio_FileName() {
    return this.audio_FileName;
  }
  
  public void setAudio_FileName(String audio_FileName) {
    this.audio_FileName = audio_FileName;
  }
  
  public byte[] getAudio_FileData() {
    return this.audio_FileData;
  }
  
  public void setAudio_FileData(byte[] audio_FileData) {
    this.audio_FileData = audio_FileData;
  }
  
  private Object __equalsCalc = null;
  
  public synchronized boolean equals(Object obj) {
    if (!(obj instanceof MmsFileGroup))
      return false; 
    MmsFileGroup other = (MmsFileGroup)obj;
    if (obj == null)
      return false; 
    if (this == obj)
      return true; 
    if (this.__equalsCalc != null)
      return (this.__equalsCalc == obj); 
    this.__equalsCalc = obj;
    boolean _equals = 
      (this.playTime == other.getPlayTime() && ((
      this.image_FileName == null && other.getImage_FileName() == null) || (
      this.image_FileName != null && 
      this.image_FileName.equals(other.getImage_FileName()))) && ((
      this.image_FileData == null && other.getImage_FileData() == null) || (
      this.image_FileData != null && 
      Arrays.equals(this.image_FileData, other.getImage_FileData()))) && ((
      this.text_FileName == null && other.getText_FileName() == null) || (
      this.text_FileName != null && 
      this.text_FileName.equals(other.getText_FileName()))) && ((
      this.text_Content == null && other.getText_Content() == null) || (
      this.text_Content != null && 
      this.text_Content.equals(other.getText_Content()))) && ((
      this.audio_FileName == null && other.getAudio_FileName() == null) || (
      this.audio_FileName != null && 
      this.audio_FileName.equals(other.getAudio_FileName()))) && ((
      this.audio_FileData == null && other.getAudio_FileData() == null) || (
      this.audio_FileData != null && 
      Arrays.equals(this.audio_FileData, other.getAudio_FileData()))));
    this.__equalsCalc = null;
    return _equals;
  }
  
  private boolean __hashCodeCalc = false;
  
  public synchronized int hashCode() {
    if (this.__hashCodeCalc)
      return 0; 
    this.__hashCodeCalc = true;
    int _hashCode = 1;
    _hashCode += (new Long(getPlayTime())).hashCode();
    if (getImage_FileName() != null)
      _hashCode += getImage_FileName().hashCode(); 
    if (getImage_FileData() != null) {
      int i = 0;
      for (; i < Array.getLength(getImage_FileData()); 
        i++) {
        Object obj = Array.get(getImage_FileData(), i);
        if (obj != null && 
          !obj.getClass().isArray())
          _hashCode += obj.hashCode(); 
      } 
    } 
    if (getText_FileName() != null)
      _hashCode += getText_FileName().hashCode(); 
    if (getText_Content() != null)
      _hashCode += getText_Content().hashCode(); 
    if (getAudio_FileName() != null)
      _hashCode += getAudio_FileName().hashCode(); 
    if (getAudio_FileData() != null) {
      int i = 0;
      for (; i < Array.getLength(getAudio_FileData()); 
        i++) {
        Object obj = Array.get(getAudio_FileData(), i);
        if (obj != null && 
          !obj.getClass().isArray())
          _hashCode += obj.hashCode(); 
      } 
    } 
    this.__hashCodeCalc = false;
    return _hashCode;
  }
  
  private static TypeDesc typeDesc = new TypeDesc(MmsFileGroup.class, true);
  
  static {
    typeDesc.setXmlType(new QName("http://tempuri.org/", "MmsFileGroup"));
    ElementDesc elemField = new ElementDesc();
    elemField.setFieldName("playTime");
    elemField.setXmlName(new QName("http://tempuri.org/", "PlayTime"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("image_FileName");
    elemField.setXmlName(new QName("http://tempuri.org/", "Image_FileName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("image_FileData");
    elemField.setXmlName(new QName("http://tempuri.org/", "Image_FileData"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("text_FileName");
    elemField.setXmlName(new QName("http://tempuri.org/", "Text_FileName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("text_Content");
    elemField.setXmlName(new QName("http://tempuri.org/", "Text_Content"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("audio_FileName");
    elemField.setXmlName(new QName("http://tempuri.org/", "Audio_FileName"));
    elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc((FieldDesc)elemField);
    elemField = new ElementDesc();
    elemField.setFieldName("audio_FileData");
    elemField.setXmlName(new QName("http://tempuri.org/", "Audio_FileData"));
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
  
  public MmsFileGroup() {}
}
