package LKSFileManagerTools;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MobObject 
{
	float num0; //First 2 Bytes //these first 3 MAY be the offsets for the target points, like where onii men throw pots
	float num1; //Next 2 Bytes
	float num2; //Next 2 Bytes
	float num3; //Next 2 Bytes//probably the actual rotation
	float num4; //Next 2 Bytes
	int mobModNumber; //Next 2 Bytes
	int num5b; //Next 2 Bytes
	float num6; //Next 2 Bytes, normally same as num4
	float rotation; //Next 2 Bytes
	int num8; //Next 2 Bytes
	int num9; //Next 2 Bytes
	int num10; //Next 2 Bytes
	int num11; //Next 2 Bytes
	public MobObject(byte[] data)
	{
		num0 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(0);
		num1 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(4);
		num2 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(8);
		num3 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(12);
		num4 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(16);
		mobModNumber = (int)getShort(data, 20);
		num5b = (int)getShort(data, 22);
		num6 = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(24);
		rotation = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getFloat(28);
		num8 = (int)getShort(data, 32);
		num9 = (int)getShort(data, 34);
		num10 = (int)getShort(data, 36);
		num11 = (int)getShort(data, 38);
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
		if(ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN).getShort(0)==-1)
		{
			return -1;
		}
		int ret = (int)data[index];
		if(ret<0)ret+=256;
		ret*=256;
		int ret2 =(int)data[index+1];
		if(ret2<0)ret2+=256;
		if(ret+ret2==65535) return -1;
		return ret+ret2;
	}
	public int getMobCode()
	{
		return mobModNumber;
		
		
	}
	public MobObject(String line)
	{
		String goodNumbers = "1234567890.,";
		String tempLine = "";
		for(int i = 0; i<line.length(); i++)
		{
			if(goodNumbers.indexOf(line.charAt(i))!=' ') tempLine = tempLine + line.charAt(i);
		}
		line = tempLine;
		int index = line.indexOf(',');
		num0 = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num1 = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num2 = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num3 = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num4 = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		mobModNumber = Integer.valueOf(line.substring(0, index)).shortValue();
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num5b = Integer.valueOf(line.substring(0, index)).shortValue();
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num6 = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		rotation = Float.valueOf(line.substring(0, index));
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num8 = Integer.valueOf(line.substring(0, index)).shortValue();
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num9 = Integer.valueOf(line.substring(0, index)).shortValue();
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		num10 = Integer.valueOf(line.substring(0, index)).shortValue();
		line = line.substring(index,line.length()-1);
		index = line.indexOf(',');
		if(index!=-1)num11 = Integer.valueOf(line.substring(0, index)).shortValue();
		else num11 = Integer.valueOf(line).shortValue();
	}
	public MobObject(float float4, float float5, float float6, float float7, float float8, short num32, short num62,
			float float9, float float10, short num7, short num82, short num92, short num102) 
	{
		num0 = float4;
		num1 = float5;
		num2 = float6;
		num3 = float7;
		num4 = float8;
		mobModNumber = num32;
		num5b = num62;
		num6 = float9;
		rotation = float10;
		num8 = num7;
		num9 = num82;
		num10 = num92;
		num11 = num102;
	}
	public boolean equals(float float4, float float5, float float6, float float7, float float8, short num32, short num62, float float9, float float10, short num7, short num82, short num92, short num102) 
	{
		if(num0 != float4) return false;
		if(num1 != float5) return false;
		if(num2 != float6) return false;
		if(num3 != float7) return false;
		if(num4 != float8) return false;
		if(mobModNumber != num32) return false;
		if(num5b != num62) return false;
		if(num6 != float9) return false;
		if(rotation != float10) return false;
		if(num8 != num7) return false;
		if(num9 != num82) return false;
		if(num10 != num92) return false;
		if(num11 != num102) return false;
		return true;
	}
	public String toString()
	{
		return "Monster Object: "+num0 +", "+num1 +", "+num2 +", "+num3 +", "+num4 +", "+mobModNumber+", "+num5b +", "+num6 +", "+rotation +", "+num8 +", "+num9+", "+num10+", "+num11+"\n";
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
	public byte[] toBytes()
	{
		byte[] ret = ByteBuffer.allocate(4).putFloat(num0).array();
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(num1).array());
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(num2).array());
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(num3).array());
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(num4).array());
		ret = mergeArrays(ret, toByteArr(mobModNumber,2));
		ret = mergeArrays(ret, toByteArr(num5b,2));
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(num6).array());
		ret = mergeArrays(ret, ByteBuffer.allocate(4).putFloat(rotation).array());
		ret = mergeArrays(ret, toByteArr(num8,2));
		ret = mergeArrays(ret, toByteArr(num9,2));
		ret = mergeArrays(ret, toByteArr(num10,2));
		ret = mergeArrays(ret, toByteArr(num11,2));
		return ret;
	}
	public String bMos() 
	{
		return "Constant Object: "+num0 +", "+num1 +", "+num2 +", "+num3 +", "+num4 +", "+mobModNumber+", "+num5b +", "+num6 +", "+rotation +", "+num8 +", "+num9+", "+num10+", "+num11 +"\n";
	}
}
