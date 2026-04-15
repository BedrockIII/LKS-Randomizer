package RandomizerGui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class MonsterRandomizerPanel extends JPanel
{
	JCheckBox enabled;
	
	
	
	JCheckBox randomizeDrops;
	JCheckBox randomizeAllDrops;
	JCheckBox randomizeHP;
	JTextField maxHPBox;
	JCheckBox difficultyInsanity;
	public MonsterRandomizerPanel()
	{ 
		super(new GridLayout(0, 1));
		
		Dimension size = new Dimension(400,100);
		setPreferredSize(size);
		setMinimumSize(size);
	    Border border = BorderFactory.createTitledBorder("Enemy Randomizer Settings");
	    setBorder(border);
		
		enabled = new JCheckBox("Randomize Monsters", true);
		enabled.setToolTipText("Enables the Monster Randomizer, disabling this will cause all Monster settings to have no effect");
		add(enabled);
		
		//Randomizer Mode : Group or Object
		// Group randomizes group codes
		//Group Settings:
		//Include Boss Aides
		//Include Bosses (ban invincible and some specific killers for some bosses)
		JPanel groupSettings = new JPanel();
		
		
		
		//Object Settings:
		//Who to randomize?
				//Dropdown with following options
					//Monsters Only
					//Blockers Only
					//Monsters and Blockers Seperately
					//Monsters and Blockers Together
		JPanel objectSettings = new JPanel();
		
		
		JTabbedPane randomizerType = new JTabbedPane();
		randomizerType.addTab("Group Randomization Mode", groupSettings);
		randomizerType.addTab("Object Randomization Mode", objectSettings);
		add(randomizerType);
		
		
		//Global Settings:
		//Randomize Drops
		randomizeDrops = new JCheckBox("Randomize Drops", false);
		randomizeDrops.setToolTipText("Randomize which items are dropped by slain UMA");
		add(randomizeDrops);
		//All Enemy Drops
		randomizeAllDrops = new JCheckBox("Randomize All Drops", false);
		randomizeAllDrops.setToolTipText("Randomize which UMA drop items (Only works if Randomize Drops is enabled)");
		add(randomizeAllDrops);
		//Randomize HP
		randomizeHP = new JCheckBox("Randomize HP", false);
		randomizeHP.setToolTipText("Randomize The HP of UMA");
		add(randomizeHP);
		//Max HP
		maxHPBox = new JTextField("1000");
		maxHPBox.setToolTipText("Set The Max HP for Randomized UMA (default 2500)");
	    add(maxHPBox);
		//Difficulty insanity(Every Difficulty is randomized Differently
	    difficultyInsanity = new JCheckBox("Difficulty Insanity", false);
	    difficultyInsanity.setToolTipText("Randomize Each Difficulty Seperately");
		add(difficultyInsanity);
		
	}
	public boolean enableMe()
	{
		return enabled.isSelected();
	}
}
