package dricds.dao.icds;

import dricds.common.ListPageObject;
import dricds.model.frontlineWorker.FrontlineWorkerModel;
import dricds.model.icds.ICDSModel;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author surendra
 */
public interface ICDSDAO {

    public ListPageObject getICDSProjectList(ICDSModel icdsm, Locale locale);

    public void deleteICDSProject(String projectCode);

    public void addICDSProjectData(ICDSModel icdsm, String officeId, String addMode);

    public ICDSModel viewIcdsProjectData(String projCode, Locale local, String mode);

    public ListPageObject getICDSSanctionPostList(String projCode, String level);

    public Map<String, String> getDefaultPost(Locale locale);

    public String getHrmsIdDetails(String hrmsId);

    public Map<String, String> getDefaultTime(Locale locale);

    public void updateIncumbency(ICDSModel icdsModel);

    public void updateIncumbencyForRelieve(ICDSModel icdsModel);

    public ListPageObject getIncumbencyChartList(String substattivePostId, String postCode, Locale locale);

    public void addICDSProjectEditData(ICDSModel icdsm, String officeId);

    public ListPageObject getICDSSanctionPostList1(String projCode, String level);

    public int getCountPost(String OfficeLevel);

    public int getSubsCountPost(String OfficeId);

    public Map<String, ICDSModel> getMasterPlanPost(String OfficeLevel, String officeId, Locale locale);

    public Map<String, ICDSModel> getSavedPost(String OfficeLevel, String officeId, Locale locale);

    public void updateICDSProjectEditData(ICDSModel icdsModel, String officeId);

    public Map<String, String> getProjectListFilter(Locale locale, String distId);

    public Map<String, String> getDistrictList(Locale locale);

    public Map<String, String> getDistrictListFilter(Locale locale, String distId);

    public Map<String, String> getDistrictListOfficeCodeFilter(Locale locale, String officeCode);

    public Map<String, String> getProjectList(Locale locale);

    public Map<String, String> getDistrictFromProject(Locale locale, String projId);

    public void addIncumbency(ICDSModel icdsModel);

    public Map<String, String> getSectorListFilter(Locale locale, String distId);

    public ICDSModel getIncumbencyChartById(int incumbencyId);

    public ICDSModel getLastIncumbencyBySPId(String substattivePostId, Locale locale);
}
