import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;
public class Loader {
	private static ArrayList<Board> boardArray = new ArrayList<Board>();
	
	
	public void addToArray(Board board)
	{
		boardArray.add(board);
	}
	
	public ArrayList<Board> getArray()
	{
		return boardArray;
	}
	/**
	 * Loads information
	 * @param fileName
	 */
	public void load(String fileName)
	{

		try {
			Scanner sc = new Scanner(Paths.get(fileName+".txt"));
			
			String boardX;
			String boardY;
			String boardName;
			
			int port;
			String compName;
			
			boardX = sc.next();
			boardY = sc.next();
			boardName = sc.next();
		//	port = sc.nextInt();
		//	compName = sc.next();
			
			while(boardX!=null)
			{
				Field[][] fieldArray = new Field[Integer.parseInt(boardY)][Integer.parseInt(boardX)];
				String changed;
				int access;
				boolean ch;
				
				System.out.println("Loader boardX : "+boardX+" Loader boardY: "+boardY);
				
				for(int i=0;i<Integer.parseInt(boardY);i++)
				{
					for(int j=0;j<Integer.parseInt(boardX);j++)
					{
						
						changed = sc.next();
						access = sc.nextInt();
						if(changed.equals("false")) ch=false;
						else ch=true;
						
						Field tempField = new Field(ch,access);
						fieldArray[i][j] = tempField;
					}
				}
				Board board = new Board(Integer.parseInt(boardX),Integer.parseInt(boardY),boardName,fieldArray);
				boardArray.add(board);
				
				if(sc.hasNext()){
					boardX = sc.next();
					boardY = sc.next();
					boardName = sc.next();
				}
				else
				{
					boardX =null;
				}

			}
			
		} catch (IOException e) {
			System.out.println("cant find file");
			e.printStackTrace();
		}
		
	}
	/**
	 * Saves Information
	 * @param fileName
	 */
	public void safeToFile(String fileName)
	{
		fileName+=".txt";
		try {
			PrintWriter pt = new PrintWriter(fileName);
			
			for(Board board:boardArray)
			{
				System.out.println("It's here");
				pt.println(board.toString());	
			}
			pt.close();
		} catch (FileNotFoundException e) {
			System.out.println("No file found!");
			e.printStackTrace();
		}
		
	}
	
	
}
