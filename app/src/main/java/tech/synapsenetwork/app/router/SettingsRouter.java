package tech.synapsenetwork.app.Router;

import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.ui.SettingsActivity;

public class SettingsRouter {

    public void open(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
