/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.trainermaster;

import dricds.model.trainermaster.TrainerMasterModel;
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
public class TrainerMasterDAOImpl implements TrainerMasterDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List getTrainerMasterList() {
        List li = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("select trainer_id,tc.tc_id,tc.tc_name,trainer_name,gdesig.trainer_desig from trainer "
                    + "left outer join training_center tc on trainer.tc_id=tc.tc_id "
                    + "left outer join g_trainer_desig gdesig on trainer.trainer_desig=gdesig.trainer_desig_id");
            rs = ps.executeQuery();
            TrainerMasterModel tm = null;
            while (rs.next()) {
                tm = new TrainerMasterModel();
                tm.setTrainerId(rs.getString("trainer_id"));
                tm.setTcId(rs.getString("tc_id"));
                tm.setTcName(rs.getString("tc_name"));
                tm.setTrainerName(rs.getString("trainer_name"));
                tm.setTrinerDesigasString(rs.getString("trainer_desig"));
                li.add(tm);
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
        return li;
    }

    @Override
    public void addTrainerMasterData(TrainerMasterModel tcm) {
        Connection con = null;
        PreparedStatement ps = null;
        String teachsub = "";
        String edusub = "";

        try {
            con = this.dataSource.getConnection();
            if (tcm.getTeachingSubject() != null && tcm.getTeachingSubject().length > 0) {
                for (int i = 0; i < tcm.getTeachingSubject().length; i++) {
                    if (teachsub != null && !teachsub.equals("")) {
                        teachsub = teachsub + "," + tcm.getTeachingSubject()[i];
                    } else {
                        teachsub = tcm.getTeachingSubject()[i];
                    }
                }
            }
            System.out.println("teaching=="+teachsub);
            if (tcm.getTrainerEduSubject() != null && tcm.getTrainerEduSubject().length > 0) {
                for (int i = 0; i < tcm.getTrainerEduSubject().length; i++) {
                    if (edusub != null && !edusub.equals("")) {
                        edusub = edusub + "," + tcm.getTrainerEduSubject()[i];
                    } else {
                        edusub = tcm.getTrainerEduSubject()[i];
                    }
                }
            }

            ps = con.prepareStatement("INSERT INTO trainer (tc_id,trainer_id,trainer_name,trainer_name_hindi,if_fulltime,trainer_aadhaar,"
                    + "trainer_desig,teaching_subject,trainer_edu_level,trainer_prof_edu,trainer_edu_subject,"
                    + "trainer_experience,if_job_training,job_train_Year,refresh_training) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, tcm.getTcId());
            ps.setString(2, tcm.getTrainerId());
            ps.setString(3, tcm.getTrainerName());
            ps.setString(4, tcm.getTrainerNameHindi());
            ps.setString(5, tcm.getIfFulltime());
            ps.setString(6, tcm.getTrainerAadhaar());
            ps.setString(7, tcm.getTrainerDesig());
            ps.setString(8, teachsub);
            ps.setString(9, tcm.getTrainerEduLevel());
            ps.setString(10, tcm.getTrainerProfEdu());;
            ps.setString(11, edusub);
            ps.setString(12, tcm.getTrainerExperience());
            ps.setString(13, tcm.getIfJobTraining());
            ps.setInt(14, tcm.getJobTrainYear());
            ps.setString(15, tcm.getRefreshTraining());
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
    public TrainerMasterModel editTrainerMasterData(String trainerId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TrainerMasterModel tcm = null;
        String sql = "";
        try {
            con = this.dataSource.getConnection();
            tcm = new TrainerMasterModel();

            sql = "select trainer.tc_id,tc.tc_name,trainer_id,trainer_name,trainer_name_hindi,if_fulltime,trainer_aadhaar,\n"
                    + "trainer_desig,teaching_subject,trainer_edu_level,trainer_prof_edu,trainer_edu_subject,\n"
                    + "trainer_experience,if_job_training,job_train_Year,refresh_training from trainer\n"
                    + "left outer join training_center tc on trainer.tc_id=tc.tc_id where trainer_id=?";
            ps=con.prepareStatement(sql);
            ps.setString(1, trainerId);
            rs=ps.executeQuery();
            if(rs.next()){
                tcm.setTcId(rs.getString("tc_id"));
                tcm.setTcName(rs.getString("tc_name"));
                tcm.setTrainerId(rs.getString("trainer_id"));
                tcm.setTrainerName(rs.getString("trainer_name"));
                tcm.setTrainerNameHindi(rs.getString("trainer_name_hindi"));
                tcm.setIfFulltime(rs.getString("if_fulltime"));
                tcm.setTrainerAadhaar(rs.getString("trainer_aadhaar"));
                tcm.setTrainerDesig(rs.getString("trainer_desig"));
                if (rs.getString("teaching_subject") != null && !rs.getString("teaching_subject").equals("")) {
                    tcm.setTeachingSubject(rs.getString("teaching_subject").split(","));
                } else {
                    tcm.setTeachingSubject(null);
                }
                tcm.setTrainerEduLevel(rs.getString("trainer_edu_level"));
                tcm.setTrainerProfEdu(rs.getString("trainer_prof_edu"));
                if (rs.getString("trainer_edu_subject") != null && !rs.getString("trainer_edu_subject").equals("")) {
                    tcm.setTrainerEduSubject(rs.getString("trainer_edu_subject").split(","));
                } else {
                    tcm.setTrainerEduSubject(null);
                }
                tcm.setTrainerExperience(rs.getString("trainer_experience"));
                tcm.setIfJobTraining(rs.getString("if_job_training"));
                tcm.setJobTrainYear(rs.getInt("job_train_Year"));
                tcm.setRefreshTraining(rs.getString("refresh_training"));
                
                
            
            
            
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

        return tcm;
    }

    @Override
    public void updateTrainerMasterData(TrainerMasterModel tcm) {
        Connection con = null;
        PreparedStatement ps = null;
        String teachsub = "";
        String edusub = "";

        try {
            con = this.dataSource.getConnection();
            if (tcm.getTeachingSubject() != null && tcm.getTeachingSubject().length > 0) {
                for (int i = 0; i < tcm.getTeachingSubject().length; i++) {
                    if (teachsub != null && !teachsub.equals("")) {
                        teachsub = teachsub + "," + tcm.getTeachingSubject()[i];
                    } else {
                        teachsub = tcm.getTeachingSubject()[i];
                    }
                }
            }

            if (tcm.getTrainerEduSubject() != null && tcm.getTrainerEduSubject().length > 0) {
                for (int i = 0; i < tcm.getTrainerEduSubject().length; i++) {
                    if (edusub != null && !edusub.equals("")) {
                        edusub = edusub + "," + tcm.getTrainerEduSubject()[i];
                    } else {
                        edusub = tcm.getTrainerEduSubject()[i];
                    }
                }
            }

            ps = con.prepareStatement("UPDATE trainer set tc_id=?,trainer_name=?,trainer_name_hindi=?,if_fulltime=?,trainer_aadhaar=?,"
                    + "trainer_desig=?,teaching_subject=?,trainer_edu_level=?,trainer_prof_edu=?,trainer_edu_subject=?,"
                    + "trainer_experience=?,if_job_training=?,job_train_Year=?,refresh_training=? where trainer_id=?");

            ps.setString(1, tcm.getTcId());
            ps.setString(2, tcm.getTrainerName());
            ps.setString(3, tcm.getTrainerNameHindi());
            ps.setString(4, tcm.getIfFulltime());
            ps.setString(5, tcm.getTrainerAadhaar());
            ps.setString(6, tcm.getTrainerDesig());
            ps.setString(7, teachsub);
            ps.setString(8, tcm.getTrainerEduLevel());
            ps.setString(9, tcm.getTrainerProfEdu());;
            ps.setString(10, edusub);
            ps.setString(11, tcm.getTrainerExperience());
            ps.setString(12, tcm.getIfJobTraining());
            ps.setInt(13, tcm.getJobTrainYear());
            ps.setString(14, tcm.getRefreshTraining());
            ps.setString(15, tcm.getTrainerId());
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
    public void deleteTrainerMasterData(String trainerId) {
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("DELETE FROM trainer where trainer_id=?");
            ps.setString(1, trainerId);
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
