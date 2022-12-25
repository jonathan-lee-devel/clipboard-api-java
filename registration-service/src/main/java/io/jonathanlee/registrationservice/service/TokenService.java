package io.jonathanlee.registrationservice.service;

import io.jonathanlee.registrationservice.model.Token;

public interface TokenService {

    Token generateAndPersistNewValidToken();

    Token generateAndPersistNewExpiredToken();

    Token findByTokenValue(final String tokenValue);

    void expireToken(final Token token);

}
