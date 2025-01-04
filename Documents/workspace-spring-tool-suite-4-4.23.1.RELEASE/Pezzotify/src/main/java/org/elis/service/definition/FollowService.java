package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.FollowDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;



public interface FollowService {
	void insert(CustomerDto f,CustomerDto f2) throws EntityAlreadyExistException;

	void unfollow(CustomerDto f,CustomerDto f2) throws EntityAlreadyExistException;
	List<CustomerDto> selectAll(CustomerDto c) throws EmptyListException;
}
