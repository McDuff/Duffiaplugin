package com.Mc_Duff.DuffiaPlugin.economy;

import org.bukkit.entity.Player;

import com.Mc_Duff.DuffiaPlugin.DuffiaPlugin;
import com.Mc_Duff.mini.Arguments;

public class economy {
	String currency = "D-Coin";
	String hasaccount = "You already have an account";
	String noaccount = "You do not have a bank account";
	String newaccount = "Your bank account has been created";
	String accountcheck ="Your bank account has";
	String paymentfail ="Payment not possible!";
	String paymentsuccess ="Payment was successfull!";
	String payment ="You have made a Payment to";
	String recieved = "has paid you";
	
	DuffiaPlugin plugin;
	
	public economy(DuffiaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void addaccount(Player accountholder) {
		Arguments lastEntry = plugin.database2.getArguments(accountholder.getDisplayName());
		int dcoins=0;
		if(lastEntry!=null){
			accountholder.sendMessage(accountholder.getDisplayName()+ ", " + hasaccount);
		}
		else {

			Arguments mentry = new Arguments(accountholder.getDisplayName());
			mentry.setValue("D-Coins",String.valueOf(dcoins));
			accountholder.sendMessage(accountholder.getDisplayName()+ ", " + newaccount);
			plugin.database2.addIndex(mentry.getKey(), mentry);
			plugin.database2.update();
		}
	}
	
	public void checkaccount(Player caster, Player accountholder) {
		Arguments lastEntry = plugin.database2.getArguments(accountholder.getDisplayName());
		if(lastEntry != null){
			String coinsdata = lastEntry.getValue("D-Coins");
			caster.sendMessage(accountholder.getDisplayName()+ ", " + accountcheck + " " + coinsdata + " " + currency);
		}
		else {
			
			accountholder.sendMessage(accountholder.getDisplayName()+ ", " + noaccount);
			addaccount(accountholder);
		}
	}
	
	public void addmoney(Player payer, Player accountholder, int amount) {
		Arguments pentry = plugin.database2.getArguments(accountholder.getDisplayName());
		if(pentry != null){
			String currentcoins = pentry.getValue("D-Coins");
			int dcoins = Integer.parseInt(currentcoins) + amount;
			pentry.setValue("D-Coins",String.valueOf(dcoins));
			accountholder.sendMessage(accountholder.getDisplayName() + ", You have been given " + amount +" " + currency + " by " + payer.getDisplayName());
			plugin.database2.addIndex(pentry.getKey(), pentry);
	
		}
		else {
			accountholder.sendMessage(accountholder.getDisplayName()+ ", " + noaccount);
		}
	}
	
	public void takemoney(Player accountholder2, Player payee, int amount2) {
		Arguments tentry = plugin.database2.getArguments(accountholder2.getDisplayName());
		if(tentry != null){
			String currentcoins = tentry.getValue("D-Coins");
			int dcoins = Integer.parseInt(currentcoins) - amount2;
			tentry.setValue("D-Coins",String.valueOf(dcoins));
			accountholder2.sendMessage(accountholder2.getDisplayName() + ", You have given " + amount2 +" " + currency + " to " + payee.getDisplayName());
			plugin.database2.addIndex(tentry.getKey(), tentry);
	
		}
		else {
			accountholder2.sendMessage(accountholder2.getDisplayName()+ ", " + noaccount);
		}
	}	

	
	public void payplayer(Player payer,Player payee, int amount) {
		Arguments payerlastEntry = plugin.database2.getArguments(payer.getDisplayName());
		int payerfunds = Integer.parseInt(payerlastEntry.getValue("D-Coins"));
		if(payerlastEntry !=null && payerfunds>=amount && amount>=0) {
		takemoney(payer,payee,amount);
		addmoney(payer,payee,amount);
		payer.sendMessage(payer.getDisplayName() + ", " + paymentsuccess );
		} else {
			payer.sendMessage(payer.getDisplayName()+ ", " + paymentfail);
		}
	
	}
}
