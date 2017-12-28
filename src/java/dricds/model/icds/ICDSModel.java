/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.icds;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;

/**
 *
 * @author Ekank
 */
public class ICDSModel implements Serializable {

    private String projectName = null;
    private String projectNameHindi = null;
    private String projectCode = null;
    private String headOfOffice = null;
    private String districtCode = null;
    private String districtName = null;
    private int districtID = 0;
    private String officeName = null;
    private String officeNameHindi = null;
    private String officeAddress = null;
    private String longitude = null;
    private String latitude = null;
    private String buildingOwner = null;
    private String buildingOwnerName = null;
    private String buildingOwnerNameHindi = null;
    private String userlevel = null;
    private String userDistCode = null;
    private String hrmsIDHOD = null;
    private String hodName = null;
    private String hodNameHindi = null;
    private String phoneNo = null;
    private String mobileNo = null;
    private String email = null;
    private boolean inPosition = false;
    private boolean additionalChargers = false;
    private String hooChoice = null;
    //Saction post details Model
    private String sanctionOrder = null;
    private String sanctionOrderNo = null;
    private String sanctionDate = null;
    private String sanctionPostName = null;
    private String sanctionPostNameDis = null;
    private String sanctionPostNameHindi = null;
    private String sanctionPostOccupied = null;
    private String sanctionStatus = null;
    private String staffingName = null;
    private String staffingNameHindi = null;
    private int sanctionPosition = 0;
    private int sanctionPostPlanId = 0;
    private String subject = null;
    private String subjectHindi = null;
    private String joinedDate = null;
    private String joiningTime = null;
    private String relevingDate = null;
    private String relevingTime = null;
    private String sanctionPostCode = null;
    private String incumbencyId = null;
    private int trackID = 0;
    private String saveDisabled = null;
    private List dtaList = null;
    private boolean hoo = false;
    private String substattivePostId = null;
    private String level = null;
    private boolean active = true;
    private String officeLevel = null;
    private String officeCode = null;
    private String reportingOffice = null;
    private String selectedProjectId = null;
    private String selectedProjectName = null;
    private String LevelAreaCode = null;
    private String officeId = null;
    private String genericPostId = null;
    private String lastIncumJoinDt=null;
    public String errorStr;

    public String getErrorStr() {
        return errorStr;
    }

    public void setErrorStr(String errorStr) {
        this.errorStr = errorStr;
    }

    

    public String getLastIncumJoinDt() {
        return lastIncumJoinDt;
    }

    public void setLastIncumJoinDt(String lastIncumJoinDt) {
        this.lastIncumJoinDt = lastIncumJoinDt;
    }

    public String getBuildingOwnerName() {
        return buildingOwnerName;
    }

    public void setBuildingOwnerName(String buildingOwnerName) {
        this.buildingOwnerName = buildingOwnerName;
    }

    public String getBuildingOwnerNameHindi() {
        return buildingOwnerNameHindi;
    }

    public void setBuildingOwnerNameHindi(String buildingOwnerNameHindi) {
        this.buildingOwnerNameHindi = buildingOwnerNameHindi;
    }

    public String getGenericPostId() {
        return genericPostId;
    }

    public void setGenericPostId(String genericPostId) {
        this.genericPostId = genericPostId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getLevelAreaCode() {
        return LevelAreaCode;
    }

    public void setLevelAreaCode(String LevelAreaCode) {
        this.LevelAreaCode = LevelAreaCode;
    }

    public String getUserDistCode() {
        return userDistCode;
    }

    public void setUserDistCode(String userDistCode) {
        this.userDistCode = userDistCode;
    }

    public String getSelectedProjectName() {
        return selectedProjectName;
    }

    public void setSelectedProjectName(String selectedProjectName) {
        this.selectedProjectName = selectedProjectName;
    }

    public String getSelectedProjectId() {
        return selectedProjectId;
    }

    public void setSelectedProjectId(String selectedProjectId) {
        this.selectedProjectId = selectedProjectId;
    }

    public String getReportingOffice() {
        return reportingOffice;
    }

    public void setReportingOffice(String reportingOffice) {
        this.reportingOffice = reportingOffice;
    }

    public String getOfficeLevel() {
        return officeLevel;
    }

    public void setOfficeLevel(String officeLevel) {
        this.officeLevel = officeLevel;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getSanctionPostPlanId() {
        return sanctionPostPlanId;
    }

    public void setSanctionPostPlanId(int sanctionPostPlanId) {
        this.sanctionPostPlanId = sanctionPostPlanId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubstattivePostId() {
        return substattivePostId;
    }

    public void setSubstattivePostId(String substattivePostId) {
        this.substattivePostId = substattivePostId;
    }

    public boolean isHoo() {
        return hoo;
    }

    public void setHoo(boolean hoo) {
        this.hoo = hoo;
    }

    public int getTrackID() {
        return trackID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public String getSaveDisabled() {
        return saveDisabled;
    }

    public void setSaveDisabled(String saveDisabled) {
        this.saveDisabled = saveDisabled;
    }

    public String getSanctionPostNameDis() {
        return sanctionPostNameDis;
    }

    public void setSanctionPostNameDis(String sanctionPostNameDis) {
        this.sanctionPostNameDis = sanctionPostNameDis;
    }

    public List getDtaList() {
        return dtaList;
    }

    public void setDtaList(List dtaList) {
        this.dtaList = dtaList;
    }

    public String getHooChoice() {
        return hooChoice;
    }

    public void setHooChoice(String hooChoice) {
        this.hooChoice = hooChoice;
        if (hooChoice.equalsIgnoreCase("inPosition")) {
            setInPosition(true);
        } else if (hooChoice.equalsIgnoreCase("additionalChargers")) {
            setAdditionalChargers(true);
        }
    }

    public String getIncumbencyId() {
        return incumbencyId;
    }

    public void setIncumbencyId(String incumbencyId) {
        this.incumbencyId = incumbencyId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSanctionPostCode() {
        return sanctionPostCode;
    }

    public void setSanctionPostCode(String sanctionPostCode) {
        this.sanctionPostCode = sanctionPostCode;
    }

    public int getSanctionPosition() {
        return sanctionPosition;
    }

    public void setSanctionPosition(int sanctionPosition) {
        this.sanctionPosition = sanctionPosition;
    }

    public String getStaffingNameHindi() {
        return staffingNameHindi;
    }

    public void setStaffingNameHindi(String staffingNameHindi) {
        this.staffingNameHindi = staffingNameHindi;
    }

    public String getSanctionPostName() {
        return sanctionPostName;
    }

    public void setSanctionPostName(String sanctionPostName) {
        this.sanctionPostName = sanctionPostName;
    }

    public String getSanctionPostNameHindi() {
        return sanctionPostNameHindi;
    }

    public void setSanctionPostNameHindi(String sanctionPostNameHindi) {
        this.sanctionPostNameHindi = sanctionPostNameHindi;
    }

    public String getSanctionPostOccupied() {
        return sanctionPostOccupied;
    }

    public void setSanctionPostOccupied(String sanctionPostOccupied) {
        this.sanctionPostOccupied = sanctionPostOccupied;
    }

    public String getSanctionOrder() {
        return sanctionOrder;
    }

    public void setSanctionOrder(String sanctionOrder) {
        this.sanctionOrder = sanctionOrder;
    }

    public String getSanctionOrderNo() {
        return sanctionOrderNo;
    }

    public void setSanctionOrderNo(String sanctionOrderNo) {
        this.sanctionOrderNo = sanctionOrderNo;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getSanctionStatus() {
        return sanctionStatus;
    }

    public void setSanctionStatus(String sanctionStatus) {
        this.sanctionStatus = sanctionStatus;
    }

    public String getStaffingName() {
        return staffingName;
    }

    public void setStaffingName(String staffingName) {
        this.staffingName = staffingName;
    }

    public String getSubjectHindi() {
        return subjectHindi;
    }

    public void setSubjectHindi(String subjectHindi) {
        this.subjectHindi = subjectHindi;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getJoiningTime() {
        return joiningTime;
    }

    public void setJoiningTime(String joiningTime) {
        this.joiningTime = joiningTime;
    }

    public String getRelevingDate() {
        return relevingDate;
    }

    public void setRelevingDate(String relevingDate) {
        this.relevingDate = relevingDate;
    }

    public String getRelevingTime() {
        return relevingTime;
    }

    public void setRelevingTime(String relevingTime) {
        this.relevingTime = relevingTime;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public boolean isInPosition() {
        return inPosition;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public boolean isAdditionalChargers() {
        return additionalChargers;
    }

    public void setAdditionalChargers(boolean additionalChargers) {
        this.additionalChargers = additionalChargers;
    }

    public String getHrmsIDHOD() {
        return hrmsIDHOD;
    }

    public void setHrmsIDHOD(String hrmsIDHOD) {
        this.hrmsIDHOD = hrmsIDHOD;
    }

    public String getHodName() {
        return hodName;
    }

    public void setHodName(String hodName) {
        this.hodName = hodName;
    }

    public String getHodNameHindi() {
        return hodNameHindi;
    }

    public void setHodNameHindi(String hodNameHindi) {
        this.hodNameHindi = hodNameHindi;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeNameHindi() {
        return officeNameHindi;
    }

    public void setOfficeNameHindi(String officeNameHindi) {
        this.officeNameHindi = officeNameHindi;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getBuildingOwner() {
        return buildingOwner;
    }

    public void setBuildingOwner(String buildingOwner) {
        this.buildingOwner = buildingOwner;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProjectNameHindi() {
        return projectNameHindi;
    }

    public void setProjectNameHindi(String projectNameHindi) {
        this.projectNameHindi = projectNameHindi;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getHeadOfOffice() {
        return headOfOffice;
    }

    public void setHeadOfOffice(String headOfOffice) {
        this.headOfOffice = headOfOffice;
    }
}
