import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hub {
	private static JPanel menuCards = new JPanel(new CardLayout()); 
	
	private static CardLayout menuCardLay;
	private static JComboBox<String> comboBoxMapList = new JComboBox<String>();
	private static int comboCounter = 0;
	
	
	private static String MAINMENU = new String("MAINMENU");
	private static String ADDMAP = new String("ADDMAP");
	private static String SEEMAPLIST = new String("SEEMAPLIST");
	
	
	private static Player mapPlayer;
	
	private static JButton buttonPlayAction;
	
	/**
	 * JPanel containing Menu
	 * @param pane
	 */
	public static void menuPanel(Container pane)
	{
		JPanel 	panelMenu  = new JPanel();
		JButton buttonAddMap = new JButton("1.Add New Map");
		JButton buttonPlay = new JButton("2.Play");
		JButton buttonSeeMapList = new JButton("3.See Map List");
		JButton buttonExit = new JButton("4.Exit");
		
		buttonPlayAction = buttonPlay;
		
		Dimension dim = new Dimension(200,50);
		
		buttonAddMap.setPreferredSize(dim);
		buttonPlay.setPreferredSize(dim);
		buttonSeeMapList.setPreferredSize(dim);
		buttonExit.setPreferredSize(dim);
		
		buttonPlay.setEnabled(false);
		
		panelMenu.add(buttonAddMap);
		panelMenu.add(buttonAddMap);
		panelMenu.add(buttonPlay);
		panelMenu.add(buttonSeeMapList);
		panelMenu.add(buttonExit);
		
		
		
		NewMapPanel mapPanel = new NewMapPanel();
		PanelMapList maplistPanel = new PanelMapList();
		
		
		menuCards.add(panelMenu,MAINMENU);
		menuCards.add(mapPanel, ADDMAP);
		menuCards.add(maplistPanel,SEEMAPLIST);
		
		pane.add(menuCards);
		
		menuCardLay = (CardLayout) (menuCards.getLayout());
		
		panelMenu.setVisible(true);
		
		menuCardLay.show(menuCards, MAINMENU);
		/**
		 * Adds new Map
		 */
		buttonAddMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				menuCardLay.show(menuCards, ADDMAP);
				
			}
			
		});
		/**
		 * Starts Thread which will ask for specific field  and then solve map
		 */
		buttonPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mapPlayer.start();
			}
			
		});
		
		buttonSeeMapList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuCardLay.show(menuCards, SEEMAPLIST);
				
			}
			
		});
		
		/**
		 * Exits application
		 */
		buttonExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
	}
	/**
	 * Adds new Map creates graphic representation of the board
	 * @author Imperative
	 *
	 */
	public static class NewMapPanel extends JPanel
	{
		public NewMapPanel()
		{
			super();
			JLabel textPort = new JLabel("Enter Map Port:");
			JTextField fieldPort = new JTextField("5555");
			JLabel textCompName = new JLabel("Enter Computer Name:");
			JTextField fieldCompName = new JTextField("LAPTOP-O5N9TJ7D");
			JLabel textMapName = new JLabel("Enter Map Name:");
			JTextField fieldMapName = new JTextField("Map:1");
			JButton buttonReturn = new JButton("Return");
			JButton buttonOK = new JButton("OK");
			
			Dimension dim1 = new Dimension(200,25);
			Dimension dim2 = new Dimension(100,30);
			
			textPort.setPreferredSize(dim1);
			fieldPort.setPreferredSize(dim1);
			textCompName.setPreferredSize(dim1);
			fieldCompName.setPreferredSize(dim1);
			textMapName.setPreferredSize(dim1);
			fieldMapName.setPreferredSize(dim1);
			buttonReturn.setPreferredSize(dim2);
			buttonOK.setPreferredSize(dim2);
			
			this.add(textPort);
			this.add(fieldPort);
			this.add(textCompName);
			this.add(fieldCompName);
			this.add(textMapName);
			this.add(fieldMapName);
			this.add(buttonOK);
			this.add(buttonReturn);
			
			
			buttonReturn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					menuCardLay.show(menuCards, MAINMENU);
				}
				
			});
			
			buttonOK.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					
					Player player = new Player(Integer.parseInt(fieldPort.getText())
							,fieldCompName.getText(),fieldMapName.getText());
					
					AddToComboBox atcb = new AddToComboBox(player);
					buttonPlayAction.setEnabled(true);
					menuCardLay.show(menuCards, MAINMENU);
					mapPlayer = player;
					
				}
				
			});
		}
	}
	
	public static class AddToComboBox
	{
		Player player;
		public AddToComboBox(Player aPlayer)
		{
			player = aPlayer;
			JPanel panel = new JPanel();
			
			panel.setLayout(new FlowLayout());
			

			JLabel textMapName = new JLabel("Map Name: " + player.getMapName());
			JLabel textPort = new JLabel("port: "+player.getMapPort());
			JLabel textCompName = new JLabel("Comp Name: "+player.getCompName());
			JLabel textFieldToAcess = new JLabel("Fields to access: " + player.getValue());
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

			panel.setName(player.getMapName());

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
					frameBoard.setTitle("MapFrame "+player.getMapName());
					frameBoard.setSize(800, 480);
					frameBoard.setLocation(450, 100);
					frameBoard.setResizable(false);

					
					player.makeWindow(frameBoard);
					frameBoard.setVisible(true);
					mapPlayer = player;
					menuCardLay.show(menuCards, MAINMENU);
				}

			});
		}
	}
	
	public static class PanelMapList extends JPanel  {
		
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
	/**
	 * Creates Frame Containing Menu
	 */
	public static void ShowGUI() {

		JFrame menuFrame = new JFrame();
		menuFrame.setTitle("Game HUB");
		menuFrame.setSize(250, 400);
		menuFrame.setLocation(200, 100);
		menuFrame.setDefaultCloseOperation(3);
		menuFrame.setResizable(false);

		menuPanel(menuFrame.getContentPane());

		menuFrame.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override 
			public void run()
			{
				ShowGUI();
			}
		});

	}

}
