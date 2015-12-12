package Clustering.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class FileHelper {
	public static LinkedList<Record> readFile(String fileRoute,String resutlFileRoute) {
		File file1 = new File(fileRoute);
		File file2 = new File(resutlFileRoute);
		LinkedList<Record> recordList = new LinkedList<Record>();
		try {
			FileReader fr1 = new FileReader(file1);
			FileReader fr2 = new FileReader(file2);
			BufferedReader br1 = new BufferedReader(fr1);
			BufferedReader br2 = new BufferedReader(fr2);
			String line = null,truth=null;
			while (null != (line = br1.readLine())&&null != (truth = br2.readLine())) {
				line=line.trim();
				Record record = new Record(line);
				record.setValue("truth", truth);
				recordList.add(record);
			}
			br1.close();
			fr1.close();
			br2.close();
			fr2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recordList;
	}  
}
