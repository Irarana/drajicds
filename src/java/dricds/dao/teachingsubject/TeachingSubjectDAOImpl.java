/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.teachingsubject;

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
public class TeachingSubjectDAOImpl implements TeachingSubjectDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Map<String, String> getTeachingSubjectList(Locale locale){
        Map<String, String> subjectList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT teaching_subject_id,teaching_subject,teaching_subject_hindi FROM g_teaching_subject order by teaching_subject_id");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    subjectList.put(rs.getString("teaching_subject_id"), rs.getString("teaching_subject_hindi"));
                }
            } else {
                while (rs.next()) {
                    subjectList.put(rs.getString("teaching_subject_id"), rs.getString("teaching_subject"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return subjectList;
    }
}
