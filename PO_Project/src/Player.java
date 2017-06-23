import java.awt.Container;
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
	
	private DataInputStream iStream ;
	
	private DataOutputStream oStream;
	
	private Field[][] fieldArray;
	
	
	private int sizeX;
	private int sizeY;
	private int value;
	
	private int playerPositionX;
	private int playerPositionY;
	
	
	/**
	 * Constructor of class Player creates Thread, and socket with given port and computer name, set's map name
	 * @param aMapPort - int Server port to which connect to
	 * @param aCompName - String name of the computer containing server
	 * @param aMapName - String name of the map
	 */
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
	/**
	 * Makes new windows with board State
	 * @param pane
	 */
	public void makeWindow(Container pane)
	{
		board.display(pane, fieldArray);
	}
	/**
	 * method that if responsible for asking Map_Gen_Server for accessible field and moving to them if possible
	 */
	public void run()
	{
		ArrayList<ArrayList<PointOnBoard>> ArrayOfArrays = new ArrayList<>();//ArrayList of ArrayLists /player movement lasts till those lists aren't empty
		boolean[][] visitedArray = new boolean[sizeY][sizeX];
		for(int i=0;i<sizeY;i++)
			for(int j=0;j<sizeX;j++) visitedArray[i][j] = false;
		visitedArray[playerPositionY][playerPositionX] = true; // array of visited field / made so player doesn't have to access the same field twice
		
		
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
					if(playerPositionY+i>=0&&playerPositionX+j>=0&&playerPositionY+i<sizeY&&playerPositionX+j<sizeX)
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
	/**
	 * Gets information of board size,value, through socket connection from Map_Gen_Server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void getInfo() throws UnknownHostException, IOException
	{
		playerSocket = new Socket(compName,mapPort);
		iStream = new DataInputStream(playerSocket.getInputStream());
		oStream = new DataOutputStream(playerSocket.getOutputStream());
		
		
		oStream.writeBoolean(true);
		sizeX = iStream.readInt();
		sizeY = iStream.readInt();
		value = iStream.readInt();
		
		System.out.println("Values we have: "+sizeX+" "+sizeY+" "+value);
		
	}
	/**
	 * Set's player position to given X , Y
	 * @throws IOException
	 */
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
	public int getMapPort() {
		return mapPort;
	}

/**
 * Returns Computer Name
 * @return String compName
 */ 
	public String getCompName() {
		return compName;
	}

/**
 * Returns Map Name
 * @return String mapName
 */
	public String getMapName() {
		return mapName;
	}


/**
 * Returns current board state in an 2-dim array
 * @return
 */
	public Field[][] getFieldArray() {
		return fieldArray;
	}

/**
 * Returns board Value
 * @return
 */
	public int getValue() {
		return value;
	}

	
}
