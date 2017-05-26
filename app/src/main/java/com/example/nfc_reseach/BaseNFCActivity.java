package com.example.nfc_reseach;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 瑜哥 on 2017/5/26.
 */

public class BaseNFCActivity extends AppCompatActivity {

    private NfcAdapter defaultAdapter;
    private PendingIntent pendingIntent;

    @Override
    protected void onStart() {
        super.onStart();
        defaultAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (defaultAdapter != null) {
            defaultAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (defaultAdapter != null) {
            defaultAdapter.disableForegroundDispatch(this);
        }
    }
}
