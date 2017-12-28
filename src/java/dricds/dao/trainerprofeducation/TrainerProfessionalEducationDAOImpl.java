/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.trainerprofeducation;

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
public class TrainerProfessionalEducationDAOImpl implements TrainerProfessionalEducationDAO{
 
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Map<String, String> getTrainerProfEduList(Locale locale){
    Map<String, String> profEduList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT trainer_prof_edu_id,trainer_prof_edu,trainer_prof_edu_hindi FROM g_trainer_prof_edu order by trainer_prof_edu_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    profEduList.put(rs.getString("trainer_prof_edu_id"), rs.getString("trainer_prof_edu_hindi"));
                }
            } else {
                while (rs.next()) {
                    profEduList.put(rs.getString("trainer_prof_edu_id"), rs.getString("trainer_prof_edu"));
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return profEduList;
    }
}
