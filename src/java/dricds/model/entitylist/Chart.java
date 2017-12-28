/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dricds.model.entitylist;

import java.util.ArrayList;

/**
 *
 * @author Bibek Sa
 */
public class Chart {
    private String charttitle;
    private String chartdescription;
    private ArrayList<String> chartrows;
    private String strjson;
    private String charttype;
    
    public String getCharttitle() {
        return charttitle;
    }

    public void setCharttitle(String charttitle) {
        this.charttitle = charttitle;
    }

    public String getChartdescription() {
        return chartdescription;
    }

    public void setChartdescription(String chartdescription) {
        this.chartdescription = chartdescription;
    }

    public ArrayList<String> getChartrows() {
        return chartrows;
    }

    public void setChartrows(ArrayList<String> chartrows) {
        this.chartrows = chartrows;
    }

    public String getStrjson() {
        return strjson;
    }

    public void setStrjson(String strjson) {
        this.strjson = strjson;
    }

    public String getCharttype() {
        return charttype;
    }

    public void setCharttype(String charttype) {
        this.charttype = charttype;
    }
    
}
