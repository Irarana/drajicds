/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.model.project;

/**
 *
 * @author Mana Jena
 */
public class Awc {
    private int awcCode;
    private String awcName;
    private String district;
    private String sector;
    private String division;

    public int getAwcCode() {
        return awcCode;
    }

    public void setAwcCode(int awcCode) {
        this.awcCode = awcCode;
    }

    public String getAwcName() {
        return awcName;
    }

    public void setAwcName(String awcName) {
        this.awcName = awcName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    
}
