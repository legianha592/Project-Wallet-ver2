package org.walletAPI.request.wallet;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class UpdateWalletRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long walletId;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(max = 50, message = "{exception.length.of.wallet.name.is.invalid}")
    private String walletName;
}
