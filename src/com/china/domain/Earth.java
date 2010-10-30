package com.china.domain;

public class Earth {
	public static int LEN = 7;
	private String nameTag;
	private Point p1,p2;
	private double dis;
	private double bear;
	
	Earth(String str,Point point1,Point point2,double distance,double bearing){
		nameTag = str;
		p1 = point1;
		p2 = point2;
		dis = distance;
		bear = bearing;
	};
	Earth(String str,Point point1,Point point2,String distance,String bearing){
		nameTag = str;
		p1 = point1;
		p2 = point2;
		dis = Double.parseDouble(distance);
		bear = Double.parseDouble(bearing);
	};
	public String getName(){return nameTag;};
	public Point getPointOne(){ return p1;	};
	public Point getPointTwo(){ return p2;	};
	public double getDistance(){
		dis = p1.distance(p2);
		return dis;
	};
	public double getBearing(){
		bear = p1.bearing(p2);
		return bear;};
	};
class Point {
	double lat;
	double lon;
	private double RADIUS = 6371;// radius of earth 6,371km
	Point(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	Point(String lat, String lon) {
		this.lat = Double.parseDouble(lat);
		this.lon = Double.parseDouble(lon);
	}
	
	public double distance(Point p) {
		double lat1 = Math.toRadians(lat);
		double lon1 = Math.toRadians(lon);
		double lat2 = Math.toRadians(p.lat);
		double lon2 = Math.toRadians(p.lon);

		double tmp = Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1)
				* Math.cos(lat2) * Math.cos(lon2 - lon1);
		double dis = RADIUS * (Math.acos(tmp));
		return dis;
	}

	public double bearing(Point p) {
		double lat1 = Math.toRadians(lat);
		double lon1 = Math.toRadians(lon);
		double lat2 = Math.toRadians(p.lat);
		double lon2 = Math.toRadians(p.lon);

		double y = Math.sin(lon2 - lon1) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(lon2 - lon1);
		double bear = Math.toDegrees(Math.atan2(y, x));
		return bear;
	}

	public Point destination(Double dis, Double bear) {
		double lat1 = Math.toRadians(lat);
		double lon1 = Math.toRadians(lon);
		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dis / RADIUS))
				+ Math.cos(lat1) * Math.sin(dis / RADIUS) * Math.cos(bear);
		double lon2 = lon1
				+ Math.atan2(Math.sin(bear) * Math.sin(dis / RADIUS)
						* Math.cos(lat1), Math.cos(dis / RADIUS)
						- Math.sin(lat1) * Math.sin(lat2));
		return new Point(Math.toDegrees(lat2),Math.toDegrees(lon2));
	}
};
