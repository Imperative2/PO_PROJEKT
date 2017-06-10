import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hub {
	private static JPanel menuCards = new JPanel(new CardLayout()); 
	
	private static CardLayout menuCardLay;
	
	
	private static String MAINMENU = new String("MAINMENU");
	private static String ADDMAP = new String("ADDMAP");
	//private static String PLAY = new String("PLAY");
	private static String SEEMAPLIST = new String("SEEMAPLIST");
	//private static String EXIT =
	
	
	public static void menuPanel(Container pane)
	{
		JPanel 	panelMenu  = new JPanel();
		JButton buttonAddMap = new JButton("1.Add New Map");
		JButton buttonPlay = new JButton("2.Play");
		JButton buttonSeeMapList = new JButton("3.See Map List");
		JButton buttonExit = new JButton("4.Exit");
		
		Dimension dim = new Dimension(200,50);
		
		buttonAddMap.setPreferredSize(dim);
		buttonPlay.setPreferredSize(dim);
		buttonSeeMapList.setPreferredSize(dim);
		buttonExit.setPreferredSize(dim);
		
		panelMenu.add(buttonAddMap);
		panelMenu.add(buttonAddMap);
		panelMenu.add(buttonPlay);
		panelMenu.add(buttonSeeMapList);
		panelMenu.add(buttonExit);
		
		
		NewMapPanel mapPanel = new NewMapPanel();
		
		
		menuCards.add(panelMenu,MAINMENU);
		menuCards.add(mapPanel, ADDMAP);
		
		pane.add(menuCards);
		
		menuCardLay = (CardLayout) (menuCards.getLayout());
		
		panelMenu.setVisible(true);
		
		menuCardLay.show(menuCards, MAINMENU);
		
		buttonAddMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				menuCardLay.show(menuCards, ADDMAP);
				
			}
			
		});
		
		
		buttonExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
		
		
	}
	
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
					menuCardLay.show(menuCards, MAINMENU);
					player.start();
				}
				
			});
		}
	}
	
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
