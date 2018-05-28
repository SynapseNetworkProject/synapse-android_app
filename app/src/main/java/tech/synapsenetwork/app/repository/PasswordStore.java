package tech.synapsenetwork.app.repository;

import tech.synapsenetwork.app.entity.Wallet;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface PasswordStore {
	Single<String> getPassword(Wallet wallet);
	Completable setPassword(Wallet wallet, String password);
	Single<String> generatePassword();
}
