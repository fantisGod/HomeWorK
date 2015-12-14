package classificaton.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import classification.util.DecisionTree;
import classification.util.FileHelper;
import classification.util.KNN;
import classification.util.PropertyChoose;
import classification.util.Record;;

public class Classify {
	public static void main(String[] args) {
		LinkedList<Record> recordList = FileHelper.readFile("input/bank-additional-full.csv");
		HashSet<String> propertySet =PropertyChoose.Choose(recordList, recordList.get(1).getColumnNames());
		ClassifyByID3(recordList,propertySet);
		ClassifyByKnn(recordList,propertySet);
	}
	
	//决策树方法分类
	private static void ClassifyByID3(LinkedList<Record> recordList,HashSet<String> propertySet){
		recordList = AgeRange(recordList);
		DecisionTree tree = new DecisionTree();
		LinkedList<Record> dataList=new LinkedList<Record> ();
		LinkedList<Record> testList=new LinkedList<Record> ();
		int i;
	    for(i=0;i<(recordList.size()/10)*9;++i){
	    	dataList.add(recordList.get(i));
	    }
	    for(;i<recordList.size();++i){
	    	testList.add(recordList.get(i));
	    }
		tree.createTree(recordList, propertySet);
		double count=0;
		for(int j=0;j<testList.size();++j){
			if(tree.Check(recordList.get(j)).equals(recordList.get(j).getValue("y"))){
				++count;
			} 
		}
		double total=recordList.size()/10;
		System.out.println("accuracy:"+count/total);	
	}
	
	// 年龄离散化处理
	private static LinkedList<Record> AgeRange(LinkedList<Record> recordList) {
		for (Record record : recordList) {
			int age = Integer.parseInt(record.getValue("age"));
			age = age / 10;
			record.setValue("age", age + "");
		}
		return recordList;
	}
	
	//使用knn方法进行分类
		private static void ClassifyByKnn(LinkedList<Record> recordList,HashSet<String> propertySet)
		{
			ArrayList<String> attrList = new ArrayList<String>(propertySet);
			ArrayList<Record> trainRecords = new ArrayList<Record>(recordList);
			ArrayList<Record> testRecords = new ArrayList<Record>();
			Random random = new Random();
			for(int i=0;i<4120;i++)
			{
				int tmp = random.nextInt(41187-i);
				testRecords.add(trainRecords.get(tmp));
				trainRecords.remove(tmp);
			}
			
			ArrayList<String> knnResList = KNN.classifier(recordList,trainRecords,testRecords,attrList);
			
			int rightNum = 0;
			int len = knnResList.size();
			for(int i=0;i<len;i++)
			{
				//System.out.println(knnResList.get(i));
				if(knnResList.get(i).equals(testRecords.get(i).getValue("y")))
					rightNum++;
			}
			System.out.println((double)rightNum/len);
		}
}
