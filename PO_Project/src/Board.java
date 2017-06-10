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
	public Board(int sizeX, int sizeY, String aMapName,int aServerPort, String aCompName, Field[][] aFieldArray)
	{
		fieldArray = aFieldArray;
		boardX = sizeX;
		boardY = sizeY;
		mapName = aMapName;
		serverPort = aServerPort;
		compName = aCompName;
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
	
	public String getMapName()
	{
		return mapName;
	}
	
	public Field[][] getArray()
	{
		return fieldArray;
	}
	public int getSizeX()
	{
		return boardX;
	}
	
	public int getSizeY()
	{
		return boardY;
	}
	
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
		return boardX+" "+boardY+" "+mapName+" "+serverPort+" "+compName+" "+fieldStr;
	}

	
	public int getServerPort()
	{
		return serverPort;
	}
	
	public String getCompName()
	{
		return compName;
	}
	
	public void setServerPort(int aPort)
	{
		serverPort = aPort;
	}
	
	public void setCompName(String aName)
	{
		compName = aName;
	}
	
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
	
	public int getState(int x, int y)
	{
		return fieldArray[y][x].getState();
	}

}
