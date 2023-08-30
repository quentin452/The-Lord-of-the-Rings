package lotr.common.fac;

import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRWaypoint;

public class LOTRControlZone {
	public double mapX;
	public double mapY;
	public int radius;
	public int xCoord;
	public int zCoord;
	public int radiusCoord;
	public long radiusCoordSq;

	public LOTRControlZone(double x, double y, int r) {
		mapX = x;
		mapY = y;
		radius = r;
		xCoord = LOTRWaypoint.mapToWorldX(mapX);
		zCoord = LOTRWaypoint.mapToWorldZ(mapY);
		radiusCoord = LOTRWaypoint.mapToWorldR(radius);
		radiusCoordSq = (long) radiusCoord * radiusCoord;
	}

	public LOTRControlZone(LOTRAbstractWaypoint wp, int r) {
		this(wp.getX(), wp.getY(), r);
	}

	public boolean intersectsWith(LOTRControlZone other, int extraMapRadius) {
		double dx = other.xCoord - xCoord;
		double dz = other.zCoord - zCoord;
		double distSq = dx * dx + dz * dz;
		double r12 = radiusCoord + other.radiusCoord + LOTRWaypoint.mapToWorldR(extraMapRadius * 2);
		double r12Sq = r12 * r12;
		return distSq <= r12Sq;
	}

	public boolean inZone(double x, double y, double z, int extraMapRange) {
		double dx = x - xCoord;
		double dz = z - zCoord;
		double distSq = dx * dx + dz * dz;
		if (extraMapRange == 0) {
			return distSq <= radiusCoordSq;
		}
		int checkRadius = LOTRWaypoint.mapToWorldR(radius + extraMapRange);
		long checkRadiusSq = (long) checkRadius * checkRadius;
		return distSq <= checkRadiusSq;
	}
}
