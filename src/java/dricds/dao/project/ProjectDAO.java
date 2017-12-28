/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.project;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface ProjectDAO {
    
    public Map<String, String> getProjectList(Locale locale);
    public Map<String, String> getProjectListDistrictWise(Locale locale,String distCode);
    public Map<String, String> getProjectListFilter(Locale locale, String proCode);
    public String getDistrictCode(String projectCode);
}
