package tech.synapsenetwork.app.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import tech.synapsenetwork.app.interact.FetchTransactionsInteract;
import tech.synapsenetwork.app.interact.FindDefaultNetworkInteract;
import tech.synapsenetwork.app.interact.FindDefaultWalletInteract;
import tech.synapsenetwork.app.interact.GetDefaultWalletBalance;
import tech.synapsenetwork.app.router.ExternalBrowserRouter;
import tech.synapsenetwork.app.router.ManageWalletsRouter;
import tech.synapsenetwork.app.router.MyAddressRouter;
import tech.synapsenetwork.app.router.MyTokensRouter;
import tech.synapsenetwork.app.router.SendRouter;
import tech.synapsenetwork.app.router.SettingsRouter;
import tech.synapsenetwork.app.router.TransactionDetailRouter;
import tech.synapsenetwork.app.router.TransactionsRouter;
import tech.synapsenetwork.app.router.WebRTCRouter;

public class TransactionsViewModelFactory implements ViewModelProvider.Factory {

    private final FindDefaultNetworkInteract findDefaultNetworkInteract;
    private final FindDefaultWalletInteract findDefaultWalletInteract;
    private final GetDefaultWalletBalance getDefaultWalletBalance;
    private final FetchTransactionsInteract fetchTransactionsInteract;
    private final ManageWalletsRouter manageWalletsRouter;
    private final SettingsRouter settingsRouter;
    private final SendRouter sendRouter;
    private final TransactionDetailRouter transactionDetailRouter;
    private final MyAddressRouter myAddressRouter;
    private final MyTokensRouter myTokensRouter;
    private final TransactionsRouter transactionsRouter;
    private final ExternalBrowserRouter externalBrowserRouter;
    private final WebRTCRouter webRTCRouter;

    public TransactionsViewModelFactory(
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
        this.findDefaultNetworkInteract = findDefaultNetworkInteract;
        this.findDefaultWalletInteract = findDefaultWalletInteract;
        this.getDefaultWalletBalance = getDefaultWalletBalance;
        this.fetchTransactionsInteract = fetchTransactionsInteract;
        this.manageWalletsRouter = manageWalletsRouter;
        this.settingsRouter = settingsRouter;
        this.sendRouter = sendRouter;
        this.transactionDetailRouter = transactionDetailRouter;
        this.myAddressRouter = myAddressRouter;
        this.myTokensRouter = myTokensRouter;
        this.transactionsRouter = transactionsRouter;
        this.externalBrowserRouter = externalBrowserRouter;
        this.webRTCRouter = webRTCRouter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TransactionsViewModel(
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
}
