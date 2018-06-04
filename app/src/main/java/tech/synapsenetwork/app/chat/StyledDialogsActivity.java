package tech.synapsenetwork.app.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.Date;

import tech.synapsenetwork.app.R;
import tech.synapsenetwork.app.chat.common.data.fixtures.DialogsFixtures;
import tech.synapsenetwork.app.chat.common.data.model.Dialog;

public class StyledDialogsActivity extends DemoDialogsActivity implements DateFormatter.Formatter {

    public static void open(Context context) {
        context.startActivity(new Intent(context, StyledDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled_dialogs);
        getSupportActionBar().show();
        dialogsList = findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        optionsDialog();
    }

    private void optionsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_chat_options, null,false);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();

        dialogView.findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomMediaMessagesActivity.open(v.getContext());
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialogView.findViewById(R.id.video_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        alertDialog.getWindow().setLayout(300,300);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else if (DateFormatter.isCurrentYear(date)) {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(super.imageLoader);
        super.dialogsAdapter.setItems(DialogsFixtures.getDialogs());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);
        super.dialogsAdapter.setDatesFormatter(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
