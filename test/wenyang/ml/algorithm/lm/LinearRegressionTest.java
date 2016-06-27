package wenyang.ml.algorithm.lm;

import org.junit.Before;
import org.junit.Test;

public class LinearRegressionTest {

	LinearRegressionHelper lwy;

	@Before
	public void before() {
		lwy = new LinearRegressionHelper();
		lwy.loadData();
	}

	@Test
	public void test() {		
		lwy.running();	
	}

	
	@Test
	public void loadDataTest() {
		lwy.loadData();
	}

}
