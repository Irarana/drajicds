/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.frontlineWorker;

import dricds.common.CommonFunctions;
import dricds.common.DataBaseFunctions;
import dricds.common.ListPageObject;
import dricds.model.frontlineWorker.FrontlineWorkerModel;
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
public class FrontlineWorkerDAOImpl implements FrontlineWorkerDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addFrontlineWorkerData(FrontlineWorkerModel fwm) {

        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("INSERT INTO  frontline_worker (worker_id,worker_name,worker_name_hindi,worker_desig, "
                    + "awc_code, tagged_relation, relation_name,  dob, category, aadhaar_no, bhamshah_no, bank_acct,"
                    + " ifsc_code, doj, address, mobile, education, if_job_training, job_train_year, refresh_training) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            ps.setString(1, fwm.getWorkerId());
            ps.setString(2, fwm.getWorkerName());
            ps.setString(3, fwm.getWorkerNameHindi());
            ps.setString(4, fwm.getWorkerDesig());
            ps.setString(5, fwm.getaWCCode());
            ps.setString(6, fwm.getTaggedRelation());
            ps.setString(7, fwm.getRelationName());
            if (fwm.getDob() != null && !fwm.getDob().equals("")) {
                ps.setString(8, CommonFunctions.getFormattedInputDate(fwm.getDob()));
            } else {
                ps.setString(8, null);
            }
            ps.setString(9, fwm.getCategory());
            ps.setString(10, fwm.getAadhaarNo());
            ps.setString(11, fwm.getBhamshahNo());
            ps.setString(12, fwm.getBankAcct());
            ps.setString(13, fwm.getIfcsCode());
            if (fwm.getDoj() != null && !fwm.getDoj().equals("")) {
                ps.setString(14, CommonFunctions.getFormattedInputDate(fwm.getDoj()));
            } else {
                ps.setString(14, null);
            }
            ps.setString(15, fwm.getAddress());
            ps.setString(16, fwm.getMobile());
            ps.setString(17, fwm.getEducation());
            ps.setString(18, fwm.getIfJobTraining());
            ps.setInt(19, fwm.getJobTrainYear());
            ps.setString(20, fwm.getRefreshTraining());
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
    public FrontlineWorkerModel editFrontlineWorkerData(String workerId) {
        FrontlineWorkerModel fwm = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("select worker_id,worker_name,worker_name_hindi,worker_desig,awc.awc_name_english, awc.awc_code,awctype.awc_type, "
                    + "tagged_relation, relation_name,  dob, category, aadhaar_no, bhamshah_no, "
                    + " bank_acct, ifsc_code, doj, worker.address, worker.mobile, education, if_job_training, "
                    + " job_train_year, refresh_training from frontline_worker worker "
                    + " left outer join awc_master awc on worker.awc_code=awc.awc_code "
                    + " left outer join g_awc_type awctype on awc.awc_type=awctype.awc_type_id where worker_id=?");
            ps.setString(1, workerId);

            rs = ps.executeQuery();
            if (rs.next()) {
                fwm = new FrontlineWorkerModel();
                fwm.setWorkerId(rs.getString("worker_id"));
                fwm.setWorkerName(rs.getString("worker_name"));
                fwm.setWorkerNameHindi(rs.getString("worker_name_hindi"));
                fwm.setWorkerDesig(rs.getString("worker_desig"));
                fwm.setaWCCode(rs.getString("awc_code"));
                fwm.setAwcName(rs.getString("awc_name_english"));
                fwm.setAwcType(rs.getString("awc_type"));
                fwm.setTaggedRelation(rs.getString("tagged_relation"));
                fwm.setRelationName(rs.getString("relation_name"));
                fwm.setDob(CommonFunctions.getFormattedOutputDate1(rs.getDate("dob")));
                fwm.setCategory(rs.getString("category"));
                fwm.setAadhaarNo(rs.getString("aadhaar_no"));
                fwm.setBhamshahNo(rs.getString("bhamshah_no"));
                fwm.setBankAcct(rs.getString("bank_acct"));
                fwm.setIfcsCode(rs.getString("ifsc_code"));
                fwm.setDoj(CommonFunctions.getFormattedOutputDate1(rs.getDate("doj")));
                fwm.setAddress(rs.getString("address"));
                fwm.setMobile(rs.getString("mobile"));
                fwm.setEducation(rs.getString("education"));
                fwm.setIfJobTraining(rs.getString("if_job_training"));
                fwm.setJobTrainYear(rs.getInt("job_train_year"));
                fwm.setRefreshTraining(rs.getString("refresh_training"));

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
        return fwm;
    }

    @Override
    public FrontlineWorkerModel viewFrontlineWorkerData(String workerId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        FrontlineWorkerModel fwm = null;
        String sql = "";

        try {
            sql = "select worker_id,worker_name,worker_name_hindi,desig.worker_desig,desig.worker_desig_hindi,worker.address worker_address,awc.awc_name_hindi, awc.awc_code,awctype.awc_type,awc.address, \n"
                    + "tgrelation.tagged_relation,tagged_relation_hindi, relation_name,  dob, g_category.category,g_category.category_hindi, aadhaar_no, bhamshah_no,\n"
                    + "bank_acct, ifsc_code, doj, worker.address, worker.mobile, edu.education,edu.education_hindi, if_job_training,\n"
                    + "job_train_year, refresh_training,sector_name_hindi,project_name_hindi,district_name_hindi from frontline_worker worker\n"
                    + "left outer join awc_master awc on worker.awc_code=awc.awc_code\n"
                    + "left outer join g_awc_type awctype on awc.awc_type=awctype.awc_type_id\n"
                    + "left outer join g_worker_desig desig on worker.worker_desig=desig.worker_desig_id\n"
                    + "left outer join g_tagged_relation tgrelation on worker.tagged_relation=tgrelation.tagged_relation_id\n"
                    + "left outer join g_category on worker.category=g_category.category_id\n"
                    + "left outer join g_education edu on worker.education=edu.education_id \n"
                    + "left outer join g_sector on awc.sector_code=g_sector.sector_code "
                    + "left outer join g_project on awc.project_code=g_project.project_code "
                    + "left outer join g_district on awc.dist_code=g_district.district_code "
                    + "where worker_id=?";
            System.out.println("sql==" + sql);
            con = dataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, workerId);

            rs = ps.executeQuery();
            if (rs.next()) {
                fwm = new FrontlineWorkerModel();
                fwm.setWorkerId(rs.getString("worker_id"));
                fwm.setWorkerName(rs.getString("worker_name"));
                fwm.setWorkerNameHindi(rs.getString("worker_name_hindi"));
                fwm.setWorkerDesig(rs.getString("worker_desig"));
                fwm.setWorkerDesigHindi(rs.getString("worker_desig_hindi"));
                fwm.setaWCCode(rs.getString("awc_code"));
                fwm.setAwcName(rs.getString("awc_name_hindi"));
                fwm.setAwcType(rs.getString("awc_type"));
                fwm.setAwwAddress(rs.getString("address"));
                fwm.setTaggedRelation(rs.getString("tagged_relation"));
                fwm.setTaggedRelationHindi(rs.getString("tagged_relation_hindi"));
                fwm.setRelationName(rs.getString("relation_name"));
                fwm.setDob(CommonFunctions.getFormattedOutputDate1(rs.getDate("dob")));
                fwm.setCategory(rs.getString("category"));
                fwm.setCategoryHindi(rs.getString("category_hindi"));
                fwm.setAadhaarNo(rs.getString("aadhaar_no"));
                fwm.setBhamshahNo(rs.getString("bhamshah_no"));
                fwm.setBankAcct(rs.getString("bank_acct"));
                fwm.setIfcsCode(rs.getString("ifsc_code"));
                fwm.setDoj(CommonFunctions.getFormattedOutputDate1(rs.getDate("doj")));
                fwm.setAddress(rs.getString("worker_address"));
                fwm.setMobile(rs.getString("mobile"));
                fwm.setEducation(rs.getString("education"));
                fwm.setEducationHindi(rs.getString("education_hindi"));
                fwm.setIfJobTraining(rs.getString("if_job_training"));
                fwm.setJobTrainYear(rs.getInt("job_train_year"));
                fwm.setRefreshTraining(rs.getString("refresh_training"));
                fwm.setSectorName(rs.getString("sector_name_hindi"));
                fwm.setProjectName(rs.getString("project_name_hindi"));
                fwm.setDistrictNameHindi(rs.getString("district_name_hindi"));
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
        return fwm;
    }

    @Override
    public ListPageObject getFrontlineWorkerList(FrontlineWorkerModel fwm) {
        List li = new ArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection con = null;
        String sql = "";
        String searchString = "";
        ListPageObject lo = new ListPageObject();
        try {
            con = this.dataSource.getConnection();

            if (fwm.getDistrictCode().equalsIgnoreCase("ALL")) {
                if (fwm.getProjectCode() != null && !fwm.getProjectCode().equals("") && fwm.getProjectCode().equalsIgnoreCase("ALL")) {
                    if (fwm.getSectorCode() != null && !fwm.getSectorCode().equals("") && fwm.getSectorCode().equalsIgnoreCase("ALL")) {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = "";
                        } else {
                            searchString = " awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    } else {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.sector_code='" + fwm.getSectorCode() + "'";
                        } else {
                            searchString = " awc.sector_code='" + fwm.getSectorCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    }
                } else {
                    if (fwm.getSectorCode() != null && !fwm.getSectorCode().equals("") && fwm.getSectorCode().equalsIgnoreCase("ALL")) {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.project_code='" + fwm.getProjectCode() + "'";
                        } else {
                            searchString = " awc.project_code='" + fwm.getProjectCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    } else {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.project_code='" + fwm.getProjectCode() + "' AND awc.sector_code='" + fwm.getSectorCode() + "'";
                        } else {
                            searchString = " awc.project_code='" + fwm.getProjectCode() + "' AND awc.sector_code='" + fwm.getSectorCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    }
                }
            } else {
                if (fwm.getProjectCode() != null && !fwm.getProjectCode().equals("") && fwm.getProjectCode().equalsIgnoreCase("ALL")) {
                    if (fwm.getSectorCode() != null && !fwm.getSectorCode().equals("") && fwm.getSectorCode().equalsIgnoreCase("ALL")) {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "'";
                        } else {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    } else {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.sector_code='" + fwm.getSectorCode() + "'";
                        } else {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.sector_code='" + fwm.getSectorCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    }
                } else {
                    if (fwm.getSectorCode() != null && !fwm.getSectorCode().equals("") && fwm.getSectorCode().equalsIgnoreCase("ALL")) {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.project_code='" + fwm.getProjectCode() + "'";
                        } else {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.project_code='" + fwm.getProjectCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    } else {
                        if (fwm.getCenterCode() != null && !fwm.getCenterCode().equals("") && fwm.getCenterCode().equalsIgnoreCase("ALL")) {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.project_code='" + fwm.getProjectCode() + "' AND awc.sector_code='" + fwm.getSectorCode() + "'";
                        } else {
                            searchString = " awc.dist_code='" + fwm.getDistrictCode() + "' AND awc.project_code='" + fwm.getProjectCode() + "' AND awc.sector_code='" + fwm.getSectorCode() + "' AND awc.awc_code='" + fwm.getCenterCode() + "'";
                        }
                    }
                }
            }

            if (searchString != null && !searchString.equals("")) {
                sql = "SELECT worker_id,worker_name,worker_name_hindi,desig.worker_desig,worker_desig_hindi,\n"
                        + " awc_name_english,awc_name_hindi,g_awc_type.awc_type from frontline_worker fw\n"
                        + " left outer join awc_master awc on fw.awc_code=awc.awc_code\n"
                        + " left outer join g_worker_desig desig on fw.worker_desig=desig.worker_desig_id\n"
                        + " left outer join g_awc_type on awc.awc_type=g_awc_type.awc_type_id where " + searchString + " ORDER BY worker_name";
            } else {
                sql = "SELECT worker_id,worker_name,worker_name_hindi,desig.worker_desig,worker_desig_hindi,\n"
                        + " awc_name_english,awc_name_hindi,g_awc_type.awc_type from frontline_worker fw\n"
                        + " left outer join awc_master awc on fw.awc_code=awc.awc_code\n"
                        + " left outer join g_worker_desig desig on fw.worker_desig=desig.worker_desig_id\n"
                        + " left outer join g_awc_type on awc.awc_type=g_awc_type.awc_type_id";
            }
            System.out.println("sql=="+sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            FrontlineWorkerModel fw = null;
            while (rs.next()) {
                fw = new FrontlineWorkerModel();
                fw.setWorkerId(rs.getString("worker_id"));
                fw.setWorkerName(rs.getString("worker_name"));
                fw.setWorkerNameHindi(rs.getString("worker_name_hindi"));
                fw.setWorkerDesig(rs.getString("worker_desig"));
                fw.setAwcType(rs.getString("awc_type"));
                li.add(fw);
            }

            lo.setDtaList(li);
            DataBaseFunctions.closeSqlObjects(rs);

            if (searchString != null && !searchString.equals("")) {
                sql = "SELECT count(desig.worker_desig) cnt,worker_desig_id,desig.worker_desig,worker_desig_hindi from frontline_worker fw\n"
                        + "left outer join awc_master awc on fw.awc_code=awc.awc_code\n"
                        + "left outer join g_worker_desig desig on fw.worker_desig=desig.worker_desig_id\n"
                        + "left outer join g_awc_type on awc.awc_type=g_awc_type.awc_type_id where  " + searchString + " group by desig.worker_desig_id";
            } else {
                sql = "SELECT count(desig.worker_desig) cnt,worker_desig_id,desig.worker_desig,worker_desig_hindi from frontline_worker fw\n"
                        + "left outer join awc_master awc on fw.awc_code=awc.awc_code\n"
                        + "left outer join g_worker_desig desig on fw.worker_desig=desig.worker_desig_id\n"
                        + "left outer join g_awc_type on awc.awc_type=g_awc_type.awc_type_id  group by desig.worker_desig_id";
            }
            System.out.println("sql222222=="+sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    if (rs.getString("worker_desig_id").equals("1")) {
                        lo.setBox1(rs.getString("cnt"));
                    } else if (rs.getString("worker_desig_id").equals("2")) {
                        lo.setBox2(rs.getString("cnt"));
                    } else if (rs.getString("worker_desig_id").equals("3")) {
                        lo.setBox3(rs.getString("cnt"));
                    } else if (rs.getString("worker_desig_id").equals("4")) {
                        lo.setBox4(rs.getString("cnt"));
                    }
                }
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
        return lo;
    }

    public void updateFrontlineWorkData(FrontlineWorkerModel fwm) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("UPDATE  frontline_worker set worker_name=? ,worker_name_hindi=?,worker_desig=?, "
                    + "awc_code=?, tagged_relation=?, relation_name=?,  dob=?, category=?, aadhaar_no=?, bhamshah_no=?, bank_acct=?,"
                    + " ifsc_code=?, doj=?, address=?, mobile=?, education=?, if_job_training=?, job_train_year=?, refresh_training=? where worker_id=?");

            ps.setString(1, fwm.getWorkerName());
            ps.setString(2, fwm.getWorkerNameHindi());
            ps.setString(3, fwm.getWorkerDesig());
            ps.setString(4, fwm.getaWCCode());
            ps.setString(5, fwm.getTaggedRelation());
            ps.setString(6, fwm.getRelationName());
            if (fwm.getDob() != null && !fwm.getDob().equals("")) {
                ps.setString(7, CommonFunctions.getFormattedInputDate(fwm.getDob()));
            } else {
                ps.setString(7, null);
            }

            ps.setString(8, fwm.getCategory());
            ps.setString(9, fwm.getAadhaarNo());
            ps.setString(10, fwm.getBhamshahNo());
            ps.setString(11, fwm.getBankAcct());
            ps.setString(12, fwm.getIfcsCode());
            if (fwm.getDoj() != null && !fwm.getDoj().equals("")) {
                ps.setString(13, CommonFunctions.getFormattedInputDate(fwm.getDoj()));
            } else {
                ps.setString(13, null);
            }
            ps.setString(14, fwm.getAddress());
            ps.setString(15, fwm.getMobile());
            ps.setString(16, fwm.getEducation());
            ps.setString(17, fwm.getIfJobTraining());
            ps.setInt(18, fwm.getJobTrainYear());
            ps.setString(19, fwm.getRefreshTraining());
            ps.setString(20, fwm.getWorkerId());
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

    public void deleteFrontlineWorkData(String workerId) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("DELETE FROM frontline_worker where worker_id=?");
            ps.setString(1, workerId);
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
