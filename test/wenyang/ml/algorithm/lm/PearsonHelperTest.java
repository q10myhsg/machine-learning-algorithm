package wenyang.ml.algorithm.lm;

import org.junit.Test;

public class PearsonHelperTest {

	PearsonHelper  ph= new PearsonHelper();
	
	@Test
	public void testLoadData() {
		ph.loadData();
	}

	@Test
	public void testRunning() {
		ph.loadData();
		ph.running();
	}

}
