package com.china.domain;

/* ------------------------------------------------------------------------- */
/* Copyright (C) 2010 guevara.ya@gmail.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

/* ------------------------------------------------------------------------- */
public class Earth {
	public static int LEN = 7;
	public static String SPLIT = "\\s+";
	private String nameTag;
	private Point p1,p2;
	private double dis;
	private double bear;
	
	public Earth(String str,Point point1,Point point2,double distance,double bearing){
		nameTag = str;
		p1 = point1;
		p2 = point2;
		dis = distance;
		bear = bearing;
	};
	public Earth(String str,Point point1,Point point2,String distance,String bearing){
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

