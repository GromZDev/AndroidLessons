package Android_Intro.Lesson_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class Task_3 extends AppCompatActivity {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n", "MissingSuperCall"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarview);


        TextView tw = findViewById(R.id.tw_task3);

        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_3.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



}
