package tech.synapsenetwork.app.router;

import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.entity.Transaction;
import tech.synapsenetwork.app.ui.TransactionDetailActivity;

import tech.synapsenetwork.app.Constants;

public class TransactionDetailRouter {

    public void open(Context context, Transaction transaction) {
        Intent intent = new Intent(context, TransactionDetailActivity.class);
        intent.putExtra(Constants.Key.TRANSACTION, transaction);
        context.startActivity(intent);
    }
}
