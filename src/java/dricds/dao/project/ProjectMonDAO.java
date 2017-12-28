/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.dao.project;

import dricds.model.form.DynaForm;
import dricds.model.form.Observation;
import dricds.model.form.ProjectInitiationListForm;
import dricds.model.project.Activity;
import dricds.model.project.Project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mana Jena
 */
public interface ProjectMonDAO {
    public List getProjectList(int programmeId);
    public Project getProjectDetails(int pid);
    public String workFlowStatus(int pid);
    public Activity getCurrentActivityOfProject(int pid,String planno,int ppid);
    public DynaForm getDynaForm(HttpServletRequest request,int formid);
    public Observation[] getObservationsOfPALID(String strPALID);
    public Observation[] getOpenObservationsOfPID(int pid);
    public ArrayList getHistory(int pid,int ppid);
    public HashMap getObservationHistory(int pid,int ppid);
    public String saveNewActivity(ProjectInitiationListForm pif,HttpServletRequest request);
}
