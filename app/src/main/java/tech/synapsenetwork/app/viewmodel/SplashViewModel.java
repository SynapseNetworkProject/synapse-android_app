package tech.synapsenetwork.app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.interact.FetchWalletsInteract;

public class SplashViewModel extends ViewModel {
    private final FetchWalletsInteract fetchWalletsInteract;
    private MutableLiveData<Wallet[]> wallets = new MutableLiveData<>();

    SplashViewModel(FetchWalletsInteract fetchWalletsInteract) {
        this.fetchWalletsInteract = fetchWalletsInteract;

        fetchWalletsInteract
                .fetch()
                .subscribe(wallets::postValue, this::onError);
    }

    private void onError(Throwable throwable) {
        wallets.postValue(new Wallet[0]);
    }

    public LiveData<Wallet[]> wallets() {
        return wallets;
    }
}
