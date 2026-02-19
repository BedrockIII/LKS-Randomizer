package RandomizerGui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RandomizerSeed extends JPanel
{
	JTextField Seed;
	public RandomizerSeed()
	{
		JLabel seedLabel = new JLabel("Set The Max Price (default 2500)");
		add(seedLabel);
		
		Seed = new JTextField("0");
	    add(Seed);
	}
	public int getSeed()
	{
		int ret = 0;
		
		seedString = input.nextLine();
		seed = seedString.hashCode();
		
		return ret;
	}
}
