package LKSFileManagerTools;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MobGroup //probably who has attack bonus
{
	int groupIndex; //First 2 Bytes
	int num1; //Next 2 Bytes
	int objectIndex; //Next 2 Bytes
	int objectCount; //Next 2 Bytes
	int num4; //Next 2 Bytes
	int groupNumber; //Next 2 Bytes
	int num6; //Next 2 Bytes
	int num7; //Next 2 Bytes
	float num8; //Next 4 Bytes
	int num9; //Next 2 Bytes
	boolean changableIndex = false;
	public MobGroup(byte[] data)
	{
		groupIndex = (int)getShort(data, 0);
		num1 = (int)getShort(data, 2);
		objectIndex = (int)getShort(data, 4);
		objectCount = (int)getShort(data, 6);
		num4 = (int)getShort(data, 8);
		groupNumber = (int)getShort(data, 10);
		num6 = (int)getShort(data, 12);
		num7 = (int)getShort(data, 14);
		num8 = (ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(16));
	}
	private int getShort(byte[] data, int index)
	{
		if(data==null)
		{
			return -1;
		}
		if(data.length<index+2)
		{
			return -1;
		}
		int ret = (int)data[index];
		if(ret<0)ret+=256;
		ret*=256;
		int ret2 =(int)data[index+1];
		if(ret2<0)ret2+=256;
		
		ret+=ret2;
		
		
		if(ret==65535) return -1;
		return (ret);
	}
	public boolean groupIndex(int code)
	{
		if(code==groupIndex)return true;
		return false;
	}
	public MobGroup(int num12, int num2, int objectIndex, int num11, int num122, int num13, int num14, int num15, float num16) 
	{
		groupIndex = num12;
		num1 = num2;
		this.objectIndex = objectIndex;
		objectCount = num11;
		num4 = num122;
		groupNumber = num13;
		num6 = num14;
		num7 = num15;
		num8 = num16;
	}
	public MobGroup(int num12, int num2, int objectIndex, int num11, int num122, int num13, int num14, int num15, float num16, boolean b) {
		groupIndex = num12;
		num1 = num2;
		this.objectIndex = objectIndex;
		objectCount = num11;
		num4 = num122;
		groupNumber = num13;
		num6 = num14;
		num7 = num15;
		num8 = num16;
		changableIndex = b;
	}
	public boolean generatedIndex()
	{
		return changableIndex;
	}
	public static byte[] mergeArrays(byte[] main, byte[] add)
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
	public String toString()
	{
		return ""+groupIndex +", "+num1 +", "+objectIndex +", "+objectCount +", "+num4 +", "+groupNumber +", "+num6 +", "+num7 +", "+num8 +"\n";
	}
	public byte[] toBytes() 
	{
		byte[] ret = toByteArr(groupIndex,2);
		ret = mergeArrays(ret, toByteArr(num1,2));
		ret = mergeArrays(ret, toByteArr(objectIndex,2));
		ret = mergeArrays(ret, toByteArr(objectCount,2));
		ret = mergeArrays(ret, toByteArr(num4,2));
		ret = mergeArrays(ret, toByteArr(groupNumber,2));
		ret = mergeArrays(ret, toByteArr(num6,2));
		ret = mergeArrays(ret, toByteArr(num7,2));
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(num8).array());
		return ret;
	}
	public int getObjectCount()
	{
		return objectCount;
	}
	private byte[] toByteArr(int input, int arrLength) 
	{
		if(input>=0)
		{
			byte[] ret = new byte[arrLength];
			for(int i = 1; i<=arrLength; i++)
			{
				ret[arrLength-i] = (byte) (input%256);
				input/=256;
				
				
			}
			return ret;
		}
		if(input==-1)
			return new byte[]{(byte) 0xff, (byte) 0xff};
		return toByteArr(65536+input, arrLength);
	}
	public String bMos() 
	{
		return "\tMonster Group: "+ groupIndex + ", "+num1+", "+num4 +", "+groupNumber +", "+num6 +", "+num7 +", "+num8 +"\n";
	}	
	public int getObjectIndex() 
	{
		return objectIndex;
	}
	public int getCode() {
		// TODO Auto-generated method stub
		return groupIndex;
	}
	public int getNumber() {
		// TODO Auto-generated method stub
		return groupNumber;
	}
	public String bMos2() 
	{
		return "Unsorted Group: "+groupIndex +", "+num1+", "+num4 +", "+groupNumber +", "+num6 +", "+num7 +", "+num8+"\n";
	}
	public void newCode() 
	{
		groupIndex = (int)(Short.MAX_VALUE*Math.random());
	}
}
