package br.com.dmchallenge.ui.login;

import androidx.annotation.Nullable;

import br.com.dmchallenge.model.LoggedInUser;

/**
 * Authentication result : success (token value) or error message.
 */
public class AuthResult {

    @Nullable
    private LoggedInUser success;
    @Nullable
    private Integer error;

    public AuthResult(@Nullable Integer error) {
        this.error = error;
    }

    public <T> AuthResult(@Nullable LoggedInUser user) {
        this.success = user;
    }

    @Nullable
    LoggedInUser getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}