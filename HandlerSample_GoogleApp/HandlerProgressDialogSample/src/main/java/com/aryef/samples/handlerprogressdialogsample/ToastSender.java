package com.aryef.samples.handlerprogressdialogsample;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

/**
 * Created by arye on 23/12/13.
 */
public class ToastSender implements Runnable {

    private volatile boolean running = true;
    String toast_key ;
    public ToastSender(String toastKey)
    {
        toast_key= toastKey;

    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {

        running = true;
        int i =0;

        Log.e("entered arye service messaging loop", "start from zero");

        while(running)
        {

            Message msg = new Message();
            Bundle b = new Bundle();
            b.putString("toast_message_key", "hi there!!" + i++);

            msg.setData(b);

            MainActivity.shandler.handleMessage(msg);

            //  Log.e("send data to mainactivity ","loop" + i);

            try {

                Thread.sleep(5000);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }


}
