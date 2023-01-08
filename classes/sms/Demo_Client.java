package sms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class Demo_Client {
  private String urlStr = "http://127.0.0.1:8080/jsoa/WebServiceProxy";
  
  private static String recordId = "59847";
  
  public void createNewProcess() {
    String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><WorkFlow>\t<Action>createNewProcess</Action>    <Process processId=\"58845\" />    <UserName submitName=\"admin1\" receiveName=\"\" />    <Data>        <Table tableId=\"58832\" tableName=\"jst_3036\">            <Column>               <field name=\"jst_3036_f3448\" type=\"varchar\">a1</field>               <field name=\"jst_3036_f3449\" type=\"varchar\">a2</field>            </Column>        </Table>        <SubTable tableName=\"jst_3037\"  type=\"0\">            <Column>               <field name=\"jst_3037_f3452\" type=\"varchar\">aab1</field>               <field name=\"jst_3037_f3453\" type=\"varchar\">bbb1</field>               <field name=\"jst_3037_f3454\" type=\"varchar\">ccc1</field>            </Column>        </SubTable>        <SubTable tableName=\"jst_3037\"  type=\"1\">            <Column>               <field name=\"jst_3037_f3452\" type=\"varchar\">aaa21</field>               <field name=\"jst_3037_f3453\" type=\"varchar\">bbb21</field>               <field name=\"jst_3037_f3454\" type=\"varchar\">ccc21</field>            </Column>        </SubTable>        <SubTable tableName=\"jst_3037\"  type=\"2\">            <Column>               <field name=\"jst_3037_f3452\" type=\"varchar\">aaa321</field>               <field name=\"jst_3037_f3453\" type=\"varchar\">bbb321</field>               <field name=\"jst_3037_f3454\" type=\"varchar\">ccc321</field>            </Column>        </SubTable>    </Data></WorkFlow>";
    byte[] xmlData = xmlString.getBytes();
    DataInputStream input = null;
    ByteArrayOutputStream out = null;
    try {
      URL url = new URL(this.urlStr);
      URLConnection urlCon = url.openConnection();
      urlCon.setDoOutput(true);
      urlCon.setDoInput(true);
      urlCon.setUseCaches(false);
      urlCon.setRequestProperty("Content-Type", "text/xml");
      urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
      DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
      printout.write(xmlData);
      printout.flush();
      printout.close();
      input = new DataInputStream(urlCon.getInputStream());
      out = new ByteArrayOutputStream();
      byte[] bufferByte = new byte[256];
      int l = -1;
      while ((l = input.read(bufferByte)) > -1) {
        out.write(bufferByte, 0, l);
        out.flush();
      } 
      byte[] rResult = out.toByteArray();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(rResult));
      String result = doc.getElementsByTagName("result").item(0).getFirstChild().getNodeValue();
      System.out.println("result:" + result);
      recordId = result;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
        input.close();
      } catch (Exception exception) {}
    } 
  }
  
  public void toNextActivity() {
    String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><WorkFlow>\t<Action>toNextActivity</Action>    <Process processId=\"58845\" recordId=\"" + 

      
      recordId + "\"/>" + 
      "    <Activity nextActivityId=\"58856\"/>" + 
      "    <UserName submitName=\"admin1\" />" + 
      "    <Data>" + 
      "        <Table tableId=\"58832\" tableName=\"jst_3036\">" + 
      "            <Column>" + 
      "               <field name=\"jst_3036_f3448\" type=\"varchar\">a1</field>" + 
      "               <field name=\"jst_3036_f3449\" type=\"varchar\">a2</field>" + 

      
      "            </Column>" + 
      "        </Table>" + 
      
      "        <SubTable tableName=\"jst_3037\"  type=\"0\">" + 
      "            <Column>" + 
      "               <field name=\"jst_3037_f3452\" type=\"varchar\">aab1a</field>" + 
      "               <field name=\"jst_3037_f3453\" type=\"varchar\">bbb1</field>" + 
      "               <field name=\"jst_3037_f3454\" type=\"varchar\">ccc1</field>" + 
      "            </Column>" + 
      "        </SubTable>" + 
      "        <SubTable tableName=\"jst_3037\"  type=\"1\">" + 
      "            <Column>" + 
      "               <field name=\"jst_3037_f3452\" type=\"varchar\">aaa21b</field>" + 
      "               <field name=\"jst_3037_f3453\" type=\"varchar\">bbb21b</field>" + 
      "               <field name=\"jst_3037_f3454\" type=\"varchar\">ccc21</field>" + 
      "            </Column>" + 
      "        </SubTable>" + 
      "        <SubTable tableName=\"jst_3037\"  type=\"2\">" + 
      "            <Column>" + 
      "               <field name=\"jst_3037_f3452\" type=\"varchar\">aaa321</field>" + 
      "               <field name=\"jst_3037_f3453\" type=\"varchar\">bbb321</field>" + 
      "               <field name=\"jst_3037_f3454\" type=\"varchar\">ccc321c</field>" + 
      "            </Column>" + 
      "        </SubTable>" + 
      
      "    </Data>" + 
      "</WorkFlow>";
    byte[] xmlData = xmlString.getBytes();
    DataInputStream input = null;
    ByteArrayOutputStream out = null;
    try {
      URL url = new URL(this.urlStr);
      URLConnection urlCon = url.openConnection();
      urlCon.setDoOutput(true);
      urlCon.setDoInput(true);
      urlCon.setUseCaches(false);
      urlCon.setRequestProperty("Content-Type", "text/xml");
      urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
      DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
      printout.write(xmlData);
      printout.flush();
      printout.close();
      input = new DataInputStream(urlCon.getInputStream());
      out = new ByteArrayOutputStream();
      byte[] bufferByte = new byte[256];
      int l = -1;
      while ((l = input.read(bufferByte)) > -1) {
        out.write(bufferByte, 0, l);
        out.flush();
      } 
      byte[] rResult = out.toByteArray();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(rResult));
      String result = doc.getElementsByTagName("result").item(0).getFirstChild().getNodeValue();
      System.out.println("result:" + result);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
        input.close();
      } catch (Exception exception) {}
    } 
  }
  
  public void completeProcess() {
    String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"?><WorkFlow>\t<Action>completeProcess</Action>    <Process processId=\"259\"  recordId=\"" + 

      
      recordId + "\"/>" + 
      "    <UserName submitName=\"zs\" receiveName=\"zs\" />" + 
      "    <Data>" + 
      "        <Table tableId=\"337\" tableName=\"jst_3014\">" + 
      "            <Column>" + 
      "               <field name=\"jst_3014_f3067\" type=\"varchar\">18660118263</field>" + 
      "               <field name=\"jst_3014_f3068\" type=\"varchar\">60.208.159.95</field>" + 
      "               <field name=\"jst_3014_f3069\" type=\"varchar\">主表数据1</field>" + 
      "               <field name=\"jst_3014_f3070\" type=\"varchar\">1333</field>" + 
      "            </Column>" + 
      "        </Table>" + 
      "    </Data>" + 
      "</WorkFlow>";
    byte[] xmlData = xmlString.getBytes();
    DataInputStream input = null;
    ByteArrayOutputStream out = null;
    try {
      URL url = new URL(this.urlStr);
      URLConnection urlCon = url.openConnection();
      urlCon.setDoOutput(true);
      urlCon.setDoInput(true);
      urlCon.setUseCaches(false);
      urlCon.setRequestProperty("Content-Type", "text/xml");
      urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
      DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
      printout.write(xmlData);
      printout.flush();
      printout.close();
      input = new DataInputStream(urlCon.getInputStream());
      out = new ByteArrayOutputStream();
      byte[] bufferByte = new byte[256];
      int l = -1;
      while ((l = input.read(bufferByte)) > -1) {
        out.write(bufferByte, 0, l);
        out.flush();
      } 
      byte[] rResult = out.toByteArray();
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new ByteArrayInputStream(rResult));
      String result = doc.getElementsByTagName("result").item(0).getFirstChild().getNodeValue();
      System.out.println("result:" + result);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        out.close();
        input.close();
      } catch (Exception exception) {}
    } 
  }
  
  public static void main(String[] args) throws UnsupportedEncodingException {
    Demo_Client client = new Demo_Client();
    client.toNextActivity();
  }
}
