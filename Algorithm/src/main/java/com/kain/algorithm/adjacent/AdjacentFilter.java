package com.kain.algorithm.adjacent;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 12/20/2016.
 */
public class AdjacentFilter {

    private static final float ANGLE_DISTANCE = 0.5F;

    public static void filterAdjacentPoints(Point2D currentLocation, List<Point2D> pointList) {
        Map<AngleOfPoint, Point2D> angleOfPointMap = getAngleOfPointMap(currentLocation, pointList);
        filterPoints(pointList, angleOfPointMap, ANGLE_DISTANCE);
    }

    public static void filterAdjacentPoints(Point2D currentLocation, List<Point2D> pointList, float angleDistance) {
        Map<AngleOfPoint, Point2D> angleOfPointMap = getAngleOfPointMap(currentLocation, pointList);
        filterPoints(pointList, angleOfPointMap, angleDistance);
    }

    /**
     * filter the points which the angle between current and previous is less than angle distance
     * and the angle of previous between the one before previous is less than angle distance only.
     * @param pointList
     * @param angleOfPointMap
     * @param angleDistance
     */
    private static void filterPoints(List<Point2D> pointList, Map<AngleOfPoint, Point2D> angleOfPointMap, float angleDistance) {
        AngleOfPoint tmp1 = null;
        AngleOfPoint tmp2 = null;
        for (Map.Entry<AngleOfPoint, Point2D> angleOfPoint : angleOfPointMap.entrySet()) {
            AngleOfPoint curr = angleOfPoint.getKey();
            if (tmp1 == null) {
                tmp1 = curr;
                continue;
            }
            if (tmp2 == null) {
                tmp2 = tmp1;
                tmp1 = curr;
                continue;
            }
            if (curr.angle - tmp1.angle >= angleDistance) {
                tmp2 = tmp1;
                tmp1 = curr;
                continue;
            } else if (tmp1.angle - tmp2.angle >= angleDistance) {
                tmp2 = tmp1;
                tmp1 = curr;
                continue;
            } else {
                if (tmp1.distance >= curr.distance) {
                    pointList.remove(angleOfPointMap.get(tmp1));
                    tmp1 = curr;
                    continue;
                } else {
                    pointList.remove(angleOfPoint.getValue());
                }
            }
        }
    }

    private static Map<AngleOfPoint, Point2D> getAngleOfPointMap(Point2D currentLocation, List<Point2D> pointList) {
        Map<AngleOfPoint, Point2D> angleOfPointMap = new TreeMap<AngleOfPoint, Point2D>(new Comparator<AngleOfPoint>() {
            @Override
            public int compare(AngleOfPoint o1, AngleOfPoint o2) {
                return Double.compare(o1.angle, o2.angle);
            }
        });
        for (Point2D destPoint : pointList) {
            double angle = getAngle(currentLocation, destPoint);
            angleOfPointMap.put(new AngleOfPoint(angle, currentLocation.distance(destPoint)), destPoint);
        }
        return angleOfPointMap;
    }

    static class AngleOfPoint {
        double angle;

        double distance;

        AngleOfPoint(double angle, double distance) {
            this.angle = angle;
            this.distance = distance;
        }
    }

    private static double getAngle(Point2D currentPoint, Point2D destPoint) {
        Point2D point1 = new Point();
        point1.setLocation(destPoint.getX() - currentPoint.getX(), destPoint.getY() - currentPoint.getY());
        Point2D point2 = new Point();
        point2.setLocation(0, 2);

        double x1 = latlon2x(point1);
        double y1 = latlon2y(point1);
        double z1 = latlon2z(point1);

        double x2 = latlon2x(point2);
        double y2 = latlon2y(point2);
        double z2 = latlon2z(point2);

        double product = x1 * x2 + y1 * y2 + z1 * z2;
        double modular = Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2))
                * Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2) + Math.pow(z2, 2));

        return Math.acos(product / modular);
    }

    private static double latlon2x(Point2D point) {
        return Math.cos(point.getY()) * Math.cos(point.getX());
    }

    private static double latlon2y(Point2D point) {
        return Math.cos(point.getY()) * Math.sin(point.getX());
    }

    private static double latlon2z(Point2D point) {
        return Math.sin(point.getY());
    }


}
