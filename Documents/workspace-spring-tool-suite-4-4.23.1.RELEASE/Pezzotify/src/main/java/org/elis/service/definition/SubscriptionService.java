package org.elis.service.definition;

import org.elis.dto.SubscriptionDto;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;

public interface SubscriptionService {

	void insert(SubscriptionDto s) throws EntityAlreadyExistException, InsertFailureException;

	void delete(long id);

	void extend();
	
	SubscriptionDto findById(long id) throws Exception;
}
