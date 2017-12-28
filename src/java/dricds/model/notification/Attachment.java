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
public class Attachment {
    
    private int attachmentId;
    private String fileNamelFILE_NAME;
    private String filePath;
    private String contentType;
    private String documentTitle;
    private String uploadDate;
    private String description;
     private String tags;

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileNamelFILE_NAME() {
        return fileNamelFILE_NAME;
    }

    public void setFileNamelFILE_NAME(String fileNamelFILE_NAME) {
        this.fileNamelFILE_NAME = fileNamelFILE_NAME;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
     
     
    
    
}
