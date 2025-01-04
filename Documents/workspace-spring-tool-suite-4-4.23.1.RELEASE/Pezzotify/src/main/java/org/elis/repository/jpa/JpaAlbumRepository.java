package org.elis.repository.jpa;


import java.util.Optional;

import org.elis.model.Album;
import org.elis.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaAlbumRepository extends JpaRepository<Album, Long> {

	@Query("select c from Album c where c.name = :name")
	Optional<Album> findAlbumByName(String name);

}
