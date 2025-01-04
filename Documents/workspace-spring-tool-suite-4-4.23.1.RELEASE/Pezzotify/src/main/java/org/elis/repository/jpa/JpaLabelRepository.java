package org.elis.repository.jpa;

import java.util.Optional;

import org.elis.model.Genre;
import org.elis.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaLabelRepository extends JpaRepository<Label, Long>{
	@Query("select c from Label c where c.name = :name")
	Optional<Label> findLabelByName(String name);
}
