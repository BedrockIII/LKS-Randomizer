

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Main 
{
	static boolean jobBool;
	static boolean diffBool;
	static boolean houseBool;
	static int max;
	public static void main(String args[])
	{
		String title = (args.length == 0 ? "LKS Randomizer" : args[0]);
	    JFrame frame = new JFrame(title);
	    frame.setSize(400, 300);
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        	JPanel panel = new JPanel(new GridLayout(0, 1));
		    	    Border border = BorderFactory.createTitledBorder("Job Randomizer Settings");
		    	    panel.setBorder(border);
		    	    
		    	    //JCheckBox home = new JCheckBox("All Building Randomization?(Incomplete)");
		    	    //panel.add(home);
		    	    
		    	    //JCheckBox job = new JCheckBox("Enable Special Job training?");
		    	    //panel.add(job);
		    	    
		    	    
		    	    JCheckBox diff = new JCheckBox("Price Insanity (Training cost is unique for each job)");
		    	    panel.add(diff);
		    	    JButton button = new JButton("Submit");
		    	    
		    	    JLabel maxPrice = new JLabel("Enter The Max Price (default 2500)");
		    	    panel.add(maxPrice);
		    	    
		    	    JTextField maxPriceBox = new JTextField();
		    	    panel.add(maxPriceBox);
		    	    
		    	    Container contentPane = frame.getContentPane();
		    	    contentPane.add(panel, BorderLayout.CENTER);
		    	    contentPane.add(button, BorderLayout.SOUTH);
		    	    frame.setVisible(true);
		    	    button.addActionListener(new ActionListener() 
		    	    {
		    	        public void actionPerformed(ActionEvent e) 
		    	        {
		    	        	jobBool = false;
		    	            //jobBool = job.isSelected();
		    	            diffBool = diff.isSelected();
		    	            //houseBool = home.isSelected();
		    	            try
		    	            {
		    	            	max = Integer.parseInt(maxPriceBox.getText().toString());
		    	            }catch(Exception error)
		    	            {
		    	            	max = 2500;
		    	            }
		    	            
		    	            
		    	            
		    	            
		    	            final JobChangePriceChanger arr = new JobChangePriceChanger(max, jobBool, diffBool);
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
		    	    });
	}
}
