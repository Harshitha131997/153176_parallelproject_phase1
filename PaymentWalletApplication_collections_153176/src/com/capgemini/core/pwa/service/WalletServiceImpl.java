package com.capgemini.core.pwa.service;

import java.math.BigDecimal;
import java.util.Map;

import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.bean.Wallet;
import com.capgemini.core.pwa.exception.InsufficientBalanceException;
import com.capgemini.core.pwa.exception.InvalidInputException;
import com.capgemini.core.pwa.repository.WalletRepo;
import com.capgemini.core.pwa.repository.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{

	WalletRepo walletrepo;
	

	public WalletServiceImpl(Map<String, Customer> data){
		walletrepo	= new WalletRepoImpl(data);
	}
	public WalletServiceImpl(WalletRepo walletrepo) {		
	this.walletrepo = walletrepo;
	}
	

	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount) {
		// TODO Auto-generated method stub
		Customer customer = new Customer();
		Wallet wallet = new Wallet();
		wallet.setBalance(amount);
		
		customer.setName(name);
		customer.setMobileNo(mobileno);	
		customer.setWallet(wallet);
		if(isValid(customer))
		 walletrepo.save(customer);
		
		return customer;
	
	}

	@Override
	public Customer showBalance(String mobileno) {
		// TODO Auto-generated method stub
		
		if(isMobileNumberInvalid(mobileno)==true)
			
				throw new InvalidInputException("invalid mobile number");
		
		Customer customer = new Customer();
		
		customer = walletrepo.findOne(mobileno);
		
	
		return customer;
		
	
		
			
		
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		// TODO Auto-generated method stub
		
Customer customer1;
Customer customer2;
		customer1 = withdrawAmount(sourceMobileNo ,amount);
		customer2= depositAmount(targetMobileNo ,amount);
	
         
		return customer1;
	
			
		
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		// TODO Auto-generated method stub
		
		if(isMobileNumberInvalid(mobileNo)==true)
			
			throw new InvalidInputException("invalid mobile number");
		if(isBalanceInvalid(amount)==true)
			throw new InvalidInputException("amount is not valid");
		
		
		Customer customer1 = new Customer();
		customer1 = walletrepo.findOne(mobileNo);
	
BigDecimal bal = customer1.getWallet().getBalance().add(amount);
customer1.getWallet().setBalance(bal);

customer1.setWallet(customer1.getWallet());

walletrepo.save(customer1);

		return customer1;
		
		
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		// TODO Auto-generated method stub
		

		if(isMobileNumberInvalid(mobileNo)==true)
			
			throw new InvalidInputException("invalid mobile number");
		if(isBalanceInvalid(amount))
			throw new InvalidInputException("amount is not valid");
		
		
		Customer customer1;
		Wallet wallet= new Wallet();	
		customer1 = walletrepo.findOne(mobileNo);
		
		if(customer1.getWallet().getBalance().compareTo(amount)>0)
		{
BigDecimal bal = customer1.getWallet().getBalance().subtract(amount);
wallet.setBalance(bal);

customer1.setWallet(wallet);

walletrepo.save(customer1);

		
		} 
		else
		
				throw new InsufficientBalanceException("balance is not adequate");
			
			
		
		
		
		return customer1;	

	}

	@Override
	public boolean isValid(Customer customer) 
	{
		
		if( customer.getName() == null ||  customer.getName().trim().isEmpty() )
			
				throw new InvalidInputException( "Name Cannot be Empty" );
			
		
		if( customer.getMobileNo() == null ||  isMobileNumberInvalid( customer.getMobileNo() ) || customer.getMobileNo().trim().isEmpty())
		
				throw new InvalidInputException( "Mobile Number is invalid" );
		
		if(customer.getWallet() == null || isBalanceInvalid(customer.getWallet().getBalance()))
		
				throw new InvalidInputException("Balance is invalid ");
	
		else
			return true;
	
	}
	
	public static boolean isMobileNumberInvalid( String phoneNumber )
	{
		if(String.valueOf(phoneNumber).matches("^[7-9]{1}[0-9]{9}$")) 
		{
			return false;
		}		
		else 
			return true;
	}
	public static boolean isBalanceInvalid(BigDecimal balance)
	{
		
		if(balance.compareTo(new BigDecimal(0))<0)
			return true;
		
		else
			return false;
			
			
	}
	
}
		
		
		

