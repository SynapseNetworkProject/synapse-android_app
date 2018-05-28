package tech.synapsenetwork.app.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import tech.synapsenetwork.app.entity.ErrorEnvelope;
import tech.synapsenetwork.app.entity.Token;
import tech.synapsenetwork.app.ui.widget.adapter.TokensAdapter;
import tech.synapsenetwork.app.viewmodel.TokensViewModel;
import tech.synapsenetwork.app.viewmodel.TokensViewModelFactory;
import tech.synapsenetwork.app.widget.SystemView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import tech.synapsenetwork.app.Constants;

public class TokensActivity extends BaseActivity implements View.OnClickListener {
    @Inject
    TokensViewModelFactory transactionsViewModelFactory;
    private TokensViewModel viewModel;

    private SystemView systemView;
    private TokensAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        setContentView(tech.synapsenetwork.app.R.layout.activity_tokens);

        toolbar();

        adapter = new TokensAdapter(this::onTokenClick);
        SwipeRefreshLayout refreshLayout = findViewById(tech.synapsenetwork.app.R.id.refresh_layout);
        systemView = findViewById(tech.synapsenetwork.app.R.id.system_view);

        RecyclerView list = findViewById(tech.synapsenetwork.app.R.id.list);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        systemView.attachRecyclerView(list);
        systemView.attachSwipeRefreshLayout(refreshLayout);

        viewModel = ViewModelProviders.of(this, transactionsViewModelFactory)
                .get(TokensViewModel.class);
        viewModel.progress().observe(this, systemView::showProgress);
        viewModel.error().observe(this, this::onError);
        viewModel.tokens().observe(this, this::onTokens);
        viewModel.wallet().setValue(getIntent().getParcelableExtra(Constants.Key.WALLET));

        refreshLayout.setOnRefreshListener(viewModel::fetchTokens);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(tech.synapsenetwork.app.R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case tech.synapsenetwork.app.R.id.action_add: {
                viewModel.showAddToken(this);
            } break;
            case android.R.id.home: {
                viewModel.showTransactions(this, true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        viewModel.showTransactions(this, true);
    }

    private void onTokenClick(View view, Token token) {
        Context context = view.getContext();
        viewModel.showSendToken(context, token.tokenInfo.address, token.tokenInfo.symbol, token.tokenInfo.decimals);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.prepare();
    }

    private void onTokens(Token[] tokens) {
        adapter.setTokens(tokens);
        if (tokens == null || tokens.length == 0) {
            systemView.showEmpty(getString(tech.synapsenetwork.app.R.string.no_tokens));
        }
    }

    private void onError(ErrorEnvelope errorEnvelope) {
        systemView.showError(getString(tech.synapsenetwork.app.R.string.error_fail_load_transaction), this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case tech.synapsenetwork.app.R.id.try_again: {
                viewModel.fetchTokens();
            } break;
        }
    }
}
