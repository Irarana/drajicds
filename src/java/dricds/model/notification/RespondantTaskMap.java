/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.notification;

/**
 *
 * @author ekank
 */
public class RespondantTaskMap {

    private int respTaskId;
    private int notResId;
    private int taskId;
    private String status;
    private String responantCode;
    private String respondantType;

    public String getResponantCode() {
        return responantCode;
    }

    public void setResponantCode(String responantCode) {
        this.responantCode = responantCode;
    }

    public String getRespondantType() {
        return respondantType;
    }

    public void setRespondantType(String respondantType) {
        this.respondantType = respondantType;
    }
    
    

    public int getRespTaskId() {
        return respTaskId;
    }

    public void setRespTaskId(int respTaskId) {
        this.respTaskId = respTaskId;
    }

    public int getNotResId() {
        return notResId;
    }

    public void setNotResId(int notResId) {
        this.notResId = notResId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
