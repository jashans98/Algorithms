package Week3.A3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Jashan Shewakramani
 * Fast sorting based solution for collinear points >= 4
 */
public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] result;

    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        this.points = points;
        result = getLineSegments();
    }

    public int numberOfSegments() {
        return result.clone().length;
    }

    public LineSegment[] segments() {
        return result.clone();
    }

    private void validatePoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("argument to constructor cannot be null");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("Array contains a null point");
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Array contains duplicate points");
                }
            }
        }
    }

    private LineSegment[] getLineSegments() {
        HashMap<String, LineSegment> resultMap = new HashMap<>();
        Point[] clone = points.clone();
        for (int i = 0; i < points.length; i++) {
            Point p = clone[i];
            Arrays.sort(points, p.slopeOrder());
            double currSlope = p.slopeTo(points[0]);
            ArrayList<Point> pList = new ArrayList<>();
            pList.add(p);
            for (int j = 0; j < points.length; j++) {
                if (p.slopeTo(points[j]) == currSlope)
                    pList.add(points[j]);

                if (p.slopeTo(points[j]) != currSlope || j == points.length - 1){
                    currSlope = p.slopeTo(points[j]);
                    if (pList.size() >= 4) {
                        LineSegment toAdd = clean(pList);
                        if (!resultMap.containsKey(toAdd.toString()))
                            resultMap.put(toAdd.toString(), toAdd);
                    }
                    pList = new ArrayList<>();
                    pList.add(p);
                    pList.add(points[j]);
                }
            }
        }

        return resultMap.values().toArray(new LineSegment[resultMap.size()]);
    }

    // The running time of this can be improved a little
    private LineSegment clean(ArrayList<Point> pList) {
        pList.sort(Point::compareTo);
        Point a = pList.get(0);
        Point b = pList.get(pList.size() - 1);
        return new LineSegment(a, b);
    }

    public static void main(String[] args) {

        Point[] k = new Point[15];
        k[0] = new Point(123, 2331);
        for (int i = 1; i < 5; i++)
            k[i] = new Point(4+i, i);
        for (int i = 5; i < 9; i++)
            k[i] = new Point(7+i, 2 *i);
        for (int i = 9; i < 15; i++)
            k[i]= new Point(2+i, 3 * i);


        LineSegment[] res = new FastCollinearPoints(k).segments();
        System.out.println(res.length);
        for (LineSegment l : res) {
            System.out.println(l);
        }
    }
}
