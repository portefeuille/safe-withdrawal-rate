package swr;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Scenario {
	private float swr;
	private float maxDrawdown;
	private String monthStartOfMaxDrawdown;
	private String monthStartOfRetirement;
	private String monthEndOfMaxDrawdown;
	
	public Scenario(Float swr, float worseDD, String monthStartOfMaxDrawdown, String monthStartOfRetirement, String monthEndOfMaxDrawdown) {
		this.swr=swr;
		this.maxDrawdown=worseDD;
		this.monthStartOfMaxDrawdown=monthStartOfMaxDrawdown;
		this.monthEndOfMaxDrawdown=monthEndOfMaxDrawdown;
		this.monthStartOfRetirement=monthStartOfRetirement;
	}

	public float getSwr() {
		return swr;
	}

	public void setSwr(float swr) {
		this.swr = swr;
	}

	public float getMaxDrawdown() {
		return maxDrawdown;
	}

	public void setMaxDrawdown(float maxDrawdown) {
		this.maxDrawdown = maxDrawdown;
	}
	

	public String getMonthEndOfMaxDrawdown() {
		return monthEndOfMaxDrawdown;
	}

	public void setMonthEndOfMaxDrawdown(String monthEndOfMaxDrawdown) {
		this.monthEndOfMaxDrawdown = monthEndOfMaxDrawdown;
	}

	public String getMonthStartOfMaxDrawdown() {
		return monthStartOfMaxDrawdown;
	}

	public void setMonthStartOfMaxDrawdown(String monthStartOfMaxDrawdown) {
		this.monthStartOfMaxDrawdown = monthStartOfMaxDrawdown;
	}

	public String getMonthStartOfRetirement() {
		return monthStartOfRetirement;
	}

	public void setMonthStartOfRetirement(String monthStartOfRetirement) {
		this.monthStartOfRetirement = monthStartOfRetirement;
	}

	@Override
	public String toString() {
		Locale locale  = new Locale("en", "UK");
		
		String pattern = "###,###.#";
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);
		
		return "Scenario [swr=" + decimalFormat.format(swr) + "%, maxDrawdown=" + decimalFormat.format(maxDrawdown*100) + "%, monthEndOfMaxDrawdown="
				+ monthEndOfMaxDrawdown + ", monthStartOfMaxDrawdown=" + monthStartOfMaxDrawdown
				+ ", monthStartOfRetirement=" + monthStartOfRetirement + "]";
	}
	
	
	
	
}
