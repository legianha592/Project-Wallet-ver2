package org.walletAPI.response.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.Wallet;

import java.util.List;

@Data
@AllArgsConstructor
public class GetListWalletResponse {
    List<Wallet> listWallet;
}
