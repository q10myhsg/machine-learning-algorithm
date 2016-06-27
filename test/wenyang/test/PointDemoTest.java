package wenyang.test;

import org.junit.Test;

public class PointDemoTest {
	PointDemo pd = new PointDemo();
	
	@Test
	public void testRun() {
		
		long start = System.currentTimeMillis();
		int size=5;
		for(int i=0;i<10000000;i++){
			pd.run(size);
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

}
