/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.roomtype;

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
public class RoomTypeDAOImpl implements RoomTypeDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getRoomTypeList(Locale locale) {
        Map<String, String> roomTypeList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT room_type_id,room_type,room_type_hindi FROM g_room_type order by room_type_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    roomTypeList.put(rs.getString("room_type_id"), rs.getString("room_type_hindi"));
                }
            } else {
                while (rs.next()) {
                    roomTypeList.put(rs.getString("room_type_id"), rs.getString("room_type"));
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
        return roomTypeList;
    }
}
