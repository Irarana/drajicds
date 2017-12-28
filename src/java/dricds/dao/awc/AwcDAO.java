package dricds.dao.awc;

import dricds.common.ListPageObject;
import dricds.model.awc.AwcForm;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface AwcDAO {

    public Map<String, String> getAnganWadiCenterList(Locale locale);

    public Map<String, String> getAnganWadiCenterListSectorWise(Locale locale, String sectorCode);

    public Map<String, String> getCenterListFilter(Locale locale, String awcCode);

    public String getSectorCode(String sectorCode);

    public ListPageObject getAwcList(AwcForm awc);

    public void addAwcData(AwcForm awc);

    public AwcForm viewAWCProfileData(String awcCode);

    public ArrayList getAwcTypeList(Locale locale);

    public ArrayList getDistrictName(Locale locale);

    public ArrayList getProjectName(String distCode, Locale locale);

    public ArrayList getSectorName(String projectCode, Locale locale);

    public ArrayList getDivisionName(Locale locale);

    public ArrayList getSubDivisionName(String distCode, Locale locale);

    public ArrayList getTehsilName(String subDivCode, String distCode, Locale locale);

    public ArrayList getVillageName(String tehsilCode, String subdivCode, String distCode, Locale locale);

    public ArrayList getBlockName(String distCode, Locale locale);

    public ArrayList getGpName(String distCode, String blockCode, Locale locale);

    public ArrayList getULBType(Locale locale);

    public ArrayList getULBName(String distCode, Locale locale);

    public ArrayList getLSName(Locale locale);

    public ArrayList getVSName(Locale locale);

    public ArrayList getProximity(Locale locale);

    public ArrayList getAnganwadiWorker();

    public ArrayList getAnganwadiHelper();

    public ArrayList getAshaWorker();

    public ArrayList getDistance(Locale locale);

    public ArrayList getBuilderOwner(Locale locale);

    public ArrayList getBuildingStructure(Locale locale);

    public ArrayList getSpace(Locale locale);

    public ArrayList getToiletType(Locale locale);

    public ArrayList getDrinkingWaterSource(Locale locale);

    public ArrayList getAccessOfElectricity(Locale locale);

    public ArrayList getSchoolName(String distCode, String blockCode, Locale locale);

    public ArrayList getSchoolType(Locale locale);

    public AwcForm getAwcData(String awcCode);

    public void updateAwcData(AwcForm awc);

    public String getLocalizedText(String textHn, Locale locale);

    public String getAwcName(String awcId);

    public ArrayList getPHCName(String distCode, String blockCode, String ulbCode, Locale locale);

    public ArrayList getSubCenterName(String phcCode, Locale locale);

    public void deleteAwcData(String awcCode);

    public String getSchoolName(String schoolCode);

}
