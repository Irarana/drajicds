/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.maxcode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author surendra
 */
public class MaxCodeDAOImpl implements MaxCodeDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public int getMaxCode(String tablename, String columnName){
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        int maxid=1;
        try{
            con=this.dataSource.getConnection();
            
            ps=con.prepareStatement("SELECT max(cast("+columnName+" AS UNSIGNED))+1 maxid  FROM "+tablename);
            rs=ps.executeQuery();
            if(rs.next()){
                maxid=rs.getInt("MAXID");
            }
        } catch (SQLException e) {
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
        return maxid;
    }
    @Override
    public String getMaxNumber(String tablename, String columnName){
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String maxid="";
        try{
            con=this.dataSource.getConnection();
            System.out.println("SELECT max(("+columnName+"))+1 maxid  FROM "+tablename);
            ps=con.prepareStatement("SELECT max(("+columnName+"))+1 maxid  FROM "+tablename);
            rs=ps.executeQuery();
            if(rs.next()){
               
                maxid=rs.getString("MAXID");
            }
            if(maxid==null){
               maxid="0001";
            }
        } catch (SQLException e) {
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
        return maxid;
    }
}
