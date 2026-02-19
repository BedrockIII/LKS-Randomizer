package RandomizerGui;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class JobRandomizerPanel extends JPanel 
{
	JCheckBox allBuildingRandomization;
	JCheckBox specialJobRandomization;
	JCheckBox priceInsanity;
	JTextField maxPriceBox;
	
	public JobRandomizerPanel()
	{
		super(new GridLayout(0, 1));
		
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
	    
	    
	    JLabel maxPrice = new JLabel("Set The Max Price (default 2500)");
	    add(maxPrice);
	    
	    maxPriceBox = new JTextField("2500");
	    add(maxPriceBox);
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
		return priceInsanity.isSelected();
	}
	public int getMaxPrice()
	{
		int ret = 2500;
		
		try
        {
			ret = Integer.parseInt(maxPriceBox.getText().toString());
        }catch(Exception error)
        {
        	ret = 2500;
        }
		
		return ret;
	}
}
