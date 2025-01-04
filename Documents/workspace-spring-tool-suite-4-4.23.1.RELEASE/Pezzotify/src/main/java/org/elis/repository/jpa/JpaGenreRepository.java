package org.elis.repository.jpa;

import java.util.Optional;

import org.elis.model.Album;
import org.elis.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaGenreRepository extends JpaRepository<Genre, Long>{
	@Query("select c from Genre c where c.name = :name")
	Optional<Genre> findGenreByName(String name);
}
