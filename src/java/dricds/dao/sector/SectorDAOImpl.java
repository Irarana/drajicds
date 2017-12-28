/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.sector;

import dricds.common.DataBaseFunctions;
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
public class SectorDAOImpl implements SectorDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getSectorList(Locale locale) {
        Map<String, String> sectorList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT sector_code,sector_name,sector_name_hindi FROM g_sector order by sector_name");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    sectorList.put(rs.getString("sector_code"), rs.getString("sector_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    sectorList.put(rs.getString("sector_code"), rs.getString("sector_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return sectorList;
    }

    public Map<String, String> getSectorListProjectWise(Locale locale, String projectCode) {
        Map<String, String> sectorList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT sector_code,sector_name,sector_name_hindi FROM g_sector where project_code=? order by sector_name");
            ps.setString(1, projectCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    sectorList.put(rs.getString("sector_code"), rs.getString("sector_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    sectorList.put(rs.getString("sector_code"), rs.getString("sector_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return sectorList;
    }

    @Override
    public Map<String, String> getSectorListFilter(Locale locale, String sectorCode) {
        Map<String, String> sectorList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT sector_code,sector_name,sector_name_hindi FROM g_sector where sector_code=? order by sector_name");
            ps.setString(1, sectorCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    sectorList.put(rs.getString("sector_code"), rs.getString("sector_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    sectorList.put(rs.getString("sector_code"), rs.getString("sector_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return sectorList;
    }

    @Override
    public String getProjectCode(String sectorCode) {
        String procode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            System.out.println("SELECT project_code FROM g_sector WHERE project_code="+sectorCode);
            ps = con.prepareStatement("SELECT project_code FROM g_sector WHERE project_code=? ");
            ps.setString(1, sectorCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                procode = rs.getString("project_code");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return procode;
    }
}
