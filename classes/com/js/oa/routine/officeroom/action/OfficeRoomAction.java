package com.js.oa.routine.officeroom.action;

import com.js.oa.routine.officeroom.bean.OfficeRoomEJBBean;
import com.js.oa.routine.officeroom.po.OfficeBuildPO;
import com.js.oa.routine.officeroom.po.OfficePO;
import com.js.oa.routine.officeroom.po.OfficeUsePO;
import com.js.oa.routine.officeroom.service.OfficeRoomBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ConversionString;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OfficeRoomAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    OfficeRoomActionForm officeRoomActionForm = (OfficeRoomActionForm)actionForm;
    String action = request.getParameter("action");
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    OfficeRoomBD officeRoomBD = new OfficeRoomBD();
    if ("list".equals(action)) {
      buildList(request);
    } else {
      if ("save".equals(action)) {
        OfficeBuildPO po = new OfficeBuildPO();
        po.setBuildname(officeRoomActionForm.getBuildname());
        po.setBuildidaccount(officeRoomActionForm.getBuildidaccount());
        po.setBuildparentid("1");
        po.setBuildreadname(officeRoomActionForm.getBuildreadname());
        po.setBuildAdminId(officeRoomActionForm.getBuildAdminId());
        po.setBuildAdminName(officeRoomActionForm.getBuildAdminName());
        po.setCreatedemp(Long.valueOf(Long.parseLong(userId)));
        po.setCreatedempname(userName);
        po.setBz(officeRoomActionForm.getBz());
        ConversionString conversionString = new ConversionString(officeRoomActionForm.getBuildreader());
        String userIds = conversionString.getUserString();
        String orgIds = conversionString.getOrgString();
        String groupIds = conversionString.getGroupString();
        po.setBuildreader(userIds);
        po.setBuildreadgroup(groupIds);
        po.setBuildreadorg(orgIds);
        officeRoomBD.save(po);
        return actionMapping.findForward("add");
      } 
      if (action.equals("modify") || action.equals("view")) {
        Long id = new Long(request.getParameter("id"));
        OfficeBuildPO po = officeRoomBD.load(id);
        officeRoomActionForm.setBuidId(id);
        officeRoomActionForm.setBuildname(po.getBuildname());
        officeRoomActionForm.setBuildidaccount(po.getBuildidaccount());
        officeRoomActionForm.setBuildreadname(po.getBuildreadname());
        officeRoomActionForm.setBz(po.getBz());
        officeRoomActionForm.setBuildAdminId(po.getBuildAdminId());
        officeRoomActionForm.setBuildAdminName(po.getBuildAdminName());
        String userIds = po.getBuildreader();
        if (userIds == null || "null".equals(userIds))
          userIds = ""; 
        String orgIds = po.getBuildreadorg();
        if (orgIds == null || "null".equals(orgIds))
          orgIds = ""; 
        String groupIds = po.getBuildreadgroup();
        if (groupIds == null || "null".equals(groupIds))
          groupIds = ""; 
        officeRoomActionForm.setBuildreader(String.valueOf(userIds) + orgIds + groupIds);
        if (action.equals("view"))
          return actionMapping.findForward("view"); 
        return actionMapping.findForward("modify");
      } 
      if ("update".equals(action)) {
        OfficeBuildPO po = new OfficeBuildPO();
        po.setBuildname(officeRoomActionForm.getBuildname());
        po.setBuildidaccount(officeRoomActionForm.getBuildidaccount());
        po.setBuildreader(officeRoomActionForm.getBuildreader());
        po.setBuildreadname(officeRoomActionForm.getBuildreadname());
        po.setCreatedemp(Long.valueOf(Long.parseLong(userId)));
        po.setBz(officeRoomActionForm.getBz());
        po.setBuildAdminId(officeRoomActionForm.getBuildAdminId());
        po.setBuildAdminName(officeRoomActionForm.getBuildAdminName());
        ConversionString conversionString = new ConversionString(officeRoomActionForm.getBuildreader());
        String userIds = conversionString.getUserString();
        String orgIds = conversionString.getOrgString();
        String groupIds = conversionString.getGroupString();
        po.setBuildreader(userIds);
        po.setBuildreadgroup(groupIds);
        po.setBuildreadorg(orgIds);
        officeRoomBD.update(po, officeRoomActionForm.getBuidId());
        return actionMapping.findForward("modify");
      } 
      if ("delete".equals(action)) {
        if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
          Long id = new Long(request.getParameter("id"));
          List isdeleteList = officeRoomBD.getListBuildNumber(id.toString(), "", "");
          if (isdeleteList != null && isdeleteList.size() > 0) {
            request.setAttribute("message", "deleteFalse");
          } else {
            officeRoomBD.delete(id);
          } 
        } 
        action = "list";
      } else if ("search".equals(action)) {
        buildList(request);
      } else if ("listOffice".equals(action)) {
        List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
        request.setAttribute("buildList", list);
        officeList(request);
      } else {
        if ("officeAdd".equals(action)) {
          List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
          List directionList = officeRoomBD.getDirectionList("", "");
          request.setAttribute("directionList", directionList);
          request.setAttribute("buildList", list);
          return actionMapping.findForward("officeAdd");
        } 
        if ("officeSave".equals(action)) {
          OfficePO po = new OfficePO();
          po.setOfficename(officeRoomActionForm.getOfficename());
          po.setOfficenumber(officeRoomActionForm.getOfficenumber());
          po.setBuidId(officeRoomActionForm.getBuidId());
          po.setBuildname(officeRoomBD.getBuildname((String)officeRoomActionForm.getBuidId()));
          po.setBuidnumber(officeRoomActionForm.getBuidnumber());
          po.setOfficearea(officeRoomActionForm.getOfficearea());
          po.setOfficefaces(officeRoomActionForm.getOfficefaces());
          po.setOfficereader(officeRoomActionForm.getOfficereader());
          po.setOfficereadname(officeRoomActionForm.getOfficereadname());
          po.setCreatedemp(Long.valueOf(Long.parseLong(userId)));
          po.setCreatedempname(userName);
          po.setBz(officeRoomActionForm.getBz());
          ConversionString conversionString = new ConversionString(officeRoomActionForm.getOfficereader());
          String userIds = conversionString.getUserString();
          String orgIds = conversionString.getOrgString();
          String groupIds = conversionString.getGroupString();
          po.setOfficereader(userIds);
          po.setOfficereadgroup(groupIds);
          po.setOfficereadorg(orgIds);
          po.setOfficeAdminId(officeRoomActionForm.getOfficeAdminId());
          po.setOfficeAdminName(officeRoomActionForm.getOfficeAdminName());
          po.setOfficestation(officeRoomActionForm.getOfficestation());
          List listCoun = officeRoomBD.getOfficeIsTrue((String)officeRoomActionForm.getBuidId(), (
              new StringBuilder(String.valueOf(officeRoomActionForm.getBuidnumber()))).toString(), (
              new StringBuilder(String.valueOf(officeRoomActionForm.getOfficenumber()))).toString());
          if (listCoun != null && listCoun.size() > 0) {
            List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
            List<Object[]> officeLists = officeRoomBD.getListBuild(po.getBuidId().toString());
            List applayNum = null;
            if (officeLists != null && officeLists.size() > 0)
              for (int ms = 0; ms < officeLists.size(); ms++) {
                Object[] objc = officeLists.get(ms);
                request.setAttribute("applayCount", (objc[2] == null) ? "0" : objc[2].toString());
              }  
            request.setAttribute("buildList", list);
            request.setAttribute("message", "officeSaveFalse");
            request.setAttribute("buildId", officeRoomActionForm.getBuidId());
            request.setAttribute("buidNumber", officeRoomActionForm.getBuidnumber());
          } else {
            officeRoomBD.officeSave(po);
          } 
          return actionMapping.findForward("officeAdd");
        } 
        if (action.equals("officeModify") || action.equals("officeView")) {
          Long id = new Long(request.getParameter("id"));
          OfficePO po = officeRoomBD.officeLoad(id);
          officeRoomActionForm.setOfficeId(id);
          officeRoomActionForm.setOfficename(po.getOfficename());
          officeRoomActionForm.setOfficenumber(po.getOfficenumber());
          officeRoomActionForm.setBuidId(po.getBuidId());
          officeRoomActionForm.setBuidnumber(po.getBuidnumber());
          officeRoomActionForm.setOfficearea(po.getOfficearea());
          officeRoomActionForm.setOfficefaces(po.getOfficefaces());
          officeRoomActionForm.setOfficereader(po.getOfficereader());
          officeRoomActionForm.setOfficereadname(po.getOfficereadname());
          officeRoomActionForm.setBz(officeRoomActionForm.getBz());
          officeRoomActionForm.setOfficeAdminId(po.getOfficeAdminId());
          officeRoomActionForm.setOfficeAdminName(po.getOfficeAdminName());
          officeRoomActionForm.setOfficestation(po.getOfficestation());
          String userIds = po.getOfficereader();
          if (userIds == null || "null".equals(userIds))
            userIds = ""; 
          String orgIds = po.getOfficereadorg();
          if (orgIds == null || "null".equals(orgIds))
            orgIds = ""; 
          String groupIds = po.getOfficereadgroup();
          if (groupIds == null || "null".equals(groupIds))
            groupIds = ""; 
          officeRoomActionForm.setBuildreader(String.valueOf(userIds) + orgIds + groupIds);
          request.setAttribute("Officenumber", po.getOfficenumber());
          List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
          List<Object[]> officeLists = officeRoomBD.getListBuild(po.getBuidId().toString());
          List applayNum = null;
          if (officeLists != null && officeLists.size() > 0)
            for (int ms = 0; ms < officeLists.size(); ms++) {
              Object[] objc = officeLists.get(ms);
              request.setAttribute("applayCount", (objc[2] == null) ? "0" : objc[2].toString());
            }  
          List directionList = officeRoomBD.getDirectionList("", "");
          request.setAttribute("directionList", directionList);
          request.setAttribute("buildList", list);
          request.setAttribute("buidNumber", po.getBuidnumber());
          request.setAttribute("build", po.getBuidId());
          request.setAttribute("directName", (new StringBuilder(String.valueOf(po.getOfficefaces()))).toString());
          if (action.equals("officeView"))
            return actionMapping.findForward("officeView"); 
          return actionMapping.findForward("officeModify");
        } 
        if ("officeUpdate".equals(action)) {
          OfficePO po = new OfficePO();
          po.setOfficename(officeRoomActionForm.getOfficename());
          po.setOfficenumber(officeRoomActionForm.getOfficenumber());
          po.setBuidId(officeRoomActionForm.getBuidId());
          po.setBuildname(officeRoomBD.getBuildname((String)officeRoomActionForm.getBuidId()));
          po.setBuidnumber(officeRoomActionForm.getBuidnumber());
          po.setOfficearea(officeRoomActionForm.getOfficearea());
          po.setOfficefaces(officeRoomActionForm.getOfficefaces());
          po.setOfficereadname(officeRoomActionForm.getOfficereadname());
          ConversionString conversionString = new ConversionString(officeRoomActionForm.getOfficereader());
          String userIds = conversionString.getUserString();
          String orgIds = conversionString.getOrgString();
          String groupIds = conversionString.getGroupString();
          po.setOfficereader(userIds);
          po.setOfficereadgroup(groupIds);
          po.setOfficereadorg(orgIds);
          po.setOfficeAdminId(officeRoomActionForm.getOfficeAdminId());
          po.setOfficeAdminName(officeRoomActionForm.getOfficeAdminName());
          po.setOfficestation(officeRoomActionForm.getOfficestation());
          request.setAttribute("Officenumber", officeRoomActionForm.getOfficenumber());
          String officenumberOld = request.getParameter("officenumberOld");
          if (!officenumberOld.equals((new StringBuilder(String.valueOf(officeRoomActionForm.getOfficenumber()))).toString())) {
            List listCoun = officeRoomBD.getOfficeIsTrue((String)officeRoomActionForm.getBuidId(), (
                new StringBuilder(String.valueOf(officeRoomActionForm.getBuidnumber()))).toString(), (
                new StringBuilder(String.valueOf(officeRoomActionForm.getOfficenumber()))).toString());
            if (listCoun != null && listCoun.size() > 0) {
              List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
              List<Object[]> officeLists = officeRoomBD.getListBuild((String)officeRoomActionForm.getBuidId());
              List applayNum = null;
              if (officeLists != null && officeLists.size() > 0)
                for (int ms = 0; ms < officeLists.size(); ms++) {
                  Object[] objc = officeLists.get(ms);
                  request.setAttribute("applayCount", (objc[2] == null) ? "0" : objc[2].toString());
                }  
              request.setAttribute("Officenumber", officenumberOld);
              request.setAttribute("message", "officeSaveFalse");
              request.setAttribute("buildList", list);
              request.setAttribute("buidNumber", officeRoomActionForm.getBuidnumber());
              request.setAttribute("build", officeRoomActionForm.getBuidId());
              request.setAttribute("officenumberOld", officenumberOld);
            } 
          } else {
            officeRoomBD.officeUpdate(po, officeRoomActionForm.getOfficeId());
          } 
          return actionMapping.findForward("officeModify");
        } 
        if ("officeDelete".equals(action)) {
          if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
            Long id = new Long(request.getParameter("id"));
            String buildId = request.getParameter("buildId");
            String officeNumber = request.getParameter("officeNumber");
            String buildNumber = request.getParameter("buildNumber");
            List list = officeRoomBD.getOfficeUses(buildId, buildNumber, officeNumber);
            if (list != null && list.size() > 0) {
              request.setAttribute("message", "deleteFalse");
            } else {
              officeRoomBD.officeDelete(id);
            } 
          } 
          action = "listOffice";
        } else if ("officeSearch".equals(action)) {
          officeList(request);
        } else if ("listOfficeUse".equals(action)) {
          officeUseList(request);
        } else {
          if ("officeUseAdd".equals(action)) {
            List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
            request.setAttribute("buildList", list);
            List titleList = officeRoomBD.getTitleList("", "");
            request.setAttribute("titleList", titleList);
            request.setAttribute("buildId", request.getParameter("buildId"));
            officeRoomActionForm.setApplayClass("0");
            officeRoomActionForm.setApplaySex("0");
            return actionMapping.findForward("officeUseAdd");
          } 
          if ("officeUseSave".equals(action)) {
            OfficeUsePO po = new OfficeUsePO();
            po.setApplayBuildId(officeRoomActionForm.getApplayBuildId());
            request.setAttribute("buildId", officeRoomActionForm.getApplayBuildId());
            po.setApplayBuildNumber(officeRoomActionForm.getApplayBuildNumber());
            po.setApplayNumber(officeRoomActionForm.getApplayNumber());
            po.setApplayClass(officeRoomActionForm.getApplayClass());
            officeRoomActionForm.setApplayClass(officeRoomActionForm.getApplayClass());
            po.setApplayOrg(officeRoomActionForm.getApplayOrg());
            po.setApplayOrgName(officeRoomActionForm.getApplayOrgName());
            po.setApplayReason(officeRoomActionForm.getApplayReason());
            po.setApplaySex(officeRoomActionForm.getApplaySex());
            po.setApplayTitle(officeRoomActionForm.getApplayTitle());
            po.setApplayUserId(officeRoomActionForm.getApplayUserId());
            po.setApplayUsername(officeRoomActionForm.getApplayUsername());
            Date visitBeginTime = new Date(request.getParameter("visitBeginTime"));
            Date visitEndTime = new Date(request.getParameter("visitEndTime"));
            po.setVisitBeginTime(visitBeginTime);
            po.setVisitEndTime(visitEndTime);
            po.setVisitName(officeRoomActionForm.getVisitName());
            po.setVisitOrg(officeRoomActionForm.getVisitOrg());
            po.setVisitOrgName(officeRoomActionForm.getVisitOrgName());
            po.setVisitWorkunit(officeRoomActionForm.getVisitWorkunit());
            po.setBz(officeRoomActionForm.getBz());
            po.setApplayStation(officeRoomActionForm.getApplayStation());
            po.setApplayKth(officeRoomActionForm.getApplayKth());
            int applayStation = 0;
            if (officeRoomActionForm.getApplayStation() != null)
              applayStation = Integer.parseInt((String)officeRoomActionForm.getApplayStation()); 
            String OfficeTotalStation = officeRoomBD.getOfficeTotalStation((new StringBuilder(String.valueOf(officeRoomActionForm.getApplayBuildId()))).toString(), (
                new StringBuilder(String.valueOf(officeRoomActionForm.getApplayBuildNumber()))).toString(), (new StringBuilder(String.valueOf(officeRoomActionForm.getApplayNumber()))).toString(), (new StringBuilder(String.valueOf(officeRoomActionForm.getApplayUserId()))).toString());
            String[] use = OfficeTotalStation.split("&");
            int stationUse = 0;
            stationUse = Integer.parseInt(use[0].toString()) - Integer.parseInt(use[1].toString());
            int alreadyUse = Integer.parseInt(use[1].toString());
            int maxUse = Integer.parseInt(use[0].toString());
            int shengyu = maxUse - alreadyUse;
            if (maxUse > 0) {
              if (applayStation > shengyu) {
                request.setAttribute("maxUse", Integer.valueOf(shengyu));
                request.setAttribute("message", "-1");
              } else {
                officeRoomBD.officeUseSave(po);
                String title = String.valueOf(userName) + "提交的办公室使用需要您处理！";
                String url = "/jsoa/OfficeRoomAction.do?action=listOfficeUse&buildId=" + po.getApplayBuildId() + "&buildNumber=" + po.getApplayBuildNumber() + "&applayNumber=" + po.getApplayNumber() + " &isSh=0&flagMark=1 ";
                String adminIdString = officeRoomBD.getBuildAdminId((new StringBuilder(String.valueOf(po.getApplayBuildId()))).toString());
                String userIds = adminIdString.substring(1, adminIdString.length() - 1).replace("$$", ",");
                String moduleType = "oa_office_use";
                String send_UserName = userName;
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                RemindUtil.sendMessageToUsers2(title, url, userIds, moduleType, new Date(), dateTimeFormat.parse("2050-01-01 00:00:00"), send_UserName, Long.valueOf(0L), 1);
              } 
            } else {
              request.setAttribute("message", "2");
            } 
            List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
            request.setAttribute("buildList", list);
            List titleList = officeRoomBD.getTitleList("", "");
            request.setAttribute("titleList", titleList);
            return actionMapping.findForward("officeUseAdd");
          } 
          if (action.equals("officeUseModify") || action.equals("officeUseView")) {
            Long id = new Long(request.getParameter("id"));
            OfficeUsePO po = officeRoomBD.officeUseLoad(id);
            officeRoomActionForm.setApplayId(id);
            officeRoomActionForm.setApplayBuildId(po.getApplayBuildId());
            officeRoomActionForm.setApplayBuildNumber(po.getApplayBuildNumber());
            officeRoomActionForm.setApplayNumber(po.getApplayNumber());
            officeRoomActionForm.setApplayClass(po.getApplayClass());
            officeRoomActionForm.setApplayOrg(po.getApplayOrg());
            officeRoomActionForm.setApplayReason(po.getApplayReason());
            officeRoomActionForm.setApplaySex(po.getApplaySex());
            officeRoomActionForm.setApplayTitle(po.getApplayTitle());
            officeRoomActionForm.setApplayUserId(po.getApplayUserId());
            officeRoomActionForm.setApplayUsername(po.getApplayUsername());
            officeRoomActionForm.setVisitName(po.getVisitName());
            officeRoomActionForm.setVisitOrg(po.getVisitOrg());
            officeRoomActionForm.setVisitWorkunit(po.getVisitWorkunit());
            officeRoomActionForm.setBz(po.getBz());
            officeRoomActionForm.setApplayStation(po.getApplayStation());
            request.setAttribute("applayStation_old", po.getApplayStation());
            officeRoomActionForm.setApplayKth(po.getApplayKth());
            officeRoomActionForm.setApplayOrgName(po.getApplayOrgName());
            officeRoomActionForm.setVisitOrgName(po.getVisitOrgName());
            List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
            List titleList = officeRoomBD.getTitleList("", "");
            request.setAttribute("titleList", titleList);
            request.setAttribute("title", po.getApplayTitle());
            List<Object[]> applayList = officeRoomBD.getListBuild(po.getApplayBuildId().toString());
            List applayNum = null;
            applayNum = officeRoomBD.getListBuildNumber(po.getApplayBuildId().toString(), po.getApplayBuildNumber(), "");
            if (applayList != null && applayList.size() > 0)
              for (int ms = 0; ms < applayList.size(); ms++) {
                Object[] objc = applayList.get(ms);
                request.setAttribute("applayCount", (objc[2] == null) ? "0" : objc[2].toString());
              }  
            request.setAttribute("visitBeginTime", po.getVisitBeginTime());
            request.setAttribute("visitEndTime", po.getVisitEndTime());
            request.setAttribute("buildList", list);
            request.setAttribute("applayList", applayList);
            request.setAttribute("applayNum", applayNum);
            request.setAttribute("applayNumber", po.getApplayNumber());
            request.setAttribute("applayBuildNumber", po.getApplayBuildNumber());
            request.setAttribute("build", (new StringBuilder(String.valueOf(po.getApplayBuildId()))).toString());
            String canEdit = "1";
            if (action.equals("officeUseView"))
              canEdit = "0"; 
            request.setAttribute("canEdit", canEdit);
            return actionMapping.findForward("officeUseModify");
          } 
          if ("officeUseUpdate".equals(action)) {
            OfficeUsePO po = new OfficeUsePO();
            po.setApplayBuildId(officeRoomActionForm.getApplayBuildId());
            po.setApplayBuildNumber(officeRoomActionForm.getApplayBuildNumber());
            po.setApplayNumber(officeRoomActionForm.getApplayNumber());
            po.setApplayClass(officeRoomActionForm.getApplayClass());
            officeRoomActionForm.setApplayClass(officeRoomActionForm.getApplayClass());
            po.setApplayOrg(officeRoomActionForm.getApplayOrg());
            po.setApplayReason(officeRoomActionForm.getApplayReason());
            po.setApplaySex(officeRoomActionForm.getApplaySex());
            po.setApplayTitle(officeRoomActionForm.getApplayTitle());
            po.setApplayUserId(officeRoomActionForm.getApplayUserId());
            po.setApplayUsername(officeRoomActionForm.getApplayUsername());
            Date visitBeginTime = new Date(request.getParameter("visitBeginTime"));
            Date visitEndTime = new Date(request.getParameter("visitEndTime"));
            po.setVisitBeginTime(visitBeginTime);
            po.setVisitEndTime(visitEndTime);
            po.setVisitName(officeRoomActionForm.getVisitName());
            po.setVisitOrg(officeRoomActionForm.getVisitOrg());
            po.setVisitWorkunit(officeRoomActionForm.getVisitWorkunit());
            po.setBz(officeRoomActionForm.getBz());
            po.setApplayOrgName(officeRoomActionForm.getApplayOrgName());
            po.setVisitOrgName(officeRoomActionForm.getVisitOrgName());
            po.setApplayStation(officeRoomActionForm.getApplayStation());
            po.setApplayKth(officeRoomActionForm.getApplayKth());
            List list = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
            List<Object[]> applayList = officeRoomBD.getListBuild(officeRoomActionForm.getApplayBuildId().toString());
            List applayNum = null;
            applayNum = officeRoomBD.getListBuildNumber(officeRoomActionForm.getApplayBuildId().toString(), officeRoomActionForm.getApplayBuildNumber(), "");
            if (applayList != null && applayList.size() > 0)
              for (int ms = 0; ms < applayList.size(); ms++) {
                Object[] objc = applayList.get(ms);
                request.setAttribute("applayCount", (objc[2] == null) ? "0" : objc[2].toString());
              }  
            List titleList = officeRoomBD.getTitleList("", "");
            request.setAttribute("titleList", titleList);
            request.setAttribute("title", po.getApplayTitle());
            request.setAttribute("buildList", list);
            request.setAttribute("applayList", applayList);
            request.setAttribute("applayNum", applayNum);
            request.setAttribute("applayNumber", officeRoomActionForm.getApplayNumber());
            request.setAttribute("applayBuildNumber", officeRoomActionForm.getApplayBuildNumber());
            String applayStation_old = request.getParameter("applayStation_old");
            request.setAttribute("applayStation_old", applayStation_old);
            request.setAttribute("build", (new StringBuilder(String.valueOf(officeRoomActionForm.getApplayBuildId()))).toString());
            String canEdit = "1";
            if (action.equals("officeUseView"))
              canEdit = "0"; 
            request.setAttribute("canEdit", canEdit);
            int applayStation = 0;
            if (officeRoomActionForm.getApplayStation() != null)
              applayStation = Integer.parseInt((String)officeRoomActionForm.getApplayStation()); 
            String OfficeTotalStation = officeRoomBD.getOfficeTotalStation((new StringBuilder(String.valueOf(officeRoomActionForm.getApplayBuildId()))).toString(), (
                new StringBuilder(String.valueOf(officeRoomActionForm.getApplayBuildNumber()))).toString(), (new StringBuilder(String.valueOf(officeRoomActionForm.getApplayNumber()))).toString(), (new StringBuilder(String.valueOf(officeRoomActionForm.getApplayUserId()))).toString());
            String[] use = OfficeTotalStation.split("&");
            int stationUse = 0;
            stationUse = Integer.parseInt(use[0].toString()) - Integer.parseInt(use[1].toString());
            int alreadyUse = Integer.parseInt(use[1].toString());
            int maxUse = Integer.parseInt(use[0].toString());
            int shengyu = maxUse - alreadyUse + Integer.parseInt(applayStation_old);
            if (maxUse > 0) {
              if (applayStation > shengyu && !applayStation_old.equals(po.getApplayStation())) {
                request.setAttribute("maxUse", Integer.valueOf(shengyu));
                request.setAttribute("message", "-1");
              } else if (applayStation > shengyu && Integer.parseInt(applayStation_old) > Integer.parseInt((String)po.getApplayStation())) {
                officeRoomBD.officeUseUpdate(po, officeRoomActionForm.getApplayId());
              } else {
                officeRoomBD.officeUseUpdate(po, officeRoomActionForm.getApplayId());
              } 
            } else {
              request.setAttribute("message", "2");
            } 
            return actionMapping.findForward("officeUseModify");
          } 
          if ("officeUseDelete".equals(action)) {
            if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
              Long id = new Long(request.getParameter("id"));
              officeRoomBD.officeUseDelete(id);
            } 
            action = "listOfficeUse";
          } else if ("outFlagSH".equals(action)) {
            String ids = request.getParameter("ssMasterId");
            String flag = request.getParameter("flag");
            String applayBuildId = request.getParameter("applayBuildId");
            String applayBuildNumber = request.getParameter("applayBuildNumber");
            String applayNumber = request.getParameter("applayNumber");
            OfficeUsePO officeUsePO = new OfficeUsePO();
            officeUsePO.setApplayBuildId(applayBuildId);
            officeUsePO.setApplayBuildNumber(applayBuildNumber);
            officeUsePO.setApplayNumber(applayNumber);
            officeRoomBD.officeUpdateIsUse(ids, flag, officeUsePO);
            action = "listOfficeUse";
          } else if ("officeUseSearch".equals(action)) {
            officeUseList(request);
          } else {
            if ("officeRoomShow".equals(action)) {
              officeUseShowList(request);
              return actionMapping.findForward("officeRoomShow");
            } 
            if ("listOfficeShow".equals(action)) {
              officeUseShowList(request);
              return actionMapping.findForward("OfficeShow");
            } 
            if ("officeBasicShow".equals(action)) {
              Long id = new Long(request.getParameter("buildId"));
              OfficeBuildPO po = officeRoomBD.load(id);
              request.setAttribute("buildId", id.toString());
              request.setAttribute("Buildname", po.getBuildname());
              request.setAttribute("Buildidaccount", po.getBuildidaccount());
              request.setAttribute("Buildreadname", po.getBuildreadname());
              request.setAttribute("BuildAdminName", po.getBuildAdminName());
              String userIds = po.getBuildreader();
              if (userIds == null || "null".equals(userIds))
                userIds = ""; 
              String orgIds = po.getBuildreadorg();
              if (orgIds == null || "null".equals(orgIds))
                orgIds = ""; 
              String groupIds = po.getBuildreadgroup();
              if (groupIds == null || "null".equals(groupIds))
                groupIds = ""; 
              request.setAttribute("buildReader", String.valueOf(userIds) + orgIds + groupIds);
              return actionMapping.findForward("officeBasicShow");
            } 
            if ("listOfficeBasic".equals(action)) {
              officeList(request);
              return actionMapping.findForward("listOfficeBasic");
            } 
            if ("listOfficeBasicSearch".equals(action)) {
              officeList(request);
              return actionMapping.findForward("listOfficeBasicSearch");
            } 
          } 
        } 
      } 
    } 
    if ("listOffice".equals(action))
      officeList(request); 
    if (action.equals("list"))
      buildList(request); 
    if ("listOfficeUse".equals(action))
      officeUseList(request); 
    return actionMapping.findForward(action);
  }
  
  private void buildList(HttpServletRequest httpServletRequest) {
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String buildName = httpServletRequest.getParameter("buildName");
    String whereString = " ";
    if (!"".equals(buildName) && buildName != null) {
      whereString = String.valueOf(whereString) + "where 1=1 and po.buidId<>1 and po.buildname like '%" + buildName + "%' ";
    } else {
      whereString = String.valueOf(whereString) + " where 1=1 and po.buidId<>1";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.buidId,po.buildname,po.buildidaccount,po.createdempname", 
        "com.js.oa.routine.officeroom.po.OfficeBuildPO po", 
        String.valueOf(whereString) + "  order by po.buidId ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("buildList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void officeList(HttpServletRequest httpServletRequest) {
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String officeName = httpServletRequest.getParameter("officeName");
    String buidId = httpServletRequest.getParameter("buidId");
    String buidnumber = httpServletRequest.getParameter("buidnumber");
    String useStatusId = httpServletRequest.getParameter("useStatusId");
    String flag = httpServletRequest.getParameter("flag");
    String whereString = "where 1=1  ";
    if (!"".equals(officeName) && officeName != null) {
      whereString = String.valueOf(whereString) + " and po.officename like '%" + officeName + "%' ";
    } else {
      whereString = (new StringBuilder(String.valueOf(whereString))).toString();
    } 
    if (!"".equals(buidId) && buidId != null)
      whereString = String.valueOf(whereString) + " and po.buidId = " + buidId + " "; 
    if (!"".equals(buidnumber) && buidnumber != null)
      whereString = String.valueOf(whereString) + " and po.buidnumber = '" + buidnumber + "' "; 
    if (!"".equals(useStatusId) && useStatusId != null && !"-1".equals(useStatusId))
      if ("0".equals(useStatusId)) {
        whereString = String.valueOf(whereString) + " and (po.officeIsUse = '" + useStatusId.trim() + "' or po.officeIsUse  is null ) ";
      } else {
        whereString = String.valueOf(whereString) + " and po.officeIsUse = '" + useStatusId.trim() + "' ";
      }  
    String stationB = httpServletRequest.getParameter("stationB");
    String useStationLeft = httpServletRequest.getParameter("useStationLeft");
    String stationT = httpServletRequest.getParameter("stationT");
    String useStationRight = httpServletRequest.getParameter("useStationRight");
    String areaB = httpServletRequest.getParameter("areaB");
    String areaLeft = httpServletRequest.getParameter("areaLeft");
    String areaT = httpServletRequest.getParameter("areaT");
    String areaRight = httpServletRequest.getParameter("areaRight");
    String databaseType = 
      SystemCommon.getDatabaseType();
    if ("1".equals(flag)) {
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      String orgIdString = httpSession.getAttribute("orgIdString").toString();
      ManagerService managerBD = new ManagerService();
      String buildClassWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
          "po.officereader", 
          "po.officereadorg", 
          "po.officereadgroup");
      if (buildClassWhere != null && !"".equals(buildClassWhere))
        if (databaseType.indexOf("mysql") >= 0) {
          buildClassWhere = "and (" + buildClassWhere + " or ((po.officereader ='' or po.officereader is null) and po.officereadorg is null and po.officereadgroup is null ))";
        } else {
          buildClassWhere = "and (" + buildClassWhere + " or (po.officereader is null and po.officereadorg is null and po.officereadgroup is null ))";
        }  
      whereString = String.valueOf(whereString) + buildClassWhere;
      if (stationB != null && !"".equals(stationB) && useStationLeft != null && !"".equals(useStationLeft)) {
        String sqlStationLeft = "";
        if ("0".equals(stationB)) {
          sqlStationLeft = " and po.officestation>" + useStationLeft;
        } else if ("1".equals(stationB)) {
          sqlStationLeft = " and po.officestation<" + useStationLeft;
        } else if ("2".equals(stationB)) {
          sqlStationLeft = " and po.officestation=" + useStationLeft;
        } 
        whereString = String.valueOf(whereString) + sqlStationLeft;
      } 
      if (stationT != null && !"".equals(stationT) && useStationRight != null && !"".equals(useStationRight)) {
        String sqlStationRight = "";
        if ("0".equals(stationT)) {
          sqlStationRight = " and po.officestation>" + useStationRight;
        } else if ("1".equals(stationT)) {
          sqlStationRight = " and po.officestation<" + useStationRight;
        } else if ("2".equals(stationT)) {
          sqlStationRight = " and po.officestation=" + useStationRight;
        } 
        whereString = String.valueOf(whereString) + sqlStationRight;
      } 
      if (areaB != null && !"".equals(areaB) && areaLeft != null && !"".equals(areaLeft)) {
        String sqlareaLeft = "";
        if ("0".equals(areaB)) {
          sqlareaLeft = " and po.officearea>" + areaLeft;
        } else if ("1".equals(areaB)) {
          sqlareaLeft = " and po.officearea<" + areaLeft;
        } else if ("2".equals(areaB)) {
          sqlareaLeft = " and po.officearea=" + areaLeft;
        } 
        whereString = String.valueOf(whereString) + sqlareaLeft;
      } 
      if (areaT != null && !"".equals(areaT) && areaRight != null && !"".equals(areaRight)) {
        String sqlareaRight = "";
        if ("0".equals(areaT)) {
          sqlareaRight = " and po.officearea>" + areaRight;
        } else if ("1".equals(areaT)) {
          sqlareaRight = " and po.officearea<" + areaRight;
        } else if ("2".equals(areaT)) {
          sqlareaRight = " and po.officearea=" + areaRight;
        } 
        whereString = String.valueOf(whereString) + sqlareaRight;
      } 
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.officeId,po.officename,po.officenumber,po.officearea,po.officefaces,po.buildname,po.buidnumber,po.createdempname,po.buidId,po.officestation,po.officeIsUse", 
        "com.js.oa.routine.officeroom.po.OfficePO po", 
        String.valueOf(whereString) + "  order by po.officeId ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    OfficeRoomBD officeRoomBD = new OfficeRoomBD();
    List officeList = officeRoomBD.list("", Long.valueOf(Long.parseLong("0")));
    httpServletRequest.setAttribute("buildList", officeList);
    httpServletRequest.setAttribute("buidId", buidId);
    httpServletRequest.setAttribute("officeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,buidnumber,buidId,officeName");
  }
  
  private void officeUseList(HttpServletRequest httpServletRequest) {
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String userId = httpSession.getAttribute("userId").toString();
    String officeName = httpServletRequest.getParameter("officeName");
    String whereString = " ";
    if (!"".equals(officeName) && officeName != null) {
      whereString = String.valueOf(whereString) + "where 1=1 and po.applayReason like '%" + officeName + "%' ";
    } else {
      whereString = String.valueOf(whereString) + " where 1=1 ";
    } 
    String applayUserId = httpServletRequest.getParameter("applayUserId");
    if (!"".equals(applayUserId) && applayUserId != null)
      whereString = String.valueOf(whereString) + " and po.applayUserId ='" + applayUserId + "'"; 
    String officeNumber = httpServletRequest.getParameter("officeNumber");
    if (!"".equals(officeNumber) && officeNumber != null)
      whereString = String.valueOf(whereString) + " and po.applayNumber like '%" + officeNumber + "%'"; 
    OfficeRoomBD officeRoomBD = new OfficeRoomBD();
    String buildId = httpServletRequest.getParameter("buildId");
    String buildNumber = httpServletRequest.getParameter("buildNumber");
    String applayNumber = httpServletRequest.getParameter("applayNumber");
    String isSh = httpServletRequest.getParameter("isSh");
    String flagMark = httpServletRequest.getParameter("flagMark");
    if (flagMark != null && !"".equals(flagMark))
      flagMark = "0"; 
    String adminIdString = officeRoomBD.getBuildAdminId(buildId);
    whereString = String.valueOf(whereString) + " and po.applayBuildId=" + buildId;
    if (buildNumber != null && !"".equals(buildNumber))
      whereString = String.valueOf(whereString) + " and po.applayBuildNumber='" + buildNumber + "'"; 
    if (applayNumber != null && !"".equals(applayNumber))
      whereString = String.valueOf(whereString) + " and po.applayNumber='" + applayNumber.trim() + "'"; 
    if (isSh != null && !"".equals(isSh))
      if ("1".equals(isSh)) {
        whereString = String.valueOf(whereString) + " and po.applayIsUse='1'  ";
      } else {
        whereString = String.valueOf(whereString) + " and (po.applayIsUse='0' or po.applayIsUse is null)  ";
      }  
    String applayTitle = httpServletRequest.getParameter("applayTitle");
    String visitName = httpServletRequest.getParameter("visitName");
    String visitOrg = httpServletRequest.getParameter("visitOrg");
    if (applayTitle != null && !"".equals(applayTitle))
      whereString = String.valueOf(whereString) + " and po.applayTitle='" + applayTitle + "'"; 
    if (visitName != null && !"".equals(visitName))
      whereString = String.valueOf(whereString) + " and po.visitName like '%" + visitName + "%'"; 
    if (visitOrg != null && !"".equals(visitOrg))
      whereString = String.valueOf(whereString) + " and po.visitOrg='" + visitOrg + "'"; 
    ManagerService managerBD = new ManagerService();
    if ((adminIdString != null && adminIdString.contains("$" + userId + "$")) || managerBD.hasRight(userId, "07*03*05")) {
      httpServletRequest.setAttribute("isAdmin", "1");
    } else {
      httpServletRequest.setAttribute("isAdmin", "0");
      String userIdString = userId;
      whereString = String.valueOf(whereString) + " and po.applayUserId ='" + userIdString + "'";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.applayId,po.applayBuildId,po.applayBuildNumber,po.applayNumber,po.applayClass,po.applayUsername,po.applayReason,po.applayStation,po.applayIsUse", 
        "com.js.oa.routine.officeroom.po.OfficeUsePO po", 
        String.valueOf(whereString) + "  order by po.applayId ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    List titleList = officeRoomBD.getTitleList("", "");
    httpServletRequest.setAttribute("titleList", titleList);
    httpServletRequest.setAttribute("flagMark", flagMark);
    httpServletRequest.setAttribute("buildNumber", buildNumber);
    httpServletRequest.setAttribute("applayNumber", applayNumber);
    httpServletRequest.setAttribute("isSh", isSh);
    httpServletRequest.setAttribute("officeUseList", list);
    httpServletRequest.setAttribute("buildId", buildId);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,buildId,buildNumber,applayNumber,applayTitle,visitName,visitOrg");
  }
  
  private void officeUseShowList(HttpServletRequest httpServletRequest) throws Exception {
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String officeName = httpServletRequest.getParameter("officeName");
    String whereString = " ";
    if (!"".equals(officeName) && officeName != null) {
      whereString = String.valueOf(whereString) + "where 1=1 and po.officename like '%" + officeName + "%' ";
    } else {
      whereString = String.valueOf(whereString) + "where 1=1 ";
    } 
    String buildId = httpServletRequest.getParameter("buildId");
    String buildNumber = httpServletRequest.getParameter("buildNumber");
    if (!"".equals(buildId) && buildId != null && !"".equals(buildNumber) && buildNumber != null)
      whereString = String.valueOf(whereString) + " and po.buidId=" + buildId + " and po.buidnumber='" + buildNumber + "' "; 
    int pageSize = 150;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.officeId,po.officename,po.officenumber,po.officearea,po.officefaces,po.buildname,po.buidId,po.buidnumber,po.createdempname,po.officeIsUse", 
        "com.js.oa.routine.officeroom.po.OfficePO po", 
        String.valueOf(whereString) + "  order by po.officenumber ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    OfficeRoomEJBBean OfficeRoom = new OfficeRoomEJBBean();
    List buildList = OfficeRoom.getBuildList(httpServletRequest);
    httpServletRequest.setAttribute("buildList", buildList);
    httpServletRequest.setAttribute("officeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("buildId", buildId);
    httpServletRequest.setAttribute("buildNumber", buildNumber);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,buildId,buildNumber,officeName");
  }
}
