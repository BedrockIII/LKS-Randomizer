package Randomization;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import MSDBManager.MobGroup;
import MSDBManager.MobObject;

public class EnemyRandomizer 
{
	MobObject[] randomizedList = new MobObject[0];
	ArrayList<MobObject> Objects = new ArrayList<MobObject>();
	ArrayList<MobGroup> Group = new ArrayList<MobGroup>();
	static Random random = new Random();
	boolean includeObjects = true;
	boolean objectsSeperate = false;
	public EnemyRandomizer(byte[] data, int seed, byte[] groupData)
	{
		for(int i = 4; i<data.length; i+=40)
		{
			Objects.add(new MobObject(Arrays.copyOfRange(data, i, i+40)));
		}
		for(int i = 4; i<groupData.length; i+=20)
		{
			Group.add(new MobGroup(Arrays.copyOfRange(groupData, i, i+20)));
		}
		randomizedList = new MobObject[Objects.size()];
		random = new Random(seed);
		
		
		
		
		
		
		setConsistantValsDayOne();
		setConsistantValsBossesFull();
		//setConsistantValsBossesOnly();
		setConsistantValsFixObjects();
		
		
		
		randomizeVals();
		if(objectsSeperate)
		{
			randomizeObjectVals();
		}
	}
	private void randomizeObjectVals() {
		// TODO Auto-generated method stub
		
	}
	private void setConsistantValsBossesOnly() 
	{
		final int[] modCodes = {310, 504, 9132, 9133,50052,50053,50054,50055,50056,50057,50058,50059,15,51,50,49,48};
		
		for(int i = 0; i<Objects.size(); i++)
		{
			if(isInArray(modCodes, Objects.get(i).getModCode()))
			{
				randomizedList[i]=Objects.get(i);
			}
		}
	}
	private void setConsistantValsFixObjects() 
	{
		for(int i = randomizedList.length-1; i>=0; i--)
		{
			if(randomizedList[i]!=null)
			{
				Objects.remove(i);
			}
		}
	}
	private void randomizeVals() 
	{
		int listIndex = 0; 
		int objectIndex = 0;
		while(Objects.size()>0)
		{
			while(randomizedList[listIndex]!=null)
			{
				listIndex++;
			}
			objectIndex = (int)random.nextDouble(0,Objects.size());
			randomizedList[listIndex] = Objects.remove(objectIndex);
		}
	}
	private void setConsistantValsDayOne()
	{
		//1st day stuff
				randomizedList[0]=Objects.get(0);
				randomizedList[4]=Objects.get(4);
				randomizedList[5]=Objects.get(5);
				randomizedList[6]=Objects.get(6);
	}
	private void setConsistantValsBossesFull()
	{
		final int[] safeCodes = {20000,21000,22000,23000,24000,25000,26000,27000,12000};
		final int[] modCodes = {310, 504, 9133, 50053, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136};
		
		//save other Guardians
		for(int i = 0; i<Group.size(); i++)
		{//Group.get(i).getNumber()
			if(isInArray(safeCodes, Group.get(i).getGroupNumber()))
			{
				for(int j = Group.get(i).getObjectIndex(); j < Group.get(i).getObjectIndex()+Group.get(i).getObjectCount(); j++)
				{
					randomizedList[j]=Objects.get(j);
				}
			}
		}
		
		for(int i = 0; i<Objects.size(); i++)
		{
			if(isInArray(modCodes, Objects.get(i).getModCode()))
			{
				randomizedList[i]=Objects.get(i);
			}
		}
	}
	private boolean isInArray(int[] safeCodes, int number) 
	{
		for(int i = 0; i < safeCodes.length; i++)
		{
			if(safeCodes[i]==number)
			{
				return true;
			}
		}
		return false;
	}
	public byte[] toArr()
	{
		byte[] ret = toByteArr(1,2);
		ret = mergeArrays(ret, toByteArr(randomizedList.length, 2));
		
		for( int i = 0; i < randomizedList.length; i++)
		{
			
			ret = mergeArrays(ret, randomizedList[i].toBytes());
		}
		return ret;
	}
	private static byte[] toByteArr(int input, int arrLength) 
	{
		byte[] ret = new byte[arrLength];
		for(int i = 1; i<=arrLength; i++)
		{
			ret[arrLength-i] = (byte) (input%256);
			input/=256;
		}
		return ret;
	}
	private static byte[] mergeArrays(byte[] main, byte[] add)
	{
		if(add==null) return main;
		if(main==null) return add;
		byte[] ret = new byte[main.length+add.length];
		for(int i = 0; i < main.length; i++)
		{
			ret[i] = main[i];
		}
		for(int i = 0; i < add.length; i++)
		{
			ret[i+main.length] = add[i];
		}
		return ret;
	}
}
