package Android_Intro.Lesson_1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Task_2 extends AppCompatActivity {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        TextView tw = findViewById(R.id.tw);

        EditText editTextText = findViewById(R.id.et_text);
        String editText = editTextText.getText().toString();
        if (!editText.equals("")){
            tw.setText("Nice! Let's move on");
        }






    }
}
