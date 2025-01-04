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
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = true, unique = true)
	private String name;

	@ManyToMany(mappedBy = "song")
	private List<Playlist> playlist;
	@ManyToOne
	private Customer artist;
	
	@ManyToMany
	@JoinTable(name = "featuring", joinColumns = @JoinColumn(name = "id_song"), inverseJoinColumns = @JoinColumn(name = "id_artist"))
	private List<Customer> feat;	
	
	@ManyToOne
	private Genre genre;

	@ManyToMany(mappedBy = "songs")
	private List<Album> albums;
	
	private LocalDateTime releaseDate = LocalDateTime.now();

	@CreationTimestamp
	private LocalDateTime orario;

	@UpdateTimestamp
	private LocalDateTime orarioAggiornato;
}
