package org.walletAPI.request.user;

public class UserRequestValidation {
    public static void checkConfirmPassword(String password, String confirmPassword) throws Exception {
        if (!password.equals(confirmPassword)) {
            throw new Exception("exception.password.not.equal.confirmPassword");
        }
    }

    public static void checkPreviousPassword(String password, String previousPassword) throws Exception {
        if (!password.equals(previousPassword)) {
            throw new Exception("exception.password.not.equal.confirmPassword");
        }
    }
}
