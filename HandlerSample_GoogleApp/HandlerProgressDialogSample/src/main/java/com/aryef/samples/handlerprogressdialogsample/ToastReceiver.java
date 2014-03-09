package com.aryef.samples.handlerprogressdialogsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by arye on 23/12/13.
 */
public class ToastReceiver extends Handler{

        private final WeakReference<Activity> mTarget;
        String toast_key;

        ToastReceiver(MainActivity target, String toastKey) {
            mTarget = new WeakReference <Activity>(target);
            toast_key = toastKey;
            if(toast_key.isEmpty())
            {
                // throw new Exception("key cannot be empy");
            }
        }

        @Override
        public void handleMessage(Message msg) {

            final Activity target = mTarget.get();
            Bundle b = msg.getData();
            final String message = b.getString(toast_key);

            target.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(target.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
            // target.showToast(message);
            super.handleMessage(msg);

        }
}
