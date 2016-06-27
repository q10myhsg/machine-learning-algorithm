package wenyang.ml.algorithm.decisiontree;

import java.util.HashMap;
import java.util.Iterator;

import wenyang.ml.base.Atrribute;
import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;
import wenyang.ml.data.in.IDataInfo;
import wenyang.ml.data.in.IDataParser;

public class DTreeParser  implements IDataParser<BaseItem<String>> {	
	public static final String SPLIT_STRING=" ";
	HashMap<Integer,String> hm = new HashMap<Integer,String>();
	@Override
	public Dataum<BaseItem<String>> parseData(IDataInfo d) {
		Dataum<BaseItem<String>> dd = new Dataum<BaseItem<String>>();
		Iterator<String> it =d.loadData();	
		String temp ="";
		if(it.hasNext()){
			temp=it.next();
			String[] arr =temp.split(SPLIT_STRING);
			for(int i=0;i<arr.length;i++){
				hm.put(i, arr[i].trim());
			}	
		}
		while(it.hasNext()){
			temp= it.next();
			if(temp!=null&&temp.trim().length()>0){
				String[] arr =temp.split(SPLIT_STRING);		
				BaseItem<String> bi = new BaseItem<String>(); 
				for(int i=0;i<arr.length;i++){				
					Atrribute<String> ai = new Atrribute<String>();
					ai.setKey(i);
					ai.setValue(arr[i].trim());
					bi.add(ai);					
				}
				dd.addItem(bi);
			}
		}		
		dd.setAttribute(hm);
		return dd;
	}

}
