package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.Cow;
import data.Farmer;
import service.FarmerServiceInterface;
import service.ServiceResponse;

import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Component;

public class TransferCowPopUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private FarmerServiceInterface fsi;


	/**
	 * Create the frame.
	 */
	public TransferCowPopUp(ListModel<Farmer> lm, FarmerServiceInterface fsi, final List<Cow> transfers) {
		final JFrame thisFrame = this;
		
		setTitle("Cow Transfer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel titleLabel = new JLabel("Recieving Farmer");
		contentPane.add(titleLabel, BorderLayout.NORTH);
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.SOUTH);
		
		JButton cancelBtn = new JButton("Cancel");
		horizontalBox.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
			}
			
		});
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		
		JButton transferBtn = new JButton("Transfer");
		horizontalBox.add(transferBtn);
		
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JList<Farmer> list = new JList<>(lm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JLabel warningLabel = new JLabel("");
		scrollPane.setColumnHeaderView(warningLabel);
		
		transferBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Farmer farmer = list.getSelectedValue();
				if(farmer == null) {
					warningLabel.setForeground(Color.red);
					warningLabel.setText("You must select a farmer to transfer too first!!");
					return;
				}
				ServiceResponse response = fsi.transferCowsToFarmer(transfers, farmer);
				if(!response.isSuccess()) {
					warningLabel.setForeground(Color.red);
					warningLabel.setText(response.getMessage());
					return;
				}
				thisFrame.dispose();
			}
			
		});
	}

}
