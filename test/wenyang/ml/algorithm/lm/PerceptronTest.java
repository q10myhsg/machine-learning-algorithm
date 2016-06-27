package wenyang.ml.algorithm.lm;

import org.junit.Before;
import org.junit.Test;

public class PerceptronTest {

	PerceptronModelHelper lwy;

	@Before
	public void before() {
		lwy = new PerceptronModelHelper();
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
