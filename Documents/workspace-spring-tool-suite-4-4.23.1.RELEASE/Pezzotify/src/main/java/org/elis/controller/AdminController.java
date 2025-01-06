package org.elis.controller;


import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.GenreDto;
import org.elis.dto.LabelDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.SubscriptionDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.InsertFailureException;

import org.elis.service.definition.CustomerService;
import org.elis.service.definition.GenreService;
import org.elis.service.definition.LabelService;
import org.elis.service.definition.PlaylistService;
import org.elis.service.definition.SubscriptionService;
import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
public class AdminController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private GenreService genreService;
	@Autowired
	private LabelService labelService;

	@Autowired
	private PlaylistService playlistService;


	@PostMapping("/admin/createSubscription")
	public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody SubscriptionDto json) throws Exception {
		try {
			subscriptionService.insert(json);
			return ResponseEntity.ok().body(json);
		} catch (InsertFailureException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/admin/mostraUtenti")
	public ResponseEntity<List<CustomerDto>> showAllUsers() throws Exception {
		try {
			List<CustomerDto> customer = customerService.selectAll();
			return ResponseEntity.ok().body(customer);
		} catch (EmptyListException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/admin/deleteUtenti")
	public ResponseEntity<List<Void>> deleteUsers(@RequestParam long id) throws Exception {
		try {
			CustomerDto c = customerService.findById(id);
			customerService.removeCustomer(c);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// GENERE

	@PostMapping("/admin/createGenre")
	public ResponseEntity<GenreDto> createGenere(@Valid @RequestBody GenreDto json) throws Exception {
		try {
			genreService.insert(json);
			return ResponseEntity.ok().body(json);
		} catch (InsertFailureException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/admin/removeGenre")
	public ResponseEntity<GenreDto> removeGenre(@RequestBody GenreDto json) throws Exception {
		try {
			genreService.delete(json.getId());
			return ResponseEntity.ok().body(json);
		} catch (EntityNotFoundException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// PLAYLIST

	@PostMapping("/admin/createPlaylist")
	public ResponseEntity<PlaylistDto> createPlaylist(@Valid @RequestBody PlaylistDto json,
			UsernamePasswordAuthenticationToken u) throws Exception {
		String username = (String) u.getPrincipal();
		CustomerDto user = customerService.findCustByUsername(username);
		if (u != null) {
			try {
				json.setCreator(user);
				playlistService.insert(json);
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

	@DeleteMapping("/admin/removePlaylist")
	public ResponseEntity<PlaylistDto> removeGenre(@RequestBody PlaylistDto json) throws Exception {
		try {
			playlistService.delete(json.getId());
			return ResponseEntity.ok().body(json);
		} catch (EntityNotFoundException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/admin/addLabel")
	public ResponseEntity<LabelDto> insertLabel(@RequestBody LabelDto json) throws Exception {
		try {
			labelService.insert(json);
			return ResponseEntity.ok().body(json);
		} catch (InsertFailureException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

}
