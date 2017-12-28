/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.awc;

import dricds.common.DataBaseFunctions;
import dricds.common.ListPageObject;
import dricds.common.SelectOption;
import dricds.model.awc.AwcForm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author surendra
 */
public class AwcDAOImpl implements AwcDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getAnganWadiCenterList(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT awc_code,awc_name_english,awc_name_hindi FROM awc_master order by awc_name_english");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("awc_code"), rs.getString("awc_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("awc_code"), rs.getString("awc_name_english"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return centerList;
    }

    @Override
    public Map<String, String> getAnganWadiCenterListSectorWise(Locale locale, String sectorCode) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT awc_code,awc_name_english,awc_name_hindi FROM awc_master WHERE sector_code=? order by awc_name_english");
            ps.setString(1, sectorCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("awc_code"), rs.getString("awc_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("awc_code"), rs.getString("awc_name_english"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return centerList;
    }

    @Override
    public Map<String, String> getCenterListFilter(Locale locale, String awcCode) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT awc_code,awc_name_english,awc_name_hindi FROM awc_master WHERE awc_code=? order by awc_name_english");
            ps.setString(1, awcCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("awc_code"), rs.getString("awc_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("awc_code"), rs.getString("awc_name_english"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return centerList;
    }

    @Override
    public String getSectorCode(String awcCode) {
        String sectorCode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT sector_code FROM awc_master WHERE awc_code=? ");
            ps.setString(1, awcCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                sectorCode = rs.getString("sector_code");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return sectorCode;
    }

    @Override
    public AwcForm viewAWCProfileData(String awcCode) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        AwcForm awc = new AwcForm();
        try {

            sql = "SELECT awc_name_hindi,awc_name_english,urban_rural,g_sector.sector_name,g_sector.sector_name_hindi,g_proximity.proximity,proximity_hindi,\n"
                    + "g_project.project_name,g_project.project_name_hindi,g_division.division_name,division_name_hindi,latitude,longitude, \n"
                    + "g_district.district_name,g_district.district_name_hindi,\n"
                    + "sub.subdivision_name,sub.subdivision_name_hindi,g_tehsil.tehsil_name,tehsil_name_hindi,g_village.village_name,village_name_hindi,  \n"
                    + "g_block.block_name,block_name_hindi,g_gp.gp_name,gp_name_hindi, \n"
                    + "g_ls_mast.ls_name,ls_name_hindi,g_vs_mast.vs_name,vs_name_hindi, \n"
                    + "g_school_mast.school_name,school_name_hindi,g_school_mast.school_type,mentor_name,mentor_mobile,\n"
                    + "worker.worker_name,worker.worker_name_hindi,worker.mobile workermobile,aww_id,helper.worker_name helper_name,\n"
                    + "helper.worker_name_hindi helper_name_hindi,asha.worker_name asha_name,asha.worker_name_hindi asha_name_hindi,AWWDIST.aww2awc,aww2awc_hindi,\n"
                    + "struct.buidling_structure, buidling_structure_hindi,building_needs_repair,\n"
                    + "g_space.space,space_hindi,if_kitchen,if_store,if_toilet,if_toilet_functional,\n"
                    + "g_toilet_type.toilet_type,g_toilet_type.toilet_type_hindi,if_water_in_toilet,if_exclussive_toilet,if_drink_water,\n"
                    + "dws.drink_water_source,dws.drink_water_source_hindi,electric.electricty_access,electricty_access_hindi,ece_enrolment\n"
                    + "\n"
                    + " FROM awc_master awc\n"
                    + "LEFT OUTER JOIN g_sector ON awc.sector_code=g_sector.sector_code\n"
                    + "LEFT OUTER JOIN g_project ON awc.project_code=g_project.project_code\n"
                    + "LEFT OUTER JOIN g_proximity ON awc.proximity=g_proximity.proximity_id \n"
                    + "LEFT OUTER JOIN g_division ON awc.division_code=g_division.division_code\n"
                    + "LEFT OUTER JOIN g_district ON awc.dist_code=g_district.district_code\n"
                    + "LEFT OUTER JOIN g_subdivision sub ON awc.subdivision_code=sub.subdivision_code\n"
                    + "LEFT OUTER JOIN g_tehsil ON awc.tehsil_code=g_tehsil.tehsil_code\n"
                    + "LEFT OUTER JOIN g_village ON awc.village_code=g_village.village_code\n"
                    + "LEFT OUTER JOIN g_block ON awc.block_code=g_block.block_code \n"
                    + "LEFT OUTER JOIN g_gp ON awc.gp_code=g_gp.gp_code\n"
                    + "LEFT OUTER JOIN g_ls_mast  ON awc.ls_code=g_ls_mast.ls_code \n"
                    + "LEFT OUTER JOIN g_vs_mast ON awc.vs_code=g_vs_mast.vs_code\n"
                    + "LEFT OUTER JOIN g_school_mast ON awc.school_code=g_school_mast.school_code\n"
                    + "LEFT OUTER JOIN frontline_worker worker ON awc.aww_id=worker.worker_id\n"
                    + "LEFT OUTER JOIN frontline_worker helper ON awc.awh_id=helper.worker_id\n"
                    + "LEFT OUTER JOIN frontline_worker asha ON awc.asha_id=asha.worker_id\n"
                    + "LEFT OUTER JOIN g_building_structure struct ON awc.buidling_structure=struct.buidling_structure_id\n"
                    + "LEFT OUTER JOIN g_space ON awc.space=g_space.space_id\n"
                    + "LEFT OUTER JOIN g_toilet_type ON awc.toilet_type=g_toilet_type.toilet_type_id\n"
                    + "LEFT OUTER JOIN g_drink_water_source dws ON awc.water_source=dws.drink_water_source_id\n"
                    + "LEFT OUTER JOIN G_ELECTRICITY_ACCESS electric ON awc.electricty_access=electric.electricty_access_id\n"
                    + "LEFT OUTER JOIN G_AWW2AWC AWWDIST ON AWC.AWW2AWC=AWWDIST.aww2awc_id\n"
                    + "WHERE awc.awc_code=?";
            System.out.println(sql);
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, awcCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                awc.setAwcNameEn(rs.getString("awc_name_english"));
                awc.setAwcNameHn(rs.getString("awc_name_hindi"));
                if (rs.getString("urban_rural") != null && !rs.getString("urban_rural").equals("")) {
                    if (rs.getString("urban_rural").equalsIgnoreCase("U")) {
                        awc.setUrbanRural("Urban");
                    } else {
                        awc.setUrbanRural("Rural");
                    }
                }
                awc.setProximity(rs.getString("proximity"));
                awc.setProximityHindi(rs.getString("proximity_hindi"));
                awc.setSectorNameHindi(rs.getString("sector_name_hindi"));
                awc.setSectorName(rs.getString("sector_name"));
                awc.setProjectName(rs.getString("project_name"));
                awc.setProjectNameHindi(rs.getString("project_name_hindi"));
                awc.setDivisionName(rs.getString("division_name"));
                awc.setDivisionNameHindi(rs.getString("division_name_hindi"));
                awc.setLatitude(rs.getString("latitude"));
                awc.setLongitude(rs.getString("longitude"));
                awc.setDistName(rs.getString("district_name"));
                awc.setDistNameHindi(rs.getString("district_name_hindi"));
                awc.setSubDivisionName(rs.getString("subdivision_name"));
                awc.setSubDivisionHindi(rs.getString("subdivision_name_hindi"));
                awc.setTehsilName(rs.getString("tehsil_name"));
                awc.setTehsilHindi(rs.getString("tehsil_name_hindi"));
                awc.setVillagename(rs.getString("village_name"));
                awc.setVillagenameHindi(rs.getString("village_name_hindi"));
                awc.setBlockName(rs.getString("block_name"));
                awc.setBlocknameHindi(rs.getString("block_name_hindi"));
                awc.setGpName(rs.getString("gp_name"));
                awc.setGpnameHindi(rs.getString("gp_name_hindi"));
                awc.setLsName(rs.getString("ls_name"));
                awc.setLsnameHindi(rs.getString("ls_name_hindi"));
                awc.setVsName(rs.getString("vs_name"));
                awc.setVsnameHindi(rs.getString("vs_name_hindi"));
                awc.setSchoolName(rs.getString("school_name"));
                awc.setSchoolType(rs.getString("school_type"));
                awc.setMentorName(rs.getString("mentor_name"));
                awc.setMentorMobile(rs.getString("mentor_mobile"));
                awc.setAnganwadiWorker(rs.getString("worker_name"));
                awc.setAnganwadiWorkerHindi(rs.getString("worker_name_hindi"));
                awc.setAnganwadiWorkerMobile(rs.getString("workermobile"));
                awc.setAnganwadiHelper(rs.getString("helper_name"));
                awc.setAnganwadiHelperHindi(rs.getString("helper_name_hindi"));
                awc.setAshaWorker(rs.getString("asha_name"));
                awc.setAshaWorkerHindi(rs.getString("asha_name_hindi"));
                awc.setAww2awcDist(rs.getString("aww2awc"));
                awc.setAww2awcDistHindi(rs.getString("aww2awc_hindi"));
                awc.setBuildingStruct(rs.getString("buidling_structure"));
                awc.setBuildingStructHindi(rs.getString("buidling_structure_hindi"));
                if (rs.getString("building_needs_repair") != null && !rs.getString("building_needs_repair").equals("") && rs.getString("building_needs_repair").equalsIgnoreCase("Y")) {
                    awc.setIsRepair("Y");
                } else {
                    awc.setIsRepair("N");
                }
                awc.setSpace(rs.getString("space"));
                awc.setSpaceHindi(rs.getString("space_hindi"));
                if (rs.getString("if_kitchen") != null && !rs.getString("if_kitchen").equals("") && rs.getString("if_kitchen").equalsIgnoreCase("Y")) {
                    awc.setIfKitchen("Y");
                } else {
                    awc.setIfKitchen("N");
                }
                if (rs.getString("if_store") != null && !rs.getString("if_store").equals("") && rs.getString("if_store").equalsIgnoreCase("Y")) {
                    awc.setIfStorageSpace("Y");
                } else {
                    awc.setIfStorageSpace("N");
                }
                if (rs.getString("if_toilet") != null && !rs.getString("if_toilet").equals("") && rs.getString("if_toilet").equalsIgnoreCase("Y")) {
                    awc.setIfToilet("Y");
                } else {
                    awc.setIfToilet("N");
                }
                if (rs.getString("if_toilet_functional") != null && !rs.getString("if_toilet_functional").equals("") && rs.getString("if_toilet_functional").equalsIgnoreCase("Y")) {
                    awc.setIfToiletFunc("Y");
                } else {
                    awc.setIfToiletFunc("N");
                }
                awc.setToiletType(rs.getString("toilet_type"));
                awc.setToiletTypeHindi(rs.getString("toilet_type_hindi"));
                if (rs.getString("if_exclussive_toilet") != null && !rs.getString("if_exclussive_toilet").equals("") && rs.getString("if_exclussive_toilet").equalsIgnoreCase("Y")) {
                    awc.setIfExclusiveToilet("Y");
                } else {
                    awc.setIfExclusiveToilet("N");
                }
                awc.setIfWaterAvail(rs.getString("if_drink_water"));
                awc.setDrinkWaterSrc(rs.getString("drink_water_source"));
                awc.setDrinkWaterSrcHindi(rs.getString("drink_water_source_hindi"));
                awc.setElectAccess(rs.getString("electricty_access"));
                awc.setElectAccessHindi(rs.getString("electricty_access_hindi"));
                awc.setEnrolNo(rs.getString("ece_enrolment"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return awc;
    }

    @Override
    public void addAwcData(AwcForm awc
    ) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("INSERT INTO awc_master(AWC_Name_English,AWC_Name_Hindi,AWC_Type,Urban_Rural,Proximity,Sanction_Year,ADDRESS,Latitude,Longitude,Dist_Code,Project_Code,Sector_Code,Division_Code,Subdivision_Code,Tehsil_Code,Village_Code,Block_Code,"
                    + "GP_Code,ULB_Type,ULB_Code,LS_Code,VS_Code,School_Code,School_Type,AWW_Id,AWH_Id,ASHA_Id,AWW_Mobile,AWW2AWC,Buidling_Owner,"
                    + "Buidling_Structure,Needs_Repair,Disable_Access,Space,If_Kitchen,If_Store,If_Toilet,If_Toilet_Functional,Toilet_Type,If_Water_in_Toilet,If_Exclussive_Toilet,"
                    + "If_Drink_Water,Water_Source,Electricty_Access,ECE_Enrolment,PHC_Code,SubCenter_Code,Mentor_Name,Mentor_Mobile,Zilaparishad_Code,Is_Tagged2School,AWC_Code) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, awc.getAwcNameEn());
            ps.setString(2, awc.getAwcNameHn());
            ps.setString(3, awc.getAwcType());
            ps.setString(4, awc.getUrbanRural());
            ps.setString(5, awc.getProximity());
            ps.setInt(6, awc.getSanctionYear());
            ps.setString(7, awc.getAddress());
            if (awc.getLatitude() != null && !awc.getLatitude().equals("")) {
                ps.setString(8, "" + Double.parseDouble(awc.getLatitude()));
            } else {
                ps.setString(8, null);
            }
            if (awc.getLongitude() != null && !awc.getLongitude().equals("")) {
                ps.setString(9, "" + Double.parseDouble(awc.getLongitude()));
            } else {
                ps.setString(9, null);
            }
            ps.setString(10, awc.getDistName());

            ps.setString(11, awc.getProjectName());

            ps.setString(12, awc.getSectorName());

            ps.setString(13, awc.getDivisionName());

            ps.setString(14, awc.getSubDivisionName());

            ps.setString(15, awc.getTehsilName());

            ps.setString(16, awc.getVillName());

            ps.setString(17, awc.getBlockName());

            ps.setString(18, awc.getGpName());

            ps.setString(19, awc.getUlbType());
            ps.setString(20, awc.getUlbName());

            ps.setString(21, awc.getLsName());

            ps.setString(22, awc.getVsName());

            ps.setString(23, awc.getSchoolCode());
            ps.setString(24, awc.getSchoolType());
            ps.setString(25, awc.getAnganwadiWorker());
            ps.setString(26, awc.getAnganwadiHelper());
            ps.setString(27, awc.getAshaWorker());
            ps.setString(28, awc.getAwwMob());
            ps.setString(29, awc.getAww2awcDist());
            ps.setString(30, awc.getOwnerName());
            ps.setString(31, awc.getBuildingStruct());
            ps.setString(32, awc.getIsRepair());
            ps.setString(33, awc.getIsdisableAccess());
            ps.setString(34, awc.getSpace());
            ps.setString(35, awc.getIfKitchen());
            ps.setString(36, awc.getIfStorageSpace());
            ps.setString(37, awc.getIfToilet());
            ps.setString(38, awc.getIfToiletFunc());
            ps.setString(39, awc.getToiletType());
            ps.setString(40, awc.getIfWaterAvail());
            ps.setString(41, awc.getIfExclusiveToilet());
            ps.setString(42, awc.getIfdrinkWaterAvail());
            ps.setString(43, awc.getDrinkWaterSrc());
            ps.setString(44, awc.getElectAccess());
            if (awc.getEnrolNo() != null && !awc.getEnrolNo().equals("")) {
                ps.setString(45, "" + Double.parseDouble(awc.getEnrolNo()));
            } else {
                ps.setString(45, null);
            }
            ps.setString(46, awc.getPhc());
            ps.setString(47, awc.getSubCenter());
            ps.setString(48, awc.getMentorName());
            ps.setString(49, awc.getMentorMobile());
            ps.setString(50, awc.getIsTagged2school());
            ps.setString(51, awc.getZilaparishad());
            ps.setString(52, awc.getAwcCode());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            DataBaseFunctions.closeSqlObjects(ps);
            DataBaseFunctions.closeSqlObjects(con);
        }

    }

    public ArrayList getAwcTypeList(Locale locale) {
        SelectOption so = null;
        ArrayList awcTypeList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_AWC_TYPE");
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("AWC_Type_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("AWC_Type_Hindi"));
                } else {
                    so.setLabel(rs.getString("AWC_Type"));
                }
                awcTypeList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }

        return awcTypeList;
    }

    public ArrayList getDistrictName(Locale locale) {
        SelectOption so = null;
        ArrayList distList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_DISTRICT");
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("DISTRICT_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("district_name_hindi"));
                } else {
                    so.setLabel(rs.getString("DISTRICT_NAME"));
                }
                distList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return distList;
    }

    public ArrayList getProjectName(String distCode, Locale locale) {
        SelectOption so = null;
        ArrayList proList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            // System.out.println("*******distCode************"+distCode);
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_PROJECT WHERE DISTRICT_CODE=?");
            pst.setString(1, distCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("PROJECT_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Project_Name_Hindi"));
                } else {
                    so.setLabel(rs.getString("PROJECT_NAME"));
                }
                proList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return proList;
    }

    public ArrayList getSectorName(String projectCode, Locale locale) {
        SelectOption so = null;
        ArrayList sectorList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_SECTOR WHERE PROJECT_CODE=?");
            pst.setString(1, projectCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("SECTOR_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("SECTOR_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("Sector_Name"));
                }

                sectorList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return sectorList;
    }

    public ArrayList getDivisionName(Locale locale) {
        SelectOption so = null;
        ArrayList divisionList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_DIVISION");
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("DIVISION_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Division_Name_Hindi"));
                } else {
                    so.setLabel(rs.getString("Division_Name"));
                }

                divisionList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return divisionList;
    }

    public ArrayList getSubDivisionName(String distCode, Locale locale) {
        SelectOption so = null;
        ArrayList subdivList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_SUBDIVISION WHERE DISTRICT_CODE=?");
            pst.setString(1, distCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("SUBDIVISION_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Subdivision_Name_Hindi"));
                } else {
                    so.setLabel(rs.getString("Subdivision_Name"));
                }
                subdivList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return subdivList;
    }

    public ArrayList getTehsilName(String subDivcode, String distCode, Locale locale) {
        SelectOption so = null;
        ArrayList tehsilList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_TEHSIL WHERE DISTRICT_CODE=? AND SUBDIVISION_CODE=?");
            pst.setString(1, distCode);
            pst.setString(2, subDivcode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("TEHSIL_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("TEHSIL_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("TEHSIL_NAME"));
                }
                tehsilList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return tehsilList;
    }

    public ArrayList getVillageName(String tehsilCode, String subdivCode, String distCode, Locale locale) {
        SelectOption so = null;
        ArrayList villList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_VILLAGE WHERE DISTRICT_CODE=? AND SUBDIVISION_CODE=? AND TEHSIL_CODE=?");
            pst.setString(1, distCode);
            pst.setString(2, subdivCode);
            pst.setString(3, tehsilCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("VILLAGE_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("VILLAGE_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("VILLAGE_NAME"));
                }
                villList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return villList;
    }

    public ArrayList getBlockName(String distCode, Locale locale) {
        SelectOption so = null;
        ArrayList blockList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_BLOCK WHERE DISTRICT_CODE=?");
            pst.setString(1, distCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("BLOCK_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("BLOCK_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("BLOCK_NAME"));
                }
                blockList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return blockList;
    }

    public ArrayList getGpName(String distCode, String blockCode, Locale locale) {
        SelectOption so = null;
        ArrayList gpList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_GP WHERE DISTRICT_CODE=? AND BLOCK_CODE=?");
            pst.setString(1, distCode);
            pst.setString(2, blockCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("GP_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("GP_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("GP_NAME"));
                }
                gpList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return gpList;
    }

    public ArrayList getULBType(Locale locale) {
        SelectOption so = null;
        ArrayList ulbTypeList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_ULB_TYPE");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("ULB_Type_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("ULB_TYPE_HINDI"));
                } else {
                    so.setLabel(rs.getString("ULB_TYPE"));
                }
                ulbTypeList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return ulbTypeList;
    }

    public ArrayList getULBName(String distCode, Locale locale) {
        SelectOption so = null;
        ArrayList ulbNameList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_ULB_MAST WHERE DISTRICT_CODE=?");
            pst.setString(1, distCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();

                so.setValue(rs.getString("ULB_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("ULB_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("ULB_NAME"));
                }
                ulbNameList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return ulbNameList;
    }

    public ArrayList getLSName(Locale locale) {
        SelectOption so = null;
        ArrayList lsList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_LS_MAST");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("LS_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("LS_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("LS_NAME"));
                }
                lsList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return lsList;
    }

    public ArrayList getVSName(Locale locale) {
        SelectOption so = null;
        ArrayList vsList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_VS_MAST");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("VS_CODE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("VS_NAME_HINDI"));
                } else {
                    so.setLabel(rs.getString("VS_NAME"));
                }
                vsList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return vsList;
    }

    public ArrayList getProximity(Locale locale) {
        SelectOption so = null;
        ArrayList proximityList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_PROXIMITY");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Proximity_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Proximity_Hindi"));
                } else {
                    so.setLabel(rs.getString("Proximity"));
                }
                proximityList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return proximityList;

    }

    public ArrayList getAnganwadiWorker() {
        SelectOption so = null;
        ArrayList workerList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT Worker_Id,Worker_Name FROM FRONTLINE_WORKER WHERE Worker_Desig='1'");
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Worker_Id"));
                so.setLabel(rs.getString("Worker_Name"));
                workerList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return workerList;
    }

    public ArrayList getAnganwadiHelper() {
        SelectOption so = null;
        ArrayList workerList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT Worker_Id,Worker_Name FROM FRONTLINE_WORKER WHERE Worker_Desig='3'");
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Worker_Id"));
                so.setLabel(rs.getString("Worker_Name"));
                workerList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return workerList;
    }

    public ArrayList getAshaWorker() {
        SelectOption so = null;
        ArrayList workerList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT Worker_Id,Worker_Name FROM FRONTLINE_WORKER WHERE Worker_Desig='4'");
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Worker_Id"));
                so.setLabel(rs.getString("Worker_Name"));
                workerList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return workerList;
    }

    public ArrayList getDistance(Locale locale) {
        SelectOption so = null;
        ArrayList workerList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM g_aww2awc");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("AWW2AWC_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("AWW2AWC_HINDI"));
                } else {
                    so.setLabel(rs.getString("AWW2AWC"));
                }
                workerList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return workerList;
    }

    public ArrayList getBuilderOwner(Locale locale) {
        SelectOption so = null;
        ArrayList builderOwnerList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM g_building_owner");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Buidling_Owner_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Buidling_Owner_Hindi"));
                } else {
                    so.setLabel(rs.getString("Buidling_Owner"));
                }
                builderOwnerList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return builderOwnerList;
    }

    public ArrayList getBuildingStructure(Locale locale) {
        SelectOption so = null;
        ArrayList buildStructList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM g_building_structure");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Buidling_Structure_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Buidling_Structure_Hindi"));
                } else {
                    so.setLabel(rs.getString("Buidling_Structure"));
                }
                buildStructList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return buildStructList;
    }

    public ArrayList getSpace(Locale locale) {
        SelectOption so = null;
        ArrayList spaceList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_SPACE");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Space_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("SPACE_Hindi"));
                } else {
                    so.setLabel(rs.getString("SPACE"));
                }
                spaceList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return spaceList;
    }

    public ArrayList getToiletType(Locale locale) {
        SelectOption so = null;
        ArrayList toiletTypeLiist = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_TOILET_TYPE");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Toilet_Type_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("TOILET_TYPE_Hindi"));
                } else {
                    so.setLabel(rs.getString("TOILET_TYPE"));
                }
                toiletTypeLiist.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return toiletTypeLiist;
    }

    public ArrayList getDrinkingWaterSource(Locale locale) {
        SelectOption so = null;
        ArrayList drinkSrcLiist = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM g_drink_water_source");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Drink_Water_Source_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Drink_Water_Source_Hindi"));
                } else {
                    so.setLabel(rs.getString("Drink_Water_Source"));
                }
                drinkSrcLiist.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return drinkSrcLiist;
    }

    public ArrayList getAccessOfElectricity(Locale locale) {
        SelectOption so = null;
        ArrayList electAccessLiist = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM g_electricity_access");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("Electricty_Access_Id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("Electricty_Access_Hindi"));
                } else {
                    so.setLabel(rs.getString("Electricty_Access"));
                }
                electAccessLiist.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return electAccessLiist;
    }

    public ArrayList getSchoolName(String distCode, String blockCode, Locale locale) {
        SelectOption so = null;
        ArrayList gpList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM g_school_mast WHERE DISTRICT_CODE=? AND BLOCK_CODE=?");
            pst.setString(1, distCode);
            pst.setString(2, blockCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("School_Code"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("School_Name_Hindi"));
                } else {
                    so.setLabel(rs.getString("School_Name"));
                }
                gpList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return gpList;
    }

    public ArrayList getSchoolType(Locale locale) {
        SelectOption so = null;
        ArrayList schooTypelList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT * FROM G_SCHOOL_TYPE");

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("SCHOOL_TYPE"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("SCHOOL_TYPE_Hindi"));
                } else {
                    so.setLabel(rs.getString("SCHOOL_TYPE"));
                }
                schooTypelList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return schooTypelList;
    }

    public AwcForm getAwcData(String awcCode) {
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        AwcForm awc = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT AWC_Name_English,AWC_Name_Hindi,AWC_Type,Urban_Rural,Sanction_Year,Proximity,ADDRESS,CAST(Latitude AS char(15))Latitude,CAST(Longitude AS char(15))Longitude,Dist_Code,Project_Code,Sector_Code,Division_Code,Subdivision_Code,Tehsil_Code,Village_Code,Block_Code,"
                    + "GP_Code,ULB_Type,ULB_Code,LS_Code,VS_Code,School_Code,School_Type,AWW_Id,AWH_Id,ASHA_Id,AWW_Mobile,AWW2AWC,Buidling_Owner,"
                    + "Buidling_Structure,Needs_Repair,Disable_Access,Space,If_Kitchen,If_Store,If_Toilet,If_Toilet_Functional,Toilet_Type,If_Water_in_Toilet,If_Exclussive_Toilet,"
                    + "If_Drink_Water,Water_Source,Electricty_Access,CAST(ECE_Enrolment AS char(15))ECE_Enrolment,PHC_Code,SubCenter_Code,Mentor_Name,Mentor_Mobile,Zilaparishad_Code,Is_Tagged2School FROM awc_master where AWC_Code=?");

            pst.setString(1, awcCode);
            rs = pst.executeQuery();
            if (rs.next()) {
                awc = new AwcForm();
                awc.setAwcCode(awcCode);
                awc.setAwcNameEn(rs.getString("AWC_Name_English"));
                awc.setAwcNameHn(StringEscapeUtils.unescapeJava(rs.getString("AWC_Name_Hindi")));
                awc.setAwcType(rs.getString("AWC_Type"));
                awc.setUrbanRural(rs.getString("Urban_Rural"));
                awc.setSanctionYear(rs.getInt("Sanction_Year"));
                awc.setProximity(rs.getString("proximity"));
                awc.setAddress(rs.getString("ADDRESS"));
                if (rs.getString("Latitude") != null && !rs.getString("Latitude").equals("")) {
                    awc.setLatitude("" + Double.parseDouble(rs.getString("Latitude")));
                } else {
                    awc.setLatitude(null);
                }
                if (rs.getString("Longitude") != null && !rs.getString("Longitude").equals("")) {
                    awc.setLongitude("" + Double.parseDouble(rs.getString("Longitude")));

                } else {
                    awc.setLongitude(null);
                }
                awc.setDistName(rs.getString("Dist_Code"));
                awc.setHidDistName(rs.getString("Dist_Code"));
                awc.setHidProName(rs.getString("Project_Code"));
                awc.setProjectName(rs.getString("Project_Code"));
                awc.setSectorName(rs.getString("Sector_Code"));
                awc.setHidSectorName(rs.getString("Sector_Code"));
                awc.setDivisionName(rs.getString("Division_Code"));
                awc.setSubDivisionName(rs.getString("Subdivision_Code"));
                awc.setTehsilName(rs.getString("Tehsil_Code"));
                awc.setVillName(rs.getString("Village_Code"));

                awc.setBlockName(rs.getString("Block_Code"));
                awc.setGpName(rs.getString("GP_Code"));

                awc.setUlbType(rs.getString("ULB_Type"));
                awc.setUlbName(rs.getString("ULB_Code"));
                awc.setLsName(rs.getString("LS_Code"));
                awc.setVsName(rs.getString("VS_Code"));
                awc.setSchoolCode(rs.getString("School_Code"));
                awc.setSchoolName(getSchoolName(rs.getString("School_Code")));
                awc.setSchoolType(rs.getString("School_Type"));
                awc.setAnganwadiWorker(rs.getString("AWW_Id"));
                awc.setAnganwadiHelper(rs.getString("AWH_Id"));
                awc.setAshaWorker(rs.getString("ASHA_Id"));

                awc.setOwnerName(rs.getString("Buidling_Owner"));
                awc.setBuildingStruct(rs.getString("Buidling_Structure"));
                awc.setIsRepair(rs.getString("Needs_Repair"));

                awc.setAwwMob(rs.getString("AWW_Mobile"));
                awc.setAww2awcDist(rs.getString("AWW2AWC"));
                awc.setIsdisableAccess(rs.getString("Disable_Access"));
                awc.setSpace(rs.getString("Space"));
                awc.setIfKitchen(rs.getString("If_Kitchen"));
                awc.setIfStorageSpace(rs.getString("If_Store"));
                awc.setIfToilet(rs.getString("If_Toilet"));
                awc.setIfToiletFunc(rs.getString("If_Toilet_Functional"));
                awc.setToiletType(rs.getString("Toilet_Type"));
                awc.setIfWaterAvail(rs.getString("If_Water_in_Toilet"));
                awc.setIfExclusiveToilet(rs.getString("If_Exclussive_Toilet"));
                awc.setIfdrinkWaterAvail(rs.getString("If_Drink_Water"));
                awc.setDrinkWaterSrc(rs.getString("Water_Source"));
                awc.setElectAccess(rs.getString("Electricty_Access"));
                awc.setEnrolNo(rs.getString("ECE_Enrolment"));
                awc.setPhc(rs.getString("PHC_Code"));
                awc.setSubCenter(rs.getString("SubCenter_Code"));
                awc.setMentorName(rs.getString("Mentor_Name"));
                awc.setMentorMobile(rs.getString("Mentor_Mobile"));
                awc.setZilaparishad(rs.getString("Zilaparishad_Code"));
                awc.setIsTagged2school(rs.getString("Is_Tagged2School"));

            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return awc;
    }

    /**
     *
     * @param awc
     * @return
     */
    @Override
    public ListPageObject getAwcList(AwcForm awc) {
        List li = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        ListPageObject lo = new ListPageObject();
        String searchString = "";
        String sql = "";
        try {
            con = this.dataSource.getConnection();

            if (awc.getDistrictCode().equalsIgnoreCase("ALL")) {
                if (awc.getProjectCode() != null && !awc.getProjectCode().equals("") && awc.getProjectCode().equalsIgnoreCase("ALL")) {
                    if (awc.getSectorCode() != null && !awc.getSectorCode().equals("") && awc.getSectorCode().equalsIgnoreCase("ALL")) {
                        // IF USER SELECTED ALL DROPDOWN BOX AS ALL
                        searchString = "";
                    } else {
                        searchString = " AWC_MASTER.sector_code='" + awc.getSectorCode() + "'";
                    }
                } else {
                    if (awc.getSectorCode() != null && !awc.getSectorCode().equals("") && awc.getSectorCode().equalsIgnoreCase("ALL")) {
                        // IF USER SELECTED ALL DROPDOWN BOX AS ALL
                        searchString = " AWC_MASTER.project_code ='" + awc.getProjectCode() + "'";
                    } else {
                        searchString = " AWC_MASTER.project_code ='" + awc.getProjectCode() + "' AND " + "AWC_MASTER.sector_code='" + awc.getSectorCode() + "'";
                    }
                }
            } else {
                if (awc.getProjectCode() != null && !awc.getProjectCode().equals("") && awc.getProjectCode().equalsIgnoreCase("ALL")) {
                    if (awc.getSectorCode() != null && !awc.getSectorCode().equals("") && awc.getSectorCode().equalsIgnoreCase("ALL")) {
                        // IF USER SELECTED ALL DROPDOWN BOX AS ALL
                        searchString = "";
                    } else {
                        searchString = " AWC_MASTER.dist_code='" + awc.getDistrictCode() + "' AND AWC_MASTER.sector_code='" + awc.getSectorCode() + "'";
                    }
                } else {
                    if (awc.getSectorCode() != null && !awc.getSectorCode().equals("") && awc.getSectorCode().equalsIgnoreCase("ALL")) {
                        // IF USER SELECTED ALL DROPDOWN BOX AS ALL
                        searchString = " AWC_MASTER.dist_code='" + awc.getDistrictCode() + "' AND AWC_MASTER.project_code ='" + awc.getProjectCode() + "'";
                    } else {
                        searchString = " AWC_MASTER.dist_code='" + awc.getDistrictCode() + "' AND AWC_MASTER.project_code ='" + awc.getProjectCode() + "' AND " + "AWC_MASTER.sector_code='" + awc.getSectorCode() + "'";
                    }
                }
            }
            if (searchString != null && !searchString.equals("")) {
                sql = "SELECT AWC_Code,AWC_Name_English,Urban_Rural,ADDRESS,g_project.project_name, \n"
                        + "g_sector.sector_name,g_awc_type.awc_type FROM AWC_MASTER \n"
                        + "LEFT OUTER JOIN g_awc_type on AWC_MASTER.awc_type=g_awc_type.AWC_Type_id \n"
                        + "LEFT OUTER JOIN g_project on  g_project.project_code=AWC_MASTER.project_code \n"
                        + "LEFT OUTER JOIN g_sector on g_sector.sector_code=AWC_MASTER.sector_code where " + searchString + " ORDER BY awc_name_english";
            } else {
                sql = "SELECT AWC_Code,AWC_Name_English,Urban_Rural,ADDRESS,g_project.project_name, \n"
                        + "g_sector.sector_name,g_awc_type.awc_type FROM AWC_MASTER \n"
                        + "LEFT OUTER JOIN g_awc_type on AWC_MASTER.awc_type=g_awc_type.AWC_Type_id \n"
                        + "LEFT OUTER JOIN g_project on  g_project.project_code=AWC_MASTER.project_code \n"
                        + "LEFT OUTER JOIN g_sector on g_sector.sector_code=AWC_MASTER.sector_code ORDER BY awc_name_english";
            }
            System.out.println("sql==" + sql);
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            AwcForm aw = null;
            while (rs.next()) {
                aw = new AwcForm();

                aw.setAwcCode(rs.getString("AWC_Code"));
                aw.setAwcNameEn(rs.getString("AWC_Name_English"));
                aw.setAwcType(rs.getString("AWC_Type"));
                if (rs.getString("Urban_Rural") != null && rs.getString("Urban_Rural").equalsIgnoreCase("R")) {
                    aw.setUrbanRural("RURAL");
                }
                if (rs.getString("Urban_Rural") != null && rs.getString("Urban_Rural").equalsIgnoreCase("U")) {
                    aw.setUrbanRural("URBAN");
                }

                aw.setAddress(rs.getString("ADDRESS"));
                aw.setProjectName(rs.getString("project_name"));
                aw.setSectorName(rs.getString("sector_name"));
                li.add(aw);
            }

            lo.setDtaList(li);

            DataBaseFunctions.closeSqlObjects(rs);
            if (searchString != null && !searchString.equals("")) {
                sql = "SELECT count(AWC_MASTER.awc_type) cnt,AWC_MASTER.awc_type,count(is_tagged2school) schoolcnt,count(Sponsor_Id) adoptedcnt FROM AWC_MASTER \n"
                        + "LEFT OUTER JOIN g_awc_type on AWC_MASTER.awc_type=g_awc_type.AWC_Type_id \n"
                        + "LEFT OUTER JOIN g_project on  g_project.project_code=AWC_MASTER.project_code \n"
                        + "LEFT OUTER JOIN g_sector on g_sector.sector_code=AWC_MASTER.sector_code where  " + searchString + " group by AWC_MASTER.awc_type";
            } else {
                sql = "SELECT count(AWC_MASTER.awc_type) cnt,AWC_MASTER.awc_type,count(is_tagged2school) schoolcnt,count(Sponsor_Id) adoptedcnt FROM AWC_MASTER \n"
                        + "LEFT OUTER JOIN g_awc_type on AWC_MASTER.awc_type=g_awc_type.AWC_Type_id \n"
                        + "LEFT OUTER JOIN g_project on  g_project.project_code=AWC_MASTER.project_code \n"
                        + "LEFT OUTER JOIN g_sector on g_sector.sector_code=AWC_MASTER.sector_code group by AWC_MASTER.awc_type";
            }
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            int taggedschoolNo = 0;
            int adoptedNo = 0;
            while (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    if (rs.getString("awc_type").equals("1")) {
                        lo.setBox1(rs.getString("cnt"));
                    } else if (rs.getString("awc_type").equals("2")) {
                        lo.setBox2(rs.getString("cnt"));
                    }
                    taggedschoolNo = taggedschoolNo + rs.getInt("schoolcnt");
                    adoptedNo = adoptedNo+rs.getInt("adoptedcnt");
                    
                }
            }
            if (taggedschoolNo > 0) {
                lo.setBox3(taggedschoolNo + "");
            }
            if (adoptedNo > 0) {
                lo.setBox4(adoptedNo+"");
            }
            
        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return lo;
    }

    public void updateAwcData(AwcForm awc) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("UPDATE  awc_master set awc_name_english=?,awc_name_hindi=?,awc_type=?,urban_rural=?,proximity=?,sanction_year=?,address=?,latitude=?,longitude=?,Dist_Code=?,Project_Code=?,"
                    + "Sector_Code=?,Division_Code=?,Subdivision_Code=?,Tehsil_Code=?,"
                    + "Village_Code=?,Block_Code=?,GP_Code=?,ULB_Type=?,ULB_Code=?,LS_Code=?,VS_Code=?,School_Code=?,School_Type=?,"
                    + "AWW_Id=?,AWH_Id=?,ASHA_Id=?,AWW_Mobile=?,AWW2AWC=?,Buidling_Owner=?,Buidling_Structure=?,Building_Needs_Repair=?,Disable_Access=?,"
                    + "Space=?,If_Kitchen=?,If_Store=?,If_Toilet=?,If_Toilet_Functional=?,Toilet_Type=?,If_Water_in_Toilet=?,If_Exclussive_Toilet=?,If_Drink_Water=?,"
                    + "Water_Source=?,Electricty_Access=?,ECE_Enrolment=?,PHC_Code=?,SubCenter_Code=?,Mentor_Name=?,Mentor_Mobile=?,Zilaparishad_Code=?,Is_Tagged2School=? where awc_code=?");
            ps.setString(1, awc.getAwcNameEn());
            ps.setString(2, awc.getAwcNameHn());
            ps.setString(3, awc.getAwcType());
            ps.setString(4, awc.getUrbanRural());
            ps.setString(5, awc.getProximity());
            ps.setInt(6, awc.getSanctionYear());
            ps.setString(7, awc.getAddress());
            if (awc.getLatitude() != null && !awc.getLatitude().equals("")) {
                ps.setString(8, "" + Double.parseDouble(awc.getLatitude()));
            } else {
                ps.setString(8, null);
            }
            if (awc.getLongitude() != null && !awc.getLongitude().equals("")) {
                ps.setString(9, "" + Double.parseDouble(awc.getLongitude()));
            } else {
                ps.setString(9, null);
            }
            ps.setString(10, awc.getDistName());
            ps.setString(11, awc.getProjectName());
            ps.setString(12, awc.getSectorName());
            ps.setString(13, awc.getDivisionName());
            ps.setString(14, awc.getSubDivisionName());
            ps.setString(15, awc.getTehsilName());
            ps.setString(16, awc.getVillName());
            ps.setString(17, awc.getBlockName());
            ps.setString(18, awc.getGpName());
            ps.setString(19, awc.getUlbType());
            ps.setString(20, awc.getUlbName());
            ps.setString(21, awc.getLsName());
            ps.setString(22, awc.getVsName());
            ps.setString(23, awc.getSchoolCode());
            ps.setString(24, awc.getSchoolType());
            ps.setString(25, awc.getAnganwadiWorker());
            ps.setString(26, awc.getAnganwadiHelper());
            ps.setString(27, awc.getAshaWorker());
            ps.setString(28, awc.getAwwMob());
            ps.setString(29, awc.getAww2awcDist());
            ps.setString(30, awc.getOwnerName());
            ps.setString(31, awc.getBuildingStruct());
            ps.setString(32, awc.getIsRepair());
            ps.setString(33, awc.getIsdisableAccess());
            ps.setString(34, awc.getSpace());
            ps.setString(35, awc.getIfKitchen());
            ps.setString(36, awc.getIfStorageSpace());
            ps.setString(37, awc.getIfToilet());
            ps.setString(38, awc.getIfToiletFunc());
            ps.setString(39, awc.getToiletType());
            ps.setString(40, awc.getIfWaterAvail());
            ps.setString(41, awc.getIfExclusiveToilet());
            ps.setString(42, awc.getIfdrinkWaterAvail());
            ps.setString(43, awc.getDrinkWaterSrc());
            ps.setString(44, awc.getElectAccess());
            if (awc.getEnrolNo() != null && !awc.getEnrolNo().equals("")) {
                ps.setString(45, "" + Double.parseDouble(awc.getEnrolNo()));
            } else {
                ps.setString(45, null);
            }
            ps.setString(46, awc.getPhc());
            ps.setString(47, awc.getSubCenter());
            ps.setString(48, awc.getMentorName());
            ps.setString(49, awc.getMentorMobile());
            ps.setString(50, awc.getZilaparishad());
            ps.setString(51, awc.getIsTagged2school());
            ps.setString(52, awc.getAwcCode());
            ps.execute();

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(ps);
            DataBaseFunctions.closeSqlObjects(con);
        }

    }

    @Override
    public String getLocalizedText(String key, Locale locale) {
        try {

            System.out.println("locale." + locale.getLanguage());
            System.out.println("locale." + locale.getDisplayCountry());

        } catch (Exception e) {
            return "LOCALIZATION FAILED: " + e.toString();
        }
        return key;
    }

    @Override
    public String getAwcName(String awcId) {
        String awcname = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT awc_name_english,g_awc_type.awc_type from awc_master "
                    + "left outer join g_awc_type on awc_master.awc_type=g_awc_type.awc_type_id where awc_code=?");
            ps.setString(1, awcId);
            rs = ps.executeQuery();
            if (rs.next()) {
                awcname = rs.getString("awc_name_english") + "@" + rs.getString("awc_type");
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
        return awcname;
    }

    public ArrayList getPHCName(String distCode, String blockCode, String ulbCode, Locale locale) {
        SelectOption so = null;
        ArrayList phcList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT PHC_Code,PHC_Name,PHC_Name_Hind FROM g_phc_mast WHERE DISTRICT_CODE=?");
            pst.setString(1, distCode);
            //pst.setString(2, blockCode);
            //pst.setString(3, ulbCode);

            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("PHC_Code"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("PHC_Name_Hind"));
                } else {
                    so.setLabel(rs.getString("PHC_Name"));
                }
                phcList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return phcList;
    }

    public ArrayList getSubCenterName(String phcCode, Locale locale) {
        SelectOption so = null;
        ArrayList subcenterList = new ArrayList();
        PreparedStatement pst = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("SELECT SubCenter_Code,SubCenter_Name,SubCenter_Name_Hindi FROM g_subcenter WHERE PHC_Code=?");
            pst.setString(1, phcCode);
            rs = pst.executeQuery();
            while (rs.next()) {
                so = new SelectOption();
                so.setValue(rs.getString("SubCenter_Code"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    so.setLabel(rs.getString("SubCenter_Name_Hindi"));
                } else {
                    so.setLabel(rs.getString("SubCenter_Name"));
                }
                subcenterList.add(so);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return subcenterList;
    }

    public void deleteAwcData(String awcCode) {
        PreparedStatement pst = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            pst = con.prepareStatement("delete from awc_master where awc_code=?");
            pst.setString(1, awcCode);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(con);
        }
    }
    public String getSchoolName(String schoolCode){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList nandgharList = null;
        String awcName = null;
        try {
            con = dataSource.getConnection();
            System.out.println("select * from g_school_mast where School_Code='" + schoolCode + "'");
            // pst=con.prepareStatement("select * from awc_master where awc_code=?");
            st = con.createStatement();
            rs = st.executeQuery("select * from g_school_mast where School_Code='" + schoolCode + "'");

            // rs=pst.executeQuery();
            if (rs.next()) {
                awcName = rs.getString("School_Name");
                System.out.println("=========School_Name name=====" + rs.getString("School_Name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(st);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return awcName;
    }

}
