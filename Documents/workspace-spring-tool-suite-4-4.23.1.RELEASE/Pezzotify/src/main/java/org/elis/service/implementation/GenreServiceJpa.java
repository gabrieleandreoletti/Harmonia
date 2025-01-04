package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.dto.FollowDto;
import org.elis.dto.GenreDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.GenreMapper;
import org.elis.model.Album;
import org.elis.model.Follow;
import org.elis.model.Genre;
import org.elis.repository.jpa.JpaGenreRepository;
import org.elis.service.definition.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceJpa implements GenreService {

	@Autowired
	private final JpaGenreRepository repository;
	@Autowired
	private final GenreMapper genreMapper;
	
	@Override
	public void insert(GenreDto g) throws InsertFailureException, EntityAlreadyExistException {
		Optional<Genre> c = repository.findGenreByName(g.getName());
		if (c.isPresent()) {
			throw new EntityAlreadyExistException("Nome già in uso in un altro album");
		}else if(g.getName()==null || g.getName().isBlank()) {
			throw new InsertFailureException("Controllare i dati inseriti");
		}else {
			Genre genre = genreMapper.fromGenreDto(g);
			repository.save(genre);
		}
	}

	@Override
	public void delete(long id) {
		Optional<Genre> optGenre = repository.findById(id);
		Genre genre = optGenre.orElseThrow(() -> new EntityNotFoundException("Non è presente un genere con questo id"));
		repository.delete(genre);
	}

	@Override
	public GenreDto updateName(GenreDto g, String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GenreDto> selectAll() throws EmptyListException {
		List<Genre> genre = repository.findAll();
		if (genre != null) {
			List<GenreDto> genreDto = new ArrayList<>();
			for (Genre f : genre) {
				genreDto.add(genreMapper.toGenreDto(f));
			}
			return genreDto;
		}
		else {
			throw new EmptyListException("La lista dei generi è vuota");
		}
	}

}
