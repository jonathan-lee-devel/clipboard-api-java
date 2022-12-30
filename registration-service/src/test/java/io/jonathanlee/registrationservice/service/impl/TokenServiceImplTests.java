package io.jonathanlee.registrationservice.service.impl;

import static io.jonathanlee.registrationservice.service.impl.TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES;

import io.jonathanlee.registrationservice.model.Token;
import io.jonathanlee.registrationservice.repository.TokenRepository;
import io.jonathanlee.registrationservice.service.RandomService;
import io.jonathanlee.registrationservice.service.TokenService;
import java.time.ZonedDateTime;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TokenServiceImplTests {

  private TokenService tokenService;

  @Mock
  private RandomService randomService;

  @Mock
  private TokenRepository tokenRepository;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    this.tokenService = new TokenServiceImpl(this.randomService, this.tokenRepository);
  }

  @Test
  void testGenerateAndPersistNewValidToken() {
    Mockito.when(this.randomService.generateNewId()).thenReturn("123");
    Mockito.when(this.randomService.generateNewTokenValue()).thenReturn("456");

    final Token token = new Token(ObjectId.get(), "123", "456",
        ZonedDateTime.now().plusMinutes(DEFAULT_TOKEN_EXPIRY_TIME_MINUTES).toInstant());
    Mockito.when(this.tokenRepository.save(ArgumentMatchers.any())).thenReturn(token);

    final Token persistedToken = this.tokenService.generateAndPersistNewValidToken();

    Assertions.assertEquals(token, persistedToken);
    Assertions.assertTrue(ZonedDateTime.now().toInstant().isBefore(persistedToken.getExpiryDate()));
  }

  @Test
  void testGenerateAndPersistNewExpiredToken() {
    Mockito.when(this.randomService.generateNewId()).thenReturn("123");
    Mockito.when(this.randomService.generateNewTokenValue()).thenReturn("456");

    final Token token = new Token(ObjectId.get(), "123", "456", ZonedDateTime.now().toInstant());
    Mockito.when(this.tokenRepository.save(ArgumentMatchers.any())).thenReturn(token);

    final Token persistedToken = this.tokenService.generateAndPersistNewExpiredToken();

    Assertions.assertEquals(token, persistedToken);
    Assertions.assertFalse(
        ZonedDateTime.now().toInstant().isBefore(persistedToken.getExpiryDate()));
  }

  @Test
  void testFindByTokenValue() {
    final String tokenValue = "456";
    final Token token = new Token(ObjectId.get(), "123", tokenValue,
        ZonedDateTime.now().toInstant());

    Mockito.when(this.tokenRepository.findByValue(tokenValue)).thenReturn(token);

    final Token returnedToken = this.tokenService.findByTokenValue(tokenValue);

    Assertions.assertEquals(token, returnedToken);
  }

  @Test
  void testExpireToken() {
    final Token token = new Token(ObjectId.get(), "123", "456",
        ZonedDateTime.now().plusMinutes(DEFAULT_TOKEN_EXPIRY_TIME_MINUTES).toInstant());
    Mockito.when(this.tokenRepository.save(ArgumentMatchers.any())).thenReturn(token);

    Assertions.assertTrue(ZonedDateTime.now().toInstant().isBefore(token.getExpiryDate()));
    this.tokenService.expireToken(token);
    Assertions.assertFalse(ZonedDateTime.now().toInstant().isBefore(token.getExpiryDate()));
  }

}
