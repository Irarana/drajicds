/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.notification;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ekank
 */
public class Notification implements Serializable {

    private int notificationId;
    private String notType;
    private String sendFrom;
    private int scheduleId;
    private String notificationDate;
    private String notification;
    private String ifNeedReply;
    private boolean needReply;
    private int needReplyBefore;
    private String detailsAbout;
    private int form_Id;
    private int needApprovalBefore;
    private int original_Notification_Id;
    private String remark;
    private String sendFromCode;
    private String sendFromUserId;
    private String approver;
    private String attachment;
    private boolean allDistrict;
    private boolean allProject;
    private boolean allSector;

    private List<NotiRespondant> repondantList;
    List<String> selectedCheckBox;
    private String selectedDistric;
    private String selectedProject;
    private String selectedSector;

    private List<TaskList> taskList;
    private String submittedBy;
    private String status;
    private String respondents;
    private String respondentsHindi;
    private String respondentsCode;
    private String type;
    private List<Respondents> respondentsList;
    private int taskCount;
    private int acknowledgedCount;
    private int repliedCount;
    private int approvedCount;
    private String genericPostName;
    private int respondentId;
    private String genericPostNameHindi;
    private boolean isApprover;
    private String ifWithdrawn;
    private String approverCode;
    private String approverType;
    private boolean isReplied;

    public boolean isIsReplied() {
        return isReplied;
    }

    public void setIsReplied(boolean isReplied) {
        this.isReplied = isReplied;
    }

    public String getApproverCode() {
        return approverCode;
    }

    public void setApproverCode(String approverCode) {
        this.approverCode = approverCode;
    }

    public String getApproverType() {
        return approverType;
    }

    public void setApproverType(String approverType) {
        this.approverType = approverType;
    }

    public String getIfWithdrawn() {
        return ifWithdrawn;
    }

    public void setIfWithdrawn(String ifWithdrawn) {
        this.ifWithdrawn = ifWithdrawn;
    }

    public boolean isIsApprover() {
        return isApprover;
    }

    public void setIsApprover(boolean isApprover) {
        this.isApprover = isApprover;
    }

    public String getGenericPostNameHindi() {
        return genericPostNameHindi;
    }

    public void setGenericPostNameHindi(String genericPostNameHindi) {
        this.genericPostNameHindi = genericPostNameHindi;
    }

    public int getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(int respondentId) {
        this.respondentId = respondentId;
    }

    public String getGenericPostName() {
        return genericPostName;
    }

    public void setGenericPostName(String genericPostName) {
        this.genericPostName = genericPostName;
    }

    public String getSelectedSector() {
        return selectedSector;
    }

    public void setSelectedSector(String selectedSector) {
        this.selectedSector = selectedSector;
    }

    public boolean isAllSector() {
        return allSector;
    }

    public void setAllSector(boolean allSector) {
        this.allSector = allSector;
    }

    public int getAcknowledgedCount() {
        return acknowledgedCount;
    }

    public void setAcknowledgedCount(int acknowledgedCount) {
        this.acknowledgedCount = acknowledgedCount;
    }

    public int getRepliedCount() {
        return repliedCount;
    }

    public void setRepliedCount(int repliedCount) {
        this.repliedCount = repliedCount;
    }

    public int getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(int approvedCount) {
        this.approvedCount = approvedCount;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public String getRespondentsHindi() {
        return respondentsHindi;
    }

    public void setRespondentsHindi(String respondentsHindi) {
        this.respondentsHindi = respondentsHindi;
    }

    public String getRespondentsCode() {
        return respondentsCode;
    }

    public void setRespondentsCode(String respondentsCode) {
        this.respondentsCode = respondentsCode;
    }

    public List<Respondents> getRespondentsList() {
        return respondentsList;
    }

    public void setRespondentsList(List<Respondents> respondentsList) {
        this.respondentsList = respondentsList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRespondents() {
        return respondents;
    }

    public void setRespondents(String respondents) {
        this.respondents = respondents;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TaskList> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskList> taskList) {
        this.taskList = taskList;
    }

    public String getSelectedDistric() {
        return selectedDistric;
    }

    public void setSelectedDistric(String selectedDistric) {
        this.selectedDistric = selectedDistric;
    }

    public String getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }

    public boolean isAllDistrict() {
        return allDistrict;
    }

    public void setAllDistrict(boolean allDistrict) {
        this.allDistrict = allDistrict;
    }

    public boolean isAllProject() {
        return allProject;
    }

    public void setAllProject(boolean allProject) {
        this.allProject = allProject;
    }

    public List<String> getSelectedCheckBox() {
        return selectedCheckBox;
    }

    public void setSelectedCheckBox(List<String> selectedCheckBox) {
        this.selectedCheckBox = selectedCheckBox;
    }

    public List<NotiRespondant> getRepondantList() {
        return repondantList;
    }

    public void setRepondantList(List<NotiRespondant> repondantList) {
        this.repondantList = repondantList;
    }

    public boolean isNeedReply() {
        return needReply;
    }

    public void setNeedReply(boolean needReply) {
        this.needReply = needReply;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotType() {
        return notType;
    }

    public void setNotType(String notType) {
        this.notType = notType;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getIfNeedReply() {
        return ifNeedReply;
    }

    public void setIfNeedReply(String ifNeedReply) {
        this.ifNeedReply = ifNeedReply;
    }

    public String getDetailsAbout() {
        return detailsAbout;
    }

    public void setDetailsAbout(String detailsAbout) {
        this.detailsAbout = detailsAbout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public int getNeedReplyBefore() {
        return needReplyBefore;
    }

    public void setNeedReplyBefore(int needReplyBefore) {
        this.needReplyBefore = needReplyBefore;
    }

    public int getForm_Id() {
        return form_Id;
    }

    public void setForm_Id(int form_Id) {
        this.form_Id = form_Id;
    }

    public int getNeedApprovalBefore() {
        return needApprovalBefore;
    }

    public void setNeedApprovalBefore(int needApprovalBefore) {
        this.needApprovalBefore = needApprovalBefore;
    }

    public int getOriginal_Notification_Id() {
        return original_Notification_Id;
    }

    public void setOriginal_Notification_Id(int original_Notification_Id) {
        this.original_Notification_Id = original_Notification_Id;
    }

    public String getSendFromCode() {
        return sendFromCode;
    }

    public void setSendFromCode(String sendFromCode) {
        this.sendFromCode = sendFromCode;
    }

    public String getSendFromUserId() {
        return sendFromUserId;
    }

    public void setSendFromUserId(String sendFromUserId) {
        this.sendFromUserId = sendFromUserId;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

}
