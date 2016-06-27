package wenyang.ml.optimization;

import java.util.Iterator;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;

public class GradientDescent {
	protected float alpha = 0.001f;// 训练步长
	protected int iteration_max = 1000;// 迭代次数
	protected float deltaRss = 0.00000001f;// 迭代次数
	protected float[] weight;
	protected float[] rss;	
	protected Dataum<BaseItem<Float>> d;
	public GradientDescent(){		
	}

	public float[] getWeight() {
		return weight;
	}

	public void setWeight(float[] weight) {
		this.weight = weight;
	}


	public void setRss(float[] rss) {
		this.rss = rss;
	}
	
	public float[] execute(float[] _weight,Dataum<BaseItem<Float>> _d){
		this.d=_d;
		rss=new float[iteration_max];
		int iteration = 0;		
		while (iteration < iteration_max) {
			
			float[] partial_derivative = computePartialDerivative();// 偏导数
			weight = getNextWeight(partial_derivative);
//			weight = normalizeWeight();
			rss[iteration] = this.getRss();
			System.out.println("=================");
			System.out.println("iteration:" + iteration);
			System.out.println("rss:" + rss[iteration]);
			System.out.println("model:\r\n" + this.toString());
			
			if(iteration>1){				
				float rssUpdate=Math.abs(rss[iteration]-rss[iteration-1]);
				System.out.println("rss update: " + rssUpdate);
				if(rssUpdate<deltaRss){
					iteration=Integer.MAX_VALUE-1;
				}	
			}
			iteration++;		
		}		
		return weight;
	}
	private float[] computePartialDerivative() {
		float[] partial_derivative = new float[weight.length];
		for (int j = 0; j < weight.length; j++)// 遍历，对每个theta求偏导数
		{
			float f = 0f;
			Iterator<BaseItem<Float>> it = d.getData().iterator();
			while (it.hasNext()) {
				float temp = 0f;
				BaseItem<Float> bi = it.next();
				if (bi.get(j) != null) {
					temp = getPredictScore(weight, bi);
					temp -= bi.getScore();
					temp *= bi.get(j);
				}
				f += temp;
			}
			partial_derivative[j] = f;
		}
		return partial_derivative;
	}


	/**
	 * 下一轮迭代weight
	 */
	public float[] getNextWeight(float[] partialDerivative) {
		float[] next = new float[partialDerivative.length];
		for (int i = 0; i < next.length; i++) {
			next[i] = weight[i] - alpha * partialDerivative[i];
		}
		return next;
	}

	public float getRss() {
		float sum = 0f;
		Iterator<BaseItem<Float>> it = d.getData().iterator();
		float lineRss = 0f;
		while (it.hasNext()) {
			BaseItem<Float> bi = it.next();
			lineRss = getPredictScore(weight, bi);
			lineRss -= bi.getScore();
			sum += Math.abs(lineRss);
		}
		return sum;
	}

	public float getPredictScore(float[] _weight, BaseItem<Float> bi) {
		float ret = 0f;
		for (int i = 0; i < _weight.length; i++) {
			Float f = bi.get(i);
			if (f != null) {
				ret = f * _weight[i];
			}
		}
		return ret;
	}

	public float[] normalize(float[] _weight) {

		float[] ret = new float[_weight.length];
		float sum = 0f;
		for (int i = 0; i < _weight.length; i++) {
			sum += _weight[i] * _weight[i];
		}
		sum = (float) Math.sqrt(sum);
		for (int i = 0; i < _weight.length; i++) {
			ret[i] = _weight[i] / sum;
		}
		return ret;
	}
}
