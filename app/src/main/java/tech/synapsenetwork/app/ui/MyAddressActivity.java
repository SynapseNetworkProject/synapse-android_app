package tech.synapsenetwork.app.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import tech.synapsenetwork.app.entity.NetworkInfo;
import tech.synapsenetwork.app.entity.Wallet;
import tech.synapsenetwork.app.repository.EthereumNetworkRepositoryType;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import tech.synapsenetwork.app.Constants;
import tech.synapsenetwork.app.util.CreateQRImage;

public class MyAddressActivity extends BaseActivity implements View.OnClickListener {


    public static final String KEY_ADDRESS = "key_address";

    @Inject
    protected EthereumNetworkRepositoryType ethereumNetworkRepository;

    private Wallet wallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        setContentView(tech.synapsenetwork.app.R.layout.activity_my_address);

        toolbar();

        wallet = getIntent().getParcelableExtra(Constants.Key.WALLET);
        NetworkInfo networkInfo = ethereumNetworkRepository.getDefaultNetwork();
        String suggestion = getString(tech.synapsenetwork.app.R.string.suggestion_this_is_your_address, networkInfo.name);
        ((TextView) findViewById(tech.synapsenetwork.app.R.id.address_suggestion)).setText(suggestion);
        ((TextView) findViewById(tech.synapsenetwork.app.R.id.address)).setText(wallet.address);
        findViewById(tech.synapsenetwork.app.R.id.copy_action).setOnClickListener(this);
        final Bitmap qrCode = CreateQRImage.createQRImage(this, getWindowManager(), wallet.address);
        ((ImageView) findViewById(tech.synapsenetwork.app.R.id.qr_image)).setImageBitmap(qrCode);
    }


    @Override
    public void onClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(KEY_ADDRESS, wallet.address);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
