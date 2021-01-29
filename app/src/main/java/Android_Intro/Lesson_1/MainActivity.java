package Android_Intro.Lesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextName = findViewById(R.id.editTextName); // findViewById - метод, позволяющий
        // найти элемент по id. R позволяет вытащить его сюда и использовать.
        TextView textViewTop = findViewById(R.id.headText);
        Button buttonConfirm = findViewById(R.id.buttonConfirm);
        TextView textViewMiddle = findViewById(R.id.textMiddle);



        buttonConfirm.setOnClickListener(new View.OnClickListener() { // Для кнопки
            @Override
            public void onClick(View v) {
                String userName = String.valueOf(editTextName.getText().toString());
                textViewMiddle.setText("Glad to see you in my great place, " + userName);
            }
        });

        Switch switchLeft = findViewById(R.id.switchColorLeft);

        Switch switchRight = findViewById(R.id.switchColorRight);

        switchLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    textViewTop.setBackgroundColor(Color.GRAY);
                } else {
                    textViewTop.setBackgroundColor(Color.MAGENTA);
                }

            }
        });

        switchRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    buttonConfirm.setText(">>> OK <<<");
                } else {
                    buttonConfirm.setText("Don't do it!");
                }
            }
        });



    }



}