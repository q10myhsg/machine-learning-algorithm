package wenyang.ml.optimization;

import java.util.Random;

import wenyang.ml.base.BaseItem;

public class StochasticGradientDescent  extends GradientDescent{

	protected float[] computePartialDerivative() {
		float[] partial_derivative = new float[weight.length];
		for (int j = 0; j < weight.length; j++)// 遍历，对每个theta求偏导数
		{
			float f = 0f;
			int size = d.getData().size();
			Random r = new Random();
			int index = r.nextInt(size);
			BaseItem<Float> bi = d.getData().get(index);
			float temp = 0f;			
				if (bi.get(j) != null) {
					temp = getPredictScore(weight, bi);
					temp -= bi.getScore();
					temp *= bi.get(j);
				}
				f += temp;
			partial_derivative[j] = f;
		}
		return partial_derivative;
	}
}
