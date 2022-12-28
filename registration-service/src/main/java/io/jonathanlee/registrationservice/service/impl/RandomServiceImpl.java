package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.service.RandomService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RandomServiceImpl implements RandomService {

    public static final int DEFAULT_ID_LENGTH = 32;

    public static final int DEFAULT_TOKEN_VALUE_LENGTH = 64;

    @Override
    public String generateNewId() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_ID_LENGTH);
    }

    @Override
    public String generateNewTokenValue() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_TOKEN_VALUE_LENGTH);
    }

}
