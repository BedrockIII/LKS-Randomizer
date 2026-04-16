package RandomizerGui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class MonsterRandomizerPanel extends JPanel
{
	private final int defaultMaxHP = 100;
	JCheckBox enabled;
	
	
	
	JCheckBox randomizeDrops;
	JCheckBox randomizeAllDrops;
	JCheckBox randomizeHP;
	JTextField maxHPBox;
	JCheckBox difficultyInsanity;
	JCheckBox includeUnkillables;
	JCheckBox includeGuardians;
	JCheckBox includeBossTeams;
	public MonsterRandomizerPanel()
	{ 
		setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		layout.fill = GridBagConstraints.HORIZONTAL;
	    layout.gridwidth = GridBagConstraints.REMAINDER;
	    layout.weightx = 1.0;
	    layout.weighty = 0.0;
	    layout.anchor = GridBagConstraints.NORTHWEST;
		
		//Dimension size = new Dimension(400,300);
		//setPreferredSize(size);
		//setMinimumSize(size);
	    Border border = BorderFactory.createTitledBorder("Enemy Randomizer Settings");
	    setBorder(border);
		
		enabled = new JCheckBox("Randomize Monsters", true);
		enabled.setToolTipText("Enables the Monster Randomizer, disabling this will cause all Monster settings to have no effect");
		layout.weighty = 1.0;
		add(enabled, layout);
		
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
		//add(randomizerType, layout);
		
		
		//Global Settings:
		//Randomize Drops
		randomizeDrops = new JCheckBox("Randomize Drops", false);
		randomizeDrops.setToolTipText("Randomize which items are dropped by slain UMA");
		//add(randomizeDrops, layout);
		//All Enemy Drops
		randomizeAllDrops = new JCheckBox("Randomize All Drops", false);
		randomizeAllDrops.setToolTipText("Randomize which UMA drop items (Only works if Randomize Drops is enabled)");
		//add(randomizeAllDrops, layout);
		//Randomize HP
		randomizeHP = new JCheckBox("Randomize HP", false);
		randomizeHP.setToolTipText("Randomize The HP of UMA");
		//add(randomizeHP, layout);
		//Max HP
		JLabel maxHP = new JLabel("Set The Max HP");
		maxHP.setToolTipText("Set The Max HP for Randomized UMA (default " + defaultMaxHP +")");
	    //add(maxHP, layout);
	    
		maxHPBox = new JTextField("defaultMaxHP");
		maxHPBox.setPreferredSize(new Dimension(300,30));
		maxHPBox.setMinimumSize(new Dimension(300,30));
		maxHPBox.setToolTipText("Set The Max HP for Randomized UMA (default " + defaultMaxHP + ")");
	    //add(maxHPBox, layout);
		//Difficulty insanity(Every Difficulty is randomized Differently
	    difficultyInsanity = new JCheckBox("Difficulty Insanity", false);
	    difficultyInsanity.setToolTipText("Randomize Each Difficulty Seperately");
	    
		//add(difficultyInsanity, layout);
		
		
		
		
		layout.weighty = 1.0;
		includeUnkillables = new JCheckBox("Randomize Unkillable Enemies", false);
		includeUnkillables.setToolTipText("Adds in Invulnerable UMA to the list of enemies. NOT recommended. DO NOT PLAY WITH THIS ENABLED IF YOU WANT TO ENSURE PROGRESS");
		//add(includeUnkillables, layout);
	}
	public boolean enableMe()
	{
		return enabled.isSelected();
	}
}
