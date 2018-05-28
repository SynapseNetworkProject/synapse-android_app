package tech.synapsenetwork.app.service;

import tech.synapsenetwork.app.entity.TokenInfo;

import io.reactivex.Observable;

public interface TokenExplorerClientType {
    Observable<TokenInfo[]> fetch(String walletAddress);
}