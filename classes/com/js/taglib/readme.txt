OrgTag.java����

1�����в�����jspҳ���ǩ������
<SSHA:tree condition="<%=conditionType%>" //���ֶ�ȡֵ
           target="<%=target%>"//��תĿ��ҳ��
           allowId="<%=allowId%>"//��ѡ
           inputType="<%=inputType%>"//ѡ�˷�ʽ����ѡ����ѡ
           range="<%=range%>"//��Χ
           type="<%=type%>"//���š����ˡ�����
           orgInputType="<%=orgInputType%>">//ѡ��֯��ʽ����ѡ����ѡ,Ψһ
</SSHA:tree>

2����ǩ����һ��js�����磬���wtree.js���������ṹ��������ο�/jsoa/public/readme.txt��
<script language="javascript" type="text/javascript">
var d=new dTree('d','/jsoa/js/tree/images/menubar/')
	d.config.folderLinks=true;
	d.add(0,-1,'�������ǻ�����������޹�˾','javascript:submit(1);','�������ǻ�����������޹�˾','');
	d.add(20080808582,0,'�ۺϲ�','javascript:submit(20080808582);','�ۺϲ�','');
	d.add(20080808583,0,'�ͻ�����','javascript:submit(20080808583);','�ͻ�����','');
	document.getElementById('treearea').innerHTML = d;
	function submit(a){
	if(a==20080808582){
		orgTreeBar.action="/jsoa/selectObj.do?method=getOrgUserList&range=*0*&allowId=sendToId&type=orgPerson&orgInputType=yes&inputType=no&condition=&orgName=�ۺϲ�&orgId=20080808582";
	}
	if(a==20080808583){
		orgTreeBar.action="/jsoa/selectObj.do?method=getOrgUserList&range=*0*&allowId=sendToId&type=orgPerson&orgInputType=yes&inputType=no&condition=&orgName=�ͻ�����&orgId=20080808583";
	}

</script>
