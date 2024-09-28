import java.io.*;
public class FileManager {

	private LayoutManager layoutManager;
	private GameLogicManager gameManager;
	private boolean fileExists = false; 


	private int hiflips;
	private int hitime;

	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	public void setGameLogicManager(GameLogicManager gameManager) {
		this.gameManager = gameManager;
	}


	public void createFile() {
		try {
			File file = new File("hiscore.txt");
			if (file.createNewFile()) 
			{
				//System.out.println("File has been created!");
				fileExists = true;
				writeFile(999,999);	//default vaules
			} 
			else 
			{
				//System.out.println("File already exists.");
				fileExists = true;
			}
		} 

		catch (IOException e) {
			//System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}


	public void writeFile(int flips, int time) {

		if (fileExists)
		{
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("hiscore.txt"))) {
				writer.write(flips + "\n"+time);
				//System.out.println("F:\t"+flips+"\tT:\t"+time);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 

	public void readFile() {

		if (fileExists)
		{
			try {
				String[] nums = readVariablesFromFile();
				hiflips = Integer.parseInt(nums[0]);
				hitime = Integer.parseInt(nums[1]);
				layoutManager.setHiScore(hiflips, hitime);
				gameManager.setPreviousHighs(hiflips, hitime);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		else 
		{
			//System.out.println("File is empty.");
		}
	}


	public static String[] readVariablesFromFile() throws IOException {
		String[] nums = new String[2];
		try (BufferedReader reader = new BufferedReader(new FileReader("hiscore.txt"))) {
			nums[0] = reader.readLine();
			nums[1] = reader.readLine();
		}
		return nums;
	}
}