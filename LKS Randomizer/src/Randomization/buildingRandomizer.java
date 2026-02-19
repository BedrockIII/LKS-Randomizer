package Randomization;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class buildingRandomizer {
	private ArrayList<byte[]> lines = new ArrayList<byte[]>();
	private ArrayList<byte[][]> parts = new ArrayList<byte[][]>();
	private byte[] bytes;
	int usefulJobIndex = 13;
	int usefulJobBy = 2;
	int buildingJobBy = 4;
	int cuttingJobBy = 10;
	int miningJobBy = 17;
	Random random = new Random();
	byte[] jobArr;
	byte[] job2Arr;
	boolean allHouses = false;
	boolean extraJobs = false;
	private ArrayList<byte[]> jobCodes = new ArrayList<byte[]>();
	private ArrayList<byte[]> workplaceCodes = new ArrayList<byte[]>();
	boolean hasBuilder = false, hasCutter = false, hasMiner = false;
	Integer[] jobs = {25,26,27,31,30,32,33,34,35,23,24,28,37,36,29,38,39,40,41,42};
	public buildingRandomizer(byte[] data, boolean allHouses, boolean extraJobs,int seed)
	{
		random = new Random(seed);
		this.allHouses = allHouses;
		this.extraJobs = extraJobs;
		bytes = data;
		extractData(data);
		extractLine();
		randomize();
		packLines();
		packAll();
	}
	public buildingRandomizer(byte[] data, boolean allHouses, boolean extraJobs)
	{
		random = new Random((int)(Math.random()*Integer.MAX_VALUE));
		this.allHouses = allHouses;
		this.extraJobs = extraJobs;
		bytes = data;
		extractData(data);
		extractLine();
		randomize();
		packLines();
		packAll();
	}
	private void packAll()
	{
		byte[] bytes;
		bytes = lines.get(0);
		for(int line = 1; line < lines.size(); line++)
		{
			byte[] temp = new byte[lines.get(line).length + bytes.length + 3];
			for(int n = 0; n < bytes.length; n++)
			{
				temp[n] = bytes[n];
			}
			temp[bytes.length ] = 0x3B;
			temp[bytes.length + 1] = 0x0D;
			temp[bytes.length + 2] = 0x0A;
			for(int n = 0; n < lines.get(line).length; n++)
			{
				temp[n+bytes.length+3] = lines.get(line)[n];
			}
			bytes = temp;
		}
		byte[] temp = new byte[bytes.length + 3];
		for(int n = 0; n < temp.length-3; n++)
		{
			temp[n] = bytes[n];
		}
		temp[temp.length-3]=0x3B;
		temp[temp.length-2] = 0x0D;
		temp[temp.length-1] = 0x0A;
		this.bytes = temp;
	}
	private void packLines()
	{
		for(int j = 0; j < parts.size() && parts.get(j).length > 0; j++)
		{
			byte[] tempLine = parts.get(j)[0];
			for(int i = 1; i < parts.get(j).length; i++)
			{
				byte[] tempPart = parts.get(j)[i];
				byte[] temp = new byte[tempLine.length + tempPart.length + 1];
				for(int n = 0; n< tempLine.length; n++)
				{
					temp[n] = tempLine[n];
				}
				temp[tempLine.length] = 0x2C;
				for(int n = 0; n+tempLine.length+1 < temp.length; n++)
				{
					temp[n+tempLine.length+1] = tempPart[n];
				}
				tempLine = temp;
			}
			lines.set(j, tempLine);

		}
	}
	public byte[] getBytes()
	{
		return bytes;
	}
	private void makeJobCodeArray()
	{
		jobCodes.add("31".getBytes());//farmer
		jobCodes.add("31".getBytes());//farmer
		jobCodes.add("31".getBytes());//farmer
		jobCodes.add("25".getBytes());//grunt
		jobCodes.add("33".getBytes());//build1
		jobCodes.add("34".getBytes());//build2
		jobCodes.add("35".getBytes());//build3
		jobCodes.add("30".getBytes());//lumber
		jobCodes.add("32".getBytes());//miner
		jobCodes.add("32".getBytes());//miner
		jobCodes.add("32".getBytes());//miner
		jobCodes.add("32".getBytes());//miner
		jobCodes.add("26".getBytes());//hardened soldier
		if(extraJobs)
		{
			jobCodes.add("27".getBytes());//steel
			jobCodes.add("39".getBytes());//egg
			jobCodes.add("40".getBytes());//caster
			jobCodes.add("41".getBytes());//soba
			jobCodes.add("42".getBytes());//champ
			jobCodes.add("29".getBytes());//wizard
			jobCodes.add("38".getBytes());//doctor
			if(allHouses)
			{
				jobCodes.add("38".getBytes());//doctor
				//kid
			}
		}
		//bad attackers
		jobCodes.add("37".getBytes());//chef
		jobCodes.add("37".getBytes());//chef
		jobCodes.add("28".getBytes());//bowman
		jobCodes.add("36".getBytes());//merchant
		jobCodes.add("36".getBytes());//merchant
		jobCodes.add("36".getBytes());//merchant
		jobCodes.add("24".getBytes());//man
	}
	private void makeHouseArray()
	{
		if(allHouses){
			workplaceCodes.add("DAT2 10106".getBytes());//Liams House
			workplaceCodes.add("DAT2 10107".getBytes());//Verdes Hosue
		}
		workplaceCodes.add("DAT2 10350".getBytes());//frm1
		workplaceCodes.add("DAT2 10169".getBytes());//soldier1
		//Castle Town pt 2
		workplaceCodes.add("DAT2 10168".getBytes());//Regular Carpenter Hut
		//Grassland Town
		workplaceCodes.add("DAT2 10134".getBytes());//Animal Hunter Hut
		if(allHouses)
		{
			//Castle Town pt 2
			workplaceCodes.add("DAT2 10100".getBytes());//Wooden House A
			workplaceCodes.add("DAT2 10109".getBytes());//Wooden and Stone House B
			workplaceCodes.add("DAT2 10112".getBytes());//Wooden and Stone House E
			workplaceCodes.add("DAT2 10212".getBytes());//Corobo's House
			//Grassland Town
			workplaceCodes.add("DAT2 10132".getBytes());//Poor House C
			workplaceCodes.add("DAT2 10135".getBytes());//Fishing House
			workplaceCodes.add("DAT2 10130".getBytes());//Poor House A
			workplaceCodes.add("DAT2 10105".getBytes());//Wooden House F
		}
		//Farmers Town
		workplaceCodes.add("DAT2 10138".getBytes());//farmers
		workplaceCodes.add("DAT2 10137".getBytes());//farmers
		workplaceCodes.add("DAT2 10171".getBytes());//merchants
		workplaceCodes.add("DAT2 10174".getBytes());//merchants
		workplaceCodes.add("DAT2 10175".getBytes());//merchants
		workplaceCodes.add("DAT2 10136".getBytes());//jack
		if(allHouses)
		{
			workplaceCodes.add("DAT2 10103".getBytes());//Wooden House D
			workplaceCodes.add("DAT2 10162".getBytes());//Small Florist
			workplaceCodes.add("DAT2 10102".getBytes());//Wooden House C
			workplaceCodes.add("DAT2 10139".getBytes());//Ranch A
			workplaceCodes.add("DAT2 10170".getBytes());//Shop 1
		}
		//Stone City
		workplaceCodes.add("DAT2 10146".getBytes());//Mine
		workplaceCodes.add("DAT2 10147".getBytes());//Mine
		workplaceCodes.add("DAT2 10156".getBytes());//Mega Carpenters Hut
		//Soldier Town
		workplaceCodes.add("DAT2 10151".getBytes());//Hardened Soldier's Hut
		if(allHouses)
		{
			workplaceCodes.add("DAT2 10116".getBytes());//Stone House C
			workplaceCodes.add("DAT2 10172".getBytes());//shop 3
			workplaceCodes.add("DAT2 10110".getBytes());//wood and stone house c
			workplaceCodes.add("DAT2 10173".getBytes());//shop 4
			workplaceCodes.add("DAT2 10167".getBytes());//tailor B
			workplaceCodes.add("DAT2 10143".getBytes());//Bar
			workplaceCodes.add("DAT2 10117".getBytes());//stone house d
			workplaceCodes.add("DAT2 10118".getBytes());//^ e
		}
		//Royal City
		workplaceCodes.add("DAT2 10150".getBytes());//School
		workplaceCodes.add("DAT2 10153".getBytes());//Chef 1
		if(allHouses)
		{
			workplaceCodes.add("DAT2 10163".getBytes());//Large Florist Hut
			workplaceCodes.add("DAT2 10120".getBytes());//Skyscraper House A
			workplaceCodes.add("DAT2 10121".getBytes());//Skyscraper House B
			workplaceCodes.add("DAT2 10122".getBytes());//Skyscraper House C
			workplaceCodes.add("DAT2 10123".getBytes());//Skyscraper House D
			workplaceCodes.add("DAT2 10124".getBytes());//Skyscraper House E
			workplaceCodes.add("DAT2 10125".getBytes());//Skyscraper House F
		}
		
		//Gourmet Town
		workplaceCodes.add("DAT2 10152".getBytes());//Chef 2
		if(allHouses)
		{
			workplaceCodes.add("DAT2 10154".getBytes());//bake A
			workplaceCodes.add("DAT2 10155".getBytes());//bake B
			workplaceCodes.add("DAT2 10142".getBytes());//Orchard B
			workplaceCodes.add("DAT2 10141".getBytes());//Orchard A
			workplaceCodes.add("DAT2 10113".getBytes());//Wooden and Stone House F
		}
		//Glamour Town
		if(extraJobs||allHouses)
		{
			workplaceCodes.add("DAT2 10160".getBytes());//Clinic
			workplaceCodes.add("DAT2 10161".getBytes());//Hospital
		}
		if(allHouses)
		{
			workplaceCodes.add("DAT2 10158".getBytes());//Theater
			workplaceCodes.add("DAT2 10128".getBytes());//Rich House C
			workplaceCodes.add("DAT2 10164".getBytes());//Jeweler A
			workplaceCodes.add("DAT2 10165".getBytes());//Jeweler B
			workplaceCodes.add("DAT2 10126".getBytes());//Rich House A
			workplaceCodes.add("DAT2 10127".getBytes());//Rich House B
		}
		//Miners Town
		workplaceCodes.add("DAT2 10148".getBytes());//Mine
		workplaceCodes.add("DAT2 10149".getBytes());//Mine
		workplaceCodes.add("DAT2 10241".getBytes());// Giga Carpenter's Hut
		if(allHouses)
		{
			workplaceCodes.add("DAT2 10115".getBytes());//Stone House B
			workplaceCodes.add("DAT2 10114".getBytes());//Stone House A
			workplaceCodes.add("DAT2 10119".getBytes());//Stone House F
		}
		//Magic Town
		if(extraJobs||allHouses)
		{
			workplaceCodes.add("DAT2 10176".getBytes());//Wizard
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
		byte[] currentJobCode = jobCodes.remove((int)(random.nextDouble(0,1)*usefulJobIndex));
		checkJobCode(currentJobCode);
		int currentBuildingIndex = -1;
		if(allHouses)
		{
			currentBuildingIndex = (int)(random.nextDouble(0,1)*usefulJobBy);
			if(currentBuildingIndex<2)//if it is one of the og houses
			{
				jobArr = currentJobCode;
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
				job2Arr = currentJobCode;
				workplaceCodes.remove(0);//remove the other starting house
				
				//decrease all by 2 because 2 were removed
				buildingJobBy --;
				cuttingJobBy --;
				miningJobBy --;
				buildingJobBy --;
				cuttingJobBy --;
				miningJobBy --;
			}
			else
			{
				jobArr = currentJobCode;
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
				usefulJobBy--;
				usefulJobIndex--;
				
				currentJobCode = jobCodes.remove((int)(random.nextDouble(0,1)*usefulJobIndex));
				checkJobCode(currentJobCode);
				currentBuildingIndex = (int)(random.nextDouble(0,1)*usefulJobBy);
				job2Arr = currentJobCode;
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
				
				workplaceCodes.remove(0);
				workplaceCodes.remove(0);//remove the two starting homes
				buildingJobBy -=4;
				cuttingJobBy -=4;
				miningJobBy -=4;
			}
		}
		else
		{
			jobArr = currentJobCode;
			setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			usefulJobBy--;
			usefulJobIndex--;
			
			currentJobCode = jobCodes.remove((int)(random.nextDouble(0,1)*usefulJobIndex));
			checkJobCode(currentJobCode);
			currentBuildingIndex = (int)(Math.random()*usefulJobBy);
			job2Arr = currentJobCode;
			setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			
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
			
			currentBuildingIndex = (int)(random.nextDouble(0,1)*buildingJobBy);
			
			if(extraJobs&&num<oddsForCraftian)
			{
				currentJobCode = getJobCode("42".getBytes());
				if(currentJobCode!=null)
				{
					checkJobCode(currentJobCode);
					setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
				}
			}
			else if(num<oddsForGiga)
			{
				currentJobCode = getJobCode("35".getBytes());
				checkJobCode(currentJobCode);
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			}
			else if(num<oddsForMega)
			{
				currentJobCode = getJobCode("34".getBytes());
				checkJobCode(currentJobCode);
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			}
			else
			{
				currentJobCode = getJobCode("33".getBytes());
				checkJobCode(currentJobCode);
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			}
			cuttingJobBy --;
			miningJobBy --;
		}
		if(hasCutter==false)
		{
			double oddsForCraftian = .01;
			double num = random.nextDouble(0,1);
			
			currentBuildingIndex = (int)(random.nextDouble(0,1)*cuttingJobBy);
			
			if(extraJobs&&num<oddsForCraftian)
			{
				currentJobCode = getJobCode("42".getBytes());
				if(currentJobCode!=null)
				{
					checkJobCode(currentJobCode);
					setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
				}
				hasCutter = false;
			}
			else
			{
				currentJobCode = getJobCode("30".getBytes());
				checkJobCode(currentJobCode);
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			}
			miningJobBy --;
		}
		if(hasCutter==false)
		{
			double oddsForCraftian = .01;
			double num = random.nextDouble(0,1);
			
			currentBuildingIndex = (int)(random.nextDouble(0,1)*miningJobBy);
			
			if(extraJobs&&num<oddsForCraftian)
			{
				currentJobCode = getJobCode("42".getBytes());
				if(currentJobCode!=null)
				{
					setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
				}
			}
			else
			{
				currentJobCode = getJobCode("32".getBytes());
				setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
			}
		}
		while(jobCodes.size()>0)
		{
			currentBuildingIndex = (int)(random.nextDouble(0,1)*workplaceCodes.size());
			currentJobCode = jobCodes.remove(0);
			setBuildingJob(workplaceCodes.remove(currentBuildingIndex), currentJobCode);
		}
		//clear out remaining values
		while(workplaceCodes.size()>0)
		{
			setBuildingJob(workplaceCodes.remove(0), "-1".getBytes());
		}
	}
	private byte[] getJobCode(byte[] ret) 
	{
		for(int i = 0; i <jobCodes.size(); i++)
		{
			if(same(jobCodes.get(i),ret))
			{
				jobCodes.remove(i);
				return ret;
			}
		}
		return null;
	}
	private void checkJobCode(byte[] currentJobCode) 
	{
		//set the boolean flags if job meets requirements
		if(same(currentJobCode,"42".getBytes()))
		{
			hasBuilder = true;
			hasCutter = true;
			hasMiner = true;
			return;
		}
		if(same(currentJobCode,"35".getBytes()))
		{
			hasBuilder = true;
			return;
		}
		if(same(currentJobCode,"34".getBytes()))
		{
			hasBuilder = true;
			return;
		}
		if(same(currentJobCode,"33".getBytes()))
		{
			hasBuilder = true;
			return;
		}
		if(same(currentJobCode,"30".getBytes()))
		{
			hasCutter = true;
			return;
		}
		if(same(currentJobCode,"32".getBytes()))
		{
			hasMiner = true;
			return;
		}
	}
	private void setBuildingJob(byte[] currentBuildingHeader, byte[] currentJobCode) 
	{
		int index = getIndexOfBuilding(currentBuildingHeader);
		byte[][] buildingLine = parts.get(index);
		buildingLine[1] = currentJobCode;
		buildingLine[4] = currentJobCode;
		parts.set(index, buildingLine);
		
		
		byte[][] buildingLine1 = parts.get(index-1);
		if(buildingLine1[0].toString().indexOf("DAT ")!=-1)
		{
			if(same(currentJobCode,"-1".getBytes()))buildingLine1[1] = "1".getBytes();
			else buildingLine1[1] = "2".getBytes();
		}
		parts.set(index-1, buildingLine1);
	}
	private int getIndexOfBuilding(byte[] currentBuildingHeader)
	{
		for(int i = 0; i < parts.size(); i++)
		{
			if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],currentBuildingHeader))
			{
				return i;
			}
		}
		return -1;
	}
	private boolean same(byte[] one, byte[] two) {
		if(one.length != two.length) return false;
		for(int i = 0; i < one.length; i++)
		{
			if(one[i]!=two[i]) return false;
		}
		return true;
	}
	public int freeJobs(int place)
	{
		byte[] freeJob = new byte[1];
		if(place == 1)
		{
			freeJob = jobArr;
		}
		else freeJob = job2Arr;
		String jobCode = "" + (char)freeJob[0]+(char)freeJob[1];
		
		int code = Integer.parseInt(jobCode);
		for(int i = 0; i < jobs.length; i++)
		{
			if(jobs[i]==code)
			{
				return i;
			}
		}
		return -1;
	}

		
	private void extractData(byte[] data)
	{
		byte[] line;
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] == 0x3B)//if it is a semicolon
			{
				line = new byte[i];
				line = Arrays.copyOfRange(data, 0, i);//add everything before to a array
				lines.add(line);//and add the array to the list
				data = Arrays.copyOfRange(data, i+3, data.length);//then make a new array that contains everything else and sort through that (plus 3 to skip ; and enter)
				i = 0;//then start from the beginning
			}
		}
	}
	private void extractLine()
	{
		ArrayList<byte[]> line;
		byte[] part;
		byte[] data;
		byte[][] lineBytes;
		for(int i = 0; i < lines.size(); i++)
		{
			line = new ArrayList<byte[]>();
			data = lines.get(i);
			for(int j = 0; j < data.length; j++)
			{
				if(data[j] == 0x2C)//if it is a comma
				{
					part = Arrays.copyOfRange(data, 0, j);
					line.add(part); //and add the array to the list
					data = Arrays.copyOfRange(data, j+1, data.length); //then make a new array that contains everything else and sort through that
					j = 0;//then start from the beginning
				}
				if(j == data.length-1)
				{
					part = Arrays.copyOfRange(data, 0, j+1);
					line.add(part); //and add the array to the list
				}
			}
			lineBytes = new byte[line.size()][];
			for(int j = 0; j < line.size(); j++)
			{
					lineBytes[j]=line.get(j);
			}
			parts.add(lineBytes);
			
		}
	}

}
