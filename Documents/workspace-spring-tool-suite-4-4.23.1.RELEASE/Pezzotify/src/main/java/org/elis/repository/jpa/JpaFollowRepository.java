package org.elis.repository.jpa;

import java.util.List;

import org.elis.model.Customer;
import org.elis.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaFollowRepository extends JpaRepository<Follow, Long>{

	@Query("select (count(f)>0) from Follow f where f.followed = :followed and f.follower = :follower")
	boolean isFollowed(Customer followed, Customer follower);
	
	@Query("select f from Follow f where f.follower = :follower")
	List<Follow> findAllFollowedById (Customer follower);
	
	@Query("select f from Follow f where f.followed = :followed and f.follower = :follower")
	Follow isFollowedEntity(Customer followed, Customer follower);
	
}
