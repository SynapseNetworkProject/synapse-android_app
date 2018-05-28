package tech.synapsenetwork.app.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import tech.synapsenetwork.app.entity.NetworkInfo;

public class EmptyTransactionsView extends FrameLayout {

    public EmptyTransactionsView(@NonNull Context context, OnClickListener onClickListener) {
        super(context);

        LayoutInflater.from(getContext())
                .inflate(tech.synapsenetwork.app.R.layout.layout_empty_transactions, this, true);

        findViewById(tech.synapsenetwork.app.R.id.action_buy).setOnClickListener(onClickListener);
    }

    public void setNetworkInfo(NetworkInfo networkInfo) {
        if (networkInfo.isMainNetwork) {
            findViewById(tech.synapsenetwork.app.R.id.action_buy).setVisibility(VISIBLE);
        } else {
            findViewById(tech.synapsenetwork.app.R.id.action_buy).setVisibility(GONE);
        }
    }
}
