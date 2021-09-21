package org.walletAPI.request.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@RequiredArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(min = 6, max = 20, message = "{exception.length.of.userName.is.invalid}")
    @Pattern(regexp = "^[a-zA-Z0-9]", message = "{exception.userName.has.invalid.character}")
    private String userName;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(min = 6, max = 20, message = "{exception.length.of.password.is.invalid}")
    @Pattern(regexp = "^[a-zA-Z0-9]", message = "{exception.password.has.invalid.character}")
    private String password;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(min = 6, max = 20, message = "{exception.length.of.confirmPassword.is.invalid}")
    private String confirmPassword;
}
