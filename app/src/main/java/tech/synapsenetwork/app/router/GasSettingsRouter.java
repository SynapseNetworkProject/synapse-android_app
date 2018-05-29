package tech.synapsenetwork.app.Router;


import android.app.Activity;
import android.content.Intent;

import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.entity.GasSettings;
import tech.synapsenetwork.app.ui.GasSettingsActivity;
import tech.synapsenetwork.app.viewmodel.GasSettingsViewModel;

public class GasSettingsRouter {
    public void open(Activity context, GasSettings gasSettings) {
        Intent intent = new Intent(context, GasSettingsActivity.class);
        intent.putExtra(Constants.EXTRA_GAS_PRICE, gasSettings.gasPrice.toString());
        intent.putExtra(Constants.EXTRA_GAS_LIMIT, gasSettings.gasLimit.toString());
        context.startActivityForResult(intent, GasSettingsViewModel.SET_GAS_SETTINGS);
    }
}
