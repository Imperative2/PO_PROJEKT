import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Player extends Thread{

	private Hub_Board board;
	
	private int mapPort;
	private String compName;
	private String mapName;
	private Socket playerSocket;
	
	private DataInputStream iStream ;//socket.getInputStream();
	
	private DataOutputStream oStream;
	
	private Field[][] fieldArray;
	
	
	private int sizeX;
	private int sizeY;
	private int value;
	
	private int playerPositionX;
	private int playerPositionY;
	
	
	
	public Player(int aMapPort,String aCompName, String aMapName)
	{
		mapPort = aMapPort;
		compName = aCompName;
		mapName = aMapName;
		
		JFrame frameBoard = new JFrame();
		frameBoard.setTitle("MapFrame "+mapName);
		frameBoard.setSize(800, 480);
		frameBoard.setLocation(450, 100);
		frameBoard.setResizable(false);
		
		System.out.println("We got so far");	
	
		try {
			
			getInfo();
			 board = new Hub_Board(sizeX, sizeY, frameBoard.getContentPane(),value,mapName);
			 setPlayerPosition();
			frameBoard.setVisible(true);
			
			fieldArray = board.getArray();
			
		} catch (UnknownHostException e) {
			System.out.println("Exception occured");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception occured");
			e.printStackTrace();
		}
System.out.println("We are victorious with matthiew");
		 
	}
	
	public void run()
	{
		ArrayList<ArrayList<PointOnBoard>> ArrayOfArrays = new ArrayList<>();
		boolean[][] visitedArray = new boolean[sizeY][sizeX];
		for(int i=0;i<sizeY;i++)
			for(int j=0;j<sizeX;j++) visitedArray[i][j] = false;
		visitedArray[playerPositionY][playerPositionX] = true;
		
		boolean flag = true;
		
		while(true)
		{
			ArrayOfArrays.add(new ArrayList<PointOnBoard>());
			for(int i=-1;i<2;i++)
			{
				for(int j=-1;j<2;j++)
				{
					int y = playerPositionY+i;
					int x = playerPositionX+j;
					ArrayList<PointOnBoard> tempArray =ArrayOfArrays.get(ArrayOfArrays.size()-1);
					if(playerPositionY+i>=0&&playerPositionX+j>=0&&playerPositionY+i<sizeY&&playerPositionX+j<sizeY)
					{
						Field tempField = fieldArray[playerPositionY+i][playerPositionX+j];
						int state=1;
						
						try {
							oStream.writeInt(x);
							oStream.writeInt(y);
							state = iStream.readInt();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tempField.setState(state);
						
						PointOnBoard tempPoint = new PointOnBoard(x,y,tempField.getState(),tempField);
						
						if(visitedArray[y][x]==false&&tempField.getState()==1)
						{
							tempArray.add(tempPoint);
							visitedArray[y][x]=true;
							System.out.println("Its working we are looking for fields");
						}
					}
				}
			}
			
			ArrayList<PointOnBoard> tempArray =ArrayOfArrays.get(ArrayOfArrays.size()-1);
			if(tempArray.isEmpty())
			{
				ArrayOfArrays.remove(ArrayOfArrays.size()-1);
				tempArray =ArrayOfArrays.get(ArrayOfArrays.size()-1);
			}
			
			if(tempArray.size()!=0)
				{
					PointOnBoard tempPoint = tempArray.get(0);
					board.makePlayer(tempPoint.getX(), tempPoint.getY());
					playerPositionX = tempPoint.getX();
					playerPositionY = tempPoint.getY();
					tempArray.remove(0);
				}
			
			if(tempArray.isEmpty())
				{
					ArrayOfArrays.remove(ArrayOfArrays.size()-1);
					
				}
			if(ArrayOfArrays.isEmpty())
			{
				System.out.println("My Work is Done!!!!!!!!!!!!!!!!!");
				break;
			}
			

			
		}
		
		
	
		
		
	}
	
	public void getInfo() throws UnknownHostException, IOException
	{
		playerSocket = new Socket(compName,mapPort);
		iStream = new DataInputStream(playerSocket.getInputStream());
		oStream = new DataOutputStream(playerSocket.getOutputStream());
		
		OutputStream tempOStream = playerSocket.getOutputStream();
		
		oStream.writeBoolean(true);
		sizeX = iStream.readInt();
		sizeY = iStream.readInt();
		value = iStream.readInt();
		
		System.out.println("Values we have: "+sizeX+" "+sizeY+" "+value);
		
	}
	
	public void setPlayerPosition() throws IOException
	{
		boolean flag = false;
		
		int X = 0;
		int Y = 0;
		
		while(flag == false)
		{
			oStream.writeInt(X);
			oStream.writeInt(Y);
			flag =  iStream.readBoolean();
			X++;
			Y++;
		}
		board.makePlayer(X-1, Y-1);
	}
	
}
