package tech.synapsenetwork.app.Router;

import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.ui.TokensActivity;

import tech.synapsenetwork.app.Constants;

public class MyTokensRouter {

    public void open(Context context, Wallet wallet) {
        Intent intent = new Intent(context, TokensActivity.class);
        intent.putExtra(Constants.Key.WALLET, wallet);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}
