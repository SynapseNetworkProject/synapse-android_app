package tech.synapsenetwork.app.Router;

import android.content.Context;
import android.content.Intent;

import tech.synapsenetwork.app.ui.AddTokenActivity;

public class AddTokenRouter {

    public void open(Context context) {
        Intent intent = new Intent(context, AddTokenActivity.class);
        context.startActivity(intent);
    }
}
