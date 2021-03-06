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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Image;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

import javax.swing.JProgressBar;

import java.awt.Component;

import javax.swing.ImageIcon;

import java.awt.CardLayout;

import javax.swing.SpringLayout;

import org.wowdoge.viewsystem.swing.view.PrivateKeyFileFilter;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

import com.google.dogecoin.core.Address;
import com.google.dogecoin.core.AddressFormatException;
import com.google.dogecoin.core.ECKey;
import com.google.dogecoin.core.InsufficientMoneyException;
import com.google.dogecoin.core.NetworkParameters;
import com.google.dogecoin.core.Transaction;
import com.google.dogecoin.core.Utils;
import com.google.dogecoin.crypto.KeyCrypterException;
import com.google.dogecoin.params.MainNetParams;
import com.google.dogecoin.wallet.WalletTransaction;

//import net.miginfocom.swing.MigLayout;























import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;

import javax.swing.ListSelectionModel;
import javax.swing.JToggleButton;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.prefs.BackingStoreException;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import static com.google.dogecoin.core.Utils.bitcoinValueToFriendlyString;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JMenuItem;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.StatusPrinter;

import org.slf4j.LoggerFactory;
import org.wowdoge.file.PrivateKeyAndDate;
import org.wowdoge.file.PrivateKeysHandler;
import org.wowdoge.file.PrivateKeysHandlerException;

import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;
import javax.swing.JCheckBoxMenuItem;


public class Wow implements Runnable {

	private JFrame frmWow;
	private JTextField txtName;
	private JTextField txtBalance;
	private JTextField txtReceived;
	private JTextField txtSent;
	private CoreWallet coreWallet;
	private JTabbedPane tabbedPane;
	private JProgressBar progressBarStatus;
	private JLabel lblStatus;
	private JTextField textTotalBalance;
	private JToggleButton tglbtnLock;
	private JTextField txtAddress;
	private JPanel panelTransactions;
	private JPanel panelAddressBook;
	private JTable tableTransactions;
	private TransactionsTableModel transactionsTableModel;
	private JTable tableAddressBook;
	private AddressBookTableModel addressBookTableModel;
	private JButton btnQRCode;
	private JButton btnDoge;
	private JFileChooser fileChooser;
	private AddressesListModel addressesListModel;
	private JList listAddresses;
	private JMenu mnFile;
	private RecentFilesMenu mnRecent;
	private JButton btnStatus;
	private JPanel panelAddresses;
	private JButton buttonZoomOut;
	private Component horizontalStrutZoomOut;
	private JSplitPane splitPaneWallet;
	private JMenuItem mntmExit;
	private static String OS = System.getProperty("os.name").toLowerCase();
	private JMenuItem mntmAbout;
	private JCheckBoxMenuItem chckbxmntmTransactionsInputsOutputs;
	private TableColumn colIns;
	private TableColumn colOuts;
	
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
	
	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}
 
	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
 
	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	private static void initLogging()
	{
		final File logDir = new File(System.getProperty("user.home")); //new File("."); //getDir("log", Constants.TEST ? Context.MODE_WORLD_READABLE : MODE_PRIVATE);
		final File logFile = new File(logDir, "wowdoge.log");

		final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		final PatternLayoutEncoder filePattern = new PatternLayoutEncoder();
		filePattern.setContext(context);
		filePattern.setPattern("%d{HH:mm:ss.SSS} [%thread] %logger{0} - %msg%n");
		filePattern.start();

		final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<ILoggingEvent>();
		fileAppender.setContext(context);
		fileAppender.setFile(logFile.getAbsolutePath());

		final TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
		rollingPolicy.setContext(context);
		rollingPolicy.setParent(fileAppender);
		rollingPolicy.setFileNamePattern(logDir.getAbsolutePath() + "/wowdoge.%d.log.gz");
		rollingPolicy.setMaxHistory(7);
		rollingPolicy.start();

		fileAppender.setEncoder(filePattern);
		fileAppender.setRollingPolicy(rollingPolicy);
		fileAppender.start();

		final PatternLayoutEncoder logcatTagPattern = new PatternLayoutEncoder();
		logcatTagPattern.setContext(context);
		logcatTagPattern.setPattern("%logger{0}");
		logcatTagPattern.start();

		final PatternLayoutEncoder logcatPattern = new PatternLayoutEncoder();
		logcatPattern.setContext(context);
		logcatPattern.setPattern("[%thread] %msg%n");
		logcatPattern.start();

		//final LogcatAppender logcatAppender = new LogcatAppender();
		//logcatAppender.setContext(context);
		//logcatAppender.setTagEncoder(logcatTagPattern);
		//logcatAppender.setEncoder(logcatPattern);
		//logcatAppender.start();

		final ch.qos.logback.classic.Logger log = context.getLogger(Logger.ROOT_LOGGER_NAME);
		log.addAppender(fileAppender);
		//log.addAppender(logcatAppender);
		log.setLevel(Level.INFO);//Level.INFO);
		
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		initLogging();
//		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//	    RollingFileAppender rfAppender = new RollingFileAppender();
//	    rfAppender.setContext(loggerContext);
//	    rfAppender.setFile("testFile.log");
//	    FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
//	    rollingPolicy.setContext(loggerContext);
//	    // rolling policies need to know their parent
//	    // it's one of the rare cases, where a sub-component knows about its parent
//	    rollingPolicy.setParent(rfAppender);
//	    rollingPolicy.setFileNamePattern("testFile.%i.log.zip");
//	    rollingPolicy.start();
//
//	    SizeBasedTriggeringPolicy triggeringPolicy = new ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy();
//	    triggeringPolicy.setMaxFileSize("5MB");
//	    triggeringPolicy.start();
//
//	    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//	    encoder.setContext(loggerContext);
//	    encoder.setPattern("%-4relative [%thread] %-5level %logger{35} - %msg%n");
//	    encoder.start();
//
//	    rfAppender.setEncoder(encoder);
//	    rfAppender.setRollingPolicy(rollingPolicy);
//	    rfAppender.setTriggeringPolicy(triggeringPolicy);
//
//	    rfAppender.start();
//
//	    // attach the rolling file appender to the logger of your choice
//	    Logger logbackLogger = loggerContext.getLogger("Main");
//	    logbackLogger.addAppender(rfAppender);
//
//	    // OPTIONAL: print logback internal status messages
//	    StatusPrinter.print(loggerContext);
//
//	    // log something
//	    logbackLogger.debug("hello");
		
		try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WowDoge");
            //System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
            //System.setProperty("com.apple.mrj.application.live-resize", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch(ClassNotFoundException e) {
	            System.out.println("ClassNotFoundException: " + e.getMessage());
	    }
	    catch(InstantiationException e) {
	            System.out.println("InstantiationException: " + e.getMessage());
	    }
	    catch(IllegalAccessException e) {
	            System.out.println("IllegalAccessException: " + e.getMessage());
	    }
	    catch(UnsupportedLookAndFeelException e) {
	            System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
	    }

		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Wow window = new Wow();
					window.frmWow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Wow() {
		initialize();
		//ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		coreWallet = new CoreWallet();
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Wow DOGE Wallet Files", "dogewallet");
		fileChooser.addChoosableFileFilter(filter);
		addressesListModel = new AddressesListModel();
		listAddresses.setModel(addressesListModel);//new DefaultListModel());
		transactionsTableModel = new TransactionsTableModel();
		tableTransactions.setModel(transactionsTableModel);
		tableTransactions.setDefaultRenderer(Object.class, new TransactionsTableCellRenderer());
		TableColumn column = tableTransactions.getColumnModel().getColumn(1);
		column.setCellRenderer(new TransactionsTableCellRenderer());
		column = tableTransactions.getColumnModel().getColumn(2);
		TransactionsTableCellRenderer r = new TransactionsTableCellRenderer(); 
		r.setHorizontalAlignment(SwingConstants.RIGHT);
		column.setCellRenderer(r);
		colOuts = tableTransactions.getColumnModel().getColumn(tableTransactions.getColumnCount() - 1);
		colIns = tableTransactions.getColumnModel().getColumn(tableTransactions.getColumnCount() - 2);
		addressBookTableModel = new AddressBookTableModel(coreWallet.getPreferences());
		tableAddressBook.setModel(addressBookTableModel);
		try {
			addressBookTableModel.getAddressBook().load();
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(frmWow,
				    e1.getMessage(), "Error during Reading Address Book Data", JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(frmWow,
				    e1.getMessage(), "Error in Reading Address Book Data", JOptionPane.ERROR_MESSAGE);
		} catch (BackingStoreException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(frmWow,
				    e1.getMessage(), "Error within Reading Address Book Data", JOptionPane.ERROR_MESSAGE);
		}
		
		JMenuBar menuBar = new JMenuBar();
		frmWow.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New Wallet");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogNew d = new DialogNew();
				d.setLocationRelativeTo(frmWow);
				if (d.showDialog()) {
					try {
						coreWallet.open(new File(d.getFolder(), d.getWalletFileName()));
						mnRecent.addFileToFileHistory(new File(d.getFolder(), d.getWalletFileName()).getAbsolutePath());
						mnRecent.storeToPreferences();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frmWow,
							    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmWow,
							    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_MASK));
		mntmNew.setMnemonic(KeyEvent.VK_N);
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open Wallet...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(frmWow);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						if (file.getName().equals("wallet.dat")) {
							showInformationAboutSatoshisWalletDat();
						} else {
							coreWallet.open(file);
							mnRecent.addFileToFileHistory(file.getAbsolutePath());
							mnRecent.storeToPreferences();
						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frmWow,
							    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmWow,
							    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_MASK));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showSaveDialog(frmWow);
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						String file_name = file.toString();
						if (!file_name.endsWith(".dogewallet"))
						    file_name += ".dogewallet";
							file = new File(file_name);
				        File temp = new File(file.getParentFile().getAbsolutePath(), "TEMP" + file.getName());
						coreWallet.saveToFile(temp, file);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(frmWow,
							    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				 }
			}
		});
		//coreWallet = new CoreWallet();
		
		mnRecent = new RecentFilesMenu("Open Recent Wallet", coreWallet.getPreferences().node("Recent")) {
			@Override
			protected Action createOpenAction(String fileFullPath) {
				return new OpenAction(this, fileFullPath);
			}
			
		};
		mnFile.add(mnRecent);
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_MASK));
		mnFile.add(mntmSaveAs);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		mntmExit.setVisible(!isMac());
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		chckbxmntmTransactionsInputsOutputs = new JCheckBoxMenuItem("Advanced");
		chckbxmntmTransactionsInputsOutputs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//transactionsTableModel.setAdvancedView(chckbxmntmTransactionsInputsOutputs.getState());
				coreWallet.getPreferences().putBoolean("ViewTransactionsInputsOutputs", chckbxmntmTransactionsInputsOutputs.getState());
				if (chckbxmntmTransactionsInputsOutputs.getState()) {
					tableTransactions.addColumn(colIns);
					tableTransactions.addColumn(colOuts);
				} else {
					tableTransactions.removeColumn(colIns);
					tableTransactions.removeColumn(colOuts);
				}
			}
		});
		mnView.add(chckbxmntmTransactionsInputsOutputs);
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmImportPrivateKeys = new JMenuItem("Import Private Keys");
		mntmImportPrivateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				PrivateKeyFileFilter filter = new PrivateKeyFileFilter();
				c.setFileFilter(filter);
				int rVal = c.showOpenDialog(frmWow);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					File file = c.getSelectedFile();
					if (filter.accept(file)) {
                        try {
                        	Collection<PrivateKeyAndDate> privateKeysAndDates = null;
                        	if (coreWallet.isImportedPrivateKeyFileEncrypted(file)) {
                        		String password;
                        		DialogPassword d = new DialogPassword();
                        		d.setTitle("Decrypt Private Keys File");
            					d.setLocationRelativeTo(frmWow);
            					if (d.showDialog()) {
            						password = new String(d.getPassword());
            					} else
            						return;
            					privateKeysAndDates = coreWallet.readInPrivateKeysFromFile(c.getSelectedFile().getAbsolutePath(), password);
                        	} else {
                        		privateKeysAndDates = coreWallet.readInPrivateKeysFromFile(c.getSelectedFile().getAbsolutePath(), null);
                        	}
                        	
                        	Date replayDate = PrivateKeysHandler.calculateReplayDate(privateKeysAndDates, coreWallet.getWallet());
                        	String replayDateString = null;
                        	if (replayDate == null) {
                        		replayDateString = "Missing Key Dates";
                        	} else {
                        		replayDateString = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("en")).format(replayDate);
                        	}

                        	int dialogResult = JOptionPane.showConfirmDialog(frmWow, "Would you like to import private keys?\nNumber of Keys: " + privateKeysAndDates.size() + "\nReplay Date: " + replayDateString, "Confirm Import of Private Keys", JOptionPane.YES_NO_OPTION);
            				if  (dialogResult == JOptionPane.YES_OPTION) {
            					String password = null;
            					if (coreWallet.isEncrypted()) {
            						DialogPassword d = new DialogPassword();
            						d.setLocationRelativeTo(frmWow);
            						if (d.showDialog()) {
            							password = new String(d.getPassword());
            						} else
            							return;
            					}
            					coreWallet.importPrivateKeys(privateKeysAndDates, password);
            					coreWallet.sync();
            				}
                        } catch (IOException e) {
                        	e.printStackTrace();
        					JOptionPane.showMessageDialog(frmWow,
        						    "Import private keys error!\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (KeyCrypterException e) {
                            // TODO User may not have entered a password yet so
                            // password incorrect is ok at this stage.
                            // Other errors indicate a more general problem with
                            // the
                            // import.
                        	e.printStackTrace();
        					JOptionPane.showMessageDialog(frmWow,
        						    "Import private keys error!\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                        	e.printStackTrace();
        					JOptionPane.showMessageDialog(frmWow,
        						    "Import private keys error!\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
				}
			}
		});
		mnTools.add(mntmImportPrivateKeys);
		
		JMenuItem mntmExportPrivateKeys = new JMenuItem("Export Private Keys");
		mntmExportPrivateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = null;
				String passwordToEncrypt = null;
				if (coreWallet.isEncrypted()) {
					DialogPassword d = new DialogPassword();
					d.setLocationRelativeTo(frmWow);
					if (d.showDialog()) {
						password = new String(d.getPassword());
					} else
						return;
				}
				int dialogResult = JOptionPane.showConfirmDialog(frmWow, "Would you like to encrypt exported private keys file to protect it?","Encrypt Exported Private Kyes File?",JOptionPane.YES_NO_OPTION);
				boolean encryptExport = (dialogResult == JOptionPane.YES_OPTION);
				if (encryptExport) {
					DialogEncrypt d = new DialogEncrypt();
					d.setTitle("Encrypt Exported Private Keys File with Password");
					d.setWarning("WARNING: Encryption of exported private keys file should help to protect keys file. But if you loose or forget password, there is not any way to decrypt your encrypted private key file! There is not any way to help you in that case! There is not any recovery option! So be very careful! Write the password to paper and store it to safe box. To multiple places at home. Do not forget your password! Be very careful! Do everything you can to protect your password!");
					d.setLocationRelativeTo(frmWow);
					if (d.showDialog()) {
						passwordToEncrypt = new String(d.getPassword());
					} else {
						return;
					}
				}
				try {
					JFileChooser c = new JFileChooser();
					PrivateKeyFileFilter f = new PrivateKeyFileFilter();
					c.setFileFilter(f);
					int rVal = c.showSaveDialog(frmWow);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						File file = c.getSelectedFile();
						String file_name = file.toString();
						if (!file_name.endsWith(PrivateKeyFileFilter.PRIVATE_KEY_FILE_EXTENSION))
						    file_name += PrivateKeyFileFilter.PRIVATE_KEY_FILE_EXTENSION;
							file = new File(file_name);
						coreWallet.exportPrivateKeysToFile(file.getAbsolutePath(), password, encryptExport,
								passwordToEncrypt);
					}
					;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frmWow,
						    "File save error!\n" + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (KeyCrypterException e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(frmWow,
						    "Wallet dencryption failed!", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (PrivateKeysHandlerException e3) {
					e3.printStackTrace();
					JOptionPane.showMessageDialog(frmWow,
						    "Private keys export failed!\n" + e3.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnTools.add(mntmExportPrivateKeys);
		
		JMenuItem mntmReset = new JMenuItem("Resynchronize");
		mntmReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					coreWallet.sync();
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frmWow,
						    "Re-sync failed!\n" + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnTools.add(mntmReset);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("WowDoge Help");
		mnHelp.add(mntmHelp);
		
		mntmAbout = new JMenuItem("About WowDoge");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogAbout d = new DialogAbout();
				d.setLocationRelativeTo(frmWow);
				d.showDialog();
			}
		});
		mnHelp.add(mntmAbout);
		mntmAbout.setVisible(!isMac());
		
		tableAddressBook.setDefaultRenderer(Object.class, new AddressBookTableCellRenderer());
		
		if (!coreWallet.getPreferences().getBoolean("ViewTransactionsInputsOutputs", false)) {
			tableTransactions.removeColumn(colIns);
			tableTransactions.removeColumn(colOuts);
		} else {
			chckbxmntmTransactionsInputsOutputs.setState(true);
		}
		
		(new Thread(this)).start();
	}
	
	class OpenAction extends AbstractAction {
		private String fileFullPath;
		private RecentFilesMenu recentFilesMenu;
		
	    public OpenAction(RecentFilesMenu menu, String fileFullPath) {
	        super(new File(fileFullPath).getName(), null);
	        this.recentFilesMenu = menu;
	        this.fileFullPath = fileFullPath; 
	    }
	    public void actionPerformed(ActionEvent e) {
			try {
				File file = new File(fileFullPath);
				if (file.getName().equals("wallet.dat")) {
					showInformationAboutSatoshisWalletDat();
				} else {
					coreWallet.open(file);
					recentFilesMenu.addFileToFileHistory(fileFullPath);
					recentFilesMenu.storeToPreferences();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frmWow,
					    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frmWow,
					    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
	    }
	}
	
	private void showInformationAboutSatoshisWalletDat() {
		JOptionPane.showMessageDialog(frmWow,
			    "Satoshi's 'wallet.dat' wallet file can not be opened with WowDoge!"
			    + "\nWowDoge uses it's own light wallet format.\n"
			    + "Create a new wallet and then send coins to the new wallet.\n"
			    + "Or it is possible to import private keys via Tools menu.", "Information - Satoshi's 'wallet.dat' wallet file can not be opened with WowDoge", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWow = new JFrame();
		frmWow.setIconImage(Toolkit.getDefaultToolkit().getImage(Wow.class.getResource("/org/wowdoge/doge.png")));
		frmWow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					if (coreWallet.getWalletFilePath() == null) {
						DialogStart d = new DialogStart();
						d.setLocationRelativeTo(frmWow);
						if (d.showDialog()) {
							String path = d.getWalletFilePath();
							File f = new File(path);
							if (f.getName().equals("wallet.dat")) {
								showInformationAboutSatoshisWalletDat();
							} else {
								coreWallet.run(f.getParentFile(), f.getName());
								mnRecent.addFileToFileHistory(path);
								mnRecent.storeToPreferences();
							}
						} else {
							System.exit(0);
						}
					} else
						coreWallet.run();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
						    "Failed to open wallet.\nDetails:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					try {
						coreWallet.stop();
					} finally {
						System.exit(1);
					}
					// Improve!
					//e.printStackTrace();
				}
			}
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					coreWallet.stop();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
						    e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frmWow.setTitle("WowDoge");
		frmWow.setBounds(100, 100, 819, 503);
		frmWow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWow.getContentPane().setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmWow.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelWallet = new JPanel();
		tabbedPane.addTab("Wallet", null, panelWallet, null);
		panelWallet.setLayout(new BorderLayout(0, 0));
		
		splitPaneWallet = new JSplitPane();
		splitPaneWallet.setOneTouchExpandable(true);
		panelWallet.add(splitPaneWallet, BorderLayout.CENTER);
		
		panelAddresses = new JPanel();
		splitPaneWallet.setLeftComponent(panelAddresses);
		panelAddresses.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneAddresses = new JScrollPane();
		scrollPaneAddresses.setPreferredSize(new Dimension(280, 4));
		scrollPaneAddresses.setAutoscrolls(true);
		panelAddresses.add(scrollPaneAddresses, BorderLayout.CENTER);
		
		listAddresses = new JList();
		listAddresses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				refreshAddressAndQRCode();
			}
		});
		scrollPaneAddresses.setViewportView(listAddresses);
		
		JToolBar toolBarAddresses = new JToolBar();
		panelAddresses.add(toolBarAddresses, BorderLayout.NORTH);
		
		JButton btnNewAddress = new JButton("New Address");
		btnNewAddress.setToolTipText("Create new wallet address for receiving coins");
		btnNewAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ret = JOptionPane.showConfirmDialog(
			            frmWow,
			            "Would you like to create a new address?",
			            "Confirm New Address Creation",
			            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (ret == JOptionPane.YES_OPTION) {
					//coreWallet.createNewKeys(1);
					//System.out.println(coreWallet.getKeys());
					
					createKeys(1);
				}
			}
		});
		btnNewAddress.setActionCommand("");
		toolBarAddresses.add(btnNewAddress);
		
		JButton btnNewAddresses = new JButton("Create Multiple");
		btnNewAddresses.setToolTipText("Create multiple wallet addresses for receiving coins");
		btnNewAddresses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogAddress dialog = new DialogAddress();
				dialog.setLocationRelativeTo(frmWow);
				if (dialog.showDialog()) {
					int number = dialog.getNumberOfAddressToCreate();
					//coreWallet.createNewKeys(number);
					//System.out.println(coreWallet.getKeys());
					
					createKeys(number);
				}
			}
		});
		toolBarAddresses.add(btnNewAddresses);
		
		JButton buttonZoomIn = new JButton(">");
		buttonZoomIn.setVisible(false);
		buttonZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				horizontalStrutZoomOut.setVisible(true);
				buttonZoomOut.setVisible(true);
				panelAddresses.setVisible(false);
			}
		});
		toolBarAddresses.add(buttonZoomIn);
		
		JSplitPane splitPaneAddressAndTransactoions = new JSplitPane();
		splitPaneAddressAndTransactoions.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneWallet.setRightComponent(splitPaneAddressAndTransactoions);
		
		JPanel panelAddress = new JPanel();
		panelAddress.setPreferredSize(new Dimension(10, 100));
		splitPaneAddressAndTransactoions.setLeftComponent(panelAddress);
		panelAddress.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDogeQRMain = new JPanel();
		panelDogeQRMain.setPreferredSize(new Dimension(240, 120));
		panelAddress.add(panelDogeQRMain, BorderLayout.CENTER);
		panelDogeQRMain.setLayout(new BorderLayout(0, 0));
		
		JPanel panelDogeQR = new JPanel();
		panelDogeQRMain.add(panelDogeQR);
		
		btnDoge = new JButton("");
		btnDoge.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int min = btnDoge.getWidth() > btnDoge.getHeight() ? btnDoge.getHeight() : btnDoge.getWidth();
				min = (int) (min * 0.9);
				//System.out.println("RESIZED");
				btnDoge.setIcon(new ImageIcon (new ImageIcon(Wow.class.getResource("/org/wowdoge/doge.png")).getImage().getScaledInstance(min, min, Image.SCALE_SMOOTH)));
			}
		});
		btnDoge.setToolTipText("Send payment");
		btnDoge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				System.out.println("WALLET OUTPUT:");
				System.out.println(coreWallet.getWallet().toString(true, true, true, null));
				System.out.println("WALLET OUTPUT END.");
				*/
				tabbedPane.setSelectedComponent(panelAddressBook);
			}
		});
		panelDogeQR.setLayout(new GridLayout(0, 2, 0, 0));
		panelDogeQR.add(btnDoge);
		btnDoge.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/doge.png")));
		btnDoge.setSize(new Dimension(500, 500));
		btnDoge.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDoge.setMaximumSize(new Dimension(500, 500));
		btnDoge.setMinimumSize(new Dimension(120, 120));
		btnDoge.setPreferredSize(new Dimension(200, 200));
		
		btnQRCode = new JButton("");
		btnQRCode.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				String address = (String)listAddresses.getSelectedValue();
				//System.out.println("Addres:" + address);
				if (address != null) {
					txtAddress.setText(address);
					btnQRCode.setIcon(new ImageIcon(new QRImage(address, btnQRCode.getWidth(), btnQRCode.getHeight())));
				}
			}
		});
		btnQRCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnQRCode.setToolTipText("Request payment");
		panelDogeQR.add(btnQRCode);
		btnQRCode.setSize(new Dimension(120, 120));
		btnQRCode.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnQRCode.setMinimumSize(new Dimension(120, 120));
		btnQRCode.setMaximumSize(new Dimension(500, 500));
		btnQRCode.setPreferredSize(new Dimension(200, 200));
		
		JPanel panelAddressNameBalance = new JPanel();
		panelAddress.add(panelAddressNameBalance, BorderLayout.NORTH);
		panelAddressNameBalance.setLayout(new BoxLayout(panelAddressNameBalance, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panelAddressNameBalance.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		buttonZoomOut = new JButton("<");
		buttonZoomOut.setVisible(false);
		buttonZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				horizontalStrutZoomOut.setVisible(false);
				buttonZoomOut.setVisible(false);
				panelAddresses.setVisible(true);
				panelAddresses.setSize(panelAddresses.getPreferredSize());
			}
		});
		buttonZoomOut.setMinimumSize(new Dimension(29, 29));
		panel.add(buttonZoomOut, BorderLayout.WEST);
		
		JLabel lblReceivingAddress = new JLabel(" Receiving Address:");
		lblReceivingAddress.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblReceivingAddress);
		lblReceivingAddress.setPreferredSize(new Dimension(59, 30));
		lblReceivingAddress.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		horizontalStrutZoomOut = Box.createHorizontalStrut(20);
		horizontalStrutZoomOut.setVisible(false);
		horizontalStrutZoomOut.setPreferredSize(new Dimension(75, 29));
		panel.add(horizontalStrutZoomOut, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panelAddressNameBalance.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		txtAddress = new JTextField();
		txtAddress.setMinimumSize(new Dimension(400, 28));
		txtAddress.setPreferredSize(new Dimension(14, 30));
		panel_1.add(txtAddress);
		txtAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		txtAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtAddress.setBackground(SystemColor.window);
		txtAddress.setEditable(false);
		txtAddress.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setVisible(false);
		panelAddressNameBalance.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JLabel lblDescription = new JLabel(" Name:");
		lblDescription.setPreferredSize(new Dimension(55, 16));
		panel_2.add(lblDescription);
		
		txtName = new JTextField();
		panel_2.add(txtName);
		txtName.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		txtName.setText("Donation");
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setBackground(Color.WHITE);
		txtName.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setVisible(false);
		panelAddressNameBalance.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JLabel lblBalance = new JLabel(" Balance:");
		lblBalance.setPreferredSize(new Dimension(55, 16));
		panel_3.add(lblBalance);
		
		txtBalance = new JTextField();
		panel_3.add(txtBalance);
		txtBalance.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtBalance.setText("NA");
		txtBalance.setHorizontalAlignment(SwingConstants.CENTER);
		txtBalance.setBackground(SystemColor.window);
		txtBalance.setEditable(false);
		txtBalance.setColumns(10);
		
		JLabel lblDoge = new JLabel("DOGE");
		lblDoge.setPreferredSize(new Dimension(40, 16));
		panel_3.add(lblDoge);
		
		JPanel panelReceivedSent = new JPanel();
		panelReceivedSent.setVisible(false);
		panelAddress.add(panelReceivedSent, BorderLayout.SOUTH);
		panelReceivedSent.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelReceived = new JPanel();
		panelReceivedSent.add(panelReceived);
		panelReceived.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel labelReceived = new JLabel("Received:");
		labelReceived.setHorizontalAlignment(SwingConstants.CENTER);
		labelReceived.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		panelReceived.add(labelReceived);
		
		txtReceived = new JTextField();
		txtReceived.setText("NA");
		txtReceived.setHorizontalAlignment(SwingConstants.CENTER);
		txtReceived.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		txtReceived.setEditable(false);
		txtReceived.setColumns(10);
		txtReceived.setBackground(SystemColor.window);
		panelReceived.add(txtReceived);
		
		JLabel lblDogeReceived = new JLabel("DOGE");
		panelReceived.add(lblDogeReceived);
		
		JPanel panelSent = new JPanel();
		panelReceivedSent.add(panelSent);
		panelSent.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel labelSent = new JLabel("Sent:");
		labelSent.setPreferredSize(new Dimension(59, 16));
		labelSent.setHorizontalAlignment(SwingConstants.CENTER);
		labelSent.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		panelSent.add(labelSent);
		
		txtSent = new JTextField();
		txtSent.setText("NA");
		txtSent.setHorizontalAlignment(SwingConstants.CENTER);
		txtSent.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		txtSent.setEditable(false);
		txtSent.setColumns(10);
		txtSent.setBackground(SystemColor.window);
		panelSent.add(txtSent);
		
		JLabel lblDogeSent = new JLabel("DOGE");
		panelSent.add(lblDogeSent);
		
		JTable tableAddressTransactions = new JTable();
		tableAddressTransactions.setVisible(false);
		splitPaneAddressAndTransactoions.setRightComponent(tableAddressTransactions);
		
		panelTransactions = new JPanel();
		tabbedPane.addTab("Transactions", null, panelTransactions, null);
		panelTransactions.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		panelTransactions.add(scrollPane, BorderLayout.CENTER);
		
		tableTransactions = new JTable();
		tableTransactions.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableTransactions.setAutoCreateRowSorter(true);
		tableTransactions.setRowHeight(30);
		tableTransactions.setAutoscrolls(true);
		tableTransactions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableTransactions);
		
		panelAddressBook = new JPanel();
		tabbedPane.addTab("Address Book", null, panelAddressBook, null);
		panelAddressBook.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneAddressBook = new JScrollPane();
		panelAddressBook.add(scrollPaneAddressBook, BorderLayout.CENTER);
		
		tableAddressBook = new JTable();
		tableAddressBook.setAutoCreateRowSorter(true);
		tableAddressBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
			         JTable target = (JTable)arg0.getSource();
			         int row = target.getSelectedRow();
			         //int column = target.getSelectedColumn();
			         if (row != -1) {
			        	 Contact c = addressBookTableModel.getAddressBook().getContacts().get(row);
			        	 send(c.getAddress(), c.getAmount());
			         }
			    }
			}
		});
		tableAddressBook.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAddressBook.setRowHeight(25);
		scrollPaneAddressBook.setViewportView(tableAddressBook);
		
		JToolBar toolBarAddressbook = new JToolBar();
		panelAddressBook.add(toolBarAddressbook, BorderLayout.NORTH);
		
		JButton btnNewRecipient = new JButton("New");
		btnNewRecipient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogContact d = new DialogContact();
				d.setLocationRelativeTo(frmWow);
				d.edit(false);
				d.setNetworkParameters(coreWallet.getNetworkParameters());
				if (d.showDialog()) {
					Contact c = new Contact(d.getName(), d.getAddress(), d.getDescription(), d.getAmount());
					addressBookTableModel.getAddressBook().addContact(c);
					addressBookTableModel.getAddressBook().sort();
					
					try {
						addressBookTableModel.getAddressBook().save();
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frmWow,
							    e1.getMessage(), "Error During Address Book Save", JOptionPane.ERROR_MESSAGE);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frmWow,
							    e1.getMessage(), "Error During Address Book Save", JOptionPane.ERROR_MESSAGE);
					} catch (BackingStoreException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frmWow,
							    e1.getMessage(), "Error During Address Book Save", JOptionPane.ERROR_MESSAGE);
					}
					addressBookTableModel.fireTableDataChanged();
				}
			}
		});
		btnNewRecipient.setToolTipText("New address template to send payments to");
		toolBarAddressbook.add(btnNewRecipient);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogContact d = new DialogContact();
				d.setLocationRelativeTo(frmWow);
				d.edit(true);
				int index = tableAddressBook.getSelectedRow();
				if (index != -1) {
					d.setNetworkParameters(coreWallet.getNetworkParameters());
					Contact c = addressBookTableModel.getAddressBook().getContacts().get(index);
					d.setName(c.getName());
					d.setAddress(c.getAddress());
					d.setDescription(c.getDescription());
					d.setAmount(c.getAmount());
					if (d.showDialog()) {
						c.setName(d.getName());
						c.setAddress(d.getAddress());
						c.setDescription(d.getDescription());
						c.setAmount(d.getAmount());
						addressBookTableModel.getAddressBook().sort();
						try {
							addressBookTableModel.getAddressBook().save();
						} catch (IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
								    e1.getMessage(), "Error During Address Book Save", JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
								    e1.getMessage(), "Error During Address Book Save", JOptionPane.ERROR_MESSAGE);
						} catch (BackingStoreException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
								    e1.getMessage(), "Error During Address Book Save", JOptionPane.ERROR_MESSAGE);
						}
						addressBookTableModel.fireTableDataChanged();
					}
				}
			}
		});
		btnEdit.setToolTipText("Edit address template");
		toolBarAddressbook.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = tableAddressBook.getSelectedRow();
				if (index != -1) {
					Contact c = addressBookTableModel.getAddressBook().getContacts().get(index);
					if (JOptionPane.showConfirmDialog(frmWow, "Do you really want to delete Address Template?\nName: " + c.getName() + "\nAddress: " + c.getAddress() + "\nDescription: " + c.getDescription()  + "\nAmount: " + c.getAmount(), "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						addressBookTableModel.getAddressBook().getContacts().remove(c);
						try {
							addressBookTableModel.getAddressBook().save();
						} catch (IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
									e1.getMessage(),
									"Error During Address Book Save",
									JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
									e1.getMessage(),
									"Error During Address Book Save",
									JOptionPane.ERROR_MESSAGE);
						} catch (BackingStoreException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
									e1.getMessage(),
									"Error During Address Book Save",
									JOptionPane.ERROR_MESSAGE);
						}
						addressBookTableModel.fireTableDataChanged();
					}
				}
			}
		});
		btnDelete.setToolTipText("Delete address template");
		toolBarAddressbook.add(btnDelete);
		
		JButton btnSend = new JButton("Send to Address");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableAddressBook.getSelectedRow();
		         if (row != -1) {
		        	 Contact c = addressBookTableModel.getAddressBook().getContacts().get(row);
		        	 send(c.getAddress(), c.getAmount());
		         }
			}
		});
		btnSend.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		btnSend.setToolTipText("Send coins to address template");
		toolBarAddressbook.add(btnSend);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		toolBarAddressbook.add(horizontalGlue);
		
		JButton btnSendToAddress = new JButton("Send Now");
		btnSendToAddress.setPreferredSize(new Dimension(156, 29));
		btnSendToAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send(null, 0);
			}
		});
		btnSendToAddress.setToolTipText("Send coins to address to be entered");
		btnSendToAddress.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		toolBarAddressbook.add(btnSendToAddress);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		toolBarAddressbook.add(horizontalGlue_1);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(210, 0));
		toolBarAddressbook.add(horizontalStrut);
		
		JToolBar toolBarTotal = new JToolBar();
		frmWow.getContentPane().add(toolBarTotal, BorderLayout.NORTH);
		
		tglbtnLock = new JToggleButton("");
		tglbtnLock.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/notencrypted.png")));
		tglbtnLock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (coreWallet.isEncrypted()) {
					DialogPassword d = new DialogPassword();
					d.setLocationRelativeTo(frmWow);
					if (d.showDialog()) {
						try {
							coreWallet.decrypt(new String(d.getPassword()));
						} catch (KeyCrypterException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
								    "Wallet dencryption failed!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					DialogEncrypt d = new DialogEncrypt();
					d.setLocationRelativeTo(frmWow);
					if (d.showDialog()) {
						//System.out.println("NAZDAR");
						//System.out.println(d.getPassword());
						try {
							coreWallet.encrypt(new String(d.getPassword()));
						} catch (Exception e) { //KeyCrypterException
							e.printStackTrace();
							JOptionPane.showMessageDialog(frmWow,
								    "Wallet encryption failed!\n", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				updateEncryptionState();
			}
		});
		toolBarTotal.add(tglbtnLock);
		tglbtnLock.setToolTipText("Encrypt Wallet");
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
		toolBarTotal.add(horizontalStrut_1);
		
		JLabel lblTotal = new JLabel("Balance:");
		lblTotal.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		toolBarTotal.add(lblTotal);
		
		textTotalBalance = new JTextField();
		textTotalBalance.setPreferredSize(new Dimension(250, 28));
		textTotalBalance.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		textTotalBalance.setHorizontalAlignment(SwingConstants.CENTER);
		textTotalBalance.setBackground(SystemColor.window);
		textTotalBalance.setEditable(false);
		toolBarTotal.add(textTotalBalance);
		textTotalBalance.setColumns(10);
		
		JLabel lblTotalDoge = new JLabel("DOGE ");
		lblTotalDoge.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		toolBarTotal.add(lblTotalDoge);
		
		JToolBar toolBarStatus = new JToolBar();
		frmWow.getContentPane().add(toolBarStatus, BorderLayout.SOUTH);
		
		btnStatus = new JButton("Connecting...");
		btnStatus.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/redled.png")));
		btnStatus.setBorderPainted(false);
		toolBarStatus.add(btnStatus);
		
		lblStatus = new JLabel(" Connecting...    ");
		lblStatus.setVisible(false);
		toolBarStatus.add(lblStatus);
		
		progressBarStatus = new JProgressBar();
		progressBarStatus.setPreferredSize(new Dimension(250, 20));
		toolBarStatus.add(progressBarStatus);
		
		JLabel label = new JLabel("    ");
		toolBarStatus.add(label);
	}
	
	private void createKeys(int number) {
		try {
			if (coreWallet.isEncrypted()) {
				DialogPassword d = new DialogPassword();
				d.setLocationRelativeTo(frmWow);
				if (d.showDialog()) {
					try {
						if (coreWallet.checkPassword(new String(d.getPassword()))) {
							coreWallet.createNewKeys(number, new String(d.getPassword()));
							System.out.println(coreWallet.getKeys());
						} else {
							JOptionPane.showMessageDialog(frmWow,
								    "Not correct password provided!", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (KeyCrypterException e) {
						JOptionPane.showMessageDialog(frmWow,
							    "Wallet decryption failed!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				coreWallet.createNewKeys(number);
				System.out.println(coreWallet.getKeys());
			}
		} catch (KeyCrypterException e) {
			JOptionPane.showMessageDialog(frmWow,
				    e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void send(String address, float amount) {
		DialogSend dialog = new DialogSend();
		dialog.setLocationRelativeTo(frmWow);
		BigDecimal valueInBTC = new BigDecimal(coreWallet.getBalance()).divide(new BigDecimal(Utils.COIN));
		dialog.setMaximum(valueInBTC.floatValue());
		dialog.setNetworkParameters(coreWallet.getNetworkParameters());
		if (address != null) {
			dialog.setAddress(address);
			dialog.setAmount(amount);
		}
		while (dialog.showDialog()) {
			System.out.println("SEND: " + dialog.getAddress() + " " + dialog.getAmount() + " " + dialog.isFeeUsed());
			try {
				if (coreWallet.isEncrypted()) {
					DialogPassword d = new DialogPassword();
					d.setLocationRelativeTo(frmWow);
					if (d.showDialog()) {
						try {
							if (coreWallet.checkPassword(new String(d.getPassword()))) {
								coreWallet.sendCoins(dialog.getAddress(), dialog.getAmount(), new String(d.getPassword()));
							} else {
								JOptionPane.showMessageDialog(frmWow,
									    "Not correct password provided!", "Information", JOptionPane.INFORMATION_MESSAGE);
								break;
							}
						} catch (KeyCrypterException e) {
							JOptionPane.showMessageDialog(frmWow,
								    "Wallet dencryption failed!", "Error", JOptionPane.ERROR_MESSAGE);
							break;
						}
					}
					//coreWallet.sendCoins(dialog.getAddress(), dialog.getAmount());
				} else
					coreWallet.sendCoins(dialog.getAddress(), dialog.getAmount());
				break;
			} catch (InsufficientMoneyException e) {
				JOptionPane.showMessageDialog(frmWow,
					    "Insufficient coins!", "Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} catch (KeyCrypterException e) {
				JOptionPane.showMessageDialog(frmWow,
					    e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
	}
	
	private void updateEncryptionState() {
		tglbtnLock.setIcon(new ImageIcon(coreWallet.isEncrypted() ? Wow.class.getResource("/org/wowdoge/encrypted.png") : Wow.class.getResource("/org/wowdoge/notencrypted.png")));
		//tglbtnLock.setText(coreWallet.isEncrypted() ? "Encrypted" : "Not Encrypted");
		tglbtnLock.setToolTipText(coreWallet.isEncrypted() ? "Decrypt Wallet" : "Encrypt Wallet"); //"Wallet is encrypted" : "Wallet is not encrypted");
		tglbtnLock.setSelected(coreWallet.isEncrypted());
	}
	
	private void refreshAddressAndQRCode() {
		String address = (String)listAddresses.getSelectedValue();
		//System.out.println("Addres:" + address);
		if (address != null) {
			if (address.equals(txtAddress.getText()))
				return;
			txtAddress.setText(address);
			btnQRCode.setIcon(new ImageIcon(new QRImage(address, btnQRCode.getWidth(), btnQRCode.getHeight())));
		}
	}
	
	public void run() {
		while (true) {
			try {
				//System.out.println(coreWallet.state());
				//System.out.println("Hello from a thread!");
				if (coreWallet.isDirty()) { // Setup completed
					updateEncryptionState();
					//System.out.println(coreWallet.getKeys());
					java.util.List<ECKey> keys = coreWallet.getKeys();
					//if (keys.size() != listAddresses.getModel().getSize()) {
						int selectedIndex = listAddresses.getSelectedIndex();
						//DefaultListModel model = (DefaultListModel) listAddresses.getModel();
						//model.removeAllElements();
						//NetworkParameters params = MainNetParams.get();
						List<String> addresses = new ArrayList<String>();
						for (ECKey k : keys) {
							//System.out.println(k.toAddress(coreWallet.getNetworkParameters())); //params));
							//model.addElement(k.toAddress(coreWallet.getNetworkParameters()).toString());
							addresses.add(k.toAddress(coreWallet.getNetworkParameters()).toString());
						}
						addressesListModel.setAddresses(addresses);
						if (selectedIndex != -1) {
							if (selectedIndex < addressesListModel.getSize())
								listAddresses.setSelectedIndex(selectedIndex);
							else
								listAddresses.setSelectedIndex(0);
						} else
							if (addressesListModel.getSize() > 0)
								listAddresses.setSelectedIndex(0);
						refreshAddressAndQRCode();
						
						updateEncryptionState();
//						if (coreWallet.getWallet().isEncrypted()) {
//							tglbtnLock.setText("Encrypted");
//							tglbtnLock.setSelected(true);
//						} else {
//							tglbtnLock.setText("Not Encrypted");
//							tglbtnLock.setSelected(false);
//						}
					//}
					String textBalance = bitcoinValueToFriendlyString(coreWallet.getBalance());
					if (!textBalance.equals(textTotalBalance.getText())) {
						this.textTotalBalance.setText(textBalance);
					}
					this.frmWow.setTitle("WowDoge - " + coreWallet.getWalletFilePath());
					
					//java.lang.Iterable<WalletTransaction> transactions = coreWallet.getWalletTransactions();
					List<Transaction> transactions = coreWallet.getTransactionsByTime();
					System.out.println(transactions);
					//transactionsTableModel = new TransactionsTableModel();
					//tableTransactions.setModel(transactionsTableModel);
					this.transactionsTableModel.setTransactions(transactions, coreWallet.getWallet());
					tableTransactions.repaint();
					
					coreWallet.setDirty(false);
				}
				if (coreWallet.isRunning()) {				
					this.progressBarStatus.setVisible(true);
					if (coreWallet.isSynchronizing() > 0) {
						if (this.progressBarStatus.isIndeterminate()) {
							this.btnStatus.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/orangeled.png")));
							this.btnStatus.setText(" Synchronizing...    ");
							this.lblStatus.setText(" Synchronizing...    ");
							this.progressBarStatus.setIndeterminate(false);
							this.progressBarStatus.setMaximum(coreWallet.isSynchronizing());
						}
						int progress = this.progressBarStatus.getMaximum() - coreWallet.isSynchronizing();
						int max = this.progressBarStatus.getMaximum();
						this.progressBarStatus.setValue(progress);
						String progressString = Math.round(((float) progress) / max * 100) + "%";
						String progressStringS = "Synchronizing... " + progressString;
						this.progressBarStatus.setToolTipText(progressString);
						this.btnStatus.setToolTipText(progressStringS);
						this.lblStatus.setToolTipText(progressStringS);
						//if (!progressStringS.equals(textTotalBalance.getText()))
						//	this.textTotalBalance.setText(progressStringS);
					} else {
						if (coreWallet.isSynchronizing() == 0) {
							this.progressBarStatus.setVisible(false);
							this.btnStatus.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/greenled.png")));
							this.btnStatus.setText(" Online    ");
							this.btnStatus.setToolTipText("Synchronized");
							this.lblStatus.setText(" Online    ");
							this.lblStatus.setToolTipText("Synchronized");
							this.progressBarStatus.setToolTipText("Synchronized");
						} else {
							this.btnStatus.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/redled.png")));
							this.progressBarStatus.setIndeterminate(true);
							this.btnStatus.setText(" Connecting...    ");
							this.lblStatus.setText(" Connecting...    ");
							this.btnStatus.setToolTipText("Connecting");
							this.lblStatus.setToolTipText("Connecting");
							//if (!textTotalBalance.getText().equals("Connecting..."))
							//	this.textTotalBalance.setText("Connecting...");
						}
					}
				} else {
					this.btnStatus.setIcon(new ImageIcon(Wow.class.getResource("/org/wowdoge/redled.png")));
					this.progressBarStatus.setIndeterminate(true);
					this.btnStatus.setText(" Connecting...    ");
					this.lblStatus.setText(" Connecting...    ");
					this.btnStatus.setToolTipText("Connecting");
					this.lblStatus.setToolTipText("Connecting");
					//if (!textTotalBalance.getText().equals("Connecting..."))
					//	this.textTotalBalance.setText("Connecting...");
				}
				if (coreWallet.isStoreFileLocked()) {
					JOptionPane.showMessageDialog(frmWow,
						    "Another Wow Doge Wallet instance is already running!\nPress OK button to quit.", "Information", JOptionPane.INFORMATION_MESSAGE);
					System.exit(1);
				}
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}
