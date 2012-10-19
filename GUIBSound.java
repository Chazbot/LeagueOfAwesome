import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File; 
import java.io.IOException; 
 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.DataLine; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.SourceDataLine; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUIBSound extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static void main(String[] args)
    {
		
		class MakeSound { 
			 
		    private final int BUFFER_SIZE = 128000; 
		    private File soundFile; 
		    private AudioInputStream audioStream; 
		    private AudioFormat audioFormat; 
		    private  SourceDataLine sourceLine; 
		 
		    /** 
		     *  
		     * @param filename the name of the file that is going to be played 
		     * 
		     */ 
		    public void playSound(String filename){ 
		 
		        String strFilename = filename; 
		 
		        try { 
		            soundFile = new File(strFilename); 
		        } catch (Exception e) { 
		            e.printStackTrace(); 
		            System.exit(1); 
		        } 
		 
		        try { 
		            audioStream = AudioSystem.getAudioInputStream(soundFile); 
		        } catch (Exception e){ 
		            e.printStackTrace(); 
		           System.exit(1); 
		        } 
		 
		        audioFormat = audioStream.getFormat(); 
		 
		        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat); 
		        try { 
		            sourceLine = (SourceDataLine) AudioSystem.getLine(info); 
		            sourceLine.open(audioFormat); 
		        } catch (LineUnavailableException e) { 
		            e.printStackTrace(); 
		            System.exit(1); 
		        } catch (Exception e) { 
		            e.printStackTrace(); 
		            System.exit(1); 
		        } 
		 
		        sourceLine.start(); 
		 
		        int nBytesRead = 0; 
		        byte[] abData = new byte[BUFFER_SIZE]; 
		        while (nBytesRead != -1) { 
		            try { 
		                nBytesRead = audioStream.read(abData, 0, abData.length); 
		            } catch (IOException e) { 
		                e.printStackTrace(); 
		            } 
		            if (nBytesRead >= 0) { 
		                @SuppressWarnings("unused") 
		                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead); 
		            } 
		        } 
		 
		        sourceLine.drain(); 
		        sourceLine.close(); 
		    } 
		} 
		class JButtonActionListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				MakeSound play = new MakeSound();
				play.playSound("myFile.wav");
				
			}
			
		}
		JFrame GUI = new GUIBSound();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setVisible(true);
		GUI.setSize(800, 600);
		ReadSound read = new ReadSound();
		try {
			File myFile = read.readData();
			BufferedImage img = null;
			    //img = ImageIO.read(myFile);
			    GUI.getContentPane().setLayout(new GridLayout()); 
			    //GUI.getContentPane().add(new JLabel(new ImageIcon(img)));
			    JButton sound = new JButton("Play Sound");
			    ActionListener listen = new JButtonActionListener();
			    sound.addActionListener(listen);
			    GUI.getContentPane().add(sound);
			    GUI.pack(); 

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
