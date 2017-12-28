/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.form;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Mana Jena
 */
public class ProjectInitiationListForm {

    private String proId = null;
    private String userName = null;
    private String projectDesc = null;
    private String projectCat = null;
    private String district = null;
    private String state = null;
    private String city = null;
    private String zone = null;
    private String contractorName = null;
    private ArrayList proList = null;
    private String contractValue = null;
    private String startDate = null;

    private String period = null;
    private String status = null;
    private String contractNo = null;

    private String completionDateOrg = null;
    private String completionDateRev = null;
    private String lastReportedOn = null;
    private String mMONITORING_AGENCY = null;
    private String projectType = null;
    private String projectCode = null;
    ArrayList getListData = null;
    ArrayList getCategory = null;
    private String empId = null;

    private String agreementDate = null;
    private String saap1 = null;
    private String saap2 = null;
    private String saap3 = null;
    private String onmcost = null;

    private String endDate = null;
    private String mSHPSC_APPROVAL_DATE = null;
    private String mSHPSC_APPROVED_AMOUNT = null;
    private String mTENDER_INVITATION_DATE = null;
    private String mTENDER_NO = null;
    private String mCONTRACT_AWARD_DATE = null;
    private String mPERFORMANCE_SECURITY_TYPE = null;
    private String mPERFORMANCE_SECURITY_NO = null;
    private String mPERFORMANCE_SECURITY_AMOUNT = null;
    private String mPERFORMANCE_SECURITY_VALIDITY_DATE = null;

    private String contractorId = null;

    /**
     * WORKFLOW FIELDS*
     */
    private String hidselpid = null;
    private String hidselpaid = null;
    private String hidaid = null;
    private String sltDecision = null;
    private String txtDecisionDate = null;
    private String[] txtObservation = null;
    private String[] txtCompliance = null;
    private String[] hidOpenObsId = null;
    private String[] chkCompliedObs = null;
    private String txtremark = null;
    private DynaForm dynaForm = null;
    private String hidselppid = null;
    private String hidselplanno = null;
    private String packagedesc = null;
    private String[] chkComponent = null;
    private String[] hidpsid = null;
    private String[] txtProScope = null;
    private String[] txtWeightage = null;
    private String[] txtCostComponent = null;
    private ArrayList componentList = null;
    private String[] txtRevScope = null;
    private String[] compoName = null;
    private String[] txtCompUnit = null;
    private String[] hidScId = null;
    private String[] hidCcId = null;
    private String txtUnit = null;
    private String txtWeightageWork = null;
    private String txtRevWorkScope = null;
    //private String txtTarget=null;
    private String paymentDate = null;
    private String arrBillNo = null;
    private String amount = null;
    private ArrayList paymentList = null;
    private String hidPmtId = null;
    private ArrayList mprList = null;
    private String reportDate = null;
    private String progressStatus = null;
    private ArrayList mprReportList = null;
    private String param = null;
    private ArrayList monthlyTarget = null;
    private ArrayList workplanTargetList = null;
    private String[] targetId = null;
    private String ccId = null;
    private String scId = null;
    private String[] txtTarget = null;
    private String[] month = null;
    private String[] year = null;
    private String copy = null;
    private String copypath = null;
    private List<MultipartFile> listFile = new ArrayList();

    public List<MultipartFile> getListFile() {
        return listFile;
    }

    public void setListFile(List<MultipartFile> listFile) {
        this.listFile = listFile;
    }

    public String getTxtremark() {
        return txtremark;
    }

    public void setTxtremark(String txtremark) {
        this.txtremark = txtremark;
    }

    public String[] getChkCompliedObs() {
        return chkCompliedObs;
    }

    public void setChkCompliedObs(String[] chkCompliedObs) {
        this.chkCompliedObs = chkCompliedObs;
    }

    public String[] getHidOpenObsId() {
        return hidOpenObsId;
    }

    public void setHidOpenObsId(String[] hidOpenObsId) {
        this.hidOpenObsId = hidOpenObsId;
    }

    public String[] getTxtCompliance() {
        return txtCompliance;
    }

    public void setTxtCompliance(String[] txtCompliance) {
        this.txtCompliance = txtCompliance;
    }

    public String[] getTxtObservation() {
        return txtObservation;
    }

    public void setTxtObservation(String[] txtObservation) {
        this.txtObservation = txtObservation;
    }

    public String getHidselpaid() {
        return hidselpaid;
    }

    public void setHidselpaid(String hidselpaid) {
        this.hidselpaid = hidselpaid;
    }

    public String getHidselpid() {
        return hidselpid;
    }

    public void setHidselpid(String hidselpid) {
        this.hidselpid = hidselpid;
    }

    public String getHidaid() {
        return hidaid;
    }

    public void setHidaid(String hidaid) {
        this.hidaid = hidaid;
    }

    public String getSltDecision() {
        return sltDecision;
    }

    public void setSltDecision(String sltDecision) {
        this.sltDecision = sltDecision;
    }

    public String getTxtDecisionDate() {
        return txtDecisionDate;
    }

    public void setTxtDecisionDate(String txtDecisionDate) {
        this.txtDecisionDate = txtDecisionDate;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getmSHPSC_APPROVAL_DATE() {
        return mSHPSC_APPROVAL_DATE;
    }

    public void setmSHPSC_APPROVAL_DATE(String mSHPSC_APPROVAL_DATE) {
        this.mSHPSC_APPROVAL_DATE = mSHPSC_APPROVAL_DATE;
    }

    public String getmSHPSC_APPROVED_AMOUNT() {
        return mSHPSC_APPROVED_AMOUNT;
    }

    public void setmSHPSC_APPROVED_AMOUNT(String mSHPSC_APPROVED_AMOUNT) {
        this.mSHPSC_APPROVED_AMOUNT = mSHPSC_APPROVED_AMOUNT;
    }

    public String getmTENDER_INVITATION_DATE() {
        return mTENDER_INVITATION_DATE;
    }

    public void setmTENDER_INVITATION_DATE(String mTENDER_INVITATION_DATE) {
        this.mTENDER_INVITATION_DATE = mTENDER_INVITATION_DATE;
    }

    public String getmTENDER_NO() {
        return mTENDER_NO;
    }

    public void setmTENDER_NO(String mTENDER_NO) {
        this.mTENDER_NO = mTENDER_NO;
    }

    public String getmCONTRACT_AWARD_DATE() {
        return mCONTRACT_AWARD_DATE;
    }

    public void setmCONTRACT_AWARD_DATE(String mCONTRACT_AWARD_DATE) {
        this.mCONTRACT_AWARD_DATE = mCONTRACT_AWARD_DATE;
    }

    public String getmPERFORMANCE_SECURITY_TYPE() {
        return mPERFORMANCE_SECURITY_TYPE;
    }

    public void setmPERFORMANCE_SECURITY_TYPE(String mPERFORMANCE_SECURITY_TYPE) {
        this.mPERFORMANCE_SECURITY_TYPE = mPERFORMANCE_SECURITY_TYPE;
    }

    public String getmPERFORMANCE_SECURITY_NO() {
        return mPERFORMANCE_SECURITY_NO;
    }

    public void setmPERFORMANCE_SECURITY_NO(String mPERFORMANCE_SECURITY_NO) {
        this.mPERFORMANCE_SECURITY_NO = mPERFORMANCE_SECURITY_NO;
    }

    public String getmPERFORMANCE_SECURITY_AMOUNT() {
        return mPERFORMANCE_SECURITY_AMOUNT;
    }

    public void setmPERFORMANCE_SECURITY_AMOUNT(String mPERFORMANCE_SECURITY_AMOUNT) {
        this.mPERFORMANCE_SECURITY_AMOUNT = mPERFORMANCE_SECURITY_AMOUNT;
    }

    public String getmPERFORMANCE_SECURITY_VALIDITY_DATE() {
        return mPERFORMANCE_SECURITY_VALIDITY_DATE;
    }

    public void setmPERFORMANCE_SECURITY_VALIDITY_DATE(String mPERFORMANCE_SECURITY_VALIDITY_DATE) {
        this.mPERFORMANCE_SECURITY_VALIDITY_DATE = mPERFORMANCE_SECURITY_VALIDITY_DATE;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(String agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getSaap1() {
        return saap1;
    }

    public void setSaap1(String saap1) {
        this.saap1 = saap1;
    }

    public String getSaap2() {
        return saap2;
    }

    public void setSaap2(String saap2) {
        this.saap2 = saap2;
    }

    public String getSaap3() {
        return saap3;
    }

    public void setSaap3(String saap3) {
        this.saap3 = saap3;
    }

    public String getOnmcost() {
        return onmcost;
    }

    public void setOnmcost(String onmcost) {
        this.onmcost = onmcost;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public ArrayList getGetListData() {
        return getListData;
    }

    public void setGetListData(ArrayList getListData) {
        this.getListData = getListData;
    }

    public ArrayList getGetCategory() {
        return getCategory;
    }

    public void setGetCategory(ArrayList getCategory) {
        this.getCategory = getCategory;
    }

    public String getmMONITORING_AGENCY() {
        return mMONITORING_AGENCY;
    }

    public void setmMONITORING_AGENCY(String mMONITORING_AGENCY) {
        this.mMONITORING_AGENCY = mMONITORING_AGENCY;
    }

    public String getCopypath() {
        return copypath;
    }

    public void setCopypath(String copypath) {
        this.copypath = copypath;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getLastReportedOn() {
        return lastReportedOn;
    }

    public void setLastReportedOn(String lastReportedOn) {
        this.lastReportedOn = lastReportedOn;
    }

    public String getCompletionDateOrg() {
        return completionDateOrg;
    }

    public void setCompletionDateOrg(String completionDateOrg) {
        this.completionDateOrg = completionDateOrg;
    }

    public String getCompletionDateRev() {
        return completionDateRev;
    }

    public void setCompletionDateRev(String completionDateRev) {
        this.completionDateRev = completionDateRev;
    }

    public String[] getTxtCostComponent() {
        return txtCostComponent;
    }

    public void setTxtCostComponent(String[] txtCostComponent) {
        this.txtCostComponent = txtCostComponent;
    }

    public String[] getMonth() {
        return month;
    }

    public void setMonth(String[] month) {
        this.month = month;
    }

    public String[] getYear() {
        return year;
    }

    public void setYear(String[] year) {
        this.year = year;
    }

    public String[] getTxtTarget() {
        return txtTarget;
    }

    public void setTxtTarget(String[] txtTarget) {
        this.txtTarget = txtTarget;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public ArrayList getMonthlyTarget() {
        return monthlyTarget;
    }

    public void setMonthlyTarget(ArrayList monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
    }

    public ArrayList getWorkplanTargetList() {
        return workplanTargetList;
    }

    public void setWorkplanTargetList(ArrayList workplanTargetList) {
        this.workplanTargetList = workplanTargetList;
    }

    public String[] getTargetId() {
        return targetId;
    }

    public void setTargetId(String[] targetId) {
        this.targetId = targetId;
    }

    public String[] getHidpsid() {
        return hidpsid;
    }

    public void setHidpsid(String[] hidpsid) {
        this.hidpsid = hidpsid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public ArrayList getMprList() {
        return mprList;
    }

    public void setMprList(ArrayList mprList) {
        this.mprList = mprList;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public ArrayList getProList() {
        return proList;
    }

    public void setProList(ArrayList proList) {
        this.proList = proList;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectCat() {
        return projectCat;
    }

    public void setProjectCat(String projectCat) {
        this.projectCat = projectCat;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getTxtUnit() {
        return txtUnit;
    }

    public void setTxtUnit(String txtUnit) {
        this.txtUnit = txtUnit;
    }

    public String getTxtWeightageWork() {
        return txtWeightageWork;
    }

    public void setTxtWeightageWork(String txtWeightageWork) {
        this.txtWeightageWork = txtWeightageWork;
    }

    public String getTxtRevWorkScope() {
        return txtRevWorkScope;
    }

    public void setTxtRevWorkScope(String txtRevWorkScope) {
        this.txtRevWorkScope = txtRevWorkScope;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public ArrayList getComponentList() {
        return componentList;
    }

    public void setComponentList(ArrayList componentList) {
        this.componentList = componentList;
    }

    public String getContractValue() {
        return contractValue;
    }

    public void setContractValue(String contractValue) {
        this.contractValue = contractValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getCompoName() {
        return compoName;
    }

    public void setCompoName(String[] compoName) {
        this.compoName = compoName;
    }

    public String[] getChkComponent() {
        return chkComponent;
    }

    public void setChkComponent(String[] chkComponent) {
        this.chkComponent = chkComponent;
    }

    public String[] getTxtProScope() {
        return txtProScope;
    }

    public void setTxtProScope(String[] txtProScope) {
        this.txtProScope = txtProScope;
    }

    public String[] getTxtWeightage() {
        return txtWeightage;
    }

    public void setTxtWeightage(String[] txtWeightage) {
        this.txtWeightage = txtWeightage;
    }

    public String[] getTxtRevScope() {
        return txtRevScope;
    }

    public void setTxtRevScope(String[] txtRevScope) {
        this.txtRevScope = txtRevScope;
    }

    public String[] getTxtCompUnit() {
        return txtCompUnit;
    }

    public void setTxtCompUnit(String[] txtCompUnit) {
        this.txtCompUnit = txtCompUnit;
    }

    public String[] getHidScId() {
        return hidScId;
    }

    public void setHidScId(String[] hidScId) {
        this.hidScId = hidScId;
    }

    public String[] getHidCcId() {
        return hidCcId;
    }

    public void setHidCcId(String[] hidCcId) {
        this.hidCcId = hidCcId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getArrBillNo() {
        return arrBillNo;
    }

    public void setArrBillNo(String arrBillNo) {
        this.arrBillNo = arrBillNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ArrayList getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList paymentList) {
        this.paymentList = paymentList;
    }

    public String getHidPmtId() {
        return hidPmtId;
    }

    public void setHidPmtId(String hidPmtId) {
        this.hidPmtId = hidPmtId;
    }

    public ArrayList getMprReportList() {
        return mprReportList;
    }

    public void setMprReportList(ArrayList mprReportList) {
        this.mprReportList = mprReportList;
    }

    public DynaForm getDynaForm() {
        return dynaForm;
    }

    public void setDynaForm(DynaForm dynaForm) {
        this.dynaForm = dynaForm;
    }

    public String getHidselppid() {
        return hidselppid;
    }

    public void setHidselppid(String hidselppid) {
        this.hidselppid = hidselppid;
    }

    public String getHidselplanno() {
        return hidselplanno;
    }

    public void setHidselplanno(String hidselplanno) {
        this.hidselplanno = hidselplanno;
    }

    public String getPackagedesc() {
        return packagedesc;
    }

    public void setPackagedesc(String packagedesc) {
        this.packagedesc = packagedesc;
    }
}
