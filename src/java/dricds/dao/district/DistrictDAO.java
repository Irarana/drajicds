/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.district;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface DistrictDAO {
    public Map<String, String> getDistrictList(Locale locale);
    public Map<String, String> getDistrictListFilter(Locale locale, String distcode);
    
}
