package com.iodigital.ksp.service;

public interface AuthService {

    Object signIn(Object request);

    void signOut(String token);
    
    Object refreshToken(String token);

}
