/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.common;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Bibek Sa
 */
public class Datasource {

    static Connection con = null;

    public static Connection getConnection() {
        try {

            //ResourceBundle ecsLdapConf = ResourceBundle.getBundle(LdapConstants.LDAP_CONFIGURATION_FILE);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String URL = "jdbc:mysql://localhost:3306/rajicds?useUnicode=yes&characterEncoding=UTF-8";
            String dbName="rajicds";
            String UserId = "root";
            String Password = "password";
            String dbstr=URL+"databasename="+dbName+";user="+UserId+";password="+Password+";useUnicode=yes;characterEncoding=UTF-8;";
            System.out.println("dbstr::" + URL);
            con = DriverManager.getConnection(URL,UserId,Password);
            System.out.println("after connection::");
        } catch (Exception e) {
            System.out.println("YOUR ERROR IS HERE" + e.toString());
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] a) {
        Connection con = null;
        try {
            con = Datasource.getConnection();
            Statement st=con.createStatement();
            //st.executeUpdate("USE rajicds");
            //String str = URLDecoder.decode( "बिबेक", "UTF-8" );
            String str = "बिबेक";
            st.executeUpdate("UPDATE  awc_master SET AWC_Name_Hindi='"+str+"' WHERE AWC_Code=2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
