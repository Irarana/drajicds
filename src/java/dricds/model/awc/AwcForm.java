/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.awc;

import java.util.ArrayList;

/**
 *
 * @author surendra
 */
public class AwcForm {

    private String awcCode = "";
    private String awcNameEn = "";
    private String awcNameHn = "";
    private String awcType = "";
    private String urbanRural = "";
    private String urbanRuralHindi = "";
    private String proximity = "";
    private String proximityHindi = "";
    private int sanctionYear;
    private String address = "";
    private String latitude = "";
    private String longitude = "";
    private ArrayList awcTypeList = null;

    private String sectorCode = null;
    private String projectCode = null;
    private String districtCode = null;
    private String distName = null;
    private String distNameHindi = null;
    private ArrayList distNameList = null;
    private String projectName = null;
    private String projectNameHindi = null;
    private ArrayList projectNameList = null;
    private String sectorName = null;
    private String sectorNameHindi = null;
    private ArrayList sectorList = null;
    private ArrayList proximityList = null;

    private String divisionName = null;
    private String divisionNameHindi = null;
    private ArrayList divisionNameList = null;
    private String subDivisionName = null;
    private ArrayList subDivisionNameList = null;
    private String tehsilName = null;
    private ArrayList tehsilNameList = null;
    private String villName = null;
    private ArrayList villNameList = null;

    private String awwMob = null;
    private String isRepair = null;
    private String isdisableAccess = null;
    private String ifKitchen = null;
    private String ifStorageSpace = null;
    private String ifToilet = null;
    private String ifToiletFunc = null;
    private String ifWaterAvail = null;
    private String ifExclusiveToilet = null;
    private String ifdrinkWaterAvail = null;
    private String enrolNo = null;

    private String blockName = null;
    private ArrayList blockNameList = null;
    private String gpName = null;
    private ArrayList gpNameList = null;
    private String ulbType = null;
    private String ulbName = null;
    private ArrayList ulbTypeList = null;
    private ArrayList ulbNameList = null;
    private ArrayList zilaParisadNameList = null;

    private String lsName = null;
    private ArrayList lsNameList = null;
    private String vsName = null;
    private ArrayList vsNameList = null;
    private String schoolName = null;
    private ArrayList schoolNameList = null;
    private String schoolType = null;
    private ArrayList schoolTypeList = null;
    private String anganwadiWorker = null;
    private ArrayList anganwadiWorkerList = null;
    private String anganwadiHelper = null;
    private ArrayList anganwadiHelperList = null;
    private String ashaWorker = null;
    private ArrayList ashaWorkerList = null;

    private String aww2awcDist = null;
    private ArrayList aww2awcDistList = null;
    private String ownerName = null;
    private ArrayList ownerNameList = null;
    private String buildingStruct = null;
    private ArrayList buildingStructList = null;
    private String space = null;
    private ArrayList spaceList = null;
    private String toiletType = null;
    private ArrayList toiletTypeList = null;
    private String drinkWaterSrc = null;
    private ArrayList drinkWaterSrcList = null;
    private String electAccess = null;
    private ArrayList electAccessList = null;

    private String hidDistName = null;
    private String hidProName = null;
    private String hidSectorName = null;
    private String hidSubDivisionName = null;
    private String hidVillName = null;
    private String hidBlockName = null;
    private String hidGpName = null;
    private String hidUlbType = null;
    private String hidUlbName = null;
    private String hidOwnerName = null;
    private String hidBuildingStruct = null;
    private String hidSchoolName = null;

    private String isTagged2school = null;
    private String zilaparishad = null;
    private String phc = null;
    private String subCenter = null;
    private String mentorName = null;
    private String mentorMobile = null;
    private String hidPHCName = null;
    private String hidSubCenterName = null;
    private String hidZilaparishad = null;

    private String subDivision = null;
    private String subDivisionHindi = null;
    private String tehsil = null;
    private String tehsilHindi = null;
    private String villagename = null;
    private String villagenameHindi = null;
    private String blocknameHindi = null;
    private String gpnameHindi = null;
    private String lsnameHindi=null;
    private String vsnameHindi=null;
    private String anganwadiWorkerHindi=null;
    private String anganwadiHelperHindi=null;
    private String ashaWorkerHindi = null;
    private String anganwadiWorkerMobile=null;
    private String buildingStructHindi=null;
    private String spaceHindi=null;
    private String toiletTypeHindi=null;
    private String drinkWaterSrcHindi=null;
    private String electAccessHindi=null;
    private String aww2awcDistHindi=null;
    private String userlevel=null;
    private String schoolCode=null;

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    
    

    public String getAww2awcDistHindi() {
        return aww2awcDistHindi;
    }

    public void setAww2awcDistHindi(String aww2awcDistHindi) {
        this.aww2awcDistHindi = aww2awcDistHindi;
    }
    
    

    public String getBuildingStructHindi() {
        return buildingStructHindi;
    }

    public void setBuildingStructHindi(String buildingStructHindi) {
        this.buildingStructHindi = buildingStructHindi;
    }

    public String getSpaceHindi() {
        return spaceHindi;
    }

    public void setSpaceHindi(String spaceHindi) {
        this.spaceHindi = spaceHindi;
    }

    public String getToiletTypeHindi() {
        return toiletTypeHindi;
    }

    public void setToiletTypeHindi(String toiletTypeHindi) {
        this.toiletTypeHindi = toiletTypeHindi;
    }

    

    public String getDrinkWaterSrcHindi() {
        return drinkWaterSrcHindi;
    }

    public void setDrinkWaterSrcHindi(String drinkWaterSrcHindi) {
        this.drinkWaterSrcHindi = drinkWaterSrcHindi;
    }

    public String getElectAccessHindi() {
        return electAccessHindi;
    }

    public void setElectAccessHindi(String electAccessHindi) {
        this.electAccessHindi = electAccessHindi;
    }
    
    
    
    public String getAnganwadiWorkerMobile() {
        return anganwadiWorkerMobile;
    }

    public void setAnganwadiWorkerMobile(String anganwadiWorkerMobile) {
        this.anganwadiWorkerMobile = anganwadiWorkerMobile;
    }
    
    
    
    public String getAnganwadiWorkerHindi() {
        return anganwadiWorkerHindi;
    }

    public void setAnganwadiWorkerHindi(String anganwadiWorkerHindi) {
        this.anganwadiWorkerHindi = anganwadiWorkerHindi;
    }

    public String getAnganwadiHelperHindi() {
        return anganwadiHelperHindi;
    }

    public void setAnganwadiHelperHindi(String anganwadiHelperHindi) {
        this.anganwadiHelperHindi = anganwadiHelperHindi;
    }

    public String getAshaWorkerHindi() {
        return ashaWorkerHindi;
    }

    public void setAshaWorkerHindi(String ashaWorkerHindi) {
        this.ashaWorkerHindi = ashaWorkerHindi;
    }
    

    
    public String getLsnameHindi() {
        return lsnameHindi;
    }

    public void setLsnameHindi(String lsnameHindi) {
        this.lsnameHindi = lsnameHindi;
    }

    public String getVsnameHindi() {
        return vsnameHindi;
    }

    public void setVsnameHindi(String vsnameHindi) {
        this.vsnameHindi = vsnameHindi;
    }

    public String getBlocknameHindi() {
        return blocknameHindi;
    }

    public void setBlocknameHindi(String blocknameHindi) {
        this.blocknameHindi = blocknameHindi;
    }

    public String getGpnameHindi() {
        return gpnameHindi;
    }

    public void setGpnameHindi(String gpnameHindi) {
        this.gpnameHindi = gpnameHindi;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getSubDivisionHindi() {
        return subDivisionHindi;
    }

    public void setSubDivisionHindi(String subDivisionHindi) {
        this.subDivisionHindi = subDivisionHindi;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getTehsilHindi() {
        return tehsilHindi;
    }

    public void setTehsilHindi(String tehsilHindi) {
        this.tehsilHindi = tehsilHindi;
    }

    public String getVillagename() {
        return villagename;
    }

    public void setVillagename(String villagename) {
        this.villagename = villagename;
    }

    public String getVillagenameHindi() {
        return villagenameHindi;
    }

    public void setVillagenameHindi(String villagenameHindi) {
        this.villagenameHindi = villagenameHindi;
    }

    public String getUrbanRuralHindi() {
        return urbanRuralHindi;
    }

    public void setUrbanRuralHindi(String urbanRuralHindi) {
        this.urbanRuralHindi = urbanRuralHindi;
    }

    public String getProjectNameHindi() {
        return projectNameHindi;
    }

    public void setProjectNameHindi(String projectNameHindi) {
        this.projectNameHindi = projectNameHindi;
    }

    public String getSectorNameHindi() {
        return sectorNameHindi;
    }

    public void setSectorNameHindi(String sectorNameHindi) {
        this.sectorNameHindi = sectorNameHindi;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public ArrayList getZilaParisadNameList() {
        return zilaParisadNameList;
    }

    public void setZilaParisadNameList(ArrayList zilaParisadNameList) {
        this.zilaParisadNameList = zilaParisadNameList;
    }

    public String getHidZilaparishad() {
        return hidZilaparishad;
    }

    public void setHidZilaparishad(String hidZilaparishad) {
        this.hidZilaparishad = hidZilaparishad;
    }

    public String getHidPHCName() {
        return hidPHCName;
    }

    public void setHidPHCName(String hidPHCName) {
        this.hidPHCName = hidPHCName;
    }

    public String getHidSubCenterName() {
        return hidSubCenterName;
    }

    public void setHidSubCenterName(String hidSubCenterName) {
        this.hidSubCenterName = hidSubCenterName;
    }

    public String getZilaparishad() {
        return zilaparishad;
    }

    public void setZilaparishad(String zilaparishad) {
        this.zilaparishad = zilaparishad;
    }

    public String getIsTagged2school() {
        return isTagged2school;
    }

    public void setIsTagged2school(String isTagged2school) {
        this.isTagged2school = isTagged2school;
    }

    public String getPhc() {
        return phc;
    }

    public void setPhc(String phc) {
        this.phc = phc;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorMobile() {
        return mentorMobile;
    }

    public void setMentorMobile(String mentorMobile) {
        this.mentorMobile = mentorMobile;
    }

    public String getHidSchoolName() {
        return hidSchoolName;
    }

    public void setHidSchoolName(String hidSchoolName) {
        this.hidSchoolName = hidSchoolName;
    }

    public String getHidDistName() {
        return hidDistName;
    }

    public void setHidDistName(String hidDistName) {
        this.hidDistName = hidDistName;
    }

    public String getHidUlbName() {
        return hidUlbName;
    }

    public void setHidUlbName(String hidUlbName) {
        this.hidUlbName = hidUlbName;
    }

    public String getAnganwadiWorker() {
        return anganwadiWorker;
    }

    public void setAnganwadiWorker(String anganwadiWorker) {
        this.anganwadiWorker = anganwadiWorker;
    }

    public ArrayList getAnganwadiWorkerList() {
        return anganwadiWorkerList;
    }

    public void setAnganwadiWorkerList(ArrayList anganwadiWorkerList) {
        this.anganwadiWorkerList = anganwadiWorkerList;
    }

    public String getAnganwadiHelper() {
        return anganwadiHelper;
    }

    public void setAnganwadiHelper(String anganwadiHelper) {
        this.anganwadiHelper = anganwadiHelper;
    }

    public ArrayList getAnganwadiHelperList() {
        return anganwadiHelperList;
    }

    public void setAnganwadiHelperList(ArrayList anganwadiHelperList) {
        this.anganwadiHelperList = anganwadiHelperList;
    }

    public String getAshaWorker() {
        return ashaWorker;
    }

    public void setAshaWorker(String ashaWorker) {
        this.ashaWorker = ashaWorker;
    }

    public ArrayList getAshaWorkerList() {
        return ashaWorkerList;
    }

    public void setAshaWorkerList(ArrayList ashaWorkerList) {
        this.ashaWorkerList = ashaWorkerList;
    }

    public String getHidBuildingStruct() {
        return hidBuildingStruct;
    }

    public void setHidBuildingStruct(String hidBuildingStruct) {
        this.hidBuildingStruct = hidBuildingStruct;
    }

    public String getHidOwnerName() {
        return hidOwnerName;
    }

    public void setHidOwnerName(String hidOwnerName) {
        this.hidOwnerName = hidOwnerName;
    }

    public String getHidUlbType() {
        return hidUlbType;
    }

    public void setHidUlbType(String hidUlbType) {
        this.hidUlbType = hidUlbType;
    }

    public String getHidBlockName() {
        return hidBlockName;
    }

    public void setHidBlockName(String hidBlockName) {
        this.hidBlockName = hidBlockName;
    }

    public String getHidGpName() {
        return hidGpName;
    }

    public void setHidGpName(String hidGpName) {
        this.hidGpName = hidGpName;
    }

    public String getHidVillName() {
        return hidVillName;
    }

    public void setHidVillName(String hidVillName) {
        this.hidVillName = hidVillName;
    }

    public String getHidSubDivisionName() {
        return hidSubDivisionName;
    }

    public void setHidSubDivisionName(String hidSubDivisionName) {
        this.hidSubDivisionName = hidSubDivisionName;
    }

    public String getDivisionNameHindi() {
        return divisionNameHindi;
    }

    public void setDivisionNameHindi(String divisionNameHindi) {
        this.divisionNameHindi = divisionNameHindi;
    }

    public String getHidSectorName() {
        return hidSectorName;
    }

    public void setHidSectorName(String hidSectorName) {
        this.hidSectorName = hidSectorName;
    }

    public String getHidProName() {
        return hidProName;
    }

    public void setHidProName(String hidProName) {
        this.hidProName = hidProName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public ArrayList getSchoolTypeList() {
        return schoolTypeList;
    }

    public void setSchoolTypeList(ArrayList schoolTypeList) {
        this.schoolTypeList = schoolTypeList;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public ArrayList getSchoolNameList() {
        return schoolNameList;
    }

    public void setSchoolNameList(ArrayList schoolNameList) {
        this.schoolNameList = schoolNameList;
    }

    public String getAww2awcDist() {
        return aww2awcDist;
    }

    public void setAww2awcDist(String aww2awcDist) {
        this.aww2awcDist = aww2awcDist;
    }

    public ArrayList getAww2awcDistList() {
        return aww2awcDistList;
    }

    public void setAww2awcDistList(ArrayList aww2awcDistList) {
        this.aww2awcDistList = aww2awcDistList;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public ArrayList getOwnerNameList() {
        return ownerNameList;
    }

    public void setOwnerNameList(ArrayList ownerNameList) {
        this.ownerNameList = ownerNameList;
    }

    public String getBuildingStruct() {
        return buildingStruct;
    }

    public void setBuildingStruct(String buildingStruct) {
        this.buildingStruct = buildingStruct;
    }

    public ArrayList getBuildingStructList() {
        return buildingStructList;
    }

    public void setBuildingStructList(ArrayList buildingStructList) {
        this.buildingStructList = buildingStructList;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public ArrayList getSpaceList() {
        return spaceList;
    }

    public void setSpaceList(ArrayList spaceList) {
        this.spaceList = spaceList;
    }

    public String getToiletType() {
        return toiletType;
    }

    public void setToiletType(String toiletType) {
        this.toiletType = toiletType;
    }

    public ArrayList getToiletTypeList() {
        return toiletTypeList;
    }

    public void setToiletTypeList(ArrayList toiletTypeList) {
        this.toiletTypeList = toiletTypeList;
    }

    public String getDrinkWaterSrc() {
        return drinkWaterSrc;
    }

    public void setDrinkWaterSrc(String drinkWaterSrc) {
        this.drinkWaterSrc = drinkWaterSrc;
    }

    public ArrayList getDrinkWaterSrcList() {
        return drinkWaterSrcList;
    }

    public void setDrinkWaterSrcList(ArrayList drinkWaterSrcList) {
        this.drinkWaterSrcList = drinkWaterSrcList;
    }

    public String getElectAccess() {
        return electAccess;
    }

    public void setElectAccess(String electAccess) {
        this.electAccess = electAccess;
    }

    public ArrayList getElectAccessList() {
        return electAccessList;
    }

    public void setElectAccessList(ArrayList electAccessList) {
        this.electAccessList = electAccessList;
    }

    public String getEnrolNo() {
        return enrolNo;
    }

    public void setEnrolNo(String enrolNo) {
        this.enrolNo = enrolNo;
    }

    public String getLsName() {
        return lsName;
    }

    public void setLsName(String lsName) {
        this.lsName = lsName;
    }

    public ArrayList getLsNameList() {
        return lsNameList;
    }

    public void setLsNameList(ArrayList lsNameList) {
        this.lsNameList = lsNameList;
    }

    public String getVsName() {
        return vsName;
    }

    public void setVsName(String vsName) {
        this.vsName = vsName;
    }

    public ArrayList getVsNameList() {
        return vsNameList;
    }

    public void setVsNameList(ArrayList vsNameList) {
        this.vsNameList = vsNameList;
    }

    public String getUlbType() {
        return ulbType;
    }

    public void setUlbType(String ulbType) {
        this.ulbType = ulbType;
    }

    public String getUlbName() {
        return ulbName;
    }

    public void setUlbName(String ulbName) {
        this.ulbName = ulbName;
    }

    public ArrayList getUlbTypeList() {
        return ulbTypeList;
    }

    public void setUlbTypeList(ArrayList ulbTypeList) {
        this.ulbTypeList = ulbTypeList;
    }

    public ArrayList getUlbNameList() {
        return ulbNameList;
    }

    public void setUlbNameList(ArrayList ulbNameList) {
        this.ulbNameList = ulbNameList;
    }

    public String getGpName() {
        return gpName;
    }

    public void setGpName(String gpName) {
        this.gpName = gpName;
    }

    public ArrayList getGpNameList() {
        return gpNameList;
    }

    public void setGpNameList(ArrayList gpNameList) {
        this.gpNameList = gpNameList;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public ArrayList getBlockNameList() {
        return blockNameList;
    }

    public void setBlockNameList(ArrayList blockNameList) {
        this.blockNameList = blockNameList;
    }

    public String getIfKitchen() {
        return ifKitchen;
    }

    public void setIfKitchen(String ifKitchen) {
        this.ifKitchen = ifKitchen;
    }

    public String getIfStorageSpace() {
        return ifStorageSpace;
    }

    public void setIfStorageSpace(String ifStorageSpace) {
        this.ifStorageSpace = ifStorageSpace;
    }

    public String getIfToilet() {
        return ifToilet;
    }

    public void setIfToilet(String ifToilet) {
        this.ifToilet = ifToilet;
    }

    public String getIfToiletFunc() {
        return ifToiletFunc;
    }

    public void setIfToiletFunc(String ifToiletFunc) {
        this.ifToiletFunc = ifToiletFunc;
    }

    public String getIfWaterAvail() {
        return ifWaterAvail;
    }

    public void setIfWaterAvail(String ifWaterAvail) {
        this.ifWaterAvail = ifWaterAvail;
    }

    public String getIfExclusiveToilet() {
        return ifExclusiveToilet;
    }

    public void setIfExclusiveToilet(String ifExclusiveToilet) {
        this.ifExclusiveToilet = ifExclusiveToilet;
    }

    public String getIfdrinkWaterAvail() {
        return ifdrinkWaterAvail;
    }

    public void setIfdrinkWaterAvail(String ifdrinkWaterAvail) {
        this.ifdrinkWaterAvail = ifdrinkWaterAvail;
    }

    public String getIsdisableAccess() {
        return isdisableAccess;
    }

    public void setIsdisableAccess(String isdisableAccess) {
        this.isdisableAccess = isdisableAccess;
    }

    public String getIsRepair() {
        return isRepair;
    }

    public void setIsRepair(String isRepair) {
        this.isRepair = isRepair;
    }

    public String getAwwMob() {
        return awwMob;
    }

    public void setAwwMob(String awwMob) {
        this.awwMob = awwMob;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public ArrayList getDivisionNameList() {
        return divisionNameList;
    }

    public void setDivisionNameList(ArrayList divisionNameList) {
        this.divisionNameList = divisionNameList;
    }

    public String getSubDivisionName() {
        return subDivisionName;
    }

    public void setSubDivisionName(String subDivisionName) {
        this.subDivisionName = subDivisionName;
    }

    public ArrayList getSubDivisionNameList() {
        return subDivisionNameList;
    }

    public void setSubDivisionNameList(ArrayList subDivisionNameList) {
        this.subDivisionNameList = subDivisionNameList;
    }

    public String getTehsilName() {
        return tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }

    public ArrayList getTehsilNameList() {
        return tehsilNameList;
    }

    public void setTehsilNameList(ArrayList tehsilNameList) {
        this.tehsilNameList = tehsilNameList;
    }

    public String getVillName() {
        return villName;
    }

    public void setVillName(String villName) {
        this.villName = villName;
    }

    public ArrayList getVillNameList() {
        return villNameList;
    }

    public void setVillNameList(ArrayList villNameList) {
        this.villNameList = villNameList;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public ArrayList getDistNameList() {
        return distNameList;
    }

    public void setDistNameList(ArrayList distNameList) {
        this.distNameList = distNameList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList getProjectNameList() {
        return projectNameList;
    }

    public void setProjectNameList(ArrayList projectNameList) {
        this.projectNameList = projectNameList;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public ArrayList getSectorList() {
        return sectorList;
    }

    public void setSectorList(ArrayList sectorList) {
        this.sectorList = sectorList;
    }

    public ArrayList getAwcTypeList() {
        return awcTypeList;
    }

    public String getDistNameHindi() {
        return distNameHindi;
    }

    public void setDistNameHindi(String distNameHindi) {
        this.distNameHindi = distNameHindi;
    }

    public void setAwcTypeList(ArrayList awcTypeList) {
        this.awcTypeList = awcTypeList;
    }

    public String getAwcCode() {
        return awcCode;
    }

    public void setAwcCode(String awcCode) {
        this.awcCode = awcCode;
    }

    public String getAwcNameEn() {
        return awcNameEn;
    }

    public void setAwcNameEn(String awcNameEn) {
        this.awcNameEn = awcNameEn;
    }

    public String getAwcNameHn() {
        return awcNameHn;
    }

    public void setAwcNameHn(String awcNameHn) {
        this.awcNameHn = awcNameHn;
    }

    public String getAwcType() {
        return awcType;
    }

    public void setAwcType(String awcType) {
        this.awcType = awcType;
    }

    public String getUrbanRural() {
        return urbanRural;
    }

    public void setUrbanRural(String urbanRural) {
        this.urbanRural = urbanRural;
    }

    public String getProximity() {
        return proximity;
    }

    public void setProximity(String proximity) {
        this.proximity = proximity;
    }

    public int getSanctionYear() {
        return sanctionYear;
    }

    public void setSanctionYear(int sanctionYear) {
        this.sanctionYear = sanctionYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList getProximityList() {
        return proximityList;
    }

    public void setProximityList(ArrayList proximityList) {
        this.proximityList = proximityList;
    }

    public String getProximityHindi() {
        return proximityHindi;
    }

    public void setProximityHindi(String proximityHindi) {
        this.proximityHindi = proximityHindi;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    
}
