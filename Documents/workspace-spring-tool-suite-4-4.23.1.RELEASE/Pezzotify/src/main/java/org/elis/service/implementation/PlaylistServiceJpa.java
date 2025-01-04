package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.dto.GenreDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.SongDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.PlaylistMapper;
import org.elis.model.Customer;
import org.elis.model.Genre;
import org.elis.model.Playlist;
import org.elis.model.Song;
import org.elis.repository.jpa.JpaCustomerRepository;
import org.elis.repository.jpa.JpaPlaylistRepository;
import org.elis.repository.jpa.JpaSongRepository;
import org.elis.service.definition.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistServiceJpa implements PlaylistService {
	@Autowired
	private final JpaPlaylistRepository playlistRepository;

	@Autowired
	private final JpaSongRepository songRepository;
	@Autowired
	private final JpaCustomerRepository customerRepository;

	@Autowired
	private final PlaylistMapper playlistMapper;

	@Override
	public void insert(PlaylistDto p) throws InsertFailureException {
		if (p.getName().isBlank() || p.getName() == null) {
			throw new InsertFailureException("Il nome della playlist non può esserre vuoto");
		} else {
			Optional<Playlist> optPlaylist = playlistRepository.findPlaylistByName(p.getName());
			if (optPlaylist.isPresent()) {
				throw new InsertFailureException("Il nome della playlist è stato già usato");
			} else {
				Playlist playlist = playlistMapper.fromPlaylistDto(p);
				List<Song> songs = new ArrayList<>();

				for (SongDto songDto : p.getSongs()) {
					Optional<Song> songOpt = songRepository.findById(songDto.getId());
					if (songOpt.isPresent()) {
						Song song = songOpt.get();
						songs.add(song);
						song.getPlaylist().add(playlist); // Aggiungi la playlist alla canzone
					} else {
						throw new InsertFailureException("Canzone non trovata: " + songDto.getId());
					}
				}
				playlist.setSong(songs);
				;

				playlistRepository.save(playlist);
			}
		}

	}

	@Override
	public void delete(long id) {
		Optional<Playlist> optPlaylist = playlistRepository.findById(id);
		Playlist playlist = optPlaylist
				.orElseThrow(() -> new EntityNotFoundException("Non è presente una playlist con questo id"));
		playlistRepository.delete(playlist);

	}

	@Override
	public PlaylistDto getPlaylistById(long id) {
		Optional<Playlist> optPla = playlistRepository.findById(id);
		Playlist playlist = optPla
				.orElseThrow(() -> new EntityNotFoundException("Non esiste una playlist con questo id"));
		return playlistMapper.toPlaylistDto(playlist);
	}

	@Override
	public void insertSong(PlaylistDto p, SongDto s) throws Exception {

		// Ottieni l'entità Playlist
		Optional<Playlist> playlistOpt = playlistRepository.findById(p.getId());
		Playlist playlist = playlistOpt
				.orElseThrow(() -> new EntityNotFoundException("Non esiste una playlist con questo id"));
		// Ottieni l'entità Song
		Optional<Song> songOpt = songRepository.findById(s.getId());
		if (!songOpt.isPresent()) {
			throw new InsertFailureException("Canzone non trovata: " + s.getId());
		}

		Song song = songOpt.get();

		// Verifica se la canzone è già presente nella playlist
		if (playlist.getSong().contains(song)) {
			throw new EntityAlreadyExistException("Canzone già presente nella playlist");
		}

		// Aggiungi la canzone alla playlist
		playlist.getSong().add(song);

		// Aggiungi la playlist alla canzone
		if (!song.getPlaylist().contains(playlist)) {
			song.getPlaylist().add(playlist);
		}

		// Salva le modifiche
		playlistRepository.save(playlist);
		songRepository.save(song);
	}

	@Override
	public void removeSong(PlaylistDto p, SongDto s) throws Exception {
		if (s.getGenre() == null || s.getName().isBlank() || s.getName() == null) {
			throw new InsertFailureException("Campi non validi");
		} else if (p.getSongs().contains(s)) {
			p.getSongs().remove(s);
			playlistRepository.save(playlistMapper.fromPlaylistDto(p));
		} else {
			throw new EntityNotFoundException("Canzone non presente nell'album");
		}

	}

	@Override
	public List<SongDto> showSongs(PlaylistDto p) throws Exception {

		if (p.getSongs() == null) {
			throw new EmptyListException("Questa playlist è vuota");
		} else {
			List<SongDto> songs = new ArrayList<>();
			for (SongDto s : p.getSongs()) {
				if (s.getName() != null || !s.getName().isBlank()) {
					songs.add(s);
				} else {
					throw new InsertFailureException("Non ci possono essere canzoni nulle");
				}
			}
			return songs;
		}
	}

	@Override
	public void updateName(PlaylistDto p, String nome) throws InsertFailureException {
		if (nome == null || nome.isBlank()) {
			throw new InsertFailureException("Devi inserire un nome per modificarla");
		} else {
			Optional<Playlist> playlistOpt = playlistRepository.findPlaylistByName(p.getName());
			if (playlistOpt.isPresent()) {
				Playlist playlist = playlistOpt
						.orElseThrow(() -> new EntityNotFoundException("Non esiste una playlist con questi dati"));
				playlist.setName(nome);
				playlistRepository.save(playlist);
			}

		}

	}

	@Override
	public List<PlaylistDto> selectAll() throws EmptyListException {
		List<Playlist> playlist = playlistRepository.findAll();
		if (playlist != null) {
			List<PlaylistDto> playlistDto = new ArrayList<>();
			for (Playlist f : playlist) {
				playlistDto.add(playlistMapper.toPlaylistDto(f));
			}
			return playlistDto;
		} else {
			throw new EmptyListException("La lista dei generi è vuota");
		}
	}

	@Override
	public List<PlaylistDto> getPlaylistByUserId(long id) {
		Optional<Customer> cus = customerRepository.findById(id);

		if (cus.isPresent()) {
			Customer c = cus.get();
			Optional<List<Playlist>> p = playlistRepository.findPlaylistByCustomerId(c.getId());

			List<Playlist> playlists = p.get();
			List<PlaylistDto> playlistDto = new ArrayList<>();
			for (Playlist po : playlists) {
				playlistDto.add(playlistMapper.toPlaylistDto(po));
			}
			return playlistDto;

		} else {
			throw new EntityNotFoundException("non esiste un utente con questo id");
		}

	}
}
