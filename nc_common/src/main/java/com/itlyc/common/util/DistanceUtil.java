package com.itlyc.common.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * @author WangHao
 * @date 2019/10/16 10:17
 */
public class DistanceUtil {

   /* public static void main(String[] args) {
        //double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);
        System.out.println(getDistanceMeter(116.661418, 40.135976, 116.661916, 40.135552));
    }*/

    public static Integer getDistanceMeter(Double latFrom, Double lonFrom, Double latTo, Double longTo) {
        GlobalCoordinates source = new GlobalCoordinates(latFrom, lonFrom);
        GlobalCoordinates target = new GlobalCoordinates(latTo, longTo);
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target);
        return new Double(geoCurve.getEllipsoidalDistance()).intValue();
    }
}
