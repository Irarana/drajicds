/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.block;

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
public class BlockDAOImpl implements BlockDAO{
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getBlockList(Locale locale) {
        Map<String, String> blockList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT block_code,block_name,block_name_hindi FROM g_block order by block_name");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    blockList.put(rs.getString("block_code"), rs.getString("block_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    blockList.put(rs.getString("block_code"), rs.getString("block_name"));
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
        return blockList;
    }
    
    public Map<String, String> getBlockListDistrictWise(Locale locale,String districtCode){
        Map<String, String> blockList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT block_code,block_name,block_name_hindi FROM g_block where district_code=? order by block_name");
            ps.setString(1, districtCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    blockList.put(rs.getString("block_code"), rs.getString("block_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    blockList.put(rs.getString("block_code"), rs.getString("block_name"));
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
        return blockList;
    }
            
}
