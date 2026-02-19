package Randomization;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mapDBManager.BuildingResource;
import mapDBManager.BuildingResourceList;

public class buildingRandomizer {
	private BuildingResourceList Buildings = null;
	//private ArrayList<byte[]> lines = new ArrayList<byte[]>();
	//private ArrayList<byte[][]> parts = new ArrayList<byte[][]>();
	//private byte[] bytes;
	int usefulJobIndex = 13;
	int usefulJobBy = 2;
	int buildingJobBy = 4;
	int cuttingJobBy = 10;
	int miningJobBy = 17;
	Random random = new Random();
	int job1;
	int job2;
	boolean allHouses = false;
	boolean extraJobs = false;
	private ArrayList<Integer> jobCodes = new ArrayList<Integer>();
	private ArrayList<Integer> workplaceCodes = new ArrayList<Integer>();
	boolean hasBuilder = false, hasCutter = false, hasMiner = false;
	Integer[] jobs = {25,26,27,31,30,32,33,34,35,23,24,28,37,36,29,38,39,40,41,42};
	public buildingRandomizer(byte[] bs, boolean allHouses, boolean extraJobs,int seed)
	{
		initialize(bs, allHouses, extraJobs, seed);
	}
	public buildingRandomizer(byte[] data, boolean allHouses, boolean extraJobs)
	{
		initialize(data, allHouses, extraJobs, (int)(Math.random()*Integer.MAX_VALUE));
	}
	private void initialize(byte[] data, boolean allHouses, boolean extraJobs,int seed)
	{
		List<String> lines = bFM.Utils.bytesToStrs(data);
		//System.out.println(lines.get(0));
		Buildings = new BuildingResourceList(lines);
		random = new Random(seed);
		this.allHouses = allHouses;
		this.extraJobs = extraJobs;
		//System.out.println(Buildings.getBuilding(10350).getCode());
		randomize();
	}
	public byte[] getBytes()
	{
		return Buildings.toBytes();
	}
	private void makeJobCodeArray()
	{
		jobCodes.add(31);//farmer
		jobCodes.add(31);//farmer
		jobCodes.add(31);//farmer
		jobCodes.add(25);//grunt
		jobCodes.add(33);//build1
		jobCodes.add(34);//build2
		jobCodes.add(35);//build3
		jobCodes.add(30);//lumber
		jobCodes.add(32);//miner
		jobCodes.add(32);//miner
		jobCodes.add(32);//miner
		jobCodes.add(32);//miner
		jobCodes.add(26);//hardened soldier
		if(extraJobs)
		{
			jobCodes.add(27);//steel
			jobCodes.add(39);//egg
			jobCodes.add(40);//caster
			jobCodes.add(41);//soba
			jobCodes.add(42);//champ
			jobCodes.add(29);//wizard
			jobCodes.add(38);//doctor
			if(allHouses)
			{
				jobCodes.add(38);//doctor
				//kid
			}
		}
		//bad attackers
		jobCodes.add(37);//chef
		jobCodes.add(37);//chef
		jobCodes.add(28);//bowman
		jobCodes.add(36);//merchant
		jobCodes.add(36);//merchant
		jobCodes.add(36);//merchant
		jobCodes.add(24);//man
	}
	private void makeHouseArray()
	{
		if(allHouses){
			workplaceCodes.add(10106);//Liams House
			workplaceCodes.add(10107);//Verdes Hosue
		}
		workplaceCodes.add(10350);//frm1
		workplaceCodes.add(10169);//soldier1
		//Castle Town pt 2
		workplaceCodes.add(10168);//Regular Carpenter Hut
		//Grassland Town
		workplaceCodes.add(10134);//Animal Hunter Hut
		if(allHouses)
		{
			//Castle Town pt 2
			workplaceCodes.add(10100);//Wooden House A
			workplaceCodes.add(10109);//Wooden and Stone House B
			workplaceCodes.add(10112);//Wooden and Stone House E
			workplaceCodes.add(10212);//Corobo's House
			//Grassland Town
			workplaceCodes.add(10132);//Poor House C
			workplaceCodes.add(10135);//Fishing House
			workplaceCodes.add(10130);//Poor House A
			workplaceCodes.add(10105);//Wooden House F
		}
		//Farmers Town
		workplaceCodes.add(10138);//farmers
		workplaceCodes.add(10137);//farmers
		workplaceCodes.add(10171);//merchants
		workplaceCodes.add(10174);//merchants
		workplaceCodes.add(10175);//merchants
		workplaceCodes.add(10136);//jack
		if(allHouses)
		{
			workplaceCodes.add(10103);//Wooden House D
			workplaceCodes.add(10162);//Small Florist
			workplaceCodes.add(10102);//Wooden House C
			workplaceCodes.add(10139);//Ranch A
			workplaceCodes.add(10170);//Shop 1
		}
		//Stone City
		workplaceCodes.add(10146);//Mine
		workplaceCodes.add(10147);//Mine
		workplaceCodes.add(10156);//Mega Carpenters Hut
		//Soldier Town
		workplaceCodes.add(10151);//Hardened Soldier's Hut
		if(allHouses)
		{
			workplaceCodes.add(10116);//Stone House C
			workplaceCodes.add(10172);//shop 3
			workplaceCodes.add(10110);//wood and stone house c
			workplaceCodes.add(10173);//shop 4
			workplaceCodes.add(10167);//tailor B
			workplaceCodes.add(10143);//Bar
			workplaceCodes.add(10117);//stone house d
			workplaceCodes.add(10118);//^ e
		}
		//Royal City
		workplaceCodes.add(10150);//School
		workplaceCodes.add(10153);//Chef 1
		if(allHouses)
		{
			workplaceCodes.add(10163);//Large Florist Hut
			workplaceCodes.add(10120);//Skyscraper House A
			workplaceCodes.add(10121);//Skyscraper House B
			workplaceCodes.add(10122);//Skyscraper House C
			workplaceCodes.add(10123);//Skyscraper House D
			workplaceCodes.add(10124);//Skyscraper House E
			workplaceCodes.add(10125);//Skyscraper House F
		}
		
		//Gourmet Town
		workplaceCodes.add(10152);//Chef 2
		if(allHouses)
		{
			workplaceCodes.add(10154);//bake A
			workplaceCodes.add(10155);//bake B
			workplaceCodes.add(10142);//Orchard B
			workplaceCodes.add(10141);//Orchard A
			workplaceCodes.add(10113);//Wooden and Stone House F
		}
		//Glamour Town
		if(extraJobs||allHouses)
		{
			workplaceCodes.add(10160);//Clinic
			workplaceCodes.add(10161);//Hospital
			workplaceCodes.add(10158);//Theater
			workplaceCodes.add(10128);//Rich House C
		}
		if(allHouses)
		{
			
			workplaceCodes.add(10164);//Jeweler A
			workplaceCodes.add(10165);//Jeweler B
			workplaceCodes.add(10126);//Rich House A
			workplaceCodes.add(10127);//Rich House B
		}
		//Miners Town
		workplaceCodes.add(10148);//Mine
		workplaceCodes.add(10149);//Mine
		workplaceCodes.add(10241);// Giga Carpenter's Hut
		if(extraJobs||allHouses)
		{//3 houses for extra jobs or houses
			workplaceCodes.add(10115);//Stone House B
			workplaceCodes.add(10114);//Stone House A
			workplaceCodes.add(10119);//Stone House F
		}
		//Magic Town
		if(extraJobs||allHouses)
		{
			workplaceCodes.add(10176);//Wizard
		}
	}
	public void randomize()
	{
		makeJobCodeArray();
		makeHouseArray();
		if(allHouses)
		{
			usefulJobBy = 4;
			buildingJobBy = 14;
			cuttingJobBy = 25;
			miningJobBy = 52;
		}
		if(extraJobs)
		{
			usefulJobIndex = 18;
		}
		//generate the first (free) job
		//this will be different for allHouses and not allHouses
		int currentJobCode = jobCodes.remove((int)(random.nextDouble(0,1)*usefulJobIndex));
		checkJobCode(currentJobCode);
		int currentBuildingIndex = (int)random.nextDouble(0,usefulJobBy);
		if(allHouses)
		{
			BuildingResource Building = Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex));
			int BuildingCode = Building.getCode();
			if(BuildingCode==10106||BuildingCode==10107)//if it is one of the og houses
			{
				job1 = currentJobCode;
				setBuildingJob(Building, currentJobCode);
				
				currentJobCode = jobCodes.remove((int)random.nextDouble(0,usefulJobIndex));
				checkJobCode(currentJobCode);
				job2 = currentJobCode;
				Building = Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex));//remove the other starting house
				setBuildingJob(Building, currentJobCode);
				
				//decrease all by 2 because 2 were removed
				buildingJobBy -=2;
				cuttingJobBy -=2;
				miningJobBy -=2;
			}
			else
			{
				job1 = currentJobCode;
				setBuildingJob(Building, currentJobCode);
				usefulJobBy--;
				usefulJobIndex--;
				
				currentJobCode = jobCodes.remove((int)random.nextDouble(0,usefulJobIndex));
				checkJobCode(currentJobCode);
				currentBuildingIndex = (int)random.nextDouble(0,usefulJobBy);
				Building = Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex));
				job2 = currentJobCode;
				setBuildingJob(Building, currentJobCode);
				
				
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(0)),-1);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(0)),-1);//remove the two starting homes
				buildingJobBy -=4;
				cuttingJobBy -=4;
				miningJobBy -=4;
			}
		}
		else
		{
			job1 = currentJobCode;
			setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			usefulJobBy--;
			usefulJobIndex--;
			
			currentJobCode = jobCodes.remove((int)random.nextDouble(0,usefulJobIndex));
			checkJobCode(currentJobCode);
			currentBuildingIndex = (int)random.nextDouble(0,usefulJobBy);
			job2 = currentJobCode;
			setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			
			buildingJobBy -=2;
			cuttingJobBy -=2;
			miningJobBy -=2;
		}
		if(hasBuilder==false)
		{
			double oddsForCraftian = .01;
			double oddsForGiga = .05;
			double oddsForMega = .25;
			double num = random.nextDouble(0,1);
			
			currentBuildingIndex = (int)(random.nextDouble(0,buildingJobBy));
			
			if(extraJobs&&num<oddsForCraftian)
			{
				currentJobCode = getJobCode(42);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			else if(num<oddsForGiga)
			{
				currentJobCode = getJobCode(35);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			else if(num<oddsForMega)
			{
				currentJobCode = getJobCode(34);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			else
			{
				currentJobCode = getJobCode(33);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			cuttingJobBy --;
			miningJobBy --;
		}
		if(hasCutter==false)
		{
			double oddsForCraftian = .01;
			double num = random.nextDouble(0,1);
			
			currentBuildingIndex = (int)(random.nextDouble(0,cuttingJobBy));
			
			if(extraJobs&&num<oddsForCraftian)
			{
				currentJobCode = getJobCode(42);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			else
			{
				currentJobCode = getJobCode(30);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			miningJobBy --;
		}
		if(hasCutter==false)
		{
			double oddsForCraftian = .01;
			double num = random.nextDouble(0,1);
			
			currentBuildingIndex = (int)(random.nextDouble(0,miningJobBy));
			
			if(extraJobs&&num<oddsForCraftian)
			{
				currentJobCode = getJobCode(42);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
			else
			{
				currentJobCode = getJobCode(32);
				setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
			}
		}
		while(jobCodes.size()>0)
		{
			currentBuildingIndex = (int)(random.nextDouble(0,workplaceCodes.size()));
			currentJobCode = jobCodes.remove(0);
			setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(currentBuildingIndex)), currentJobCode);
		}
		//clear out remaining values
		while(workplaceCodes.size()>0)
		{
			setBuildingJob(Buildings.getBuilding(workplaceCodes.remove(0)), -1);
		}
	}
	private int getJobCode(int ret) 
	{
		for(int i = 0; i <jobCodes.size(); i++)
		{
			if(jobCodes.get(i)==ret)
			{
				jobCodes.remove(i);
				checkJobCode(ret);
				return ret;
			}
		}
		return -1;
	}
	private void checkJobCode(int currentJobCode) 
	{
		//set the boolean flags if job meets requirements
		if(currentJobCode == 42)
		{
			hasBuilder = true;
			hasCutter = true;
			hasMiner = true;
			return;
		}
		if(currentJobCode == 35)
		{
			hasBuilder = true;
			return;
		}
		if(currentJobCode == 34)
		{
			hasBuilder = true;
			return;
		}
		if(currentJobCode == 33)
		{
			hasBuilder = true;
			return;
		}
		if(currentJobCode == 30)
		{
			hasCutter = true;
			return;
		}
		if(currentJobCode == 32)
		{
			hasMiner = true;
			return;
		}
	}
	private void setBuildingJob(BuildingResource Building, int currentJobCode) 
	{
		Building.setTrainingCode(currentJobCode);
		Building.setSpeakingCode(currentJobCode);
		
		if(currentJobCode==-1)Building.setInteractionType(1);
		else Building.setInteractionType(2);
		
	}
	public int freeJobs(int place)
	{
		if(place == 1)
		{
			return toJobArrIndex(job1);
		}
		return toJobArrIndex(job2);
	}
	private int toJobArrIndex(int jobCode) 
	{
		int[] codes = new int[] {25, 26, 27, 31, 30, 32, 33, 34, 35, 23, 24, 28, 37, 36, 29, 38, 39, 40, 41, 42};
		for(int i =0; i< codes.length; i++)
		{
			if(codes[i]==jobCode) return i;
		}
		return -1;
	}


}
