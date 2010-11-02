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
public class Point {
	double lat;
	double lon;
	private double RADIUS = 6371;// radius of earth 6,371km
	public Point(Double lat, Double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	public Point(String lat, String lon) {
		this.lat = Double.parseDouble(lat);
		this.lon = Double.parseDouble(lon);
	}
	public double getLat(){return lat;};
	public double getLon(){return lon;};
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