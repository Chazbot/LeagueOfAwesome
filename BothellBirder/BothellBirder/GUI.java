/**
 * 
 */
package BothellBirder;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

/**
 * @author Bret Van Hof
 *
 */
public class GUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JButton nameSearch;
	private JTextField searchBox;
	private JList<?> listJList;
	private JScrollPane jScrollPane;
	private JPanel listPanel;
	private ArrayList<ImageIcon> images;
	private ArrayList<BirdName> birdNames;
	private ArrayList<BirdName> updater;
	private BirdNameRetriever birdNamer;
	private ImageMaker imager;
	private JPanel nameSearcher;
	private JPanel imagePanel;
	private JButton searchByFeatures;
	private JLabel pic;
	private JButton resetList;
	private JPanel buttonPanel;
	private JButton displayBird;
	private Map<Integer,String> mySet;
	private  Map<String, ImageIcon> icons = new TreeMap<String, ImageIcon>();
	private ArrayList<BirdName> newer = new ArrayList<BirdName>();
	private Set<BirdName> uniqueSetOfBirdNameOb = new LinkedHashSet<BirdName>();
	private ListCellRenderer lr;
	private boolean hasAdded = false;
	private ActionListener listener;
	private JButton next = new JButton("Select Feature and Continue Search");
	private JLabel featuresJlistJLabel = new JLabel();
	private JList<String> selectableFeaturesJList = new JList<String>();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private Map<String, ArrayList<String>> featureToSelectableFeatureMap = new TreeMap<String, ArrayList<String>>();
	/**
	 * @throws HeadlessException
	 */
	public GUI() throws HeadlessException 
	{
		createData();
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
		setTitle("BothellBirder by Team 5");
		setLayout(new BorderLayout());
		this.setIconImage(Toolkit.getDefaultToolkit().getImage
				("defaultBird.jpg")); //sets icon
		initComponents();
	}
	class Reseter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			listPanel.removeAll();
			createList();
			listPanel.revalidate();
		}
	}
	class Birder implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int birdId = 0;
			ArrayList<String> names = new ArrayList<String>();
			for(BirdName birdName : birdNames)
			{
				if(birdName.getNameId() == (Integer)listJList.getSelectedValue())
				{
					birdId = birdName.getBirdId();
				}
			}
			for(BirdName aName : birdNames)
			{
				if(aName.getBirdId() == birdId)
					names.add(aName.getName());
			}
			if(birdId != 0)
			{
				JFrame display = new BirdGUI(birdId, names);
				display.setVisible(true);
				GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
				display.setExtendedState(display.getExtendedState() | display.MAXIMIZED_BOTH);
				setTitle("BothellBirder by Team 5");
			}
		}
	}

	class Searcher implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			makeFeaturesJList(0);
		}
	}
	class Next implements ActionListener
	{
		private String[] featureNames;
		private int theIndexOfTheCurrentFeature;
		private int[] IDS;
		Next(String[] a, int index, int[] counter)
		{
			featureNames = a; 
			theIndexOfTheCurrentFeature = index;
			IDS = counter;
		}
		public void actionPerformed(ActionEvent e)
		{
			if(theIndexOfTheCurrentFeature >= featureNames.length - 1)
			{
				imagePanel.removeAll();
				imagePanel.add(pic);
				imagePanel.revalidate();
				theIndexOfTheCurrentFeature = 0;
				hasAdded = false;
				return;
			}
			else
			{
				ArrayList<BirdName> newer = new ArrayList<BirdName>();
				try 
				{
					int featr = birdNamer.getFeatureID(featureNames[theIndexOfTheCurrentFeature]);
					int aGoodIndex = selectableFeaturesJList.getSelectedIndex() + 1;
					
					updater = birdNamer.updateData(featr, aGoodIndex);
					
					if(!hasAdded)
					{
						uniqueSetOfBirdNameOb = new LinkedHashSet<BirdName>(updater);
						hasAdded = true;
						ArrayList<BirdName> updatedList = new ArrayList<BirdName>();
						updatedList.addAll(uniqueSetOfBirdNameOb);
					}
					else
					{
						System.out.println("2nd shot: " + updater.size());
						BirdName[] b = new BirdName[uniqueSetOfBirdNameOb.size()];
						int theIndex = 0;
						for(BirdName a : uniqueSetOfBirdNameOb)
						{
							boolean stillExists = false;
							
							for(int z = 0; z < updater.size(); z++)
							{
								if(updater.get(z).getBirdId() == a.getBirdId())
								{
									stillExists = true;
								}
							}
							if(!stillExists)
							{
								b[theIndex] = a;
								theIndex++;
							}
						}
						for(int j = 0; j < theIndex; j++)
						{
							uniqueSetOfBirdNameOb.remove(b[j]);
						}
					}
					
						
					listJList.removeAll();
					Integer[] birdID = new Integer[uniqueSetOfBirdNameOb.size()];

					int k = 0;
					ArrayList<BirdName> updatedList = new ArrayList<BirdName>();
					for(BirdName ID : uniqueSetOfBirdNameOb)
					{
						birdID[k] = (Integer)ID.getBirdId();
						k++;
					}	
	                updatedList.addAll(uniqueSetOfBirdNameOb);
					updateList(updatedList);
					boolean hasAdded = true;
					makeFeaturesJList(theIndexOfTheCurrentFeature + 1);
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		}
	}

	private void createData()
	{
		birdNames = new ArrayList<BirdName>();
		images = new ArrayList<ImageIcon>();
		birdNamer = new BirdNameRetriever();
		imager = new ImageMaker();
		try 
		{
			birdNames = birdNamer.readData();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Can't connect to database!", 
					"", JOptionPane.WARNING_MESSAGE);
		}
	}

	public ActionListener makeThis(String[] a, int b, int[] c)
	{
			ActionListener nextAction =   new Next(a, b, c);
			return nextAction;
	}
    private void makeFeaturesJList(int anIndex)
    {
    	descriptionMaker make = new descriptionMaker();
		try {
			featureToSelectableFeatureMap = make.getFeatures();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] featureName = new String[featureToSelectableFeatureMap.size()];
		int z = 0;
		for (Map.Entry<String, ArrayList<String>> entry : featureToSelectableFeatureMap.entrySet())
		{
			featureName[z] = entry.getKey();
			z++;
		}
		ArrayList<String> feature = new ArrayList<String>
		(featureToSelectableFeatureMap.get(featureName[anIndex]));//new ArrayList<String>();
		int[] IDS = {};
		if(feature.size() == 2)
		{
			IDS = new int[2];
		}
		else
		{
			IDS = new int[feature.size()/2 + 1];
		}
		ArrayList<String> list = featureToSelectableFeatureMap.get(featureName[anIndex]);
		int counter = 0;
		for(int g = 1; g < IDS.length - 2; g += 2)
		{
			int featureID = Integer.parseInt(list.get(g));
			IDS[counter] = featureID;
			counter++;
		}
		for(int index = 0; index < list.size(); index++)
		{
			list.remove(index+1);
		}
		int i = 0;
		model.clear();
		for(String fam: list)
		{
			model.add(i, fam);
			i++;
		}
		JScrollPane jScrollPanes = new javax.swing.JScrollPane();
		String[] as = new String[list.size()];
		selectableFeaturesJList = new JList<String>(list.toArray(as));
		selectableFeaturesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectableFeaturesJList.setModel(model);
		selectableFeaturesJList.setSelectedIndex(0);
		jScrollPanes.setViewportView(selectableFeaturesJList);
		next = new JButton("Next set of criteria");
		imagePanel.removeAll();
		JLabel aFeatureName = new JLabel(featureName[anIndex]);
		imagePanel.add(aFeatureName);
		imagePanel.add(featuresJlistJLabel);
		imagePanel.add(jScrollPanes);
		listener = makeThis(featureName, anIndex, IDS);
		next.addActionListener(listener);
		imagePanel.add(next);
		imagePanel.revalidate();	
    }
	private void initComponents() 
	{
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		createJPanels();
		createButtons();
		createOther();
		createList();
	}

	private void createOther()
	{
		pic = new JLabel(new ImageIcon("defaultBird.jpg"));
		imagePanel.add(pic);
		searchBox = new JTextField();
		searchBox.setText("Enter Bird Name Here");
		searchBox.setColumns(20);
		nameSearcher.add(searchBox);
		searchBox.grabFocus();
	}

	private void createJPanels()
	{
		listPanel = new JPanel();
		imagePanel = new JPanel();
		nameSearcher = new JPanel();
		buttonPanel = new JPanel();
		this.add( listPanel, BorderLayout.WEST);
		this.add( nameSearcher, BorderLayout.NORTH);
		this.add( imagePanel, BorderLayout.EAST);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private void createButtons()
	{
		nameSearch = new JButton("Search by name");
		searchByFeatures = new JButton("Search by features");
		displayBird = new JButton("Display Bird Selected in List");
		ActionListener dis = new Birder();
		displayBird.addActionListener(dis);
		ActionListener feat = new Searcher();
		searchByFeatures.addActionListener(feat);
		resetList = new JButton("Reset List to default (clear features search)");
		ActionListener reset = new Reseter();
		resetList.addActionListener(reset);
		nameSearcher.add(nameSearch);
		buttonPanel.add(searchByFeatures);
		buttonPanel.add(resetList);
		buttonPanel.add(displayBird);
	}
	private void updateList(ArrayList<BirdName> listOfBirds)
	{
		updateJList(listOfBirds);
		int k = 0;
		Integer[] birdID = new Integer[listOfBirds.size()];
		for(BirdName IDa : listOfBirds)
		{
			birdID[k] = (Integer)IDa.getNameId();
			k++;
		}
		listJList = null;
		listJList = new JList<Integer>(birdID);
		listJList.setSelectedIndex(0);
		listJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane = null;
		jScrollPane = new javax.swing.JScrollPane();
		jScrollPane.setViewportView(listJList);
		listPanel.removeAll();
		lr = new listRenderer(icons, mySet);
		listJList.setCellRenderer(lr);
		listPanel.add(jScrollPane, java.awt.BorderLayout.WEST);
		listPanel.revalidate();
	}

	private void createList()
	{
		getInitJListData();
		Integer[] birdID = new Integer[birdNames.size()];
		int i = 0;
		for(BirdName ID : birdNames)
		{
			birdID[i] = (Integer)ID.getNameId();
			i++;
		}
		listJList = new JList<Integer>(birdID);
		listJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listJList.setSelectedIndex(0);
		jScrollPane = new javax.swing.JScrollPane();
		jScrollPane.setViewportView(listJList);
		listPanel.add(jScrollPane, java.awt.BorderLayout.WEST);
		lr = new listRenderer(icons, mySet);
		listJList.setCellRenderer(lr);
	}

	private void getInitJListData()
	{
		icons = new TreeMap<String, ImageIcon>();
		images = new ArrayList<ImageIcon>();
		ImageIcon test = new ImageIcon("0a0.jpg");
		mySet = new TreeMap<Integer, String>();
		int index = 0;
		for(BirdName name : birdNames)
		{
			try {
				images.add(imager.readData(name.getBirdId(), name.getNameId()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			icons.put(name.getName(), images.get(index));
			mySet.put(name.getNameId(), name.getName());
			index++;
		}
		if(birdNames.size() == 0)
			icons.put("empty", test);
	}
    
	private void updateJList(ArrayList<BirdName> listOfBirdNameObjects)
	{
		icons = new TreeMap<String, ImageIcon>();
		images = new ArrayList<ImageIcon>();
		mySet = new TreeMap<Integer, String>();
		ImageIcon test = new ImageIcon("0a0.jpg");
		int index = 0;
		for(BirdName name : listOfBirdNameObjects)
		{
			try {
				images.add(imager.readData(name.getBirdId(), name.getNameId()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			icons.put(name.getName(), images.get(index));
			mySet.put(name.getNameId(), name.getName());
			index++;
		}
		if(birdNames.size() == 0)
			icons.put("empty", test); 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		java.awt.EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				JFrame.setDefaultLookAndFeelDecorated(true);
				new GUI().setVisible(true);                
			} 
		});  
	}
}
