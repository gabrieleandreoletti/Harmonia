package org.elis.service.definition;

import java.util.List;

import org.elis.dto.GenreDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.SongDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;

public interface SongService {

	void insert(SongDto s) throws InsertFailureException, EntityAlreadyExistException;

	void delete(long id);

	void updateName(SongDto s, String nome) throws InsertFailureException;

	void updateGenre(SongDto s, GenreDto genre)  throws InsertFailureException;

	List<SongDto> selectAll() throws EmptyListException;

}
