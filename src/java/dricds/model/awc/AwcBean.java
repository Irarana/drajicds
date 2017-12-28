/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.model.awc;

/**
 *
 * @author bab
 */
public class AwcBean {
   private String hidAwcCode=null;
   private String awcName=null;
    private String awcType=null;
   private String awcUrbanRural=null;
   private String awcAddress=null;
   private String projectName=null;
   private String sectorName=null;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }
   
   

    public String getHidAwcCode() {
        return hidAwcCode;
    }

    public void setHidAwcCode(String hidAwcCode) {
        this.hidAwcCode = hidAwcCode;
    }

    public String getAwcName() {
        return awcName;
    }

    public void setAwcName(String awcName) {
        this.awcName = awcName;
    }

    public String getAwcType() {
        return awcType;
    }

    public void setAwcType(String awcType) {
        this.awcType = awcType;
    }

    public String getAwcUrbanRural() {
        return awcUrbanRural;
    }

    public void setAwcUrbanRural(String awcUrbanRural) {
        this.awcUrbanRural = awcUrbanRural;
    }

    public String getAwcAddress() {
        return awcAddress;
    }

    public void setAwcAddress(String awcAddress) {
        this.awcAddress = awcAddress;
    }
  
   
}
