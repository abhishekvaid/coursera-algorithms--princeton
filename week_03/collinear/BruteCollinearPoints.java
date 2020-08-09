/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BruteCollinearPoints {

    private List<LineSegment> segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException();
        else if (Arrays.stream(points).anyMatch(Objects::isNull))
            throw new IllegalArgumentException();

        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double slope1 = points[j].slopeTo(points[i]);
                        double slope2 = points[k].slopeTo(points[j]);
                        double slope3 = points[l].slopeTo(points[k]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            Point[] p_ = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(p_);
                            segments.add(new LineSegment(p_[0], p_[3]));
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
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        for (int i1 = 0; i1 < args.length; ++i1) {

            StdDraw.clear();

            // read the n points from a file
            In in = new In(args[i1]);
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
        }
    }

}
