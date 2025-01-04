package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.FollowDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.SubscriptionDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;

public interface CustomerService {

	void insert(RegistrationCustomerDto r) throws EntityAlreadyExistException, InsertFailureException;

	void insertAdmin(RegistrationCustomerDto r) throws InsertFailureException, EntityAlreadyExistException;

	CustomerDto findByUsernamePassword(String username, String password) throws Exception;
	
	LoginCustomerDto findByUsername(String username);
	
	CustomerDto findCustByUsername(String username);


	String login(String username, String password) throws Exception;

	List<CustomerDto> selectAll() throws EmptyListException;

	void updateEmail(LoginCustomerDto c, String email) throws InsertFailureException;

	void updatePassword(LoginCustomerDto c, String password) throws InsertFailureException;

	void insertArtist(RegistrationCustomerDto r) throws EntityAlreadyExistException, InsertFailureException;
	
	void getSubscription(SubscriptionDto s , CustomerDto c) throws InsertFailureException;

	CustomerDto findById(long id); 
	
	void removeCustomer(CustomerDto c);

	

}
