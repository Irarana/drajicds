/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.block;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface BlockDAO {
    public Map<String, String> getBlockList(Locale locale);
    
    public Map<String, String> getBlockListDistrictWise(Locale locale,String districtCode);
    
    
}
