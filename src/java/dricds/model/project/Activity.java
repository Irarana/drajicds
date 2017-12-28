/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.project;

import java.util.ArrayList;

/**
 *
 * @author Mana Jena
 */
public class Activity {

    private String strACTIVITY_ID = null;
    private String strACTIVITY = null;
    private String strSTAGE = null;
    private String strACTIVITY_SL = null;
    private String strPARENT_SID = null;
    private String strIS_COMPULSORY = null;
    private String strHAS_DECISION = null;
    private String strSTART_AFTER_ACTIVITY = null;
    private String strTYPE = null;
    private ArrayList<Decision> alDecision = null;
    private int selpaid;
    private String selppid = null;
    private String strIF_END = null;
    private String strFORM_ID = null;

    //private DynaForm dynaform = null;
    public String getStrACTIVITY_ID() {
        return strACTIVITY_ID;
    }

    public void setStrACTIVITY_ID(String strACTIVITY_ID) {
        this.strACTIVITY_ID = strACTIVITY_ID;
    }

    public String getStrACTIVITY() {
        return strACTIVITY;
    }

    public void setStrACTIVITY(String strACTIVITY) {
        this.strACTIVITY = strACTIVITY;
    }

    public String getStrSTAGE() {
        return strSTAGE;
    }

    public void setStrSTAGE(String strSTAGE) {
        this.strSTAGE = strSTAGE;
    }

    public String getStrACTIVITY_SL() {
        return strACTIVITY_SL;
    }

    public void setStrACTIVITY_SL(String strACTIVITY_SL) {
        this.strACTIVITY_SL = strACTIVITY_SL;
    }

    public String getStrPARENT_SID() {
        return strPARENT_SID;
    }

    public void setStrPARENT_SID(String strPARENT_SID) {
        this.strPARENT_SID = strPARENT_SID;
    }

    public String getStrIS_COMPULSORY() {
        return strIS_COMPULSORY;
    }

    public void setStrIS_COMPULSORY(String strIS_COMPULSORY) {
        this.strIS_COMPULSORY = strIS_COMPULSORY;
    }

    public String getStrHAS_DECISION() {
        return strHAS_DECISION;
    }

    public void setStrHAS_DECISION(String strHAS_DECISION) {
        this.strHAS_DECISION = strHAS_DECISION;
    }

    public String getStrSTART_AFTER_ACTIVITY() {
        return strSTART_AFTER_ACTIVITY;
    }

    public void setStrSTART_AFTER_ACTIVITY(String strSTART_AFTER_ACTIVITY) {
        this.strSTART_AFTER_ACTIVITY = strSTART_AFTER_ACTIVITY;
    }

    public String getStrTYPE() {
        return strTYPE;
    }

    public void setStrTYPE(String strTYPE) {
        this.strTYPE = strTYPE;
    }

    public ArrayList<Decision> getAlDecision() {
        return alDecision;
    }

    public void setAlDecision(ArrayList<Decision> alDecision) {
        this.alDecision = alDecision;
    }

    public int getSelpaid() {
        return selpaid;
    }

    public void setSelpaid(int selpaid) {
        this.selpaid = selpaid;
    }

    public String getSelppid() {
        return selppid;
    }

    public void setSelppid(String selppid) {
        this.selppid = selppid;
    }

    public String getStrIF_END() {
        return strIF_END;
    }

    public void setStrIF_END(String strIF_END) {
        this.strIF_END = strIF_END;
    }

    public String getStrFORM_ID() {
        return strFORM_ID;
    }

    public void setStrFORM_ID(String strFORM_ID) {
        this.strFORM_ID = strFORM_ID;
    }
    
}
