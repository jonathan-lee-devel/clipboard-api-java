package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.service.RandomService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RandomServiceImpl implements RandomService {

    private static final int ID_LENGTH = 32;

    private static final int TOKEN_VALUE_LENGTH = 64;

    @Override
    public String generateNewId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }

    @Override
    public String generateNewTokenValue() {
        return RandomStringUtils.randomAlphanumeric(TOKEN_VALUE_LENGTH);
    }

}
