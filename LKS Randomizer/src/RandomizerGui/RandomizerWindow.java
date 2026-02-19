package RandomizerGui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;

import LKSFileManagerTools.PCKGManager;
import Randomization.JobChangePriceChanger;

@SuppressWarnings("serial")
public class RandomizerWindow extends JFrame
{
	int seed = 0;
	public RandomizerWindow() 
	{
		//String title = (args.length == 0 ? "LKS Randomizer" : args[0]);
	    super("LKS Randomizer");
	    setSize(400, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    JobRandomizerPanel jobRandomizer = new JobRandomizerPanel();
	    
	    RandomizerButton button = new RandomizerButton();
	    button.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {randomize(jobRandomizer);}});
	    
	    add(jobRandomizer, BorderLayout.CENTER);
	    
	    
	    
	    
	    add(button, BorderLayout.SOUTH);
	    setVisible(true);
	}
	private void randomize(JobRandomizerPanel jobRandomizer)
	{
		boolean specialJobs = jobRandomizer.getSpecialJobRandomization();
        boolean priceInsanity = jobRandomizer.getPriceInsanity();
        boolean houseBool = jobRandomizer.getEnableBuildingRandomization();
        int maxPrice = jobRandomizer.getMaxPrice();
        
        
        
        JobChangePriceChanger arr = new JobChangePriceChanger(maxPrice, specialJobs, priceInsanity);
        byte[] pck = null;
		byte[] pck2 = null;
		try {
			pck = Files.readAllBytes(Paths.get("./res/chrDB.pac"));
			pck2 = Files.readAllBytes(Paths.get("./res/mapDB.pac"));
		} catch (Exception error) {
			System.out.println("Failed to read file");
		}
		PCKGManager tester = new PCKGManager(pck);
		PCKGManager tester2 = new PCKGManager(pck2);
		
		final buildingRandomizer buildRand = new buildingRandomizer(tester2.getFile("building0.lst"), false, jobBool);

		arr.setPrice(0, 11, buildRand.freeJobs(1));
		arr.setPrice(0, 11, buildRand.freeJobs(2));
		
		tester2.replaceFile("building0.lst", buildRand.getBytes());
		tester.replaceFile("JobChangePrice.cfg", arr.generateArray());
		try {
			Files.write(Paths.get("./res/chrDB0.pac"), tester.getFile());
			Files.write(Paths.get("./res/mapDB0.pac"), tester2.getFile());
		} catch (Exception error) {
			System.out.println("Unable to write the file");
		}
		System.exit(0);
	}
}
