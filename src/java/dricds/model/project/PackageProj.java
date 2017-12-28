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
public class PackageProj {
    private int pid;
    private int ppid;
    private String planno;
    private String packagedesc;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public String getPlanno() {
        return planno;
    }

    public void setPlanno(String planno) {
        this.planno = planno;
    }

    public String getPackagedesc() {
        return packagedesc;
    }

    public void setPackagedesc(String packagedesc) {
        this.packagedesc = packagedesc;
    }

    
    
}
