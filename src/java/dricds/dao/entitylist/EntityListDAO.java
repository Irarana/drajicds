/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.entitylist;

import dricds.model.entitylist.Indicator;
import dricds.model.entitylist.QueryParamField;
import dricds.model.entitylist.SelectField;
import dricds.model.entitylist.SpecialCondition;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
/**
 *
 * @author Bibek Sa
 */
public interface EntityListDAO {
    public Map<String, String> getDistrictList(Locale locale);
    public ArrayList<QueryParamField> getSagrigatingParams(String mqid);
    public ArrayList<QueryParamField> getFilterParams(String mqid);
    public ArrayList<QueryParamField> getFilterParams(String mqid,String filterCondition);
    public ArrayList<SelectField> getTimeDimension(String qid);
    public ArrayList<SelectField> getLocationDimension(String qid);
    public ArrayList<SpecialCondition> getSpecialConditions(String mqid);
    public String getSelectParamFieldName(String qid,String paramField,Statement st);
    public String getSelectParamAppliedOnField(String paramId, Statement st);
    public String getIdenticalColumnName(String mqid, String fldName, String iqid);
    public ArrayList<Indicator> getIndicators(String mqid);
    public int getColumnSl(ResultSetMetaData rsmd, String columnName);
}
