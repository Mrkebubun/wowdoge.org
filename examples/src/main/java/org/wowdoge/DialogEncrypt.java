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
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import java.awt.Component;

import javax.swing.Box;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class DialogEncrypt extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepeat;
	private boolean dialogResultOK = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogEncrypt dialog = new DialogEncrypt();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogEncrypt() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("Encrypt Wallet with Password");
		setBounds(100, 100, 453, 456);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelPasswordTop = new JPanel();
			panelPasswordTop.setPreferredSize(new Dimension(10, 90));
			contentPanel.add(panelPasswordTop, BorderLayout.NORTH);
			panelPasswordTop.setLayout(new BorderLayout(0, 0));
			{
				JPanel panelPasswordTop1 = new JPanel();
				panelPasswordTop.add(panelPasswordTop1);
				panelPasswordTop1.setLayout(new GridLayout(0, 1, 0, 0));
				{
					JPanel panelPassword = new JPanel();
					panelPasswordTop1.add(panelPassword);
					panelPassword.setLayout(new GridLayout(2, 1, 0, 0));
					{
						JLabel lblPassword = new JLabel("Password:");
						lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
						panelPassword.add(lblPassword);
					}
					{
						passwordField = new JPasswordField();
						panelPassword.add(passwordField);
					}
				}
				{
					JPanel panelRepeatPassword = new JPanel();
					panelPasswordTop1.add(panelRepeatPassword);
					panelRepeatPassword.setLayout(new GridLayout(0, 1, 0, 0));
					{
						JLabel lblRepeatPassword = new JLabel("Repeat Password:");
						lblRepeatPassword.setHorizontalAlignment(SwingConstants.CENTER);
						panelRepeatPassword.add(lblRepeatPassword);
					}
					{
						passwordFieldRepeat = new JPasswordField();
						panelRepeatPassword.add(passwordFieldRepeat);
					}
				}
			}
		}
		{
			JPanel panelWarning = new JPanel();
			contentPanel.add(panelWarning, BorderLayout.CENTER);
			panelWarning.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panelWarning.add(scrollPane);
				{
					JTextArea txtrWarning = new JTextArea();
					txtrWarning.setForeground(Color.RED);
					txtrWarning.setBackground(SystemColor.window);
					txtrWarning.setWrapStyleWord(true);
					txtrWarning.setLineWrap(true);
					txtrWarning.setEditable(false);
					txtrWarning.setText("WARNING: Encrypt wallet with password should help to protect wallet. But if you encrypt wallet with password and you loose or forget password, there is not any way to decrypt your wallet! That means that in that case you will have no access to your coins any more! There is no any way to help you in that case! There is not any recovery option!\n\nSo be very careful!\n\nWrite the password to paper and store it to safe box. To multiple places at home.\nDo not forget your password!\n\nBe very careful!\n\nDo everything you can to protect your password!");
					scrollPane.setViewportView(txtrWarning);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (Arrays.equals (passwordField.getPassword(), passwordFieldRepeat.getPassword())) {
							dialogResultOK = true;
							dispose();
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "Typed passwords are not the same. Try again.",
					                "Passwords are not the same", JOptionPane.WARNING_MESSAGE);
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
					public void actionPerformed(ActionEvent arg0) {
						dialogResultOK = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public boolean showDialog() {
		setModal(true);
		setVisible(true);
		return dialogResultOK;
	}
	
	public char [] getPassword() {
		return passwordField.getPassword();
	}
	
	public void clear() {
		passwordField.selectAll();
		passwordField.requestFocusInWindow();
		passwordField.setText("");
		
		passwordFieldRepeat.selectAll();
		passwordFieldRepeat.requestFocusInWindow();
		passwordFieldRepeat.setText("");
	}
}
