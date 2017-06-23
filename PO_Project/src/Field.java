import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Integer;

public class Field extends JButton {
	private boolean changedColor = false;
	private int accessible = 1;
	
	private boolean isWall = false;
	
	private  boolean empty = false;
	
	/**
	 * Constructor of the class Field
	 * Set's default button  to green 
	 */
	public Field()
	{
		super();
		this.setBackground(Color.GREEN);
	}
	/**
	 * Constructor of the class Field and sets color of the button
	 * @param aEmpty takes boolean if the field is to be created empty - color grey
	 */
	public Field(boolean aEmpty)
	{
		super();
		empty = aEmpty;
		this.setBackground(Color.GRAY);
		accessible = 0;
		
	}
	/**
	 * Set's player button color to Yellow and accessible
	 */
	public void makePlayer()
	{
		this.setBackground(Color.YELLOW);
		accessible = 1;
	}
	/**
	 * Constructor of class Field sets color and accessible
	 * @param chCol boolean, color of the button
	 * @param acces int - 1 (accessible) , 0 - (not accessible)
	 */
	public Field(boolean chCol, int acces)
	{
		super();
		changedColor = chCol;
		accessible = acces;
		if(changedColor==false) this.setBackground(Color.GREEN);
		else this.setBackground(Color.RED);
	}
	/**
	 * Method that Changes state of the button from Wall to accessible and if the field is accesible sets it to wall
	 */
	public void makeWall()
	{
		if(changedColor==false)
		{
			this.setBackground(Color.RED);
			changedColor = true;
			accessible = 0;
			isWall = true;
		}
		else
		{
			this.setBackground(Color.GREEN);
			changedColor = false;
			accessible = 1;
			isWall = false;
		}
		
	}
	
	/**
	 * Sets state of the button
	 * @param Int state 1 - empty field 0- wall
	 */
	public void setState(int state)
	{
		if(state==0)
		{
			accessible =0;
			isWall = true;
		}
		else
		{
			accessible =1;
			isWall = false;
		}
		
	}
	/**
	 * Returns state of the wall 
	 * @return if wall returns 0 else returns 1
	 */
	public int getState()
	{
		if(isWall== true) return 0;
		else return 1;
	}
	/**
	 * return value of the accessible 
	 * @return 0 - if wall , else 1
	 */
	public int  getValue()
	{
		return accessible;
	}
	/**
	 * method that converts Field to String
	 */
	public String toString()
	{
		return changedColor+"  "+accessible+" ";
	}

}
