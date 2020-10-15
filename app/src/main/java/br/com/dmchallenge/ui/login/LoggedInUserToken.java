package br.com.dmchallenge.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserToken {
    private String accessToken;

    LoggedInUserToken(String token) {
        this.accessToken = token;
    }

    String getAccessToken() {
        return accessToken;
    }
}