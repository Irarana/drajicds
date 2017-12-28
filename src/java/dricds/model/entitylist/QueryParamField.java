package dricds.model.entitylist;

public class QueryParamField {
	String paramid;
	String paramname;
	String[] paramvalues;
	String[] paramlables;
	String ctrlType;
	String ifCSV;
	
	
	public String getIfCSV() {
		return ifCSV;
	}
	public void setIfCSV(String ifCSV) {
		this.ifCSV = ifCSV;
	}
	public String getCtrlType() {
		return ctrlType;
	}
	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}
	public String getParamid() {
		return paramid;
	}
	public void setParamid(String paramid) {
		this.paramid = paramid;
	}
	public String getParamname() {
		return paramname;
	}
	public void setParamname(String paramname) {
		this.paramname = paramname;
	}
	public String[] getParamvalues() {
		return paramvalues;
	}
	public void setParamvalues(String[] paramvalues) {
		this.paramvalues = paramvalues;
	}
	public String[] getParamlables() {
		return paramlables;
	}
	public void setParamlables(String[] paramlables) {
		this.paramlables = paramlables;
	}
	
	
	
}
