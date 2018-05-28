package tech.synapsenetwork.app.service;

import tech.synapsenetwork.app.entity.Ticker;

import io.reactivex.Observable;

public interface TickerService {

    Observable<Ticker> fetchTickerPrice(String ticker);
}
