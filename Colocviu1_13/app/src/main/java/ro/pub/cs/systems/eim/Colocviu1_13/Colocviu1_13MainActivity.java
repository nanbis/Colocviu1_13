package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Colocviu1_13MainActivity extends AppCompatActivity {
    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    ArrayList<String> pressed = new ArrayList<String>();

    Button north, south, west, east;
    TextView textView;

    String to_view = "";
    int total_pressed = 0;

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

        west.setOnClickListener(buttonClickListener);
        east.setOnClickListener(buttonClickListener);
        north.setOnClickListener(buttonClickListener);
        south.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            to_view = "";
            if (savedInstanceState.containsKey(Constants.TOTAL_PRESSED)) {
                total_pressed = Integer.parseInt(savedInstanceState.get(Constants.TOTAL_PRESSED).toString());
            }
            if (savedInstanceState.containsKey(Constants.TO_VIEW)) {
                to_view = savedInstanceState.get(Constants.TO_VIEW).toString();
            }
            textView.setText(to_view);
        }
    }
}
