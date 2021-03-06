package com.kostyukov;

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location>
{
	private static Map<Integer, Location> locations = new LinkedHashMap<>();
	private static Map<Integer, IndexRecord> index = new LinkedHashMap<>();
	
	private static RandomAccessFile ra;
	
	public static void main(String[] args) throws IOException
	{
		try (RandomAccessFile rao = new RandomAccessFile("res/locations_rnd.dat", "rwd"))
		{
			
			rao.writeInt(locations.size());
			int indexSize = locations.size() * 3 * Integer.BYTES;
			int locationStart = (int) (indexSize + rao.getFilePointer() + Integer.BYTES);
			rao.writeInt(locationStart);
			
			long indexStart = rao.getFilePointer();
			
			int startPointer = locationStart;
			rao.seek(startPointer);
			
			for (Location location : locations.values())
			{
				rao.writeInt(location.getLocationID());
				rao.writeUTF(location.getDescription());
				StringBuilder string = new StringBuilder();
				for (String direction : location.getExits().keySet())
				{
					if (direction.equalsIgnoreCase("Q"))
					{
						continue;
					}
					string.append(direction);
					string.append(",");
					string.append(location.getExits().get(direction));
					string.append(",");
				}
				rao.writeUTF(string.toString());
				
				IndexRecord indexRecord = new IndexRecord(startPointer, (int) rao.getFilePointer() - startPointer);
				index.put(location.getLocationID(), indexRecord);
				
				startPointer = (int) rao.getFilePointer();
			}
			
			rao.seek(indexStart);
			for (Integer locationID : index.keySet())
			{
				rao.writeInt(locationID);
				rao.writeInt(index.get(locationID).getStartByte());
				rao.writeInt(index.get(locationID).getLength());
			}
		}
		
		// 1. This first four bytes will contain the number of locations (bytes 0-3)
		// 2. The next four bytes will contain the start offset of the locations section (bytes 4-7)
		// 3. The next section of the file will contain the index (the index is 1692 bytes long.  It will start at byte 8 and end at byte 1699
		// 4. The final section of the file will contain the location records (the data). It will start at byte 1700
	}
	
	static
	{
		try
		{
			ra = new RandomAccessFile("res/locations_rnd.dat", "rwd");
			int numLocations = ra.readInt();
			long locationStartPointer = ra.readInt();

			while (ra.getFilePointer() < locationStartPointer)
			{
				int locationID = ra.readInt();
				int locationStart = ra.readInt();
				int locationLength = ra.readInt();

				IndexRecord record = new IndexRecord(locationStart, locationLength);
				index.put(locationID,record);
			}
		}
	
//		try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("res/locations.loc"))))
//		{
//			while (true)
//			{
//				Location location = (Location) inputStream.readObject();
//				System.out.println("Found location: " + location.getDescription());
//				locations.put(location.getLocationID(), location);
//			}
//		}
//		catch (ClassNotFoundException | EOFException e)
//		{
//			e.printStackTrace();
//		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Location getLocation(int locationID) throws IOException
	{
		IndexRecord record = index.get(locationID);
		ra.seek(record.getStartByte());
		int id = ra.readInt();
		String description = ra.readUTF();
		String exits = ra.readUTF();
		String[] exitPart = exits.split(",");
		Location location = new Location(locationID, description);
		
		if (locationID != 0)
		{
			for (short i = 0; i < exitPart.length; i++)
			{
				String direction = exitPart[i];
				int destination = Integer.parseInt(exitPart[++i]);
				location.addExit(direction, destination);
			}
		}
		return location;
	}
	
	@Override
	public int size()
	{
		return locations.size();
	}
	
	@Override
	public boolean isEmpty()
	{
		return locations.isEmpty();
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		return locations.containsKey(key);
	}
	
	@Override
	public boolean containsValue(Object value)
	{
		return locations.containsValue(value);
	}
	
	@Override
	public Location get(Object key)
	{
		return locations.get(key);
	}
	
	@Override
	public Location put(Integer key, Location value)
	{
		return locations.put(key, value);
	}
	
	@Override
	public Location remove(Object key)
	{
		return locations.remove(key);
	}
	
	@Override
	public void putAll(Map<? extends Integer, ? extends Location> m)
	{
	
	}
	
	@Override
	public void clear()
	{
		locations.clear();
	}
	
	@Override
	public Set<Integer> keySet()
	{
		return locations.keySet();
	}
	
	@Override
	public Collection<Location> values()
	{
		return locations.values();
	}
	
	@Override
	public Set<Entry<Integer, Location>> entrySet()
	{
		return locations.entrySet();
	}
	
	public void close() throws IOException
	{
		ra.close();
	}
}