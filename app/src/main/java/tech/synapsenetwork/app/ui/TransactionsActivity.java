package tech.synapsenetwork.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.entity.ErrorEnvelope;
import tech.synapsenetwork.app.entity.NetworkInfo;
import tech.synapsenetwork.app.entity.Transaction;
import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.ui.widget.adapter.TransactionsAdapter;
import tech.synapsenetwork.app.util.RootUtil;
import tech.synapsenetwork.app.viewmodel.BaseNavigationActivity;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModel;
import tech.synapsenetwork.app.viewmodel.TransactionsViewModelFactory;
import tech.synapsenetwork.app.widget.DepositView;
import tech.synapsenetwork.app.widget.EmptyTransactionsView;
import tech.synapsenetwork.app.widget.SystemView;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TransactionsActivity extends BaseNavigationActivity implements View.OnClickListener {

    @Inject
    TransactionsViewModelFactory transactionsViewModelFactory;
    private TransactionsViewModel viewModel;

    private SystemView systemView;
    private TransactionsAdapter adapter;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(tech.synapsenetwork.app.R.layout.activity_transactions);

        toolbar();
        //setTitle(getString(tech.synapsenetwork.app.R.string.unknown_balance_with_symbol));
        //setSubtitle("");
        initBottomNavigation();
        dissableDisplayHomeAsUp();

        adapter = new TransactionsAdapter(this::onTransactionClick);
        SwipeRefreshLayout refreshLayout = findViewById(tech.synapsenetwork.app.R.id.refresh_layout);
        systemView = findViewById(tech.synapsenetwork.app.R.id.system_view);

        RecyclerView list = findViewById(tech.synapsenetwork.app.R.id.list);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        systemView.attachRecyclerView(list);
        systemView.attachSwipeRefreshLayout(refreshLayout);

        viewModel = ViewModelProviders.of(this, transactionsViewModelFactory)
                .get(TransactionsViewModel.class);
        viewModel.progress().observe(this, systemView::showProgress);
        viewModel.error().observe(this, this::onError);
        viewModel.defaultNetwork().observe(this, this::onDefaultNetwork);
        //viewModel.defaultWalletBalance().observe(this, this::onBalanceChanged);
        viewModel.defaultWallet().observe(this, this::onDefaultWallet);
        viewModel.transactions().observe(this, this::onTransactions);

        refreshLayout.setOnRefreshListener(viewModel::fetchTransactions);
    }

    private void onTransactionClick(View view, Transaction transaction) {
        viewModel.showDetails(view.getContext(), transaction);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //setTitle(getString(tech.synapsenetwork.app.R.string.unknown_balance_without_symbol));
        //setSubtitle("");
        adapter.clear();
        viewModel.prepare();
        checkRoot();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(tech.synapsenetwork.app.R.menu.menu_settings, menu);
//
//        NetworkInfo networkInfo = viewModel.defaultNetwork().getValue();
//        if (networkInfo != null && networkInfo.name.equals(Constants.ETHEREUM_NETWORK_NAME)) {
//            getMenuInflater().inflate(tech.synapsenetwork.app.R.menu.menu_deposit, menu);
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case tech.synapsenetwork.app.R.id.action_settings: {
                viewModel.showSettings(this);
            } break;
            case tech.synapsenetwork.app.R.id.action_deposit: {
                openExchangeDialog();
            } break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case tech.synapsenetwork.app.R.id.try_again: {
                viewModel.fetchTransactions();
            } break;
            case tech.synapsenetwork.app.R.id.action_buy: {
                openExchangeDialog();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case tech.synapsenetwork.app.R.id.action_my_address: {
                viewModel.showMyAddress(this);
                return true;
            }
            case tech.synapsenetwork.app.R.id.action_my_tokens: {
                viewModel.showTokens(this);
                return true;
            }
            case tech.synapsenetwork.app.R.id.action_send: {
                viewModel.showSend(this);
                return true;
            }
        }
        return false;
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

    private void onTransactions(Transaction[] transaction) {
        if (transaction == null || transaction.length == 0) {
            EmptyTransactionsView emptyView = new EmptyTransactionsView(this, this);
            //emptyView.setNetworkInfo(viewModel.defaultNetwork().getValue());
            systemView.showEmpty(emptyView);
        }
        adapter.addTransactions(transaction);
        invalidateOptionsMenu();
    }

    private void onDefaultWallet(Wallet wallet) {
        adapter.setDefaultWallet(wallet);
    }

    private void onDefaultNetwork(NetworkInfo networkInfo) {
        adapter.setDefaultNetwork(networkInfo);
        setBottomMenu(tech.synapsenetwork.app.R.menu.menu_main_network);
    }

    private void onError(ErrorEnvelope errorEnvelope) {
        systemView.showError(getString(tech.synapsenetwork.app.R.string.error_fail_load_transaction), this);
    }

    private void checkRoot() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if (RootUtil.isDeviceRooted() && pref.getBoolean("should_show_root_warning", true)) {
            pref.edit().putBoolean("should_show_root_warning", false).apply();
            new AlertDialog.Builder(this)
                    .setTitle(tech.synapsenetwork.app.R.string.root_title)
                    .setMessage(tech.synapsenetwork.app.R.string.root_body)
                    .setNegativeButton(tech.synapsenetwork.app.R.string.ok, (dialog, which) -> {
                    })
                    .show();
        }
    }

    private void openExchangeDialog() {
        Wallet wallet = viewModel.defaultWallet().getValue();
        if (wallet == null) {
            Toast.makeText(this, getString(tech.synapsenetwork.app.R.string.error_wallet_not_selected), Toast.LENGTH_SHORT)
                    .show();
        } else {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            DepositView view = new DepositView(this, wallet);
            view.setOnDepositClickListener(this::onDepositClick);
            dialog.setContentView(view);
            dialog.show();
            this.dialog = dialog;
        }
    }

    private void onDepositClick(View view, Uri uri) {
        viewModel.openDeposit(this, uri);
    }
}
