package Clustering.impl;

import java.util.LinkedList;

import Clustering.util.FileHelper;
import Clustering.util.Record;

public class Cluster {

	public static void main(String[] args) {
		LinkedList<Record> recordList = FileHelper.readFile("input/dataset1.dat","input/dataset1-label.dat");
		for(Record record :recordList){
			System.out.println(record.getValue("x")+"~"+record.getValue("y")+"~"+record.getValue("truth"));
		}
	}
}
