package org.walletAPI.response.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.Wallet;

@Data
@AllArgsConstructor
public class UpdateWalletResponse {
    private Wallet wallet;
}
