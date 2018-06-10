package tech.synapsenetwork.app.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.R;
import tech.synapsenetwork.app.chat.StyledDialogsActivity;
import tech.synapsenetwork.app.entity.ErrorEnvelope;
import tech.synapsenetwork.app.entity.NetworkInfo;
import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.service.FCMTokenService;
import tech.synapsenetwork.app.service.NotifyService;
import tech.synapsenetwork.app.service.UpdateFCMTokenService;
import tech.synapsenetwork.app.util.CreateQRImage;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModel;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModelFactory;

public class HomeActivity extends BaseActivity {
    @Inject
    TransactionsViewModelFactory transactionsViewModelFactory;
    private TransactionsViewModel viewModel;

    @Inject
    FCMTokenService fcmTokenService;

    @Inject
    NotifyService notifyService;

    private String walletAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        toolbar();
        setTitle(getString(R.string.transactions));
        dissableDisplayHomeAsUp();

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh_layout);

        viewModel = ViewModelProviders.of(this, transactionsViewModelFactory)
                .get(TransactionsViewModel.class);
        //viewModel.progress().observe(this, systemView::showProgress);
        viewModel.error().observe(this, this::onError);
        viewModel.defaultNetwork().observe(this, this::onDefaultNetwork);
        //viewModel.defaultWalletBalance().observe(this, this::onBalanceChanged);
        viewModel.defaultWallet().observe(this, this::onDefaultWallet);
        //viewModel.transactions().observe(this, this::onTransactions);

        setListener();
        Log.d("firebase", FirebaseInstanceId.getInstance().getToken() + "");
    }

    private void setListener() {

        findViewById(R.id.receive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showMyAddress(v.getContext());
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showSettings(v.getContext());
            }
        });
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showSend(v.getContext());
            }
        });

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showTransactions(v.getContext());
            }
        });

        findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showChat(v.getContext());
            }
        });

        findViewById(R.id.dapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), StyledDialogsActivity.class));
            }
        });

        findViewById(R.id.noti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewModel.prepare();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void showQrImage(Wallet wallet) {
        final Bitmap qrCode = CreateQRImage.createQRImage(this, getWindowManager(), wallet.address);
        ((ImageView) findViewById(R.id.qr_code_image)).setImageBitmap(qrCode);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings: {
//                viewModel.showSettings(this);
//            }
//            break;
//            case R.id.action_deposit: {
//                openExchangeDialog();
//            }
//            break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_my_address: {
//                viewModel.showMyAddress(this);
//                return true;
//            }
//            case R.id.action_my_tokens: {
//                viewModel.showTokens(this);
//                return true;
//            }
//            case R.id.action_send: {
//                viewModel.showSend(this);
//                return true;
//            }
//        }
//        return false;
//    }


    private void onError(ErrorEnvelope errorEnvelope) {
        Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
    }

    private void onBalanceChanged(Map<String, String> balance) {
        ActionBar actionBar = getSupportActionBar();
        NetworkInfo networkInfo = viewModel.defaultNetwork().getValue();
        Wallet wallet = viewModel.defaultWallet().getValue();
        if (actionBar == null || networkInfo == null || wallet == null) {
            return;
        }
        if (TextUtils.isEmpty(balance.get(Constants.USD_SYMBOL))) {
            actionBar.setTitle(balance.get(networkInfo.symbol) + " " + networkInfo.symbol);
            actionBar.setSubtitle("");
        } else {
            actionBar.setTitle("$" + balance.get(Constants.USD_SYMBOL));
            actionBar.setSubtitle(balance.get(networkInfo.symbol) + " " + networkInfo.symbol);
        }
    }

    private void onDefaultWallet(Wallet wallet) {
        walletAddress = wallet.address;
        showQrImage(wallet);
        updateFCMToken();
        Log.d("address", walletAddress);
    }

    private void onDefaultNetwork(NetworkInfo networkInfo) {
        Log.d("networkInfo", networkInfo.name);
    }


    private void updateFCMToken() {

        String fcmToken = FirebaseInstanceId.getInstance().getToken();

        Observable<String> responseObservable = fcmTokenService.updateToken(walletAddress, fcmToken);

        List<Observable<?>> observableList = new ArrayList<>();
        observableList.add(responseObservable);

        Observable.zip(observableList,
                new Function<Object[], Object[]>() {
                    @Override
                    public Object[] apply(Object[] objects) throws Exception {
                        return objects;
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object[] responseObject) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void call() {

        String fromAddress = walletAddress;
        String toAddress = walletAddress;
        String notifyType = "CALL";

        Observable<String> responseObservable = notifyService.notify(fromAddress, toAddress, notifyType);

        List<Observable<?>> observableList = new ArrayList<>();
        observableList.add(responseObservable);

        Observable.zip(observableList,
                new Function<Object[], Object[]>() {
                    @Override
                    public Object[] apply(Object[] objects) throws Exception {
                        return objects;
                    }
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object[] responseObject) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
