package cn.zzy.action;

import java.io.File;
import java.util.ArrayList;

public class Test {
  public static void main(String[] args) {
    NotifyRequest data = new NotifyRequest();
    data.setTransid(1L);
    data.setOaname("中资源OA办公系统");
    data.setAccount("tjqnzyxyoa");
    data.setPassword("0da5c8dbf82dc059a89d161993019d8e");
    data.setSender("016024");
    data.setSendername("徐涛");
    data.setRecvlist(new String[] { "016024", "140728", "261721" });
    data.setSubject("公司网址");
    data.setContent("厦门市中资源网络服务有限公司<a href=\"http://www.zzy.cn\" target=\"_blank\" style=\"font-size:12px;font-family:arial;line-height:1.5;\">www.zzy.cn</a>");
    QResponse q = Communicator.getInstance().sendRequest(data);
    System.out.println("测试OA提醒：返回码:" + q.getCode() + " 描述:" + q.getError_des());
    File f = new File("D:\\content.txt");
    AfficheRequest afficheRequest = new AfficheRequest();
    afficheRequest.setTransid(1L);
    afficheRequest.setOaname("中资源OA系统");
    afficheRequest.setAccount("tjqnzyxyoa");
    afficheRequest.setPassword("0da5c8dbf82dc059a89d161993019d8e");
    afficheRequest.setSender("016024");
    afficheRequest.setSendername("徐涛");
    afficheRequest.setRecvlist(new String[] { "016024", "140728", "261721" });
    afficheRequest.setSubject("关于做好2015年毕业设计的通知");
    afficheRequest.setHtml("<DIV>测试html<IMG src=\"cid:620\"></DIV>");
    afficheRequest.setSid(0L);
    afficheRequest.setContent("这个是文本内容");
    afficheRequest.setType((short)0);
    AttachmentRequest attachment = new AttachmentRequest();
    attachment.setId(620L);
    attachment.setPath("D:\\14565_1331167046.jpg");
    attachment.setSize((new File(attachment.getPath())).length());
    afficheRequest.getHtml().replaceAll(attachment.getPath(), "cid:" + attachment.getId());
    ArrayList<AttachmentRequest> attachments = new ArrayList<AttachmentRequest>();
    attachments.add(attachment);
    afficheRequest.setAttachments(attachments);
    FileRequest file = new FileRequest();
    file.setId(621L);
    file.setPath("D:\\city.txt");
    file.setSize((new File(file.getPath())).length());
    file.setName("city.txt");
    ArrayList<FileRequest> files = new ArrayList<FileRequest>();
    files.add(file);
    afficheRequest.setFiles(files);
    QResponse q1 = Communicator.getInstance().sendRequest(afficheRequest);
    if (afficheRequest.getSid() == 0L) {
      System.out.println("测试新增电子公告： 返回码:" + q1.getCode() + " 描述:" + q1.getError_des() + " sid：" + q1.getSid());
    } else {
      System.out.println("测试修改电子公告： 返回码:" + q1.getCode() + " 描述:" + q1.getError_des() + " sid：" + q1.getSid());
    } 
    AfficheRequest afficheRequest1 = new AfficheRequest();
    afficheRequest1.setTransid(1L);
    afficheRequest1.setOaname("中资源OA系统");
    afficheRequest1.setAccount("tjqnzyxyoa");
    afficheRequest1.setPassword("0da5c8dbf82dc059a89d161993019d8e");
    afficheRequest1.setSender("016024");
    afficheRequest1.setSendername("徐涛");
    afficheRequest1.setRecvlist(new String[] { "016024", "140728", "261721" });
    afficheRequest1.setSubject("关于做好2015年毕业设计的通知-测试修改电子公告");
    afficheRequest1.setHtml("<DIV>测试修改电子公告html<IMG src=\"cid:622\"></DIV>");
    afficheRequest1.setSid(570L);
    afficheRequest1.setContent("这个是文本内容测试修改电子公告");
    afficheRequest1.setType((short)0);
    AttachmentRequest attachment1 = new AttachmentRequest();
    attachment1.setId(622L);
    attachment1.setPath("D:\\108X108.png");
    attachment1.setSize((new File(attachment1.getPath())).length());
    afficheRequest1.getHtml().replaceAll(attachment1.getPath(), "cid:" + attachment1.getId());
    ArrayList<AttachmentRequest> attachments1 = new ArrayList<AttachmentRequest>();
    attachments1.add(attachment1);
    afficheRequest1.setAttachments(attachments1);
    FileRequest file1 = new FileRequest();
    file1.setId(623L);
    file1.setPath("D:\\content.txt");
    file1.setSize((new File(file1.getPath())).length());
    file1.setName("content.txt");
    ArrayList<FileRequest> files1 = new ArrayList<FileRequest>();
    files1.add(file1);
    afficheRequest1.setFiles(files1);
    QResponse q3 = Communicator.getInstance().sendRequest(afficheRequest1);
    if (afficheRequest1.getSid() == 0L) {
      System.out.println("测试新增电子公告： 返回码:" + q3.getCode() + " 描述:" + q3.getError_des() + " sid：" + q3.getSid());
    } else {
      System.out.println("测试修改电子公告： 返回码:" + q3.getCode() + " 描述:" + q3.getError_des() + " sid：" + q3.getSid());
    } 
    AfficheDelRequest afficheDelRequest = new AfficheDelRequest();
    afficheDelRequest.setTransid(1L);
    afficheDelRequest.setOaname("中资源OA办公系统");
    afficheDelRequest.setAccount("tjqnzyxyoa");
    afficheDelRequest.setPassword("0da5c8dbf82dc059a89d161993019d8e");
    afficheDelRequest.setSid(565L);
    QResponse q2 = Communicator.getInstance().sendRequest(afficheDelRequest);
    System.out.println("测试删除电子公告：返回码:" + q2.getCode() + " 描述:" + q2.getError_des());
  }
}
