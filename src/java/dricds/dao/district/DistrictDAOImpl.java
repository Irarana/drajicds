/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.district;

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
public class DistrictDAOImpl implements DistrictDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getDistrictList(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT district_code,district_name,district_name_hindi FROM g_district order by district_name");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("district_code"), rs.getString("district_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("district_code"), rs.getString("district_name"));
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
    public Map<String, String> getDistrictListFilter(Locale locale, String distcode) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT district_code,district_name,district_name_hindi FROM g_district WHERE district_code=? order by district_name");
            ps.setString(1, distcode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("district_code"), rs.getString("district_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("district_code"), rs.getString("district_name"));
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

    
}
