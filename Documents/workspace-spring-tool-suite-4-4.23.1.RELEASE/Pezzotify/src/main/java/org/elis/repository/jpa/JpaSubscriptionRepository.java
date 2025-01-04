package org.elis.repository.jpa;

import java.util.Optional;

import org.elis.model.Song;
import org.elis.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaSubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	
	@Query("select c from Subscription c where c.name = :name")
	Optional<Subscription> findSubscriptionByName(String name);
}
