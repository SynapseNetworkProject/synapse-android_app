package tech.synapsenetwork.app.dagger;

import tech.synapsenetwork.app.interact.FetchTransactionsInteract;
import tech.synapsenetwork.app.interact.FindDefaultNetworkInteract;
import tech.synapsenetwork.app.interact.FindDefaultWalletInteract;
import tech.synapsenetwork.app.interact.GetDefaultWalletBalance;
import tech.synapsenetwork.app.repository.EthereumNetworkRepositoryType;
import tech.synapsenetwork.app.repository.TransactionRepositoryType;
import tech.synapsenetwork.app.repository.WalletRepositoryType;
import tech.synapsenetwork.app.router.ExternalBrowserRouter;
import tech.synapsenetwork.app.router.ManageWalletsRouter;
import tech.synapsenetwork.app.router.MyAddressRouter;
import tech.synapsenetwork.app.router.MyTokensRouter;
import tech.synapsenetwork.app.router.SendRouter;
import tech.synapsenetwork.app.router.SettingsRouter;
import tech.synapsenetwork.app.router.TransactionDetailRouter;
import tech.synapsenetwork.app.router.TransactionsRouter;
import tech.synapsenetwork.app.router.WebRTCRouter;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
class TransactionsModule {
    @Provides
    TransactionsViewModelFactory provideTransactionsViewModelFactory(
            FindDefaultNetworkInteract findDefaultNetworkInteract,
            FindDefaultWalletInteract findDefaultWalletInteract,
            FetchTransactionsInteract fetchTransactionsInteract,
            GetDefaultWalletBalance getDefaultWalletBalance,
            ManageWalletsRouter manageWalletsRouter,
            SettingsRouter settingsRouter,
            SendRouter sendRouter,
            TransactionDetailRouter transactionDetailRouter,
            MyAddressRouter myAddressRouter,
            MyTokensRouter myTokensRouter,
            TransactionsRouter transactionsRouter,
            ExternalBrowserRouter externalBrowserRouter,
            WebRTCRouter webRTCRouter) {

        return new TransactionsViewModelFactory(
                findDefaultNetworkInteract,
                findDefaultWalletInteract,
                fetchTransactionsInteract,
                getDefaultWalletBalance,
                manageWalletsRouter,
                settingsRouter,
                sendRouter,
                transactionDetailRouter,
                myAddressRouter,
                myTokensRouter,
                transactionsRouter,
                externalBrowserRouter,
                webRTCRouter);
    }

    @Provides
    FindDefaultNetworkInteract provideFindDefaultNetworkInteract(
            EthereumNetworkRepositoryType ethereumNetworkRepositoryType) {
        return new FindDefaultNetworkInteract(ethereumNetworkRepositoryType);
    }

    @Provides
    FindDefaultWalletInteract provideFindDefaultWalletInteract(WalletRepositoryType walletRepository) {
        return new FindDefaultWalletInteract(walletRepository);
    }

    @Provides
    FetchTransactionsInteract provideFetchTransactionsInteract(TransactionRepositoryType transactionRepository) {
        return new FetchTransactionsInteract(transactionRepository);
    }

    @Provides
    GetDefaultWalletBalance provideGetDefaultWalletBalance(
            WalletRepositoryType walletRepository, EthereumNetworkRepositoryType ethereumNetworkRepository) {
        return new GetDefaultWalletBalance(walletRepository, ethereumNetworkRepository);
    }

    @Provides
    ManageWalletsRouter provideManageWalletsRouter() {
        return new ManageWalletsRouter();
    }

    @Provides
    SettingsRouter provideSettingsRouter() {
        return new SettingsRouter();
    }

    @Provides
    SendRouter provideSendRouter() {
        return new SendRouter();
    }

    @Provides
    TransactionDetailRouter provideTransactionDetailRouter() {
        return new TransactionDetailRouter();
    }

    @Provides
    TransactionsRouter provideTransactionsRouter() {
        return new TransactionsRouter();
    }

    @Provides
    MyAddressRouter provideMyAddressRouter() {
        return new MyAddressRouter();
    }

    @Provides
    MyTokensRouter provideMyTokensRouter() {
        return new MyTokensRouter();
    }

    @Provides
    ExternalBrowserRouter provideExternalBrowserRouter() {
        return new ExternalBrowserRouter();
    }

    @Provides
    WebRTCRouter provideWebRTCRouter() {
        return new WebRTCRouter();
    }

}
