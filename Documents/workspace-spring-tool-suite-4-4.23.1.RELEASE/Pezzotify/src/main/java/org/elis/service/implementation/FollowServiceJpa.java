package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.FollowDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.mapper.CustomerMapper;
import org.elis.mapper.FollowMapper;
import org.elis.model.Customer;
import org.elis.model.Follow;
import org.elis.repository.jpa.JpaCustomerRepository;
import org.elis.repository.jpa.JpaFollowRepository;
import org.elis.service.definition.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowServiceJpa implements FollowService {

	@Autowired
	private final JpaCustomerRepository customerRepository;
	@Autowired
	private final FollowMapper followMapper;
	@Autowired
	private final CustomerMapper customerMapper;
	@Autowired
	private final JpaFollowRepository followRepository;

	@Override
	public void insert(CustomerDto f, CustomerDto f2) throws EntityAlreadyExistException {
		Optional<Customer> optCustomer = customerRepository.findCustomerByUsername(f.getUsername());
		Customer customer = optCustomer.orElseThrow(() -> new EntityAlreadyExistException("Utente non trovato"));
		Optional<Customer> optCustomer2 = customerRepository.findCustomerByUsername(f2.getUsername());
		Customer customer2 = optCustomer2.orElseThrow(() -> new EntityAlreadyExistException("Utente non trovato"));
		if (!followRepository.isFollowed(customer, customer2)) {
			FollowDto followed = new FollowDto();
			followed.setFollower(customer);
			followed.setFollowed(customer2);
			followRepository.save(followMapper.fromFollowDto(followed));
		} else {
			throw new EntityAlreadyExistException("Gli utenti gia si seguono");
		}

	}

	@Override
	public List<CustomerDto> selectAll(CustomerDto c) throws EmptyListException {
		List<Follow> follow = followRepository.findAll();
		List<CustomerDto> follower = new ArrayList<>();
		if (follow != null) {
			List<FollowDto> followDto = new ArrayList<>();
			for (Follow f : follow) {
				followDto.add(followMapper.toFollowDto(f));
			}
			for (FollowDto f : followDto) {
				follower.add(customerMapper.toCustomerDto(f.getFollower()));
			}

			return follower;
		} else {
			throw new EmptyListException("La lista dei follower è vuota");
		}

	}

	@Override
	public void unfollow(CustomerDto f, CustomerDto f2) throws EntityAlreadyExistException {
		Optional<Customer> optCustomer = customerRepository.findCustomerByUsername(f.getUsername());
		Customer customer = optCustomer.orElseThrow(() -> new EntityAlreadyExistException("Utente non trovato"));
		Optional<Customer> optCustomer2 = customerRepository.findCustomerByUsername(f2.getUsername());
		Customer customer2 = optCustomer2.orElseThrow(() -> new EntityAlreadyExistException("Utente non trovato"));
		if (followRepository.isFollowedEntity(customer, customer2) != null) {
			Follow follow = followRepository.isFollowedEntity(customer, customer2);
			followRepository.delete(follow);

		} else {
			throw new EntityAlreadyExistException("Gli utenti non si seguono");
		}

	}

}
