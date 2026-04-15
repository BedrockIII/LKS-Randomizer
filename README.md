The LKS Randomizer.

To Randomize:
  Run the jar via double clicking or with "java -jar <File Path>" in cmd
  Java must be Installed
  Apply desired settings
  Click the "Randomize" button at the bottom
  Navigate to the "Riivolution folder in dolphin emulator and select it"

To Play:
	Rightclick your LKS Rom
	Choose Start with Riivolution Patches...
	Enable the LKS Randomizer Mod
	Optional: Create a Dolphin Tile by clicking Save as Preset...

Options:

  Enemy Randomizer:
  
    Randomize Monsters
    - Shuffle every monster in the game
    - VERY experimental
    - NO softlock prevention
    
  Job Randomizer:
  
    Randomize Jobs
    - Enable the Job Randomizer
    - Shuffle the order jobs appear in the job buildings
    - Randomize the price to train jobs
    
    All Building Randomization
    - Adds every NPC building in the set of possible Job Buildings
    
    Special Jobs
    - Adds every type of job including Steel Knights and Wizards to the randomization pool
    
    Price Insanity
    - Changes the price of training troops to also depend on what the troop starts as
    - Example:
      Grunt Soldier -> Farmer could be 29 bol
      but Gourmet Chef -> Farmer could be 3853 bol
      
    Max Price
    - The Maximum price of a job rounded to the nearest bol
    

In the base game, you can only train 1 Brainy Doctor, enabling this Gecko Code will bypass that and allow infinite doctors:

Name: Uncap Doctor Training

Creator: Bedrock_III

Description: bypass the doctor cap and allow infinite doctors

Code:

    C21F0BB8 00000001
    2C1C0000 00000000
    C21F0D94 00000001
    2C1C0000 00000000
