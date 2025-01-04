package org.elis.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.elis.model.Customer;
import org.elis.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaPlaylistRepository extends JpaRepository<Playlist, Long>{

	@Query("select p from Playlist p where p.name = :name")
	Optional<Playlist> findPlaylistByName(String name);
	
	@Query("select p from Playlist p join p.customer c where c.id = :cId")
	Optional<List<Playlist>> findPlaylistByCustomerId(Long cId);
}
