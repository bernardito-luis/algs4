import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        Arrays.sort(points);
        segments = new ArrayList<LineSegment>();
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("some point(s) is null");

            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("input contains repeated point");
                }
            }
        }
        for (int i = 0; i < points.length; i++) {
            Point[] copy = new Point[points.length];
            for (int j = 0; j < points.length; j++) copy[j] = points[j];

            Arrays.sort(copy, points[i].slopeOrder());
            int points_in_line_counter = 2;
            Point min = points[i];
            int startIndex = 0;
            if (points[i].compareTo(copy[startIndex]) == 0) {
                startIndex = 1;
            }
            double skipSlope = points[i].slopeTo(copy[startIndex]);
            for (int j = startIndex+1; j < copy.length; j++) {
                skipSlope = points[i].slopeTo(copy[j]);
                if (points[i].compareTo(copy[j]) != -1) {
                    continue;
                }

                if (points[i].compareTo(copy[j]) == 0) {
                    startIndex = j;
                }
            }
            double prevSlope = points[i].slopeTo(copy[startIndex]);
            Point max = copy[startIndex];
            Point buf;
//            if (min.compareTo(max) == +1) {
//                buf = max;
//                max = min;
//                min = buf;
//            }

            LineSegment curLS;
            for (int j = startIndex+1; j < points.length; j++) {
                if (points[i].compareTo(copy[j]) == 0) continue;

                if (points[i].compareTo(copy[j]) != -1) {
                    points_in_line_counter = 1;
                    continue;
                }
                if (copy[j].compareTo(min) == -1) min = copy[j];
                if (copy[j].compareTo(max) == +1) max = copy[j];

                double curSlope = points[i].slopeTo(copy[j]);
                if (curSlope == prevSlope) {
                    points_in_line_counter++;
                    if (points_in_line_counter == 4) {
                        curLS = new LineSegment(min, max);
                        segments.add(curLS);
                    } else if (points_in_line_counter > 4) {
                        curLS = new LineSegment(min, max);
                        segments.remove(segments.size()-1);
                        segments.add(curLS);
                    }
                } else {
                    points_in_line_counter = 2;
                    prevSlope = curSlope;
                    min = points[i];
                    max = copy[j];
                    if (min.compareTo(max) == +1) {
                        buf = max;
                        max = min;
                        min = buf;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
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
