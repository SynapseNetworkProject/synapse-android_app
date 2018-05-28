package tech.synapsenetwork.app.service;

import tech.synapsenetwork.app.entity.Transaction;

import io.reactivex.Observable;

public interface BlockExplorerClientType {
	Observable<Transaction[]> fetchTransactions(String forAddress);
}
