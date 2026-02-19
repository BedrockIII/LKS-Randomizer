package RandomizerGui;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class JobRandomizerPanel extends JPanel 
{
	JCheckBox randomize;
	JCheckBox allBuildingRandomization;
	JCheckBox specialJobRandomization;
	JCheckBox priceInsanity;
	JTextField maxPriceBox;
	
	public JobRandomizerPanel()
	{ 
		super(new GridLayout(0, 1));
		
		randomize = new JCheckBox("Randomize Jobs", true);
		randomize.setToolTipText("Enables the Job Randomizer, disabling this will cause all job settings to have no effect");
		add(randomize);

		Dimension size = new Dimension(400,200);
		setPreferredSize(size);
		setMinimumSize(size);
	    Border border = BorderFactory.createTitledBorder("Job Randomizer Settings");
	    setBorder(border);
	    
	    allBuildingRandomization = new JCheckBox("All Building Randomization?");
	    allBuildingRandomization.setToolTipText("Lets the randomizer set any building to be a unit training building, including houses");
	    add(allBuildingRandomization);
	    
	    specialJobRandomization = new JCheckBox("Enable Special Job training?");
	    specialJobRandomization.setToolTipText("Add in \"Special\" jobs such as wizards to the randomizer");
	    add(specialJobRandomization);
	    
	    
	    priceInsanity = new JCheckBox("Enable Price Insanity");
	    priceInsanity.setToolTipText("Makes the cost to train a unit differ depending on the job of the unit pre training");
	    add(priceInsanity);
	    
	    
	    JLabel maxPrice = new JLabel("Set The Max Price");
	    maxPrice.setToolTipText("Set The Max Price (default 2500)");
	    add(maxPrice);
	    
	    maxPriceBox = new JTextField("2500");
	    maxPriceBox.setToolTipText("Set The Max Price (default 2500)");
	    add(maxPriceBox);
	}
	public boolean enableMe()
	{
		return randomize.isSelected();
	}
	public boolean getEnableBuildingRandomization()
	{
		return allBuildingRandomization.isSelected();
	}
	public boolean getSpecialJobRandomization()
	{
		return specialJobRandomization.isSelected();
	}
	public boolean getPriceInsanity()
	{
		return priceInsanity.isSelected()&&randomize.isSelected();
	}
	public int getMaxPrice()
	{
		int ret = 2500;
		
		String maxPrice = maxPriceBox.getText().toString();
		
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
        	ret = Integer.parseInt(numOnlyString);
        }
		
		return ret;
	}
}
