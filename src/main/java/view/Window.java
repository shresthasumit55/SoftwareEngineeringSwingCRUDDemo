package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import data.Cow;
import data.Farmer;
import model.ObservantTableModel;
import service.FarmerServiceInterface;
import service.ServiceResponse;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListModel;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

public class Window extends Observable {

	private JFrame frmFarmersHashcows;
	private JTable HashCowTable;
	private Farmer selectedFarmer;
	private List<Cow> clickedCows;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public Window(ListModel<Farmer> flm, ObservantTableModel<List<Cow>> sc, FarmerServiceInterface fsi) {
		initialize(flm, sc, fsi);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ListModel<Farmer> lm, ObservantTableModel<List<Cow>> otm, final FarmerServiceInterface fsi) {

		// Make a FarmerListModel for the list
		ListModel<Farmer> farmerListModel = lm;

		// Make a SelectedCowTableModel
		ObservantTableModel<List<Cow>> selectedCows = otm;
		
		frmFarmersHashcows = new JFrame();
		frmFarmersHashcows.setTitle("Farmer's Hashcows");
		frmFarmersHashcows.setBounds(100, 100, 450, 300);
		frmFarmersHashcows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane();
		frmFarmersHashcows.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBackground(UIManager.getColor("Button.background"));
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);

		JLabel lblNewLabel = new JLabel("Farmers");
		lblNewLabel.setBackground(Color.YELLOW);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane_1.setLeftComponent(lblNewLabel);

		Box verticalBox = Box.createVerticalBox();
		splitPane_1.setRightComponent(verticalBox);

		JList<Farmer> farmerList = new JList<>(farmerListModel);
		farmerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		farmerList.setBackground(new Color(255, 255, 255));
		verticalBox.add(farmerList);

		JSeparator separator = new JSeparator();
		verticalBox.add(separator);

		Component horizontalStrut = Box.createHorizontalStrut(150);
		verticalBox.add(horizontalStrut);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_2);

		JLabel lblNewLabel_1 = new JLabel("HashCows");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		splitPane_2.setLeftComponent(lblNewLabel_1);

		Box verticalBox_1 = Box.createVerticalBox();
		splitPane_2.setRightComponent(verticalBox_1);
		
		HashCowTable = new JTable(selectedCows);
		HashCowTable.setFillsViewportHeight(true);
		HashCowTable.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				ObservantTableModel<List<Cow>> cowModel = (ObservantTableModel<List<Cow>>)table.getModel();
				int[] selected = table.getSelectedRows();
				final List<Cow> allSelectedCows = cowModel.getObservedValue();
				clickedCows = new ArrayList<Cow>();
				for(Integer cowIndex: selected) {
					clickedCows.add(allSelectedCows.get(cowIndex));
				}
			}
		});;
		
		JScrollPane scrollPane = new JScrollPane(HashCowTable);
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		verticalBox_1.add(scrollPane);

		Component horizontalStrut_1 = Box.createHorizontalStrut(200);
		verticalBox_1.add(horizontalStrut_1);

		JMenuBar menuBar = new JMenuBar();
		frmFarmersHashcows.setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem addFarmerBtn = new JMenuItem("Add Farmer");
		addFarmerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						AddFarmerPopUp popup = new AddFarmerPopUp(fsi,null);
						popup.setVisible(true);
					}
				});
			}
		});
		mnMenu.add(addFarmerBtn);
		
		final JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		menuBar.add(warningLabel);

		JMenuItem removeFarmerBtn = new JMenuItem("Remove Farmer");
		removeFarmerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedFarmer == null) {
					warningLabel.setForeground(Color.RED);
					warningLabel.setText("Select a Farmer first!!");
					return;
				}
				ServiceResponse response = fsi.deleteFarmer(selectedFarmer);
				warningLabel.setForeground(response.isSuccess() ? Color.GRAY : Color.RED);
				warningLabel.setText(response.getMessage());
			}

		});
		mnMenu.add(removeFarmerBtn);

		JMenuItem editFarmerBtn = new JMenuItem("Edit Farmer");
		editFarmerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedFarmer == null) {
					warningLabel.setForeground(Color.RED);
					warningLabel.setText("Select a Farmer first!!");
					return;
				}
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						AddFarmerPopUp popup = new AddFarmerPopUp(fsi,selectedFarmer);
						popup.setVisible(true);
					}
				});
			}

		});
		mnMenu.add(editFarmerBtn);
		
		

		final Window myWindow = this;
		farmerList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				selectedFarmer = (Farmer) list.getSelectedValue();

				// The selected farmer has changed. Notify anyone who cares.
				myWindow.setChanged();
				myWindow.notifyObservers(selectedFarmer);

			}
		});
		
		JButton transferBtn = new JButton("Transfer Cow");
		verticalBox_1.add(transferBtn);
		transferBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(clickedCows == null || clickedCows.size() == 0) {
					warningLabel.setForeground(Color.RED);
					warningLabel.setText("You must pick cows to transfer first!!");
					return;
				}
				myWindow.setChanged();
				myWindow.notifyObservers(null);
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						TransferCowPopUp popup = new TransferCowPopUp(lm, fsi, clickedCows);
						clickedCows = new ArrayList<>();
						popup.setVisible(true);
					}
				});
			}
			
		});
	}
	
	public void setVisible(boolean visibility) {
		frmFarmersHashcows.setVisible(visibility);
	}

}
