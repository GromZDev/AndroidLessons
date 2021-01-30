package Android_Intro.Lesson_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class Task_3 extends AppCompatActivity {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "MissingSuperCall"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarview);

        Button button = findViewById(R.id.button);


        button.setOnClickListener(v -> {
            Intent intent = new Intent(Task_3.this, MainActivity.class);
            startActivity(intent);
        });

    }



}
