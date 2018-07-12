package com.capgemini.core.pwa.repository;

import java.util.Map;

import com.capgemini.core.pwa.bean.Customer;
import com.capgemini.core.pwa.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo {
	private Map<String, Customer> data; 
	public WalletRepoImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}

	@Override
	public boolean save(Customer customer) {
		String mobileno = customer.getMobileNo();
		data.put(mobileno,customer);
		boolean res = data.containsKey(mobileno);
		return res;
	}

	@Override
	public Customer findOne(String mobileNo) {
		Customer customer = new Customer();
		if(data.containsKey(mobileNo)==false)
				throw new InvalidInputException("invalid  mobileno");
		else
			customer = data.get(mobileNo);

		return customer;
	}
}
