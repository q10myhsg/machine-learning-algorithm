package wenyang.ml.algorithm.decisiontree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;

public class DecisionTree {

	Dataum<BaseItem<String>> allData;
	HashMap<Integer, Set<String>> attSets = new HashMap<Integer, Set<String>>();
	private int lable = 0;

	public void setData(Dataum<BaseItem<String>> a) {
		allData = a;
	}

	/**
	 * d 是tree的一个node 节点 attribute 属性选择列表，不包括已选择属性
	 */
	public void run(TreeNode d, final HashMap<Integer, String> attribute) {

		// 只有一个属性
		if (shouldStop(d, attribute))
			return;
		int select = selectDecisionPoint(d.data, attribute, 4);

		System.out.println("select point:" + select);
		// attribute statistics, it should initialized before calling
		Set<String> s = attSets.get(select);
		d.attribute = attribute.get(select);

		Iterator<BaseItem<String>> it = d.data.iterator();
		HashMap<String, List<BaseItem<String>>> hm = new HashMap<String, List<BaseItem<String>>>();
		while (it.hasNext()) {
			BaseItem<String> bi = it.next();
			Iterator<String> sIt = s.iterator();
			while (sIt.hasNext()) {
				String selectedValue = sIt.next();
				String itemSelectedValue = bi.get(select);
				if (itemSelectedValue.equals(selectedValue)) {
					List<BaseItem<String>> l = hm.get(itemSelectedValue);
					if (l == null) {
						l = new LinkedList<BaseItem<String>>();
					}
					l.add(bi);
					hm.put(itemSelectedValue, l);
				}
			}
		}

		HashMap<Integer, String> childAttribute = cloneHashMap(attribute);
		// remove select point
		childAttribute.remove(select);
		Iterator<String> childKey = hm.keySet().iterator();
		while (childKey.hasNext()) {
			String value = childKey.next();
			TreeNode t = new TreeNode();
			t.data = hm.get(value);
			t.value = value;
			d.children.add(t);
			t.depth = d.depth + 1;
			// 递归调用
			run(t, childAttribute);
		}

	}

	public void setLable(int a) {
		lable = a;
	}
	
	/**
	 * 选择划分点
	 */
	public int selectDecisionPoint(List<BaseItem<String>> l, HashMap<Integer, String> attribute, int lable) {
		int ret = 0;
		Iterator<Integer> itKey = attribute.keySet().iterator();
		double min = 2;
		while (itKey.hasNext()) {
			Integer key = itKey.next();
			if (key == lable) {
				break;
			}
			double temp = computeConditionEntropyLog(l, key);
			if (temp < min) {
				min = temp;
				ret = key;
			}
		}
		return ret;
	}

	public void init() {
		List<BaseItem<String>> l = allData.getData();
		Iterator<BaseItem<String>> it = l.iterator();
		while (it.hasNext()) {
			BaseItem<String> bi = it.next();
			Iterator<Integer> itP = bi.getPairs().keySet().iterator();
			while (itP.hasNext()) {
				int temp = itP.next();
				String value = bi.get(temp);
				if (value != null) {
					Set<String> s = attSets.get(temp);
					if (s == null) {
						s = new HashSet<String>();
					}
					s.add(value);
					attSets.put(temp, s);
				}
			}
		}
	}

	/**
	 * 计算熵前统计的准备
	 */
	public void computeEntropyStat(List<BaseItem<String>> data) {
		Iterator<BaseItem<String>> it = data.iterator();
		Set<String> s = attSets.get(lable);
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		if (s != null) {
			while (it.hasNext()) {
				BaseItem<String> bi = it.next();
				String str = bi.get(lable);
				Integer i = hm.get(str);
				if (i == null) {
					i = 0;
				}
				i++;
				hm.put(str, i);
			}
		}
	}

	/**
	 * 条件熵
	 */
	public double computeConditionEntropyLog(List<BaseItem<String>> data, int selected) {
		Set<String> lableSet = attSets.get(lable);
		Iterator<String> itLable = lableSet.iterator();
		double dSum = 0;
		while (itLable.hasNext()) {
			String temp = itLable.next();
			Iterator<BaseItem<String>> it = data.iterator();
			HashMap<String, Integer> hm = new HashMap<String, Integer>();
			while (it.hasNext()) {
				BaseItem<String> bi = it.next();
				if (bi.get(lable).equals(temp)) {
					String selecteValue = bi.get(selected);
					if (selecteValue != null) {
						Integer value = hm.get(selecteValue);
						if (value == null) {
							value = 0;
						}
						value++;
						hm.put(selecteValue, value);
					}
				}
			}
			dSum += computeEntropyLog(hm);
		}
		return dSum;
	}

	/**
	 * 计算熵
	 */
	public double computeEntropyLog(HashMap<String, Integer> hm) {
		Iterator<String> it = hm.keySet().iterator();
		int sum = 0;
		while (it.hasNext()) {
			sum += hm.get(it.next());
		}

		it = hm.keySet().iterator();

		double entropy = 0d;
		while (it.hasNext()) {
			int temp = hm.get(it.next());
			double p = (double) temp / sum;
			entropy += -p * Math.log(p);
		}
		return entropy;
	}

	// /**
	// * 计算条件熵
	// */
	// public double computeConditionEntropyLog(HashMap<String, Integer> hm, int
	// lable, int selectAtri) {
	//
	// Iterator<String> it = hm.keySet().iterator();
	// int sum = 0;
	// while (it.hasNext()) {
	// sum += hm.get(it.next());
	// }
	//
	// it = hm.keySet().iterator();
	// double entropy = 0d;
	// while (it.hasNext()) {
	// double p = hm.get(it.next()) / sum;
	// entropy += -p * Math.log(p);
	// }
	// return entropy;
	// }

	private HashMap<Integer, String> cloneHashMap(HashMap<Integer, String> hm) {
		HashMap<Integer, String> ret = new HashMap<Integer, String>();
		Iterator<Integer> it = hm.keySet().iterator();
		while (it.hasNext()) {
			Integer temp = it.next();
			ret.put(temp, new String(hm.get(temp)));
		}
		return ret;

	}

	/*
	 * 是否应该再继续遍历子节点
	 * 
	 */
	private boolean shouldStop(TreeNode d, final HashMap<Integer, String> attribute) {
		boolean ret = true;
		if (attribute.size() == 1) {
			ret = true;
			d.isLeaf = true;
			d.category = d.data.get(0).get(lable);
		} else {
			Iterator<BaseItem<String>> it = d.data.iterator();
			String temp = null;

			while (it.hasNext()) {
				BaseItem<String> bi = it.next();
				String category = bi.get(lable);
				if (temp == null) {
					temp = category;
				} else if (!temp.equals(category)) {
					return false;
				}
			}
			d.isLeaf = true;
			d.category = temp;
			return ret;
		}
		return true;
	}

	/**
	 * 层次遍历
	 */
	// public String printModel(TreeNode d){
	// StringBuffer sb = new StringBuffer();
	// List<TreeNode> temp=new LinkedList<TreeNode>();
	// temp.add(d);
	// while(temp.size()>0){
	// TreeNode tn = temp.remove(0);
	// for(int i=1;i<=tn.depth;i++){
	// sb.append("\t");
	// }
	// sb.append("---"+tn.attribute+"\r\n");
	// temp.addAll(tn.children);
	// }
	// return sb.toString();
	// }
	/**
	 * 递归深度遍历
	 */
	public String printModel(TreeNode d) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= d.depth; i++) {
			sb.append("\t\t");
		}
		sb.append(d.value);

		sb.append("\t " + d.attribute);
		if (d.isLeaf) {
			sb.append(" = " + d.category);
		}
		sb.append("\r\n");

		Vector<TreeNode> t = d.children;
		for (int i = 0; i < t.size(); i++) {
			sb.append(printModel(t.get(i)));
		}
		return sb.toString();
	}
}
