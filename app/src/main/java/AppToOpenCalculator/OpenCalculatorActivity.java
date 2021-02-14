package AppToOpenCalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import Android_Intro.Lesson_5.R;

public class OpenCalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.openapp_layout);

        Button buttonApp = findViewById(R.id.button_openApp);
        buttonApp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent goToCalculator = new Intent("My_Calculator");
        startActivity(goToCalculator);
    }
}
