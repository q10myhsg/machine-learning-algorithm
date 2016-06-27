package wenyang.ml.algorithm.lm;

import org.junit.Before;
import org.junit.Test;

import wenyang.ml.algorithm.decisiontree.DecisionTreeHelper;

public class DecisonTreeTest {

	DecisionTreeHelper lwy;

	@Before
	public void before() {
		lwy = new DecisionTreeHelper();
		lwy.loadData();
		
	}

	@Test
	public void test() {		
		lwy.running();	
	}

	

}
