package rtx.rtxsms.tempuri;

import com.js.util.config.SystemCommon;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class MobsetApiLocator extends Service implements MobsetApi {
  public MobsetApiLocator() {}
  
  public MobsetApiLocator(EngineConfiguration config) {
    super(config);
  }
  
  public MobsetApiLocator(String wsdlLoc, QName sName) throws ServiceException {
    super(wsdlLoc, sName);
  }
  
  private String MobsetApiSoap_address = SystemCommon.getRtxIP();
  
  public String getMobsetApiSoapAddress() {
    return this.MobsetApiSoap_address;
  }
  
  private String MobsetApiSoapWSDDServiceName = "MobsetApiSoap";
  
  public String getMobsetApiSoapWSDDServiceName() {
    return this.MobsetApiSoapWSDDServiceName;
  }
  
  public void setMobsetApiSoapWSDDServiceName(String name) {
    this.MobsetApiSoapWSDDServiceName = name;
  }
  
  public MobsetApiSoap getMobsetApiSoap() throws ServiceException {
    URL endpoint = null;
    try {
      endpoint = new URL(this.MobsetApiSoap_address);
    } catch (MalformedURLException e) {
      throw new ServiceException(e);
    } 
    return getMobsetApiSoap(endpoint);
  }
  
  public MobsetApiSoap getMobsetApiSoap(URL portAddress) throws ServiceException {
    try {
      MobsetApiSoapStub _stub = new MobsetApiSoapStub(portAddress, this);
      _stub.setPortName(getMobsetApiSoapWSDDServiceName());
      return _stub;
    } catch (AxisFault e) {
      return null;
    } 
  }
  
  public void setMobsetApiSoapEndpointAddress(String address) {
    this.MobsetApiSoap_address = address;
  }
  
  public Remote getPort(Class<?> serviceEndpointInterface) throws ServiceException {
    try {
      if (MobsetApiSoap.class.isAssignableFrom(serviceEndpointInterface)) {
        MobsetApiSoapStub _stub = new MobsetApiSoapStub(new URL(this.MobsetApiSoap_address), this);
        _stub.setPortName(getMobsetApiSoapWSDDServiceName());
        return _stub;
      } 
    } catch (Throwable t) {
      throw new ServiceException(t);
    } 
    throw new ServiceException("There is no stub implementation for the interface:  " + ((serviceEndpointInterface == null) ? "null" : serviceEndpointInterface.getName()));
  }
  
  public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
    if (portName == null)
      return getPort(serviceEndpointInterface); 
    String inputPortName = portName.getLocalPart();
    if ("MobsetApiSoap".equals(inputPortName))
      return getMobsetApiSoap(); 
    Remote _stub = getPort(serviceEndpointInterface);
    ((Stub)_stub).setPortName(portName);
    return _stub;
  }
  
  public QName getServiceName() {
    return new QName("http://tempuri.org/", "MobsetApi");
  }
  
  private HashSet ports = null;
  
  public Iterator getPorts() {
    if (this.ports == null) {
      this.ports = new HashSet();
      this.ports.add(new QName("http://tempuri.org/", "MobsetApiSoap"));
    } 
    return this.ports.iterator();
  }
  
  public void setEndpointAddress(String portName, String address) throws ServiceException {
    if ("MobsetApiSoap".equals(portName)) {
      setMobsetApiSoapEndpointAddress(address);
    } else {
      throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
    } 
  }
  
  public void setEndpointAddress(QName portName, String address) throws ServiceException {
    setEndpointAddress(portName.getLocalPart(), address);
  }
}
