package wenyang.ml.matri;

import java.util.Arrays;

/**
 * 矩阵的直接三角分解 ，调用示例：
 * 
 * DirectDecomposition dd = new
 * DirectDecomposition(data);//data为一个二维double数组，代替一个矩阵
 * 
 * double[][] l = dd.getL();//获取L
 * 
 * double[][] u = dd.getU();//获取U
 */
public class LUDecomposition {
	private double[][] data;
	private double[][] l;
	private double[][] u;
	private int n;

	public LUDecomposition(double[][] data) {
		if (data == null || data.length == 0 || data.length != data[0].length) {
			throw new RuntimeException("不是一个方阵");
		}
		this.data = data;
		n = data.length;
		l = new double[n][n];
		u = new double[n][n];
		countLU();
	}

	protected void countLU() {
		for (int i = 0; i < n; i++) {// 第一步，计算L的第一列和U的第一行：U1i=A1i,Li1=Ai1/U1i
			u[0][i] = data[0][i];
			l[i][0] = data[i][0] / u[0][0];
		}
		for (int r = 1; r < n; r++) {
			for (int i = r; i < n; i++) {
				u[r][i] = data[r][i] - sumLrkUki(r, i);
				l[i][r] = (data[i][r] - sumLikUkr(r, i)) / u[r][r];
			}
		}
	}

	/**
	 * 求和：Lrk*Uki 对k求和：1<=k<=r-1
	 * 
	 * @param r
	 * @param i
	 * @return
	 */
	private double sumLrkUki(int r, int i) {
		double re = 0.0;
		for (int k = 0; k < r; k++) {
			re += l[r][k] * u[k][i];
		}
		return re;
	}

	private double sumLikUkr(int r, int i) {
		double re = 0.0;
		for (int k = 0; k < r; k++) {
			re += l[i][k] * u[k][r];
		}
		return re;
	}

	public double[][] getData() {
		return data;
	}

	public double[][] getL() {
		return l;
	}

	public double[][] getU() {
		return u;
	}

	public static void main(String[] args) {
		double[][] data = { { 1, 2, 6 }, { 2, 5, 15 }, { 6, 15, 46 }, };
		LUDecomposition dd = new LUDecomposition(data);
		double[][] l = dd.getL();
		double[][] u = dd.getU();
		int n = l.length;
		System.out.println("L阵：");
		for (int i = 0; i < n; i++) {
			System.out.println(Arrays.toString(u[i]));
		}
		System.out.println("---------------------");
		System.out.println("U阵：");
		for (int i = 0; i < n; i++) {
			System.out.println(Arrays.toString(l[i]));
		}
	}
}
