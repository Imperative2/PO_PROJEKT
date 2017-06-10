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
	
	
	
	public Field()
	{
		super();
		this.setBackground(Color.GREEN);
	}
	
	public Field(boolean aEmpty)
	{
		super();
		empty = aEmpty;
		this.setBackground(Color.GRAY);
		accessible = 0;
		
	}
	
	public void makePlayer()
	{
		this.setBackground(Color.YELLOW);
		accessible = 1;
	}
	
	public Field(boolean chCol, int acces)
	{
		super();
		changedColor = chCol;
		accessible = acces;
		if(changedColor==false) this.setBackground(Color.GREEN);
		else this.setBackground(Color.RED);
	}
	
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
	
	public int getState()
	{
		if(isWall== true) return 0;
		else return 1;
	}
	
	public int  getValue()
	{
		return accessible;
	}
	
	public String toString()
	{
		return changedColor+"  "+accessible+" ";
	}

}
