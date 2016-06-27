package wenyang.ml.algorithm.perceptron;

import java.util.Iterator;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;
import wenyang.ml.optimization.GradientDescent;

public class PerceptronGradientDescent extends GradientDescent {
	public PerceptronGradientDescent() {
	}

	public float[] execute(float[] _weight, Dataum<BaseItem<Float>> _d) {
		this.d = _d;
		rss = new float[iteration_max];
		int iteration = 0;
		while (iteration < iteration_max) {
			float[] partial_derivative = computePartialDerivative();// 偏导数
			weight = getNextWeight(partial_derivative);
			// weight = normalizeWeight();
			rss[iteration] = this.getRss();
			System.out.println("=================");
			System.out.println("iteration:" + iteration);
			System.out.println("rss:" + rss[iteration]);
			System.out.println("model:\r\n" + this.toString());
			int errorCount =0;
			if (iteration > 1) {				
				Iterator<BaseItem<Float>> it = d.getData().iterator();
				while (it.hasNext()) {
					if (!isRight(weight, it.next()))
						errorCount++;
				}
				if(errorCount==0){
					iteration=iteration_max;
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
				temp = getPredictScore(this.weight, bi) * bi.getScore();
				if (temp < 0) {
					if (bi.get(j) != null) {
						Float featureValue = bi.get(j);
						if (featureValue != null && featureValue != 0) {
							temp = bi.getScore() * (-1) * featureValue;
							f += temp;
						}
					}
				}
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
			next[i] = weight[i] + alpha * partialDerivative[i];
		}
		return next;
	}






	public boolean isRight(float[] _weight, BaseItem<Float> bi) {
		boolean ret = false;

		float f = getPredictScore(_weight, bi);
		f *= bi.getScore();
		if (f > 0)
			ret = true;
		else
			ret = false;
		return ret;
	}
}
