package org.walletAPI.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.Wallet;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private long userId;

    private boolean rememberMe;

    private List<Wallet> listWallet;
}
