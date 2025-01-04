package org.elis.service.definition;

import java.util.List;

import org.elis.dto.PlaylistDto;
import org.elis.dto.SongDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.InsertFailureException;

public interface PlaylistService {

	void insert(PlaylistDto p) throws InsertFailureException;

	void delete(long id);

	void updateName(PlaylistDto p, String name) throws InsertFailureException;

	List<PlaylistDto> selectAll() throws EmptyListException;

	void insertSong(PlaylistDto p, SongDto s) throws Exception;

	void removeSong(PlaylistDto p, SongDto s) throws Exception;

	List<SongDto> showSongs(PlaylistDto p) throws Exception;

	PlaylistDto getPlaylistById(long id);
	
	List<PlaylistDto> getPlaylistByUserId(long id);
}
