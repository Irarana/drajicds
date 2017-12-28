/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.dao.notification;

import dricds.model.notification.InBox;
import dricds.model.notification.NotiRespondant;
import dricds.model.notification.Notification;
import dricds.model.notification.OutBox;
import dricds.model.notification.Respondents;
import dricds.model.notification.TaskList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ekank
 */
public interface NotificationDao {

    public Notification insert(Notification notification);

    public List<NotiRespondant> respondantList();

    public InBox setupInBox(String areaCode, String userlevel);

    public List<Notification> inBoxNotificationList(String userIncumbencyId, String officeCode);

    public List<TaskList> inBoxTaskList(int respondentId);

    public List<Respondents> inBoxRespondentList(int notificationId);

    public OutBox setupOutBox(String userId);

    public List<Notification> outboxNotificationList(String userId);

    public String getUserPostCode(String areaCode);

    public Notification insertRespondant(Notification notification) throws Exception;

    public Notification insertTaskList(Notification notification) throws Exception;

    public List<NotiRespondant> getTaskByOfficeCodeList(String officeCode);

    public String addDays(String date, int days);

    public Map<String, String> getOptionByRespoLevel(String officeCode, String respondentLevel, Locale locale);

    public Map<String, List<Respondents>> getRespondantOnDist(List<Respondents> respondentsLst);

    public void markCompleted(String status, int respTaskId);

    public List<TaskList> inBoxRelatedTaskList(String detailsAboutId, int notificationID, int respondentId);

    public List<NotiRespondant> getOfficeByOfficeCodeList(String officeCode);

    public Map<String, String> getOptionBySet(Set<String> setOfOfficeId, Locale locale);

    public Map<String, String> getOptionByRespoLevelNdCode(String officeCode, String respondentLevel, Locale locale);

    public void saveReassign(TaskList taskList, String currentRespondent, int notificationId, String aboutId, String about, int respTaskId, String sender);

    public void withdrawFullNotification(List<Respondents> respondentsLst, int notificationId);

    public void withdrawRespondent(List<Respondents> respondentsLst, int notificationId, int respondentID);

    public void repliedOnRespondent(String userIncumbencyId, int notificationId, int respondentID);

    public void acknowlegedRespondent(String userIncumbencyId, int notificationId, int respondentID);

    public void approvedRespondent(String userIncumbencyId, int notificationId, int respondentID);

    public void completedTask(String ifComplied, String completedByType, String completedByCode, String completedByUserId,  int taskID);

}
