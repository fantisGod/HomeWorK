package Clustering.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Kmeans {
	private LinkedList<Record> recordList; // 原始数据
	private HashMap<String, Record> center; // 中心点
	private HashMap<String, LinkedList<Record>> cluster; // 聚类后的数据
	private int k;
	private boolean isStop;

	public Kmeans(int k) {
		center = new HashMap<String, Record>();
		recordList = new LinkedList<Record>();
		cluster = new HashMap<String, LinkedList<Record>>();
		this.k = k;
		this.isStop = true;
	}

	public HashMap<String, LinkedList<Record>> clustering(LinkedList<Record> records) {
		this.recordList = records;
		int i=0;
		init();
		do {
			cluster.clear();
			classify();
			i++;
			calCenter();
		} while (!isStop); 
		System.out.println("Total:"+i+" times");
		return this.cluster;
	}
	// 选取初始中心点
	private void init() {
		int max = this.recordList.size() + 1;
		int k = this.k;
		Random random = new Random();
		for (int i = 1; i <= k; ++i) {
			center.put(i + "", this.recordList.get(random.nextInt(max)));
		}
	}

	// 将每个点划分到最近的类
	private void classify() {
		String classNum;
		boolean flag=true;
		for (Record record : recordList) {	
			classNum = chooseCenter(record);
			if (record.getValue("class") == null || !record.getValue("class").equals(classNum)) {
				record.setValue("flag","0");
				flag=false;
			}
			if(flag){
				this.isStop=true;
			}
			else{
				this.isStop=false;
			}
			record.setValue("class", classNum);
			if (!cluster.containsKey(classNum)) {	
				cluster.put(classNum, new LinkedList<Record>());
			}
			cluster.get(classNum).add(record);
		}
	}

	// 重新计算每个类的中心点
	private void calCenter() {
		Iterator iter = cluster.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String classNum = (String) entry.getKey();
			LinkedList<Record> records = (LinkedList<Record>) entry.getValue();
			// 选择新的中心点
			center.put(classNum, calMeans(records));
		}
	}

	private Record calMeans(LinkedList<Record> records) {
		int num = records.size();
		double totalX = 0, totalY = 0;
		for (Record record : records) {
			totalX += Double.parseDouble(record.getValue("x"));
			totalY += Double.parseDouble(record.getValue("y"));
		}
		Record center = new Record(totalX / num + "", totalY / num + "");
		return center;
	}

	private String chooseCenter(Record record) {
		String result = "";
		double minDistance = Double.MAX_VALUE;
		Iterator iter = center.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String classNum = (String) entry.getKey();
			Record currenCenter = (Record) entry.getValue();
			double currentDistance = distance(currenCenter, record);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				result = classNum;
			}
		}
		return result;
	}

	private double distance(Record r1, Record r2) {
		double x1, y1, x2, y2;
		x1 = Double.parseDouble(r1.getValue("x"));
		y1 = Double.parseDouble(r1.getValue("y"));
		x2 = Double.parseDouble(r2.getValue("x"));
		y2 = Double.parseDouble(r2.getValue("y"));
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

}
