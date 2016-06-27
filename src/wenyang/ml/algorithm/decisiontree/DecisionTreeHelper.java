package wenyang.ml.algorithm.decisiontree;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;
import wenyang.ml.data.imp.SimpleDataInfo;
import wenyang.ml.utils.IHelper;

public class DecisionTreeHelper implements IHelper {
	Dataum<BaseItem<String>> allData;

	@Override
	public void loadData() {
		String fileName = "resources/decisiontree.txt";
		SimpleDataInfo sdi = new SimpleDataInfo();
		sdi.setFileName(fileName);
		DTreeParser rdp = new DTreeParser();
		allData = rdp.parseData(sdi);
	}

	@Override
	public void running() {
		TreeNode td = new TreeNode();
		DecisionTree dt = new DecisionTree();
		dt.setData(allData);
		dt.setLable(4);
		dt.init();
		td.data=allData.getData();
		td.addDepth();
		dt.run(td, allData.getAttribute());
		System.out.println(dt.printModel(td));
	}

}
