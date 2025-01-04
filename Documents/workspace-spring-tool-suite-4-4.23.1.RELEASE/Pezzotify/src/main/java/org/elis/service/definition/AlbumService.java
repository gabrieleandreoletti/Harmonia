package org.elis.service.definition;

import java.util.List;

import org.elis.dto.AlbumDto;
import org.elis.dto.SongDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;

public interface AlbumService {

	void insert(AlbumDto a) throws EntityAlreadyExistException, InsertFailureException;
	
	void delete(long id);

	List<AlbumDto> selectAll() throws EmptyListException;

	void updateName(AlbumDto a, String name);

	void insertSong(AlbumDto a,SongDto s) throws InsertFailureException, EntityAlreadyExistException;
	void deleteSong(AlbumDto a,SongDto s) throws InsertFailureException, EntityAlreadyExistException;
}
