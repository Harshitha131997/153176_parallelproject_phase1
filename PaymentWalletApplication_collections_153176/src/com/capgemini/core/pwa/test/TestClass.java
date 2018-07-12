package com.capgemini.core.pwa.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.bean.Wallet;
import com.capgemini.core.pwa.exception.InsufficientBalanceException;
import com.capgemini.core.pwa.exception.InvalidInputException;
import com.capgemini.core.pwa.service.WalletService;
import com.capgemini.core.pwa.service.WalletServiceImpl;

public class TestClass {

	WalletService service;
	
	@Before
	public void initData(){
		 Map<String,Customer> data= new HashMap<String, Customer>();
		 Customer cust1=new Customer("Amit", "9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer("Ajay", "9963242422",new Wallet(new BigDecimal(6000)));
		 Customer cust3=new Customer("Yogini", "9922950519",new Wallet(new BigDecimal(7000)));
				
		 data.put("9900112212", cust1);
		 data.put("9963242422", cust2);	
		 data.put("9922950519", cust3);	
			service= new WalletServiceImpl(data);
			
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount1() {
		Customer cust = service.createAccount(null,null,null);
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount2()
	{
		Customer  cust = service.createAccount(" ","9866823975",new BigDecimal(5000));
	
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3()
	{
		Customer  cust = service.createAccount("raghu","$6859",new BigDecimal(5000));
	
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4()
	{
		Customer  cust1 = service.createAccount("raghu","9866823975",new BigDecimal(-5));
	
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5()
	{
		Customer  cust = service.createAccount("raghu"," ",new BigDecimal(5000));
	
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount6()
	{
		Customer  cust = service.createAccount(" ","9866823975",new BigDecimal(5000));
	
	}
	
	@Test
	public void testCreateAccount7()
	{
		Customer  cust1 = service.createAccount("raghu","9866823975",new BigDecimal(5000));
		Customer  cust2 = new Customer();
		cust2.setName("raghu");
		cust2.setMobileNo("9866823975");
		cust2.setWallet(new Wallet(new BigDecimal(5000)));
        assertEquals(cust2,cust1);
	}
	
	@Test
	public void testBalance8()
	{
		Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(9000)));

		Customer customer = service.showBalance("9900112212");
		assertEquals(customer.getWallet().getBalance(),cust1.getWallet().getBalance());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testBalance9()
	{
		 service.showBalance("9900112");
		
	}
	
	@Test
	public void testBalance10()
	{
		 Customer cust1=new Customer("Ajay","9963242422",new Wallet(new BigDecimal(600)));

		Customer customer = service.showBalance("9963242422");
		assertNotEquals(0,customer.getWallet().getBalance());
		
	}
	
	@Test
	public void testDepositAccount11()
	{
		 Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(9000)));

		Customer customer = service.depositAmount("9900112212", new BigDecimal( 3000));
		assertEquals(customer.getWallet().getBalance(),new BigDecimal(12000));
		
	}
	
	@Test
	public void testDepositAccount12()
	{
		 Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(9000)));

		Customer customer = service.depositAmount("9900112212", new BigDecimal( 200));
		assertNotEquals(0,customer.getWallet().getBalance());
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAccount13()
	{
		 Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(9000)));

		Customer customer = service.depositAmount("99001122",new BigDecimal(3000));
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAccount14()
	{
		 Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(9000)));

		Customer customer = service.depositAmount("9900112212", new BigDecimal(-3));
		
	}
	
	@Test
	public void testDepositAccount15()
	{
		 Customer cust1=new Customer("Amit","9900112212",new Wallet(new BigDecimal(9000)));
		 Customer cust2=new Customer();
		Customer customer = service.depositAmount("9900112212", new BigDecimal( 200));
		cust2.setMobileNo("9900112212");
		cust2.setName("Amit");
		cust2.setWallet(new Wallet(new BigDecimal(9200)));
		assertEquals(cust2,cust1);
		
	}
	
	@Test(expected=InvalidInputException.class)
	public void testWithdraw16()
	{
		service.withdrawAmount("900000000", new BigDecimal(2000));
	}

	@Test(expected=InvalidInputException.class)
	public void testWithdraw17()
	{
		service.withdrawAmount("9963242422", new BigDecimal(-2000));
	}
	
	@Test(expected=InsufficientBalanceException.class)
	public void testWithdraw18()
	{
		service.withdrawAmount("9963242422", new BigDecimal(10000));
	}
	
	@Test
	public void testWithdraw19()
	{
		Customer cust1=service.withdrawAmount("9963242422", new BigDecimal(2000));
		BigDecimal actual=cust1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(4000);
		assertEquals(expected, actual);
	}	
	
	
	@Test
	public void testWithdraw20()
	{
		Customer cust1=service.withdrawAmount("9963242422", new BigDecimal(2000));
		BigDecimal actual=cust1.getWallet().getBalance();
		assertNotEquals(0, actual);
	}
	
	@Test
	public void testWithdraw21()
	{
		Customer cust1=service.withdrawAmount("9963242422", new BigDecimal(2000));
		Customer cust2 = new Customer();
		cust2.setName("Ajay");
		cust2.setMobileNo("9963242422");
		cust2.setWallet(new Wallet(new BigDecimal(4000)));
		assertEquals(cust2,cust1);
	}	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer22() {
		service.fundTransfer(null, null,new BigDecimal(7000));
	}

	@Test
	public void testFundTransfer23() {
		Customer cust1=service.fundTransfer("9900112212","9963242422",new BigDecimal(2000));
		BigDecimal actual=cust1.getWallet().getBalance();
		BigDecimal expected=new BigDecimal(7000);
		assertEquals(expected, actual);
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer24() {
		service.fundTransfer(null,"9963242422" ,new BigDecimal(7000));
	}

	@Test(expected=InvalidInputException.class)
	public void testFundTransfer25() {
		service.fundTransfer("9963242422",null,new BigDecimal(1000));
	}

	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer26() {
		service.fundTransfer("9963242422","9900112212",new BigDecimal(-8000));
	}

	@Test(expected=InsufficientBalanceException.class)
	public void testFundTransfer27() {
		service.fundTransfer("9963242422","9900112212",new BigDecimal(7000));
	}
	
	
	@Test
	public void testFundTransfer28() {
		Customer cust1=service.fundTransfer("9900112212","9963242422",new BigDecimal(2000));
		Customer cust2 = new Customer();
		cust2.setMobileNo("9900112212");
		cust2.setName("Amit");
		cust2.setWallet(new Wallet(new BigDecimal(7000)));
		assertEquals(cust2,cust1);
	}
	
	@Test
	public void testisValid29()
	{
		Customer cust = new Customer("radhika", "9966823975",new Wallet(new BigDecimal(6000)));
		boolean result = service.isValid(cust);
		assertEquals(true,result);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testisValid30()
	{
		Customer cust = new Customer("radhika", "_99668239",new Wallet(new BigDecimal(6000)));
		 service.isValid(cust);
	
	}
	
	@Test(expected=InvalidInputException.class)
	public void testisValid31()
	{
		Customer cust = new Customer("radhika", "9966823975",new Wallet(new BigDecimal(-8)));
		 service.isValid(cust);
	
	}

	
	@Test(expected=InvalidInputException.class)
	public void testisValid32()
	{
		Customer cust = new Customer(" ","9966823975",new Wallet(new BigDecimal(600)));
		 service.isValid(cust);
	
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testisValid33()
	{
		Customer cust = new Customer("radhika", " ",new Wallet(new BigDecimal(600)));
		 service.isValid(cust);
	
	}
	
	
	@Test
	public void testisValid34()
	{
		Customer cust = new Customer("radhika", "9866823975",new Wallet(new BigDecimal(0)));
		 boolean result = service.isValid(cust);
		 assertEquals(true,result);
	
	}
	
	
}
	
