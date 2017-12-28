/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.sector;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface SectorDAO {
    public Map<String, String> getSectorList(Locale locale);
    public Map<String, String> getSectorListProjectWise(Locale locale,String projectCode);
    public Map<String, String> getSectorListFilter(Locale locale, String sectorCode);
    public String getProjectCode(String sectorCode);
    
}
