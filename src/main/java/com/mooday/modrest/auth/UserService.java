package com.mooday.modrest.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getOrCreateUser(GoogleIdToken.Payload payload) {
        String email = payload.getEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName((String) payload.get("name"));
            user = userRepository.save(user);
        }
        return user;
    }
}
