package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.SubscriptionDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.CustomerMapper;
import org.elis.mapper.SubscriptionMapper;
import org.elis.model.Customer;
import org.elis.model.Role;
import org.elis.model.Subscription;
import org.elis.repository.jpa.JpaCustomerRepository;
import org.elis.repository.jpa.JpaSubscriptionRepository;
import org.elis.service.definition.CustomerService;
import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceJpa implements CustomerService {

	@Autowired
	private final CustomerMapper customerMapper;

	@Autowired
	private final JpaCustomerRepository repository;

	@Autowired
	private final SubscriptionMapper subscriptionMapper;

	@Autowired
	private final JpaSubscriptionRepository subscriptionRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final JWTUtilities jwtUtilities;

	@Override
	public void insert(RegistrationCustomerDto r) throws EntityAlreadyExistException, InsertFailureException {
		Optional<Customer> c = repository.findCustomerByUsername(r.getUsername());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Username già in uso");
		} else if (r.getEmail().isBlank() || r.getEmail() == null || r.getPassword().isBlank()
				|| r.getPassword() == null || r.getUsername().isBlank() || r.getUsername() == null) {
			throw new InsertFailureException("Controllare i dati inseriti");
		} else {
			String passCifrata = passwordEncoder.encode(r.getPassword());
			Customer user = customerMapper.fromRegistrationCustomerDto(r);
			user.setPassword(passCifrata);
			user.setRuolo(Role.BASE);
			repository.save(user);
		}

	}

	@Override
	public void insertArtist(RegistrationCustomerDto r) throws EntityAlreadyExistException, InsertFailureException {
		Optional<Customer> c = repository.findCustomerByUsername(r.getUsername());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Username già in uso");
		} else if (r.getEmail().isBlank() || r.getEmail() == null || r.getPassword().isBlank()
				|| r.getPassword() == null || r.getUsername().isBlank() || r.getUsername() == null) {
			throw new InsertFailureException("Controllare i dati inseriti");
		} else {
			String passCifrata = passwordEncoder.encode(r.getPassword());
			Customer user = customerMapper.fromRegistrationCustomerDto(r);
			user.setPassword(passCifrata);
			user.setRuolo(Role.ARTIST);
			repository.save(user);
		}

	}

	@Override
	public void insertAdmin(RegistrationCustomerDto r) throws InsertFailureException, EntityAlreadyExistException {
		Optional<Customer> c = repository.findCustomerByUsername(r.getUsername());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Username già in uso");
		} else if (r.getEmail().isBlank() || r.getEmail() == null || r.getPassword().isBlank()
				|| r.getPassword() == null || r.getUsername().isBlank() || r.getUsername() == null) {
			throw new InsertFailureException("Controllare i dati inseriti");
		} else {
			String passCifrata = passwordEncoder.encode(r.getPassword());
			Customer user = customerMapper.fromRegistrationCustomerDto(r);
			user.setPassword(passCifrata);
			user.setRuolo(Role.ADMIN);
			repository.save(user);
		}

	}
	
	@Cacheable(value = "Customer")
	@Override
	public CustomerDto findByUsernamePassword(String username, String password) throws Exception {
		Optional<Customer> c = repository.findCustomerByUsername(username);

		Customer user = c.orElseThrow(() -> new UsernameNotFoundException("User not found for username " + username));

		if (passwordEncoder.matches(password, user.getPassword())) {
			return customerMapper.toCustomerDto(user);
		} else {
			throw new Exception("utente non trovato");
		}
	}

	@Override
	public String login(String username, String password) throws Exception {

		Optional<Customer> c = repository.findCustomerByUsername(username);

		Customer user = c.orElseThrow(() -> new UsernameNotFoundException("User not found for username " + username));
		if (passwordEncoder.matches(password, user.getPassword())) {
			return jwtUtilities.generateToken(user);
		} else {
			throw new Exception("password non corretta");
		}
	}

	@Override
	@Cacheable(value = "Customers")
	public List<CustomerDto> selectAll() throws EmptyListException {
		List<Customer> utenti = repository.findAll();
		List<CustomerDto> utentiDto = new ArrayList<>();
		if (utenti.isEmpty()) {
			throw new EmptyListException("Non sono presenti utenti nel database");
		} else {
			for (Customer u : utenti) {
				utentiDto.add(customerMapper.toCustomerDto(u));
			}

			return utentiDto;
		}
	}

	@Override
	public void updateEmail(LoginCustomerDto customerDto, String email) throws InsertFailureException {
		if (email == null || email.isBlank()) {
			throw new InsertFailureException("Devi inserire una mail per modificarla");
		} else {
			Optional<Customer> c = repository.findCustomerByUsername(customerDto.getUsername());
			if (c.isPresent()) {
				Customer customer = c
						.orElseThrow(() -> new EntityNotFoundException("Non esiste un utente con questi dati"));
				if (passwordEncoder.matches(customer.getPassword(), customerDto.getPassword())) {
					customer.setEmail(email);
					repository.save(customer);
				}
			}
		}

	}

	@Override
	public void updatePassword(LoginCustomerDto customerDto, String password) throws InsertFailureException {
		if (password == null || password.isBlank()) {
			throw new InsertFailureException("Devi inserire una password per modificarla");
		} else {
			Optional<Customer> c = repository.findCustomerByUsername(customerDto.getUsername());
			if (c.isPresent()) {
				Customer customer = c
						.orElseThrow(() -> new EntityNotFoundException("Non esiste un utente con questi dati"));
				if ((customer.getPassword().equals(customerDto.getPassword()))) {
					customer.setPassword(passwordEncoder.encode(password));
					repository.save(customer);
				}

			}
		}
	}

	@Override
	@Cacheable(value = "Customers",key = "#username")
	public LoginCustomerDto findByUsername(String username) {
		Optional<Customer> c = repository.findCustomerByUsername(username);

		Customer user = c.orElseThrow(() -> new UsernameNotFoundException("User not found for username " + username));

		return customerMapper.toLoginCustomerDto(user);

	}

	@Override
	@Cacheable(value = "Customers" , key = "#id")
	public CustomerDto findById(long id) {
		Optional<Customer> c = repository.findById(id);

		Customer user = c.orElseThrow(() -> new UsernameNotFoundException("User not found for id " + id));

		return customerMapper.toCustomerDto(user);

	}

	@Override
	@Cacheable(value = "Customers",key = "#username")
	public CustomerDto findCustByUsername(String username) {
		Optional<Customer> c = repository.findCustomerByUsername(username);

		Customer user = c.orElseThrow(() -> new UsernameNotFoundException("User not found for username " + username));

		return customerMapper.toCustomerDto(user);

	}

	@Override
	public void getSubscription(SubscriptionDto s, CustomerDto customer) throws InsertFailureException {
		Optional<Subscription> subOpt = subscriptionRepository.findById(s.getId());
		if (subOpt.isPresent()) {
			Subscription sub = subOpt
					.orElseThrow(() -> new EntityNotFoundException("Non esiste un utente con questi dati"));
			if (customer != null && s != null && customer.getSub() == null) {
				s.setId(sub.getId());

				Customer c = customerMapper.fromCustomerDto(customer);
				c.setSubscription(sub);
				c.setRuolo(Role.PREMIUM);
				repository.save(c);

			} else {
				throw new InsertFailureException("Impossibile ottenere abbonamento");
			}
		}

	}

	@Override
	@CacheEvict(value = "Customers",key = "#c.id")
	public void removeCustomer(CustomerDto c) {
		Optional<Customer> optCust = repository.findById(c.getId());
		if (optCust.isPresent()) {
			Customer customer = optCust
					.orElseThrow(() -> new EntityNotFoundException("Non trovo un utente con questo id"));
			repository.delete(customer);
		}

	}

}
