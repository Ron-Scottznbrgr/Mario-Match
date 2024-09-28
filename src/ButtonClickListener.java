import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonClickListener implements ActionListener {

	 private GameLogicManager gameLogicManager;
	 
	 public ButtonClickListener(GameLogicManager gameLogicManager) {
	        this.gameLogicManager = gameLogicManager;
	    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		   JButton clickedButton = (JButton) e.getSource();
		   
		   String imagePath = ("src/imgs/"+clickedButton.getName());
		  
		   ImageIcon newIcon = new ImageIcon(imagePath);
		   clickedButton.setIcon(newIcon);
		   gameLogicManager.checkData(clickedButton);
		   
	}

}
