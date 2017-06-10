import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hub_Board {
	private Field[][] fieldArray;
	private int sizeX;
	private int sizeY;
	private int boardValue = 0;
	
	public Hub_Board(int x, int y,Container pane, int value, String aMapName)
	{
		sizeX = x;
		sizeY = y;
		boardValue = value;
		


		JPanel menu = new JPanel();
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
				Field field = new Field(false);
				fieldArray[i][j] = field;
				
				field.setBounds(600/sizeX*j, 450/sizeY*i, 600/sizeX-1, 450/sizeY-1);
				menu.add(field);

			}
			
		}
		
		pane.add(menu);
		
	}
	
	public void makePlayer(int x, int y)
	{
		fieldArray[y][x].makePlayer();
	}
	
	public Field[][] getArray()
	{
		return fieldArray;
	}
}
 