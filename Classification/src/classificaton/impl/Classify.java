package classificaton.impl;

import java.util.HashSet;
import java.util.LinkedList;

import classification.util.DecisionTree;
import classification.util.FileHelper;
import classification.util.PropertyChoose;
import classification.util.Record;;

public class Classify {
	public static void main(String[] args) {
		LinkedList<Record> recordList = FileHelper.readFile("input/bank-additional-full.csv");
		HashSet<String> propertySet =PropertyChoose.Choose(recordList, recordList.get(1).getColumnNames());
		ClassifyByID3(recordList,propertySet);
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
}
