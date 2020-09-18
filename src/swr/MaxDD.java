package swr;

public class MaxDD {
	private int idMonthStart;
	private int idMonthEnd;
	private float maxDD;
	
	@Override
	public String toString() {
		return "MaxDD [idMonthStart=" + idMonthStart + ", idMonthEnd=" + idMonthEnd + ", maxDD=" + maxDD + "]";
	}

	public int getIdMonthStart() {
		return idMonthStart;
	}

	public void setIdMonthStart(int idMonthStart) {
		this.idMonthStart = idMonthStart;
	}

	public int getIdMonthEnd() {
		return idMonthEnd;
	}

	public void setIdMonthEnd(int idMonthEnd) {
		this.idMonthEnd = idMonthEnd;
	}

	public float getMaxDD() {
		return maxDD;
	}

	public void setMaxDD(float maxDD) {
		this.maxDD = maxDD;
	}

	public MaxDD(float maxDD, int idMonthStart, int idMonthEnd) {
		this.maxDD=maxDD;
		this.idMonthStart=idMonthStart;
		this.idMonthEnd=idMonthEnd;
	}
	
	
}
