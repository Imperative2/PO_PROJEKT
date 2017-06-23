
public class PointOnBoard {

	int x;
	int y;
	int isWall;
	Field field;
	
	/**
	 * Constructor of PointOnBoard being representation of the State of the Field
	 * @param aX - int X location
	 * @param aY - int Y location
	 * @param aIsWall - int  0 - Wall 1- accessible
	 * @param aField - Field specific field of which this object is representation of 
	 */
	public PointOnBoard(int aX, int aY, int aIsWall, Field aField)
	{
		x = aX;
		y = aY;
		isWall = aIsWall;
		field = aField;
	}
	/**
	 * Returns X position of the Point 
	 * @return int X position
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * Return Y position of the Point
	 * @return int Y position
	 */
	public int getY()
	{
		return y;
	}
}
