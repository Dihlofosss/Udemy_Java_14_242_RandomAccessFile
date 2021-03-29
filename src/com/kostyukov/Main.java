package com.kostyukov;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main
{
	private static Locations locations = new Locations();
	
	public static void main(String[] args) throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		Map<String, String> dictionary = new HashMap<>();
		dictionary.put("QUIT", "Q");
		dictionary.put("NORTH", "N");
		dictionary.put("EAST", "E");
		dictionary.put("SOUTH", "S");
		dictionary.put("WEST", "W");
		
		Location currentLoc = locations.getLocation(1);
        while(true) {
            System.out.println(currentLoc.getDescription());
            if(currentLoc.getLocationID() == 0) {
                break;
            }

            Map<String, Integer> exits = currentLoc.getExits();
            System.out.print("Available exits are ");
            for(String exit: exits.keySet()) {
                System.out.print(exit + ", ");
            }
            System.out.println();

            String direction = scanner.nextLine().toUpperCase();
			if (direction.length() > 1)
			{
				String[] words = direction.split(" ");
				for (String word: words)
				{
					if (dictionary.containsKey(word))
					{
						direction = dictionary.get(word);
						break;
					}
				}
			}
			
            if(exits.containsKey(direction)) {
                currentLoc = locations.getLocation(currentLoc.getExits().get(direction));

            } else {
                System.out.println("You cannot go in that direction");
            }
        }
        
        locations.close();
	}
}
