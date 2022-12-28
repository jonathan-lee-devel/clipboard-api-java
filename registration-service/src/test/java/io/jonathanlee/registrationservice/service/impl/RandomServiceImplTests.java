package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.service.RandomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RandomServiceImplTests {

    private RandomService randomService;

    @BeforeEach
    void beforeEach() {
        this.randomService = new RandomServiceImpl();
    }

    @Test
    void testLengthOfId() {
        final String generatedId = this.randomService.generateNewId();

        Assertions.assertEquals(RandomServiceImpl.DEFAULT_ID_LENGTH, generatedId.length());
    }

    @Test
    void testLengthOfTokenValue() {
        final String generatedTokenValue = this.randomService.generateNewTokenValue();

        Assertions.assertEquals(RandomServiceImpl.DEFAULT_TOKEN_VALUE_LENGTH, generatedTokenValue.length());
    }

}
