/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.project;

import dricds.model.project.Awc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Mana Jena
 */
public class AwcDAOImpl implements AwcDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Awc getAwcDetail(int awcCode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Awc awc = new Awc();
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM awc_master WHERE AWC_CODE=?");
            ps.setInt(1, awcCode);
            rs = ps.executeQuery();
            if(rs.next()){
               awc.setAwcCode(awcCode);
               awc.setAwcName(rs.getString("AWC_Name_English"));
               awc.setDistrict(rs.getString("Dist_Name"));
               awc.setSector(rs.getString("Sector_Name"));
               awc.setDivision(rs.getString("Division_Name"));
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
        return awc;
    }

}
