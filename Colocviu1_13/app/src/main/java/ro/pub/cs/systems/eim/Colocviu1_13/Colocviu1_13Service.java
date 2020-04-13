package ro.pub.cs.systems.eim.Colocviu1_13;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu1_13Service extends Service {
    private ProcessingThread thread = null;
    public Colocviu1_13Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String to_view = intent.getStringExtra(Constants.TO_VIEW_FIELD);
        thread = new ProcessingThread(this, to_view);
        thread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        thread.interrupt();
        thread = null;
    }
}
