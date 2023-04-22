

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class PCKGManager {
	byte[] file;
	String name = "unknown";
	ArrayList<String> filesNames = new ArrayList<String>();
	ArrayList<byte[]> filesContents = new ArrayList<byte[]>();
	ArrayList<String> pacFilesNames = new ArrayList<String>();
	ArrayList<PCKGManager> pacFilesContents = new ArrayList<PCKGManager>();
	//makes new PackManager then extracts the contents
	public PCKGManager(byte[] data)
	{
		file = data;
		extractPAC();
	}
	public PCKGManager(byte[] data, String name)
	{
		file = data;
		this.name = name;
		extractPAC();
	}
	//turns the pacFile into ArrayLists
	private void extractPAC()
	{
		byte[] fileSize; //Size of the current file(used to get size of file contents array
		byte[] fileName; //Name of current file
		byte[] fileContents = {0x01,0x01}; //Contents of current file
		byte[] nextSize; //Space until next File Contents
		int fileSrtPos = 32;
		int nextCnt = 1;
		int nameSrt = 44;
		int fileCntSrt = 64;
		String theName = "";
		int nameHeader = 43;
		while (nextCnt != 0) 
		{
			nextSize = Arrays.copyOfRange(file, fileSrtPos, fileSrtPos+4);
			fileSize = Arrays.copyOfRange(file, fileSrtPos+4, (fileSrtPos+8));
			if(file[nameHeader]==0x20)
			{
				fileName = Arrays.copyOfRange(file, nameSrt, (nameSrt+20));
			}else if(file[nameHeader]==0x40)
			{
				fileName = Arrays.copyOfRange(file, nameSrt, (nameSrt+52));
				fileCntSrt += 32;
			}else
			{
				fileName = Arrays.copyOfRange(file, nameSrt, (nameSrt+20));
			}
			nextCnt = ByteBuffer.wrap(nextSize).getInt();
			nameHeader+=nextCnt;
			fileContents = Arrays.copyOfRange(file, fileCntSrt, fileCntSrt + ByteBuffer.wrap(fileSize).getInt());	
			//makes the file name as a string and removes all the 0x00's
			try {theName = new String(fileName, "ISO-8859-1");} catch (Exception error) {System.out.println("Failed to Extract due to being an unsupported encoding");break;}
			theName = theName.replaceAll("\0+$", "");
			filesNames.add(theName);
			filesContents.add(fileContents);
			//makes new Pack Manager with the file
			if(isPAC(fileContents))
			{
				pacFilesContents.add(new PCKGManager(fileContents));
				pacFilesNames.add(theName);
			}
			
			fileSrtPos += nextCnt;
			nameSrt += nextCnt;
			fileCntSrt += nextCnt;
			}
		}
	private void rePAC()
	{//Turns the ArrayLists into a pacFile again
		byte[] name = {0x00, 0x00, 0x00, 0x00};
		byte[] cntSize = {0x00, 0x00, 0x00, 0x00};
		byte[] nextSize = {0x00, 0x00, 0x00, 0x00};
		byte[] line;
		byte[] data = {0x50, 0x43, 0x4B, 0x47, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		byte nameHeader = 0x20;
		long pkingSize = 0;

		for (int i = 0; i < filesContents.size(); i++) 
		{
			//make header info
			pkingSize = filesContents.get(i).length;
			cntSize = longToBytes(pkingSize, 4);
			try {name = filesNames.get(i).getBytes("ISO-8859-1");} catch (Exception error) {System.out.println("Unsupported Encoding!!!");break;}
			nameHeader = (byte)((name.length/32 + 1) * 32);
			if(isPAC(filesContents.get(i)))
			{
				pkingSize -= 32;
			}
			nextSize = longToBytes((pkingSize/32+1)*32+(name.length/32 + 1) * 32, 4);
			line = new byte[32];
			System.arraycopy(nextSize, 0, line, 0, 4);
			System.arraycopy(cntSize, 0, line, 4, 4);
			line[11] = nameHeader;
			if(i == filesContents.size()-1)
			{
				line[0] = 0x00;
				line[1] = 0x00;
				line[2] = 0x00;
				line[3] = 0x00;
			}
			System.arraycopy(name, 0, line, 12, name.length);       
			
			data = mergeArrays(data, mergeArrays(line, filesContents.get(i))); //adds the newly packed data at the end of the data array
			if(data.length%32!=0)data = mergeArrays(data, new byte[32-data.length%32]);
		}
		file = data;
	}
	public byte[] getFile(String name)
	{
		for(int i = 0; i < filesNames.size(); i++)
		{
			if(filesNames.get(i).equals(name))
			{
				return filesContents.get(i);
			}
		}
		return null;
	}
	public void replaceFile(String name, byte[] file)
	{
		for(int i = 0; i < filesNames.size(); i++)
		{
			if(filesNames.get(i).equals(name))
			{
				filesContents.set(i, file);
			}
			
		}
		rePAC();
	}
	public void removeFile(String name)
	{
		for(int i = 0; i < filesNames.size(); i++)
		{
			if(filesNames.get(i).equals(name))
			{
				filesNames.remove(i);
				filesContents.remove(i);
				if(pacFilesNames.get(i).equals(name))
				{
					pacFilesNames.remove(i);
					pacFilesContents.remove(i);
				}
			}
			
		}
		rePAC();
	}
	public byte[] getFile()
	{
		return file;
	}
	private static boolean isPAC(byte[] data)
	{//returns if the header matches a PAC file header
		return data[0] == 0x50 && data[1] == 0x43 && data[2] == 0x4B && data[3] == 0x47;
	}
	private byte[] longToBytes(long num, int size)
	{
		byte[] ret = new byte[size];
		int place=0;
		for(long i = (long) Math.pow(256, size); i > 1; i/=256)
		{
			ret[place] = (byte)(num*256/i);
			place++;
		}
		
		return ret;
	}
	private byte[] mergeArrays(byte[] main, byte[] add)
	{
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
	public void extractAll(String dest)
	{
		for(int i = 0; i < filesContents.size(); i++)
		{
			try {
				Files.write(Paths.get(dest + filesNames.get(i)), filesContents.get(i));
			} catch (IOException e) {
				System.out.println("Failed to Create File \"" + filesNames.get(i) + "\"");
			}
		}
	}
	public String toString()
	{
		return name;
	}
}
