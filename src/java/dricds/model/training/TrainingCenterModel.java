/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.training;

import java.util.List;

/**
 *
 * @author surendra
 */
public class TrainingCenterModel {

    private String tcId = "";
    private String tcName = "";
    private String tcNameHindi = "";
    private String tcType = "";
    private String tcAddress = "";
    private String tcDistrict = "";
    private String latitude = "";
    private String longitude = "";
    private String inchargeName = "";
    private String phone = "";
    private String mobile = "";
    private String eMail = "";
    private String bankAcct = "";
    private String ifsc = "";
    private int capacity = 0;
    private int fulltimeInstructorNos = 0;
    private int parttimeInstructorNos = 0;
    private int adminStaffNos = 0;
    private String[] adminStaffs = null;
    private int classRoomNos = 0;
    private String classFurnitureType = "";
    private String classFacilities = "";
    private String[] trainingEquipment = null;
    private String[] officeEquipment = null;
    private String ifLibrary = "";
    private String bookNos = "";
    private String ifHostel = "";
    private int hostelCapacity = 0;
    private String hostelProximity = "";
    private String[] roomType = null;
    private String[] hostelFacilities = null;
    private String[] medicalCare = null;
    private String ngo = "";

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getTcNameHindi() {
        return tcNameHindi;
    }

    public void setTcNameHindi(String tcNameHindi) {
        this.tcNameHindi = tcNameHindi;
    }
    
    public String getTcType() {
        return tcType;
    }

    public void setTcType(String tcType) {
        this.tcType = tcType;
    }

    public String getTcAddress() {
        return tcAddress;
    }

    public void setTcAddress(String tcAddress) {
        this.tcAddress = tcAddress;
    }

    public String getTcDistrict() {
        return tcDistrict;
    }

    public void setTcDistrict(String tcDistrict) {
        this.tcDistrict = tcDistrict;
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

    public String getInchargeName() {
        return inchargeName;
    }

    public void setInchargeName(String inchargeName) {
        this.inchargeName = inchargeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFulltimeInstructorNos() {
        return fulltimeInstructorNos;
    }

    public void setFulltimeInstructorNos(int fulltimeInstructorNos) {
        this.fulltimeInstructorNos = fulltimeInstructorNos;
    }

    public int getParttimeInstructorNos() {
        return parttimeInstructorNos;
    }

    public void setParttimeInstructorNos(int parttimeInstructorNos) {
        this.parttimeInstructorNos = parttimeInstructorNos;
    }

    public int getAdminStaffNos() {
        return adminStaffNos;
    }

    public void setAdminStaffNos(int adminStaffNos) {
        this.adminStaffNos = adminStaffNos;
    }

    public String[] getAdminStaffs() {
        return adminStaffs;
    }

    public void setAdminStaffs(String[] adminStaffs) {
        this.adminStaffs = adminStaffs;
    }

    public int getClassRoomNos() {
        return classRoomNos;
    }

    public void setClassRoomNos(int classRoomNos) {
        this.classRoomNos = classRoomNos;
    }

    public String getClassFurnitureType() {
        return classFurnitureType;
    }

    public void setClassFurnitureType(String classFurnitureType) {
        this.classFurnitureType = classFurnitureType;
    }

    public String getClassFacilities() {
        return classFacilities;
    }

    public void setClassFacilities(String classFacilities) {
        this.classFacilities = classFacilities;
    }

    public String[] getTrainingEquipment() {
        return trainingEquipment;
    }

    public void setTrainingEquipment(String[] trainingEquipment) {
        this.trainingEquipment = trainingEquipment;
    }

    public String[] getOfficeEquipment() {
        return officeEquipment;
    }

    public void setOfficeEquipment(String[] officeEquipment) {
        this.officeEquipment = officeEquipment;
    }

    public String getIfLibrary() {
        return ifLibrary;
    }

    public void setIfLibrary(String ifLibrary) {
        this.ifLibrary = ifLibrary;
    }

    public String getBookNos() {
        return bookNos;
    }

    public void setBookNos(String bookNos) {
        this.bookNos = bookNos;
    }

    public String getIfHostel() {
        return ifHostel;
    }

    public void setIfHostel(String ifHostel) {
        this.ifHostel = ifHostel;
    }

    public int getHostelCapacity() {
        return hostelCapacity;
    }

    public void setHostelCapacity(int hostelCapacity) {
        this.hostelCapacity = hostelCapacity;
    }

    public String getHostelProximity() {
        return hostelProximity;
    }

    public void setHostelProximity(String hostelProximity) {
        this.hostelProximity = hostelProximity;
    }

    public String[] getRoomType() {
        return roomType;
    }

    public void setRoomType(String[] roomType) {
        this.roomType = roomType;
    }

    public String[] getHostelFacilities() {
        return hostelFacilities;
    }

    public void setHostelFacilities(String[] hostelFacilities) {
        this.hostelFacilities = hostelFacilities;
    }

    public String[] getMedicalCare() {
        return medicalCare;
    }

    public void setMedicalCare(String[] medicalCare) {
        this.medicalCare = medicalCare;
    }

    public String getNgo() {
        return ngo;
    }

    public void setNgo(String ngo) {
        this.ngo = ngo;
    }

}
