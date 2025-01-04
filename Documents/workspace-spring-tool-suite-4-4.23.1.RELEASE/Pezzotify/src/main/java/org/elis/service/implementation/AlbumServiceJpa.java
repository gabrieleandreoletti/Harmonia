package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.dto.AlbumDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.SongDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.AlbumMapper;
import org.elis.model.Album;
import org.elis.model.Customer;
import org.elis.model.Genre;
import org.elis.model.Playlist;
import org.elis.model.Song;
import org.elis.repository.jpa.JpaAlbumRepository;
import org.elis.service.definition.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumServiceJpa implements AlbumService {

	@Autowired
	private final JpaAlbumRepository repository;

	@Autowired
	private final AlbumMapper albumMapper;

	@Override
	public void insert(AlbumDto a) throws EntityAlreadyExistException, InsertFailureException {
		Optional<Album> c = repository.findAlbumByName(a.getName());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Nome già in uso in un altro album");
		} else if (a.getName() == null || a.getName().isBlank() || a.getArtist() == null || a.getSongs() == null) {
			throw new InsertFailureException("Controllare i dati inseriti");
		} else {
			Album album = albumMapper.fromAlbumDto(a);
			repository.save(album);
		}

	}

	@Override
	public List<AlbumDto> selectAll() throws EmptyListException {
		List<Album> album = repository.findAll();
		if (album != null) {
			List<AlbumDto> albumDto = new ArrayList<>();
			for (Album f : album) {
				albumDto.add(albumMapper.toAlbumDto(f));
			}
			return albumDto;
		} else {
			throw new EmptyListException("La lista degli album è vuota");
		}
	}

	@Override
	public void updateName(AlbumDto a, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertSong(AlbumDto a, SongDto s) throws InsertFailureException, EntityAlreadyExistException {
		if (s.getGenre() == null || s.getName().isBlank() || s.getName() == null) {
			throw new InsertFailureException("Campi non validi");
		} else if (a.getSongs().contains(s)) {
			throw new EntityAlreadyExistException("Canzone già presente nell'album");
		} else {
			a.getSongs().add(s);
			repository.save(albumMapper.fromAlbumDto(a));
		}

	}

	@Override
	public void delete(long id) {
		Optional<Album> optAlbum = repository.findById(id);
		Album album = optAlbum.orElseThrow(() -> new EntityNotFoundException("Non è presente un album con questo id"));
		repository.delete(album);

	}

	@Override
	public void deleteSong(AlbumDto a, SongDto s) throws InsertFailureException, EntityAlreadyExistException {
		if (s.getGenre() == null || s.getName().isBlank() || s.getName() == null) {
			throw new InsertFailureException("Campi non validi");
		} else if (a.getSongs().contains(s)) {
			a.getSongs().remove(s);
			repository.save(albumMapper.fromAlbumDto(a));
		} else {
			throw new EntityNotFoundException("Canzone non presente nell'album");
		}

	}

}
