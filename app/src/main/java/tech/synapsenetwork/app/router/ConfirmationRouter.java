package tech.synapsenetwork.app.Router;


import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.ui.ConfirmationActivity;

import java.math.BigInteger;

public class ConfirmationRouter {
    public void open(Context context, String to, BigInteger amount, String contractAddress, int decimals, String symbol, boolean sendingTokens) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(Constants.EXTRA_TO_ADDRESS, to);
        intent.putExtra(Constants.EXTRA_AMOUNT, amount.toString());
        intent.putExtra(Constants.EXTRA_CONTRACT_ADDRESS, contractAddress);
        intent.putExtra(Constants.EXTRA_DECIMALS, decimals);
        intent.putExtra(Constants.EXTRA_SYMBOL, symbol);
        intent.putExtra(Constants.EXTRA_SENDING_TOKENS, sendingTokens);
        context.startActivity(intent);
    }
}
