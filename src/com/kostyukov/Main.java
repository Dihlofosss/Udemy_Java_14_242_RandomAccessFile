package com.kostyukov;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main
{
	private static Locations locations = new Locations();
	
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		
		Map<String, String> dictionary = new HashMap<>();
		dictionary.put("QUIT", "Q");
		dictionary.put("NORTH", "N");
		dictionary.put("EAST", "E");
		dictionary.put("SOUTH", "S");
		dictionary.put("WEST", "W");
		
		int loc = 1;
        while(true) {
            System.out.println(locations.get(loc).getDescription());
            if(loc == 0) {
                break;
            }

            Map<String, Integer> exits = locations.get(loc).getExits();
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
                loc = exits.get(direction);

            } else {
                System.out.println("You cannot go in that direction");
            }
        }
	}
}
