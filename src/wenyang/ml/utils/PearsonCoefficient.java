package wenyang.ml.utils;

import java.util.HashMap;
import java.util.Iterator;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;

public class PearsonCoefficient implements ICoefficent {
	private Dataum<BaseItem<Float>> allData;

	
	HashMap<Integer, Float> init(int... is) {

		HashMap<Integer, Float> hm = new HashMap<Integer, Float>();

		// 计算均值放到hm中
		for (int i = 0; i < is.length; i++) {
			if (is[i] == -1) {
				float sum = 0f;
				int count = 0;
				Iterator<BaseItem<Float>> it = allData.getData().iterator();
				while (it.hasNext()) {
					BaseItem<Float> bi = it.next();
					sum += bi.getScore();
					count++;
				}
				float floatDid = 0;
				if (count != 0) {
					floatDid = sum / count;
				}
				hm.put(is[i], floatDid);
			} else {
				float sum = 0f;
				int count = 0;
				Iterator<BaseItem<Float>> it = allData.getData().iterator();
				while (it.hasNext()) {
					BaseItem<Float> bi = it.next();
					Float f = bi.get(is[i]);
					if (f != null) {
						sum += bi.get(is[i]);
						count++;
					}
				}
				float floatDid = 0;
				if (count != 0) {
					floatDid = sum / count;
				}
				hm.put(is[i], floatDid);
			}
		}
		return hm;
	}

	@Override
	public String getCoefficent(int... is) {
		
		StringBuffer sb = new StringBuffer();
		HashMap<Integer, Float> hm = init(is);
		for (int i = 0; i < is.length; i++) {
			for (int j = i+1; j < is.length; j++) {
				Iterator<BaseItem<Float>> it = allData.getData().iterator();
				float asqr=0;
				float bsqr=0;
				float fenzi=0f;
				while (it.hasNext()) {
					BaseItem<Float> bi =  it.next();
					Float a=0f;
					if(is[i]==-1){
						a=bi.getScore();
					}else{
						a=bi.get(is[i]);
					}
					Float b=0f;
					if(is[j]==-1){
						b=bi.getScore();
					}else{
						b=bi.get(is[j]);
					}
					if(a==null) a =0f;
					if(b==null) b =0f;					
					a-=hm.get(is[i]);
					b-=hm.get(is[j]);
					fenzi+=a*b;
					asqr+=a*a;
					bsqr+=b*b;
				}
				
				double result=fenzi/Math.sqrt(asqr*bsqr);
				if(Double.isNaN(result)){
					result=0;
				}
				
				sb.append("this relatlion of "+is[i]+",+"+is[j] +" is "+result+"\r\n");				
			}
		}

		return sb.toString();
	}

	public void setData(Dataum<BaseItem<Float>> _data) {
		allData = _data;
	}

}
