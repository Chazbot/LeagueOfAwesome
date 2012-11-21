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
    private Map<String, Integer> mySet;
    private  Map<Object, ImageIcon> icons = new HashMap<Object, ImageIcon>();
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
	
	class Birder implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFrame display = new BirdGUI((Integer) listJList.getSelectedValue());
			display.setVisible(true);
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    display.setExtendedState(display.getExtendedState() | display.MAXIMIZED_BOTH);
	        setTitle("BothellBirder by Team 5");
		}
	}
	
	class Searcher implements ActionListener
	{
		
		JButton next = new JButton("Select Feature and Continue Search");
		private JList<String> features = new JList<String>();
		private DefaultListModel<String> model = new DefaultListModel<String>();
		private Map<String, ArrayList<String>> my = new TreeMap<String, ArrayList<String>>();
		public void actionPerformed(ActionEvent e)
		{
			//Frame owner = new Frame();
			//String title = "Search Dialog";
			//JDialog pop = new JDialog(owner, title, true);
			//pop.setVisible(true);
			//pop.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			descriptionMaker make = new descriptionMaker();
			try {
				my = make.getFeatures();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] a = new String[my.size()];
			int z = 0;
				
			for (Map.Entry<String, ArrayList<String>> entry : my.entrySet())
			{
				a[z] = entry.getKey();
				System.out.println(a[z]);
				z++;
			}
			ArrayList<String> list = new ArrayList<String>(my.get(a[0]));//new ArrayList<String>();
			updater = new ArrayList<BirdName>();
			birdNamer = new BirdNameRetriever();
			int IDs = 0;
			try {
				IDs = birdNamer.getFeatureID(a[0]);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try 
		    {
			
				int[] IDS = new int[list.size()/2];
				int counter = 0;
				for(int g = 1; g < IDS.length; g += 2)
				{
					int featureID = Integer.parseInt(list.get(g));
					IDS[counter] = featureID;
					counter++;
				}
		    	 updater = birdNamer.updateData(IDs, IDS);
			}
			catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			//listJList.removeAll();
			Integer[] birdID = new Integer[updater.size()];
			int k = 0;
	        for(BirdName IDa : updater)
	        {
	        	birdID[k] = (Integer)IDa.getBirdId();
	        	k++;
	        }
//	        listJList = new JList<Object>(birdID);
//			listJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	        jScrollPane = new javax.swing.JScrollPane();
//	        jScrollPane.setViewportView(listJList);
	        listPanel.removeAll();
//       	listPanel.add(jScrollPane, java.awt.BorderLayout.WEST);
//	        listJList.setCellRenderer(new listRenderer(icons));
	        updateList(updater);
	        listPanel.revalidate();
	        listJList.revalidate();
			int i = 0;
			for(String fam: list)
			{
				System.out.println(fam);
				model.add(i, fam);
				i++;
			}
			features.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        JScrollPane jScrollPanes = new javax.swing.JScrollPane();
	        String[] as = new String[list.size()];
	        features = new JList<String>(list.toArray(as));
	        features.setModel(model);
	        jScrollPanes.setViewportView(features);
			ActionListener listener = new Next(a, 1);
			next.addActionListener(listener);
			imagePanel.removeAll();
			imagePanel.add(jScrollPanes);
			imagePanel.add(next);
			imagePanel.revalidate();
		}
		class Next implements ActionListener
		{
			private String[] b;
			private int theIndex;
			Next(String[] a, int index)
			{
				b = a; 
				theIndex = index;
			}
			public void actionPerformed(ActionEvent e)
			{
				if(theIndex >= b.length)
				{
					imagePanel.removeAll();
					imagePanel.add(pic);
					imagePanel.revalidate();
					theIndex = 0;
					return;
				}
				else
				{
					
				ArrayList<BirdName> newer = new ArrayList<BirdName>();
				try 
			    {
					int featr = birdNamer.getFeatureID(b[theIndex]);
					System.out.println("" + featr);
					ArrayList<String> feature = my.get(b[theIndex]);
					int[] IDS = new int[feature.size()/2];
					int counter = 0;
					for(int g = 1; g < IDS.length; g += 2)
					{
						int featureID = Integer.parseInt(feature.get(g));
						IDS[counter] = featureID;
						counter++;
					}
					birdNamer.updateData(featr, IDS);
			    }
				catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				for(BirdName ID : updater)
				{
					newer.add(ID);
				}
				Set<BirdName> Unique_set = new LinkedHashSet<BirdName>(newer);
				listJList.removeAll();
				Integer[] birdID = new Integer[Unique_set.size()];
				ArrayList<String> list = my.get(b[theIndex]);
				int k = 0;
		        for(BirdName ID : Unique_set)
		        {
		        	birdID[k] = (Integer)ID.getBirdId();
		        	k++;
		        }
		        listJList = new JList<Object>(birdID);
				listJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		        jScrollPane = new javax.swing.JScrollPane();
		        jScrollPane.setViewportView(listJList);
		        listPanel.removeAll();
		        listPanel.add(jScrollPane, java.awt.BorderLayout.WEST);
		        listJList.setCellRenderer(new listRenderer(icons));
		        listPanel.revalidate();
				int i = 0;
				model.clear();
				for(String fam: list)
				{
					model.add(i, fam);
					i++;
				}
				imagePanel.removeAll();
				features.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		        JScrollPane jScrollPanes = new javax.swing.JScrollPane();
		        features = new JList<String>(model);
		        jScrollPanes.setViewportView(features);
				imagePanel.add(jScrollPanes);
				imagePanel.add(next);
				imagePanel.revalidate();
				theIndex += 2;
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
			JOptionPane.showMessageDialog(null, "Can't connect to database.  Closing program!", 
					"", JOptionPane.WARNING_MESSAGE);
			//System.exit(-1);
		}
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
		nameSearcher.add(nameSearch);
		buttonPanel.add(searchByFeatures);
		buttonPanel.add(resetList);
		buttonPanel.add(displayBird);
	}
	private void updateList(ArrayList<BirdName> um)
	{
		icons = updateJList(um);
        ArrayList<Integer> birdID = new ArrayList<Integer>();
        for(BirdName ID : um)
        	birdID.add((Integer)ID.getBirdId());
		listJList = new JList<Object>(birdID.toArray());
		listJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane = new javax.swing.JScrollPane();
        jScrollPane.setViewportView(listJList);
        listPanel.add(jScrollPane, java.awt.BorderLayout.WEST);
        listJList.setCellRenderer(new listRenderer(icons));
	}
	
	private void createList()
	{
        icons = getInitJListData();
        ArrayList<Integer> birdID = new ArrayList<Integer>();
        for(BirdName ID : birdNames)
        	birdID.add((Integer)ID.getBirdId());
		listJList = new JList<Object>(birdID.toArray());
		listJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane = new javax.swing.JScrollPane();
        jScrollPane.setViewportView(listJList);
        listPanel.add(jScrollPane, java.awt.BorderLayout.WEST);
        listJList.setCellRenderer(new listRenderer(icons));
	}
	
	private Map<Object, ImageIcon> getInitJListData()
	{
        Map<Object, ImageIcon> icons = new HashMap<Object, ImageIcon>();
        ImageIcon test = new ImageIcon("0a0.jpg");
        for(BirdName name : birdNames)
        {
        	try {
        			images.add(imager.readData(name.getBirdId(), name.getNameId()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	icons.put(name.getName(), test);
        	mySet = new HashMap<String, Integer>();
        	mySet.put(name.getName(), name.getBirdId());
        }
        if(birdNames.size() == 0)
        	icons.put(0, test);
        return icons;
	}
	
	private Map<Object, ImageIcon> updateJList(ArrayList<BirdName> um)
	{
		 Map<Object, ImageIcon> icons = new HashMap<Object, ImageIcon>();
	        ImageIcon test = new ImageIcon("0a0.jpg");
	        for(BirdName name : um)
	        {
	        	try {
	        			images.add(imager.readData(name.getBirdId(), name.getNameId()));
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        	icons.put(name.getName(), test);
	        	mySet = new HashMap<String, Integer>();
	        	mySet.put(name.getName(), name.getBirdId());
	        }
	        if(birdNames.size() == 0)
	        	icons.put(0, test);
	        return icons;
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
