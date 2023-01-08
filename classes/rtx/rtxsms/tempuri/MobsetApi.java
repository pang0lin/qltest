package rtx.rtxsms.tempuri;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface MobsetApi extends Service {
  String getMobsetApiSoapAddress();
  
  MobsetApiSoap getMobsetApiSoap() throws ServiceException;
  
  MobsetApiSoap getMobsetApiSoap(URL paramURL) throws ServiceException;
}
