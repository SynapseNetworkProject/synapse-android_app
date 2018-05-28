package tech.synapsenetwork.app.repository;

import tech.synapsenetwork.app.entity.Token;
import tech.synapsenetwork.app.entity.Wallet;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface TokenRepositoryType {

    Observable<Token[]> fetch(String walletAddress);

    Completable addToken(Wallet wallet, String address, String symbol, int decimals);
}
