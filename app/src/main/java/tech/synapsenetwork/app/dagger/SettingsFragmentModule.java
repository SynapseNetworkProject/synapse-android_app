package tech.synapsenetwork.app.dagger;

import tech.synapsenetwork.app.interact.FindDefaultWalletInteract;
import tech.synapsenetwork.app.repository.WalletRepositoryType;
import tech.synapsenetwork.app.Router.ManageWalletsRouter;

import dagger.Module;
import dagger.Provides;

@Module
class SettingsFragmentModule {
    @Provides
    FindDefaultWalletInteract provideFindDefaultWalletInteract(WalletRepositoryType walletRepository) {
        return new FindDefaultWalletInteract(walletRepository);
    }

    @Provides
    ManageWalletsRouter provideManageWalletsRouter() {
        return new ManageWalletsRouter();
    }
}
