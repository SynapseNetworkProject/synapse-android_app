package tech.synapsenetwork.app.router;

import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.ui.SendActivity;

public class SendRouter {

    public void open(Context context) {
        Intent intent = new Intent(context, SendActivity.class);
        context.startActivity(intent);
    }
}
