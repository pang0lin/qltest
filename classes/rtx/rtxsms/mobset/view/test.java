package rtx.rtxsms.mobset.view;

import rtx.rtxsms.mobset.bean.DataObjectBean;
import rtx.rtxsms.mobset.factory.DataObjectFactory;

public class test {
  public static void main(String[] args) {
    DataObjectBean bean = DataObjectFactory.getInstance();
    bean.setServerIP("192.168.1.119");
    System.out.println(bean.getServerIP());
    bean = DataObjectFactory.getInstance();
    System.out.println(bean.getServerIP());
  }
}
