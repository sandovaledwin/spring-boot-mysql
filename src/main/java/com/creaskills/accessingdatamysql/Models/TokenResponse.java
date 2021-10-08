package com.creaskills.accessingdatamysql.Models;

public class TokenResponse {
    private String userName;
    private String token;

    public TokenResponse(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
