package tech.synapsenetwork.app.service;

import io.reactivex.Observable;
import tech.synapsenetwork.app.entity.Ticker;

public interface FCMTokenService {

    Observable<String> updateToken(String address, String token);
}
