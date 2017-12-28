/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.hostel;

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
public class HostelFacilityDAOImpl implements HostelFacilityDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getHostelFacilitiesList(Locale locale) {
        Map<String, String> facilitiesList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT hostel_facility_id,hostel_facility,hostel_facility_hindi FROM g_hostel_facilities order by hostel_facility_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    facilitiesList.put(rs.getString("hostel_facility_id"), rs.getString("hostel_facility_hindi"));
                }
            } else {
                while (rs.next()) {
                    facilitiesList.put(rs.getString("hostel_facility_id"), rs.getString("hostel_facility"));
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
        return facilitiesList;
    }
}
