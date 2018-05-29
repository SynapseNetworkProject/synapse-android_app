package tech.synapsenetwork.app.Router;


import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.ui.SendActivity;

public class SendTokenRouter {
    public void open(Context context, String address, String symbol, int decimals) {
        Intent intent = new Intent(context, SendActivity.class);
        intent.putExtra(Constants.EXTRA_SENDING_TOKENS, true);
        intent.putExtra(Constants.EXTRA_CONTRACT_ADDRESS, address);
        intent.putExtra(Constants.EXTRA_SYMBOL, symbol);
        intent.putExtra(Constants.EXTRA_DECIMALS, decimals);
        context.startActivity(intent);
    }
}
