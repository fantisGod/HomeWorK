package classification.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class PropertyChoose {
	public static HashSet<String> Choose(LinkedList<Record> recordList, LinkedList<String> Property){
		HashMap<String,Double> map=new HashMap<String,Double>();
		for(String attr:Property){
			if(!attr.equals("y")){
				double entropy=getEntropy(new ArrayList(recordList),attr);
				map.put(attr, entropy);
			}		
		}
		HashSet<String> propertySet = new HashSet<String>();
		for(int i=0;i<10;++i){
			Iterator iter = map.entrySet().iterator();
			double max =1;
			String current="";
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				double value = (double) entry.getValue();
				String key = (String) entry.getKey();
				if(value<max){
					max=value;
					current=key;
				}
			}
			propertySet.add(current);
			map.remove(current);
		}	
		return propertySet;
	}
	private static double getEntropy(ArrayList<Record> recordList, String attr) {
		double entropy = 0;
		int total = recordList.size();
		HashMap<String, ArrayList<Record>> partition = getPartition(recordList, attr);
		Iterator iter = partition.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ArrayList<Record> records = (ArrayList<Record>) entry.getValue();
			double n = records.size();
			double yes = 0, no = 0;
			for (Record record : records) {
				if (record.getValue("y").equals("yes")) {
					++yes;
				} else if (record.getValue("y").equals("no")) {
					++no;
				}
			}
			++yes;
			++no;
			double entropy_y = (yes / n) * (Math.log(yes / n) / Math.log(2));
			double entropy_n = (no / n) * (Math.log(no / n) / Math.log(2));
			double currentEntropy = -entropy_y - entropy_n;
			entropy += currentEntropy * n;
		}
		return entropy / total;
	}
	private static HashMap<String, ArrayList<Record>> getPartition(ArrayList<Record> recordList, String attr) {
		HashMap<String, ArrayList<Record>> Partition = new HashMap<String, ArrayList<Record>>();
		for (Record record : recordList) {
			String key = record.getValue(attr);
			if (Partition.containsKey(key)) {
				Partition.get(key).add(record);
			} else {
				ArrayList<Record> set = new ArrayList<Record>();
				set.add(record);
				Partition.put(key, set);
			}
		}
		return Partition;
	}
}
