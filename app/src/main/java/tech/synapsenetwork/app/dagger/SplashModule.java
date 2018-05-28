package tech.synapsenetwork.app.dagger;

import tech.synapsenetwork.app.interact.FetchWalletsInteract;
import tech.synapsenetwork.app.repository.WalletRepositoryType;
import tech.synapsenetwork.app.viewmodel.SplashViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    SplashViewModelFactory provideSplashViewModelFactory(FetchWalletsInteract fetchWalletsInteract) {
        return new SplashViewModelFactory(fetchWalletsInteract);
    }

    @Provides
    FetchWalletsInteract provideFetchWalletInteract(WalletRepositoryType walletRepository) {
        return new FetchWalletsInteract(walletRepository);
    }
}
