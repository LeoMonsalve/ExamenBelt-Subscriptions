package com.leonel.examen.services;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.leonel.examen.models.Role;
import com.leonel.examen.models.User;
import com.leonel.examen.repositories.RoleRepository;
import com.leonel.examen.repositories.UserRepository;


@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
    }
     
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
        userRepository.save(user);
    }    
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public void updateUser(User user){ 
    		user.setUpdatedAt(new Date());
    		userRepository.save(user);
    }
    
    public void createUser(User user) {
    		userRepository.save(user);
    }
    
    public User findUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}

    
    public void deletebyId(Long id) {
    		userRepository.deleteById(id);
    }
    
    public boolean checkIfAdmin(User user) {
    		List<Role> roles = user.getRoles();
    		for( int i = 0; i < roles.size(); i++) {
    			if(roles.get(i).getName().equals("ROLE_ADMIN")) {
    				return true;
    			}
    		}
    		return false; 			
    }
    
    public List<User> getAll(){
    		return (List<User>) userRepository.findAll();
    }
    
    public void deleteUser(Long id) {
    		userRepository.deleteById(id);
    }
}

