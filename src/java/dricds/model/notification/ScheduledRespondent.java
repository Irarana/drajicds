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
public class ScheduledRespondent {

    private int schRespId;
    private String schNotId;
    private String respondentType;

    private String respondentCode;

    public int getSchRespId() {
        return schRespId;
    }

    public void setSchRespId(int schRespId) {
        this.schRespId = schRespId;
    }

    public String getSchNotId() {
        return schNotId;
    }

    public void setSchNotId(String schNotId) {
        this.schNotId = schNotId;
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

}
