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

import com.google.common.util.concurrent.Service.State;
import com.google.dogecoin.core.Wallet;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.google.dogecoin.core.AbstractPeerEventListener;
import com.google.dogecoin.core.AbstractWalletEventListener;
import com.google.dogecoin.core.Address;
import com.google.dogecoin.core.Block;
import com.google.dogecoin.core.ECKey;
import com.google.dogecoin.core.InsufficientMoneyException;
import com.google.dogecoin.core.Message;
import com.google.dogecoin.core.NetworkParameters;
import com.google.dogecoin.core.Peer;
import com.google.dogecoin.core.Transaction;
import com.google.dogecoin.core.Utils;
//import com.google.dogecoin.kits.WalletAppKit;
import com.google.dogecoin.params.MainNetParams;
import com.google.dogecoin.utils.Threading;
import com.google.dogecoin.wallet.WalletTransaction;
import com.google.dogecoin.core.Utils.*;
import com.google.dogecoin.crypto.KeyCrypterException;

public class CoreWallet {

	private CoreWalletAppKit appKit = null;
	private boolean dirty = false;
	private int synchronizing = 0;
	private String walletFilePath;
	private Preferences preferences;
	//private String applicationDataDirectory;

	public static void main(String[] args) throws Exception {
		new CoreWallet().run();
	}
	
	public CoreWallet() {
		preferences = Preferences.userRoot().node("org.wowdoge"); //this.getClass().getName());
	}
	
	public void run() throws Exception {
		if (getWalletFilePath() == null)
			run(new File("."), "dogecoins.dogewallet");
		else {
			File f = new File(getWalletFilePath());
			run(f.getParentFile(), f.getName());
		}
	}

	public void run(final File directory, final String fileName) throws Exception {
		NetworkParameters params = MainNetParams.get();
		
		walletFilePath = new File(directory, fileName).getAbsolutePath();
		String spvFilePath = getSPVFilePath(new File(directory,"dogecoins.dogespvchain").getAbsolutePath());
		System.out.println("SPVFilePath: " + spvFilePath);
		boolean exists = new File(walletFilePath).exists();
		
		appKit = new CoreWalletAppKit(params, directory, fileName, spvFilePath) {
			@Override
			protected void onSetupCompleted() {
				if (wallet().getKeychainSize() < 1) {
					ECKey key = new ECKey();
					wallet().addKey(key);
				}
				
				setWalletFilePath(walletFilePath);
				
				peerGroup().setConnectTimeoutMillis(1000);
				//peerGroup().setFastCatchupTimeSecs(0);//wallet().getEarliestKeyCreationTime());
				
				System.out.println(appKit.wallet());
				
				peerGroup().addEventListener(new AbstractPeerEventListener() {
					@Override
					public void onPeerConnected(Peer peer, int peerCount) {
						super.onPeerConnected(peer, peerCount);
						System.out.println(String.format("onPeerConnected: %s %s",peer,peerCount));
					}
					@Override
					public void onPeerDisconnected(Peer peer, int peerCount) {
						super.onPeerDisconnected(peer, peerCount);
						System.out.println(String.format("onPeerDisconnected: %s %s",peer,peerCount));
					}
					@Override public void onBlocksDownloaded(Peer peer, Block block, int blocksLeft) {
						super.onBlocksDownloaded(peer, block, blocksLeft);
						synchronizing = blocksLeft;
						System.out.println(String.format("%s blocks left (downloaded %s)",blocksLeft,block.getHashAsString()));
					}
					
					@Override public Message onPreMessageReceived(Peer peer, Message m) {
						System.out.println(String.format("%s -> %s",peer,m.getClass()));
						return super.onPreMessageReceived(peer, m);
					}
				},Threading.SAME_THREAD);
				
				wallet().addEventListener(new AbstractWalletEventListener() {
		            @Override
		            public void onWalletChanged(Wallet wallet) {
		            	dirty = true;
		            }
		            
		            @Override
		            public void onCoinsReceived(Wallet wallet, Transaction tx, java.math.BigInteger prevBalance, java.math.BigInteger newBalance) {
		            	playSoundFile("/org/wowdoge/coins-drop-1.wav");
		            }
		            
		            @Override
		            public void onKeysAdded(Wallet wallet, java.util.List<ECKey> keys) {
		            	dirty = true;
		            }
				});
				dirty = true;
			}
		};
		
		if (!exists)
			appKit.setCheckpoints(Wow.class.getResourceAsStream("/org/wowdoge/wowdoge.checkpoints"));
		
		appKit.start(); //startAndWait();
	}
	
	public Preferences getPreferences() {
		return preferences;
	}
	
	public String getWalletFilePath() {
		walletFilePath = preferences.get("walletFilePath", null);
		return walletFilePath;
	}
	
	public void setWalletFilePath(String path) {
		preferences.put("walletFilePath", path);
		walletFilePath = path;
	}
	
	public String getSPVFilePath(String path) {
		path = preferences.get("spvFilePath", path);
		preferences.put("spvFilePath", path);
		return path;
	}

	public void stop() {
		appKit.stopAndWait();
	}
	
	public void open(File f) throws Exception {
		stop();
		run(f.getParentFile(), f.getName());
	}
	
	public boolean isDirty() {
		if (appKit == null)
			return false;
		if (appKit.state() == State.STOPPING || appKit.state() == State.TERMINATED)
			return false;
		else
			return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public NetworkParameters getNetworkParameters() {
		return appKit.wallet().getNetworkParameters();
    }
	
	public Wallet getWallet() {
		return appKit.wallet();
	}
	
	public boolean isEncrypted() {
		return appKit.wallet().isEncrypted();
	}
	
	public void encrypt(CharSequence password) throws KeyCrypterException {
		appKit.wallet().encrypt(password);
	}
	
	public void decrypt(CharSequence password) throws KeyCrypterException {
		appKit.wallet().decrypt(appKit.wallet().getKeyCrypter().deriveKey(password));
	}
	
	public void createNewKeys(int number) {
		List<ECKey> keys = new ArrayList<ECKey>();
		for (int i = 0; i < number; i++) {
			ECKey key = new ECKey();
			keys.add(key);
		}
		appKit.wallet().addKeys(keys);
	}
	
	public java.util.List<ECKey> getKeys() {
		return appKit.wallet().getKeys();
	}
	
	public java.math.BigInteger getBalance() {
		return appKit.wallet().getBalance();
	}
	
	public java.lang.Iterable<WalletTransaction> getWalletTransactions() {
		return appKit.wallet().getWalletTransactions();
	}
	
	public List<Transaction> getTransactionsByTime() {
		return appKit.wallet().getTransactionsByTime();
	}
	
	public java.math.BigInteger getBalance(ECKey key) {
		return null;
	}
	
	public Wallet.SendResult sendCoins(Address address, float amount) throws InsufficientMoneyException {
		BigInteger value = Utils.toNanoCoins(new Float(amount).toString());
		// Send with a small fee attached to ensure rapid confirmation.
		final BigInteger amountToSend = value.subtract(Transaction.REFERENCE_DEFAULT_MIN_TX_FEE);
		final Wallet.SendResult sendResult = appKit.wallet().sendCoins(appKit.peerGroup(), address, amountToSend);
		return sendResult;
	}
	
	public Wallet.SendResult sendCoins(Address address, float amount, CharSequence password) throws InsufficientMoneyException {
		BigInteger value = Utils.toNanoCoins(new Float(amount).toString());
		// Send with a small fee attached to ensure rapid confirmation.
		final BigInteger amountToSend = value.subtract(Transaction.REFERENCE_DEFAULT_MIN_TX_FEE);
		// Make sure this code is run in a single thread at once.
		Wallet.SendRequest request = Wallet.SendRequest.to(address, amountToSend);
		// The SendRequest object can be customized at this point to modify how the transaction will be created.
		request.aesKey = appKit.wallet().getKeyCrypter().deriveKey(password);
		final Wallet.SendResult sendResult = appKit.wallet().sendCoins(appKit.peerGroup(), request);
		return sendResult;
	}
	
	public boolean checkPassword(CharSequence password) {
		return appKit.wallet().checkAESKey(appKit.wallet().getKeyCrypter().deriveKey(password));
	}
	
	public void saveToFile(java.io.File temp, java.io.File destFile) throws java.io.IOException {
		appKit.wallet().saveToFile(temp, destFile);
	}
	
	public final boolean isRunning() {
		if (appKit != null)
			return appKit.isRunning();
		else
			return false;
	}
	
	public final int isSynchronizing() {
		return synchronizing;
	}
	
	public final boolean isStoreFileLocked() {
		if (appKit != null)
			return appKit.isStoreFileLocked();
		else
			return false;
	}
	
	public void playSoundFile(String soundFilePath) {
		try
	    {
	        Clip clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File(soundFilePath)));
	        clip.start();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace();
	    }
	}
}
