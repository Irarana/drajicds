/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.login;

import dricds.common.DataBaseFunctions;
import dricds.model.login.LoginModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author surendra
 */
public class LoginDAOImpl implements LoginDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public LoginModel validateUser(String userid, String password){
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        LoginModel login=null;
        try{
            con=dataSource.getConnection();
            ps=con.prepareStatement("SELECT uid,pwd,role,level,level_area_code,user_profile_id,office_code,post_code,email,created_by,created_on  FROM user_mast WHERE uid=? AND pwd=?");
            ps.setString(1, userid);
            ps.setString(2, password);
            rs=ps.executeQuery();
            if(rs.next()){
                login=new LoginModel();
                login.setUserid(rs.getString("uid"));
                login.setPassword(rs.getString("pwd"));
                login.setRole(rs.getString("role"));
                login.setLevel(rs.getString("level"));
                login.setLevelAreaCode(rs.getString("level_area_code"));
                login.setProfileId(rs.getString("user_profile_id"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            DataBaseFunctions.closeSqlObjects(con);
        }
        return login;
    }
}
