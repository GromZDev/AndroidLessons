package Android_Intro.Lesson_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class Task_2 extends AppCompatActivity {


    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);


        Button button = findViewById(R.id.button);


        button.setOnClickListener(v -> {
            Intent intent = new Intent(Task_2.this, Task_3.class);
            startActivity(intent);
        });


    }
}
