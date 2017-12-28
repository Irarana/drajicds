/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.project;

import dricds.common.CommonFunctions;
import dricds.common.DataBaseFunctions;
import dricds.model.form.Attachment;
import dricds.model.form.ComboProperty;
import dricds.model.form.Compliance;
import dricds.model.form.DynaField;
import dricds.model.form.DynaForm;
import dricds.model.form.LabelValueBean;
import dricds.model.form.Observation;
import dricds.model.form.ProjectInitiationListForm;
import dricds.model.project.Activity;
import dricds.model.project.Decision;
import dricds.model.project.HistoryObj;
import dricds.model.project.PlanObj;
import dricds.model.project.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import mep.dataset.ColumnDefination;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Mana Jena
 */
public class ProjectMonDAOImpl implements ProjectMonDAO {

    @Resource(name = "dataSource")
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List getProjectList(int programmeId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List projectList = new ArrayList();
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM t_project_master INNER JOIN awc_master ON t_project_master.ICDS_UNIT_CODE = awc_master.AWC_CODE WHERE PROGREMME_ID=?");
            ps.setInt(1, programmeId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                project.setPid(rs.getInt("PID"));
                project.setProjectDesc(rs.getString("PROJECT_DESC"));
                project.setIcdsUnitName(rs.getString("ICDS_UNIT_NAME"));
                project.setIcdsUnitType(rs.getString("ICDS_UNIT_TYPE"));
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dricds.common.DataBaseFunctions.closeSqlObjects(rs);
            dricds.common.DataBaseFunctions.closeSqlObjects(ps);
            dricds.common.DataBaseFunctions.closeSqlObjects(con);
        }
        return projectList;
    }

    @Override
    public Project getProjectDetails(int pid) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Project project = new Project();
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM t_project_master WHERE PID = ?");
            ps.setInt(1, pid);
            rs = ps.executeQuery();
            if (rs.next()) {
                project.setPid(rs.getInt("PID"));
                project.setProjectDesc(rs.getString("PROJECT_DESC"));
                project.setIcdsUnitName(rs.getString("ICDS_UNIT_NAME"));
                project.setIcdsUnitType(rs.getString("ICDS_UNIT_TYPE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dricds.common.DataBaseFunctions.closeSqlObjects(rs);
            dricds.common.DataBaseFunctions.closeSqlObjects(ps);
            dricds.common.DataBaseFunctions.closeSqlObjects(con);
        }
        return project;
    }

    @Override
    public String workFlowStatus(int pid) {
        String wfStatus = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.dataSource.getConnection();
            ps = con.prepareStatement("SELECT CASE WHEN IFNULL(CURRENT_ACTIVITY_STATUS,'')='COMPLETED' AND IFNULL(IF_END,'')='Y' THEN 'COMPLETED' ELSE 'ONGOING' END WF_STATUS "
                    + "FROM T_PROJECT_MASTER LEFT OUTER JOIN G_ACTIVITY ON CURRENT_ACTIVITY=ACTIVITY_ID where PID=?");
            ps.setInt(1, pid);
            rs = ps.executeQuery();
            if (rs.next()) {
                wfStatus = rs.getString("WF_STATUS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dricds.common.DataBaseFunctions.closeSqlObjects(rs);
            dricds.common.DataBaseFunctions.closeSqlObjects(ps);
            dricds.common.DataBaseFunctions.closeSqlObjects(con);
        }
        return wfStatus;
    }

    @Override
    public Activity getCurrentActivityOfProject(int pid, String planno, int ppid) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Activity activity = new Activity();
        String qry = "";
        int curActivityId = 0;
        int selpaid = 0;
        try {
            con = this.dataSource.getConnection();
            if (ppid != 0) {
                qry = "SELECT ACTIVITY_ID,PAID FROM T_PROJECT_ACTIVITY WHERE PID=? AND PPID=? AND STATUS='ONGOING'";
                pst = con.prepareStatement(qry);
                pst.setInt(1, pid);
                pst.setInt(2, ppid);
                rs = pst.executeQuery();

                if (rs.next()) {
                    curActivityId = rs.getInt("ACTIVITY_ID");
                    selpaid = rs.getInt("PAID");
                }
            }

            //Project prj = getProjectDetails(pid, con);
            if (curActivityId != 0) {
                activity = getActivityDetails(curActivityId);
                //activity.setSelpaid(selpaid);
            } else {
                pst = con.prepareStatement("SELECT ACTIVITY_ID FROM G_ACTIVITY WHERE TYPE='ACTIVITY' AND PLAN_NO=? AND START_AFTER_ACTIVITY IS NULL");
                pst.setString(1, planno);
                rs = pst.executeQuery();
                if (rs.next()) {
                    curActivityId = rs.getInt("ACTIVITY_ID");
                }
                activity = getActivityDetails(curActivityId);
                activity.setSelpaid(selpaid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dricds.common.DataBaseFunctions.closeSqlObjects(rs);
            dricds.common.DataBaseFunctions.closeSqlObjects(pst);
            dricds.common.DataBaseFunctions.closeSqlObjects(con);
        }
        return activity;
    }

    public ArrayList<Decision> getDecisions(int aid) {
        ArrayList<Decision> aldlist = new ArrayList<Decision>();
        String qry = "SELECT * FROM G_ACTIVITY_DECISIONS WHERE ACTIVITY_ID=?";
        Connection con = null;
        ResultSet rsDecisions = null;
        PreparedStatement pst1 = null;
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setInt(1, aid);
            rsDecisions = pst1.executeQuery();
            while (rsDecisions.next()) {
                Decision d = new Decision();
                d.setStrACTIVITY_ID(rsDecisions.getString("ACTIVITY_ID"));
                d.setStrDECISION(rsDecisions.getString("DECISION"));
                d.setStrIF_POSITIVE(rsDecisions.getString("IF_POSITIVE"));
                d.setStrJUMP_TO_STAGEID(rsDecisions.getString("JUMP_TO_ACTIVITYID"));
                aldlist.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dricds.common.DataBaseFunctions.closeSqlObjects(rsDecisions);
            dricds.common.DataBaseFunctions.closeSqlObjects(pst1);
            dricds.common.DataBaseFunctions.closeSqlObjects(con);
        }

        return aldlist;
    }

    public String getActivityName(String aid) {
        String actname = "";
        Connection con = null;
        String qry = "SELECT * FROM G_ACTIVITY WHERE ACTIVITY_ID=?";
        ResultSet rsActivity = null;
        PreparedStatement pst1 = null;
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setString(1, aid);
            rsActivity = pst1.executeQuery();
            while (rsActivity.next()) {
                actname = rsActivity.getString("ACTIVITY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dricds.common.DataBaseFunctions.closeSqlObjects(rsActivity);
            dricds.common.DataBaseFunctions.closeSqlObjects(pst1);
            dricds.common.DataBaseFunctions.closeSqlObjects(con);
        }
        return actname;
    }

    public Activity getActivityDetails(int activityId) {
        Connection con = null;
        ResultSet rsActivity = null;
        PreparedStatement pst1 = null;
        Activity activity = new Activity();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement("SELECT * FROM G_ACTIVITY WHERE ACTIVITY_ID=?");
            pst1.setInt(1, activityId);
            rsActivity = pst1.executeQuery();
            while (rsActivity.next()) {
                activity.setStrACTIVITY_ID(rsActivity.getString("ACTIVITY_ID"));
                activity.setStrACTIVITY(rsActivity.getString("ACTIVITY"));
                activity.setStrACTIVITY_SL(rsActivity.getString("ACTIVITY_ORDER"));
                activity.setStrPARENT_SID(rsActivity.getString("PARENT_AID"));
                activity.setStrIS_COMPULSORY(rsActivity.getString("IS_COMPULSORY"));
                activity.setStrHAS_DECISION(rsActivity.getString("HAS_DECISION"));
                activity.setStrSTART_AFTER_ACTIVITY(rsActivity.getString("START_AFTER_ACTIVITY"));
                activity.setStrTYPE(rsActivity.getString("TYPE"));
                activity.setAlDecision(getDecisions(activityId));
                activity.setStrSTAGE(getActivityName(rsActivity.getString("PARENT_AID")));
                activity.setStrIF_END(rsActivity.getString("IF_END"));
                activity.setStrFORM_ID(rsActivity.getString("FORM_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsActivity);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return activity;
    }

    @Override
    public DynaForm getDynaForm(HttpServletRequest request, int formid) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        DynaForm dynaForm = new DynaForm();
        //ArrayList coldefs = getParameterValues(request, con);
        ArrayList coldefs = getColumnDefination(request, formid);
        System.out.println("In New Dynaform formid:" + formid);
        try {
            con = this.dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM form_mast WHERE FORM_ID=?");
            pstmt.setInt(1, formid);
            res = pstmt.executeQuery();
            if (res.next()) {
                dynaForm.setFormid(res.getInt("FORM_ID"));
                dynaForm.setFormname(res.getString("FORM_NAME"));
                dynaForm.setCompfield(res.getString("COMP_FIELD"));
                dynaForm.setFormDesc(res.getString("FORM_DESC"));
                dynaForm.setFootNote(res.getString("FOOT_NOTE"));
            }

            String qry = "SELECT form_field_mast.*,form_field_section.SECTION_ID,form_field_section.SECTION_NAME, form_field_section.SECTION_SL "
                    + "FROM (SELECT * FROM form_field_mast WHERE form_field_mast.FORM_ID=?) form_field_mast "
                    + "LEFT OUTER JOIN form_field_section "
                    + "ON form_field_mast.SECTION_ID=form_field_section.SECTION_ID "
                    + "ORDER BY form_field_section.SECTION_SL, VIEW_ORDER";

            pstmt = con.prepareStatement(qry);
            pstmt.setInt(1, formid);
            res = pstmt.executeQuery();
            ArrayList<DynaField> fields = new ArrayList();
            for (int k = 0; k < coldefs.size(); k++) {
                ColumnDefination coldef = (ColumnDefination) coldefs.get(k);

                System.out.println(coldef.getFieldname() + ">>>---------Bibek----------" + "----------val---------" + coldef.getFieldval());

            }
            while (res.next()) {
                DynaField dynaField = new DynaField();
                dynaField.setFieldid(res.getInt("FIELD_ID"));
                dynaField.setFieldname(res.getString("FIELD_NAME"));

                for (int k = 0; k < coldefs.size(); k++) {
                    ColumnDefination coldef = (ColumnDefination) coldefs.get(k);
                    if (coldef.getFieldname() != null && dynaField.getFieldname() != null && coldef.getFieldname().equalsIgnoreCase(dynaField.getFieldname())) {
                        //System.out.println(coldef.getFieldname()+"---------Bibek----------"+dynaField.getFieldname()+"----------val---------"+coldef.getFieldval());
                        dynaField.setFieldval(coldef.getFieldval());
                    }
                }

                dynaField.setFieldlabel(res.getString("FIELD_LABEL"));
                dynaField.setFielddatatype(res.getString("FIELD_DATA_TYPE"));
                dynaField.setFieldtype(res.getString("FIELD_TYPE"));
                dynaField.setIscompulsory(res.getString("IS_COMPULSORY"));
                dynaField.setFieldlength(res.getInt("FIELD_LENGTH"));
                dynaField.setSize(res.getInt("SIZE"));

                if (dynaField.getFieldtype().equalsIgnoreCase("COMBO")) {
                    ComboProperty cp = getComboProperty(dynaField.getFieldid());
                    dynaField.setInline(cp.getInline());
                    if (cp.getInline().equalsIgnoreCase("Y")) {
                        dynaField.setLabelvalues(getLabelValues(dynaField.getFieldid()));
                    } else {
                        //Code for non-inline combo
                    }
                }

                dynaField.setHelptext(res.getString("HELP_TEXT"));
                dynaField.setIfhdrlbl(res.getString("IF_HDR_LBL"));
                dynaField.setSectionid(res.getString("SECTION_ID"));
                dynaField.setSectionname(res.getString("SECTION_NAME"));
                dynaField.setFilterField(res.getString("FILTER_FIELD_NAME"));
                dynaField.setIscompfield("N");

                if (res.getString("IF_AUTO_CALC") != null && !res.getString("IF_AUTO_CALC").trim().equals("")) {
                    dynaField.setIfAutoCalc(res.getString("IF_AUTO_CALC"));
                } else {
                    dynaField.setIfAutoCalc("N");
                }
                if (res.getString("AUTO_CALC_FORMULA") != null && !res.getString("AUTO_CALC_FORMULA").trim().equals("")) {
                    dynaField.setAutoCalcFormula(res.getString("AUTO_CALC_FORMULA"));
                }
                if (res.getString("IF_OB") != null && !res.getString("IF_OB").trim().equals("")) {
                    dynaField.setIfOB(res.getString("IF_OB"));
                } else {
                    dynaField.setIfOB("N");
                }
                //System.out.println("In execute Before Setting:"+dynaField.getFieldname()+"--------"+dynaField.getFieldval());
                fields.add(dynaField);
            }
            dynaForm.setFields(fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstmt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return dynaForm;
    }

    public ArrayList getColumnDefination(HttpServletRequest request, int formid) {
        ArrayList coldefs = new ArrayList();
        Connection con = null;
        PreparedStatement pstamt = null;
        ResultSet res = null;
        try {
            con = this.dataSource.getConnection();
            HttpSession session = request.getSession();
            pstamt = con.prepareStatement("SELECT * FROM v_column_def WHERE FORM_ID=?");
            pstamt.setInt(1, formid);
            res = pstamt.executeQuery();
            while (res.next()) {
                ColumnDefination coldef = new ColumnDefination();
                coldef.setColumnid(res.getInt("COLUMN_ID"));
                coldef.setFieldname(StringUtils.trim(res.getString("FIELD_NAME")));
                coldef.setIshidden(res.getString("IS_HIDDEN"));
                coldef.setIsparam(res.getString("IS_PARAM"));
                coldef.setParamName(res.getString("PARAM_NAME"));

                if (coldef.getIsparam().equalsIgnoreCase("N")) {
                    String value = (String) session.getAttribute(coldef.getParamName());
                    System.out.println("value:" + value);
                    coldef.setFieldval(value);
                } else {
                    System.out.println("bIBEK------------------>" + coldef.getParamName());
                    String value = "";
                    if (request.getParameter(coldef.getParamName()) != null) {
                        value = request.getParameter(coldef.getParamName());
                    }/*else{
                     value = request.getParameter("F"+coldef.getColumnid());
                     }*/

                    System.out.println("bIBEK------------------>" + coldef.getParamName() + "--------" + value);
                    coldef.setFieldval(value);
                }

                coldefs.add(coldef);
            }

            Enumeration en = request.getParameterNames();
            while (en.hasMoreElements()) {
                ColumnDefination coldef = new ColumnDefination();
                Object objOri = en.nextElement();
                String param = (String) objOri;
                String value = request.getParameter(param);
                if (param.substring(0, 1).equals("F")) {
                    int columnid = Integer.parseInt(param.substring(1));
                    coldef.setColumnid(columnid);
                    coldef.setFieldval(value);
                    getColumnDefination(columnid, coldef);
                    coldefs.add(coldef);
                }

                System.out.println("Parameter Name is '" + param + "' and Parameter Value is '" + value + "'");
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstamt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return coldefs;
    }

    public void getColumnDefination(int columnid, ColumnDefination coldef) {
        Connection con = null;
        PreparedStatement pstamt = null;
        ResultSet res = null;
        try {
            con = this.dataSource.getConnection();
            pstamt = con.prepareStatement("SELECT * FROM v_column_def WHERE COLUMN_ID=?");
            pstamt.setInt(1, columnid);
            res = pstamt.executeQuery();
            if (res.next()) {
                coldef.setFieldname(StringUtils.trim(res.getString("FIELD_NAME")));
                coldef.setIshidden(res.getString("IS_HIDDEN"));
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstamt);
            DataBaseFunctions.closeSqlObjects(con);
        }
    }

    public ComboProperty getComboProperty(int fieldId) {
        Connection con = null;
        PreparedStatement pstamt = null;
        ResultSet res = null;
        ComboProperty cp = new ComboProperty();
        try {
            con = this.dataSource.getConnection();
            pstamt = con.prepareStatement("SELECT IS_INLINE FROM combo_property WHERE FIELD_ID=?");
            pstamt.setInt(1, fieldId);
            res = pstamt.executeQuery();
            if (res.next()) {
                cp.setInline(res.getString("IS_INLINE"));
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstamt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return cp;
    }

    public ArrayList<LabelValueBean> getLabelValues(HttpServletRequest request, int fieldId) {
        ArrayList<LabelValueBean> labelvalues = new ArrayList();
        Connection con = null;
        PreparedStatement pstamt = null;
        ResultSet res = null;
        try {
            con = this.dataSource.getConnection();
            pstamt = con.prepareStatement("SELECT DATA_SQL,PARAM_COLUMN_ID,IS_PAGE_PARAM FROM combo_property WHERE FIELD_ID=?");
            pstamt.setInt(1, fieldId);
            res = pstamt.executeQuery();
            String datasql = "";
            String paramcolid = "";
            String paramVal = "";
            String isPageParam = "";
            if (res.next()) {
                datasql = res.getString("DATA_SQL");
                paramcolid = res.getString("PARAM_COLUMN_ID");
                isPageParam = res.getString("IS_PAGE_PARAM");
            }
            if (paramcolid != null && !paramcolid.trim().equals("")) {
                String paramname = getVParamName(paramcolid, isPageParam);
                String isReq = getVParamTypeIsRequest(paramcolid);
                paramVal = getVParamValue(request, paramname, isReq, isPageParam);
                //datasql.replace("@"+paramname, "'"+paramVal+"'");
                String ndatasql = datasql.replace("@" + paramname, "'" + paramVal + "'");
                datasql = ndatasql;
                System.out.println("new datasql:" + ndatasql);
            }

            pstamt = con.prepareStatement(datasql);
            res = pstamt.executeQuery();
            while (res.next()) {
                LabelValueBean lvBean = new LabelValueBean();
                lvBean.setValue(res.getString(1));
                lvBean.setLabel(res.getString(2));
                labelvalues.add(lvBean);
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstamt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return labelvalues;
    }

    public ArrayList<LabelValueBean> getLabelValues(int fieldId) {
        ArrayList<LabelValueBean> labelvalues = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = this.dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT DATA_SQL,PARAM_COLUMN_ID FROM combo_property WHERE FIELD_ID=?");
            pstmt.setInt(1, fieldId);
            res = pstmt.executeQuery();
            String datasql = "";
            String paramcolid = "";
            String paramVal = "";
            if (res.next()) {
                datasql = res.getString("DATA_SQL");
                paramcolid = res.getString("PARAM_COLUMN_ID");
            }

            pstmt = con.prepareStatement(datasql);
            res = pstmt.executeQuery();
            while (res.next()) {
                LabelValueBean lvBean = new LabelValueBean();
                lvBean.setValue(res.getString(1));
                lvBean.setLabel(res.getString(2));
                labelvalues.add(lvBean);
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstmt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return labelvalues;
    }

    public String getVParamName(String paramid, String isPageParam) {
        String paramname = "";
        Connection con = null;
        PreparedStatement pstamt = null;
        ResultSet res = null;
        try {
            con = this.dataSource.getConnection();
            if (isPageParam != null && isPageParam.equalsIgnoreCase("Y")) {
                pstamt = con.prepareStatement("SELECT FIELD_NAME PARAM_NAME FROM form_field_mast WHERE FIELD_ID=?");
            } else {
                pstamt = con.prepareStatement("SELECT PARAM_NAME FROM v_column_def WHERE COLUMN_ID=?");
            }

            pstamt.setInt(1, Integer.parseInt(paramid));
            res = pstamt.executeQuery();

            if (res.next()) {
                paramname = res.getString("PARAM_NAME");
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstamt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return paramname;
    }

    public String getVParamTypeIsRequest(String paramid) {
        String paramname = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = this.dataSource.getConnection();
            pstmt = con.prepareStatement("SELECT IS_PARAM FROM v_column_def WHERE COLUMN_ID=?");
            pstmt.setInt(1, Integer.parseInt(paramid));
            res = pstmt.executeQuery();

            if (res.next()) {
                paramname = res.getString("IS_PARAM");
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(res);
            DataBaseFunctions.closeSqlObjects(pstmt);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return paramname;
    }

    public String getVParamValue(HttpServletRequest request, String paramname, String isRequest, String isPageParam) {
        String paramVal = "";
        try {

            if (isPageParam != null && isPageParam.equalsIgnoreCase("Y")) {
                if (request.getParameter(paramname) != null) {
                    paramVal = (String) request.getParameter(paramname);
                }
            } else {
                if (isRequest != null && isRequest.equalsIgnoreCase("Y")) {
                    if (request.getAttribute(paramname) != null) {
                        paramVal = (String) request.getAttribute(paramname);
                    }
                } else {
                    HttpSession session = request.getSession();

                    if (session.getAttribute(paramname) != null) {
                        paramVal = (String) session.getAttribute(paramname);
                    }
                }
            }
        } catch (Exception sqe) {
            sqe.printStackTrace();
        }
        System.out.println("In get Param Value of " + paramname + " = " + paramVal);
        return paramVal;
    }

    @Override
    public Observation[] getObservationsOfPALID(String strPALID) {
        Observation[] obs = null;
        String qry = "SELECT * FROM T_ACTIVITY_OBSERVATION WHERE PALID=?";
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ArrayList<Observation> alObservation = new ArrayList<Observation>();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setString(1, strPALID);
            rsObservations = pst1.executeQuery();
            while (rsObservations.next()) {
                Observation o = new Observation();
                o.setStrAOID(rsObservations.getString("AOID"));
                o.setStrPALID(rsObservations.getString("PALID"));
                o.setStrPAID(rsObservations.getString("PAID"));
                o.setStrPID(rsObservations.getString("PID"));
                o.setStrACTIVITY_ID(rsObservations.getString("ACTIVITY_ID"));
                o.setStrOBSERVATION(rsObservations.getString("OBSERVATION"));
                o.setStrIF_CLOSED(rsObservations.getString("IF_CLOSED"));
                alObservation.add(o);
            }

            obs = new Observation[alObservation.size()];
            for (int ctr = 0; ctr < alObservation.size(); ctr++) {
                obs[ctr] = alObservation.get(ctr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return obs;
    }

    @Override
    public Observation[] getOpenObservationsOfPID(int pid) {
        Observation[] obs = null;
        String qry = "SELECT * FROM T_ACTIVITY_OBSERVATION WHERE PID=? and IFNULL(IF_CLOSED,'')<>'Y'";
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ArrayList<Observation> alObservation = new ArrayList<>();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setInt(1, pid);
            rsObservations = pst1.executeQuery();
            while (rsObservations.next()) {
                Observation o = new Observation();
                o.setStrAOID(rsObservations.getString("AOID"));
                o.setStrPALID(rsObservations.getString("PALID"));
                o.setStrPAID(rsObservations.getString("PAID"));
                o.setStrPID(rsObservations.getString("PID"));
                o.setStrACTIVITY_ID(rsObservations.getString("ACTIVITY_ID"));
                o.setStrOBSERVATION(rsObservations.getString("OBSERVATION"));
                o.setStrIF_CLOSED(rsObservations.getString("IF_CLOSED"));
                alObservation.add(o);
            }

            obs = new Observation[alObservation.size()];
            for (int ctr = 0; ctr < alObservation.size(); ctr++) {
                obs[ctr] = alObservation.get(ctr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return obs;
    }

    @Override
    public ArrayList getHistory(int pid, int ppid) {
        ArrayList<HistoryObj> alHistory = new ArrayList<>();
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ResultSet rs;
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement("SELECT PALID,T_PROJECT_ACTIVITY_LOG.ACTIVITY_ID,ACTIVITY, COMPLETION_DATE,DECISION "
                    + "FROM T_PROJECT_ACTIVITY_LOG LEFT OUTER JOIN G_ACTIVITY ON T_PROJECT_ACTIVITY_LOG.ACTIVITY_ID=G_ACTIVITY.ACTIVITY_ID WHERE PID=? AND PPID=? ORDER BY PALID");
            pst1.setInt(1, pid);
            pst1.setInt(2, ppid);
            rs = pst1.executeQuery();
            while (rs.next()) {
                HistoryObj hObj = new HistoryObj();
                hObj.setPalid(rs.getInt("PALID"));
                hObj.setActivityId(rs.getInt("ACTIVITY_ID"));
                hObj.setActivity(rs.getString("ACTIVITY"));
                hObj.setCompletionDate(rs.getString("COMPLETION_DATE"));
                hObj.setDecision(rs.getString("DECISION"));
                alHistory.add(hObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return alHistory;
    }

    @Override
    public HashMap getObservationHistory(int pid, int ppid) {
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ResultSet rs = null;
        HashMap projectHistory = new HashMap();
        ArrayList<Observation[]> alObservations = new ArrayList<>();
        ArrayList<Compliance[]> alCompliance = new ArrayList<>();
        ArrayList<Attachment[]> alAttachments = new ArrayList<>();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement("SELECT PALID,T_PROJECT_ACTIVITY_LOG.ACTIVITY_ID,ACTIVITY, COMPLETION_DATE,DECISION "
                    + "FROM T_PROJECT_ACTIVITY_LOG LEFT OUTER JOIN G_ACTIVITY ON T_PROJECT_ACTIVITY_LOG.ACTIVITY_ID=G_ACTIVITY.ACTIVITY_ID WHERE PID=? AND PPID=? ORDER BY PALID");
            pst1.setInt(1, pid);
            pst1.setInt(2, ppid);
            rs = pst1.executeQuery();
            while (rs.next()) {
                Observation[] obs = getObservationsOfPALID(rs.getString("PALID"));
                Compliance[] com = getCompliancesOfPALID(rs.getString("PALID"));
                Attachment[] ar = getAttachmentsOfPALID(rs.getString("PALID"));
                alObservations.add(obs);
                alCompliance.add(com);
                alAttachments.add(ar);
            }
            projectHistory.put("alObservations", alObservations);
            projectHistory.put("alCompliance", alCompliance);
            projectHistory.put("alAttachments", alAttachments);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return projectHistory;
    }

    public Observation[] getOpenObservationsOfPID(String strPID) {
        Observation[] obs = null;
        String qry = "SELECT * FROM T_ACTIVITY_OBSERVATION WHERE PID=? and ISNULL(IF_CLOSED,'')<>'Y'";
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ArrayList<Observation> alObservation = new ArrayList<Observation>();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setString(1, strPID);
            rsObservations = pst1.executeQuery();
            while (rsObservations.next()) {
                Observation o = new Observation();
                o.setStrAOID(rsObservations.getString("AOID"));
                o.setStrPALID(rsObservations.getString("PALID"));
                o.setStrPAID(rsObservations.getString("PAID"));
                o.setStrPID(rsObservations.getString("PID"));
                o.setStrACTIVITY_ID(rsObservations.getString("ACTIVITY_ID"));
                o.setStrOBSERVATION(rsObservations.getString("OBSERVATION"));
                o.setStrIF_CLOSED(rsObservations.getString("IF_CLOSED"));
                alObservation.add(o);
            }

            obs = new Observation[alObservation.size()];
            for (int ctr = 0; ctr < alObservation.size(); ctr++) {
                obs[ctr] = alObservation.get(ctr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return obs;
    }

    public Compliance[] getCompliancesOfPALID(String strPALID) {
        Compliance[] obs = null;
        String qry = "SELECT * FROM T_ACTIVITY_COMPLIANCE WHERE PALID=?";
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ArrayList<Compliance> alObservation = new ArrayList<Compliance>();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setString(1, strPALID);
            rsObservations = pst1.executeQuery();
            while (rsObservations.next()) {
                Compliance o = new Compliance();
                o.setStrACID(rsObservations.getString("ACID"));
                o.setStrAOID(rsObservations.getString("AOID"));
                o.setStrPALID(rsObservations.getString("PALID"));
                o.setStrPAID(rsObservations.getString("PAID"));
                o.setStrPID(rsObservations.getString("PID"));
                o.setStrACTIVITY_ID(rsObservations.getString("ACTIVITY_ID"));
                o.setStrCOMPLIANCE_NOTE(rsObservations.getString("COMPLIANCE_NOTE"));
                alObservation.add(o);
            }

            obs = new Compliance[alObservation.size()];
            for (int ctr = 0; ctr < alObservation.size(); ctr++) {
                obs[ctr] = alObservation.get(ctr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return obs;
    }

    public Attachment[] getAttachmentsOfPALID(String strPALID) {
        Attachment[] obs = null;
        String qry = "SELECT * FROM T_ACTIVITY_ATTACHMENT WHERE PALID=?";
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        ArrayList<Attachment> alObservation = new ArrayList<Attachment>();
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement(qry);
            pst1.setString(1, strPALID);
            rsObservations = pst1.executeQuery();
            while (rsObservations.next()) {
                Attachment o = new Attachment();
                o.setStrAAID(rsObservations.getString("AAID"));
                o.setStrPALID(rsObservations.getString("PALID"));
                o.setStrPAID(rsObservations.getString("PAID"));
                o.setStrPID(rsObservations.getString("PID"));
                o.setStrACTIVITY_ID(rsObservations.getString("ACTIVITY_ID"));
                o.setStrFILE_NAME(rsObservations.getString("FILE_NAME"));
                o.setStrFILE_PATH(rsObservations.getString("FILE_PATH"));
                alObservation.add(o);
            }

            obs = new Attachment[alObservation.size()];
            for (int ctr = 0; ctr < alObservation.size(); ctr++) {
                obs[ctr] = alObservation.get(ctr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return obs;
    }    
    public PlanObj getPlan(String strPLANNO) {
        PlanObj plan = new PlanObj();
        Connection con = null;
        ResultSet rsObservations = null;
        PreparedStatement pst1 = null;
        try {
            con = this.dataSource.getConnection();
            pst1 = con.prepareStatement("SELECT * FROM G_PLAN_MASTER WHERE PLAN_NO=?");
            pst1.setString(1, strPLANNO);
            rsObservations = pst1.executeQuery();
            if (rsObservations.next()) {
                plan.setStrPLAN_NO(rsObservations.getString("PLAN_NO"));
                plan.setStrPLAN_DESC(rsObservations.getString("PLAN_DESC"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rsObservations);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return plan;
    }
    @Override
    public String saveNewActivity(ProjectInitiationListForm pif, HttpServletRequest request) {
        String resString = "";
        Connection con = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rsKey = null;
        PreparedStatement pst = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4 = null;
        PreparedStatement pst5 = null;
        PreparedStatement pst6 = null;
        PreparedStatement pst7 = null;
        PreparedStatement pst8 = null;
        Statement st = null;
        String curaid = pif.getHidaid();
        int auto_id=0,auto_ppid=0;
        try {
            con = this.dataSource.getConnection();
            st = con.createStatement();
            String qry = "SELECT COUNT(*) CNT FROM T_PROJECT_ACTIVITY WHERE PID=? AND PPID=?";
            pst = con.prepareStatement(qry);
            pst.setString(1, pif.getHidselpid());
            if (pif.getHidselppid() != null && !pif.getHidselppid().trim().equals("")) {
                pst.setString(2, pif.getHidselppid());
            } else {
                pst.setString(2, "0");
            }
            rs = pst.executeQuery();
            int cnt = 0;
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }

            DataBaseFunctions.closeSqlObjects(pst);
            DataBaseFunctions.closeSqlObjects(rs);

            String insQry = "INSERT INTO [T_PROJECT_ACTIVITY]([PID],[ACTIVITY_ID],[ACTIVITY_ORDER],[IF_REPEATING],[LAST_PALID],[START_DATE],[SCH_COMPLETION_DATE],[COMPLETION_DATE],[DECISION],[IF_SKIPPED],[STATUS],[START_AFTER_ACTIVITY],[PPID]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String insQry2 = "INSERT INTO [T_PROJECT_ACTIVITY_LOG]([PAID],[PID],[ACTIVITY_ID],[ACTIVITY_ORDER],[IF_REPEATING],[LAST_PALID],[START_DATE],[SCH_COMPLETION_DATE],[COMPLETION_DATE],[DECISION],[IF_SKIPPED],[STATUS],[START_AFTER_ACTIVITY],[INSERTED_ON],[REMARK],[PPID]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String insQryUpdate = "UPDATE [T_PROJECT_ACTIVITY] SET [START_DATE]=?,[SCH_COMPLETION_DATE]=?,[COMPLETION_DATE]=?,[DECISION]=?,[IF_SKIPPED]=?,[STATUS]=? WHERE PID=? AND ACTIVITY_ID=?";
            String insObservation = "INSERT INTO [T_ACTIVITY_OBSERVATION]([PALID],[PAID],[PID],[ACTIVITY_ID],[OBSERVATION]) VALUES(?,?,?,?,?)";
            String insCompliance = "INSERT INTO [T_ACTIVITY_COMPLIANCE]([PALID],[PAID],[PID],[ACTIVITY_ID],[AOID],[COMPLIANCE_NOTE]) VALUES(?,?,?,?,?,?)";
            String updObservation = "UPDATE [T_ACTIVITY_OBSERVATION] SET IF_CLOSED='Y' WHERE [AOID]=?";
            String insAttachment = "INSERT INTO [T_ACTIVITY_ATTACHMENT]([PALID],[PAID],[PID],[ACTIVITY_ID],[FILE_NAME],[FILE_PATH]) VALUES (?,?,?,?,?,?)";
            String insPackage = "INSERT INTO [T_PACKAGE_MASTER]([PID],[PLAN_NO],[PLAN_DESC],[PACKAGE_DESC],[START_DATE],[STATUS])VALUES(?,?,?,?,?,?)";

            pst = con.prepareStatement(insQry, Statement.RETURN_GENERATED_KEYS);
            pst2 = con.prepareStatement(insQryUpdate);
            pst3 = con.prepareStatement(insQry2, Statement.RETURN_GENERATED_KEYS);
            pst4 = con.prepareStatement(insObservation);
            pst5 = con.prepareStatement(insCompliance);
            pst6 = con.prepareStatement(updObservation);
            pst7 = con.prepareStatement(insAttachment);
            pst8 = con.prepareStatement(insPackage, Statement.RETURN_GENERATED_KEYS);
            Project prj = getProjectDetails(Integer.parseInt(pif.getHidselpid()));
            if (cnt == 0) {
                pst8.setString(1, pif.getHidselpid());
                pst8.setString(2, pif.getHidselplanno());
                pst8.setString(3, getPlan(pif.getHidselplanno()).getStrPLAN_DESC());
                if (pif.getPackagedesc() != null && !pif.getPackagedesc().trim().equals("")) {
                    pst8.setString(4, pif.getPackagedesc());
                } else {
                    pst8.setString(4, null);
                }
                java.util.Date dt1 = new java.util.Date();
                java.sql.Date sqlDate1 = new java.sql.Date(dt1.getTime());
                pst8.setDate(5, sqlDate1);
                pst8.setString(6, "ONGOING");
                pst8.executeUpdate();
                rsKey = null;
                rsKey = pst8.getGeneratedKeys();
                rsKey.next();
                auto_ppid = rsKey.getInt(1);
                resString = auto_ppid + "";
                /*INSERT PACKAGE DETAILS*/

                /*UPDATE PROJECT MAST IF */
                st.executeUpdate("UPDATE T_PROJECT_MASTER SET PRE_EXEC_PPID='" + auto_ppid + "' WHERE PID=" + pif.getHidselpid() + " AND PRE_EXEC_PLAN_NO='" + pif.getHidselplanno() + "'");

                /*UPDATE PROJECT MAST*/
                qry = "SELECT * FROM G_ACTIVITY WHERE TYPE='ACTIVITY' AND PLAN_NO='" + pif.getHidselplanno() + "' ORDER BY ACTIVITY_ORDER";
                pst1 = con.prepareStatement(qry);
                rs = pst1.executeQuery();
                /*Create Activity Plan*/
                while (rs.next()) {
                    String activityid = rs.getString("ACTIVITY_ID");
                    String activityorder = rs.getString("ACTIVITY_ORDER");
                    String parentid = rs.getString("PARENT_AID");
                    String startAfter = rs.getString("START_AFTER_ACTIVITY");

                    pst.setString(1, pif.getHidselpid());
                    pst.setString(2, activityid);
                    pst.setString(3, activityorder);
                    pst.setString(4, "N");
                    pst.setString(5, null);
                    if (curaid.equalsIgnoreCase(activityid)) {
                        pst.setDate(6, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        pst.setDate(7, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        pst.setDate(8, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        //pst.setString(9, pif.getSltDecision());
                        pst.setString(9, null);
                        pst.setString(10, null);
                        pst.setString(11, null);
                    } else {
                        pst.setString(6, null);
                        pst.setString(7, null);
                        pst.setString(8, null);
                        pst.setString(9, null);
                        pst.setString(10, null);
                        pst.setString(11, null);
                    }
                    pst.setString(12, startAfter);
                    pst.setInt(13, auto_ppid);
                    pst.executeUpdate();

                    if (curaid.equalsIgnoreCase(activityid)) {
                        rsKey = null;
                        rsKey = pst.getGeneratedKeys();
                        rsKey.next();
                        auto_id = rsKey.getInt(1);
                        /*Update Activity Plan*/
                        pst2.setDate(1, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        pst2.setDate(2, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        pst2.setDate(3, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        if (pif.getSltDecision() != null && !pif.getSltDecision().trim().equals("")) {
                            pst2.setString(4, pif.getSltDecision());
                        } else {
                            pst2.setString(4, null);
                        }
                        pst2.setString(5, null);
                        pst2.setString(6, "COMPLETED");
                        pst2.setString(7, pif.getHidselpid());
                        pst2.setString(8, activityid);
                        pst2.executeUpdate();
                        /*Update Activity Plan*/

                        /*Insert Activity Log*/
                        pst3.setInt(1, auto_id);
                        pst3.setString(2, pif.getHidselpid());
                        pst3.setString(3, activityid);
                        pst3.setString(4, activityorder);
                        pst3.setString(5, "N");
                        pst3.setString(6, null);
                        pst3.setDate(7, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        pst3.setDate(8, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        pst3.setDate(9, CommonFunctions.getDateFromInputString(pif.getTxtDecisionDate()));
                        if (pif.getSltDecision() != null && !pif.getSltDecision().trim().equals("")) {
                            pst3.setString(10, pif.getSltDecision());
                        } else {
                            pst3.setString(10, null);
                        }
                        pst3.setString(11, null);
                        pst3.setString(12, "COMPLETED");
                        pst3.setString(13, startAfter);
                        java.util.Date dt = new java.util.Date();
                        java.sql.Date sqlDate = new java.sql.Date(dt.getTime());
                        pst3.setDate(14, sqlDate);
                        if (pif.getTxtremark() != null && !pif.getTxtremark().trim().equals("")) {
                            pst3.setString(15, pif.getTxtremark());
                        } else {
                            pst3.setString(15, null);
                        }
                        pst3.setInt(16, auto_ppid);
                        pst3.executeUpdate();
                        /*Insert Activity Log*/

                        /*Insert Observation*/
                        rsKey = null;
                        rsKey = pst3.getGeneratedKeys();
                        rsKey.next();
                        int auto_id1 = rsKey.getInt(1);

                        if (pif.getTxtObservation() != null && pif.getTxtObservation().length > 0) {
                            for (int octr = 0; octr < pif.getTxtObservation().length; octr++) {
                                pst4.setInt(1, auto_id1);
                                pst4.setString(2, pif.getHidselpaid());
                                pst4.setString(3, pif.getHidselpid());
                                pst4.setString(4, activityid);
                                pst4.setString(5, pif.getTxtObservation()[octr]);
                                if (pif.getTxtObservation()[octr] != null && !pif.getTxtObservation()[octr].trim().equals("")) {
                                    pst4.executeUpdate();
                                }
                            }
                        }
                        /*Insert Observation*/

                        /*Insert Compliance*/
                        if (pif.getHidOpenObsId() != null && pif.getHidOpenObsId().length > 0 && pif.getTxtCompliance() != null && pif.getTxtCompliance().length > 0) {
                            for (int cctr = 0; cctr < pif.getTxtCompliance().length; cctr++) {
                                pst5.setInt(1, auto_id1);
                                pst5.setString(2, pif.getHidselpaid());
                                pst5.setString(3, pif.getHidselpid());
                                pst5.setString(4, activityid);
                                pst5.setString(5, pif.getHidOpenObsId()[cctr]);
                                pst5.setString(6, pif.getTxtCompliance()[cctr]);
                                if (pif.getTxtCompliance()[cctr] != null && !pif.getTxtCompliance()[cctr].trim().equals("")) {
                                    pst5.executeUpdate();
                                }
                            }
                        }
                        /*Insert Compliance*/

                        /*Update Complied Observations*/
                        if (pif.getChkCompliedObs() != null && pif.getChkCompliedObs().length > 0) {
                            for (int cctr = 0; cctr < pif.getChkCompliedObs().length; cctr++) {
                                String compliedObs = pif.getChkCompliedObs()[cctr];
                                pst6.setString(1, compliedObs);
                                pst6.executeUpdate();
                            }
                        }
                        /*Update Complied Observations*/
                    }
                }
                /*Create Activity Plan*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataBaseFunctions.closeSqlObjects(rs);
            DataBaseFunctions.closeSqlObjects(pst1);
            DataBaseFunctions.closeSqlObjects(con);
        }
        return resString;
    }
}
