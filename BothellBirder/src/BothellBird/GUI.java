package BothellBird;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Frame;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class GUI extends JFrame 
{
	private JTextField txtEnterBirdName;
	private JButton btnSearch;
	private JPanel panel;
	private JPanel panel_1;
	private JTextArea txtrTest;
	private JLabel label;
	private JButton btnBirdPic;
	private ReadFile getStuff;
	private Bird displayBird;
	private HashMap<String, String> names; 

	/**
	 * Launch the application.
	 */
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
	class SoundAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			MakeSound play = new MakeSound();
			play.playSound("mySound.wav");		
		}
		
	}
	public class SearchAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			String birdName = txtEnterBirdName.getText().toLowerCase();
			birdName = names.get(txtEnterBirdName.getText());
			if(birdName == null)
			{
				txtrTest.setText("Bird Not Found!\r\n\r\nWe Hope you find a different bird!"
						 + "\r\nTry clicking your birds picture to hear it!"
						+ "\r\n\r\nThe right picture is the picture of the map of where the bird is found");
				label.setIcon(null);
				btnBirdPic.setIcon(null);
				panel.remove(btnBirdPic);
				panel_1.remove(label);
				label.setIcon(new ImageIcon("src\\BothellBird\\defaultMap.jpg"));
				btnBirdPic.setIcon(new ImageIcon("src\\BothellBird\\defaultBird.jpg"));
				btnBirdPic.setEnabled(false);
				panel_1.add(label, BorderLayout.EAST);
				panel.add(btnBirdPic, BorderLayout.WEST);
			}
			else
			{
				displayBird = new Bird();
//				for(int i = birdName.length(); i < 20; i++)
//					birdName += " ";
				displayBird.setName(birdName);
				try
				{
					getStuff = new ReadFile(displayBird);
					displayBird.setDescription(getStuff.readDescription());
					displayBird.setLocation(getStuff.readLocation());
					displayBird.setMap(getStuff.hasMap());
					displayBird.setPhoto(getStuff.hasPhoto());
					displayBird.setSound(getStuff.hasSound());	
				
					txtrTest.setText("Location of Bird: " + displayBird.getLocation()
						+ "\nDescription: " + displayBird.getDescription());
					if(displayBird.getMap())
					{
						panel_1.remove(label);
						label.setIcon(null);
						getStuff.getMap();
						label.setIcon(new ImageIcon("myMap.jpg"));
						panel_1.add(label, BorderLayout.WEST);
					}
					if(displayBird.getPhoto())
					{
						btnBirdPic.setIcon(null);
						panel.remove(btnBirdPic);
						getStuff.getImage();
						btnBirdPic.setIcon(new ImageIcon("myImage.jpg"));
						panel.add(btnBirdPic, BorderLayout.WEST);
					}
					if(displayBird.getSound())
					{
						getStuff.getSound();
						btnBirdPic.setEnabled(true);
					}
				}
				catch(SQLException error)
				{
					JOptionPane.showMessageDialog(null, "Can't connect to database.  Closing program!", 
							"", JOptionPane.WARNING_MESSAGE);
					System.exit(1);
				}
			}
				
		}
	}
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
			}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() 
	{
		initComponents();
		setMinimumSize(new Dimension(600, 600));
		setTitle("iBird By Team 5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
	}
	private void initComponents()
	{
		btnSearch = new JButton("Search");
		ActionListener searcher = new SearchAction(); 
		btnSearch.addActionListener(searcher);
		getStuff = new ReadFile();
		try
		{
			names = getStuff.readNames();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Can't connect to database.  Closing program!", 
					"", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		txtEnterBirdName = new JTextField();
		txtEnterBirdName.setText("Enter Bird Name Here");
		txtEnterBirdName.setColumns(10);
		
		panel = new JPanel();
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		
		txtrTest = new JTextArea();
		txtrTest.setText("Welcome To Bothell Bird!\r\n\r\nWe Hope you find the bird you are looking for!"
				 + "\r\nTry clicking your birds picture to hear it!"
				+ "\r\n\r\nThe right picture is the picture of the map of where the bird is found");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtEnterBirdName, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
							.addGap(12)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
						.addComponent(txtrTest, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch)
						.addComponent(txtEnterBirdName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(txtrTest, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon("src\\BothellBird\\defaultMap.jpg"));
		panel_1.add(label, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnBirdPic = new JButton("");
		btnBirdPic.setMargin(new Insets(0, 0, 0, 0));
		btnBirdPic.setIcon(new ImageIcon("src\\BothellBird\\defaultBird.jpg"));
		ActionListener soundOff = new SoundAction();
		btnBirdPic.addActionListener(soundOff);
		panel.add(btnBirdPic, BorderLayout.CENTER);
		getContentPane().setLayout(groupLayout);
	}
}
