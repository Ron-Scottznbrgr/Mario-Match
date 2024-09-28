import javax.swing.JButton;

public class GameLogicManager {

	private LayoutManager layoutManager;
	private FileManager fileManager;

	private int cardsFlipped=0;
	private int totalCardsFlipped=0;
	private JButton lastButton;
	private int buttonsRemaining=32;

	private int previousHiFlips;
	private int previousHiTime;

	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager=fileManager;
	}

	public void gameInit ()	//grabs hi score
	{
		fileManager.createFile();
		fileManager.readFile();
		layoutManager.layout_create();
		layoutManager.startTimer();
		
	}

	public void gameEnd()
	{
		layoutManager.stopTimer();

		int currentFlips;
		int currentTime;

		currentFlips = layoutManager.getFlips();
		currentTime = layoutManager.getTime();

		if ((currentFlips <= previousHiFlips)&&(currentTime <= previousHiTime))
		{				
			fileManager.createFile();
			fileManager.writeFile(currentFlips, currentTime);
		}
		layoutManager.GameOver(currentFlips, currentTime);
	}

	public void checkData (JButton button)
	{
		

		if (cardsFlipped==0)
		{
			totalCardsFlipped+=1;
			layoutManager.setFlips(totalCardsFlipped);
			cardsFlipped++;
			lastButton=button;
		}
		else 
		{

			if (button.equals(lastButton))
			{
				//do nothing, can't select same button twice
			}
			else
			{
				totalCardsFlipped+=1;
				layoutManager.setFlips(totalCardsFlipped);
				cardsFlipped--;

				if (button.getName()==lastButton.getName())
				{
					destroy(button);
					destroy(lastButton);
					buttonsRemaining-=2;
					try {
						layoutManager.addCollected((String)(button.getName()));	//adds image to collection panel

						if (buttonsRemaining<=0)
						{
							gameEnd();	
						}

					}
					catch (Exception e) {System.out.println("Failed in GameLogic");

					}
				}
				else
				{
					facedown(button);		//if no match, flip both cards down
					facedown(lastButton);
				}
			}
		}
	}

	public void destroy(JButton button)
	{
		layoutManager.RequestDelete(button);	//"deletes" button... visible = false;
	}

	public void facedown(JButton button)
	{
		layoutManager.RequestFaceDown(button);	//flips buttons face down
	}

	public void setPreviousHighs(int flips, int time)
	{
		previousHiFlips=flips;
		previousHiTime=time;
	}
}
