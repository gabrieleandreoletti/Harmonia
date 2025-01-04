package org.elis.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.FollowDto;
import org.elis.dto.GenreDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.PlaylistDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.SongDto;
import org.elis.dto.SubscriptionDto;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityAlreadyExistException;
import org.elis.exception.InsertFailureException;
import org.elis.mapper.CustomerMapper;
import org.elis.service.definition.CustomerService;
import org.elis.service.definition.FollowService;
import org.elis.service.definition.GenreService;
import org.elis.service.definition.PlaylistService;
import org.elis.service.definition.SongService;
import org.elis.service.definition.SubscriptionService;
import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class FreeUserController {

	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private SongService songService;
	@Autowired
	private GenreService genreService;
	@Autowired
	private FollowService followService;
	@Autowired
	private JWTUtilities jwtUtilities;

	// UTENTE

	@PostMapping("/all/registration")
	public ResponseEntity<RegistrationCustomerDto> registration(@Valid @RequestBody RegistrationCustomerDto user) {
		try {
			customerService.insert(user);
			return ResponseEntity.ok().body(user);
		} catch (EntityAlreadyExistException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (InsertFailureException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}

	}

	@PostMapping("/all/adminRegistration")
	public ResponseEntity<RegistrationCustomerDto> adminRegistration(@Valid @RequestBody RegistrationCustomerDto user) {
		try {
			customerService.insertAdmin(user);
			return ResponseEntity.ok().body(user);
		} catch (EntityAlreadyExistException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (InsertFailureException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}

	}

	@PostMapping("/all/artistRegistration")
	public ResponseEntity<RegistrationCustomerDto> artistRegistration(
			@Valid @RequestBody RegistrationCustomerDto user) {
		try {
			customerService.insertArtist(user);
			return ResponseEntity.ok().body(user);
		} catch (EntityAlreadyExistException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (InsertFailureException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/all/login")
	public ResponseEntity<String> loginUtente(@Valid @RequestBody LoginCustomerDto json) {
		try {
			String token = customerService.login(json.getUsername(), json.getPassword());
			return ResponseEntity.ok().header("Authorization", token).build();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}

	}

	@PutMapping("/base/updatePassword")
	public ResponseEntity<LoginCustomerDto> updatePassword(@RequestParam String newPassword,
			UsernamePasswordAuthenticationToken u) throws InsertFailureException {

		String username = (String) u.getPrincipal();
		LoginCustomerDto user = customerService.findByUsername(username);
		if (user != null) {
			try {
				customerService.updatePassword(user, newPassword);
				return ResponseEntity.ok().body(user);
			} catch (InsertFailureException i) {
				return ResponseEntity.badRequest().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().build();
			}
		}
		return null;
	}

	@PutMapping("/base/updateEmail")
	public ResponseEntity<LoginCustomerDto> updateEmail(@RequestParam String newEmail,
			UsernamePasswordAuthenticationToken u) throws InsertFailureException {

		String username = (String) u.getPrincipal();
		LoginCustomerDto user = customerService.findByUsername(username);
		if (user != null) {
			try {
				customerService.updateEmail(user, newEmail);
				return ResponseEntity.ok().body(user);
			} catch (InsertFailureException i) {
				return ResponseEntity.badRequest().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().build();
			}
		}
		return null;
	}

	// PLAYLIST

	@PostMapping("/base/createPlaylist")
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

	@GetMapping("/base/mostraPlaylist")
	public ResponseEntity<List<PlaylistDto>> showPlaylist() {
		try {
			List<PlaylistDto> film = playlistService.selectAll();
			return ResponseEntity.ok().body(film);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/base/mostraPlaylistCreate")
	public ResponseEntity<List<PlaylistDto>> showPlaylist(UsernamePasswordAuthenticationToken u) {
		try {
			String username = (String) u.getPrincipal();
			CustomerDto user = customerService.findCustByUsername(username);
			List<PlaylistDto> playlist = playlistService.getPlaylistByUserId(user.getId());
			return ResponseEntity.ok().body(playlist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/base/addSongToPlaylist")
	public ResponseEntity<PlaylistDto> addSongToPlaylist(@RequestParam long id_playlist,
			@RequestBody List<SongDto> song) {
		try {
			PlaylistDto playlist = playlistService.getPlaylistById(id_playlist);
			for (SongDto s : song) {
				playlistService.insertSong(playlist, s);
			}
			return ResponseEntity.ok().body(playlist);
		} catch (InsertFailureException i) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// SONGS

	@GetMapping("/base/mostraSongs")
	public ResponseEntity<List<SongDto>> showSongs() {
		try {
			List<SongDto> film = songService.selectAll();
			return ResponseEntity.ok().body(film);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// GENRE

	@GetMapping("/base/mostraGenre")
	public ResponseEntity<List<GenreDto>> showGenre() {
		try {
			List<GenreDto> film = genreService.selectAll();
			return ResponseEntity.ok().body(film);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// FOLLOW

	@PostMapping("base/follow")
	public ResponseEntity<FollowDto> follow(UsernamePasswordAuthenticationToken u, @RequestBody CustomerDto followed) {
		try {
			String username = (String) u.getPrincipal();
			CustomerDto user = customerService.findCustByUsername(username);
			followService.insert(user, followed);
			return ResponseEntity.ok().build();
		} catch (EntityAlreadyExistException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("base/unfollow")
	public ResponseEntity<FollowDto> unfollow(UsernamePasswordAuthenticationToken u,
			@RequestBody CustomerDto followed) {
		try {
			String username = (String) u.getPrincipal();
			CustomerDto user = customerService.findCustByUsername(username);
			followService.unfollow(followed, user);
			return ResponseEntity.ok().build();
		} catch (EntityAlreadyExistException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("base/mostraFollower")
	public ResponseEntity<List<CustomerDto>> showFollower(UsernamePasswordAuthenticationToken u) {
		try {
			String username = (String) u.getPrincipal();
			CustomerDto user = customerService.findCustByUsername(username);
			List<CustomerDto> follower = followService.selectAll(user);
			return ResponseEntity.ok().build();
		} catch (EmptyListException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("base/get6Subscription")
	public ResponseEntity<SubscriptionDto> getSub(UsernamePasswordAuthenticationToken u, @RequestParam long id) {
		try {
			String username = (String) u.getPrincipal();
			CustomerDto user = customerService.findCustByUsername(username);
			SubscriptionDto subDto = subscriptionService.findById(id);
			subDto.setId(id);
			subDto.setSubscriptionDate(LocalDateTime.now());
			subDto.setExpiration(LocalDateTime.now().plusMonths(6));
			customerService.getSubscription(subDto, user);
			return ResponseEntity.ok().body(subDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

}
