package tech.synapsenetwork.app.service;

import io.reactivex.Observable;

public interface FCMTokenService {

    Observable<String> updateToken(String address, String token);
}
