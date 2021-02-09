package com.leonel.examen.repositories;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leonel.examen.models.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long>{
	@Query (value = "SELECT COUNT(user.id) AS c FROM user"
			+" JOIN subscription ON user.subscription_id = subscription.id"
			+" GROUP BY subscription_id "
			+" ORDER BY c DESC", nativeQuery=true)
	List<Tuple> countUsers();
}
