/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.traineredusubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author surendra
 */
public class TrainerEducationalSubjectDAOImpl implements TrainerEducationalSubjectDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    public Map<String, String> getTrainerEduSubList(Locale locale){
        
        Map<String, String> eduList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT trainer_edu_subject_id,trainer_edu_subject,trainer_edu_subject_hindi FROM g_trainer_edu_subject order by trainer_edu_subject_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    eduList.put(rs.getString("trainer_edu_subject_id"), rs.getString("trainer_edu_subject_hindi"));
                }
            } else {
                while (rs.next()) {
                    eduList.put(rs.getString("trainer_edu_subject_id"), rs.getString("trainer_edu_subject"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return eduList;
    }
}
