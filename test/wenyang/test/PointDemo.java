package wenyang.test;

import java.util.ArrayList;
import java.util.Random;

/**
 * 判断一个点，是否在一个多边形区域内
 */
public class PointDemo {

	public void run(int size) {
		double px = 113.705835;
		double py = 34.787479;
		ArrayList<Double> polygonXA = new ArrayList<Double>(size);
		ArrayList<Double> polygonYA = new ArrayList<Double>(size);
		for (int i = 0; i < size; i++) {
			Random rand = new Random();
			polygonXA.add(rand.nextDouble() + 113);
			polygonYA.add(rand.nextDouble() + 34);
		}
		isPointInPolygon(px, py, polygonXA, polygonYA);
	}

	public boolean isPointInPolygon(double px, double py, ArrayList<Double> polygonXA, ArrayList<Double> polygonYA) {
		boolean isInside = false;
		// double ESP = 1e-9;
		double ESP = 0;
		int count = 0;
		double linePoint1x;
		double linePoint1y;
		double linePoint2x = 180;
		double linePoint2y;

		linePoint1x = px;
		linePoint1y = py;
		linePoint2y = py;

		for (int i = 0; i < polygonXA.size() - 1; i++) {
			double cx1 = polygonXA.get(i);
			double cy1 = polygonYA.get(i);
			double cx2 = polygonXA.get(i + 1);
			double cy2 = polygonYA.get(i + 1);
			if (isPointOnLine(px, py, cx1, cy1, cx2, cy2)) {
				return true;
			}
			if (Math.abs(cy2 - cy1) < ESP) {
				continue;
			}

			if (isPointOnLine(cx1, cy1, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
				if (cy1 > cy2)
					count++;
			} else if (isPointOnLine(cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
				if (cy2 > cy1)
					count++;
			} else if (isIntersect(cx1, cy1, cx2, cy2, linePoint1x, linePoint1y, linePoint2x, linePoint2y)) {
				count++;
			}
		}
		// System.out.println(count);
		if (count % 2 == 1) {
			isInside = true;
		}

		return isInside;
	}

	public double Multiply(double px0, double py0, double px1, double py1, double px2, double py2) {
		return ((px1 - px0) * (py2 - py0) - (px2 - px0) * (py1 - py0));
	}

	public boolean isPointOnLine(double px0, double py0, double px1, double py1, double px2, double py2) {
		boolean flag = false;
		double ESP = 1e-9;
		if ((Math.abs(Multiply(px0, py0, px1, py1, px2, py2)) < ESP) && ((px0 - px1) * (px0 - px2) <= 0)
				&& ((py0 - py1) * (py0 - py2) <= 0)) {
			flag = true;
		}
		return flag;
	}

	public boolean isIntersect(double px1, double py1, double px2, double py2, double px3, double py3, double px4,
			double py4) {
		boolean flag = false;
		double d = (px2 - px1) * (py4 - py3) - (py2 - py1) * (px4 - px3);
		if (d != 0) {
			double r = ((py1 - py3) * (px4 - px3) - (px1 - px3) * (py4 - py3)) / d;
			double s = ((py1 - py3) * (px2 - px1) - (px1 - px3) * (py2 - py1)) / d;
			if ((r >= 0) && (r <= 1) && (s >= 0) && (s <= 1)) {
				flag = true;
			}
		}
		return flag;
	}

}
