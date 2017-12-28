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
public class TaskList implements Serializable   {

    private int taskId;
    private int notResId;
    private int notId;
    private String detailsAbout;
    private String detailsAboutId;
    private String formId;
    private String formTableTranId;
    private String cur_Respondent_Type;
    private String cur_Respondent_Code;
    private String if_Complied;
    private int totalExp;
    private int toalBeneficior ;
    private boolean check ;
    private String currentNotificationId;
    private Respondents respondents;
    private String completedByType;
    private String completedByCode;
    private String completedByUserId;
    private String completedOn;
    private boolean markCompleted;
     private int respTaskId;
    private String status;
    private String respondentType;
    private String respondentCode;
    private String sender;
    private List<TaskList> relatedTaskList;

    public List<TaskList> getRelatedTaskList() {
        return relatedTaskList;
    }

    public void setRelatedTaskList(List<TaskList> relatedTaskList) {
        this.relatedTaskList = relatedTaskList;
    }
    
    

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    

    public int getRespTaskId() {
        return respTaskId;
    }

    public void setRespTaskId(int respTaskId) {
        this.respTaskId = respTaskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    
    
    public boolean isMarkCompleted() {
        return markCompleted;
    }

    public void setMarkCompleted(boolean markCompleted) {
        this.markCompleted = markCompleted;
    }
    
    

    public String getCompletedByType() {
        return completedByType;
    }

    public void setCompletedByType(String completedByType) {
        this.completedByType = completedByType;
    }

    public String getCompletedByCode() {
        return completedByCode;
    }

    public void setCompletedByCode(String completedByCode) {
        this.completedByCode = completedByCode;
    }

    public String getCompletedByUserId() {
        return completedByUserId;
    }

    public void setCompletedByUserId(String completedByUserId) {
        this.completedByUserId = completedByUserId;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }
    

    public Respondents getRespondents() {
        return respondents;
    }

    public void setRespondents(Respondents respondents) {
        this.respondents = respondents;
    }
    
    
    

    public String getCurrentNotificationId() {
        return currentNotificationId;
    }

    public void setCurrentNotificationId(String currentNotificationId) {
        this.currentNotificationId = currentNotificationId;
    }
    
    

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    
    
    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getToalBeneficior() {
        return toalBeneficior;
    }

    public void setToalBeneficior(int toalBeneficior) {
        this.toalBeneficior = toalBeneficior;
    }
    
    

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getNotResId() {
        return notResId;
    }

    public void setNotResId(int notResId) {
        this.notResId = notResId;
    }

    public int getNotId() {
        return notId;
    }

    public void setNotId(int notId) {
        this.notId = notId;
    }

    public String getDetailsAbout() {
        return detailsAbout;
    }

    public void setDetailsAbout(String detailsAbout) {
        this.detailsAbout = detailsAbout;
    }

    public String getDetailsAboutId() {
        return detailsAboutId;
    }

    public void setDetailsAboutId(String detailsAboutId) {
        this.detailsAboutId = detailsAboutId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormTableTranId() {
        return formTableTranId;
    }

    public void setFormTableTranId(String formTableTranId) {
        this.formTableTranId = formTableTranId;
    }

    public String getCur_Respondent_Type() {
        return cur_Respondent_Type;
    }

    public void setCur_Respondent_Type(String cur_Respondent_Type) {
        this.cur_Respondent_Type = cur_Respondent_Type;
    }

    public String getCur_Respondent_Code() {
        return cur_Respondent_Code;
    }

    public void setCur_Respondent_Code(String cur_Respondent_Code) {
        this.cur_Respondent_Code = cur_Respondent_Code;
    }

    public String getIf_Complied() {
        return if_Complied;
    }

    public void setIf_Complied(String if_Complied) {
        this.if_Complied = if_Complied;
    }

}
