package tech.synapsenetwork.app.dagger;

import tech.synapsenetwork.app.interact.ImportWalletInteract;
import tech.synapsenetwork.app.repository.PasswordStore;
import tech.synapsenetwork.app.repository.WalletRepositoryType;
import tech.synapsenetwork.app.viewmodel.ImportWalletViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
class ImportModule {
    @Provides
    ImportWalletViewModelFactory provideImportWalletViewModelFactory(
            ImportWalletInteract importWalletInteract) {
        return new ImportWalletViewModelFactory(importWalletInteract);
    }

    @Provides
    ImportWalletInteract provideImportWalletInteract(
            WalletRepositoryType walletRepository, PasswordStore passwordStore) {
        return new ImportWalletInteract(walletRepository, passwordStore);
    }
}
