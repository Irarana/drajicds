    

package dricds.dao.frontlineWorker;

import dricds.common.ListPageObject;
import dricds.model.frontlineWorker.FrontlineWorkerModel;
import java.util.List;

/**
 *
 * @author surendra
 */
public interface FrontlineWorkerDAO {
    
    public ListPageObject getFrontlineWorkerList(FrontlineWorkerModel fwm);
            
    public void addFrontlineWorkerData(FrontlineWorkerModel fwm);
    
    public FrontlineWorkerModel editFrontlineWorkerData(String workerId);
    
    public void updateFrontlineWorkData(FrontlineWorkerModel fwm);
    
    public void deleteFrontlineWorkData(String workerId);
    
    public FrontlineWorkerModel viewFrontlineWorkerData(String workerId);
}
