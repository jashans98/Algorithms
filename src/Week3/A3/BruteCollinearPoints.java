package Week3.A3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jashan Shewakramani
 * Description: Brute force line collinear points >=4
 */
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private Point[] points;
    private HashMap<Double, ArrayList<Point>> gradientMap = new HashMap<>();

    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);
        this.points = points;
        segments = getLineSegments();
    }

    public int numberOfSegments() {
        return segments.clone().length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    private LineSegment[] getLineSegments() {
        ArrayList<LineSegment> result = new ArrayList<>();
        for (int i = 0;  i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    ArrayList<Point> pts = new ArrayList<>();
                    if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                        pts.add(points[i]);
                        pts.add(points[j]);
                        pts.add(points[k]);
                    }
                    for (int l = k+1; l < points.length; l++) {
                        if (points[i].slopeTo(points[l]) == points[i].slopeTo(points[j]))
                            pts.add(points[l]);
                    }
                    if (pts.size() >= 4)
                        result.add(getMaximizingSegment(pts));
                }
            }
        }

        LineSegment[] res = new LineSegment[result.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = result.get(i);

        return res;
    }

    private LineSegment getMaximizingSegment(ArrayList<Point> pts) {
        pts.sort(Point::compareTo);
        return new LineSegment(pts.get(0), pts.get(pts.size() - 1));
    }


    private void validatePoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("Points cannot be null");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new NullPointerException("Array contains a null point");
            for (int j = i+1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Array contains repeated point");
        }

    }

    public static void main(String[] args) {
        Point[] t = new Point[5];
        for (int i = 0; i < t.length; i++)
            t[i] = new Point(i, i);

        BruteCollinearPoints b = new BruteCollinearPoints(t);
        System.out.println(b.numberOfSegments());
    }
}
