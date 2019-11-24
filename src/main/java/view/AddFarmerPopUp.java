package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.Farmer;
import service.FarmerService;
import service.FarmerServiceInterface;
import service.ServiceResponse;

import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EtchedBorder;

public class AddFarmerPopUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private Farmer farmerInQuestion;

	
	
	public void setFarmerInQuestion(Farmer farmerInQuestion) {
		this.farmerInQuestion = farmerInQuestion;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFarmerPopUp frame = new AddFarmerPopUp(new FarmerService(),null);
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
	public AddFarmerPopUp(final FarmerServiceInterface fsi, Farmer selectedFarmer) {
		
		// Retain reference to this under different name
		final AddFarmerPopUp window = this;
		
		// Make a farmer
		if (selectedFarmer==null) {
			farmerInQuestion = new Farmer();
			setTitle("Add new Farmer");
		}
		else {
			farmerInQuestion = selectedFarmer;
			setTitle("Edit Farmer");
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(verticalBox, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		verticalBox.add(panel);
		
		JLabel lblNewLabel = new JLabel("New Farmer's Name");
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		textField = new JTextField();
		textField.setText(farmerInQuestion.getName());
		panel.add(textField);
		textField.setColumns(10);
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalBox.add(verticalGlue);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
			
		});
		
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);

		String buttonTxt = "Save";
		if (selectedFarmer!=null)
			buttonTxt = "Update";
		
		JButton saveBtn = new JButton(buttonTxt);
		saveBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		horizontalBox.add(saveBtn);
		
		final JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		contentPane.add(warningLabel, BorderLayout.NORTH);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// update the name with the current text field value
				farmerInQuestion.setName(textField.getText());
				ServiceResponse response;

				// save or update the new farmer
				if (selectedFarmer==null)
					response = fsi.saveFarmer(farmerInQuestion);
				else
					response = fsi.editFarmer(farmerInQuestion);


				if(response.isSuccess()) {
					// dispose of the window
					window.dispose();
				}
				
				// something went wrong with saving
				warningLabel.setText(response.getMessage());
				
			}
			
		});
	}

}
