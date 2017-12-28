/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.project;

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
public class ProjectDAOImpl implements ProjectDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getProjectList(Locale locale) {
        Map<String, String> proList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT project_code,project_name,project_name_hindi FROM g_project order by project_name");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    proList.put(rs.getString("project_code"), rs.getString("project_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    proList.put(rs.getString("project_code"), rs.getString("project_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return proList;
    }
    
    public Map<String, String> getProjectListDistrictWise(Locale locale,String distcode){
        Map<String, String> proList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT project_code,project_name,project_name_hindi FROM g_project where district_code=? order by project_name");
            ps.setString(1, distcode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    proList.put(rs.getString("project_code"), rs.getString("project_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    proList.put(rs.getString("project_code"), rs.getString("project_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return proList;
    }
    
    @Override
    public Map<String, String> getProjectListFilter(Locale locale, String proCode){
        Map<String, String> proList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT project_code,project_name,project_name_hindi FROM g_project where project_code=? order by project_name");
            ps.setString(1, proCode);
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    proList.put(rs.getString("project_code"), rs.getString("project_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    proList.put(rs.getString("project_code"), rs.getString("project_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return proList;
    }
    
    @Override
    public String getDistrictCode(String projectCode) {
        String distcode = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT district_code FROM g_project WHERE project_code=? ");
            ps.setString(1, projectCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                distcode = rs.getString("district_code");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return distcode;
    }
}
