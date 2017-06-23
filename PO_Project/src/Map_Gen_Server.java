import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Map_Gen_Server extends Thread {
	
	private int portNumber ;
	
	private String compName = "Unknown";
	
	private Board board;
	
	private DataInputStream iStream ;//socket.getInputStream();
	
	private DataOutputStream oStream;
	
	private Socket socket;
	
	private ServerSocket serverSocket;
	
	private static Random rng = new Random();
	
	
	/**
	 * Constructor of class Map_Gen_Server which makes Thread and Server socket 
	 * @param aBoard - specific board to which server is added
	 */
	public Map_Gen_Server(Board aBoard) 
	{
		board = aBoard;
		
		portNumber  = rng.nextInt(1000)+5000;
		
		
		

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    compName = addr.getHostName();
		    board.setCompName(compName);

		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}
		
		 try {
			 System.out.println("Waiting for you to make me a socket!!!");
				serverSocket = new ServerSocket(portNumber);
				board.setServerPort(portNumber);

				//socket = serverSocket.accept();
			//	iStream = socket.getInputStream();
			//	oStream = socket.getOutputStream();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				 System.out.println("Exception was thrown");
				e.printStackTrace();
			}
				
	
	}
	/**
	 * Constructor of class Map_Gen_Server makes thread and server socket with given port and computer name
	 * @param aPort - int port to which set server socket
	 * @param aCompName - String Computer name to which set server socket
	 * @param aBoard - Board specific board to which add server
	 */
	public Map_Gen_Server(int aPort,String aCompName, Board aBoard)
	{
		portNumber = aPort;
		compName = aCompName;
		board = aBoard;

		 try {
			serverSocket = new ServerSocket(portNumber);
			//socket = serverSocket.accept();
		//	iStream = socket.getInputStream();
		//	oStream = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception thrown");
			e.printStackTrace();
		}
	}
	/**
	 * Method that first accepts connection to Client Socket
	 * First it sends board width and height and board Value
	 * 
	 * When Board is made in client application, waits to return values of the Fields that client socket requests
	 */
	public void run()
	{
		System.out.println("Server at port "+portNumber);
		try {
			socket = serverSocket.accept();
			iStream = new DataInputStream(socket.getInputStream());
			oStream = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("We established connection");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flag=false;
		boolean happened = false;
		
		while(true)
		{
			if(happened == false)
			{
				try {
					flag = iStream.readBoolean();
					System.out.println("We got a message");
					happened = true;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(flag== true)
			{
				System.out.println("we got into ifstatement");
				try {
					oStream.writeInt(board.getSizeX());
					oStream.flush();
					oStream.writeInt(board.getSizeY());
					oStream.flush();
					oStream.writeInt(board.getBoardValue());
					oStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				System.out.println("Server sent information!! BoardValue: "+board.getBoardValue());
				flag=false;
			
			}
			
			System.out.println("Matthew's fault");
			
			break;
		}
		
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				
			while(true)
			{
				int X;
				int Y;
				

				X = iStream.readInt();
				Y = iStream.readInt();
				if(board.getState(X, Y)==1)
				{
					oStream.writeBoolean(true);
					break;
				}
				else oStream.writeBoolean(false);
			}
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while(true)
		{
			System.out.println("We got into the third part");
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int x;
			int y;
			
			try {
				x = iStream.readInt();
				y = iStream.readInt();
				
				int state = board.getState(x, y);
				
				oStream.writeInt(state);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}

	}
	/**
	 * Returns server Port
	 * @return int server Port
	 */
	public int getServerPort()
	{
		return portNumber;
	}
	/**
	 * Return Computer Name 
	 * @return String computer name
	 */
	public String getCompName()
	{
		return compName;
	}
	
}
