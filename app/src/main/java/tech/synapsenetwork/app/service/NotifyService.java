package tech.synapsenetwork.app.service;

import io.reactivex.Observable;

public interface NotifyService {

    Observable<String> notify(String fromAddress, String toAddress, String notifyType);
}
