package rtx;

public class Test {
  public static void main(String[] args) {
    String szSender = new String("1200");
    String szReceiver = new String("wwjs");
    String szSmInfo = new String(
        "我们在测试1,我们在测试2,我们在测试3,我们在测试4,我们在测试5,我们在测试6,我们在测试7,我们在测试8");
    String szError = new String("ss");
    Test test = new Test();
    test.addDept(null);
    int iRet = 0;
  }
  
  public void addDept(RTXDeptVO dept) {
    dept = new RTXDeptVO();
    dept.setDeptId("10");
    dept.setDeptName("bbb");
    dept.setDeptParentId("0");
    dept.setDeptInfo("aaaaaaaaaaaaaaa");
    RTXSvrApi api = new RTXSvrApi();
    if (api.Init()) {
      api.saveDept(dept);
      api.UnInit();
    } 
  }
}
