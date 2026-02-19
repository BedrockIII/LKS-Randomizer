package RandomizerGui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;

import PCKGManager.PCKGManager;
import Randomization.EnemyRandomizer;
import Randomization.JobChangePriceChanger;
import Randomization.buildingRandomizer;

@SuppressWarnings("serial")
public class RandomizerWindow extends JFrame
{
	int seed = 0;
	public RandomizerWindow() 
	{
		//String title = (args.length == 0 ? "LKS Randomizer" : args[0]);
	    super("LKS Randomizer");
	    setSize(400, 450);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new GridBagLayout());
	    GridBagConstraints layout = new GridBagConstraints();
	    layout.gridwidth = GridBagConstraints.REMAINDER;
	    JobRandomizerPanel jobRandomizer = new JobRandomizerPanel();
	    RandomizerSeed seedPanel = new RandomizerSeed();
	    MonsterRandomizerPanel monsterRandomizerPanel = new MonsterRandomizerPanel();
	    
	    
	    
	    
	    RandomizerButton button = new RandomizerButton();
	    button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {randomize(jobRandomizer, seedPanel, monsterRandomizerPanel);}});
	    
	    
	    
	    add(seedPanel, layout);
	    add(monsterRandomizerPanel, layout);
	    add(jobRandomizer, layout);
	    
	    
	    
	    
	    add(button);
	    setVisible(true);
	}
	private static void monsterRandomizer(int seed)
	{
		PCKGManager normalMSDB;
		try 
		{
			normalMSDB = new PCKGManager(Files.readAllBytes(Paths.get(".\\LKS Randomizer\\Contents\\msDB.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read normal difficulty enemy file");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		PCKGManager easyMSDB;
		try 
		{
			easyMSDB = new PCKGManager(Files.readAllBytes(Paths.get(".\\LKS Randomizer\\Contents\\msDB_EASY.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read easy difficulty enemy file");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		PCKGManager hardMSDB;
		try 
		{
			hardMSDB = new PCKGManager(Files.readAllBytes(Paths.get(".\\LKS Randomizer\\Contents\\msDB_HARD.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read hard difficulty enemy file");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		PCKGManager hellMSDB;
		try 
		{
			hellMSDB = new PCKGManager(Files.readAllBytes(Paths.get(".\\LKS Randomizer\\Contents\\msDB_HELL.pac")));
		} catch (IOException e) 
		{
			System.out.println("Failed to read tyrant difficulty enemy file");
			System.out.println("The Enemy Randomizer cannot continue and will now disable itself.");
			return;
		}
		
		EnemyRandomizer rando = new EnemyRandomizer(normalMSDB.getFile("MOP_14_OBJECT.lst"), seed, normalMSDB.getFile("MOP_14_GROUP.lst"));
		
		
		
		byte[] objects = rando.toArr();
		normalMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(".\\LKS Randomizer\\Contents\\msDB27.pac") , normalMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write normal difficulty enemy file");
			System.out.println("The Enemy Randomizer will not be enabled for Normal Difficulty.");
		}
		easyMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(".\\LKS Randomizer\\Contents\\msDB27_EASY.pac") , easyMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write easy difficulty enemy file");
			System.out.println("The Enemy Randomizer will not be enabled for Easy Difficulty.");
		}
		hardMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(".\\LKS Randomizer\\Contents\\msDB27_HARD.pac") , hardMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write hard difficulty enemy file");
			System.out.println("The Enemy Randomizer will not be enabled for Hard Difficulty.");
		}
		hellMSDB.replaceFile("MOP_14_OBJECT.lst", objects);
		try 
		{
			Files.write(Paths.get(".\\LKS Randomizer\\Contents\\msDB27_HELL.pac") , hellMSDB.getFile() );
		} catch (IOException e) 
		{
			System.out.println("Failed to write tyrant difficulty enemy file");
			System.out.println("The Enemy Randomizer will not be enabled for Tyrant Difficulty.");
		}
	}
	private void randomizeJobs(JobRandomizerPanel jobRandomizer, int seed)
	{
		boolean specialJobs = jobRandomizer.getSpecialJobRandomization();
        boolean priceInsanity = jobRandomizer.getPriceInsanity();
        boolean houseBool = jobRandomizer.getEnableBuildingRandomization();
        int maxPrice = jobRandomizer.getMaxPrice();
        
        
        
        JobChangePriceChanger arr = new JobChangePriceChanger(maxPrice, specialJobs, priceInsanity, seed);
        byte[] characterDataBaseData = null;
		byte[] mapDataBaseData = null;
		try {
			characterDataBaseData = Files.readAllBytes(Paths.get("./LKS Randomizer/Contents/chrDB.pac"));
			mapDataBaseData = Files.readAllBytes(Paths.get("./LKS Randomizer/Contents/mapDB.pac"));
		} catch (Exception error) {
			System.out.println("Failed to read file");
		}
		PCKGManager characterDataBase = new PCKGManager(characterDataBaseData);
		PCKGManager mapDataBase = new PCKGManager(mapDataBaseData);
		
		buildingRandomizer buildRand = new buildingRandomizer(mapDataBase.getFile("building0.lst"), houseBool, specialJobs, seed);

		arr.setPrice(0, 11, buildRand.freeJobs(1));
		arr.setPrice(0, 11, buildRand.freeJobs(2));
		
		mapDataBase.replaceFile("building0.lst", buildRand.getBytes());
		characterDataBase.replaceFile("JobChangePrice.cfg", arr.generateArray());
		try {
			Files.write(Paths.get("./LKS Randomizer/Contents/chrDB0.pac"), characterDataBase.getFile());
			Files.write(Paths.get("./LKS Randomizer/Contents/mapDB0.pac"), mapDataBase.getFile());
		} catch (Exception error) {
			System.out.println("Unable to write the file");
		}
	}
	private void randomize(JobRandomizerPanel jobRandomizer, RandomizerSeed seedPanel, MonsterRandomizerPanel monsterRandomizerPanel)
	{
		boolean doJobRandomizer = jobRandomizer.enableMe();
		boolean doEnemyRandomizer = monsterRandomizerPanel.enableMe();
		int seed = seedPanel.getSeed();
		if(doJobRandomizer) randomizeJobs(jobRandomizer, seed);
		if(doEnemyRandomizer) monsterRandomizer(seed);
		System.exit(0);
	}
}
