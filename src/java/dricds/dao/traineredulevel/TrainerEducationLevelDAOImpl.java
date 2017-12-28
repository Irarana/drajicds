/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.traineredulevel;

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
public class TrainerEducationLevelDAOImpl implements TrainerEducationLevelDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getTrainerEduLevelList(Locale locale) {

        Map<String, String> eduLevelList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT trainer_edu_level_id,trainer_edu_level,trainer_edu_level_hindi FROM g_trainer_edu_level order by trainer_edu_level_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    eduLevelList.put(rs.getString("trainer_edu_level_id"), rs.getString("trainer_edu_level_hindi"));
                }
            } else {
                while (rs.next()) {
                    eduLevelList.put(rs.getString("trainer_edu_level_id"), rs.getString("trainer_edu_level"));
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
        return eduLevelList;
    }

}
