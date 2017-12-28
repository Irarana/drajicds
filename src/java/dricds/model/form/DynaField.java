/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.form;

import java.util.ArrayList;

/**
 *
 * @author manas jena
 */
public class DynaField {

    private int fieldid;
    private String fieldname;
    private String fieldlabel;
    private String fielddatatype;
    private String fieldtype;
    private String iscompulsory;
    private int fieldlength;
    private ArrayList<LabelValueBean> labelvalues;
    private String inline;
    private int size;
    private String fieldval;
    private String iscompfield;
    private String helptext;
    private String ifhdrlbl;
    private String sectionid;
    private String sectionname;
    private String filterField;
    private String ifAutoCalc;
    private String autoCalcFormula;
    private String ifOB;

    public String getIfOB() {
        return ifOB;
    }

    public void setIfOB(String ifOB) {
        this.ifOB = ifOB;
    }

    public String getIfAutoCalc() {
        return ifAutoCalc;
    }

    public void setIfAutoCalc(String ifAutoCalc) {
        this.ifAutoCalc = ifAutoCalc;
    }

    public String getAutoCalcFormula() {
        return autoCalcFormula;
    }

    public void setAutoCalcFormula(String autoCalcFormula) {
        this.autoCalcFormula = autoCalcFormula;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public String getSectionid() {
        return sectionid;
    }

    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

    public String getSectionname() {
        return sectionname;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname;
    }

    public String getIfhdrlbl() {
        return ifhdrlbl;
    }

    public void setIfhdrlbl(String ifhdrlbl) {
        this.ifhdrlbl = ifhdrlbl;
    }

    public String getHelptext() {
        return helptext;
    }

    public void setHelptext(String helptext) {
        this.helptext = helptext;
    }

    public int getFieldid() {
        return fieldid;
    }

    public void setFieldid(int fieldid) {
        this.fieldid = fieldid;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getFieldlabel() {
        return fieldlabel;
    }

    public void setFieldlabel(String fieldlabel) {
        this.fieldlabel = fieldlabel;
    }

    public String getFielddatatype() {
        return fielddatatype;
    }

    public void setFielddatatype(String fielddatatype) {
        this.fielddatatype = fielddatatype;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getIscompulsory() {
        return iscompulsory;
    }

    public void setIscompulsory(String iscompulsory) {
        this.iscompulsory = iscompulsory;
    }

    public int getFieldlength() {
        return fieldlength;
    }

    public void setFieldlength(int fieldlength) {
        this.fieldlength = fieldlength;
    }

    public ArrayList<LabelValueBean> getLabelvalues() {
        return labelvalues;
    }

    public void setLabelvalues(ArrayList<LabelValueBean> labelvalues) {
        this.labelvalues = labelvalues;
    }

    public String getInline() {
        return inline;
    }

    public void setInline(String inline) {
        this.inline = inline;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFieldval() {
        return fieldval;
    }

    public void setFieldval(String fieldval) {
        this.fieldval = fieldval;
    }

    public String getIscompfield() {
        return iscompfield;
    }

    public void setIscompfield(String iscompfield) {
        this.iscompfield = iscompfield;
    }

}
