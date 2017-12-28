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
public class Project {
    private int pid;
    private String projectDesc;
    private String icdsUnitName;
    private String icdsUnitType;
    private String programId;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getIcdsUnitName() {
        return icdsUnitName;
    }

    public void setIcdsUnitName(String icdsUnitName) {
        this.icdsUnitName = icdsUnitName;
    }

    public String getIcdsUnitType() {
        return icdsUnitType;
    }

    public void setIcdsUnitType(String icdsUnitType) {
        this.icdsUnitType = icdsUnitType;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }
    
    
}
