package org.walletAPI.request.wallet;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class DeleteWalletRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long walletId;
}
