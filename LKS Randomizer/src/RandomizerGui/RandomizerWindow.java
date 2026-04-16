package RandomizerGui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
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
	    setMinimumSize(new Dimension(400, 300));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new GridBagLayout());
	    GridBagConstraints layout = new GridBagConstraints();
	    layout.gridwidth = GridBagConstraints.REMAINDER;
	    layout.fill = GridBagConstraints.HORIZONTAL;
	    layout.weightx = 1.0;
	    layout.weighty = 0.0;
	    layout.anchor = GridBagConstraints.NORTH;
	    JobRandomizerPanel jobRandomizer = new JobRandomizerPanel();
	    RandomizerSeed seedPanel = new RandomizerSeed();
	    MonsterRandomizerPanel monsterRandomizerPanel = new MonsterRandomizerPanel();
	    
	    
	    
	    
	    RandomizerButton button = new RandomizerButton();
	    button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {randomize(jobRandomizer, seedPanel, monsterRandomizerPanel);}});
	    
	    
	    
	    add(seedPanel, layout);
	    add(monsterRandomizerPanel, layout);
	    add(jobRandomizer, layout);
	    
	    
	    
	    layout.weighty = 1.0;
	    add(button, layout);
	    pack();
	    setVisible(true);
	}
	@SuppressWarnings("unused")
	private static void monsterRandomizer(int seed, File outputFolder, String difficultyCode)
	{
		PCKGManager msDB = null;
		msDB = new PCKGManager(bFM.Utils.readFile("msDB" + difficultyCode + ".pac", "./LKS Randomizer/Contents/"));
		
		if(msDB==null)
		{
			System.out.println("The Enemy Randomizer cannot continue for the " + difficultyCode + " difficulty and will now disable itself.");
			return;
		}
		
		EnemyRandomizer rando = new EnemyRandomizer(msDB, seed);
		
		Path directory = Paths.get(outputFolder.getAbsolutePath());
		Path contentsFolder = directory.resolve("LKS Randomizer/Contents");
		
		//Write contents folder
		try 
		{
			Files.write(contentsFolder.resolve("msDB27" + difficultyCode + ".pac"), msDB.getFile());
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write randomized msDB27" + difficultyCode + ".pac file");
		}
	}
	private static void monsterRandomizer(int seed, File file)
	{
		monsterRandomizer(seed, file, "");
		monsterRandomizer(seed, file, "_EASY");
		monsterRandomizer(seed, file, "_HARD");
		monsterRandomizer(seed, file, "_HELL");
	}
	private void randomizeJobs(JobRandomizerPanel jobRandomizer, int seed, File outputFolder)
	{
		boolean specialJobs = jobRandomizer.getSpecialJobRandomization();
        boolean priceInsanity = jobRandomizer.getPriceInsanity();
        boolean houseBool = jobRandomizer.getEnableBuildingRandomization();
        int maxPrice = jobRandomizer.getMaxPrice();
        
        
        
        JobChangePriceChanger arr = new JobChangePriceChanger(maxPrice, specialJobs, priceInsanity, seed);
        byte[] characterDataBaseData = null;
		byte[] mapDataBaseData = null;
		
		//Read the Files
		characterDataBaseData = bFM.Utils.readFile("chrDB.pac","./LKS Randomizer/Contents/");
		mapDataBaseData = bFM.Utils.readFile("mapDB.pac","./LKS Randomizer/Contents/");
		
		//Create the pacs for those files
		PCKGManager characterDataBase = new PCKGManager(characterDataBaseData);
		PCKGManager mapDataBase = new PCKGManager(mapDataBaseData);
		
		buildingRandomizer buildRand = new buildingRandomizer(mapDataBase.getFile("building0.lst"), houseBool, specialJobs, seed);

		arr.setPrice(0, 11, buildRand.freeJobs(1));
		arr.setPrice(0, 11, buildRand.freeJobs(2));
		
		mapDataBase.replaceFile("building0.lst", buildRand.getBytes());
		characterDataBase.replaceFile("JobChangePrice.cfg", arr.generateArray());
		
		
		
		Path directory = Paths.get(outputFolder.getAbsolutePath());
		Path contentsFolder = directory.resolve("LKS Randomizer/Contents");
		
		//Write contents folder
		try 
		{
			Files.write(contentsFolder.resolve("chrDB0.pac"), characterDataBase.getFile());
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write randomized chrDB0.pac file");
		}
		try 
		{
			Files.write(contentsFolder.resolve("mapDB0.pac"), mapDataBase.getFile());
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write randomized mapDB0.pac file");
		}
	}
	private void randomize(JobRandomizerPanel jobRandomizer, RandomizerSeed seedPanel, MonsterRandomizerPanel monsterRandomizerPanel)
	{
		JFileChooser filePicker = new JFileChooser();
	    filePicker.setSelectedFile(Paths.get("D:\\Dolphin_Emulator\\Load\\Riivolution\\").toFile());
	    filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int saveCode = filePicker.showSaveDialog(this);
	    if(saveCode == JFileChooser.APPROVE_OPTION)
	    {
	    	File file = filePicker.getSelectedFile();
	    	createFileStructure(file);
	    	boolean doJobRandomizer = jobRandomizer.enableMe();
			boolean doEnemyRandomizer = monsterRandomizerPanel.enableMe();
			int seed = seedPanel.getSeed();
			if(doJobRandomizer) randomizeJobs(jobRandomizer, seed, file);
			if(doEnemyRandomizer) monsterRandomizer(seed, file);
			System.exit(0);
	    }

		
	}
	private void createFileStructure(File outputFolder) 
	{
		//Create needed directories if they dont exist
		Path directory = Paths.get(outputFolder.getAbsolutePath());
		Path randomizerFolder = directory.resolve("LKS Randomizer");
		try 
		{
			Files.createDirectories(randomizerFolder);
		} catch (IOException e) 
		{
			System.out.println("Failed to create missing directory: " + randomizerFolder);
		}
		Path riivolutionFolder = directory.resolve("riivolution");
		try 
		{
			Files.createDirectories(riivolutionFolder);
		} catch (IOException e) 
		{
			System.out.println("Failed to create missing directory: " + riivolutionFolder);
		}
		Path contentsFolder = randomizerFolder.resolve("Contents");
		try 
		{
			Files.createDirectories(contentsFolder);
		} catch (IOException e) 
		{
			System.out.println("Failed to create missing directory: " + contentsFolder);
		}
		Path modelsFolder = randomizerFolder.resolve("Models");
		try 
		{
			Files.createDirectories(modelsFolder);
		} catch (IOException e) 
		{
			System.out.println("Failed to create missing directory: " + modelsFolder);
		}
		Path monstersFolder = randomizerFolder.resolve("Monsters");
		try 
		{
			Files.createDirectories(monstersFolder);
		} catch (IOException e) 
		{
			System.out.println("Failed to create missing directory: " + monstersFolder);
		}
		Path textFolder = randomizerFolder.resolve("Text");
		try 
		{
			Files.createDirectories(textFolder);
		} catch (IOException e) 
		{
			System.out.println("Failed to create missing directory: " + textFolder);
		}
		
		//Try to write constant files in directories
		
		try 
		{
			Files.write(modelsFolder.resolve("cbData1.pac"), bFM.Utils.readFile("cbData1.pac", "./LKS Randomizer/Models/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing cbData1.pac file");
		}
		try 
		{
			Files.write(modelsFolder.resolve("ccceData1.pac"), bFM.Utils.readFile("ccceData1.pac", "./LKS Randomizer/Models/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing ccceData1.pac file");
		}
		try 
		{
			Files.write(monstersFolder.resolve("bl0739.pac"), bFM.Utils.readFile("bl0739.pac", "./LKS Randomizer/Monsters/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing bl0739.pac file");
		}
		try 
		{
			Files.write(monstersFolder.resolve("ex82079.pac"), bFM.Utils.readFile("ex82079.pac", "./LKS Randomizer/Monsters/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing ex82079.pac file");
		}
		try 
		{
			Files.write(monstersFolder.resolve("ex82080.pac"), bFM.Utils.readFile("ex82080.pac", "./LKS Randomizer/Monsters/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing ex82080.pac file");
		}
		try 
		{
			Files.write(textFolder.resolve("mes0.pac"), bFM.Utils.readFile("mes0.pac", "./LKS Randomizer/Text/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing mes0.pac file");
		}
		try 
		{
			Files.write(riivolutionFolder.resolve("LKS_Randomizer.xml"), bFM.Utils.readFile("LKS_Randomizer.xml", "./LKS Randomizer/"));
		} 
		catch (IOException e) 
		{
			System.out.println("Failed to write missing LKS_Randomizer.xml file");
		}
	}
}
