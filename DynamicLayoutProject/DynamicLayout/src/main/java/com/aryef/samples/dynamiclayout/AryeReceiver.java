package com.aryef.samples.dynamiclayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by arye on 23/12/13.
 */
public class AryeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "hi there" + intent.getAction(), Toast.LENGTH_SHORT).show();

        Intent newintent = new Intent();

        newintent.setClass(context, MainActivity.class);
        newintent.putExtra("open from outside", intent.getAction());
        newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newintent);
    }
}
