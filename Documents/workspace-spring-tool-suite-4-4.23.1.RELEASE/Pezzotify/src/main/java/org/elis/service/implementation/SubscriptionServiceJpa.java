package org.elis.service.implementation;

import java.util.Optional;

import org.elis.dto.SubscriptionDto;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.SubscriptionMapper;
import org.elis.model.Genre;
import org.elis.model.Subscription;
import org.elis.repository.jpa.JpaSubscriptionRepository;
import org.elis.service.definition.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceJpa implements SubscriptionService {
	@Autowired
	private final JpaSubscriptionRepository repository;
	@Autowired
	private final SubscriptionMapper mapper;

	@Override
	public void insert(SubscriptionDto s) throws EntityAlreadyExistException, InsertFailureException {
		Optional<Subscription> optSub = repository.findSubscriptionByName(s.getName());
		if (optSub.isPresent()) {
			throw new EntityAlreadyExistException("Esiste già un abbonamento con questo nome");
		} else if (s.getName() == null || s.getPrice() <= 0 || s.getPrice() > 100 || s.getName().isBlank()) {
			throw new InsertFailureException("Controllare i valori inseriti");
		} else {
			Subscription sub = mapper.fromSubscriptionDto(s);
			repository.save(sub);
		}

	}

	@Override
	public void delete(long id) {
		Optional<Subscription> optSubscription = repository.findById(id);
		Subscription abbonamento = optSubscription
				.orElseThrow(() -> new EntityNotFoundException("Non è presente un abbonamento con questo id"));
		repository.delete(abbonamento);

	}

	@Override
	public void extend() {
		// TODO Auto-generated method stub

	}

	@Override
	public SubscriptionDto findById(long id) {
		Optional<Subscription> optSubscription = repository.findById(id);
		Subscription abbonamento = optSubscription
				.orElseThrow(() -> new EntityNotFoundException("Non è presente un abbonamento con questo id"));
		return mapper.toSubscriptionDto(abbonamento);
	}

}
