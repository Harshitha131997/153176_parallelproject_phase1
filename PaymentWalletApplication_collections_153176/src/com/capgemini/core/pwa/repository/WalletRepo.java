package com.capgemini.core.pwa.repository;

import com.capgemini.core.pwa.bean.Customer;

public interface WalletRepo {
public boolean save(Customer customer);
	
	public Customer findOne(String mobileNo);
}
