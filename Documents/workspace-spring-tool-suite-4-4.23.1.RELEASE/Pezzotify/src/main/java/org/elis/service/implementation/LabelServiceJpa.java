package org.elis.service.implementation;

import java.util.Optional;

import org.elis.dto.GenreDto;
import org.elis.dto.LabelDto;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.LabelMapper;
import org.elis.model.Genre;
import org.elis.model.Label;
import org.elis.repository.jpa.JpaLabelRepository;
import org.elis.service.definition.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelServiceJpa implements LabelService {

	@Autowired
	private final LabelMapper labelMapper;

	@Autowired
	private final JpaLabelRepository labelRepository;

	@Override
	public void insert(LabelDto g) throws InsertFailureException, EntityAlreadyExistException {
		Optional<Label> c = labelRepository.findLabelByName(g.getName());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Nome già in uso in un altro album");
		} else if (g.getName() == null || g.getName().isBlank()) {
			throw new InsertFailureException("Controllare i dati inseriti");
		} else {
			Label genre = labelMapper.fromLabelDto(g);
			labelRepository.save(genre);
		}
	}
}
