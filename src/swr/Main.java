package swr;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static List<Record> serie = KensFile.load();
	static List<Float> inflation = CpiFile.load();

	public static void main(String[] args) {
		
		System.out.println(computeSWR(100,0,0)); //SWR of the market portfolio
		System.out.println(computeSWR(115,80,40)); //SWR of a fund tracking MSCI Small Cap Value Weighted Index
		System.out.println(computeSWR(99,87,80)); //SWR of a fund like Avantis Small Cap Value
		System.out.println(computeSWR(30,85,100)); //the optimal SWR : 5.6% SWR
		
//		System.out.println();
//		
//		for(int marketBeta=-100; marketBeta<=100; marketBeta+=10) {
//			
//			System.out.print(marketBeta+",");
//			
//			for(int avantis=0; avantis<=200; avantis+=10) {
//				int beta = (int) (marketBeta+avantis);
//				int smb = (int) (0.9f*avantis);
//				int hml = (int) (0.8f*avantis);
//				Scenario res = computeSWR(beta,smb,hml);
//				float swr = res==null ? 0 : res.getSwr();
//				System.out.print(swr+",");
//			}
//			
//			System.out.println();	
//		}
//		
//		System.out.println();
//		
//		for(int marketBeta=-100; marketBeta<=100; marketBeta+=10) {
//			
//			System.out.print(marketBeta+",");
//			
//			for(int avantis=0; avantis<=200; avantis+=10) {
//				int beta = (int) (marketBeta+avantis);
//				int smb = (int) (0.9f*avantis);
//				int hml = (int) (0.8f*avantis);
//				Scenario res = computeSWR(beta,smb,hml);
//				float maxDD = res==null ? 1 : res.getMaxDrawdown();
//				System.out.print(maxDD+",");
//			}
//			
//			System.out.println();
//		for(int marketBeta=0; marketBeta<=100; marketBeta+=10) {
//			for(int smallMinusBig=0; smallMinusBig<=150; smallMinusBig+=5) {
//				for(int valueMinusGrowth=0; valueMinusGrowth<=150; valueMinusGrowth+=5) {
//					Scenario swr = computeSWR(marketBeta,smallMinusBig,valueMinusGrowth);
//					if(swr!=null && swr.getSwr()>5.9) {
//						//displays all High SWRs it finds
//						System.out.println(swr+ ", beta:"+marketBeta+", SMB:"+smallMinusBig+", HML:"+valueMinusGrowth);
//					}
//				}
//			}
//		}
	}

	private static Scenario computeSWR(int marketBeta, int smallMinusBig, int valueMinusGrowth) {
		float swr = 0.0f;
		Scenario res = null;
		boolean portfolioSurvival = true;
		while(portfolioSurvival) {
			List<MaxDD> maxDrawdowns = new ArrayList<>();
			for(int i=0; i<serie.size()-60*12; i++) {
				MaxDD simulationI = simulation(i,marketBeta/100f,smallMinusBig/100f,valueMinusGrowth/100f,swr);
				maxDrawdowns.add(simulationI);
			}
			portfolioSurvival=lessThanFivePercentEqualOne(maxDrawdowns);
			if(portfolioSurvival) {
				res = getScenario(swr,maxDrawdowns);
				swr+=0.1;
			}
		}
		return res;
	}

	private static Scenario getScenario(Float swr, List<MaxDD> maxDrawdowns) {
		// Here less than 5% of maxDrawdowns equal 1.
		// We search for the worst event excluding those outliers.
		int worsePosition=0;
		float worseDD = 0;
		for(int i=0; i<maxDrawdowns.size(); i++) {
			float f = maxDrawdowns.get(i).getMaxDD();
			if(f<=0.99 && f>worseDD) {
				worseDD=f;
				worsePosition=i;
			}
		}
		MaxDD worseRetirement = maxDrawdowns.get(worsePosition);
		return new Scenario(swr,worseDD,serie.get(worseRetirement.getIdMonthStart()).getMonth() ,serie.get(worsePosition).getMonth(),serie.get(worseRetirement.getIdMonthEnd()).getMonth());
	}

	private static boolean lessThanFivePercentEqualOne(List<MaxDD> survivalEvents) {
		int counter = 0;
		for(MaxDD p : survivalEvents) {
			if(p.getMaxDD()>0.99f) {
				counter++;
			}
		}
		return counter*1.0/survivalEvents.size()<=0.05;
	}

	private static MaxDD simulation(int idMonthStart, float marketBeta, float smallMinusBig,
			float valueMinusGrowth, float swr) {
		int idMonthEnd=Math.min(idMonthStart+60*12, serie.size()-1);
		float portfolioRealValue = 1000000f;
		float maxPortfolioValue=portfolioRealValue;
		int idMonthStartMaxDD=0;
		int idMonthEndMaxDD=0;
		int recordHigh=0;
		float maxDD=0;
		float monthlyRealWithdrawal = swr*portfolioRealValue/100.0f/12.0f;
		int i = idMonthStart;
		while(i<idMonthEnd && portfolioRealValue>0) {
			Record month = serie.get(i);
			portfolioRealValue=(1+month.getMarketBeta()*marketBeta+month.getSmallMinusBig()*smallMinusBig+month.getValueMinusGrowth()*valueMinusGrowth+month.getRiskFree())*portfolioRealValue/(1+inflation.get(i));
			portfolioRealValue-=monthlyRealWithdrawal;
			if(portfolioRealValue>maxPortfolioValue) {
				maxPortfolioValue=portfolioRealValue;
				recordHigh=i;
			}
			float drawdown = Math.min(1-portfolioRealValue/maxPortfolioValue,1);
			if(drawdown>maxDD) {
				maxDD = drawdown;
				idMonthEndMaxDD=i;
				idMonthStartMaxDD=recordHigh;
			}
			i++;
		}
		return new MaxDD(maxDD,idMonthStartMaxDD,idMonthEndMaxDD);
	}

}
