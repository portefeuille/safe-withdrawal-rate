package swr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CpiFile {
	private static final String FILENAME="cpi.csv";
	
	public static List<Float> load(){
		List<Float> res = new ArrayList<>();
		try {
			List<String> allLines = Files.readAllLines(Paths.get(FILENAME));
			for (String line : allLines) {
				String[] elements = line.split(",");
				res.add(Float.valueOf(elements[1])/100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
}
