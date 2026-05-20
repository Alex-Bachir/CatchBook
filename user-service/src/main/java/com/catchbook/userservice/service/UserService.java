package com.catchbook.userservice.service;
import com.catchbook.userservice.model.User;
import com.catchbook.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
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

    public User findOrCreateOAuthUser(Map<String, String> profile) {
        String googleId = profile.get("googleId");
        String email = profile.get("email");
        String name = profile.get("name");
        String picture = profile.get("picture");

        return userRepository.findByGoogleId(googleId).orElseGet(() -> {
            User newUser = new User();
            newUser.setGoogleId(googleId);
            newUser.setEmail(email);
            newUser.setFirstName(name);
            newUser.setPhotoProfile(picture);
            return userRepository.save(newUser);
        });
    }

}
