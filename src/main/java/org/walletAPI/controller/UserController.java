package org.walletAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.walletAPI.entity.Message;
import org.walletAPI.entity.User;
import org.walletAPI.message.FinalMessage;
import org.walletAPI.request.user.ChangePasswordRequest;
import org.walletAPI.request.user.LoginRequest;
import org.walletAPI.request.user.SignUpRequest;
import org.walletAPI.request.user.UserRequestValidation;
import org.walletAPI.response.user.ChangePasswordResponse;
import org.walletAPI.response.user.LoginResponse;
import org.walletAPI.response.user.SignUpResponse;
import org.walletAPI.service.UserService;

import java.util.Optional;

public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity addUser(@RequestBody SignUpRequest request) {
        try {
            UserRequestValidation.checkConfirmPassword(request.getPassword(), request.getConfirmPassword());
            Optional<User> findUser = userService.findByUserName(request.getUserName());
            Message<SignUpResponse> message;
            if (findUser.isPresent()) {
                message = new Message<>(FinalMessage.USERNAME_EXISTED, null);
            } else {
                User user = userService.createNewUser(request);
                message = new Message<>(FinalMessage.SIGNUP_SUCCESS, new SignUpResponse(user.getId()));
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
        try {
            Optional<User> findUser = userService.findByUserName(request.getUserName());
            Message<LoginResponse> message;
            if (findUser.isPresent()) {
                if (!request.getPassword().equals(findUser.get().getPassword())) {
                    message = new Message<>(FinalMessage.WRONG_PASSWORD, null);
                } else {
                    message = new Message<>(FinalMessage.LOGIN_SUCCESS,
                            new LoginResponse(findUser.get().getId(), request.isRememberMe(), findUser.get().getListWallet()));
                }
            } else {
                message = new Message<>(FinalMessage.NO_USER, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            Optional<User> findUser = userService.findByUserId(request.getUserId());
            Message<ChangePasswordResponse> message;
            if (findUser.isPresent()) {
                UserRequestValidation.checkPreviousPassword(request.getOldPassword(), findUser.get().getPassword());
                User user = userService.setNewPassword(findUser.get(), request.getNewPassword());
                message = new Message<>(FinalMessage.CHANGE_PASSWORD_SUCCESS, new ChangePasswordResponse(user.getId()));
            } else {
                message = new Message<>(FinalMessage.NO_USER, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
