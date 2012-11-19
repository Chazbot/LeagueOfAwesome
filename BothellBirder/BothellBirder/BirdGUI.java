package BothellBirder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BirdGUI extends JFrame 
{
	private JPanel panel;
	private JPanel panel_1;
	private JPanel control;
	private JTextArea txtrTest;
	private JLabel label;
	private JLabel namesLabel;
	private JButton[] btnBirdPic;
	private BirdNameRetriever getStuff;
	private int birdId;
	private ImageIcon male;
	private ImageIcon female;
	private ImageIcon am;
	private JButton back;

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

	class closeAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			disposer();		
		}
		
	}

	/**
	 * Create the frame.
	 */
	public BirdGUI(int ID) 
	{
		birdId = ID;
		initComponents();
		setMinimumSize(new Dimension(600, 600));
		setTitle("iBird By Team 5");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 600);
	}
	private void initComponents()
	{
		panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel_1 = new JPanel();
		control = new JPanel();
		txtrTest = new JTextArea();
		getBirdData();
		getText();
		panel_1.add(txtrTest);
		back = new JButton("Close Bird display and return to Bothell Bird");
		ActionListener close = new closeAction(); 
		back.addActionListener(close);
		control.add(back);
		this.setLayout(new BorderLayout());
		this.add(control, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		this.add(panel_1, BorderLayout.SOUTH);
	}
	public void disposer()
	{
		this.dispose();
	}
	private void getBirdData()
	{
		getStuff = new BirdNameRetriever();
		GenderRetriever sex = new GenderRetriever();
		String scientificName = "";
		try {
			scientificName = getStuff.getScientificName(birdId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedHashMap<String, String> genders = new LinkedHashMap<String, String>();
		try {
			genders = sex.getGender(birdId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		ArrayList<String> names = (ArrayList<String>) genders.keySet();
		ArrayList<JLabel> namesLabel = new ArrayList<JLabel>();
		ArrayList<String> labelTxt = new ArrayList<String>();
		labelTxt.add("Bird Species: " + scientificName + "\nCommon Name(s):\n");
		int i = names.size();
		btnBirdPic = new JButton[i];
		int k = 0;
		ActionListener soundOff = new SoundAction();
		for(String name : names)
		{
			ImageMaker maker = new ImageMaker();
			SoundMaker sMaker = new SoundMaker();
			String gen = genders.get(name);
			btnBirdPic[k] = new JButton("");
			btnBirdPic[k].setMargin(new Insets(0, 0, 0, 0));
			btnBirdPic[k].addActionListener(soundOff);
			if(gen.charAt(0) == 'm' || gen.charAt(0) == 'M')
			{
				name.concat(" ,(Male)");
				labelTxt.add(name);
				namesLabel.add(new JLabel(labelTxt.get(k)));
				panel.add(namesLabel.get(k));
				try {
					male = maker.bigImage(birdId, sex.getNameId(birdId, name));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				btnBirdPic[k].setIcon(male);
				panel.add(btnBirdPic[k]);
				try {
					File maleSound = sMaker.getSound(birdId, sex.getNameId(birdId, name));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(gen.charAt(0) == 'f' || gen.charAt(0) == 'F')
			{
				name.concat(" ,(Female)");
				labelTxt.add(name);
				namesLabel.add(new JLabel(labelTxt.get(k)));
				panel.add(namesLabel.get(k));
				try {
					female = maker.bigImage(birdId, sex.getNameId(birdId, name));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				btnBirdPic[k].setIcon(female);
				panel.add(btnBirdPic[k]);
				try {
					File femaleSound = sMaker.getSound(birdId, sex.getNameId(birdId, name));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					am = maker.bigImage(birdId, sex.getNameId(birdId, name));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				labelTxt.add(name);
				namesLabel.add(new JLabel(labelTxt.get(k)));
				panel.add(namesLabel.get(k));
				btnBirdPic[k].setIcon(am);
				panel.add(btnBirdPic[k]);
				try {
					File amSound = sMaker.getSound(birdId, sex.getNameId(birdId, name));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			k++;
		}	
	}
	
	private void getText()
	{
		String description = "";
		descriptionMaker descr = new descriptionMaker();
		try {
			description = descr.getDescription(birdId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtrTest.setText(description);
		txtrTest.setEditable(false);
	}
}
