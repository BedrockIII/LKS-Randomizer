

import java.util.ArrayList;
import java.util.Arrays;

public class buildingRandomizer {
	private ArrayList<byte[]> lines = new ArrayList<byte[]>();
	private ArrayList<byte[][]> parts = new ArrayList<byte[][]>();
	private byte[] bytes;
	final int random1 = (int)(Math.random() * 13);
	final int random2 = (int)(Math.random() * 12);
	final int random3 = (int)(Math.random() * 18);
	final int random4 = (int)(Math.random() * 17);
	byte[] jobArr;
	byte[] job2Arr;
	Integer[] jobs = {31,31,31,25,33,34,35,30,32,32,32,32,26,37,37,28,36,36,36,24,38,29,27,39,40,41,42};
	public buildingRandomizer(byte[] data, boolean allHouses, boolean extraJobs)
	{
		bytes = data;
		extractData(data);
		extractLine();
		randomize(allHouses, extraJobs);
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
	public void randomize(boolean allHouses, boolean extraJobs)
	{
		//boolean firstJob = false;
		//boolean secondJob = false;
		boolean carpenter = false;
		boolean cutter = false;
		ArrayList<byte[]> jobCodes = new ArrayList<byte[]>();

		ArrayList<byte[]> workplaceCodes = new ArrayList<byte[]>();
		
		jobCodes.add(new byte[]{0x33,0x31});//farmer
		jobCodes.add(new byte[]{0x33,0x31});//farmer
		jobCodes.add(new byte[]{0x33,0x31});//farmer
		jobCodes.add(new byte[]{0x32,0x35});//grunt
		jobCodes.add(new byte[]{0x33,0x33});//build1
		jobCodes.add(new byte[]{0x33,0x34});//build2
		jobCodes.add(new byte[]{0x33,0x35});//build3
		jobCodes.add(new byte[]{0x33,0x30});//lumber
		jobCodes.add(new byte[]{0x33,0x32});//miner
		jobCodes.add(new byte[]{0x33,0x32});//miner
		jobCodes.add(new byte[]{0x33,0x32});//miner
		jobCodes.add(new byte[]{0x33,0x32});//miner
		jobCodes.add(new byte[]{0x32,0x36});//hardened soldier
		//bad attackers
		jobCodes.add(new byte[]{0x33,0x37});//chef
		jobCodes.add(new byte[]{0x33,0x37});//chef
		jobCodes.add(new byte[]{0x32,0x38});//bowman
		jobCodes.add(new byte[]{0x33,0x36});//merchant
		jobCodes.add(new byte[]{0x33,0x36});//merchant
		jobCodes.add(new byte[]{0x33,0x36});//merchant
		jobCodes.add(new byte[]{0x32,0x34});//man
		if(extraJobs)
		{
			jobCodes.add(new byte[]{0x33,0x38});//doctor
			jobCodes.add(new byte[]{0x33,0x38});//doctor
			jobCodes.add(new byte[]{0x32,0x39});//wizard
			jobCodes.add(new byte[]{0x32,0x37});//steel
			//jobCodes.add(new byte[]{0x33,0x39});//egg
			//jobCodes.add(new byte[]{0x34,0x30});//caster
			//jobCodes.add(new byte[]{0x34,0x31});//soba
			//jobCodes.add(new byte[]{0x34,0x32});//champ
		}
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x33, 0x35, 0x30});//frm1
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x36, 0x39});//soldier1
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x36, 0x38});//carp
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x33, 0x34});//hunter
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x33, 0x38});//farmers
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x33, 0x37});
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x37, 0x31});//merchants
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x37, 0x34});
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x37, 0x34});
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x33, 0x36});//jack
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x34, 0x36});//miners
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x34, 0x37});
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x35, 0x36});//build2
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x35, 0x30});//school
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x35, 0x33});//chef 1
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x35, 0x31});//hardened
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x35, 0x32});//chef 2
		
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x34, 0x38});//miners2
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x34, 0x39});
		workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x32, 0x34, 0x31});//gig
		
		if(extraJobs)
		{
			//workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x36, 0x30});//doctor
			//workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x36, 0x31});//doctor
			//workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x34, 0x37, 0x34});//wizard
			//workplaceCodes.add(new byte[] {0x44, 0x41, 0x54, 0x32, 0x20, 0x31, 0x30, 0x31, 0x36, 0x34});//steel stand-in
			//jobCodes.add(new byte[]{0x33,0x39});//egg
			//jobCodes.add(new byte[]{0x34,0x30});//caster
			//jobCodes.add(new byte[]{0x34,0x31});//soba
			//jobCodes.add(new byte[]{0x34,0x32});//champ
		}
		
		if(allHouses)
		{
			//TODO, I AM NOT MAKING A LIST OF ALL THE HOMES BY 4/22/23
		}
		else
		{
			jobArr = new byte[0];
			job2Arr = new byte[0];
			for(int i = 0; i < parts.size(); i++)//farm home 1
			{
				if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],workplaceCodes.get(0)))
				{
					jobArr = jobCodes.get(random1);
					workplaceCodes.remove(0);
					carpenter = random1 == 4 || random1 == 5 || random1 ==6;
					cutter = random1 == 7;
					byte[][] temp1 = parts.get(i);
					temp1[4] = jobCodes.remove(random1);
					parts.set(i, temp1);
				}
				
			}
			for(int i = 0; i < parts.size(); i++)//guard home
			{
				if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],workplaceCodes.get(0)))
				{
					job2Arr = jobCodes.get(random2);
					workplaceCodes.remove(0);
					if(!carpenter)
					{
						if(random1 < 4) carpenter = random2 == 4 || random2 == 5 || random2 ==3;
						else carpenter = random2 == 4 || random2 == 5 || random2 ==6;
					}
					if(!cutter)
					{
						if(random1 < 7) cutter = random2 == 6;
						else cutter = random2 == 7;
					}
					byte[][] temp2 = parts.get(i);
					temp2[4] = jobCodes.remove(random2);
					parts.set(i, temp2);
					break;
				}
				
			}
			for(int i = 0; i < parts.size(); i++)//carpenter home
			{
				if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],workplaceCodes.get(0)))
				{
					workplaceCodes.remove(0);
					if(!carpenter)
					{
						if(random1 < 4) //fisrt shifts it
						{
							if(random2 < 3) carpenter = random3 == 4 || random3 == 2 || random3 == 3;//2 shifts
							else carpenter = random3 == 4 || random3 == 5 || random3 ==3; // 1 shift
						}
						else if(random2 < 4) carpenter = random3 == 4 || random3 == 5 || random3 ==3; // 1 shift
						else carpenter = random3 == 4 || random3 == 5 || random3 ==6; // no shifts
					}
					if(!cutter)
					{
						if(random1 < 7) 
							{
								if(random2 < 6) cutter = random3 == 5; //2 shifts
								else cutter = random3 == 6; // 1 shift
							}
						else if (random2 < 7) cutter = random3 == 6; // 1 shift
						else cutter = random3 == 7;
					}
					byte[][] temp2 = parts.get(i);
					temp2[4] = jobCodes.remove(random3);
					parts.set(i, temp2);
				}
				
			}
			for(int i = 0; i < parts.size(); i++)//hunter home
			{
				if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],workplaceCodes.get(0)))
				{
					byte[][] temp2 = parts.get(i);
					int ranB = (int)(Math.random() * 3) + 4;
					workplaceCodes.remove(0);
					if(!carpenter)
					{
						if(random1 < 4)
						{
							ranB --;
							if(random2 < 3)
							{
								ranB --;
								if(random3 < 2)    ranB --;
							} else if(random3 < 3) ranB --;
						} else if(random2 < 4)
						{
							ranB --;
							if(random3 < 3)        ranB --;
						} else if(random3 < 4)     ranB --;
						carpenter = true;
						temp2[4] = jobCodes.remove(ranB);
						parts.set(i, temp2);
					} else if(!cutter)
					{
						byte[][] temp3 = parts.get(i);
						for(int n = 0; n < jobCodes.size(); n++)
						{
							if(Arrays.equals(jobCodes.get(n), new byte[]{0x33,0x30})) 
							{
								cutter = random4 == n;
								if(cutter)temp3[4] = jobCodes.remove(n);
							}
						}
						parts.set(i, temp3);
					}
				}
			}
			if(!cutter)
			{
				int iRanOuttaNames = (int)(Math.random()*6);
				for(int i = 0; i < parts.size(); i++)
				{
					if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],workplaceCodes.get(iRanOuttaNames)))
					{;
						workplaceCodes.remove(iRanOuttaNames);
						cutter=true;
						byte[][] temp3 = parts.get(i);
						for(int n = 0; n < jobCodes.size(); n++)
						{
							if(Arrays.equals(jobCodes.get(n), new byte[]{0x33,0x30})) temp3[4] = jobCodes.remove(n);
						}
						parts.set(i, temp3);
					}
				}
			}
			while(workplaceCodes.size()>0)
			{
				int rem = (int)(workplaceCodes.size()*Math.random());
				int ram = (int)(jobCodes.size()*Math.random());
				for(int i = 0; i < parts.size(); i++)
				{
					if(parts.get(i).length > 0 && parts.get(i)[0].length == 10 && Arrays.equals(parts.get(i)[0],workplaceCodes.get(rem)))
					{
						workplaceCodes.remove(rem);
						byte[][] temp = parts.get(i);
						temp[4] = jobCodes.get(ram);
						parts.set(i, temp);
						break;
					}
				}
			}
		}
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
		ArrayList<byte[]> jobCodes2 = new ArrayList<byte[]>();
		jobCodes2.add(new byte[]{0x32,0x35});//grunt
		jobCodes2.add(new byte[]{0x32,0x36});//hardened soldier
		jobCodes2.add(new byte[]{0x30,0x30,0x32,0x37});//steel
		jobCodes2.add(new byte[]{0x33,0x31});//farmer
		jobCodes2.add(new byte[]{0x33,0x30});//lumber
		jobCodes2.add(new byte[]{0x33,0x32});//miner
		jobCodes2.add(new byte[]{0x33,0x33});//build1
		jobCodes2.add(new byte[]{0x33,0x34});//build2
		jobCodes2.add(new byte[]{0x33,0x35});//build3
		jobCodes2.add(new byte[]{0x32,0x33});//kid
		jobCodes2.add(new byte[]{0x32,0x38});//bowman
		jobCodes2.add(new byte[]{0x32,0x34});//man
		jobCodes2.add(new byte[]{0x33,0x37});//chef
		jobCodes2.add(new byte[]{0x33,0x36});//merchant
		jobCodes2.add(new byte[]{0x33,0x38});//doctor
		jobCodes2.add(new byte[]{0x32,0x39});//wizard
		jobCodes2.add(new byte[]{0x33,0x39});//egg
		jobCodes2.add(new byte[]{0x34,0x30});//caster
		jobCodes2.add(new byte[]{0x30,0x34,0x31});//soba
		jobCodes2.add(new byte[]{0x30,0x34,0x32});//champ
		if(place==1)
		{
			for(int i = 0; i < jobCodes2.size(); i++)
			{
				if(same(jobCodes2.get(i), jobArr)) return i;
			}
		}
		if(place==1)
		{
			for(int i = 0; i < jobCodes2.size(); i++)
			{
				if(same(jobCodes2.get(i), job2Arr)) return i;
			}
		}
		return 0;
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
