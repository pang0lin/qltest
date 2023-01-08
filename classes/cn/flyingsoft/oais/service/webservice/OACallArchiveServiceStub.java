package cn.flyingsoft.oais.service.webservice;

import com.js.oa.form.WorkFlowArchives;
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

public class OACallArchiveServiceStub extends Stub {
  protected AxisOperation[] _operations;
  
  public static class BaseObject implements ADBBean {
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://system.di.oais.flyingsoft.cn/xsd"))
        return "ns1"; 
      return BeanUtil.getUniquePrefix();
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
        new ADBDataSource(this, parentQName) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            OACallArchiveServiceStub.BaseObject.this.serialize(this.parentQName, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(
          parentQName, factory, (OMDataSource)aDBDataSource);
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
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://system.di.oais.flyingsoft.cn/xsd");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              String.valueOf(namespacePrefix) + ":BaseObject", 
              (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              "BaseObject", 
              (XMLStreamWriter)xmlWriter);
        } 
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
      ArrayList elementList = new ArrayList();
      ArrayList attribList = new ArrayList();
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static OACallArchiveServiceStub.BaseObject parse(XMLStreamReader reader) throws Exception {
        OACallArchiveServiceStub.BaseObject object = 
          new OACallArchiveServiceStub.BaseObject();
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
              if (!"BaseObject".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (OACallArchiveServiceStub.BaseObject)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(
                    nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
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
  
  public static class Exception implements ADBBean {
    protected Object localException;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://webService.service.oais.flyingsoft.cn"))
        return "ns3"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localExceptionTracker = false;
    
    public Object getException() {
      return this.localException;
    }
    
    public void setException(Object param) {
      if (param != null) {
        this.localExceptionTracker = true;
      } else {
        this.localExceptionTracker = true;
      } 
      this.localException = param;
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
        new ADBDataSource(this, parentQName) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            OACallArchiveServiceStub.Exception.this.serialize(this.parentQName, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(
          parentQName, factory, (OMDataSource)aDBDataSource);
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
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://webService.service.oais.flyingsoft.cn");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              String.valueOf(namespacePrefix) + ":Exception", 
              (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", 
              "Exception", 
              (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localExceptionTracker)
        if (this.localException != null) {
          if (this.localException instanceof ADBBean) {
            ((ADBBean)this.localException).serialize(
                new QName("http://webService.service.oais.flyingsoft.cn", "Exception"), 
                factory, xmlWriter, true);
          } else {
            String namespace2 = "http://webService.service.oais.flyingsoft.cn";
            if (!namespace2.equals("")) {
              String prefix2 = xmlWriter.getPrefix(namespace2);
              if (prefix2 == null) {
                prefix2 = generatePrefix(namespace2);
                xmlWriter.writeStartElement(prefix2, "Exception", namespace2);
                xmlWriter.writeNamespace(prefix2, namespace2);
                xmlWriter.setPrefix(prefix2, namespace2);
              } else {
                xmlWriter.writeStartElement(namespace2, "Exception");
              } 
            } else {
              xmlWriter.writeStartElement("Exception");
            } 
            ConverterUtil.serializeAnyType(this.localException, (XMLStreamWriter)xmlWriter);
            xmlWriter.writeEndElement();
          } 
        } else {
          String namespace2 = "http://webService.service.oais.flyingsoft.cn";
          if (!namespace2.equals("")) {
            String prefix2 = xmlWriter.getPrefix(namespace2);
            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);
              xmlWriter.writeStartElement(prefix2, "Exception", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);
            } else {
              xmlWriter.writeStartElement(namespace2, "Exception");
            } 
          } else {
            xmlWriter.writeStartElement("Exception");
          } 
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
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
      if (this.localExceptionTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", 
              "Exception"));
        elementList.add((this.localException == null) ? null : 
            this.localException);
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static OACallArchiveServiceStub.Exception parse(XMLStreamReader reader) throws Exception {
        OACallArchiveServiceStub.Exception object = 
          new OACallArchiveServiceStub.Exception();
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
              if (!"Exception".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (OACallArchiveServiceStub.Exception)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(
                    nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "Exception")).equals(reader.getName())) {
            object.setException(ConverterUtil.getAnyTypeObject(reader, 
                  OACallArchiveServiceStub.ExtensionMapper.class));
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
  
  public static class ExceptionE implements ADBBean {
    public static final QName MY_QNAME = new QName("http://webService.service.oais.flyingsoft.cn", "Exception", "ns3");
    
    protected OACallArchiveServiceStub.Exception localException;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://webService.service.oais.flyingsoft.cn"))
        return "ns3"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localExceptionTracker = false;
    
    public OACallArchiveServiceStub.Exception getException() {
      return this.localException;
    }
    
    public void setException(OACallArchiveServiceStub.Exception param) {
      if (param != null) {
        this.localExceptionTracker = true;
      } else {
        this.localExceptionTracker = true;
      } 
      this.localException = param;
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
            OACallArchiveServiceStub.ExceptionE.this.serialize(OACallArchiveServiceStub.ExceptionE.MY_QNAME, factory, xmlWriter);
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
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://webService.service.oais.flyingsoft.cn");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":Exception", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "Exception", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localExceptionTracker)
        if (this.localException == null) {
          String namespace2 = "http://webService.service.oais.flyingsoft.cn";
          if (!namespace2.equals("")) {
            String prefix2 = xmlWriter.getPrefix(namespace2);
            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);
              xmlWriter.writeStartElement(prefix2, "Exception", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);
            } else {
              xmlWriter.writeStartElement(namespace2, "Exception");
            } 
          } else {
            xmlWriter.writeStartElement("Exception");
          } 
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
          xmlWriter.writeEndElement();
        } else {
          this.localException.serialize(new QName("http://webService.service.oais.flyingsoft.cn", "Exception"), factory, xmlWriter);
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
      if (this.localExceptionTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", "Exception"));
        elementList.add((this.localException == null) ? null : this.localException);
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static OACallArchiveServiceStub.ExceptionE parse(XMLStreamReader reader) throws Exception {
        OACallArchiveServiceStub.ExceptionE object = new OACallArchiveServiceStub.ExceptionE();
        String nillableValue = null;
        String prefix = "";
        String namespaceuri = "";
        try {
          while (!reader.isStartElement() && !reader.isEndElement())
            reader.next(); 
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
            while (!reader.isEndElement())
              reader.next(); 
            return null;
          } 
          if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
            String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
            if (fullTypeName != null) {
              String nsPrefix = null;
              if (fullTypeName.indexOf(":") > -1)
                nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
              nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
              String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
              if (!"Exception".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (OACallArchiveServiceStub.ExceptionE)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "Exception")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              object.setException(null);
              reader.next();
              reader.next();
            } else {
              object.setException(OACallArchiveServiceStub.Exception.Factory.parse(reader));
              reader.next();
            } 
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
  
  public static class FinishWorkFlow implements ADBBean {
    public static final QName MY_QNAME = new QName("http://webService.service.oais.flyingsoft.cn", "finishWorkFlow", "ns3");
    
    protected String localUsername;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://webService.service.oais.flyingsoft.cn"))
        return "ns3"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localUsernameTracker = false;
    
    protected String localWorkflowID;
    
    public String getUsername() {
      return this.localUsername;
    }
    
    public void setUsername(String param) {
      if (param != null) {
        this.localUsernameTracker = true;
      } else {
        this.localUsernameTracker = true;
      } 
      this.localUsername = param;
    }
    
    protected boolean localWorkflowIDTracker = false;
    
    protected String localStatus;
    
    public String getWorkflowID() {
      return this.localWorkflowID;
    }
    
    public void setWorkflowID(String param) {
      if (param != null) {
        this.localWorkflowIDTracker = true;
      } else {
        this.localWorkflowIDTracker = true;
      } 
      this.localWorkflowID = param;
    }
    
    protected boolean localStatusTracker = false;
    
    protected String localDataHavaRightlist;
    
    public String getStatus() {
      return this.localStatus;
    }
    
    public void setStatus(String param) {
      if (param != null) {
        this.localStatusTracker = true;
      } else {
        this.localStatusTracker = true;
      } 
      this.localStatus = param;
    }
    
    protected boolean localDataHavaRightlistTracker = false;
    
    public String getDataHavaRightlist() {
      return this.localDataHavaRightlist;
    }
    
    public void setDataHavaRightlist(String param) {
      if (param != null) {
        this.localDataHavaRightlistTracker = true;
      } else {
        this.localDataHavaRightlistTracker = true;
      } 
      this.localDataHavaRightlist = param;
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
            OACallArchiveServiceStub.FinishWorkFlow.this.serialize(OACallArchiveServiceStub.FinishWorkFlow.MY_QNAME, factory, xmlWriter);
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
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://webService.service.oais.flyingsoft.cn");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":finishWorkFlow", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "finishWorkFlow", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.localUsernameTracker) {
        namespace = "http://webService.service.oais.flyingsoft.cn";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "username", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "username");
          } 
        } else {
          xmlWriter.writeStartElement("username");
        } 
        if (this.localUsername == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localUsername);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localWorkflowIDTracker) {
        namespace = "http://webService.service.oais.flyingsoft.cn";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "workflowID", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "workflowID");
          } 
        } else {
          xmlWriter.writeStartElement("workflowID");
        } 
        if (this.localWorkflowID == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localWorkflowID);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localStatusTracker) {
        namespace = "http://webService.service.oais.flyingsoft.cn";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "status", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "status");
          } 
        } else {
          xmlWriter.writeStartElement("status");
        } 
        if (this.localStatus == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localStatus);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localDataHavaRightlistTracker) {
        namespace = "http://webService.service.oais.flyingsoft.cn";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "dataHavaRightlist", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "dataHavaRightlist");
          } 
        } else {
          xmlWriter.writeStartElement("dataHavaRightlist");
        } 
        if (this.localDataHavaRightlist == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localDataHavaRightlist);
        } 
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
      if (this.localUsernameTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", "username"));
        elementList.add((this.localUsername == null) ? null : ConverterUtil.convertToString(this.localUsername));
      } 
      if (this.localWorkflowIDTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", "workflowID"));
        elementList.add((this.localWorkflowID == null) ? null : ConverterUtil.convertToString(this.localWorkflowID));
      } 
      if (this.localStatusTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", "status"));
        elementList.add((this.localStatus == null) ? null : ConverterUtil.convertToString(this.localStatus));
      } 
      if (this.localDataHavaRightlistTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", "dataHavaRightlist"));
        elementList.add((this.localDataHavaRightlist == null) ? null : ConverterUtil.convertToString(this.localDataHavaRightlist));
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static OACallArchiveServiceStub.FinishWorkFlow parse(XMLStreamReader reader) throws Exception {
        OACallArchiveServiceStub.FinishWorkFlow object = new OACallArchiveServiceStub.FinishWorkFlow();
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
              if (!"finishWorkFlow".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (OACallArchiveServiceStub.FinishWorkFlow)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "username")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setUsername(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "workflowID")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setWorkflowID(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "status")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setStatus(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "dataHavaRightlist")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setDataHavaRightlist(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
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
  
  public static class GetWfUsingFormEntityResponse implements ADBBean {
    public static final QName MY_QNAME = new QName("http://webService.service.oais.flyingsoft.cn", "getWfUsingFormEntityResponse", "ns3");
    
    protected OACallArchiveServiceStub.OsFormEntity local_return;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://webService.service.oais.flyingsoft.cn"))
        return "ns3"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean local_returnTracker = false;
    
    public OACallArchiveServiceStub.OsFormEntity get_return() {
      return this.local_return;
    }
    
    public void set_return(OACallArchiveServiceStub.OsFormEntity param) {
      if (param != null) {
        this.local_returnTracker = true;
      } else {
        this.local_returnTracker = true;
      } 
      this.local_return = param;
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
            OACallArchiveServiceStub.GetWfUsingFormEntityResponse.this.serialize(OACallArchiveServiceStub.GetWfUsingFormEntityResponse.MY_QNAME, factory, xmlWriter);
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
        String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://webService.service.oais.flyingsoft.cn");
        if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":getWfUsingFormEntityResponse", (XMLStreamWriter)xmlWriter);
        } else {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "getWfUsingFormEntityResponse", (XMLStreamWriter)xmlWriter);
        } 
      } 
      if (this.local_returnTracker)
        if (this.local_return == null) {
          String namespace2 = "http://webService.service.oais.flyingsoft.cn";
          if (!namespace2.equals("")) {
            String prefix2 = xmlWriter.getPrefix(namespace2);
            if (prefix2 == null) {
              prefix2 = generatePrefix(namespace2);
              xmlWriter.writeStartElement(prefix2, "return", namespace2);
              xmlWriter.writeNamespace(prefix2, namespace2);
              xmlWriter.setPrefix(prefix2, namespace2);
            } else {
              xmlWriter.writeStartElement(namespace2, "return");
            } 
          } else {
            xmlWriter.writeStartElement("return");
          } 
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
          xmlWriter.writeEndElement();
        } else {
          this.local_return.serialize(new QName("http://webService.service.oais.flyingsoft.cn", "return"), factory, xmlWriter);
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
      if (this.local_returnTracker) {
        elementList.add(new QName("http://webService.service.oais.flyingsoft.cn", "return"));
        elementList.add((this.local_return == null) ? null : this.local_return);
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static OACallArchiveServiceStub.GetWfUsingFormEntityResponse parse(XMLStreamReader reader) throws Exception {
        OACallArchiveServiceStub.GetWfUsingFormEntityResponse object = new OACallArchiveServiceStub.GetWfUsingFormEntityResponse();
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
              if (!"getWfUsingFormEntityResponse".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (OACallArchiveServiceStub.GetWfUsingFormEntityResponse)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "return")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
              object.set_return(null);
              reader.next();
              reader.next();
            } else {
              object.set_return(OACallArchiveServiceStub.OsFormEntity.Factory.parse(reader));
              reader.next();
            } 
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
  
  public static class OsFormEntity extends BaseObject implements ADBBean {
    protected String localCreate;
    
    private static String generatePrefix(String namespace) {
      if (namespace.equals("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd"))
        return "ns2"; 
      return BeanUtil.getUniquePrefix();
    }
    
    protected boolean localCreateTracker = false;
    
    protected String localCreateTime;
    
    public String getCreate() {
      return this.localCreate;
    }
    
    public void setCreate(String param) {
      if (param != null) {
        this.localCreateTracker = true;
      } else {
        this.localCreateTracker = true;
      } 
      this.localCreate = param;
    }
    
    protected boolean localCreateTimeTracker = false;
    
    protected String localDescription;
    
    public String getCreateTime() {
      return this.localCreateTime;
    }
    
    public void setCreateTime(String param) {
      if (param != null) {
        this.localCreateTimeTracker = true;
      } else {
        this.localCreateTimeTracker = true;
      } 
      this.localCreateTime = param;
    }
    
    protected boolean localDescriptionTracker = false;
    
    protected String localFormId;
    
    public String getDescription() {
      return this.localDescription;
    }
    
    public void setDescription(String param) {
      if (param != null) {
        this.localDescriptionTracker = true;
      } else {
        this.localDescriptionTracker = true;
      } 
      this.localDescription = param;
    }
    
    protected boolean localFormIdTracker = false;
    
    protected String localFormJs;
    
    public String getFormId() {
      return this.localFormId;
    }
    
    public void setFormId(String param) {
      if (param != null) {
        this.localFormIdTracker = true;
      } else {
        this.localFormIdTracker = true;
      } 
      this.localFormId = param;
    }
    
    protected boolean localFormJsTracker = false;
    
    protected String localFormJsHtml;
    
    public String getFormJs() {
      return this.localFormJs;
    }
    
    public void setFormJs(String param) {
      if (param != null) {
        this.localFormJsTracker = true;
      } else {
        this.localFormJsTracker = true;
      } 
      this.localFormJs = param;
    }
    
    protected boolean localFormJsHtmlTracker = false;
    
    protected long localFormTypeID;
    
    public String getFormJsHtml() {
      return this.localFormJsHtml;
    }
    
    public void setFormJsHtml(String param) {
      if (param != null) {
        this.localFormJsHtmlTracker = true;
      } else {
        this.localFormJsHtmlTracker = true;
      } 
      this.localFormJsHtml = param;
    }
    
    protected boolean localFormTypeIDTracker = false;
    
    protected int localFormVersion;
    
    public long getFormTypeID() {
      return this.localFormTypeID;
    }
    
    public void setFormTypeID(long param) {
      if (param == Long.MIN_VALUE) {
        this.localFormTypeIDTracker = true;
      } else {
        this.localFormTypeIDTracker = true;
      } 
      this.localFormTypeID = param;
    }
    
    protected boolean localFormVersionTracker = false;
    
    protected long localId;
    
    public int getFormVersion() {
      return this.localFormVersion;
    }
    
    public void setFormVersion(int param) {
      if (param == Integer.MIN_VALUE) {
        this.localFormVersionTracker = true;
      } else {
        this.localFormVersionTracker = true;
      } 
      this.localFormVersion = param;
    }
    
    protected boolean localIdTracker = false;
    
    protected int localIs_create_table;
    
    public long getId() {
      return this.localId;
    }
    
    public void setId(long param) {
      if (param == Long.MIN_VALUE) {
        this.localIdTracker = true;
      } else {
        this.localIdTracker = true;
      } 
      this.localId = param;
    }
    
    protected boolean localIs_create_tableTracker = false;
    
    protected String localShowType;
    
    public int getIs_create_table() {
      return this.localIs_create_table;
    }
    
    public void setIs_create_table(int param) {
      if (param == Integer.MIN_VALUE) {
        this.localIs_create_tableTracker = true;
      } else {
        this.localIs_create_tableTracker = true;
      } 
      this.localIs_create_table = param;
    }
    
    protected boolean localShowTypeTracker = false;
    
    protected int localState;
    
    public String getShowType() {
      return this.localShowType;
    }
    
    public void setShowType(String param) {
      if (param != null) {
        this.localShowTypeTracker = true;
      } else {
        this.localShowTypeTracker = true;
      } 
      this.localShowType = param;
    }
    
    protected boolean localStateTracker = false;
    
    protected String localTitle;
    
    public int getState() {
      return this.localState;
    }
    
    public void setState(int param) {
      if (param == Integer.MIN_VALUE) {
        this.localStateTracker = true;
      } else {
        this.localStateTracker = true;
      } 
      this.localState = param;
    }
    
    protected boolean localTitleTracker = false;
    
    protected String localUpdateBy;
    
    public String getTitle() {
      return this.localTitle;
    }
    
    public void setTitle(String param) {
      if (param != null) {
        this.localTitleTracker = true;
      } else {
        this.localTitleTracker = true;
      } 
      this.localTitle = param;
    }
    
    protected boolean localUpdateByTracker = false;
    
    protected String localUpdateTime;
    
    public String getUpdateBy() {
      return this.localUpdateBy;
    }
    
    public void setUpdateBy(String param) {
      if (param != null) {
        this.localUpdateByTracker = true;
      } else {
        this.localUpdateByTracker = true;
      } 
      this.localUpdateBy = param;
    }
    
    protected boolean localUpdateTimeTracker = false;
    
    protected String localWfModelIds;
    
    public String getUpdateTime() {
      return this.localUpdateTime;
    }
    
    public void setUpdateTime(String param) {
      if (param != null) {
        this.localUpdateTimeTracker = true;
      } else {
        this.localUpdateTimeTracker = true;
      } 
      this.localUpdateTime = param;
    }
    
    protected boolean localWfModelIdsTracker = false;
    
    public String getWfModelIds() {
      return this.localWfModelIds;
    }
    
    public void setWfModelIds(String param) {
      if (param != null) {
        this.localWfModelIdsTracker = true;
      } else {
        this.localWfModelIdsTracker = true;
      } 
      this.localWfModelIds = param;
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
      ADBDataSource aDBDataSource = new ADBDataSource(this, parentQName) {
          public void serialize(MTOMAwareXMLStreamWriter xmlWriter) throws XMLStreamException {
            OACallArchiveServiceStub.OsFormEntity.this.serialize(this.parentQName, factory, xmlWriter);
          }
        };
      return (OMElement)new OMSourcedElementImpl(parentQName, factory, (OMDataSource)aDBDataSource);
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
      String namespacePrefix = registerPrefix((XMLStreamWriter)xmlWriter, "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd");
      if (namespacePrefix != null && namespacePrefix.trim().length() > 0) {
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", String.valueOf(namespacePrefix) + ":OsFormEntity", (XMLStreamWriter)xmlWriter);
      } else {
        writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "OsFormEntity", (XMLStreamWriter)xmlWriter);
      } 
      if (this.localCreateTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "create", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "create");
          } 
        } else {
          xmlWriter.writeStartElement("create");
        } 
        if (this.localCreate == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localCreate);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localCreateTimeTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "createTime", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "createTime");
          } 
        } else {
          xmlWriter.writeStartElement("createTime");
        } 
        if (this.localCreateTime == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localCreateTime);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localDescriptionTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "description", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "description");
          } 
        } else {
          xmlWriter.writeStartElement("description");
        } 
        if (this.localDescription == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localDescription);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localFormIdTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "formId", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "formId");
          } 
        } else {
          xmlWriter.writeStartElement("formId");
        } 
        if (this.localFormId == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localFormId);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localFormJsTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "formJs", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "formJs");
          } 
        } else {
          xmlWriter.writeStartElement("formJs");
        } 
        if (this.localFormJs == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localFormJs);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localFormJsHtmlTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "formJsHtml", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "formJsHtml");
          } 
        } else {
          xmlWriter.writeStartElement("formJsHtml");
        } 
        if (this.localFormJsHtml == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localFormJsHtml);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localFormTypeIDTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "formTypeID", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "formTypeID");
          } 
        } else {
          xmlWriter.writeStartElement("formTypeID");
        } 
        if (this.localFormTypeID == Long.MIN_VALUE) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(this.localFormTypeID));
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localFormVersionTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "formVersion", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "formVersion");
          } 
        } else {
          xmlWriter.writeStartElement("formVersion");
        } 
        if (this.localFormVersion == Integer.MIN_VALUE) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(this.localFormVersion));
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localIdTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "id", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "id");
          } 
        } else {
          xmlWriter.writeStartElement("id");
        } 
        if (this.localId == Long.MIN_VALUE) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(this.localId));
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localIs_create_tableTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "is_create_table", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "is_create_table");
          } 
        } else {
          xmlWriter.writeStartElement("is_create_table");
        } 
        if (this.localIs_create_table == Integer.MIN_VALUE) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(this.localIs_create_table));
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localShowTypeTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "showType", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "showType");
          } 
        } else {
          xmlWriter.writeStartElement("showType");
        } 
        if (this.localShowType == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localShowType);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localStateTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "state", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "state");
          } 
        } else {
          xmlWriter.writeStartElement("state");
        } 
        if (this.localState == Integer.MIN_VALUE) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(ConverterUtil.convertToString(this.localState));
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localTitleTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "title", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "title");
          } 
        } else {
          xmlWriter.writeStartElement("title");
        } 
        if (this.localTitle == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localTitle);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localUpdateByTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "updateBy", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "updateBy");
          } 
        } else {
          xmlWriter.writeStartElement("updateBy");
        } 
        if (this.localUpdateBy == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localUpdateBy);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localUpdateTimeTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "updateTime", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "updateTime");
          } 
        } else {
          xmlWriter.writeStartElement("updateTime");
        } 
        if (this.localUpdateTime == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localUpdateTime);
        } 
        xmlWriter.writeEndElement();
      } 
      if (this.localWfModelIdsTracker) {
        namespace = "http://entity.formbuilder.di.oais.flyingsoft.cn/xsd";
        if (!namespace.equals("")) {
          prefix = xmlWriter.getPrefix(namespace);
          if (prefix == null) {
            prefix = generatePrefix(namespace);
            xmlWriter.writeStartElement(prefix, "wfModelIds", namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
          } else {
            xmlWriter.writeStartElement(namespace, "wfModelIds");
          } 
        } else {
          xmlWriter.writeStartElement("wfModelIds");
        } 
        if (this.localWfModelIds == null) {
          writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "1", (XMLStreamWriter)xmlWriter);
        } else {
          xmlWriter.writeCharacters(this.localWfModelIds);
        } 
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
      ArrayList<QName> attribList = new ArrayList();
      attribList.add(new QName("http://www.w3.org/2001/XMLSchema-instance", "type"));
      attribList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "OsFormEntity"));
      if (this.localCreateTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "create"));
        elementList.add((this.localCreate == null) ? null : ConverterUtil.convertToString(this.localCreate));
      } 
      if (this.localCreateTimeTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "createTime"));
        elementList.add((this.localCreateTime == null) ? null : ConverterUtil.convertToString(this.localCreateTime));
      } 
      if (this.localDescriptionTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "description"));
        elementList.add((this.localDescription == null) ? null : ConverterUtil.convertToString(this.localDescription));
      } 
      if (this.localFormIdTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formId"));
        elementList.add((this.localFormId == null) ? null : ConverterUtil.convertToString(this.localFormId));
      } 
      if (this.localFormJsTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formJs"));
        elementList.add((this.localFormJs == null) ? null : ConverterUtil.convertToString(this.localFormJs));
      } 
      if (this.localFormJsHtmlTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formJsHtml"));
        elementList.add((this.localFormJsHtml == null) ? null : ConverterUtil.convertToString(this.localFormJsHtml));
      } 
      if (this.localFormTypeIDTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formTypeID"));
        elementList.add(ConverterUtil.convertToString(this.localFormTypeID));
      } 
      if (this.localFormVersionTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formVersion"));
        elementList.add(ConverterUtil.convertToString(this.localFormVersion));
      } 
      if (this.localIdTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "id"));
        elementList.add(ConverterUtil.convertToString(this.localId));
      } 
      if (this.localIs_create_tableTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "is_create_table"));
        elementList.add(ConverterUtil.convertToString(this.localIs_create_table));
      } 
      if (this.localShowTypeTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "showType"));
        elementList.add((this.localShowType == null) ? null : ConverterUtil.convertToString(this.localShowType));
      } 
      if (this.localStateTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "state"));
        elementList.add(ConverterUtil.convertToString(this.localState));
      } 
      if (this.localTitleTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "title"));
        elementList.add((this.localTitle == null) ? null : ConverterUtil.convertToString(this.localTitle));
      } 
      if (this.localUpdateByTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "updateBy"));
        elementList.add((this.localUpdateBy == null) ? null : ConverterUtil.convertToString(this.localUpdateBy));
      } 
      if (this.localUpdateTimeTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "updateTime"));
        elementList.add((this.localUpdateTime == null) ? null : ConverterUtil.convertToString(this.localUpdateTime));
      } 
      if (this.localWfModelIdsTracker) {
        elementList.add(new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "wfModelIds"));
        elementList.add((this.localWfModelIds == null) ? null : ConverterUtil.convertToString(this.localWfModelIds));
      } 
      return (XMLStreamReader)new ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
    }
    
    public static class Factory {
      public static OACallArchiveServiceStub.OsFormEntity parse(XMLStreamReader reader) throws Exception {
        OACallArchiveServiceStub.OsFormEntity object = new OACallArchiveServiceStub.OsFormEntity();
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
              if (!"OsFormEntity".equals(type)) {
                String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                return (OACallArchiveServiceStub.OsFormEntity)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
              } 
            } 
          } 
          Vector handledAttributes = new Vector();
          reader.next();
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "create")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setCreate(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "createTime")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setCreateTime(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "description")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setDescription(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formId")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setFormId(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formJs")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setFormJs(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formJsHtml")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setFormJsHtml(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formTypeID")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setFormTypeID(ConverterUtil.convertToLong(content));
            } else {
              object.setFormTypeID(Long.MIN_VALUE);
              reader.getElementText();
            } 
            reader.next();
          } else {
            object.setFormTypeID(Long.MIN_VALUE);
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "formVersion")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setFormVersion(ConverterUtil.convertToInt(content));
            } else {
              object.setFormVersion(-2147483648);
              reader.getElementText();
            } 
            reader.next();
          } else {
            object.setFormVersion(-2147483648);
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "id")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setId(ConverterUtil.convertToLong(content));
            } else {
              object.setId(Long.MIN_VALUE);
              reader.getElementText();
            } 
            reader.next();
          } else {
            object.setId(Long.MIN_VALUE);
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "is_create_table")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setIs_create_table(ConverterUtil.convertToInt(content));
            } else {
              object.setIs_create_table(-2147483648);
              reader.getElementText();
            } 
            reader.next();
          } else {
            object.setIs_create_table(-2147483648);
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "showType")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setShowType(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "state")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setState(ConverterUtil.convertToInt(content));
            } else {
              object.setState(-2147483648);
              reader.getElementText();
            } 
            reader.next();
          } else {
            object.setState(-2147483648);
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "title")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setTitle(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "updateBy")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setUpdateBy(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "updateTime")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setUpdateTime(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
            reader.next();
          } 
          for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
          if (reader.isStartElement() && (new QName("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd", "wfModelIds")).equals(reader.getName())) {
            nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
              String content = reader.getElementText();
              object.setWfModelIds(ConverterUtil.convertToString(content));
            } else {
              reader.getElementText();
            } 
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
  
  private static synchronized String getUniqueSuffix() {
    if (counter > 99999)
      counter = 0; 
    counter++;
    return String.valueOf(Long.toString(System.currentTimeMillis())) + "_" + counter;
  }
  
  private void populateAxisService() throws AxisFault {
    this._service = new AxisService("OACallArchiveService" + getUniqueSuffix());
    addAnonymousOperations();
    this._operations = new AxisOperation[2];
    OutInAxisOperation outInAxisOperation = new OutInAxisOperation();
    outInAxisOperation.setName(new QName("http://webService.service.oais.flyingsoft.cn", "finishWorkFlow"));
    this._service.addOperation((AxisOperation)outInAxisOperation);
    this._operations[0] = (AxisOperation)outInAxisOperation;
    outInAxisOperation = new OutInAxisOperation();
    outInAxisOperation.setName(new QName("http://webService.service.oais.flyingsoft.cn", "getWfUsingFormEntity"));
    this._service.addOperation((AxisOperation)outInAxisOperation);
    this._operations[1] = (AxisOperation)outInAxisOperation;
  }
  
  private void populateFaults() {
    this.faultExceptionNameMap.put(new QName("http://webService.service.oais.flyingsoft.cn", "Exception"), "cn.flyingsoft.oais.service.webservice.ExceptionException0");
    this.faultExceptionClassNameMap.put(new QName("http://webService.service.oais.flyingsoft.cn", "Exception"), "cn.flyingsoft.oais.service.webservice.ExceptionException0");
    this.faultMessageMap.put(new QName("http://webService.service.oais.flyingsoft.cn", "Exception"), "cn.flyingsoft.oais.service.webservice.OACallArchiveServiceStub$ExceptionE");
  }
  
  public OACallArchiveServiceStub(ConfigurationContext configurationContext, String targetEndpoint) throws AxisFault {
    this(configurationContext, targetEndpoint, false);
  }
  
  public static String targetEendPoint = WorkFlowArchives.getArchivesPath();
  
  private QName[] opNameArray;
  
  public OACallArchiveServiceStub(ConfigurationContext configurationContext) throws AxisFault {
    this(configurationContext, String.valueOf(targetEendPoint) + "/esoaisapp/services/OACallArchiveService.OACallArchiveServiceHttpSoap12Endpoint/");
  }
  
  public OACallArchiveServiceStub() throws AxisFault {
    this(String.valueOf(targetEendPoint) + "/esoaisapp/services/OACallArchiveService.OACallArchiveServiceHttpSoap12Endpoint/");
  }
  
  public OACallArchiveServiceStub(String targetEndpoint) throws AxisFault {
    this((ConfigurationContext)null, targetEndpoint);
  }
  
  public void finishWorkFlow(FinishWorkFlow finishWorkFlow2) throws RemoteException {
    MessageContext _messageContext = null;
    try {
      OperationClient _operationClient = this._serviceClient.createClient(this._operations[0].getName());
      _operationClient.getOptions().setAction("urn:finishWorkFlow");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
      addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
      _messageContext = new MessageContext();
      SOAPEnvelope env = null;
      env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), finishWorkFlow2, optimizeContent(new QName("http://webService.service.oais.flyingsoft.cn", "finishWorkFlow")));
      this._serviceClient.addHeadersToEnvelope(env);
      _messageContext.setEnvelope(env);
      _operationClient.addMessageContext(_messageContext);
      _operationClient.execute(true);
      return;
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
  
  public void startfinishWorkFlow(FinishWorkFlow finishWorkFlow2, OACallArchiveServiceCallbackHandler callback) throws RemoteException {
    OperationClient _operationClient = this._serviceClient.createClient(this._operations[0].getName());
    _operationClient.getOptions().setAction("urn:finishWorkFlow");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
    addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
    SOAPEnvelope env = null;
    MessageContext _messageContext = new MessageContext();
    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()), finishWorkFlow2, optimizeContent(new QName("http://webService.service.oais.flyingsoft.cn", "finishWorkFlow")));
    this._serviceClient.addHeadersToEnvelope(env);
    _messageContext.setEnvelope(env);
    _operationClient.addMessageContext(_messageContext);
    CallbackReceiver _callbackReceiver = null;
    if (this._operations[0].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()) {
      _callbackReceiver = new CallbackReceiver();
      this._operations[0].setMessageReceiver((MessageReceiver)_callbackReceiver);
    } 
    _operationClient.execute(false);
  }
  
  public GetWfUsingFormEntityResponse getWfUsingFormEntity() throws RemoteException, ExceptionException0 {
    MessageContext _messageContext = null;
    try {
      OperationClient _operationClient = this._serviceClient.createClient(this._operations[1].getName());
      _operationClient.getOptions().setAction("urn:getWfUsingFormEntity");
      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
      addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
      _messageContext = new MessageContext();
      SOAPEnvelope env = null;
      SOAPFactory factory = getFactory(_operationClient.getOptions().getSoapVersionURI());
      env = factory.getDefaultEnvelope();
      this._serviceClient.addHeadersToEnvelope(env);
      _messageContext.setEnvelope(env);
      _operationClient.addMessageContext(_messageContext);
      _operationClient.execute(true);
      MessageContext _returnMessageContext = _operationClient.getMessageContext("In");
      SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
      Object object = fromOM(_returnEnv.getBody().getFirstElement(), GetWfUsingFormEntityResponse.class, getEnvelopeNamespaces(_returnEnv));
      return (GetWfUsingFormEntityResponse)object;
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
            if (ex instanceof ExceptionException0)
              throw (ExceptionException0)ex; 
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
  
  public void startgetWfUsingFormEntity(final OACallArchiveServiceCallbackHandler callback) throws RemoteException {
    OperationClient _operationClient = this._serviceClient.createClient(this._operations[1].getName());
    _operationClient.getOptions().setAction("urn:getWfUsingFormEntity");
    _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
    addPropertyToOperationClient(_operationClient, "whttp:queryParameterSeparator", "&");
    SOAPEnvelope env = null;
    final MessageContext _messageContext = new MessageContext();
    SOAPFactory factory = getFactory(_operationClient.getOptions().getSoapVersionURI());
    env = factory.getDefaultEnvelope();
    this._serviceClient.addHeadersToEnvelope(env);
    _messageContext.setEnvelope(env);
    _operationClient.addMessageContext(_messageContext);
    _operationClient.setCallback(new AxisCallback() {
          public void onMessage(MessageContext resultContext) {
            try {
              SOAPEnvelope resultEnv = resultContext.getEnvelope();
              Object object = OACallArchiveServiceStub.this.fromOM(resultEnv.getBody().getFirstElement(), OACallArchiveServiceStub.GetWfUsingFormEntityResponse.class, OACallArchiveServiceStub.this.getEnvelopeNamespaces(resultEnv));
              callback.receiveResultgetWfUsingFormEntity((OACallArchiveServiceStub.GetWfUsingFormEntityResponse)object);
            } catch (AxisFault e) {
              callback.receiveErrorgetWfUsingFormEntity((Exception)e);
            } 
          }
          
          public void onError(Exception error) {
            if (error instanceof AxisFault) {
              AxisFault f = (AxisFault)error;
              OMElement faultElt = f.getDetail();
              if (faultElt != null) {
                if (OACallArchiveServiceStub.this.faultExceptionNameMap.containsKey(faultElt.getQName())) {
                  try {
                    String exceptionClassName = (String)OACallArchiveServiceStub.this.faultExceptionClassNameMap.get(faultElt.getQName());
                    Class<?> exceptionClass = Class.forName(exceptionClassName);
                    Exception ex = (Exception)exceptionClass.newInstance();
                    String messageClassName = (String)OACallArchiveServiceStub.this.faultMessageMap.get(faultElt.getQName());
                    Class<?> messageClass = Class.forName(messageClassName);
                    Object messageObject = OACallArchiveServiceStub.this.fromOM(faultElt, messageClass, (Map)null);
                    Method m = exceptionClass.getMethod("setFaultMessage", new Class[] { messageClass });
                    m.invoke(ex, new Object[] { messageObject });
                    if (ex instanceof ExceptionException0) {
                      callback.receiveErrorgetWfUsingFormEntity(ex);
                      return;
                    } 
                    callback.receiveErrorgetWfUsingFormEntity(new RemoteException(ex.getMessage(), ex));
                  } catch (ClassCastException e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } catch (ClassNotFoundException e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } catch (NoSuchMethodException e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } catch (InvocationTargetException e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } catch (IllegalAccessException e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } catch (InstantiationException e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } catch (AxisFault e) {
                    callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                  } 
                } else {
                  callback.receiveErrorgetWfUsingFormEntity((Exception)f);
                } 
              } else {
                callback.receiveErrorgetWfUsingFormEntity((Exception)f);
              } 
            } else {
              callback.receiveErrorgetWfUsingFormEntity(error);
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
              callback.receiveErrorgetWfUsingFormEntity((Exception)axisFault);
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
  
  private Map getEnvelopeNamespaces(SOAPEnvelope env) {
    Map<Object, Object> returnMap = new HashMap<Object, Object>();
    Iterator<OMNamespace> namespaceIterator = env.getAllDeclaredNamespaces();
    while (namespaceIterator.hasNext()) {
      OMNamespace ns = namespaceIterator.next();
      returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
    } 
    return returnMap;
  }
  
  public OACallArchiveServiceStub(ConfigurationContext configurationContext, String targetEndpoint, boolean useSeparateListener) throws AxisFault {
    this.opNameArray = null;
    populateAxisService();
    populateFaults();
    this._serviceClient = new ServiceClient(configurationContext, this._service);
    configurationContext = this._serviceClient.getServiceContext().getConfigurationContext();
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
  
  public static class ExtensionMapper {
    public static Object getTypeObject(String namespaceURI, String typeName, XMLStreamReader reader) throws Exception {
      if ("http://entity.formbuilder.di.oais.flyingsoft.cn/xsd".equals(namespaceURI) && "OsFormEntity".equals(typeName))
        return OACallArchiveServiceStub.OsFormEntity.Factory.parse(reader); 
      if ("http://system.di.oais.flyingsoft.cn/xsd".equals(namespaceURI) && "BaseObject".equals(typeName))
        return OACallArchiveServiceStub.BaseObject.Factory.parse(reader); 
      if ("http://webService.service.oais.flyingsoft.cn".equals(namespaceURI) && "Exception".equals(typeName))
        return OACallArchiveServiceStub.Exception.Factory.parse(reader); 
      throw new ADBException("Unsupported type " + namespaceURI + " " + typeName);
    }
  }
  
  public static class Factory {
    public static OACallArchiveServiceStub.GetWfUsingFormEntityResponse parse(XMLStreamReader reader) throws Exception {
      OACallArchiveServiceStub.GetWfUsingFormEntityResponse object = new OACallArchiveServiceStub.GetWfUsingFormEntityResponse();
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
            if (!"getWfUsingFormEntityResponse".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (OACallArchiveServiceStub.GetWfUsingFormEntityResponse)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "return")).equals(reader.getName())) {
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
            object.set_return(null);
            reader.next();
            reader.next();
          } else {
            object.set_return(OACallArchiveServiceStub.OsFormEntity.Factory.parse(reader));
            reader.next();
          } 
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
    public static OACallArchiveServiceStub.ExceptionE parse(XMLStreamReader reader) throws Exception {
      OACallArchiveServiceStub.ExceptionE object = new OACallArchiveServiceStub.ExceptionE();
      String nillableValue = null;
      String prefix = "";
      String namespaceuri = "";
      try {
        while (!reader.isStartElement() && !reader.isEndElement())
          reader.next(); 
        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
        if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
          while (!reader.isEndElement())
            reader.next(); 
          return null;
        } 
        if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
          String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type");
          if (fullTypeName != null) {
            String nsPrefix = null;
            if (fullTypeName.indexOf(":") > -1)
              nsPrefix = fullTypeName.substring(0, fullTypeName.indexOf(":")); 
            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;
            String type = fullTypeName.substring(fullTypeName.indexOf(":") + 1);
            if (!"Exception".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (OACallArchiveServiceStub.ExceptionE)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "Exception")).equals(reader.getName())) {
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if ("true".equals(nillableValue) || "1".equals(nillableValue)) {
            object.setException(null);
            reader.next();
            reader.next();
          } else {
            object.setException(OACallArchiveServiceStub.Exception.Factory.parse(reader));
            reader.next();
          } 
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
    public static OACallArchiveServiceStub.FinishWorkFlow parse(XMLStreamReader reader) throws Exception {
      OACallArchiveServiceStub.FinishWorkFlow object = new OACallArchiveServiceStub.FinishWorkFlow();
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
            if (!"finishWorkFlow".equals(type)) {
              String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
              return (OACallArchiveServiceStub.FinishWorkFlow)OACallArchiveServiceStub.ExtensionMapper.getTypeObject(nsUri, type, reader);
            } 
          } 
        } 
        Vector handledAttributes = new Vector();
        reader.next();
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "username")).equals(reader.getName())) {
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
            String content = reader.getElementText();
            object.setUsername(ConverterUtil.convertToString(content));
          } else {
            reader.getElementText();
          } 
          reader.next();
        } 
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "workflowID")).equals(reader.getName())) {
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
            String content = reader.getElementText();
            object.setWorkflowID(ConverterUtil.convertToString(content));
          } else {
            reader.getElementText();
          } 
          reader.next();
        } 
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "status")).equals(reader.getName())) {
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
            String content = reader.getElementText();
            object.setStatus(ConverterUtil.convertToString(content));
          } else {
            reader.getElementText();
          } 
          reader.next();
        } 
        for (; !reader.isStartElement() && !reader.isEndElement(); reader.next());
        if (reader.isStartElement() && (new QName("http://webService.service.oais.flyingsoft.cn", "dataHavaRightlist")).equals(reader.getName())) {
          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
          if (!"true".equals(nillableValue) && !"1".equals(nillableValue)) {
            String content = reader.getElementText();
            object.setDataHavaRightlist(ConverterUtil.convertToString(content));
          } else {
            reader.getElementText();
          } 
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
  
  private OMElement toOM(FinishWorkFlow param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(FinishWorkFlow.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(GetWfUsingFormEntityResponse param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(GetWfUsingFormEntityResponse.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private OMElement toOM(ExceptionE param, boolean optimizeContent) throws AxisFault {
    try {
      return param.getOMElement(ExceptionE.MY_QNAME, 
          OMAbstractFactory.getOMFactory());
    } catch (ADBException e) {
      throw AxisFault.makeFault(e);
    } 
  }
  
  private SOAPEnvelope toEnvelope(SOAPFactory factory, FinishWorkFlow param, boolean optimizeContent) throws AxisFault {
    try {
      SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
      emptyEnvelope.getBody().addChild((OMNode)param.getOMElement(FinishWorkFlow.MY_QNAME, (OMFactory)factory));
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
      if (FinishWorkFlow.class.equals(type))
        return FinishWorkFlow.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (GetWfUsingFormEntityResponse.class.equals(type))
        return GetWfUsingFormEntityResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
      if (ExceptionE.class.equals(type))
        return ExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching()); 
    } catch (Exception e) {
      throw AxisFault.makeFault(e);
    } 
    return null;
  }
}
