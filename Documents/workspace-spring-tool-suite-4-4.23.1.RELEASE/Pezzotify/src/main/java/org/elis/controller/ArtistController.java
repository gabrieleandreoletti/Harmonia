package org.elis.controller;

import javax.security.sasl.AuthenticationException;

import org.elis.dto.AlbumDto;
import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.SongDto;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.CustomerMapper;
import org.elis.service.definition.AlbumService;
import org.elis.service.definition.CustomerService;
import org.elis.service.definition.SongService;
import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
public class ArtistController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private SongService songService;
	@Autowired
	private JWTUtilities jwtUtilities;

	@PostMapping("/artist/createSong")
	public ResponseEntity<SongDto> createSong(@Valid @RequestBody SongDto json,
			UsernamePasswordAuthenticationToken u) throws Exception {
		String username = (String) u.getPrincipal();
		CustomerDto user = customerService.findCustByUsername(username);
		if (u != null) {
			try {
				json.setArtist(user);
				songService.insert(json);
				return ResponseEntity.ok().body(json);
			} catch (InsertFailureException i) {
				//>>Voglio dare un messaggio nella risposta di errore
				return ResponseEntity.badRequest().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().build();
			}
		}
		return ResponseEntity.badRequest().build();

	}
	
	@DeleteMapping("/artist/removeSong")
	public ResponseEntity<SongDto> removeSong(@RequestBody SongDto json) throws Exception {
		try {
			songService.delete(json.getId());
			return ResponseEntity.ok().body(json);
		} catch (EntityNotFoundException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/artist/createAlbum")
	public ResponseEntity<AlbumDto> createAlbum(@Valid @RequestBody AlbumDto json,
			UsernamePasswordAuthenticationToken u) throws Exception {
		String username = (String) u.getPrincipal();
		CustomerDto user = customerService.findCustByUsername(username);
		if (u != null) {
			try {
				json.setArtist(user);
				albumService.insert(json);
				return ResponseEntity.ok().body(json);
			} catch (InsertFailureException i) {
				return ResponseEntity.badRequest().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/artist/removeAlbum")
	public ResponseEntity<AlbumDto> removeAlbum(@RequestBody AlbumDto json) throws Exception {
		try {
			albumService.delete(json.getId());
			return ResponseEntity.ok().body(json);
		} catch (EntityNotFoundException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

}
