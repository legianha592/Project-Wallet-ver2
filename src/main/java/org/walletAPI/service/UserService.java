package org.walletAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walletAPI.entity.User;
import org.walletAPI.repository.UserRepository;
import org.walletAPI.request.user.SignUpRequest;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User createNewUser(SignUpRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User setNewPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
