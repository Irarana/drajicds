/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.gp;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface GPDAO {
    public Map<String, String> getGPList(Locale locale);
    
    public Map<String, String> getGPListBlockWise(Locale locale,String blockCode);
}
