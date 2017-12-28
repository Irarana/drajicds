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
public class Respondents implements Serializable {

    private int respondentId;
    private int notificationId;
    private String respondentType;
    private String respondentCode;
    private String acknowledgedOn;
    private String acknowledgedBy;
    private String repliedOn;
    private String repliedBy;
    private String approvedOn;
    private String approvedBy;
    private String respondentUserId;
    private String respondentPostName;
    private String respondentPostNameHindi;
    private String respondentPostId;
    private List<TaskList> taskList;
    private String respReportingDist;
    private String respReportingProj;
    private String respReportingSec;
    private String respReportingSecName;
    private String respReportingDistName;
    private String respReportingProjName;
    private int totalAcknowledged;
    private int totalReplied;
    private int totalApproved;
    private int acknowledged;
    private int replied;
    private int approved;
    private String if_Replied;
    private String if_Approve;
    private String respondentOfficeCode;
     private String headOfOffice;
 private boolean allTaskCompleted;

    public boolean isAllTaskCompleted() {
        return allTaskCompleted;
    }

    public void setAllTaskCompleted(boolean allTaskCompleted) {
        this.allTaskCompleted = allTaskCompleted;
    }
 
 
    public String getHeadOfOffice() {
        return headOfOffice;
    }

    public void setHeadOfOffice(String headOfOffice) {
        this.headOfOffice = headOfOffice;
    }

    public String getRespondentOfficeCode() {
        return respondentOfficeCode;
    }

    public void setRespondentOfficeCode(String respondentOfficeCode) {
        this.respondentOfficeCode = respondentOfficeCode;
    }
    

    public String getIf_Replied() {
        return if_Replied;
    }

    public void setIf_Replied(String if_Replied) {
        this.if_Replied = if_Replied;
    }

    public String getIf_Approve() {
        return if_Approve;
    }

    public void setIf_Approve(String if_Approve) {
        this.if_Approve = if_Approve;
    }
    
    private List<RespondantTaskMap> respondantTaskMap;

    public List<RespondantTaskMap> getRespondantTaskMap() {
        return respondantTaskMap;
    }

    public void setRespondantTaskMap(List<RespondantTaskMap> respondantTaskMap) {
        this.respondantTaskMap = respondantTaskMap;
    }

    public String getRespReportingSec() {
        return respReportingSec;
    }

    public void setRespReportingSec(String respReportingSec) {
        this.respReportingSec = respReportingSec;
    }

    public String getRespReportingSecName() {
        return respReportingSecName;
    }

    public void setRespReportingSecName(String respReportingSecName) {
        this.respReportingSecName = respReportingSecName;
    }

    public String getRespReportingDistName() {
        return respReportingDistName;
    }

    public void setRespReportingDistName(String respReportingDistName) {
        this.respReportingDistName = respReportingDistName;
    }

    public String getRespReportingProjName() {
        return respReportingProjName;
    }

    public void setRespReportingProjName(String respReportingProjName) {
        this.respReportingProjName = respReportingProjName;
    }

    public int getTotalAcknowledged() {
        return totalAcknowledged;
    }

    public void setTotalAcknowledged(int totalAcknowledged) {
        this.totalAcknowledged = totalAcknowledged;
    }

    public int getTotalReplied() {
        return totalReplied;
    }

    public void setTotalReplied(int totalReplied) {
        this.totalReplied = totalReplied;
    }

    public int getTotalApproved() {
        return totalApproved;
    }

    public void setTotalApproved(int totalApproved) {
        this.totalApproved = totalApproved;
    }

    public int getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(int acknowledged) {
        this.acknowledged = acknowledged;
    }

    public int getReplied() {
        return replied;
    }

    public void setReplied(int replied) {
        this.replied = replied;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public String getRespReportingDist() {
        return respReportingDist;
    }

    public void setRespReportingDist(String respReportingDist) {
        this.respReportingDist = respReportingDist;
    }

    public String getRespReportingProj() {
        return respReportingProj;
    }

    public void setRespReportingProj(String respReportingProj) {
        this.respReportingProj = respReportingProj;
    }

    public String getRespondentPostName() {
        return respondentPostName;
    }

    public void setRespondentPostName(String respondentPostName) {
        this.respondentPostName = respondentPostName;
    }

    public String getRespondentPostNameHindi() {
        return respondentPostNameHindi;
    }

    public void setRespondentPostNameHindi(String respondentPostNameHindi) {
        this.respondentPostNameHindi = respondentPostNameHindi;
    }

    public String getRespondentPostId() {
        return respondentPostId;
    }

    public void setRespondentPostId(String respondentPostId) {
        this.respondentPostId = respondentPostId;
    }

    public List<TaskList> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskList> taskList) {
        this.taskList = taskList;
    }

    public String getRespondentUserId() {
        return respondentUserId;
    }

    public void setRespondentUserId(String respondentUserId) {
        this.respondentUserId = respondentUserId;
    }

    public int getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(int respondentId) {
        this.respondentId = respondentId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getRespondentType() {
        return respondentType;
    }

    public void setRespondentType(String respondentType) {
        this.respondentType = respondentType;
    }

    public String getRespondentCode() {
        return respondentCode;
    }

    public void setRespondentCode(String respondentCode) {
        this.respondentCode = respondentCode;
    }

    public String getAcknowledgedOn() {
        return acknowledgedOn;
    }

    public void setAcknowledgedOn(String acknowledgedOn) {
        this.acknowledgedOn = acknowledgedOn;
        
    }

    public String getAcknowledgedBy() {
        return acknowledgedBy;
    }

    public void setAcknowledgedBy(String acknowledgedBy) {
        this.acknowledgedBy = acknowledgedBy;
    }

    public String getRepliedOn() {
        return repliedOn;
    }

    public void setRepliedOn(String repliedOn) {
        this.repliedOn = repliedOn;
         if(repliedOn!=null && !repliedOn.trim().isEmpty()){
            setIf_Replied("Yes");
        }else{
            setIf_Replied("No");
        }
    }

    public String getRepliedBy() {
        return repliedBy;
    }

    public void setRepliedBy(String repliedBy) {
        this.repliedBy = repliedBy;
    }

    public String getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(String approvedOn) {
        this.approvedOn = approvedOn;
        if(approvedOn!=null && !approvedOn.trim().isEmpty()){
            setIf_Approve("Yes");
        }else{
            setIf_Approve("No");
        }
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

}
