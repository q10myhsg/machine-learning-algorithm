package wenyang.ml.algorithm.lm;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;
import wenyang.ml.data.imp.SimpleDataInfo;
import wenyang.ml.data.parser.RegressionDataParser;
import wenyang.ml.utils.PearsonCoefficient;

public class PearsonHelper {
	Dataum<BaseItem<Float>> allData;	
	public void loadData(){
		String fileName="resources/regression_perc.txt";
		SimpleDataInfo sdi = new SimpleDataInfo();
		sdi.setFileName(fileName);
		RegressionDataParser rdp = new RegressionDataParser();		
		allData= rdp.parseData(sdi);		
	}	
	
	public void running(){
		PearsonCoefficient pc = new PearsonCoefficient();
		pc.setData(allData);
		System.out.println(pc.getCoefficent(-1,1,2,3));
	}
}
