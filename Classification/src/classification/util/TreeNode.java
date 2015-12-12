package classification.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class TreeNode {
	private String name;  // 当前属性名称
	private String value; // 上一个属性分裂至此结点的值
	private ArrayList<TreeNode> children; // 子结点
	private boolean isLeaf;  //是否为叶子结点
    public TreeNode(String value,boolean isLeaf){
    	this.children=new ArrayList<TreeNode>();
    	this.value=value;	
    	this.isLeaf=isLeaf;
    }
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ArrayList<TreeNode> getChildren() {
		return this.children;
	}

	public void addChild(TreeNode node) {
		this.children.add(node);
	}
	public boolean isLeaf(){
		return this.isLeaf;
	}
}
