package LKSFileManagerTools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import Randomization.Main;
public class PCKGManager {
	byte[] file;
	String name = "unknown";
	ArrayList<String> filesNames = new ArrayList<String>();
	ArrayList<byte[]> filesContents = new ArrayList<byte[]>();
	ArrayList<String> pacFilesNames = new ArrayList<String>();
	ArrayList<PCKGManager> pacFilesContents = new ArrayList<PCKGManager>();
	public PCKGManager(String name)
	{
		this.name = name;
	}
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
	private void extractPAC()
	{//turns the pacFile into ArrayLists
		/*PCKG File layout
		 * First 32 Bytes: PCKG Header, PCKG and then 28 0x00 bytes
		 * 
		 * PCKG Block Layout:
		 * First Four Bytes: Size of File Block
		 * Second Four Bytes: Size of File in File Block
		 * Third Four Bytes: Size of File Block Header (default 0x20)
		 * Byte 13+: name
		 * 
		 * */
		byte[] fileName; //Name of current file
		byte[] fileContents = {0x01,0x01}; //Contents of current file
		int nextHeaderPos = 32;
		int nextCnt = 1;
		String theName = "";
		int headerSize = 32;
		if(isPAC(file))
		{
		while (nextCnt != 0) 
		{
			
			
			if(Main.DEBUGMODE)System.out.println("NextHeaderPos: " + nextHeaderPos);
			headerSize = ByteBuffer.wrap(Arrays.copyOfRange(file, nextHeaderPos+8, nextHeaderPos+12)).getInt();
			if(Main.DEBUGMODE)System.out.println(headerSize);
			fileContents = Arrays.copyOfRange(file, nextHeaderPos+headerSize, nextHeaderPos+headerSize + ByteBuffer.wrap(Arrays.copyOfRange(file,nextHeaderPos+4,nextHeaderPos+8)).getInt());	
			
			fileName = Arrays.copyOfRange(file, nextHeaderPos+12, nextHeaderPos+headerSize);
			try {theName = new String(fileName, "ISO-8859-1");} catch (Exception error) {System.out.println("Failed to Extract due to being an unsupported encoding");break;}
			
			String letters =  "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890,.;'/`~<>?_:";
			String theNewName = "";
			theName = theName.replaceAll("\0+$", "");
			for(int i = 0; i<theName.length(); i++)
			{
				if(letters.indexOf(theName.charAt(i))!=-1)
				{
					theNewName = theNewName + theName.charAt(i);
				}
			}
			
			filesNames.add(theNewName);
			
			if(Main.DEBUGMODE)System.out.println("Found File: " + theName + " File Size: " + Math.abs(ByteBuffer.wrap(Arrays.copyOfRange(file, nextHeaderPos+4, nextHeaderPos+8)).getInt()));
			filesContents.add(fileContents);
			
			
			
			nextCnt = ByteBuffer.wrap(Arrays.copyOfRange(file, nextHeaderPos+0, nextHeaderPos+4)).getInt();
			nextHeaderPos += nextCnt;

			}
		}
		}
	private void rePAC()
	{//Turns the ArrayLists into a pacFile again
		byte[] name = {0x00, 0x00, 0x00, 0x00};
		byte[] line;
		byte[] data = {0x50, 0x43, 0x4B, 0x47, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		byte headerSize = 0x20;

		for (int i = 0; i < filesContents.size(); i++) 
		{
			//make header info
			try {name = filesNames.get(i).getBytes("ISO-8859-1");} catch (Exception error) {System.out.println("Unsupported Encoding!!!");break;}
			if(name.length>20) headerSize = (byte)(((name.length+12)/32 + 1) * 32);
			else headerSize = 32;
			line = new byte[headerSize];
			System.arraycopy(longToBytes((filesContents.get(i).length/32+1)*32+headerSize, 4), 0, line, 0, 4);
			System.arraycopy(longToBytes(filesContents.get(i).length, 4), 0, line, 4, 4);
			line[11] = headerSize;
			if(i == filesContents.size()-1)
			{
				line[0] = 0x00;
				line[1] = 0x00;
				line[2] = 0x00;
				line[3] = 0x00;
			}
			System.arraycopy(name, 0, line, 12, name.length);       
			
			data = mergeArrays(data, mergeArrays(line, filesContents.get(i))); //adds the newly packed data at the end of the data array
			data = mergeArrays(data, new byte[((filesContents.get(i).length/32+1)*32+headerSize)-(filesContents.get(i).length+headerSize)]);
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
				///if(pacFilesNames.get(i).equals(name))
				//{
					//pacFilesNames.remove(i);
					//pacFilesContents.remove(i);
				//}
			}
			
		}
		rePAC();
	}
	public byte[] getFile()
	{
		rePAC();
		return file;
	}
	public static boolean isPAC(byte[] data)
	{//returns if the header matches a PAC file header
		if(data.length<4) return false;
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
				System.out.println(e);
			}
		}
	}
	public String toString()
	{
		return name;
	}
	public void addFile(String name, byte[] bytes) 
	{
		for(int i = 0; i<filesNames.size(); i++)
		{
			if(filesNames.get(i).equals(name))
			{
				filesNames.set(i, name);
				filesContents.set(i, bytes);
				rePAC();
				return;
			}
		}
		filesNames.add(name);
		filesContents.add(bytes);
		rePAC();
	}
	public void writePac(String outputName) 
	{
		try{Files.write(Paths.get(outputName + name), file);}catch(Exception error) {System.out.println("File Writing Error");}
	}
	public int getFileAmount()
	{
		return filesContents.size();
	}
	public String getName()
	{
		return name;
	}
	public String getName(int index)
	{
		return filesNames.get(index);
	}
	public String getFileType(int index) {
		try {
			if(new String(Arrays.copyOf(filesContents.get(index), 14), "ISO-8859-1").equals("MDF_FP_WII_100"))
			{
				return "Fixed Point";
			} else if (new String(Arrays.copyOf(filesContents.get(index), 15), "ISO-8859-1").equals("MDF_COL_WII_100"))
			{
				return "Collision";
			} else if(isPAC(filesContents.get(index)))
			{
				return "Package";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Normal";
	}
	public byte[] getFile(int i) {

		return filesContents.get(i);
	}
}
