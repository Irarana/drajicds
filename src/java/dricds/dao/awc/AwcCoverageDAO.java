/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.awc;

import dricds.model.awc.AwcHabitationModel;
import java.util.List;

/**
 *
 * @author Bibek Sa
 */
public interface AwcCoverageDAO {
    
    public List getAwcHabitationList(String awcCode);
    
    public void deleteAwcHabitationList(String habitationCode);
    
    public void deleteHabitationMap(String habitationCode);
            
    public AwcHabitationModel editHabitationData(String habitationCode);  
    
    public void addHabitationData(AwcHabitationModel awcHabi);
    
    public void updateHabitationData(AwcHabitationModel awcHabi);
    
    public String getAWCCompleteName(String awcCode);
}
