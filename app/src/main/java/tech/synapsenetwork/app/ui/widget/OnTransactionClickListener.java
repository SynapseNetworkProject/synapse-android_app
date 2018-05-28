package tech.synapsenetwork.app.ui.widget;

import android.view.View;

import tech.synapsenetwork.app.entity.Transaction;

public interface OnTransactionClickListener {
    void onTransactionClick(View view, Transaction transaction);
}
