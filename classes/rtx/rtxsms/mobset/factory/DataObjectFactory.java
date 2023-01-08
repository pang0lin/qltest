package rtx.rtxsms.mobset.factory;

import javax.xml.rpc.ServiceException;
import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.tempuri.MobsetApi;
import rtx.rtxsms.tempuri.MobsetApiLocator;
import rtx.rtxsms.tempuri.MobsetApiSoap;

public class DataObjectFactory {
  private static DataObjectBean mobsetBean = null;
  
  private static MobsetApiSoap mobsetApiSub = null;
  
  private static MobsetApi mobsetApi = null;
  
  public static DataObjectBean getInstance() {
    if (mobsetBean == null)
      mobsetBean = new DataObjectBean(); 
    return mobsetBean;
  }
  
  public static MobsetApiSoap getMobsetApi() {
    if (mobsetApi == null)
      try {
        mobsetApi = new MobsetApiLocator();
        mobsetApiSub = mobsetApi.getMobsetApiSoap();
      } catch (ServiceException e) {
        e.printStackTrace();
      }  
    return mobsetApiSub;
  }
}
