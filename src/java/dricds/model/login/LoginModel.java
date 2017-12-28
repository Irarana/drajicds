/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.model.login;

import java.io.Serializable;

/**
 *
 * @author surendra
 */
public class LoginModel implements Serializable{
    private String userid=null;
    private String password=null;
    private String role=null;
    private String level=null;
    private String levelAreaCode=null;
    private String profileId=null;
    private String sessionurl="";
    

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelAreaCode() {
        return levelAreaCode;
    }

    public void setLevelAreaCode(String levelAreaCode) {
        this.levelAreaCode = levelAreaCode;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getSessionurl() {
        return sessionurl;
    }

    public void setSessionurl(String sessionurl) {
        this.sessionurl = sessionurl;
    }
    
    
    
}
