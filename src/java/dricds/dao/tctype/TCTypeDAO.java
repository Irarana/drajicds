/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.tctype;

import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface TCTypeDAO {
    public Map<String, String> getTrainingCenterTypeList(Locale locale);
}
