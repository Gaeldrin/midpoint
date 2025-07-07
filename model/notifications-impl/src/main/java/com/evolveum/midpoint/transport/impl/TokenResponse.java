package com.evolveum.midpoint.transport.impl;

public class TokenResponse {
        private String tokenType;
        private int expiresIn;
        private int extExpiresIn;
        private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getExtExpiresIn() {
        return extExpiresIn;
    }

    public void setExtExpiresIn(int extExpiresIn) {
        this.extExpiresIn = extExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
