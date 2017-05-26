package com.example.nfc_reseach;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends BaseNFCActivity {

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "准备开始写数据", Toast.LENGTH_SHORT).show();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        writeNfcTag(tag);


    }

    private void writeNfcTag(Tag tag) {
        if (tag == null) {
            return;
        }


        NdefMessage ndefMessage = new NdefMessage(
                new NdefRecord[]{NdefRecord.createApplicationRecord(getPackageName())});
        int size = ndefMessage.toByteArray().length;
        Ndef ndef = Ndef.get(tag);
        try {
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable() && ndef.getMaxSize() < size) {
                    return;
                }
                ndef.writeNdefMessage(ndefMessage);
                Toast.makeText(ctx, "写入成功", Toast.LENGTH_SHORT).show();
            } else {//当我们买回来的nfc标签是没有格式化的,或者没有区分的执行此步骤
                NdefFormatable formatable = NdefFormatable.get(tag);
                if (formatable != null) {
                    formatable.connect();
                    formatable.format(ndefMessage);
                    Toast.makeText(ctx, "写成功了!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "写失败了!", Toast.LENGTH_SHORT).show();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }
}
