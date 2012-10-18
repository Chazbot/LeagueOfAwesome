import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUIB extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
    {
		JFrame GUI = new GUIB();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setVisible(true);
		GUI.setSize(800, 600);
		ReadFile read = new ReadFile();
		try {
			File myFile = read.readData();
			BufferedImage img = null;
			try {
			    img = ImageIO.read(myFile);
			    GUI.getContentPane().setLayout(new FlowLayout()); 
			    GUI.getContentPane().add(new JLabel(new ImageIcon(img))); 
			    GUI.pack(); 

			} catch (IOException e) {
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
