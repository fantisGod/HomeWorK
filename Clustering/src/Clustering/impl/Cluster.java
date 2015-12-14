package Clustering.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import Clustering.util.DBSCAN;
import Clustering.util.FileHelper;
import Clustering.util.Kmeans;
import Clustering.util.Record;

public class Cluster {

	public static void main(String[] args) {
		LinkedList<Record> recordList = FileHelper.readFile("input/dataset1.dat","input/dataset1-label.dat");
		clusteringByKmeans(recordList);
		clusteringByDBSCAN(recordList);
	}
	private static void clusteringByKmeans(LinkedList<Record> recordList){
		Kmeans cluster =new Kmeans(15);
		HashMap<String, LinkedList<Record>> result=cluster.clustering(recordList);
		System.out.println("Purity:"+getPurity(result,recordList));
		System.out.println("F-score:"+getFscore(result,recordList));		
	}
    public static double getPurity(HashMap<String, LinkedList<Record>> cluster,LinkedList<Record> recordList){
    	double accuracy=0;
		Iterator iter = cluster.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String classNum = (String) entry.getKey();
			LinkedList<Record> records=(LinkedList<Record>)entry.getValue();
			HashMap<String,Integer> countMap = new HashMap<String,Integer>();
			for(Record record :records){
				String key=record.getValue("truth");
				if(countMap.containsKey(key)){
					countMap.put(key, countMap.get(key)+1);
				}
				else{
					countMap.put(key, 1);
				}
			}
			int max=0;
			String key="";
			Iterator it = countMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry en = (Map.Entry) it.next();
				int times=(int)en.getValue();
				
				if(times>max){
					max=times;
					key=(String)en.getKey();
				}
			}
			for(Record record :records){
				record.setValue("class", key);
			}
			double currentAccuracy=(double)max/(double)records.size();
			accuracy+=records.size()*currentAccuracy;
		}	
		return accuracy/recordList.size();	
    }
	public static double getFscore(HashMap<String, LinkedList<Record>> cluster,LinkedList<Record> recordList){
    	double f_core=0;
    	Iterator iter = cluster.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String classNum = (String) entry.getKey();
			LinkedList<Record> records=(LinkedList<Record>)entry.getValue();
			String currentNum=records.get(0).getValue("class");
			double n1=0;
			for(Record record:records){
				if(record.getValue("truth").equals(currentNum)){
					++n1;
				}
			}
			double n2=0;
			for(Record record :recordList){
				if(record.getValue("class").equals(currentNum)){
					++n2;
				}
			}
			double P=n1/records.size();
			double R=n1/n2;
			f_core+= records.size()*2*P*R/(R+P);
		}
		return f_core/(double)recordList.size();  
    }
	
	private static void clusteringByDBSCAN(LinkedList<Record> recordList){
		HashMap<String, LinkedList<Record>> result=DBSCAN.clustering(5,3,recordList);
		System.out.println("Purity:"+getPurity(result,recordList));
		System.out.println("F-score:"+getFscore(result,recordList));		
	}
}
