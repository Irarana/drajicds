/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.notification;

import static dricds.controller.notification.InboxController.LOG;
import dricds.model.notification.InBox;
import dricds.model.notification.NotiRespondant;
import dricds.model.notification.Notification;
import dricds.model.notification.OutBox;
import dricds.model.notification.RespondantTaskMap;
import dricds.model.notification.Respondents;
import dricds.model.notification.TaskList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author ekank
 */
@Repository
public class NotificationDaoImpl implements NotificationDao {

    public static Log LOG = LogFactory.getLog(NotificationDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertActor;
    private SimpleJdbcInsert insertRespondant;
    private SimpleJdbcInsert insertTaskList;
    private SimpleJdbcInsert insertRespondantTaskMap;
    private PlatformTransactionManager transactionManager;

    private final RowMapper<NotiRespondant> icdsModelRowMapper = new BeanPropertyRowMapper<>(NotiRespondant.class);

    private final RowMapper<Notification> notificationModelRowMapper = new BeanPropertyRowMapper<>(Notification.class);

    private final RowMapper<Respondents> respondentsRowMapper = new BeanPropertyRowMapper<>(Respondents.class);
    private final RowMapper<TaskList> taskListRowMapper = new BeanPropertyRowMapper<>(TaskList.class);

    private static final String STATUS_PENDING = "pending";
    private static final String STATUS_SUBMITTED = "submitted";
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_ACTIVE = "active";
    private static final String ACTIVE = "A";
    private static final String REASSIGNED = "R";
    private static final String COMPLETED = "C";
    private static final String DISTRICT = "DIST";
    private static final String PROJECT = "PROJECT";
    private static final String SECTOR = "SECTOR";
    private static final String AWC = "AWC";
    private static final String STATE = "STATE";

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("T_NOTIFICATION")
                .usingGeneratedKeyColumns("NotificationId");

        this.insertRespondant = new SimpleJdbcInsert(dataSource)
                .withTableName("T_RESPONDENTS")
                .usingGeneratedKeyColumns("RespondentId");

        this.insertTaskList = new SimpleJdbcInsert(dataSource)
                .withTableName("T_TASKLIST")
                .usingGeneratedKeyColumns("TaskId");

        this.insertRespondantTaskMap = new SimpleJdbcInsert(dataSource)
                .withTableName("T_RESPONDANT_TASK_MAP")
                .usingGeneratedKeyColumns("RespTaskId");
    }

    @Override
    public Notification insert(Notification notification) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            LOG.info("Inserting Comment + " + notification);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
            Map<String, Object> parameters = new HashMap<String, Object>(2);
            LOG.info("Sender From Code Id" + notification.getSendFromCode());
            LOG.info("Sender USer Id" + notification.getSendFromUserId());
            parameters.put("Notification", notification.getNotification());
            parameters.put("IfNeedReply", (notification.isNeedReply() ? "Y" : "N"));
            parameters.put("Form_Id", notification.getForm_Id());
            parameters.put("NeedReplyBefore", notification.getNeedReplyBefore());
            parameters.put("Approver", notification.getApprover());
            parameters.put("NeedApprovalBefore", notification.getNeedApprovalBefore());
            parameters.put("DetailsAbout", notification.getDetailsAbout());
            parameters.put("SendFromCode", notification.getSendFromCode());
            parameters.put("SendFromUserId", notification.getSendFromUserId());
            parameters.put("NotType", "Manual");
            parameters.put("NotificationDate", dateFormat.format(date));
            parameters.put("SendFrom", notification.getSendFrom());
            parameters.put("If_Withdrawn", "N");
            Number newId = insertActor.executeAndReturnKey(parameters);

            notification.setNotificationId(newId.intValue());
            LOG.info("getNotificationId =" + notification.getNotificationId());
            notification = insertRespondant(notification);
            LOG.info("New Comment inserted. Id=" + notification.getRespondentsList().size());
            insertTaskList(notification);
            transactionManager.commit(status);
        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
        return notification;
    }

    @Override
    public Notification insertRespondant(Notification notification) throws Exception {

        try {
            LOG.info("Inserting Comment + " + notification);
            List<Respondents> respondentsList = new ArrayList<Respondents>();
            for (NotiRespondant nr : notification.getRepondantList()) {
                if (nr.isChecked()) {
                    String tempAprrover = "";
                    String approverCode = "";
                    if (notification.getApprover() != null && notification.getApprover().equalsIgnoreCase(DISTRICT)
                            && nr.getOfficeLevel() != null && (nr.getOfficeLevel().equalsIgnoreCase(PROJECT)
                            || nr.getOfficeLevel().equalsIgnoreCase(SECTOR)
                            || nr.getOfficeLevel().equalsIgnoreCase(DISTRICT))) {
                        tempAprrover = nr.getOfficeLevel();
                    } else if (notification.getApprover() != null && notification.getApprover().equalsIgnoreCase(PROJECT)
                            && nr.getOfficeLevel() != null && (nr.getOfficeLevel().equalsIgnoreCase(PROJECT))) {
                        tempAprrover = DISTRICT;

                    } else if (notification.getApprover() != null && notification.getApprover().equalsIgnoreCase(PROJECT)
                            && nr.getOfficeLevel() != null && (nr.getOfficeLevel().equalsIgnoreCase(SECTOR))) {
                        tempAprrover = PROJECT;

                    } else if (notification.getApprover() != null && notification.getApprover().equalsIgnoreCase(SECTOR)
                            && nr.getOfficeLevel() != null && (nr.getOfficeLevel().equalsIgnoreCase(SECTOR))) {
                        tempAprrover = DISTRICT;

                    }
                    List<NotiRespondant> officeCodeList = getReportingDetails(nr.getOfficeCode(), tempAprrover);
                    if (officeCodeList.size() > 0) {
                        approverCode = officeCodeList.get(0).getOfficeCode();
                    }
                    Map<String, Object> parameters = new HashMap<String, Object>(2);
                    parameters.put("NotificationId", notification.getNotificationId());
                    parameters.put("RespondentType", nr.getOfficeLevel());
                    parameters.put("RespondentCode", nr.getGenericPostId());
                    parameters.put("RespondentUserId", nr.getIncumbencyId());
                    parameters.put("ApproverType", notification.getApprover());
                    parameters.put("ApproverCode", approverCode);
                    parameters.put("RespondentOfficeCode", nr.getOfficeCode());

                    Number newId = insertRespondant.executeAndReturnKey(parameters);

                    Respondents respondents = new Respondents();
                    respondents.setNotificationId(notification.getNotificationId());
                    respondents.setRespondentType(nr.getOfficeLevel());
                    respondents.setRespondentCode(nr.getGenericPostId());
                    respondents.setRespondentUserId(nr.getIncumbencyId());
                    respondents.setRespondentId(newId.intValue());
                    respondents.setRespondentOfficeCode(nr.getOfficeCode());
                    respondentsList.add(respondents);

                    // int i=2/0;
                }
                notification.setRespondentsList(respondentsList);
            }
            // LOG.info("New Comment inserted. Id=" + notification.getNotificationId());
        } catch (Exception e) {
            throw e;
        }
        return notification;
    }

    public List<NotiRespondant> recuReadlist(List<List<NotiRespondant>> lstRespondents, String officeCode, String about) {
        boolean breakFlag = true;
        String tempofficeCode = officeCode;
        List<NotiRespondant> tempLstRespondents = null;
        List<NotiRespondant> currlstRespondents = null;
        while (breakFlag) {

            tempLstRespondents = currlstRespondents;
            LOG.info("tempofficeCode =" + tempofficeCode);
            currlstRespondents = getTaskByOfficeCodeList(tempofficeCode);
            LOG.info("currlstRespondents size =" + currlstRespondents.size());
            LOG.info("about =" + about);
            String tempOfficeLevel = "";
            for (NotiRespondant model : currlstRespondents) {
                tempOfficeLevel = model.getOfficeLevel();
                System.out.println("officeCode > " + model.getOfficeCode());
                System.out.println("officeLevel > " + model.getOfficeLevel());
            }
            if (currlstRespondents.size() == 0 || about.trim().equalsIgnoreCase("")) {
                if (tempLstRespondents != null) {
                    LOG.info("tempLstRespondents add =" + tempLstRespondents.size());
                    LOG.info("lstRespondents.contains(tempLstRespondents) =" + lstRespondents.contains(tempLstRespondents));
                    if (!lstRespondents.contains(tempLstRespondents)) {

                        for (NotiRespondant model : tempLstRespondents) {

                            System.out.println("officeCode > " + model.getOfficeCode());
                            System.out.println("officeLevel > " + model.getOfficeLevel());
                        }
                        lstRespondents.add(tempLstRespondents);
                    }
                }
                breakFlag = false;
            } else if (about.trim().equalsIgnoreCase(tempOfficeLevel)) {
                if (currlstRespondents != null) {
                    LOG.info("tempLstRespondents add =" + currlstRespondents.size());
                    LOG.info("lstRespondents.contains(tempLstRespondents) =" + lstRespondents.contains(currlstRespondents));
                    if (!lstRespondents.contains(currlstRespondents)) {
                        lstRespondents.add(currlstRespondents);
                        for (NotiRespondant model : currlstRespondents) {

                            System.out.println("currlstRespondents officeCode > " + model.getOfficeCode());
                            System.out.println("currlstRespondents officeLevel > " + model.getOfficeLevel());
                        }

                    }
                }
                breakFlag = false;
            } else {
                for (NotiRespondant noti : currlstRespondents) {
                    LOG.info("currlstRespondents getOfficeCode() =" + noti.getOfficeCode());

                    tempofficeCode = noti.getOfficeCode();
                    recuReadlist(lstRespondents, tempofficeCode, about);
                }
            }
        }
        return tempLstRespondents;
    }

    @Override
    public Notification insertTaskList(Notification notification) throws Exception {

        try {
            LOG.info("Inserting Comment + " + notification);
            List<TaskList> taskListArray = new ArrayList<TaskList>();

            for (Respondents rs : notification.getRespondentsList()) {
                LOG.info("rs.getRespondentCode() =" + rs.getRespondentCode());
                LOG.info("rs.getRespondentOfficeCode() =" + rs.getRespondentOfficeCode());
                List<List<NotiRespondant>> lstRespondents = new ArrayList<List<NotiRespondant>>();
                recuReadlist(lstRespondents, rs.getRespondentOfficeCode(), notification.getDetailsAbout());
                // recuReadlist(lstRespondents, rs.getRespondentCode(), notification.getDetailsAbout());
                LOG.info("lstRespondents > " + lstRespondents.size());

                List<TaskList> taskLstList = new ArrayList<TaskList>();
                List<RespondantTaskMap> respTaskMap = new ArrayList<RespondantTaskMap>();

                for (List<NotiRespondant> lst : lstRespondents) {
                    for (NotiRespondant noti : lst) {
                        LOG.info("getRespondentId > " + rs.getRespondentId());
                        LOG.info("getNotificationId > " + notification.getNotificationId());
                        LOG.info("details about > " + noti.getOfficeLevel());
                        LOG.info("details about id getOfficeCode > " + noti.getOfficeCode());
                        Map<String, Object> parameters = new HashMap<String, Object>(2);

                        parameters.put("OriginalNotificationId", notification.getNotificationId());
                        parameters.put("DetailsAbout", noti.getOfficeLevel());
                        parameters.put("DetailsAboutId", noti.getOfficeCode());
                        parameters.put("RespondentId", rs.getRespondentId());
                        parameters.put("Cur_Respondent_Type", rs.getRespondentType());
                        parameters.put("Cur_Respondent_Code", rs.getRespondentCode());
                        parameters.put("Sender", notification.getSendFromCode());

                        Number newId = insertTaskList.executeAndReturnKey(parameters);

                        TaskList taskLst = new TaskList();
                        taskLst.setDetailsAbout(noti.getOfficeLevel());
                        taskLst.setDetailsAboutId(noti.getOfficeCode());
                        taskLst.setNotId(notification.getNotificationId());
                        taskLst.setNotResId(rs.getRespondentId());
                        taskLst.setTaskId(newId.intValue());
                        taskLstList.add(taskLst);
                        // Insert into responant task map

                        Map<String, Object> parametersMap = new HashMap<String, Object>(2);

                        parametersMap.put("RespondentId", rs.getRespondentId());
                        parametersMap.put("TaskId", taskLst.getTaskId());
                        parametersMap.put("Status", "A");
                        parametersMap.put("RespondentType", rs.getRespondentType());
                        parametersMap.put("RespondentCode", rs.getRespondentCode());

                        Number generatedId = insertRespondantTaskMap.executeAndReturnKey(parametersMap);
                        RespondantTaskMap rtm = new RespondantTaskMap();
                        rtm.setRespTaskId(generatedId.intValue());
                        rtm.setNotResId(rs.getRespondentId());
                        rtm.setTaskId(taskLst.getTaskId());
                        rtm.setStatus("A");
                        rtm.setRespondantType(rs.getRespondentType());
                        rtm.setResponantCode(rs.getRespondentCode());
                        respTaskMap.add(rtm);
                        // int i= 2/0;
                    }
                }
                rs.setTaskList(taskLstList);
                rs.setRespondantTaskMap(respTaskMap);

            }
        } catch (Exception e) {
            throw e;
        }

        return notification;
    }

    @Override
    public List<NotiRespondant> respondantList() {

        String sql = "SELECT inc.HRMS_Emp_Id as hrmsIDHOD,inc.Emp_Name as hodName,inc.Emp_Name_Hindi as hodNameHindi,"
                + "sub.Incumbency_Id as incumbencyId,sub.Substattive_Post_Id as substattivePostId,sub.Generic_Post_Id as genericPostId, "
                + "off.office_name as officeName,off.office_name_hindi as officeNameHindi,off.office_id as officeId,off.office_level as officeLevel,off.office_code as officeCode "
                + " FROM g_office off "
                + " left outer join  g_substantive_post sub on off.head_of_office=sub.Substattive_Post_Id "
                + " left outer join incumbency_chart inc on  off.head_of_office=inc.Substattive_Post_Id and inc.Incumbency_Id=sub.Incumbency_Id"
                + " where office_level in ('DIST','PROJECT','SECTOR') order by off.office_level";

        List<NotiRespondant> result = jdbcTemplate.query(sql, icdsModelRowMapper);

        LOG.info("Found " + result.size() + " Respondant records.");

        return result;

    }

    @Override
    public List<NotiRespondant> getTaskByOfficeCodeList(String officeCode) {

        String sql = "SELECT inc.HRMS_Emp_Id as hrmsIDHOD,inc.Emp_Name as hodName,inc.Emp_Name_Hindi as hodNameHindi,"
                + "sub.Incumbency_Id as incumbencyId,sub.Substattive_Post_Id as substattivePostId,"
                + "off.office_name as officeName,off.office_name_hindi as officeNameHindi,off.office_id as officeId,off.office_level as officeLevel,off.office_code as officeCode "
                + " FROM g_office off "
                + " left outer join  g_substantive_post sub on off.head_of_office=sub.Substattive_Post_Id "
                + " left outer join incumbency_chart inc on  off.head_of_office=inc.Substattive_Post_Id "
                + " where off.reporting_office in (select office_id from g_office where office_code='" + officeCode + "') order by off.office_level";

        List<NotiRespondant> result = jdbcTemplate.query(sql, icdsModelRowMapper);

        LOG.info("Found " + result.size() + " TaskByOfficeCode (" + officeCode + " )records.");

        return result;

    }

    @Override
    public InBox setupInBox(String areaCode, String userlevel) {
        InBox inBox = new InBox();
        String userIncumbencyId = getIncumbencyIdByArea(areaCode);
        //  String officeCode=getOfficeCode(areaCode,userlevel);
        inBox.setUserIncumbencyId(userIncumbencyId);
        inBox.setNotificationList(inBoxNotificationList(userIncumbencyId, areaCode));

        int totalReplied = 0;
        int totalApproved = 0;
        int totalAcknowleged = 0;
        int repliedCount = 0;
        int approvedCount = 0;
        int acknowledgedCount = 0;
        for (Notification noti : inBox.getNotificationList()) {
            int reply = 0;
            int ack = 0;
            int approve = 0;
            for (Respondents respondentsTemp : noti.getRespondentsList()) {
                if (respondentsTemp.getRepliedOn() != null && !respondentsTemp.getRepliedOn().trim().isEmpty()) {
                    reply++;
                }
                if (respondentsTemp.getAcknowledgedOn() != null && !respondentsTemp.getAcknowledgedOn().trim().isEmpty()) {
                    ack++;
                }
                if (respondentsTemp.getApprovedOn() != null && !respondentsTemp.getApprovedOn().trim().isEmpty()) {
                    approve++;
                }
                //get information for approver
                LOG.info("All task Completed " + respondentsTemp.isAllTaskCompleted() + " equalsIgnoreCase " + noti.getApprover());
                if (respondentsTemp.isAllTaskCompleted() && respondentsTemp.getRepliedBy() == null || (respondentsTemp.getRepliedBy() != null && respondentsTemp.getRepliedBy().isEmpty())) {
                    noti.setIsReplied(true);
                }
                if (areaCode.equalsIgnoreCase(noti.getApproverCode()) && respondentsTemp.isAllTaskCompleted() && respondentsTemp.getRepliedBy() != null && respondentsTemp.getApprovedBy() == null || (respondentsTemp.getApprovedBy() != null && respondentsTemp.getApprovedBy().isEmpty())) {
                    noti.setIsApprover(true);
                }
                for (TaskList taskListTemp : noti.getTaskList()) {
                    LOG.info(respondentsTemp.getRespondentUserId() + " repondent id with incumbency id " + inBox.getUserIncumbencyId() + " taskListTemp.getStatus() " + taskListTemp.getStatus());
                    if (respondentsTemp.getRespondentUserId().equalsIgnoreCase(inBox.getUserIncumbencyId()) && taskListTemp.getStatus().equalsIgnoreCase(ACTIVE)) {
                        taskListTemp.setMarkCompleted(true);
                    } else {
                        taskListTemp.setMarkCompleted(false);
                    }
                    if (respondentsTemp.getRespondentUserId().equalsIgnoreCase(inBox.getUserIncumbencyId()) && noti.isIsReplied()) {
                        noti.setIsReplied(true);
                    } else {
                        noti.setIsReplied(false);
                    }

                }
            }

            if ((noti.getIfNeedReply() != null && noti.getIfNeedReply().trim().equalsIgnoreCase("Y"))) {
                totalReplied = totalReplied + noti.getRespondentsList().size();
            }
            totalApproved = totalApproved + noti.getRespondentsList().size();
            totalAcknowleged = totalAcknowleged + noti.getRespondentsList().size();

            repliedCount = repliedCount + reply;
            approvedCount = approvedCount + approve;
            acknowledgedCount = acknowledgedCount + ack;

//            //get information for approver
//            LOG.info(userlevel + " equalsIgnoreCase " + noti.getApprover());
//            if (areaCode.equalsIgnoreCase(noti.getApproverCode()) && ) {
//                noti.setIsApprover(true);
//            }
//             else if (userlevel.equalsIgnoreCase(DISTRICT) && (noti.getApprover().equalsIgnoreCase(PROJECT) || noti.getApprover().equalsIgnoreCase(SECTOR) || noti.getApprover().equalsIgnoreCase(AWC))) {
//                noti.setIsApprover(true);
//            } else if (userlevel.equalsIgnoreCase(PROJECT) && (noti.getApprover().equalsIgnoreCase(SECTOR) || noti.getApprover().equalsIgnoreCase(AWC))) {
//                noti.setIsApprover(true);
//            } else if (userlevel.equalsIgnoreCase(SECTOR) && (noti.getApprover().equalsIgnoreCase(AWC))) {
//                noti.setIsApprover(true);
//            } else if (userlevel.equalsIgnoreCase(STATE)) {
//                noti.setIsApprover(true);
//            }
        }
        LOG.info("dashboard " + acknowledgedCount + approvedCount + repliedCount + totalAcknowleged + totalApproved + totalReplied);
        inBox.setAcknowledged(acknowledgedCount);
        inBox.setTotalAcknowledged(totalAcknowleged);
        inBox.setApproved(approvedCount);
        inBox.setTotalApproved(totalApproved);
        inBox.setReplied(repliedCount);
        inBox.setTotalReplied(totalReplied);
        inBox.setTotalNotification(inBox.getNotificationList().size());
        return inBox;
    }

    @Override
    public List<Notification> inBoxNotificationList(String userIncumbencyId, String officeCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String sql = "select "
                + "noti.NotificationId as notificationId,"
                + "noti.NotType as notType,"
                // + "noti.SendFrom as sendFrom,"
                // + "noti.ScheduleId as scheduleId,"
                + "noti.NotificationDate as notificationDate,"
                + "noti.Notification as notification,"
                + "noti.IfNeedReply as ifNeedReply,"
                + "noti.NeedReplyBefore as needReplyBefore,"
                + "noti.DetailsAbout as detailsAbout,"
                + "noti.Form_Id as form_Id,"
                + "noti.NeedApprovalBefore as needApprovalBefore,"
                //+ "noti.Original_Notification_Id as original_Notification_Id,"
                // + "noti.Remark as remark,"
                + "noti.SendFromCode as sendFromCode,"
                + "noti.SendFromUserId as sendFromUserId,"
                + "noti.Approver as approver,"
                + "gen.Generic_Post_Name as genericPostName,"
                + "gen.Generic_Post_Name_Hindi as genericPostNameHindi ,"
                + "res.RespondentId as respondentId ,"
                + "res.ApproverCode as approverCode,"
                + "res.ApproverType as approverType "
                + "from T_NOTIFICATION noti "
                + "left outer join g_generic_post gen on noti.SendFromCode=gen.Generic_Post_Id "
                + "left outer join T_RESPONDENTS res on res.RespondentUserId='" + userIncumbencyId + "' or res.ApproverCode='" + officeCode + "'"
                + " where noti.NotificationId=res.NotificationId";
        LOG.info("SQL " + sql);
        List<Notification> result = jdbcTemplate.query(sql, notificationModelRowMapper);

        LOG.info("Found " + result.size() + " Comment records.");
        //Get Task List

        for (Notification notificationTemp : result) {
            List<TaskList> selectedTaskList = new ArrayList<>();
            if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                notificationTemp.setSubmittedBy(notificationTemp.getGenericPostNameHindi());
            } else {
                notificationTemp.setSubmittedBy(notificationTemp.getGenericPostName());
            }
            notificationTemp.setRespondentsList(inBoxRespondentList(notificationTemp.getRespondentId()));
            //notificationTemp.setTaskList(inBoxTaskList(notificationTemp.getRespondentId()));
            String status = STATUS_ACTIVE;
            for (Respondents resTemp : notificationTemp.getRespondentsList()) {
                boolean respondentApprover = true;
                for (TaskList taskListTemp : resTemp.getTaskList()) {
                    TaskList taskListLocal = new TaskList();
                    taskListLocal = taskListTemp;
                    taskListLocal.setRespondents(resTemp);

                    if (taskListLocal.getRespondentCode().equalsIgnoreCase(resTemp.getRespondentCode())
                            && taskListLocal.getStatus().equalsIgnoreCase(ACTIVE)) {
                        taskListLocal.setMarkCompleted(true);
                        LOG.info("MARK COMPLETED >>>>>>>>>>>>>>  " + taskListLocal.isMarkCompleted());
                    }
                    if (!taskListLocal.getStatus().equalsIgnoreCase(COMPLETED)) {
                        respondentApprover = false;
                    }
                    if (taskListLocal.getStatus().equalsIgnoreCase(REASSIGNED)) {
                        taskListLocal.setRelatedTaskList(inBoxRelatedTaskList(taskListLocal.getDetailsAboutId(), notificationTemp.getNotificationId(), taskListLocal.getRespTaskId()));
                    }
                    selectedTaskList.add(taskListLocal);
                }
                resTemp.setAllTaskCompleted(respondentApprover);

                if (resTemp.getAcknowledgedOn() != null && !resTemp.getAcknowledgedOn().trim().isEmpty()) {
                    status = STATUS_PENDING;
                }
                if (resTemp.getRepliedOn() != null && !resTemp.getRepliedOn().trim().isEmpty()) {
                    status = STATUS_SUBMITTED;
                }
                if (resTemp.getApprovedOn() != null && !resTemp.getApprovedOn().trim().isEmpty()) {
                    status = STATUS_APPROVED;
                }
            }
            notificationTemp.setStatus(status);

            notificationTemp.setTaskList(selectedTaskList);
            if (notificationTemp.getNotificationDate() != null) {
                notificationTemp.setNotificationDate(dateFormat(notificationTemp.getNotificationDate()));
            } else {
                notificationTemp.setNotificationDate("No Date Available");
            }

        }

        return result;
    }

    @Override
    public List<Respondents> inBoxRespondentList(int respondentId) {
        String sql = "SELECT "
                + "RespondentId as respondentId,"
                + "NotificationId as notificationId,"
                + "RespondentType as respondentType,"
                + "RespondentCode as respondentCode,"
                + "AcknowledgedOn as acknowledgedOn,"
                + "AcknowledgedBy as acknowledgedBy,"
                + "RepliedOn as repliedOn,"
                + "RepliedBy as repliedBy,"
                + "ApprovedOn as approvedOn,"
                + "ApprovedBy as approvedBy,"
                + "RespondentUserId as respondentUserId"
                + " FROM T_RESPONDENTS "
                + "where RespondentId=" + respondentId;

        List<Respondents> result = jdbcTemplate.query(sql, respondentsRowMapper);

        LOG.info("Found " + result.size() + " Comment records.");

        for (Respondents res : result) {
            res.setTaskList(inBoxTaskList(res.getRespondentId()));

        }

        return result;

    }

    @Override
    public void markCompleted(String status, int respTaskId) {
        String updateSql = "UPDATE T_RESPONDANT_TASK_MAP SET Status=? WHERE RespTaskId=?;";
        Object[] params = {status, respTaskId};
        int rows = jdbcTemplate.update(updateSql, params);
        LOG.info(rows + " row(s) updated.");
    }

    @Override
    public List<TaskList> inBoxTaskList(int respondentId) {
        String sql = "select "
                + "task.TaskId as taskId,"
                + "OriginalNotificationId as notId,"
                + "CurrentNotificationId as currentNotificationId,"
                + "DetailsAbout as detailsAbout,"
                + "DetailsAboutId as detailsAboutId,"
                + "FormId as formId,"
                + "FormTableTranId as formTableTranId,"
                + "Cur_Respondent_Type as cur_Respondent_Type,"
                + "Cur_Respondent_Code as cur_Respondent_Code ,"
                + "If_Complied as if_Complied,"
                + "task.RespondentId  as notResId,"
                + "CompletedByType  as completedByType,"
                + "CompletedByCode  as completedByCode,"
                + "CompletedByUserId  as completedByUserId,"
                + "CompletedOn  as completedOn, "
                + "Status  as status, "
                + "RespondentType  as respondentType, "
                + "RespondentCode  as respondentCode, "
                + "RespTaskId  as respTaskId, "
                + "RespTaskId  as respTaskId "
                + " from T_TASKLIST task "
                + "left outer join T_RESPONDANT_TASK_MAP map on map.TaskId=task.TaskId "
                + "where task.RespondentId=" + respondentId;
        LOG.info("SQL  " + sql);
        List<TaskList> result = jdbcTemplate.query(sql, taskListRowMapper);

        LOG.info("Found " + result.size() + " Comment records.");

        return result;

    }

    @Override
    public List<TaskList> inBoxRelatedTaskList(String detailsAboutId, int notificationID, int respTaskId) {
        String sql = "select "
                + "task.TaskId as taskId,"
                + "OriginalNotificationId as notId,"
                + "CurrentNotificationId as currentNotificationId,"
                + "DetailsAbout as detailsAbout,"
                + "DetailsAboutId as detailsAboutId,"
                + "FormId as formId,"
                + "FormTableTranId as formTableTranId,"
                + "Cur_Respondent_Type as cur_Respondent_Type,"
                + "Cur_Respondent_Code as cur_Respondent_Code ,"
                + "If_Complied as if_Complied,"
                + "task.RespondentId  as notResId,"
                + "CompletedByType  as completedByType,"
                + "CompletedByCode  as completedByCode,"
                + "CompletedByUserId  as completedByUserId,"
                + "CompletedOn  as completedOn, "
                + "Status  as status, "
                + "RespondentType  as respondentType, "
                + "RespondentCode  as respondentCode, "
                + "RespTaskId  as respTaskId "
                + " from T_TASKLIST task "
                + "left outer join T_RESPONDANT_TASK_MAP map on map.TaskId=task.TaskId "
                + "where OriginalNotificationId=" + notificationID + " and DetailsAboutId=" + detailsAboutId + " and RespTaskId not in (" + respTaskId + ")  "
                + " Order by task.TaskId desc";
        LOG.info("SQL  " + sql);
        List<TaskList> result = jdbcTemplate.query(sql, taskListRowMapper);

        LOG.info("Found " + result.size() + " Comment records.");

        return result;

    }

    @Override
    public OutBox setupOutBox(String userId) {
        OutBox outBox = new OutBox();
        LOG.info("lm.getUserid() >> " + userId);
        outBox.setNotificationList(outboxNotificationList(userId));
        outBox.setTotalNotification(outBox.getNotificationList().size());
        int totalReplied = 0;
        int totalApproved = 0;
        int totalAcknowleged = 0;
        int repliedCount = 0;
        int approvedCount = 0;
        int acknowledgedCount = 0;
        for (Notification noti : outBox.getNotificationList()) {
            if ((noti.getIfNeedReply() != null && noti.getIfNeedReply().trim().equalsIgnoreCase("Y"))) {
                totalReplied = totalReplied + noti.getRespondentsList().size();
            }
            totalApproved = totalApproved + noti.getRespondentsList().size();
            totalAcknowleged = totalAcknowleged + noti.getRespondentsList().size();
            repliedCount = repliedCount + noti.getRepliedCount();
            approvedCount = approvedCount + noti.getApprovedCount();
            acknowledgedCount = acknowledgedCount + noti.getAcknowledgedCount();

        }
        LOG.info("dashboard " + acknowledgedCount + approvedCount + repliedCount + totalAcknowleged + totalApproved + totalReplied);
        outBox.setAcknowledged(acknowledgedCount);
        outBox.setTotalAcknowledged(totalAcknowleged);
        outBox.setApproved(approvedCount);
        outBox.setTotalApproved(totalApproved);
        outBox.setReplied(repliedCount);
        outBox.setTotalReplied(totalReplied);
        outBox.setSelNotiResponseTime(userId);

        return outBox;
    }

    @Override
    public List<Notification> outboxNotificationList(String userId) {
        LOG.info("userId " + userId);
        Locale locale = LocaleContextHolder.getLocale();

        String sql = "SELECT NotificationId as notificationId,NotType as notType,Notification as notification,"
                + "NotificationDate as notificationDate,IfNeedReply as ifNeedReply, "
                + "NeedReplyBefore as needReplyBefore,NeedApprovalBefore as needApprovalBefore,"
                + "DetailsAbout as detailsAbout,If_Withdrawn as ifWithdrawn"
                + " FROM T_NOTIFICATION  WHERE SendFromUserId='" + userId + "'";

        LOG.info("sql " + sql);
        List<Notification> result = jdbcTemplate.query(sql, notificationModelRowMapper);

        LOG.info("Found " + result.size() + " Notification for outBox records.");
        Map<String, Respondents> respondentOnDist = new HashMap<>();

        for (Notification noti : result) {
            noti.setRespondentsList(getRespondant(noti.getNotificationId()));
            if (noti.getRespondentsList().size() > 0) {

                LOG.info("getRespondents " + noti.getRespondents());
                noti.setTaskCount(noti.getRespondentsList().size());
                int ackCount = 0;
                int relyCount = 0;
                int apprCount = 0;

                for (Respondents res : noti.getRespondentsList()) {

                    if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                        noti.setRespondents(res.getRespondentPostNameHindi());
                    } else {
                        noti.setRespondents(res.getRespondentPostName());
                    }
                    LOG.info("res.getRespondentCode() " + res.getRespondentCode());
                    LOG.info("res.getRespondentType() " + res.getRespondentType());
                    List<NotiRespondant> notiRespLst = getReportingDetails(res.getRespondentOfficeCode(), res.getRespondentType());

                    for (NotiRespondant notiResp : notiRespLst) {

                        LOG.info("notiResp.getReportingOffice() out " + notiResp.getReportingOffice());
                        if (res.getRespondentType() != null && !res.getRespondentType().trim().isEmpty()
                                && res.getRespondentType().trim().equalsIgnoreCase("SECTOR")) {
                            res.setRespReportingDist(notiResp.getOfficeId());
                            res.setRespReportingDistName(notiResp.getOfficeName());
                            //Project details
                            List<NotiRespondant> localProjectLst = getReportingDetails(res.getRespondentOfficeCode(), "PROJECT");
                            LOG.info("localProjectLst.size() " + localProjectLst.size());
                            if (localProjectLst.size() > 0) {
                                res.setRespReportingProjName(localProjectLst.get(0).getOfficeName());
                                res.setRespReportingProj(localProjectLst.get(0).getOfficeId());
                                LOG.info("localProjectLst.name " + localProjectLst.get(0).getOfficeName());
                            } else {
                                res.setRespReportingDistName("Name Blank");
                            }
                            //Sector details
                            List<NotiRespondant> localLst = getOfficeDetails(res.getRespondentOfficeCode());
                            LOG.info("localLst.size() " + localLst.size());
                            if (localLst.size() > 0) {
                                res.setRespReportingSecName(localLst.get(0).getOfficeName());
                                res.setRespReportingSec(localLst.get(0).getOfficeId());
                                LOG.info("localLst.name " + localLst.get(0).getOfficeName());
                            } else {
                                res.setRespReportingDistName("Name Blank");
                            }

                            LOG.info(" District " + res.getRespReportingDistName());
                            LOG.info(" Project " + res.getRespReportingProjName());
                            LOG.info(" Sector " + res.getRespReportingSecName());

                        } else if (res.getRespondentType() != null && !res.getRespondentType().trim().isEmpty() && res.getRespondentType().trim().equalsIgnoreCase("PROJECT")) {

                            LOG.info("notiResp.getOfficeId()) " + notiResp.getOfficeId());
                            LOG.info("notiResp.getOfficeName() " + notiResp.getOfficeName());
                            LOG.info("res.getRespondentOfficeCode()" + res.getRespondentOfficeCode());
                            res.setRespReportingDist(notiResp.getOfficeId());
                            res.setRespReportingDistName(notiResp.getOfficeName());
                            List<NotiRespondant> localLst = getOfficeDetails(res.getRespondentOfficeCode());

                            LOG.info("localLst.size() " + localLst.size());
                            if (localLst.size() > 0) {
                                res.setRespReportingProjName(localLst.get(0).getOfficeName());
                                res.setRespReportingProj(localLst.get(0).getOfficeId());
                                LOG.info("localLst.name " + localLst.get(0).getOfficeName());

                            } else {
                                res.setRespReportingProjName("Name Blank");
                                res.setRespReportingProj(localLst.get(0).getOfficeId());
                                // res.setRespReportingDistName("Name Blank");
                            }

                            LOG.info(" District " + res.getRespReportingDistName());
                            LOG.info(" Project " + res.getRespReportingProjName());

                        } else {
                            res.setRespReportingDistName(notiResp.getOfficeName());
                            res.setRespReportingDist(notiResp.getOfficeId());
                            LOG.info("Else District " + res.getRespReportingDistName());
                            LOG.info("Else Project " + res.getRespReportingProjName());
                        }

                    }
                    LOG.info("getRespondents " + noti.getRespondents());
                    if (res.getAcknowledgedOn() != null && !res.getAcknowledgedOn().equalsIgnoreCase("")) {
                        ackCount++;
                    }
                    if (res.getRepliedOn() != null && !res.getRepliedOn().equalsIgnoreCase("")) {
                        relyCount++;
                    }
                    if (res.getApprovedOn() != null && !res.getApprovedOn().equalsIgnoreCase("")) {
                        apprCount++;
                    }

                    noti.setNotificationDate(dateFormat(noti.getNotificationDate()));
//                    try {
//                        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        Calendar cal = Calendar.getInstance();
//                        if (noti.getNotificationDate().length() > 21) {
//                            cal.setTime(dtf.parse(noti.getNotificationDate()));
//                        } else {
//                            cal.setTime(dateFormat.parse(noti.getNotificationDate()));
//                        }
//                        noti.setNotificationDate(dateFormat.format(cal.getTime()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }

                noti.setApprovedCount(apprCount);
                noti.setRepliedCount(relyCount);
                noti.setAcknowledgedCount(ackCount);
            }

        }

        return result;

    }

    @Override
    public Map<String, List<Respondents>> getRespondantOnDist(List<Respondents> respondentsLst) {

        Map<String, List<Respondents>> resMap = new HashMap<>();
        Respondents localRespondent = new Respondents();
        String dist = "";
        Set<String> distSet = new HashSet();
        for (Respondents res : respondentsLst) {
            LOG.info("RespReportingDist() " + res.getRespReportingDist());
            distSet.add(res.getRespReportingDist());
        }
        LOG.info("District " + distSet);
        for (String distTemp : distSet) {
            List<Respondents> listRespondents = new ArrayList<>();
            for (Respondents resTemp : respondentsLst) {
                if (resTemp.getRespReportingDist() != null && distTemp.equalsIgnoreCase(resTemp.getRespReportingDist())) {
                    listRespondents.add(resTemp);
                }
            }
            resMap.put(distTemp, listRespondents);
        }

        return resMap;
    }

    public List<Respondents> getRespondant(int notificationId) {

        String sql = "select gen.Generic_Post_Name as respondentPostName,gen.Generic_Post_Name_Hindi as respondentPostNameHindi, "
                + " gen.Generic_Post_Id as respondentPostId,RespondentId as respondentId, "
                + " NotificationId as notificationId,RespondentType as respondentType,RespondentCode as respondentCode, "
                + " AcknowledgedOn as acknowledgedOn,AcknowledgedBy as acknowledgedBy, "
                + " RepliedOn as repliedOn,RepliedBy as repliedBy, "
                + " ApprovedOn as approvedOn,ApprovedBy as approvedBy, "
                + " RespondentUserId as respondentUserId, "
                + "off.head_of_office as headOfOffice,"
                + "off.office_code as respondentOfficeCode"
                + " from T_RESPONDENTS res "
                + " left outer join g_generic_post gen on gen.Generic_Post_Id in (select def.Generic_Post_Id from g_default_post def where def.Is_HOO='Y' and def.Office_Level= res.RespondentType) "
                + "left outer join g_office off on off.head_of_office in (select Substattive_Post_Id from incumbency_chart inc where inc.Incumbency_Id=res.RespondentUserId) "
                + " where NotificationId=" + notificationId;

        List<Respondents> result = jdbcTemplate.query(sql, respondentsRowMapper);

        LOG.info("Found " + result.size() + " Notification for outBox records.");
        for (Respondents res : result) {
            res.setTaskList(inBoxTaskList(res.getRespondentId()));
            boolean activeFlag = true;
            for (TaskList taskListTemp : res.getTaskList()) {

                if (taskListTemp.getStatus().equalsIgnoreCase(ACTIVE)) {
                    activeFlag = false;
                }
            }
            res.setAllTaskCompleted(activeFlag);
        }

        return result;
    }

    @Override
    public Map<String, String> getOptionByRespoLevel(String reportingOfficeCode, String respondentLevel, Locale locale) {
        LOG.info("Found " + reportingOfficeCode + " Notification for " + respondentLevel + " records.");
        String sql = "";
        List<NotiRespondant> result = null;
        Map<String, String> options = new HashMap<String, String>();
        if (respondentLevel.equalsIgnoreCase("DIST")) {
            sql = "select off.office_name as officeName,off.office_name_hindi as officeNameHindi ,off.office_id as officeId "
                    + "from  g_office off where off.office_id='" + reportingOfficeCode + "'";
            result = jdbcTemplate.query(sql, icdsModelRowMapper);

            LOG.info("Found " + result.size() + " Notification for outBox records.");
        } else if (respondentLevel.equalsIgnoreCase("PROJECT")) {
            sql = "select off1.office_name as officeName,off1.office_name_hindi as officeNameHindi ,off1.office_id as officeId "
                    + "from g_office off1 where off1.reporting_office in "
                    + "('" + reportingOfficeCode + "')";

            result = jdbcTemplate.query(sql, icdsModelRowMapper);

            LOG.info("Found " + result.size() + " Notification for outBox records.");
        } else if (respondentLevel.equalsIgnoreCase("SECTOR")) {
            sql = "select off2.office_name as officeName,off2.office_name_hindi as officeNameHindi ,off2.office_id as officeId "
                    + "from g_office off2 where off2.reporting_office in ("
                    + "select off1.reporting_office "
                    + "from g_office off1 where off1.office_id in "
                    + "('" + reportingOfficeCode + "'))";
            result = jdbcTemplate.query(sql, icdsModelRowMapper);

            LOG.info("Found " + result.size() + " Notification for outBox records.");
        }

        if (result.size() > 0) {
            for (NotiRespondant notiRes : result) {
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    options.put(notiRes.getOfficeId(), notiRes.getOfficeNameHindi());
                } else {
                    options.put(notiRes.getOfficeId(), notiRes.getOfficeName());
                }
            }
        }

        return options;
    }

    public List<NotiRespondant> getReportingDetails(String officeCode, String respondentLevel) {
        Locale locale = LocaleContextHolder.getLocale();
        String postCode = "0";
        String sql = "";
        LOG.info("getReportingDetails officeCode - " + officeCode);
        LOG.info("getReportingDetails respondentLevel - " + respondentLevel);
        if (respondentLevel.equalsIgnoreCase("SECTOR")) {
            sql = "select office_name as officeName,office_name_hindi as officeNameHindi ,office_id as officeId,reporting_office as reportingOffice,office_code as officeCode from g_office where office_id in"
                    + "(select reporting_office from g_office where office_id in "
                    + "(select reporting_office from g_office where office_code in ('" + officeCode + "')))";
        } else if (respondentLevel.equalsIgnoreCase("PROJECT")) {
            sql = "select office_name as officeName,office_name_hindi as officeNameHindi ,office_id as officeId,reporting_office as reportingOffice, office_code as officeCode from g_office where office_id in "
                    + "(select reporting_office from g_office where office_code in ('" + officeCode + "'))";
        } else {
            sql = "select office_name as officeName,office_name_hindi as officeNameHindi ,office_id as officeId,reporting_office as reportingOffice, office_code as officeCode from g_office where office_code in "
                    + " ('" + officeCode + "')";
        }
        LOG.info("getReportingDetails Sql - " + sql);
        List<NotiRespondant> result = jdbcTemplate.query(sql, icdsModelRowMapper);

        LOG.info("Found " + result.size() + " ReportingDetails records.");
        if (result.size() > 0) {
            for (NotiRespondant notiRes : result) {
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    notiRes.setOfficeName(notiRes.getOfficeNameHindi());
                } else {
                    notiRes.setOfficeName(notiRes.getOfficeName());
                }
            }
        }
        return result;
    }

    public List<NotiRespondant> getOfficeDetails(String officeCode) {
        String postCode = "0";
        String sql = "";
        if (officeCode.contains("P") || officeCode.contains("D") || officeCode.contains("S")) {
            sql = "select office_name as officeName,office_name_hindi as officeNameHindi ,office_id as officeId,reporting_office as reportingOffice from g_office where office_id in "
                    + "('" + officeCode + "')";
        } else {
            sql = "select office_name as officeName,office_name_hindi as officeNameHindi ,office_id as officeId,reporting_office as reportingOffice from g_office where office_code in "
                    + "('" + officeCode + "')";
        }

        List<NotiRespondant> result = jdbcTemplate.query(sql, icdsModelRowMapper);

        LOG.info("getOfficeDetails Found " + result.size() + "  records.");

        return result;
    }

    @Override
    public String getUserPostCode(String areaCode) {
        String postCode = "0";
        String sql = "select Generic_Post_Id as sendFromCode from g_substantive_post where Substattive_Post_Id in (select head_of_office from g_office where office_code='" + areaCode + "')";

        List<Notification> result = jdbcTemplate.query(sql, notificationModelRowMapper);

        LOG.info("Found " + result.size() + " Comment records.");

        for (Notification noti : result) {

            postCode = noti.getSendFromCode();

        }
        LOG.info("User PostCode " + postCode);
        return postCode;
    }

    @Override
    public String addDays(String date, int days) {
        //String date="2011-10-08";//can take any date in current format  
        String convertedDate = null;
        try {
            SimpleDateFormat dtf
                    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFormat.parse(date));
            cal.add(Calendar.DATE, days);
            convertedDate = dateFormat.format(cal.getTime());
            System.out.println(date + " increase by " + days + ".." + convertedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public String getIncumbencyIdByArea(String areaCode) {
        String incumbencyId = "0";
        String sql = "select Incumbency_Id as incumbencyId from g_substantive_post where Is_Occupied='Y' and Substattive_Post_Id in ("
                + "select head_of_office from g_office where office_code='" + areaCode + "' )";

        List<NotiRespondant> result = jdbcTemplate.query(sql, icdsModelRowMapper);

        LOG.info("Found " + result.size() + " Comment records.");

        for (NotiRespondant noti : result) {

            incumbencyId = noti.getIncumbencyId();

        }
        LOG.info("User incumbencyId " + incumbencyId);
        return incumbencyId;
    }

    public String dateFormat(String date) {

        String formatedDate = "";
        try {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            if (date.length() > 21) {
                cal.setTime(dtf.parse(date));
            } else {
                cal.setTime(dateFormat.parse(date));
            }
            formatedDate = dateFormat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formatedDate;
    }

    @Override
    public List<NotiRespondant> getOfficeByOfficeCodeList(String officeCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> getOptionBySet(Set<String> setOfOfficeId, Locale locale) {

        LOG.info("Found " + setOfOfficeId + " set office id");
        String sql = "";
        StringBuffer strBuff = new StringBuffer();
        int count = 1;
        for (String str : setOfOfficeId) {
            strBuff.append("'" + str + "'" + (setOfOfficeId.size() == count ? "" : ","));
            count++;
        }
        LOG.info("strBuff " + strBuff);
        List<NotiRespondant> result = null;
        Map<String, String> options = new HashMap<String, String>();

        sql = "select off.office_name as officeName,off.office_name_hindi as officeNameHindi ,off.office_id as officeId "
                + "from g_office off where office_id in ( " + strBuff + ")";
        LOG.info("sql " + sql);
        result = jdbcTemplate.query(sql, icdsModelRowMapper);

        LOG.info("Found " + result.size() + " Notification for outBox records.");

        if (result.size() > 0) {
            for (NotiRespondant notiRes : result) {
                if (locale != null && locale.toString().equalsIgnoreCase("hindi")) {
                    options.put(notiRes.getOfficeId(), notiRes.getOfficeNameHindi());
                } else {
                    options.put(notiRes.getOfficeId(), notiRes.getOfficeName());
                }
            }
        }

        return options;
    }

    @Override
    public Map<String, String> getOptionByRespoLevelNdCode(String officeCode, String respondentLevel, Locale locale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveReassign(TaskList taskList, String currentRespondent, int notificationId, String aboutId, String about, int respTaskId, String sender) {

        //find the  code  ofgeneric post and incumbancy id from selected current respondant
        //insert a row in respondent 
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //get Proejct details
            List<NotiRespondant> result = null;
            String sql = "select off.office_id  as officeId ,"
                    + "off.office_name as officeName,"
                    + "off.office_name_hindi as officeNameHindi,"
                    + "off.office_level as officeLevel,"
                    + "off.office_code  as officeCode,"
                    + "off.head_of_office as headOfOffice,"
                    + "sub.Generic_Post_Id as genericPostId,"
                    + "sub.Incumbency_Id as incumbencyId from g_office off "
                    + "left outer join g_substantive_post sub on off.head_of_office = sub.Substattive_Post_Id"
                    + " where off.office_id in ( select reporting_office from g_office where office_code ='" + aboutId + "')";
            LOG.info("sql " + sql);
            result = jdbcTemplate.query(sql, icdsModelRowMapper);

            LOG.info("Found " + result.size() + " Notification for outBox records.");
//            List<Respondents> respondentsList = new ArrayList<Respondents>();
            Number newId;
            for (NotiRespondant nr : result) {

                Map<String, Object> parametersResp = new HashMap<String, Object>();

                parametersResp.put("NotificationId", notificationId);
                parametersResp.put("RespondentType", nr.getOfficeLevel());
                parametersResp.put("RespondentCode", nr.getGenericPostId());
                parametersResp.put("RespondentUserId", nr.getIncumbencyId());

                newId = insertRespondant.executeAndReturnKey(parametersResp);

                Map<String, Object> parameters = new HashMap<String, Object>();

                parameters.put("OriginalNotificationId", notificationId);
                parameters.put("DetailsAbout", about);
                parameters.put("DetailsAboutId", aboutId);
                parameters.put("RespondentId", newId.intValue());
                parameters.put("Cur_Respondent_Type", nr.getOfficeLevel());
                parameters.put("Cur_Respondent_Code", nr.getGenericPostId());
                parameters.put("Sender", sender);

                Number newTaskId = insertTaskList.executeAndReturnKey(parameters);

                Map<String, Object> parametersMap = new HashMap<String, Object>();

                parametersMap.put("RespondentId", newId.intValue());
                parametersMap.put("TaskId", newTaskId.intValue());
                parametersMap.put("Status", "A");
                parametersMap.put("RespondentType", nr.getOfficeLevel());
                parametersMap.put("RespondentCode", nr.getGenericPostId());

                Number generatedId = insertRespondantTaskMap.executeAndReturnKey(parametersMap);

                markCompleted("R", respTaskId);
            }
            // LOG.info("New Comment inserted. Id=" + notification.getNotificationId());
            transactionManager.commit(status);
        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
        // create a task in tasklit
        //create a map 
    }

    @Override
    public void withdrawFullNotification(List<Respondents> respondentsLst, int notificationId) {

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            for (Respondents res : respondentsLst) {
                LOG.info("getRespondentId() " + res.getRespondentId());

                String updateSql = "UPDATE T_RESPONDANT_TASK_MAP SET Status=? WHERE RespondentId=? and Status='A'";
                Object[] params = {"W", res.getRespondentId()};
                int rows = jdbcTemplate.update(updateSql, params);
                LOG.info(rows + " row(s) updated.");
            }
            String updateNotificationSql = "UPDATE T_NOTIFICATION SET If_Withdrawn=? WHERE NotificationId=? ";
            Object[] paramsNoti = {"Y", notificationId};
            int rows = jdbcTemplate.update(updateNotificationSql, paramsNoti);
            // LOG.info("New Comment inserted. Id=" + notification.getNotificationId());
            transactionManager.commit(status);

        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
    }

    @Override
    public void withdrawRespondent(List<Respondents> respondentsLst, int notificationId, int respondentID) {

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            for (Respondents res : respondentsLst) {
                if (respondentID == res.getRespondentId()) {
                    LOG.info(respondentID + " getRespondentId() " + res.getRespondentId());
                    String updateSql = "UPDATE T_RESPONDANT_TASK_MAP SET Status=? WHERE RespondentId=? and Status='A'";
                    Object[] params = {"W", res.getRespondentId()};
                    int rows = jdbcTemplate.update(updateSql, params);
                    LOG.info(rows + " row(s) updated withdrawRespondent.");
                }
            }
            String updateNotificationSql = "UPDATE T_NOTIFICATION SET If_Withdrawn=? WHERE NotificationId=? ";
            Object[] paramsNoti = {"Y", notificationId};
            int rows = jdbcTemplate.update(updateNotificationSql, paramsNoti);
            // LOG.info("New Comment inserted. Id=" + notification.getNotificationId());
            transactionManager.commit(status);

        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
    }

    @Override
    public void approvedRespondent(String userIncumbencyId, int notificationId, int respondentID
    ) {
        LOG.info("approvedRespondent : userIncumbencyId - " + userIncumbencyId + " notificationId - " + notificationId + " respondentID - " + respondentID);

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            LOG.info(dateFormat.format(date));
            LOG.info(respondentID + " getRespondentId() " + respondentID);
            String updateSql = "UPDATE T_RESPONDENTS SET ApprovedOn=?,ApprovedBy=? WHERE RespondentId=? and (ApprovedBy IS  NULL or ApprovedBy='')";
            Object[] params = {dateFormat.format(date), userIncumbencyId, respondentID};
            int rows = jdbcTemplate.update(updateSql, params);
            LOG.info(rows + " row(s) updated.");

            transactionManager.commit(status);

        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
    }

    @Override
    public void acknowlegedRespondent(String userIncumbencyId, int notificationId, int respondentID
    ) {

        LOG.info("acknowlegedRespondent : userIncumbencyId - " + userIncumbencyId + " notificationId - " + notificationId + " respondentID - " + respondentID);
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            LOG.info(dateFormat.format(date));

            LOG.info(respondentID + " getRespondentId() ");
            String updateSql = "UPDATE T_RESPONDENTS SET AcknowledgedOn=?,AcknowledgedBy=? WHERE RespondentId=? and (AcknowledgedBy IS NULL or AcknowledgedBy='')";
            Object[] params = {dateFormat.format(date), userIncumbencyId, respondentID};
            int rows = jdbcTemplate.update(updateSql, params);
            LOG.info(rows + " row(s) updated.");

            transactionManager.commit(status);

        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
    }

    @Override
    public void repliedOnRespondent(String userIncumbencyId, int notificationId, int respondentID
    ) {
        LOG.info("repliedOnRespondent : userIncumbencyId - " + userIncumbencyId + " notificationId - " + notificationId + " respondentID - " + respondentID);

        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            LOG.info(dateFormat.format(date));

            LOG.info(respondentID + " getRespondentId() ");
            String updateSql = "UPDATE T_RESPONDENTS SET RepliedOn=?,RepliedBy=? WHERE RespondentId=? and (RepliedBy IS NULL or RepliedBy='')";
            Object[] params = {dateFormat.format(date), userIncumbencyId, respondentID};
            int rows = jdbcTemplate.update(updateSql, params);
            LOG.info(rows + " row(s) updated.");

            transactionManager.commit(status);

        } catch (Exception e) {
            System.out.println("Error in creating record, rolling back");
            e.printStackTrace();

            transactionManager.rollback(status);
        }
    }

    @Override
    public void completedTask(String ifComplied, String completedByType, String completedByCode, String completedByUserId, int taskID
    ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        LOG.info(dateFormat.format(date));

        String updateSql = "UPDATE T_TASKLIST SET If_Complied=? ,CompletedByType=?,CompletedByCode=?,CompletedByUserId=?,CompletedOn=? WHERE TaskId=?;";
        Object[] params = {ifComplied, completedByType, completedByCode, completedByUserId, dateFormat.format(date), taskID};
        int rows = jdbcTemplate.update(updateSql, params);
        LOG.info(rows + " row(s) updated.");
    }

}
