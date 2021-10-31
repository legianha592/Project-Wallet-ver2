package org.walletAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walletAPI.entity.User;
import org.walletAPI.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }
}
