package tech.synapsenetwork.app.ui;

import android.arch.lifecycle.ViewModelProviders;
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

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.R;
import tech.synapsenetwork.app.entity.ErrorEnvelope;
import tech.synapsenetwork.app.entity.NetworkInfo;
import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.util.CreateQRImage;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModel;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModelFactory;

public class HomeActivity extends BaseActivity {
    @Inject
    TransactionsViewModelFactory transactionsViewModelFactory;
    private TransactionsViewModel viewModel;


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

    }

    private void setListener() {

        findViewById(R.id.receive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showMyAddress(getBaseContext());
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showSettings(getBaseContext());
            }
        });
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showSend(getBaseContext());
            }
        });

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showTransactions(getBaseContext());
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
        showQrImage(wallet);
        Log.d("address", wallet.address);
    }

    private void onDefaultNetwork(NetworkInfo networkInfo) {
        Log.d("networkInfo", networkInfo.name);
    }


}
