package tech.synapsenetwork.app.router;

import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.ui.MyAddressActivity;

import tech.synapsenetwork.app.Constants;

public class MyAddressRouter {

    public void open(Context context, Wallet wallet) {
        Intent intent = new Intent(context, MyAddressActivity.class);
        intent.putExtra(Constants.Key.WALLET, wallet);
        context.startActivity(intent);
    }
}
