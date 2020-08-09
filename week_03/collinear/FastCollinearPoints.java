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

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {

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

        List<LineSegment> segmentList = new ArrayList<>();

        for (Point origin : points) {
            Arrays.sort(pointsCopy, (p1, p2) -> {
                int cmp = origin.slopeOrder().compare(p1, p2);
                return cmp == 0 ? p1.compareTo(p2) : cmp;
            });
            for(int i = 1, j = 2 ; i < pointsCopy.length ; i = j) {
                double slope = pointsCopy[i].slopeTo(origin);
                while (j < pointsCopy.length && pointsCopy[j].slopeTo(origin) == slope)
                    j++;
                if (j - i >= 3 && origin.compareTo(pointsCopy[i]) < 0)
                    segmentList.add(new LineSegment(origin, pointsCopy[j-1]));
            }
        }
        segments = segmentList.toArray(new LineSegment[0]);
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
            FastCollinearPoints collinear = new FastCollinearPoints(points);

            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }

            StdDraw.show();
        }
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    public int numberOfSegments() {
        return segments.length;
    }

}
