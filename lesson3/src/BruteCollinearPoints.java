import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        segments = new ArrayList<LineSegment>();
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("some point(s) is null");
            }
            for (int j = i; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0 && i != j) {
                    throw new IllegalArgumentException("input contains repeated point");
                }
                if (i == j) continue;
                for (int k = j; k < points.length; k++) {
                    if (i == k || j == k) continue;
                    for (int l = k; l < points.length; l++) {
                        if (i == l || j == l || k == l) continue;
                        double ij_slope = points[i].slopeTo(points[j]);
                        double jk_slope = points[j].slopeTo(points[k]);
                        double kl_slope = points[k].slopeTo(points[l]);

                        if (ij_slope == jk_slope && jk_slope == kl_slope) {
                            Point min = points[i];
                            Point max = points[i];
                            if (points[j].compareTo(min) == -1) {
                                min = points[j];
                            }
                            if (points[j].compareTo(max) == +1) {
                                max = points[j];
                            }
                            if (points[k].compareTo(min) == -1) {
                                min = points[k];
                            }
                            if (points[k].compareTo(max) == +1) {
                                max = points[k];
                            }
                            if (points[l].compareTo(min) == -1) {
                                min = points[l];
                            }
                            if (points[l].compareTo(max) == +1) {
                                max = points[l];
                            }
                            LineSegment segment = new LineSegment(min, max);
                            segments.add(segment);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] array = new LineSegment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            array[i] = segments.get(i);
        }
        return array;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
        LineSegment[] segms = collinear.segments();
        System.out.println(segms);
        System.out.println("Success!!");
    }
}
