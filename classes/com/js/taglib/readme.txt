OrgTag.java解析

1、所有参数有jsp页面便签传递如
<SSHA:tree condition="<%=conditionType%>" //获字段取值
           target="<%=target%>"//跳转目标页面
           allowId="<%=allowId%>"//已选
           inputType="<%=inputType%>"//选人方式，多选，单选
           range="<%=range%>"//范围
           type="<%=type%>"//部门、个人、公共
           orgInputType="<%=orgInputType%>">//选组织方式，多选，单选,唯一
</SSHA:tree>

2、便签生成一段js代码如，结合wtree.js便生成树结构，具体请参考/jsoa/public/readme.txt：
<script language="javascript" type="text/javascript">
var d=new dTree('d','/jsoa/js/tree/images/menubar/')
	d.config.folderLinks=true;
	d.add(0,-1,'湖南新星会计事务所有限公司','javascript:submit(1);','湖南新星会计事务所有限公司','');
	d.add(20080808582,0,'综合部','javascript:submit(20080808582);','综合部','');
	d.add(20080808583,0,'客户管理部','javascript:submit(20080808583);','客户管理部','');
	document.getElementById('treearea').innerHTML = d;
	function submit(a){
	if(a==20080808582){
		orgTreeBar.action="/jsoa/selectObj.do?method=getOrgUserList&range=*0*&allowId=sendToId&type=orgPerson&orgInputType=yes&inputType=no&condition=&orgName=综合部&orgId=20080808582";
	}
	if(a==20080808583){
		orgTreeBar.action="/jsoa/selectObj.do?method=getOrgUserList&range=*0*&allowId=sendToId&type=orgPerson&orgInputType=yes&inputType=no&condition=&orgName=客户管理部&orgId=20080808583";
	}

</script>
