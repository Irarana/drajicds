package dricds.model.entitylist;

public class QueryCondition {
	private String l_bracket_cnt;
	private String param_id;
	private String param;
	private String operator;
	private String rhs_value1;
	private String rhs_value2;
	private String rhs_qid;
	private String rhs_query_str;
	private String r_bracket_cnt;
	private String logical;
	private String appliedontype;
	private String appliedontable;
	private String appliedonfield;
	
	public String getRhs_query_str() {
		return rhs_query_str;
	}

	public void setRhs_query_str(String rhs_query_str) {
		this.rhs_query_str = rhs_query_str;
	}

	public QueryCondition(String l_bracket_cnt, String param_id, String param,
			String operator, String rhs_value1, String rhs_value2,
			String rhs_qid,String rhs_query_str, String r_bracket_cnt, String logical,
			String appliedontype, String appliedontable, String appliedonfield) {
		super();
		this.l_bracket_cnt = l_bracket_cnt;
		this.param_id = param_id;
		this.param = param;
		this.operator = operator;
		this.rhs_value1 = rhs_value1;
		this.rhs_value2 = rhs_value2;
		this.rhs_qid = rhs_qid;
		this.rhs_query_str = rhs_query_str;
		this.r_bracket_cnt = r_bracket_cnt;
		this.logical = logical;
		this.appliedontype = appliedontype;
		this.appliedontable = appliedontable;
		this.appliedonfield = appliedonfield;
	}
	
	public String getSQLStr(){
		String sqlstr="";
		String paramType=this.getAppliedontype();
		System.out.println(">>>>>>>>>>>>>> In getSQLStr operator:"+operator);
		if(operator.equalsIgnoreCase("LIKE")){
			sqlstr = this.getAppliedonfield() + " LIKE '%"+ this.getRhs_value1()+"%'";
		}else if(operator.equalsIgnoreCase("=") || operator.equalsIgnoreCase("<>")){
			sqlstr = this.getAppliedonfield() + this.getOperator() +"'"+ this.getRhs_value1()+"'";
		}else if(operator.trim().equalsIgnoreCase("IN")){
			System.out.println(" In getSQLStr In operator:"+operator);
			sqlstr = this.getAppliedonfield() + " " +this.getOperator()+" ("+ this.getRhs_query_str()+")";
			System.out.println(" In getSQLStr sqlstr:"+sqlstr);
		}else if(operator.equalsIgnoreCase("BETWEEN")){
			if(paramType.equalsIgnoreCase("D")){
				sqlstr = "("+this.getAppliedonfield() + this.getOperator() +"'"+ this.getRhs_value1()+"' AND '"+ this.getRhs_value2() +"')";
			}else{
				sqlstr = "("+this.getAppliedonfield() + this.getOperator() + this.getRhs_value1()+" AND "+ this.getRhs_value2() +")";
			}
		}else if(operator.equalsIgnoreCase(">") || operator.equalsIgnoreCase(">=") || operator.equalsIgnoreCase("<") || operator.equalsIgnoreCase("<=")){
			sqlstr = this.getAppliedonfield() + this.getOperator() + this.getRhs_value1();
		}else if(operator.equalsIgnoreCase("IS NULL") || operator.equalsIgnoreCase("IS NOT NULL")){
			sqlstr = this.getAppliedonfield() + this.getOperator();
		}
		
		if(this.l_bracket_cnt!=null && !this.l_bracket_cnt.trim().equalsIgnoreCase("")){
			if(this.l_bracket_cnt.equalsIgnoreCase("1")){
				sqlstr = "("+sqlstr;
			}else if(this.l_bracket_cnt.equalsIgnoreCase("2")){
				sqlstr = "(("+sqlstr;
			}else if(this.l_bracket_cnt.equalsIgnoreCase("3")){
				sqlstr = "((("+sqlstr;
			}else if(this.l_bracket_cnt.equalsIgnoreCase("4")){
				sqlstr = "((("+sqlstr;
			}
		}
		
		if(this.r_bracket_cnt!=null && !this.r_bracket_cnt.trim().equalsIgnoreCase("")){
			if(this.r_bracket_cnt.equalsIgnoreCase("1")){
				sqlstr = sqlstr+")";
			}else if(this.r_bracket_cnt.equalsIgnoreCase("2")){
				sqlstr = sqlstr+"))";
			}else if(this.r_bracket_cnt.equalsIgnoreCase("3")){
				sqlstr = sqlstr+")))";
			}else if(this.r_bracket_cnt.equalsIgnoreCase("4")){
				sqlstr = sqlstr+"))))";
			}
		}
		
		if(this.logical!=null && !this.logical.trim().equalsIgnoreCase("")){
			sqlstr = sqlstr+" "+this.logical;
		}
		return sqlstr+" ";
	}
	
	public String getBlankSQLStr(){
		String sqlstr="";
		String paramType=this.getAppliedontype();
		
		if(operator.equalsIgnoreCase("LIKE")){
			sqlstr = this.getAppliedonfield() + " LIKE '%"+ this.getRhs_value1()+"%'";
		}else if(operator.equalsIgnoreCase("=") || operator.equalsIgnoreCase("<>")){
			sqlstr = this.getAppliedonfield() + this.getOperator() +"'"+ this.getRhs_value1()+"'";
		}else if(operator.equalsIgnoreCase("IN")){
			sqlstr = this.getAppliedonfield() +" "+ this.getOperator() +" ("+ this.getRhs_query_str()+")";
		}else if(operator.equalsIgnoreCase("BETWEEN")){
			if(paramType.equalsIgnoreCase("D")){
				sqlstr = "("+this.getAppliedonfield() + this.getOperator() +"'"+ this.getRhs_value1()+"' AND '"+ this.getRhs_value2() +"')";
			}else{
				sqlstr = "("+this.getAppliedonfield() + this.getOperator() + this.getRhs_value1()+" AND "+ this.getRhs_value2() +")";
			}
		}else if(operator.equalsIgnoreCase(">") || operator.equalsIgnoreCase(">=") || operator.equalsIgnoreCase("<") || operator.equalsIgnoreCase("<=")){
			sqlstr = this.getAppliedonfield() + this.getOperator() + this.getRhs_value1();
		}else if(operator.equalsIgnoreCase("IS NULL") || operator.equalsIgnoreCase("IS NOT NULL")){
			sqlstr = this.getAppliedonfield() + this.getOperator();
		}
		sqlstr=" TRUE ";
		if(this.l_bracket_cnt!=null && !this.l_bracket_cnt.trim().equalsIgnoreCase("")){
			if(this.l_bracket_cnt.equalsIgnoreCase("1")){
				sqlstr = "("+sqlstr;
			}else if(this.l_bracket_cnt.equalsIgnoreCase("2")){
				sqlstr = "(("+sqlstr;
			}else if(this.l_bracket_cnt.equalsIgnoreCase("3")){
				sqlstr = "((("+sqlstr;
			}else if(this.l_bracket_cnt.equalsIgnoreCase("4")){
				sqlstr = "((("+sqlstr;
			}
		}
		
		if(this.r_bracket_cnt!=null && !this.r_bracket_cnt.trim().equalsIgnoreCase("")){
			if(this.r_bracket_cnt.equalsIgnoreCase("1")){
				sqlstr = sqlstr+")";
			}else if(this.r_bracket_cnt.equalsIgnoreCase("2")){
				sqlstr = sqlstr+"))";
			}else if(this.r_bracket_cnt.equalsIgnoreCase("3")){
				sqlstr = sqlstr+")))";
			}else if(this.r_bracket_cnt.equalsIgnoreCase("4")){
				sqlstr = sqlstr+"))))";
			}
		}
		
		if(this.logical!=null && !this.logical.trim().equalsIgnoreCase("")){
			sqlstr = sqlstr+" "+this.logical;
		}
		return sqlstr+" ";
	}
	
	public String getRawConditionSQLStr(){
		String sqlstr="";
		String paramType=this.getAppliedontype();
		
		if(operator.equalsIgnoreCase("LIKE")){
			sqlstr = this.getAppliedonfield() + " LIKE '%"+ this.getRhs_value1()+"%'";
		}else if(operator.equalsIgnoreCase("=") || operator.equalsIgnoreCase("<>")){
			sqlstr = this.getAppliedonfield() + this.getOperator() +"'"+ this.getRhs_value1()+"'";
		}else if(operator.equalsIgnoreCase("IN")){
			sqlstr = this.getAppliedonfield() +" " + this.getOperator() +" ("+ this.getRhs_query_str()+")";
		}else if(operator.equalsIgnoreCase("BETWEEN")){
			if(paramType.equalsIgnoreCase("D")){
				sqlstr = "("+this.getAppliedonfield() + this.getOperator() +"'"+ this.getRhs_value1()+"' AND '"+ this.getRhs_value2() +"')";
			}else{
				sqlstr = "("+this.getAppliedonfield() + this.getOperator() + this.getRhs_value1()+" AND "+ this.getRhs_value2() +")";
			}
		}else if(operator.equalsIgnoreCase(">") || operator.equalsIgnoreCase(">=") || operator.equalsIgnoreCase("<") || operator.equalsIgnoreCase("<=")){
			sqlstr = this.getAppliedonfield() + this.getOperator() + this.getRhs_value1();
		}else if(operator.equalsIgnoreCase("IS NULL") || operator.equalsIgnoreCase("IS NOT NULL")){
			sqlstr = this.getAppliedonfield() + this.getOperator();
		}
		
		return sqlstr+" ";
	}
	
	public String getL_bracket_cnt() {
		return l_bracket_cnt;
	}
	public void setL_bracket_cnt(String l_bracket_cnt) {
		this.l_bracket_cnt = l_bracket_cnt;
	}
	public String getParam_id() {
		return param_id;
	}
	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRhs_value1() {
		return rhs_value1;
	}
	public void setRhs_value1(String rhs_value1) {
		this.rhs_value1 = rhs_value1;
	}
	public String getRhs_value2() {
		return rhs_value2;
	}
	public void setRhs_value2(String rhs_value2) {
		this.rhs_value2 = rhs_value2;
	}
	public String getRhs_qid() {
		return rhs_qid;
	}
	public void setRhs_qid(String rhs_qid) {
		this.rhs_qid = rhs_qid;
	}
	public String getR_bracket_cnt() {
		return r_bracket_cnt;
	}
	public void setR_bracket_cnt(String r_bracket_cnt) {
		this.r_bracket_cnt = r_bracket_cnt;
	}
	public String getLogical() {
		return logical;
	}
	public void setLogical(String logical) {
		this.logical = logical;
	}
	public String getAppliedontype() {
		return appliedontype;
	}
	public void setAppliedontype(String appliedontype) {
		this.appliedontype = appliedontype;
	}
	public String getAppliedontable() {
		return appliedontable;
	}
	public void setAppliedontable(String appliedontable) {
		this.appliedontable = appliedontable;
	}
	public String getAppliedonfield() {
		return appliedonfield;
	}
	public void setAppliedonfield(String appliedonfield) {
		this.appliedonfield = appliedonfield;
	}
}
