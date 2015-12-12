package classification.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;


public class FileHelper {
	public static LinkedList<Record> readFile(String fileRoute) {
		File file = new File(fileRoute);
		LinkedList<Record> recordList = new LinkedList<Record>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			boolean columnNameLine = true;
			ColumnNames columnNames = null;
			while (null != (line = br.readLine())) {
				if (columnNameLine) {
					columnNames = new ColumnNames(line);
					columnNameLine = false;
				} else {
					Record record = new Record(columnNames, line);
					recordList.add(record);
				}
			}

			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return recordList;
	}  
}
