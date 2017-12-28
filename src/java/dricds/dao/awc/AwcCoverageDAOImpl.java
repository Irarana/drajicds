/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.awc;

import dricds.common.DataBaseFunctions;
import dricds.model.awc.AwcHabitationModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Bibek Sa
 */
public class AwcCoverageDAOImpl implements AwcCoverageDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List getAwcHabitationList(String awcCode) {
        List li = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AwcHabitationModel awcHabi = null;

        try {

            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT habitation_code,awc_code,habitation_name,"
                    + "habitation_description,map_colour,map_longlat FROM awc_habitation WHERE awc_code=?");
            ps.setString(1, awcCode);
            rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                awcHabi = new AwcHabitationModel();
                awcHabi.setSlno(i++);
                awcHabi.setAwcCode(rs.getString("awc_code"));
                awcHabi.setHabitationCode(rs.getString("habitation_code"));
                awcHabi.setHabitationDescription(rs.getString("habitation_description"));
                awcHabi.setHabitationName(rs.getString("habitation_name"));
                awcHabi.setMapColour(rs.getString("map_colour"));
                awcHabi.setMapLonglat(rs.getString("map_longlat"));
                li.add(awcHabi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return li;
    }

    @Override
    public void deleteAwcHabitationList(String habitationCode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = dataSource.getConnection();
            ps = con.prepareStatement("DELETE FROM awc_habitation WHERE habitation_code=?");
            ps.setString(1, habitationCode);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
    }

    @Override
    public void deleteHabitationMap(String habitationCode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = dataSource.getConnection();
            ps = con.prepareStatement("UPDATE  awc_habitation SET map_longlat=null WHERE habitation_code=?");
            ps.setString(1, habitationCode);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
    }

    @Override
    public AwcHabitationModel editHabitationData(String habitationCode) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AwcHabitationModel awcHabi = null;
        try {

            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT habitation_code,awc_code,habitation_name,"
                    + "habitation_description,map_colour,map_longlat FROM awc_habitation WHERE habitation_code=?");
            ps.setString(1, habitationCode);
            rs = ps.executeQuery();
            awcHabi = new AwcHabitationModel();
            if (rs.next()) {
                awcHabi.setHabitationCode(rs.getString("habitation_code"));
                awcHabi.setAwcCode(rs.getString("awc_code"));
                awcHabi.setHabitationName(rs.getString("habitation_name"));
                awcHabi.setHabitationDescription(rs.getString("habitation_description"));
                awcHabi.setMapColour(rs.getString("map_colour"));
                awcHabi.setMapLonglat(rs.getString("map_longlat"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return awcHabi;
    }

    @Override
    public void addHabitationData(AwcHabitationModel awcHabi) {

        Connection con = null;
        PreparedStatement ps = null;
        try {
            
            con = dataSource.getConnection();
            ps = con.prepareStatement("INSERT INTO awc_habitation (habitation_code,awc_code,habitation_name,"
                    + "habitation_description,map_colour,map_longlat) VALUES(?,?,?,?,?,?)");
            ps.setString(1, awcHabi.getHabitationCode());
            ps.setString(2, awcHabi.getAwcCode());
            ps.setString(3, awcHabi.getHabitationName());
            ps.setString(4, awcHabi.getHabitationDescription());
            if(awcHabi.getMapColour()!=null && !awcHabi.getMapColour().equals("")){
                ps.setString(5, awcHabi.getMapColour());
            }else{
                ps.setString(5, "#337ab7");
            }
            ps.setString(6, awcHabi.getMapLonglat());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
    }

    @Override
    public void updateHabitationData(AwcHabitationModel awcHabi) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            
            con = dataSource.getConnection();
            ps = con.prepareStatement("UPDATE awc_habitation set awc_code=?,habitation_name=?,"
                    + "habitation_description=?,map_colour=?,map_longlat=? WHERE habitation_code=?");
            
            ps.setString(1, awcHabi.getAwcCode());
            ps.setString(2, awcHabi.getHabitationName());
            ps.setString(3, awcHabi.getHabitationDescription());
            ps.setString(4, awcHabi.getMapColour());
            ps.setString(5, awcHabi.getMapLonglat());
            ps.setString(6, awcHabi.getHabitationCode());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
    }
    
    @Override
    public String getAWCCompleteName(String awcCode){
        String compName = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT AWC_Name_English,g_sector.sector_name,g_project.project_name,g_district.district_name FROM awc_master\n" +
                                        "LEFT OUTER JOIN g_sector ON awc_master.sector_code=g_sector.sector_code\n" +
                                        "LEFT OUTER JOIN g_project ON awc_master.project_code=g_project.project_code\n" +
                                        "LEFT OUTER JOIN g_district ON awc_master.dist_code=g_district.district_code\n" +
                                        "WHERE awc_code=?");
            ps.setString(1, awcCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                compName = rs.getString("AWC_Name_English")+", Sector: "+rs.getString("sector_name")+", Project: "+rs.getString("project_name")+", District: "+rs.getString("district_name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return compName;
    }
}
