/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.project;

import dricds.model.project.PackageProj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Mana Jena
 */
public class PackageDAOImpl implements PackageDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List getPackageList(int projectId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List packageList = new ArrayList();
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM t_package_master WHERE PID=?");
            ps.setInt(1, projectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                PackageProj packageObj = new PackageProj();
                packageObj.setPid(rs.getInt("PID"));
                packageObj.setPpid(rs.getInt("PPID"));
                packageObj.setPlanno(rs.getString("PLAN_NO"));
                packageObj.setPackagedesc(rs.getString("PACKAGE_DESC"));
                packageList.add(packageObj);
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
        return packageList;

    }

}
