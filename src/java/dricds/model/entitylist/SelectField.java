package dricds.model.entitylist;

import java.sql.ResultSet;
import java.sql.Statement;

public class SelectField {
	private String strSELECT_PARAM_ID;
	private String strQID;
	private String strSELECT_PARAM;
	private String strPARAM_TABLE;
	private String strPARAM_FIELD;
	private String strIS_DEFAULT;
	private String strIS_DERIVED;
	private String strUSER_FORMULA;
	private String strSQL_FORMULA;
	
	private String strIF_TR_ROW;
	private String strIF_TR_COL;
	private String strIF_TR_MEASURE;
	
	/*If user selected the parameter to view in select clause*/
	private String ifSelectedByUserToView;
	private String strSELECT_PARAM_IMPL_ID;
	private String strSLNO;
	/*If user selected the parameter to group by*/
	private String ifSelectedByUserToGroupBy;
	private String strGROUPBY_PARAM_ID;
	private String strGB_SLNO;
	
	private String ifSelectedByUserToTransRow;
	private String ifSelectedByUserToTransCol;
	
	
	public SelectField(String strSELECT_PARAM_ID, String strQID,
			String strSELECT_PARAM, String strPARAM_TABLE,
			String strPARAM_FIELD, String strIS_DEFAULT, String strIS_DERIVED,
			String strUSER_FORMULA, String strSQL_FORMULA,
			String ifSelectedByUserToView, String strSELECT_PARAM_IMPL_ID,
			String strSLNO, String ifSelectedByUserToGroupBy,
			String strGROUPBY_PARAM_ID, String strGB_SLNO) {
		super();
		this.strSELECT_PARAM_ID = strSELECT_PARAM_ID;
		this.strQID = strQID;
		this.strSELECT_PARAM = strSELECT_PARAM;
		this.strPARAM_TABLE = strPARAM_TABLE;
		this.strPARAM_FIELD = strPARAM_FIELD;
		this.strIS_DEFAULT = strIS_DEFAULT;
		this.strIS_DERIVED = strIS_DERIVED;
		this.strUSER_FORMULA = strUSER_FORMULA;
		this.strSQL_FORMULA = strSQL_FORMULA;
		this.ifSelectedByUserToView = ifSelectedByUserToView;
		this.strSELECT_PARAM_IMPL_ID = strSELECT_PARAM_IMPL_ID;
		this.strSLNO = strSLNO;
		this.ifSelectedByUserToGroupBy = ifSelectedByUserToGroupBy;
		this.strGROUPBY_PARAM_ID = strGROUPBY_PARAM_ID;
		this.strGB_SLNO = strGB_SLNO;
	}
	
	public SelectField(String strSELECT_PARAM_ID, String strQID,
			String strSELECT_PARAM, String strPARAM_TABLE,
			String strPARAM_FIELD, String strIS_DEFAULT, String strIS_DERIVED,
			String strUSER_FORMULA, String strSQL_FORMULA,
			String ifSelectedByUserToView, String strSELECT_PARAM_IMPL_ID,
			String strSLNO, String ifSelectedByUserToGroupBy,
			String strGROUPBY_PARAM_ID, String strGB_SLNO,String strIF_TR_ROW,String strIF_TR_COL,String strIF_TR_MEASURE) {
		super();
		this.strSELECT_PARAM_ID = strSELECT_PARAM_ID;
		this.strQID = strQID;
		this.strSELECT_PARAM = strSELECT_PARAM;
		this.strPARAM_TABLE = strPARAM_TABLE;
		this.strPARAM_FIELD = strPARAM_FIELD;
		this.strIS_DEFAULT = strIS_DEFAULT;
		this.strIS_DERIVED = strIS_DERIVED;
		this.strUSER_FORMULA = strUSER_FORMULA;
		this.strSQL_FORMULA = strSQL_FORMULA;
		this.ifSelectedByUserToView = ifSelectedByUserToView;
		this.strSELECT_PARAM_IMPL_ID = strSELECT_PARAM_IMPL_ID;
		this.strSLNO = strSLNO;
		this.ifSelectedByUserToGroupBy = ifSelectedByUserToGroupBy;
		this.strGROUPBY_PARAM_ID = strGROUPBY_PARAM_ID;
		this.strGB_SLNO = strGB_SLNO;
		this.strIF_TR_ROW = strIF_TR_ROW;
		this.strIF_TR_COL = strIF_TR_COL;
		this.strIF_TR_MEASURE = strIF_TR_MEASURE;
	}
	
	public SelectField(String strSELECT_PARAM_ID, String strQID,
			String strSELECT_PARAM, String strPARAM_TABLE,
			String strPARAM_FIELD, String strIS_DEFAULT, String strIS_DERIVED,
			String strUSER_FORMULA, String strSQL_FORMULA,
			String ifSelectedByUserToView, String strSELECT_PARAM_IMPL_ID,
			String strSLNO, String ifSelectedByUserToGroupBy,
			String strGROUPBY_PARAM_ID, String strGB_SLNO,String strIF_TR_ROW,String strIF_TR_COL,String strIF_TR_MEASURE,String ifSelectedByUserToTransRow,String ifSelectedByUserToTransCol) {
		super();
		this.strSELECT_PARAM_ID = strSELECT_PARAM_ID;
		this.strQID = strQID;
		this.strSELECT_PARAM = strSELECT_PARAM;
		this.strPARAM_TABLE = strPARAM_TABLE;
		this.strPARAM_FIELD = strPARAM_FIELD;
		this.strIS_DEFAULT = strIS_DEFAULT;
		this.strIS_DERIVED = strIS_DERIVED;
		this.strUSER_FORMULA = strUSER_FORMULA;
		this.strSQL_FORMULA = strSQL_FORMULA;
		this.ifSelectedByUserToView = ifSelectedByUserToView;
		this.strSELECT_PARAM_IMPL_ID = strSELECT_PARAM_IMPL_ID;
		this.strSLNO = strSLNO;
		this.ifSelectedByUserToGroupBy = ifSelectedByUserToGroupBy;
		this.strGROUPBY_PARAM_ID = strGROUPBY_PARAM_ID;
		this.strGB_SLNO = strGB_SLNO;
		this.strIF_TR_ROW = strIF_TR_ROW;
		this.strIF_TR_COL = strIF_TR_COL;
		this.strIF_TR_MEASURE = strIF_TR_MEASURE;
		this.setIfSelectedByUserToTransRow(ifSelectedByUserToTransRow);
		this.setIfSelectedByUserToTransCol(ifSelectedByUserToTransCol);
	}
	
	public String getIfSelectedByUserToTransRow() {
		return ifSelectedByUserToTransRow;
	}

	public void setIfSelectedByUserToTransRow(String ifSelectedByUserToTransRow) {
		this.ifSelectedByUserToTransRow = ifSelectedByUserToTransRow;
	}

	public String getIfSelectedByUserToTransCol() {
		return ifSelectedByUserToTransCol;
	}

	public void setIfSelectedByUserToTransCol(String ifSelectedByUserToTransCol) {
		this.ifSelectedByUserToTransCol = ifSelectedByUserToTransCol;
	}

	public String getIfSelectedByUserToGroupBy() {
		return ifSelectedByUserToGroupBy;
	}
	public void setIfSelectedByUserToGroupBy(String ifSelectedByUserToGroupBy) {
		this.ifSelectedByUserToGroupBy = ifSelectedByUserToGroupBy;
	}
	public String getStrGB_SLNO() {
		return strGB_SLNO;
	}
	public void setStrGB_SLNO(String strGB_SLNO) {
		this.strGB_SLNO = strGB_SLNO;
	}
	public String getStrGROUPBY_PARAM_ID() {
		return strGROUPBY_PARAM_ID;
	}
	public void setStrGROUPBY_PARAM_ID(String strGROUPBY_PARAM_ID) {
		this.strGROUPBY_PARAM_ID = strGROUPBY_PARAM_ID;
	}
	
	
	public String getIfSelectedByUserToView() {
		return ifSelectedByUserToView;
	}
	public void setIfSelectedByUserToView(String ifSelectedByUserToView) {
		this.ifSelectedByUserToView = ifSelectedByUserToView;
	}
	public String getStrSLNO() {
		return strSLNO;
	}
	public void setStrSLNO(String strSLNO) {
		this.strSLNO = strSLNO;
	}
	public String getStrSELECT_PARAM_IMPL_ID() {
		return strSELECT_PARAM_IMPL_ID;
	}
	public void setStrSELECT_PARAM_IMPL_ID(String strSELECT_PARAM_IMPL_ID) {
		this.strSELECT_PARAM_IMPL_ID = strSELECT_PARAM_IMPL_ID;
	}
	public String getStrSELECT_PARAM_ID() {
		return strSELECT_PARAM_ID;
	}
	public void setStrSELECT_PARAM_ID(String strSELECT_PARAM_ID) {
		this.strSELECT_PARAM_ID = strSELECT_PARAM_ID;
	}
	public String getStrQID() {
		return strQID;
	}
	public void setStrQID(String strQID) {
		this.strQID = strQID;
	}
	public String getStrSELECT_PARAM() {
		return strSELECT_PARAM;
	}
	public void setStrSELECT_PARAM(String strSELECT_PARAM) {
		this.strSELECT_PARAM = strSELECT_PARAM;
	}
	public String getStrPARAM_TABLE() {
		return strPARAM_TABLE;
	}
	public void setStrPARAM_TABLE(String strPARAM_TABLE) {
		this.strPARAM_TABLE = strPARAM_TABLE;
	}
	public String getStrPARAM_FIELD() {
		return strPARAM_FIELD;
	}
	public void setStrPARAM_FIELD(String strPARAM_FIELD) {
		this.strPARAM_FIELD = strPARAM_FIELD;
	}
	public String getStrIS_DEFAULT() {
		return strIS_DEFAULT;
	}
	public void setStrIS_DEFAULT(String strIS_DEFAULT) {
		this.strIS_DEFAULT = strIS_DEFAULT;
	}
	public String getStrIS_DERIVED() {
		return strIS_DERIVED;
	}
	public void setStrIS_DERIVED(String strIS_DERIVED) {
		this.strIS_DERIVED = strIS_DERIVED;
	}
	public String getStrUSER_FORMULA() {
		return strUSER_FORMULA;
	}
	public void setStrUSER_FORMULA(String strUSER_FORMULA) {
		this.strUSER_FORMULA = strUSER_FORMULA;
	}
	public String getStrSQL_FORMULA() {
		return strSQL_FORMULA;
	}
	public void setStrSQL_FORMULA(String strSQL_FORMULA) {
		this.strSQL_FORMULA = strSQL_FORMULA;
	}

	public String getStrIF_TR_ROW() {
		return strIF_TR_ROW;
	}

	public void setStrIF_TR_ROW(String strIF_TR_ROW) {
		this.strIF_TR_ROW = strIF_TR_ROW;
	}

	public String getStrIF_TR_COL() {
		return strIF_TR_COL;
	}

	public void setStrIF_TR_COL(String strIF_TR_COL) {
		this.strIF_TR_COL = strIF_TR_COL;
	}

	public String getStrIF_TR_MEASURE() {
		return strIF_TR_MEASURE;
	}

	public void setStrIF_TR_MEASURE(String strIF_TR_MEASURE) {
		this.strIF_TR_MEASURE = strIF_TR_MEASURE;
	}
	
	
	
	
}
