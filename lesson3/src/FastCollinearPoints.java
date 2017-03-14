import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        segments = new ArrayList<LineSegment>();
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        for (int i=0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("some point(s) is null");

            for (int j=0; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("input contains repeated point");
                }
            }
        }
        for (int i=0; i < points.length; i++) {
            Point[] copy = new Point[points.length];
            for (int j=0; j < points.length; j++) copy[j] = points[j];

            Arrays.sort(copy, points[i].slopeOrder());
            int points_in_line_counter = 2;
            double prev_slope = points[i].slopeTo(copy[0]);
            Point min = points[i];
            int start_index = 0;
            if (points[i].compareTo(copy[start_index]) == 0) {
                start_index = 1;
            }
            Point max = copy[start_index];
            Point buf;
            if (min.compareTo(max) == +1) {
                buf = max;
                max = min;
                min = buf;
            }

            LineSegment cur_ls;
            for (int j=start_index+1; j < points.length; j++) {
                if (points[i].compareTo(copy[j]) == 0) continue;

                if (copy[j].compareTo(min) == -1) min = copy[j];
                if (copy[j].compareTo(max) == +1) max = copy[j];

                double cur_slope = points[i].slopeTo(copy[j]);
                if (cur_slope == prev_slope) {
                    cur_ls = new LineSegment(min, max);
                    points_in_line_counter++;
                    if (points_in_line_counter == 4) {
                        segments.add(cur_ls);
                    } else if (points_in_line_counter > 4) {
                        segments.remove(segments.size());
                        segments.add(cur_ls);
                    }
                } else {
                    points_in_line_counter = 1;
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] array = new LineSegment[segments.size()];
        return segments.toArray(array);
    }

    public static void main(String[] args) {
        Point p1 = new Point(0,0);
        Point p2 = new Point(1,1);
        Point p3 = new Point(2,2);
        Point p4 = new Point(4,4);
        Point p5 = new Point(1,2);
        ArrayList<Point> arr = new ArrayList<Point>();
        arr.add(p1);
        arr.add(p5);
        arr.add(p2);
        arr.add(p3);
        arr.add(p4);

//        arr.remove(p2);
        Point p = new Point(1,1);
        arr.remove(p);
//        Arrays.sort(arr2, p1.slopeOrder());
//        for (int i=0; i < arr.size(); i++) {
//            System.out.println(arr.get(i));
//        }
    }
}
