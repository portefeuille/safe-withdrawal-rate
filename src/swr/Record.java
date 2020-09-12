package swr;

public class Record {
	
	String month;
	float marketBeta;
	float smallMinusBig;
	float valueMinusGrowth;
	float riskFree;
	
	public float getRiskFree() {
		return riskFree;
	}
	public void setRiskFree(float riskFree) {
		this.riskFree = riskFree;
	}
	public String getMois() {
		return month;
	}
	public void setMois(String mois) {
		this.month = mois;
	}
	public float getMarketBeta() {
		return marketBeta;
	}
	public void setMarketBeta(float marketBeta) {
		this.marketBeta = marketBeta;
	}
	public float getSmallMinusBig() {
		return smallMinusBig;
	}
	public void setSmallMinusBig(float smallMinusBig) {
		this.smallMinusBig = smallMinusBig;
	}
	public float getValueMinusGrowth() {
		return valueMinusGrowth;
	}
	public void setValueMinusGrowth(float valueMinusGrowth) {
		this.valueMinusGrowth = valueMinusGrowth;
	}
	
	

}
