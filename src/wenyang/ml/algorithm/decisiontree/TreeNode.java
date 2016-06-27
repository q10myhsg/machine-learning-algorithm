package wenyang.ml.algorithm.decisiontree;

import java.util.List;
import java.util.Vector;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;

public class TreeNode {
	String attribute="";
	String value="";
	Vector<TreeNode> children= new Vector<TreeNode>();
	List<BaseItem<String>> data;
	boolean isLeaf=false;
	String category="";
	int depth=0;
	public void addDepth(){
		depth++;
	}
	
	public TreeNode init(Dataum<BaseItem<String>> a){		
		data=a.getData();
		TreeNode tn = new TreeNode();
		return tn;
	}
	
}
