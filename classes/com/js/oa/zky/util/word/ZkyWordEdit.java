package com.js.oa.zky.util.word;

import com.js.oa.jsflow.util.mark.MSWordTool;
import java.util.List;
import java.util.Map;

public class ZkyWordEdit {
  ZkyWordData zky = new ZkyWordData();
  
  public String creatZkyDocx(String userId, String fileName, String path, String nd) {
    String jobNumber = this.zky.getJobNumber(userId);
    long startTime = System.currentTimeMillis();
    double[] score = new double[15];
    double zongFen = 0.0D;
    Map<String, String> grxx = this.zky.getGrxx(jobNumber, nd);
    if (grxx.get("姓名") != null) {
      zongFen += this.zky.getDefen();
      score[0] = this.zky.getDefen();
      List<Map<String, String>> lwzz = this.zky.getLwzz(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[1] = this.zky.getDefen();
      List<Map<String, String>> xshy = this.zky.getXshy(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[2] = this.zky.getDefen();
      List<Map<String, String>> kyxm = this.zky.getKyxm(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[3] = this.zky.getDefen();
      List<Map<String, String>> yhdjx = this.zky.getYhdjx(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[4] = this.zky.getDefen();
      List<Map<String, String>> zzsbjx = this.zky.getZzsbjx(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[5] = this.zky.getDefen();
      List<Map<String, String>> sbzl = this.zky.getSbzl(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[6] = this.zky.getDefen();
      List<Map<String, String>> rcpy = this.zky.getRcpy(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[7] = this.zky.getDefen();
      List<Map<String, String>> shrzjz = this.zky.getShrzjz(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[8] = this.zky.getDefen();
      List<Map<String, String>> cf = this.zky.getCf(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[9] = this.zky.getDefen();
      List<Map<String, String>> lf = this.zky.getLf(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[10] = this.zky.getDefen();
      List<Map<String, String>> gjhz = this.zky.getGjhz(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[11] = this.zky.getDefen();
      List<Map<String, String>> ydhz = this.zky.getYdhz(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[12] = this.zky.getDefen();
      List<Map<String, String>> zxbg = this.zky.getZxbg(jobNumber, nd);
      zongFen += this.zky.getDefen();
      score[13] = this.zky.getDefen();
      zongFen = this.zky.myRound(zongFen, 2);
      score[14] = zongFen;
      (new ZkySaveScore()).insertScore(jobNumber, nd, score, grxx);
      grxx.put("总分", (new StringBuilder(String.valueOf(zongFen))).toString());
      long curTime = System.currentTimeMillis();
      System.out.println("取值耗时：" + (curTime - startTime) + "毫秒");
      MSWordTool changer = new MSWordTool();
      changer.setTemplate(String.valueOf(path) + "\\" + fileName + ".docx");
      changer.replaceBookMark(grxx);
      int zhuozuoNum = changer.fillTableAtBookMark("著作论文", lwzz.size());
      int huiyiNum = changer.fillTableAtBookMark("会议", xshy.size());
      int keyanNum = changer.fillTableAtBookMark("科研项目情况", kyxm.size());
      int jiangliNum = changer.fillTableAtBookMark("奖励及知识产权", yhdjx.size());
      int jiangxiangNum = changer.fillTableAtBookMark("申报奖项", zzsbjx.size());
      int dengjiNum = changer.fillTableAtBookMark("著作登记", sbzl.size());
      int rencaiNum = changer.fillTableAtBookMark("人才培养及教学情况", rcpy.size());
      int shehuiNum = changer.fillTableAtBookMark("社会任职兼职情况", shrzjz.size());
      int chufangNum = changer.fillTableAtBookMark("出访国家", cf.size());
      int waibenNum = changer.fillTableAtBookMark("外宾情况", lf.size());
      int guojiNum = changer.fillTableAtBookMark("国际合作及院地合作情况", gjhz.size());
      int hezuoNum = changer.fillTableAtBookMark("合作实验室", ydhz.size());
      int zixunNum = changer.fillTableAtBookMark("咨询报告及建议情况", zxbg.size());
      changer.setTemplate(changer.saveAs(String.valueOf(path) + "\\" + fileName + jobNumber + "_temp.docx"));
      changer.replaceTextToRow(zhuozuoNum, lwzz, "表格");
      changer.replaceTextToRow(huiyiNum, xshy, "表格");
      changer.replaceTextToRow(keyanNum, kyxm, "表格");
      changer.replaceTextToRow(jiangliNum, yhdjx, "表格");
      changer.replaceTextToRow(jiangxiangNum, zzsbjx, "表格");
      changer.replaceTextToRow(dengjiNum, sbzl, "表格");
      changer.replaceTextToRow(rencaiNum, rcpy, "表格");
      changer.replaceTextToRow(shehuiNum, shrzjz, "表格");
      changer.replaceTextToRow(chufangNum, cf, "表格");
      changer.replaceTextToRow(waibenNum, lf, "表格");
      changer.replaceTextToRow(guojiNum, gjhz, "表格");
      changer.replaceTextToRow(hezuoNum, ydhz, "表格");
      changer.replaceTextToRow(zixunNum, zxbg, "表格");
      changer.saveAs(String.valueOf(path) + "\\" + nd + "_" + fileName + "_" + jobNumber + ".docx");
      long endTime = System.currentTimeMillis();
      System.out.println("文件生成耗时：" + (endTime - curTime) + "毫秒");
      System.out.println("文件生成总耗时：" + (endTime - startTime) + "毫秒");
      return String.valueOf(nd) + "_" + fileName + "_" + jobNumber + ".docx";
    } 
    return "no";
  }
  
  public static void main(String[] args) {
    String fileName = "2013年科研人员业绩考核表";
    ZkyWordEdit zkyDocx = new ZkyWordEdit();
    zkyDocx.creatZkyDocx("", fileName, "e:", "2013");
  }
}
