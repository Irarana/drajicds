/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.adminstaff;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface AdminStaffDAO {
    
    public Map<String, String> getAdminStaffList(Locale locale);
    
}
