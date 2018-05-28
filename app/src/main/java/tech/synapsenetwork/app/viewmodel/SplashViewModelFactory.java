package tech.synapsenetwork.app.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import tech.synapsenetwork.app.interact.FetchWalletsInteract;

public class SplashViewModelFactory implements ViewModelProvider.Factory {

    private final FetchWalletsInteract fetchWalletsInteract;

    public SplashViewModelFactory(FetchWalletsInteract fetchWalletsInteract) {
        this.fetchWalletsInteract = fetchWalletsInteract;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SplashViewModel(fetchWalletsInteract);
    }
}