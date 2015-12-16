package classification.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class DecisionTree {
	private TreeNode root;

	public void createTree(LinkedList<Record> recordList, HashSet<String> propertySet) {
		this.root = new TreeNode("", false);
		divide(new ArrayList(recordList), propertySet, root);
	}
	public String Check(Record record) {
		return search(record, this.root);
	}
	// 递归遍历决策树
	private String search(Record record, TreeNode node) {
		if (!node.isLeaf()) {
			for (TreeNode child : node.getChildren()) {
				if(node.getName().equals(""))
					return "no";
				if (record.getValue(node.getName()).equals(child.getValue())) {
					return search(record, child);
				}
			}
			return "failed";
		} else {
			return node.getName();
		}
	}
	private void divide(ArrayList<Record> recordList, HashSet<String> attrs, TreeNode currentNode) {
		String attr = getAttr(recordList, attrs);	
		currentNode.setName(attr);
		HashMap<String, ArrayList<Record>> partition = getPartition(recordList, attr);
		attrs.remove(attr);
		Iterator iter = partition.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ArrayList<Record> records = (ArrayList<Record>) entry.getValue();
			String key = (String) entry.getKey();
			String outPut = isLeaf(records);
			// 不是叶子结点
			if (outPut.equals("yes")||outPut.equals("no")) {
				TreeNode node = new TreeNode("key", true);
				node.setName(outPut);
				currentNode.addChild(node);
			}
			// 是叶子结点
			else {
				if(attrs.size()==0){
					TreeNode node = new TreeNode(key, true);
					node.setName(maxCount(records));
					currentNode.addChild(node);
				}
				else{
					TreeNode node = new TreeNode(key, false);
					currentNode.addChild(node);
					divide(records, attrs, node);
				}
			}
		}
	}
    private String maxCount(ArrayList<Record> records){
    	int y=0,n=0;
    	for (Record record : records) {
			if (record.getValue("y").equals("yes")) {
				++y;
			} else if(record.getValue("y").equals("no")){
				++n;
			}
		}
    	if(y>n)
    		return "yes";
    	else
    		return "no";
    }
	// 判断是否为叶子结点
	private String isLeaf(ArrayList<Record> records) {
		int y=0,n=0;
		for (Record record : records) {
			if (record.getValue("y").equals("yes")) {
				++y;
			} else if(record.getValue("y").equals("no")){
				++n;
			}
		}
		if (y==0&&n!=0) {
			return "no";
		} else if (y!=0&&n==0) {
			return "yes";
		}
		return "";
	}

	// 返回信息增益最大的属性
	private String getAttr(ArrayList<Record> recordList, HashSet<String> attrs) {
		String result = "";
		double entropy = 1;
		for (String attr : attrs) {
			double currentEntropy = getEntropy(recordList, attr);
			if (currentEntropy < entropy) {
				entropy = currentEntropy;
				result = attr;
			}
		}
		return result;
	}

	// 信息熵
	private double getEntropy(ArrayList<Record> recordList, String attr) {
		double entropy = 0;
		int total = recordList.size();   
		HashMap<String, ArrayList<Record>> partition = getPartition(recordList, attr);
		Iterator iter = partition.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ArrayList<Record> records = (ArrayList<Record>) entry.getValue();
			double n=records.size();
			double yes=0,no=0;
			for(Record record :records){
				if(record.getValue("y").equals("yes")){
					++yes;	
				}
				else if (record.getValue("y").equals("no")){
					++no;
				}
			}
			++yes;
			++no;
			double entropy_y=(yes/n)*(Math.log(yes/n)/Math.log(2));
			double entropy_n=(no/n)*(Math.log(no/n)/Math.log(2));
			double currentEntropy=-entropy_y-entropy_n;
			entropy+=currentEntropy*n;
		}
		return entropy/total;
	}

	// 返回根据属性分裂后的子集
	private HashMap<String, ArrayList<Record>> getPartition(ArrayList<Record> recordList, String attr) {
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
