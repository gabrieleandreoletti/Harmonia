package org.elis.repository.jpa;

import java.util.Optional;

import org.elis.model.Album;
import org.elis.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaSongRepository extends JpaRepository<Song, Long>{
	@Query("select c from Song c where c.name = :name")
	Optional<Song> findSongByName(String name);
}
