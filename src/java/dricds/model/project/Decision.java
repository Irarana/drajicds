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
public class Decision {

    private String strACTIVITY_ID = null;
    private String strDECISION = null;
    private String strIF_POSITIVE = null;
    private String strJUMP_TO_STAGEID = null;

    public String getStrACTIVITY_ID() {
        return strACTIVITY_ID;
    }

    public void setStrACTIVITY_ID(String strACTIVITY_ID) {
        this.strACTIVITY_ID = strACTIVITY_ID;
    }

    public String getStrDECISION() {
        return strDECISION;
    }

    public void setStrDECISION(String strDECISION) {
        this.strDECISION = strDECISION;
    }

    public String getStrIF_POSITIVE() {
        return strIF_POSITIVE;
    }

    public void setStrIF_POSITIVE(String strIF_POSITIVE) {
        this.strIF_POSITIVE = strIF_POSITIVE;
    }

    public String getStrJUMP_TO_STAGEID() {
        return strJUMP_TO_STAGEID;
    }

    public void setStrJUMP_TO_STAGEID(String strJUMP_TO_STAGEID) {
        this.strJUMP_TO_STAGEID = strJUMP_TO_STAGEID;
    }

}
