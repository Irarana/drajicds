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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Bibek Sa
 */
public class EntityListDAOImpl implements EntityListDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, String> getDistrictList(Locale locale) {
        Map<String, String> centerList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();

            ps = con.prepareStatement("SELECT district_code,district_name,district_name_hindi FROM g_district order by district_code");
            rs = ps.executeQuery();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                while (rs.next()) {
                    centerList.put(rs.getString("district_code"), rs.getString("district_name_hindi"));
                }
            } else {
                while (rs.next()) {
                    centerList.put(rs.getString("district_code"), rs.getString("district_name"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sq) {
                sq.printStackTrace();
            }
        }
        return centerList;
    }

    @Override
    public ArrayList<QueryParamField> getSagrigatingParams(String mqid) {
        ResultSet rs = null;
        Statement st = null;
        ResultSet rs1 = null;
        Statement st1 = null;
        Connection con = null;
        QueryParamField qpf = null;
        String tableName = "";
        String fieldName = "";
        ArrayList<String> alValues = new ArrayList<String>();
        String[] arrValues = null;
        ArrayList<QueryParamField> paramlist = new ArrayList<QueryParamField>();
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String sQuery = "select * from query_select_param where QID='" + mqid + "' and IF_TR_COL='Y' and ifnull(IF_CSV,'')<>'Y'";
            System.out.println(sQuery);
            rs = st.executeQuery(sQuery);
            while (rs.next()) {
                qpf = new QueryParamField();
                qpf.setParamid(rs.getString("SELECT_PARAM_ID"));
                qpf.setParamname(rs.getString("SELECT_PARAM"));
                tableName = rs.getString("PARAM_TABLE");
                fieldName = rs.getString("PARAM_FIELD");

                String sQuery1 = "select distinct " + fieldName + " from " + tableName + " ORDER BY " + fieldName;
                st1 = con.createStatement();
                System.out.println(sQuery1);
                rs1 = st1.executeQuery(sQuery1);
                alValues = new ArrayList<String>();
                while (rs1.next()) {
                    alValues.add(rs1.getString(fieldName));
                }
                if (alValues.size() > 0) {
                    arrValues = new String[alValues.size()];
                    for (int i = 0; i < alValues.size(); i++) {
                        arrValues[i] = alValues.get(i);
                    }
                    qpf.setParamvalues(arrValues);
                    qpf.setParamlables(arrValues);
                }
                paramlist.add(qpf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (rs1 != null) {
                    rs1.close();
                    rs1 = null;
                }
                if (st1 != null) {
                    st1.close();
                    st1 = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paramlist;
    }

    @Override
    public ArrayList<QueryParamField> getFilterParams(String mqid) {
        ResultSet rs = null;
        Statement st = null;
        ResultSet rs1 = null;
        Statement st1 = null;
        Connection con = null;
        QueryParamField qpf = null;
        String tableName = "";
        String fieldName = "";
        String sortingOrder = "";

        ArrayList<String> alValues = new ArrayList<String>();
        String[] arrValues = null;
        ArrayList<QueryParamField> paramlist = new ArrayList<QueryParamField>();
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String sQuery = "select * from query_select_param where QID='" + mqid + "' and DIMENSION IS NULL AND (IF_TR_COL='Y' OR IF_TR_ROW='Y')";
            System.out.println(sQuery);
            rs = st.executeQuery(sQuery);
            while (rs.next()) {
                qpf = new QueryParamField();
                qpf.setParamid(rs.getString("SELECT_PARAM_ID"));
                qpf.setParamname(rs.getString("SELECT_PARAM"));
                qpf.setCtrlType(rs.getString("control_type"));

                tableName = rs.getString("PARAM_TABLE");
                fieldName = rs.getString("PARAM_FIELD");
                sortingOrder = rs.getString("SORTING_ORDER");

                String sQuery1 = "";
                if (sortingOrder != null && !sortingOrder.trim().equalsIgnoreCase("")) {
                    String sortStr = "ORDER BY CASE " + fieldName + " ";
                    String[] ordflds = sortingOrder.split("\\,");
                    for (int i = 0; i < ordflds.length; i++) {
                        sortStr = sortStr + " WHEN '" + ordflds[i] + "' THEN " + (i + 1) + " ";
                    }
                    sortStr = sortStr + " END";

                    sQuery1 = "SELECT * FROM (select distinct " + fieldName + " from " + tableName + " WHERE " + fieldName + " IS NOT NULL) R " + sortStr;
                } else {
                    sQuery1 = "select distinct " + fieldName + " from " + tableName + " WHERE " + fieldName + " IS NOT NULL ORDER BY " + fieldName;
                }

                System.out.println("sQuery1::::::::-------------->" + sQuery1);

                st1 = con.createStatement();
                rs1 = st1.executeQuery(sQuery1);
                alValues = new ArrayList<String>();
                while (rs1.next()) {
                    alValues.add(rs1.getString(fieldName));
                }
                if (alValues.size() > 0) {
                    arrValues = new String[alValues.size()];
                    for (int i = 0; i < alValues.size(); i++) {
                        arrValues[i] = alValues.get(i);
                    }
                    qpf.setParamvalues(arrValues);
                    qpf.setParamlables(arrValues);
                }
                paramlist.add(qpf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (rs1 != null) {
                    rs1.close();
                    rs1 = null;
                }
                if (st1 != null) {
                    st1.close();
                    st1 = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paramlist;
    }

    @Override
    public ArrayList<QueryParamField> getFilterParams(String mqid, String filterCondition) {
        ResultSet rs = null;
        Statement st = null;
        ResultSet rs1 = null;
        Statement st1 = null;
        Connection con = null;
        QueryParamField qpf = null;
        String tableName = "";
        String fieldName = "";
        String sortingOrder = "";
        String ifcsv = "";
        System.out.println("filterCondition:" + filterCondition);
        ArrayList<String> alValues = new ArrayList<String>();
        String[] arrValues = null;
        ArrayList<QueryParamField> paramlist = new ArrayList<QueryParamField>();
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String sQuery = "select * from query_select_param where QID='" + mqid + "' and DIMENSION IS NULL AND (IF_TR_COL='Y' OR IF_TR_ROW='Y')";
            System.out.println(sQuery);
            rs = st.executeQuery(sQuery);
            while (rs.next()) {
                qpf = new QueryParamField();
                qpf.setParamid(rs.getString("SELECT_PARAM_ID"));
                qpf.setParamname(rs.getString("SELECT_PARAM"));
                qpf.setCtrlType(rs.getString("control_type"));
                qpf.setIfCSV(rs.getString("IF_CSV"));

                tableName = rs.getString("PARAM_TABLE");
                fieldName = rs.getString("PARAM_FIELD");
                sortingOrder = rs.getString("SORTING_ORDER");
                ifcsv = rs.getString("IF_CSV");

                String sQuery1 = "";
                if (sortingOrder != null && !sortingOrder.trim().equalsIgnoreCase("")) {
                    String sortStr = "ORDER BY CASE " + fieldName + " ";
                    String[] ordflds = sortingOrder.split("\\,");
                    for (int i = 0; i < ordflds.length; i++) {
                        sortStr = sortStr + " WHEN '" + ordflds[i] + "' THEN " + (i + 1) + " ";
                    }
                    sortStr = sortStr + " END";
                    if (filterCondition != null && !filterCondition.trim().equals("")) {
                        sQuery1 = "SELECT * FROM (select distinct " + fieldName + " from " + tableName + " WHERE " + fieldName + " IS NOT NULL AND " + filterCondition + ") R " + sortStr;
                    } else {
                        sQuery1 = "SELECT * FROM (select distinct " + fieldName + " from " + tableName + " WHERE " + fieldName + " IS NOT NULL) R " + sortStr;
                    }

                } else if (filterCondition != null && !filterCondition.trim().equals("")) {
                    sQuery1 = "select distinct " + fieldName + " from " + tableName + " WHERE " + fieldName + " IS NOT NULL  AND " + filterCondition + " ORDER BY " + fieldName;
                } else {
                    sQuery1 = "select distinct " + fieldName + " from " + tableName + " WHERE " + fieldName + " IS NOT NULL ORDER BY " + fieldName;
                }

                System.out.println("sQuery1::::::::-------------->" + sQuery1);

                st1 = con.createStatement();
                rs1 = st1.executeQuery(sQuery1);
                alValues = new ArrayList<String>();
                while (rs1.next()) {
                    if (ifcsv != null && ifcsv.trim().equalsIgnoreCase("Y")) {
                        String val = rs1.getString(fieldName);
                        if (val != null && !val.trim().equalsIgnoreCase("")) {
                            String[] arrval = val.split(",");
                            for (int i = 0; i < arrval.length; i++) {
                                if (!ifValueExist(alValues, arrval[i].trim())) {
                                    alValues.add(arrval[i].trim());
                                }
                            }
                        }
                    } else {
                        alValues.add(rs1.getString(fieldName));
                    }
                }
                if (alValues.size() > 0) {
                    arrValues = new String[alValues.size()];
                    for (int i = 0; i < alValues.size(); i++) {
                        arrValues[i] = alValues.get(i);
                    }
                    qpf.setParamvalues(arrValues);
                    qpf.setParamlables(arrValues);
                }
                paramlist.add(qpf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (rs1 != null) {
                    rs1.close();
                    rs1 = null;
                }
                if (st1 != null) {
                    st1.close();
                    st1 = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paramlist;
    }

    @Override
    public ArrayList<SelectField> getTimeDimension(String qid) {
        ResultSet rs = null;
        Statement st = null;
        Connection con = null;
        ArrayList<SelectField> alTimeFields = new ArrayList<SelectField>();
        SelectField sf = null;
        String strSELECT_PARAM_ID;
        String strQID;
        String strSELECT_PARAM;
        String strPARAM_TABLE;
        String strPARAM_FIELD;
        String strIS_DEFAULT;
        String strIS_DERIVED;
        String strUSER_FORMULA;
        String strSQL_FORMULA;

        String strIF_TR_ROW;
        String strIF_TR_COL;
        String strIF_TR_MEASURE;

        /*If user selected the parameter to view in select clause*/
        String ifSelectedByUserToView;
        String strSELECT_PARAM_IMPL_ID;
        String strSLNO;
        /*If user selected the parameter to group by*/
        String ifSelectedByUserToGroupBy;
        String strGROUPBY_PARAM_ID;
        String strGB_SLNO;

        String IfSelectedByUserToTransRow;
        String IfSelectedByUserToTransCol;
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String sQuery = "SELECT query_select_param.SELECT_PARAM_ID,query_select_param.QID,query_select_param.SELECT_PARAM,query_select_param.PARAM_TABLE,query_select_param.PARAM_FIELD,query_select_param.IS_DEFAULT,query_select_param.IS_DERIVED,query_select_param.USER_FORMULA,query_select_param.SQL_FORMULA,query_select_param.IF_TR_ROW,query_select_param.IF_TR_COL,query_select_param.IF_TR_MEASURE, '' ifSelectedByUserToView,'' SELECT_PARAM_IMPL_ID,'' SLNO, '' ifSelectedByUserToGroupBy,'' GROUPBY_PARAM_ID,'' GB_SLNO,DIM_SLNO FROM (select * from query_select_param where qid=" + qid + " and DIMENSION='TIME') query_select_param order by DIM_SLNO";
            System.out.println(sQuery);
            rs = st.executeQuery(sQuery);
            while (rs.next()) {
                strSELECT_PARAM_ID = rs.getString("SELECT_PARAM_ID");
                strQID = rs.getString("QID");
                strSELECT_PARAM = rs.getString("SELECT_PARAM");
                strPARAM_TABLE = rs.getString("PARAM_TABLE");
                strPARAM_FIELD = rs.getString("PARAM_FIELD");
                strIS_DEFAULT = rs.getString("IS_DEFAULT");
                strIS_DERIVED = rs.getString("IS_DERIVED");
                strUSER_FORMULA = rs.getString("USER_FORMULA");
                strSQL_FORMULA = rs.getString("SQL_FORMULA");

                strIF_TR_ROW = rs.getString("IF_TR_ROW");
                strIF_TR_COL = rs.getString("IF_TR_COL");
                strIF_TR_MEASURE = rs.getString("IF_TR_MEASURE");

                ifSelectedByUserToView = rs.getString("ifSelectedByUserToView");
                strSELECT_PARAM_IMPL_ID = rs.getString("SELECT_PARAM_IMPL_ID");
                strSLNO = rs.getString("SLNO");

                ifSelectedByUserToGroupBy = rs.getString("ifSelectedByUserToGroupBy");
                strGROUPBY_PARAM_ID = rs.getString("GROUPBY_PARAM_ID");
                strGB_SLNO = rs.getString("GB_SLNO");

                sf = new SelectField(strSELECT_PARAM_ID, strQID, strSELECT_PARAM, strPARAM_TABLE, strPARAM_FIELD, strIS_DEFAULT, strIS_DERIVED, strUSER_FORMULA, strSQL_FORMULA, ifSelectedByUserToView, strSELECT_PARAM_IMPL_ID, strSLNO, ifSelectedByUserToGroupBy, strGROUPBY_PARAM_ID, strGB_SLNO, strIF_TR_ROW, strIF_TR_COL, strIF_TR_MEASURE);
                alTimeFields.add(sf);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return alTimeFields;
    }

    @Override
    public ArrayList<SelectField> getLocationDimension(String qid) {
        ResultSet rs = null;
        Statement st = null;
        Connection con = null;
        ArrayList<SelectField> alLocationFields = new ArrayList<SelectField>();
        SelectField sf = null;
        String strSELECT_PARAM_ID;
        String strQID;
        String strSELECT_PARAM;
        String strPARAM_TABLE;
        String strPARAM_FIELD;
        String strIS_DEFAULT;
        String strIS_DERIVED;
        String strUSER_FORMULA;
        String strSQL_FORMULA;

        String strIF_TR_ROW;
        String strIF_TR_COL;
        String strIF_TR_MEASURE;

        /*If user selected the parameter to view in select clause*/
        String ifSelectedByUserToView;
        String strSELECT_PARAM_IMPL_ID;
        String strSLNO;
        /*If user selected the parameter to group by*/
        String ifSelectedByUserToGroupBy;
        String strGROUPBY_PARAM_ID;
        String strGB_SLNO;

        String IfSelectedByUserToTransRow;
        String IfSelectedByUserToTransCol;
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String sQuery = "SELECT query_select_param.SELECT_PARAM_ID,query_select_param.QID,query_select_param.SELECT_PARAM,query_select_param.PARAM_TABLE,query_select_param.PARAM_FIELD,query_select_param.IS_DEFAULT,query_select_param.IS_DERIVED,query_select_param.USER_FORMULA,query_select_param.SQL_FORMULA,query_select_param.IF_TR_ROW,query_select_param.IF_TR_COL,query_select_param.IF_TR_MEASURE, '' ifSelectedByUserToView,'' SELECT_PARAM_IMPL_ID,'' SLNO, '' ifSelectedByUserToGroupBy,'' GROUPBY_PARAM_ID,'' GB_SLNO,DIM_SLNO FROM (select * from query_select_param where qid=" + qid + " and DIMENSION='LOCATION') query_select_param order by DIM_SLNO";
            System.out.println(sQuery);
            rs = st.executeQuery(sQuery);
            while (rs.next()) {
                strSELECT_PARAM_ID = rs.getString("SELECT_PARAM_ID");
                strQID = rs.getString("QID");
                strSELECT_PARAM = rs.getString("SELECT_PARAM");
                strPARAM_TABLE = rs.getString("PARAM_TABLE");
                strPARAM_FIELD = rs.getString("PARAM_FIELD");
                strIS_DEFAULT = rs.getString("IS_DEFAULT");
                strIS_DERIVED = rs.getString("IS_DERIVED");
                strUSER_FORMULA = rs.getString("USER_FORMULA");
                strSQL_FORMULA = rs.getString("SQL_FORMULA");

                strIF_TR_ROW = rs.getString("IF_TR_ROW");
                strIF_TR_COL = rs.getString("IF_TR_COL");
                strIF_TR_MEASURE = rs.getString("IF_TR_MEASURE");

                ifSelectedByUserToView = rs.getString("ifSelectedByUserToView");
                strSELECT_PARAM_IMPL_ID = rs.getString("SELECT_PARAM_IMPL_ID");
                strSLNO = rs.getString("SLNO");

                ifSelectedByUserToGroupBy = rs.getString("ifSelectedByUserToGroupBy");
                strGROUPBY_PARAM_ID = rs.getString("GROUPBY_PARAM_ID");
                strGB_SLNO = rs.getString("GB_SLNO");

                sf = new SelectField(strSELECT_PARAM_ID, strQID, strSELECT_PARAM, strPARAM_TABLE, strPARAM_FIELD, strIS_DEFAULT, strIS_DERIVED, strUSER_FORMULA, strSQL_FORMULA, ifSelectedByUserToView, strSELECT_PARAM_IMPL_ID, strSLNO, ifSelectedByUserToGroupBy, strGROUPBY_PARAM_ID, strGB_SLNO, strIF_TR_ROW, strIF_TR_COL, strIF_TR_MEASURE);
                alLocationFields.add(sf);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return alLocationFields;
    }

    @Override
    public ArrayList<SpecialCondition> getSpecialConditions(String mqid) {
        ResultSet rs = null;
        Statement st = null;
        ResultSet rs1 = null;
        Statement st1 = null;
        Connection con = null;
        SpecialCondition sc = null;
        ArrayList<SpecialCondition> paramlist = new ArrayList<SpecialCondition>();
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String sQuery = "select * from query_special_condition where QID='" + mqid + "' order by SL";
            System.out.println(sQuery);
            rs = st.executeQuery(sQuery);
            while (rs.next()) {
                sc = new SpecialCondition();
                sc.setConditionid(rs.getString("QID"));
                sc.setConditionname(rs.getString("CONDITION_NAME"));
                sc.setConditionid(rs.getString("CONDITION_ID"));
                sc.setSql(rs.getString("CONDITION_SQL"));
                sc.setSl(rs.getString("SL"));
                paramlist.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (rs1 != null) {
                    rs1.close();
                    rs1 = null;
                }
                if (st1 != null) {
                    st1.close();
                    st1 = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return paramlist;
    }

    @Override
    public String getSelectParamFieldName(String qid, String paramField, Statement st) {
        String fieldname = "";
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT SELECT_PARAM FROM query_select_param WHERE QID=" + qid + " AND PARAM_FIELD='" + paramField + "'");
            //System.out.println("getSelectParamFieldName------------------------>SELECT SELECT_PARAM FROM query_select_param WHERE QID=" + qid + " AND PARAM_FIELD='" + paramField + "'");
            while (rs.next()) {
                fieldname = rs.getString("SELECT_PARAM");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fieldname != null && !fieldname.trim().equals("")) {
            //
        } else {
            fieldname = paramField;
        }
        return fieldname;
    }

    @Override
    public String getSelectParamAppliedOnField(String paramId, Statement st) {
        String appliedonfield = "";
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT PARAM_FIELD FROM query_select_param WHERE SELECT_PARAM_ID=" + paramId);
            while (rs.next()) {
                appliedonfield = rs.getString("PARAM_FIELD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appliedonfield;
    }

    @Override
    public String getIdenticalColumnName(String mqid, String fldName, String iqid) {
        String idfldName = "";
        ResultSet rs = null;
        Statement st = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String str = "SELECT IQ_FIELD_NAME FROM query_identity_cube_map WHERE MQ_ID=" + mqid + " AND MQ_FIELD_NAME='" + fldName + "'";
            rs = st.executeQuery(str);
            if (rs.next()) {
                idfldName = rs.getString("IQ_FIELD_NAME");
            }
            if (idfldName != null && idfldName.trim().equals("")) {
                idfldName = fldName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return idfldName;
    }

    @Override
    public ArrayList<Indicator> getIndicators(String mqid) {
        ArrayList<Indicator> alIndicators = new ArrayList<Indicator>();
        Indicator ind = null;
        ResultSet rs = null;
        Statement st = null;
        Connection con = null;
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String str = "SELECT * FROM query_indicator_config WHERE QID=" + mqid;
            System.out.println("In getIndicators qry:" + str);
            rs = st.executeQuery(str);
            while (rs.next()) {
                ind = new Indicator();
                ind.setQid(mqid);
                ind.setIndicatorName(rs.getString("INDICATOR"));
                ind.setIndicatorMeasure(rs.getString("INDICATOR_MEASURE"));
                ind.setDenominatorMeasure(rs.getString("DENOMINATOR_MEASURE"));
                ind.setWeight(rs.getInt("WEIGHT"));
                alIndicators.add(ind);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (st != null) {
                    st.close();
                    st = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("In getIndicators alIndicators size:" + alIndicators.size());
        return alIndicators;
    }
    
    @Override
    public int getColumnSl(ResultSetMetaData rsmd, String columnName) {
        int colsl = 0;
        try {
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                dricds.model.entitylist.Column field = new dricds.model.entitylist.Column();
                field.setField(name);

                if (name.equalsIgnoreCase(columnName)) {
                    colsl = i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colsl;
    }

    public boolean ifValueExist(ArrayList<String> alValues, String str) {
        boolean res = false;
        for (int i = 0; i < alValues.size(); i++) {
            String str1 = (String) alValues.get(i);
            if (str1.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return res;
    }
}
