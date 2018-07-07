package it.unibo.entity;

import java.util.List;

/**
 *
 * @author Simone Rondelli - simone.rondelli2@studio.it.unibo.it
 *
 */
public class Leg {

	private List<Step> steps;
	private GeoPoint startLocation;
	private GeoPoint endLocation;
	private String startAddress;
	private String endAddress;
	private int distance;
	private int duration;

	public Leg() {
	}

	public List<Step> getSteps() {
        return this.steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public GeoPoint getStartLocation() {
        return this.startLocation;
	}

	public void setStartLocation(GeoPoint startLocation) {
		this.startLocation = startLocation;
	}

	public GeoPoint getEndLocation() {
        return this.endLocation;
	}

	public void setEndLocation(GeoPoint endLocation) {
		this.endLocation = endLocation;
	}

	public int getDistance() {
        return this.distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDuration() {
        return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getEndAddress() {
        return this.endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getStartAddress() {
        return this.startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	@Override
	public String toString() {
        return "Leg [steps=" + this.steps + ", startLocation=" + this.startLocation + ", endLocation=" + this.endLocation + ", distance=" + this.distance + ", duration=" + this.duration + "]";
	}

}
