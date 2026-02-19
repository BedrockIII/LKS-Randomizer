package RandomizerGui;

import java.awt.Dimension;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RandomizerButton extends JButton
{
	public RandomizerButton()
	{
		super("Randomize");
		Dimension size = new Dimension(100,35);
		setPreferredSize(size);
		setMinimumSize(size);
	}
	
}
