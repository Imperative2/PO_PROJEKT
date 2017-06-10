
public class PointOnBoard {

	int x;
	int y;
	int isWall;
	Field field;
	
	
	public PointOnBoard(int aX, int aY, int aIsWall, Field aField)
	{
		x = aX;
		y = aY;
		isWall = aIsWall;
		field = aField;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
