package org.walletAPI.request.wallet;

import org.walletAPI.entity.Wallet;

import java.util.List;

public class WalletRequestValidation {
    public static void checkWalletNameExisted(List<Wallet> listWallet, Wallet currentWallet) throws Exception {
        for (Wallet wallet : listWallet) {
            if (wallet.getWalletName().equals(currentWallet.getWalletName())) {
                throw new Exception("exception.length.of.wallet.name.is.invalid");
            }
        }
    }
}
