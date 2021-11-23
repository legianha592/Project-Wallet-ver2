package org.walletAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.walletAPI.entity.Message;
import org.walletAPI.entity.User;
import org.walletAPI.entity.Wallet;
import org.walletAPI.message.FinalMessage;
import org.walletAPI.request.wallet.CreateWalletRequest;
import org.walletAPI.request.wallet.DeleteWalletRequest;
import org.walletAPI.request.wallet.UpdateWalletRequest;
import org.walletAPI.response.wallet.CreateWalletResponse;
import org.walletAPI.response.wallet.DeleteWalletResponse;
import org.walletAPI.response.wallet.GetListWalletResponse;
import org.walletAPI.response.wallet.UpdateWalletResponse;
import org.walletAPI.service.UserService;
import org.walletAPI.service.WalletService;

import java.util.List;
import java.util.Optional;

public class WalletController {
    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @GetMapping("/listByUserId")
    public ResponseEntity getListWalletByUserId(@RequestParam(name = "userId") long userId) {
        try {
            Optional<User> findUser = userService.findByUserId(userId);
            Message<GetListWalletResponse> message;

            if (findUser.isPresent()) {
                List<Wallet> listWallet = walletService.findByUserId(findUser.get());
                GetListWalletResponse response = new GetListWalletResponse(listWallet);
                message = new Message<>(FinalMessage.GET_LIST_WALLET_SUCCESS, response);
            } else {
                message = new Message<>(FinalMessage.NO_USER, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity createWallet(@RequestBody CreateWalletRequest request) {
        try {
            Optional<User> findUser = userService.findByUserId(request.getUserId());
            Message<CreateWalletResponse> message;

            if (findUser.isPresent()) {
                List<Wallet> listWallet = findUser.get().getListWallet();
                boolean checkDuplicateWalletName = walletService.checkDuplicateWalletName(listWallet, request.getWalletName());
                if (checkDuplicateWalletName) {
                    message = new Message<>(FinalMessage.WALLET_NAME_IS_EXISTED, null);
                    return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
                } else {
                    Wallet wallet = new Wallet();
                    wallet.setWalletName(request.getWalletName());
                    findUser.get().addWallet(wallet);
                    Wallet savedWallet = walletService.addWallet(wallet);
                    message = new Message<>(FinalMessage.CREATE_WALLET_SUCCESS, new CreateWalletResponse(savedWallet.getId()));
                }
            } else {
                message = new Message<>(FinalMessage.NO_USER, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateWallet(@RequestBody UpdateWalletRequest request) {
        try {
            Optional<Wallet> findWallet = walletService.findByWalletId(request.getWalletId());
            Message<UpdateWalletResponse> message;

            if (findWallet.isPresent()) {
                User user = findWallet.get().getUser();
                List<Wallet> listWallet = user.getListWallet();
                boolean checkDuplicateWalletName = walletService.checkDuplicateWalletName(listWallet, request.getWalletName());
                if (checkDuplicateWalletName) {
                    message = new Message<>(FinalMessage.WALLET_NAME_IS_EXISTED, null);
                    return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
                } else {
                    Wallet wallet = findWallet.get();
                    wallet.setWalletName(request.getWalletName());
                    Wallet savedWallet = walletService.addWallet(wallet);
                    message = new Message<>(FinalMessage.CHANGE_WALLET_NAME_SUCCESS, new UpdateWalletResponse(savedWallet));
                }

            } else {
                message = new Message<>(FinalMessage.NO_WALLET, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteWallet(@RequestBody DeleteWalletRequest request) {
        try {
            Optional<Wallet> findWallet = walletService.findByWalletId(request.getWalletId());
            Message<DeleteWalletResponse> message;

            if (findWallet.isPresent()) {
                Wallet wallet = findWallet.get();
                walletService.deleteWallet(wallet);
                message = new Message<>(FinalMessage.DELETE_WALLET_SUCCESS, new DeleteWalletResponse(request.getWalletId()));

            } else {
                message = new Message<>(FinalMessage.NO_WALLET, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
