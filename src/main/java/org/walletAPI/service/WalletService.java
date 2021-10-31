package org.walletAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walletAPI.entity.Record;
import org.walletAPI.entity.Wallet;
import org.walletAPI.repository.WalletRepository;

import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    public Optional<Wallet> findByWalletId(Long walletId) {
        return walletRepository.findById(walletId);
    }

    public void addRecordToWallet(Wallet wallet, Record record) {
        wallet.addRecord(record);
    }

    public void updateWallet(Wallet wallet, Double amount) {
        wallet.setTotalAmount(wallet.getTotalAmount() + amount);
        walletRepository.save(wallet);
    }
}
