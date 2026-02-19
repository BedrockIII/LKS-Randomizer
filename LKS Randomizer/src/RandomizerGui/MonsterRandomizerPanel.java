package RandomizerGui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class MonsterRandomizerPanel extends JPanel
{
	JCheckBox randomize;
	public MonsterRandomizerPanel()
	{ 
		super(new GridLayout(0, 1));
		
		Dimension size = new Dimension(400,100);
		setPreferredSize(size);
		setMinimumSize(size);
	    Border border = BorderFactory.createTitledBorder("Enemy Randomizer Settings");
	    setBorder(border);
		
		randomize = new JCheckBox("Randomize Monsters", true);
		randomize.setToolTipText("Enables the Monster Randomizer, disabling this will cause all job settings to have no effect");
		add(randomize);
	}
	public boolean enableMe()
	{
		return randomize.isSelected();
	}
}
