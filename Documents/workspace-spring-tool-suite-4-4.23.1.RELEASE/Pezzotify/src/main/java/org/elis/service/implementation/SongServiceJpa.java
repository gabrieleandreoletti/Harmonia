package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
import org.elis.dto.AlbumDto;
import org.elis.dto.GenreDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.SongDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.SongMapper;
import org.elis.model.Album;
import org.elis.model.Customer;
import org.elis.model.Song;
import org.elis.repository.jpa.JpaSongRepository;
import org.elis.service.definition.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongServiceJpa implements SongService {
	@Autowired
	private final JpaSongRepository repository;
	@Autowired
	private final SongMapper mapper;

	@Override
	public void insert(SongDto a) throws InsertFailureException, EntityAlreadyExistException {
		Optional<Song> c = repository.findSongByName(a.getName());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Nome già in uso in un altro album");
		} else if (a.getName() == null || a.getName().isBlank() || a.getGenre() == null) {
			throw new InsertFailureException("Controllare i dati inseriti");
		} else {
			Song song = mapper.fromSongDto(a);
			repository.save(song);
		}

	}

	@Override
	public void delete(long id) {
		Optional<Song> optSong = repository.findById(id);
		Song canzone = optSong
				.orElseThrow(() -> new EntityNotFoundException("Non è presente una canzone con questo id"));
		repository.delete(canzone);
	}

	@Override
	public void updateName(SongDto s, String nome) throws InsertFailureException {
		if (nome == null || nome.isBlank()) {
			throw new InsertFailureException("Devi inserire un nome per modificarla");
		} else {
			Optional<Song> c = repository.findSongByName(s.getName());
			if (c.isPresent()) {
				Song song = c.orElseThrow(() -> new EntityNotFoundException("Non esiste un utente con questi dati"));
				song.setName(nome);
				repository.save(song);
			}

		}
	}

	@Override
	public void updateGenre(SongDto s, GenreDto genre) throws InsertFailureException {
		if (genre == null || genre.getName().isBlank()) {
			throw new InsertFailureException("Devi inserire un genere per modificare la canzone");
		} else {
			Optional<Song> c = repository.findSongByName(s.getName());
			if (c.isPresent()) {
				Song song = c.orElseThrow(() -> new EntityNotFoundException("Non esiste un utente con questi dati"));
				SongDto songDto = mapper.toSongDto(song);
				songDto.setGenre(genre);
				repository.save(mapper.fromSongDto(songDto));
			}

		}
	}

	@Override
	public List<SongDto> selectAll() throws EmptyListException {
		List<Song> song = repository.findAll();
		if (song != null) {
			List<SongDto> songDto = new ArrayList<>();
			for (Song f : song) {
				songDto.add(mapper.toSongDto(f));
			}
			return songDto;
		} else {
			throw new EmptyListException("La lista degli album è vuota");
		}
	}

}
