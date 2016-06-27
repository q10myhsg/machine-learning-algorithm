package wenyang.ml.utils;

public interface ICoefficent {	
	
	
	/**
	 * 分析score与feature的关系 设置is中包括-1 
	 * linear 中0表示b所以都是0
	 *  
	 * */
	String getCoefficent(int...is);

}
