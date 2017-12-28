/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.gp;

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
public class GPDAOImpl implements GPDAO{
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getGPList(Locale locale) {
        Map<String, String> gpList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT gp_code,gp_name,gp_name_hindi FROM g_gp order by gp_name");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    gpList.put(rs.getString("gp_code"), rs.getString("gp_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    gpList.put(rs.getString("gp_code"), rs.getString("gp_name"));
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
        return gpList;
    }
    
    public Map<String, String> getGPListBlockWise(Locale locale,String blockcode){
        Map<String, String> gpList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT gp_code,gp_name,gp_name_hindi FROM g_gp where block_code=? order by gp_name");
            ps.setString(1, blockcode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    gpList.put(rs.getString("gp_code"), rs.getString("gp_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    gpList.put(rs.getString("gp_code"), rs.getString("gp_name"));
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
        return gpList;
    }
}
