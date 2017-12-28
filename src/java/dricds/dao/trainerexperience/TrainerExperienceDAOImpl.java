/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.trainerexperience;

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
public class TrainerExperienceDAOImpl implements TrainerExperienceDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    public Map<String, String> getTrainerExperienceList(Locale locale){
        
        Map<String, String> eduList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT trainer_experience_id,trainer_experience,trainer_experience_hindi FROM g_trainer_experience order by trainer_experience_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    eduList.put(rs.getString("trainer_experience_id"), rs.getString("trainer_experience_hindi"));
                }
            } else {
                while (rs.next()) {
                    eduList.put(rs.getString("trainer_experience_id"), rs.getString("trainer_experience"));
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
