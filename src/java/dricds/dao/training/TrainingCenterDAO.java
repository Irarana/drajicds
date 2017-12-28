/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.training;

import dricds.common.ListPageObject;
import dricds.model.training.TrainingCenterModel;
import java.util.List;

/**
 *
 * @author surendra
 */
public interface TrainingCenterDAO {
    
    public ListPageObject getTrainingCenterList(TrainingCenterModel tcm);
    
    public void addTrainiingCenterData(TrainingCenterModel tcm);
    
    public TrainingCenterModel editTrainiingCenterData(String trainingCenterId);
    
    public void updateTrainiingCenterData(TrainingCenterModel tcm);
    
    public String getTrainingCenterName(String trainingCenterId);
    
    public void deleteTrainingData(String trainingCenterId);

}
