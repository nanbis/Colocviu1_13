package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Colocviu1_13MainActivity extends AppCompatActivity {
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private NavigateListener navigateListener = new NavigateListener();

    int serviceStatus = Constants.SERVICE_STOPPED;

    ArrayList<String> pressed = new ArrayList<String>();

    Button north, south, west, east, navigate;
    TextView textView;

    String to_view = "";
    int total_pressed = 0;

    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.SERVICE_ACTION, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private class NavigateListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_navigate:
                    Intent intent = new Intent(getApplicationContext(), Colocviu1_13SecondaryActivity.class);
                    intent.putExtra(Constants.TO_VIEW, to_view);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    to_view = "";
                    total_pressed = 0;
                    textView.setText(to_view);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            String to_display = "";
            switch (resultCode) {
                case Constants.BUTTON_CANCEL:
                    to_display = "Cancel Button";
            }
            switch (resultCode) {
                case Constants.BUTTON_REGISTER:
                    to_display = "Register Button";
            }
            Toast.makeText(this, "The pressed button was: " + to_display, Toast.LENGTH_LONG).show();
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String to_add = "";
            switch (view.getId()) {
                case R.id.button_north:
                    pressed.add("North");
                    to_add = "North";
                    break;
                case R.id.button_south:
                    pressed.add("South");
                    to_add = "South";
                    break;
                case R.id.button_east:
                    pressed.add("East");
                    to_add = "East";
                    break;
                case R.id.button_west:
                    pressed.add("West");
                    to_add = "West";
                    break;
            }
            if (to_view.equals("")) {
                to_view = to_add;
            } else {
                to_view = to_view + ", " + to_add;
            }
            textView.setText(to_view);
            total_pressed ++;

            if (total_pressed > Constants.THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                intent.putExtra(Constants.TO_VIEW_FIELD, to_view);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(Constants.TO_VIEW, to_view);
        savedInstanceState.putString(Constants.TOTAL_PRESSED, String.valueOf(total_pressed));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.TOTAL_PRESSED)) {
            total_pressed = Integer.parseInt(savedInstanceState.get(Constants.TOTAL_PRESSED).toString());
        }
        if (savedInstanceState.containsKey(Constants.TO_VIEW)) {
            to_view = savedInstanceState.get(Constants.TO_VIEW).toString();
        }
        textView.setText(to_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviul_13_main);

        textView = (TextView)findViewById(R.id.textView);
        west = (Button)findViewById(R.id.button_west);
        east = (Button)findViewById(R.id.button_east);
        north = (Button)findViewById(R.id.button_north);
        south = (Button)findViewById(R.id.button_south);
        navigate = (Button)findViewById(R.id.button_navigate);

        west.setOnClickListener(buttonClickListener);
        east.setOnClickListener(buttonClickListener);
        north.setOnClickListener(buttonClickListener);
        south.setOnClickListener(buttonClickListener);
        navigate.setOnClickListener(navigateListener);

        if (savedInstanceState != null) {
            to_view = "";
            if (savedInstanceState.containsKey(Constants.TOTAL_PRESSED)) {
                total_pressed = Integer.parseInt(savedInstanceState.get(Constants.TOTAL_PRESSED).toString());
            }
            if (savedInstanceState.containsKey(Constants.TO_VIEW)) {
                to_view = savedInstanceState.get(Constants.TO_VIEW).toString();
            }
            textView.setText(to_view);
            Log.println(2, "NUMARUL SALVAT: ", String.valueOf(total_pressed));
        }

        intentFilter.addAction(Constants.SERVICE_ACTION);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_13Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
