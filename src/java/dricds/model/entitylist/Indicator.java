package dricds.model.entitylist;

public class Indicator {
	private String qid;
	private String indicatorName;
	private String indicatorMeasure;
	private String denominatorMeasure;
	private int weight;
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getIndicatorName() {
		return indicatorName;
	}
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	public String getIndicatorMeasure() {
		return indicatorMeasure;
	}
	public void setIndicatorMeasure(String indicatorMeasure) {
		this.indicatorMeasure = indicatorMeasure;
	}
	public String getDenominatorMeasure() {
		return denominatorMeasure;
	}
	public void setDenominatorMeasure(String denominatorMeasure) {
		this.denominatorMeasure = denominatorMeasure;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
