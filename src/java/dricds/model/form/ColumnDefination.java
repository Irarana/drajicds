/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mep.dataset;

/**
 *
 * @author manas jena
 */
public class ColumnDefination {
    private int columnid;
    private String fieldname;
    private String fieldlabel;
    private String ishidden;
    private String fieldval;
    private String isparam;
    private String paramName;
    
    public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
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

    public String getIshidden() {
        return ishidden;
    }

    public void setIshidden(String ishidden) {
        this.ishidden = ishidden;
    }

    public String getFieldval() {
        return fieldval;
    }

    public void setFieldval(String fieldval) {
        this.fieldval = fieldval;
    }

    public String getIsparam() {
        return isparam;
    }

    public void setIsparam(String isparam) {
        this.isparam = isparam;
    }    
    
}
