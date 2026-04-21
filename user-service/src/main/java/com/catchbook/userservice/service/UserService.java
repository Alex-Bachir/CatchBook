package com.catchbook.userservice.service;
import com.catchbook.userservice.model.User;
import com.catchbook.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// structure de base d'un service

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // ici, on implémente la logique métier

    // on récupére tous les utilisateur
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // on recupere les utilisateur par l'id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // modifie l'utilisateur
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // supprime l'utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
