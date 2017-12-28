/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.trainermaster;

import dricds.model.trainermaster.TrainerMasterModel;
import java.util.List;

/**
 *
 * @author surendra
 */
public interface TrainerMasterDAO {
    
    public List getTrainerMasterList();
    
    public void addTrainerMasterData(TrainerMasterModel tcm);
    
    public TrainerMasterModel editTrainerMasterData(String trainerId);
    
    public void updateTrainerMasterData(TrainerMasterModel tcm);
    
    public void deleteTrainerMasterData(String trainerId);
    
}
