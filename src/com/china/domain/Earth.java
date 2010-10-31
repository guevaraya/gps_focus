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

