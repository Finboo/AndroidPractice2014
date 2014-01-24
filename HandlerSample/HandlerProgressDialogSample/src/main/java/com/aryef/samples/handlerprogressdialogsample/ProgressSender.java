package com.aryef.samples.handlerprogressdialogsample;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

/**
 * Created by arye on 23/12/13.
 */
public class ProgressSender implements Runnable{
    private volatile boolean running = true;
    String progress_message_key;
    public ProgressSender(String progressKey)
    {
        progress_message_key = progressKey;

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
            b.putInt("progress_message_key", i++);

            msg.setData(b);

            MainActivity.shandler.handleMessage(msg);

            //  Log.e("send data to mainactivity ","loop" + i);
            if (i>100)
            {
                running = false;
            }

            try {

                Thread.sleep(100);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

}
