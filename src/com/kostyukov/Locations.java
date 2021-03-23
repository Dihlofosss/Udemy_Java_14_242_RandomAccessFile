package com.kostyukov;

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location>
{
	private static Map<Integer, Location> locations = new LinkedHashMap<>();
	
	public static void main(String[] args) throws IOException
	{
//		try (DataOutputStream gameData = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("res/locations.dat"))))
//		{
//			for (Location location : locations.values())
//			{
//				gameData.writeInt(location.getLocationID());            //write location number
//				gameData.writeUTF(location.getDescription());           //write location description
//				gameData.writeInt(location.getExits().size() - 1);   //write number of exits
//				for (String direction : location.getExits().keySet())
//				{
//					if (direction.equals("Q"))
//					{
//						continue;
//					}
//					gameData.writeUTF(direction);
//					gameData.writeInt(location.getExits().get(direction));
//				}
//			}
//		}
		try(ObjectOutputStream gameData = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("res/locations.loc"))))
		{
			for(Location location : locations.values())
			{
				gameData.writeObject(location);
			}
		}
//		try (BufferedWriter locFile = new BufferedWriter(new FileWriter("res/locations.txt"));
//		     BufferedWriter dirFile = new BufferedWriter(new FileWriter("res/directions.txt")))
//		{
//			for (Location locations : locations.values())
//			{
//				locFile.write(locations.getLocationID() + "," + locations.getDescription() + "\n");
//				for (String direction : locations.getExits().keySet())
//				{
//					if (direction.equalsIgnoreCase("Q"))
//					{
//						continue;
//					}
//					dirFile.write(locations.getLocationID() + "," + direction + "," + locations.getExits().get(direction) + "\n");
//				}
//			}
//		}
	}
	
	static
	{
//		try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("res/locations.dat"))))
//		{
//			while (inputStream.available() > 0)
//			{
//				int loc = inputStream.readInt();
//				String description = inputStream.readUTF();
//				HashMap<String, Integer> exits = new LinkedHashMap<>();;
//				short exitsAmount = (short) inputStream.readInt();
//				for (short i = 0; i < exitsAmount; i++)
//				{
//					exits.put(inputStream.readUTF(),inputStream.readInt());
//				}
//				locations.put(loc, new Location(loc, description, exits));
//			}
//		}
		try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("res/locations.loc"))))
		{
			while (true)
			{
				Location location = (Location) inputStream.readObject();
				System.out.println("Found location: " + location.getDescription());
				locations.put(location.getLocationID(), location);
			}
		}
		catch (ClassNotFoundException | EOFException e)
		{
			e.printStackTrace();
		}
		//		try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("res/locations_big.txt"))))
//		{
//			scanner.useDelimiter(",");
//			String input;
//			while (scanner.hasNextLine())
//			{
//				int loc = scanner.nextInt();
//				scanner.skip(scanner.delimiter());
//				String description = scanner.nextLine();
//				System.out.println("Imported location: " + loc + ": " + description);
//				locations.put(loc, new Location(loc, description));
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//		try (BufferedReader reader = new BufferedReader(new FileReader("res/directions_big.txt")))
//		{
//			String input;
//			while ((input = reader.readLine()) != null)
//			{
//				String[] data = input.split(",");
//				int loc = Integer.parseInt(data[0]);
//				String direction = data[1];
//				int destination = Integer.parseInt(data[2]);
//
//				System.out.println(loc + ": " + direction + ": " + destination);
//				Location location = locations.get(loc);
//				location.addExit(direction, destination);
//			}
//		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
}