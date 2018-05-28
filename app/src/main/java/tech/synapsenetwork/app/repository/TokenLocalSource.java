package tech.synapsenetwork.app.repository;

import tech.synapsenetwork.app.entity.NetworkInfo;
import tech.synapsenetwork.app.entity.TokenInfo;
import tech.synapsenetwork.app.entity.Wallet;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TokenLocalSource {
    Completable put(NetworkInfo networkInfo, Wallet wallet, TokenInfo tokenInfo);
    Single<TokenInfo[]> fetch(NetworkInfo networkInfo, Wallet wallet);
}
