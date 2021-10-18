package org.walletAPI.response.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteWalletResponse {
    private long walletId;
}
