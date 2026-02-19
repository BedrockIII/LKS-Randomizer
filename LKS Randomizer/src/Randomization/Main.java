package Randomization;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import PCKGManager.PCKGManager;
import RandomizerGui.RandomizerWindow;

public class Main 
{
	static int seed = 0;
	static boolean jobBool;
	static boolean priceInsanity;
	static boolean houseInsanity;
	static int max;
	public static final boolean DEBUGMODE = false;
	static String seedString = "";
	static Scanner input = new Scanner(System.in);
	static String inputName = "";
	public static void main(String args[])
	{
		guiAttempt();
		System.out.println("LKS Randomizer Version 3.0");
		System.out.println("**************************");
		System.out.println("Added:\n\tGUI");
		System.out.println("Fixed:\n\tJob Randomizer Should Be Better");

		
		System.out.println("Enter the seed or leave blank for a random one.");
		seedString = input.nextLine();
		seed = seedString.hashCode(); //make string a number
		
		System.out.println("Enter the File Path of the Randomizer's Directory");
		inputName = input.nextLine();
		
		consoleVersionJobs();
		
		System.out.println("Do you wish to enable Monster Randomization?");
		String monsterS = input.nextLine();
		while(monsterS.length()==0&&!(monsterS.charAt(0)=='y'||monsterS.charAt(0)=='Y'||monsterS.charAt(0)=='N'||monsterS.charAt(0)=='n'))
		{
			System.out.println("Oops! I didn't understand that. try typing yes or no");
			System.out.println("Do you wish to enable the Monster Randomizer?");
			monsterS = input.nextLine();
		}
		
		if(monsterS.charAt(0)=='y'||monsterS.charAt(0)=='Y')
		monsterRandomizer();
	}
	private static void consoleVersionJobs()
	{
		
		
		System.out.println("Do you want to enable training 'special' jobs?");
		String jobTypesEnabled = input.nextLine();
		while(jobTypesEnabled.length()==0||!(jobTypesEnabled.charAt(0)=='y'||jobTypesEnabled.charAt(0)=='Y'||jobTypesEnabled.charAt(0)=='N'||jobTypesEnabled.charAt(0)=='n'))
		{
			System.out.println("Oops! I didn't understand that. try typing yes or no");
			System.out.println("Do you want to enable training 'special' jobs?");
			jobTypesEnabled = input.nextLine();
		}
		jobBool = jobTypesEnabled.charAt(0)=='y'||jobTypesEnabled.charAt(0)=='Y';
		
		
		
		System.out.println("Do you wish to enable Price Insanity?");
		jobTypesEnabled = input.nextLine();
		while(jobTypesEnabled.length()==0&&!(jobTypesEnabled.charAt(0)=='y'||jobTypesEnabled.charAt(0)=='Y'||jobTypesEnabled.charAt(0)=='N'||jobTypesEnabled.charAt(0)=='n'))
		{
			System.out.println("Oops! I didn't understand that. try typing yes or no");
			System.out.println("Do you wish to enable Price Insanity?");
			jobTypesEnabled = input.nextLine();
		}
        priceInsanity = jobTypesEnabled.charAt(0)=='y'||jobTypesEnabled.charAt(0)=='Y';
        
        
        System.out.println("Do you wish to enable House Insanity?");
		jobTypesEnabled = input.nextLine();
		while(jobTypesEnabled.length()==0&&!(jobTypesEnabled.charAt(0)=='y'||jobTypesEnabled.charAt(0)=='Y'||jobTypesEnabled.charAt(0)=='N'||jobTypesEnabled.charAt(0)=='n'))
		{
			System.out.println("Oops! I didn't understand that. try typing yes or no");
			System.out.println("Do you wish to enable House Insanity?");
			jobTypesEnabled = input.nextLine();
		}
        houseInsanity = jobTypesEnabled.charAt(0)=='y'||jobTypesEnabled.charAt(0)=='Y';
        
        
        
        
        System.out.println("What should the max price for Jobs be? (2500 default)");
        String maxPrice = input.nextLine();
        max = 2500;
        final String validChars = "1234567890";
		String numOnlyString = "";
		
		//make string into number
		for(int i = 0; i<maxPrice.length(); i++)
		{
			if(validChars.indexOf(maxPrice.charAt(i))!=-1)
			{
				numOnlyString = numOnlyString + maxPrice.charAt(i);
			}
		}
        if(numOnlyString.length()>0)
        {
        	max = Integer.parseInt(numOnlyString);
        }
        
        
        final JobChangePriceChanger arr = new JobChangePriceChanger(max, jobBool, priceInsanity, seed);
        byte[] pck = null;
		byte[] pck2 = null;
		try {
			pck = Files.readAllBytes(Paths.get(inputName+ "/Contents/chrDB.pac"));
			pck2 = Files.readAllBytes(Paths.get(inputName+ "/Contents/mapDB.pac"));
		} catch (Exception error) {
			System.out.println("Failed to read file");
		}
		PCKGManager tester = new PCKGManager(pck);
		PCKGManager tester2 = new PCKGManager(pck2);
		
		final buildingRandomizer buildRand = new buildingRandomizer(tester2.getFile("building0.lst"), houseInsanity, jobBool, seed);

		arr.setPrice(0, 11, buildRand.freeJobs(1));
		arr.setPrice(0, 11, buildRand.freeJobs(2));
		
		tester2.replaceFile("building0.lst", buildRand.getBytes());
		tester.replaceFile("JobChangePrice.cfg", arr.generateArray());
		try {
			Files.write(Paths.get(inputName+ "/Contents/chrDB0.pac"), tester.getFile());
			Files.write(Paths.get(inputName+ "/Contents/mapDB0.pac"), tester2.getFile());
		} catch (Exception error) {
			System.out.println("Unable to write the file");
		}
	}
	private static void guiAttempt()
	{
		RandomizerWindow GUI = new RandomizerWindow();
	}
	private static void monsterRandomizer()
	{
		PCKGManager normalMSDB;
		try 
		{
			normalMSDB = new PCKGManager(Files.readAllBytes(Paths.get(inputName + "\\Contents\\msDB.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read file at: " + inputName + "\\Contents\\msDB.pac");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		PCKGManager easyMSDB;
		try 
		{
			easyMSDB = new PCKGManager(Files.readAllBytes(Paths.get(inputName + "\\Contents\\msDB_EASY.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read file at: " + inputName + "\\Contents\\msDB_EASY.pac");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		PCKGManager hardMSDB;
		try 
		{
			hardMSDB = new PCKGManager(Files.readAllBytes(Paths.get(inputName + "\\Contents\\msDB_HARD.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read file at: " + inputName + "\\Contents\\msDB_HARD.pac");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		PCKGManager hellMSDB;
		try 
		{
			hellMSDB = new PCKGManager(Files.readAllBytes(Paths.get(inputName + "\\Contents\\msDB_HELL.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read file at: " + inputName + "\\Contents\\msDB_HELL.pac");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		
		EnemyRandomizer rando = new EnemyRandomizer(normalMSDB.getFile("MOP_14_OBJECT.lst"), seed, normalMSDB.getFile("MOP_14_GROUP.lst"));
		
		
		
		byte[] objects = rando.toArr();
		normalMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(inputName + "\\Contents\\msDB27.pac") , normalMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write file at: " + inputName + "\\Contents\\msDB27.pac");
			System.out.println("The Enemy Randomizer will not be enabled for Normal Difficulty.");
		}
		easyMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(inputName + "\\Contents\\msDB27_EASY.pac") , easyMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write file at: " + inputName + "\\Contents\\msDB27_EASY.pac");
			System.out.println("The Enemy Randomizer will not be enabled for Easy Difficulty.");
		}
		hardMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(inputName + "\\Contents\\msDB27_HARD.pac") , hardMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write file at: " + inputName + "\\Contents\\msDB27_HARD.pac");
			System.out.println("The Enemy Randomizer will not be enabled for Hard Difficulty.");
		}
		hellMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(inputName + "\\Contents\\msDB27_HELL.pac") , hellMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write file at: " + inputName + "\\Contents\\msDB27_HELL.pac");
			System.out.println("The Enemy Randomizer will not be enabled for Tyrant Difficulty.");
		}
	}
}
