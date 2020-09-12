package swr;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static List<Record> serie = KensFile.load();

	public static void main(String[] args) {
		System.out.println(computeSWR(100,0,0)); //SWR of the market portfolio
		System.out.println(computeSWR(115,80,40)); //SWR of a fund tracking MSCI Small Cap Value Weighted Index
		System.out.println(computeSWR(99,87,80)); //SWR of a fund like Avantis Small Cap Value
		
		for(int marketBeta=0; marketBeta<=200; marketBeta+=5) {
			for(int smallMinusBig=-100; smallMinusBig<=100; smallMinusBig+=5) {
				for(int valueMinusGrowth=-100; valueMinusGrowth<=100; valueMinusGrowth+=5) {
					float swr = computeSWR(marketBeta,smallMinusBig,valueMinusGrowth);
					if(swr>5) {
						//displays all High SWRs it finds
						System.out.println(swr+","+marketBeta+","+smallMinusBig+","+valueMinusGrowth);
					}
				}
			}
		}
	}

	private static float computeSWR(int marketBeta, int smallMinusBig, int valueMinusGrowth) {
		float swr = 3.0f;
		boolean portfolioSurvival = true;
		while(portfolioSurvival) {
			List<Boolean> survivalEvent = new ArrayList<>();
			for(int i=0; i<serie.size()-60*12; i++) {
				survivalEvent.add(simulation(i,marketBeta/100f,smallMinusBig/100f,valueMinusGrowth/100f,swr));
			}
			portfolioSurvival=lessThanFivePercentFalse(survivalEvent);
			if(portfolioSurvival) {
				swr+=0.1;
			}			
		}
		return swr;
	}

	private static boolean lessThanFivePercentFalse(List<Boolean> survivalEvents) {
		int counter = 0;
		for(Boolean b : survivalEvents) {
			if(!b) {
				counter++;
			}
		}
		return counter*1.0/survivalEvents.size()<=0.05;
	}

	private static boolean simulation(int idMonthStart, float marketBeta, float smallMinusBig,
			float valueMinusGrowth, float swr) {
		int idMonthEnd=Math.min(idMonthStart+60*12, serie.size()-1);
		float portfolioValue = 1000000f;
		float monthlyWithdrawal = swr*portfolioValue/100.0f/12.0f;
		int i = idMonthStart;
		while(i<idMonthEnd && portfolioValue>0) {
			Record month = serie.get(i);
			portfolioValue=(1+month.getMarketBeta()*marketBeta+month.getSmallMinusBig()*smallMinusBig+month.getValueMinusGrowth()*valueMinusGrowth+month.getRiskFree())*portfolioValue;
			monthlyWithdrawal=monthlyWithdrawal*(1+month.getRiskFree());
			portfolioValue-=monthlyWithdrawal;
			i++;
		}
		return portfolioValue>=0;
	}

}
