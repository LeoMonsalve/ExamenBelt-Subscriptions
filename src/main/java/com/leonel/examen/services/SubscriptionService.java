package com.leonel.examen.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.leonel.examen.models.Subscription;
import com.leonel.examen.repositories.SubscriptionRepository;


@Service
public class SubscriptionService {
	
	private SubscriptionRepository subRepo;
	
	public SubscriptionService(SubscriptionRepository subRepo) {
		this.subRepo = subRepo;
	}

	public List<Subscription> getAll(){
		return (List<Subscription>) subRepo.findAll();
	}
	public Subscription findSubscriptionById(Long id) {
		Optional<Subscription> optionalSub = subRepo.findById(id);
		if (optionalSub.isPresent()) {
			return optionalSub.get();
		} else {
			return null;
		}
	}
	public void createSub(Subscription sub) {
		subRepo.save(sub);
	}
	public void updateSub(Subscription sub) {
		subRepo.save(sub);
	}
	public void deleteSub(Long id) {
		subRepo.deleteById(id);
	}
}