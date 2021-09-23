package org.walletAPI.request.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long userId;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(min = 6, max = 20, message = "{exception.length.of.password.is.invalid}")
    private String oldPassword;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(min = 6, max = 20, message = "{exception.length.of.password.is.invalid}")
    private String newPassword;
}
