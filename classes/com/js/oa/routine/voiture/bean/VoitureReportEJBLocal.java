package com.js.oa.routine.voiture.bean;

import java.util.List;
import javax.ejb.EJBLocalObject;

public interface VoitureReportEJBLocal extends EJBLocalObject {
  List listReportForms(String paramString1, String paramString2, String paramString3) throws Exception;
}
