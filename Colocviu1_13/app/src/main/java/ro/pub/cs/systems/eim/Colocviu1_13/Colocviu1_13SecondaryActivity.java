package ro.pub.cs.systems.eim.Colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.CookieStore;

public class Colocviu1_13SecondaryActivity extends AppCompatActivity {
    private TextView textView;
    private Button cancel, register;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_cancel:
                    setResult(Constants.BUTTON_CANCEL, null);
                    break;
                case R.id.button_register:
                    setResult(Constants.BUTTON_REGISTER, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_secondary);

        cancel = (Button)findViewById(R.id.button_cancel);
        register = (Button)findViewById(R.id.button_register);

        textView = (TextView)findViewById(R.id.textView2);

        cancel.setOnClickListener(buttonClickListener);
        register.setOnClickListener(buttonClickListener);
        Log.println(2, "Aici", "");

        Intent intent = getIntent();
        String to_view = "";
        if (intent != null && intent.getExtras().containsKey(Constants.TO_VIEW)) {
            to_view = intent.getExtras().get(Constants.TO_VIEW).toString();
        }
        textView.setText(to_view);
    }
}
