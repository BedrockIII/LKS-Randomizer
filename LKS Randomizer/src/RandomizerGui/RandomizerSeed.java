package RandomizerGui;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class RandomizerSeed extends JPanel
{
	JTextField Seed;
	public RandomizerSeed()
	{
		setToolTipText("Set the seed, blank will result in a random seed being generated");
		Dimension size = new Dimension(400,40);
		setLayout(new GridBagLayout());
		setPreferredSize(size);
		setMinimumSize(size);
		
		JLabel seedLabel = new JLabel("Seed:");
		seedLabel.setToolTipText("Set the seed, blank will result in a random seed being generated");
		seedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		seedLabel.setPreferredSize(new Dimension(100,30));
		seedLabel.setMinimumSize(new Dimension(100,30));
		add(seedLabel);
		
		Seed = new JTextField("");
		Seed.setToolTipText("Set the seed, blank will result in a random seed being generated");
		Seed.setPreferredSize(new Dimension(300,30));
		Seed.setMinimumSize(new Dimension(300,30));
	    add(Seed);
	}
	public int getSeed()
	{
		int ret = 0;
		
		String seedString = Seed.getText().toString();
		if(seedString.length()>0)
		{
			ret = seedString.hashCode();
		}
		else
		{
			ret = (int)(Integer.MAX_VALUE*(2*(Math.random()-.5)));
		}
		
		return ret;
	}
}
