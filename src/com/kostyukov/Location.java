package com.kostyukov;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Location implements Serializable
{
	private final int locationID;
	private final String description;
	private final Map<String, Integer> exits;
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public Location(int locationID, String description) {
		this(locationID, description, new LinkedHashMap<>());
		this.exits.put("Q", 0);
	}
	
	public Location(int locationID, String description, Map<String, Integer> exits)
	{
		this.locationID = locationID;
		this.description = description;
		this.exits = exits;
		this.exits.put("Q", 0);
	}
	
	public void addExit(String direction, int location) {
		exits.put(direction, location);
	}
	public int getLocationID() {
		return locationID;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Map<String, Integer> getExits() {
		return new LinkedHashMap<>(exits);
	}
}
