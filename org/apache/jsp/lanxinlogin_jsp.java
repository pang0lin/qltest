/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:38:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import com.js.oa.lanxin.logon.LanxinLogonAction;
import com.js.oa.lanxin.util.LanXinUtil;
import com.js.lang.Resource;
import java.util.*;
import java.sql.*;
import com.js.oa.haier.bean.*;
import com.js.util.mail.*;

public final class lanxinlogin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


public String getTableId(java.sql.Statement stmt) throws Exception {
	String seq = "0";
	if ("oracle".equals(com.js.util.config.SystemCommon.getDatabaseType())) {
		ResultSet rs = stmt
				.executeQuery("select hibernate_sequence.nextval from dual");
		if (rs.next()) {
			seq = rs.getString(1);
		}
		rs.close();
	} else {
		try {
			ResultSet rs = stmt
					.executeQuery("select seq_seq from JSDB.oa_seq where id=1");
			if (rs.next()) {
				seq = (rs.getLong(1) + 1) + "";
				stmt.execute("update JSDB.oa_seq set seq_seq=seq_seq+1");
			} else {
				seq = "1000";
				stmt.execute("insert into JSDB.oa_seq (id, seq_seq) values (1, 1000)");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return seq;
}
public boolean syncFlowInstance(String flowId,String instanceRecordId){
	boolean result=false;
	java.sql.Connection exeConn=null;
	java.sql.Connection conn=null;	
	
	try{
		exeConn=new com.js.util.util.DataSourceBase().getDataSource().getConnection();
		conn=new com.js.util.util.DataSourceBase().getDataSource().getConnection();
		java.sql.Statement exeStmt=exeConn.createStatement();
		java.sql.Statement stmt=conn.createStatement();
		/**
		 * 1.取得所有的未办结的流程实例信息 发起人employee_id,table_id,record_id 
		 * 2.根据每个流程实例删除原来的节点信息，增加新的流程信息
		 */
		List<String[]> instanceList=new ArrayList<String[]>();
		String sql="select workrecord_id,worktable_id,wf_submitemployee_id from jsf_work where workstatus=1 and workprocess_id=?";
		if(!"".equals(instanceRecordId)){
			sql+=" and workrecord_id=?";
		}
		java.sql.PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, flowId);
		if(!"".equals(instanceRecordId)){
			pstmt.setString(2, instanceRecordId);
		}
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			instanceList.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
		}
		rs.close();
		pstmt.close();
		
		for(String[] instanceArr:instanceList){
			//删除原流程实例的节点信息
			StringBuffer idBuffer=new StringBuffer(1024);
			pstmt=conn.prepareStatement("select wf_proceedactivity_id from jsf_p_activity where wf_workflowprocess_id=? and trecord_id=?");
			pstmt.setString(1, flowId);
			pstmt.setString(2, instanceArr[0]);
			
			rs=pstmt.executeQuery();				
			while(rs.next()){
				idBuffer.append(rs.getString(1)).append(",");					
			}
			rs.close();
			
			idBuffer.append("-1");
			
			stmt.clearBatch();
			sql="delete from jsf_p_tr where wf_proceedtransition_id in(select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in ("+idBuffer.toString()+") )";
			stmt.addBatch(sql);
			
			sql="delete from jsf_p_transition where wf_proceedactivity_id in ("+idBuffer.toString()+")";
			stmt.addBatch(sql);
			
			sql="delete from jsf_p_press where wf_proceedactivity_id in ("+idBuffer.toString()+")";
			stmt.addBatch(sql);
			
			sql="delete from jsf_p_readwritecontrol where wf_proceedactivity_id in ("+idBuffer.toString()+")";
			stmt.addBatch(sql);
			
			sql="delete from JSF_p_activity where wf_workflowprocess_id="+flowId+" and trecord_id="+instanceArr[0];
			stmt.addBatch(sql);
			
			stmt.executeBatch();
			stmt.clearBatch();
			
			//新增
			String domainId = "0";
            ArrayList activitylist = new ArrayList();
            sql = "select wf_activity_id,activityName,activityDescription,activityType," +
                  "allowStandFor,allowCancel,allowTransition,participantType,participantUser," +
                  "participantGroup,participantUserName,participantUserField,pressType," +
                  "deadLineTime,pressMotionTime,activityDocumentation,activityIcon," +
                  "activityBeginEnd,transactType,actiCommField,needPassRound,passRoundUserType," +
                  "passRoundUser,passRoundUserGroup,passRoundUserName,passRoundUserField," +
                  "passRoundCommField,participantRole,passRoundRole,activityClass,activitySubProc," +
                  "subProcType,PARTICIPANTGIVENORG,PASSROUNDGIVENORG,commentRange,domain_id,operButton,"+
                  "form_id,actiCommFieldType,passRoundCommFieldType,allowSmsRemind,tranType,tranCustomExtent,"+
                  "tranCustomExtentId,pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,allowautocheck,isDivide,isGather,isBranch,activityDelaySend from JSDB.jsf_activity where wf_workflowprocess_id = " + flowId;

            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Object[] obj = new Object[52];
                obj[0] = rs.getString("wf_activity_id");// + "";
                obj[1] = rs.getString("activityName");
                obj[2] = rs.getString("activityDescription");
                obj[3] = rs.getString("activityType");// + "";
                obj[4] = rs.getString("allowStandFor");// + "";
                obj[5] = rs.getString("allowCancel");// + "";
                obj[6] = rs.getString("allowTransition");// + "";
                obj[7] = rs.getString("participantType");// + "";
                obj[8] = rs.getString("participantUser");
                obj[9] = rs.getString("participantGroup");
                obj[10] = rs.getString("participantUserName");
                obj[11] = rs.getString("participantUserField");
                obj[12] = rs.getInt("pressType") + "";
                obj[13] = rs.getInt("deadLineTime") + "";
                obj[14] = rs.getInt("pressMotionTime") + "";
                obj[15] = rs.getString("activityDocumentation");
                obj[16] = rs.getString("activityIcon");
                obj[17] = rs.getString("activityBeginEnd");// + "";
                obj[18] = rs.getString("transactType");
                obj[19] = rs.getString("actiCommField");
                obj[20] = rs.getString("needPassRound");
                obj[21] = rs.getString("passRoundUserType");
                obj[22] = rs.getString("passRoundUser");
                obj[23] = rs.getString("passRoundUserGroup");
                obj[24] = rs.getString("passRoundUserName");
                obj[25] = rs.getString("passRoundUserField");
                obj[26] = rs.getString("passRoundCommField");
                obj[27] = rs.getString("participantRole");
                obj[28] = rs.getString("passRoundRole");
                obj[29] = rs.getString("activityClass");
                obj[30] = rs.getString("activitySubProc");
                obj[31] = rs.getString("subProcType");
                obj[32] = rs.getString("PARTICIPANTGIVENORG");
                obj[33] = rs.getString("PASSROUNDGIVENORG");
                obj[34] = rs.getString("commentRange");
                domainId = rs.getString("domain_id");
                obj[35] = rs.getString("operButton");
                obj[36] = rs.getString("form_id");
                obj[37] = rs.getString("actiCommFieldType");
                obj[38] = rs.getString("passRoundCommFieldType");
                obj[39] = rs.getString("allowSmsRemind")==null?"0":rs.getString("allowSmsRemind");
                obj[40] = rs.getString("tranType")==null?"0":rs.getString("tranType");
                obj[41] = rs.getString("tranCustomExtent");
                obj[42] = rs.getString("tranCustomExtentId");
                obj[43] = rs.getString("pressDealType")==null?"0":rs.getString("pressDealType");
                obj[44] = rs.getString("tranReadType")==null?"0":rs.getString("tranReadType");
                obj[45] = rs.getString("tranReadCustomExtent");
                obj[46] = rs.getString("tranReadCustomExtentId");
                obj[47] = rs.getString("allowautocheck");
                obj[48] = rs.getString("isDivide");
                obj[49] = rs.getString("isGather");
                obj[50] = rs.getString("isBranch");
                obj[51] = rs.getString("activityDelaySend");
                activitylist.add(obj);
            }
            rs.close();
            
            exeStmt.clearBatch();
            Calendar cal = Calendar.getInstance(); 
            Object[] obj = null;
            for(int i = 0; i < activitylist.size(); i ++){
                obj = (Object[]) activitylist.get(i);
                String wfProceedActivityId = getTableId(stmt);//ID

                String partRole = "", passRole = "",activityDelaySend="";
                if(obj[27] != null) partRole = obj[27].toString();
                if(obj[28] != null) passRole = obj[28].toString();
                if(obj[51] != null) {//此处仅针对每年执行一次流程设置，可扩展成周期性的
                	activityDelaySend = cal.get(Calendar.YEAR)+"-"+obj[51].toString()+" 08:00:00";
                }
                sql = " insert into JSDB.JSF_p_Activity (" +
                      " WF_ProceedActivity_Id, wf_activity_id, activityName, activityDescription," +
                      " activityType, allowStandFor, allowCancel, allowTransition, participantType," +
                      " participantUser, participantGroup, participantUserName, participantUserField," +
                      " pressType, deadLineTime, pressMotionTime, activityDocumentation, activityIcon," +
                      " activityBeginEnd, dealWithSign, wf_workflowprocess_id, employee_id, " +
                      " ttable_id, trecord_id, transactType, actiCommField , needPassRound, passRoundUserType, " +
                      "passRoundUser, passRoundUserGroup, passRoundUserName, passRoundUserField, passRoundCommField," +
                      "participantRole, passRoundRole, activityClass, activitySubProc, subProcType,PARTICIPANTGIVENORG,"+
                      "PASSROUNDGIVENORG, commentRange,DOMAIN_ID,operbutton,form_id,actiCommFieldType,passRoundCommFieldType,"+
                      "allowSmsRemind,tranType,tranCustomExtent,tranCustomExtentId, pressDealType,tranReadType,tranReadCustomExtent,tranReadCustomExtentId,allowautocheck,isDivide,isGather,isBranch,activityDelaySend) values (" +
                      wfProceedActivityId + "," + obj[0] + ",'" + obj[1] + "','" + obj[2] + "'," +
                      obj[3] + "," + obj[4] + "," + obj[5] + "," + obj[6] + "," + obj[7] + ",'" +
                      obj[8] + "','" + obj[9] + "','" + obj[10] + "','" + obj[11] + "'," +
                      obj[12] + "," + obj[13] + "," + obj[14] + ",'" + obj[15] + "','" + obj[16] + "'," +
                      obj[17] + ",0," + flowId + "," + instanceArr[2] + "," + instanceArr[1] + "," + instanceArr[0] +
                      ",'" + obj[18] + "', '" + obj[19] + "', " + obj[20] + ", " + obj[21] + ", '" + obj[22] +
                      "', '" + obj[23] + "','" + obj[24] + "', '" + obj[25] + "', '" + obj[26] + "', '" + partRole +"'," +
                      "'" + passRole + "', " + obj[29] + ", " + obj[30] + ", " + obj[31] + ",'" + obj[32] + "','" + obj[33] +
                      "', '" + obj[34] + "'," + domainId+ ", '" + obj[35] + "', '" + obj[36] + "', '" + obj[37] +
                      "', '" + obj[38]+ "', " + obj[39] + ", " + obj[40] + ", '" + obj[41] + "', '" + obj[42] +"',"+obj[43]+
                      ", " + obj[44] + ", '" + obj[45] + "', '" + obj[46]+"',"+obj[47]+","+obj[48]+","+obj[49]+","+obj[50]+",'"+activityDelaySend+"')";

                //stmt.executeUpdate(sql);
                exeStmt.addBatch(sql);
                
                

                //复制读写控制
                String sql2 = "";
                ArrayList alist = new ArrayList();
                sql2 = " select controlType, controlField from JSDB.JSF_readwritecontrol " +
                       " where controltype>0 and wf_activity_id = " + obj[0];
                rs = stmt.executeQuery(sql2);
                while(rs.next()){
                    Object[] rwControlObj = new Object[2];
                    rwControlObj[0] = rs.getString("controlType");
                    rwControlObj[1] = rs.getString("controlField");
                    alist.add(rwControlObj);
                }
                rs.close();
                Object[] obj2 = null;
                for(int j = 0; j < alist.size(); j ++){
                    obj2 = (Object[]) alist.get(j);
                    String wf_proceedReadWriteControl_id = getTableId(stmt);
                    
                    sql2 = " insert into JSDB.jsf_p_readwritecontrol ( " +
                           " wf_proceedreadwritecontrol_id, controlType, controlField," +
                           " wf_activity_id, wf_proceedactivity_id, DOMAIN_ID " +
                           " ) values (" +
                           wf_proceedReadWriteControl_id + "," + obj2[0] + "," + obj2[1] + "," +
                           obj[0] + "," + wfProceedActivityId + "," + domainId + ")";
                    //stmt.executeUpdate(sql2);
                    exeStmt.addBatch(sql2);
                }

                //复制办理期限
                alist.clear();
                sql2 = "select conditionField, pressRelation, compareValue, pressMotionTime, motionTime " +
                       "from JSDB.jsf_press where wf_activity_id = " + obj[0];
                rs = stmt.executeQuery(sql2);
                while(rs.next()){
                    Object[] pressObj = new Object[5];
                    pressObj[0] = rs.getInt("conditionField") + "";
                    pressObj[1] = rs.getString("pressRelation");
                    pressObj[2] = rs.getString("compareValue");
                    pressObj[3] = rs.getInt("pressMotionTime") + "";
                    pressObj[4] = rs.getInt("motionTime") + "";
                    alist.add(pressObj);
                }
                rs.close();
                for(int j = 0; j < alist.size(); j ++){
                    obj2 = (Object[]) alist.get(j);
                    String wf_proceedpress_id = getTableId(stmt);
                    sql2 = " insert into JSDB.jsf_p_press ( " +
                           " wf_press_id, conditionField, pressrelation, compareValue, pressMotionTime, " +
                           " motionTime, wf_activity_id, wf_proceedactivity_id,DOMAIN_ID " +
                           " ) values (" +
                           wf_proceedpress_id + "," + obj2[0] + ",'" + obj2[1] + "','" + obj2[2] + "'," +
                           obj2[3] + "," + obj2[4] + "," + obj[0] + "," + wfProceedActivityId + "," + domainId + ")";
                    //stmt.executeUpdate(sql2);
                    exeStmt.addBatch(sql2);
                }

                //复制节点关系
                alist.clear();
                sql2 = "select wf_transition_id, transitionFrom, transitionName, transitionTo, " +
                       " transitionDescription,EXPRESSION,defaultActivity from JSDB.jsf_transition where transitionFrom = " + obj[0];

                rs = stmt.executeQuery(sql2);

                while(rs.next()){
                    Object[] transObj = new Object[7];
                    transObj[0] = rs.getInt("wf_transition_id") + "";
                    transObj[1] = rs.getInt("transitionFrom") + "";
                    transObj[2] = rs.getString("transitionName");
                    transObj[3] = rs.getInt("transitionTo") + "";
                    transObj[4] = rs.getString("transitionDescription");
                    transObj[5] = rs.getString("EXPRESSION");//新增表达式选项 
                    transObj[6] = rs.getString("defaultActivity");//默认后继
                    alist.add(transObj);
                }
                rs.close();
                for(int j = 0; j < alist.size(); j ++){
                    obj2 = (Object[]) alist.get(j);
                    String wf_proceedtransition_id = getTableId(stmt);                   
                    sql2 = " insert into JSDB.jsf_p_transition ( " +
                           " wf_proceedtransition_id, transitionFrom, transitionName, transitionTo," +
                           " transitionDescription, wf_proceedactivity_id, DOMAIN_ID, EXPRESSION,defaultActivity " +
                           ") values (" +
                           wf_proceedtransition_id + "," + obj2[1] + ",'" + obj2[2] + "'," + obj2[3] +
                           ",'" + obj2[4] + "'," + wfProceedActivityId + ", " + domainId + ",'"+obj2[5]+ "',"+(obj2[6]==null?"0":obj2[6])+")";
                    
                    //stmt.executeUpdate(sql2);
                    exeStmt.addBatch(sql2);

                    //复制转移约束
                    sql2 = "select conditionField, compareValue, relation from JSDB.jsf_transitionrestriction " +
                           " where wf_transition_id = " + obj2[0];

                    rs = stmt.executeQuery(sql2);

                    ArrayList blist = new ArrayList();
                    while(rs.next()){
                        Object[] condiObj = new Object[3];
                        condiObj[0] = rs.getInt("conditionField") + "";
                        condiObj[1] = rs.getString("compareValue");
                        condiObj[2] = rs.getString("relation");
                        blist.add(condiObj);
                    }
                    rs.close();
                    Object[] obj3 = null;
                    for(int k = 0; k < blist.size(); k ++){
                        obj3 = (Object[]) blist.get(k);
                        String wf_proceedtr_id = getTableId(stmt);                        
                        sql2 = " insert into JSDB.jsf_p_tr (" +
                               " wf_proceedtr_id, conditionField, compareValue, relation, " +
                               " wf_proceedtransition_id, DOMAIN_ID " +
                               " ) values (" +
                               wf_proceedtr_id + "," + obj3[0] + ",'" + obj3[1] + "','" + obj3[2] + "'," +
                               wf_proceedtransition_id + "," + domainId + ")";
                        //stmt.executeUpdate(sql2);
                        exeStmt.addBatch(sql2);
                    }
                    blist.clear();
                }
                alist.clear();
            }	            
            exeStmt.executeBatch();
            exeStmt.clearBatch();	
            
           
            activitylist.clear();				
		}
		conn.close();
		exeStmt.close();
		exeConn.close();
	}catch(Exception ex){
		ex.printStackTrace();
		if(exeConn!=null){
			try{
				exeConn.close();
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		if(conn!=null){
			try{
				conn.close();
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		
	}
	return result;
}

public void transToERP(){
	String processeId = "90621"; //request.getParameter("processId");
	String recorded ="5579566"; //request.getParameter("recordId");
	String userId = "42552"; // 方珍霞//session.getAttribute("userId").toString();
	System.out.println("userId:"+userId);
	ERPSyncEJBBean eRPSyncEJBBean = new ERPSyncEJBBean();
	String sql = "select jst_3013_f3183,jst_3013_f3182,jst_3013_f3268,jst_3013_f3269,jst_3013_f3184,jst_3013_f3238,jst_3013_f3239,jst_3013_f3325,jst_3013_f3201,jst_3013_owner,jst_3013_f3278,jst_3013_f3277 from  jst_3013 where jst_3013_foreignkey = "+recorded;
	List<String[]> MADList = eRPSyncEJBBean.getNewPrdtMaterial(sql,12);
	//数据插入前验证
	for (int i = 0; i < MADList.size(); i++) {
		String[] data = MADList.get(i);
		String flag = eRPSyncEJBBean.checkPrdtNoExists(data[0], data[8]);
		//prdt表插入你数据
		if ("1".equals(flag)) {
			//更新数据
			System.out.println("新机型更新数据————————！");
			eRPSyncEJBBean.updateModelAppendDataToErpPrdt(data, userId);
			System.out.println("新机型更新数据完成————————！");
		}else if ("2".equals(flag)) {
			//插入数据
			System.out.println("新机型插入数据————————！");
			eRPSyncEJBBean.saveModelAppendDataToErpPrdt(data,userId,true);
			System.out.println("新机型插入数据完成————————！");
		}else if ("3".equals(flag)) {
			//插入停用数据
			System.out.println("新机型插入停用数据————————！");
			eRPSyncEJBBean.saveModelAppendDataToErpPrdt(data,userId,false);
			//发邮件提醒
			String[] resArr=eRPSyncEJBBean.getUserMailById(data[9]);
			if(resArr!=null&& !"".equals(resArr[1])){
				try {
					Mail wm=new Mail();
					wm.setSendTo(resArr[1]);
					wm.setHtml(true);
					wm.setSubjectTitle("新品图号重复提醒");
					wm.setBoby(resArr[0]+":<br  /><p>您好，这封信是由OA系统自动发送的，请勿回复。</p><p>图号"+data[0]+"在ERP内存在重复图号，已被停用，请与ERP录入人员补全资料重启，否则将无法使用!</p>");
					MailSender.send(wm, MailConfig.getEmailSMTP(),MailConfig.getEmailCount(),MailConfig.getEmailPWD(),MailConfig.getEmailPort(),MailConfig.getEncryptionType());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("*********未获取到邮件发送人或者邮件发送人邮箱为空");
			}
			
		}
		//prdt_z表插入数据
		eRPSyncEJBBean.saveMaterialDataToErpPrdtZForXjx(data[1],data[8],data[11]);
		
	}
}

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("com.js.oa.haier.bean");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_packages.add("com.js.util.mail");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.oa.lanxin.logon.LanxinLogonAction");
    _jspx_imports_classes.add("com.js.oa.lanxin.util.LanXinUtil");
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.ViewMenu");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');


response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);


System.out.println("11111111111");
//this.syncFlowInstance("64977","3181411");
this.transToERP();
System.out.println("2222222222222");


      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\r\n<title>九思-蓝信</title>\r\n\r\n</head>\r\n<body > \r\n   \r\n</body>\r\n</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
