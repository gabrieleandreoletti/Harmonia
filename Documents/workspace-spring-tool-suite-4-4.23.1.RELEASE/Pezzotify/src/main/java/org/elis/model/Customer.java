package org.elis.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.JoinColumn;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class Customer implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String nome;
	@Column(unique = true, nullable = false)
	private String cognome;

	@OneToMany(mappedBy = "followed")
	private List<Follow> follower;

	@OneToMany(mappedBy = "follower")
	private List<Follow> followed;

	@ManyToOne
	private Subscription subscription;

	@ManyToMany
	@JoinTable(
	    name = "customer_playlist",
	    joinColumns = @JoinColumn(name = "id_customer"),
	    inverseJoinColumns = @JoinColumn(name = "id_playlist")
	)
	private List<Playlist> playlists;


	@OneToMany(mappedBy = "artist")
	private List<Song> songs;

	@CreationTimestamp
	private LocalDateTime orario;

	@UpdateTimestamp
	private LocalDateTime orarioAggiornato;

	@Column(nullable = false)
	private Role ruolo;
	
	@ManyToMany(mappedBy = "feat")
	List<Song> featuring;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority("ROLE_" + ruolo.toString()));
	}

}
