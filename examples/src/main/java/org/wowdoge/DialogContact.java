/**
 * Copyright 2014 wowdoge.org
 *
 * Licensed under the MIT license (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://opensource.org/licenses/mit-license.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wowdoge;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

import com.google.dogecoin.core.Address;
import com.google.dogecoin.core.AddressFormatException;
import com.google.dogecoin.core.NetworkParameters;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import java.awt.Font;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.awt.Rectangle;

public class DialogContact extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldName;
	private JTextField textFieldAddress;
	private JTextField textFieldDescription;
	private boolean dialogResultOK;
	//private Address address;
	private NetworkParameters networkParams;
	private JSpinner spinnerAmount;
	private JLabel lblAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogContact dialog = new DialogContact();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogContact() {
		setTitle("New Address Template");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblName = new JLabel("Name:");
				lblName.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblName);
			}
			{
				textFieldName = new JTextField();
				textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(textFieldName);
				textFieldName.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(2, 1, 0, 0));
			{
				JLabel lblAddress = new JLabel("Address:");
				lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblAddress);
			}
			{
				textFieldAddress = new JTextField();
				textFieldAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				textFieldAddress.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(textFieldAddress);
				textFieldAddress.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(2, 2, 0, 0));
			{
				JLabel lblDescription = new JLabel("Description (Optional):");
				lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblDescription);
			}
			{
				textFieldDescription = new JTextField();
				textFieldDescription.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(textFieldDescription);
				textFieldDescription.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				lblAmount = new JLabel("Amount:");
				lblAmount.setAlignmentX(0.5f);
				panel.add(lblAmount, BorderLayout.WEST);
			}
			{
				spinnerAmount = new JSpinner(new SpinnerNumberModel(new Float(0), new Float(0), new Float(Float.MAX_VALUE), new Float(1f)));
				lblAmount.setLabelFor(spinnerAmount);
				spinnerAmount.setPreferredSize(new Dimension(175, 28));
				spinnerAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
				panel.add(spinnerAmount, BorderLayout.CENTER);
			}
			{
				JLabel lblDoge = new JLabel("DOGE");
				panel.add(lblDoge, BorderLayout.EAST);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Address address = new Address(networkParams, textFieldAddress.getText());
							dialogResultOK = true;
							dispose();
						} catch (AddressFormatException e1) {
							JOptionPane.showMessageDialog(getRootPane(),
								    "Incorrect address format!", "Address", 0);
							return;
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialogResultOK = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setMaximum(Float.MAX_VALUE);
	}
	
	public void setMaximum(float max) {
		spinnerAmount.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(max), new Float(1f)));
		spinnerAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		JTextField tf = ((JSpinner.DefaultEditor) spinnerAmount.getEditor()).getTextField();
		tf.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		tf.setHorizontalAlignment(SwingConstants.CENTER);
		JSpinner.NumberEditor editor = (JSpinner.NumberEditor)spinnerAmount.getEditor();  
        DecimalFormat format = editor.getFormat();  
        format.setMinimumFractionDigits(8);  
	}
	
	public void setNetworkParameters(NetworkParameters networkParams) {
		this.networkParams = networkParams;
	}
	
	public boolean showDialog() {
		setModal(true);
		setVisible(true);
		return dialogResultOK;
	}
	
	public void edit(boolean edit) {
		if (edit) {
			setTitle("Edit Address Template");
		} else {
			setTitle("New Address Template");
		}
	}
	
	public String getName() {
		return textFieldName.getText();
	}
	
	public void setName(String name) {
		textFieldName.setText(name);
	}

	public String getAddress() {
		return textFieldAddress.getText();
	}
	
	public void setAddress(String address) {
		textFieldAddress.setText(address);
	}
	
	public String getDescription() {
		return textFieldDescription.getText();
	}
	
	public void setDescription(String description) {
		textFieldDescription.setText(description);
	}
	
	public float getAmount() {
		return (Float) spinnerAmount.getValue();
	}
	
	public void setAmount(float amount) {
		spinnerAmount.setValue(new Float(amount));
	}
}
