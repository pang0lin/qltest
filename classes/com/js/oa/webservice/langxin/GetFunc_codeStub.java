package com.js.oa.webservice.langxin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDataSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.impl.llom.OMSourcedElementImpl;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;
import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.ADBBean;
import org.apache.axis2.databinding.ADBDataSource;
import org.apache.axis2.databinding.ADBException;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.OutInAxisOperation;
import org.apache.axis2.engine.MessageReceiver;
import org.apache.axis2.util.CallbackReceiver;
import org.apache.axis2.util.Utils;

public class GetFunc_codeStub extends Stub {
  protected AxisOperation[] _operations;
  
  public static class GetCode implements ADBBean {
    public static final QName MY_QNAME = new QName(
        "http://tempuri.org/", 
        "GetCode", 
        "ns1");
    
    protected String localFunc_code;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localFunc_codeTracker = false;
    
    public String getFunc_code() {
      return this.localFunc_code;
    }
    
    public void setFunc_code(String param) {
      if (param != null) {
        this.localFunc_codeTracker = true;
      } else {
        this.localFunc_codeTracker = false;
      } 
      this.localFunc_code = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = 
        new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetCode.this.serialize(GetFunc_codeStub.GetCode.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(
          MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              String.valueOf(namespacePrefix) + ":GetCode", 
              (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              "GetCode", 
              (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localFunc_codeTracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "func_code", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "func_code");
          } 
        } else {
          xmlWriter.writeStartElement("func_code");
        } 
        if (this.localFunc_code == null)
          throw new ADBException("func_code cannot be null!!"); 
        xmlWriter.writeCharacters(this.localFunc_code);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localFunc_codeTracker) {
        elementList.add(new QName("http://tempuri.org/", 
              "func_code"));
        if (this.localFunc_code != null) {
          elementList.add(ConverterUtil.convertToString(this.localFunc_code));
        } else {
          throw new ADBException("func_code cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetCode parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetCode object = 
          new GetFunc_codeStub.GetCode();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", 
                "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetCode".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetCode)GetFunc_codeStub.ExtensionMapper.getTypeObject(
                    nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "func_code")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setFunc_code(
                ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetCodeResponse implements ADBBean {
    public static final QName MY_QNAME = new QName("http://tempuri.org/", "GetCodeResponse", "ns1");
    
    protected String localGetCodeResult;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localGetCodeResultTracker = false;
    
    public String getGetCodeResult() {
      return this.localGetCodeResult;
    }
    
    public void setGetCodeResult(String param) {
      if (param != null) {
        this.localGetCodeResultTracker = true;
      } else {
        this.localGetCodeResultTracker = false;
      } 
      this.localGetCodeResult = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetCodeResponse.this.serialize(GetFunc_codeStub.GetCodeResponse.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":GetCodeResponse", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetCodeResponse", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localGetCodeResultTracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "GetCodeResult", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "GetCodeResult");
          } 
        } else {
          xmlWriter.writeStartElement("GetCodeResult");
        } 
        if (this.localGetCodeResult == null)
          throw new ADBException("GetCodeResult cannot be null!!"); 
        xmlWriter.writeCharacters(this.localGetCodeResult);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localGetCodeResultTracker) {
        elementList.add(new QName("http://tempuri.org/", "GetCodeResult"));
        if (this.localGetCodeResult != null) {
          elementList.add(ConverterUtil.convertToString(this.localGetCodeResult));
        } else {
          throw new ADBException("GetCodeResult cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetCodeResponse parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetCodeResponse object = new GetFunc_codeStub.GetCodeResponse();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetCodeResponse".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetCodeResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetCodeResult")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setGetCodeResult(ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetMessage implements ADBBean {
    public static final QName MY_QNAME = new QName(
        "http://tempuri.org/", 
        "GetMessage", 
        "ns1");
    
    protected String localA0190;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localA0190Tracker = false;
    
    public String getA0190() {
      return this.localA0190;
    }
    
    public void setA0190(String param) {
      if (param != null) {
        this.localA0190Tracker = true;
      } else {
        this.localA0190Tracker = false;
      } 
      this.localA0190 = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = 
        new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetMessage.this.serialize(GetFunc_codeStub.GetMessage.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(
          MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              String.valueOf(namespacePrefix) + ":GetMessage", 
              (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              "GetMessage", 
              (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localA0190Tracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "a0190", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "a0190");
          } 
        } else {
          xmlWriter.writeStartElement("a0190");
        } 
        if (this.localA0190 == null)
          throw new ADBException("a0190 cannot be null!!"); 
        xmlWriter.writeCharacters(this.localA0190);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localA0190Tracker) {
        elementList.add(new QName("http://tempuri.org/", 
              "a0190"));
        if (this.localA0190 != null) {
          elementList.add(ConverterUtil.convertToString(this.localA0190));
        } else {
          throw new ADBException("a0190 cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetMessage parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetMessage object = 
          new GetFunc_codeStub.GetMessage();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", 
                "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetMessage".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetMessage)GetFunc_codeStub.ExtensionMapper.getTypeObject(
                    nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "a0190")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setA0190(
                ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetMessageResponse implements ADBBean {
    public static final QName MY_QNAME = new QName("http://tempuri.org/", "GetMessageResponse", "ns1");
    
    protected String localGetMessageResult;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localGetMessageResultTracker = false;
    
    public String getGetMessageResult() {
      return this.localGetMessageResult;
    }
    
    public void setGetMessageResult(String param) {
      if (param != null) {
        this.localGetMessageResultTracker = true;
      } else {
        this.localGetMessageResultTracker = false;
      } 
      this.localGetMessageResult = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetMessageResponse.this.serialize(GetFunc_codeStub.GetMessageResponse.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":GetMessageResponse", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetMessageResponse", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localGetMessageResultTracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "GetMessageResult", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "GetMessageResult");
          } 
        } else {
          xmlWriter.writeStartElement("GetMessageResult");
        } 
        if (this.localGetMessageResult == null)
          throw new ADBException("GetMessageResult cannot be null!!"); 
        xmlWriter.writeCharacters(this.localGetMessageResult);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localGetMessageResultTracker) {
        elementList.add(new QName("http://tempuri.org/", "GetMessageResult"));
        if (this.localGetMessageResult != null) {
          elementList.add(ConverterUtil.convertToString(this.localGetMessageResult));
        } else {
          throw new ADBException("GetMessageResult cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetMessageResponse parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetMessageResponse object = new GetFunc_codeStub.GetMessageResponse();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetMessageResponse".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetMessageResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetMessageResult")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setGetMessageResult(ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetShortMessage implements ADBBean {
    public static final QName MY_QNAME = new QName(
        "http://tempuri.org/", 
        "GetShortMessage", 
        "ns1");
    
    protected String localA0190;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localA0190Tracker = false;
    
    public String getA0190() {
      return this.localA0190;
    }
    
    public void setA0190(String param) {
      if (param != null) {
        this.localA0190Tracker = true;
      } else {
        this.localA0190Tracker = false;
      } 
      this.localA0190 = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = 
        new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetShortMessage.this.serialize(GetFunc_codeStub.GetShortMessage.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(
          MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              String.valueOf(namespacePrefix) + ":GetShortMessage", 
              (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              "GetShortMessage", 
              (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localA0190Tracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "a0190", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "a0190");
          } 
        } else {
          xmlWriter.writeStartElement("a0190");
        } 
        if (this.localA0190 == null)
          throw new ADBException("a0190 cannot be null!!"); 
        xmlWriter.writeCharacters(this.localA0190);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localA0190Tracker) {
        elementList.add(new QName("http://tempuri.org/", 
              "a0190"));
        if (this.localA0190 != null) {
          elementList.add(ConverterUtil.convertToString(this.localA0190));
        } else {
          throw new ADBException("a0190 cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetShortMessage parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetShortMessage object = 
          new GetFunc_codeStub.GetShortMessage();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", 
                "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetShortMessage".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetShortMessage)GetFunc_codeStub.ExtensionMapper.getTypeObject(
                    nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "a0190")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setA0190(
                ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetShortMessageResponse implements ADBBean {
    public static final QName MY_QNAME = new QName("http://tempuri.org/", "GetShortMessageResponse", "ns1");
    
    protected String localGetShortMessageResult;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localGetShortMessageResultTracker = false;
    
    public String getGetShortMessageResult() {
      return this.localGetShortMessageResult;
    }
    
    public void setGetShortMessageResult(String param) {
      if (param != null) {
        this.localGetShortMessageResultTracker = true;
      } else {
        this.localGetShortMessageResultTracker = false;
      } 
      this.localGetShortMessageResult = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetShortMessageResponse.this.serialize(GetFunc_codeStub.GetShortMessageResponse.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":GetShortMessageResponse", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetShortMessageResponse", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localGetShortMessageResultTracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "GetShortMessageResult", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "GetShortMessageResult");
          } 
        } else {
          xmlWriter.writeStartElement("GetShortMessageResult");
        } 
        if (this.localGetShortMessageResult == null)
          throw new ADBException("GetShortMessageResult cannot be null!!"); 
        xmlWriter.writeCharacters(this.localGetShortMessageResult);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localGetShortMessageResultTracker) {
        elementList.add(new QName("http://tempuri.org/", "GetShortMessageResult"));
        if (this.localGetShortMessageResult != null) {
          elementList.add(ConverterUtil.convertToString(this.localGetShortMessageResult));
        } else {
          throw new ADBException("GetShortMessageResult cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetShortMessageResponse parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetShortMessageResponse object = new GetFunc_codeStub.GetShortMessageResponse();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetShortMessageResponse".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetShortMessageResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetShortMessageResult")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setGetShortMessageResult(ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetToken implements ADBBean {
    public static final QName MY_QNAME = new QName(
        "http://tempuri.org/", 
        "GetToken", 
        "ns1");
    
    protected String localUserCode;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localUserCodeTracker = false;
    
    protected int localExpirDate;
    
    public String getUserCode() {
      return this.localUserCode;
    }
    
    public void setUserCode(String param) {
      if (param != null) {
        this.localUserCodeTracker = true;
      } else {
        this.localUserCodeTracker = false;
      } 
      this.localUserCode = param;
    }
    
    public int getExpirDate() {
      return this.localExpirDate;
    }
    
    public void setExpirDate(int param) {
      this.localExpirDate = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = 
        new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetToken.this.serialize(GetFunc_codeStub.GetToken.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(
          MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              String.valueOf(namespacePrefix) + ":GetToken", 
              (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              "GetToken", 
              (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localUserCodeTracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "UserCode", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "UserCode");
          } 
        } else {
          xmlWriter.writeStartElement("UserCode");
        } 
        if (this.localUserCode == null)
          throw new ADBException("UserCode cannot be null!!"); 
        xmlWriter.writeCharacters(this.localUserCode);
        xmlWriter.writeEndElement();
      } 
      namespace = "http://tempuri.org/";
      if (!namespace.equals("")) {
        prefix = xmlWriter.getPrefix(namespace);
        if (prefix == null) {
          prefix = generatePrefix(namespace);
          xmlWriter.writeStartElement(prefix, "ExpirDate", namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } else {
          xmlWriter.writeStartElement(namespace, "ExpirDate");
        } 
      } else {
        xmlWriter.writeStartElement("ExpirDate");
      } 
      if (this.localExpirDate == Integer.MIN_VALUE)
        throw new ADBException("ExpirDate cannot be null!!"); 
      xmlWriter.writeCharacters(ConverterUtil.convertToString(this.localExpirDate));
      xmlWriter.writeEndElement();
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localUserCodeTracker) {
        elementList.add(new QName("http://tempuri.org/", 
              "UserCode"));
        if (this.localUserCode != null) {
          elementList.add(ConverterUtil.convertToString(this.localUserCode));
        } else {
          throw new ADBException("UserCode cannot be null!!");
        } 
      } 
      elementList.add(new QName("http://tempuri.org/", 
            "ExpirDate"));
      elementList.add(
          ConverterUtil.convertToString(this.localExpirDate));
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetToken parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetToken object = 
          new GetFunc_codeStub.GetToken();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", 
                "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetToken".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetToken)GetFunc_codeStub.ExtensionMapper.getTypeObject(
                    nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "UserCode")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setUserCode(
                ConverterUtil.convertToString(content));
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "ExpirDate")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setExpirDate(
                ConverterUtil.convertToInt(content));
            reader.next();
          } else {
            throw new ADBException("Unexpected subelement " + reader.getLocalName());
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  public static class GetTokenResponse implements ADBBean {
    public static final QName MY_QNAME = new QName("http://tempuri.org/", "GetTokenResponse", "ns1");
    
    protected String localGetTokenResult;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://tempuri.org/"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localGetTokenResultTracker = false;
    
    public String getGetTokenResult() {
      return this.localGetTokenResult;
    }
    
    public void setGetTokenResult(String param) {
      if (param != null) {
        this.localGetTokenResultTracker = true;
      } else {
        this.localGetTokenResultTracker = false;
      } 
      this.localGetTokenResult = param;
    }
    
    public static boolean isReaderMTOMAware(XMLStreamReader reader) {
      boolean isReaderMTOMAware = false;
      try {
        isReaderMTOMAware = Boolean.TRUE.equals(reader.getProperty("IsDatahandlersAwareParsing"));
      } catch (IllegalArgumentException e) {
        isReaderMTOMAware = false;
      } 
      return isReaderMTOMAware;
    }
    
    public OMElement getOMElement(QName parentQName, final OMFactory factory) throws ADBException {
      ADBDataSource aDBDataSource = new ADBDataSource(this, MY_QNAME) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            GetFunc_codeStub.GetTokenResponse.this.serialize(GetFunc_codeStub.GetTokenResponse.MY_QNAME, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(MY_QNAME, factory, (OMDataSource)aDBDataSource);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException, ADBException {
      serialize(parentQName, factory, xmlWriter, false);
    }
    
    public void serialize(QName parentQName, OMFactory factory, MTOMAwareXMLStreamWriter xmlWriter, boolean serializeType) throws XMLStreamException, ADBException {
      String prefix = null;
      String namespace = null;
      prefix = parentQName.getPrefix();
      namespace = parentQName.getNamespaceURI();
      if (namespace != null && namespace.trim().length() > 0) {
        String writerPrefix = xmlWriter.getPrefix(namespace);
        if (writerPrefix != null) {
          xmlWriter.writeStartElement(namespace, parentQName.getLocalPart());
        } else {
          if (prefix == null)
            prefix = generatePrefix(namespace); 
          xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(), namespace);
          xmlWriter.writeNamespace(prefix, namespace);
          xmlWriter.setPrefix(prefix, namespace);
        } 
      } else {
        xmlWriter.writeStartElement(parentQName.getLocalPart());
      } 
      if (serializeType) {
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://tempuri.org/");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":GetTokenResponse", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "GetTokenResponse", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localGetTokenResultTracker) {
        namespace = "http://tempuri.org/";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "GetTokenResult", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "GetTokenResult");
          } 
        } else {
          xmlWriter.writeStartElement("GetTokenResult");
        } 
        if (this.localGetTokenResult == null)
          throw new ADBException("GetTokenResult cannot be null!!"); 
        xmlWriter.writeCharacters(this.localGetTokenResult);
        xmlWriter.writeEndElement();
      } 
      xmlWriter.writeEndElement();
    }
    
    private void writeAttribute(String prefix, String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (xmlWriter.getPrefix(namespace) == null) {
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      xmlWriter.writeAttribute(namespace, attName, attValue);
    }
    
    private void writeAttribute(String namespace, String attName, String attValue, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attValue);
      } 
    }
    
    private void writeQNameAttribute(String namespace, String attName, QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String attributeValue, attributeNamespace = qname.getNamespaceURI();
      String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
      if (attributePrefix == null)
        attributePrefix = registerPrefix(xmlWriter, attributeNamespace); 
      if (attributePrefix.trim().length() > 0) {
        attributeValue = String.valueOf(attributePrefix) + ":" + qname.getLocalPart();
      } else {
        attributeValue = qname.getLocalPart();
      } 
      if (namespace.equals("")) {
        xmlWriter.writeAttribute(attName, attributeValue);
      } else {
        registerPrefix(xmlWriter, namespace);
        xmlWriter.writeAttribute(namespace, attName, attributeValue);
      } 
    }
    
    private void writeQName(QName qname, XMLStreamWriter xmlWriter) throws XMLStreamException {
      String namespaceURI = qname.getNamespaceURI();
      if (namespaceURI != null) {
        String prefix = xmlWriter.getPrefix(namespaceURI);
        if (prefix == null) {
          prefix = generatePrefix(namespaceURI);
          xmlWriter.writeNamespace(prefix, namespaceURI);
          xmlWriter.setPrefix(prefix, namespaceURI);
        } 
        if (prefix.trim().length() > 0) {
          xmlWriter.writeCharacters(String.valueOf(prefix) + ":" + ConverterUtil.convertToString(qname));
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
        } 
      } else {
        xmlWriter.writeCharacters(ConverterUtil.convertToString(qname));
      } 
    }
    
    private void writeQNames(QName[] qnames, XMLStreamWriter xmlWriter) throws XMLStreamException {
      if (qnames != null) {
        StringBuffer stringToWrite = new StringBuffer();
        String namespaceURI = null;
        String prefix = null;
        for (int i = 0; i < qnames.length; i++) {
          if (i > 0)
            stringToWrite.append(" "); 
          namespaceURI = qnames[i].getNamespaceURI();
          if (namespaceURI != null) {
            prefix = xmlWriter.getPrefix(namespaceURI);
            if (prefix == null || prefix.length() == 0) {
              prefix = generatePrefix(namespaceURI);
              xmlWriter.writeNamespace(prefix, namespaceURI);
              xmlWriter.setPrefix(prefix, namespaceURI);
            } 
            if (prefix.trim().length() > 0) {
              stringToWrite.append(prefix).append(":").append(ConverterUtil.convertToString(qnames[i]));
            } else {
              stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
            } 
          } else {
            stringToWrite.append(ConverterUtil.convertToString(qnames[i]));
          } 
        } 
        xmlWriter.writeCharacters(stringToWrite.toString());
      } 
    }
    
    private String registerPrefix(XMLStreamWriter xmlWriter, String namespace) throws XMLStreamException {
      String prefix = xmlWriter.getPrefix(namespace);
      if (prefix == null) {
        prefix = generatePrefix(namespace);
        while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null)
          prefix = BeanUtil.getUniquePrefix(); 
        xmlWriter.writeNamespace(prefix, namespace);
        xmlWriter.setPrefix(prefix, namespace);
      } 
      return prefix;
    }
    
    public XMLStreamReader getPullParser(QName qName) throws ADBException {
      ArrayList<QName> elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      if (this.localGetTokenResultTracker) {
        elementList.add(new QName("http://tempuri.org/", "GetTokenResult"));
        if (this.localGetTokenResult != null) {
          elementList.add(ConverterUtil.convertToString(this.localGetTokenResult));
        } else {
          throw new ADBException("GetTokenResult cannot be null!!");
        } 
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static GetFunc_codeStub.GetTokenResponse parse(XMLStreamReader reader) throws Exception {
        GetFunc_codeStub.GetTokenResponse object = new GetFunc_codeStub.GetTokenResponse();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"GetTokenResponse".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (GetFunc_codeStub.GetTokenResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetTokenResult")).equals(reader.getName())) {
            String content = reader.getElementText();
            object.setGetTokenResult(ConverterUtil.convertToString(content));
            reader.next();
          } 
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          if (reader.isStartElement())
            throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
        } catch (XMLStreamException e) {
          throw new Exception(e);
        } 
        return object;
      }
    }
  }
  
  private HashMap faultExceptionNameMap = new HashMap<Object, Object>();
  
  private HashMap faultExceptionClassNameMap = new HashMap<Object, Object>();
  
  private HashMap faultMessageMap = new HashMap<Object, Object>();
  
  private static int counter = 0;
  
  private QName[] opNameArray;
  
  private static synchronized String getUniqueSuffix() {
    if (counter > 99999)
      counter = 0; 
    counter++;
    return String.valueOf(Long.toString(System.currentTimeMillis())) + "_" + counter;
  }
  
  private void populateAxisService() throws AxisFault {
    this._service = new AxisService("GetFunc_code" + getUniqueSuffix());
    addAnonymousOperations();
    this._operations = new AxisOperation[4];
    OutInAxisOperation outInAxisOperation = new OutInAxisOperation();
    outInAxisOperation.setName(new QName("http://tempuri.org/", "getCode"));
    this._service.addOperation((AxisOperation)outInAxisOperation);
    this._operations[0] = (AxisOperation)outInAxisOperation;
    outInAxisOperation = new OutInAxisOperation();
    outInAxisOperation.setName(new QName("http://tempuri.org/", "getMessage"));
    this._service.addOperation((AxisOperation)outInAxisOperation);
    this._operations[1] = (AxisOperation)outInAxisOperation;
    outInAxisOperation = new OutInAxisOperation();
    outInAxisOperation.setName(new QName("http://tempuri.org/", "getShortMessage"));
    this._service.addOperation((AxisOperation)outInAxisOperation);
    this._operations[2] = (AxisOperation)outInAxisOperation;
    outInAxisOperation = new OutInAxisOperation();
    outInAxisOperation.setName(new QName("http://tempuri.org/", "getToken"));
    this._service.addOperation((AxisOperation)outInAxisOperation);
    this._operations[3] = (AxisOperation)outInAxisOperation;
  }
  
  private void populateFaults() {}
  
  public GetFunc_codeStub(ConfigurationContext configurationContext, String targetEndpoint) throws AxisFault {
    this(configurationContext, targetEndpoint, false);
  }
  
  public GetFunc_codeStub(ConfigurationContext configurationContext) throws AxisFault {
    this(configurationContext, "http://10.7.3.128/ws/GetFunc_Code.asmx");
  }
  
  public GetFunc_codeStub() throws AxisFault {
    this("http://10.7.3.128/ws/GetFunc_Code.asmx");
  }
  
  public GetFunc_codeStub(String targetEndpoint) throws AxisFault {
    this((ConfigurationContext)null, targetEndpoint);
  }
  
  public GetCodeResponse getCode(GetCode getCode0) throws RemoteException {
    MessageContext _messageContext = null;
    try {
      OperationClient _operationClient = this._serviceClient.createClient(this._operations[0].getName());
      _operationClient.getOptions().setAction("http://tempuri.org/GetCode");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
      addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
      _messageContext = new MessageContext();
      SOAPEnvelope env = null;
      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getCode0, optimizeContent(new QName("http://tempuri.org/", "getCode")));
      this._serviceClient.addHeadersToEnvelope(env);
      _messageContext.setEnvelope(env);
      _operationClient.addMessageContext(_messageContext);
      _operationClient.execute(true);
      MessageContext _returnMessageContext = _operationClient.getMessageContext("In");
      SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
      Object object = fromOM(_returnEnv.getBody().getFirstElement(), GetCodeResponse.class, getEnvelopeNamespaces(_returnEnv));
      return (GetCodeResponse)object;
    } catch (AxisFault f) {
      OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (this.faultExceptionNameMap.containsKey(faultElt.getQName()))
          try {
            String exceptionClassName = (String)this.faultExceptionClassNameMap.get(faultElt.getQName());
            Class<?> exceptionClass = Class.forName(exceptionClassName);
            Exception ex = (Exception)exceptionClass.newInstance();
            String messageClassName = (String)this.faultMessageMap.get(faultElt.getQName());
            Class<?> messageClass = Class.forName(messageClassName);
            Object messageObject = fromOM(faultElt, messageClass, (Map)null);
            Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
            m.invoke(ex, new Object[] { messageObject });
            throw new RemoteException(ex.getMessage(), ex);
          } catch (ClassCastException e) {
            throw f;
          } catch (ClassNotFoundException e) {
            throw f;
          } catch (NoSuchMethodException e) {
            throw f;
          } catch (InvocationTargetException e) {
            throw f;
          } catch (IllegalAccessException e) {
            throw f;
          } catch (InstantiationException e) {
            throw f;
          }  
        throw f;
      } 
      throw f;
    } finally {
      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
    } 
  }
  
  public void startgetCode(GetCode getCode0, final GetFunc_codeCallbackHandler callback) throws RemoteException {
    OperationClient _operationClient = this._serviceClient.createClient(this._operations[0].getName());
    _operationClient.getOptions().setAction("http://tempuri.org/GetCode");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
    addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
    SOAPEnvelope env = null;
    final MessageContext _messageContext = new MessageContext();
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getCode0, optimizeContent(new QName("http://tempuri.org/", "getCode")));
    this._serviceClient.addHeadersToEnvelope(env);
    _messageContext.setEnvelope(env);
    _operationClient.addMessageContext(_messageContext);
    _operationClient.setCallback(new AxisCallback() {
          public void onMessage(MessageContext resultContext) {
            try {
              SOAPEnvelope resultEnv = resultContext.getEnvelope();
              Object object = GetFunc_codeStub.this.fromOM(resultEnv.getBody().getFirstElement(), GetFunc_codeStub.GetCodeResponse.class, GetFunc_codeStub.this.getEnvelopeNamespaces(resultEnv));
              callback.receiveResultgetCode((GetFunc_codeStub.GetCodeResponse)object);
            } catch (AxisFault e) {
              callback.receiveErrorgetCode((Exception)e);
            } 
          }
          
          public void onError(Exception error) {
            if (error instanceof AxisFault) {
              AxisFault f = (AxisFault)error;
              OMElement faultElt = f.getDetail();
              if (faultElt != null) {
                if (GetFunc_codeStub.this.faultExceptionNameMap.containsKey(faultElt.getQName())) {
                  try {
                    String exceptionClassName = (String)GetFunc_codeStub.this.faultExceptionClassNameMap.get(faultElt.getQName());
                    Class<?> exceptionClass = Class.forName(exceptionClassName);
                    Exception ex = (Exception)exceptionClass.newInstance();
                    String messageClassName = (String)GetFunc_codeStub.this.faultMessageMap.get(faultElt.getQName());
                    Class<?> messageClass = Class.forName(messageClassName);
                    Object messageObject = GetFunc_codeStub.this.fromOM(faultElt, messageClass, (Map)null);
                    Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
                    m.invoke(ex, new Object[] { messageObject });
                    callback.receiveErrorgetCode(new RemoteException(ex.getMessage(), ex));
                  } catch (ClassCastException e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } catch (ClassNotFoundException e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } catch (NoSuchMethodException e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } catch (InvocationTargetException e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } catch (IllegalAccessException e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } catch (InstantiationException e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } catch (AxisFault e) {
                    callback.receiveErrorgetCode((Exception)f);
                  } 
                } else {
                  callback.receiveErrorgetCode((Exception)f);
                } 
              } else {
                callback.receiveErrorgetCode((Exception)f);
              } 
            } else {
              callback.receiveErrorgetCode(error);
            } 
          }
          
          public void onFault(MessageContext faultContext) {
            AxisFault fault = Utils.getInboundFaultFromMessageContext(faultContext);
            onError((Exception)fault);
          }
          
          public void onComplete() {
            try {
              _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            } catch (AxisFault axisFault) {
              callback.receiveErrorgetCode((Exception)axisFault);
            } 
          }
        });
    CallbackReceiver _callbackReceiver = null;
    if (this._operations[0].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new CallbackReceiver();
      this._operations[0].setMessageReceiver((MessageReceiver)_callbackReceiver);
    } 
    _operationClient.execute(false);
  }
  
  public GetMessageResponse getMessage(GetMessage getMessage2) throws RemoteException {
    MessageContext _messageContext = null;
    try {
      OperationClient _operationClient = this._serviceClient.createClient(this._operations[1].getName());
      _operationClient.getOptions().setAction("http://tempuri.org/GetMessage");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
      addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
      _messageContext = new MessageContext();
      SOAPEnvelope env = null;
      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getMessage2, optimizeContent(new QName("http://tempuri.org/", "getMessage")));
      this._serviceClient.addHeadersToEnvelope(env);
      _messageContext.setEnvelope(env);
      _operationClient.addMessageContext(_messageContext);
      _operationClient.execute(true);
      MessageContext _returnMessageContext = _operationClient.getMessageContext("In");
      SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
      Object object = fromOM(_returnEnv.getBody().getFirstElement(), GetMessageResponse.class, getEnvelopeNamespaces(_returnEnv));
      return (GetMessageResponse)object;
    } catch (AxisFault f) {
      OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (this.faultExceptionNameMap.containsKey(faultElt.getQName()))
          try {
            String exceptionClassName = (String)this.faultExceptionClassNameMap.get(faultElt.getQName());
            Class<?> exceptionClass = Class.forName(exceptionClassName);
            Exception ex = (Exception)exceptionClass.newInstance();
            String messageClassName = (String)this.faultMessageMap.get(faultElt.getQName());
            Class<?> messageClass = Class.forName(messageClassName);
            Object messageObject = fromOM(faultElt, messageClass, (Map)null);
            Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
            m.invoke(ex, new Object[] { messageObject });
            throw new RemoteException(ex.getMessage(), ex);
          } catch (ClassCastException e) {
            throw f;
          } catch (ClassNotFoundException e) {
            throw f;
          } catch (NoSuchMethodException e) {
            throw f;
          } catch (InvocationTargetException e) {
            throw f;
          } catch (IllegalAccessException e) {
            throw f;
          } catch (InstantiationException e) {
            throw f;
          }  
        throw f;
      } 
      throw f;
    } finally {
      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
    } 
  }
  
  public void startgetMessage(GetMessage getMessage2, final GetFunc_codeCallbackHandler callback) throws RemoteException {
    OperationClient _operationClient = this._serviceClient.createClient(this._operations[1].getName());
    _operationClient.getOptions().setAction("http://tempuri.org/GetMessage");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
    addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
    SOAPEnvelope env = null;
    final MessageContext _messageContext = new MessageContext();
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getMessage2, optimizeContent(new QName("http://tempuri.org/", "getMessage")));
    this._serviceClient.addHeadersToEnvelope(env);
    _messageContext.setEnvelope(env);
    _operationClient.addMessageContext(_messageContext);
    _operationClient.setCallback(new AxisCallback() {
          public void onMessage(MessageContext resultContext) {
            try {
              SOAPEnvelope resultEnv = resultContext.getEnvelope();
              Object object = GetFunc_codeStub.this.fromOM(resultEnv.getBody().getFirstElement(), GetFunc_codeStub.GetMessageResponse.class, GetFunc_codeStub.this.getEnvelopeNamespaces(resultEnv));
              callback.receiveResultgetMessage((GetFunc_codeStub.GetMessageResponse)object);
            } catch (AxisFault e) {
              callback.receiveErrorgetMessage((Exception)e);
            } 
          }
          
          public void onError(Exception error) {
            if (error instanceof AxisFault) {
              AxisFault f = (AxisFault)error;
              OMElement faultElt = f.getDetail();
              if (faultElt != null) {
                if (GetFunc_codeStub.this.faultExceptionNameMap.containsKey(faultElt.getQName())) {
                  try {
                    String exceptionClassName = (String)GetFunc_codeStub.this.faultExceptionClassNameMap.get(faultElt.getQName());
                    Class<?> exceptionClass = Class.forName(exceptionClassName);
                    Exception ex = (Exception)exceptionClass.newInstance();
                    String messageClassName = (String)GetFunc_codeStub.this.faultMessageMap.get(faultElt.getQName());
                    Class<?> messageClass = Class.forName(messageClassName);
                    Object messageObject = GetFunc_codeStub.this.fromOM(faultElt, messageClass, (Map)null);
                    Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
                    m.invoke(ex, new Object[] { messageObject });
                    callback.receiveErrorgetMessage(new RemoteException(ex.getMessage(), ex));
                  } catch (ClassCastException e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } catch (ClassNotFoundException e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } catch (NoSuchMethodException e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } catch (InvocationTargetException e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } catch (IllegalAccessException e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } catch (InstantiationException e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } catch (AxisFault e) {
                    callback.receiveErrorgetMessage((Exception)f);
                  } 
                } else {
                  callback.receiveErrorgetMessage((Exception)f);
                } 
              } else {
                callback.receiveErrorgetMessage((Exception)f);
              } 
            } else {
              callback.receiveErrorgetMessage(error);
            } 
          }
          
          public void onFault(MessageContext faultContext) {
            AxisFault fault = Utils.getInboundFaultFromMessageContext(faultContext);
            onError((Exception)fault);
          }
          
          public void onComplete() {
            try {
              _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            } catch (AxisFault axisFault) {
              callback.receiveErrorgetMessage((Exception)axisFault);
            } 
          }
        });
    CallbackReceiver _callbackReceiver = null;
    if (this._operations[1].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new CallbackReceiver();
      this._operations[1].setMessageReceiver((MessageReceiver)_callbackReceiver);
    } 
    _operationClient.execute(false);
  }
  
  public GetShortMessageResponse getShortMessage(GetShortMessage getShortMessage4) throws RemoteException {
    MessageContext _messageContext = null;
    try {
      OperationClient _operationClient = this._serviceClient.createClient(this._operations[2].getName());
      _operationClient.getOptions().setAction("http://tempuri.org/GetShortMessage");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
      addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
      _messageContext = new MessageContext();
      SOAPEnvelope env = null;
      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getShortMessage4, optimizeContent(new QName("http://tempuri.org/", "getShortMessage")));
      this._serviceClient.addHeadersToEnvelope(env);
      _messageContext.setEnvelope(env);
      _operationClient.addMessageContext(_messageContext);
      _operationClient.execute(true);
      MessageContext _returnMessageContext = _operationClient.getMessageContext("In");
      SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
      Object object = fromOM(_returnEnv.getBody().getFirstElement(), GetShortMessageResponse.class, getEnvelopeNamespaces(_returnEnv));
      return (GetShortMessageResponse)object;
    } catch (AxisFault f) {
      OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (this.faultExceptionNameMap.containsKey(faultElt.getQName()))
          try {
            String exceptionClassName = (String)this.faultExceptionClassNameMap.get(faultElt.getQName());
            Class<?> exceptionClass = Class.forName(exceptionClassName);
            Exception ex = (Exception)exceptionClass.newInstance();
            String messageClassName = (String)this.faultMessageMap.get(faultElt.getQName());
            Class<?> messageClass = Class.forName(messageClassName);
            Object messageObject = fromOM(faultElt, messageClass, (Map)null);
            Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
            m.invoke(ex, new Object[] { messageObject });
            throw new RemoteException(ex.getMessage(), ex);
          } catch (ClassCastException e) {
            throw f;
          } catch (ClassNotFoundException e) {
            throw f;
          } catch (NoSuchMethodException e) {
            throw f;
          } catch (InvocationTargetException e) {
            throw f;
          } catch (IllegalAccessException e) {
            throw f;
          } catch (InstantiationException e) {
            throw f;
          }  
        throw f;
      } 
      throw f;
    } finally {
      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
    } 
  }
  
  public void startgetShortMessage(GetShortMessage getShortMessage4, final GetFunc_codeCallbackHandler callback) throws RemoteException {
    OperationClient _operationClient = this._serviceClient.createClient(this._operations[2].getName());
    _operationClient.getOptions().setAction("http://tempuri.org/GetShortMessage");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
    addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
    SOAPEnvelope env = null;
    final MessageContext _messageContext = new MessageContext();
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getShortMessage4, optimizeContent(new QName("http://tempuri.org/", "getShortMessage")));
    this._serviceClient.addHeadersToEnvelope(env);
    _messageContext.setEnvelope(env);
    _operationClient.addMessageContext(_messageContext);
    _operationClient.setCallback(new AxisCallback() {
          public void onMessage(MessageContext resultContext) {
            try {
              SOAPEnvelope resultEnv = resultContext.getEnvelope();
              Object object = GetFunc_codeStub.this.fromOM(resultEnv.getBody().getFirstElement(), GetFunc_codeStub.GetShortMessageResponse.class, GetFunc_codeStub.this.getEnvelopeNamespaces(resultEnv));
              callback.receiveResultgetShortMessage((GetFunc_codeStub.GetShortMessageResponse)object);
            } catch (AxisFault e) {
              callback.receiveErrorgetShortMessage((Exception)e);
            } 
          }
          
          public void onError(Exception error) {
            if (error instanceof AxisFault) {
              AxisFault f = (AxisFault)error;
              OMElement faultElt = f.getDetail();
              if (faultElt != null) {
                if (GetFunc_codeStub.this.faultExceptionNameMap.containsKey(faultElt.getQName())) {
                  try {
                    String exceptionClassName = (String)GetFunc_codeStub.this.faultExceptionClassNameMap.get(faultElt.getQName());
                    Class<?> exceptionClass = Class.forName(exceptionClassName);
                    Exception ex = (Exception)exceptionClass.newInstance();
                    String messageClassName = (String)GetFunc_codeStub.this.faultMessageMap.get(faultElt.getQName());
                    Class<?> messageClass = Class.forName(messageClassName);
                    Object messageObject = GetFunc_codeStub.this.fromOM(faultElt, messageClass, (Map)null);
                    Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
                    m.invoke(ex, new Object[] { messageObject });
                    callback.receiveErrorgetShortMessage(new RemoteException(ex.getMessage(), ex));
                  } catch (ClassCastException e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } catch (ClassNotFoundException e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } catch (NoSuchMethodException e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } catch (InvocationTargetException e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } catch (IllegalAccessException e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } catch (InstantiationException e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } catch (AxisFault e) {
                    callback.receiveErrorgetShortMessage((Exception)f);
                  } 
                } else {
                  callback.receiveErrorgetShortMessage((Exception)f);
                } 
              } else {
                callback.receiveErrorgetShortMessage((Exception)f);
              } 
            } else {
              callback.receiveErrorgetShortMessage(error);
            } 
          }
          
          public void onFault(MessageContext faultContext) {
            AxisFault fault = Utils.getInboundFaultFromMessageContext(faultContext);
            onError((Exception)fault);
          }
          
          public void onComplete() {
            try {
              _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            } catch (AxisFault axisFault) {
              callback.receiveErrorgetShortMessage((Exception)axisFault);
            } 
          }
        });
    CallbackReceiver _callbackReceiver = null;
    if (this._operations[2].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new CallbackReceiver();
      this._operations[2].setMessageReceiver((MessageReceiver)_callbackReceiver);
    } 
    _operationClient.execute(false);
  }
  
  public GetTokenResponse getToken(GetToken getToken6) throws RemoteException {
    MessageContext _messageContext = null;
    try {
      OperationClient _operationClient = this._serviceClient.createClient(this._operations[3].getName());
      _operationClient.getOptions().setAction("http://tempuri.org/GetToken");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
      addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
      _messageContext = new MessageContext();
      SOAPEnvelope env = null;
      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getToken6, optimizeContent(new QName("http://tempuri.org/", "getToken")));
      this._serviceClient.addHeadersToEnvelope(env);
      _messageContext.setEnvelope(env);
      _operationClient.addMessageContext(_messageContext);
      _operationClient.execute(true);
      MessageContext _returnMessageContext = _operationClient.getMessageContext("In");
      SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
      Object object = fromOM(_returnEnv.getBody().getFirstElement(), GetTokenResponse.class, getEnvelopeNamespaces(_returnEnv));
      return (GetTokenResponse)object;
    } catch (AxisFault f) {
      OMElement faultElt = f.getDetail();
      if (faultElt != null) {
        if (this.faultExceptionNameMap.containsKey(faultElt.getQName()))
          try {
            String exceptionClassName = (String)this.faultExceptionClassNameMap.get(faultElt.getQName());
            Class<?> exceptionClass = Class.forName(exceptionClassName);
            Exception ex = (Exception)exceptionClass.newInstance();
            String messageClassName = (String)this.faultMessageMap.get(faultElt.getQName());
            Class<?> messageClass = Class.forName(messageClassName);
            Object messageObject = fromOM(faultElt, messageClass, (Map)null);
            Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
            m.invoke(ex, new Object[] { messageObject });
            throw new RemoteException(ex.getMessage(), ex);
          } catch (ClassCastException e) {
            throw f;
          } catch (ClassNotFoundException e) {
            throw f;
          } catch (NoSuchMethodException e) {
            throw f;
          } catch (InvocationTargetException e) {
            throw f;
          } catch (IllegalAccessException e) {
            throw f;
          } catch (InstantiationException e) {
            throw f;
          }  
        throw f;
      } 
      throw f;
    } finally {
      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
    } 
  }
  
  public void startgetToken(GetToken getToken6, final GetFunc_codeCallbackHandler callback) throws RemoteException {
    OperationClient _operationClient = this._serviceClient.createClient(this._operations[3].getName());
    _operationClient.getOptions().setAction("http://tempuri.org/GetToken");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
    addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
    SOAPEnvelope env = null;
    final MessageContext _messageContext = new MessageContext();
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), getToken6, optimizeContent(new QName("http://tempuri.org/", "getToken")));
    this._serviceClient.addHeadersToEnvelope(env);
    _messageContext.setEnvelope(env);
    _operationClient.addMessageContext(_messageContext);
    _operationClient.setCallback(new AxisCallback() {
          public void onMessage(MessageContext resultContext) {
            try {
              SOAPEnvelope resultEnv = resultContext.getEnvelope();
              Object object = GetFunc_codeStub.this.fromOM(resultEnv.getBody().getFirstElement(), GetFunc_codeStub.GetTokenResponse.class, GetFunc_codeStub.this.getEnvelopeNamespaces(resultEnv));
              callback.receiveResultgetToken((GetFunc_codeStub.GetTokenResponse)object);
            } catch (AxisFault e) {
              callback.receiveErrorgetToken((Exception)e);
            } 
          }
          
          public void onError(Exception error) {
            if (error instanceof AxisFault) {
              AxisFault f = (AxisFault)error;
              OMElement faultElt = f.getDetail();
              if (faultElt != null) {
                if (GetFunc_codeStub.this.faultExceptionNameMap.containsKey(faultElt.getQName())) {
                  try {
                    String exceptionClassName = (String)GetFunc_codeStub.this.faultExceptionClassNameMap.get(faultElt.getQName());
                    Class<?> exceptionClass = Class.forName(exceptionClassName);
                    Exception ex = (Exception)exceptionClass.newInstance();
                    String messageClassName = (String)GetFunc_codeStub.this.faultMessageMap.get(faultElt.getQName());
                    Class<?> messageClass = Class.forName(messageClassName);
                    Object messageObject = GetFunc_codeStub.this.fromOM(faultElt, messageClass, (Map)null);
                    Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
                    m.invoke(ex, new Object[] { messageObject });
                    callback.receiveErrorgetToken(new RemoteException(ex.getMessage(), ex));
                  } catch (ClassCastException e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } catch (ClassNotFoundException e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } catch (NoSuchMethodException e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } catch (InvocationTargetException e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } catch (IllegalAccessException e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } catch (InstantiationException e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } catch (AxisFault e) {
                    callback.receiveErrorgetToken((Exception)f);
                  } 
                } else {
                  callback.receiveErrorgetToken((Exception)f);
                } 
              } else {
                callback.receiveErrorgetToken((Exception)f);
              } 
            } else {
              callback.receiveErrorgetToken(error);
            } 
          }
          
          public void onFault(MessageContext faultContext) {
            AxisFault fault = Utils.getInboundFaultFromMessageContext(faultContext);
            onError((Exception)fault);
          }
          
          public void onComplete() {
            try {
              _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            } catch (AxisFault axisFault) {
              callback.receiveErrorgetToken((Exception)axisFault);
            } 
          }
        });
    CallbackReceiver _callbackReceiver = null;
    if (this._operations[3].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new CallbackReceiver();
      this._operations[3].setMessageReceiver((MessageReceiver)_callbackReceiver);
    } 
    _operationClient.execute(false);
  }
  
  private Map getEnvelopeNamespaces(SOAPEnvelope env) {
    Map<Object, Object> returnMap = new HashMap<Object, Object>();
    Iterator<OMNamespace> namespaceIterator = env.getAllDeclaredNamespaces();
    while (namespaceIterator.hasNext()) {
      OMNamespace ns = namespaceIterator.next();
      returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
    } 
    return returnMap;
  }
  
  public GetFunc_codeStub(ConfigurationContext configurationContext, String targetEndpoint, boolean useSeparateListener) throws AxisFault {
    this.opNameArray = null;
    populateAxisService();
    populateFaults();
    this._serviceClient = new ServiceClient(configurationContext, this._service);
    this._serviceClient.getOptions().setTo(new EndpointReference(targetEndpoint));
    this._serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
    this._serviceClient.getOptions().setSoapVersionURI("http://www.w3.org/2003/05/soap-envelope");
  }
  
  private boolean optimizeContent(QName opName) {
    if (this.opNameArray == null)
      return false; 
    for (int i = 0; i < this.opNameArray.length; i++) {
      if (opName.equals(this.opNameArray[i]))
        return true; 
    } 
    return false;
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetMessageResponse parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetMessageResponse object = new GetFunc_codeStub.GetMessageResponse();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetMessageResponse".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetMessageResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetMessageResult")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setGetMessageResult(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetShortMessageResponse parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetShortMessageResponse object = new GetFunc_codeStub.GetShortMessageResponse();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetShortMessageResponse".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetShortMessageResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetShortMessageResult")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setGetShortMessageResult(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetCodeResponse parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetCodeResponse object = new GetFunc_codeStub.GetCodeResponse();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetCodeResponse".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetCodeResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetCodeResult")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setGetCodeResult(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetTokenResponse parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetTokenResponse object = new GetFunc_codeStub.GetTokenResponse();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetTokenResponse".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetTokenResponse)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "GetTokenResult")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setGetTokenResult(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class ExtensionMapper {
    public static Object getTypeObject(String namespaceURI, String typeName, XMLStreamReader reader) throws Exception {
      throw new ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetCode parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetCode object = new GetFunc_codeStub.GetCode();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetCode".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetCode)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "func_code")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setFunc_code(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetMessage parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetMessage object = new GetFunc_codeStub.GetMessage();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetMessage".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetMessage)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "a0190")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setA0190(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetShortMessage parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetShortMessage object = new GetFunc_codeStub.GetShortMessage();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetShortMessage".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetShortMessage)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "a0190")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setA0190(ConverterUtil.convertToString(content));
          reader.next();
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  public static class Factory {
    public static GetFunc_codeStub.GetToken parse(XMLStreamReader reader) throws Exception {
      GetFunc_codeStub.GetToken object = new GetFunc_codeStub.GetToken();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"GetToken".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (GetFunc_codeStub.GetToken)GetFunc_codeStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "UserCode")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setUserCode(ConverterUtil.convertToString(content));
          reader.next();
        } 
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://tempuri.org/", "ExpirDate")).equals(reader.getName())) {
          String content = reader.getElementText();
          object.setExpirDate(ConverterUtil.convertToInt(content));
          reader.next();
        } else {
          throw new ADBException("Unexpected subelement " + reader.getLocalName());
        } 
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        if (reader.isStartElement())
          throw new ADBException("Unexpected subelement " + reader.getLocalName()); 
      } catch (XMLStreamException e) {
        throw new Exception(e);
      } 
      return object;
    }
  }
  
  private OMElement toOM(GetCode param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetCode.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetCodeResponse param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetCodeResponse.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetMessage param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetMessage.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetMessageResponse param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetMessageResponse.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetShortMessage param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetShortMessage.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetShortMessageResponse param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetShortMessageResponse.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetToken param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetToken.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetTokenResponse param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetTokenResponse.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private SOAPEnvelope toEnvelope(SOAPFactory factory, GetCode param, boolean optimizeContent) throws AxisFault {
    try {
      SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild((OMNode)param.getOMElement(GetCode.MY_QNAME, (OMFactory)factory));
      return emptyEnvelope;
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private SOAPEnvelope toEnvelope(SOAPFactory factory, GetMessage param, boolean optimizeContent) throws AxisFault {
    try {
      SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild((OMNode)param.getOMElement(GetMessage.MY_QNAME, (OMFactory)factory));
      return emptyEnvelope;
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private SOAPEnvelope toEnvelope(SOAPFactory factory, GetShortMessage param, boolean optimizeContent) throws AxisFault {
    try {
      SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild((OMNode)param.getOMElement(GetShortMessage.MY_QNAME, (OMFactory)factory));
      return emptyEnvelope;
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private SOAPEnvelope toEnvelope(SOAPFactory factory, GetToken param, boolean optimizeContent) throws AxisFault {
    try {
      SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild((OMNode)param.getOMElement(GetToken.MY_QNAME, (OMFactory)factory));
      return emptyEnvelope;
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private SOAPEnvelope toEnvelope(SOAPFactory factory) {
    return factory.getDefaultEnvelope();
  }
  
  private Object fromOM(OMElement param, Class type, Map extraNamespaces) throws AxisFault {
    try {
      if (GetCode.class.equals(type))
        return GetCode.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetCodeResponse.class.equals(type))
        return GetCodeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetMessage.class.equals(type))
        return GetMessage.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetMessageResponse.class.equals(type))
        return GetMessageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetShortMessage.class.equals(type))
        return GetShortMessage.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetShortMessageResponse.class.equals(type))
        return GetShortMessageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetToken.class.equals(type))
        return GetToken.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetTokenResponse.class.equals(type))
        return GetTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
    } catch (Exception e) {
      throw AxisFault.makeFault(e);
    } 
    return null;
  }
}
