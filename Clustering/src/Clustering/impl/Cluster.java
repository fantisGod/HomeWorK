package Clustering.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import Clustering.util.FileHelper;
import Clustering.util.Kmeans;
import Clustering.util.Record;

public class Cluster {

	public static void main(String[] args) {
		LinkedList<Record> recordList = FileHelper.readFile("input/dataset1.dat","input/dataset1-label.dat");
		clusteringByKmeans(recordList);
	}
	private static void clusteringByKmeans(LinkedList<Record> recordList){
		Kmeans cluster =new Kmeans(15);
		HashMap<String, LinkedList<Record>> result=cluster.clustering(recordList);
		System.out.println("Purity:"+cluster.getPurity());
		System.out.println("F-score:"+cluster.getFscore());		
	}
}
