package org.elis.service.definition;

import java.util.List;

import org.elis.dto.GenreDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.model.Genre;

public interface GenreService {

	void insert(GenreDto g) throws InsertFailureException, EntityAlreadyExistException;

	void delete(long id);
	
	List<GenreDto> selectAll() throws EmptyListException;
	
	GenreDto updateName(GenreDto g , String nome);
}
