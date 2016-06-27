package wenyang.ml.algorithm.perceptron;

import wenyang.ml.base.BaseItem;
import wenyang.ml.base.Dataum;
import wenyang.ml.base.Model;
import wenyang.ml.optimization.GradientDescent;

public class PerceptronModel extends Model {
	
	private float[] weight;

	@Override
	public void export(String fileName) {
		super.export(fileName);
	}

	@Override
	public void train(Dataum<BaseItem<Float>> d) {
		super.train(d);
	}

	@Override
	public void run() {
		GradientDescent gd = new PerceptronGradientDescent();
		gd.setWeight(weight);
		weight = gd.execute(weight, data);
	}

	@Override
	public void predict(Dataum<BaseItem<Float>> d) {
			
	}

	@Override
	public void setSize(int _size) {
		int size = _size + 1;
		super.setSize(size);
		weight = new float[size];
		initWeight();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < weight.length; i++) {
			sb.append("w");
			sb.append(i);
			sb.append(":");
			sb.append(weight[i]);
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * 初始化weight函数
	 * 
	 */
	public void initWeight() {
		for (int i = 0; i < weight.length; i++) {
			weight[i] = 1.0f;
		}
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

	public Dataum<BaseItem<Float>> getData() {
		return data;
	}

	public void setData(Dataum<BaseItem<Float>> data) {
		this.data = data;
	}
}
