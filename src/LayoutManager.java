import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
//import java.awt.Point;	//used for debugging GUI locations

public class LayoutManager {

	JPanel cardPanel = new JPanel();
	JPanel headerPanel = new JPanel();
	JPanel footerPanel = new JPanel();
	JPanel collectedPanel = new JPanel();
	int scoreTimer = 0;
	int collected=0;
	int collected_x=30;
	int collected_pad=5;
	int collected_size=75;
	private Timer timer;
	private int flips=0;
	private int seconds = 0;

	JLabel timerLabel = new JLabel("0");
	JLabel flipLabel = new JLabel("0");
	JLabel hiScoreLabel = new JLabel("Hi Score:   Flips: "+66+" Time Taken: "+222);


	private GameLogicManager gameLogicManager;

	public LayoutManager(GameLogicManager gameLogicManager) {
		this.gameLogicManager = gameLogicManager;
	}

	public void layout_create () {		

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//setting up screen size to center window.
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int framesize_x = 1366;
		int framesize_y = 768;
		int headerHeight = 50;

		JFrame frame=new JFrame();//creating instance of JFrame

		frame.setSize(framesize_x,framesize_y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cardPanel.setLayout(null);  
		headerPanel.setLayout(null);
		footerPanel.setLayout(null);
		collectedPanel.setLayout(null);

		
		headerPanel.setSize(frame.getWidth(),headerHeight);
		footerPanel.setSize(frame.getWidth(),headerHeight);
		collectedPanel.setSize(frame.getWidth(),100);


		JLabel timerText = new JLabel("Time: ");
		timerText.setForeground(Color.WHITE);
		timerText.setFont(new Font("Arial", Font.PLAIN, 50));
		timerText.setBounds(750, -25, 400, 100); // Set position and size
		timerText.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel flipText = new JLabel("Flips: ");
		flipText.setForeground(Color.WHITE);
		flipText.setFont(new Font("Arial", Font.PLAIN, 50));
		flipText.setBounds(-200, -25, 400, 100); // Set position and size
		flipText.setHorizontalAlignment(SwingConstants.RIGHT);


		timerLabel.setForeground(Color.WHITE);
		timerLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		timerLabel.setBounds(900, -25, 400, 100); // Set position and size
		timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);


		flipLabel.setForeground(Color.WHITE);
		flipLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		flipLabel.setBounds(-100, -25, 400, 100); // Set position and size
		flipLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		hiScoreLabel.setForeground(Color.WHITE);
		hiScoreLabel.setFont(new Font("Arial", Font.PLAIN, 50));
		hiScoreLabel.setBounds(170, -25, 1000, 100); // Set position and size
		hiScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);



		frame.getContentPane().setBackground(Color.BLACK);

		cardPanel.setBackground(Color.BLACK); // Set JPanel background color 
		headerPanel.setBackground(Color.BLUE); // Set JPanel background color
		footerPanel.setBackground(Color.BLACK);
		collectedPanel.setBackground(Color.BLACK);

		headerPanel.add(timerLabel);
		headerPanel.add(timerText);
		headerPanel.add(flipText);
		headerPanel.add(flipLabel);

		footerPanel.add(hiScoreLabel);


		frame.setTitle("Mario Match Game - Ron Scott");		

		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		int screenoffsetx = (screenWidth - frameWidth) / 2;
		int screenoffsety = (screenHeight - frameHeight) / 2;

		frame.setLocation(screenoffsetx, screenoffsety);

		int cardSizeX = 100;
		int cardSizeY = 100;
		int buttons_per_row = 8;
		int number_of_rows = 4;
		int initial_x = (frameWidth/2)-(((buttons_per_row/2)*cardSizeX)+cardSizeX/2);
		int initial_y = 0;
		int padding_x = 10;
		int padding_y = 10;

		if (buttons_per_row % 2 == 1) // for odd amounts of cards, not used. 
		{
			initial_x = (frameWidth/2)-((buttons_per_row/2)*cardSizeX)-cardSizeX;			
		}

		int row_y=1;
		int row_x=0;

		List<String> buttonValue = new ArrayList<>();

		String bName;							
		//System.out.println("Populating List");	//Debug
		for (int i = 1; i <= 16; i++) {			
			if (i < 10)
			{
				bName = "0"+i+".png";
				//System.out.println(bName);		//Debug
			}
			else
			{
				bName = ""+i+".png";
				//System.out.println(bName);		//Debug
			}	
			buttonValue.add(bName);
			buttonValue.add(bName);	//Added twice for 2 tiles with same name
		}

		Collections.shuffle(buttonValue); //Randomize List


		// debug
		/*
		for (int i=0;i<buttonValue.size();i++)
		{
			System.out.println(buttonValue.get(i));			
		}*/


		//This sets up the card rows
		for (int i=0; i<buttons_per_row*number_of_rows;i++)
		{
			ImageIcon icon = new ImageIcon("src/imgs/00.png");
			JButton button = new JButton(icon);

			button.setBounds(initial_x+(padding_x*row_x)+(cardSizeX*row_x),(initial_y-cardSizeY)+(padding_y*row_y)+(cardSizeY*row_y),cardSizeX,cardSizeY);//x axis, y axis, width, height
			button.setName(buttonValue.remove(0));
			ButtonClickListener buttonClickListener = new ButtonClickListener(this.gameLogicManager);
			button.addActionListener(buttonClickListener);

			/*Debug
			Point location = button.getLocation();
			System.out.println("Name: "+ (i+1) +",\t X:"+location.x +",\t Y: "+ location.y);
			System.out.println(button.getName());
			 */
			
			cardPanel.setSize(frame.getWidth(),(cardSizeY*number_of_rows)+(padding_y*number_of_rows)+padding_y);

			cardPanel.add(button);
			row_x+=1;
			if (row_x == buttons_per_row)
			{
				row_x-=buttons_per_row;
				row_y+=1;
			}
		}

		//Set everything up to be visible...
		frame.setLayout(null);//using no layout managers 
		frame.setVisible(true);//making the frame visible

		frame.add(headerPanel);
		frame.add(cardPanel);
		frame.add(footerPanel);
		frame.add(collectedPanel);

		collectedPanel.setLocation(0,550);
		collectedPanel.setVisible(true);

		footerPanel.setLocation(0,680);
		footerPanel.setVisible(true);

		cardPanel.setLocation(0,headerHeight);
		cardPanel.setVisible(true);

		headerPanel.setVisible(true);

	}


	//Hides Cards when matched
	public void RequestDelete(JButton buttonToDelete)
	{
		disableAll(cardPanel);
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonToDelete.setVisible(false); // Make the button invisible after a delay
			}
		});
		timer.setRepeats(false); // Only fire the timer once
		timer.start(); // Start the timer
	}
	
	
	//Flips cards face down when 2 cards don't match
	public void RequestFaceDown(JButton button) {
		disableAll(cardPanel);	
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ImageIcon newIcon = new ImageIcon("src/imgs/00.png");
				button.setIcon(newIcon);

			}
		});
		timer.setRepeats(false); // Only fire the timer once
		timer.start(); // Start the timer
	}
	
	//Removes Listener Events, so users have to wait for cards to flip/destroy
	private static void disableAll(Container panel) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;
				ActionListener[] listeners = button.getActionListeners();
				for (ActionListener listener : listeners) {
					button.removeActionListener(listener); // Remove all listeners

					ActionListener reAddListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							addOriginalListeners(button, listeners);
						}
					};
					Timer timer = new Timer(1000, reAddListener); // 1 seconds delay
					timer.setRepeats(false);
					timer.start();
				}
			}
		}
	}

	//adds the listeners back in after a timer, so buttons can be pushed again.
	private static void addOriginalListeners(JButton button, ActionListener[] listeners) {
		for (ActionListener listener : listeners) {
			button.addActionListener(listener); // Re-add the original listeners
		}
	}

	//Starts the game timer
	public void startTimer() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerLabel.setText(""+seconds);
				//System.out.println("Timer: " + seconds + " seconds");
				seconds++;
			}
		});
		timer.start();
	}

	//Stops the game timer
	public void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
	}


	//Set flips value... used in GameLogic
	public void setFlips(int flips)
	{
		flipLabel.setText(""+flips);
		this.flips = flips;
	}

	//Adds images of matched cards to collection panel to see who you have collected so far.	
	public void addCollected(String img)
	{
		try {
			//System.out.println("Firing Collection Addition");
			collected+=1;
			String imgPath = "src/imgs/"+img;
			// System.out.println(imgPath);
			BufferedImage myPicture = ImageIO.read(new File((String)imgPath));
			BufferedImage scaledImage = scaleImage(myPicture, 75, 75); // Scales Image down from 100*100 to 75*75
			JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
			collectedPanel.add(picLabel);
			picLabel.setBounds(0+(collected_size*collected), 13,collected_size,collected_size);
			//debug
			//Point location = picLabel.getLocation();
			//System.out.println("X:"+location.x +",\t Y: "+ location.y);
			//System.out.println("End Colloection");
		}
		catch (Exception e) {System.out.println(e);}
	}

	//Scales images for collection
	public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
		Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.drawImage(scaled, 0, 0, null);
		g2d.dispose();
		return scaledImage;
	}

	//sets hi score on hi score panel
	public void setHiScore (int flips, int time)
	{
		hiScoreLabel.setText("Hi Score:   Flips: "+flips+"     Time Taken: "+time);
	}

	//returns flips taken to GameLogic
	public int getFlips () {
		return flips;    	
	}
	//Returns time to GameLogic
	public int getTime () {
		return seconds;
	}
	
	//Game Over popup box
	public void GameOver(int flips, int time) {
		String message = "You win! You completed the game with "+flips+" flips, in "+time+" seconds!";
		JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0); // Exit the application
	}





}
