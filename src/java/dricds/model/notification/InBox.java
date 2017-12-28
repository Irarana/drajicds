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
public class InBox implements Serializable {

    private String ownerName;
    private int totalNotification;
    private int totalAcknowledged;
    private int acknowledged;
    private int totalReplied;
    private int replied;
    private int totalApproved;
    private int approved;
    private List<Notification> notificationList;
    private List<TaskList> selectedTaskList;
    private int selectedNotificationId;
    private String selectedNotification;
    private int selectedTaskID;
    private String userLevel;
    private String selectedNotiDate;
    private boolean approver;
    private int selApproveRespoId;
    private String currentRespondent;
    private String selectedMarkCompleted;
    private String userIncumbencyId;
    private String userAreaCode;
    private boolean selectedIsApproved;
    private boolean selectedIsReplied;

    public boolean isSelectedIsApproved() {
        return selectedIsApproved;
    }

    public void setSelectedIsApproved(boolean selectedIsApproved) {
        this.selectedIsApproved = selectedIsApproved;
    }

    public boolean isSelectedIsReplied() {
        return selectedIsReplied;
    }

    public void setSelectedIsReplied(boolean selectedIsReplied) {
        this.selectedIsReplied = selectedIsReplied;
    }

    public String getUserAreaCode() {
        return userAreaCode;
    }

    public void setUserAreaCode(String userAreaCode) {
        this.userAreaCode = userAreaCode;
    }

    public String getUserIncumbencyId() {
        return userIncumbencyId;
    }

    public void setUserIncumbencyId(String userIncumbencyId) {
        this.userIncumbencyId = userIncumbencyId;
    }

    public String getSelectedMarkCompleted() {
        return selectedMarkCompleted;
    }

    public void setSelectedMarkCompleted(String selectedMarkCompleted) {
        this.selectedMarkCompleted = selectedMarkCompleted;
    }

    public String getCurrentRespondent() {
        return currentRespondent;
    }

    public void setCurrentRespondent(String currentRespondent) {
        this.currentRespondent = currentRespondent;
    }

    public int getSelApproveRespoId() {
        return selApproveRespoId;
    }

    public void setSelApproveRespoId(int selApproveRespoId) {
        this.selApproveRespoId = selApproveRespoId;
    }

    public boolean isApprover() {
        return approver;
    }

    public void setApprover(boolean approver) {
        this.approver = approver;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public int getSelectedTaskID() {
        return selectedTaskID;
    }

    public void setSelectedTaskID(int selectedTaskID) {
        this.selectedTaskID = selectedTaskID;
    }

    public String getSelectedNotiDate() {
        return selectedNotiDate;
    }

    public void setSelectedNotiDate(String selectedNotiDate) {
        this.selectedNotiDate = selectedNotiDate;
    }

    public List<TaskList> getSelectedTaskList() {
        return selectedTaskList;
    }

    public void setSelectedTaskList(List<TaskList> selectedTaskList) {
        this.selectedTaskList = selectedTaskList;
    }

    public int getSelectedNotificationId() {
        return selectedNotificationId;
    }

    public void setSelectedNotificationId(int selectedNotificationId) {
        this.selectedNotificationId = selectedNotificationId;
    }

    public String getSelectedNotification() {
        return selectedNotification;
    }

    public void setSelectedNotification(String selectedNotification) {
        this.selectedNotification = selectedNotification;
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
