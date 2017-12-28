/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dricds.model.form;

import java.util.ArrayList;

/**
 *
 * @author Mana Jena
 */
public class DynaForm {
    private int slno;
    private int formid;
    private String formname;
    private ArrayList fields;
    private String compfield;
    private String formDesc;
    private String footNote;

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public int getFormid() {
        return formid;
    }

    public void setFormid(int formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public ArrayList getFields() {
        return fields;
    }

    public void setFields(ArrayList fields) {
        this.fields = fields;
    }

    public String getCompfield() {
        return compfield;
    }

    public void setCompfield(String compfield) {
        this.compfield = compfield;
    }

    public String getFormDesc() {
        return formDesc;
    }

    public void setFormDesc(String formDesc) {
        this.formDesc = formDesc;
    }

    public String getFootNote() {
        return footNote;
    }

    public void setFootNote(String footNote) {
        this.footNote = footNote;
    }
    
    
}
