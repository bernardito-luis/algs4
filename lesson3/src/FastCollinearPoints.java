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
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("some point(s) is null");

            for (int j = 0; j < points.length; j++) {
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
            double prevSlope = points[i].slopeTo(copy[0]);
            Point min = points[i];
            int startIndex = 0;
            if (points[i].compareTo(copy[startIndex]) == 0) {
                startIndex = 1;
            }
            Point max = copy[startIndex];
            Point buf;
            if (min.compareTo(max) == +1) {
                buf = max;
                max = min;
                min = buf;
            }

            LineSegment curLS;
            for (int j = startIndex+1; j < points.length; j++) {
                if (points[i].compareTo(copy[j]) == 0) continue;

                if (copy[j].compareTo(min) == -1) min = copy[j];
                if (copy[j].compareTo(max) == +1) max = copy[j];

                double curSlope = points[i].slopeTo(copy[j]);
                if (curSlope == prevSlope) {
                    curLS = new LineSegment(min, max);
                    points_in_line_counter++;
                    if (points_in_line_counter == 4) {
                        segments.add(curLS);
                    } else if (points_in_line_counter > 4) {
                        segments.remove(segments.size());
                        segments.add(curLS);
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
    }
}
