package tech.synapsenetwork.app.interact.rx.operator;

import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.repository.PasswordStore;
import tech.synapsenetwork.app.repository.WalletRepositoryType;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.observers.DisposableCompletableObserver;

public class SavePasswordOperator implements SingleTransformer<Wallet, Wallet> {

    private final PasswordStore passwordStore;
    private final String password;
    private final WalletRepositoryType walletRepository;

    public SavePasswordOperator(
            PasswordStore passwordStore, WalletRepositoryType walletRepository, String password) {
        this.passwordStore = passwordStore;
        this.password = password;
        this.walletRepository = walletRepository;
    }

    @Override
    public SingleSource<Wallet> apply(Single<Wallet> upstream) {
        Wallet wallet = upstream.blockingGet();
        return passwordStore
                .setPassword(wallet, password)
                .onErrorResumeNext(err -> walletRepository.deleteWallet(wallet.address, password)
                        .lift(observer -> new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                observer.onError(err);
                            }

                            @Override
                            public void onError(Throwable e) {
                                observer.onError(e);
                            }
                        }))
                .toSingle(() -> wallet);
    }
}
