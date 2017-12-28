/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.training;

import dricds.common.DataBaseFunctions;
import dricds.common.ListPageObject;
import dricds.model.training.TrainingCenterModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author surendra
 */
public class TrainingCenterDAOImpl implements TrainingCenterDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getTrainingCenterName(String trainingCenterId) {
        String tcname = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT tc_id,tc_name,tc_name_hindi from training_center where tc_id=?");
            ps.setString(1, trainingCenterId);
            rs = ps.executeQuery();
            if (rs.next()) {
                tcname = rs.getString("tc_name");
                // tcname=tcname+"@"+rs.getString("tc_name_hindi");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return tcname;
    }

    @Override
    public ListPageObject getTrainingCenterList(TrainingCenterModel tc) {
        List li = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TrainingCenterModel tcm = null;
        ListPageObject lo = new ListPageObject();
        String sql = "";
        try {
            con = this.dataSource.getConnection();
            if (tc.getTcDistrict() != null && !tc.getTcDistrict().equals("") && tc.getTcDistrict().equalsIgnoreCase("ALL")) {
                sql = "SELECT tc_id, tc_name,gtc.tc_type,gtc.tc_type_hindi,tc_address FROM training_center tc "
                        + "left outer join g_tc_type gtc on tc.tc_type=gtc.tc_type_id";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    tcm = new TrainingCenterModel();
                    tcm.setTcName(rs.getString("tc_name"));
                    tcm.setTcId(rs.getString("tc_id"));
                    tcm.setTcType(rs.getString("tc_type"));
                    tcm.setTcAddress(rs.getString("tc_address"));
                    li.add(tcm);
                }
                
                DataBaseFunctions.closeSqlObjects(rs);

                sql = "SELECT count(tc.tc_type) cnt ,tc_type_id FROM training_center tc \n"
                        + "left outer join g_tc_type gtc on tc.tc_type=gtc.tc_type_id  group by tc.tc_type";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    
                    if (rs.getInt("cnt") > 0) {
                        if(rs.getString("tc_type_id")!=null && !rs.getString("tc_type_id").equals("")){
                            if (rs.getString("tc_type_id").equals("1")) {
                            lo.setBox1(rs.getString("cnt"));
                        } else if (rs.getString("tc_type_id").equals("2")) {
                            lo.setBox2(rs.getString("cnt"));
                        }
                        }
                        
                    }
                }

            } else {
                sql = "SELECT tc_id, tc_name,tc_name_hindi,gtc.tc_type,gtc.tc_type_hindi,tc_address FROM training_center tc "
                        + "left outer join g_tc_type gtc on tc.tc_type=gtc.tc_type_id where tc_district=? order by tc_name";

                ps = con.prepareStatement(sql);
                ps.setString(1, tc.getTcDistrict());
                rs = ps.executeQuery();
                while (rs.next()) {
                    tcm = new TrainingCenterModel();
                    tcm.setTcName(rs.getString("tc_name"));
                    tcm.setTcId(rs.getString("tc_id"));
                    tcm.setTcType(rs.getString("tc_type"));
                    tcm.setTcAddress(rs.getString("tc_address"));
                    li.add(tcm);
                }

                DataBaseFunctions.closeSqlObjects(rs);

                sql = "SELECT count(tc.tc_type) cnt ,tc_type_id FROM training_center tc \n"
                        + "left outer join g_tc_type gtc on tc.tc_type=gtc.tc_type_id  where tc_district=? group by tc.tc_type";
                ps = con.prepareStatement(sql);
                ps.setString(1, tc.getTcDistrict());
                rs = ps.executeQuery();
                while (rs.next()) {
                    
                    if (rs.getInt("cnt") > 0) {
                        if (rs.getString("tc_type_id").equals("1")) {
                            lo.setBox1(rs.getString("cnt"));
                        } else if (rs.getString("tc_type_id").equals("2")) {
                            lo.setBox2(rs.getString("cnt"));
                        }
                    }
                }
            }
            lo.setDtaList(li);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return lo;
    }

    @Override
    public void addTrainiingCenterData(TrainingCenterModel tcm) {
        Connection con = null;
        PreparedStatement ps = null;
        String adminstafflist = "";
        String selectedEquipmentlist = "";
        String selectedOfficeEquipment = "";
        String selectedRoomType = "";
        String selectedHoFacilities = "";
        String selectedCareFacilities = "";

        try {
            con = this.dataSource.getConnection();

            if (tcm.getAdminStaffs() != null && tcm.getAdminStaffs().length > 0) {
                for (int i = 0; i < tcm.getAdminStaffs().length; i++) {
                    if (adminstafflist != null && !adminstafflist.equals("")) {
                        adminstafflist = adminstafflist + "," + tcm.getAdminStaffs()[i];
                    } else {
                        adminstafflist = tcm.getAdminStaffs()[i];
                    }
                }
            }
            if (tcm.getTrainingEquipment() != null && tcm.getTrainingEquipment().length > 0) {
                for (int j = 0; j < tcm.getTrainingEquipment().length; j++) {
                    if (selectedEquipmentlist != null && !selectedEquipmentlist.equals("")) {
                        selectedEquipmentlist = selectedEquipmentlist + "," + tcm.getTrainingEquipment()[j];
                    } else {
                        selectedEquipmentlist = tcm.getTrainingEquipment()[j];
                    }
                }
            }
            if (tcm.getOfficeEquipment() != null && tcm.getOfficeEquipment().length > 0) {
                for (int k = 0; k < tcm.getOfficeEquipment().length; k++) {
                    if (selectedOfficeEquipment != null && !selectedOfficeEquipment.equals("")) {
                        selectedOfficeEquipment = selectedOfficeEquipment + "," + tcm.getOfficeEquipment()[k];
                    } else {
                        selectedOfficeEquipment = tcm.getOfficeEquipment()[k];
                    }
                }
            }

            if (tcm.getRoomType() != null && tcm.getRoomType().length > 0) {
                for (int l = 0; l < tcm.getRoomType().length; l++) {
                    if (selectedRoomType != null && !selectedRoomType.equals("")) {
                        selectedRoomType = selectedRoomType + "," + tcm.getRoomType()[l];
                    } else {
                        selectedRoomType = tcm.getRoomType()[l];
                    }
                }
            }

            if (tcm.getHostelFacilities() != null && tcm.getHostelFacilities().length > 0) {
                for (int m = 0; m < tcm.getHostelFacilities().length; m++) {
                    if (selectedHoFacilities != null && !selectedHoFacilities.equals("")) {
                        selectedHoFacilities = selectedHoFacilities + "," + tcm.getHostelFacilities()[m];
                    } else {
                        selectedHoFacilities = tcm.getHostelFacilities()[m];
                    }
                }
            }

            if (tcm.getMedicalCare() != null && tcm.getMedicalCare().length > 0) {
                for (int n = 0; n < tcm.getMedicalCare().length; n++) {
                    if (selectedCareFacilities != null && !selectedCareFacilities.equals("")) {
                        selectedCareFacilities = selectedCareFacilities + "," + tcm.getMedicalCare()[n];
                    } else {
                        selectedCareFacilities = tcm.getMedicalCare()[n];
                    }
                }
            }

            ps = con.prepareStatement("INSERT INTO training_center (tc_id, tc_name,tc_type,tc_address,tc_district,latitude,longitude,in_charge_name,phone,mobile,"
                    + "e_mail,bank_acct,ifsc,capacity,fulltime_instructor_nos,parttime_instructor_nos,admin_staff_nos,admin_staffs,class_room_nos,"
                    + "class_furniture_type,class_facilities,training_equipment,office_equipment,if_library,"
                    + "book_nos,if_hostel,hostel_capacity,"
                    + "hostel_proximity,room_type,hostel_facilities,medical_care,ngo) "
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, tcm.getTcId());
            ps.setString(2, tcm.getTcName());
            ps.setString(3, tcm.getTcType());
            ps.setString(4, tcm.getTcAddress());
            ps.setString(5, tcm.getTcDistrict());
            ps.setString(6, tcm.getLatitude());
            ps.setString(7, tcm.getLongitude());
            ps.setString(8, tcm.getInchargeName());
            ps.setString(9, tcm.getPhone());
            ps.setString(10, tcm.getMobile());
            ps.setString(11, tcm.geteMail());
            ps.setString(12, tcm.getBankAcct());
            ps.setString(13, tcm.getIfsc());
            ps.setInt(14, tcm.getCapacity());
            ps.setInt(15, tcm.getFulltimeInstructorNos());
            ps.setInt(16, tcm.getParttimeInstructorNos());
            ps.setInt(17, tcm.getAdminStaffNos());
            ps.setString(18, adminstafflist);
            ps.setInt(19, tcm.getClassRoomNos());
            ps.setString(20, tcm.getClassFurnitureType());
            ps.setString(21, tcm.getClassFacilities());
            ps.setString(22, selectedEquipmentlist);
            ps.setString(23, selectedOfficeEquipment);
            ps.setString(24, tcm.getIfLibrary());
            ps.setString(25, tcm.getBookNos());
            ps.setString(26, tcm.getIfHostel());
            ps.setInt(27, tcm.getHostelCapacity());
            ps.setString(28, tcm.getHostelProximity());
            ps.setString(29, selectedRoomType);
            ps.setString(30, selectedHoFacilities);
            ps.setString(31, selectedCareFacilities);
            ps.setString(32, tcm.getNgo());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }

    }

    @Override
    public TrainingCenterModel editTrainiingCenterData(String trainingCenterId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TrainingCenterModel tcm = null;
        String sql = "";
        try {
            con = this.dataSource.getConnection();
            tcm = new TrainingCenterModel();
            sql = "SELECT tc_id, tc_name,tc_name_hindi,tc_type,tc_address,tc_district,latitude,longitude,in_charge_name,phone,mobile,"
                    + "e_mail,bank_acct,ifsc,capacity,fulltime_instructor_nos,parttime_instructor_nos,admin_staff_nos,admin_staffs,class_room_nos,"
                    + "class_furniture_type,class_facilities,training_equipment,office_equipment,if_library,"
                    + "book_nos,if_hostel,hostel_capacity,"
                    + "hostel_proximity,room_type,hostel_facilities,medical_care,ngo FROM "
                    + " training_center WHERE tc_id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, trainingCenterId);
            rs = ps.executeQuery();
            if (rs.next()) {
                tcm.setTcId(rs.getString("tc_id"));
                tcm.setTcName(rs.getString("tc_name"));
                tcm.setTcNameHindi(rs.getString("tc_name_hindi"));
                tcm.setTcType(rs.getString("tc_type"));
                tcm.setTcAddress(rs.getString("tc_address"));
                tcm.setTcDistrict(rs.getString("tc_district"));
                tcm.setLatitude(rs.getString("latitude"));
                tcm.setLongitude(rs.getString("longitude"));
                tcm.setInchargeName(rs.getString("in_charge_name"));
                tcm.setPhone(rs.getString("phone"));
                tcm.setMobile(rs.getString("mobile"));
                tcm.seteMail(rs.getString("e_mail"));
                tcm.setBankAcct(rs.getString("bank_acct"));
                tcm.setIfsc(rs.getString("ifsc"));
                tcm.setCapacity(rs.getInt("capacity"));
                tcm.setFulltimeInstructorNos(rs.getInt("fulltime_instructor_nos"));
                tcm.setParttimeInstructorNos(rs.getInt("parttime_instructor_nos"));
                tcm.setAdminStaffNos(rs.getInt("admin_staff_nos"));
                if (rs.getString("admin_staffs") != null && !rs.getString("admin_staffs").equals("")) {
                    tcm.setAdminStaffs(rs.getString("admin_staffs").split(","));
                } else {
                    tcm.setAdminStaffs(null);
                }
                tcm.setClassRoomNos(rs.getInt("class_room_nos"));
                tcm.setClassFurnitureType(rs.getString("class_furniture_type"));
                tcm.setClassFacilities(rs.getString("class_facilities"));
                if (rs.getString("training_equipment") != null && !rs.getString("training_equipment").equals("")) {
                    tcm.setTrainingEquipment(rs.getString("training_equipment").split(","));
                } else {
                    tcm.setTrainingEquipment(null);
                }
                if (rs.getString("office_equipment") != null && !rs.getString("office_equipment").equals("")) {
                    tcm.setOfficeEquipment(rs.getString("office_equipment").split(","));
                } else {
                    tcm.setOfficeEquipment(null);
                }
                tcm.setIfLibrary(rs.getString("if_library"));
                tcm.setBookNos(rs.getString("book_nos"));
                tcm.setIfHostel(rs.getString("if_hostel"));
                tcm.setHostelCapacity(rs.getInt("hostel_capacity"));
                tcm.setHostelProximity(rs.getString("hostel_proximity"));
                if (rs.getString("room_type") != null && !rs.getString("room_type").equals("")) {
                    tcm.setRoomType(rs.getString("room_type").split(","));
                } else {
                    tcm.setRoomType(null);
                }

                if (rs.getString("hostel_facilities") != null && !rs.getString("hostel_facilities").equals("")) {
                    tcm.setHostelFacilities(rs.getString("hostel_facilities").split(","));
                } else {
                    tcm.setHostelFacilities(null);
                }
                if (rs.getString("medical_care") != null && !rs.getString("medical_care").equals("")) {
                    tcm.setMedicalCare(rs.getString("medical_care").split(","));
                } else {
                    tcm.setMedicalCare(null);
                }
                tcm.setNgo(rs.getString("ngo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return tcm;
    }

    @Override
    public void updateTrainiingCenterData(TrainingCenterModel tcm) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "";
        String adminstafflist = "";
        String selectedEquipmentlist = "";
        String selectedOfficeEquipment = "";
        String selectedRoomType = "";
        String selectedHoFacilities = "";
        String selectedCareFacilities = "";
        try {

            if (tcm.getAdminStaffs() != null && tcm.getAdminStaffs().length > 0) {
                for (int i = 0; i < tcm.getAdminStaffs().length; i++) {
                    if (adminstafflist != null && !adminstafflist.equals("")) {
                        adminstafflist = adminstafflist + "," + tcm.getAdminStaffs()[i];
                    } else {
                        adminstafflist = tcm.getAdminStaffs()[i];
                    }
                }
            }
            if (tcm.getTrainingEquipment() != null && tcm.getTrainingEquipment().length > 0) {
                for (int j = 0; j < tcm.getTrainingEquipment().length; j++) {
                    if (selectedEquipmentlist != null && !selectedEquipmentlist.equals("")) {
                        selectedEquipmentlist = selectedEquipmentlist + "," + tcm.getTrainingEquipment()[j];
                    } else {
                        selectedEquipmentlist = tcm.getTrainingEquipment()[j];
                    }
                }
            }
            if (tcm.getOfficeEquipment() != null && tcm.getOfficeEquipment().length > 0) {
                for (int k = 0; k < tcm.getOfficeEquipment().length; k++) {
                    if (selectedOfficeEquipment != null && !selectedOfficeEquipment.equals("")) {
                        selectedOfficeEquipment = selectedOfficeEquipment + "," + tcm.getOfficeEquipment()[k];
                    } else {
                        selectedOfficeEquipment = tcm.getOfficeEquipment()[k];
                    }
                }
            }

            if (tcm.getRoomType() != null && tcm.getRoomType().length > 0) {
                for (int l = 0; l < tcm.getRoomType().length; l++) {
                    if (selectedRoomType != null && !selectedRoomType.equals("")) {
                        selectedRoomType = selectedRoomType + "," + tcm.getRoomType()[l];
                    } else {
                        selectedRoomType = tcm.getRoomType()[l];
                    }
                }
            }

            if (tcm.getHostelFacilities() != null && tcm.getHostelFacilities().length > 0) {
                for (int m = 0; m < tcm.getHostelFacilities().length; m++) {
                    if (selectedHoFacilities != null && !selectedHoFacilities.equals("")) {
                        selectedHoFacilities = selectedHoFacilities + "," + tcm.getHostelFacilities()[m];
                    } else {
                        selectedHoFacilities = tcm.getHostelFacilities()[m];
                    }
                }
            }

            if (tcm.getMedicalCare() != null && tcm.getMedicalCare().length > 0) {
                for (int n = 0; n < tcm.getMedicalCare().length; n++) {
                    if (selectedCareFacilities != null && !selectedCareFacilities.equals("")) {
                        selectedCareFacilities = selectedCareFacilities + "," + tcm.getMedicalCare()[n];
                    } else {
                        selectedCareFacilities = tcm.getMedicalCare()[n];
                    }
                }
            }

            con = this.dataSource.getConnection();
            sql = "UPDATE training_center set tc_name=?,tc_type=?,tc_address=?,tc_district=?,latitude=?,longitude=?,in_charge_name=?,phone=?,mobile=?,"
                    + "e_mail=?,bank_acct=?,ifsc=?,capacity=?,fulltime_instructor_nos=?,parttime_instructor_nos=?,admin_staff_nos=?,admin_staffs=?,class_room_nos=?,"
                    + "class_furniture_type=?,class_facilities=?,training_equipment=?,office_equipment=?,if_library=?,"
                    + "book_nos=?,if_hostel=?,hostel_capacity=?,"
                    + "hostel_proximity=?,room_type=?,hostel_facilities=?,medical_care=?,ngo=?,tc_name_hindi=? WHERE tc_id=?";

            ps = con.prepareStatement(sql);
            ps.setString(1, tcm.getTcName());
            ps.setString(2, tcm.getTcType());
            ps.setString(3, tcm.getTcAddress());
            ps.setString(4, tcm.getTcDistrict());
            ps.setString(5, tcm.getLatitude());
            ps.setString(6, tcm.getLongitude());
            ps.setString(7, tcm.getInchargeName());
            ps.setString(8, tcm.getPhone());
            ps.setString(9, tcm.getMobile());
            ps.setString(10, tcm.geteMail());
            ps.setString(11, tcm.getBankAcct());
            ps.setString(12, tcm.getIfsc());
            ps.setInt(13, tcm.getCapacity());
            ps.setInt(14, tcm.getFulltimeInstructorNos());
            ps.setInt(15, tcm.getParttimeInstructorNos());
            ps.setInt(16, tcm.getAdminStaffNos());
            ps.setString(17, adminstafflist);
            ps.setInt(18, tcm.getClassRoomNos());
            ps.setString(19, tcm.getClassFurnitureType());
            ps.setString(20, tcm.getClassFacilities());
            ps.setString(21, selectedEquipmentlist);
            ps.setString(22, selectedOfficeEquipment);
            ps.setString(23, tcm.getIfLibrary());
            ps.setString(24, tcm.getBookNos());
            ps.setString(25, tcm.getIfHostel());
            ps.setInt(26, tcm.getHostelCapacity());
            ps.setString(27, tcm.getHostelProximity());
            ps.setString(28, selectedRoomType);
            ps.setString(29, selectedHoFacilities);
            ps.setString(30, selectedCareFacilities);
            ps.setString(31, tcm.getNgo());
            ps.setString(32, tcm.getTcNameHindi());
            ps.setString(33, tcm.getTcId());
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
    }

    @Override
    public void deleteTrainingData(String trainingCenterId) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("DELETE FROM training_center where tc_id=?");
            ps.setString(1, trainingCenterId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
    }
}
