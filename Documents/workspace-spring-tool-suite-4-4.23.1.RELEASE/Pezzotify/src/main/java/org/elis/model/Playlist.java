package org.elis.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Playlist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = true, unique = true)
	private String name;

	
	@ManyToMany(mappedBy = "playlists")
	private List<Customer> customer;

	@ManyToMany
	@JoinTable(name = "song_playlist", joinColumns = @JoinColumn(name = "id_playlist"), inverseJoinColumns = @JoinColumn(name = "id_song"))
	private List<Song> song;

	@CreationTimestamp
	private LocalDateTime orario;

	@UpdateTimestamp
	private LocalDateTime orarioAggiornato;
}
