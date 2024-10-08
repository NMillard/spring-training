package com.mjukvare.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InMemoryBlacklistedNamesStore implements BlacklistedNamesStore {
    @Override
    public List<String> getBlacklistedNames() {
        return List.of("profanity-name");
    }
}
