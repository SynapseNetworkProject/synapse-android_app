package tech.synapsenetwork.app.repository;

import tech.synapsenetwork.app.entity.Transaction;
import tech.synapsenetwork.app.entity.Wallet;

import io.reactivex.Single;

public interface TransactionLocalSource {
	Single<Transaction[]> fetchTransaction(Wallet wallet);

	void putTransactions(Wallet wallet, Transaction[] transactions);

    void clear();
}
