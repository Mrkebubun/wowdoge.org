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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.JSpinner;

import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

import java.awt.Font;

import javax.swing.Box;

import com.google.dogecoin.core.Address;
import com.google.dogecoin.core.AddressFormatException;
import com.google.dogecoin.core.InsufficientMoneyException;
import com.google.dogecoin.core.NetworkParameters;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

public class DialogSend extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JSpinner spinnerAmount;
	private JTextField textFieldToAddress;
	private boolean dialogResultOK = false;
	private JCheckBox chckbxUseNetworkRecommendedFee;
	private Address address;
	private NetworkParameters networkParams;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogSend dialog = new DialogSend();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.textFieldToAddress.setText(address);
	}
	
	public float getAmount() {
		return (Float) spinnerAmount.getValue();
	}
	
	public void setAmount(float amount) {
		spinnerAmount.setValue(new Float(amount));
	}
	
	public boolean isFeeUsed() {
		return chckbxUseNetworkRecommendedFee.isSelected();
	}

	/**
	 * Create the dialog.
	 */
	public DialogSend() {
		setTitle("Send DOGE Coins to Address");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panelToAddress = new JPanel();
			contentPanel.add(panelToAddress);
			panelToAddress.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblAddress = new JLabel("To Address:");
				lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
				panelToAddress.add(lblAddress, BorderLayout.NORTH);
				lblAddress.setAlignmentX(Component.CENTER_ALIGNMENT);
			}
			{
				textFieldToAddress = new JTextField();
				textFieldToAddress.setHorizontalAlignment(SwingConstants.CENTER);
				textFieldToAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				panelToAddress.add(textFieldToAddress);
				textFieldToAddress.setColumns(10);
			}
		}
		{
			JPanel panelAmountAndFee = new JPanel();
			contentPanel.add(panelAmountAndFee);
			panelAmountAndFee.setLayout(new BorderLayout(0, 0));
			{
				JPanel panelAmount = new JPanel();
				panelAmountAndFee.add(panelAmount, BorderLayout.NORTH);
				panelAmount.setLayout(new BorderLayout(0, 0));
				{
					JLabel lblAmount = new JLabel("Amount:");
					panelAmount.add(lblAmount, BorderLayout.WEST);
					lblAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
				}
				{
					spinnerAmount = new JSpinner(new SpinnerNumberModel(new Float(0), new Float(0), new Float(Float.MAX_VALUE), new Float(1f)));
					panelAmount.add(spinnerAmount);
					spinnerAmount.setPreferredSize(new Dimension(175, 28));
					spinnerAmount.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
					JTextField tf = ((JSpinner.DefaultEditor) spinnerAmount.getEditor()).getTextField();
					tf.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
					tf.setHorizontalAlignment(SwingConstants.CENTER);
					JSpinner.NumberEditor editor = (JSpinner.NumberEditor)spinnerAmount.getEditor();  
			        DecimalFormat format = editor.getFormat();  
			        format.setMinimumFractionDigits(8);  
				}
				{
					JLabel lblDoge = new JLabel("DOGE");
					panelAmount.add(lblDoge, BorderLayout.EAST);
				}
			}
			{
				chckbxUseNetworkRecommendedFee = new JCheckBox("Use DOGE coin network recommended transfer fee.");
				chckbxUseNetworkRecommendedFee.setVisible(false);
				chckbxUseNetworkRecommendedFee.setSelected(true);
				panelAmountAndFee.add(chckbxUseNetworkRecommendedFee, BorderLayout.CENTER);
				chckbxUseNetworkRecommendedFee.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				JLabel lblfeeWillBe = new JLabel("To speed up sending. (Fee will be deducted from the amount.)");
				lblfeeWillBe.setVisible(false);
				lblfeeWillBe.setHorizontalAlignment(SwingConstants.CENTER);
				panelAmountAndFee.add(lblfeeWillBe, BorderLayout.SOUTH);
			}
		}
		{
			JButton okButton = new JButton("Send");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						address = new Address(networkParams, textFieldToAddress.getText());
						dialogResultOK = true;
						dispose();
					} catch (AddressFormatException e1) {
						JOptionPane.showMessageDialog(getRootPane(),
							    "Incorrect address format!", "Address", 0);
						return;
					}
				}
			});
			contentPanel.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new BorderLayout(0, 0));
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dialogResultOK = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, BorderLayout.EAST);
			}
		}
	}

}
