package tech.synapsenetwork.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

import tech.synapsenetwork.app.Constants;

/**
 * Created by jakkrit.p on 31/5/2561.
 */

public class CreateQRImage {

    public static Bitmap createQRImage(Context context, WindowManager windowManager, String address) {
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int imageSize = (int) (size.x * Constants.QR_IMAGE_WIDTH_RATIO);

        HashMap hintMap = new HashMap(); 
        hintMap.put(EncodeHintType.MARGIN, 0);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    address,
                    BarcodeFormat.QR_CODE,
                    imageSize,
                    imageSize,
                    hintMap);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(tech.synapsenetwork.app.R.string.error_fail_generate_qr), Toast.LENGTH_SHORT)
                    .show();
        }
        return null;
    }

}
