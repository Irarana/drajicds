/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.notification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ekank
 */
public class OutBox implements Serializable {

    private String ownerName;
    private int totalNotification;
    private int totalAcknowledged;
    private int acknowledged;
    private int totalReplied;
    private int replied;
    private int totalApproved;
    private int approved;
    private List<Notification> notificationList;
    private List<Respondents> selectedRespondentsList;
    private String selectedNotification;
    private int selectedNotificationId;
    private String selectedNotiDate;
    private String selNotiResponseTime;
    private String selNotiApproveTime;
    private Map<String, String> selectedDist;
    private Map<String, String> selectedProject;
    private Map<String, String> selectedSector;
    private String selDistOption;
    private String selProjOption;
    private String selSactOption;
    private String aboutLevel;
    private int selectedRespondentId;
    private String selectedIfWithdrawn;
    

    public String getSelectedIfWithdrawn() {
        return selectedIfWithdrawn;
    }

    public void setSelectedIfWithdrawn(String selectedIfWithdrawn) {
        this.selectedIfWithdrawn = selectedIfWithdrawn;
    }
    

    public int getSelectedRespondentId() {
        return selectedRespondentId;
    }

    public void setSelectedRespondentId(int selectedRespondentId) {
        this.selectedRespondentId = selectedRespondentId;
    }

    public String getAboutLevel() {
        return aboutLevel;
    }

    public void setAboutLevel(String aboutLevel) {
        this.aboutLevel = aboutLevel;
    }

    public String getSelDistOption() {
        return selDistOption;
    }

    public void setSelDistOption(String selDistOption) {
        this.selDistOption = selDistOption;
    }

    public String getSelProjOption() {
        return selProjOption;
    }

    public void setSelProjOption(String selProjOption) {
        this.selProjOption = selProjOption;
    }

    public String getSelSactOption() {
        return selSactOption;
    }

    public void setSelSactOption(String selSactOption) {
        this.selSactOption = selSactOption;
    }

    public Map<String, String> getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Map<String, String> selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Map<String, String> getSelectedSector() {
        return selectedSector;
    }

    public void setSelectedSector(Map<String, String> selectedSector) {
        this.selectedSector = selectedSector;
    }

    public Map<String, String> getSelectedDist() {
        return selectedDist;
    }

    public void setSelectedDist(Map<String, String> selectedDist) {
        this.selectedDist = selectedDist;
    }

    public String getSelNotiResponseTime() {
        return selNotiResponseTime;
    }

    public void setSelNotiResponseTime(String selNotiResponseTime) {
        this.selNotiResponseTime = selNotiResponseTime;
    }

    public List<Respondents> getSelectedRespondentsList() {
        return selectedRespondentsList;
    }

    public void setSelectedRespondentsList(List<Respondents> selectedRespondentsList) {
        this.selectedRespondentsList = selectedRespondentsList;
    }

    public String getSelNotiApproveTime() {
        return selNotiApproveTime;
    }

    public void setSelNotiApproveTime(String selNotiApproveTime) {
        this.selNotiApproveTime = selNotiApproveTime;
    }

    public String getSelectedNotiDate() {
        return selectedNotiDate;
    }

    public void setSelectedNotiDate(String selectedNotiDate) {
        this.selectedNotiDate = selectedNotiDate;
    }

    public String getSelectedNotification() {
        return selectedNotification;
    }

    public void setSelectedNotification(String selectedNotification) {
        this.selectedNotification = selectedNotification;
    }

    public int getSelectedNotificationId() {
        return selectedNotificationId;
    }

    public void setSelectedNotificationId(int selectedNotificationId) {
        this.selectedNotificationId = selectedNotificationId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getTotalNotification() {
        return totalNotification;
    }

    public void setTotalNotification(int totalNotification) {
        this.totalNotification = totalNotification;
    }

    public int getTotalAcknowledged() {
        return totalAcknowledged;
    }

    public void setTotalAcknowledged(int totalAcknowledged) {
        this.totalAcknowledged = totalAcknowledged;
    }

    public int getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(int acknowledged) {
        this.acknowledged = acknowledged;
    }

    public int getTotalReplied() {
        return totalReplied;
    }

    public void setTotalReplied(int totalReplied) {
        this.totalReplied = totalReplied;
    }

    public int getReplied() {
        return replied;
    }

    public void setReplied(int replied) {
        this.replied = replied;
    }

    public int getTotalApproved() {
        return totalApproved;
    }

    public void setTotalApproved(int totalApproved) {
        this.totalApproved = totalApproved;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

}
