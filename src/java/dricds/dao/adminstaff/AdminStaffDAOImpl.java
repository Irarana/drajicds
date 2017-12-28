/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.adminstaff;

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
public class AdminStaffDAOImpl implements AdminStaffDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, String> getAdminStaffList(Locale locale) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, String> adminstaffs = new HashMap<String, String>();
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT admin_staffs_id,admin_staffs,admin_staffs_hindi FROM g_admin_staffs order by admin_staffs_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    adminstaffs.put(rs.getString("admin_staffs_id"), rs.getString("admin_staffs_hindi"));
                }
            } else {
                while (rs.next()) {
                    adminstaffs.put(rs.getString("admin_staffs_id"), rs.getString("admin_staffs"));
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
        return adminstaffs;
    }
}
