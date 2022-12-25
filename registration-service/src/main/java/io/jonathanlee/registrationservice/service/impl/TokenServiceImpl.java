package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.model.Token;
import io.jonathanlee.registrationservice.repository.TokenRepository;
import io.jonathanlee.registrationservice.service.RandomService;
import io.jonathanlee.registrationservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final long DEFAULT_TOKEN_EXPIRY_TIME_MINUTES = 15L;

    private final RandomService randomService;

    private final TokenRepository tokenRepository;

    @Override
    public Token generateAndPersistNewValidToken() {
        return this.tokenRepository.save(
                new Token(
                        ObjectId.get(),
                        this.randomService.generateNewId(),
                        this.randomService.generateNewTokenValue(),
                        ZonedDateTime.now().plusMinutes(DEFAULT_TOKEN_EXPIRY_TIME_MINUTES).toInstant()
                )
        );
    }

    @Override
    public Token generateAndPersistNewExpiredToken() {
        return this.tokenRepository.save(
                new Token(
                        ObjectId.get(),
                        this.randomService.generateNewId(),
                        this.randomService.generateNewTokenValue(),
                        ZonedDateTime.now().toInstant()
                )
        );
    }

    @Override
    public Token findByTokenValue(final String tokenValue) {
        return this.tokenRepository.findByValue(tokenValue);
    }

    @Override
    public void expireToken(final Token token) {
        token.setExpiryDate(ZonedDateTime.now().toInstant());
        this.tokenRepository.save(token);
    }


}
