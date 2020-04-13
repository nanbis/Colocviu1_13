package ro.pub.cs.systems.eim.Colocviu1_13;

import android.content.Context;
import android.content.Intent;

import java.sql.Date;

class ProcessingThread extends Thread{
    String to_view;

    Context context;
    public ProcessingThread(Context context, String to_view) {
        this.context = context;
        this.to_view = to_view;
    }

    @Override
    public void run() {
        try {
            sleep(5000);
            sendMessage(to_view);
        } catch (Exception e) {

        }
    }

    private void sendMessage(String to_view) {
        Intent intent = new Intent();
        intent.setAction(Constants.SERVICE_ACTION);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " " + to_view);
        context.sendBroadcast(intent);
    }
}
