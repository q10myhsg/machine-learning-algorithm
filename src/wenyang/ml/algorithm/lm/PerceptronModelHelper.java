package wenyang.ml.algorithm.lm;

import wenyang.ml.algorithm.perceptron.PerceptronModel;
import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;
import wenyang.ml.base.Model;
import wenyang.ml.data.imp.SimpleDataInfo;
import wenyang.ml.data.parser.RegressionDataParser;
import wenyang.ml.utils.IHelper;

public class PerceptronModelHelper  implements IHelper{	
	Dataum<BaseItem<Float>> allData;	
	public void loadData(){
		String fileName="resources/regression_perc.txt";
		SimpleDataInfo sdi = new SimpleDataInfo();
		sdi.setFileName(fileName);
		RegressionDataParser rdp = new RegressionDataParser();		
		allData= rdp.parseData(sdi);		
	}	
	
	public void running(){
		Model m = new PerceptronModel();
		m.setSize(3);
		m.setData(allData);
		m.run();	
		System.out.println(m.toString());		
	}

}
