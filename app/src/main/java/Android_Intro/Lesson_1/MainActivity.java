package Android_Intro.Lesson_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextName = findViewById(R.id.editTextName); // findViewById - метод, позволяющий
        // найти элемент по id. R позволяет вытащить его сюда и использовать.
        TextView textViewTop = findViewById(R.id.headText);
        Button buttonConfirm = findViewById(R.id.buttonConfirm);
        TextView textViewMiddle = findViewById(R.id.textMiddle);
        TextView checkBoxView = findViewById(R.id.checkBoxText);


        // Для кнопки
        buttonConfirm.setOnClickListener(v -> {
            String userName = editTextName.getText().toString();
            textViewMiddle.setText(String.format("Glad to see you in my great place, %s", userName));
        });

        Switch switchLeft = findViewById(R.id.switchColorLeft);

        Switch switchRight = findViewById(R.id.switchColorRight);

        switchLeft.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                textViewTop.setBackgroundColor(Color.GRAY);
            } else {
                textViewTop.setBackgroundColor(Color.argb(100, 125, 125, 125));
            }

        });

        switchRight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String userName = editTextName.getText().toString();
            if (isChecked) {
                buttonConfirm.setText(">>> OK <<<");
            } else {
                buttonConfirm.setText("Don't do it!");
            }
            checkBoxView.setText("Now choose your side " + userName);
        });

        ToggleButton tb = findViewById(R.id.tb);
        tb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            @SuppressLint("CutPasteId") EditText editTextName1 = findViewById(R.id.editTextName);
            String userName = editTextName1.getText().toString();
            TextView bottom = findViewById(R.id.pass);
            if (isChecked) {
                bottom.setText("Congratulations " + userName + "! You just completed your Task №1!");

                Intent intent = new Intent(MainActivity.this, Task_2.class);
                startActivity(intent);

            } else {
                bottom.setText(userName + "! Complete your Task №1!");
            }
        });

    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;

        boolean checked = checkBox.isChecked();
        EditText editTextName = findViewById(R.id.editTextName);
        String userName = editTextName.getText().toString();
        TextView checkBoxView = findViewById(R.id.jediAnswer);


        switch (view.getId()) {  // Смотрим какой именно отмечен
            case R.id.checkBox:
                if (checked)
                    checkBoxView.setText("Great " + userName + "! Now you've joined to the Android Light force side!");
                break;
            case R.id.checkBox2:
                if (checked)
                    checkBoxView.setText("Perfect, " + userName + "! Now you've joined to the Android Dark force side!");
                break;
            default:
                checkBoxView.setText("");
        }
    }

}