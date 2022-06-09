package com.iodigital.ksp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    @Override
    public Object signIn(Object request) {
        throw new NotImplementedException();
    }

    @Override
    public void signOut(String token) {
        throw new NotImplementedException();
    }

    @Override
    public Object refreshToken(String token) {
        throw new NotImplementedException();
    }
}
