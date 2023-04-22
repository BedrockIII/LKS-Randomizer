

public class JobChangePriceChanger 
{
	//TODO nunUsualJobs
	final static String spaceHeader = "PRICE ";
	String[][] data;
	public JobChangePriceChanger(int max, boolean nonUsualJobs, boolean uniquePerJob)
	{
		data = randomizer(max, nonUsualJobs, uniquePerJob);
	}
	private static String[][] randomizer(int max, boolean nonUsualJobs, boolean uniquePerJob)
	{
		String[][] file = new String[20][21];
		if(uniquePerJob)
		{
			for(int i = 0; i < 20; i++)
			{
				for(int j = 1; j < 21; j++)
				{
					file[i][j] = Integer.valueOf((int)(Math.random() * max)).toString();
				}
				file[i][i+1] = Integer.valueOf(-1).toString();
			}
		} else
		{
			for(int i = 0; i < 20; i++)
			{
				file[0][i+1] = Integer.valueOf((int)(Math.random() * max)).toString();
				for(int j = 1; j < 20; j++)
				{
					for(int n = 0; n < 21; n++)
					{
						file[j][n] = file[0][n];
					}
				}
				
			}
			for(int i = 0; i < 20; i++)
			{
				file[i][i+1] = Integer.valueOf(-1).toString();
			}
		}
		if(!nonUsualJobs) {
			for(int i = 0; i < 20; i++)
			{
				file[i][3] = Integer.valueOf(-1).toString();
				file[i][10] = Integer.valueOf(-1).toString();
				file[i][11] = Integer.valueOf(-1).toString();
				file[i][15] = Integer.valueOf(-1).toString();
				file[i][16] = Integer.valueOf(-1).toString();
				file[i][17] = Integer.valueOf(-1).toString();
				file[i][18] = Integer.valueOf(-1).toString();
				file[i][19] = Integer.valueOf(-1).toString();
				file[i][20] = Integer.valueOf(-1).toString();
				file[2][i] = Integer.valueOf(-1).toString();
				file[9][i] = Integer.valueOf(-1).toString();
				file[14][i] = Integer.valueOf(-1).toString();
				file[15][i] = Integer.valueOf(-1).toString();
				file[16][i] = Integer.valueOf(-1).toString();
				file[17][i] = Integer.valueOf(-1).toString();
				file[18][i] = Integer.valueOf(-1).toString();
				file[19][i] = Integer.valueOf(-1).toString();
				file[9][11] = Integer.valueOf(0).toString();//kid to adult
			}
		}
		file[0][0] = spaceHeader + 25;
		file[1][0] = spaceHeader + 26;
		file[2][0] = spaceHeader + 27;
		file[3][0] = spaceHeader + 31;
		file[4][0] = spaceHeader + 30;
		file[5][0] = spaceHeader + 32;
		file[6][0] = spaceHeader + 33;
		file[7][0] = spaceHeader + 34;
		file[8][0] = spaceHeader + 35;
		file[9][0] = spaceHeader + 23;
		file[10][0] = spaceHeader + 24;
		file[11][0] = spaceHeader + 28;
		file[12][0] = spaceHeader + 37;
		file[13][0] = spaceHeader + 36;
		file[14][0] = spaceHeader + 29;
		file[15][0] = spaceHeader + 38;
		file[16][0] = spaceHeader + 39;
		file[17][0] = spaceHeader + 40;
		file[18][0] = spaceHeader + 41;
		file[19][0] = spaceHeader + 42;
		

		return file;
	}
	public byte[] generateArray()
	{
		byte[] ret = {0x4E, 0x55, 0x4D, 0x20, 0x32, 0x30, 0x3B, 0x0D, 0x0A};
		byte[] conStr;
		byte[] temp;
		
		for(int i = 0; i<data.length; i++)
		{
			for(int n = 0; n<data[i].length; n++)
			{
				
				conStr = data[i][n].getBytes();
				temp = ret;
				ret = new byte[ret.length + conStr.length+1];
				for(int j = 0; j < temp.length; j++)
				{
					ret[j] = temp[j];
				}
				for(int j = 0; j < conStr.length; j++)
				{
					ret[j+temp.length] = conStr[j];
				}
				ret[ret.length-1] = 0x2C;
				
			}
			temp = ret;
			ret = new byte[ret.length + 3];
			for(int j = 0; j < temp.length; j++)
			{
				ret[j] = temp[j];
			}
			ret[ret.length-3] = 0x3B;
			ret[ret.length-2] = 0x0D;
			ret[ret.length-1] = 0x0A;
		}
		return ret;
	}
	public void setPrice(int newPrice, int row, int col)
	{
		data[col][row] = ((Integer)newPrice).toString();
	}
	
}
