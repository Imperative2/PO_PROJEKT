import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Integer;


public class Board  {
	
	private Field[][] fieldArray;
	private int boardX;
	private int boardY;
	private int boardSum;
	private String mapName;
	
	private int serverPort;
	private String compName;
	
	private JPanel menu;


	/**
	 * Constructor of the Board class fill's frame on which player can change state of the board
	 * @param sizeX - width of the board
	 * @param sizeY - height of the board
	 * @param pane - Container of the frame 
	 * @param aMapName - name of the Map
	 */
	public Board(int sizeX, int sizeY, Container pane,String aMapName)
	{
		super();
		mapName = aMapName;
		menu = new JPanel();
		menu.setLayout(null);
		
		
		JLabel textBoardSum = new JLabel();
		
		textBoardSum.setBounds(600, 40, 300, 60);
		
		fieldArray = new Field[sizeY][sizeX];
		
		menu.add(textBoardSum);

		System.out.println("we are here");
		for(int i=0;i<sizeY;i++)
		{
			for(int j=0;j<sizeX;j++)
			{
				Field field = new Field();
				fieldArray[i][j] = field;
				
				field.setBounds(600/sizeX*j, 450/sizeY*i, 600/sizeX-1, 450/sizeY-1);
				menu.add(field);
				field.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						field.makeWall();
						boardSum = getBoardValue();
						textBoardSum.setText("Number of fields to access: "+boardSum);
						
					}
					
				});
			}
			
		}
		
		
		boardX = sizeX;
		boardY = sizeY;
		
		randomise();
		boardSum = getBoardValue();
		textBoardSum.setText("Number of fields to access: "+boardSum);
		
		pane.add(menu);
		
		
	}
	/**
	 * Constructor of the Board which has gets already defines server Socket 
	 * @param sizeX - width of the board
	 * @param sizeY - height of the board
	 * @param aMapName - map of the name
	 * @param aServerPort - server Port 
	 * @param aCompName - name of the Computer on which is contained
	 * @param aFieldArray - array with buttons (board state)
	 */
	public Board(int sizeX, int sizeY, String aMapName, Field[][] aFieldArray)
	{
		fieldArray = aFieldArray;
		boardX = sizeX;
		boardY = sizeY;
		mapName = aMapName;
		boardSum = getBoardValue();
	}
	
	public Board(Board board, Container pane) 
	{
		super();
		this.mapName = board.getMapName();
		menu = new JPanel();
		menu.setLayout(null);
		
		serverPort = board.getServerPort();
		compName = board.getCompName();
	
		
		JLabel textBoardSum = new JLabel();
		
		textBoardSum.setBounds(600, 40, 300, 60);
		
		fieldArray = board.getArray();
		
		menu.add(textBoardSum);

		for(int i=0;i<board.getSizeY();i++)
		{
			for(int j=0;j<board.getSizeX();j++)
			{

				Field field = fieldArray[i][j];
				
				field.setBounds(600/board.getSizeX()*j, 450/board.getSizeY()*i, 600/board.getSizeX()-1, 450/board.getSizeY()-1);
				menu.add(field);
				field.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						field.makeWall();
						boardSum = getBoardValue();
						textBoardSum.setText("Number of fields to access: "+boardSum);
						
					}
					
				});
			}
			
		}	
		
		
		boardX = board.getSizeX();
		boardY = board.getSizeY();
		
		boardSum = getBoardValue();
		textBoardSum.setText("Number of fields to access: "+boardSum);
		
		pane.add(menu);
		
	}
/**
 * Randomly sets each field on the wall to either wall or accessible
 */
	public void randomise()
	{
		Random rng = new Random();
		int number;
		
		for(int i=0;i<boardY;i++)
		{
			for(int j=0;j<boardX;j++)
			{
				number = rng.nextInt(9);
				if(number==0) fieldArray[i][j].makeWall();
			}
		}
	}
	/**
	 * Return number of the Field that a player is able to access
	 * @return int number of the fields accessible
	 */
	public int getBoardValue()
	{
		int sum = 0;
		for(int i=0;i<boardY;i++)
		{
			for(int j=0;j<boardX;j++)
			{
				sum+=fieldArray[i][j].getValue();
			}
		}
		return sum;
	}
	/**
	 * Returns map name
	 * @return String MapName
	 */
	public String getMapName()
	{
		return mapName;
	}
	/**
	 * Returns Field Array of the board
	 * @return Field[][]
	 */
	public Field[][] getArray()
	{
		return fieldArray;
	}
	/**
	 * Returns board width
	 * @return int board width
	 */
	public int getSizeX()
	{
		return boardX;
	}
	/**
	 * Returns  board height
	 * @return int board height
	 */
	public int getSizeY()
	{
		return boardY;
	}
	/**
	 * Returns string of the board which is representation of the board class and its Field Array
	 */
	public String toString()
	{
		String fieldStr = new String();
		Field field;
		for(int i=0;i<boardY;i++)
		{
			for(int j=0 ; j<boardX;j++)
			{
				field = fieldArray[i][j];
				fieldStr+= field.toString();
			}
		}
//		System.out.println(boardX+" "+boardY+" "+mapName+" "+fieldStr);
		return boardX+" "+boardY+" "+mapName+" "+" "+fieldStr;
	}

	/**
	 * Returns Server Port
	 * @return int Server Port
	 */
	public int getServerPort()
	{
		return serverPort;
	}
	/**
	 * Return ComputerName with the server
	 * @return Strign name of the Computer Containing Server
	 */
	public String getCompName()
	{
		return compName;
	}
	/**
	 * Sets server port
	 * @param aPort - int Sets Port of the Server 
	 */
	public void setServerPort(int aPort)
	{
		serverPort = aPort;
	}
	/**
	 * Sets Computer Name
	 * @param aName - String Sets Computer name 
	 */
	public void setCompName(String aName)
	{
		compName = aName;
	}
	/**
	 * add's Labels displaying port and computer name
	 */
	public void addLabel()
	{
		JLabel textPort = new JLabel("port: "+serverPort);
		JLabel textCompName = new JLabel("Comp Name: "+compName);
		
		textPort.setBounds(600, 100, 300, 60);
		textCompName.setBounds(600,160,300,60);
		
		menu.add(textPort);
		menu.add(textCompName);
		menu.repaint();
	}
	/**
	 * Returns state of the Specific Field
	 * @param x - int position of the X field
	 * @param y - int position of the Y field 
	 * @return Returns int state of the specified Field
	 */
	public int getState(int x, int y)
	{
		return fieldArray[y][x].getState();
	}

}
