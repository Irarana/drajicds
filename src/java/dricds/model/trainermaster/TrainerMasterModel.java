/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.trainermaster;

/**
 *
 * @author surendra
 */
public class TrainerMasterModel {

    private String tcId = "";
    private String tcName = "";
    private String trainerId = "";
    private String trainerName = "";
    private String trainerNameHindi = "";
    private String ifFulltime = "";
    private String trainerAadhaar = "";
    private String trainerDesig;
    private String[] teachingSubject=null;
    private String trainerEduLevel;
    private String trainerProfEdu;
    private String[] trainerEduSubject;
    private String trainerExperience = null;
    private String ifJobTraining = null;
    private int jobTrainYear;
    private String refreshTraining = null;
    private String trinerDesigasString=null;

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTcName() {
        return tcName;
    }

    public void setTcName(String tcName) {
        this.tcName = tcName;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerNameHindi() {
        return trainerNameHindi;
    }

    public void setTrainerNameHindi(String trainerNameHindi) {
        this.trainerNameHindi = trainerNameHindi;
    }
    

    public String getIfFulltime() {
        return ifFulltime;
    }

    public void setIfFulltime(String ifFulltime) {
        this.ifFulltime = ifFulltime;
    }

    public String getTrainerAadhaar() {
        return trainerAadhaar;
    }

    public void setTrainerAadhaar(String trainerAadhaar) {
        this.trainerAadhaar = trainerAadhaar;
    }

    public String getTrainerDesig() {
        return trainerDesig;
    }

    public void setTrainerDesig(String trainerDesig) {
        this.trainerDesig = trainerDesig;
    }

    
    public String getTrainerEduLevel() {
        return trainerEduLevel;
    }

    public void setTrainerEduLevel(String trainerEduLevel) {
        this.trainerEduLevel = trainerEduLevel;
    }

    public String[] getTeachingSubject() {
        return teachingSubject;
    }

    public void setTeachingSubject(String[] teachingSubject) {
        this.teachingSubject = teachingSubject;
    }

    public String getTrainerProfEdu() {
        return trainerProfEdu;
    }

    public void setTrainerProfEdu(String trainerProfEdu) {
        this.trainerProfEdu = trainerProfEdu;
    }

    public String[] getTrainerEduSubject() {
        return trainerEduSubject;
    }

    public void setTrainerEduSubject(String[] trainerEduSubject) {
        this.trainerEduSubject = trainerEduSubject;
    }

    public String getTrainerExperience() {
        return trainerExperience;
    }

    public void setTrainerExperience(String trainerExperience) {
        this.trainerExperience = trainerExperience;
    }

    public String getIfJobTraining() {
        return ifJobTraining;
    }

    public void setIfJobTraining(String ifJobTraining) {
        this.ifJobTraining = ifJobTraining;
    }

    public int getJobTrainYear() {
        return jobTrainYear;
    }

    public void setJobTrainYear(int jobTrainYear) {
        this.jobTrainYear = jobTrainYear;
    }

    public String getRefreshTraining() {
        return refreshTraining;
    }

    public void setRefreshTraining(String refreshTraining) {
        this.refreshTraining = refreshTraining;
    }

    public String getTrinerDesigasString() {
        return trinerDesigasString;
    }

    public void setTrinerDesigasString(String trinerDesigasString) {
        this.trinerDesigasString = trinerDesigasString;
    }
    
    
    
}
