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
public class NotAttachment {

    private int notAttachId;
    private int notId;
    private int notResId;
    private String attachmentId;

    public int getNotAttachId() {
        return notAttachId;
    }

    public void setNotAttachId(int notAttachId) {
        this.notAttachId = notAttachId;
    }

    public int getNotId() {
        return notId;
    }

    public void setNotId(int notId) {
        this.notId = notId;
    }

    public int getNotResId() {
        return notResId;
    }

    public void setNotResId(int notResId) {
        this.notResId = notResId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

}
