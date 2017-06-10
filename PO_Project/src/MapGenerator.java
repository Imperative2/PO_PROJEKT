import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;


public class MapGenerator {

	private static JPanel menuCards = new JPanel(new CardLayout());
	private static JComboBox<String> comboBoxMapList = new JComboBox<String>();
	private static JPanel mapCards = new JPanel(new CardLayout());
	
	private static int comboCounter = 0;
	
	private static Loader loader = new Loader();

	private static CardLayout menuCardLay;
	
	private static String fileNameToSafeTo = new String("Map_Gen_maps");

	public static final String MAINMENU = new String("MAINMENU");
	public static final String CREATEMENU = new String("CREATEMENU");
	public static final String LOADMENU = new String("LOADMENU");
	public static final String MAPMENU = new String("MAPMENU");

	public static void MenuPanel(Container Pane) {

		JPanel menu = new JPanel();

		JButton createNewMap = new JButton("1.Create New Map");
		JButton mapList = new JButton("2.Map List");
		JButton loadMap = new JButton("3.Load Maps");
		JButton saveMap = new JButton("4.Save Maps");
		JButton deleteMap = new JButton("5.Delete Maps");
		JButton Exit = new JButton("6.Exit");

		Dimension dim = new Dimension(200, 50);

		createNewMap.setPreferredSize(dim);
		mapList.setPreferredSize(dim);
		loadMap.setPreferredSize(dim);
		saveMap.setPreferredSize(dim);
		deleteMap.setPreferredSize(dim);
		Exit.setPreferredSize(dim);

		menu.add(createNewMap);
		menu.add(mapList);
		menu.add(loadMap);
		menu.add(saveMap);
		menu.add(deleteMap);
		menu.add(Exit);

		menu.setLayout(new FlowLayout());
		menu.setVisible(true);

		Pane.add(menuCards);

		PanelCreateNewMap creNewMap = new PanelCreateNewMap();
		PanelLoadMap loMap = new PanelLoadMap(creNewMap);
		PanelMapList listMap = new PanelMapList();

		menuCards.add(menu, MAINMENU);
		menuCards.add(creNewMap, CREATEMENU);
		menuCards.add(loMap, LOADMENU);
		menuCards.add(listMap, MAPMENU);

		menuCardLay = (CardLayout) (menuCards.getLayout());

		menuCardLay.show(menuCards, MAINMENU);

		createNewMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuCardLay.show(menuCards, CREATEMENU);
				System.out.println("We Work");

			}

		});

		mapList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menuCardLay.show(menuCards, MAPMENU);

			}

		});

		loadMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menuCardLay.show(menuCards, LOADMENU);

			}

		});

		saveMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loader.safeToFile(fileNameToSafeTo);
				System.out.println("Saving worked!");

			}

		});

		deleteMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Files.deleteIfExists(Paths.get("Map_Gen_maps"+".txt"));
				} catch (IOException e) {
					System.out.println("Can't find file to delete");
					e.printStackTrace();
				}

			}

		});

		Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});

	}

	public static class PanelCreateNewMap extends JPanel {

		public PanelCreateNewMap() {
			JLabel textTip = new JLabel("Please fill all fields");
			JLabel textSizeX = new JLabel("Size X: ");
			JTextField fieldSizeX = new JTextField("20", 7);
			JLabel textSizeY = new JLabel("Size Y: ");
			JTextField fieldSizeY = new JTextField("20", 7);
			JLabel textMapName = new JLabel("Map Name: ");
			JTextField fieldMapName = new JTextField("xxxx",7);
			JButton buttonOK = new JButton("OK");
			JButton buttonBack = new JButton("Return");

			Dimension dim1 = new Dimension(230, 50);
			Dimension dim2 = new Dimension(120, 25);

			textTip.setPreferredSize(dim1);
			textSizeX.setPreferredSize(dim2);
			fieldSizeX.setPreferredSize(dim2);
			textSizeY.setPreferredSize(dim2);
			fieldSizeY.setPreferredSize(dim2);
			textMapName.setPreferredSize(dim2);
			fieldMapName.setPreferredSize(dim2);
;

			this.add(textTip);
			this.add(textSizeX);
			this.add(fieldSizeX);
			this.add(textSizeY);
			this.add(fieldSizeY);
			this.add(textMapName);
			this.add(fieldMapName);
			this.add(buttonOK);
			this.add(buttonBack);

			buttonBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					menuCardLay.show(menuCards, MAINMENU);

				}

			});

			buttonOK.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					int intX = Integer.parseInt(fieldSizeX.getText());
					int intY = Integer.parseInt(fieldSizeY.getText());
					String mapName = fieldMapName.getText();

					JFrame frameBoard = new JFrame();
					frameBoard.setTitle("MapFrame "+mapName);
					frameBoard.setSize(800, 480);
					frameBoard.setLocation(450, 100);
					frameBoard.setResizable(false);
					// frameBoard.setDefaultCloseOperation(3);
					// frameBoard.getContentPane().setLayout(null);

					Board board = new Board(intX, intY, frameBoard.getContentPane(),mapName);
					
					loader.addToArray(board);

				//	AddToComboBox addTCB = new AddToComboBox( board);
					
					Map_Gen_Server mapServer = new Map_Gen_Server(board);
					
					board.setServerPort(mapServer.getServerPort());
					board.setCompName(mapServer.getCompName());
					board.addLabel();
					
					//mapServer.start();
					
					AddToComboBox addTCB = new AddToComboBox( board);

					// frameBoard.add(board);
					frameBoard.setVisible(true);
					mapServer.start();

				}

			});


		}
				 public class AddToComboBox
				 {
					 private Board board;
						public  AddToComboBox(Board board) {
							
							this.board = board;
							JPanel panel = new JPanel();
				
							panel.setLayout(new FlowLayout());
							
				
							JLabel textMapName = new JLabel("Map Name: " + board.getMapName());
							JLabel textPort = new JLabel("port: "+board.getServerPort());
							JLabel textCompName = new JLabel("Comp Name: "+board.getCompName());
							JLabel textFieldToAcess = new JLabel("Fields to access: " + board.getBoardValue());
							JButton buttonPlay = new JButton("Play");
							JButton buttonBack = new JButton("return");
				
				
							Dimension dim = new Dimension(180, 30);
							Dimension dim2 = new Dimension(90, 30);
				
				
							comboBoxMapList.setPreferredSize(dim);
				
							buttonBack.setPreferredSize(dim2);
				
							textMapName.setPreferredSize(dim);
							textPort.setPreferredSize(dim);
							textCompName.setPreferredSize(dim);
							textFieldToAcess.setPreferredSize(dim);
							buttonPlay.setPreferredSize(dim2);
							buttonBack.setPreferredSize(dim2);
				
							
							panel.add(textMapName);
							panel.add(textPort);
							panel.add(textCompName);
							panel.add(textFieldToAcess);
							panel.add(buttonPlay);
							panel.add(buttonBack);
				
							panel.setName(board.getMapName());
				
							menuCards.add(panel, "Map" + comboCounter);
				
							comboBoxMapList.addItem("Map" + comboCounter);
							comboCounter++;
							
				
							buttonBack.addActionListener(new ActionListener() {
				
								@Override
								public void actionPerformed(ActionEvent e) {
				
									menuCardLay.show(menuCards, MAINMENU);
				
								}
				
							});
							
							buttonPlay.addActionListener(new ActionListener() {
				
								@Override
								public void actionPerformed(ActionEvent e) {
									
				
									JFrame frameBoard = new JFrame();
									frameBoard.setTitle("MapFrame "+board.getMapName());
									frameBoard.setSize(800, 480);
									frameBoard.setLocation(450, 100);
									frameBoard.setResizable(false);
									// frameBoard.setDefaultCloseOperation(3);
									// frameBoard.getContentPane().setLayout(null);
									
									Board bb = new Board(board,frameBoard.getContentPane());
									bb.addLabel();
									
									
									frameBoard.setVisible(true);
				
								}
				
							});
				
						}
				 	}

	}

	public static class PanelMapList extends JPanel  {
		
	//	private static CardLayout mapCardLay;

		
		public PanelMapList() {

			
			
			
			
			JLabel textTip = new JLabel("Choose map");
			JButton buttonOK = new JButton("Ok");
			JButton buttonBack = new JButton("return");

			Dimension dim = new Dimension(180, 30);
			Dimension dim2 = new Dimension(90,30);
			

			

			textTip.setPreferredSize(dim);
			comboBoxMapList.setPreferredSize(dim);
			buttonOK.setPreferredSize(dim2);
			buttonBack.setPreferredSize(dim2);

			this.add(textTip);
			this.add(comboBoxMapList);
			this.add(buttonOK);
	        this.add(buttonBack);
	        


		
		
			
			buttonOK.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(comboCounter>0)
					{
						menuCardLay.show(menuCards,(String) comboBoxMapList.getSelectedItem());
					}
				}
				
			});
			


			buttonBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					menuCardLay.show(menuCards, MAINMENU);

				}

			});

		}
	}

	public static class PanelLoadMap extends JPanel {
		private PanelCreateNewMap PCNM;
		
		public PanelLoadMap(PanelCreateNewMap pcnm) 
		{
			PCNM = pcnm;
			
			JLabel textTip = new JLabel("Enter file directory");
			JTextField fieldFileDirectory = new JTextField("Map_Gen_maps", 20);
			JButton buttonOK = new JButton("OK");
			JButton buttonBack = new JButton("Return");

			this.add(textTip);
			this.add(fieldFileDirectory);
			this.add(buttonOK);
			this.add(buttonBack);

			this.setLayout(new FlowLayout());
			
			buttonOK.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					ArrayList<Board> boardArray;
					
					loader.load(fieldFileDirectory.getText());
					
					boardArray = loader.getArray();
					
					for(Board board:boardArray)
					{
						Map_Gen_Server mapServer = new Map_Gen_Server(board.getServerPort(),board.getCompName(),board);
						board.addLabel();
						mapServer.start();
						MapGenerator.PanelCreateNewMap.AddToComboBox atcb = PCNM.new AddToComboBox(board);
					}
					
					
					
					menuCardLay.show(menuCards, MAINMENU);
				}
				
			});

			buttonBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					menuCardLay.show(menuCards, MAINMENU);

				}

			});
			

		}
	}

	public static void ShowGUI() {

		JFrame menuFrame = new JFrame();
		menuFrame.setTitle("Map_Genrator");
		menuFrame.setSize(250, 400);
		menuFrame.setLocation(200, 100);
		menuFrame.setDefaultCloseOperation(3);
		menuFrame.setResizable(false);

		MenuPanel(menuFrame.getContentPane());

		menuFrame.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ShowGUI();
			}
		});
		
		
	}

}
