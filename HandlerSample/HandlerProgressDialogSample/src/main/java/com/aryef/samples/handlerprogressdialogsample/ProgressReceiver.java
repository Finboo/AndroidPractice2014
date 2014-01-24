package com.aryef.samples.handlerprogressdialogsample;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by arye on 23/12/13.
 */
public class ProgressReceiver extends Handler {

    private final WeakReference<MainActivity> mTarget;
    String Progress_key;

    ProgressReceiver(MainActivity target, String toastKey) {
        mTarget = new WeakReference <MainActivity>(target);
        Progress_key = toastKey;
        if(Progress_key.isEmpty())
        {
            // throw new Exception("key cannot be empy");
        }
    }

    @Override
    public void handleMessage(Message msg) {

        final MainActivity target = mTarget.get();
        Bundle b = msg.getData();
        final int progress = b.getInt(Progress_key);
        target.mdialog.setProgress(progress);

        if(progress >=100)
        {
            target.mdialog.dismiss();

          /*  if(target.thread_ProgressSender != null)
            {

                try {
                    target.thread_ProgressSender.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }*/

        target.runOnUiThread(new Runnable()
        {
            public void run()
            {
                Toast.makeText(target.getApplicationContext(), "finished!", Toast.LENGTH_SHORT).show();
            }
        });
        }


        // target.showToast(message);
        super.handleMessage(msg);

    }
}
