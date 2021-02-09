package com.leonel.examen.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.leonel.examen.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByName(String string);

}
