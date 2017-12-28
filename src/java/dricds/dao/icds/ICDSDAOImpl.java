/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.icds;

import dricds.common.CommonFunctions;
import dricds.common.DataBaseFunctions;
import dricds.common.ListPageObject;
import dricds.controller.icds.projects.ICDSProjectsController;
import dricds.model.icds.ICDSModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author surendra
 */
public class ICDSDAOImpl implements ICDSDAO {
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;
    
    private PlatformTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplateObject;
    private SimpleJdbcInsert insertIncumbencyChart;
    private static final String TABLE_INCUMBENCY_CHART = "incumbency_chart";
    private static final String PK_INCUMBENCY_CHART = "Incumbency_Id";
    
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        this.insertIncumbencyChart = new SimpleJdbcInsert(this.jdbcTemplateObject).withTableName(TABLE_INCUMBENCY_CHART).usingGeneratedKeyColumns(PK_INCUMBENCY_CHART);
    }
    public static Log LOG = LogFactory.getLog(ICDSProjectsController.class);
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    
    @Override
    public ListPageObject getICDSProjectList(ICDSModel icdsm, Locale locale) {
        List li = new ArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sql = "";
        String searchString = "";
        ListPageObject lo = new ListPageObject();
        try {
            con = this.dataSource.getConnection();
            LOG.info("DistCode" + icdsm.getDistrictCode() + "Menu Level Code" + icdsm.getLevel());
            if (icdsm.getDistrictCode() != null) {
                if (icdsm.getLevel().equalsIgnoreCase("DIST")) {
                    sql = "SELECT office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office FROM G_OFFICE WHERE office_level='DIST'AND is_active='Y' ";
                } else if (icdsm.getLevel().equalsIgnoreCase("PROJECT")) {
                    if (!icdsm.getDistrictCode().equalsIgnoreCase("ALL") && !icdsm.getDistrictCode().isEmpty()) {
                        sql = "SELECT office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office FROM G_OFFICE WHERE office_level='PROJECT'AND is_active='Y' AND reporting_office in ('" + icdsm.getDistrictCode() + "')";
                    } else {
                        sql = "SELECT office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office FROM G_OFFICE WHERE office_level='PROJECT'AND is_active='Y' ";
                        
                    }
                }
                if (icdsm.getLevel().equalsIgnoreCase("SECTOR")) {
                    LOG.info("icdsm.getProjectCode() >>> " + icdsm.getSelectedProjectId());
//                        if(icdsm.getDistrictCode().equalsIgnoreCase("ALL")){
//                            sql = "select office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office from g_office where "
//                                    + "office_level='SECTOR' and is_active='Y' "
//                                    + "and reporting_office in  ( select office_id from g_office where office_level='PROJECT' and reporting_office in (select office_id from g_office where office_level='DIST') )";
//                        }
//                        else 
                    if (icdsm.getSelectedProjectId() != null && !icdsm.getSelectedProjectId().isEmpty()) {
                        sql = "select office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office from g_office where "
                                + "office_level='SECTOR' and is_active='Y' "
                                + "and reporting_office in  ('" + icdsm.getSelectedProjectId() + "')";
                        
                    }// else if (icdsm.getSelectedProjectId() != null && !icdsm.getSelectedProjectId().equalsIgnoreCase("") && !icdsm.getSelectedProjectId().equalsIgnoreCase("ALL")) {
                    else if (icdsm.getDistrictCode() != null && !icdsm.getDistrictCode().equalsIgnoreCase("") && !icdsm.getDistrictCode().equalsIgnoreCase("ALL")) {
                        
                        sql = "select office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office from g_office where office_level='SECTOR' and is_active='Y'and reporting_office in  (SELECT office_id FROM G_OFFICE WHERE office_level='PROJECT'AND is_active='Y' AND reporting_office in ('" + icdsm.getDistrictCode() + "'))";
                        
                    } else {
                        sql = "select office_address,office_level,office_code,reporting_office,phone_no,mobile_no,email,latitude,longitude,building_owner_id,is_active,office_name,office_name_hindi,office_id,head_of_office from g_office where office_level='SECTOR' and is_active='Y'and reporting_office in  (SELECT office_id FROM G_OFFICE WHERE office_level='PROJECT'AND is_active='Y' )";
                    }
                }
                
            }
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ICDSModel fw = null;
            int count = 1;
            while (rs.next()) {
                fw = new ICDSModel();
                fw.setProjectCode(rs.getString("office_id"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    fw.setProjectName(rs.getString("office_name_hindi"));
                } else {
                    fw.setProjectName(rs.getString("office_name"));
                }
                fw.setOfficeLevel(rs.getString("office_level"));
                fw.setOfficeCode(rs.getString("office_code"));
                fw.setReportingOffice(rs.getString("reporting_office"));
                fw.setPhoneNo(rs.getString("phone_no"));
                fw.setMobileNo(rs.getString("mobile_no"));
                fw.setEmail(rs.getString("email"));
                fw.setLatitude(rs.getString("latitude"));
                fw.setLongitude(rs.getString("longitude"));
                fw.setBuildingOwner(rs.getString("building_owner_id"));
                fw.setActive(rs.getBoolean("is_active"));
                
                fw.setHeadOfOffice(rs.getString("head_of_office"));
                if (rs.getString("head_of_office") != null && !rs.getString("head_of_office").trim().isEmpty()) {
                    fw.setHeadOfOffice(getHOONameDetails(rs.getString("head_of_office"), locale));
                    LOG.info("getHeadOfOffice inside==" + fw.getHeadOfOffice());
                } else {
                    fw.setHeadOfOffice("");
                }
                LOG.info("getHeadOfOffice==" + fw.getHeadOfOffice());
                fw.setTrackID(count);
                count++;
                li.add(fw);
            }
            lo.setDtaList(li);
            DataBaseFunctions.closeSqlObjects(rs);
            
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
    public void deleteICDSProject(String projectCode
    ) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            LOG.info("projectCode==" + projectCode);
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("UPDATE G_OFFICE SET is_active='N' WHERE office_id=?;");
            ps.setString(1, projectCode);
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
    
    public void addICDSProjectEditData(ICDSModel icdsModel, String officeId) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            con = this.dataSource.getConnection();
            String sqlUpdateOffice = "UPDATE   g_office SET office_name=?, office_name_hindi=?, office_address=?, phone_no=?, mobile_no=?, email=?, latitude=?, longitude=?,building_owner_id=? WHERE office_id=?";
            jdbcTemplateObject.update(sqlUpdateOffice,
                    icdsModel.getOfficeName(), icdsModel.getOfficeNameHindi(),
                    icdsModel.getOfficeAddress(), icdsModel.getPhoneNo(),
                    icdsModel.getMobileNo(), icdsModel.getEmail(),
                    icdsModel.getLatitude(), icdsModel.getLongitude(),
                    icdsModel.getBuildingOwner(), officeId);

//            ps = con.prepareStatement("UPDATE   g_office SET office_name=?, office_name_hindi=?, office_address=?, phone_no=?, mobile_no=?, email=?, latitude=?, longitude=?,building_owner_id=? WHERE office_id=?");
//            ps.setString(1, icdsModel.getOfficeName());
//            ps.setString(2, icdsModel.getOfficeNameHindi());
//            ps.setString(3, icdsModel.getOfficeAddress());
//            ps.setString(4, icdsModel.getPhoneNo());
//            ps.setString(5, icdsModel.getMobileNo());
//            ps.setString(6, icdsModel.getEmail());
//            ps.setString(7, icdsModel.getLatitude());
//            ps.setString(8, icdsModel.getLongitude());
//            ps.setString(9, icdsModel.getBuildingOwner());
//            ps.setString(10, officeId);
            //ps.executeUpdate();
            if (icdsModel.getSubstattivePostId() != null && !icdsModel.getSubstattivePostId().isEmpty()) {
                String sqlUpdateSubPost = "UPDATE g_substantive_post SET Is_Occupied=? WHERE Substattive_Post_Id=?";
                jdbcTemplateObject.update(sqlUpdateSubPost,
                        (icdsModel.isInPosition() ? "Y" : "N"), icdsModel.getSubstattivePostId());
//                ps = con.prepareStatement("UPDATE g_substantive_post SET Is_Occupied=? WHERE Substattive_Post_Id=?");
//                LOG.info("icdsModel.isInPosition()  " + icdsModel.isInPosition());
//                LOG.info("icdsModel.getSubstattivePostId()  " + icdsModel.getSubstattivePostId());
//                ps.setString(1, (icdsModel.isInPosition() ? "Y" : "N"));
//                ps.setString(2, icdsModel.getSubstattivePostId());
//
//                ps.executeUpdate();

                //UPDATE incumbency_chart
                String sqlUpdateINCChart = "UPDATE incumbency_chart SET HRMS_Emp_Id=?, Emp_Name=?, Emp_Name_Hindi=?, Is_Additional_Charge=? WHERE Incumbency_Id=?";
                jdbcTemplateObject.update(sqlUpdateINCChart,
                        icdsModel.getHrmsIDHOD(), icdsModel.getHodName(), icdsModel.getHodNameHindi(),
                        (icdsModel.isAdditionalChargers() ? "Y" : "N"), icdsModel.getIncumbencyId());
//                ps = con.prepareStatement("UPDATE incumbency_chart SET HRMS_Emp_Id=?, Emp_Name=?, Emp_Name_Hindi=?, Is_Additional_Charge=? WHERE Incumbency_Id=?");
//                ps.setString(1, icdsModel.getHrmsIDHOD());
//                ps.setString(2, icdsModel.getHodName());
//                ps.setString(3, icdsModel.getHodNameHindi());
//                ps.setString(4, (icdsModel.isAdditionalChargers() ? "Y" : "N"));
//                ps.setString(5, icdsModel.getIncumbencyId());
//
//                ps.executeUpdate();
            } else {
                addICDSProjectData(icdsModel, officeId, "");
            }
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
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
    public void updateICDSProjectEditData(ICDSModel icdsModel, String officeId) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            con = this.dataSource.getConnection();

//            ps = con.prepareStatement("UPDATE   g_office SET office_name=?, office_name_hindi=?, office_address=?, phone_no=?, mobile_no=?, email=?, latitude=?, longitude=?,building_owner_id=? WHERE office_id=?");
//            ps.setString(1, icdsModel.getOfficeName());
//            ps.setString(2, icdsModel.getOfficeNameHindi());
//            ps.setString(3, icdsModel.getOfficeAddress());
//            ps.setString(4, icdsModel.getPhoneNo());
//            ps.setString(5, icdsModel.getMobileNo());
//            ps.setString(6, icdsModel.getEmail());
//            ps.setString(7, icdsModel.getLatitude());
//            ps.setString(8, icdsModel.getLongitude());
//            ps.setString(9, icdsModel.getBuildingOwner());
//            ps.setString(10, officeId);
//
//            ps.executeUpdate();
            if (icdsModel.getSubstattivePostId() != null && !icdsModel.getSubstattivePostId().isEmpty()) {
                String sanctionDate = null;
                if (icdsModel.getSanctionDate() != null && !icdsModel.getSanctionDate().equals("") && icdsModel.getSanctionDate().length() < 21) {
                    sanctionDate = CommonFunctions.getFormattedInputDate(icdsModel.getSanctionDate());
                } else if (icdsModel.getSanctionDate() != null && icdsModel.getSanctionDate().length() >= 21) {
                    LOG.info("icdsModel.getSanctionDate() in side  " + icdsModel.getSanctionDate());
                    sanctionDate = dateFormat(icdsModel.getSanctionDate());
                    LOG.info("icdsModel.getSanctionDate() in side  " + dateFormat(icdsModel.getSanctionDate()));
                }
                LOG.info("icdsModel.isInPosition()   " + icdsModel.isInPosition());
                String sqlUpdateSubPost = "UPDATE g_substantive_post SET Subject=?,Subject_Hindi=?,Sanction_Ord_No=?,Sanction_Date=?,Is_Occupied=? WHERE Substattive_Post_Id=? ";
                jdbcTemplateObject.update(sqlUpdateSubPost,
                        icdsModel.getSubject(), icdsModel.getSubjectHindi(), icdsModel.getSanctionOrderNo(),
                        sanctionDate, (icdsModel.isInPosition() ? "Y" : "N"), icdsModel.getSubstattivePostId());
//                ps = con.prepareStatement("UPDATE g_substantive_post SET Subject=?,Subject_Hindi=?,Sanction_Ord_No=?,Sanction_Date=?,Is_Occupied=? WHERE Substattive_Post_Id=? ");
//                LOG.info("icdsModel.isInPosition()  " + icdsModel.isInPosition());
//                LOG.info("icdsModel.getSubstattivePostId()  " + icdsModel.getSubstattivePostId());
//                ps.setString(1, icdsModel.getSubject());
//                ps.setString(2, icdsModel.getSubjectHindi());
//                ps.setString(3, icdsModel.getSanctionOrderNo());
//                LOG.info("icdsModel.getSanctionDate() length " + icdsModel.getSanctionDate().length());
//                if (icdsModel.getSanctionDate() != null && !icdsModel.getSanctionDate().equals("") && icdsModel.getSanctionDate().length() < 21) {
//                    ps.setString(4, CommonFunctions.getFormattedInputDate(icdsModel.getSanctionDate()));
//                } else if (icdsModel.getSanctionDate() != null && icdsModel.getSanctionDate().length() >= 21) {
//                    LOG.info("icdsModel.getSanctionDate() in side  " + icdsModel.getSanctionDate());
//                    ps.setString(4, dateFormat(icdsModel.getSanctionDate()));
//                    LOG.info("icdsModel.getSanctionDate() in side  " + dateFormat(icdsModel.getSanctionDate()));
//                } else {
//                    ps.setString(4, null);
//                }
//                // ps.setString(4, icdsModel.getSanctionDate());
//                ps.setString(5, (icdsModel.isInPosition() ? "Y" : "N"));
//                ps.setString(6, icdsModel.getSubstattivePostId());
//
//                ps.executeUpdate();

                //UPDATE incumbency_chart
                LOG.info("getIncumbencyId() " + icdsModel.getIncumbencyId());
                LOG.info("getHodName() " + icdsModel.getHodName());
                LOG.info("isAdditionalChargers() " + icdsModel.isAdditionalChargers());
                if (icdsModel.getIncumbencyId() != null && !icdsModel.getIncumbencyId().equalsIgnoreCase("0")) {
                    
                    String sqlUpdateIncChart = "UPDATE incumbency_chart SET HRMS_Emp_Id=?, Emp_Name=?, Emp_Name_Hindi=?, Is_Additional_Charge=? WHERE Incumbency_Id=? ";
                    jdbcTemplateObject.update(sqlUpdateIncChart,
                            icdsModel.getHrmsIDHOD(), icdsModel.getHodName(), icdsModel.getHodNameHindi(),
                            (icdsModel.isAdditionalChargers() ? "Y" : "N"), icdsModel.getIncumbencyId());
//                 LOG.info("Incumbency_Id==" + auto_id);
                    String sqlUpdateSubPostAfterIncId = "UPDATE g_substantive_post SET Incumbency_Id=? WHERE Substattive_Post_Id=?";
                    jdbcTemplateObject.update(sqlUpdateSubPostAfterIncId, icdsModel.getIncumbencyId(), icdsModel.getSubstattivePostId());
//                    ps = con.prepareStatement("UPDATE incumbency_chart SET HRMS_Emp_Id=?, Emp_Name=?, Emp_Name_Hindi=?, Is_Additional_Charge=? WHERE Incumbency_Id=?");
//                    ps.setString(1, icdsModel.getHrmsIDHOD());
//                    ps.setString(2, icdsModel.getHodName());
//                    ps.setString(3, icdsModel.getHodNameHindi());
//                    ps.setString(4, (icdsModel.isAdditionalChargers() ? "Y" : "N"));
//                    ps.setString(5, icdsModel.getIncumbencyId());
//                    ps.executeUpdate();
                } else {
                    String joiningDate = null;
                    if (icdsModel.getJoinedDate() != null && !icdsModel.getJoinedDate().equals("")) {
                        joiningDate = CommonFunctions.getFormattedInputDate(icdsModel.getJoinedDate());
                    }
//                   
                    final Map<String, Object> parameters = new HashMap<>();
                    parameters.put("Substattive_Post_Id", icdsModel.getSubstattivePostId());
                    parameters.put("HRMS_Emp_Id", icdsModel.getHrmsIDHOD());
                    parameters.put("Emp_Name", icdsModel.getHodName());
                    parameters.put("Emp_Name_Hindi", icdsModel.getHodNameHindi());
                    parameters.put("From_Date", joiningDate);
                    parameters.put("From_Time", icdsModel.getJoiningTime());
                    parameters.put("Is_Additional_Charge", (icdsModel.isAdditionalChargers() ? "Y" : "N"));
                    
                    final Number key = this.insertIncumbencyChart.executeAndReturnKey(parameters);
                    int auto_id = key.intValue();
                    
                    LOG.info("Incumbency_Id==" + auto_id);
                    String sqlUpdateSubPostAfterIncId = "UPDATE g_substantive_post SET Incumbency_Id=? WHERE Substattive_Post_Id=?";
                    jdbcTemplateObject.update(sqlUpdateSubPostAfterIncId, auto_id, icdsModel.getSubstattivePostId());
                }
                
            } else {
                addICDSProjectData(icdsModel, officeId, "");
            }
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
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
    
    public void addICDSProjectData(ICDSModel icdsm, String officeId, String addMode) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String genericPostId = "0000";
        int substativeCount = 0;
        int auto_id = 0;
        int defaultPostId = 0;
        String officeProjId = null;
        // String officeLevel = "PROJECT";
        String officeLevel = icdsm.getLevel();
        String reportingOffice = "";
        String projCode = "";
        int distOffCode;
        LOG.info("officeLevel >>>  " + officeLevel);
        
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            con = this.dataSource.getConnection();
            
            if (officeId == null || officeId.equalsIgnoreCase("")) {
                // Get the Post Id 
                if (icdsm.getLevel().equalsIgnoreCase("DIST")) {
                    reportingOffice = null;
                    int tempOfficeId;
                    tempOfficeId = getMaxOfficeCode(officeLevel, "");
                    if (tempOfficeId > 0) {
                        tempOfficeId = tempOfficeId + 1;
                        projCode = String.format("%03d", tempOfficeId);
                        officeProjId = "D" + String.format("%014d", tempOfficeId);
                    } else {
                        projCode = "001";
                        officeProjId = "D000000000000001";
                    }
                } else if (icdsm.getLevel().equalsIgnoreCase("PROJECT")) {
                    
                    reportingOffice = icdsm.getDistrictCode();
                    LOG.info("reportingOffice >>>  " + reportingOffice);
                    if (reportingOffice.equalsIgnoreCase("")) {
                        
                        reportingOffice = "D" + String.format("%014d", "00000001");;
                    }
                    LOG.info("reportingOffice >>>  " + reportingOffice);
                    //Get Office ID
                    int tempcode = 0;
                    distOffCode = getOfficeCode("DIST", icdsm.getDistrictCode());
                    tempcode = getMaxOfficeCode(officeLevel, reportingOffice);
                    if (tempcode > 0) {
                        // tempcode = Integer.parseInt(projCode);
                        tempcode = tempcode + 1000;
                        projCode = String.format("%08d", tempcode);
                        officeProjId = "P" + String.format("%014d", tempcode);
                    } else {
                        tempcode = distOffCode + 1000;
                        projCode = "00001" + String.format("%03d", distOffCode);
                        officeProjId = "P" + String.format("%014d", tempcode);
                    }
                } else if (icdsm.getLevel().equalsIgnoreCase("SECTOR")) {
                    
                    if (icdsm.getSelectedProjectId() != null && !icdsm.getSelectedProjectId().equalsIgnoreCase("")) {
                        LOG.info("reportingOffice >>>  " + reportingOffice);
                        reportingOffice = icdsm.getSelectedProjectId();
                    } else if (reportingOffice.equalsIgnoreCase("")) {
                        reportingOffice = "P" + String.format("%014d", "00001001");
                    }
                    int tempcode = 0;
                    distOffCode = getOfficeCode("PROJECT", reportingOffice);
                    tempcode = getMaxOfficeCode(officeLevel, reportingOffice);
                    if (tempcode > 0) {
                        tempcode = tempcode + 100000000;
                        projCode = String.format("%012d", tempcode);
                        officeProjId = "S" + String.format("%014d", tempcode);
                    } else {
                        tempcode = distOffCode + 100000000;
                        projCode = "0001" + String.format("%08d", distOffCode);
                        officeProjId = "S" + String.format("%014d", tempcode);;
                    }
                    LOG.info("reportingOffice >>>  " + reportingOffice);
                    
                }
                String sqlG_Office = "INSERT INTO g_office (office_id, office_name, office_name_hindi, office_address, office_level, office_code, phone_no, mobile_no, email, latitude, longitude, building_owner_id,reporting_office,is_active) "
                        + "  VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?,? )";
//                ps = con.prepareStatement("INSERT INTO g_office (office_id, office_name, office_name_hindi, office_address, office_level, office_code, phone_no, mobile_no, email, latitude, longitude, building_owner_id,reporting_office,is_active)"
//                        + "  VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?,?)");
//                ps.setString(1, officeProjId);
//                ps.setString(2, icdsm.getOfficeName());
//                ps.setString(3, icdsm.getOfficeNameHindi());
//                ps.setString(4, icdsm.getOfficeAddress());
//                ps.setString(5, officeLevel);
//                ps.setString(6, projCode);
//                ps.setString(7, icdsm.getPhoneNo());
//                ps.setString(8, icdsm.getMobileNo());
//                ps.setString(9, icdsm.getEmail());
//                ps.setString(10, icdsm.getLatitude());
//                ps.setString(11, icdsm.getLongitude());
//                ps.setString(12, icdsm.getBuildingOwner());
//                ps.setString(13, reportingOffice);
//                ps.setString(14, "Y");
//
//                ps.execute();
                jdbcTemplateObject.update(sqlG_Office, officeProjId, icdsm.getOfficeName(), icdsm.getOfficeNameHindi(), icdsm.getOfficeAddress(),
                        officeLevel, projCode, icdsm.getPhoneNo(), icdsm.getMobileNo(), icdsm.getEmail(), icdsm.getLatitude(), icdsm.getLongitude(),
                        icdsm.getBuildingOwner(), reportingOffice, "Y");
            } else {
                officeProjId = officeId;
            }
            LOG.info("officeProjId >> " + officeProjId);

            // Get the Post Id 
            LOG.info("icdsm.getSanctionPostName() post " + icdsm.getSanctionPostName() + icdsm.getSanctionPostCode());
            if (icdsm.getSanctionPostCode() == null) {
                String query_get_postId = "SELECT Generic_Post_Id FROM g_default_post where Office_Level=? and Is_HOO=?";
                List<Map<String, Object>> sid = jdbcTemplateObject.queryForList(query_get_postId, officeLevel, (icdsm.isHoo() ? "Y" : "N"));
                for (Map<String, Object> map : sid) {
                    LOG.info("map >>>  " + map);
                    genericPostId = (String) map.get("Generic_Post_Id");
                    LOG.info("genericPostId >>>  " + genericPostId);
                    
                }
//                ps = con.prepareStatement(query_get_postId);
//                ps.setString(1, officeLevel);
//                ps.setString(2, (icdsm.isHoo() ? "Y" : "N"));
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    genericPostId = rs.getString("Generic_Post_Id");
//                }
                // genericPostId = String.valueOf(sid);
            } else if (genericPostId != null && genericPostId.equalsIgnoreCase("0000")) {
                genericPostId = icdsm.getSanctionPostCode();
            }
            LOG.info("genericPostId >>>  " + genericPostId);
            //Get the count for Substative post
            String query_get_substative_count = " select count(*) CNT from g_substantive_post";
            substativeCount = jdbcTemplateObject.queryForInt(query_get_substative_count);
//            ps = con.prepareStatement(query_get_substative_count);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                substativeCount = rs.getInt("CNT");
//            }
            LOG.info("substativeCount >>>  " + substativeCount);
            //Generate SubstativeId
            String substativeId = officeProjId + genericPostId + String.format("%04d", (substativeCount + 1));

            //Insert record into Incumbency_chart table
            if (addMode.equalsIgnoreCase("")) {
                String date = null;
                if (icdsm.getJoinedDate() != null && !icdsm.getJoinedDate().equals("")) {
                    date = CommonFunctions.getFormattedInputDate(icdsm.getJoinedDate());
                }
                LOG.info("substativeCount >>>  " + icdsm.isAdditionalChargers());
                final Map<String, Object> parameters = new HashMap<>();
                parameters.put("Substattive_Post_Id", substativeId);
                parameters.put("HRMS_Emp_Id", icdsm.getHrmsIDHOD());
                parameters.put("Emp_Name", icdsm.getHodName());
                parameters.put("Emp_Name_Hindi", icdsm.getHodNameHindi());
                parameters.put("From_Date", date);
                parameters.put("From_Time", icdsm.getJoiningTime());
                parameters.put("Is_Additional_Charge", (icdsm.isAdditionalChargers() ? "Y" : "N"));
                
                final Number key = this.insertIncumbencyChart.executeAndReturnKey(parameters);
                auto_id = key.intValue();
                // String query_add_incumbency = "INSERT INTO incumbency_chart (Substattive_Post_Id, HRMS_Emp_Id, Emp_Name, Emp_Name_Hindi, From_Date, From_Time,Is_Additional_Charge) VALUES (?, ?, ?, ?,?, ?,?)";
                // auto_id = jdbcTemplateObject.queryForInt(query_add_incumbency, substativeId, icdsm.getHrmsIDHOD(), icdsm.getHodName(), icdsm.date(), date, icdsm.getJoiningTime(), (icdsm.isAdditionalChargers() ? "Y" : "N"));

//                ps = con.prepareStatement(query_add_incumbency, Statement.RETURN_GENERATED_KEYS);
//                LOG.info("substativeCount >>>  " + icdsm.isAdditionalChargers());
//                ps.setString(1, substativeId);
//                ps.setString(2, icdsm.getHrmsIDHOD());
//                ps.setString(3, icdsm.getHodName());
//                ps.setString(4, icdsm.getHodNameHindi());
//                //ps.setString(5, icdsm.getJoinedDate());
//                if (icdsm.getJoinedDate() != null && !icdsm.getJoinedDate().equals("")) {
//                    ps.setString(5, CommonFunctions.getFormattedInputDate(icdsm.getJoinedDate()));
//                } else {
//                    ps.setString(5, null);
//                }
//                ps.setString(6, icdsm.getJoiningTime());
//                ps.setString(7, (icdsm.isAdditionalChargers() ? "Y" : "N"));
//                ps.executeUpdate();
//                rs = ps.getGeneratedKeys();
//                rs.next();
//                auto_id = rs.getInt(1);
            }
            LOG.info("auto_id >>>  " + auto_id);

            //Update the hod details in to office table
            //Insert record into Incumbency_chart table
            //insert into default post table
            LOG.info("officeLevel >>>  " + officeLevel);
            LOG.info("genericPostId >>>  " + genericPostId);
            String query_add_defaultPost = "SELECT Post_Plan_Id FROM g_default_post where Office_Level=? and Generic_Post_Id=?";
            
            List<Map<String, Object>> ppi = jdbcTemplateObject.queryForList(query_add_defaultPost, officeLevel, genericPostId);
            for (Map<String, Object> map : ppi) {
                LOG.info("map >>>  " + map);
                defaultPostId = (int) map.get("Post_Plan_Id");
                LOG.info("genericPostId >>>  " + defaultPostId);
                
            }
//            ps = con.prepareStatement(query_add_defaultPost);
//            ps.setString(1, officeLevel);
//            ps.setString(2, genericPostId);
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                defaultPostId = rs.getInt("Post_Plan_Id");
//            }

            LOG.info("defaultPostId >>>  " + defaultPostId);

            //insert into sustantive post table
            String sanctionDate = null;
            if (icdsm.getSanctionDate() != null && !icdsm.getSanctionDate().equals("") && icdsm.getSanctionDate().length() <= 11) {
                sanctionDate = CommonFunctions.getFormattedInputDate(icdsm.getSanctionDate());
            } else if (icdsm.getSanctionDate() != null && icdsm.getSanctionDate().length() >= 21) {
                LOG.info("icdsModel.getSanctionDate() in side  " + icdsm.getSanctionDate());
                sanctionDate = dateFormat(icdsm.getSanctionDate());
                LOG.info("icdsModel.getSanctionDate() in side  " + dateFormat(icdsm.getSanctionDate()));
            }
            String query_add_sustantivePost = "INSERT INTO g_substantive_post ( Substattive_Post_Id, Office_Id, Generic_Post_Id, Post_Sl, Is_Occupied, Incumbency_Id, Is_Active,Is_HOO,Subject,Subject_Hindi,Sanction_Ord_No,Sanction_Date) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?)";
            
            jdbcTemplateObject.update(query_add_sustantivePost,
                    substativeId, officeProjId, genericPostId,
                    defaultPostId, (icdsm.isInPosition() ? "Y" : "N"), auto_id, "Y",
                    (icdsm.isHoo() ? "Y" : "N"), icdsm.getSubject(), icdsm.getSubjectHindi(), icdsm.getSanctionOrderNo(),
                    sanctionDate);

//            ps = con.prepareStatement(query_add_sustantivePost);
//            ps.setString(1, substativeId);
//            ps.setString(2, officeProjId);
//            ps.setString(3, genericPostId);
//            ps.setInt(4, defaultPostId);
//            ps.setString(5, (icdsm.isInPosition() ? "Y" : "N"));
//            ps.setInt(6, auto_id);
//            ps.setString(7, "Y");
////            if (icdsm.getHrmsIDHOD()!=null && !icdsm.getHrmsIDHOD().isEmpty()) {
////                ps.setString(7, "Y");
////
////            } else {
////                ps.setString(7, "N");
////
////            }
//            ps.setString(8, (icdsm.isHoo() ? "Y" : "N"));
//            ps.setString(9, icdsm.getSubject());
//            ps.setString(10, icdsm.getSubjectHindi());
//            ps.setString(11, icdsm.getSanctionOrderNo());
//            if (icdsm.getSanctionDate() != null && !icdsm.getSanctionDate().equals("") && icdsm.getSanctionDate().length() <= 11) {
//                ps.setString(12, CommonFunctions.getFormattedInputDate(icdsm.getSanctionDate()));
//            } else if ( icdsm.getSanctionDate().length() >= 21) {
//                LOG.info("icdsModel.getSanctionDate() in side  " + icdsm.getSanctionDate());
//                ps.setString(12, dateFormat(icdsm.getSanctionDate()));
//                LOG.info("icdsModel.getSanctionDate() in side  " + dateFormat(icdsm.getSanctionDate()));
//            } else {
//                ps.setString(12, null);
//            }
//            ps.executeUpdate();
            if (icdsm.isHoo()) {
                String query_update_office = "UPDATE g_office SET head_of_office=? WHERE office_id=?";
                jdbcTemplateObject.update(query_update_office, String.valueOf(substativeId), officeProjId);
//                ps = con.prepareStatement(query_update_office);
//                ps.setString(1, String.valueOf(substativeId));
//                ps.setString(2, officeProjId);
//
//                ps.executeUpdate();

            }
            LOG.info("Update for HOO >>>  " + substativeId);
            
            LOG.info(" "
                    + "<<< FINISH >>>  ");
            LOG.info("Created Name = ");
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
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
    
    public String getOfficeID(String officeLevel, String officeCode) {
        String projcode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            LOG.info("officeLevel >>>>>>> " + officeLevel);
            LOG.info("reportingOfficeCode >>>>>>> " + officeCode);
            List<Integer> li = new ArrayList<Integer>();
            ps = con.prepareStatement("select min(office_id) maxOfficeCode from g_office where office_level=? ");
            ps.setString(1, officeLevel);
            // ps.setString(2, officeCode);

            rs = ps.executeQuery();
            if (rs.next()) {
                projcode = rs.getString("maxOfficeCode");
                
            }
            LOG.info("list projcode >>>>>>> " + projcode);
            // maxofficeCode=Collections.max(li);
            //  LOG.info("maxofficeCode >>>>>>> " + maxofficeCode);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return projcode;
    }
    
    public int getMaxOfficeCode(String officeLevel, String reportingOfficeCode) {
        String projcode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxCnt = 0;
        try {
            con = this.dataSource.getConnection();
            LOG.info("officeLevel >>>>>>> " + officeLevel);
            LOG.info("reportingOfficeCode >>>>>>> " + reportingOfficeCode);
            List<Integer> li = new ArrayList<Integer>();
            if (reportingOfficeCode != null && !reportingOfficeCode.isEmpty()) {
                ps = con.prepareStatement("SELECT max(office_code) maxOfficeCode FROM g_office WHERE office_level=? AND reporting_office= ?");
                ps.setString(1, officeLevel);
                ps.setString(2, reportingOfficeCode);
            } else {
                ps = con.prepareStatement("SELECT max(office_code) maxOfficeCode FROM g_office WHERE office_level=? ");
                ps.setString(1, officeLevel);
                
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                projcode = rs.getString("maxOfficeCode");
                maxCnt = rs.getInt("maxOfficeCode");
                // projcode = projcode.substring(1);
                //LOG.info("projcode >>>>>>> " + projcode);
                //li.add(Integer.parseInt(projcode));
            }
            LOG.info("list projcode >>>>>>> " + projcode);
            LOG.info(" maxCnt >>>>>>> " + maxCnt);
            // maxofficeCode=Collections.max(li);
            //  LOG.info("maxofficeCode >>>>>>> " + maxofficeCode);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        //  return projcode;
        return maxCnt;
    }
    
    public int getOfficeCode(String officeLevel, String OfficeId) {
        String projcode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxCnt = 0;
        try {
            con = this.dataSource.getConnection();
            LOG.info("officeLevel >>>>>>> " + officeLevel);
            LOG.info("reportingOfficeCode >>>>>>> " + OfficeId);
            List<Integer> li = new ArrayList<Integer>();
            if (OfficeId != null && !OfficeId.isEmpty()) {
                ps = con.prepareStatement("SELECT office_code  FROM g_office WHERE office_level=? AND office_id= ?");
                ps.setString(1, officeLevel);
                ps.setString(2, OfficeId);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                // projcode = rs.getString("maxOfficeCode");
                maxCnt = rs.getInt("office_code");
                // projcode = projcode.substring(1);
                //LOG.info("projcode >>>>>>> " + projcode);
                //li.add(Integer.parseInt(projcode));
            }
            // LOG.info("list projcode >>>>>>> " + projcode);
            LOG.info(" OfficeCode >>>>>>> " + maxCnt);
            // maxofficeCode=Collections.max(li);
            //  LOG.info("maxofficeCode >>>>>>> " + maxofficeCode);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        //  return projcode;
        return maxCnt;
    }
    
    @Override
    public ICDSModel viewIcdsProjectData(String projCode, Locale locale, String mode) {
        String projcode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ICDSModel icdsModel = new ICDSModel();
        try {
            con = this.dataSource.getConnection();
            LOG.info("projCode >>>>>>> " + projCode);
            
            ps = con.prepareStatement("select off.reporting_office,off.office_code, inc.Incumbency_Id,sub.Substattive_Post_Id,off.office_name,off.office_name_hindi,off.office_address,off.phone_no,off.mobile_no,off.email, sub.Is_Occupied,inc.Emp_Name,inc.Emp_Name_Hindi,inc.HRMS_Emp_Id,inc.Is_Additional_Charge,off.latitude,off.longitude,off.building_owner_id  from g_office off "
                    + "left outer join  g_substantive_post sub on sub.office_id= off.office_id "
                    + "left outer join  incumbency_chart inc  on inc.Substattive_Post_Id = (Select Substattive_Post_Id from g_substantive_post where office_id=? and Is_HOO='Y') "
                    + "where off.office_id=?");
            ps.setString(1, projCode);
            ps.setString(2, projCode);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                icdsModel.setReportingOffice(rs.getString("reporting_office"));
                icdsModel.setOfficeCode(rs.getString("office_code"));
                icdsModel.setIncumbencyId(rs.getString("Incumbency_Id"));
                icdsModel.setSubstattivePostId(rs.getString("Substattive_Post_Id"));
                if (mode.equalsIgnoreCase("view")) {
                    if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                        icdsModel.setOfficeName(rs.getString("office_name_hindi"));
                        icdsModel.setHodName(rs.getString("Emp_Name_Hindi"));
                        
                    } else {
                        icdsModel.setOfficeName(rs.getString("office_name"));
                        icdsModel.setHodName(rs.getString("Emp_Name"));
                    }
                } else {
                    icdsModel.setOfficeName(rs.getString("office_name"));
                    icdsModel.setOfficeNameHindi(rs.getString("office_name_hindi"));
                    icdsModel.setHodName(rs.getString("Emp_Name"));
                    icdsModel.setHodNameHindi(rs.getString("Emp_Name_Hindi"));
                }
                icdsModel.setOfficeAddress(rs.getString("office_address"));
                icdsModel.setPhoneNo(rs.getString("phone_no"));
                icdsModel.setMobileNo(rs.getString("mobile_no"));
                icdsModel.setEmail(rs.getString("email"));
                icdsModel.setInPosition((rs.getBoolean("Is_Occupied") ? true : false));
                
                icdsModel.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                icdsModel.setAdditionalChargers((rs.getBoolean("Is_Additional_Charge") ? true : false));
                icdsModel.setLatitude(rs.getString("latitude"));
                icdsModel.setLongitude(rs.getString("longitude"));
                icdsModel.setBuildingOwner(rs.getString("building_owner_id"));
                if (icdsModel.isAdditionalChargers()) {
                    icdsModel.setHooChoice("additionalChargers");
                } else if (icdsModel.isInPosition()) {
                    icdsModel.setHooChoice("inPosition");
                }
                
                LOG.info("icdsModel. >>>>>>> " + icdsModel.getOfficeName());
                LOG.info("BuildingOwner. >>>>>>> " + icdsModel.getBuildingOwner());
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return icdsModel;
    }
    
    @Override
    public ListPageObject getICDSSanctionPostList(String projCode, String level) {
        List li = new ArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sql = "";
        String searchString = "";
        ListPageObject lo = new ListPageObject();
        try {
            con = this.dataSource.getConnection();
            
            sql = "Select inc.incumbency_Id,inc.From_Date,inc.From_Time,inc.HRMS_Emp_Id,gen.Generic_Post_Id,inc.Emp_Name,inc.Emp_Name_Hindi,sub.Is_Occupied,sub.Sanction_Ord_No,sub.Sanction_Date,gen.Generic_Post_Name,gen.Generic_Post_Name_Hindi,sac.Post_Strength "
                    + "from g_default_post sac "
                    + "left outer join g_generic_post gen on gen.Generic_Post_Id=sac.Generic_Post_Id "
                    + "left outer join g_substantive_post sub on sub.Post_Sl=sac.Post_Plan_Id and sub.Generic_Post_Id=sac.Generic_Post_Id and sub.office_id=? "
                    + "left outer join incumbency_chart inc on inc.Incumbency_Id=sub.Incumbency_Id and sub.Post_Sl=sac.Post_Plan_Id AND sub.Generic_Post_Id=sac.Generic_Post_Id and sub.office_id=? "
                    + "where Office_Level=?";
            
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, projCode);
            ps.setString(2, projCode);
            ps.setString(3, level);
            rs = ps.executeQuery();
            ICDSModel fw = null;
            String save = "true";
            int count = 1;
            while (rs.next()) {
                if (rs.getString("incumbency_Id") == null) {
                    for (int i = 1; i <= rs.getInt("Post_Strength"); i++) {
                        save = "false";
                        fw = new ICDSModel();
                        
                        fw.setIncumbencyId(rs.getString("incumbency_Id"));
                        fw.setJoinedDate(rs.getString("From_Date"));
                        fw.setJoiningTime(rs.getString("From_Time"));
                        fw.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                        fw.setStaffingName(rs.getString("Emp_Name"));
                        fw.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                        fw.setInPosition((rs.getBoolean("Is_Occupied") ? true : false));
                        fw.setSanctionPostOccupied(rs.getBoolean("Is_Occupied") ? "Occupied" : "Vacant");
                        fw.setSanctionOrderNo(rs.getString("Sanction_Ord_No"));
                        fw.setSanctionDate(rs.getString("Sanction_Date"));
                        fw.setSanctionPostCode(rs.getString("Generic_Post_Id"));
                        fw.setSanctionPostNameHindi(rs.getString("Generic_Post_Name_Hindi"));
                        fw.setSanctionPosition(rs.getInt("Post_Strength"));
                        fw.setSanctionPostNameDis(rs.getString("Generic_Post_Name") + (rs.getInt("Post_Strength") > 1 ? " (Post" + i + ")" : ""));
                        fw.setSanctionPostName(rs.getString("Generic_Post_Name"));
                        fw.setSaveDisabled(save);
                        fw.setTrackID(count);
                        li.add(fw);
                    }
                } else {
                    fw = new ICDSModel();
                    if (save.equalsIgnoreCase("true") || fw.getSaveDisabled() != null) {
                        fw.setSaveDisabled(save);
                    }
                    fw.setIncumbencyId(rs.getString("incumbency_Id"));
                    fw.setJoinedDate(rs.getString("From_Date"));
                    fw.setJoiningTime(rs.getString("From_Time"));
                    fw.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                    fw.setStaffingName(rs.getString("Emp_Name"));
                    fw.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                    fw.setInPosition((rs.getBoolean("Is_Occupied") ? true : false));
                    fw.setSanctionPostOccupied(rs.getBoolean("Is_Occupied") ? "Occupied" : "Vacant");
                    fw.setSanctionOrderNo(rs.getString("Sanction_Ord_No"));
                    fw.setSanctionDate(rs.getString("Sanction_Date"));
                    fw.setSanctionPostCode(rs.getString("Generic_Post_Id"));
                    fw.setSanctionPostNameHindi(rs.getString("Generic_Post_Name_Hindi"));
                    fw.setSanctionPosition(rs.getInt("Post_Strength"));
                    fw.setSanctionPostNameDis(rs.getString("Generic_Post_Name"));
                    fw.setSanctionPostName(rs.getString("Generic_Post_Name"));
                    fw.setTrackID(count);
                    li.add(fw);
                }
                count++;
            }
            lo.setBox1(save);
            lo.setDtaList(li);
            DataBaseFunctions.closeSqlObjects(rs);
            
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
    public int getCountPost(String OfficeLevel) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "";
        String searchString = "";
        int count = 0;
        try {
            con = this.dataSource.getConnection();
            
            sql = "select sum(Post_Strength) sumPost from g_default_post where Office_Level=?";
            
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, OfficeLevel);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                count = rs.getInt("sumPost");
            }
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
        
        return count;
    }
    
    @Override
    public int getSubsCountPost(String OfficeId) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "";
        String searchString = "";
        int count = 0;
        try {
            con = this.dataSource.getConnection();
            
            sql = "select count(Post_Sl) cnt from  g_substantive_post where office_id=?";
            
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, OfficeId);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                count = rs.getInt("cnt");
            }
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
        
        return count;
    }
    
    @Override
    public Map<String, ICDSModel> getMasterPlanPost(String OfficeLevel, String officeId, Locale locale) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "";
        String searchString = "";
        int count = 0;
        ICDSModel im = null;
        Map<String, ICDSModel> mapPost = new HashMap<String, ICDSModel>();
        int incumbencyId = 0;
        int postPlanID = 0;
        String genPostPlanID = null;
        try {
            con = this.dataSource.getConnection();
            sql = "select gen.Generic_Post_Name,gen.Generic_Post_Name_Hindi,gen.Generic_Post_Id,def.Post_Strength,def.Post_Plan_Id,def.Is_HOO,def.Office_Level from g_default_post def,g_generic_post gen where def.Generic_Post_Id=gen.Generic_Post_Id and def.Office_Level=?";
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, OfficeLevel);
            rs = ps.executeQuery();
            while (rs.next()) {
                im = new ICDSModel();
                im.setSanctionPostName(rs.getString("Generic_Post_Name"));
                im.setSanctionPostNameHindi(rs.getString("Generic_Post_Name_Hindi"));
                im.setSanctionPostCode(rs.getString("Generic_Post_Id"));
                im.setSanctionPosition(rs.getInt("Post_Strength"));
                im.setSanctionPostPlanId(rs.getInt("Post_Plan_Id"));
                im.setHoo((rs.getString("Is_HOO") != null && rs.getString("Is_HOO").equalsIgnoreCase("Y")) ? true : false);
                if (im.isHoo()) {
                    postPlanID = rs.getInt("Post_Plan_Id");
                    genPostPlanID = rs.getString("Generic_Post_Id");
                }
                im.setLevel(rs.getString("Office_Level"));
                mapPost.put(rs.getString("Generic_Post_Id"), im);
            }

            //get the other details and set to bean
            sql = "SELECT  Incumbency_Id, Is_Occupied, Is_HOO FROM rajicds.g_substantive_post subpost where subpost.Post_Sl=? and subpost.office_id=?";
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setInt(1, postPlanID);
            ps.setString(2, officeId);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                im = mapPost.get(genPostPlanID);
                im.setIncumbencyId(rs.getString("Incumbency_Id"));
                im.setSanctionPostOccupied(rs.getBoolean("Is_Occupied") ? "Occupied" : "Vacant");
                im.setInPosition(rs.getBoolean("Is_Occupied"));
                if (im.isHoo()) {
                    incumbencyId = rs.getInt("Incumbency_Id");
                }
                mapPost.put(genPostPlanID, im);
            }
            //get the other details and set to bean
            sql = "select Substattive_Post_Id,HRMS_Emp_Id,Emp_Name,Emp_Name_Hindi,From_Date,From_Time,To_Date,To_Time,Is_Additional_Charge from incumbency_chart inc where Incumbency_Id =?";
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setInt(1, incumbencyId);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                im = mapPost.get(genPostPlanID);
                im.setSubstattivePostId(rs.getString("Substattive_Post_Id"));
                im.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                im.setHodName(rs.getString("Emp_Name"));
                im.setStaffingName(rs.getString("Emp_Name"));
                im.setHodNameHindi(rs.getString("Emp_Name_Hindi"));
                im.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    im.setStaffingName(rs.getString("Emp_Name_Hindi"));
                } else {
                    im.setStaffingName(rs.getString("Emp_Name"));
                }
                im.setJoinedDate(rs.getString("From_Date"));
                im.setJoiningTime(rs.getString("From_Time"));
                im.setRelevingDate(rs.getString("To_Date"));
                im.setRelevingTime(rs.getString("To_Time"));
                im.setAdditionalChargers(rs.getBoolean("Is_Additional_Charge"));
                if (!im.isInPosition()) {
                    im.setSanctionPostOccupied(rs.getBoolean("Is_Additional_Charge") ? "AdditionalCharge" : "Vacant");
                }
                
                mapPost.put(genPostPlanID, im);
            }
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
        
        return mapPost;
    }
    
    @Override
    public Map<String, ICDSModel> getSavedPost(String OfficeLevel, String officeId, Locale locale) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "";
        String searchString = "";
        int count = 0;
        ICDSModel im = null;
        Map<String, ICDSModel> mapPost = new HashMap<String, ICDSModel>();
        Map<String, String> genericPostMap = null;
        int incumbencyId = 0;
        int postPlanID = 0;
        String genPostID = null;
        String genPostName = null;
        String substattivePostId = null;
        List<String> postIdList = new ArrayList<String>();
        List<Integer> incumbencyIdList = new ArrayList<Integer>();
        List<String> substattivePostIdList = new ArrayList<String>();
        try {
            con = this.dataSource.getConnection();
            genericPostMap = getDefaultPost(locale);
            LOG.info("genericPostMap > ***** " + genericPostMap);
            //get the other details and set to bean

            sql = "SELECT Substattive_Post_Id,Office_Id,Generic_Post_Id,Post_Sl,Subject,Subject_Hindi,Sanction_Ord_No,Sanction_Date,Is_Active, Incumbency_Id, Is_Occupied, Is_HOO FROM g_substantive_post where Is_Active='Y' and office_id=?";
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            //ps.setInt(1, postPlanID);
            ps.setString(1, officeId);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                im = new ICDSModel();
                im.setSubstattivePostId(rs.getString("Substattive_Post_Id"));
                im.setProjectCode(rs.getString("Office_Id"));
                im.setSanctionPostCode(rs.getString("Generic_Post_Id"));
                im.setSanctionPostPlanId(rs.getInt("Post_Sl"));
                im.setSubject(rs.getString("Subject"));
                im.setSubjectHindi(rs.getString("Subject_Hindi"));
                im.setSanctionOrderNo(rs.getString("Sanction_Ord_No"));
                im.setSanctionDate(rs.getString("Sanction_Date"));
                im.setActive(rs.getBoolean("Is_Active") ? true : false);
                im.setIncumbencyId(rs.getString("Incumbency_Id"));
                im.setInPosition(rs.getBoolean("Is_Occupied"));
                im.setSanctionPostOccupied(rs.getBoolean("Is_Occupied") ? "Occupied" : "Vacant");
                im.setHoo(rs.getBoolean("Is_HOO") ? true : false);
                im.setHooChoice(im.isInPosition() ? "inPosition" : "");
                //get the post details
                genPostID = rs.getString("Generic_Post_Id");
                postIdList.add(genPostID);
                genPostName = genericPostMap.get(rs.getString("Generic_Post_Id"));
                im.setSanctionPostName(genPostName);
                
                if (postIdList.contains(genPostID)) {
                    int occurrences = Collections.frequency(postIdList, genPostID);
                    im.setSanctionPostNameDis(genPostName + (occurrences > 1 ? "(Post " + occurrences + ")" : ""));
                    LOG.info("SanctionPostNameDis  >> " + im.getSanctionPostNameDis());
                }
                //incumbencyId = rs.getInt("Incumbency_Id");
                substattivePostId = rs.getString("Substattive_Post_Id");
                // incumbencyIdList.add(incumbencyId);
                substattivePostIdList.add(substattivePostId);
                mapPost.put(substattivePostId, im);
            }
            //get the other details and set to bean
            LOG.info("Substattive_Post_Id >> " + substattivePostIdList);
            String queryParam = "";
            for (int i = 0; i < substattivePostIdList.size(); i++) {
                if ((substattivePostIdList.size() - 1) != i) {
                    queryParam = queryParam + "'" + substattivePostIdList.get(i) + "'" + ",";
                } else {
                    queryParam = queryParam + "'" + substattivePostIdList.get(i) + "'";
                }
            }
            LOG.info("queryParam >> " + queryParam);
            sql = "select Incumbency_Id,Substattive_Post_Id,HRMS_Emp_Id,Emp_Name,Emp_Name_Hindi,From_Date,From_Time,To_Date,To_Time,Is_Additional_Charge from incumbency_chart inc where Substattive_Post_Id in (" + queryParam + ")";
            sql = sql.replace("[", "").replace("]", "");
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            // ps.setArray(1, incumbencyIdList.toArray());

            rs = ps.executeQuery();
            while (rs.next()) {
                incumbencyId = rs.getInt("Incumbency_Id");
                substattivePostId = rs.getString("Substattive_Post_Id");
                im = mapPost.get(substattivePostId);
                
                im.setSubstattivePostId(rs.getString("Substattive_Post_Id"));
                LOG.info("Is_Additional_Charge  : " + rs.getBoolean("Is_Additional_Charge"));
                if ((im.isInPosition() || rs.getBoolean("Is_Additional_Charge")) && 
                        (im.getIncumbencyId() != null && !im.getIncumbencyId().equalsIgnoreCase("0")) && 
                        (rs.getString("To_Date")==null || (rs.getString("To_Date")!=null && rs.getString("To_Date").trim().isEmpty()))) {
                    im.setIncumbencyId(String.valueOf(incumbencyId));
                    im.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                    im.setHodName(rs.getString("Emp_Name"));
                    
                    im.setStaffingName(rs.getString("Emp_Name"));
                    im.setHodNameHindi(rs.getString("Emp_Name_Hindi"));
                    im.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                    if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                        im.setStaffingName(rs.getString("Emp_Name_Hindi"));
                    } else {
                        im.setStaffingName(rs.getString("Emp_Name"));
                    }
                    im.setJoinedDate(rs.getString("From_Date"));
                    im.setJoiningTime(rs.getString("From_Time"));
                    im.setRelevingDate(rs.getString("To_Date"));
                    im.setRelevingTime(rs.getString("To_Time"));
                    im.setAdditionalChargers(rs.getBoolean("Is_Additional_Charge"));
                    im.setHooChoice(im.isAdditionalChargers() ? "additionalChargers" : "");
                    if (!im.isInPosition()) {
                        im.setSanctionPostOccupied(rs.getBoolean("Is_Additional_Charge") ? "AdditionalCharge" : "Vacant");
                    }
                }
                mapPost.put(substattivePostId, im);
            }
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
        
        return mapPost;
    }
    
    @Override
    public ListPageObject getICDSSanctionPostList1(String projCode, String level) {
        List li = new ArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sql = "";
        String searchString = "";
        ListPageObject lo = new ListPageObject();
        try {
            con = this.dataSource.getConnection();
            
            sql = "Select inc.incumbency_Id,inc.From_Date,inc.From_Time,inc.HRMS_Emp_Id,gen.Generic_Post_Id,inc.Emp_Name,inc.Emp_Name_Hindi,sub.Is_Occupied,sub.Sanction_Ord_No,sub.Sanction_Date,gen.Generic_Post_Name,gen.Generic_Post_Name_Hindi,sac.Post_Strength "
                    + "from g_default_post sac "
                    + "left outer join g_generic_post gen on gen.Generic_Post_Id=sac.Generic_Post_Id "
                    + "left outer join g_substantive_post sub on sub.Post_Sl=sac.Post_Plan_Id and sub.Generic_Post_Id=sac.Generic_Post_Id and sub.office_id=? "
                    + "left outer join incumbency_chart inc on inc.Incumbency_Id=sub.Incumbency_Id and sub.Post_Sl=sac.Post_Plan_Id AND sub.Generic_Post_Id=sac.Generic_Post_Id and sub.office_id=? "
                    + "where Office_Level=?";
            
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, projCode);
            ps.setString(2, projCode);
            ps.setString(3, level);
            rs = ps.executeQuery();
            ICDSModel fw = null;
            String save = "true";
            int count = 1;
            while (rs.next()) {
                if (rs.getString("incumbency_Id") == null) {
                    for (int i = 1; i <= rs.getInt("Post_Strength"); i++) {
                        save = "false";
                        fw = new ICDSModel();
                        
                        fw.setIncumbencyId(rs.getString("incumbency_Id"));
                        fw.setJoinedDate(rs.getString("From_Date"));
                        fw.setJoiningTime(rs.getString("From_Time"));
                        fw.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                        fw.setStaffingName(rs.getString("Emp_Name"));
                        fw.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                        fw.setInPosition((rs.getBoolean("Is_Occupied") ? true : false));
                        fw.setSanctionPostOccupied(rs.getBoolean("Is_Occupied") ? "Occupied" : "Vacant");
                        fw.setSanctionOrderNo(rs.getString("Sanction_Ord_No"));
                        fw.setSanctionDate(rs.getString("Sanction_Date"));
                        fw.setSanctionPostCode(rs.getString("Generic_Post_Id"));
                        fw.setSanctionPostNameHindi(rs.getString("Generic_Post_Name_Hindi"));
                        fw.setSanctionPosition(rs.getInt("Post_Strength"));
                        fw.setSanctionPostNameDis(rs.getString("Generic_Post_Name") + (rs.getInt("Post_Strength") > 1 ? " (Post" + i + ")" : ""));
                        fw.setSanctionPostName(rs.getString("Generic_Post_Name"));
                        fw.setSaveDisabled(save);
                        fw.setTrackID(count);
                        li.add(fw);
                    }
                } else {
                    fw = new ICDSModel();
                    if (save.equalsIgnoreCase("true") || fw.getSaveDisabled() != null) {
                        fw.setSaveDisabled(save);
                    }
                    fw.setIncumbencyId(rs.getString("incumbency_Id"));
                    fw.setJoinedDate(rs.getString("From_Date"));
                    fw.setJoiningTime(rs.getString("From_Time"));
                    fw.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                    fw.setStaffingName(rs.getString("Emp_Name"));
                    fw.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                    fw.setInPosition((rs.getBoolean("Is_Occupied") ? true : false));
                    fw.setSanctionPostOccupied(rs.getBoolean("Is_Occupied") ? "Occupied" : "Vacant");
                    fw.setSanctionOrderNo(rs.getString("Sanction_Ord_No"));
                    fw.setSanctionDate(rs.getString("Sanction_Date"));
                    fw.setSanctionPostCode(rs.getString("Generic_Post_Id"));
                    fw.setSanctionPostNameHindi(rs.getString("Generic_Post_Name_Hindi"));
                    fw.setSanctionPosition(rs.getInt("Post_Strength"));
                    fw.setSanctionPostNameDis(rs.getString("Generic_Post_Name"));
                    fw.setSanctionPostName(rs.getString("Generic_Post_Name"));
                    fw.setTrackID(count);
                    li.add(fw);
                }
                count++;
            }
            lo.setBox1(save);
            lo.setDtaList(li);
            DataBaseFunctions.closeSqlObjects(rs);
            
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
    public Map<String, String> getDefaultPost(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Generic_Post_Id,Generic_Post_Name,Generic_Post_Name_Hindi from g_generic_post");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Generic_Post_Id"), rs.getString("Generic_Post_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Generic_Post_Id"), rs.getString("Generic_Post_Name"));
                }
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
        return centerList;
    }
    
    @Override
    public Map<String, String> getDefaultTime(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        centerList.put("AN", "AN");
        centerList.put("FN", "FN");
        
        return centerList;
    }
    
    @Override
    public String getHrmsIdDetails(String hrmsId) {
        String hrmsname = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT Emp_Name,Emp_Name_Hindi FROM incumbency_chart where HRMS_Emp_Id=?");
            ps.setString(1, hrmsId);
            rs = ps.executeQuery();
            if (rs.next()) {
                hrmsname = rs.getString("Emp_Name") + "@" + rs.getString("Emp_Name_Hindi");
            }
            LOG.info(" >>>>>> *****  >>>> " + hrmsname);
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
        return hrmsname;
    }
    
    public String getHOONameDetails(String incId, Locale locale) {
        String hrmsname = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT Emp_Name,Emp_Name_Hindi FROM incumbency_chart where Incumbency_Id=(select Incumbency_Id from g_substantive_post where Substattive_Post_Id=?)");
            ps.setString(1, incId);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    hrmsname = rs.getString("Emp_Name_Hindi");
                } else {
                    hrmsname = rs.getString("Emp_Name");
                }
            }
            LOG.info(" >>>>>> ***HOO NAME**  >>>> " + hrmsname);
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
        return hrmsname;
    }
    
    @Override
    public void updateIncumbency(ICDSModel icdsModel) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            LOG.info("projectCode==" + icdsModel.getIncumbencyId());
            LOG.info("getHodName==" + icdsModel.getHodName());
            LOG.info("getHodNameHindi==" + icdsModel.getHodNameHindi());
            LOG.info("getJoinedDate==" + icdsModel.getJoinedDate());
            LOG.info("getJoiningTime==" + icdsModel.getJoiningTime());
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("UPDATE incumbency_chart SET HRMS_Emp_Id=?, Emp_Name=?, Emp_Name_Hindi=?, From_Date=?, From_Time=? WHERE Incumbency_Id=?");
            ps.setString(1, icdsModel.getHrmsIDHOD());
            ps.setString(2, icdsModel.getHodName());
            ps.setString(3, icdsModel.getHodNameHindi());
            if (icdsModel.getJoinedDate() != null && !icdsModel.getJoinedDate().equals("")) {
                ps.setString(4, CommonFunctions.getFormattedInputDate(icdsModel.getJoinedDate()));
            } else {
                ps.setString(4, null);
            }
            ps.setString(5, icdsModel.getJoiningTime());
            ps.setString(6, icdsModel.getIncumbencyId());
            
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
    public void addIncumbency(ICDSModel icdsModel) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        int auto_id = 0;
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String joiningDate = null;
            if (icdsModel.getJoinedDate() != null && !icdsModel.getJoinedDate().equals("")) {
                joiningDate = CommonFunctions.getFormattedInputDate(icdsModel.getJoinedDate());
            }
            LOG.info("SubstattivePostId ==" + icdsModel.getSubstattivePostId());
            con = this.dataSource.getConnection();
            // String query_add_incumbency = "INSERT INTO incumbency_chart (Substattive_Post_Id, HRMS_Emp_Id, Emp_Name, Emp_Name_Hindi, From_Date, From_Time,Is_Additional_Charge) VALUES (?, ?, ?, ?,?, ?,?)";

            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("Substattive_Post_Id", icdsModel.getSubstattivePostId());
            parameters.put("HRMS_Emp_Id", icdsModel.getHrmsIDHOD());
            parameters.put("Emp_Name", icdsModel.getHodName());
            parameters.put("Emp_Name_Hindi", icdsModel.getHodNameHindi());
            parameters.put("From_Date", joiningDate);
            parameters.put("From_Time", icdsModel.getJoiningTime());
            parameters.put("Is_Additional_Charge", (icdsModel.isAdditionalChargers() ? "Y" : "N"));
            
            final Number key = this.insertIncumbencyChart.executeAndReturnKey(parameters);
            auto_id = key.intValue();

//            ps = con.prepareStatement(query_add_incumbency, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, icdsModel.getSubstattivePostId());
//            ps.setString(2, icdsModel.getHrmsIDHOD());
//            ps.setString(3, icdsModel.getHodName());
//            ps.setString(4, icdsModel.getHodNameHindi());
//            if (icdsModel.getJoinedDate() != null && !icdsModel.getJoinedDate().equals("")) {
//                ps.setString(5, CommonFunctions.getFormattedInputDate(icdsModel.getJoinedDate()));
//            } else {
//                ps.setString(5, null);
//            }
//            ps.setString(6, icdsModel.getJoiningTime());
//            ps.setString(7, (icdsModel.isAdditionalChargers() ? "Y" : "N"));
//            ps.executeUpdate();
//            rs = ps.getGeneratedKeys();
//            rs.next();
//            auto_id = rs.getInt(1);
            LOG.info("Incumbency_Id==" + auto_id);
            LOG.info("isInPosition==" + icdsModel.isInPosition());
            LOG.info("isAdditionalChargers==" + icdsModel.isAdditionalChargers());
            String sqlUpdateSubPost = "UPDATE g_substantive_post SET Is_Occupied=?,Incumbency_Id=? WHERE Substattive_Post_Id=?";
            jdbcTemplateObject.update(sqlUpdateSubPost, (icdsModel.isAdditionalChargers() ? "N" : (icdsModel.isInPosition() ? "Y" : "N")), auto_id, icdsModel.getSubstattivePostId());
//            ps = con.prepareStatement("UPDATE g_substantive_post SET Is_Occupied=?,Incumbency_Id=? WHERE Substattive_Post_Id=?");
//            
//            ps.setString(1, "Y");
//            ps.setInt(2, auto_id);
//            ps.setString(3, icdsModel.getSubstattivePostId());
//
//            ps.execute();
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
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
    public void updateIncumbencyForRelieve(ICDSModel icdsModel) {
        PreparedStatement ps = null;
        Connection con = null;
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String relevingDate = null;
            if (icdsModel.getRelevingDate() != null && !icdsModel.getRelevingDate().equals("")) {
                relevingDate = CommonFunctions.getFormattedInputDate(icdsModel.getRelevingDate());
            }
            LOG.info("getIncumbencyId==" + icdsModel.getIncumbencyId());
            LOG.info("getSubstattivePostId==" + icdsModel.getSubstattivePostId());
            
            con = this.dataSource.getConnection();
            String sqlUpdateSubPost = "UPDATE incumbency_chart SET To_Date=?, To_Time=? WHERE Incumbency_Id=?";
            jdbcTemplateObject.update(sqlUpdateSubPost, relevingDate, icdsModel.getRelevingTime(), icdsModel.getIncumbencyId());
//            ps = con.prepareStatement("UPDATE incumbency_chart SET To_Date=?, To_Time=? WHERE Incumbency_Id=?");
//
//            if (icdsModel.getRelevingDate() != null && !icdsModel.getRelevingDate().equals("")) {
//                ps.setString(1, CommonFunctions.getFormattedInputDate(icdsModel.getRelevingDate()));
//            } else {
//                ps.setString(1, null);
//            }
//            ps.setString(2, icdsModel.getRelevingTime());
//            ps.setString(3, icdsModel.getIncumbencyId());
//
//            ps.execute();
            String sqlUpdateSubPostRel = "UPDATE g_substantive_post SET Is_Occupied=?,Incumbency_Id=? WHERE Substattive_Post_Id=?";
            jdbcTemplateObject.update(sqlUpdateSubPostRel, "N", 0, icdsModel.getSubstattivePostId());
//            ps = con.prepareStatement("UPDATE g_substantive_post SET Is_Occupied=?,Incumbency_Id=? WHERE Substattive_Post_Id=?");
//            ps.setString(1, "N");
//            ps.setInt(2, 0);
//            ps.setString(3, icdsModel.getSubstattivePostId());
//
//            ps.execute();
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            transactionManager.rollback(status);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
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
    public ListPageObject getIncumbencyChartList(String substattivePostId, String postCode, Locale locale) {
        List li = new ArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sql = "";
        String searchString = "";
        ListPageObject lo = new ListPageObject();
        try {
            con = this.dataSource.getConnection();

            //  sql = "select Incumbency_Id,Emp_Name,Emp_Name_Hindi,From_Date,To_Date from incumbency_chart inc where inc.Substattive_Post_Id in (  SELECT Substattive_Post_Id FROM g_substantive_post where office_id=? and Generic_Post_Id=?)";
            sql = "select Incumbency_Id,Emp_Name,Emp_Name_Hindi,From_Date,To_Date from incumbency_chart inc where inc.Substattive_Post_Id =? order by Incumbency_Id asc";
            
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, substattivePostId);
            // ps.setString(2, postCode);
            rs = ps.executeQuery();
            ICDSModel fw = null;
            String save = "true";
            int count = 1;
            while (rs.next()) {
                fw = new ICDSModel();
                fw.setTrackID(count);
                fw.setIncumbencyId(String.valueOf(rs.getInt("Incumbency_Id")));
                fw.setStaffingName(rs.getString("Emp_Name"));
                fw.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    fw.setStaffingName(rs.getString("Emp_Name_Hindi"));
                } else {
                    fw.setStaffingName(rs.getString("Emp_Name"));
                }
                fw.setJoinedDate(rs.getString("From_Date"));
                fw.setRelevingDate(rs.getString("To_Date"));
                li.add(fw);
                count++;
            }
            lo.setDtaList(li);
            DataBaseFunctions.closeSqlObjects(rs);
            
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
    public Map<String, String> getProjectListFilter(Locale locale, String distId) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where office_level='PROJECT' and reporting_office = ?");
            ps.setString(1, distId);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
    }
    
    @Override
    public Map<String, String> getSectorListFilter(Locale locale, String distId) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where office_level='SECTOR' and reporting_office = ?");
            ps.setString(1, distId);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
    }
    
    @Override
    public Map<String, String> getProjectList(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where office_level='PROJECT' ");
            
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
    }
    
    @Override
    public Map<String, String> getDistrictList(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where office_level='DIST' ");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
        
    }
    
    @Override
    public Map<String, String> getDistrictListFilter(Locale locale, String distId) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where office_level='DIST' AND office_id=? ");
            ps.setString(1, distId);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
        
    }
    
    @Override
    public Map<String, String> getDistrictFromProject(Locale locale, String projId) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where  office_id= (select reporting_office from g_office where office_id = ?) ");
            ps.setString(1, projId);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
        
    }
    
    @Override
    public Map<String, String> getDistrictListOfficeCodeFilter(Locale locale, String officeCode) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            
            ps = con.prepareStatement("Select Office_name,Office_Name_Hindi,Office_id from g_office where office_level='DIST' AND office_code=? ");
            ps.setString(1, officeCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_Name_Hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("Office_id"), rs.getString("Office_name"));
                }
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
        return centerList;
        
    }
    
    @Override
    public ICDSModel getIncumbencyChartById(int incumbencyId) {
        PreparedStatement ps = null;
        Connection con = null;
        ResultSet rs = null;
        String sql = "";
        String searchString = "";
        int count = 0;
        ICDSModel im = new ICDSModel();
        
        sql = "select Substattive_Post_Id,HRMS_Emp_Id,Emp_Name,Emp_Name_Hindi,From_Date,From_Time,To_Date,To_Time,Is_Additional_Charge from incumbency_chart inc where Incumbency_Id =?";
        LOG.info("sql==" + sql);
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, incumbencyId);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                im.setSubstattivePostId(rs.getString("Substattive_Post_Id"));
                im.setHrmsIDHOD(rs.getString("HRMS_Emp_Id"));
                im.setHodName(rs.getString("Emp_Name"));
                im.setStaffingName(rs.getString("Emp_Name"));
                im.setHodNameHindi(rs.getString("Emp_Name_Hindi"));
                im.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                im.setJoinedDate(rs.getString("From_Date"));
                im.setJoiningTime(rs.getString("From_Time"));
                im.setRelevingDate(rs.getString("To_Date"));
                im.setRelevingTime(rs.getString("To_Time"));
                im.setAdditionalChargers(rs.getBoolean("Is_Additional_Charge"));
                im.setIncumbencyId(String.valueOf(incumbencyId));
            }
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
        return im;
    }
    
    @Override
    public ICDSModel getLastIncumbencyBySPId(String substattivePostId, Locale locale) {
        
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sql = "";
        ICDSModel fw = new ICDSModel();
        try {
            con = this.dataSource.getConnection();
            
            sql = "select Incumbency_Id,Emp_Name,Emp_Name_Hindi,From_Date,To_Date from incumbency_chart inc where inc.Substattive_Post_Id =? "
                    + " AND To_Date is not NULL"
                    + " order by Incumbency_Id desc"
                    + " LIMIT 1";
            
            LOG.info("sql==" + sql);
            ps = con.prepareStatement(sql);
            ps.setString(1, substattivePostId);
            rs = ps.executeQuery();
            
            String save = "true";
            int count = 1;
            while (rs.next()) {
                fw.setTrackID(count);
                fw.setIncumbencyId(String.valueOf(rs.getInt("Incumbency_Id")));
                fw.setStaffingName(rs.getString("Emp_Name"));
                fw.setStaffingNameHindi(rs.getString("Emp_Name_Hindi"));
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    fw.setStaffingName(rs.getString("Emp_Name_Hindi"));
                } else {
                    fw.setStaffingName(rs.getString("Emp_Name"));
                }
                fw.setJoinedDate(rs.getString("From_Date"));
                fw.setRelevingDate(rs.getString("To_Date"));
                
            }
            
            DataBaseFunctions.closeSqlObjects(rs);
            
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
        return fw;
    }
    
    public String dateFormat(String date) {
        
        String formatedDate = "";
        try {
            
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            Calendar cal = Calendar.getInstance();
            if (date.length() > 21) {
                cal.setTime(dtf.parse(date));
            } else {
                cal.setTime(dateFormat.parse(date));
            }
            formatedDate = dateFormat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return formatedDate;
    }
}
