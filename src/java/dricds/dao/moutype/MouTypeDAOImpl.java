
package dricds.dao.moutype;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;


public class MouTypeDAOImpl implements MouTypeDAO{
    
    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

         @Override
    public Map<String, String> getMouTypeList(Locale locale) {
        Map<String, String> sponsorTypeList = new LinkedHashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT MoU_Type_Id,MoU_Type,MoU_Type_Hindi FROM g_mou_type order by MoU_Type_Id asc");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    sponsorTypeList.put(rs.getString("MoU_Type_Id"),rs.getString("MoU_Type"));
                }
            } else {
                while (rs.next()) {
                    sponsorTypeList.put(rs.getString("MoU_Type_Id"),rs.getString("MoU_Type"));
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
        return sponsorTypeList;
    }
     
}
