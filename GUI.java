package iBird;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Frame;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Color;

public class GUI extends JFrame {
	private JTextField txtEnterBirdName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setMinimumSize(new Dimension(600, 600));
		setTitle("iBird By Team 5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		
		JButton btnSearch = new JButton("Search");
		
		txtEnterBirdName = new JTextField();
		txtEnterBirdName.setText("Enter Bird Name Here");
		txtEnterBirdName.setColumns(10);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		
		JTextArea txtrTest = new JTextArea();
		txtrTest.setText("This is where the bird name and other info goes\r\n\r\nThe top left image is actually a button that is loaded with a\r\npicture of the bird. When clicked, the bird sound will appear.\r\n\r\nThe right picture is the picture of the map of where the bird is found");
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
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon("C:\\Users\\Martin\\Desktop\\arcticunoMap.JPG"));
		panel_1.add(label, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnBirdPic = new JButton("");
		btnBirdPic.setMargin(new Insets(0, 0, 0, 0));
		btnBirdPic.setIcon(new ImageIcon("D:\\My Pictures\\need sorting\\Articuno.png"));
		panel.add(btnBirdPic, BorderLayout.CENTER);
		getContentPane().setLayout(groupLayout);
	}
}
