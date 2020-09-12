package swr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class KensFile {
	private static final String FILENAME="F-F_Research_Data_Factors.CSV";
	
	public static List<Record> load(){
		List<Record> res = new ArrayList<>();
		try {
			List<String> allLines = Files.readAllLines(Paths.get(FILENAME));
			for (String line : allLines) {
				Record e = new Record();
				String[] elements = line.split(",");
				e.setMois(elements[0]);
				e.setMarketBeta(Float.valueOf(elements[1])/100);
				e.setSmallMinusBig(Float.valueOf(elements[2])/100);
				e.setValueMinusGrowth(Float.valueOf(elements[3])/100);
				e.setRiskFree(Float.valueOf(elements[4])/100);
				res.add(e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
