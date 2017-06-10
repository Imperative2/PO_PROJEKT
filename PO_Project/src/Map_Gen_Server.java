import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

public class Map_Gen_Server extends Thread {
	
	private int portNumber ;
	
	private String compName = "Unknown";
	
	private Board board;
	
	private DataInputStream iStream ;//socket.getInputStream();
	
	private DataOutputStream oStream;
	
	private Socket socket;
	
	private ServerSocket serverSocket;
	
	private static Random rng = new Random();
	
	
	
	//ArrayList<Socket> clientSocketArray = new ArrayList<>();
	
	public Map_Gen_Server(Board aBoard) //throws IOException
	{
		board = aBoard;
		
		portNumber  = rng.nextInt(1000)+5000;
		

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    compName = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}
		
		 try {
			 System.out.println("Waiting for you to make me a socket!!!");
				serverSocket = new ServerSocket(portNumber);

				//socket = serverSocket.accept();
			//	iStream = socket.getInputStream();
			//	oStream = socket.getOutputStream();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				 System.out.println("Exception was thrown");
				e.printStackTrace();
			}
				

			
						
				
			
	}
	
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
				Thread.sleep(1);
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
	
	public int getServerPort()
	{
		return portNumber;
	}
	
	public String getCompName()
	{
		return compName;
	}
	
	

}
