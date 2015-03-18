package com.stuffinder.tests;

import com.stuffinder.data.Account;
import com.stuffinder.engine.NetworkServiceProvider;
import com.stuffinder.exceptions.AccountNotFoundException;
import com.stuffinder.exceptions.IllegalFieldException;
import com.stuffinder.exceptions.NetworkServiceException;

public class EmulatorTest
{
	public static void main(String[] args)
	{
		NetworkServiceProvider.setNetworkService(NetworkServiceEmulator.getInstance());
		
		Account account;
		try {
			account = new Account("nth", "Nicolas", "Thierce", "nth@gmail.com");
			try {
				NetworkServiceProvider.getNetworkService().createAccount(account, "notOk");
				System.out.println("account created.");
			} catch (IllegalFieldException e) {
				System.out.println("error for account creation : " + e.getMessage());
			} catch (NetworkServiceException e) {
				e.printStackTrace();
			}
			try {
				NetworkServiceProvider.getNetworkService().createAccount(account, "correctPassword");
				System.out.println("account created.");
			} catch (IllegalFieldException e) {
				System.out.println("error for account creation : " + e.getMessage());
			} catch (NetworkServiceException e) {
				e.printStackTrace();
			}
			
			// will fail because pseudo already used.
			try {
				NetworkServiceProvider.getNetworkService().createAccount(account, "correctPassword");
				System.out.println("account created.");
			} catch (IllegalFieldException e) {
				System.out.println("error for account creation : " + e.getMessage());
			} catch (NetworkServiceException e) {
				e.printStackTrace();
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			NetworkServiceProvider.getNetworkService().authenticate("invalid", "invalid");
			System.out.println("Authentication 1 done.");
			NetworkServiceProvider.getNetworkService().logOut();
		} catch (AccountNotFoundException e) {
			System.out.println("account not found.");
		} catch (NetworkServiceException e) {
			e.printStackTrace();
		}

		try {
			NetworkServiceProvider.getNetworkService().authenticate("nth", "correctPassword");
			System.out.println("Authentication 2 done.");
		} catch (AccountNotFoundException e) {
			System.out.println("account not found.");
		} catch (NetworkServiceException e) {
			e.printStackTrace();
		}
		
	}

}
