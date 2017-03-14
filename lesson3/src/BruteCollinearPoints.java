import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        segments = new ArrayList<LineSegment>();
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        for (int i=0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("some point(s) is null");
            }
            for (int j=0; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0 && i != j) {
                    throw new IllegalArgumentException("input contains repeated point");
                }
                for (int k=0; k < points.length; k++) {
                    for (int l=0; l < points.length; l++) {
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
                            if (!segments.contains(segment)) segments.add(segment);
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
        return segments.toArray(array);
    }
}